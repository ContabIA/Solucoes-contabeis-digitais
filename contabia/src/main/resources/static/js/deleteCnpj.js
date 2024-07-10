async function delEmpresa(cnpjEmpresa, cnpj){
    await fetch("http://localhost:8080/listaCnpj?cnpjUser="+cnpj+"&cnpjEmpresa="+cnpjEmpresa, {method:"DELETE"})
    location.reload();
}

