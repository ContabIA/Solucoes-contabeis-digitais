// Função que manda requisição para deletar a empresa que tem determinado cnpjEmpresa para o MVC
async function delEmpresa(cnpjEmpresa, cnpj){
    await fetch("http://localhost:8080/listaCnpj?cnpjUser="+cnpj+"&cnpjEmpresa="+cnpjEmpresa, {method:"DELETE"})
    location.reload();
}

