<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<textarea>
	<s:if test="mensaje!=null"> 
	<s:property value="mensaje" />	
	</s:if>                
 <br /> 
<s:if test="#session._UPLOADLIST.upload1 != null && iIdUpload == 1">
    <s:iterator value="#session._UPLOADLIST.upload1">
      <s:url id="lnkFile" action="doDownloadFileTempo" includeParams="get">
         <s:param name="iIdUpload">1</s:param>
         <s:param name="sNombre"><s:property value="fArchivo.name" /></s:param>
      </s:url>
      <a href="${lnkFile}" target="_blank"><s:property value="sNombre" /></a>
      <input type="image" alt="Eliminar" id="<s:property value='fArchivo.name' />" src="images/eliminar.gif" onclick="deleteIt(1,this.id)" />
      <br />
   </s:iterator>

    
</s:if>
<s:if test="#session._UPLOADLIST.upload2 != null  && iIdUpload == 2">
   <s:iterator value="#session._UPLOADLIST.upload2">
      <s:url id="lnkFile" action="doDownloadFileTempo" includeParams="get">
         <s:param name="iIdUpload">2</s:param>
         <s:param name="sNombre">${fArchivo.name}</s:param>
      </s:url>
      <a href="${lnkFile}" target="_blank"><s:property value="sNombre" /></a>
      <input type="image" alt="Eliminar" id="<s:property value='fArchivo.name' />" src="images/eliminar.gif" onclick="deleteIt(2,this.id)" />
      <br />
   </s:iterator>
</s:if>
<s:if test="#session._UPLOADLIST.upload3 != null  && iIdUpload == 3">
   <s:iterator value="#session._UPLOADLIST.upload3">
      <s:url id="lnkFile" action="doDownloadFileTempo" includeParams="get">
         <s:param name="iIdUpload">3</s:param>
         <s:param name="sNombre"><s:property value="sNombre" /></s:param>
      </s:url>
      <a href="${lnkFile}" target="_blank"><s:property value="sNombre" /></a>
      <input type="image" alt="Eliminar" id="<s:property value='fArchivo.name' />" src="images/eliminar.gif" onclick="deleteIt(3,this.id)" />
      <br />
   </s:iterator>
</s:if>
</textarea>
