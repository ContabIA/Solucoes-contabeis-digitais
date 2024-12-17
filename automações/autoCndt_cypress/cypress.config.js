const { defineConfig } = require("cypress");
const {solveCaptcha, writeOutput, verifyPdf} = require("./cypress/support/e2e");

module.exports = defineConfig({
  e2e: {
    setupNodeEvents(on, config) {
      on('task', {
        async solveCap(){
          var ret = await solveCaptcha();
          console.log(ret);
          return ret;
        },
        async writeOut(Objeto){
          var retorno = await writeOutput(Objeto)
          console.log(Objeto)
          return retorno
        },
        async pdfStatus(cnpj){
          let ret = await verifyPdf(cnpj);
          console.log(ret);
          return ret;
        }

        
      });
    },
  },
});