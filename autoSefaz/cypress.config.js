const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    experimentalStudio: true,
    specPattern: 'cypress/e2e/login.cy.js',
    baseUrl: 'https://www4.sefaz.pb.gov.br/atf/seg/SEGf_LoginSERVirtual.jsp',
    
  },
}
);

