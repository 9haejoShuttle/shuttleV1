console.log("Apply Module")
let applyService = (()=>{
    function add(apply, callback){
        console.log("apply....");
    }
    return {add:add};
})();