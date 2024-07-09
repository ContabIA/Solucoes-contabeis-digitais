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
        cy.get('[name="gerarCertidaoForm:cpfCnpj"]').type(Cypress.env("cnpj"));
        cy.wait(2000)
        cy.get('[name="gerarCertidaoForm:btnEmitirCertidao"]').click();
        cy.wait(2000)
        cy.task('writeOut',{"cnpj" : Cypress.env("cnpj"), "status" : 3});
        cy.wait(5000)
        cy.get('#divSucesso').should('have.css', 'display', 'block').then(($element) => {
            if ($element.css('display') === 'block') {
                //função de retorno de resposta positiva
                cy.wait(5000)
                cy.log("A")
                cy.task('writeOut', {"status" : 1, "cnpj" : Cypress.env("cnpj")})
                cy.wait(5000)
                cy.log("B")
                
            }
        });
        cy.get('#divErro').should('have.css', 'display', 'none').then(($element)=>{
            if($element.css('display') != 'none'){
                //função de retorno de resposta negativa
                cy.task('writeOut', {"status" : 0, "cnpj" : Cypress.env("cnpj")})
            }
        });
    });
});
//adicionar suporte a variáveis e wait em função do download do arquivo
