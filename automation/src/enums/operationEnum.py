from enum import Enum
from operations.sefazOperation import SefazOperation

#from operacoes.consulta_cndt import ConsultaCNDT

class OperationEnum(Enum):
    
    SEFAZ_OPERATION = "sefaz_operation"
    #CONSULTA_CNDT = "consulta_cndt"
    

operationsDict = {
    OperationEnum.SEFAZ_OPERATION.value : SefazOperation,
    #OperationEnum.CONSULTA_CNDT: ConsultaCNDT
}