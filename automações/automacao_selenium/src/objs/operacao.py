

class Operacao:
    
    run_args: dict
    debug: bool
    
    def __init__(self, **kwargs):
        self.run_args = kwargs
        self.debug = kwargs.get("debug", False)
        
    async def get_input_data(self):
        raise NotImplementedError("Operacao.get_input_data")
    
    async def run(self):
        raise NotImplementedError("Operacao.run")
    
    async def send_output_data(self):
        raise NotImplementedError("Operacao.send_output_data")
    
    async def retry(self):
        raise NotImplementedError("Operacao.retry")