const cypress = require("cypress");
const fs = require("fs");
const path = require("path");

async function enviarDados(dados){
    return new Promise(async (resolve, reason) => {
      
      for (let i = 0; i < dados["runs"].length; i++){
        await fetch("http://localhost:8080/service/respSefaz", {
          method: "POST",
          body: JSON.stringify(dados["runs"][i]),
          headers: {
            "Content-Type": "application/json",
        }
        })
      }
      
      
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

  for (let i = 0; i < listaFinaisIdSemanal.length; i++){
    let req = await fetch("http://localhost:8080/service/getCnpj?tamanhoFinal=1&ultimoDigito=" + listaFinaisIdSemanal[i] + "&frequencia=1&tipoConsulta=1");
    let reqJson = await req.json();
    listaCnpj = listaCnpj.concat(reqJson["cnpjs"]);
  }
  
  let diasMes = new Date(diaAtual.getFullYear(), diaAtual.getMonth(), 0).getDate();

  let listaFinaisIdMensal = getListaNumeroDia(diasMes, 2, diaAtual);

  for (let i = 0; i < listaFinaisIdMensal.length; i++){
    let req = await fetch("http://localhost:8080/service/getCnpj?tamanhoFinal=2&ultimoDigito=" + listaFinaisIdMensal[i] + "&frequencia=2&tipoConsulta=1");
    let reqJson = await req.json();
    listaCnpj = listaCnpj.concat(reqJson["cnpjs"]);
  }

  return listaCnpj
}

async function getObjDadosLogin(listaCnpj){
  var objDadosLogin = new Object;
  for (let i = 0; i < listaCnpj.length; i++){
    let dadosLogin = await fetch("http://localhost:8080/service/getDadosLogin?cnpjEmpresa="+listaCnpj[i]);
    let respJson = await dadosLogin.json();
    let infoLogin = respJson.dadosLogin[0].split(",");
    if (objDadosLogin[infoLogin[0]] == null){
      objDadosLogin[infoLogin[0]] = [infoLogin[1], listaCnpj[i]]
    } else {
      objDadosLogin[infoLogin[0]] = objDadosLogin[infoLogin[0]].concat(listaCnpj[i])
    }
  }
  return objDadosLogin
}

async function runAuto(listaCnpj){
  let dateObj = new Date()
  getObjDadosLogin(listaCnpj).then((objDadosLogin) => {
    cypress
    .run({
      spec: './cypress/e2e/login.cy.js',
      env: {"dadosLogin" : objDadosLogin, diaUm : (dateObj.getDay() === 1)},
      headed: true,
      browser: "electron",
    })
    .then(() => {
      let dadosJson = JSON.parse(fs.readFileSync(path.join(__dirname, "/notas.json")));

      return (enviarDados(dadosJson));
    })
    // .catch((err)  => {
    //   return err;
    // })
  })
}

async function main(){
  
  if (dateObj.getDay() === 1){
    let listaCnpj = new Array()
    let req = await fetch("http://localhost:8080/service/getCnpj?tamanhoFinal=1&ultimoDigito=&frequencia=&tipoConsulta=1");
    let reqJson = await req.json();
    listaCnpj = listaCnpj.concat(reqJson["cnpjs"]);
    console.log(listaCnpj)
    await runAuto(listaCnpj)
    
  } 
  getInput().then(async (listaCnpj)=> {
    if (listaCnpj.length === 0){
      return 'no entrys'
    }
    console.log(listaCnpj)
    return await runAuto(listaCnpj)
    .catch((err) => {
      return 'oi'
    })
     
    })
  
}
  
main()
