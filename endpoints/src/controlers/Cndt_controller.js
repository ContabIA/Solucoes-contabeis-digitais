// importando a função main da automação cndt
const main_automacao_Cndt = require("../../../automações/autoCndt/index.js");


let controller_cndt = {} // objeto controlador

// controlador main da automação
async function call_automacao_Cndt() {
    main_automacao_Cndt(); // chama a função main da automação
    return {};
}
controller_cndt.call_automacao_Cndt = call_automacao_Cndt;



//exportando o controlador
module.exports = controller_cndt