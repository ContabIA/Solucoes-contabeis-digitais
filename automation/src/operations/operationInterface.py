import abc


class OperationInterface(metaclass=abc.ABCMeta):
    
    run_args: dict
    debug: bool
    
    def __init__(self, run_arg : dict):
        self.run_args = run_arg
        self.debug = run_arg.get("debug", False)
    
    @abc.abstractmethod
    async def get_data(self):
        return
    
    '''async def run(self):
        raise NotImplementedError("Operacao.run")
    
    async def send_output_data(self):
        raise NotImplementedError("Operacao.send_output_data")'''
    
    async def test():
        print("teste de interface!")