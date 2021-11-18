<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Control de Columnas</title>
<script>
         function retornar(obj,valor)
         {
            window.opener.toggleVis(obj,valor);
         }

      </script>
<script type="text/javascript">
      <%
         String rol=(String)request.getSession().getAttribute("rol");
         String col=(String)request.getSession().getAttribute("columnas");
         List<String> lstCol = (List<String>)request.getSession().getAttribute("lstCol");
         %>
      </script>
<style type="text/css">
<!--
body {
	background-color: #F6FBFF;
	font-family: Arial, sans-serif;
	font-size: 11px;
	font-style: normal;
}
-->
</style>
</head>
<body>

<%
            for(int i=0;i<lstCol.size();i++)
            {
               %>
                  <input type=checkbox name="<%=i+1%>" onclick="retornar(this.name, this.checked)" checked ><%=lstCol.get(i).toString()%> <br>
<%
            }
      %>
<!--
      <s:if test="#session.rol=='mp'">
         <input type=checkbox name="col1" onclick="retornar(this.name, this.checked)" checked >Remitente<br>
         <input type=checkbox name="col2" onclick="retornar(this.name, this.checked)" checked >Asunto<br>
         <input type=checkbox name="col3" onclick="retornar(this.name, this.checked)" checked >Fecha<br>
      </s:if>
      <s:elseif test="#session.rol=='dig'">
         <input type=checkbox name="col1" onclick="retornar(this.name, this.checked)" checked>Remitente<br>
         <input type=checkbox name="col2" onclick="retornar(this.name, this.checked)" checked>Correntista<br>
         <input type=checkbox name="col3" onclick="retornar(this.name, this.checked)" checked>Documento<br>
         <input type=checkbox name="col4" onclick="retornar(this.name, this.checked)" checked>Asunto<br>
         <input type=checkbox name="col5" onclick="retornar(this.name, this.checked)" checked>Fecha<br>
      </s:elseif>
      <s:elseif test="#session.rol=='qas'">
         <input type=checkbox name="col1" onclick="retornar(this.name, this.checked)" checked>Remitente<br>
         <input type=checkbox name="col2" onclick="retornar(this.name, this.checked)" checked>Asunto<br>
         <input type=checkbox name="col3" onclick="retornar(this.name, this.checked)" checked>Fecha<br>
      </s:elseif>
      <s:elseif test="#session.rol=='user'"> 
         <!--<input type=checkbox name="col1" onclick="retornar(this.name, this.checked)" checked><img src="images/bolita.gif" border="0"/>
         <input type=checkbox name="col2" onclick="retornar(this.name, this.checked)" checked><img src="images/alta.bmp" border="0"/> -->
         <input type=checkbox name="col3" onclick="retornar(this.name, this.checked)" checked>Remitente<br>
         <input type=checkbox name="col4" onclick="retornar(this.name, this.checked)" checked>Asunto<br>
         <input type=checkbox name="col5" onclick="retornar(this.name, this.checked)" checked>Fecha<br>
<!--<input type=checkbox name="col6" onclick="retornar(this.name, this.checked)" checked><img src="images/clic.gif"/>-->
<!-- </s:elseif>    -->

      <p>
         <a href="javascript:self.close();">Cerrar Ventana</a>
      </p>

</body>
</html>
