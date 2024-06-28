
describe('Coletando notas Sefaz', () => {
  it('acessar sefaz', () => {

    cy.coleta_notas()
    cy.cria_arquivo_json()
    
  })
  });
