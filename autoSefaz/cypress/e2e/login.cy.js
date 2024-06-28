
describe('Coletando notas Sefaz', () => {
  it('acessar sefaz', () => {

    var tabela_notas
    var tabela_resumo
    tabela_notas, tabela_resumo = cy.coleta_notas()
    cy.cria_arquivo_svc(tabela_notas, tabela_resumo)
    
  })
  });
