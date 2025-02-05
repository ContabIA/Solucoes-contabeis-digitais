from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from time import sleep
from objs.operacao import Operacao

class NotasSefaz(Operacao):
    raise NotImplementedError("automacao_selenium/src/operacoes/consulta_cndt.py")