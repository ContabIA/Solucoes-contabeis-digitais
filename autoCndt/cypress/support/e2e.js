const fs = require('fs');
const captcha = require('@2captcha/captcha-solver');
const solver = new captcha.Solver("31693569b91ed643587f2531785ae020");
const path = require('path')



// função para escrever os resultados em output_autoCndt
var writeOutput = async function(cnpj, status){
    /*
    output = {
        "status" : int[0-15],
        "cnpj" : str
    }
    */

    // escreve os resultados em um txt

    let resp = cnpj + ":" + status;

    fs.writeFileSync(
        "autoCndt_output.txt",
        fs.readFileSync("autoCndt_output.txt") + resp
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
    writeOutput
};