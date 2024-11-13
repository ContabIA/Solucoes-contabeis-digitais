// importações basicas
const express = require("express");

// constantes extras
const Router_autoSefaz = express.Router();

// usando midwares
Router_autoSefaz.use(express.json());

// importando controllers
const controller_Sefaz = require("../controlers/Sefaz_controller")

// criando as rotas
// get:/
Router_autoSefaz.get("/", (req, res) => {
    res.json(Object.keys(controller_Sefaz))
})

// get:/:operacao
Router_autoSefaz.get("/:operacao", (req, res) => {
    controller_Sefaz[req.params.operacao](req.body)
    .then((ret) => {
        res.status(200).json(ret)
    })
})

// exportando o Router
module.exports = Router_autoSefaz; 