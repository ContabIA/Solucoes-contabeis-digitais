// importações basicas
const express = require("express");

// constantes extras
const Router_autoSefaz = express.Router();

// usando midwares
Router_autoSefaz.use(express.json);

// importando controllers


// criando as rotas
// get:/
Router_autoSefaz.get("/", (req, res) => {

})


// exportando o Router
module.exports = Router_autoSefaz; 