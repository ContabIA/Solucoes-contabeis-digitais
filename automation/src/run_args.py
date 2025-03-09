from automation.src.enums.operationEnum import OperationEnum

DEFAULT_RUN_ARGS = {
    "sequence": True,
    "debug": False,
    "operations": [
        OperationEnum.SEFAZ_OPERATION.value
    ],
    "operacoes_run_args": {
        OperationEnum.SEFAZ_OPERATION.value: {
            "debug": False,
            "retry": 3
        }
    }
}