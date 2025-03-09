from services.mainService import setup_operations, update_runArgs

async def main(runArgs):

    #configurações (run_args) atualizados, que serão utilizados no decorrer no código
    new_run_args = update_runArgs(runArgs)
    
    if new_run_args.get("debug") == True: 
        print("automation/src/main.py - main - args:", new_run_args)
    
    operationHandler = setup_operations(new_run_args)
    
    await operationHandler.run_operations()
    
    print("END: automation/src/main.py - main")
