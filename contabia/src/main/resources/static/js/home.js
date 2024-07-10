import { formataCnpj,  TRADUZ_MES } from "./main.js";

var listAltCnpj = document.querySelectorAll(".nomeAlt")
listAltCnpj.forEach(AltCnpj => {
    var textAltCnpj = AltCnpj.textContent.split(' - ')
    var altCnpjFormatado = formataCnpj(textAltCnpj[1])
    AltCnpj.innerHTML = textAltCnpj[0] + ' - ' + altCnpjFormatado + ' - ' + TRADUZ_MES[textAltCnpj[2]]
});