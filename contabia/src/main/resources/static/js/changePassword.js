var erroDiv = document.getElementById("erro");
const form = document.getElementById("form");

if(form != null){
    form.addEventListener("submit", (event)=>{
        event.preventDefault();
        
        if(equalsPasswords()){
            request();
        }else{
            erroDiv.style.display = "block";
            erroDiv.style.animationName = "fadeMessage";
        }
    })
}

function equalsPasswords(){
    if(document.getElementById("password").value == document.getElementById("confirmPassword").value){
        return true;
    }else{
        return false;
    }
}

function request(){
    let body = {
        token:location.search.replace("?token=", ""),
        password:document.getElementById("password").value,
        confirmPassword:document.getElementById("confirmPassword").value
    }

    fetch("http://localhost:8080/redefinirSenha/novaSenha", {
        method:"POST",
        body:JSON.stringify(body),
        headers:{'Content-Type': 'application/json'},
    })
    .then((resposta)=>{
        if(resposta.ok){
            sessionStorage.setItem("changePassSucess", true)
            window.location = "/login";
        }
    })
    .catch((erro)=>{
        console.log(erro);
    });
}