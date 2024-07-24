var login = document.getElementById("login");
var erro = document.getElementById("erro");

//quando o usuário clicar na tela, a mensagem de erro some
login.addEventListener("click", ()=>{
    erro.style.display = "none";
});

function editUser(cnpjUser){

    //corpo da requisição
    let body = {
        cnpj : document.getElementById("cnpjCad").value,
        email : document.getElementById("emailCad").value,
        senha : document.getElementById("senhaCad").value,
        senhaSefaz : document.getElementById("senhaSefazCad").value,
        userSefaz: document.getElementById("sefazCad").value,
    }

    //requisição para atualizar os dados do usuário
    fetch('http://localhost:8080/editUser?cnpjUser='+ cnpjUser, {
        method : "PUT",
        body : JSON.stringify(body),
        headers : {'Content-Type': 'application/json'}
    })
    .then((resposta)=>{
        if (resposta.status == 200){ //se der certo, redireciona o usuário novamente para a tela de login
            window.location = "/"
        } else {
            return resposta.json() 
        }
    })
    .then((respJson) =>{ //se der errado, a mensagem de erro é exibida
        document.getElementById("erroText").innerHTML = respJson.resp
        document.getElementById("erro").style.display  = "block"
    })
}



