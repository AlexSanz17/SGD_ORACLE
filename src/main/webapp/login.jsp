<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">



<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
		<title>Bienvenido </title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />


 <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" >


		<link href="css/estilo1.css" rel="stylesheet" type="text/css" />
		<link type="image/x-icon" href="favicon.ico" rel="shortcut icon"/>
		<%--script type="text/javascript" src="js/js_intranet.js"></script--%>
		<script type="text/javascript" src="js/jquery.js"></script>
               
		<script type="text/javascript">
			/*$(document).ready(function(){
				$("#usuario").blur(function(){
					var usuario=$(this).val();
					/*$.getJSON("getRolesPorUsuario.action","sUsuario="+usuario,
							function(data){
								var select=$("#rol");
								select.empty();
								$.each(data.items,function(i,item){
									var option="<option value=\""+item.id+"\">"+item.label+"</option>";
									select.append(option);
								});
							});*/
			//	});
		//	});*/
		</script> 

		<script type="text/javascript">





         function init(){
                 <% try{
			//session.invalidate();
	            }catch(Exception e){
		        e.printStackTrace();
		    }
		 %>
                         
                 var tabID = sessionStorage.tabID ? sessionStorage.tabID  : sessionStorage.tabID = Math.random(); 
                 document.getElementById("storage").value = sessionStorage.tabID;
                 
                 
                 <s:if test="#session._USUARIO!= null">
                   //document.location.href="doLoginSession.action";
                   //location.href ="login.action?sTipoFrame=grid"
                   location.href="activa.html";
                 </s:if>    
                /* var padre = parent.frames.length ;
                 if(padre>0){
                      parent.location.href ="login.action?" + Math.random();
                 }else{
                 }*/
      
                 
               
             }   
               

        /* function Inicio(){
            location.href="inicio.html";
         }

         function selectChange() {

            if (value = "1") {
               location.href = "inicioMP.html";
            }
            if (value = "2") {
               location.href = "inicioSSS.html";
            }
            if (value = "3") {
               location.href = "inicioDDD.html";
            }
            if (value = "4") {
               location.href = "inicioFFF.html";
            }
            if (value = "5") {
               location.href = "inicio.html";
            }

         }*/

         </script>
	 <style type="text/css">
               

                /*.theme-company-logo
                {
                   height: 133px;
                   width: 204px;
                   background: transparent url(images/logo.jpg) no-repeat;
                }*/
   
                    
         <!--
         .Estilo8 {
            color: #FFFFFF;
            font-weight: bold;
            font-size: large;
         }
         body {
           // background-color: #c1dffd;
             height: 100%;
             width: 100%;
             background: transparent url(images/presentacion-final.jpg) no-repeat;
             background-size: 80% 100%;
         }
         .Estilo15 {color: #FFFFFF; font-weight: bold; font-size: 11px; }
         .Estilo16 {color: #000000}
         -->
	</style>
	</head>

                 <body onload="init();" style="height: 100%;">

           
		<table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-top:14.5%; margin-bottom: 21.5%;">
                    <tr style="height: 100%;">
				<td align="center" valign="middle">
					<table width="400px" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td valign="top">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td></td>
										<td></td>
										<td style="width:18px"></td>
									</tr>
									<tr>
										<td style="height:203px"></td>
										<td>
                                                                                    <table border="1" width="100%" cellpadding="0" cellspacing="0"<%--bordercolor="#FFFFFF"--%> align="center" style="background-color: #FFFFFF;">
												<tr>
													<td></td>
												</tr>
												<tr>
													<td align="center" valign="middle" height="100px">
														<s:form action="doLogin" method="post">

															<table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                                                                                                <tr style="height:20px;">
                                                                                                                                    <td colspan="5"></td>
                                                                                                                                </tr>
																<tr>
																	<td></td>
                                                                                                                                        <td colspan="3" class="Estilo15 Estilo16"  align="center">
																		<!--<div align="center"> --> 
                                                                                                                                                <h1>	Sistema de Gesti&oacute;n Documentaria </h1>
																		<!--</div> -->
																	</td>
																	<td width="2%" height="10px" >&nbsp;</td>
																</tr>
																<tr>
																	<td width="5%" >&nbsp;</td>
																	<td width="57%">&nbsp;</td>
																	<td width="16%">&nbsp;</td>
																	<td width="2%">&nbsp;</td>
																</tr>
															</table>
															<table width="100%" height="145" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td width="11%" height="38" align="center"></td>
																	<td align="right" class="Estilo15 Estilo16">Usuario:&nbsp;</td>
																	<td width="33%" align="center">
																		<input id="usuario" class="cajaMontoTotal1" name="sUsuario" type="text" size="20px" maxlength="25" />
                                                                                                                                                <input id="storage"  name="storage" type="hidden" size="20px" maxlength="15" />
																	</td>
																	<td width="31%" height="38"></td>
																</tr>
																<tr>
																	<td width="11%">&nbsp;</td>
																	<td width="25%" align="right" class="Estilo15 Estilo16">Contrase&ntilde;a:&nbsp;</td>
																	<td  align="center">
																		<input class="cajaMontoTotal1" name="sClave" type="password" size="20px" maxlength="25"/>


																	</td>
																	<td width="31%" height="18"></td>
																</tr>





																<tr>
																	<td width="11%" height="55"></td>
																	<td width="25%" align="right"></td>
																	<td width="33%">
																		<input type="submit" class="button" value="Aceptar" />
																		<input type="reset" class="button" onclick="javascript:window.close()" value="Cancelar" />
																	</td>
																	<td width="31%" align="right"></td>
																</tr>

																<tr>
																	<td width="11%" height="20px"></td>
																	<td width="25%"><s:actionerror /></td>
																	<td width="33%"><s:fielderror /></td>
																	<td width="31%">&nbsp;</td>
																</tr>

															</table>

														</s:form>
													</td>
												</tr>
											</table>
										</td>
										<td>&nbsp;</td>
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