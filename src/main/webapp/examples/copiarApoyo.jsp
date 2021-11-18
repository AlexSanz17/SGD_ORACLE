<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.btg.ositran.siged.domain.Parametro" %>
<%@ page import="com.btg.ositran.siged.domain.DocumentoReferencia" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
	  <title>Derivar M&uacute;ltiple</title>
          

      <link type="text/css" rel="stylesheet" href="css/styleSiged.css" />
      <link type="text/css" rel="stylesheet" href="js/dojo/css/styleDojo.css" />
		<style type="text/css">
         div.falso {
            position: absolute;
            /*top: -48px;*/
            left: 80px;
            z-index: 0;
            /*width: 14px;
            height: 3px;*/
         }

         input.file {
            position: relative;
            filter:alpha(opacity: 0);
            opacity: 0;
            z-index: 1;
            /*top: -49px;
            text-align: left;
            left: 80px;*/
            width: 50px;
            left: 10px;
         }
         .copia{
         	margin-right: 5px;
         }
		</style>
		<%
		     List<DocumentoReferencia> documentosReferencia = (List<DocumentoReferencia>)request.getAttribute("documentosReferencia");
		%>
                
                <script type="text/javascript">
			var djConfig = {
				isDebug: false,
				parseOnLoad: true
			};
		</script>
		<script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
		<script type="text/javascript" src="js/siged/upload.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/siged/siged.js"></script>
        	<script type="text/javascript" src="js/siged/siged.forms.js"></script>
		<script type="text/javascript" src="js/siged/siged.string.js"></script> 
                <script type="text/javascript" src="js/siged/derivacionMultiple.js"></script> 
                
                
		<script type="text/javascript">
                dojo.require("dijit.form.Button");
	        dojo.require("dijit.form.RadioButton");
	        dojo.require("dijit.Menu");
	        dojo.require("dijit.layout.BorderContainer");
		dojo.require("dijit.MenuItem");
	        dojo.require("dijit.form.NumberTextBox");
                
                var USUARIOACTIVO = "<s:property value='#session._USUARIO.idUsuarioPerfil'/>-<s:property value='#session._USUARIO.idUnidadPerfil' />-<s:property value='#session._USUARIO.idFuncionPerfil'/>";
                var storeProveidos = new dojo.data.ItemFileReadStore({url: "autocompletarProveido.action"});
                var storePrioridades = new dojo.data.ItemFileReadStore({url: "Parametro_getPrioridades_.action"});
                var storeCopia = new dojo.data.ItemFileReadStore({url: "getFavorito.action?mode=idconcopia"});
                var autocompletarUnidad = new dojo.data.ItemFileReadStore({url: "autocompletarUnidad.action"});
                var enviando=false;
         	var editandoAcciones = false;
         	var acciones = [];
         	var prioridades = [];
         	var idEditandoAcc;
                var txtaccionesEnviar = [];
                var prioridadEnviar = "";
                var MODE = "";
                   
                var autocompletarUnidadOrgSession = new dojo.data.ItemFileReadStore({url: "autocompletarUnidadOrgSession.action"});

                       function agregarConCopia(){
                            var nombre = dijit.byId("idconcopiaMultiple").attr("displayedValue");
                            var id = dijit.byId("idconcopiaMultiple").attr("value");
                         
                            if(nombre!=null && nombre!="" && id!=null && id!=""){
                               var existe=false;
                               var conCopia=document.getElementsByName("conCopia");
                               $(conCopia).each(function(){
                                  if($(this).val()==id)
                                     existe=true;
                               });
                               if(!existe){
                                  var copia="<span class=\"copia\">";
                                  copia+="<span>"+nombre+"</span>";
                                  copia+="<input type=\"hidden\" name=\"conCopia\" value=\""+id+"\" />";
                                  copia+="<a href=\"#\" class=\"quitar\"><img src=\"images/eliminar.gif\" alt=\"X\" /></a></span>";
                                  $("#copias").append(copia);
                                  $(".quitar").click(function(){
                                     $(this).parent().remove();
                                  });
                               }
                            }
                         };
                       
                        function showGrdParaCopia () {
                            MODE = "Copia";
                            gDoubleClick = false;
                            console.debug("(showGrdPara) MODE [" + MODE + "] gDoubleClick [" + gDoubleClick + "]");
                            dijit.byId("dlgPara").show();
                            dijit.byId("txtFiltroPara").attr("value", "");
                            dijit.byId("grdPara").showMessage("");
                            dijit.byId("grdPara").setStore(new dojo.data.ItemFileReadStore({url: "buscarPara.action?mode=" + MODE}));
                            dijit.byId("grdPara").focus.next();
                            dijit.byId("grdPara").render();
                         }; 

			function enviar(){
                                var prioridadSeleccionada = "";
                                var valor = "";
                                var arrAccT = [];
                                
                                var contenido=dijit.byId("CA_editor").getValue();
				if(contenido.length>4000){
                                     alert("El contenido no debe tener mas de 4000 caracteres");
                                     return;
				}

                                var arreglito = "";
                                dijit.byId("listParaApoyo").getChildren().forEach(function(node){
                                   arreglito = arreglito + node.id + "|";
                                });
                                
				if(!enviando){/****/
                                        enviando=true;
                                        var service = new dojo.rpc.JsonService("SMDAction.action");
                                        var defered = service.validarEnvio("<s:property value='iIdDoc' />", "<s:property value='bandeja' />", "", arreglito);
                                        defered.addCallback(function(respuesta){
                                            if (respuesta == '-1'){
                                              enviando=false;  
                                              alert("El documento ya fue derivado");    
                                              return;
                                            }

                                            if (respuesta == '-2'){
                                              enviando=false;  
                                              alert("El documento ya fue atendido");    
                                              return;
                                            }

                                            if (respuesta == '-3'){
                                               enviando=false;  
                                               alert("El documento ya fue anulado");    
                                               return;
                                            }
                                            
                                            if (respuesta == '-4'){
                                                alert("El documento debe estar Firmado.");
                                                enviando=false;
                                                return;
                                            }
                                            
                                            if (respuesta == "-5"){
                                                enviando=false;
                                                alert("Se ha producido un error inesperado. Intente procesar su solicitud nuevamente."); 
                                                return;
                                            }
                                             
                                            if (respuesta == "-6"){
                                               enviando=false; 
                                               alert("El documento ya fue enviado al Despacho Virtual para ser procesado."); 
                                               return;
                                            }

                                            if(editandoAcciones){
                                              guardarAcciones(idEditandoAcc);
                                            }
                                            var apoyos = [];
                                            var txtacciones = [];

                                            dijit.byId("listParaApoyo").getChildren().forEach(function(node){
                                               apoyos.push(node.id);
                                            });

                                            if(acciones.length > 0){
                                               txtacciones = completarAcciones();
                                            }

                                            if(!apoyos.length){
                                               alert("Debe seleccionar al menos un destinatario para la derivación multiple");
                                               enviando=false;
                                               return;
                                            }

                                             prioridadSeleccionada=dijit.byId("objDD.prioridad").attr("value");
                                            // prioridadEnviar = prioridadSeleccionada; 
                                            if (prioridadSeleccionada==""){
                                                alert("Debe especificar la Prioridad.");
                                                enviando=false;
                                                return;
                                            }

                                             if (acciones.length == 0){
                                                 alert("Debe selecionar una acción");
                                                 enviando=false;
                                                 return;
                                             }

                                             if (acciones.length!=apoyos.length){
                                                 alert("Debe selecionar una acción");
                                                 enviando=false;
                                                 return;
                                             }

                                             for(var j = 0 ; j< acciones.length; j++){ 
                                               if (acciones[j][1]==""){
                                                   alert("Debe selecionar una acción");
                                                   enviando=false;
                                                   return;
                                               }
                                             }
                                             
                                            dojo.query("input[name='docReferencias']").forEach(function(node){
                                                if(node.checked){
                                                  arrAccT.push(node.id);
                                                }
                                             });
                                             
                                             dojo.query("input[name='strSeguimiento']").forEach(function(node){
                                                if(node.checked){
                                                  document.getElementById("strSeguimiento").value = "1";
                                                }
                                             });

                                            document.getElementById("referencias").value = arrAccT;

                                            dijit.byId("dlgProgresBar").show();
                                            dojo.xhrPost({
                                                        form : document.getElementById("copiarApoyo"),
                                                        content: {
                                                                apoyo	:	apoyos,
                                                                ta	:	dijit.byId("CA_editor").attr("value"),
                                                                strAcciones : txtacciones,
                                                                strPrioridad : prioridadSeleccionada

                                                        },
                                                        load : function(){
                                                             refrescar();
                                                             dijit.byId("dlgProgresBar").hide();  
                                                        }
                                            });
                                        });
                                      
				}/*****/
			}

			var filterPara = function(e) {
			   	dijit.byId("grdPara").setQuery({nombres : "*" + this.attr("value") + "*"}, {ignoreCase: true});
			};
                        var filterParaM = function (e) {
                        dijit.byId("grdParaM").setQuery({nombres: "*" + this.attr("value") + "*"}, {ignoreCase: true});
                        };
			var resetPara = function() {
			    dijit.byId("txtFiltroPara").attr("value", "");
			    dojo.byId("txtFiltroPara").focus();
			    dijit.byId("grdPara").showMessage("");
			    dijit.byId("grdPara").setStore(new dojo.data.ItemFileReadStore({url: "buscarPara.action?mode=" + MODE}));
			    dijit.byId("grdPara").focus.next();
			    dijit.byId("grdPara").render();
			};
                        var resetParaM = function () {
                           dijit.byId("txtFiltroParaM").attr("value", "");
                           dojo.byId("txtFiltroParaM").focus();
                           dijit.byId("grdParaM").showMessage("");
                           dijit.byId("grdParaM").setStore(new dojo.data.ItemFileReadStore({url: "buscarPara.action?mode=" + MODE}));
                           dijit.byId("grdParaM").focus.next();
                           dijit.byId("grdParaM").render();
                       };
			function activarChk(){
                            dijit.byId("strAcc").attr("disabled", false);                                   
			};

			function limpiarAcciones(){
                           dijit.byId("strAcc").attr("value", "");               
			}

			function completarAcciones(){
                                var listaAcc = [];

				for(var j=0; j<acciones.length; j++){
					var filaAcc = [];
					filaAcc.push(acciones[j][0]);
                                        filaAcc.push(acciones[j][1]);
					listaAcc.push(filaAcc);
				}
				return listaAcc;
			}

			function guardarAcciones(id){
                               var list = [];

                               for(var j = 0 ; j< acciones.length; j++){
				   if(id == acciones[j][0]){
                                      list = acciones[j];
				   }
				}

                               if(list.length > 0){
				   list[1] =  dijit.byId("strAcc").attr("value");
				}else{
                                   list.push(id);
                                   list.push(dijit.byId("strAcc").attr("value"));
                                   acciones.push(list);
				}
                                
			}

			function guardarPrioridades(id){
                                var list = [];
				for(var j = 0 ; j< prioridades.length; j++){
					if(id == prioridades[j][0]){
						list = prioridades[j];
					}
				}
				if(list.length > 0){   //BENGOA
					/**REN ya se han ingresado la prioridad para el usuario -----------------------------------------------------*/
					for(var j=2; j<5; j++){ //BENGOA 2
						if(dijit.byId("prioridad"+j).attr("checked")){
							list[1] = dijit.byId("prioridad"+j).attr("value");
							break;
						}
					}
				}else{
					/**REN aun no se llenaba este nodo ----------------------------------------------------------------------*/
					list.push(id);
					for(var j=2; j<5; j++){
						if(dijit.byId("prioridad"+j).attr("checked")){ //BENGOA 2
							list.push(dijit.byId("prioridad"+j).attr("value"));
							break;
						}
					}
					prioridades.push(list);
				}
			}

			function cargarAcciones(id){
                                limpiarAcciones();
				var list = [];
                                
                               for(var j = 0 ; j< acciones.length; j++){
				   if(id == acciones[j][0]){
				      list = acciones[j];
				   }
				}
                                
                                if(list.length > 0){
                                    dijit.byId("strAcc").attr("value",list[1]);
				}
			}
                        
                        	function editarAcciones(){
				editandoAcciones = true;
			};
                        
                        function removerDestinatario(id){
                                var bandera = false;
                                dijit.byId("listParaApoyo").getChildren().forEach(function(node){
					if(node.id == id){
						removerDestinatarioArreglo(node.id);
						node.destroy();
						limpiarAcciones();
					}
				});
                                
                                
                                dijit.byId("listParaApoyo").getChildren().forEach(function(node){
                                    bandera = true;
                                });    
                                
                                if (!bandera){
                                   dijit.byId("objDD.prioridad").attr("value","<s:property value='@org.ositran.utils.Constantes@PRIORIDAD_NORMAL' />"); 
                                }
                                
                                dijit.byId("strAcc").attr("value", ""); 
                                dijit.byId("strAcc").attr("disabled", true);             	
                                editandoAcciones = false;
                                
			}
                        
                        function removerDestinatarioArreglo(id){
                                var j = 0;
				var existeDestArr = false;
				while(j< acciones.length){
					if(id == acciones[j][0]){
						existeDestArr = true;
						break;
					}
					j++;
				}
				if(existeDestArr){
					acciones.splice(j, 1);
				}
			}
                        function limpiarContactosApoyo(){
                      dijit.byId("listParaApoyo").getChildren().forEach(function (node) {
                            removerDestinatario(node.id);
                        });
                        dijit.byId("btnRemoverDest").attr("label","Remover");
                        dijit.byId("btnRemoverDest").attr("disabled",true);
                }

                        function agregarDestinatarios(p) {

                            var piduo = (p == 1) ?<s:property value='#session._USUARIO.idUnidadPerfil' /> : dijit.byId("strUO").attr("value");
                            var service = new dojo.rpc.JsonService("SMDAction.action");
                            service.usuariosxUniOrg(piduo).addCallback(function (response) {
                                for (var i = 0; i < response.items.length; i++) {
                                    var id = response.items[i].id;
                                    var sid=id.split("-")[0];
                                    if ( sid!="<s:property value='#session._USUARIO.idUsuarioPerfil'/>") {
                                        var nombre = response.items[i].nombre;
                                        try {
                                            
                                            var x = new dijit.MenuItem({
                                                id: id,
                                                label: nombre,
                                                onClick: function (e) {
                                                    if (editandoAcciones) {
                                                        guardarAcciones(idEditandoAcc);
                                                        editandoAcciones = true;
                                                    } else {
                                                        activarChk();
                                                        editandoAcciones = true;
                                                    }
                                                    idEditandoAcc = this.id;
                                                    var otherid = this.id;
                                                    cargarAcciones(idEditandoAcc);
                                                    var refBotonRemover = dijit.byId("btnRemoverDest");
                                                    refBotonRemover.attr("disabled", false);
                                                    refBotonRemover.attr("label", "Remover " + this.label);
                                                    refBotonRemover.attr("onClick", function (e) {
                                                        removerDestinatario(otherid);
                                                        refBotonRemover.attr("disabled", true);
                                                        refBotonRemover.attr("label", "Remover");
                                                    });
                                                }
                                            });
                                            dijit.byId("listParaApoyo").addChild(x);
                                            var list = [];
                                            list.push(id);
                                            list.push(1);
                                            acciones.push(list);
                                        } catch (e) {
                                            console.log(e.message); // muestra 'Error'
                                        }
                                    }
                                }
                                var cboAc = dijit.byId("strAcc");
                                cboAc.attr("disabled", false);
                                cboAc.attr("value", 2);
                                dijit.byId("strUO").attr("value", "");
                                dijit.byId("dlgParaM").hide();
                            });

                        }
                        var selectContactoFromGrdPara = function(e) {
                               if (!gDoubleClick) {
                                    if (e.rowIndex == undefined) {
                                        return;
                                    }

                                    var id = dijit.byId("grdPara").getItem(e.rowIndex).id;
                                    var nombre = dijit.byId("grdPara").getItem(e.rowIndex).nombres;
                                    
                                    if (id==USUARIOACTIVO){
                                        alert("La derivación no puede realizarse al mismo usuario.");
                                        return;
                                    }
                                    
                                    if(MODE=='Multiple' && nombre!=null && nombre!="" && id!=null && id!=""){
                            		if(!dijit.byId(""+id)){
                            			new dijit.MenuItem({
                                                                id   :  id,
								name : "apoyo",
								label: nombre,
								onClick: function(e){
                                                                       if(editandoAcciones){
                                                                           guardarAcciones(idEditandoAcc);
							   	           editandoAcciones = true;
                                                                       }else{
                                                                           activarChk();
                                                                           editandoAcciones = true;
									}
                                                                        
                                                                        idEditandoAcc = this.id;
                                                                        cargarAcciones(idEditandoAcc);
									var refBotonRemover = dijit.byId("btnRemoverDest");
									refBotonRemover.attr("disabled", false);
									refBotonRemover.attr("label","Remover " + nombre);
									refBotonRemover.attr("onClick", function(e){
									removerDestinatario(id);
									refBotonRemover.attr("disabled", true);
									refBotonRemover.attr("label","Remover");
									});

								}
							}).placeAt(dijit.byId("listParaApoyo"));
				    	}
                                        dijit.byId("dlgPara").hide();
			    	    }else{
                                        if (MODE == 'Multiple'){
                                           dijit.byId("dlgPara").hide();          
                                        }    
                                        if (MODE == 'Copia'){
                                            var service = new dojo.rpc.JsonService("SMDAction.action");
                                            service.saveFavorito(String(id)).addCallback(function(response) {
                                                dijit.byId("idconcopiaMultiple").store = new dojo.data.ItemFileReadStore({url: "getFavorito.action?mode=" + MODE});
                                                dijit.byId("idconcopiaMultiple").attr("value", id);
                                                dijit.byId("dlgPara").hide();
                                             });
                                        }
                                    }    
				}
			   	//dijit.byId("dlgPara").hide();
			};
                        var selectContactoFromGrdParaM = function (e) {
                    if (!gDoubleClick) {
                        if (e.rowIndex == undefined) {
                            return;
                        }

                        var id = dijit.byId("grdParaM").getItem(e.rowIndex).id;
                        var nombre = dijit.byId("grdParaM").getItem(e.rowIndex).nombres;
                        console.log(id);
                        if (id == USUARIOACTIVO) {
                            alert("La derivación no puede realizarse al mismo usuario.");
                            return;
                        }

                        if (MODE == 'Multiple' && nombre != null && nombre != "" && id != null && id != "") {
                            if (!dijit.byId("" + id)) {
                                new dijit.MenuItem({
                                    id: id,
                                    name: "apoyo",
                                    label: nombre,
                                    onClick: function (e) {
                                        if (editandoAcciones) {
                                            guardarAcciones(idEditandoAcc);
                                            editandoAcciones = true;
                                        } else {
                                            activarChk();
                                            editandoAcciones = true;
                                        }

                                        idEditandoAcc = this.id;
                                        cargarAcciones(idEditandoAcc);
                                        var refBotonRemover = dijit.byId("btnRemoverDest");
                                        refBotonRemover.attr("disabled", false);
                                        refBotonRemover.attr("label", "Remover " + nombre);
                                        refBotonRemover.attr("onClick", function (e) {
                                            removerDestinatario(id);
                                            refBotonRemover.attr("disabled", true);
                                            refBotonRemover.attr("label", "Remover");
                                        });

                                    }
                                }).placeAt(dijit.byId("listParaApoyo"));
                            }
                            dijit.byId("dlgParaM").hide();
                        } else {
                            if (MODE == 'Multiple') {
                                dijit.byId("dlgParaM").hide();
                            }
                            if (MODE == 'Copia') {
                                var service = new dojo.rpc.JsonService("SMDAction.action");
                                service.saveFavorito(String(id)).addCallback(function (response) {
                                    dijit.byId("idconcopiaMultiple").store = new dojo.data.ItemFileReadStore({url: "getFavorito.action?mode=" + MODE});
                                    dijit.byId("idconcopiaMultiple").attr("value", id);
                                    dijit.byId("dlgParaM").hide();
                                });
                            }
                        }
                    }
                    //dijit.byId("dlgPara").hide();
                };
                        var showGrdPara = function () {
                            MODE = "Multiple";
                            var bandera = false;
                            dijit.byId("listParaApoyo").getChildren().forEach(function (node) {
                                bandera = true;
                            });

                            if (bandera && dijit.byId("strAcc").attr("value") == "") {
                                alert("Debe selecionar una acción");
                                return;
                            }
                            gDoubleClick = false;
                            dijit.byId("dlgParaM").show();
                            dijit.byId("txtFiltroParaM").attr("value", "");
                            dijit.byId("grdParaM").showMessage("");
                            dijit.byId("grdParaM").setStore(new dojo.data.ItemFileReadStore({url: "buscarPara.action?mode=copiaApoyo&iIdProceso=" + "<s:property value='documento.expediente.proceso.idproceso' />"}));
                            dijit.byId("grdParaM").focus.next();
                            dijit.byId("grdParaM").render();
                        };  
                        
                        function refrescar() {
                           window.opener.eliminarRegistro();
			   if(window.opener.recursoIG==1 && window.opener.vistaIG ==false ){
	            	     window.opener.mostrarVisorPDFError();
	         	   }
	           	   this.window.close();
                        }
                        
			dojo.addOnLoad(function(){
				new dijit.Menu({
					style: "border:none;"
				},"listParaApoyo");
                                
                                
                               dijit.byId("objDD.prioridad").attr("value", "<s:property value='objDD.prioridad'/>");
                               
                               var service = new dojo.rpc.JsonService("SMDAction.action");
                                service.unidadOrgxSession().addCallback(function (response) {
                                     var nl = new dojo.NodeList();
                                         nl.push(dojo.byId("divstrUOLabel"));
                                         nl.push(dojo.byId("divstrUO"));
                                         nl.style("display", response.items.length === 0?"none":"block");
                      
                       
                                });
                            });
                        
                 
                
		</script>
	</head>
	<body class="soria" <s:if test='cerrar!=null'>onload="refrescar();"</s:if>>
       
		<s:if test="cerrar==null">
		    <form id="copiarApoyo" action="copiarApoyo.action" method="post">
                       <s:hidden name="objDD.observacionDocumento" /> 
                       <input type="hidden" name="strReferencia" id="referencias"/>
                        <input type="hidden" name="codigoVirtual"  value="<s:property value='codigoVirtual' />"/>
			<table width="97%" align="center" border="0">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr align="center">
					<td align="left">
                                            <table>
                                                <tr>
                                                    <td width="350px">
                                                       <img src="images/enviar.bmp" alt="Enviar" style="float: left;" onclick="javascript:enviar();" />        
                                                    </td>
                                                    <td> 
                                                         <s:if test="bandeja==null || bandeja!='E'">
                                                            <%if (documentosReferencia!=null && documentosReferencia.size()>0){%>  
                                                              <table border="0">
                                                                   <b>Documento a Atender</b>                                                                         
                                                                   <%for(DocumentoReferencia p : documentosReferencia){ %>                                                                            
                                                                      <tr>   
                                                                          <td> 
                                                                            <input dojoType="dijit.form.CheckBox" id="<%=p.getIdDocumentoReferencia()%>"
                                                                                           name="docReferencias"><%=p.getDocumentoReferencia().getTipoDocumento().getNombre() + " " + p.getDocumentoReferencia().getNumeroDocumento()%>
                                                                          </td>
                                                                       </tr>  
                                                                   <%}%>                 
                                                               </table>	
                                                           <%}%> 
                                                         </s:if>  
                                                    </td>
                                                </tr>
                                                
                                            </table>
						
					</td>
                                       
				</tr>
				<tr>
					<td>
						&nbsp;<s:hidden name="iIdDoc" />  
					</td>
				</tr>
				<tr>
					<td width="50%">
                                            <div dojoType="dijit.form.Button" onClick="showGrdPara()">Agregar Destinatario</div>
                                            <button dojoType="dijit.form.Button"
                                                    type="button"                               
                                                    onClick="agregarDestinatarios(1)"
                                                    showLabel="true"
                                                    label="Agregar Contactos de mi Área"></button>
                                            <div id="btnRemoverDest"  dojoType="dijit.form.Button" disabled >Remover</div>	
                                            <div dojoType="dijit.form.Button" onClick="limpiarContactosApoyo()">Limpiar Contactos</div>
                                        </td>
                                       
				</tr>
				<tr>
                                        <td style="height:7em; padding:5px; background-color:#FFFFFF; border:solid 1px; border-color:#BCBCBC;">
						<div dojoType="dijit.layout.BorderContainer" style="background-color:#FFFFFF; width:100%;height:7em; overflow:auto; padding:5px; border:thin; ">
							<div id="listParaApoyo"></div>
						</div>
					</td>
                                        
				</tr>
				<tr>
					<td style="background-color:#FFFFFF; border:solid 1px; border-color:#BCBCBC; ">
						<div id="chkAccionesCont" dojoType="dijit.layout.BorderContainer" style="background-color:#FFFFFF; width:100%; height:18em; padding-left:5px;">
							<table style="width:100%;" border="0">
                                                            <tr>
                                                                <td style="padding:0.5em 0.5em 0.5em 0.5em;" colspan="4"></td>
                                                            </tr>
                                                            
                                                            <tr>                   
                                                                <td style="padding-left:0.5em;"> 
                                                                     <button dojoType="dijit.form.Button" type="button" id="btnCc"
											jsId="btnCc" onClick="showGrdParaCopia()"
											showLabel="true">Cc</button> 
                                                                </td>
                                                                <td><!--
                                                                   <select dojoType="dijit.form.FilteringSelect" 
                                                                                    id="idconcopia"
                                                                                    idAttr="id"
                                                                                    labelAttr="label"
                                                                                    hasDownArrow="true"
                                                                                    queryExpr="*${0}*"
                                                                                    searchAttr="label"
                                                                                    autoComplete="true"
                                                                                    onChange="agregarConCopia" 
                                                                                    style = "width:400px;"
                                                                                    required = "false"
                                                                                    store="storeCopia"></select> -->
                                                                    <div id ="iddestinatariosMultiple"></div>

                                                                 </td>
                                                                 <td colspan="2"></td>
                                                            </tr>
                                                            
                                                            <tr>
					       		       <td colspan="4">
                                                                  <div dojoType="dijit.layout.BorderContainer" style="background-color:#FFFFFF;height:18px; width:98%;overflow:auto; padding:5px; border:thin; "> 
                                                                     <div id="copias"></div>
                                                                  </div>
                                                               </td>
						            </tr>
                                                               
                                                             
                                                           <tr>
                                                                  <td style="padding:0.5em 0.5em 0.5em 0.5em;" colspan="4"></td>
                                                           </tr>
                                                    
							   <tr style="border-top: 0.02em solid; border-top-color: #BCBCBC;">
                                                                <td  style="padding:1em 0.5em 0.5em 0.5em;" colspan="3">
                                                                     <strong>Acci&oacuten:&nbsp;&nbsp;&nbsp;&nbsp; </strong>
                                                                         <select dojoType="dijit.form.FilteringSelect" 
                                                                                    id="strAcc"
                                                                                    name="strAcc"
                                                                                    idAttr="id"
                                                                                    labelAttr="label"
                                                                                    style="width:185px;"
                                                                                    hasDownArrow="true"
                                                                                    disabled="true"
                                                                                    searchAttr="label"
                                                                                    store="storeProveidos"></select>  
                                                                              &nbsp;&nbsp;&nbsp;
                                                                              <input dojoType="dijit.form.CheckBox" id="strSeguimiento"
                                                                                     name="strSeguimiento">Marcar Para Seguimiento 
								</td>
							   </tr>
                                                            
                                                            <tr>
                                                                <td style="padding:0.5em 0.5em 0.5em 0.5em;" colspan="4"></td>
                                                            </tr> 
                                                            
                                                            
                                                            
                                                            <tr style="border-top: 0.02em solid; border-top-color: #BCBCBC;">
                                                               
                                                                <td style="padding:1em 0.5em 0.5em 0.5em;" colspan="4">
                                                                    <table border="0"> 
                                                                        <tr> 
                                                                            <td> 
                                                                              <strong>Prioridad: </strong>
                                                                                       <select dojoType="dijit.form.FilteringSelect"
                                                                                                       id="objDD.prioridad" 
                                                                                                       name="objDD.prioridad"
                                                                                                       store="storePrioridades"
                                                                                                       required="true"
                                                                                                       searchAttr="label"
                                                                                                       size="25px">
                                                                                       </select>
                                                                            </td> 
                                                                           
                                                                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>Fecha Limite: </strong> &nbsp;</td>
                                                                                            <td class="label" colspan="1">
                                                                                               <div dojoType="dijit.form.DateTextBox"
                                                                                                    id="objDD.strFechaLimiteAtencion"
                                                                                                    jsId="objDD.strFechaLimiteAtencion"
                                                                                                    name="objDD.strFechaLimiteAtencion"
                                                                                                    constraints="{datePattern:'dd/MM/yyyy'}"
                                                                                                    invalidMessage="Ingrese fecha limite dd/MM/yyyy" 
                                                                                                    trim="true">
                                                                                                  <script type="dojo/method" event="postCreate">
                                                                                                     var fechadocumento_year;
                                                                                                     var fechadocumento_month;
                                                                                                     var fechadocumento_day;

                                                                                                     if ("<s:date name='objDD.dateFechaLimiteAtencion' format='dd' />"== null ||
                                                                                                         "<s:date name='objDD.dateFechaLimiteAtencion' format='dd' />"== ""){ 
                                                                                                          this.attr("value", "");
                                                                                                     }else{
                                                                                                         fechadocumento_year = parseInt("<s:date name='objDD.dateFechaLimiteAtencion' format='yyyy' />", 10);
                                                                                                         fechadocumento_month = parseInt("<s:date name='objDD.dateFechaLimiteAtencion' format='MM' />", 10);
                                                                                                         fechadocumento_day = parseInt("<s:date name='objDD.dateFechaLimiteAtencion' format='dd' />", 10);  
                                                                                                         this.attr("value", new Date(fechadocumento_year, fechadocumento_month - 1, fechadocumento_day));
                                                                                                      }    
                                                                                                  </script>
                                                                                               </div>
                                                                                        </td>
                                                                            
                                                                            
                                                                        </tr>                  
                                                                     </table>              
                                                                </td>
                                                            </tr>
                            
							    <tr style="border-top: 0.02em solid; border-top-color: #BCBCBC;">
                                                                        <td style="padding:0.5em 0.5em 0.5em 0.5em;" colspan="4">
                                                                            <table>
                                                                                <tr>
                                                                                    <td  colspan="1">
                                                                                      <strong>Asunto: &nbsp;&nbsp;&nbsp;</strong>
                                                                                    </td>
                                                                                    <td colspan="3">
                                                                                        <s:textarea rows="3" cols="150"  name="objDD.strAsunto"  style="width:64em;" readonly="true"/>
                                                                                    </td>
                                                                                </tr>
                                                                                
                                                                            </table>
                                                                               
									</td>
							    </tr>
                                                                        
                                                            <tr style="border-top: 0.02em solid; border-top-color: #BCBCBC;">
									<td style="padding:0.5em 0.5em 0.5em 0.5em;" colspan="4">
										<strong>Proveido: &nbsp;&nbsp;</strong>
                                                                               
									</td>
							    </tr>         
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						
						<div dojoType="dijit.Editor" id="CA_editor" style="background-color: #FFF; min-height:7em;height:7em;width:100%;overflow:auto" ></div>
						<script type="text/javascript">
							var cadena  = document.getElementById("ta").value;
							document.getElementById("CA_editor").innerHTML = cadena.substring(0,3999);
						</script>
					</td>
				</tr>
                                                
                                  <tr>
                                           
                                             <td  colspan="1">
                                                  <input  size="109"   style="font-weight: bold;font-size:16px;width:100%"
						   type="text" name="ta" id="ta"
                                                    value="<s:property value='ta'  />"> </input>
                                             </td>
                                                            
                                  </tr>                   
			</table>
			</form>
		</s:if>
		<s:else>
			<p style="text-align: center;">Operaci&oacute;n realizada satisfactoriamente</p>
		</s:else>
		<%@ include file="../pages/util/progressBar.jsp"%>
		<%@ include file="derivar-dialogsM.jsp" %>
	</body>
</html>


