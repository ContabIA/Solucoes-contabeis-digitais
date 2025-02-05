from objs.operacao import Operacao
from operacoes.operacoesModulo.ModuloConsultaCNDT import ModuloConsultaCNDT
from operacoes.operacoesModulo.ModuloValidaPdfCNDT import ModuloValidaPdfCNDT

URL_INPUT = "C:\\Users\\lucas\\Desktop\\cndt\\input"
URL_OUTPUT = "C:\\Users\\lucas\\Desktop\\cndt\\output"

class ConsultaCNDT(Operacao):
    
    async def __init__(self, **kwargs):
        super().__init__(**kwargs)
        
        await self.get_input_data()
        
    async def get_input_data(self):
        raise NotImplementedError("ConsultaCNDT.get_input_data")
    
    
    async def run(self):
        raise NotImplementedError("ConsultaCNDT.run")
        