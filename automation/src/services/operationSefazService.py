from time import sleep
from playwright.async_api import async_playwright


async def automationSefaz():
    await generateConsultaTxt() #função responsável por consultar o CNPJ no sefaz gerar o txt de suas notas


async def generateConsultaTxt():
    async with async_playwright() as p:

        #criação do browser (chromium) utilizado pelo playwright
        browser = await p.chromium.launch(headless=False, slow_mo=10.0)
        page = await browser.new_page()

        #ir para a página de login do sefaz
        await page.goto("https://www4.sefaz.pb.gov.br/atf/seg/SEGf_LoginSERVirtual.jsp")

        #autenticação (no momento é manual com o certificado)
        await page.locator("xpath=/html/body/div/div/form/div[3]/div/a").click()
        sleep(3)

        #ir para a página de consulta das notas
        await page.goto("https://www4.sefaz.pb.gov.br/atf/fis/FISf_ConsultarNFeXml2.do?idSERVirtual=S&h=https://www.sefaz.pb.gov.br/ser/servirtual/credenciamento/info")

        #preenche a data inicial da consulta
        await page.fill("xpath=/html/body/table/tbody/tr[2]/td/form/table/tbody/tr[2]/td[2]/input[1]", "01/01/2025")

        #preenche a data final da consulta 
        await page.fill("xpath=/html/body/table/tbody/tr[2]/td/form/table/tbody/tr[2]/td[2]/input[2]", "01/02/2025")

        #seleciona a opção de pesquisar empresa por CNPJ
        await page.select_option("xpath=/html/body/table/tbody/tr[2]/td/form/table/tbody/tr[10]/td/table/tbody/tr[1]/td[2]/select", "CNPJ")
        
        #iframe que contém os outros inputs do site (não sei por que o sefaz fez isso???)
        frameInp = page.frame(name="cmpDest")

        #digita o CNPJ da empresa para encontra-la no sistema
        await frameInp.locator("input[name='hidNrDocumentocmpDest']").fill("09197286000111")
        await frameInp.locator("[name=btnPesquisar]").click() #realiza a pesquisa no sistema
        sleep(1)

        #faz a consulta das notas da empresa
        await page.click("xpath=/html/body/table/tbody/tr[2]/td/form/table/tbody/tr[12]/td/button")
        sleep(2)

        #seleciona todas as notas encontradas
        await page.click("xpath=/html/body/table/tbody/tr[2]/td/form/table[2]/tbody/tr[2]/td[1]/input")

        #gera o txt com todas as notas encontradas e envia para a caixa de mensagens ATF (por que é tão difícil ;-;)
        await page.click("xpath=/html/body/table/tbody/tr[2]/td/form/table[3]/tbody/tr[2]/td/input[5]")
        sleep(1)
        
        await browser.close()



def analiseTxt():
    lista_notas = []

    #abre o arquivo txt (o arquivo utilizado provavelmente não vai existir)
    with open("NFE_20250311204824.txt", "r") as arq:
        for i, linha in enumerate(arq):
            #ignora a primeira 
            if(i > 0):
                #separa os dados de cada nota em uma lista
                dados_nota = linha.split("|")

                nota = {
                    "id" : dados_nota[0],
                    "numero" : dados_nota[1],
                    "serie" : dados_nota[2],
                    "data" : dados_nota[3],
                    "nome_emitente" : dados_nota[8],
                    "situacao" : dados_nota[5],
                    "valor" : dados_nota[7]
                }

                lista_notas.append(nota)

    return lista_notas