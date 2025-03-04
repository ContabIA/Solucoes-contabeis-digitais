from objs.operacao import Operacao
from operacoes.operacoesModulo.ModuloConsultaCNDT import ModuloConsultaCNDT
from operacoes.operacoesModulo.ModuloValidaPdfCNDT import ModuloValidaPdfCNDT
from typing import Generator
import requests
import datetime

URL_INPUT_CNPJ = "/service/getCpnj/"
URL_OUTPUT = "/service/respCndt/"

class ConsultaCNDT(Operacao):
    
    def __init__(self, **kwargs):
        super().__init__(**kwargs)
            
        
    def get_ultimos_digitos_request_cnpj(self,tipo:str) -> Generator[str, None, None]:
        
        if self.run_args.get("data") is None:
            data = datetime.date.today()
        else:
            data = datetime.date.fromisoformat(self.run_args["data"])
        
        if tipo == "semanal": quant_digitos, index_day, num_dias = 1, data.weekday()+1, 7 
        elif tipo == "mensal":
            quant_digitos = 2
            index_day = data.day
            num_dias = datetime.date.fromordinal(datetime.datetime(data.year, data.month%12+1, 1).toordinal() -1).day
        elif tipo == "anual": 
            quant_digitos = 3
            index_day = data.toordinal() - datetime.date(data.year, 1, 1).toordinal() + 1
            num_dias = 365 if data.year%4 != 0 else 366
        else:
            raise ValueError(f"Tipo {tipo} não é válido.")
        
        for i in range(index_day-1, 10**quant_digitos, num_dias):
             yield str(i).rjust(quant_digitos, "0")
        
        
    async def get_input_data(self) -> list[str]:
        
        self.run_args["input_data"] = {}
        
        ultimos_digitos_semanal: list[str] = list(self.get_ultimos_digitos_request_cnpj("semanal"))
        ultimos_digitos_mensal: list[str]  = list(self.get_ultimos_digitos_request_cnpj("mensal"))
        ultimos_digitos_anual: list[str]   = list(self.get_ultimos_digitos_request_cnpj("anual"))
        
        cnpjs = []
        for digito in ultimos_digitos_semanal:
            r = requests.get(URL_INPUT_CNPJ,{
                "ultimoDigito":digito,
                "tamanhoFinal":str(len(digito)),
                "frequencia":"semanal",
                "tipoConsulta":"cndt"
            })
            cnpjs.extend(r.json["cnpjs"])
            
        for digito in ultimos_digitos_mensal:
            r = requests.get(URL_INPUT_CNPJ,{
                "ultimoDigito":digito,
                "tamanhoFinal":str(len(digito)),
                "frequencia":"mensal",
                "tipoConsulta":"cndt"
            })
            cnpjs.extend(r.json["cnpjs"])
        
        for digito in ultimos_digitos_anual:
            r = requests.get(URL_INPUT_CNPJ,{
                "ultimoDigito":digito,
                "tamanhoFinal":str(len(digito)),
                "frequencia":"anual",
                "tipoConsulta":"cndt"
            })
            cnpjs.extend(r.json["cnpjs"])
            
        self.run_args["input_data"].updata({"cnpjs":cnpjs})
            
    
    async def run(self):
        raise NotImplementedError("ConsultaCNDT.run")
        