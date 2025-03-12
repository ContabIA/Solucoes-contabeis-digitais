from enums.operationEnum import OperationEnum, operationsDict
from operations.handler.operationHandler import OperationHandler
from run_args import DEFAULT_RUN_ARGS


def update_runArgs(runArgs : dict) -> dict:
    new_run_args = {k : v for k, v in runArgs.items() if v != None}
    run_args = DEFAULT_RUN_ARGS

    run_args.update(new_run_args)

    if(runArgs.get("debug")):
        print("automation/src/services/mainService.py - mainService.update_runArgs - updated run_args: ", run_args)

    return run_args


def setup_operationHandler(runArgs : dict) -> OperationHandler:
    operations_list = runArgs.get("operations")

    if(runArgs.get("debug")):
        print("automation/src/services/mainService.py - mainService.setup_operations - running with operations list: ", operations_list) 
    
    for operation in operations_list:
        if OperationEnum(operation) not in list(OperationEnum):
            raise ValueError(f"Operacao '{operation}' não encontrada em OperationsDict")
    
    operationHandler = OperationHandler(runArgs)

    if(runArgs.get("debug")):
        print("automation/src/services/mainService.py - mainService.setup_operations - operationHandler created") 

    return operationHandler