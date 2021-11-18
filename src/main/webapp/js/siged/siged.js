//Creacion del namespace siged
Siged = {}

//Agregar funciones comunes en esta seccion ejemplo Siged.getBasepath()

Siged.windowName = window.name.split("_")[0];

Siged.contextPath = window.location.pathname.substring(0, window.location.pathname.indexOf('/',1) );


