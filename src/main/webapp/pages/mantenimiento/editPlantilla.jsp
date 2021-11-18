<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <title>Tramite Documentario</title>
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "js/dojo/dojox/grid/resources/soriaGrid.css";
         @import "css/estilo.css";
         @import "css/sigedIconos.css";
      </style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug:false"></script>
      <script type="text/javascript" src="js/dojo/dijit/dijit.js"></script>
      <script type="text/javascript">
         dojo.require("dijit.form.Button");
         dojo.require("dijit.form.FilteringSelect");
         dojo.require("dijit.form.Form");
         dojo.require("dijit.form.TextBox");
         dojo.require("dojo.data.ItemFileWriteStore");
         dojo.require("dojo.rpc.JsonService");
         dojo.require("dojox.grid.DataGrid");

         var iIdPlantilla = "<s:property value='objPlantilla.idplantilla' />";
         var iTipoPlantilla = "<s:property value='objPlantilla.tipo' />";
      </script>
      <script type="text/javascript" src="js/siged/plantilla.js"></script>
   </head>
   <body class="soria">
      <button dojoType="dijit.form.Button"
              type="button"
              iconClass="siged16 sigedSave16"
              onClick="createPlantilla"
              showLabel="true">Registrar</button>
      <s:if test="objPlantilla.idplantilla != null">
         <button dojoType="dijit.form.Button"
                 type="button"
                 iconClass="siged16 sigedSave16"
                 onClick="deletePlantilla"
                 showLabel="true">Eliminar</button>
      </s:if>
      <div dojoType="dijit.form.Form" id="frmNuevoPlantilla" jsId="frmNuevoPlantilla">
         <table width="100%">
            <tr>
               <td colspan="4"><div id="showErrorPlantilla" style="color:red;font-weight:bold;">&nbsp;</div></td>
            </tr>
            <tr>
               <td colspan="4">
                  <div dojoType="dijit.form.TextBox"
                       type="text"
                       id="objPlantilla.idplantilla"
                       jsId="objPlantilla.idplantilla"
                       name="objPlantilla.idplantilla"
                       style="display:none;"
                       value="<s:property value='objPlantilla.idplantilla' />"></div>
               </td>
            </tr>
            <tr>
               <td width="15%">Nombre :</td>
               <td width="85%" colspan="3">
                  <div dojoType="dijit.form.TextBox"
                       type="text"
                       id="objPlantilla.descripcion"
                       jsId="objPlantilla.descripcion"
                       name="objPlantilla.descripcion"
                       value="<s:property value='objPlantilla.descripcion' />"></div>
               </td>
            </tr>
            <tr>
               <td>Tipo de Documento :</td>
               <td colspan="3">
                  <div dojoType="dijit.form.FilteringSelect"
                       id="objPlantilla.tipo"
                       jsId="objPlantilla.tipo"
                       name="objPlantilla.tipo"
                       required="false"
                       searchAttr="label"
                       store="storeTipoDocumento"
                       value="<s:property value='objPlantilla.tipo' />"></div>
               </td>
            </tr>
            <tr>
               <td>Campos :</td>
               <td colspan="3">
                  <button dojoType=dijit.form.Button
                          iconClass="siged16 sigedSave16"
                          onClick="addCampo"
                          showLabel="true">Agregar</button>
                  <button dojoType=dijit.form.Button
                          iconClass="siged16 sigedSave16"
                          onClick="deleteCampo"
                          showLabel="true">Eliminar</button>
               </td>
            </tr>
            <tr>
               <td>&nbsp;</td>
               <td colspan="3">
                  <div dojoType="dojox.grid.DataGrid"
                       id="gridCampo"
                       jsId="gridCampo"
                       rowsPerPage="20"
                       rowSelector="10px"
                       style="width:100%;height:400px;"></div>
                  <div dojoType="dijit.form.TextBox"
                       type="text"
                       id="objPlantilla.campos"
                       jsId="objPlantilla.campos"
                       name="objPlantilla.campos"
                       style="display:none;"></div>
               </td>
            </tr>
         </table>
      </div>
   </body>
</html>
