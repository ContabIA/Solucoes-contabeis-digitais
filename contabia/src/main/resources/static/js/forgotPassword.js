var erro = document.getElementById("erro");
var sentEmailSucess = document.getElementById("sentEmailSucess");
var sentEmail = document.getElementById("sentEmail");
const form = document.getElementById("form");



form.addEventListener("submit", (event)=>{
    event.preventDefault();

    sentEmail.style.display = "block";
    
    request();
})

document.getElementById("forgot_password").addEventListener('click', ()=>{
    erro.style.display  = "none";
    erro.style.animationName = "none";
    sentEmail.style.display = "none";
    sentEmail.style.animationName = "none";
});

document.addEventListener("DOMContentLoaded", ()=>{
    if(sessionStorage.getItem("sentEmailSucess")){
        sentEmail.style.display = "none"
        sentEmailSucess.style.display = "block";
        sentEmailSucess.style.animationName = "fadeMessage";
        sessionStorage.removeItem("sentEmailSucess");
    }
});

function request(){
    let body = {
        userEmail:document.getElementById("userEmail").value,
    }

    fetch("http://localhost:8080/redefinirSenha", {
        method:"POST",
        body:JSON.stringify(body),
        headers:{'Content-Type': 'application/json'},
    })
    .then((resposta)=>{
        if(resposta.ok){
            sessionStorage.setItem("sentEmailSucess", true)
            window.location = "/redefinirSenha";
        }
        else{
            return resposta.json();
        }
    })
    .then((respJson)=>{
        document.getElementById("erroText").innerHTML = respJson.resp? respJson.resp : "Erro ao enviar e-mail!";
        sentEmail.style.display = "none";
        erro.style.display  = "block";
        erro.style.animationName = "fadeMessage";
    })
}