import { formataCnpj } from "./main.js";

var listSpanCnpj = document.querySelectorAll(".nomeDivCnpj")
listSpanCnpj.forEach(spanCnpj => {
    var textSpanCnpj = spanCnpj.textContent.split(' - ')
    var spanCnpjFormatado = formataCnpj(textSpanCnpj[1])
    spanCnpj.innerHTML = textSpanCnpj[0] + ' - ' + spanCnpjFormatado
});