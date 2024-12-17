const fs = require('fs');
const captcha = require('@2captcha/captcha-solver');
const solver = new captcha.Solver("31693569b91ed643587f2531785ae020");
const path = require('path');
const { rejects } = require('assert');



// função que espera uma função retornar verdadeiro
async function awaitTrue(asertTrueFn, ifTrueFn, ifTimeOutfn, repeat = 60, timeInterval = 100, log = false, fnName = "function"){
    let contador = 1;

    let interval = setInterval(() => {

        if (log){
            console.log( ( contador == 1 ) ? ("awaiting " + fnName + ":") : ("\t- " + contador + "/" + repeat + ")")) // -> "awaiting <fnName>:" : "\t- (<contador>/<repeat>)"
        }

        if (!asertTrueFn()) { contador ++} // a cada repetição que falha, aumenta o contador
        else {
            ifTrueFn(); // se der certo usa a função
            clearInterval(interval) // fecha o intervalo 
        }

        if ( contador >= repeat){ // se o contador passar do numero de repetições
            ifTimeOutfn()   // chama a função de timeout
            clearInterval(interval) // fecha o intervalo
        }

    }, timeInterval);

    return interval; // retorna o intervalo 
};

// função para esperar o download do pdf
async function awaitPdf(pdfName){
    
    return new Promise((resolve, reject) => {

        let dirPath = path.join(__dirname, "../downloads"); // caminho para o diretorio downloads

        console.log(dirPath)
        console.log(fs.readdirSync(dirPath))
        console.log(pdfName)
        console.log(fs.readdirSync(dirPath).includes(pdfName))
        awaitTrue( // espera que o diretorio exista
            () => {return (fs.existsSync(dirPath))}, // função que tem que ser verdadeira
            () => { // quando for verdadeira 
                awaitTrue(  // espera que o pdf exista
                    () => {return (fs.readdirSync(dirPath).includes(pdfName))}, // função que tem que ser verdadeira
                    () => {resolve(pdfName)}, // quando for verdadeiro resolve a promessa
                    () => {reject("timeout while awaiting for '"+pdfName+"'")}, // quando o tempo acabar rejeita a promessa
                    undefined,  // default repeat
                    1000, // defaut timeInterval
                    true, // log = true
                    "awaitPdf" // nome da "função"
                )
            },
            () => {reject("timeout while awaiting for '"+dirPath+"' to apear")}, // quando o tempo acabar rejeita a promessa
            undefined,  // default repeat
            undefined, // default timeInterval
            true, // log = true
            "awaitDir" // nome da "função"
        );
    });
};

async function strContains(string, containsVal){ // vê se uma string contem uma substring, retorna true ou false

    if (containsVal.length === 0) return(true); // se o tamanho da substring for 0 retorna true

    for (let i = 0; i < string.length; i++){ // varrendo a string

        if (string[i] === containsVal[0] && string.substring(i, i + containsVal.length) === containsVal){ // se string[i] === substring[0] então vê se a substring aparece nessa posição
            return(true); 
        };
    };

    return false; // se não encontrar retorna false
};

function trimSpaces(string){
    for (let i = 0; i < string.length-1; i++){
        while (string[i] === " " && string[i+1] === " "){
            string = string.substring(0, i) + string.substring(i+1);
        };
    };

    return string;
}

// função para verificar o texto do pdf
function verifyPdfText(text, cnpj){ // -> 0 : 1

    text = trimSpaces(text.replace(/(\r\n|\n|\r)/gm, " ")); // tira as quebras de linha e transforma todos os espaços duplos em espaços simples

    let fCnpj = `${cnpj.substring(0,2)}.${cnpj.substring(2,5)}.${cnpj.substring(5,8)}/${cnpj.substring(8,12)}-${cnpj.substring(12,14)}`; // transforma o numero do cnpj em uma string com a formação do cnpj(XX.XXX.XXX/XXXX-XX)

    const toFind = `, inscrito(a) no CNPJ sob o nº ${fCnpj}, NÃO CONSTA como inadimplente no Banco Nacional de Devedores Trabalhistas.`; // string que tem que ser encontrada no texto


    if (strContains(text, toFind)) {
        return 1;
    } else {
        return 0
    }
}

// função para verificar o pdf após o download
async function verifyPdf(cnpj){

    cnpj = cnpj.toString();
    console.log(cnpj);
    cnpj = (cnpj.length === 14) ? cnpj : cnpj.slice(1,cnpj.length -1);

    let pdfName = "certidao_"+cnpj+".pdf";

    return new Promise((resolve, reject) => {

        // espera o pdf aparecer 
        awaitPdf(pdfName).then((pdfName) => {
            let pdfParse = require("pdf-parse")

            //path até o pdf
            let pdfPath = path.join(__dirname, "../downloads", pdfName);

            // lê o buffer do pdf
            let pdfBuffer = fs.readFileSync(pdfPath);

            // lê o pdf e retorna o resultado da verificação
            pdfParse(pdfBuffer).then((pdf) => {
                resolve(verifyPdfText(pdf.text, cnpj));
            });
        })
    })
    
}

// função para escrever os resultados em output_autoCndt
async function writeOutput(Object){
    /*
    output = {
        "status" : int[0-15],
        "cnpj" : str
    }
    */

    // escreve os resultados em um txt

    let resp = Object['cnpj'] + ":" + Object['status'];

    fs.writeFileSync(
        "autoCndt_output.txt",
        fs.readFileSync("autoCndt_output.txt") + "\n" + resp 
    );
    return null;
}

async function getUrl(){
    try{
        var urlB = fs.readFileSync('imgUrl.txt', 'utf8');
        var urlImage = Buffer.from(urlB).toString();
        return urlImage;
    } catch (err){
        console.log(err);
    }
}

var solveCaptcha = async function (){
    var url = await getUrl();
    return new Promise((resolve, reject) =>{
        solver.imageCaptcha({
            body:url,
            numeric:4,
            phrase:0
        })
        .then((res)=>{
            resolve(res['data']);
        })
        .catch((err)=>{
            reject(err);
        });
    });
}

module.exports = {
    solveCaptcha,
    writeOutput,
    verifyPdf,
};