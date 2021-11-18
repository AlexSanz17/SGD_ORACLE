<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="gob.ositran.siged.config.SigedProperties"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
       
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Sistema de Gesti&oacute;n Documentaria - Top Bar</title>
                 
        <script type="text/javascript">

    
             if (sessionStorage.tabID!='<s:property value='#session.storage' />'){
                document.location.href = "<%=request.getContextPath()%>/activa.html" ;
             }
             
             dojo.declare("my.IdleListener", [], {

               _mouseMoveHandle: null,
               _keyDownHandle: null,
               _timeout: 30000,

                isRunning: function() {
                      return this._enabled;
                  },

                  isIdle: function() {
                      return this._idle;
                  },

                  start: function(newTimeout) {
                      this._enabled = true;
                      this._idle = false;
                      if (typeof newTimeout == "number") {
                          this._timeout = newTimeout;
                      }
                      this._mouseMoveHandle = dojo.connect(dojo.doc, "onmousemove", this, "_handleUserEvent");
                      this._keyDownHandle = dojo.connect(dojo.doc, "onkeydown", this, "_handleUserEvent");
                      // set a timeout to toggle state
                      this._idleTimeout = setTimeout(dojo.hitch(this, "_toggleIdleState"), this._timeout);
                  },

                  stop: function() {
                      this._enabled = false;
                      // clear any pending timeouts
                      clearTimeout(this._idleTimeout);
                      // detach the event handlers
                      dojo.forEach([this._mouseMoveHandle, this._keyDownHandle], function(item, index, array) {
                       if(item) {
                        dojo.disconnect(item);
                       }
                      });
                  },

               _handleUserEvent: function() {
                   // clear any existing timeout
                   clearTimeout(this._idleTimeout);
                   if (this._enabled) {
                    // if the user is just waking us up again, toggle the idle state.
                    // otherwise, reset the timeout with a new timeout
                       this._idle ? this._toggleIdleState() : this._idleTimeout = setTimeout(dojo.hitch(this, "_toggleIdleState"), this._timeout);
                   }
               },

               _toggleIdleState: function() {
                   this._idle = !this._idle;
                   this._idle ? dojo.publish("idle", []) : dojo.publish("active", []); 
               }
              });
              
              var idleListener = new my.IdleListener();
              idleListener.start(<%=session.getMaxInactiveInterval()*1000-100000%>); 
              dojo.subscribe("idle", function() {
                  console.log("idle");
                  alert("Su sesión ha expirado"); //dispachet al login
                  document.location.href = "<%=request.getContextPath()%>";
               });
               dojo.subscribe("active", function() {
               }); 
                    
          var storeFuncion = new dojo.data.ItemFileReadStore({url: "autocompletarAllPerfiles.action"});
          var service = new dojo.rpc.JsonService("SMDAction.action");
          
          function cargarPerfil(){
              service.cambiarPerfilUsuario(dijit.byId('unidadxperfil.idunidadxperfil').getValue()).addCallback(function(objJSON) {
                     buildTabsFromToolBarTop('UsuPerfilBtn','<s:property value='#session._USUARIO.rol' />');
               });  
            
           }
           
           
            function openNuevoCliente(){
            	resetRegistroCliente();
            	dijit.byId("dlgNuevoCliente").show();
            }

            function openAlerta(){
            	var serviceExistAlerta = new dojo.rpc.JsonService("SMDAction.action");
            	 var resultExistAlerta = serviceExistAlerta.verificarExisteAlerta();

            	 resultExistAlerta.addCallback(function(result) {
				    	if (result=='0'){
				    		alert("No existen alertas pendientes.");
				    		return;
				    	}

				    	 var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=650, height=500, top=50, left=200";
			             window.open("goViewAlerta.action", "", opciones);
				    });
            }
            dojo.addOnLoad(function() {
               
                var btnData = null;

                var toolBarTop = new dijit.Toolbar({
                    style: "text-align:right;float:right;"
                }, "divToolBarTop");
                
                
                var child1 = new dijit.form.Button({
                            iconClass: "siged16 sigedNew16",
                            label: "Gestor de Documentos",
                            onClick: function() {
                                window.open("<%=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_GESTOR)%>","_blank");
                            },
                            showLabel: true
                        });
                toolBarTop.addChild(child1);

                dojo.forEach(arrRecursoTopButton, function(item) {
                    console.debug("top.js Recurso [%s] disponible [%d]", item.codigo, item.valor);

                    if (item.valor) {
                        btnData = getButtonData(item.codigo);
                        if (btnData.label!=''){
                            var child = new dijit.form.Button({
                                iconClass: btnData.iconClass,
                                label: btnData.label,
                                onClick: function() {
                                    buildTabsFromToolBarTop(item.codigo,'<s:property value='#session._USUARIO.rol' />');
                                },
                                showLabel: true
                            });

                            toolBarTop.addChild(child);
                        }    
                    }
                });
            });
        </script>
            
    </head>
    <body>
        <div class="headerUser"><img alt="Logo OSITRAN" src="./images/logo.jpg" width="72px" height="39px" /> </div>
        
        <div class="headerUser">
            
            <span style="font-size:11px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Bienvenido <b><s:property value="#session._USUARIO.nombres" /> <s:property value="#session._USUARIO.apellidos" /></b></span>
           
            <span  class="headerSeparator" style="font-size:10px;">
                <s:if test="#session._RECURSO.MPMnuDocReg">
                    <b><span style='color:blue'>[Mesa de Partes]</span></b>
                </s:if>
                <s:if test="#session._RECURSO.DigMnuDocIng">
                    <b><span style='color:blue'>[Digitalización]</span></b>
                </s:if>
                <s:if test="#session._RECURSO.QASMnuDigitalizados">
                    <b><span style='color:blue'>[Control de Calidad]</span></b>
                </s:if>
                <s:if test="!(#session._RECURSO.MPMnuDocReg || #session._RECURSO.DigMnuDocIng || #session._RECURSO.QASMnuDigitalizados)">
                    <span style='color:blue'>[Usuario final]</span>
                </s:if>
            </span>
            
           
            
            <div dojoType="dijit.Menu" leftClickToOpen="true" style="display:none;" targetNodeIds="idOpciones">
                <div dojoType="dijit.PopupMenuItem">
                    <span>Temas</span>
                    <div dojoType="dijit.Menu">
                        <div dojoType="dijit.MenuItem" onClick="changeBodyClass('nihilo')">Nihilo</div>
                        <div dojoType="dijit.MenuItem" onClick="changeBodyClass('soria')">Soria</div>
                        <div dojoType="dijit.MenuItem" onClick="changeBodyClass('tundra')">Tundra</div>
                    </div>
                </div>
                <div dojoType="dijit.MenuSeparator"></div>
              <!--    <div dojoType="dijit.MenuItem" onClick="cambiarClave">Cambiar Contraseña</div> -->
                <!--  <div dojoType="dijit.MenuSeparator"></div> -->
                <div dojoType="dijit.MenuItem" onClick="guardarGrid">Guardar Configuraci&oacute;n</div>
                <div dojoType="dijit.MenuSeparator"></div>
                <div dojoType="dijit.MenuItem" onClick="showOnlineHelp('<s:property value='#session._USUARIO.rol' />', null)">Ayuda en L&iacute;nea</div>
            </div>
            <span class="headerSeparator"><a id="idOpciones" class="headerLink" style="font-size:11px;">Opciones</a></span>
            <a class="headerLink" href="doLogout.action" title="Salir del SIGED" style="font-size:11px;">Cerrar Sesi&oacute;n</a>
            
         
            <br/>
            
             <span style="font-size:11px;">
                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Rol:</span> 
            </span>
            
            <span style="font-size:12px;">  
                       
               <select dojoType="dijit.form.FilteringSelect"  
				                id="unidadxperfil.idunidadxperfil"
                                                name="unidadxperfil.idunidadxperfil"
					       value="<s:property value='#session._USUARIO.idUnidadPerfil' />|<s:property value='#session._USUARIO.idFuncionPerfil'/>|<s:property value='#session._USUARIO.idUsuarioPerfil'/>"
				                idAttr="id"				               
				                style="width:400px;"
                                                onChange="cargarPerfil"  
                                                 store="storeFuncion"
				               required="false"
                                                autoComplete="false"
				                searchAttr="label">
                                                                               
				               </select>
            </span>   
            
            <s:if test="#session._USUARIO.usuarioInicial" >
                <span id="resumenExpMem" style="font-weight: bold;"></span>
                <span id="resumenExpOf" style="font-weight: bold;"></span>
                <span id="resumenExpInf" style="font-weight: bold;"></span>
                <span id="resumenCopiaMem" style="font-weight: bold;"></span>
                <span id="resumenCopiaOf" style="font-weight: bold;"></span>
                <span id="resumenCopiaInf" style="font-weight: bold;"></span>
            </s:if>
           
        </div>
                                                
                                               
        <div id="divToolBarTop" style="margin-top: 0px;"></div>
        
        <div class="headerBusquedaAvanzada">       
            <s:if test="#session._RECURSO.UsuFinBtnBus">
                                
                <a class="headerLink" href="javascript:buildTabsFromToolBarTop('BusquedaAvanzada','<s:property value='#session._USUARIO.rol' />');" title="Busqueda Avanzada">B&uacute;squeda Avanzada</a>
                <!--|
                <a class="headerLink" href="javascript:buildTabsFromToolBarTop('ReporteVista', null);" title="Reportes">Reportes</a>-->
            </s:if>
               
        </div>
        
	 <script type="text/javascript" src="js/siged/registroClienteMP.js"></script>
         <%@ include file="../util/progressBar.jsp" %>
         
    </body>
</html>





