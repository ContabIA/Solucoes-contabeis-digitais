from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from webdriver_manager.chrome import ChromeDriverManager
import time
import os
import PyPDF2

# Configuração do ChromeDriver
service = Service(ChromeDriverManager.install())

# Configuração das opções do Chrome
chrome_options = Options()
download_dir = "caminho/para/a/pasta/downloads"  # Substitua pelo caminho da sua pasta de downloads
chrome_options.add_experimental_option("prefs", {
    "download.default_directory": download_dir,
    "download.prompt_for_download": False,
    "download.directory_upgrade": True,
    "safebrowsing.enabled": True
})

driver = webdriver.Chrome(service=service, options=chrome_options)

from twocaptcha import TwoCaptcha
solver = TwoCaptcha("31693569b91ed643587f2531785ae020")

async def solve_captcha(img_src: str):
    img_path = f"{download_dir}/captcha.png"
    
    b = True
    while b:
        try:
            WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, "//img[@src='" + img_src + "']")))
            driver.save_screenshot(img_path)
            b = False
        except Exception as e:
            print("error while getting captcha image: " + str(e))
        
    
    try:
        captcha = await solver.normal(img_path)
    except Exception as e:
        print("error while solving captcha: " + str(e))
    
    return captcha["code"]

class ModuloConsultaCNDT:
    def __init__(self, cnpj: str):
        self.cnpj = cnpj

    def run(self):
        driver.get("https://cndt-certidao.tst.jus.br/inicio.faces")

        driver.find_element(By.NAME, "j_id_jsp_992698495_2:j_id_jsp_992698495_3").click()
        
        driver.find_element(By.ID, "gerarCertidaoForm:cpfCnpj").send_keys(self.cnpj)
        
        solve_captcha(driver.find_element(By.ID, "idImgBase64").get_attribute("src"))
        
        
        