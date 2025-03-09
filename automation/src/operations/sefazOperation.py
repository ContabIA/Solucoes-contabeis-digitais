from .operationInterface import OperationInterface

class SefazOperation(OperationInterface):

    def __init__(self, run_arg = None):
        if(run_arg != None):
            super().__init__(run_arg)

    async def oi(self):
        print("teste interface: SefazOperation!")
        print(self.run_args)