<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <title>Tramite Documentario</title>
   <s:head theme="ajax" />
   <link rel="stylesheet" type="text/css" href="css/estilo.css">
   <script language="JavaScript" src="js/dtreeCarpeta.js"></script>
   <script language="javascript" src="js/form.js"> </script>
   <script language="Javascript" src="js/general.js"></script>
   <link rel="StyleSheet" href="css/dtree.css" type="text/css" />
   <link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
   <script type="text/javascript" src="js/calendar.js"></script>
   <script type="text/javascript" src="js/calendar-es.js"></script>
   <script type="text/javascript" src="js/calendar-setup.js"></script>
   <script type="text/javascript" src="js/si.files.js"></script>
   <script type="text/javascript" src="js/sorttable.js"></script>
   <script type="text/javascript" src="/runtime/lib/aw.js"></script>
   <link rel="stylesheet" href="/runtime/styles/system/aw.css">	

   <script language="JavaScript">
      function Abrir_ventana (pagina) {
         var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=440, height=350, top=50, left=200";
         window.open(pagina,"",opciones);
      }

      function Abrir_pagina (pagina) {
         var opciones="location=mainFrame";
         window.open(pagina,"",opciones);
      }
      function Abrir_ventanaDerivar (pagina) {
         var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=900, height=640, top=20, left=70";
         window.open(pagina,"",opciones);
      }
      function Abrir_ventanaSeguimiento (pagina) {
         var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=800, height=400, top=50, left=100";
         window.open(pagina,"",opciones);
      }

      function Abrir_ventanaObservacion (pagina) {
         var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=650, height=400, top=50, left=200";
         window.open(pagina,"",opciones);
      }

      function ejecAprob(){
         
    	    valor=confirm("Desea Mandar a la Bandeja Mensajeria")
			
			if (valor==true){
      	 		document.forms["FrmIngmen"].submit();
			}
         
      }
      function ejecRech(){
         if (confirm("Desea rechazar el expediente EXP001")){
            location.href="rechazar.html"
         }
      }

      function SubmitForm(){
          	
      }
      function ejecutar(){
          if (confirm("Desea aprobar el expediente EXP001")){
             location.href="blank.html"
          }
       }
   </script>

   <script type="text/javascript">
      function poorman_toggle(id)
      {
         var tr = document.getElementById(id);
         if (tr==null) { return; }
         var bExpand = tr.style.display == '';
         tr.style.display = (bExpand ? 'none' : '');
      }

      function poorman_changeimage(id, sMinus, sPlus)
      {
         var img = document.getElementById(id);
         if (img!=null)
         {
            var bExpand = img.src.indexOf(sPlus) >= 0;
            if (!bExpand)
               img.src = sPlus;
            else
               img.src = sMinus;
         }
      }

      function Toggle_repHeader2()
      {
         poorman_changeimage('repHeader2_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
         poorman_toggle('row1');
         poorman_toggle('row2');
         poorman_toggle('row3');
      }

      function Toggle_repHeader1()
      {
         poorman_changeimage('repHeader1_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
         poorman_toggle('trRow1');
      }
      function Toggle_repHeader3()
      {
         poorman_changeimage('repHeader3_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
         poorman_toggle('row1');
         poorman_toggle('row2');
         poorman_toggle('row3');
      }

      function Toggle_repHeader3() 
      {
         poorman_changeimage('repHeader3_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
         poorman_toggle('trRow3');
      }

      function abrirventanaxd(i,j) {
          var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=500, height=290, top=400, left=400";
				
				window.open("doingasunto.action?Idcoc="+i+"&Idusuario="+j,"",opciones);
       }    
   </script>

   <script language="JavaScript" type="text/javascript">
      function treeNodeSelected(iIdDoc) {

         // alert(iIdDoc.node.widgetId);

         dojo.io.bind({
            url: "doViewTrazabilidad.action",
            content: {
               iIdDocumento: iIdDoc.node.widgetId
            },
            load: function(type, data, evt) {
               var displayDiv = dojo.byId("displayId");
               displayDiv.innerHTML = data;
            },
            mimeType: "text/html"
         });
      };

      dojo.event.topic.subscribe("treeSelected", this, "treeNodeSelected");

      function muestra_oculta(id){
             
             if (document.getElementById(id))  //se obtiene el id
            var el = document.getElementById(id); //se define la variable "el" igual a nuestro div
            el.style.display = (el.style.display == 'none') ? 'block' : 'none'; //damos un atributo display:none que oculta el div

         } 


      function general(){ 
    	 // <s:if test="regresar=='enviados'">
    	    location.href="DocumentoEnviado_verDetalle.action"+"?idDocumentoEnviado="+"<s:property value='idenv'/>";
    	         
    	 // </s:if>
    	   
    	 // <s:else>
    	  location.href="doViewDoc.action?iIdDoc=<s:property value='objDD.iIdDocumento'/>"; 
    	 
    	 // </s:else>
    	  
          }
   </script>

   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   <style type="text/css">
      <!--
      body {
         background-color: #ffffff;
      }

      .barra {
         scrollbar-3dlight-color:#CCCCCC;
         scrollbar-arrow-color:#568BBF;
         scrollbar-base-color:#C3D5E9;
         scrollbar-darkshadow-color:#666666;
         scrollbar-face-color:;
         scrollbar-highlight-color:#669BCD;
         scrollbar-shadow-color:#999999;
      }

      div.falso {
         position: absolute;
         top: -29px;
         left: 335px;
         z-index: 0;
         width: 14px;
         height: 3px;
      }

      input.file {
         position: relative;
         text-align: left;
         filter:alpha(opacity: 0);
         opacity: 0;
         z-index: 1;
         top: -29px;
         left: 305px;
      }
      -->
   </style>
</head>

<body   topmargin="0" leftmargin="0" rigthmargin="0" marginheight="0" marginwidth="0" bottommargin="0">
   <input type="hidden" name="Idcoc" value="<s:property value='objDD.iIdDocumento'/>"/>
   <input type="hidden" name="Idusuario" value="<s:property  value="#session._USUARIO.idusuario" />"/>
   <table width="100%" topmargin="0" leftmargin="0" rigthmargin="0">
      <tr>
         <td height="14" colspan="3"> </td>
      </tr>
	<tr>	
		<td>	
            <table>
            	<tr>
            		<td></td>
            	</tr>
            </table>
         </td>
      </tr>
      <tr align="center">
         <td width="2%" align="center">&nbsp;</td>
         <td width="98%" colspan="2" align="left">
         </td>
      </tr>
      <tr>
         <td height="400"colspan="6" class="titulo" width="99%" align="left">
            <table width="100%"  height="100%"  cellpadding="0" cellspacing="0" valing="top">
               <!--DWLayoutTable-->
               <tr>
                  <td align="left" width="30%" style="height:auto" border="0" borderColor="#6487d4" valign="top" bgcolor="#ffffff">
                     <s:tree
                        theme="ajax"
                        rootNode="%{objExpedienteTree}"
                        childCollectionProperty="lstExpedienteChildren"

                        nodeIdProperty="iIdExpediente"
                        nodeTitleProperty="sDescripcion"
                        treeSelectedTopic="treeSelected"
                        iconHeight="imprimir.gif" />
                  </td>
                  <td width="80%" valign="top" >
                     <table width="100%" border="0" align="left">
                        <tr>
                           <td align="left" >
                              <div id="displayId"></div>
                           </td>
                        </tr>
                     </table> 
                  </td>
               </tr> 
            </table>
         </td>
      </tr>
   </table>
</body>
</html>
