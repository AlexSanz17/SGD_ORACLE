dojo.provide("js.top");

function Recurso(iconClass, label, href, title) {
   this.id = null;
   this.iconClass = iconClass;
   this.label = label;
   this.href = href;
   this.title = title;
}

var getButtonData = function(recurso) {
   var objRecurso = new Recurso("","","","");
   if (recurso == "UsuFinBtnDerMas") {
      objRecurso.id = "tabDerivacionMasiva";
      objRecurso.iconClass = "dijitEditorIcon dijitEditorIconIndent";
      objRecurso.label = "Transferir Masivamente";
   } else if (recurso == "UsuFinBtnFedatario") {
	      objRecurso.id = "tabOperacionFedatario";
	      objRecurso.href = "NuevoDocumento_operacionFedatario.action";
	      objRecurso.iconClass = "dijitEditorIcon dijitEditorIconCopy";
	      objRecurso.label = "Fedatario";
	      objRecurso.title = "Fedatario";
   } else if (recurso == "UsuFinBtnSeg") {
	      objRecurso.id = "tabSeguimiento";
	      objRecurso.iconClass = "dijitEditorIcon dijitEditorIconInsertTable";
	      objRecurso.label = "Seguimiento";
	      objRecurso.href = "Seguimiento_mostrarInicio.action";
	      objRecurso.title = "Seguimiento";
  }else if (recurso == "UsuPerfilBtn") {
      objRecurso.id = "tabVisor";
      objRecurso.label = "Carga Perfil";
      objRecurso.title = "Carga Perfil";
      objRecurso.href =  "inicioNO.action";
  }else if (recurso == "UsuFinBtnVis") {
     
   } else if (recurso == "UsuFinBtnLis") {
      objRecurso.id = "tabLista";
      objRecurso.iconClass = "dijitEditorIcon dijitEditorIconCopy";
      objRecurso.label = "Lista de Contactos";
   } /*else if (recurso == "UsuFinMnuRecVirtual"){
      objRecurso.id = "tabRecepcionarDocumento";
      objRecurso.href = "NuevoDocumento_mostrarVistaRecepcion.action";
      objRecurso.iconClass = "dijitEditorIcon dijitEditorIconCopy";
      objRecurso.label = "Crear Recepcion";
      objRecurso.title = "Crear Recepcion";
   }*/ else if (recurso == "UsuFinBtnNueDocPri") {
      objRecurso.id = "tabNuevoDocumento";
      objRecurso.href = "NuevoDocumento_mostrarVista.action?tipoTransaccion=N";
      objRecurso.iconClass = "dijitEditorIcon dijitEditorIconCopy";
      objRecurso.label = "Crear Flujo Documental";
      objRecurso.title = "Crear Flujo Documental";
    } else if (recurso == "UsuFinBtnNueDocTra") {
      objRecurso.id = "tabNuevoDocumento";
      objRecurso.href = "NuevoDocumento_mostrarVistaTramite.action?tipoTransaccion=N";
      //objRecurso.href = "pages/tramite/nuevoDocumento.jsp";
      objRecurso.iconClass = "dijitEditorIcon dijitEditorIconCopy";
      objRecurso.label = "Crear Flujo Documental";
      objRecurso.title = "Crear Flujo Documental";   
   } else if (recurso == "MPBtnNueDocPri") {
      objRecurso.href = "getNuevoDocumento.action";
      objRecurso.id = "tabNuevoDocumentoMP";
      objRecurso.iconClass = "dijitEditorIcon dijitEditorIconCopy";
      objRecurso.label = "Nuevo Documento MP";
      objRecurso.title = "Nuevo Documento MP";
   } else if (recurso == "BusquedaSimple") {
      var parametroBusqueda = dijit.byId("txtBusquedaSimple").attr("value");
      objRecurso.id = "tabBusquedaSimple";
      objRecurso.iconClass = "dijitEditorIcon dijitEditorIconTabIndent";
      objRecurso.label = "Buscar Documentos...";

      if (parametroBusqueda != "") {
         objRecurso.href = "pages/layout/busquedaSimple.html";
      }
      
      objRecurso.title = "B&uacute;squeda Simple";
   } else if (recurso == "BusquedaAvanzada") {
      objRecurso.id = "tabBusquedaAvanzada";
      objRecurso.iconClass = "dijitEditorIcon dijitEditorIconTabIndent";
      objRecurso.href = "pages/layout/busquedaAvanzada.html";
      objRecurso.title = "B&uacute;squeda Avanzada";
   }else if (recurso == "ReporteVista"){
      objRecurso.id = "tabReporteVista" ;
      objRecurso.iconClass = "dijitEditorIcon dijitEditorIconTabIndent";
      objRecurso.href = "pages/layout/reporteVista.html";
      objRecurso.title = "Reportes";
   } else if (recurso == "UsuFinMnuAsiRem") {
      objRecurso.id = "tabReemplazo";
      objRecurso.iconClass = "siged16 sigedSave16";
      objRecurso.label = "Mis Reemplazos";
      objRecurso.href = "pages/reemplazo/lstReemplazo.jsp";
      objRecurso.title = "Mis Reemplazos";
   } else if (recurso == "nvodocsas") {
      objRecurso.id = "tabNvoDocSas";
      objRecurso.iconClass = "dijitEditorIcon dijitEditorIconCopy";
      objRecurso.label = "nvo Doc Sas";
      objRecurso.href = "pages/sas/regInformeTecnico.jsp";
      objRecurso.title = "nvo Doc Sas";
   } else if (recurso == "UsuFinReportes") {
      objRecurso.id = "tabReportes";
      objRecurso.iconClass = "siged16 sigedSave16";
      objRecurso.label = "Reportes Disponibles";
      objRecurso.href = "ReporteSiged_showMenu.action";
      objRecurso.title = "Reportes Disponibles";
   } else if (recurso == "UsuFinRepPro") {
      objRecurso.id = "tabRepPro";
      objRecurso.iconClass = "siged16 sigedSave16";
      objRecurso.label = "Reporte Proceso";
      objRecurso.href = "ReporteSiged_inicio.action";
      objRecurso.title = "Reporte Proceso";
   } else if (recurso == "UsuFinRepMen") {
     objRecurso.id = "tabRepMen";
     objRecurso.iconClass = "siged16 sigedSave16";
     objRecurso.label = "Reporte Mensajeria";
     objRecurso.href = "ReporteSiged_mensajeria.action";
     objRecurso.title = "Reporte Mensajeria";
   } else if (recurso == "UsuFinPendientes") {
      objRecurso.id = "tabFilPendientes";
      objRecurso.iconClass = "siged16 sigedSave16";
      objRecurso.label = "Lista de Pendientes";
      objRecurso.href = "ReporteSigedSTOR_inicioPendientes.action";
      objRecurso.title = "Lista de Pendientes";
   } else if (recurso == "UsuFinIngresados") {
      objRecurso.id = "tabFilIngresados";
      objRecurso.iconClass = "siged16 sigedSave16";
      objRecurso.label = "Lista de Ingresados";
      objRecurso.href = "ReporteSigedSTOR_inicioIngresados.action";
      objRecurso.title = "Lista de Ingresados";
   } else if (recurso == "UsuFinConcluidos") {
      objRecurso.id = "tabFilConcluidos";
      objRecurso.iconClass = "siged16 sigedSave16";
      objRecurso.label = "Lista de Concluidos";
      objRecurso.href = "ReporteSigedSTOR_inicioConcluidos.action";
      objRecurso.title = "Lista de Concluidos";
   } else if (recurso == "UsuFinRepAYQ") {
      objRecurso.id = "tabFilAYQ";
      objRecurso.iconClass = "siged16 sigedSave16";
      objRecurso.label = "Expedientes Pendientes AYQ";
      objRecurso.href = "ReporteSigedUsuarios_inicioAYQ.action";
      objRecurso.title = "Expedientes Pendientes AYQ";
   } else if (recurso == "UsuFinMnuLista") {
      objRecurso.id = "tabLista";
      objRecurso.iconClass = "siged16 sigedSave16";
      objRecurso.label = "Lista";
      objRecurso.href = "pages/mantenimiento/lstListaDOJO.jsp";
      objRecurso.title = "Listas";
   } else if (recurso == "UsuFinMnuMantRee") {
      objRecurso.id = "tabMantReemplazo";
      objRecurso.iconClass = "siged16 sigedSave16";
      objRecurso.label = "Mantenimiento de Reemplazos";
      objRecurso.href = "pages/reemplazo/lstMantReemplazo.jsp";
      objRecurso.title = "Mantenimiento de Reemplazos";
   }
   else if (recurso == "nvodocsas_sa") {
	    objRecurso.id = "tabNvoDocSas";
	    objRecurso.iconClass = "dijitEditorIcon dijitEditorIconCopy";
	    objRecurso.label = "nvo Doc Sas";
	    objRecurso.href = "pages/sas/sa/regInformeTecnico.jsp";
	    objRecurso.title = "nvo Doc Sas";
	   }
   /************************************************/
   //	Recurso para iniciar Procesos en intalio
   /************************************************/
   else if (recurso == "UsuFinLstProcess") {
	      objRecurso.id = "tabProcesos";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.label = "Procesos";
	      objRecurso.href = "intalio_listarProcesos.action";
	      objRecurso.title = "Procesos";
	   }
   /************************************************/
   //	REPORTES DE SANCIONADOR
   /************************************************/
   else if (recurso == "CantOficioProc") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=CantidadOficiosxProcedimiento&titulo=Cantidad%20Oficios%20por%20Procedimiento";
	      objRecurso.title = "Reportes";
	   }
   else if (recurso == "CantOficioDFplazo") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=CantidadOficiosDentroFueraPlazo&titulo=Cantidad%20Oficios%20Emitidos%20Dentro%20Fuera%20Plazo";
	      objRecurso.title = "Reportes";
	   }
   else if (recurso == "CantDescargoFPlazo") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=CantidadDesReciFueraPlazo&titulo=Cantidad%20de%20Descargos%20recibidos%20dentro%20y%20fuera%20de%20plazo";
	      objRecurso.title = "Reportes";
	   }
   else if (recurso == "CantInfEvalDescXUT") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=CantidadInformesEvaluacionDescargosxUT&titulo=Cantidad%20de%20Informes%20Evaluacion%20de%20Descargos%20x%20UT";
	      objRecurso.title = "Reportes";
	   }
   else if (recurso == "ProyResGFE-GL") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=CantidadProyectoResolucionGFE-GL&titulo=Cantidad%20De%20Proyecto%20y%20Resolucion%20GFE-GL";
	      objRecurso.title = "Reportes";
	   }
   else if (recurso == "RecDFPlazo") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=CantidadRecuRecDentroFueraPlazo&titulo=Cantidad%20Recurso%20Reconsideracion%20Dentro%20y%20Fuera%20del%20Plazo";
	      objRecurso.title = "Reportes";
	   }
   else if (recurso == "RecXProced") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=CantidadElaboracionReconsideracionxProcedimiento&titulo=Cantidad%20de%20Proyecto%20y%20Reconsideracion%20Elaboracion%20Reconsideracion%20x%20Procedimiento";
	      objRecurso.title = "Reportes";
	   }
   else if (recurso == "ProyRecXPendiente") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=CantidadProyResRecPenRemi&titulo=Cantidad%20Proyectos%20Resolucion%20y%20Reconsidereacion%20Pendiente%20x%20Remitir";
	      objRecurso.title = "Reportes";
	   }
   else if (recurso == "ApelDFPlazo") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=CantidadRecApelReciDenFueraPlazo&titulo=Cantidad%20Recurso%20de%20Apelacion%20Recibidos%20Dentro%20o%20Fuera%20del%20Plazo";
	      objRecurso.title = "Reportes";
	   }
   else if (recurso == "ExpApel") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=CantidadExpedientesApelacion&titulo=Cantidad%20de%20Expedientes%20de%20Apelacion%20x%20remitir";
	      objRecurso.title = "Reportes";
	   }
   else if (recurso == "CantAnalistaxProc") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=CantAnalistaxProc&titulo=Cantidad%20de%20Procedimientos%20por%20Analista";
	      objRecurso.title = "Reportes";
	   }
   else if (recurso == "CantRecxProc") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=CantidadRecursosxProcedimiento&titulo=Cantidad%20Recursos%20por%20Procedimientos";
	      objRecurso.title = "Reportes";
	   }
   else if (recurso == "CantRecxAAM") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=CantidadResolucionesxArchivoAmonestacionMulta&titulo=Cantidad%20Resolucion%20por%20Archivo,%20Amonestacion,%20Multa";
	      objRecurso.title = "Reportes";
	   }
   else if (recurso == "CantResxProc") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=CantidadResolucionesxProcedimiento&titulo=Cantidad%20Resolucion%20por%20Procedimientos";
	      objRecurso.title = "Reportes";
	   }
   else if (recurso == "CantRecxFII") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "reporteGenerico1JSP.action?reportName=cantidadRecursosxFundadoInfundadoImprocedente&titulo=CantidadRecursos%20x%20FundadoInfundado%20Improcedente";
	      objRecurso.title = "Reportes";
	}else if (recurso == "reportePentaho") {
	      objRecurso.id = "reporte";
	      objRecurso.iconClass = "siged16 sigedSave16";
	      objRecurso.href = "pages/reportes/reportePentaho.jsp";
	      objRecurso.title = "Reportes";
	}else if (recurso == "UsuFinConsulta3") {
		objRecurso.id = "consulta3";
		objRecurso.iconClass = "siged16 sigedSave16";
		objRecurso.href = "ConsultaAPN_consultaAPN3.action";
		objRecurso.title = "Documentos Recepcionados";
	}else if (recurso == "UsuFinConsulta4") {
		objRecurso.id = "consulta3";
		objRecurso.iconClass = "siged16 sigedSave16";
		objRecurso.href = "ConsultaAPN_consultaAPN4.action";
		objRecurso.title = "Documentos Remitidos";
	}else if (recurso == "menArchCentral") {
		objRecurso.id = "menuArchivoCentral";
		objRecurso.iconClass = "siged16 sigedSave16";
		objRecurso.href = "pages/archivoCentral/menuArchivoCentral.jsp";
		objRecurso.title = "Archivo Central";
    }else if (recurso == "RepAPN1") {
    	objRecurso.id = "reporte";
    	objRecurso.iconClass = "siged16 sigedSave16";
    	objRecurso.href = "ReporteAPN_reporteAPN1.action";
    	objRecurso.title = "Documentos Emitidos";
    }else if (recurso == "RepAPN2") {
    	objRecurso.id = "reporte";
    	objRecurso.iconClass = "siged16 sigedSave16";
    	objRecurso.href = "ReporteAPN_reporteAPN2.action";
    	objRecurso.title = "TUPAs mes";
    }else if (recurso == "RepAPN3") {
    	objRecurso.id = "reporte";
    	objRecurso.iconClass = "siged16 sigedSave16";
    	objRecurso.href = "ReporteAPN_reporteAPN3.action";
    	objRecurso.title = "Documentos Mes";
    }else if (recurso == "RepAPN4") {
    	objRecurso.id = "reporte";
    	objRecurso.iconClass = "siged16 sigedSave16";
    	objRecurso.href = "ReporteAPN_reporteAPN4.action";
    	objRecurso.title = "Seguimiento de Documentos";

    } else if (recurso == "RepAPN5") {
    	objRecurso.id = "reporte";
    	objRecurso.iconClass = "siged16 sigedSave16";
    	objRecurso.href = "ReporteAPN_reporteAPN5.action";
    	objRecurso.title = "Documentos Pendientes";

    } else if (recurso == "RepAPN6") {
    	objRecurso.id = "reporte";
    	objRecurso.iconClass = "siged16 sigedSave16";
    	objRecurso.href = "ReporteAPN_reporteAPN6.action";
    	objRecurso.title = "Documentos Fedateados";

    } else if (recurso == "RepAPN8") {
    	objRecurso.id = "reporte";
    	objRecurso.iconClass = "siged16 sigedSave16";
    	objRecurso.href = "ReporteAPN_reporteAPN8.action";
    	objRecurso.title = "Documentos Recibidos por Mesa de Parte";
    } else if (recurso == "RepAPN9") {
    	objRecurso.id = "reporte";
    	objRecurso.iconClass = "siged16 sigedSave16";
    	objRecurso.href = "ReporteAPN_reporteAPN9.action";
    	objRecurso.title = "Documentos MTC";
    } else if (recurso == "RepAPN10"){
    	objRecurso.id = "reporte";
    	objRecurso.iconClass = "siged16 sigedSave16";
    	objRecurso.href = "ReporteAPN_reporteAPN10.action";
    	objRecurso.title = "Documentos Mesa de Parte";
    }else if (recurso == "UsuFinMisExpedientes") {
		objRecurso.id = "misexpedientes";
		objRecurso.iconClass = "siged16 sigedSave16";
		objRecurso.href = "ConsultaAPN_misExpedientes.action";
		objRecurso.title = "Mis Expedientes";
	}else if (recurso == "ArchCentral_MC") {
		objRecurso.id = "ArchCentral_MC";
		objRecurso.iconClass = "siged16 sigedSave16";
		objRecurso.href = "pages/archivoCentral/mantenimientoCajas/principalMC.jsp";
		objRecurso.title = "Mantenimiento de Cajas";
	}
	else if (recurso == "ArchCentral_RP") {
		objRecurso.id = "ArchCentral_RP";
		objRecurso.iconClass = "siged16 sigedSave16";
		objRecurso.href = "pages/archivoCentral/registroPrestamo/principalRP.jsp";
		objRecurso.title = "Registro de Prestamos";
	}
	else if (recurso == "ArchCentral_BD") {
		objRecurso.id = "ArchCentral_BD";
		objRecurso.iconClass = "siged16 sigedSave16";
		objRecurso.href = "pages/archivoCentral/busquedaDocumentos/principalBD.jsp";
		objRecurso.title = "Busqueda de Documentos";
	}
	else if (recurso == "ArchCentral_BP") {
		objRecurso.id = "ArchCentral_BP";
		objRecurso.iconClass = "siged16 sigedSave16";
		objRecurso.href = "pages/archivoCentral/bandejaPrestamos/principalBP.jsp";
		objRecurso.title = "Bandeja de Prestamos";
	}
   return objRecurso;
};


var buildTabsFromToolBarTop = function(recurso) {
  
   var btnData = getButtonData(recurso);
   
    if (dijit.byId("grdBusquedaExpediente")) {
      dijit.byId("grdBusquedaExpediente").destroy();
   }
  
   if(dijit.byId("dlgBusquedaExpediente")){
      dijit.byId("dlgBusquedaExpediente").destroy();
   }
   if(dijit.byId("sNroExpedienteBE")){
      dijit.byId("sNroExpedienteBE").destroy();
   }
   if(dijit.byId("procesoBE")){
      dijit.byId("procesoBE").destroy();
   }
   if(dijit.byId("fechaBE")){
      dijit.byId("fechaBE").destroy();
   }
   if(dijit.byId("sAsuntoBE")){
      dijit.byId("sAsuntoBE").destroy();
   }
   if(dijit.byId("sNTBE")){
      dijit.byId("sNTBE").destroy();
   }

   try{
      if (btnData.href != "" && btnData.title != "") {
         var dialog = dijit.byId("dlgProgresBar");
         dialog.show() ;
       
         if (!dijit.byId(btnData.id)) {
               var newTab = new dojox.layout.ContentPane({
               id : btnData.id,
               jsId : btnData.id,
               closable: true,
               href : btnData.href,
               iconClass : btnData.iconClass,
               onClose : function() {
                   if (btnData.id == 'tabNuevoDocumento'){
              	      accion(2);
                  }else{
              	      showGridInbox(sTipoGridActual);
                   }
            	   return true;
               },
               title: btnData.title
            });
            
            if (btnData.id!='tabVisor'){
              dijit.byId("tabContainerInbox").addChild(newTab);
             
            }
         } else {
            dijit.byId("tabContainerInbox").removeChild(dijit.byId(btnData.id));
            dijit.byId(btnData.id).destroyDescendants();
            dijit.byId(btnData.id).destroy();
           
            if (dijit.byId("grdBusquedaExpediente")) {
                  dijit.byId("grdBusquedaExpediente").destroy();
            }

            var newTab1 = new dojox.layout.ContentPane({
               id : btnData.id,
               jsId : btnData.id,
               closable: true,
               href : btnData.href,
               iconClass : btnData.iconClass,
               onClose : function() {
                  if (recurso == "UsuFinBtnNueDocPri" || recurso == "UsuFinBtnNueDocTra") {
                    showGridInbox(sTipoGridActual);
            	   return true;   
                  } 
                  else if (recurso == "MPBtnNueDocPri") {
                     showGridInbox(TIPO_GRID_REGISTRADOS_MP);
                  } else if (recurso == "UsuFinBtnSeg") {
                     showGridInbox(sTipoGridActual);
                  } else if (recurso == "BusquedaAvanzada"){
                     showGridInbox(sTipoGridActual);
                  }else{
                     console.debug( "selecting tab principal  function " );
                     var grid = dijit.byId("gridInbox");
                     grid.startup();
                     addToolBarInbox(0);
                     dijit.byId("tabContainerInbox").selectChild(dijit.byId("borderContainerInbox"));
                  }
                  console.debug( "onclose  function " );
                  return true;
               },
               title: btnData.title
            });
            
            dijit.byId("tabContainerInbox").addChild(newTab1);   
         }
         
         if (btnData.id!='tabVisor'){
            dijit.byId("tabContainerInbox").selectChild(dijit.byId(btnData.id));
         }
         
         if (btnData.id=='tabVisor'){
             window.location.href= btnData.href;
          }
          
         dialog.hide() ;

      }

   }catch(err) {
      console.debug( "El error: "+err.description ) ;
   }
};

var showOnlineHelp = function(sRol, sFlujo) {
   var sURL = "";

   console.debug("Rol del usuario logeado [%s] Flujo [%s]", sRol, sFlujo);

   if (sRol != null && sRol != "") {
      if (sRol == "Siged - Mesa de Partes") {
         sURL = "ayuda/Mesa de Partes/Mesa de Partes.htm";
      } else if (sRol == "Siged - Digitalizador") {
         sURL = "ayuda/Digitalizador/Digitalizador.htm";
      } else if (sRol == "Siged - Control de Calidad") {
         sURL = "ayuda/Control de Calidad/Control de Calidad.htm";
      }
   } else {
      if (sFlujo == "stor") {
         sURL = "ayuda/STOR/Manual de Usuario STOR.htm";
      } else {
         sURL = "ayuda/User Final/User Final.htm";
      }
   }

   console.debug("URL [%s]", sURL);
   window.open(sURL, "", "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=600, top=180, left=200");
};


function reemplazarEspaciosEnBlanco(cadena) {
    while(cadena.indexOf(' ')!=-1) {
        cadena=cadena.replace(' ','_');
    }
    return cadena;
}

function cambiarClave(){
	dojo.xhrPost({
		url: "verCambiarClave.action",
		load: function(data){
			if(!dijit.byId("dlgCambiarClave")){
				new dijit.Dialog({
					id: "dlgCambiarClave",
        			draggable:"true",
        			style:"height:200px;width:500px;display:none;",
        			title:"Cambiar clave de acceso",
		            onClose: dojo.hitch(this, function(){
		            	dijit.byId("dlgCambiarClave").hide();
		        		dijit.byId("dlgCambiarClave").destroyRecursive();
		            })
        		});
			}
			dijit.byId("dlgCambiarClave").attr("content", data);
			dijit.byId("dlgCambiarClave").show();
		}
	});
}