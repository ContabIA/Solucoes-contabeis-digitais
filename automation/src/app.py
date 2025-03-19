from fastapi import FastAPI
from fastapi.responses import JSONResponse
import uvicorn
from pydantic import BaseModel
from typing import Optional

from main import main

app = FastAPI()

class RunArgsBody(BaseModel):
    recursive: bool | None = None
    paralel: bool | None = None
    sequence: bool | None = None
    debug: bool | None = None
    frequency: int | None = None
    operations: list | None = None
    operations_run_args: dict | None = None

class BodyRequest(BaseModel):
    run_args : RunArgsBody | None = None


@app.get("/")
async def root(body: Optional[BodyRequest]):

    runArgsBody = body.model_dump().get("run_args")

    if runArgsBody is None:
        return JSONResponse(content={"message": "run args não enviado"}, status_code=400)
    
    print("automation/src/app.py - root")

    try:
        await main(runArgsBody)
    except ValueError:
        return JSONResponse(content={"message": "verifique se as operações listadas existem"}, status_code=400)

    return JSONResponse(content={"message":"operação concluida com sucesso!"}, status_code=200)


if __name__ == "__main__":
    uvicorn.run(app, host="127.0.0.1", port=8000)