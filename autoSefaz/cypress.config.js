const { defineConfig } = require("cypress");
const fs = require("fs");

module.exports = defineConfig({
  e2e: {
    experimentalStudio: true,
    specPattern: 'cypress/e2e/login.cy.js',
    baseUrl: 'https://www4.sefaz.pb.gov.br/atf/seg/SEGf_LoginSERVirtual.jsp',
    
    setupNodeEvents(on, config){
      on('task', {
        escreverJson({registro, quebra}){
          let conteudo = {
            id : parseInt(registro.id),
            serie : parseInt(registro.serie),
            data : registro.data.split("/")[2] + "-" + registro.data.split("/")[1] + "-" + registro.data.split("/")[0],
            nomeEmitente : registro.nomeEmitente,
            situacao : registro.situacao,
            valor : registro.valor,
            cnpjEmpresa : registro.cnpjEmpresa
          }
          fs.writeFileSync('notas.json', JSON.stringify(conteudo), {flag: 'a+'})
          fs.writeFileSync('notas.json', quebra, {flag: 'a+'})

          return null
        }
      })
    }
  },
}
);

