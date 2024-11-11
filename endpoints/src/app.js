// importações basicas
const express = require("express");

// contantes extras
const app = express()

// usando midwares
app.use(express.json())

// importação de routers
let Routers = {};
const Cndt_router = require("./routers/Router_autoCndt");
Routers.cndt = Cndt_router

// usando routers
Routers_list = Object.entries(Routers);
for (let i = 0; i < Routers_list.length; i++){
    app.use("/" + Routers_list[i][0], Routers_list[i][1]);
}

// caminhos extras
app.get("/", (req, res) => {
    res.status(200).json({"caminhos_validos":Object.keys(Routers)});
})

// lidando com o error 404
app.all("/*", (req, res) => {
    res.status(404).json({error: "page not found"})
})

// listen
const PORT = 3000

app.listen(PORT, () => {
    console.log(`servidor ativo na porta: ${PORT}`)
})