from operacoes.operacoes_enum import OperacoesDict
from operacoes.operacoes_enum import OperacoesEnum
from automações.automacao_selenium.src.objs.operacaoHandler import OperacaoHandler


DEFAULT_RUN_ARGS = {
    "recursive": False,
    "paralel": False,
    "debug": False,
    "operacoes": [
        OperacoesEnum.NOTAS_SEFAZ,
        OperacoesEnum.CONSULTA_CNDT
    ],
    "operacoes_run_args": {
        OperacoesEnum.NOTAS_SEFAZ: {
            "debug": False,
            "retry": 3
        
        },
        OperacoesEnum.CONSULTA_CNDT: {
            "debug": False,
            "retry": 3
        }
    }
}


def setup_operacoes(**run_args) -> OperacaoHandler:

    operacoes = run_args.get("operacoes")
    operacoes_run_args = run_args.get("operacoes_run_args")
    
    for operacao in operacoes:
        if operacao not in OperacoesDict:
            raise ValueError(f"Operacao '{operacao}' não encontrada em OperacoesDict")
        
        
    operacao_handler = OperacaoHandler(**run_args)
    
    for operacao in operacoes:
        operacao_handler.add_operacao(OperacoesDict[operacao](**operacoes_run_args[operacao]))
        
    return operacao_handler


async def run_operacoes(operacao_handler: OperacaoHandler):
    
    print("run_operacoes - operacao_handler:", operacao_handler)
    
    if operacao_handler.args.get("recursive"):
        if operacao_handler.debug: print("run_operacoes - recursive")
        await operacao_handler.run_recursive()
        
    elif operacao_handler.args.get("paralel"):
        if operacao_handler.debug: print("run_operacoes - paralel")
        await operacao_handler.run_operations_in_paralel()
        
    else:
        if operacao_handler.debug: print("run_operacoes - sequence")
        await operacao_handler.run_operations_in_sequence()
        
    return operacao_handler



async def main(**kwargs):
    
    print("automacao_selenium/src/main.py - main - kwargs:", kwargs)
    
    run_args = DEFAULT_RUN_ARGS
    if kwargs.get("run_args"):
        run_args.update(kwargs.get("run_args"))
        
    if run_args.get("debug"): print("main.run_args:", run_args)
    
    
    operacao_handler = setup_operacoes(**run_args)
    
    await run_operacoes(operacao_handler)
    
    print("END: autmoacao_selenium/src/main.py - main - operacao_handler:", operacao_handler)