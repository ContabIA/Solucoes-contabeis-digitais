Cypress.Commands.add("loginSefaz", () => {
    //Logando no Sefaz
    cy.visit('https://www4.sefaz.pb.gov.br/atf/seg/SEGf_LoginSERVirtual.jsp', {failOnStatusCode: false})
    cy.get('[name=edtNoLogin]').type('***REMOVED***');
    cy.get('[name=edtDsSenha]').type('***REMOVED***');
    cy.get('[name=btnAvancar]').click()
})

Cypress.Commands.add('buscandoCnpj', (indexCnpj) => {

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
    .type('01062024')

    cy.get('[name=edtDtFinal]')
    .type(new Date().toLocaleDateString())

    cy.get('[name=cmbTpDoccmpDest]')
    .select(1)
    
    cy.iframe('[name=cmpDest]')
    .as('iframe')
    .find('[name=hidNrDocumentocmpDest]') 
    .type(Cypress.env('cnpjs')[indexCnpj])

    cy.get('@iframe')
    .find('[name=btnPesquisar]')
    .click()

    cy.wait(1000)

    cy.get('[name=btnConsultar]').click()

    cy.wait(1000)
})

let valores = []

Cypress.Commands.add('cria_arquivo_json', (indexCnpj) => {

    valores = []
    cy.writeFile('notas.json', '{' + '\n' + '"listaNotas" : [' + '\n', {flag:"a+"})

    
    //Coletando tabela com notas e tabela com resumo das notas 

    var c = 0
    var len = 0

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
                let registro = {
                id: valores[0].trim(),
                serie: valores[1].trim(),
                data: valores[2].trim(),
                nomeEmitente: valores[3].trim(),
                situacao: valores[4].trim(),
                valor: valores[5].trim(),
                cnpjEmpresa: Cypress.env('cnpjs')[indexCnpj]
                }
                // cy.writeFile('notas.json', registro, {flag: 'a+'})
                // cy.writeFile('notas.json', ',' + "\n", {flag: 'a+'})
                cy.task("escreverJson", {registro: registro, quebra: ",\n"})
                
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


Cypress.Commands.add('ultimo_dado', (indexCnpj) => {
    let registro = {
        id: valores[0].trim(),
        serie: valores[1].trim(),
        data: valores[2].trim(),
        nomeEmitente: valores[3].trim(),
        situacao: valores[4].trim(),
        valor: valores[5].trim(),
        cnpjEmpresa: Cypress.env('cnpjs')[indexCnpj]
    }
    // cy.writeFile('notas.json', registro, {flag: 'a+'})
    // cy.writeFile('notas.json', "\n" + ']' + '\n' + '}', {flag: 'a+'})
    cy.task("escreverJson", {registro: registro, quebra: "\n]\n}"})
})