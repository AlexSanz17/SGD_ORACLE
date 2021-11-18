//Tiene todas las funciones relacionadas a los formularios
Siged.Forms = {};

Siged.Forms.combobox = function(applyTo, params) {
   if (!dojo.byId(applyTo)) {
      console.warn("[%s] DOESN'T EXIST!", applyTo);

      return null;
   }

   try {
      var combo = new dijit.form.FilteringSelect({
         id             : params['id'],
         jsId           : params['jsId'],
         name           : params['name'],
         searchAttr     : params['searchAttr'],
         searchDelay    : params['searchDelay'],
         queryExpr      : params['queryExpr'],
         autoComplete   : params['autoComplete'],
         hasDownArrow   : params['hasDownArrow'],
         highlightMatch : params['highlightMatch'],
         required       : params['required'],
         style          : params['style']
      }, applyTo);

      if (params['invalidMessage'] != undefined) {
         combo.invalidMessage = params['invalidMessage'];
      }

      if (params['onChange'] != undefined) {
         combo.onChange = params['onChange'];
      }

      if (params['onFocus'] != undefined) {
         combo.onFocus = params['onFocus'];
      }

      if (params['promptMessage'] != undefined) {
         combo.promptMessage = params['promptMessage'];
      }

      if (params['storeUrl'] != undefined) {
         combo.store = new dojo.data.ItemFileReadStore({
            url: params['storeUrl']
         });
      }

      if (params['value'] != undefined) {
         combo.value = params['value'];
      }

      return combo;
   } catch (err) {
      console.error("No se pudo crear el objeto asociado a [%s] error [%s]", applyTo, err);
   }

   return null;
}

Siged.Forms.comboboxChangeStore = function(combobox, url, customValue) {
   var sComboBox = new String(combobox);
   var sUrl = new String(url);
   var sCustomValue = new String(customValue);

   if (dijit.byId(sComboBox)) {
      dijit.byId(sComboBox).attr("displayedValue", "Cargando data...");
      dijit.byId(sComboBox).attr("disabled", true);
      dijit.byId(sComboBox).store = new dojo.data.ItemFileReadStore({
         url: sUrl
      });
      dijit.byId(sComboBox).attr("value", "");
      dijit.byId(sComboBox).attr("displayedValue", "");
      dijit.byId(sComboBox).attr("disabled", false);

      if (!Siged.String.isEmpty(sCustomValue)) {
         dijit.byId(sComboBox).attr("value", sCustomValue);
      }
   }
};

Siged.Forms.comboboxCleanDisplayedValue = function(combobox) {
   var sComboBox = new String(combobox);

   if (dijit.byId(sComboBox)) {
      dijit.byId(sComboBox).attr("value", "");
      dijit.byId(sComboBox).attr("displayedValue", "");
   }
};

Siged.Forms.radiobuttonGetCheckedByName = function(name) {
   var sName = new String(name);
   var inputName = "";

   dojo.query("input[name='" + sName + "']").some(function(node) {
      inputName = node.value;

      return node.checked;
   });

   return inputName;
};

Siged.Forms.radiobuttonResetByName = function(name, value) {
   var sName = new String(name);
   var sValue = new String(value);

   dojo.query("input[name='" + sName + "']").forEach(function(node) {
      dijit.byId(node.id).attr("checked", false);
      dijit.byId(node.id).attr("readOnly", false);

      if (node.value.toLowerCase() == sValue.toLowerCase()) {
         dijit.byId(node.id).attr("checked", true);
      }
   });
};

Siged.Forms.selectAddOption = function(select, value, displayedValue) {
   var sSelect = new String(select);
   var sValue = new String(value);
   var sDisplayedValue = new String(displayedValue);
   var option = dojo.doc.createElement("option");

   option.value = sValue;
   option.innerHTML = sDisplayedValue;
   dojo.byId(sSelect).appendChild(option);
};

Siged.Forms.selectCleanIt = function(select) {
   var sSelect = new String(select);

   if (dojo.byId(sSelect)) {
      while (dojo.byId(sSelect).firstChild) {
         dojo.byId(sSelect).removeChild(dojo.byId(sSelect).firstChild);
      }
   }
};

Siged.Forms.selectHasOption = function(select, value) {
   var sSelect = new String(select);
   var sValue = new String(value);

   return dojo.query("option", dojo.byId(sSelect)).some(function(option) {
      return option.value == sValue;
   });
};

Siged.Forms.selectSelectAll = function(select) {
   var sSelect = new String(select);

   dojo.query("option", dojo.byId(sSelect)).forEach(function(option) {
      option.selected = true;
   });
};

/*Siged.Forms.selectUpdateOption = function(select, value, displayedValue) {
   for (var i = 0; i < dojo.byId(select).options.length; i++) {
      if (dojo.byId(select).options[i].value == value) {
         dojo.byId(select).options[i] = new Option(displayedValue, value);
         break;
      }
   }
};*/
