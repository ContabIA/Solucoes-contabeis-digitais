const cypress = require('cypress');
const fs = require("fs");
const path = require("path");

async function enviarDados(dados){
    return new Promise((resolve, reason) => {
      
      fs.writeFileSync(path.join(__dirname, "/saidaTeste.txt"), dados);
      resolve("funcionou!!!");

    });
};


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

main()
