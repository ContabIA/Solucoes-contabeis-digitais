import { formataCnpj } from "./main.js";

// Coleta todos os cnpj na tela e formata os mesmos
var listSpanCnpj = document.querySelectorAll(".nomeDivCnpj")
listSpanCnpj.forEach(spanCnpj => {
    var textSpanCnpj = spanCnpj.textContent.split(' - ')
    var spanCnpjFormatado = formataCnpj(textSpanCnpj[1])
    spanCnpj.innerHTML = textSpanCnpj[0] + ' - ' + spanCnpjFormatado
});