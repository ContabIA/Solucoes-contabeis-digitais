const cypress = require('cypress')

  cypress
  .run({
    spec: './cypress/e2e/login.cy.js',
  })
  .then((results) => {
    console.log(results)
  })
  .catch((err)  => {
    console.error(err)
  })