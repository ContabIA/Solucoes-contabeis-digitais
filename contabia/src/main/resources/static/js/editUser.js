var form = document.getElementById("form");
var erro = document.getElementById("erro");

function CnpjMask(input) {
    let cnpj = input.value.replace(/\D/g, '');
    cnpj = cnpj.replace(/^(\d{2})(\d)/, '$1.$2');
    cnpj = cnpj.replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3');
    cnpj = cnpj.replace(/\.(\d{3})(\d)/, '.$1/$2');
    cnpj = cnpj.replace(/(\d{4})(\d)/, '$1-$2');
    input.value = cnpj.substring(0, 18);
}

form.addEventListener('click', ()=>{
    erro.style.display  = "none";
    erro.style.animationName = "none";
});

document.addEventListener('DOMContentLoaded', ()=>{
    CnpjMask(document.getElementById("cnpjCad"));
});

function editUser(){

    //corpo da requisição
    let body = {
        cnpj : document.getElementById("cnpjCad").value,
        email : document.getElementById("emailCad").value,
        senhaSefaz : document.getElementById("senhaSefazCad").value,
        userSefaz: document.getElementById("sefazCad").value,
    }

    //requisição para atualizar os dados do usuário
    fetch('http://localhost:8080/editUser', {
        method : "PUT",
        body : JSON.stringify(body),
        headers : {'Content-Type': 'application/json'}
    })
    .then((resposta)=>{
        if (resposta.status == 200){ //se der certo, redireciona o usuário novamente para a tela de login
            window.location = "/logout"
        } else {
            return resposta.json() 
        }
    })
    .then((respJson) =>{ //se der errado, a mensagem de erro é exibida
        document.getElementById("erroText").innerHTML = respJson.resp
        erro.style.display  = "block";
        erro.style.animationName = "fadeMessage";
    })
}



