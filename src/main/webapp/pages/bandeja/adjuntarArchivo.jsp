<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
          
            
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
		<script type="text/javascript" src="js/siged/upload.js"></script>
		<script type="text/javascript">
                var i =0;
		 function AA_Adjuntar(tipo) {
                                if(dijit.byId("dlgProgresBar")!=null){
					dijit.byId("dlgProgresBar").show() ;
				}
                                
                                dojo.io.iframe.send({
					handleAs: "text/html",
				    form: dojo.byId("AA_frmUploadFile"),
				    content: {
				    	iIdUpload: 2,
                                        iIdDoc : "<s:property value='iIdDoc' />",
                                        tipo: tipo
				    },
				    handle: function(data) {
                                        dojo.byId("AA_archivos").innerHTML = divPanel(data, tipo);
                                        if(dijit.byId("dlgProgresBar")!=null){
				          dijit.byId("dlgProgresBar").hide() ;
				        }
				      }
				});
			}

			function AA_Registrar(idButton){
                                dijit.byId(idButton).attr("disabled", true);
                                var serviceUpload = new dojo.rpc.JsonService("SMDAction.action");
                                if(dijit.byId("dlgProgresBar")!=null){
				  dijit.byId("dlgProgresBar").show() ;
				}
                                
			        var permiso = serviceUpload.verificarPermiso("<s:property value='iIdDoc' />");

                                permiso.addCallback(function(result) {
                                    if (result !="" ){
                                      dijit.byId("dlgProgresBar").hide() ;  
                                      dijit.byId(idButton).attr("disabled", false);
                                      alert("Usted no puede actualizar el archivo:"  + result + ", porque usted no es el propietario.");
                                      return;
                                    }else{
                                        var enviar =  true;
                                        var camposdelForm =document.getElementById("AA_archivos");
                                        var filesinput = camposdelForm.getElementsByTagName("a") ;
                                        var archivos= filesinput.length;

                                        if(archivos<1){
                                          alert("Es necesario subir al menos un archivo.");
                                          enviar =  false;
                                          dijit.byId("dlgProgresBar").hide() ;
                                          dijit.byId(idButton).attr("disabled", false);
                                          return;
                                        }

                                        if(enviar){
                                           dojo.xhrPost({
                                                url: "doAdjuntarArchivo.action",
                                           content:{
                                                    iIdDoc : "<s:property value='iIdDoc' />"
                                           },
                                           load: function(data) {
                                                if(dijit.byId("contentPaneDetail")){
                                                   if (sTipoGridActual==TIPO_GRID_FIRMAR){
                                                     var sURL="doViewDocFirmado.action?idfirmados="+"<s:property value='iIdDoc' />";
                                                     dijit.byId("contentPaneDetail").setHref(sURL);
                                                   }else{
                                                      if (sTipoGridActual==TIPO_GRID_MI_RECEPCION_VIRTUAL){
                                                         var sURL="doViewDocUsuarioFinalRecepcion.action?iIdDoc="+"<s:property value='iIdDoc' />";
                                                         dijit.byId("contentPaneDetail").setHref(sURL);
                                                      }else{
                                                         if (sTipoGridActual==TIPO_GRID_EXPEDIENTES){ 
                                                            var sURL="doViewDocUsuarioFinal.action?iIdDoc="+"<s:property value='iIdDoc' />" + "&idGrid=6";
                                                            dijit.byId("contentPaneDetail").setHref(sURL);
                                                         }else{
                                                             if (sTipoGridActual==TIPO_GRID_RECIBIDOS){ 
                                                                var sURL="doViewDocUsuarioFinal.action?iIdDoc="+"<s:property value='iIdDoc' />" + "&idGrid=0";
                                                                dijit.byId("contentPaneDetail").setHref(sURL);
                                                             }   
                                                         }    
                                                      }   
                                                   }   
                                                }
                                                if(dijit.byId("contentPaneDatosCargo")){
                                                   dijit.byId("contentPaneDatosCargo").refresh();
                                                }
                                                dijit.byId("dlgProgresBar").hide() ;
                                                dijit.byId("dlgAnexar").hide();
                                                dijit.byId("dlgAnexar").destroyRecursive();
                                            }
                                           });
                                        }
                                    }    
			        });
                        
			}

			var divPanel = function(data, tipo){
                                var dataTemp ="";
				var n = 0;
				n = contador(data);
				dataTemp =  data;
				var error ="";
				var finError = dataTemp.indexOf('<br />');
                                
				if(finError > 0){
				 error += dataTemp.substring(0,finError);
				 error +=' <br />';
				 dataTemp = dataTemp.substring(finError+6);
                                }
                                
				var inicioN = 0;
				var finN = 0;
				var inicioI = 0;
				var finI = 0;
				var nombre ="";
				var divA ="";
				var divI ="";
				var extension ="";
				var inner= "";
				var i = 0;
				var divPanelArchivos = "";
				divPanelArchivos+=error;
                                
				for(i=0; i<n ;i++){
                                        inicioN = dataTemp.indexOf('>');
					finN = dataTemp.indexOf('</a>');
					inicioI = dataTemp.indexOf('<input');
					finI = dataTemp.indexOf('/>');
					nombre = dataTemp.substring(inicioN+1, finN);
					divA = dataTemp.substring(0,finN+4);
					divI = dataTemp.substring(inicioI,finI+2);
					inner= "";
                                        extension = funcExtension(nombre);

                                        if(extension=='pdf'|| extension=='PDF'){
                                              inner = '<tr>' + '<td width="300px" style="font-size: 11px;">'  + divA+ divI+ '</td></tr>';
                                        }else{
                                              inner = '<tr><td width="300px" style="font-size: 11px;">'  + divA+ divI+  '</td></tr>';  
                                        }
                                        
                                        
                                        divPanelArchivos+=inner;
					dataTemp = dataTemp.substring(finI+2);
                                }
                                
                                divPanelArchivos = "<table width='408px' border=1 cellspacing=0 cellpadding=2 bordercolor='#EAEFF5'><tr style='background-color:#E9E9E9'><td style='font-size: 11px;'><strong>Archivo</strong></td>" + divPanelArchivos + "</table>";
                                
                               return divPanelArchivos;
			};

			var contador = function(String){
                            var i = 0;
                            var counter = 0;
                            while (i != -1)
                            {
                            var i = String.indexOf('</a>',i);
                            if (i != -1)
                            {
                            i++;
                            counter++;
                            }
                            }
                            return counter;
			};


			var divId = function (textDivCadena) {
				var textDiv = "";
				textDiv = textDivCadena;
				var inicio = textDiv.lastIndexOf('id=');
				var nuevaCadena = textDivCadena.substring(inicio + 4, textDivCadena.length);
				var fin = nuevaCadena.indexOf('"');
				myId = nuevaCadena.substring(0, fin);
				console.debug("estamos contruyendo un Id :"+myId);
				return myId;
			};

			var nombreArchivo= function(textDivCadena) {
				var textDiv = "";
				textDiv = textDivCadena;

				var fin = textDiv.lastIndexOf('</a>');
				var inicio = textDiv.lastIndexOf('>',fin);
				nombre = textDiv.substring(inicio+1, fin);
				console.debug("estamos contruyendo nombre :"+nombre);
				return nombre;
			};

			var funcExtension=function(miString){
                            var extensionTemp = "";
                            var extension= "";
                            
                            for (i=miString.length-1;i+1>0;i--) {
                                if(miString.charAt(i)=='.'){
                                    break;
                                }
                                extensionTemp +=miString.charAt(i);
                            }
                            
                            for (i=extensionTemp.length-1;i+1>0;i--) {
                                extension +=extensionTemp.charAt(i);
                            }
                            
                            return extension;
			};
		</script>
	</head>
	<body>
              <table style="width:100%; height:125px;" border="0" align="center" >
			<tr>
				<td style="text-align:left;" colspan="2">
                                     
					        <s:form id = "AA_frmUploadFile" action="doUploadFile" method="POST" enctype="multipart/form-data">
                                                    <table  border="1" bordercolor="#669BCD" bgcolor="#ffffff">
                                                       <s:if test="#session._USUARIO.idRolPerfil != @org.ositran.utils.Constantes@COD_ROL_MENSAJERIA">   
                                                          <s:if test="proyecto != @org.ositran.utils.Constantes@DOCUMENTO_PROYECTO || (proyecto == @org.ositran.utils.Constantes@DOCUMENTO_PROYECTO && destinatarioIgualRemitente == false)">   
                                                              <s:if test="codigoVirtual==null">
                                                                    <tr>
                                                                         <td width="8px"></td>
                                                                         <td width="165px" align="left"> 
                                                                             <input type="file" class="file"  name="uploadPrincipal" multiple="multiple" style="display:none;" id="aa_fchooser_principal" onChange="AA_Adjuntar('P'); "  onclick="this.value=''">
                                                                             <input type="button" value=" &nbsp;Subir Archivo Principal&nbsp; " onclick="document.getElementById('aa_fchooser_principal').click();" style="">
                                                                         </td>

                                                                         <td align="right" width="400px">

                                                                         </td>    
                                                                     </tr>
                                                              </s:if>     
                                                                <tr>
                                                                    <td width="8px"></td>
                                                                    <td width=165px" align="left">
                                                                        <input type="file" class="file"  name="upload"  style="display:none;" id="aa_fchooser" onChange="AA_Adjuntar('A'); "  onclick="this.value=''">
                                                                        <input type="button" value=" &nbsp;Subir Archivo Anexo&nbsp; " onclick="document.getElementById('aa_fchooser').click();" style="">
                                                                    </td>
                                                                     <td align="right" width="400px">

                                                                         </td>  
                                                                </tr>
                                                           </s:if>     
                                                         
                                                       </s:if>   
                                                       <s:if test="#session._USUARIO.idRolPerfil == @org.ositran.utils.Constantes@COD_ROL_MENSAJERIA || #session._USUARIO.permisoCargo=='1'">   
                                                            <tr>
                                                                <td width="8px"></td>
                                                                <td width="165px" align="left"> 
                                                                    <input type="file" class="file"  name="uploadCargo" multiple="multiple" style="display:none;" id="aa_fchooser_cargo" onChange="AA_Adjuntar('C'); "  onclick="this.value=''">
                                                                    <input type="button" value=" &nbsp;Subir Archivo Cargo&nbsp; " onclick="document.getElementById('aa_fchooser_cargo').click();" style="">
                                                                </td>
                                                                 <td align="right" width="400px">

                                                                </td>  
                                                            </tr>
                                                        </s:if>       
                                                     </table>       
                                                 </s:form>
                                                <!-- 
						<input id = "aa_fchooser" name="upload" type="file"  class="file" onChange="AA_Adjuntar(); "  onclick="this.value=''"/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->
						
                                     
				</td>
                                
			</tr>
                     
			<tr>
				<td colspan="2" >
					<div id="AA_archivos" style="height:135px; overflow: auto;"></div>
				</td>
			</tr>
              </table>
              <table border="0" style="width:100%;">
                        <tr> <td colspan="4" style="text-align:center;" align="center"> <div dojoType="dijit.form.Button"
                                                                                iconClass="siged16 sigedSearch16"
                                                                                onClick="AA_Registrar(this.id)"
                                                                                showLabel="true">Registrar
                                                                            </div></td></tr>
                        
		</table>
            
	</body>
</html>