
describe('Coletando notas Sefaz', () => {
  it('acessar sefaz', () => {
    let dict = Cypress.env("dadosLogin")
    for (let j = 0; j < Object.keys(dict).length; j++){
      let user = Object.keys(dict)[j]
      let senha = dict[user][0]
      
      cy.loginSefaz(user, senha);
      
      cy.writeFile("notas.json", '{\n"runs":[')
      
      for (let i = 1; i < dict[user].length; i ++){
        const dateObj = new Date();
        let mod = (Cypress.env("diaUm")) ? 0:-1
        const dateInit = new Date(dateObj.getFullYear()+mod, dateObj.getMonth()- (mod+1), 1).toLocaleDateString();
        let dateFim = new Date(dateObj.getFullYear(), dateObj.getMonth(), 1) - new Date(1000*60*60*24)
        dateFim = new Date(dateFim).toLocaleDateString()
        
        cy.buscandoCnpj(user,i, dateInit, dateFim)
        cy.wait(10000)
        cy.cria_arquivo_json(user,i)
        cy.ultimo_dado(user,i)
        
        if (i == dict[user][i].length - 2){
          cy.writeFile("notas.json","\n", {flag: "a+"})
        } else {
          cy.writeFile("notas.json",",\n", {flag: "a+"})
        }
        
      }
      cy.writeFile("notas.json", "\n]\n}", {flag: "a+"});
    }
    
  })
});
