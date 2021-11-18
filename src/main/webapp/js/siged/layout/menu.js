
/*
 * Functions
 */
var getUnread = function() {
   service.getUnread().addCallback(function(lstUnread) {
      dojo.forEach(lstUnread, function(item) {
         console.debug("(getUnread) Recurso [%s] disponible [%d]", item.recurso, item.valor);

         if (item.valor > 0) {
            dojo.byId(item.recurso).innerHTML = "(" + item.valor + ")";
         } else {
            dojo.byId(item.recurso).innerHTML = "";
         }
      });
   /*if (unread > 0) {
         dojo.byId(item.codigo).innerHTML = "(" + unread + ")";
      } else {
         dojo.byId(item.codigo).innerHTML = "";
      }*/
   });

/*dojo.forEach(arrRecursoMenu, function(item) {
   console.debug("(getUnread) Recurso [%s] disponible [%d]", item.codigo, item.valor);

      if (item.valor) {
         service.getUnread(item.codigo).addCallback(function(unread) {
            if (unread > 0) {
               dojo.byId(item.codigo).innerHTML = "(" + unread + ")";
            } else {
               dojo.byId(item.codigo).innerHTML = "";
            }
         });
      }
   });*/
};

/*
 * DOJO Main
 */
dojo.addOnLoad(function() {
   getUnread();
// if(first){  
//	   showGridInbox(0);
//	   first =false ;
//  }  
});
