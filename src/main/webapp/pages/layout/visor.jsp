<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<script type="text/javascript" src="js/pdfViewer/pdfobject.js"></script>
	<script type="text/javascript">
		var myPDF = new PDFObject({ 
	    	url: 'http://servicios.apn.gob.pe:8090/alfresco/download/direct/workspace/SpacesStore/d1300d49-1a7d-42da-aa36-425ca2d4ba0c/MEMORANDO%20506-2011-OODD.pdf?ticket=TICKET_591e33506ae4a635791892b34a6602329bef63ff'
	    }).embed('divPDF');
		
		var treeGetIcon=function(objNodo,opened){
			var sinsplit=""+objNodo.id;
			var tipo=sinsplit.split("-")[0];
			var id=sinsplit.split("-")[1];
			if(tipo =="E" && bnd==0){
				bnd=1;
			}
			if(tipo=="C" || tipo=="E"){
				return (opened ? "dijitFolderOpened" : "dijitFolderClosed");
			}
			else{
				return  "dijitLeaf";
			}
		};
	</script>
	<style type="text/css">
        	td.itemDocAdicPrincGG{
        		padding:0.3em;
        		cursor: pointer;
        		background-color:#94B9EF;
        		font-weight:bold;
        	}
        	td.itemDocAdicGG{
        		padding:0.3em;
        		cursor: pointer;
        		background-color:#FFFFFF;
        	}
        	td.itemDocAdicGG:HOVER{
        		background-color:#D4E3FB;
        		color:#0066D1;
        	}
        	td.itemDocAdicGG:FOCUS{
        		background-color:#D4E3FB;
        	}
        	td.centerGG{
        		text-align:center;
        		padding: 0.3em;
        	}
        	td.itemDestGG{
        		padding: 0.3em;
        	}
        </style>
</head>
<body>
	<div dojoType="dijit.layout.BorderContainer" region="top" style="height:99%;">
		<div id="divPDF" style="width:99%; height:99%;"></div>
	</div>
	
</body>
</html>