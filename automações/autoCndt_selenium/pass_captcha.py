from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as Ec
from selenium.webdriver.common.by import By



def pass_captcha(nav) -> None:
    
    WebDriverWait(nav, 10).until(
        Ec.presence_of_element_located((By.ID, "idImgBase64"))
    )
    
    imagem = nav.find_element(By.ID, "idImgBase64")
    
    src_imagem = imagem.get_attribute("src")
    
    ...
    
