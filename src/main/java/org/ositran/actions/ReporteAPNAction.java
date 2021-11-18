/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;


import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.reporte.LBTRUtil;
import org.ositran.services.DocumentoReferenciaService;
import org.ositran.services.DocumentoService;
import org.ositran.services.ReporteAPNService;
import org.ositran.services.ExpedienteService;
import org.ositran.services.UnidadService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;
import org.ositran.utils.DateUtil;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoAdjunto;
import com.btg.ositran.siged.domain.DocumentoReferencia;
import com.btg.ositran.siged.domain.Documentofedateado;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.FilaHojaRuta;
import com.btg.ositran.siged.domain.FilaHojaFirma;
import com.btg.ositran.siged.domain.FilaReporteAPN2;
import com.btg.ositran.siged.domain.IotdtcDespacho;
import com.btg.ositran.siged.domain.IotdtmDocExterno;
import com.btg.ositran.siged.domain.NodoDocConsolidadoReporteAPN4;
import com.btg.ositran.siged.domain.NodoDocReporteAPN3;
import com.btg.ositran.siged.domain.NodoDocReporteAPN4;
import com.btg.ositran.siged.domain.NodoDocReporteAPN8;
import com.btg.ositran.siged.domain.NodoExpReporteAPN3;
import com.btg.ositran.siged.domain.NodoExpReporteAPN4;
import com.btg.ositran.siged.domain.NodoExpReporteAPN8;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.ReporteAPN1;
import com.btg.ositran.siged.domain.ReporteAPN10;
import com.btg.ositran.siged.domain.ReporteAPN9;
import com.btg.ositran.siged.domain.TrazabilidadDocumentaria;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Usuario;
import java.util.Map;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.ositran.daos.DocumentoAdjuntoDAO;
import org.ositran.daos.DocumentoExternoVirtualDAO;
import org.ositran.services.ParametroService;
public class ReporteAPNAction {

	private static Logger log = Logger.getLogger(ReporteSigedAction.class);
	private Integer idDocumento;
	private Integer size;
	private String idAreaOrigen;
	private String idAreaDestino;
	
	private String areaOrigen;
	private String idTipoDocumento;
	private String idPrioridad;
	private String fechaCreacion;
	private String fechaDesde;
	private String fechaHasta;
	private String html;

	private String grupoProceso;
	private String cliente;
	private String filtroPlazo;
	private String prioridad;
	private String servicio;

	private Expediente expediente;
	private Documento documento;
	private Trazabilidaddocumento trazabilidad;
	private BusquedaAvanzada objFiltro;
	private List<Unidad> areas;
	private List<ReporteAPN1> listaReporteAPN1;
	private List<ReporteAPN10> listaReporteAPN10;
        
	private List<NodoExpReporteAPN3> listaReporteAPN3;
	private List<NodoExpReporteAPN4> listaReporteAPN4;
	private List<NodoDocReporteAPN4> listaDocReporteAPN4;
	private List<Documentofedateado> listaDocReporteAPN7;
	private List<FilaReporteAPN2> listaReporteAPN2;
	private List<NodoExpReporteAPN8> listaReporteAPN8;
	private List<ReporteAPN9> listaReporteAPN9;
	private List<TrazabilidadDocumentaria> hojaTrazabilidadDocumentaria;
	private ReporteAPNService reporteAPNService;
	private ExpedienteService expedienteService;
	private DocumentoService documentoService;
        private DocumentoAdjuntoDAO documentoAdjuntoDAO;
	private UnidadService unidadService;
	private UsuarioService usuarioService;
	private DocumentoReferenciaService documentoReferenciaService;
	private List<FilaHojaRuta> hojaRuta;
        private List<FilaHojaFirma> hojaFirma;
        private ParametroService parametroService;
        private List<Parametro> lstPrioridad;
        private List<String> lstAdjuntos;
        private Integer codigoVirtual;
        private List<IotdtcDespacho> listIotdtcDespacho;
        private DocumentoExternoVirtualDAO documentoExternoVirtualDAO;
        
        public List<IotdtcDespacho> getListIotdtcDespacho() {
            return listIotdtcDespacho;
        }

        public void setListIotdtcDespacho(List<IotdtcDespacho> listIotdtcDespacho) {
            this.listIotdtcDespacho = listIotdtcDespacho;
        }
     
        public DocumentoExternoVirtualDAO getDocumentoExternoVirtualDAO() {
            return documentoExternoVirtualDAO;
        }

        public void setDocumentoExternoVirtualDAO(DocumentoExternoVirtualDAO documentoExternoVirtualDAO) {
            this.documentoExternoVirtualDAO = documentoExternoVirtualDAO;
        }

       

        public Integer getCodigoVirtual() {
            return codigoVirtual;
        }

        public void setCodigoVirtual(Integer codigoVirtual) {
            this.codigoVirtual = codigoVirtual;
        }

        public List<String> getLstAdjuntos() {
            return lstAdjuntos;
        }

        public void setLstAdjuntos(List<String> lstAdjuntos) {
            this.lstAdjuntos = lstAdjuntos;
        }
        
        public DocumentoAdjuntoDAO getDocumentoAdjuntoDAO() {
            return documentoAdjuntoDAO;
        }

        public void setDocumentoAdjuntoDAO(DocumentoAdjuntoDAO documentoAdjuntoDAO) {
            this.documentoAdjuntoDAO = documentoAdjuntoDAO;
        }

        public List<Parametro> getLstPrioridad() {
            return lstPrioridad;
        }

        public void setLstPrioridad(List<Parametro> lstPrioridad) {
            this.lstPrioridad = lstPrioridad;
        }

        public ParametroService getParametroService() {
            return parametroService;
        }

        public void setParametroService(ParametroService parametroService) {
            this.parametroService = parametroService;
        }
        
        public String getServicio() {
		return servicio;
	}

        public List<FilaHojaFirma> getHojaFirma() {
            return hojaFirma;
        }

        public void setHojaFirma(List<FilaHojaFirma> hojaFirma) {
            this.hojaFirma = hojaFirma;
        }

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public List<ReporteAPN10> getListaReporteAPN10() {
		return listaReporteAPN10;
	}

	public void setListaReporteAPN10(List<ReporteAPN10> listaReporteAPN10) {
		this.listaReporteAPN10 = listaReporteAPN10;
	}


        public String getIdAreaDestino() {
		return idAreaDestino;
	}

	public void setIdAreaDestino(String idAreaDestino) {
		this.idAreaDestino = idAreaDestino;
	}

        
	public List<ReporteAPN9> getListaReporteAPN9() {
		return listaReporteAPN9;
	}

	public void setListaReporteAPN9(List<ReporteAPN9> listaReporteAPN9) {
		this.listaReporteAPN9 = listaReporteAPN9;
	}

	public List<NodoExpReporteAPN8> getListaReporteAPN8() {
		return listaReporteAPN8;
	}

	public void setListaReporteAPN8(List<NodoExpReporteAPN8> listaReporteAPN8) {
		this.listaReporteAPN8 = listaReporteAPN8;
	}


	public List<Documentofedateado> getListaDocReporteAPN7() {
		return listaDocReporteAPN7;
	}

	public void setListaDocReporteAPN7(List<Documentofedateado> listaDocReporteAPN7) {
		this.listaDocReporteAPN7 = listaDocReporteAPN7;
	}

	private List<NodoDocConsolidadoReporteAPN4> listaConsolidadoReporteAPN4;


	public List<NodoDocConsolidadoReporteAPN4> getListaConsolidadoReporteAPN4() {
		return listaConsolidadoReporteAPN4;
	}

	public void setListaConsolidadoReporteAPN4(
			List<NodoDocConsolidadoReporteAPN4> listaConsolidadoReporteAPN4) {
		this.listaConsolidadoReporteAPN4 = listaConsolidadoReporteAPN4;
	}

	public List<NodoDocReporteAPN4> getListaDocReporteAPN4() {
		return listaDocReporteAPN4;
	}

	public void setListaDocReporteAPN4(List<NodoDocReporteAPN4> listaDocReporteAPN4) {
		this.listaDocReporteAPN4 = listaDocReporteAPN4;
	}

	public List<TrazabilidadDocumentaria> getHojaTrazabilidadDocumentaria() {
		return hojaTrazabilidadDocumentaria;
	}

	public void setHojaTrazabilidadDocumentaria(
			List<TrazabilidadDocumentaria> hojaTrazabilidadDocumentaria) {
		this.hojaTrazabilidadDocumentaria = hojaTrazabilidadDocumentaria;
	}


	public DocumentoReferenciaService getDocumentoReferenciaService() {
		return documentoReferenciaService;
	}

	public void setDocumentoReferenciaService(
			DocumentoReferenciaService documentoReferenciaService) {
		this.documentoReferenciaService = documentoReferenciaService;
	}

	private Unidad unidad;
	private Map<String, Object> mapSession;

	public String reporteDocumentosAlertas(){
		return "reporteDocumentosAlertas";
	}

	public String reporteDocumentosRecibidos(){
		return "reporteDocumentosRecibidos";
	}

	public String reporteDocumentosCompartidos(){
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setAttribute("usuarioCompartido", request.getParameter("usuarioCompartido"));
		return "reporteDocumentosCompartidos";
	}

	public String reporteDocumentosInformativos(){
		return "reporteDocumentosInformativos";
	}

	public String reporteAPN1(){
                mapSession = ActionContext.getContext().getSession();
		Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		log.debug("ReporteAPN1 usuario "+usuario.getIdusuario());
		usuario = usuarioService.findByIdUsuario(usuario.getIdusuario());
		this.idAreaOrigen=String.valueOf(usuario.getUnidad().getIdunidad().toString()) ;
		this.idAreaDestino=String.valueOf(usuario.getUnidad().getIdunidad().toString()) ;
		log.debug("ReporteAPN1 idArea "+idAreaOrigen);
		return "reporteAPN1";
	}

	public String reporteAPN2(){
		mapSession = ActionContext.getContext().getSession();
		Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		log.debug("ReporteAPN2 usuario "+usuario.getIdusuario());
		usuario = usuarioService.findByIdUsuario(usuario.getIdusuario());
		this.idAreaOrigen=String.valueOf(usuario.getUnidad().getIdunidad().toString()) ;
		log.debug("ReporteAPN2 idArea "+idAreaOrigen);
		return "reporteAPN2";
	}

	public String reporteAPN3(){
		log.debug("ReporteAPN3");
		return "reporteAPN3";
	}

	public String reporteAPN4(){
		log.debug("ReporteAPN4");
		return "reporteAPN4";
	}

	public String reporteAPN5(){
                log.debug("ReporteAPN5");
		mapSession = ActionContext.getContext().getSession();
                Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                log.debug("ReporteAPN5 usuario "+usuario.getIdusuario());
                usuario = usuarioService.findByIdUsuario(usuario.getIdusuario());
                this.idAreaOrigen=String.valueOf(usuario.getUnidad().getUnidadgrupo().toString()) ;
                log.debug("ReporteAPN5 idArea "+idAreaOrigen);
                return "reporteAPN5";
	}

	public String reporteAPN6(){
		log.debug("ReporteAPN6");
		return "reporteAPN6";
	}

	public String reporteAPN8(){
		log.debug("ReporteAPN8");
		return "reporteAPN8";
	}

	public String reporteAPN9(){
		log.debug("ReporteAPN9");
		return "reporteAPN9";
	}

	public String reporteAPN10(){
		log.debug("ReporteAPN10");
		return "reporteAPN10";
	}


	public void exportarExcelAPN1() throws ServletException, IOException {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();

		try {


			this.idAreaOrigen = new String(request.getParameter("idAreaOrigenH").getBytes(
					"ISO-8859-1"), "UTF-8");
			this.idTipoDocumento = new String(request.getParameter(
					"idTipoDocumentoH").getBytes("ISO-8859-1"), "UTF-8");
			this.idPrioridad = new String(request.getParameter("idPrioridadH")
					.getBytes("ISO-8859-1"), "UTF-8");
			this.fechaDesde = new String(request.getParameter("fechaDesdeH")
					.getBytes("ISO-8859-1"), "UTF-8");
			this.fechaHasta = new String(request.getParameter("fechaHastaH")
					.getBytes("ISO-8859-1"), "UTF-8");
			this.areaOrigen = new String(request.getParameter("areaOrigenH")
					.getBytes("ISO-8859-1"), "UTF-8");

			this.idAreaDestino = new String(request.getParameter("idAreaDestinoH").getBytes(
					"ISO-8859-1"), "UTF-8");

			listaReporteAPN1 = reporteAPNService.getListaReporteAPN1(idAreaOrigen, idTipoDocumento, idPrioridad, fechaDesde, fechaHasta,idAreaDestino);
		    HttpServletRequestWrapper srw = new HttpServletRequestWrapper(request);
			String ruta = srw.getRealPath("");
			ruta += "/excel/ReporteAPN1.xls";
		    HSSFWorkbook objetoExcel = getReporteAPN1HSSFW(listaReporteAPN1,ruta, fechaDesde, fechaHasta, areaOrigen);
		    LBTRUtil.ExportExcel(objetoExcel, response, "ReporteAPN1");


		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public HSSFWorkbook getReporteAPN1HSSFW(List listResultados, String ruta, String fechaDesde, String fechaHasta, String areaOrigen){
		{
			try{
			    Map beans = new HashMap();
		        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd/MM/yyyy hh:mm");
		        beans.put("row", listResultados);
		        beans.put("fechaDesde", fechaDesde);
		        beans.put("fechaHasta", fechaHasta);
		        beans.put("areaOrigen", areaOrigen);
		        beans.put("dateFormat", dateFormat);
		        XLSTransformer transformer = new XLSTransformer();
		        File plantilla  = new File (ruta);
		        FileInputStream plantillaStream =  new FileInputStream(plantilla);
		        return  transformer.transformXLS ( plantillaStream , beans ) ;
			} catch (Exception e) {
				e.printStackTrace();
				return null ;
			}
	    }
	}

    public String verTramiteDocumentario() {
		//areas = unidadService.buscarUnidadesFuncionales();
    	documento = documentoService.findByIdDocumento(idDocumento);
    	int iddoc = documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : idDocumento;

		if (documento.getID_CODIGO()!=null){
			hojaTrazabilidadDocumentaria =  reporteAPNService.generarTramiteDocumentario(documento.getID_CODIGO());
		}else{
			DocumentoReferencia r = new DocumentoReferencia();
			r.setIdDocumento(iddoc);
			try{
			  //  r = documentoReferenciaService.getDocumento(r);
			}catch(Exception e){

			}

			if (r!=null){
				//hojaTrazabilidadDocumentaria =  reporteAPNService.generarTramiteDocumentario(r.getId_codigo());
                        }
              }


		return "TrazabilidadDocumentaria";
	}

        public String verHojaFirma() {

    	  try {
          	Map<String, Object> mapSession = ActionContext.getContext().getSession();
          	Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
          	if (usuario==null)
          	   return Action.ERROR;

          }catch (Exception e){
              e.printStackTrace();
          }

	  documento = documentoService.findByIdDocumento(idDocumento);
	  int iddoc = documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : idDocumento;
	  hojaFirma = reporteAPNService.generarHojaFirma(iddoc);
          //trazabilidad = reporteAPNService.buscarUltimaTraza(iddoc);
          lstPrioridad = parametroService.findByTipo(Constantes.PARAMETRO_TIPO_PRIORIDAD);
                
          lstAdjuntos = new ArrayList<String>();
          List<DocumentoAdjunto> lstDocumentoAdjunto =documentoAdjuntoDAO.findByListDocumentoAdjunto(iddoc);
          List<Parametro> lstTipoAdjunto = parametroService.findByTipo(Constantes.PARAMETRO_TIPOS_DE_ADJUNTOS_MP);
          List<Parametro> lstCopia = parametroService.findByTipo(Constantes.PARAMETRO_ADJUNTO_COPIA_ORIGINAL);
            
          if (lstDocumentoAdjunto!=null){
                  for(int i=0;i<lstDocumentoAdjunto.size();i++){
                     String valor = ""; 
                     for(int j=0;j<lstTipoAdjunto.size();j++){
                       if (lstTipoAdjunto.get(j).getValor().equals(lstDocumentoAdjunto.get(i).getCodTipoAdj())){
                           valor = lstTipoAdjunto.get(j).getDescripcion();
                           for(int k=0;k<lstCopia.size();k++){
                              if (lstCopia.get(k).getValor().equals(lstDocumentoAdjunto.get(i).getCopOrig())){
                                  valor = valor + " " + lstCopia.get(k).getDescripcion() + " (" + lstDocumentoAdjunto.get(i).getNroAdj() + ")";
                                  lstAdjuntos.add(valor);
                                  break;
                              }
                           }
                           break;
                       } 
                     }      
                  }    
           }
                  
	    return "HojaFirma";
	}

        
        public String verHojaRuta() {

    	    try {
          	Map<String, Object> mapSession = ActionContext.getContext().getSession();
          	Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
          	if (usuario==null)
          	   return Action.ERROR;

            }catch (Exception e){
                e.printStackTrace();
            }
            
            documento = documentoService.findByIdDocumento(idDocumento);
	    int iddoc = documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : idDocumento;
            hojaRuta = reporteAPNService.generarHojaRuta(iddoc);
	    lstPrioridad = parametroService.findByTipo(Constantes.PARAMETRO_TIPO_PRIORIDAD);
                
            lstAdjuntos = new ArrayList<String>();
            List<DocumentoAdjunto> lstDocumentoAdjunto =documentoAdjuntoDAO.findByListDocumentoAdjunto(iddoc);
            List<Parametro> lstTipoAdjunto = parametroService.findByTipo(Constantes.PARAMETRO_TIPOS_DE_ADJUNTOS_MP);
            List<Parametro> lstCopia = parametroService.findByTipo(Constantes.PARAMETRO_ADJUNTO_COPIA_ORIGINAL);
            
            if (lstDocumentoAdjunto!=null){
                  for(int i=0;i<lstDocumentoAdjunto.size();i++){
                     String valor = ""; 
                     for(int j=0;j<lstTipoAdjunto.size();j++){
                       if (lstTipoAdjunto.get(j).getValor().equals(lstDocumentoAdjunto.get(i).getCodTipoAdj())){
                           valor = lstTipoAdjunto.get(j).getDescripcion();
                           for(int k=0;k<lstCopia.size();k++){
                              if (lstCopia.get(k).getValor().equals(lstDocumentoAdjunto.get(i).getCopOrig())){
                                  valor = valor + " " + lstCopia.get(k).getDescripcion() + " (" + lstDocumentoAdjunto.get(i).getNroAdj() + ")";
                                  lstAdjuntos.add(valor);
                                  break;
                              }
                           }
                           break;
                       } 
                     }      
                  }    
            }
            
            if (codigoVirtual!=null){
                IotdtmDocExterno iotdtmDocExterno = documentoExternoVirtualDAO.buscarDocumentoVirtual(codigoVirtual);
                if (iotdtmDocExterno!=null && (iotdtmDocExterno.getSidemiext().getCflgest()=='R' || iotdtmDocExterno.getSidemiext().getCflgest()=='O' || iotdtmDocExterno.getSidemiext().getCflgest()=='S')){
                    listIotdtcDespacho = new ArrayList<IotdtcDespacho>();
                    listIotdtcDespacho.add(iotdtmDocExterno.getSidemiext());
                }
            }else{
                List<IotdtmDocExterno> lstIotdtmDocExterno = documentoExternoVirtualDAO.buscarDocumentoProcesadoDespachoVirtual(documento.getID_CODIGO());
                if (lstIotdtmDocExterno!=null && lstIotdtmDocExterno.size()>0){
                    listIotdtcDespacho = new ArrayList<IotdtcDespacho>();
                    for(int i=0;i<lstIotdtmDocExterno.size();i++){
                        listIotdtcDespacho.add(lstIotdtmDocExterno.get(i).getSidemiext());
                    }
                }
            }
            
            if (documento.getNroVirtual()!=null){
                IotdtmDocExterno iotdtmDocExterno = documentoExternoVirtualDAO.buscarDocumentoVirtual(documento.getNroVirtual());
                if (iotdtmDocExterno!=null){
                  documento.setVcuo(iotdtmDocExterno.getSidrecext().getVcuo());
                }else{
                  documento.setVcuo("");
                }
            }
                   
            return "HojaRuta";
	}
        
        public String verHojaRutaEmail() {

    	    try {
          	Map<String, Object> mapSession = ActionContext.getContext().getSession();
          	Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO_EMAIL);
          	if (usuario==null)
          	   return Action.ERROR;

            }catch (Exception e){
                e.printStackTrace();
            }

            documento = documentoService.findByIdDocumento(idDocumento);
	    int iddoc = documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : idDocumento;
	    hojaRuta = reporteAPNService.generarHojaRuta(iddoc);
	    lstPrioridad = parametroService.findByTipo(Constantes.PARAMETRO_TIPO_PRIORIDAD);
                
            lstAdjuntos = new ArrayList<String>();
            List<DocumentoAdjunto> lstDocumentoAdjunto =documentoAdjuntoDAO.findByListDocumentoAdjunto(documento.getIdDocumento());
            List<Parametro> lstTipoAdjunto = parametroService.findByTipo(Constantes.PARAMETRO_TIPOS_DE_ADJUNTOS_MP);
            List<Parametro> lstCopia = parametroService.findByTipo(Constantes.PARAMETRO_ADJUNTO_COPIA_ORIGINAL);
            
            if (lstDocumentoAdjunto!=null){
                  for(int i=0;i<lstDocumentoAdjunto.size();i++){
                     String valor = ""; 
                     for(int j=0;j<lstTipoAdjunto.size();j++){
                       if (lstTipoAdjunto.get(j).getValor().equals(lstDocumentoAdjunto.get(i).getCodTipoAdj())){
                           valor = lstTipoAdjunto.get(j).getDescripcion();
                           for(int k=0;k<lstCopia.size();k++){
                              if (lstCopia.get(k).getValor().equals(lstDocumentoAdjunto.get(i).getCopOrig())){
                                  valor = valor + " " + lstCopia.get(k).getDescripcion() + " (" + lstDocumentoAdjunto.get(i).getNroAdj() + ")";
                                  lstAdjuntos.add(valor);
                                  break;
                              }
                           }
                           break;
                       } 
                     }      
                  }    
            } 
                   
            return "HojaRuta";
	}

	public String verHojaRutaExpediente(){
		documento = documentoService.findByIdDocumento(idDocumento);
		hojaRuta = reporteAPNService.generarHojaRutaExpediente(documento.getExpediente().getId());
		//trazabilidad = reporteAPNService.buscarUltimaTraza(iddoc);
		return "HojaRutaExpediente";
	}



	public String listarReporte2(){
		listaReporteAPN2 = reporteAPNService.getListaReporteAPN2(objFiltro);
		grupoProceso = "";
		if(!StringUtils.isEmpty(objFiltro.getGrupoProceso()) && !objFiltro.getGrupoProceso().equals("0")){
			if(listaReporteAPN2 != null && !listaReporteAPN2.isEmpty()){
				Documento documento = documentoService.findByIdDocumento(listaReporteAPN2.get(0).getIdDocumento());
				//grupoProceso = documento.getExpediente().getProceso().getIdgrupoproceso().getNombre();
			}
		}
		return Action.SUCCESS + "ReporteAPN2";
	}



	public String listarReporte4(){
		log.debug("listarReporte4.");
		if(StringUtils.isEmpty(cliente)){
			objFiltro.setCliente("");
		}

		objFiltro.setFechaDesde(objFiltro.getFechaDesde().replace("-", ""));
		objFiltro.setFechaHasta(objFiltro.getFechaHasta().replace("-", ""));

		listaReporteAPN4 = reporteAPNService.getListaReporteAPN4(objFiltro);

		return Action.SUCCESS + "ReporteAPN4";
	}


	public String listarReporte7(){
		log.debug("listarReporte7.");
		HttpServletRequest request = ServletActionContext.getRequest();
		mapSession = ActionContext.getContext().getSession();
        listaDocReporteAPN7 = null;

        try{
	        this.fechaDesde = new String(request.getParameter("fechaDesde")
					.getBytes("ISO-8859-1"), "UTF-8");
			this.fechaHasta = new String(request.getParameter("fechaHasta")
					.getBytes("ISO-8859-1"), "UTF-8");
			this.servicio = new String(request.getParameter("servicio")
					.getBytes("ISO-8859-1"), "UTF-8");



        }catch(Exception e){

        }
		Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
        listaDocReporteAPN7 = documentoService.buscarLstDocumentoFedateado(usuario.getIdusuario(), fechaDesde, fechaHasta, servicio);

        return Action.SUCCESS + "ReporteAPN7";
	}

	public String listarReporte5(){
		log.debug("listarReporte5.");
		mapSession = ActionContext.getContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
        String area = null;
        String consolidado = null;
        listaDocReporteAPN4 = null;
        listaConsolidadoReporteAPN4 = null;

        try{
		     area = new String(request.getParameter("area").getBytes("ISO-8859-1"), "UTF-8");
		     consolidado = new String(request.getParameter("consolidado"));

		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			return "error";
		}

        if (consolidado.equals("0")){
            listaDocReporteAPN4 = reporteAPNService.getListaDocReporteAPN4(area);
		    return Action.SUCCESS + "ReporteAPN5";
        }else{
        	Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
        	listaConsolidadoReporteAPN4 = reporteAPNService.getListaConsolidadoReporteAPN4(area, usuario.getUnidad().getIdunidad());
        	 return Action.SUCCESS + "ReporteAPN6";
        }


	}

	public String listarReporte1() {
		HttpServletRequest request = ServletActionContext.getRequest();

		try {

			this.idAreaOrigen = new String(request.getParameter("area").getBytes(
					"ISO-8859-1"), "UTF-8");

			 this.idAreaDestino = new String(request.getParameter("areaDestino").getBytes(
					"ISO-8859-1"), "UTF-8");

			this.idTipoDocumento = new String(request.getParameter(
					"tipodocumento").getBytes("ISO-8859-1"), "UTF-8");
			this.idPrioridad = new String(request.getParameter("prioridad")
					.getBytes("ISO-8859-1"), "UTF-8");
			this.fechaDesde = new String(request.getParameter("fechaDesde")
					.getBytes("ISO-8859-1"), "UTF-8");
			this.fechaHasta = new String(request.getParameter("fechaHasta")
					.getBytes("ISO-8859-1"), "UTF-8");
			if(((idAreaOrigen.toString().trim()).equals("Todos"))){
				this.areaOrigen="TODAS LAS AREAS";
			}else{
				unidad= unidadService.buscarObjPor(Integer.parseInt(idAreaOrigen.toString().trim()));
				this.areaOrigen= unidad.getNombre().toString();
			}


		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			return "error";
		}

		log.debug("(listarReporte1())"+"idareaOrigen "+ idAreaOrigen+" idtipodocumento "+ idTipoDocumento+" idprioridad "+ idPrioridad + "fechaDesde: "+ fechaDesde+"fechaHasta"+ fechaHasta);

		if (checkErrorFechas()) {
			listaReporteAPN1 = reporteAPNService.getListaReporteAPN1(idAreaOrigen, idTipoDocumento, idPrioridad, fechaDesde, fechaHasta, idAreaDestino);
			exportarExcelReporte1();
			return Action.SUCCESS + "ReporteAPN1";
		} else {
			return "errorFechas";
		}

	}


	public String listarReporte10() {
		HttpServletRequest request = ServletActionContext.getRequest();

		try {

			this.idAreaOrigen = new String(request.getParameter("area").getBytes(
					"ISO-8859-1"), "UTF-8");


			this.fechaDesde = new String(request.getParameter("fechaDesde")
					.getBytes("ISO-8859-1"), "UTF-8");
			this.fechaHasta = new String(request.getParameter("fechaHasta")
					.getBytes("ISO-8859-1"), "UTF-8");



		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
			return "error";
		}



		if (checkErrorFechas()) {
			listaReporteAPN10 = reporteAPNService.getListaReporteAPN10(idAreaOrigen,  fechaDesde, fechaHasta);
			exportarExcelReporte10();
			return Action.SUCCESS + "ReporteAPN10";
		} else {
			return "errorFechas";
		}

	}

	public String listarReporte3(){
		mapSession = ActionContext.getContext().getSession();

		log.debug("listarReporte3.");


		if(StringUtils.isEmpty(cliente)){
			objFiltro.setCliente("");
		}
		listaReporteAPN3 = reporteAPNService.getListaReporteAPN3(objFiltro);
		size=0;
		if(listaReporteAPN3 != null && !listaReporteAPN3.isEmpty()){
			for(NodoExpReporteAPN3 nodo : listaReporteAPN3){
				if(nodo.getCantidadDocumentos().intValue() > size.intValue()){
					size = new Integer(nodo.getCantidadDocumentos().intValue());
				}
			}
		}

		exportarExcelReporte3();
		return Action.SUCCESS + "ReporteAPN3";
	}



	public String listarReporte8(){
		mapSession = ActionContext.getContext().getSession();

		log.debug("listarReporte8.");


		if(StringUtils.isEmpty(cliente)){
			objFiltro.setCliente("");
		}
		listaReporteAPN8 = reporteAPNService.getListaReporteAPN8(objFiltro);
		size=0;
		if(listaReporteAPN8 != null && !listaReporteAPN8.isEmpty()){
			for(NodoExpReporteAPN8 nodo : listaReporteAPN8){
				if(nodo.getCantidadDocumentos().intValue() > size.intValue()){
					size = new Integer(nodo.getCantidadDocumentos().intValue());
				}
			}
		}

		exportarExcelReporte8();
		return Action.SUCCESS + "ReporteAPN8";
	}

	public String listarReporte9(){
		mapSession = ActionContext.getContext().getSession();
		HttpServletRequest request=ServletActionContext.getRequest();

		log.debug("listarReporte9.");

		try{
	        String area_ =  new String(request.getParameter("area")
					.getBytes("ISO-8859-1"), "UTF-8");
	        String mes_ =  new String(request.getParameter("mes")
					.getBytes("ISO-8859-1"), "UTF-8");
	        String ano_ =  new String(request.getParameter("ano")
					.getBytes("ISO-8859-1"), "UTF-8");
	        String tipodocumento_ =  new String(request.getParameter("tipodocumento")
					.getBytes("ISO-8859-1"), "UTF-8");
	        System.out.println("area=" + area_ + "-" + mes_ + "-" + ano_ + "-" + tipodocumento_);
			listaReporteAPN9 = reporteAPNService.getListaReporteAPN9(area_, ano_, mes_, tipodocumento_);

			exportarExcelReporte9();
        }catch(Exception e){
        	e.printStackTrace();
        }

		return Action.SUCCESS + "ReporteAPN9";
	}


	public String exportarExcelReporte1()
	{

		try {

			HttpServletRequest request=ServletActionContext.getRequest();
			mapSession = ActionContext.getContext().getSession();

			String fileName = "R1_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +UUID.randomUUID().toString().concat(".xls");
			List<Map<String, Object>> html_ = obtenerExcelReporte1(listaReporteAPN1);

			InputStream reporteStream = getClass().getResourceAsStream("/org/osinerg/utils/ReporteDocumentos_Emitidos.jasper");
			HashMap map = new HashMap();

			map.put("areaOrigen", areaOrigen);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
            map.put("logo", "/org/osinerg/utils/logo-apn.gif");

			JRXlsExporter exporter = new JRXlsExporter();
			JasperPrint print = JasperFillManager.fillReport(reporteStream,map,new JRBeanCollectionDataSource(html_));

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/")+ "export/" + fileName);
		    exporter.exportReport();

		    request.setAttribute("url", "/export/" + fileName);

			}
		catch (Exception e1) {
			e1.printStackTrace();
			log.error(e1.getMessage(),e1);
		}

		return null;
	}

	public String exportarExcelReporte10()
	{

		try {

			HttpServletRequest request=ServletActionContext.getRequest();
			mapSession = ActionContext.getContext().getSession();

			String fileName = "R10_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +UUID.randomUUID().toString().concat(".xls");
			List<Map<String, Object>> html_ = obtenerExcelReporte10(listaReporteAPN10);

			InputStream reporteStream = getClass().getResourceAsStream("/org/osinerg/utils/ReporteDocumentos_MesaParte.jasper");
			HashMap map = new HashMap();

			//map.put("areaOrigen", areaOrigen);
			//map.put("fechaDesde", fechaDesde);
			//map.put("fechaHasta", fechaHasta);
            map.put("logo", "/org/osinerg/utils/logo-apn.gif");

			JRXlsExporter exporter = new JRXlsExporter();
			JasperPrint print = JasperFillManager.fillReport(reporteStream,map,new JRBeanCollectionDataSource(html_));

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/")+ "export/" + fileName);
		    exporter.exportReport();

		    request.setAttribute("url", "/export/" + fileName);

			}
		catch (Exception e1) {
			e1.printStackTrace();
			log.error(e1.getMessage(),e1);
		}

		return null;
	}





	public String exportarExcelReporte3()
	{

		try {

			HttpServletRequest request=ServletActionContext.getRequest();
			mapSession = ActionContext.getContext().getSession();
			String fileName = "R3_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" + UUID.randomUUID().toString().concat(".xls");
			List<Map<String, Object>> html_ = obtenerExcelReporte3(listaReporteAPN3, size, filtroPlazo);

			InputStream reporteStream = getClass().getResourceAsStream("/org/osinerg/utils/ReporteDocumentos.jasper");
			HashMap map = new HashMap();
			map.put("paramFecConsulta", "Reporte");

			JRXlsExporter exporter = new JRXlsExporter();
			JasperPrint print = JasperFillManager.fillReport(reporteStream,map,new JRBeanCollectionDataSource(html_));

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/")+ "export/" + fileName);
		    exporter.exportReport();

		    request.setAttribute("url", "/export/" + fileName);

			}
		catch (Exception e1) {
			e1.printStackTrace();
			log.error(e1.getMessage(),e1);
		}

		return null;
	}

	public String exportarExcelReporte8()
	{

		try {

			HttpServletRequest request=ServletActionContext.getRequest();
			mapSession = ActionContext.getContext().getSession();
			String fileName = "R8_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" + UUID.randomUUID().toString().concat(".xls");
			List<Map<String, Object>> html_ = obtenerExcelReporte8(listaReporteAPN8, size, filtroPlazo);

			InputStream reporteStream = getClass().getResourceAsStream("/org/osinerg/utils/ReporteDocumentosTramite.jasper");
			HashMap map = new HashMap();
			map.put("paramFecConsulta", "Reporte");

			JRXlsExporter exporter = new JRXlsExporter();
			JasperPrint print = JasperFillManager.fillReport(reporteStream,map,new JRBeanCollectionDataSource(html_));

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/")+ "export/" + fileName);
		    exporter.exportReport();

		    request.setAttribute("url", "/export/" + fileName);

			}
		catch (Exception e1) {
			e1.printStackTrace();
			log.error(e1.getMessage(),e1);
		}

		return null;
	}



	public String exportarExcelReporte9()
	{

		try {

			HttpServletRequest request=ServletActionContext.getRequest();
			mapSession = ActionContext.getContext().getSession();
			String fileName = "R9_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" + UUID.randomUUID().toString().concat(".pdf");
			List<Map<String, Object>> html_ = obtenerExcelReporte9(listaReporteAPN9);

			InputStream reporteStream = getClass().getResourceAsStream("/org/osinerg/utils/ReporteDocumentosMTC.jasper");
			HashMap map = new HashMap();
			map.put("paramFecConsulta", "Reporte");
			map.put("logo", "/org/osinerg/utils/logo-apn.gif");

			JRPdfExporter exporter = new JRPdfExporter();
			JasperPrint print = JasperFillManager.fillReport(reporteStream,map,new JRBeanCollectionDataSource(html_));

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/")+ "export/" + fileName);
                        exporter.exportReport();

		    request.setAttribute("url", "/export/" + fileName);

			}
		catch (Exception e1) {
			e1.printStackTrace();
			log.error(e1.getMessage(),e1);
		}

		return null;
	}

	private List<Map<String, Object>> obtenerExcelReporte1(List<ReporteAPN1> listaReporteAPN1) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = null;
	    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm");


	    if (listaReporteAPN1!=null && listaReporteAPN1.size()>0){
		    for(ReporteAPN1 fila : listaReporteAPN1){
				 m = new HashMap<String, Object>();
                 m.put("nroExpediente", fila.getNroExpediente());
                 m.put("proceso", fila.getProceso());
                 m.put("fechaCreacion", new SimpleDateFormat("dd-MM-yyyy hh:mm").format(fila.getFechaCreacion()));
                 m.put("tipoDocumento", fila.getTipoDocumento());
                 m.put("nroDocumento", fila.getNroDocumento());
                 m.put("fechaDocumento", new SimpleDateFormat("dd-MM-yyyy hh:mm").format(fila.getFechaDocumento()));
                 m.put("cliente", fila.getCliente());
                 m.put("destinatario", fila.getDestinatario());
                 m.put("asuntoDocumento", fila.getAsuntoDocumento());
                 m.put("prioridad", fila.getPrioridad());
                 m.put("areaDestino", fila.getAreaDestino());
                 list.add(m);
			}
	    }


          return list;
    }

	private List<Map<String, Object>> obtenerExcelReporte10(List<ReporteAPN10> listaReporteAPN10) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = null;
	    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm");


	    if (listaReporteAPN10!=null && listaReporteAPN10.size()>0){
		    for(ReporteAPN10 fila : listaReporteAPN10){
				 m = new HashMap<String, Object>();
                 m.put("nroexpediente", fila.getNroExpediente());
                 m.put("tipodocumento", fila.getTipoDocumento());
                 m.put("documento", fila.getNroDocumento());
                 m.put("asunto", fila.getAsunto());
                 m.put("administrado", fila.getCliente());
                 m.put("fechacreacion", f.format(fila.getFechaCreacion()));
                 m.put("area_t", fila.getAreas());
                 m.put("tipodocumento_t", fila.getTipoDocumentoAt());
                 m.put("documento_t", fila.getNroDocumentoAt());
                 m.put("asunto_t", fila.getAsuntoAt());
                 m.put("fechacreacion_t", fila.getFechaCreacionAt()==null?"":f.format(fila.getFechaCreacionAt()));

                 list.add(m);
			}
	    }


          return list;
    }

	private List<Map<String, Object>> obtenerExcelReporte3(List<NodoExpReporteAPN3> listaReporteAPN3, Integer tamano, String filtroPlazo) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = null;
	    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm");

	    for(NodoExpReporteAPN3 fila : listaReporteAPN3){
			 m = new HashMap<String, Object>();

			long tiempoTramite = 0l;
			long tiempoIntervalo = 0l;
		    int j=0;
			List<String> tiempoDias = new ArrayList<String>();
			for(NodoDocReporteAPN3 doc : fila.getDocumentos()){
				String diasTramite = "";
					if(doc.getFechaTransferencia() != null && doc.getFechaCreacion() != null){
						long tiempo = doc.getDias();
						tiempoTramite += tiempo;
						diasTramite = DateUtil.milisegundosADias(tiempo);
					}else{
						diasTramite = "-";
					}

				tiempoDias.add(diasTramite);
				j++;
			}

		     boolean fueraPlazo = false;
		     tiempoIntervalo = fila.getDias();//fila.getFechalimite().getTime() - fila.getFechaCreacionExterna().getTime();
			 if (tiempoTramite > tiempoIntervalo)  //tiempoTramite < fila.getPlazoProceso()*Constantes.MILISEGUNDOS_DIA;
		       fueraPlazo = true;

		     if(tiempoIntervalo>0 && (filtroPlazo.equals("T") || (filtroPlazo.equals("D") && !fueraPlazo) || (filtroPlazo.equals("F") && fueraPlazo))){
		    	   m.put("nroExpediente", fila.getNumeroExpediente());
		           m.put("fechaExpediente", f.format(fila.getFechaCreacion()));
		           m.put("documento",fila.getNroDocumento().replaceAll("&", "&#38;"));
		           m.put("area", fila.getDesArea());
		           m.put("prioridad",fila.getPrioridad());
		           m.put("administrado",fila.getAdministrado().replaceAll("&", "&#38;"));
		           m.put("asunto",fila.getAsunto().replaceAll("&", "&#38;"));
		           m.put("tiempoAtencion",DateUtil.milisegundosADias(fila.getDias()));
		           m.put("estado", fila.getEstado()==null?"":fila.getEstado());
				   j=0;

				   for(NodoDocReporteAPN3 doc : fila.getDocumentos()){
						m.put("ultimoDocumento",  doc.getDocumento().replaceAll("&", "&#38;"));
						m.put("areaDestino", doc.getAreaDestino());
						m.put("fechaCreacion", doc.getFechaCreacionStr());
						m.put("fechaTransferencia",doc.getFechaTransferenciaStr());
						m.put("tiempoTransferencia", tiempoDias.get(j));
					    j++;
					}
					while(j<tamano){
					    j++;
					}
					m.put("tiempoTramite", DateUtil.milisegundosADias(tiempoTramite));

					if(fueraPlazo){
						m.put("desPlazo","<font color='red'>Fuera del plazo</font>");
					}else{

						m.put("desPlazo","<font color='black'>Dentro del plazo</font>");
					}

					 list.add(m);
					}
		       }

          return list;
    }


	private List<Map<String, Object>> obtenerExcelReporte8(List<NodoExpReporteAPN8> listaReporteAPN8, Integer tamano, String filtroPlazo) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = null;
	    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm");

	    for(NodoExpReporteAPN8 fila : listaReporteAPN8){
			 m = new HashMap<String, Object>();

			long tiempoTramite = 0l;
			long tiempoIntervalo = 0l;
		    int j=0;
			List<String> tiempoDias = new ArrayList<String>();
			for(NodoDocReporteAPN8 doc : fila.getDocumentos()){
				String diasTramite = "";
					if(doc.getFechaTransferencia() != null && doc.getFechaCreacion() != null){
						long tiempo = doc.getDias();
						tiempoTramite += tiempo;
						diasTramite = DateUtil.milisegundosADias(tiempo);
					}else{
						diasTramite = "-";
					}

				tiempoDias.add(diasTramite);
				j++;
			}

		     boolean fueraPlazo = false;
		     tiempoIntervalo = fila.getDias();//fila.getFechalimite().getTime() - fila.getFechaCreacionExterna().getTime();
			 if (tiempoTramite > tiempoIntervalo)  //tiempoTramite < fila.getPlazoProceso()*Constantes.MILISEGUNDOS_DIA;
		       fueraPlazo = true;

		     if(tiempoIntervalo>0 && (filtroPlazo.equals("T") || (filtroPlazo.equals("D") && !fueraPlazo) || (filtroPlazo.equals("F") && fueraPlazo))){
		    	   m.put("nroExpediente", fila.getNumeroExpediente());
		           m.put("fechaExpediente", f.format(fila.getFechaCreacion()));
		           m.put("documento",fila.getNroDocumento().replaceAll("&", "&#38;"));
		           m.put("area", fila.getDesArea());
		           m.put("prioridad",fila.getPrioridad());
		           m.put("administrado",fila.getAdministrado().replaceAll("&", "&#38;"));
		           m.put("asunto",fila.getAsunto().replaceAll("&", "&#38;"));
		           m.put("tiempoAtencion",DateUtil.milisegundosADias(fila.getDias()));
		           m.put("estado", fila.getEstado()==null?"":fila.getEstado());
				   j=0;

				   for(NodoDocReporteAPN8 doc : fila.getDocumentos()){
						m.put("ultimoDocumento",  doc.getDocumento().replaceAll("&", "&#38;"));
						m.put("areaDestino", doc.getAreaDestino());
					//	m.put("fechaCreacion", doc.getFechaCreacionStr());
					//	m.put("fechaTransferencia",doc.getFechaTransferenciaStr());
						m.put("tiempoTransferencia", tiempoDias.get(j));
					    j++;
					}
					while(j<tamano){
					    j++;
					}
					m.put("tiempoTramite", DateUtil.milisegundosADias(tiempoTramite));

					if(fueraPlazo){
						m.put("desPlazo","<font color='red'>Fuera del plazo</font>");
					}else{

						m.put("desPlazo","<font color='black'>Dentro del plazo</font>");
					}

					 list.add(m);
					}
		       }

          return list;
    }


	private List<Map<String, Object>> obtenerExcelReporte9(List<ReporteAPN9> listaReporteAPN9) {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = null;
	    SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm");

	    for(ReporteAPN9 fila : listaReporteAPN9){
			 m = new HashMap<String, Object>();
             m.put("tipodocumento", fila.getTipoDocumento());
             m.put("documento", fila.getNroDocumento());
		     m.put("asunto", fila.getAsuntoDocumento());
             m.put("fechacreacion", f.format(fila.getFechaCreacion()));
             m.put("prioridad", fila.getPrioridad());
             m.put("administrado", fila.getCliente());
             m.put("area", fila.getAreaDestino());
		     list.add(m);
		 }


          return list;
    }

	private boolean checkErrorFechas(){
		/**
		 * Este error es causado cuando hay un conflicto entre el nombre de variables
		 * que son usadas en el reporte. Para que desaparezca debes revisar el nombre
		 * de las variables de los javascript y JSP.
		 */
		SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
		try{
			fechita.parse(fechaDesde);
		}catch(ParseException e){
			log.warn("ERROR!, ha ocurrido un error con el campo FechaDesde y se han desactivado los filtros");
			return false;
		}
		try{
			fechita.parse(fechaHasta);
		}catch(ParseException e){
			log.warn("ERROR!, ha ocurrido un error con el campo FechaHasta y se han desactivado los filtros");
			return false;
		}
		return true;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		ReporteAPNAction.log = log;
	}

	public ExpedienteService getExpedienteService() {
		return expedienteService;
	}

	public void setExpedienteService(ExpedienteService expedienteService) {
		this.expedienteService = expedienteService;
	}

	public Expediente getExpediente() {
		return expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public List<Unidad> getAreas() {
		return areas;
	}

	public void setAreas(List<Unidad> areas) {
		this.areas = areas;
	}

	public UnidadService getUnidadService() {
		return unidadService;
	}

	public void setUnidadService(UnidadService unidadService) {
		this.unidadService = unidadService;
	}

	public Trazabilidaddocumento getTrazabilidad() {
		return trazabilidad;
	}

	public void setTrazabilidad(Trazabilidaddocumento trazabilidad) {
		this.trazabilidad = trazabilidad;
	}

	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getIdPrioridad() {
		return idPrioridad;
	}

	public void setIdPrioridad(String idPrioridad) {
		this.idPrioridad = idPrioridad;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public ReporteAPNService getReporteAPNService() {
		return reporteAPNService;
	}

	public void setReporteAPNService(ReporteAPNService reporteAPNService) {
		this.reporteAPNService = reporteAPNService;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public DocumentoService getDocumentoService() {
		return documentoService;
	}

	public void setDocumentoService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	public Integer getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	public List<ReporteAPN1> getListaReporteAPN1() {
		return listaReporteAPN1;
	}

	public void setListaReporteAPN1(List<ReporteAPN1> listaReporteAPN1) {
		this.listaReporteAPN1 = listaReporteAPN1;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}


	public String getIdAreaOrigen() {
		return idAreaOrigen;
	}


	public void setIdAreaOrigen(String idAreaOrigen) {
		this.idAreaOrigen = idAreaOrigen;
	}


	public String getAreaOrigen() {
		return areaOrigen;
	}


	public void setAreaOrigen(String areaOrigen) {
		this.areaOrigen = areaOrigen;
	}


	public Map<String, Object> getMapSession() {
		return mapSession;
	}


	public void setMapSession(Map<String, Object> mapSession) {
		this.mapSession = mapSession;
	}


	public UsuarioService getUsuarioService() {
		return usuarioService;
	}


	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public List<FilaHojaRuta> getHojaRuta() {
		return hojaRuta;
	}

	public void setHojaRuta(List<FilaHojaRuta> hojaRuta) {
		this.hojaRuta = hojaRuta;
	}

	public BusquedaAvanzada getObjFiltro() {
		return objFiltro;
	}

	public void setObjFiltro(BusquedaAvanzada objFiltro) {
		this.objFiltro = objFiltro;
	}

	public List<FilaReporteAPN2> getListaReporteAPN2() {
		return listaReporteAPN2;
	}

	public void setListaReporteAPN2(List<FilaReporteAPN2> listaReporteAPN2) {
		this.listaReporteAPN2 = listaReporteAPN2;
	}

	public String getGrupoProceso() {
		return grupoProceso;
	}

	public void setGrupoProceso(String grupoProceso) {
		this.grupoProceso = grupoProceso;
	}

	public List<NodoExpReporteAPN3> getListaReporteAPN3() {
		return listaReporteAPN3;
	}

	public void setListaReporteAPN3(List<NodoExpReporteAPN3> listaReporteAPN3) {
		this.listaReporteAPN3 = listaReporteAPN3;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getFiltroPlazo() {
		return filtroPlazo;
	}

	public void setFiltroPlazo(String filtroPlazo) {
		this.filtroPlazo = filtroPlazo;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public List<NodoExpReporteAPN4> getListaReporteAPN4() {
		return listaReporteAPN4;
	}

	public void setListaReporteAPN4(List<NodoExpReporteAPN4> listaReporteAPN4) {
		this.listaReporteAPN4 = listaReporteAPN4;
	}

}
