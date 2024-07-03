const cypress = require('cypress');


var runArgs = {
    spec: './cypress/e2e/certidao.cy.js', 
    configFile: './cypress.config.js', 
    env:'', 
    headed:true, 
    browser:'firefox'
};

// função para pegar os cnpjs
function getInput(){

    // função temporaria para testes
    let fs = require('fs');
    let path = require('path');

    return fs.readFileSync(path.join(__dirname, "testeInput.txt")).toString().split('\n');

};

// função para enviar o output
function sendOutput(output){

    // função temporaria para testes 
    let fs = require("fs");
    let path = require("path");
    
    fs.writeFileSync(path.join(__dirname, "testeOutput"), output)
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
        runCypress(runArgs, (resultados) => {

            // envia os resultados 
            sendOutput(listaCnpj[i] + ":" + resultados.status);
        });
    };
};
