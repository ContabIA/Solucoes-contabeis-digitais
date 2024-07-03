var cnpj  = document.getElementById("user")
var textCnpj = cnpj.textContent.split('')
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

cnpj.innerHTML = cnpjFormatado

