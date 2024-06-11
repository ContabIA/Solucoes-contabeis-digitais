# ContabIA - Soluções Contabeis Digitais

<p>* use "npm run dev_start" ou "npm run debug_dev_start" para abrir um sever em localHost</p>

## resumo de arquivos:

<p>um breve resumo do objetivo dos arquivos/diretorios principais do projeto</p>

- `app.js` : cria o app e exporta
- `/bin/www` : faz o setup e criar um serve localhost para o app
- `/routes` : diretorio de rotas, onde seram criados os roteadores para o site
- `/routes/index.js` : rota principal do root
- `/routes/users.js` : rota automaticamente criada pelo `express-generator`, não vai ser usada até que seja necessário fazer login
- `/routes/buscar-CNT.js` : rota dentro da rota `/routes/index.js` que será a base para buscar a CNT(Certidão Negativa Trabalhista)
- `/routes/buscar-CNF.js` : rota dentro da rota `/routes/index.js` que será a base para buscar a CNF(Certidão Negativa Federal)
- `/routes/buscar-SeFaz.js` : rota dentro da rota `/routes/index.js` que será a base para buscar atualizações no SeFaz(Secretaría da Fazenda)
- `/views` : guarda os arquivos de visualização dinamicos(aka: html dinámico, aka: .hbs) que serão usados no projeto
- `/public` : guarda os arquivos publicos ultilizados pelo projeto
- `/public/javascript` guarda os arquivos javascript utilizados pelos arquivos de visualização
- `/public/images` guarda os arquivos de imagem utilizados pelos arquivos de visualização
- `/public/stylesheets` guarda os arquivos css utilizados pelos arquivos de visualização
- `/public/html` guarda os arquivos html utilizados no projeto
