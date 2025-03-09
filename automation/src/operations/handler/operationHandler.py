from ..operationInterface import OperationInterface
from automation.src.enums.operationEnum import OperationEnum
from services.operationHandlerService import operations_in_sequence
from typing import Any

class OperationHandler:
    
    args: dict
    debug: bool
    _operations: list[OperationInterface | Any]
    
    #GET do atributo _operations
    @property
    def operations(self) -> list[OperationInterface]:
        return self._operations
    
    #SETTER do atributo _operations
    @operations.setter
    def operations(self, val:OperationInterface):

        if (not isinstance(val, OperationInterface)): 
            raise ValueError(f"val deveria ser de tipo 'Operacao' mas é do tipo '{type(OperationInterface)}'")
        
        self._operations.append(val)


    def __init__(self, run_args : dict):
        self.args = run_args
        self.debug = run_args.get("debug", False)
        self._operations = []


    async def run_operations(self):

        if self.args.get("sequence"):

            if self.debug: 
                print("automation/src/operations/handler/operationHandler.py - OperacaoHandler.run_operations - running operations in sequence mode")

            await operations_in_sequence(self.args)
    
    
    def pop_operacao(self) -> OperationInterface:
        return self._operations.pop()