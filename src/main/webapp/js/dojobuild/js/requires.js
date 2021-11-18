/*
	Copyright (c) 2004-2009, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


if(!dojo._hasResource["js.requires"]){dojo._hasResource["js.requires"]=true;dojo.provide("js.requires");dojo.declare("siged.TabContainer",dijit.layout.TabContainer,{addChild:function(_1){this.inherited(arguments);if(!_1["setTitle"]&&_1.title){dojo.mixin(_1,{setTitle:function(_2){this.title=_2;this.controlButton.containerNode.innerHTML=_2||"";}});}}});}