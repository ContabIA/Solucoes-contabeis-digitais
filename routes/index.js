var express = require('express');
var router = express.Router();

// routers utilizados por index
const cnt_router = require("./buscar_CNT")

router.use("/buscar-cnt", cnt_router)


/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

module.exports = router;
