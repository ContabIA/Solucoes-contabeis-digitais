from exceptions import MaxRetrysExceded
from time import sleep


def retry_while_error(f: function, *args, exception:Exception | None = Exception, max_retrys: int|None = 10, sep_time: int|None = 1, log: bool = True, retry_message: str|None = None, **kwargs ):
    
    if retry_message is None: retry_message = "retrying..." 
    
    c = 0
    while c <= max_retrys:
        c += 1
        
        try:
            return f(*args, **kwargs)
        except exception as e:
            
            if log: print(retry_message)
            
            sleep(sep_time)
            
     
    raise MaxRetrysExceded(f"function {f}, exceded the maximun number o retrys {max_retrys}")
