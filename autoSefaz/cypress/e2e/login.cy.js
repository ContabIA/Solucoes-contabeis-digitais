
describe('Coletando notas Sefaz', () => {
  it('acessar sefaz', () => {
    
    cy.loginSefaz();
    
    cy.writeFile("notas.json", '{\n"runs":[')
    for (let i = 0; i < Cypress.env("cnpjs").length; i ++){
      cy.buscandoCnpj(i)
      cy.cria_arquivo_json(i)
      cy.ultimo_dado(i)
      cy.writeFile("notas.json",",\n", {flag: "a+"})
    }
    cy.writeFile("notas.json", "\n]\n}", {flag: "a+"});
    
  })
});
