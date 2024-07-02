const cypress = require('cypress')

var runArgs = {
    spec: './cypress/e2e/certidao.cy.js', 
    configFile: './cypress.config.js', 
    env:'cnpj=123.456.78/9101-11', 
    headed:true, 
    browser:'firefox'
}

cypress.run(runArgs).then((result) => {
    console.log('ok');
});
