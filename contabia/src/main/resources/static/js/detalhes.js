import { formataCnpj, formataData, TRADUZ_MES } from "./main.js";

// Coleta a data da tabela html e chama a função formataData
var tdsData = document.querySelectorAll(".data")
tdsData.forEach(tdData => {
    if (tdData != null){
        var data = tdData.textContent
        tdData.innerHTML = formataData(data)
    }
})



// Coleta os titulos secundarios da página e chama a função formataCnpj e traduz o mês
var listaTitulos = document.querySelectorAll(".title2")
var textoTituloCnpj = listaTitulos[0].textContent.split(" - ")
var cnpjFormatado = formataCnpj(textoTituloCnpj[1])
listaTitulos[0].innerHTML = textoTituloCnpj[0] + " - " + cnpjFormatado
var mesFormatado = TRADUZ_MES[listaTitulos[1].textContent.split(" - ")[1]]
listaTitulos[1].innerHTML = listaTitulos[1].textContent.split(" - ")[0] + " - " + mesFormatado

// Função que manda requisição para fazer deleção lógica do alteração já vista para MVC
window.removerNovo = function(tipoAlt, cnpjUser){

    if (tipoAlt == 'Notas'){
        tipoAlt = 'Alteração Sefaz'
    }

    var ids = document.querySelectorAll(".id")
    var listaResp = new Array

    ids.forEach(id=>{
        id = (Number(id.textContent))
        listaResp.push(id)
    })

    var body = {
        listaIds : listaResp
    }

    fetch("http://localhost:8080/home?tipoAlt="+tipoAlt+"&cnpjUser="+cnpjUser, {
        method : "PUT",
        body : JSON.stringify(body),
        headers : {'Content-Type': 'application/json'}
    })
    .then((resp) => {return resp.json()})
    .then((respJson) => {

        // Requisição para carregar página de listagem de empresas relacionadas usuario logado
        window.location = "http://localhost:8080/home?cnpjUser="+respJson;
    });

};


