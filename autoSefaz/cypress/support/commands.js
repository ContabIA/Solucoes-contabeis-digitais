// Função que loga no sefaz utilizando o usuario e senha fornecido
Cypress.Commands.add("loginSefaz", (user, senha) => {
    //Logando no Sefaz
    cy.visit('https://www4.sefaz.pb.gov.br/atf/seg/SEGf_LoginSERVirtual.jsp', {failOnStatusCode: false})
    cy.get('[name=edtNoLogin]').type('***REMOVED***');
    cy.get('[name=edtDsSenha]').type('***REMOVED***');
    cy.get('[name=btnAvancar]').click()
})

// Função que busca cnpj no Sefaz
Cypress.Commands.add('buscandoCnpj', (user, indexCnpj, dateInit, dateFim) => {
    
    
    //Inserindo dados das consultas
    cy.visit('https://www4.sefaz.pb.gov.br/atf/fis/FISf_ConsultarNFeXml2.do?idSERVirtual=S&h=https://www.sefaz.pb.gov.br/ser/servirtual/credenciamento/info', {failOnStatusCode: false})
    
    cy.get('[name=edtDtInicial]').clear()

    cy.get('[name=edtDtFinal]').clear()

    cy.iframe('[name=cmpDest]')
    .as('iframe')
    .find('[name=hidNrDocumentocmpDest]').clear()

    cy.iframe('[name=cmpDest]')
    .as('iframe')
    .find('[name="hidNoHumanoInstcmpDest"]') 
    .clear()


    cy.get('[name=edtDtInicial]')
    .type(dateInit)

    cy.get('[name=edtDtFinal]') 
    .type(dateFim)

    cy.get('[name=cmbTpDoccmpDest]')
    .select(1)
    
    cy.iframe('[name=cmpDest]')
    .as('iframe')
    .find('[name=hidNrDocumentocmpDest]') 
    .type(Cypress.env("dadosLogin")[user][indexCnpj])

    cy.get('@iframe')
    .find('[name=btnPesquisar]')
    .click()

    cy.wait(1000)

    cy.get('[name=btnConsultar]').click()

    cy.wait(1000)
})

let valores = []
let registro = ""

Cypress.Commands.add('cria_arquivo_json', (user, indexCnpj) => {

    valores = []

    //Coletando tabela com notas e tabela com resumo das notas 
    var c = 0
    var len = 0
    registro = ""
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
            
            if (len == 6){
                let nota = {
                id: parseInt(valores[0].trim()),
                serie: parseInt(valores[1].trim()),
                data: valores[2].trim().split("/")[2] + "-" + valores[2].trim().split("/")[1] + "-" + valores[2].trim().split("/")[0],
                nomeEmitente: valores[3].trim(),
                situacao: valores[4].trim(),
                valor: valores[5].trim(),
                cnpjEmpresa: Cypress.env('dadosLogin')[user][indexCnpj]
                }
                registro += JSON.stringify(nota) + ","
                
                len = 1
                valores = []
                valores.push($conteudo)
            } else { 
                len = len + 1
                valores.push($conteudo)
            }
        })
        if (index <= 1){
            return
        }
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
