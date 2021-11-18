<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
   <script type="text/javascript">
    
	
   </script>
</head>
<body >
 
<s:if test="mensaje!=null">    
    <div style="color:red;font-weight: bold;">  <s:property value="mensaje" /> </div>
   
</s:if>

<s:if test="#session._UPLOADLIST.upload1 != null && iIdUpload == 1">    
    <s:iterator value="#session._UPLOADLIST.upload1">
        <s:url id="lnkFile" action="doDownloadFileTempo" includeParams="get" escapeAmp="false">
            <s:param name="iIdUpload">1</s:param>
            <s:param name="sNombre"><s:property value="fArchivo.name" /></s:param>
        </s:url>
        <a href="<s:property value='lnkFile' />" target="_blank"><s:property value="sNombre" /></a>
        <input type="image" alt="Eliminar" id="<s:property value='fArchivo.name' />" src="images/eliminar.gif" onclick="deleteIt(1,this.id)" />
        <br />
    </s:iterator>    
</s:if>

<s:if test="#session._UPLOADLIST.upload2 != null  && iIdUpload == 2">
    <s:iterator value="#session._UPLOADLIST.upload2">
        <%--
        <s:url id="lnkFile" action="doDownloadFileTempo" includeParams="get">
		<s:param name="iIdUpload">2</s:param>
		<s:param name="sNombre"><s:property value='fArchivo.name'/></s:param>
	</s:url>
	<a href="<s:property value='lnkFile' />" target="_blank"><s:property value="sNombre" /></a>
        --%>
        <a href="doDownloadFileTempo.action?iIdUpload=2&sNombre=<s:property value="fArchivo.name"/>"><s:property value="sNombre" /></a>
        <input type="image" alt="Eliminar" id="<s:property value='fArchivo.name' />" src="images/eliminar.gif" onclick="deleteIt(2,this.id)" />
        <br />
    </s:iterator>
</s:if>
<s:if test="#session._UPLOADLIST.upload3 != null  && iIdUpload == 3">
    <s:iterator value="#session._UPLOADLIST.upload3">
        <s:url id="lnkFile" action="doDownloadFileTempo" includeParams="get">
            <s:param name="iIdUpload">3</s:param>
            <s:param name="sNombre"><s:property value="fArchivo.name" /></s:param>
        </s:url>
        <a href="<s:property value='lnkFile' />" target="_blank"><s:property value="sNombre" /></a>
        <input type="image" alt="Eliminar" id="<s:property value='fArchivo.name' />" src="images/eliminar.gif" onclick="deleteIt(3,this.id)" />
        <br />
    </s:iterator>
</s:if>
<s:if test="#session._UPLOADLIST.upload4 != null  && iIdUpload == 4">
    <s:iterator value="#session._UPLOADLIST.upload4">
        <s:url id="lnkFile" action="doDownloadFileTempo" includeParams="get">
            <s:param name="iIdUpload">4</s:param>
            <s:param name="sNombre"><s:property value="fArchivo.name" /></s:param>
        </s:url>
        <a href="<s:property value='lnkFile' />" target="_blank"><s:property value="sNombre" /></a>
        <input type="image" alt="Eliminar" id="<s:property value='fArchivo.name' />" src="images/eliminar.gif" onclick="deleteIt(4,this.id)" />
        <br />
    </s:iterator>
</s:if>


</body>
</html>