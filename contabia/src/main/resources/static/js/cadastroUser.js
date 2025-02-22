const mainForm = document.getElementById("form");
const erro = document.getElementById("erro");

function CnpjMask(input) {
    let cnpj = input.value.replace(/\D/g, '');
    cnpj = cnpj.replace(/^(\d{2})(\d)/, '$1.$2');
    cnpj = cnpj.replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3');
    cnpj = cnpj.replace(/\.(\d{3})(\d)/, '.$1/$2');
    cnpj = cnpj.replace(/(\d{4})(\d)/, '$1-$2');
    input.value = cnpj.substring(0, 18);
}

mainForm.addEventListener('click', ()=>{
    erro.style.display  = "none";
    erro.style.animationName = "none";
});

mainForm.addEventListener("submit", (event) => {
    event.preventDefault();
    //corpo da requisição
    let body = {
        cnpj : document.getElementById("cnpjCad").value.replace(/\D/g, ''),
        email : document.getElementById("emailCad").value,
        senha : document.getElementById("senhaCad").value,
        senhaSefaz : document.getElementById("senhaSefazCad").value,
        userSefaz: document.getElementById("sefazCad").value,
    };
    // alert(JSON.stringify(body));

    //requisição para cadastrar novo usuário
    fetch("http://localhost:8080/cadastro", {
        method : "POST",
        body : JSON.stringify(body),
        headers : {'Content-Type': 'application/json'}
    })
    .then((resposta)=>{
        if (resposta.ok){
            window.location = "/login"; //se der certo, redireciona para a tela de login
        } else {
            // alert(resposta.resp? resposta.resp : "Erro ao cadastrar usuário");
            return resposta.json();
        }
    })
    .then((respJson) =>{ //se der errado, a mensagem de erro é exibida
        document.getElementById("erroText").innerHTML = respJson.resp? respJson.resp : "Erro ao cadastrar usuário";
        erro.style.display  = "block";
        erro.style.animationName = "fadeMessage";
    });
})