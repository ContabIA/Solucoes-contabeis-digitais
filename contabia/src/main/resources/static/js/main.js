function formataCnpj(textCnpj){
    var cnpjFormatado = ''
    
    for(var i = 0; i < textCnpj.length; i++){

        if(i == 2 || i == 5){
            cnpjFormatado += '.';
        }else if(i == 8){
            cnpjFormatado += '/';
        }else if(i == 12){
            cnpjFormatado += '-';
        }
        cnpjFormatado += textCnpj[i]
        
    }

    return cnpjFormatado
}

function formataData(data){
    var dataFormatada = ''

    listaData = data.split('-')
    dataFormatada = listaData[2] + "/" + listaData[1] + "/" + listaData[0]

    return dataFormatada
}

const TRADUZ_MES = {"JANUARY" : "Janeiro", "FEBRUARY" : "Fevereiro", "MARCH" : "Março", "APRIL" : "Abril", "MAY" : "Maio", "JUNE" : "Junho", "JULY" : "Julho", "AUGUST" : "Agosto", "SEPTEMBER" : "Setembro", "OCTOBER" : "Outubro", "NOVEMBER" : "Novembro", "DECEMBER" : "Dezembro"}

var cnpj  = document.getElementById("user")
var textCnpj = cnpj.textContent.split('')
var cnpjFormatado = formataCnpj(textCnpj)
cnpj.innerHTML = cnpjFormatado

var listSpanCnpj = document.querySelectorAll(".nomeDivCnpj")
listSpanCnpj.forEach(spanCnpj => {
    var textSpanCnpj = spanCnpj.textContent.split(' - ')
    var spanCnpjFormatado = formataCnpj(textSpanCnpj[1])
    spanCnpj.innerHTML = textSpanCnpj[0] + ' - ' + spanCnpjFormatado
});

var listAltCnpj = document.querySelectorAll(".nomeAlt")
listAltCnpj.forEach(AltCnpj => {
    var textAltCnpj = AltCnpj.textContent.split(' - ')
    var altCnpjFormatado = formataCnpj(textAltCnpj[1])
    AltCnpj.innerHTML = textAltCnpj[0] + ' - ' + altCnpjFormatado + ' - ' + TRADUZ_MES[textAltCnpj[2]]
});

var tdData = document.getElementById("data")
var data = tdData.textContent
dataFormatada = formataData(data)
tdData.innerHTML = dataFormatada

var listaTitulos = document.querySelectorAll(".title2")
var textoTituloCnpj = listaTitulos[0].textContent.split(" - ")
var cnpjFormatado = formataCnpj(textoTituloCnpj[1])
listaTitulos[0].innerHTML = textoTituloCnpj[0] + " - " + cnpjFormatado
var mesFormatado = TRADUZ_MES[listaTitulos[1].textContent.split(" - ")[1]]
listaTitulos[1].innerHTML = listaTitulos[1].textContent.split(" - ")[0] + " - " + mesFormatado
