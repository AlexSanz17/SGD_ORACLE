Siged.Commons = {};

Siged.Commons.hideWidget = function(widgetToHide) {
   if (!dojo.byId(widgetToHide)) {
      console.error("(Siged.Commons.hideWidget) No se encontro el widget [%s]", widgetToHide);

      return;
   }

   dojo.byId(widgetToHide).style.display = "none";
};

Siged.Commons.showWidget = function(widgetToShow) {
   if (!dojo.byId(widgetToShow)) {
      console.error("(Siged.Commons.showWidget) No se encontro el widget [%s]", widgetToShow);

      return;
   }

   //dojo.byId(widgetToShow).style.display = "table-row-group";
   dojo.byId(widgetToShow).style.display = "";
};

Siged.Commons.thereIsWidget = function(widget) {
   if (!dijit.byId(widget) || typeof(dijit.byId(widget)) == "undefined") {
      console.error("(Siged.Commons.existWidget6) No existe el widget [%s]", widget);

      return false;
   }

   return true;
}
