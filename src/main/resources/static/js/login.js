let ingre = document.getElementById("ingreso");
let regis = document.getElementById("registro");
let boton_regis = document.getElementById("btn-rg");
let boton_ingre = document.getElementById("btn-ing");
let barra = document.getElementById("sub");

function ingreso() {
    ingre.style.display = 'block';
    boton_regis.style.color = 'rgb(133, 133, 133)';
    barra.style.gridColumn = '1/ span 3';
    regis.style.display = 'none';
    boton_ingre.style.color = 'black';
}

function registro() {
    regis.style.display = 'block';
    boton_regis.style.color = 'black';
    barra.style.gridColumn = '3/ span 5';
    ingre.style.display = 'none';
    boton_ingre.style.color = 'rgb(133, 133, 133)';
}

let most = document.getElementById("mostrar");
let ocu = document.getElementById("oculto");

function mostrar(){
    most.style.display = 'block';
    ocu.style.display = 'none';
}

/* MOSTRAR FORMULARIO DE LOGIN*/
let ir = document.getElementById("bt_ir");
let login = document.getElementById("login");
let form = document.getElementById("form");

function abrir(){
    login.style.display = 'grid';
    // form.style.display = 'block';
    login.classList = 'contenedor-login ab';
}

function cerrar(){    
    login.classList = 'ce';
    login.style.display = 'none'; 
}