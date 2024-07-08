import formataData from "./main";

const TRADUZ_MES = {"JANUARY" : "Janeiro", "FEBRUARY" : "Fevereiro", "MARCH" : "Março", "APRIL" : "Abril", "MAY" : "Maio", "JUNE" : "Junho", "JULY" : "Julho", "AUGUST" : "Agosto", "SEPTEMBER" : "Setembro", "OCTOBER" : "Outubro", "NOVEMBER" : "Novembro", "DECEMBER" : "Dezembro"}

var tdData = document.getElementById("data")
var data = tdData.textContent
dataFormatada = formataData(data)
tdData.innerHTML = dataFormatada

var listaTitulos = document.querySelectorAll(".title2")
var textoTituloCnpj = listaTitulos[0].textContent().split(" - ")
var cnpjFormatado = formataCnpj(textoTituloCnpj)
listaTitulos[0].innerHTML(textoTituloCnpj[0] + " - " + cnpjFormatado)

alert('oi')
