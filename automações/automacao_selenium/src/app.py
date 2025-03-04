from fastapi import FastAPI
from main import main
from _collections_abc import Any
import uvicorn


app = FastAPI()

@app.get("/")
async def root(kwargs: dict[str: Any] = {}):
    
    print("automacao_selenium/src/app.py - root")
            
    return await main(**kwargs)
    
if __name__ == "__main__":
    uvicorn.run(app, host="127.0.0.1", port=8000)