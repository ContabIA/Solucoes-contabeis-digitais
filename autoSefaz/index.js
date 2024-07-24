const cypress = require("cypress");
const fs = require("fs");
const path = require("path");

// Função que envia as notas para a aplicação Spring
async function enviarDados(dados){
    return new Promise(async (resolve) => {
      
      // Loop com requisição a cada lista de notas da resposta da automação
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

// Função que retorna a lista com finais de id que deve ser consultado na requisição.
function getListaNumeroDia(periodo, digitos, diaAtual){
  // periodo = 7 para consulta semanal ou a quantidade de dias do mês para consulta mensal
  // digitos = quantidade de digitos do fim do id (1 para consulta semanal e 2 para consulta mensal)
    
  var listaFimId = new Array(); // Lista para armazenar os fins de id que devem ser consultados naquele dia.
  
  // Coleta o dia da semana em número(0 à 6) ou a data do mês (1 à 28,29,30 ou 31) de acordo com o período passado como parâmetro.
  if (periodo == 7){
    diaAtual = diaAtual.getDay()
  } else if (periodo > 7 & periodo <= 31){
    diaAtual = diaAtual.getDate()
  }

  let finalIdInserir = diaAtual // Define primeiro id inserir como o dia atual.
  // Loop até o fim do tamanho do periodo.
  while(Math.floor(finalIdInserir / Math.pow(10, digitos)) == 0){

    listaFimId.push(finalIdInserir) // Insere final id na lista.
    finalIdInserir += periodo
  }  
    
  return listaFimId

}

// Função que pega todos os cnpj's do dia atual
async function getInput(){
  
  let diaAtual = new Date(); // Variável com data atual
  let listaCnpj = new Array(); // // Lista para guardar os cnpj's.

  let listaFinaisIdSemanal = getListaNumeroDia(7, 1, diaAtual); // Lista com todos os finais de id corlistaFimIdondente daquele dia para as consultas semanais.

  // Requisita os cnpj's as consultas semanais para cada fim de id fornecido.
  for (let i = 0; i < listaFinaisIdSemanal.length; i++){
    // Faz a requisição e acessa o body com a lista de cnpj's.
    let req = await fetch("http://localhost:8080/service/getCnpj?ultimoDigito=" + listaFinaisIdSemanal[i] + "&frequencia=1&tipoConsulta=1");
    let reqJson = await req.json();
    listaCnpj = listaCnpj.concat(reqJson["cnpjs"]); // Concatena os cnpj's adquiridos da requisição à lista com todos os cnpj's.
  }
  
  let diasMes = new Date(diaAtual.getFullYear(), diaAtual.getMonth(), 0).getDate(); // Variável com a quantidade de dias do mês.

  let listaFinaisIdMensal = getListaNumeroDia(diasMes, 2, diaAtual);  // Lista com todos os finais de id correspondente daquele dia das consultas mensais.

  for (let i = 0; i < listaFinaisIdMensal.length; i++){
    let req = await fetch("http://localhost:8080/service/getCnpj?ultimoDigito=" + listaFinaisIdMensal[i] + "&frequencia=2&tipoConsulta=1");
    let reqJson = await req.json();
    listaCnpj = listaCnpj.concat(reqJson["cnpjs"]); // Concatena os cnpj's adquiridos da requisição à lista com todos os cnpj's.
  }

  return listaCnpj
}

// Função que coleta dados do login sefaz e relaciona com seus respectivos cnpj's
async function getObjDadosLogin(listaCnpj){
  let objDadosLogin = new Object; // Variavel do tipo objeto que guardará os dados do login e seus respectivos cnpj's.

  // Faz requisição que retorna os dados do login a cada cnpj da lista passada como argumento.
  for (let i = 0; i < listaCnpj.length; i++){
    // Faz requisição e coleta resposta do body
    let dadosLogin = await fetch("http://localhost:8080/service/getDadosLogin?cnpjEmpresa="+listaCnpj[i]);
    let respJson = await dadosLogin.json();
    let infoLogin = respJson.dadosLogin[0].split(",");

    if (objDadosLogin[infoLogin[0]] == null){
      objDadosLogin[infoLogin[0]] = [infoLogin[1], listaCnpj[i]] // cria chave no objeto caso não exista.
    } else {
      objDadosLogin[infoLogin[0]] = objDadosLogin[infoLogin[0]].concat(listaCnpj[i]) // adiciona cnpj em chave existente.
    }
  }
  return objDadosLogin
}

// Função que roda automação.
async function runAuto(listaCnpj){
  let dataAtual = new Date() // Variável com a data atual.

  // Coleta dados do login sefaz e roda automação
  getObjDadosLogin(listaCnpj).then((objDadosLogin) => {
    cypress
    .run({
      spec: './cypress/e2e/login.cy.js',
      env: {"dadosLogin" : objDadosLogin, diaUm : (dataAtual.getDay() === 1)},
      headed: false,
      browser: "electron",
    })
    .then(() => {
      let dadosJson = JSON.parse(fs.readFileSync(path.join(__dirname, "/notas.json"))); // Variável que lê o arquivo notas.json, criado pela automação

      return (enviarDados(dadosJson)); // Envia dados para a aplicação Spring
    })
  })
}

// Função main que coordena as funções auxiliares criadas.
async function main(){
  
  let dataAtual = new Date() // Variável com a data atual.

  // Verifica se o dia atual é dia 1, se for antes de fazer as consultas diárias faz a consulta do último mês para cada cnpj cadastrado no banco.
  if (dataAtual.getDay() === 1){
    let listaCnpj = new Array()  // Lista para guardar os cnpj's.
    let req = await fetch("http://localhost:8080/service/getCnpj?ultimoDigito=&frequencia=&tipoConsulta=1"); // Requisição que retorna no body uma lista com todos os cnpj's.
    
    // Acessa o body para pegar a lista.
    let reqJson = await req.json();
    listaCnpj = listaCnpj.concat(reqJson["cnpjs"]);
    
    await runAuto(listaCnpj)  // Roda função que chama a automação.
  } 

  // Chama função getInput e verifica se existe cnpj's naquele dia.
  getInput().then(async (listaCnpj)=> {
    if (listaCnpj.length === 0){
      return 'no entrys' // Retorna uma mensagem que não há entradas para aquele dia.
    }
    console.log(listaCnpj)
    return await runAuto(listaCnpj) // Roda função que chama a automação.
    .catch(console.error)
    })
  
}
  
main()
