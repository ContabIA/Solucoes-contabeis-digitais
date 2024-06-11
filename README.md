*use "npm run dev_start" ou "npm run debug_dev_start" para abrir um sever em localHost

app.js : cria o app e exporta
/bin/www : faz o setup e criar um serve localhost para o app
/routes : diretorio de rotas, onde seram criados os roteadores para o site
/routes/index.js : rota principal do root
/routes/users.js : rota automaticamente criada pelo 'express-generator', não vai ser usada até que seja necessário fazer login
/routes/buscar-CNT.js : rota dentro da rota '/routes/index.js' que será a base para buscar a CNT(Certidão Negativa Trabalhista) 