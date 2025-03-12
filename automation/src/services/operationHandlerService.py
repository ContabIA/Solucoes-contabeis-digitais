from enums.operationEnum import operationsDict

async def operations_in_sequence(run_args : dict):
    operations_list = run_args.get("operations")
    
    for o in operations_list:
        operation = operationsDict.get(o)(run_args)
        await operation.oi()

