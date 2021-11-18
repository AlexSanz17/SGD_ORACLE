<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title></title>
      <link href="css/estilo.css" rel="stylesheet" type="text/css">
      <style type="text/css">
         <!--
         body {background-color: #ffffff;}
         -->
      </style>
      
      <script type="text/javascript">
			var djConfig = {
				isDebug: false,
				parseOnLoad: true
			};
		</script>
<script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
<script type="text/javascript" src="js/siged/upload.js"></script>
<%--<script type='text/javascript' src='./dwr/engine.js'> </script>
		<script type='text/javascript' src='./dwr/util.js'> </script>
		<script type='text/javascript' src='./dwr/interface/toolDwr.js'></script>--%>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/siged/siged.js"></script>
<script type="text/javascript" src="js/siged/siged.forms.js"></script>
<script type="text/javascript" src="js/siged/siged.string.js"></script>
<script type="text/javascript" src="js/siged/derivacion.js"></script>

      <script type="text/javascript">
      function refrescar() {
          //window.opener.parent.location.href = "inicio.action?sTipoFrame=grid";
          console.debug(window.opener.sTipoGridActual);
          console.debug(window.opener.TIPO_GRID_SEGUIMIENTO);
      	 try {
      		 if(window.opener.sTipoGridActual != window.opener.TIPO_GRID_SEGUIMIENTO){
      			 window.opener.eliminarRegistro();
                	 window.opener.filtrarSeguimiento();
      		 }else{
      			window.opener.refrescarDetalle(); 
      		 }
      	}catch(err){ 
   			  //console.debug("errror : "+err);
   			  this.window.close();
           }

          if (provieneDeMail == "true") {
             window.opener.close();
          }
         
          this.window.close();
       }
      </script>
   </head>
   <body class="soria"
	onload="refrescar();">
		<p style="text-align: center;">Operaci&oacute;n realizada
			satisfactoriamente</p>
   </body>
</html>