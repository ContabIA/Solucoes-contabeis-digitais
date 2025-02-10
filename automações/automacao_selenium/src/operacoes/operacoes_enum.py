from enum import Enum
from operacoes.notas_sefaz import NotasSefaz
from operacoes.consulta_cndt import ConsultaCNDT

class OperacoesEnum(Enum):
    
    NOTAS_SEFAZ = "notas_sefaz"
    CONSULTA_CNDT = "consulta_cndt"
    

OperacoesDict = {
    OperacoesEnum.NOTAS_SEFAZ: NotasSefaz,
    OperacoesEnum.CONSULTA_CNDT: ConsultaCNDT
}