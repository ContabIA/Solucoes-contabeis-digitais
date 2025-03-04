from exceptions import MaxRetrysExceded
from time import sleep
from typing import Any, Callable


def retry_while_error(f: Callable, *args, exception:Exception | None = Exception, max_retrys: int|None = 10, sep_time: float|None = 1, log: bool = True, retry_message: str|None = None, show_error: bool = True, stop_if_wrogh_error: bool = False, **kwargs ):
    """função que usa a função repassada 'max_retry' vezes até que a função não levante a exeção 'exception'

    Args:
        f (function): função que sera rodada
        exception (Exception | None, optional): exeção esperada. Defaults to Exception.
        max_retrys (int | None, optional): número maximo de tentativas. Defaults to 10.
        sep_time (float | None, optional): tempo de espera entre as tentativas. Defaults to 1.
        log (bool, optional): se verdadeiro, vai printar a retry message. Defaults to True.
        retry_message (str | None, optional): a menssagem que vai ser imprimida em caso de error. Defaults to "Retrying...".
        show_error (bool, optional): se verdadeiro vai imprimir a exeção depois da 'retry_message'. Defaults to True.
        stop_if_wrogh_error (bool, optional): se verdadeiro para o codigo quando uma exeção diferente da esperada aparece. Defaults to False.

    Raises:
        e: exeção não esperada (se 'stop_if_wrogh_error' for True)
        MaxRetrysExceded: o limite de tentativas('max_retrys') foi passado

    Returns:
        any: retorno da função executada
    """
    
    if retry_message is None: retry_message = "retrying..." 
    
    c = 0
    while c <= max_retrys:  
        c += 1
        
        try:
            return f(*args, **kwargs)
        except exception as e:
            
            if log: print(retry_message)
            if show_error: print(e)
            
            sleep(sep_time)
            
        except Exception as e:
            
            if stop_if_wrogh_error: raise e
            
            if log: print(retry_message)
            if show_error: print(e)
            
            sleep(sep_time)
            
            
     
    raise MaxRetrysExceded(f"function {f}, exceded the maximun number o retrys {max_retrys}")
