//const cypress = require("cypress");
const fs = require("fs");
const path = require("path");

async function enviarDados(dados){
    return new Promise((resolve, reason) => {
      
      fs.writeFileSync(path.join(__dirname, "/saidaTeste.txt"), dados);
      resolve("funcionou!!!");

    });
};

function getListaNumeroDia(periodo, digitos, diaAtual){
    
  var resp = new Array();
  if (periodo == 7){
    diaAtual = diaAtual.getDay()
  } else if (periodo > 7 & periodo <= 31){
    diaAtual = diaAtual.getDate()
  }

  let finalIdInserir = diaAtual
  while(Math.floor(finalIdInserir / Math.pow(10, digitos)) == 0){

    resp.push(finalIdInserir)
    finalIdInserir += periodo
  }  
    
  return resp

}

async function getInput(){
  
  let diaAtual = new Date();

  let listaCnpj = new Array();

  let listaFinaisIdSemanal = getListaNumeroDia(7, 1, diaAtual);

  for (let i = 0; listaFinaisIdSemanal.length; i++){
    let req = await fetch("http://localhost:8080/service/getCnpj?tamanhoFinal=1&ultimoDigito=" + listaFinaisIdSemanal[i] + "&frequencia=1&tipoConsulta=1");
    let reqJson = await req.json();
    listaCnpj = listaCnpj.concat(reqJson["cnpjs"]);
  }
  
  let diasMes = new Date(dataObj.getFullYear(), dataObj.getMonth(), 0).getDate();

  let listaFinaisIdMensal = getListaNumeroDia(diasMes, 2);

  for (let i = 0; listaFinaisIdMensal.length; i++){
    let req = await fetch("http://localhost:8080/service/getCnpj?tamanhoFinal=2&ultimoDigito=" + listaFinaisIdSemanal[i] + "&frequencia=2&tipoConsulta=1");
    let reqJson = await req.json();
    listaCnpj = listaCnpj.concat(reqJson["cnpjs"]);
  }

  return listaCnpj
}

async function loginSefaz(){
  let dadosLogin = await fetch("http://localhost:8080/service/")
  let respJson = await dadosLogin.json()
  let infoLogin = respJson.body.respJson;

  return infoLogin
}

async function main(){
  //let resp = await fetch('http://localhost:8080/service/getCnpj?tamanhoFinal={tamanho}&ultimoDigito={digitos}&frequencia={1:semanal,2:mensal,3:anual}&tipoConsulta=1'); 
  //let respJSON = await listaCnpj.json();
  //let listaCnpjs = respJSON.body.listaCnpjs;
  let listaCpnjs = ["09197286000111", "04006932000100", "04006932000100", "00326425000195", "43699175000141", "31474812000100", "43331703000105"];

  
  cypress
  .run({
    spec: './cypress/e2e/login.cy.js',
    env: {"cnpjs" : listaCpnjs},
    headed: true,
    browser: "electron",
  })
  .then((results) => {
    console.log(results)
    let dadosJson = JSON.parse(fs.readFileSync(path.join(__dirname, "/notas.json")));

    return (enviarDados(dadosJson));
  })
  .catch((err)  => {
    console.error(err)
  })

}

listaCnpj = getInput()
console.log(listaCnpj)
