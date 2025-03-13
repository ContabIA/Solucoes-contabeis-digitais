from .operationInterface import OperationInterface
import requests

class SefazOperation(OperationInterface):

    def __init__(self, run_arg = None):
        if(run_arg != None):
            super().__init__(run_arg)


    async def get_data(self):
        response = requests.get("http:localhost:8080/automation/getCnpj", {
            "frequencia" : self.run_args.get("frequency"),
            "tipoConsulta" : 1
        })

        return response.json()


    async def test(self):
        print("teste interface: SefazOperation!")
        print(self.run_args)