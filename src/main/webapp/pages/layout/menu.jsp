<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.btg.ositran.siged.domain.FilaBandejaUF" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sistema de Gesti&oacute;n Documentaria - Men&uacute; de Opciones</title>
        <%     
         String contDocumentosRecibidos = (String)request.getAttribute("contDocumentosRecibidos");
        //ArrayList listDocumentosRecibidos = (ArrayList)request.getAttribute("listDocumentosRecibidos");
        //String contDocumentosRecibidos = String.valueOf(listDocumentosRecibidos.size()); 
         %>
        <script type="text/javascript">
         
        <s:if test="#session._RECURSO.UsuFinVisorGerencial">
        interfazGerente = true; 
        </s:if>
        var arrRecursoMenu = new Array();
  
          arrRecursoMenu[0] = {codigo:"MPMnuDocReg", valor:<s:property value="#session._RECURSO.MPMnuDocReg"/>};
          arrRecursoMenu[1] = {codigo:"UsuFinMnuNotif", valor:<s:property value="#session._RECURSO.UsuFinMnuNotif"/>};
          arrRecursoMenu[2] = {codigo:"UsuFinMnuInfo", valor:<s:property value="#session._RECURSO.UsuFinMnuInfo"/>};
          arrRecursoMenu[3] = {codigo:"QASMnuDigitalizados", valor:<s:property value="#session._RECURSO.QASMnuDigitalizados"/>};
          arrRecursoMenu[4] = {codigo:"DigMnuDocIng", valor:<s:property value="#session._RECURSO.DigMnuDocIng"/>};
          
          function accion(id){
                 
                 for(var i = 1; i<=31; i++){
                    if(i != id && dojo.byId("filamenu"+i)!=null){
                      dojo.byId("filamenu"+i).setAttribute("class", "filamenu");
        	    }
        	  }
                  
                  dojo.byId("filamenu"+id).setAttribute("class", "filamenusel");
                   
                  switch (id){
                        
        	  	case 1 :   
                                showGridInbox(TIPO_GRID_RECIBIDOS);
        	  		break;
        	  	case 2:
        	  		showGridInbox(TIPO_GRID_EXPEDIENTES);
        	  		break;
        	  	case 3:
        	  		showGridInbox(TIPO_GRID_DOCUMENTOS_ARCHIVADOS);
        	  		break;
        	  	case 4:
        	  		showGridInbox(TIPO_GRID_INFORMATIVOS);
        	  		break;
			case 5:
                                showGridInbox(TIPO_GRID_PENDIENTES);
				break;
			case 6:
				showGridInbox(TIPO_GRID_ENVIADOS);
				break;
			case 7:
				showGridInbox(TIPO_GRID_MENSAJERIA);
				break;
			case 8:
				showGridInbox(TIPO_GRID_NOTIFICACIONES);
				break;
			case 9:
				showGridInbox(TIPO_GRID_PROCESOS);
				break;
			case 10:
				buildTabsFromToolBarTop('UsuFinMnuAsiRem',null);
				break;
			case 11:
				showGridInbox(TIPO_GRID_REGISTRADOS_MP);
				break;
			case 12:
				buildTabsFromToolBarTop('UsuFinMnuLista',null);
				break;
			case 13:
				buildTabsFromToolBarTop('UsuFinMnuMantRee',null);
				break;
			case 14:
				buildTabsFromToolBarTop('UsuFinReportes',null);
				break;
			case 15:
				buildTabsFromToolBarTop('UsuFinConsulta3',null);
				break;
          		case 16:
          			buildTabsFromToolBarTop('UsuFinConsulta4',null);
				break;
			case 17:
				showGridInbox(TIPO_GRID_QAS_DIGITALIZADOS);
				break;
			case 18:
				showGridInbox(TIPO_GRID_DIG_DOC_INGRESADOS);
				break;
			case 19:
				showGridInbox(TIPO_GRID_MENSAJERIA_RECIBIDOS);
				break;
			case 20:
				showGridInbox(TIPO_GRID_MENSAJERIA_CERRADOS);
				break;
          		case 21:
				showGridInbox(TIPO_GRID_SEGUIMIENTO);
				break;
                         case 22:
				showGridInbox(TIPO_GRID_ANULADOS);
				break;         
                        case 23:
                                showGridInbox(TIPO_GRID_FIRMAR);
                                break; 
                        case 24:
                                showGridInbox(TIPO_GRID_RECEPCION_VIRTUAL);
				break; 
                                
                        case 25:
                                showGridInbox(TIPO_GRID_DESPACHO_VIRTUAL);
				break;    
                            
                        case 26:
                                showGridInbox(TIPO_GRID_MI_RECEPCION_VIRTUAL);
				break;        
                            
                        case 27:
			        showGridInbox(TIPO_GRID_MI_LEGAJO);
				break; 
                        
                        case 28:
			        showGridInbox(TIPO_GRID_LEGAJO_COMPARTIDO);
				break; 
                        
          		case 29:
          			document.location="http://www.google.com.pe";
          			break;
                                
                        case 30:
                                buildTabsFromToolBarTop('ReporteVista', null);
                                break;
                                
                        case 31:
                                showGridInbox(TIPO_GRID_RECEPCION_VIRTUAL_OBSERVADOS);
                                break;        
		     }
                               
		}
		</script>
        <style type="text/css">
        	table.menu{
        		width:100%;
        		font-family:sans-serif;
        		font-size:1.1em;
        	}
        	td.filamenu{
        		padding-left:1em;
        		padding-top: 0.3em;
        		padding-bottom: 0.3em;
        		cursor: pointer;
        		background-color:#FFFFFF;
        	}
        	td.filamenu:HOVER{
        		background-color:#D4E3FB;
        		color:#0066D1;
        	}
        	td.filamenu:FOCUS{
        		background-color:#D4E3FB;
        	}
        	td.filamenusel{
        		padding-left:1em;
        		padding-top: 0.3em;
        		padding-bottom: 0.3em;
        		background-color:#94B9EF;
        	}
        </style>
        <script type="text/javascript" src="js/siged/layout/menu.js"></script>
    </head>
    <body>
        <div dojoType="dijit.layout.AccordionContainer"
             id="rightAccordion"
             style="width:100%;height:43%;">
             
            <div dojoType="dojox.layout.ContentPane" title="Documentos" style="height:40%;">
            	<table class="menu">
            	
            		<s:if test="#session._RECURSO.UsuFinMnuDocRec">
                            <tr>
                                    <td id="filamenu1" name="filamenu" class="filamenusel" onClick="accion(1)">
                                         Recibidos 
                                            <b><span id="UsuFinMnuDocRec"></span></b>
                                    </td>
                            </tr>
            		</s:if>
                        
                        <s:if test="!#session._RECURSO.UsuFinMnuRecVirtual">    
                            <s:if test="#session._RECURSO.UsuFinMnuInfo">
                                <tr>
                                    <td id="filamenu23" name="filamenu" class="filamenu" onClick="accion(23)">
                                        Para Firmar
                                         <b><span id="UsuFinMnuFirma"></span></b>
                                    </td>
                                </tr>
                            </s:if>
                         </s:if>
                                
                         <s:if test="#session._RECURSO.UsuFinMnuInfo">
                            <tr>
                                <td id="filamenu4" name="filamenu" class="filamenu" onClick="accion(4)">
                                    Con Copia
                                    <b><span id="UsuFinMnuInfo"></span></b>
                                </td>
                            </tr>
                         </s:if>    
            		
            		<s:if test="#session._RECURSO.UsuFinMnuMisExp">
                            <tr>
                                    <td id="filamenu2" name="filamenu" class="filamenu" onClick="accion(2)">
                                            Mis Documentos
                                    <b><span id="UsuFinMnuMisExp"></span></b>
                                    </td>
                            </tr>

            		</s:if>
            		
            		<s:if test="#session._RECURSO.UsuFinMnuDocEnv">
                            <tr>
                                <td id="filamenu6" name="filamenu" class="filamenu" onClick="accion(6)">
                                         Enviados
                                </td>
                            </tr>
                         </s:if>
                    
                        <s:if test="#session._RECURSO.UsuFinMnuDocPen && #session._USUARIO.idRolPerfil==4">
                            <tr>
                                <td id="filamenu5" name="filamenu" class="filamenu" onClick="accion(5)">
                                        Pendientes del Area
                                </td>
                            </tr>
                       </s:if>
                    

                        <s:if test="#session._RECURSO.UsuFinMnuDocRec">
                        <tr>
                            <td id="filamenu3" name="filamenu" class="filamenu" onClick="accion(3)">
                                <s:if test="#session._RECURSO.UsuFinMnuDocPen && #session._USUARIO.idRolPerfil==4">
                                    Atendidos del Area
                                </s:if>
                                <s:else>
                                    Atendidos
                                </s:else>    
                            </td>
                        </tr>
                        </s:if>
                    
                    <s:if test="#session._RECURSO.menSeguimiento">
                    <tr>
                        <td id="filamenu21" name="filamenu" class="filamenu" onClick="accion(21)">
                        	Seguimiento
                        </td>
                    </tr>
                    </s:if>

                     <s:if test="#session._RECURSO.UsuFinMnuDocAnu">
                    <tr>
                        <td id="filamenu22" name="filamenu" class="filamenu" onClick="accion(22)">
                        	Anulados
                        </td>
                    </tr>
                    </s:if>
                    
                    <s:if test="#session._RECURSO.UsuFinBtnBus">
                    <tr>
                        <td id="filamenu30" name="filamenu" class="filamenu" onClick="accion(30)">
                             Reportes
                        </td>
                    </tr>
                    </s:if>  
                    
                    <s:if test="#session._RECURSO.UsuFinMnuDocMsg">
                    <tr>
                        <td id="filamenu7" name="filamenu" class="filamenu" onClick="accion(7)">
                        	 Mensajer&iacute;a
                        </td>
                    </tr>
                    </s:if>
                    
                    <s:if test="#session._RECURSO.UsuFinMnuNotif">
                    <tr>
                        <td id="filamenu8" name="filamenu" class="filamenu" onClick="accion(8)">
                            Notificaciones
                            <b><span id="UsuFinMnuNotif"></span></b>
                        </td>
                    </tr>
                    </s:if>
                    
                    <s:if test="#session._RECURSO.UsuFinMnuMisPro">
                    <tr>
                        <td id="filamenu9" name="filamenu" class="filamenu" onClick="accion(9)">
                        	Procesos
                        </td>
                    </tr>
                    </s:if>
                    
                    <s:if test="#session._RECURSO.UsuFinMnuAsiRem">
                    <tr>
                        <td id="filamenu10" name="filamenu" class="filamenu" onClick="accion(10)">
                        	 Mis Reemplazos
                        </td>
                    </tr>
                    </s:if>
                    
                    <s:if test="#session._RECURSO.MPMnuDocReg">
                    <tr>
                        <td id="filamenu11" name="filamenu" class="filamenu" onClick="accion(11)">
                            <strong>&bull;</strong> Registrados
                            <b><span id="MPMnuDocReg"></span></b>
                        </td>
                    </tr>
                    </s:if>
                    
                    <s:if test="#session._RECURSO.UsuFinMnuLista">
                    <tr>
                        <td id="filamenu12" name="filamenu" class="filamenu" onClick="accion(12)">
                        	<strong>&bull;</strong> Listas
                        </td>
                    </tr>
                    </s:if>
            
                    
                    <s:if test="#session._RECURSO.DigMnuDocIng">
                    <tr>
                        <td id="filamenu18" name="filamenu" class="filamenu" onClick="accion(18)">
                            <strong>&bull;</strong> Doc Ingresados
                            <b><span id="DigMnuDocIng"></span></b>
                        </td>
                    </tr>
                    </s:if>
                    
                    <s:if test="#session._RECURSO.MensMnuDocRec">
                    <tr>
                        <td id="filamenu19" name="filamenu" class="filamenu" onClick="accion(19)">
                            <strong>&bull;</strong> Doc. Recibidos
                            <b><span id="MensMnuDocRec"></span></b>
                        </td>
                    </tr>
                    </s:if>
                  
            	</table>
            </div>
            
            <s:if test="#session._RECURSO.UsuFinMnuRecVirtual">    
                <div dojoType="dojox.layout.ContentPane" title="Trámite Virtual" style="height:60%;">
                   <table class="menu">         
                     <s:if test="#session._RECURSO.UsuFinMnuRecVirtual">  
                       <tr>
                             <td id="filamenu24" name="filamenu" class="filamenu" onClick="accion(24)">
                                 Recepción 
                                 <b><span id="UsuFinMnuRecVirtual"></span></b>
                              </td>   
                         </tr>
                         <tr>
                            <td id="filamenu23" name="filamenu" class="filamenu" onClick="accion(23)">
                                Para Firmar
                                 <b><span id="UsuFinMnuFirma"></span></b>
                            </td>
                          </tr>
                          
                           <tr>
                            <td id="filamenu31" name="filamenu" class="filamenu" onClick="accion(31)">
                                 Observados
                                 <b><span id="UsuFinMnuRecObs"></span></b>
                            </td>
                          </tr>
                          
                     </s:if>        
                   </table>
                </div>    
             </s:if>
            
             <s:if test="#session._RECURSO.UsuFinMnuDesVirtual"> 
                  <div dojoType="dojox.layout.ContentPane" title="Trámite Virtual" style="height:60%;">
                    <table class="menu">
                      <s:if test="#session._RECURSO.UsuFinMnuDesVirtual">  
                        <tr>
                              <td id="filamenu25" name="filamenu" class="filamenu" onClick="accion(25)">
                                  Despacho 
                                  <b><span id="UsuFinMnuDesVirtual"></span></b>
                               </td>
                          </tr>
                      </s:if>  
                    </table>     
                  </div>       
            </s:if>  
            
             <s:if test="#session._RECURSO.UsuFinMnuMisLeg"> 
                  <div dojoType="dojox.layout.ContentPane" title="Expedientes" style="height:60%;">
                    <table class="menu">
                      <s:if test="#session._RECURSO.UsuFinMnuMisLeg">  
                        <tr>
                              <td id="filamenu27" name="filamenu" class="filamenu" onClick="accion(27)">
                                  Mis Expedientes
                                  <b><span id="UsuFinMnuMisLeg"></span></b>
                               </td>
                          </tr>
                      </s:if>
                      <s:if test="#session._RECURSO.UsuFinMnuComLeg">  
                        <tr>
                              <td id="filamenu28" name="filamenu" class="filamenu" onClick="accion(28)">
                                  Compartido
                                  <b><span id="UsuFinMnuComLeg"></span></b>
                               </td>
                          </tr>
                      </s:if>    
                    </table>     
                  </div>       
            </s:if>  
            
            
        </div>
    </body>
</html>
