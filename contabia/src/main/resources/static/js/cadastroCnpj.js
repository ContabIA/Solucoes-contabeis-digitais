var count = 0;

function CnpjMask(input) {
    let cnpj = input.value.replace(/\D/g, '');
    cnpj = cnpj.replace(/^(\d{2})(\d)/, '$1.$2');
    cnpj = cnpj.replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3');
    cnpj = cnpj.replace(/\.(\d{3})(\d)/, '.$1/$2');
    cnpj = cnpj.replace(/(\d{4})(\d)/, '$1-$2');
    input.value = cnpj.substring(0, 18);
}

//sistema para fazer o menu de edição de usuário aparecer e sumir
document.getElementById("caixa-user").addEventListener('click', ()=>{
    if(count == 0){
        document.getElementById("lista-config").style.display = 'flex';
        count = 1;
    }
    else if(count == 1){
        document.getElementById("lista-config").style.display = 'none';
        count = 0;
    }
});

var cad = document.getElementById("formCadCnpj");
var erro = document.getElementById("erroText");

//quando o usuário clicar na tela, a mensagem de erro some
cad.addEventListener("click", ()=>{
    erro.style.display = "none";
});

function cadastroCnpj(){

    let body = {
        cnpjEmpresa : document.getElementById("cnpjCadCnpj").value.replace(/\D/g, ''),
        nome : document.getElementById("nomeCadCnpj").value,
        checkboxSefaz : document.getElementById("checkboxSefaz").checked,
        checkboxCndt : document.getElementById("checkboxCndt").checked,
        frequenciaSefaz : document.getElementById("frequenciaSefaz").value,
        frequenciaCndt : document.getElementById("frequenciaCndt").value
    }

    fetch('http://localhost:8080/cadastroCnpj', {
        method:"POST",
        body:JSON.stringify(body),
        headers:{'Content-Type': 'application/json'}
    })
    .then((resposta)=>{
        if (resposta.ok){
            window.location = "/listaCnpj";
        } else {
            return resposta.json() ;
        }
    })
    .then((respJson) =>{ //se der errado, a mensagem de erro é exibida

        console.log(respJson)

        document.getElementById("erroText").innerHTML = respJson.resp ? respJson.resp : "Erro ao cadastrar CNPJ";
        document.getElementById("erroText").style.display  = "block";  
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