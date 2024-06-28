Cypress.Commands.add('coleta_notas', () => {

    //Logando no Sefaz
    cy.visit('https://www4.sefaz.pb.gov.br/atf/seg/SEGf_LoginSERVirtual.jsp', {failOnStatusCode: false})
    cy.get('[name=edtNoLogin]').type('***REMOVED***');
    cy.get('[name=edtDsSenha]').type('***REMOVED***');
    cy.get('[name=btnAvancar]').click()

    //Inserindo dados das consultas
    cy.visit('https://www4.sefaz.pb.gov.br/atf/fis/FISf_ConsultarNFeXml2.do?idSERVirtual=S&h=https://www.sefaz.pb.gov.br/ser/servirtual/credenciamento/info', {failOnStatusCode: false})
    cy.get('[name=edtDtInicial]')
    .type('01052024')
    cy.get('[name=edtDtFinal]')
    .type('31052024')
    cy.get('[name=cmbTpDoccmpDest]')
    .select(1)

    cy.iframe('[name=cmpDest]')
    .as('iframe')
    .find('[name=hidNrDocumentocmpDest]') 
    .type('09197286000111')

    cy.get('@iframe')
    .find('[name=btnPesquisar]')
    .click()

    cy.wait(1000)

    cy.get('[name=btnConsultar]').click()

    cy.wait(1000)
    

    
})

Cypress.Commands.add('cria_arquivo_json', () => {
    cy.writeFile('notas.txt', '')
    
    //Coletando tabela com notas e tabela com resumo das notas 

    var c = 0
    cy.get('[name=frmConsultar] > table:nth-child(2) > tbody > tr > td')
    .each(($coluna, index) => {

        if (index < 8){
            c += 1
            return
        }
        if (index == 8){
            c += 7
            return
        }
        if (index == c){
            c += 7
            return
        } 
        cy.get($coluna).invoke('text').then(($conteudo) => {
            if (index == c - 1){
                cy.writeFile('notas.txt', $conteudo  + '\n', {flag :'a+'})
            } else{
                cy.writeFile('notas.txt', $conteudo  + ', ', {flag :'a+'})
            }
            
        })
        if (index <= 1){
            return
        }
        
        // var conteudo = ''
        // cy.get($linha).find('td').each(($coluna, k, $lista) => {

        //     // cy.get($lista).invoke('text').then(($conteudo) => {
        //     //     cy.writeFile('notas.txt', $conteudo + '\n', {flag : 'a+'})
        //     // })

        //     // return false
        //     if (k==0){
        //         return
        //     }

            
        //     cy.get($coluna).invoke('text').then(($conteudo) => {
        //         conteudo = $conteudo + ','
        //         if (k == $lista.length - 1){
        //             conteudo = $conteudo + '\n'
        //         }
                
        //         cy.writeFile('notas.txt', conteudo, {flag : 'a+'})
        //     })
            

            
        // })
        
        
        


        // cy.get($linha).find('td').each(($coluna) => {
        //     cy.get($coluna)
        // })
    }) 

    
    

})

//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })