const { defineConfig } = require("cypress");
const fs = require("fs");

module.exports = defineConfig({
  e2e: {
    experimentalStudio: true,
    specPattern: 'cypress/e2e/login.cy.js',
    baseUrl: 'https://www4.sefaz.pb.gov.br/atf/seg/SEGf_LoginSERVirtual.jsp',
    
    setupNodeEvents(on, config){
      on('task', {
        escreverJson({registro, flag}){
         
          fs.writeFileSync('notas.json', registro, {flag: flag})

          return null
        }
      })
    }
  },
}
);

