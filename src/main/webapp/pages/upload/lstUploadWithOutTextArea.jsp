<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<s:if test="#session._UPLOADLIST.upload1 != null && iIdUpload == 1">
   
	<s:iterator value="#session._UPLOADLIST.upload1">
	<s:url id="lnkFile" action="doDownloadFileTempo" includeParams="get">
		<s:param name="iIdUpload">1</s:param>
		<s:param name="sNombre"><s:property value="fArchivo.name" /></s:param>
	</s:url>
	<a href="<s:property value='lnkFile' />" target="_blank"><s:property value="sNombre" /></a>
	<input type="image" alt="Eliminar" id="<s:property value='fArchivo.name' />" src="images/eliminar.gif" onclick="deleteIt(1,this.id)" />
	<br />
	</s:iterator>
</s:if>
<s:elseif test="#session._UPLOADLIST.upload2 != null && iIdUpload == 2">
        
	<s:iterator value="#session._UPLOADLIST.upload2"> 
           <s:if test='principal=="P"'>  
                <s:url id="lnkFile" action="doDownloadFileTempo" includeParams="get"/>
                <a href="<s:property value='lnkFile' />&iIdUpload=2&sNombre=<s:property value="fArchivo.name" />" target="_blank" style="color:<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_PRINCIPAL"/>;font-weight:bold"><s:property value="sNombre" /></a>
                <input type="image" alt="Eliminar" id="<s:property value='fArchivo.name' />" src="images/eliminar.gif" onclick="deleteIt(2,this.id)" />
                <br />
           </s:if>
           
           <s:if test='principal=="C"'>  
                <s:url id="lnkFile" action="doDownloadFileTempo" includeParams="get"/>
                <a href="<s:property value='lnkFile' />&iIdUpload=2&sNombre=<s:property value="fArchivo.name" />" target="_blank" style="color:<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_CARGO"/>;font-weight:bold"><s:property value="sNombre" /></a>
                <input type="image" alt="Eliminar" id="<s:property value='fArchivo.name' />" src="images/eliminar.gif" onclick="deleteIt(2,this.id)" />
                <br />
           </s:if>     
                
           <s:if test='principal=="A"'>
               <s:url id="lnkFile" action="doDownloadFileTempo" includeParams="get"/>
                <a href="<s:property value='lnkFile' />&iIdUpload=2&sNombre=<s:property value="fArchivo.name" />" target="_blank" style="color:<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_ANEXO"/>;font-weight:bold"><s:property value="sNombre" /></a>
                <input type="image" alt="Eliminar" id="<s:property value='fArchivo.name' />" src="images/eliminar.gif" onclick="deleteIt(2,this.id)" />
                <br />
           </s:if>
                
           <s:if test='principal=="Y"'>  
                <s:url id="lnkFile" action="doDownloadFileTempo" includeParams="get"/>
                <a href="<s:property value='lnkFile' />&iIdUpload=2&sNombre=<s:property value="fArchivo.name" />" target="_blank"><s:property value="sNombre" /></a>
                <input type="image" alt="Eliminar" id="<s:property value='fArchivo.name' />" src="images/eliminar.gif" onclick="deleteIt(2,this.id)" />
                <br />
           </s:if>       
                
            <s:if test='principal=="M"'>
               <s:url id="lnkFile" action="doDownloadFileTempo" includeParams="get"/>
                <a href="<s:property value='lnkFile' />&iIdUpload=2&sNombre=<s:property value="fArchivo.name" />" target="_blank" style="color:<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_MP_CARGO"/>;font-weight:bold"><s:property value="sNombre" /></a>
                <input type="image" alt="Eliminar" id="<s:property value='fArchivo.name' />" src="images/eliminar.gif" onclick="deleteIt(2,this.id)" />
                <br />
           </s:if>       
           
	</s:iterator>
</s:elseif>