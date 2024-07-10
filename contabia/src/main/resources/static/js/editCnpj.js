//var form = document.getElementById("formEditCnpj");

async function sla(cnpjUser, cnpjEmpresa){

    var freqSefaz = document.getElementById("frequenciaSefaz");
    var freqCndt = document.getElementById("frequenciaCndt");
    var checkboxSefaz = document.getElementById("checkboxSefaz");
    var checkboxCndt = document.getElementById("checkboxCndt");
    var nome = document.getElementById("nomeCadCnpj");
    var cnpjEmpresaInp = document.getElementById("cnpjCadCnpj");

    let body = {
        cnpjEmpresa : cnpjEmpresaInp.value,
        nome : nome.value,
        checkboxSefaz : checkboxSefaz.checked,
        checkboxCndt : checkboxCndt.checked,
        frequenciaSefaz : freqSefaz.value,
        frequenciaCndt : freqCndt.value
    }

    await fetch('http://localhost:8080/editCnpj?cnpjUser='+cnpjUser+'&cnpjEmpresa='+cnpjEmpresa, {
        method:"PUT",
        body:JSON.stringify(body),
        headers:{'Content-Type': 'application/json'},
    })
    .then((resp)=>{return resp.json()})
    .then((respJson) => {
        window.location = "/listaCnpj?cnpjUser="+respJson;
    });
}