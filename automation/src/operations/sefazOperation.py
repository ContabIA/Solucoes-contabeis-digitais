from .operationInterface import OperationInterface
import requests

class SefazOperation(OperationInterface):

    def __init__(self, run_arg = None):
        if(run_arg != None):
            super().__init__(run_arg)


    # NÃO ESTÁ FUNCIONANDO (FALTA CRIAR O TOKEN JWT PARA A AUTENTICAÇÃO) 
    async def get_data(self):
        freq = self.run_args.get("frequency")
        response = requests.get(f"http://localhost:8080/automation/getCnpjs?frequencia={freq}&tipoConsulta=1")
        return response.text
    
    async def run(self):
        return await super().run()


    async def test(self):
        print("teste interface: SefazOperation!")
        print(self.run_args)