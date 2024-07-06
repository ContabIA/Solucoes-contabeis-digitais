function formataCnpj(textCnpj){
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

    return cnpjFormatado
}

var cnpj  = document.getElementById("user")
var textCnpj = cnpj.textContent.split('')
var cnpjFormatado = formataCnpj(textCnpj)
cnpj.innerHTML = cnpjFormatado

var listSpanCnpj = document.querySelectorAll(".nomeDivCnpj")
listSpanCnpj.forEach(spanCnpj => {
    var textSpanCnpj = spanCnpj.textContent.split(' - ')
    var spanCnpjFormatado = formataCnpj(textSpanCnpj[1])
    spanCnpj.innerHTML = textSpanCnpj[0] + ' - ' + spanCnpjFormatado
});
