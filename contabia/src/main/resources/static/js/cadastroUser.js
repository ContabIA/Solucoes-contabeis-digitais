var login = document.getElementById("login");
var erro = document.getElementById("erro");

//quando o usuário clicar na tela, a mensagem de erro some
login.addEventListener("click", ()=>{
    erro.style.display = "none";
});

function cadUser(){

    //corpo da requisição
    let body = {
        cnpj : document.getElementById("cnpjCad").value,
        email : document.getElementById("emailCad").value,
        senha : document.getElementById("senhaCad").value,
        senhaSefaz : document.getElementById("senhaSefazCad").value,
        userSefaz: document.getElementById("sefazCad").value,
    };

    //requisição para cadastrar novo usuário
    fetch("http://localhost:8080/cadastro", {
        method : "POST",
        body : JSON.stringify(body),
        headers : {'Content-Type': 'application/json'}
    })
    .then((resposta)=>{
        if (resposta.status == 200){
            window.location = "/"; //se der certo, redireciona para a tela de login
        } else {
            alert(resposta);
            return resposta.json();
        }
    })
    .then((respJson) =>{ //se der errado, a mensagem de erro é exibida
        document.getElementById("erroText").innerHTML = respJson.resp;
        document.getElementById("erro").style.display  = "block";  
    });
}