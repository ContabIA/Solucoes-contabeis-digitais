async function atualizaDados(cnpjUser, cnpjEmpresa){

    var freqSefaz = document.getElementById("frequenciaSefaz");
    var freqCndt = document.getElementById("frequenciaCndt");
    var checkboxSefaz = document.getElementById("checkboxSefaz");
    var checkboxCndt = document.getElementById("checkboxCndt");
    var nome = document.getElementById("nomeCadCnpj");
    var cnpjEmpresaInp = document.getElementById("cnpjCadCnpj");

    let body = {
        cnpjEmpresa : cnpjEmpresaInp.value,
        nome : nome.value,
        checkboxSefaz : checkboxSefaz.checked,
        checkboxCndt : checkboxCndt.checked,
        frequenciaSefaz : freqSefaz.value,
        frequenciaCndt : freqCndt.value
    }

    await fetch('http://localhost:8080/editCnpj?cnpjUser='+cnpjUser+'&cnpjEmpresa='+cnpjEmpresa, {
        method:"PUT",
        body:JSON.stringify(body),
        headers:{'Content-Type': 'application/json'},
    })
    .then((resp)=>{return resp.json()})
    .then((respJson) => {
        window.location = "/listaCnpj?cnpjUser="+respJson;
    });
}

// Função que formata o cnpj de XXXXXXXXXXXXXX para XX.XXX.XXX/XXXX-XX
const formataCnpj = function (textCnpj){    
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

// Coleta o cnpj do usuário que fica no header e chama a função formataCnpj
var cnpj  = document.getElementById("user")
var textCnpj = cnpj.textContent.split('')
var cnpjFormatado = formataCnpj(textCnpj)
cnpj.innerHTML = cnpjFormatado  


