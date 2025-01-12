async function detalhesEncaps(cnpj, key) {

    let mes = key.split('-')[0]
    let cnpjEmpresa = key.split('-')[1]
    await fetch("http://localhost:8080/detalhes/sefaz?cnpjUser="+cnpj+"&cnpjEmpresa="+cnpjEmpresa+"&mes="+mes+"&idAlt=0", {method:"GET"})
    .then(resposta =>{
        if (resposta.status = 200){
            window.location.href = resposta.url;
        }
    })
}