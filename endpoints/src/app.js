// importações basicas
const express = require("express");

// contantes extras
const app = express()

// usando midwares
app.use(express.json)

// importação de routers

// usando routers

// caminhos extras

// lidando com o error 404
app.all("/*", (req, res) => {
    res.status(404).json({error: "page not found"})
})

// listen
const PORT = 3000

app.listen(PORT, () => {
    console.log(`serve active at port: ${PORT}`)
})