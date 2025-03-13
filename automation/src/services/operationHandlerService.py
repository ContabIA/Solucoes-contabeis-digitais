from enums.operationEnum import operationsDict

async def operations_in_sequence(run_args : dict):
    operations_list = run_args.get("operations")
    
    for o in operations_list:
        
        if run_args.get("debug"):
            print("sevices/operationHandlerService.py - operations_in_sequence - runing operation:", o)
        
        operation = operationsDict.get(o)(run_args)
        await operation.test()

