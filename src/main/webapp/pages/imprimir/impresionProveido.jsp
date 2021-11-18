<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Proveido</title>
		<link rel="stylesheet" type="text/css" href="css/estilo.css" />
	</head>
	<body style="background:#FFFFFF;">
		<table>
		<tr>
                <td style="width: 1%;"></td>
                <td colspan="4" style="width: 60%;">
                    <s:if test="archivos != null">
                        <table>
                            <tr>
                                <td></td>
                                <td height="16" colspan="5" align="left">Adjuntos:</td>
                            </tr>
                            <tr>
                                <td></td>
                                <td height="16" colspan="5" align="left">
                                    <s:iterator value="archivos">
                                        <s:if test="principal.equals('S')">
                                           <b> <s:property value="nombreReal" /> </b><br />
                                        </s:if>
                                    </s:iterator>

                                     <s:iterator value="archivos">
                                        <s:if test="!principal.equals('S')">
                                          <s:property value="nombreReal" /><br/>
                                        </s:if>
                                    </s:iterator>
                                </td>
                            </tr>
                        </table>
                    </s:if>
                </td>
                <td style="width: 39%;" align="right">
                    <div id="tabla12" style="width: 100%;text-align:left;">
                        <table cellpadding="1" cellspacing="1" class="tableForm">
                            <tr>
                                <td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Tipo Documento
                                </td>
                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                    <s:property value="documento.tipoDocumento.nombre" />
                                </td>
                            </tr>
                            <tr>
                                <td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Nro. Documento
                                </td>
                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                    <s:property value="documento.numeroDocumento" />
                                </td>
                            </tr>
                            <tr>
                                <td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Nro. Expediente
                                </td>
                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                    <s:property value="objDD.strReferencia" />
                                </td>
                            </tr>
                            <tr>
                                <td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Proceso
                                </td>
                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                    <s:property value="objDD.strProceso" />
                                </td>
                            </tr>
                            <s:if test="objDD.strRazonSocial!=null && objDD.codProceso!=@org.ositran.utils.Constantes@CODIGO_COMUNICACIONES_INT">
                                <tr>
                                    <td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Cliente
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                        <s:property value="objDD.strRazonSocial" />
                                    </td>
                                </tr>
                            </s:if>
                            <s:if test="documento.referenciados!=null">
                                <tr>
                                    <td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
									Documentos <br/>Referenciados
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                        <s:property value="documento.referenciados" />
                                    </td>
                                </tr>
                            </s:if>
                            <s:elseif test="objDD.strCorrentista!=null && objDD.codProceso!=@org.ositran.utils.Constantes@CODIGO_COMUNICACIONES_INT">
                                <tr>
                                    <td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Concesionario
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                        <s:property value="objDD.strCorrentista" />
                                    </td>
                                </tr>
                            </s:elseif>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td height="50" colspan="6">
                    <table width="100%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#ffffff">
                        <tr>
                            <td>
                                <table width="100%" height="100" align="center" >
                                    <tr>
                                        <td width="2%"></td>
                                        <td width="1%" height="5"></td>
                                        <td width="1%"></td>
                                        <td width="4%"></td>
                                        <td width="1%"></td>
                                        <td width="91%"></td>
                                    </tr>
                                    <tr>
                                        <td width="2%"></td>
                                        <td height="16" colspan="5" align="left" class="asunto">
                                            <s:textfield name="iIdDoc" cssStyle="display:none" />
                                            <s:textfield name="objDD.cEstado" cssStyle="display:none" />
                                            <s:textfield name="objDD.iIdCliente" cssStyle="display:none" />
                                            <s:property value="objDD.strAsunto" />
                                            <s:textfield name="strAcc" value="aprobar" cssStyle="display:none" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="23" colspan="6"></td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td height="16" colspan="4" align="left" >Para:</td>
                                        <td><s:property value="objDD.strDestinatario" /></td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td height="16" colspan="4" align="left" >Cc: </td>
                                         <td><s:property value="objDD.cadenaCC" /></td>

                                    </tr>
                                    <tr>
                                        <td height="5" colspan="6" ><hr></td>
                                    </tr>
                                    <tr>
                                        <td colspan="6">
                                            <s:if test="objDD.strContenido != null">
                                                <s:hidden id="objDD.strContenido" name="objDD.strContenido" />
                                                <div id="contenido"></div>
                                                <script type="text/javascript">
                                                    document.getElementById("contenido").innerHTML = document.getElementById("objDD.strContenido").value;
                                                </script>
                                            </s:if>
                                            <s:else>
                                                <p>
                                                    Ud. ha registrado un <font class="negrita"><s:property value="objDD.strTipoDocumento" /></font>
                                                    Nro. <font class="negrita"><s:property value="objDD.strNroDocumento" /></font>
                                                    el dia <font class="negrita"><s:date name="objDD.strFecha" format="dd/MM/yyyy" /></font>
                                                    a las <font class="negrita"><s:date name="objDD.strFecha" format="HH:mm" /></font>
                                                    <s:if test='document.expediente.nrointerno != null && document.expediente.nrointerno != " "'>
                                                        se generó el expediente interno <font class="negrita">Nro. <s:property value="documento.expediente.nrointerno"/> </font>,
                                                    </s:if>
                                                    referente al proceso <font class="negrita"><s:property value="objDD.strProceso" /></font>.
                                                </p>
                                                <s:if test="objDD.strRazonSocial!=null && objDD.codProceso!=@org.ositran.utils.Constantes@CODIGO_COMUNICACIONES_INT">
                                                    <p>
                                                        El cliente <font class="negrita"><s:property value="objDD.strRazonSocial" /></font>
                                                        se identifica con <font class="negrita"><s:property value="objDD.strTipoIdentificacion" /></font>:
                                                        <font class="negrita"><s:property value="objDD.strNroIdentificacion" /></font>
                                                        y dirección domiciliaria <font class="negrita"><s:property value="objDD.strDireccionPrincipal" /></font>.
                                                    </p>
                                                </s:if>
                                                <s:elseif test="objDD.strCorrentista!=null">
                                                    <p>
                                                        La entidad <font class="negrita"><s:property value="objDD.strCorrentista" /></font>
                                                        se identifica con <font class="negrita">RUC </font>:
                                                        <font class="negrita"><s:property value="objDD.strRUC" /></font>
                                                        y dirección <font class="negrita"><s:property value="objDD.strDireccionConcesionario" /></font>.
                                                    </p>
                                                </s:elseif>
                                                <s:if test="objDD.observacionDigitalizador!=null">
                                                    <p>
                                                        Observacion Digitalizador: <font class="negrita"><s:property value="objDD.observacionDigitalizador" /></font>.
                                                    </p>
                                                </s:if>
                                            </s:else>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="25" colspan="6" ></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
    	</table>
		<script type="text/javascript">
			window.print();
		</script>
	</body>
</html>