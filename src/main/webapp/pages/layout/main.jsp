
<%@page import="gob.ositran.siged.config.SigedProperties"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>


<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Sistema de Gesti&oacute;n Documentaria - SIGED</title>
        <link type="image/x-icon" href="favicon.ico" rel="shortcut icon" />

        <%-- Desarrollo - Estilos Siged --%>
        <link type="text/css" rel="stylesheet" href="css/styleSiged.css" />
        <%-- Produccion - Estilos Siged --%>
        <%--<link type="text/css" rel="stylesheet" href="cssbuild/styleSiged.css" />--%>

        <%-- Desarrollo - Estilos Dojo --%>
        <%--<link type="text/css" rel="stylesheet" href="js/dojo/css/styleDojo.css" />--%>
        <%-- Produccion - Estilos Dojo --%>
        <link type="text/css" rel="stylesheet" href="js/dojobuild/css/styleDojo.css" />

        <script type="text/javascript">
            var djConfig = {
                isDebug: false,
                parseOnLoad: true
            };
        </script>

        <%-- Desarrollo - API Dojo --%>
        <%--<script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>--%>
        <%-- Produccion - API Dojo --%>
        <script type="text/javascript" src="js/dojobuild/dojo/dojo.js"></script>

        <%-- Desarrollo - ExpandoPane --%>
        <%--<script type="text/javascript" src="js/dojo/dojox/layout/ExpandoPane.js"></script>--%>

        <script type="text/javascript">
           // Tipos de Grid existentes
            var TIPO_GRID_RECIBIDOS         = "0";
           // var TIPO_GRID_PENDIENTES        = "1";
            var TIPO_GRID_ENVIADOS          = "2";
            var TIPO_GRID_MENSAJERIA        = "3";
            var TIPO_GRID_NOTIFICACIONES    = "4";
            var TIPO_GRID_PROCESOS          = "5";
            var TIPO_GRID_EXPEDIENTES       = "6";
            var TIPO_GRID_REGISTRADOS_MP    = "14";
            var TIPO_GRID_CARPETA_BUSQUEDA  = "19";
            var TIPO_GRID_INFORMATIVOS      = "9";
            var TIPO_GRID_BUSQUEDA_SIMPLE   = "13";
            var TIPO_GRID_QAS_DIGITALIZADOS = "14";
            //digitalizador
            var TIPO_GRID_DIG_DOC_INGRESADOS = "15";

            var TIPO_GRID_MENSAJERIA_RECIBIDOS = "16";
            var TIPO_GRID_MENSAJERIA_CERRADOS = "17";
            var TIPO_GRID_DOCUMENTOS_ARCHIVADOS = "18";
            var TIPO_GRID_CONSULTA_RECEPCIONADOS  = "20";
            var TIPO_GRID_CONSULTA_REMITIDOS  = "21";
            var TIPO_GRID_SEGUIMIENTO = "22";
            var TIPO_GRID_MIS_EXPEDIENTES = "23";
            var TIPO_GRID_PENDIENTES = "24";
            var TIPO_GRID_ANULADOS = "25";
            var TIPO_GRID_FIRMAR   = "26";
            var TIPO_GRID_RECEPCION_VIRTUAL   = "27";
            var TIPO_GRID_DESPACHO_VIRTUAL   = "28";
            var TIPO_GRID_MI_RECEPCION_VIRTUAL   = "29";
            var TIPO_GRID_MI_LEGAJO   = "30";
            var TIPO_GRID_LEGAJO_COMPARTIDO   = "31";
            var TIPO_GRID_RECEPCION_VIRTUAL_OBSERVADOS   = "32";
            
            // Tipos de Grid existentes
            var recursoIG = "<s:property value='#session._RECURSO.UsuFinVisorGerencial' />";
            var readVisor = "<s:property value='#session.readVisor'/>";
            if (readVisor=='VG'){
            	recursoIG = "1";
            }
			if (readVisor=='NO'){
				recursoIG = "0";
            }

            var agrupaIG= true;
            var vistaIG=false;
            var temaUsuario = "<s:property value='#session._USUARIO.tema' />";
            var bandejaAgrupadaUsuario = "<s:property value='#session._USUARIO.bandejaAgrupada' />";
            var bandejaJefe = "<s:property value='#session._ROLCARGO' />";
            var unidadSeleccionada = "<s:property value='#session._USUARIO.idUnidadPerfil' />";
            var unidadTramiteVirtual = "<s:property value='@org.ositran.utils.Constantes@UNIDAD_TRAMITE' />";
            
            var arrRecursoTopButton = [];

            arrRecursoTopButton.push({codigo:"UsuFinBtnVis", valor:<s:property value="#session._RECURSO.UsuFinBtnVis"/>});
          
            arrRecursoTopButton.push({codigo:"UsuFinBtnSeg", valor:<s:property value="#session._RECURSO.UsuFinBtnSeg"/>});
            
            arrRecursoTopButton.push({codigo:"UsuFinBtnNueDocPri", valor:<s:property value="#session._RECURSO.UsuFinBtnNueDocPri"/>});
            arrRecursoTopButton.push({codigo:"UsuFinBtnNueDocTra", valor:<s:property value="#session._RECURSO.UsuFinBtnNueDocTra"/>});
            arrRecursoTopButton.push({codigo:"MPBtnNueDocPri", valor:<s:property value="#session._RECURSO.MPBtnNueDocPri"/>});
            arrRecursoTopButton.push({codigo:"nvodocsas", valor:<s:property value="#session._RECURSO.nvodocsas"/>});
            arrRecursoTopButton.push({codigo:"UsuFinLstProcess", valor:<s:property value="#session._RECURSO.UsuFinLstProcess"/>});

            var arrRecursoFirstGrid = [];
            arrRecursoFirstGrid.push({codigo:"MPMnuDocReg", valor:<s:property value="#session._RECURSO.MPMnuDocReg"/>, grid:TIPO_GRID_REGISTRADOS_MP});
            arrRecursoFirstGrid.push({codigo:"UsuFinMnuDocRec", valor:<s:property value="#session._RECURSO.UsuFinMnuDocRec"/>, grid:TIPO_GRID_RECIBIDOS});

            <s:if test="#session._USUARIO.grupoFedatario != null">
              arrRecursoTopButton.push({codigo:"UsuFinBtnFedatario", valor:<s:property value="#session._RECURSO.UsuFinBtnFedatario"/>});
           </s:if>



            <s:if test="#session._RECURSO.QASMnuDigitalizados != null">
                arrRecursoFirstGrid.push({codigo:"QASMnuDigitalizados", valor:<s:property value="#session._RECURSO.QASMnuDigitalizados"/>, grid:TIPO_GRID_QAS_DIGITALIZADOS});
            </s:if>
            arrRecursoFirstGrid.push({codigo:"DigMnuDocIng", valor:<s:property value="#session._RECURSO.DigMnuDocIng"/>, grid:TIPO_GRID_QAS_DIGITALIZADOS});

            arrRecursoFirstGrid.push({codigo:"MensMnuDocRec", valor:<s:property value="#session._RECURSO.MensMnuDocRec"/>, grid:TIPO_GRID_MENSAJERIA_RECIBIDOS});

            var recursoBusqueda = {codigo:"UsuFinBtnBus", valor:<s:property value="#session._RECURSO.UsuFinBtnBus"/>};





        </script>

        <%-- Desarrollo - API Siged --%>
    
        <script src='<%=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCOFIRMA_HOST)%>:<%=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCOFIRMA_PORT)%>/SignFastService/js/signFastService.js'></script>
        <script type="text/javascript" src="js/dojobuild/js/requires.js"></script>                                                                                                                      
        <script type="text/javascript" src="js/dojo/js/commons.js"></script>
        <script type="text/javascript" src="js/dojo/js/main.js"></script>
        <script type="text/javascript" src="js/dojo/js/gridFormatters.js"></script>
        <script type="text/javascript" src="js/dojo/js/gridInbox.js"></script>
        <script type="text/javascript" src="js/dojo/js/top.js"></script>
        <script type="text/javascript" src="js/dojo/js/carpetasBusqueda.js"></script>
        <script type="text/javascript" src="js/dojo/js/documentosRecibidos.js"></script>

        <script type="text/javascript" src="js/dojo/js/informativosRecibidos.js"></script>
        <script type="text/javascript" src="js/siged/siged.js"></script>
        <script type="text/javascript" src="js/siged/siged.array.js"></script>
        <script type="text/javascript" src="js/siged/siged.commons.js"></script>
        <script type="text/javascript" src="js/siged/siged.documento.js"></script>
        <script type="text/javascript" src="js/siged/siged.documento.numeracion.js"></script>
        
       
        
        <script type="text/javascript" src="js/siged/siged.forms.js"></script>
        <script type="text/javascript" src="js/siged/siged.string.js"></script>
        <%-- Produccion - API Siged --%>
        <%--<script type="text/javascript" src="js/dojobuild/js/siged.js"></script>--%>

        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/jquery-validate.js"></script>
    </head>
    <body >
        
        <div dojoType="dijit.Menu"
             id="gridMenu"
             jsId="gridMenu"
             style="display:none;">
            <div dojoType="dojox.widget.PlaceholderMenuItem" label="GridColumns"></div>
        </div>
        
        <div id="divContainerMain">  </div>
        
        <input type="hidden" name="sistema"       id="sistema"        value = "SGD" />
        <input type="hidden" name="servidorFirma" id="servidorFirma"  value = "<%=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCOFIRMA_HOST)%>:<%=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCOFIRMA_PORT)%>"/>
        <input type="hidden" name="documentosId"  id="documentosId"   value = "" />
        <input type="hidden" name="firmaHolografica" id="firmaHolografica" value = "S" />
        <input type="hidden" name="insertarSelloTiempo" id="insertarSelloTiempo" value = "N" />
        <input type="hidden" name="usuarioAlfresco" id="usuarioAlfresco" value = "sgd:usuario|<s:property value='#session._USUARIO.usuario'/>" />
        <input type="hidden" name="tipoFirma" id="tipoFirma" value = "" />
        <input type="hidden" name="respuesta"     id="respuesta"      value = "" />
        
        <%@ include file="../util/cargandoGrid.jsp" %> 
    </body>
</html>
