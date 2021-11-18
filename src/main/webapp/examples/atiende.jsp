<%@ taglib prefix="s" uri="/struts-tags" %>
<%
   if (true){    
%>    
  <script type="text/javascript">
    this.window.close();  

    if (confirm("Desea atender un documento pendiente con la presente derivación")) {
        var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=650, height=440, top=20, left=70";
        var pagina = "Archivar_inicio1.action?idDocumento=" + <s:property value='iIdDoc' /> + "&tipoArchivar=derivar";
        window.open(pagina, "", opciones);                           
    }      
  </script>  
<%}%>    