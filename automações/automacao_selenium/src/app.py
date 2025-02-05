from fastapi import FastAPI
from main import main


app = FastAPI()

@app.get("/")
async def root(*args):
    
    print("automacao_selenium/src/app.py - root")
    
    kwargs = {}
    if args:
        print("args:", args)
        for arg in args:
            if "=" not in arg: continue
            key, val = arg.split("=")
            kwargs[key] = val
            
    main(**kwargs)