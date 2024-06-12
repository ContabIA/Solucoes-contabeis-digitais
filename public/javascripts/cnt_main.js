
function toggle_class(element,strClass,ms){

    element.classList.toggle(strClass);
    
    setTimeout(() =>{
        toggle_class(element,strClass,ms)
    },ms)
}

window.addEventListener("load",() => {
    toggle_class(document.getElementById("1"),"red",1000)
})
