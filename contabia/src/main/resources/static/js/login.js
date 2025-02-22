var login = document.getElementById("login");
var erro = document.getElementById("erro");
var changePassSucessDiv = document.getElementById("changePassSucessDiv");


function CnpjMask(input) {
    let cnpj = input.value.replace(/\D/g, '');
    cnpj = cnpj.replace(/^(\d{2})(\d)/, '$1.$2');
    cnpj = cnpj.replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3');
    cnpj = cnpj.replace(/\.(\d{3})(\d)/, '.$1/$2');
    cnpj = cnpj.replace(/(\d{4})(\d)/, '$1-$2');
    input.value = cnpj.substring(0, 18);
}
//quando o usuário clicar na tela, a mensagem de erro some
login.addEventListener("click", ()=>{
    erro.style.display = "none";
});

document.addEventListener("DOMContentLoaded", ()=>{
    if(sessionStorage.getItem("changePassSucess")){
        changePassSucessDiv.style.display = "block";
        changePassSucessDiv.style.animationName = "fadeMessage";
        sessionStorage.removeItem("changePassSucess");
    }
});

document.getElementById("form").addEventListener('submit', (event)=>{
    event.preventDefault();
    loginUser();
});

function loginUser(){

    //corpo da requisição
    let body = {
        cnpj : document.getElementById("userLogin").value.replace(/\D/g, ''),
        senha : document.getElementById("senhaLogin").value,
    };

    //requisição para fazer login
    fetch("http://localhost:8080/login", {
        method : "POST",
        body : JSON.stringify(body),
        headers : {'Content-Type': 'application/json'}
    })
    .then((resposta)=>{
        if (resposta.ok){
            window.location = "/home"; //se der certo, redireciona para a página principal
        } else {
            return resposta.json();
        }
    })
    .then((respJson) =>{ //se der errado, exibe mensagem de erro
        document.getElementById("erroText").innerHTML = respJson.resp? respJson.resp : "Erro a fazer login";
        document.getElementById("erro").style.display  = "block";
    });
}