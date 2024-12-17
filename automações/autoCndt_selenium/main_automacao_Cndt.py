from pass_captcha import pass_captcha
from baixar_arquivo_cndt import baixar_arquivo_cndt
from verify_cndt import verify_cndt


def main_automacao_Cndt():
    
    from navegador_config import navegador as nav
    
    pass_captcha(nav)
    
    baixar_arquivo_cndt(nav)
    
    return verify_cndt(nav)