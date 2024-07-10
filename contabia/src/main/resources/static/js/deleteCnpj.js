async function delEmpresa(cnpjEmpresa, cnpjUser){
    await fetch("http://localhost:8080/listaCnpj?cnpj="+cnpjUser+"&cnpjEmpresa="+cnpjEmpresa, {method:"DELETE"});
    location.reload();
}

