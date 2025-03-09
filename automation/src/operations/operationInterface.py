#from typing import Any
#from .operationHandler import OperacaoHandler


class OperationInterface:
    
    run_args: dict
    debug: bool
    
    def __init__(self, run_arg : dict):
        self.run_args = run_arg
        self.debug = run_arg.get("debug", False)
        
    '''async def get_input_data(self) -> Any:
        raise NotImplementedError("Operacao.get_input_data")
    
    async def run(self):
        raise NotImplementedError("Operacao.run")
    
    async def send_output_data(self):
        raise NotImplementedError("Operacao.send_output_data")'''
    
    async def oi():
        print("teste de interface!")
    
    #async def retry(self, module: OperacaoHandler, retrys: int = 3) -> Any:
    #    raise NotImplementedError("Operacao.retry")