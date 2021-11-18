
dojo.addOnLoad(function() {
  Siged.Forms.combobox("iddestinatariosMultiple",{
      id              : "idconcopiaMultiple",
      jsId            : "idconcopiaMultiple",
      storeUrl        : "getFavorito.action?mode=idconcopia",
      searchAttr      : "label",
      searchDelay     : 650,
      queryExpr       : "*${0}*",
      autoComplete    : false,
      hasDownArrow    : true,
      highlightMatch  : "all",
      style          : "width:400px;",
      required        : false,
      onChange        : agregarConCopia
   });

});