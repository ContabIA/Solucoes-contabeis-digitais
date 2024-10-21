// importações basicas
const express = require("express");

// constantes extras
const Router_autoCndt = express.Router();

// usando midwares
Router_autoCndt.use(express.json);

// importando controllers


// criando as rotas
// get:/
Router_autoCndt.get("/", (req, res) => {

})


// exportando o Router
module.exports = Router_autoCndt; 