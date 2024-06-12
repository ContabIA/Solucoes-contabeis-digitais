// importando os modulos
const express = require('express')
const path = require("path")


// criando o router
var router = express.Router()


// criando os caminhos 

// criando o caminho para a root



router.get("/",(req,res,next)=>{
    res.render("cnt_main", {script:"/javascripts/cnt_main.js", css:"/stylesheets/cnt_main.css"})
})

module.exports = router;