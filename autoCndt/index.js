#!/bin/env node index.js
const fs = require('fs');
const { resolve, join } = require('path');
const RunCypress = require(join(__dirname, "/RunCypress"))

class RunArgs{
    constructor (env){
        this.env = env;
        this.spec = './cypress/e2e/certidao.cy.js';
        this.configFile = './cypress.config.js';
        this.headed = false;
        this.browser ='firefox';
        this.quiet = true;
    }
};

// função para gerar um Obj que divide os indices para o fetch em um periodo de dias 
function getObjNumeroDia(periodo, digitos){
    
    // cria um array para cada dia do periodo
    var resp = new Array(periodo);

    // quantidade de numeros a serem divididos
    let NumIndices = Math.pow(10, digitos);

    // tamanho de cada array dentro de resposta
    let insideArrayLegth = Math.floor( NumIndices/periodo);

    // criandos os arrays dentro de resposta com +1 de tamanho
    for (let i = 0; i < NumIndices % periodo; i++){
        resp[i] = new Array(insideArrayLegth + 1);
    }

    // criando os arrays dentro de resposta com o tamanho normal
    for (let i = NumIndices % periodo; i < periodo; i++){
        resp[i] = new Array(insideArrayLegth);
    };
    
    // varrendo a matriz e dividindo os numeros para cada um dos espaços
    let dia = -1;
    for (let i = 0; i < periodo; i++){
        for (let j = 0; j < resp[i].length; j++){
            dia++;
            resp[i][j] = dia;
        }
    };
    
    return resp;

}

// função para pegar os cnpjs
async function getInput(){

    // data atual
    let dataObj = new Date();
    
    // obj que separa 10 numeros para 7 dias
    let objNumerosSemanal = getObjNumeroDia(7, 1);

    // dia da semana atual
    let diaSemana = dataObj.getDay() + 1

    // array resposta
    let resp = new Array()

    // fazendo fetch para pegar todos os cnpj's semanais para consultar hoje
    for (let i = 0; i < objNumerosSemanal[diaSemana].length; i++){

        let req = await fetch("http://localhost:8080/service/getCnpj?tamanhoFinal=1&ultimoDigito=" + objNumerosSemanal[diaSemana][i] + "&frequencia=1&tipoConsulta=3")
        let reqJson = await req.json()
        resp = resp.concat(reqJson["cnpjs"])
    };

    // pega quantos dias tem nesse mes
    let diasMes = new Date(dataObj.getFullYear(), dataObj.getMonth(), 0).getDate();

    // obj que separa 100 numeros para os dias do mes
    let objNumerosMensal = getObjNumeroDia(diasMes, 2);
    
    // dia do mes atual
    let diaMes = dataObj.getDate();

    // fazendo fetch para pegar todos os cnpj's mensais para consultar hoje
    for (let i = 0; i < objNumerosMensal[diaMes].length; i++){
        let req = await fetch("http://localhost:8080/service/getCnpj?tamanhoFinal=2&ultimoDigito=" + objNumerosMensal[diaMes][i] + "&frequencia=2&tipoConsulta=3")
        let reqJson = await req.json()
        resp = resp.concat(reqJson["cnpjs"])
    };

    // dias do ano
    let diasAno = 365;
    if (dataObj.getFullYear() % 4 == 0) diasAno++;

    // obj que separa 100 numeros para os dias do ano
    let objNumerosAnual = getObjNumeroDia(diasAno, 2);

    // dia do ano atual
    let diaAno =   Math.floor((dataObj - new Date(dataObj.getFullYear(), 0, 1))/(1000*60*60*24)) + 1;

    // fazendo fetch para pegar todos os cnpj's anuais para consultar hoje
    for (let i = 0; i < objNumerosAnual[diaAno].length; i++){
        let req = await fetch("http://localhost:8080/service/getCnpj?tamanhoFinal=2&ultimoDigito=" + objNumerosAnual[diaAno][i] + "&frequencia=3&tipoConsulta=3")
        let reqJson = await req.json()
        resp = resp.concat(reqJson["cnpjs"])
    };


    return resp;

};

function getFormatedDate(){
    let dateObj = new Date();

    let year = dateObj.getFullYear();
    
    let month = (dateObj.getMonth()+1).toString().padStart(2,"0");

    let date = dateObj.getDate().toString().padStart(2,"0");

    return year + "-" + month + "-" + date;
}

// função para enviar o output
function sendOutput(){

    let dateObj = new Date();

    // pegando todos os outputs gerados
    let outputs_cypress = fs.readFileSync("autoCndt_output.txt").toString().split("\n");

    console.log("autoCndt/index.js/sendOutput - outputArq: " + outputs_cypress);

    // transformandos todos os outputs de strig para obj
    outputs_cypress = outputs_cypress.map(element => {

        let args = element.split(":");

        return {
            status : parseInt(args[1]),
            novo : true,
            cnpjEmpresa : args[0],
            data : getFormatedDate()
        };
    });
    
    // criando o body da requisição
    body = {
        listaRespostas : outputs_cypress.slice(1)
    }
    
    console.log("autoCndt/index.js/sendOutput - req.body: " + JSON.stringify(body))

    //enviandos os outputs para a API
    fetch("http://localhost:8080/service/respCndt",{
        method : "POST",
        body : JSON.stringify(body),
        headers: {
            "Content-Type": "application/json",
        }
    });

};


async function loop(){

    fs.writeFileSync(join(__dirname, "autoCndt_output.txt"), "");

    return new Promise((resolve, reject)=>{

        // lista com todos os cnpjs
        getInput().then((listaCnpj)=>{


            if (listaCnpj.length === 0) {resolve("no entry's")}
            console.log(listaCnpj);

            let lastObj = undefined;
            
            //loop para varer os cnpjs
            for (let i = 0; i < listaCnpj.length; i++){
                // criando os objetos
                lastObj = new RunCypress(new RunArgs("cnpj="+listaCnpj[i]), lastObj);
            };

            lastObj.run().then((value)=>{
                resolve(value);
            }, (reason) => {
                reject(reason);
            });
        });
    });
}

// função principal do documento
async function main(){

    fs.writeFileSync(join(__dirname, "autoCndt_output.txt"), "");

    await loop()
    .then(console.log)
    .catch(console.error);

    console.log("\n\n\n\n\nenviando os dados");
    sendOutput();
    console.log("dados enviados");

};

main()