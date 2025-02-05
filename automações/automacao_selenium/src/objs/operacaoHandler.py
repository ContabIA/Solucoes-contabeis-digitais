from objs.operacao import Operacao

class OperacaoSelenium:
    
    args: dict
    debug: bool
    _operacoes: list[Operacao]
    
    @property
    def operacoes(self) -> list[Operacao]:
        return self._operacoes
    
    @operacoes.setter
    def operacoes_setter(self, val:Operacao):
        if (isinstance(val, Operacao)): raise ValueError(f"val deveria ser de tipo 'Operacao' mas é do tipo '{type(Operacao)}'")
        
        self._operacoes.append(Operacao)
    
    
    def __init__(self, **kwargs):
        self.args = kwargs
        self.debug = kwargs.get("debug", False)
        
    async def run_recursive(self):
        raise NotImplementedError("OperacaoHandler.run_recursive")
        
    async def run_operations_in_paralel(self):
        raise NotImplementedError("OperacaoHandler.run_operations_in_paralel")
    
    async def run_operations_in_sequence(self):
        raise NotImplementedError("OperacaoHandler.run_operations_in_sequence")
    
    async def run_operations(self):
        
        if self.args.get("recursive"):
            if self.debug: print("OperacaoHandler.run_operations - recursive")
            self.run_recursive()
            
        elif self.args.get("paralel"):
            if self.debug: print("OperacaoHandler.run_operations - paralel")
            self.run_operations_in_paralel()
            
        else:
            if self.debug: print("OperacaoHandler.run_operations - sequence")
            self.run_operations_in_sequence()
            
    
    def pop_operacao(self) -> Operacao:
        
        return Operacao.pop()
    
     