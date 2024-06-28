const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    experimentalStudio: true,
    setupNodeEvents(on, config) {
      on('task', {
        cria_arquivo_csv() {
          console.log(tabela_resumo)

          return null
        }

      })
      // implement node event listeners here
    },
    specPattern: 'cypress/e2e/login.cy.js',
    baseUrl: 'https://www4.sefaz.pb.gov.br/atf/seg/SEGf_LoginSERVirtual.jsp',
    
  },
}
);

