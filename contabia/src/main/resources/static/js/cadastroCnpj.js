var count = 0;

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

    let freqSefaz = document.getElementById("frequenciaSefaz");
    let freqCndt = document.getElementById("frequenciaCndt");
    let checkboxSefaz = document.getElementById("checkboxSefaz");
    let checkboxCndt = document.getElementById("checkboxCndt");
    let nome = document.getElementById("nomeCadCnpj");
    let cnpjEmpresaInp = document.getElementById("cnpjCadCnpj");

    //corpo da requisição
    let body = {
        cnpjEmpresa : cnpjEmpresaInp.value,
        nome : nome.value,
        checkboxSefaz : checkboxSefaz.checked,
        checkboxCndt : checkboxCndt.checked,
        frequenciaSefaz : freqSefaz.value,
        frequenciaCndt : freqCndt.value
    }

    //requisição para cadastrar novo CNPJ
    fetch('http://localhost:8080/cadastroCnpj', {
        method:"POST",
        body:JSON.stringify(body),
        headers:{'Content-Type': 'application/json'}
    })
    .then((resposta)=>{
        if (resposta.status == 200){
            window.location = "/listaCnpj"; //se der certo, redireciona para a lista de empresas cadastradas
        } else {
            return resposta.json() ;
        }
    })
    .then((respJson) =>{ //se der errado, a mensagem de erro é exibida
        document.getElementById("erroText").innerHTML = respJson.resp;
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