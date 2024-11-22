describe("Teste do negativado trabalhista", ()=>{

    before("coleta de src", ()=>{
        cy.visit("https://cndt-certidao.tst.jus.br/gerarCertidao.faces");
        cy.wait(50000);
        cy.get("#idImgBase64").invoke('attr', 'src').then(($url)=>{
            cy.writeFile('imgUrl.txt', $url);
        });
    });

    it("download", ()=>{
        cy.task('solveCap').then((resp)=>{
            cy.get('[name="resposta"]').type(resp);
        })
        cy.get('[name="gerarCertidaoForm:cpfCnpj"]').type(Cypress.env("cnpj"));
        cy.get('[name="gerarCertidaoForm:btnEmitirCertidao"]').click();
        cy.get('#divSucesso').should('have.css', 'display', 'block').then(($element) => {
            if ($element.css('display') === 'block') {
                //função de retorno de resposta positiva
                cy.task("pdfStatus", Cypress.env("cnpj")).then((status) => {
                    cy.task('writeOut', {"status" : status, "cnpj" : Cypress.env("cnpj")})
                })
                
            }
        });
        cy.get('#divErro').should('have.css', 'display', 'none').then(($element)=>{
            if($element.css('display') === 'block'){
                //função de retorno de resposta negativa
                cy.task('writeOut', {"status" : 0, "cnpj" : Cypress.env("cnpj")})
            }
        });
    });
});
//adicionar suporte a variáveis e wait em função do download do arquivo
