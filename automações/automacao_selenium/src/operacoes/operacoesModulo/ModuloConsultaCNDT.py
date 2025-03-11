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
from helpers import retry_while_error

# Configuração do ChromeDriver
service = Service(ChromeDriverManager().install())

# Configuração das opções do Chrome
chrome_options = Options()
download_dir = "caminho/para/a/pasta/downloads"  # Substitua pelo caminho da sua pasta de downloads
chrome_options.add_experimental_option("prefs", {
    "download.default_directory": download_dir,
    "download.prompt_for_download": False,
    "download.directory_upgrade": True,
    "safebrowsing.enabled": True
})


from twocaptcha import TwoCaptcha
solver = TwoCaptcha("31693569b91ed643587f2531785ae020")


DEFAULT_RUN_DATA: dict = {
    "url_pagina_inicial": "",
    "pagina_inicial_xpath_gerar_certidao_button": "",
    "pagina_gerar_certidao_xpath_cnpj_input": "",
    "pagina_gerar_certidao_xpath_gerar_certidao_button": "",
    "pagina_gerar_certidao_xpath_captcha_img": "",
    "pagina_gerar_certidao_xpath_captcha_text_input": "",
    "quant_retry_solve_captcha": 3
}


class ModuloConsultaCNDT:
    def __init__(self, cnpj: str, run_data: dict):
        self.cnpj = cnpj        
        self.run_data = DEFAULT_RUN_DATA
        self.run_data.update(run_data)
        
        
    async def solve_captcha(self, img_src):
        
        c = self.run_data["quant_retry_solve_captcha"]
        while c >= 0:
            try:
                return await solver.normal(file = img_src)

            except:
                pass
        

    def run(self):
        driver = webdriver.Chrome(service=service, options=chrome_options)
        driver.get("https://cndt-certidao.tst.jus.br/inicio.faces")
        
        WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, self.run_data["pagina_inicial_xpath_gerar_certidao_button"])))
        driver.find_element(By.ByXPath, self.run_data["pagina_inicial_xpath_gerar_certidao_button"]).click()
        
        WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, self.run_data["pagina_gerar_certidao_xpath_cnpj_input"])))
        driver.find_element(By.ByXPath, self.run_data["pagina_gerar_certidao_xpath_cnpj_input"]).send_keys(self.cnpj)
        
        WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, self.run_data["pagina_gerar_certidao_xpath_captcha_img"])))
        captcha_result = self.solve_captcha(driver.find_element(By.ByXPath, self.run_data["pagina_gerar_certidao_xpath_captcha_img"]).src)

        driver.find_element(By.Xpath, self.run_data["pagina_gerar_certidao_xpath_captcha_text_input"]).send_keys(captcha_result)
        
        driver.find_element(By.Xpath, self.run_data["pagina_gerar_certidao_xpath_gerar_certidao_button"]).click()     
        
        driver.quit()
        
    
        
        