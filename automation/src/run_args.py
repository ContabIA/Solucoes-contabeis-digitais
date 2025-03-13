from enums.operationEnum import OperationEnum

DEFAULT_RUN_ARGS = {
    "sequence": True,
    "debug": False,
    "frequency": 1, # 1 - semanalmente / 2 - mensalmente / 3 - anualmente (vamos mesmo fazer isso?)
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