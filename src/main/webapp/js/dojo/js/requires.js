dojo.provide("js.requires");

/*dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.layout.AccordionContainer");
dojo.require("dijit.layout.BorderContainer");
dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.Toolbar");
dojo.require("dojo.data.ItemFileWriteStore");
dojo.require("dojo.rpc.JsonService");
dojo.require("dojox.grid.DataGrid");
dojo.require("dojox.layout.ContentPane");
dojo.require("dojox.layout.ExpandoPane");
dojo.require("dojox.widget.PlaceholderMenuItem");

dojo.require("dijit.Declaration");
dojo.require("dijit.form.ComboBox");
dojo.require("dijit.Editor");


dojo.require("dijit.Menu");
dojo.require("dijit.MenuItem");
dojo.require("dijit.MenuSeparator");
dojo.require("dijit.tree.ForestStoreModel");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dijit.Tree");
dojo.require("dojox.data.QueryReadStore");

dojo.require("dijit.Dialog");



dojo.require("dijit.form.FilteringSelect");
dojo.require("dijit.form.RadioButton");

dojo.require("dijit.TitlePane");


dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.DateTextBox");


dojo.require("dojo.parser");

dojo.require("dojox.grid._CheckBoxSelector");



////
dojo.require("dijit.dijit");
dojo.require("dijit.ProgressBar");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("dojox.form.FileUploader");
dojo.require("dojox.embed.Flash");



dojo.require("dojox.layout.ToggleSplitter");*/

dojo.require("dijit.form.TimeTextBox");
dojo.require("dojox.data.QueryReadStore");
dojo.require("dijit.form.NumberTextBox");
dojo.declare("siged.TabContainer", dijit.layout.TabContainer, {
   addChild: function(child){
      this.inherited(arguments);

      if (!child["setTitle"] && child.title) {
         dojo.mixin(child, {
            setTitle: function(title) {
               this.title = title;
               this.controlButton.containerNode.innerHTML = title || "";
            }
         });
      }
   }
});
