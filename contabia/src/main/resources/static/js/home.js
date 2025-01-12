import { formataCnpj,  TRADUZ_MES } from "./main.js";


// Coleta todas as alterações que estão na tela e formata o cnpj e traduz o mês
var listAltCnpj = document.querySelectorAll(".nomeAlt")
listAltCnpj.forEach(AltCnpj => {
    var textAltCnpj = AltCnpj.textContent.split(' - ')
    var altCnpjFormatado = formataCnpj(textAltCnpj[1])
    var tipoAlt = textAltCnpj[0].split(' ')[0] + ' ' + textAltCnpj[0].split(' ')[1]
    var mes = textAltCnpj[0].split(' ')[2]
    AltCnpj.innerHTML = tipoAlt + ' ' + TRADUZ_MES[mes] + ' - ' + altCnpjFormatado
});

var listAltEncaps = document.querySelectorAll(".altEncaps")
listAltEncaps.forEach(AltEncaps => {
    var textAltEncaps = AltEncaps.textContent.split(' ')
    var mes = textAltEncaps[1].split('-')[0]
    var cnpj = formataCnpj(textAltEncaps[1].split('-')[1])
    AltEncaps.innerHTML =textAltEncaps[0] + ' ' + TRADUZ_MES[mes] + ' - ' + cnpj
})

