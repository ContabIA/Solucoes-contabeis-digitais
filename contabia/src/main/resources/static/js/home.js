import { formataCnpj,  TRADUZ_MES } from "./main.js";


// Coleta todas as alterações que estão na tela e formata o cnpj e traduz o mês
var listAltCnpj = document.querySelectorAll(".nomeAlt")
listAltCnpj.forEach(AltCnpj => {
    var textAltCnpj = AltCnpj.textContent.split(' - ')
    var altCnpjFormatado = formataCnpj(textAltCnpj[1])
    AltCnpj.innerHTML = textAltCnpj[0] + ' - ' + altCnpjFormatado + ' - ' + TRADUZ_MES[textAltCnpj[2]]
});
