async function editEmpresa(cnpjEmpresa, cnpj){
    var form = document.getElementById("formCadCnpj")
    await fetch("http://localhost:8080/listaCnpj?cnpj="+cnpj+"&cnpjEmpresa="+cnpjEmpresa, {method:"PUT"})
    location.reload();
}