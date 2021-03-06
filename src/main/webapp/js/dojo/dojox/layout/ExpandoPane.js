/*
	Copyright (c) 2004-2009, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


if(!dojo._hasResource["dojox.layout.ExpandoPane"]){ //_hasResource checks added by build. Do not use _hasResource directly in your code.
dojo._hasResource["dojox.layout.ExpandoPane"] = true;
dojo.provide("dojox.layout.ExpandoPane");
dojo.experimental("dojox.layout.ExpandoPane"); // just to show it can be done?

dojo.require("dojox.layout.ContentPane");
dojo.require("dijit._Templated");
dojo.require("dijit._Container");

dojo.declare("dojox.layout.ExpandoPane",
	[dojox.layout.ContentPane, dijit._Templated, dijit._Contained],
	{
	// summary: An experimental collapsing-pane for dijit.layout.BorderContainer
	//
	// description:
	//		Works just like a ContentPane inside of a borderContainer. Will expand/collapse on
	//		command, and supports having Layout Children as direct descendants
	//

	maxHeight: "",
	maxWidth: "",
	splitter: "",

	templateString:"<div class=\"dojoxExpandoPane\" dojoAttachEvent=\"ondblclick:toggle\" >\r\n\t<div dojoAttachPoint=\"titleWrapper\" class=\"dojoxExpandoTitle\">\r\n\t\t<div class=\"dojoxExpandoIcon\" dojoAttachPoint=\"iconNode\" dojoAttachEvent=\"onclick:toggle\"><span class=\"a11yNode\">X</span></div>\t\t\t\r\n\t\t<span class=\"dojoxExpandoTitleNode\" dojoAttachPoint=\"titleNode\">${title}</span>\r\n\t</div>\r\n\t<div class=\"dojoxExpandoWrapper\" dojoAttachPoint=\"cwrapper\" dojoAttachEvent=\"ondblclick:_trap\">\r\n\t\t<div class=\"dojoxExpandoContent\" dojoAttachPoint=\"containerNode\"></div>\r\n\t</div>\r\n</div>\r\n",

	// easeOut: String|Function
	//		easing function used to hide pane
	easeOut: "dojo._DefaultEasing",

	// easeIn: String|Function
	//		easing function use to show pane
	easeIn: "dojo._DefaultEasing",

	// duration: Integer
	//		duration to run show/hide animations
	duration: 420,

	// startExpanded: Boolean
	//		Does this widget start in an open (true) or closed (false) state
	startExpanded: true,

	baseClass: "dijitExpandoPane",

	postCreate: function(){
		this.inherited(arguments);
		this._animConnects = [];

		this._isHorizontal = true;

		if(dojo.isString(this.easeOut)){
			this.easeOut = dojo.getObject(this.easeOut);
		}
		if(dojo.isString(this.easeIn)){
			this.easeIn = dojo.getObject(this.easeIn);
		}

		var thisClass = "", rtl = !this.isLeftToRight();
		if(this.region){
			switch(this.region){
				case "trailing" :
				case "right" :
					thisClass = rtl ? "Left" : "Right";
					break;
				case "leading" :
				case "left" :
					thisClass = rtl ? "Right" : "Left";
					break;
				case "top" :
					thisClass = "Top";
					break;
				case "bottom" :
					thisClass = "Bottom";
					break;
			}
			dojo.addClass(this.domNode, "dojoxExpando" + thisClass);
			this._isHorizontal = /top|bottom/.test(this.region);
		}
		dojo.style(this.domNode, {
			overflow: "hidden",
			padding:0
		});
	},

	_startupSizes: function(){

		this._container = this.getParent();
		this._closedSize = this._titleHeight = dojo.marginBox(this.titleWrapper).h;

		if(this.splitter){
			// find our splitter and tie into it's drag logic
			var myid = this.id;
			dijit.registry.filter(function(w){
				return w && w.child && w.child.id == myid;
			}).forEach(dojo.hitch(this,function(w){
				this.connect(w,"_stopDrag","_afterResize");
			}));
		}

		this._currentSize = dojo.contentBox(this.domNode);	// TODO: can compute this from passed in value to resize(), see _LayoutWidget for example
		this._showSize = this._currentSize[(this._isHorizontal ? "h" : "w")];
		this._setupAnims();

		if(this.startExpanded){
			this._showing = true;
		}else{
			this._showing = false;
			this._hideWrapper();
			this._hideAnim.gotoPercent(99,true);
		}

		this._hasSizes = true;
	},

	_afterResize: function(e){
		var tmp = this._currentSize;						// the old size
		this._currentSize = dojo.marginBox(this.domNode);	// the new size
		var n = this._currentSize[(this._isHorizontal ? "h" : "w")]
		if(n > this._titleHeight){
			if(!this._showing){
				this._showing = !this._showing;
				this._showEnd();
			}
			this._showSize = n;
			this._setupAnims();
		}else{
			this._showSize = tmp[(this._isHorizontal ? "h" : "w")];
			this._showing = false;
			this._hideWrapper();
			this._hideAnim.gotoPercent(89,true);
		}

	},

	_setupAnims: function(){
		// summary: Create the show and hide animations
		dojo.forEach(this._animConnects, dojo.disconnect);

		var _common = {
				node:this.domNode,
				duration:this.duration
			},
			isHorizontal = this._isHorizontal,
			showProps = {},
			hideProps = {},
			dimension = isHorizontal ? "height" : "width"
		;

		showProps[dimension] = {
			end: this._showSize,
			unit:"px"
		};
		hideProps[dimension] = {
			end: this._closedSize,
			unit:"px"
		};

		this._showAnim = dojo.animateProperty(dojo.mixin(_common,{
			easing:this.easeIn,
			properties: showProps
		}));
		this._hideAnim = dojo.animateProperty(dojo.mixin(_common,{
			easing:this.easeOut,
			properties: hideProps
		}));

		this._animConnects = [
			dojo.connect(this._showAnim, "onEnd", this, "_showEnd"),
			dojo.connect(this._hideAnim, "onEnd", this, "_hideEnd")
		];
	},

	toggle: function(){
		// summary: Toggle this pane's visibility
		if(this._showing){
			this._hideWrapper();
			this._showAnim && this._showAnim.stop();
			this._hideAnim.play();
		}else{
			this._hideAnim && this._hideAnim.stop();
			this._showAnim.play();
		}
		this._showing = !this._showing;
	},

	_hideWrapper: function(){
		// summary: Set the Expando state to "closed"
		dojo.addClass(this.domNode, "dojoxExpandoClosed");
		dojo.style(this.cwrapper,{
			visibility: "hidden",
			opacity: "0",
			overflow: "hidden"
		});
	},

	_showEnd: function(){
		// summary: Common animation onEnd code - "unclose"
		dojo.style(this.cwrapper, { opacity: 0, visibility:"visible" });
		dojo.fadeIn({ node:this.cwrapper, duration:227 }).play(1);
		dojo.removeClass(this.domNode, "dojoxExpandoClosed");
		setTimeout(dojo.hitch(this._container, "layout"), 15);
	},

	_hideEnd: function(){
		// summary: Callback for the hide animation - "close"
		setTimeout(dojo.hitch(this._container, "layout"), 15);
	},

	resize: function(/* Object? */psize){
		// summary: we aren't a layout widget, but need to act like one:
		//
		// psize: Object
		//		The size object optionally passed to us by our parent.

		if(!this._hasSizes){ this._startupSizes(psize); }

		// compute size of container (ie, size left over after title bar)
		// it looks like two marginBox calls, but sometimes psize comes in with only one member
		var	size = (psize && psize.h) ? psize : dojo.marginBox(this.domNode);
		this._contentBox = {
			w: size.w || dojo.marginBox(this.domNode).w,
			h: size.h - this._titleHeight
		};

		dojo.style(this.containerNode, "height", this._contentBox.h + "px");
		this._layoutChildren();
	},

	_trap: function(e){
		// summary: Trap stray events
		dojo.stopEvent(e);
	}

});

}
