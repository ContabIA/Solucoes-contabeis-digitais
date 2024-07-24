var login = document.getElementById("login");
var erro = document.getElementById("erro");

//quando o usuário clicar na tela, a mensagem de erro some
login.addEventListener("click", ()=>{
    erro.style.display = "none";
});

function loginUser(){

    //corpo da requisição
    let body = {
        cnpj : document.getElementById("userLogin").value,
        senha : document.getElementById("senhaLogin").value,
    };

    //requisição para fazer login
    fetch("http://localhost:8080/", {
        method : "POST",
        body : JSON.stringify(body),
        headers : {'Content-Type': 'application/json'}
    })
    .then((resposta)=>{
        if (resposta.status == 200){
            window.location = "/home?cnpjUser="+body.cnpj; //se der certo, redireciona para a página principal
        } else {
            return resposta.json() ;
        }
    })
    .then((respJson) =>{ //se der errado, exibe mensagem de erro
        document.getElementById("erroText").innerHTML = respJson.resp;
        document.getElementById("erro").style.display  = "block";
    });
}