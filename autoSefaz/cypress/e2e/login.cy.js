
describe('Coletando notas Sefaz', () => {
  it('acessar sefaz', () => {
    let dict = Cypress.env("dadosLogin") // Objeto com dados de Login

    // Loop para cada dado do objeto com usuario e senha sefaz e seus respectivos cnpj's
    for (let j = 0; j < Object.keys(dict).length; j++){
      let user = Object.keys(dict)[j]
      let senha = dict[user][0]
      
      cy.task("escreverJson", {registro: '{\n"runs":[' , flag: "w"}) // Abre resposta no arquivo json
      
      // Loop para cada cnpj relacionado a login Sefaz
      for (let i = 1; i < dict[user].length; i ++){

        cy.loginSefaz(user, senha); // Chama função que loga no sefaz.
        const dataAtual = new Date(); // Variável com data atual.

        // Lógica para modificar intervalo de pesquisa caso seja o dia primeiro do mês.
        let mod = (Cypress.env("diaUm")) ? 0:-1
        const dateInit = new Date(dataAtual.getFullYear()+mod, dataAtual.getMonth()- (mod+1), 1).toLocaleDateString();
        let dateFim = new Date(dataAtual.getFullYear(), dataAtual.getMonth(), 1) - new Date(1000*60*60*24)
        dateFim = new Date(dateFim).toLocaleDateString();
        
        cy.buscandoCnpj(user,i, dateInit, dateFim) // Busca cnpj no Sefaz do usuario.
        cy.wait(10000)
        cy.task("escreverJson", {registro: '{' + '\n' + '"listaNotas" : [' + '\n', flag:"a+"}) // Abre o objeto com a lista de notas para essa consulta.

        // Verifica se o cnpj digitado está cadastrado no Sefaz do usuario
        cy.get('[name=frmConsultar]').then(($form) =>{
          cy.wait(3000)
          if($form.find('table:nth-child(2)').length > 0){
            cy.cria_arquivo_json(user,i) // Insere no arquivo json todas as notas encontradas.
            cy.ultimo_dado(user,i) // Insere o último dado com formatação diferente.
          }
        })
        
        cy.task("escreverJson", {registro: "\n] \n}", flag:"a+"})  // Fecha a lista de notas 
        
        // Verifica se haverá mais alguma lista de notas.
        if (i == dict[user].length - 1){
          cy.task("escreverJson", {registro: "\n", flag:"a+"})
        } else {
          cy.task("escreverJson", {registro: ",\n", flag:"a+"})
        }
        
      }
      cy.task("escreverJson", {registro: "\n]\n}", flag:"a+"}) // Fecha a runs.
    }
    
  })
});
