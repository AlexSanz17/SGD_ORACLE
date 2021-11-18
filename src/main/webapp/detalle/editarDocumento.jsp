<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<script type="text/javascript">


		function setCriteriosConfidencialidad(){
			if (dijit.byId("criteriosConfidencialidad").value=="<s:property value='@org.ositran.utils.Constantes@CRITERIOS_CONFIDENCIAL_MATERIA_EXCEPTUADA'/>"){
				document.getElementById('divLey').style.display = 'block';
			}else{
				document.getElementById('divLey').style.display = 'none';
			}
		}

		 function fConfidencial(){
			 if (document.getElementById("confidencialDocMod").checked ==true){
				 document.getElementById('divCriteriosConfidencialidad').style.display = 'block';
	         }else{
	        	 document.getElementById('divCriteriosConfidencialidad').style.display = 'none';
	        	 document.getElementById('divLey').style.display = 'none';
	         }
		 }

		 var storeCriteriosConfidencialidad = new dojo.data.ItemFileReadStore({url: "autocompletarCriteriosConfidencialidad.action"});


	     function ED_Guardar(){

				if (document.getElementById("confidencialDocMod").checked ==true){
					   if (dijit.byId("criteriosConfidencialidad").value.trim()==''){
						   alert("Debe seleccionar un criterio de confidencialidad.");
						   return;
					   }

					   if (dijit.byId("criteriosConfidencialidad").value=="<s:property value='@org.ositran.utils.Constantes@CRITERIOS_CONFIDENCIAL_MATERIA_EXCEPTUADA'/>"){
						   if (dijit.byId("ley").value.trim()=='' || dijit.byId("articulo").value.trim()=='' || dijit.byId("inciso").value.trim()==''){
							   alert("Debe ingresar la ley, articulo e inciso.");
							   return;
						   }
					   }
				 }


				if(dijit.byId("dlgProgresBar")!=null){
					dijit.byId("dlgProgresBar").show() ;
				}

				var checkboxes = document.getElementsByName("confidencialDocMod");
				if(checkboxes[0].checked){
					document.getElementById("confidencialDocMod").value = "<s:property value='@org.ositran.utils.Constantes@Si' />";
				}else{
					document.getElementById("confidencialDocMod").value = "<s:property value='@org.ositran.utils.Constantes@No' />";
				}

				dojo.xhrPost({
					form : dojo.byId("ED_formulario"),
					load : function(){
						if(dijit.byId("dlgProgresBar")!=null){
							dijit.byId("dlgProgresBar").hide() ;
						}

						showGridInbox(sTipoGridActual);

						dijit.byId("dlgModificarDoc").hide();
				    	dijit.byId("dlgModificarDoc").destroyRecursive();
					}
				});
			}
		</script>
	</head>
	<body>
		<form id="ED_formulario" action="modificarDocumento.action" method="post">
			<input name="iIdDoc" type="hidden" value="<s:property value='objDocumento.idDocumento'/>"/>
			<p>
				<label>Asunto</label>
				<input dojoType="dijit.form.TextBox" name="asuntoObservacion" value="<s:property value='objDocumento.asunto'/>" style="width: 500px; position:relative; left:41px;"/>
			</p>

			<p>
				<label>Fecha</label>
				<input name="objDD.strFechaDocumento" dojoType="dijit.form.DateTextBox" constraints="{datePattern:'dd/MM/yyyy'}" invalidMessage="Ingrese fecha de Documento dd/MM/yyyy" displayedValue="<s:property value='objDD.strFechaDocumento'/>" style="position:relative; left:45px;"/>
			</p>

			<p>
				<label>Documentos Referenciados</label>
				<input dojoType="dijit.form.TextBox" name="referencia" value="<s:property value='objDocumento.referenciados'/>" style="width: 412px; position:relative; left:10px;"/>
			</p>

			<p>
				<label style="vertical-align: top;">Observaci&oacute;n</label>
				<input name="ultimaObservacion" class="dijitArea" type="text" dojoType="dijit.form.Textarea" value="<s:property value='objDocumento.observacion'/>" style="width: 500px; position:relative; left:10px;"/>
			</p>

			<p>
				<label style="vertical-align: top;">Confidencial</label>
				<input name="confidencialDocMod" id="confidencialDocMod" type="checkbox"   <s:if test="objDocumento.confidencial.equals('S')"> checked </s:if> value="<s:property value='objDocumento.confidencial'/>"  onclick="fConfidencial();" style="width: 35px; position:relative; left:1px;"/>
			</p>

			<p>


			    <s:if test="objDocumento.confidencial.equals('S')">
				      <div id="divCriteriosConfidencialidad">
	                    <label>Criterios Confidencialidad  </label>
	                     <select dojoType="dijit.form.FilteringSelect"
				                id="criteriosConfidencialidad"
				                name="criteriosConfidencialidad"
								value="<s:property value='objDocumento.crt_confidencialidad' />"
				                idAttr="id"
				                labelAttr="label"
				                style="width:430px;"
				                required="false"
				                searchAttr="label"
								onChange= "setCriteriosConfidencialidad();"
				                store="storeCriteriosConfidencialidad"></select>
	                   </div>

                        <s:if test="objDocumento.crt_confidencialidad!=null && objDocumento.crt_confidencialidad.equals('<s:property value='@org.ositran.utils.Constantes@CRITERIOS_CONFIDENCIAL_MATERIA_EXCEPTUADA'/>')">
	                               Ley <input dojoType="dijit.form.ValidationTextBox" id="ley" jsId="ley" name="ley" style="width:64px"   size="7" maxlength="5"/>
			                       Articulo <input dojoType="dijit.form.ValidationTextBox" id="articulo" jsId="articulo" name="articulo" style="width:64px" size="7" maxlength="5"/>
			                       Inciso <input dojoType="dijit.form.ValidationTextBox" id="inciso" jsId="inciso" name="inciso" style="width:62px" size="7" maxlength="5"/>
				        </s:if>
				        <s:else>
				             <div id="divLey" style="display: none;">
				                  Ley <input dojoType="dijit.form.ValidationTextBox" id="ley" jsId="ley" name="ley" style="width:64px"   size="7" maxlength="5"/>
			                       Articulo <input dojoType="dijit.form.ValidationTextBox" id="articulo" jsId="articulo" name="articulo" style="width:64px" size="7" maxlength="5"/>
			                       Inciso <input dojoType="dijit.form.ValidationTextBox" id="inciso" jsId="inciso" name="inciso" style="width:64px" size="7" maxlength="5"/>
			                 </div>
				        </s:else>
			    </s:if>
			    <s:else>
			       <div id="divCriteriosConfidencialidad" style="display: none;">
                            <label>Criterios Confidencialidad  </label>
                                 <select dojoType="dijit.form.FilteringSelect"
			                id="criteriosConfidencialidad"
			                name="criteriosConfidencialidad"
					value="<s:property value='objDocumento.crt_confidencialidad' />"
			                idAttr="id"
			                labelAttr="label"
			                style="width:430px;"
			                required="false"
			                searchAttr="label"
							onChange= "setCriteriosConfidencialidad();"
			                store="storeCriteriosConfidencialidad"></select>
                   </div>
                   <div id="divLey" style="display: none;">
	                      Ley      <input dojoType="dijit.form.ValidationTextBox" id="ley" jsId="ley" name="ley" style="width:64px"   size="7" maxlength="5"/>
			              Articulo <input dojoType="dijit.form.ValidationTextBox" id="articulo" jsId="articulo" name="articulo" style="width:64px" size="7" maxlength="5"/>
			              Inciso   <input dojoType="dijit.form.ValidationTextBox" id="inciso" jsId="inciso" name="inciso" style="width:64px" size="7" maxlength="5"/>
				    </div>
			    </s:else>
			  </div>
			</p>
			<p>
				<div dojoType="dijit.form.Button"
	                 id="ED_Guardar"
	                 iconClass="siged16 sigedSave16"
	                 onClick="ED_Guardar()"
	                 showLabel="true">Guardar</div>
			</p>
		</form>
	</body>
</html>