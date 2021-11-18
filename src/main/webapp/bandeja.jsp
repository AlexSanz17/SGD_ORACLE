


<%@page import="com.btg.ositran.siged.domain.Archivo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            List<Archivo> lst= (List<Archivo>)request.getAttribute("lstBandeja");
            
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu</title>
        
         <script type="text/javascript">
         function verDocumento(idFile, objectId, url){
             top['detalle'].location.href= "<%=request.getContextPath()%>/verDocumento.png?idArchivo=" +idFile + "&objectId=" + objectId + "&url=" + url + "&accion=abrirDocumento&fecha=" + new Date();
         }
       </script>
    </head>
    <body>
         <%if (lst!=null && lst.size()>0) {
              boolean banderap = true;
              boolean banderaa = true;
         %>
            <table border="0" width="100%"> 
                    <%for(int i=0;i<lst.size();i++){%>
                          <%if (banderap == true && lst.get(i).getPrincipal()=='S'){%>
			      <tr>
			         <td colspan="4" align="left">
			   	   <b><font color="black" face="arial" size=2px>Principal</font></b>
                                 </td>							
			      </tr>		
                              
                               
                             <%banderap = false;%>
                           <%}%>
                           
                            <%if (banderaa == true && lst.get(i).getPrincipal()!='S'){%>
			      <tr>
			         <td colspan="4" align="left">
			   	   <b><font color="black" face="arial" size=2px>Anexos</font></b>
                                 </td>							
			      </tr>	
                              
                               
                             <%banderaa = false;%>
                           <%}%>
                           
                    
                           <tr>
                                  <td width="3px"></td>
                                   <td width="20px">
                                       <%if (lst.get(i).getNombre().substring(lst.get(i).getNombre().lastIndexOf('.')+1,lst.get(i).getNombre().length()).toUpperCase().equals("PDF")){%> 
                                             <img alt="Documento" src="images/pdf.PNG" />
                                       <%}else{%>
                                             <img alt="Documento" src="images/nuevoDocumento.gif" />
                                       <%}%>
                                   </td>
                                   <td><a href='#' onclick="javascript:verDocumento('<%=lst.get(i).getIdArchivo()%>', '<%=lst.get(i).getObjectId()%>', '<%=lst.get(i).getRutaAlfresco()%>')"  style=”text-decoration:none;”> <font size="2" style="color:#005799"  > <b> <%=lst.get(i).getNombre().substring(lst.get(i).getNombre().indexOf(']') + 1)%></b> </font> </a> </td>
                           </tr>
                    <%}%>
            </table>
         <%}%>   
        
          
    </body>
</html>
