function cadUser(){

    let body = {
        cnpj : document.getElementById("cnpjCad").value,
        email : document.getElementById("emailCad").value,
        senha : document.getElementById("senhaCad").value,
        senhaSefaz : document.getElementById("senhaSefazCad").value,
        userSefaz: document.getElementById("sefazCad").value,
    }

    fetch("http://localhost:8080/cadastro", {
        method : "POST",
        body : JSON.stringify(body),
        headers : {'Content-Type': 'application/json'}
    })
    .then((resposta)=>{
        if (resposta.status == 200){
            window.location = "/"
        } else {
            return resposta.json() 
        }
    })
    .then((respJson) =>{
        document.getElementById("erroText").innerHTML = respJson.resp
        document.getElementById("erro").style.display  = "block"
        document.getElementById("login").addEventListener("click", ()=>{
            document.getElementById("erro").display = "none"
        })   
    })
}



