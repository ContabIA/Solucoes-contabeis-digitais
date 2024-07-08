const cypress = require('cypress');
const fs = require('fs');

var runArgs = {
    spec: './cypress/e2e/certidao.cy.js', 
    configFile: './cypress.config.js', 
    env:'', 
    headed:true, 
    browser:'edge'
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
    let dia = 0;
    for (let i = 0; i < periodo; i++){
        for (let j = 0; j < resp[i].length; j++){
            dia++;
            resp[i][j] = dia;
        }
    };
    
    return resp;

}

// função para pegar os cnpjs
function getInput(){

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
    resp = resp.concat(fetch("https:/service/getCnpj?tamanhoFinal=1&ultimoDigito=" + objNumerosSemanal[diaSemana][i] + "&frequencia=1&tipoConsulta=3"));
    };

    // pega quantos dias tem nesse mes
    let diasMes = new Date(dataObj.getFullYear(), dataObj.getMonth(), 0).getDate();

    // obj que separa 100 numeros para os dias do mes
    let objNumerosMensal = getObjNumeroDia(diasMes, 2);
    
    // dia do mes atual
    let diaMes = dataObj.getDate();

    // fazendo fetch para pegar todos os cnpj's mensais para consultar hoje
    for (let i = 0; i < objNumerosMensal[diaMes].length; i++){
        resp = resp.concat(fetch("https:/service/getCnpj?tamanhoFinal=2&ultimoDigito=" + objNumerosMensal[diaMes][i] + "&frequencia=2&tipoConsulta=3"));
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
        resp = resp.concat(fetch("https:/service/getCnpj?tamanhoFinal=2&ultimoDigito=" + objNumerosAnual[diaAno][i] + "&frequencia=3&tipoConsulta=3"));
    };


    return resp;

};

// função para enviar o output
function sendOutput(){

    // pegando todos os outputs gerados
    let outputs_cypress = fs.readFileSync("autoCndt_output.txt").split("\n");

    // transformandos todos os outputs de strig para obj
    outputs_cypress.forEach(element => {

        let { cnpj , status } = element.split(":")

        return {
            status : status,
            novo : true,
            cnpjEmpresa : cnpj,
        };
    });
    
    // criando o body da requisição
    body = {
        "listaRespostas" : outputs_cypress
    }
    

    //enviandos os outputs para a API
    fetch("http://localhost:8080/service/respCndt",{
        "method":"POST",
        "body":body
    });
};


// roda o cypress asyncronamente usando os argumentos em runArgs e depois retorna os resultados para o callback
async function runCypress(runArgs, callback){

    // roda o cypress utilizando os argumentos  e 
    cypress.run(runArgs).then(callback);

};

// função principal do documento
function main(){

    // lista com todos os cnpjs
    let listaCnpj = getInput();

    //loop para varer os cnpjs
    for (let i = 0; i < listaCnpj.length; i++){

        // seta o valor de cnpj
        runArgs.env = "cnpj=" + listaCnpj[i];

        // run cypress/e2e/certidao.cy.js
        runCypress(runArgs, (resultados) => {});

        
    };
    sendOutput()
};

main()