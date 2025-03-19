from time import sleep
import os
import zipfile
from playwright.async_api import async_playwright


pasta_downloads = os.path.join(os.getcwd(), 'automation/downloads')  # Pasta onde o arquivo ZIP está


async def runAutomation():
    await generateConsultaTxt() #função responsável por consultar o CNPJ no sefaz gerar o txt de suas notas
    await downloadNotas()
    await descompactar_e_apagar_zip(pasta_downloads)
    a = await analiseTxt()
    return a



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

async def downloadNotas():
     async with async_playwright() as p:

        #criação do browser (chromium) utilizado pelo playwright
        browser = await p.chromium.launch(headless=False, slow_mo=10.0)
        page = await browser.new_page()

        #ir para a página de login do sefaz
        await page.goto("https://www4.sefaz.pb.gov.br/atf/")

        frameInp = page.frame(name="contents")

        #login via credenciaias
        await frameInp.locator("input[name='edtNoLogin']").fill("jos00037")
        await frameInp.fill("xpath=/html/body/table/tbody/tr[2]/td/table[1]/tbody/tr[4]/td[2]/input", "2025senior")
        await frameInp.click("xpath=/html/body/table/tbody/tr[2]/td/table[1]/tbody/tr[5]/td[2]/input[2]")

        sleep(0.5) #NUNCA TIRE ESSE SLEEP, EM HIPOTESE ALGUMA!!!!

         #ir para a página de consulta das notas
        await page.goto("https://www4.sefaz.pb.gov.br/atf/seg/SEGf_MinhasMensagens.do?limparSessao=true")

        # Dá foco ao elemento
        await page.focus("xpath=/html/body/form/div/table/tbody/tr[3]/td[2]/a")

        await page.click("xpath=/html/body/form/div/table/tbody/tr[3]/td[2]/a")

        await page.focus("xpath=/html/body/form/div/table/tbody/tr[5]/td[4]/a")

        await page.click("xpath=/html/body/form/div/table/tbody/tr[5]/td[4]/a")

        await page.focus("xpath=/html/body/table/tbody/tr[2]/td/form/table/tbody/tr[8]/td/a")

        await page.click("xpath=/html/body/table/tbody/tr[2]/td/form/table/tbody/tr[8]/td/a")

        # Aguarda o download ser iniciado
        download = await page.wait_for_event('download')


        # Aguarda o download ser concluído e obtém o caminho do arquivo
        caminho_arquivo = await download.path()

        # Move o arquivo para o diretório de downloads da API
        nome_arquivo = download.suggested_filename  # Nome sugerido do arquivo
        api_download_path = os.path.join(os.getcwd(), 'automation/downloads')  # Pasta da API
        if not os.path.exists(api_download_path):
            os.makedirs(api_download_path)
        novo_caminho = os.path.join(api_download_path, nome_arquivo)
        os.rename(caminho_arquivo, novo_caminho)

        # Fecha o navegador
        await browser.close()

async def descompactar_e_apagar_zip(pasta_downloads):
    """
    Descompacta o único arquivo ZIP na pasta de downloads e apaga o arquivo ZIP após a descompactação.
    :param pasta_downloads: Caminho da pasta onde o arquivo ZIP está localizado.
    """
    # Encontra o único arquivo ZIP na pasta
    arquivo_zip = next(arquivo for arquivo in os.listdir(pasta_downloads) if arquivo.endswith(".zip"))

    # Caminho completo do arquivo ZIP
    caminho_zip = os.path.join(pasta_downloads, arquivo_zip)

    # Descompacta o arquivo ZIP na mesma pasta
    with zipfile.ZipFile(caminho_zip, 'r') as zip_ref:
        zip_ref.extractall(pasta_downloads)

    # Apaga o arquivo ZIP após a descompactação
    os.remove(caminho_zip)

async def analiseTxt():
    lista_notas = []
    txt = os.listdir("automation/downloads/")

    #abre o arquivo txt (o arquivo utilizado provavelmente não vai existir)
    with open(os.path.join("automation/downloads/", txt[0]), "r") as arq:
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


#const dateInit = new Date(dataAtual.getFullYear()+mod, dataAtual.getMonth()- (mod+1), 1).toLocaleDateString();