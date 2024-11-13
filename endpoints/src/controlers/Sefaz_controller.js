// importando a função main da automação cndt
const main_automacao_Sefaz = require("../../../automações/autoSefaz/index.js");

let controller_Sefaz = {} // objeto controlador

// controlador main da automação
async function call_automacao_Sefaz() {
    main_automacao_Sefaz(); // chama a função main da automação
    return {};
}
controller_Sefaz.call_automacao_Sefaz = call_automacao_Sefaz;

//exportando o controlador
module.exports = controller_Sefaz