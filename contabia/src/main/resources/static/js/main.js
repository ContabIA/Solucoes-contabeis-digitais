const formataCnpj = function (textCnpj){    
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

const formataData = function(data){
    var dataFormatada = ''

    dataFormatada = data.split('-')[2] + "/" + data.split('-')[1] + "/" + data.split('-')[0]
    
    return dataFormatada
}

// Dicionário para tradução dos meses que são inseridos em inglês
const TRADUZ_MES = {"JANUARY" : "Janeiro", "FEBRUARY" : "Fevereiro", "MARCH" : "Março", "APRIL" : "Abril", "MAY" : "Maio", "JUNE" : "Junho", "JULY" : "Julho", "AUGUST" : "Agosto", "SEPTEMBER" : "Setembro", "OCTOBER" : "Outubro", "NOVEMBER" : "Novembro", "DECEMBER" : "Dezembro"}

const formataCnpjUser = function(){
    var cnpj  = document.getElementById("user")
    var textCnpj = cnpj.textContent.split('')
    var cnpjFormatado = formataCnpj(textCnpj)
    cnpj.innerHTML = cnpjFormatado  
}

formataCnpjUser()

export {formataCnpj, formataData, TRADUZ_MES}