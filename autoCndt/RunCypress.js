const cypress = require("cypress");
const {join} = require("path");
const fs = require("fs");

class RunCypress{
    constructor(runArgs, runableObj){
        this.runArgs = runArgs;
        this.runableObj = runableObj;
        this.current = undefined;
        this.maxRetrys = 1;
    }

    async #runRunableObj(){
        

        if (this.runableObj != undefined) {

            return this.runableObj.run();

        } else {

            return new Promise((resolve, _) => {
                resolve("end:\n");
            });
        
        };
        
    };
    
    #addStatusToOutput(status){
        let path = join(__dirname, "/autoCndt_output.txt");

        fs.writeFileSync(path,
            fs.readFileSync(path) + "\n" +
            this.runArgs.env.slice(5) + ":" + status
        )
    }

    async #cypressRetry(failMsg, failStatus){
        
        if (this.current === undefined) this.current = 1;

        if (this.current < this.maxRetrys){
            this.current ++;
            console.log("retryng: "+this.runArgs.env);
            return this.run();

        } else {

            this.#addStatusToOutput(failStatus);

            function resp(resp){
                return new Promise((_, reject) => {
                    reject(resp +"\n\t- "+ failMsg)
                });
            };

            return this.#runRunableObj().then(resp, resp);
        }
        
    }

    async #cypressErrorHandler(reason){
        
        return this.#cypressRetry(reason, 3);

    }
    

    async #cypressFailHandler(result){

        return this.#cypressRetry(result.runs[0].tests[0].displayError, 2)
            
    }

    async run(){

        return cypress.run(this.runArgs)
        .then((resultado)=>{

            //console.log(resultado);

            if (resultado.totalFailed === 1) return this.#cypressFailHandler(resultado);

            return this.#runRunableObj();

        }, this.#cypressErrorHandler)

    };
};

module.exports = RunCypress