/**
 * form contains the name of the form that should be submitted.
 */
var form;

/**
 * field contains the name of the field that should get some value.
 */
var field;

/**
 * JSP specific variables
 */
var calendarWindow;
var editorWindow;
var editorValue;
var editorField;
var editorMaxlength;

/**
 * isString checks if the argument is a string
 * note: UIX & JSP
 * @param 'argument': the argument to check
 * @return: true if the 1st argument is a string, false otherwise
 */
function isString()
{
  if (arguments[0] == null)
  {
    return false;
  }
  else if (typeof arguments[0] == 'string')
  {
    return true;
  }
  else if (  (typeof arguments[0] == 'object')
          && (arguments[0].constructor)
          )
  {
    var criterion = arguments[0].constructor.toString().match(/string/i);
    return (criterion != null);
  }
  else
  {
    return false;
  }
}


/**
 * stripSpaces removes leading and trailing spaces from a string
 * note: UIX & JSP
 * @param aString: the string to strip
 * @return: the stripped string
 */
function stripSpaces(aString)
{
  if (!isString(aString))
  {
    alert("undefined string >"+aString+"< in stripSpaces");
    return null;
  }
  else
  {
    // strip leading spaces
    while (aString.substring(0,1) == " ")
    {
      aString = aString.substring(1);
    }
    // strip trailing spaces
    while (aString.substring(aString.length-1,aString.length) == " ")
    {
      aString = aString.substring(0,aString.length-1);
    }

    return aString
  }
} // stripSpaces


/**
 * isNumber checks if a string (after stripping leading and trailing spaces) represents
 * a numeric value. Empty strings are not considered to be a number.
 * This validator is more strict than the Marlin decimal validator, because it does not
 * accept values like "1a2".
 * Remark: values like "1e3" are accepted, because its interpreted as 1000 (one raised
 * to the power of three)
 * note: UIX & JSP
 * @param aString: the string to check
 * @return: true when aString represents a number, false otherwise
 */
function isNumber(aString)
{
  if (!isString(aString))
  {
    alert("undefined string >"+aString+"< in isNumber");
    return null;
  }
  else
  {
    // empty strings are not numbers
    if (stripSpaces(aString).length == 0)
    {
      return false;
    }

    // if aString evaluates to number zero, return true
    if ((aString*aString) == 0)
    {
      return true;
    }

    // in all other cases, return true if item divided by itself it returns 1
    return (aString/aString == 1);
  }
} // isNumber


/**
 * ignoreField checks if an item should be ignored when checking for changed items.
 * Items to be ignored are expected to start with an underscore (_) or be in the array
 * ignoreChangedFields
 * note: UIX & JSP
 * @param itemName: the item to be checked
 * @return: true when the item should be ignored (when the name start with an
 * underscore or when it is in the array ignoreChangedFields), false otherwise
 */
function ignoreField(itemName)
{
  debug("In ignoreField with itemName: "+itemName);

  itemName = stripRownumFromName(itemName);

  if (itemName.substring(0,1) == "_")
  {
    return true;
  }

  if (ignoreChangedFields)
  {
    for (var j = 0 ; j < ignoreChangedFields.length ; j++)
    {
      if (ignoreChangedFields[j] == itemName)
      {
        return true;
      }
    }
  }

  // return false in all other cases
  return false;

} // ignoreField


/**
 * hasChanges loops over all forms in the document and checks if one or more of the form items
 * have been changed by the user. If there are (unsumitted) changes, the user is asked
 * whether he wants to leave the page, or return to the page and save the changes. By calling
 * ignoreField, some of the items are ignored by hasChanges().
 * note: UIX & JSP
 * @return: true when there are unsubmitted changes (that might be because of an error on
 * the page), false otherwise
 */
function hasChanges()
{
  for (var i = 0 ; i < document.forms.length ; i++)
  {
    var form = document.forms[i];

    debug("checking form >"+form.name+"< for changes");

    // return true when there are errors on the page
    if (form.elements["HasErrors"])
    {
      if (stripSpaces(form.elements["HasErrors"].value) != "" )
      {
        debug("hasChanges returns true because HasErrors has a value");
        return true;
      }
      // else it concerns a select page
    }

    // return true when one of the items in any form have been changed by the user
    for (var k = 0 ; k < form.elements.length ; k++)
    {
      var item = form.elements[k];

      if (  !ignoreField(item.name)
         && userHasChanged(item)
         )
      {
        debug("hasChanges returns true because item >"+item.name+"< has changed");
        return true;
      }
    }
  }

  // in any other case, return false
  debug("hasChanges returns false");
  return false;
} // hasChanges


/**
 * confirmChanges checks if one of the form items has changed by calling hasChanges
 * If so, it asks the user to confirm if he wants to leave the page without saving,
 * or return to the same page. After the user has the opportunity to save the changes.
 * note: UIX & JSP
 * @return; true when there are no changes or when the user choose to leave the page
 * whithout saving them, false otherwise.
 */
function confirmChanges()
{
  var anyFormChanged = hasChanges();

  var confirmed;

  if (anyFormChanged)
  {
    confirmed = confirm(getMessage("hasChanged"));
  }

  if (!anyFormChanged || confirmed)
  {
    return true;
  }
  else
  {
    return false;
  }
} // confirmChanges


/**
 * DESCRIPTION TO BE PROVIDED
 * note: UIX
 */
function editorPopup(destination, formName, itemName, maxLength)
{
  destination += (destination.indexOf("?") == -1)
                 ? "?"
                 : "&";
  if (maxLength != null)
  {
    destination += "maxLength=" + maxLength;
  }
  top.editorFieldValue=document.forms[formName][itemName].value;
  popup(destination,formName,itemName,670,320);
} // editorPopup


/**
 * DESCRIPTION TO BE PROVIDED
 * note: UIX
 */
function popup(destination, formName, displayItemName, windowWidth, windowHeight, valueItemName)
{
  var displayField = document.forms[formName][displayItemName];

  var calWindow = openWindow( self
                            , destination
                            , "ModalWindow"
                            , {width:windowWidth, height:windowHeight}
                            , true
                            , "dialog"
                            , returnValue
                            );
  calWindow.displayField = displayField;
  if (valueItemName)
  {
    calWindow.valueField = document.forms[formName][valueItemName];
  }
} // popup



/**
 * replace replaces all occurences of a specific text in a string
 * note: UIX & JSP (should move to utility library)
 * @param string: the string for which text needs to be replaced
 * @param text: the text that needs to be replaced
 * @param by: the replacement text
 */
function replace(aString, text, by)
{
  if (  !isString(aString)
     || !isString(text)
     || !isString(by)
     )
  {
    alert("undefined aString >"+aString+", "+text+", "+by+"< in replace");
    return null;
  }
  else
  {
    var strLength = aString.length;
    var txtLength = text.length;

    // return the original string when either the string or the text to be
    // replaced have length 0
    if (  (strLength == 0)
       || (txtLength == 0)
       )
    {
      return aString;
    }

    var i = aString.indexOf(text);
    if (  (!i)
       && (text != aString.substring(0,txtLength))
       )
    {
      return aString;
    }

    if (i == -1)
    {
      return aString;
    }

    var newstr = aString.substring(0,i) + by;
    if ((i + txtLength) < strLength)
    {
      newstr += replace(aString.substring(i+txtLength,strLength),text,by);
    }

    return newstr;
  }
} // replace


/**
 * Translates a string into a proper url 
 *
 * note: UIX & JSP (should move to utility library)
 */
function toURLString(text)
{
  text = replace(text,'"',unescape('%22'));
  text = replace(text,'&',unescape('%26'));
  text = replace(text,'<',unescape('%3C'));
  text = replace(text,'>',unescape('%3E'));
  text = replace(text,' ',unescape('%A0'));
  text = replace(text,'¡',unescape('%A1'));
  text = replace(text,'¢',unescape('%A2'));
  text = replace(text,'£',unescape('%A3'));
  text = replace(text,'¥',unescape('%A5'));
  text = replace(text,'¦',unescape('%A6'));
  text = replace(text,'§',unescape('%A7'));
  text = replace(text,'¨',unescape('%A8'));
  text = replace(text,'©',unescape('%A9'));
  text = replace(text,'ª',unescape('%AA'));
  text = replace(text,'«',unescape('%AB'));
  text = replace(text,'¬',unescape('%AC'));
  text = replace(text,'­',unescape('%AD'));
  text = replace(text,'®',unescape('%AE'));
  text = replace(text,'¯',unescape('%AF'));
  text = replace(text,'°',unescape('%B0'));
  text = replace(text,'±',unescape('%B1'));
  text = replace(text,'²',unescape('%B2'));
  text = replace(text,'³',unescape('%B3'));
  text = replace(text,'´',unescape('%B4'));
  text = replace(text,'µ',unescape('%B5'));
  text = replace(text,'¶',unescape('%B6'));
  text = replace(text,'·',unescape('%B7'));
  text = replace(text,'¸',unescape('%B8'));
  text = replace(text,'¹',unescape('%B9'));
  text = replace(text,'º',unescape('%BA'));
  text = replace(text,'»',unescape('%BB'));
  text = replace(text,'¼',unescape('%BC'));
  text = replace(text,'½',unescape('%BD'));
  text = replace(text,'¾',unescape('%BE'));
  text = replace(text,'¿',unescape('%BF'));
  text = replace(text,'‘',unescape('%D1'));
  text = replace(text,'’',unescape('%D2'));

  return text;
} // toURLString


/**
 * Opens an editor window. 
 *
 * note: JSP
 * @param destination The url pointing to the editor
 * @param itemName The field in which the result of the editor will be stored
 * @param maxLength The maximum amount of characters allowed in the editor. 
 */
function jspEditorPopup(destination, itemName, maxLength)
{
  editorMaxlength = maxLength;

  editorField = getFormElement(itemName);
  editorValue = editorField.value;

  editorWindow = window.open
  (
    destination
  , "editor"
  , "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no," +
    "resizable=no,dependent=yes, width=350, height=190, screenX=200, screenY=300"
  );

} // editorPopup


/**
 * Opens the calendar window. Before opening the window it will call the functions
 * setDateField and setDateFormat in calendar.js such that the calendar knows
 * where to put the result of the calendar window (setDateField) and which date format
 * to use (setDateFormat).
 *
 * note: JSP
 * @param itemName The element where the result of the calendarWindow
 *                 should be put
 * @param format   The date format
 */
function calendarPopup(itemName, format)
{
  setDateField(getFormElement(itemName)); 
  setDateFormat(format);
  alert("la fecha es..."); 
  calendarWindow = window.open
  (
    "../include/calendar.html"
  , "calendar"
  , "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,dependent=yes, width=210, height=230, screenX=200, screenY=300"
  );
} // lovPopup

/**
 * Loops through all the forms and look for the first occurence of an element with name
 * elementName.
 *
 * note: JSP
 * @param elementName The name of the element to look for
 * @result The first occurence of the element with name elementName. null, if no
 *         element with name elementName could be found
 */
function getFormElement(elementName)
{
  var result = null;

  for (var i = 0 ; i < document.forms.length ; i++)
  {
    var searchForm = document.forms[i];
    if (searchForm.elements[elementName])
    {
      result = searchForm.elements[elementName];
    }
  }

  return result;
}

/**
 * Loops through all the forms and looks for the first occurence of an element with name
 * elementName. The function returns the value of this element. 
 *
 * note: JSP
 * @param elementName The name of the element to look for
 * @result The value of the first occurence of the element with name elementName. null, if no
 *         element with name elementName could be found
 */
function getFormElementValue(elementName)
{
  var result = null;
  var formElement = getFormElement(elementName);
  if (formElement)
  {
    result = formElement.value;
  } 
  return result;
}

function validateForm(formName, name,value)
{
  var form    = document.forms[formName];
  
  if((formName==null)||(name==null)||(value==null))
    return true;
  if((name=="")||(value==""))
    return true;
    
  objs =  name.split("-");
  objsValue =  value.split("-");
  
  var mess="";
    for(m=0; m<objsValue.length; m++) {     
     v=document.getElementsByName(objsValue[m]).item(0).value;     
  
     if((v==null)||(esVacio(v))){
        mess=mess+" -"+objs[m] ;//alert("Debe ingresar "+objs[m]);
     }
    }
    if(!esVacio(mess)) {
      alert("Debe ingresar :"+mess);
      return false;
    }else{
      return true;
    }
} // validateForm

/*************************************************************
    ES VACIO
**************************************************************/
function esVacio(cad) { //retorna true si la cadena está vacía 
	var es_vacio;
    var blanco = " \n\t" + String.fromCharCode(13); // blancos
	if ((cad == null) || (cad.length == 0)) { es_vacio=true }
		else
		{
    		for(i = 0, es_vacio = true; (i < cad.length) && es_vacio; i++)
      			es_vacio = blanco.indexOf(cad.charAt(i)) != - 1;
		}
	return es_vacio;
}
/*************************************************************
    direccionar
**************************************************************/
function ir_pagina(frm,metodo){
        f = document.forms[frm];
        f.method.value=metodo;        
        f.submit(); 
}
function ir_accion(frm,metodo,accion){          
        f = document.forms[frm];
        f.method.value=metodo;    
        f.action=accion;
        f.submit(); 
}
function esTeclaNumero(e) {
var valid = "0123456789";
var key = String.fromCharCode(event.keyCode);
	if (valid.indexOf("" + key) == "-1") return false;
}

function maximize(){
  if (window.screen) {
    var aw = screen.availWidth;
    var ah = screen.availHeight;
    window.moveTo(0, 0);
    window.resizeTo(aw, ah);
  }
}

 function overMouse(obj){
		obj.style.background = "#e1f3ff";
  }
	
 function outMouse(obj){
	obj.style.background = "#ffffff";
  }
		