<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Aprobar Masivamente</title>
		<link type="text/css" rel="stylesheet" href="css/styleSiged.css" />
		<link type="text/css" rel="stylesheet" href="js/dojo/css/styleDojo.css" />
		<style type="text/css">
		div.falso {
			position: absolute;
			top: -49px;
			left: 80px;
			z-index: 0;
			width: 14px;
			height: 3px;
		}
		
		input.file {
			position: relative;
			text-align: left;
			filter: alpha(opacity :     0);
			opacity: 0;
			z-index: 1;
			top: -49px;
			left: 80px;
		}
		</style>
		
		<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
		<script type="text/javascript" src="js/dojo/dijit/dijit.js" djConfig="parseOnLoad:true, isDebug: false"></script>
		<script type="text/javascript" src="js/siged/upload.js"></script>
		<script type='text/javascript' src='./dwr/engine.js'></script>
		<script type='text/javascript' src='./dwr/util.js'></script>
		<script type='text/javascript' src='./dwr/interface/toolDwr.js'></script>
		<script type="text/javascript" src="js/siged/siged.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/siged/siged.forms.js"></script>
		<script type="text/javascript">
		var service = new dojo.rpc.JsonService("SMDAction.action");
                var store = new dojo.data.ItemFileReadStore({url: "buscarPara.action?mode=iddestinatario"});
        
		var MODE = null;
		var gDoubleClick = false;
		var filterPara = function(e) {
		   //console.debug("value [%s]", this.attr("value"));
		   dijit.byId("grdPara").setQuery({nombres : "*" + this.attr("value") + "*"}, {ignoreCase: true});
		};

		var resetPara = function() {
		   dijit.byId("txtFiltroPara").attr("value", "");
		   dojo.byId("txtFiltroPara").focus();
		   dijit.byId("grdPara").showMessage("");
		   dijit.byId("grdPara").setStore(new dojo.data.ItemFileReadStore({url: "buscarPara.action?mode=" + MODE}));
		   dijit.byId("grdPara").focus.next();
		   dijit.byId("grdPara").render();
		};

		var selectContactoFromGrdPara = function(e) {
		   if (!gDoubleClick) {
		      if (e.rowIndex == undefined) {
		         return;
		      }

		      var id = dijit.byId("grdPara").getItem(e.rowIndex).id;
		      var nombres = dijit.byId("grdPara").getItem(e.rowIndex).nombres;

		      console.debug("(selectContactoFromGrdPara) rowIndex [%s] id [%s] nombres [%s]", e.rowIndex, id, nombres);
		      gDoubleClick = true;
		      service.saveFavorito(String(id)).addCallback(function(response) {
		      console.debug("Se registro contacto con id [%s]", response);
		      dijit.byId(MODE).store = new dojo.data.ItemFileReadStore({url: "getFavorito.action?mode=" + MODE});
		      dijit.byId(MODE).attr("value", id);
		      });
		   }

		   dijit.byId("dlgPara").hide();
		};

		var showGrdPara = function(mode) {
		   MODE = mode;
		   gDoubleClick = false;
		   console.debug("(showGrdPara) MODE [" + MODE + "] gDoubleClick [" + gDoubleClick + "]");
		   dijit.byId("dlgPara").show();
		   dijit.byId("txtFiltroPara").attr("value", "");
		   dijit.byId("grdPara").showMessage("");
		   dijit.byId("grdPara").setStore(new dojo.data.ItemFileReadStore({url: "buscarPara.action?mode=" + MODE}));
		   dijit.byId("grdPara").focus.next();
		   dijit.byId("grdPara").render();
		};

		var validarRemitente = function(idUsuarioSeleccionado) {
		   console.debug("Usuario remitente [%d]. Usuario seleccionado [%d]", idRemitente, idUsuarioSeleccionado);
		   var componente = dojo.byId("tbPlazo");

		   if(paraAprobar=="true" && !Siged.String.isEmpty(componente)){
		      if (idRemitente == idUsuarioSeleccionado ) {
		         //console.debug("Ocultando plazos...");
		         dojo.byId("tbPlazo").style.display = "none";
		         document.getElementById("destinatarioIgualRemitente").value=true;

		      } else  {
		         console.debug("Mostrando plazos...");
		         dojo.byId("tbPlazo").style.display = "";
		         document.getElementById("destinatarioIgualRemitente").value=false;
		      }
		   }
		};

		var agregarConCopia=function(){
		   //var nombre=document.getElementById("iddestinatarios").value;
		   //var id=dijit.byId("iddestinatarios").getValue();
		   var nombre = dijit.byId("idconcopia").attr("displayedValue");
		   var id = dijit.byId("idconcopia").attr("value");

		   if(nombre!=null && nombre!="" && id!=null && id!=""){
		      var existe=false;
		      var conCopia=document.getElementsByName("conCopia");
		      //"#copias span input[name='conCopia']"
		      $(conCopia).each(function(){
		         if($(this).val()==id)
		            existe=true;
		      });
		      if(!existe){
		         var copia="<span class=\"copia\">";
		         copia+="<span>"+nombre+"</span>";
		         copia+="<input type=\"hidden\" name=\"conCopia\" value=\""+id+"\" />";
		         copia+="<a href=\"#\" class=\"quitar\"><img src=\"images/eliminar.gif\" alt=\"X\" /></a></span>";
		         $("#conCopia").append(copia);
		         $(".quitar").click(function(){
		            $(this).parent().remove();
		         });
		      }
		   }
		};
		
			function aprobarMasivo(){
				if(confirm("Esta seguro que desea aprobar todos los documentos?")){
					if(dijit.byId("dlgProgresBar")){
	            		dijit.byId("dlgProgresBar").show();
	            	}
					document.getElementById("objDD.strTexto").value = dijit.byId("contenid").getValue(false);
					dojo.xhrPost({
		            	form: document.forms["frmAprobarMasivo"],
		            	load: function(){
		            		window.opener.showGridInbox();
		                    window.opener.refreshTabDXE();
		                    if(dijit.byId("dlgProgresBar")){
		                		dijit.byId("dlgProgresBar").hide();
		                	}
		                    window.close();
		            	}
					});
				}
			}
			
			dojo.addOnLoad(function(){
				Siged.Forms.combobox("iddestinatarios",{
				      id              : "idconcopia",
				      jsId            : "idconcopia",
				      storeUrl        : "buscarPara.action?mode=iddestinatario",
				      searchAttr      : "label",
				      searchDelay     : 650,
				      queryExpr       : "*${0}*",
				      autoComplete    : false,
				      hasDownArrow    : true,
				      highlightMatch  : "all",
				      required        : false,
				      onChange        : agregarConCopia
				   });
			});
		</script>
	</head>
	<body class="soria" <s:if test="cerrar!=null">onload="refrescar();"</s:if>>
		<s:if test="!flagMensaje">
		<table width="100%">
         <tr>
            <td height="14" colspan="6"></td>
         </tr>
         <tr align="center">
            <td width="1%" align="center">&nbsp;</td>
            <td colspan="5" align="left">
               <img id="idbtnEnviar"  onClick="aprobarMasivo()" src="images/enviar.bmp" border="0" alt="Aprobar" style="cursor: pointer;" class="aprobar"/>
            </td>
         </tr>
         <tr>
            <td></td>
            <td height="16" colspan="4" class="plomo">
               <s:form name="frmAprobarMasivo" action="doAprobarMasivo" method="POST">
               	  <s:hidden name="iddestinatario"/>
                  <table width="100%" border="0">
                     <tr>
                        <td></td>
                        <td>
                           Se aprobar&aacute;n masivamente los documentos : <s:property value="sOpcion" />
                        </td>
                        <td></td>
                     </tr>
                     <tr>
                        <td>
                        	Para
                        </td>
                        <td>
                            <div dojoType="dijit.form.TextBox"
                                 value="<s:property value='usuarioLogueado' />"
                                 readonly
                                 size="25"></div>
                        </td>
                        <td></td>
                     </tr> 
					<tr>
						<td>
                         <button dojoType="dijit.form.Button"
                                 type="button"
                                 id="btnCc"
                                 jsId="btnCc"
                                 onClick="showGrdPara('idconcopia')"
                                 showLabel="true">Cc</button>
                      </td>
						<td>
							<div id="iddestinatarios" ></div>									
						</td>
                        <td></td>
					</tr>
					<tr>
						<td></td>
						<td>
							<div id="conCopia"></div>
						</td>
                        <td></td>
					</tr>
					<tr>
						<td align="right" class="plomo">Asunto:</td>
						<td>
							<input dojoType="dijit.form.TextBox" id="asunto" name="objDD.strAsunto" required="false" maxlength="300" value="Aprobar Masivamente" style="width:500px;"/>
						</td>
						<td></td>
					</tr>
					<tr>
						<td colspan="3"><br/></td>
					</tr>					 
                     <tr>
                        <td></td>
                        <td>
                           <s:textfield id="objDD.strTexto" name="objDD.strTexto" cssStyle="display:none" />
                           <s:textfield id="ta" name="ta" cssStyle="display:none" />
                           <div dojoType="dijit.Editor" jsid="contenid" id="contenid" style="background-color: #fff;width: 95%;"></div>
                           <script type="text/javascript">
                              document.getElementById("contenid").innerHTML = document.getElementById("ta").value;
                           </script>
                        </td>
                        <td><br></td>
                     </tr>
                  </table>
               </s:form>
            </td>
         </tr>
         <tr>
            <td height="14"  colspan="6"></td>
         </tr>
      </table>
		</s:if>
		<s:else>
			<script type="text/javascript">
				alert("<s:property value='mensaje' escape='false'/>");
				window.close();
			</script>
		</s:else>
		<%@ include file="derivar-dialogs.jsp" %>
		<%@ include file="../pages/util/progressBar.jsp"%>
</body>
</html>