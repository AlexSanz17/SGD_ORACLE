<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <title>Tramite Documentario</title>
   <link rel="stylesheet" type="text/css" href="css/estilo.css">
   <script language="javascript" src="js/form.js"> </script>
   <script language="Javascript" src="js/general.js"></script>
   <script language="JavaScript" src="js/dtreeCarpeta.js"></script>
   <link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
   <script type="text/javascript" src="js/calendar.js"></script>
   <script type="text/javascript" src="js/calendar-es.js"></script>
   <script type="text/javascript" src="js/calendar-setup.js"></script>

   <script language="JavaScript">
      function Abrir_ventana (pagina) {
         var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=700, top=50, left=200";
         window.open(pagina,"",opciones);
      }

      function Abrir_pagina (pagina) {
         var opciones="location=mainFrame";
         window.open(pagina,"",opciones);
      }

      function regresar(){
         window.close()
      }

      function refrescar() {
         parent.location.href = "inicio.action?sTipoFrame=grid";
      }
   </script>

   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"><style type="text/css">
      <!--
      body {
         background-color: #ffffff;
      }
      -->
   </style>
   <style>
      a, A:link, a:visited, a:active
      {color: #000000;text-decoration: none; font-family: Tahoma, Verdana; font-size: 11px}
      A:hover
      {color: #ff0000; text-decoration: none; font-family: Tahoma, Verdana; font-size: 11px}
   </style>
   <style TYPE="text/css">
      .tipo0{
         padding: 6px;
         position: absolute;
         width: 200px;
         border: 2px solid black;
         background-color: menu;
         font-family: Verdana;
         line-height: 20px;
         cursor: default;
         visibility: hidden;
      }
      .tipo1{
         padding: 6px;
         cursor: default;
         position: absolute;
         width: 165px;
         background-color: Menu;
         color: MenuText;
         border: 0 solid white;
         visibility: hidden;
         border: 2 outset ButtonHighlight;
      }
      a.menuitem {font-size: 0.7em; font-family: Arial, Serif; text-decoration: none;}
      a.menuitem:link {color: #000000; }
      a.menuitem:hover {color: #ffffff; background: #0A246A;}
      a.menuitem:visited {color: #868686;}
   </style>
</head>

<body topmargin="0" leftmargin="0" rigthmargin="0" <s:if test="regresar!=null">onload='refrescar()'</s:if>>
   
</body>
</html>
