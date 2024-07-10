import { formataCnpj, formataData, TRADUZ_MES } from "./main.js";

// Coleta a data da tabela html e chama a função formataData
var tdData = document.getElementById("data")
if (tdData != null){
    var data = tdData.textContent
    tdData.innerHTML = formataData(data)
}

// Coleta os titulos secundarios da página e chama a função formataCnpj e traduz o mês
var listaTitulos = document.querySelectorAll(".title2")
var textoTituloCnpj = listaTitulos[0].textContent.split(" - ")
var cnpjFormatado = formataCnpj(textoTituloCnpj[1])
listaTitulos[0].innerHTML = textoTituloCnpj[0] + " - " + cnpjFormatado
var mesFormatado = TRADUZ_MES[listaTitulos[1].textContent.split(" - ")[1]]
listaTitulos[1].innerHTML = listaTitulos[1].textContent.split(" - ")[0] + " - " + mesFormatado

// Função que manda requisição para fazer deleção lógica do alteração já vista para MVC
window.removerNovo = function(idAlt, tipoAlt, cnpjUser){

    fetch("http://localhost:8080/home?idAlt="+idAlt+"&tipoAlt="+tipoAlt+"&cnpjUser="+cnpjUser, {
        method : "PUT"
    })
    .then((resp) => {return resp.json()})
    .then((respJson) => {

        // Requisição para carregar página de listagem de empresas relacionadas usuario logado
        window.location = "http://localhost:8080/home?cnpjUser="+respJson;
    });

};


