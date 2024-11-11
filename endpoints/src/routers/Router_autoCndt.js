// importações basicas
const express = require("express");

// constantes extras
const Router_autoCndt = express.Router();

// usando midwares
Router_autoCndt.use(express.json);

// importando controllers
const controller_cndt = require("../controlers/Cndt_controller");

// criando as rotas
// get:/
Router_autoCndt.get("/", (req, res) => {
    res.json(controller_cndt.keys());
})


// get:/:operacao
Router_autoCndt.get("/:operacao", (req, res) => {
    controller_cndt[req.params.operacao](req.body);
})


// exportando o Router
module.exports = Router_autoCndt; 