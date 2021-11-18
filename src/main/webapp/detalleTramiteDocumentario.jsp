<!-- REN detalle del documento que va debajo de la grilla, se carga cuando se presiona una fila de la grilla ------------------->

<?xml version="1.0" encoding="UTF-8"?>
<%@page import="com.btg.ositran.siged.domain.TramiteDocumentario"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>







<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
        <title>Detalle del Documento</title>

        <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" >

        <% String nro = request.getAttribute("nroConsultasTD")==null? "": "Número de Consultas: " + (String)request.getAttribute("nroConsultasTD"); %>



		 <script>


		 function cleanFields() {
			 document.forms["frmFiltrarTramite"]["nroTramitedocumentario"].value = "";

		 }

            function SearchTramiteDocumentarioSubmit() {
            	valorsito = document.forms["frmFiltrarTramite"]["nroTramitedocumentario"].value;
            	if (valorsito == ''){
            		alert("Debe ingresar un N° de Trámite. Por favor Verificar");
            		return
            	}

                document.forms["frmFiltrarTramite"].submit();
            }

            function soloNumeros(e){
            	var key = window.Event ? e.which : e.keyCode
            	return (key >= 48 && key <= 57)
            }

         </script>

    </head>
    <body class="soria">
       <form id="frmFiltrarTramite" action="doViewTramiteDocumentario.action" method="post">


        <table style="width:100%;border:1px solid #830713;border-collapse:collapse" >
              <tr>
		            <td height="20px" width="100%">
		               <table width="100%" border="0" height="60px" align="left">
		                  <tr>
		                     <td bgcolor="#830713" width="100%" align="center" style="color:white;font-size:25px;"><b>Consulta de Trámite Documentario</b></td>
		                  </tr>
		               </table>
		            </td>
              </tr>

	         <tr>
	            <td>

	                     <table width="100%" border="0">
	                           <tr>
			                   	     <td class="filaR3" width="200px"><b></b></td>
									 <td class="filaR3" align="center"><b>N° de Ticket:</b>&nbsp;<input type="text" name = "nroTramitedocumentario" id="nTramitedocumentario" size="20" maxlength="6" onKeyPress="return soloNumeros(event);"/></td>
									  <td class="filaR3" width="200px" align="right"><b><%=nro%></b></td>


							   </tr>

							     <tr>
							         <td>

									</td>
                                </tr>

								<tr>
								    <td></td>

					                <td colspan="1" align="center">
					                        <img src="images/buscar.bmp" border="0" onClick="SearchTramiteDocumentarioSubmit()" alt="Buscar" style="cursor:pointer" />
					                        <img src="images/Limpiar.bmp" border="0" onClick="cleanFields()" alt="Limpiar" style="cursor:pointer" />
					                </td>
 									 <td class="filaR3" width="100px" ></td>
					            </tr>

                          </table>

		        </td>
	         </tr>



		 </table>

		</form>

    </body>
</html>