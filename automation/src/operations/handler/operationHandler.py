from ..operationInterface import OperationInterface
from services.operationHandlerService import operations_in_sequence

class OperationHandler:
    
    args: dict
    debug: bool


    def __init__(self, run_args : dict):
        self.args = run_args
        self.debug = run_args.get("debug", False)
        self._operations = []


    async def run_operations(self):

        if self.args.get("sequence"):

            if self.debug: 
                print("automation/src/operations/handler/operationHandler.py - OperacaoHandler.run_operations - running operations in sequence mode")

            await operations_in_sequence(self.args)