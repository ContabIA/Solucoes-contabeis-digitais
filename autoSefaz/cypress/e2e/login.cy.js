
describe('Coletando notas Sefaz', () => {
  it('acessar sefaz', () => {
    
    cy.loginSefaz();
    
    cy.writeFile("notas.json", '{\n"runs":[')
    for (let i = 0; i < Cypress.env("cnpjs").length; i ++){
      cy.buscandoCnpj(i)
      cy.wait(5000)
      cy.cria_arquivo_json(i)
      cy.ultimo_dado(i)
      if (i == Cypress.env("cnpjs").length - 1){
        cy.writeFile("notas.json","\n", {flag: "a+"})
      } else {
        cy.writeFile("notas.json",",\n", {flag: "a+"})
      }
      
    }
    cy.writeFile("notas.json", "\n]\n}", {flag: "a+"});
    
  })
});
