

class OperacaoSelenium:
    
    input: dict
    
    def __init__(self, input:dict):
        self.input = input
        
    async def run(self) -> list[str]:
        
        raise NotImplementedError("OperacaoSelenium.run")