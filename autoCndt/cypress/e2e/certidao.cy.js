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
        cy.get('[name="gerarCertidaoForm:cpfCnpj"]').type('09197286000111');
        cy.get('[name="gerarCertidaoForm:btnEmitirCertidao"]').click();
        
    });
});


//adicionar suporte a variáveis e wait em função do download do arquivo
