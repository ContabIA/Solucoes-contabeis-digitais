describe("Teste do negativado trabalhista", ()=>{

    before("coleta de src", ()=>{
        cy.visit("https://cndt-certidao.tst.jus.br/gerarCertidao.faces");
        cy.wait(2000);
        cy.get("#idImgBase64").invoke('attr', 'src').then(($url)=>{
            cy.writeFile('imgUrl.txt', $url);
        });
    });

    it("download", ()=>{
        cy.task('solveCap').then((resp)=>{
            cy.get('[name="resposta"]').type(resp);
        })
        cy.get('[name="gerarCertidaoForm:cpfCnpj"]').type(Cypress.env('cnpj'));
        cy.get('[name="gerarCertidaoForm:btnEmitirCertidao"]').click();
        cy.task('writeOutput',"cnpj:"+Cypress.env('cnpj')+"\nsucesso!!!")
    });
});


//adicionar suporte a variáveis e wait em função do download do arquivo
