<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="js/dojobuild/css/styleDojo.css" />
        <script type="text/javascript" src="js/dojobuild/js/requires.js"></script>
        <script type="text/javascript" src="js/dojo/js/commons.js"></script>        
        <script type="text/javascript">
            //                    alert("(getUsuariosCompartidosd) IdUsuario -> [%s] ", item.idUsuario);

            /*if (item.valor > 0) {
                        dojo.byId(item.recurso).innerHTML = "(" + item.valor + ")";
                    } else {
                        dojo.byId(item.recurso).innerHTML = "";
                    }*/
            var serviceDA = new dojo.rpc.JsonService("SMDAction.action");
            var getUsuarios = function() {
                serviceDA.getUsuariosCompartidos().addCallback(function(items) {
                    var cad = "";
                    dojo.forEach(items, function(item) {
                        console.debug("(getUsuariosCompartidos) Usuario[" + item.valor + "]=" + item.recurso);
                        var nombre = reemplazarEspaciosEnBlanco(item.recurso);
                        var url = "javascript:showGridInboxByUser('" + item.valor + "','" + nombre + "');";
                        console.debug("(getUsuariosCompartidos) url->"+url);
                        cad = cad + "<li><a href=" + url + ">" + item.recurso + "</a></li>";
                    });
                    dojo.byId("userList").innerHTML = cad;
                });
            };
            dojo.addOnLoad(function() {
                getUsuarios();
            })
        </script>
    </head>
    <body>       
        <ul id="userList">
        </ul>
    </body>
</html>