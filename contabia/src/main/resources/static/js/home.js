import { formataData, TRADUZ_MES, formataCnpj } from "./main.js";

var cnpj  = document.getElementById("user")
var textCnpj = cnpj.textContent.split('')
var cnpjFormatado = formataCnpj(textCnpj)
cnpj.innerHTML = cnpjFormatado


// var listSpanCnpj = document.querySelectorAll(".nomeDivCnpj")
// listSpanCnpj.forEach(spanCnpj => {
//     var textSpanCnpj = spanCnpj.textContent.split(' - ')
//     var spanCnpjFormatado = formataCnpj(textSpanCnpj[1])
//     spanCnpj.innerHTML = textSpanCnpj[0] + ' - ' + spanCnpjFormatado
// });

var listAltCnpj = document.querySelectorAll(".nomeAlt")
listAltCnpj.forEach(AltCnpj => {
    var textAltCnpj = AltCnpj.textContent.split(' - ')
    var altCnpjFormatado = formataCnpj(textAltCnpj[1])
    AltCnpj.innerHTML = textAltCnpj[0] + ' - ' + altCnpjFormatado + ' - ' + TRADUZ_MES[textAltCnpj[2]]
});
