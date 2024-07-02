const fs = require('fs');
const captcha = require('@2captcha/captcha-solver');
const solver = new captcha.Solver("31693569b91ed643587f2531785ae020");

var getInput = async function(){
    // função para pegar os cnpj's em input_CNPJ.txt

    let arqCnpj = fs.readFileSync("input_autoCndt.txt", "utf8").split("\n");
    return arqCnpj;
}

var writeOutput = async function(output){
    // função para escrever os resultados em output_autoCndt
    
    fs.writeFileSync("output_autoCndt.txt", output);
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
    getInput
};