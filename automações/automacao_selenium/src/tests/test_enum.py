from enum import Enum
from tests import test_operation_ConsultaCNDT


class test_enum(Enum):
    
    OPERACAO__CONSULTA_CNDT__GET_ULTIMOS_DIGITOS_REQUEST_CNPJ = "operacao__consulta_cndt__get_utimos_digitos_request_cnpj"


test_dict = {
    test_enum.OPERACAO__CONSULTA_CNDT__GET_ULTIMOS_DIGITOS_REQUEST_CNPJ: test_operation_ConsultaCNDT.Test_ConsultaCNDT__get_utimos_digitos_request_cnpj
}