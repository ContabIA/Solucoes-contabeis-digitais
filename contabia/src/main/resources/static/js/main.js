var count = 0;

document.getElementById("caixa-user").addEventListener('click', ()=>{
    if(count == 0){
        document.getElementById("lista-config").style.display = 'flex';
        count = 1;
    }
    else if(count == 1){
        document.getElementById("lista-config").style.display = 'none';
        count = 0;
    }
});

// Função que formata o cnpj de XXXXXXXXXXXXXX para XX.XXX.XXX/XXXX-XX
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

// Função que formata a data do forato YYYY-MM-DD para DD/MM/YYYY
const formataData = function(data){
    var dataFormatada = ''

    dataFormatada = data.split('-')[2] + "/" + data.split('-')[1] + "/" + data.split('-')[0]
    
    return dataFormatada
}

// Dicionário para tradução dos meses que são inseridos em inglês
const TRADUZ_MES = {"JANUARY" : "Janeiro", "FEBRUARY" : "Fevereiro", "MARCH" : "Março", "APRIL" : "Abril", "MAY" : "Maio", "JUNE" : "Junho", "JULY" : "Julho", "AUGUST" : "Agosto", "SEPTEMBER" : "Setembro", "OCTOBER" : "Outubro", "NOVEMBER" : "Novembro", "DECEMBER" : "Dezembro"}


// Coleta o cnpj do usuário que fica no header e chama a função formataCnpj
var cnpj  = document.getElementById("user")
var textCnpj = cnpj.textContent.split('')
var cnpjFormatado = formataCnpj(textCnpj)
cnpj.innerHTML = cnpjFormatado  

export {formataCnpj, formataData, TRADUZ_MES}