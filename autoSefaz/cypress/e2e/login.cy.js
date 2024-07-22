
describe('Coletando notas Sefaz', () => {
  it('acessar sefaz', () => {
    let dict = Cypress.env("dadosLogin")
    for (let j = 0; j < Object.keys(dict).length; j++){
      let user = Object.keys(dict)[j]
      let senha = dict[user][0]
      
      cy.task("escreverJson", {registro: '{\n"runs":[' , flag: "w"})
      // cy.writeFile("notas.json", '{\n"runs":[')
      
      for (let i = 1; i < dict[user].length; i ++){
        cy.loginSefaz(user, senha);
        const dateObj = new Date();
        let mod = (Cypress.env("diaUm")) ? 0:-1
        const dateInit = new Date(dateObj.getFullYear()+mod, dateObj.getMonth()- (mod+1), 1).toLocaleDateString();
        let dateFim = new Date(dateObj.getFullYear(), dateObj.getMonth(), 1) - new Date(1000*60*60*24)
        dateFim = new Date(dateFim).toLocaleDateString()
        
        cy.buscandoCnpj(user,i, dateInit, dateFim)
        cy.wait(10000)

        cy.task("escreverJson", {registro: '{' + '\n' + '"listaNotas" : [' + '\n', flag:"a+"})
        cy.get('[name=frmConsultar]').then(($form) =>{
          cy.wait(3000)
          if($form.find('table:nth-child(2)').length > 0){
            cy.cria_arquivo_json(user,i)
            cy.ultimo_dado(user,i)
          }
          
        })
        
        cy.task("escreverJson", {registro: "\n] \n}", flag:"a+"})  
        
        if (i == dict[user].length - 1){
          cy.task("escreverJson", {registro: "\n", flag:"a+"})
        } else {
          cy.task("escreverJson", {registro: ",\n", flag:"a+"})
        }
        
      }
      cy.task("escreverJson", {registro: "\n]\n}", flag:"a+"})
    }
    
  })
});
