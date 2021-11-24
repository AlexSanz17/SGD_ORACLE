/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import gob.ositran.siged.config.SigedProperties;
import gob.ositran.siged.service.AlfrescoWebscriptService; 
import org.ositran.daos.TrazabilidaddocumentoDAO;
import java.io.File; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;    
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.codec.binary.Base64;
import javax.persistence.NoResultException;
import javax.xml.stream.XMLStreamException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.ositran.daos.AuditoriaDAO;
import org.ositran.daos.DocumentoDAO;
import org.ositran.daos.DocumentoEnviadoDAO;
import org.ositran.daos.DocumentoFedatarioDAO;
import org.ositran.daos.DocumentoReferenciaDAO;
import org.ositran.daos.ExpedienteDAO;
import org.ositran.daos.NotificacionxEnumerarDAO;
import org.ositran.daos.ParametroDAO;
import org.ositran.daos.ProcesoDAO;
import org.ositran.daos.ProveidoDAO;
import org.ositran.daos.RolDAO;
import org.ositran.daos.SedeDAO;
import org.ositran.daos.NumeracionDAO;
import org.ositran.daos.TipodocumentoDAO;
import org.ositran.daos.TrazabilidadapoyoDAO;
import org.ositran.daos.TrazabilidadmovimientoDAO;
import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.Recurso;
import org.ositran.dojo.grid.ItemUF;
import org.ositran.dojo.tree.Nodo;
import org.ositran.dojo.tree.NodoArbol;
import org.ositran.dojo.tree.SimpleNode;
import org.ositran.dojo.tree.SimpleNodeLeaf;
import org.ositran.pojos.jasper.CargoReporte;
import org.ositran.siged.service.AlfrescoWSService;
import org.ositran.utils.ArchivoTemporal;
import org.ositran.utils.Constantes;
import org.ositran.utils.DocumentoDetail;
import org.ositran.utils.DocumentoList;
import org.ositran.utils.ExpedienteSearch;
import org.ositran.utils.FechaLimite;
import org.ositran.utils.StringUtil;
import org.ositran.utils.template.TemplateUtils;
import org.ositran.webservice.clientes.intalio.InvalidInputMessageFaultException;
import org.ositran.webservice.clientes.intalio.InvalidParticipantTokenFaultException;
import org.ositran.webservice.clientes.intalio.UnavailableTaskFaultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.btg.ositran.siged.domain.Estado;
import com.btg.ositran.siged.domain.Accion;
import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.DocumentoAdjunto;
import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.ArchivoPendiente;
import com.btg.ositran.siged.domain.Auditoria;
import com.btg.ositran.siged.domain.LegajoDocumento;
import com.btg.ositran.siged.domain.ReferenciaArchivo;
import com.btg.ositran.siged.domain.DiaFestivo;
import com.btg.ositran.siged.domain.Distrito;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoAnulado;
import com.btg.ositran.siged.domain.DocumentoAreaFuncional;
import com.btg.ositran.siged.domain.DocumentoAtendido;
import com.btg.ositran.siged.domain.DocumentoReferencia;
import com.btg.ositran.siged.domain.DocumentoReunion;
import com.btg.ositran.siged.domain.DocumentoDerivacion;
import com.btg.ositran.siged.domain.Documentoenviado;
import com.btg.ositran.siged.domain.Documentofedateado;
import com.btg.ositran.siged.domain.Documentoxexpediente;
import com.btg.ositran.siged.domain.DocumentoxexpedientePK;
import com.btg.ositran.siged.domain.Etapa;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.FilaBandejaUF;
import com.btg.ositran.siged.domain.IotdtcDespacho;
import com.btg.ositran.siged.domain.IotdtcRecepcion;
import com.btg.ositran.siged.domain.IotdtdAnexo;
import com.btg.ositran.siged.domain.IotdtdDocPrincipal;
import com.btg.ositran.siged.domain.IotdtmDocExterno;
import com.btg.ositran.siged.domain.Lista;
import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.Numeracion;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Perfil;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Proveido;
import com.btg.ositran.siged.domain.Rol;
import com.btg.ositran.siged.domain.Sede;
import com.btg.ositran.siged.domain.Serie;
import com.btg.ositran.siged.domain.Tipodocumento;
import com.btg.ositran.siged.domain.TramiteDocumentario;
import com.btg.ositran.siged.domain.TrazabilidadMovimiento;
import com.btg.ositran.siged.domain.Trazabilidadapoyo;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import com.opensymphony.xwork2.ActionContext;
import com.ositran.cmis.api.AlfrescoApiWs;
import com.ositran.pide.EndPoint;
import com.ositran.pide.EndPointRUC;
import com.ositran.pide.WSPideTramite;
import com.ositran.ws.CargoTramite;
import com.ositran.ws.ConsultaTramite;
import com.ositran.ws.DocumentoAnexo;
import com.ositran.ws.RecepcionTramite;
import com.ositran.ws.RespuestaCargoTramite;
import com.ositran.ws.RespuestaConsultaTramite;
import com.ositran.ws.RespuestaTramite;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.UUID;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.commons.lang.time.DateFormatUtils;
import org.ositran.daos.DespachoVirtualDAO;
import org.ositran.daos.DocAnexoVirtualDAO;
import org.ositran.daos.DocPrincipalVirtualDAO;
import org.ositran.daos.DocumentoAdjuntoDAO;
import org.ositran.daos.DocumentoAnuladoDAO;
import org.ositran.daos.DocumentoAtendidoDAO;
import org.ositran.daos.DocumentoDerivacionDAO;
import org.ositran.daos.DocumentoExternoVirtualDAO;
import org.ositran.daos.DocumentoPendienteDAO;
import org.ositran.daos.DocumentoReunionDAO;
import org.ositran.daos.FirmaArchivoDAO;
import org.ositran.daos.LegajoDocumentoDAO;
import org.ositran.daos.RecepcionVirtualDAO;
import org.ositran.daos.ReferenciaArchivoDAO;
import org.ositran.daos.SeguimientoXFirmaDAO;
import org.ositran.daos.UsuarioxunidadxfuncionDAO;
import org.ositran.dojo.grid.Item; 
import org.ositran.utils.DocumentoPublicar;
 
/**
 *
 * @author jbengoa (JUAN CARLOS BENGOA)
 */

public class DocumentoServiceImpl implements DocumentoService {   
        private String REPOSITORIO_ID  = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_ROOTID);
        private String USERCONSULTA=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_USUARIOEXTERNO);
        private String USERCONSULTA_CLAVE=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_USUARIOEXTERNO_CLAVE);
    
	private final Logger log = LoggerFactory.getLogger(DocumentoServiceImpl.class);
        private DocumentoFedatarioDAO documentoFedatarioDao;
	private AuditoriaDAO auditoriaDao;
	private DocumentoDAO documentoDao;
	private DocumentoEnviadoDAO documentoEnviadoDao;
        private DocumentoDerivacionDAO documentoDerivacionDAO;
	private NumeracionDAO numeracionDAO;
        private SeguimientoXFirmaDAO seguimientoXFirmaDAO;
        private ParametroDAO parametroDao;
	private ProcesoDAO procesoDao;
	private RolDAO rolDao;
	private SedeDAO sedeDao;
        private DocumentoPendienteDAO documentoPendienteDAO;
        private DocumentoAtendidoDAO documentoAtendidoDAO;
	private DocumentoReferenciaDAO documentoReferenciaDAO;
	private TipodocumentoDAO tipoDocumentoDao;
	private ProveidoDAO proveidoDAO;
	private TrazabilidadapoyoDAO trazabilidadDocumentoDao;
	private ExpedienteDAO expedienteDAO;
	private TrazabilidaddocumentoDAO trazabilidaddocumentoDAO;
	private TrazabilidadmovimientoDAO trazabilidadMovimientoDAO;
	private AccionService accionService;
	private ArchivoService archivoService;
	private ArchivopendienteService srvArchivoPendiente;
	private AuditoriaService auditoriaService;
	private DiafestivoService diaFestivoService;
	private NumeracionService numeracionService;
	private DistritoService distritoService;
	private DocumentoxexpedienteService documentoPorExpedienteService;
	private ExpedienteService expedienteService;
	private ListaService listaService;
	private NotificacionService notificacionService;
	private RepositorioService repositorioService;
	private TrazabilidaddocumentoService trazabilidadDocumentoService;
	private UsuarioService usuarioService;
	private TrazabilidadcopiaService trazabilidadcopiaService;
	private FechaLimite fechaLimite;
        private DocumentoReunionDAO documentoReunionDAO;
        private ReferenciaArchivoDAO referenciaArchivoDAO;
	private ManejoDeEmailService mailService;
	private AlfrescoWSService alfrescoWebServiceClient;
	private ClienteService clienteService;
	private DocumentoxclienteService documentoxclienteService;
	private NotificacionxEnumerarDAO notificacionxEnumerarDAO;
	private AlfrescoWebscriptService alfrescoWebscriptClient;
	private TrazabilidadapoyoService trazabilidadapoyoService;
	private EstadoService estadoService;
	private UnidadService unidadService;
	private ParametroService parametroService;
	private Map<String, Object> mapSession;
	private ProcesoService procesoService;
	private DocumentoAdjuntoDAO documentoAdjuntoDAO;
        private DocumentoAnuladoDAO documentoAnuladoDAO;
        private UsuarioxunidadxfuncionDAO usuarioxunidadxfuncionDAO;
        private DocumentoExternoVirtualDAO documentoExternoVirtualDAO;
        private RecepcionVirtualDAO recepcionVirtualDAO; 
        private DocPrincipalVirtualDAO docPrincipalVirtualDAO;
        private DocAnexoVirtualDAO docAnexoVirtualDAO;
        private DespachoVirtualDAO despachoVirtualDAO;
        private LegajoDocumentoDAO legajoDocumentoDAO;
        private FirmaArchivoDAO firmaArchivoDAO;

        public FirmaArchivoDAO getFirmaArchivoDAO() {
            return firmaArchivoDAO;
        }

        public void setFirmaArchivoDAO(FirmaArchivoDAO firmaArchivoDAO) {
            this.firmaArchivoDAO = firmaArchivoDAO;
        }

        public LegajoDocumentoDAO getLegajoDocumentoDAO() {
            return legajoDocumentoDAO;
        }

        public void setLegajoDocumentoDAO(LegajoDocumentoDAO legajoDocumentoDAO) {
            this.legajoDocumentoDAO = legajoDocumentoDAO;
        }
        
        public ReferenciaArchivoDAO getReferenciaArchivoDAO() {
            return referenciaArchivoDAO;
        }

        public void setReferenciaArchivoDAO(ReferenciaArchivoDAO referenciaArchivoDAO) {
            this.referenciaArchivoDAO = referenciaArchivoDAO;
        }

        public DocPrincipalVirtualDAO getDocPrincipalVirtualDAO() {
            return docPrincipalVirtualDAO;
        }

        public void setDocPrincipalVirtualDAO(DocPrincipalVirtualDAO docPrincipalVirtualDAO) {
            this.docPrincipalVirtualDAO = docPrincipalVirtualDAO;
        }

        public DocAnexoVirtualDAO getDocAnexoVirtualDAO() {
            return docAnexoVirtualDAO;
        }

        public void setDocAnexoVirtualDAO(DocAnexoVirtualDAO docAnexoVirtualDAO) {
            this.docAnexoVirtualDAO = docAnexoVirtualDAO;
        }

        public DespachoVirtualDAO getDespachoVirtualDAO() {
            return despachoVirtualDAO;
        }

        public void setDespachoVirtualDAO(DespachoVirtualDAO despachoVirtualDAO) {
            this.despachoVirtualDAO = despachoVirtualDAO;
        }
      

        public RecepcionVirtualDAO getRecepcionVirtualDAO() {
            return recepcionVirtualDAO;
        }

        public void setRecepcionVirtualDAO(RecepcionVirtualDAO recepcionVirtualDAO) {
            this.recepcionVirtualDAO = recepcionVirtualDAO;
        }

        public DocumentoExternoVirtualDAO getDocumentoExternoVirtualDAO() {
            return documentoExternoVirtualDAO;
        }

        public void setDocumentoExternoVirtualDAO(DocumentoExternoVirtualDAO documentoExternoVirtualDAO) {
            this.documentoExternoVirtualDAO = documentoExternoVirtualDAO;
        }

        public UsuarioxunidadxfuncionDAO getUsuarioxunidadxfuncionDAO() {
            return usuarioxunidadxfuncionDAO;
        }

        public void setUsuarioxunidadxfuncionDAO(UsuarioxunidadxfuncionDAO usuarioxunidadxfuncionDAO) {
            this.usuarioxunidadxfuncionDAO = usuarioxunidadxfuncionDAO;
        }
        
        public DocumentoDerivacionDAO getDocumentoDerivacionDAO() {
            return documentoDerivacionDAO;
        }

        public void setDocumentoDerivacionDAO(DocumentoDerivacionDAO documentoDerivacionDAO) {
            this.documentoDerivacionDAO = documentoDerivacionDAO;
        }

        public SeguimientoXFirmaDAO getSeguimientoXFirmaDAO() {
            return seguimientoXFirmaDAO;
        }

        public void setSeguimientoXFirmaDAO(SeguimientoXFirmaDAO seguimientoXFirmaDAO) {
            this.seguimientoXFirmaDAO = seguimientoXFirmaDAO;
        }

        public DocumentoAnuladoDAO getDocumentoAnuladoDAO() {
            return documentoAnuladoDAO;
        }

        public void setDocumentoAnuladoDAO(DocumentoAnuladoDAO documentoAnuladoDAO) {
            this.documentoAnuladoDAO = documentoAnuladoDAO;
        }
        
        public DocumentoAdjuntoDAO getDocumentoAdjuntoDAO() {
            return documentoAdjuntoDAO;
        }

        public void setDocumentoAdjuntoDAO(DocumentoAdjuntoDAO documentoAdjuntoDAO) {
            this.documentoAdjuntoDAO = documentoAdjuntoDAO;
        }

     
	 public String getAtendidoTramiteDocumentario(Documento d){
		return documentoDao.getAtendidoTramiteDocumentario(d);
	 }
         
          public List<NodoArbol> getArbolDocumentosNavegador(Usuario usuario, String tipoVista) {
              List<ItemUF> lst = documentoDao.findByDataUF(usuario, tipoVista, true);
              
              List<NodoArbol> lstNodo = null;
              List<Object> lstArbol = new ArrayList<Object>();
              List<NodoArbol> lstNodoFinal = new ArrayList<NodoArbol>();
              
              for(int i=0;i<lst.size();i++){
                    String icono = lst.get(i).getIconoDocumento();
                    
                    if (icono!=null && !icono.equals("")){
                         List<Archivo> lstArchivo = null;

                         if (lst.get(i).getDocumentoreferencia()==null){
                             lstArchivo =archivoService.findLstByIdDocumento(lst.get(i).getId());
                         }else{
                             lstArchivo =archivoService.findLstByIdDocumento(new Integer(lst.get(i).getDocumentoreferencia()));
                         }

                         List<NodoArbol> lstTreeDocumento = new ArrayList<NodoArbol>();
                         int contadorArchivo = 0;

                         for(int k=0;k<lstArchivo.size();k++){
                              NodoArbol objETDocumento = new NodoArbol(false, "D|" + lstArchivo.get(k).getPrincipal() + "|" + UUID.randomUUID().toString().replace("|", "U2") + "|" +lstArchivo.get(k).getIdArchivo() + "|" + lstArchivo.get(k).getRutaAlfresco() + "|" + lstArchivo.get(k).getObjectId(), lstArchivo.get(k).getNombreArchivo() , null);
                              lstTreeDocumento.add(objETDocumento);
                              contadorArchivo ++;
                         }

                         NodoArbol objFlujoTree = new NodoArbol(true, "U|" +lst.get(i).getId(), lst.get(i).getDocumento() + " ["  +  contadorArchivo + "]" , lstTreeDocumento);
                         List<NodoArbol> allElements = new ArrayList<NodoArbol>();
                         allElements.add(objFlujoTree);             
                         allElements.addAll(lstTreeDocumento);
                         lstArbol.add(allElements);
                    }       
              }
              
              for(int j=0;j<lstArbol.size();j++){
                  lstNodo = (List<NodoArbol>)lstArbol.get(j);
                  for(int k=0;k<lstNodo.size();k++){
                      NodoArbol nodito = lstNodo.get(k);
                      lstNodoFinal.add(nodito);
                  }
             } 
                    
              return lstNodoFinal;
          }
          
        /*  public List<String> buscarPendienteVirtual(String nroTramite){
              return documentoDao.buscarPendienteVirtual(nroTramite);
          }*/
         
          public List<NodoArbol> getArbolTipoDocumentosNavegador(Usuario usuario, String tipoVista) {
              List<NodoArbol> lstNodoFinal = new ArrayList<NodoArbol>();
              List<NodoArbol> lstNodo = null;
              List<Object> lstArbol = new ArrayList<Object>();
              
              List<ItemUF> lst = documentoDao.findByDataUF(usuario, tipoVista, true);
              String origen = "";
              List<String> lstTipos = new ArrayList<String>();
              
              for(int i =0;i<lst.size();i++){
                 String icono = lst.get(i).getIconoDocumento();
                 String tipo =  lst.get(i).getTipodocumento();
                 if (!icono.equals("")){
                     if (!origen.equals(tipo)){
                         lstTipos.add(tipo);
                         origen = tipo;
                     }
                 }
              }
              
              for(int j=0;j<lstTipos.size();j++){
                  boolean bandera = false;
                  List<NodoArbol> allElements = new ArrayList<NodoArbol>();
                  List<NodoArbol> allElementsTemp = new ArrayList<NodoArbol>();
                  List<NodoArbol> allElementsTemp2 = new ArrayList<NodoArbol>();
                  int contador = 0;
                  for(int i=0;i<lst.size();i++){
                       if (lstTipos.get(j).equals(lst.get(i).getTipodocumento().toString())){
                                String icono = lst.get(i).getIconoDocumento();
                                if (icono!=null && !icono.equals("")){
                                     bandera = true;
                                     contador ++;
                                     List<Archivo> lstArchivo = null;
                                     
                                     if (lst.get(i).getDocumentoreferencia()==null){
                                         lstArchivo =archivoService.findLstByIdDocumento(lst.get(i).getId());
                                     }else{
                                         lstArchivo =archivoService.findLstByIdDocumento(new Integer(lst.get(i).getDocumentoreferencia()));
                                     }
                                     
                                     List<NodoArbol> lstTreeDocumento = new ArrayList<NodoArbol>();
                                     int contadorArchivo = 0;
                                     
                                     for(int k=0;k<lstArchivo.size();k++){
                                          NodoArbol objETDocumento = new NodoArbol(false, "D|" + lstArchivo.get(k).getPrincipal() + "|" + UUID.randomUUID().toString().replace("|", "U2") + "|" +lstArchivo.get(k).getIdArchivo() + "|" + lstArchivo.get(k).getRutaAlfresco() + "|" + lstArchivo.get(k).getObjectId(), lstArchivo.get(k).getNombreArchivo() , null);
                                          lstTreeDocumento.add(objETDocumento);
                                          contadorArchivo ++;
                                     }
                                     
                                     NodoArbol objFlujoTree = new NodoArbol(false, "U|" +lst.get(i).getId(), lst.get(i).getDocumento() + " ["  +  contadorArchivo + "]" , lstTreeDocumento);
                                     allElementsTemp.add(objFlujoTree);
                                     allElementsTemp2.add(objFlujoTree);
                                     allElementsTemp.addAll(lstTreeDocumento);
                                }
                      }
                  }
                  
                  if (bandera){
                    Tipodocumento t = tipoDocumentoDao.findByIdTipoDocumento(new Integer(lstTipos.get(j)));
                    NodoArbol objTipoTree = new NodoArbol(true, "E|" +  lstTipos.get(j) , t.getNombre() + " [" + contador + "]",allElementsTemp2); // new
                    allElements.add(objTipoTree);
                    allElements.addAll(allElementsTemp);
                    lstArbol.add(allElements);
                  }  
             }
              
             for(int j=0;j<lstArbol.size();j++){
                  lstNodo = (List<NodoArbol>)lstArbol.get(j);
                  for(int k=0;k<lstNodo.size();k++){
                      NodoArbol nodito = lstNodo.get(k);
                      lstNodoFinal.add(nodito);
                  }
             } 
                    
                  
              return lstNodoFinal;
          }
          
          
           public List<NodoArbol> getArbolUnidadNavegador(Usuario usuario, String tipoVista) {
              List<NodoArbol> lstNodoFinal = new ArrayList<NodoArbol>();
              List<NodoArbol> lstNodo = null;
              List<Object> lstArbol = new ArrayList<Object>();
              
              List<ItemUF> lst = documentoDao.findByDataUF(usuario, tipoVista, true);
              String origen = "";
              List<String> lstTipos = new ArrayList<String>();
              
              for(int i =0;i<lst.size();i++){
                 String icono = lst.get(i).getIconoDocumento();
                 String tipo =  lst.get(i).getIdAreaAutor();
                 if (!icono.equals("")){
                     if (!origen.equals(tipo)){
                         lstTipos.add(tipo);
                         origen = tipo;
                     }
                 }
              }
              
              for(int j=0;j<lstTipos.size();j++){
                  boolean bandera = false;
                  List<NodoArbol> allElements = new ArrayList<NodoArbol>();
                  List<NodoArbol> allElementsTemp = new ArrayList<NodoArbol>();
                  List<NodoArbol> allElementsTemp2 = new ArrayList<NodoArbol>();
                  int contador = 0;
                  for(int i=0;i<lst.size();i++){
                       if (lstTipos.get(j).equals(lst.get(i).getIdAreaAutor().toString())){
                                String icono = lst.get(i).getIconoDocumento();
                                if (icono!=null && !icono.equals("")){
                                     bandera = true;
                                     contador ++;
                                     List<Archivo> lstArchivo = null;
                                     
                                     if (lst.get(i).getDocumentoreferencia()==null){
                                         lstArchivo =archivoService.findLstByIdDocumento(lst.get(i).getId());
                                     }else{
                                         lstArchivo =archivoService.findLstByIdDocumento(new Integer(lst.get(i).getDocumentoreferencia()));
                                     }
                                     
                                     List<NodoArbol> lstTreeDocumento = new ArrayList<NodoArbol>();
                                     int contadorArchivo = 0;
                                     
                                     for(int k=0;k<lstArchivo.size();k++){
                                          NodoArbol objETDocumento = new NodoArbol(false, "D|" + lstArchivo.get(k).getPrincipal() + "|" + UUID.randomUUID().toString().replace("|", "U2") + "|" +lstArchivo.get(k).getIdArchivo() + "|" + lstArchivo.get(k).getRutaAlfresco() + "|" + lstArchivo.get(k).getObjectId(), lstArchivo.get(k).getNombreArchivo() , null);
                                          lstTreeDocumento.add(objETDocumento);
                                          contadorArchivo ++;
                                     }
                                     
                                     NodoArbol objFlujoTree = new NodoArbol(false, "U|" +lst.get(i).getId(), lst.get(i).getDocumento() + " ["  +  contadorArchivo + "]" , lstTreeDocumento);
                                     allElementsTemp.add(objFlujoTree);
                                     allElementsTemp2.add(objFlujoTree);
                                     allElementsTemp.addAll(lstTreeDocumento);
                                }
                      }
                  }
                  
                  if (bandera){
                     
                    Unidad t = unidadService.buscarObjPor(new Integer(lstTipos.get(j)));
                    NodoArbol objTipoTree = new NodoArbol(true, "E|" +  lstTipos.get(j) , t.getNombre().toUpperCase() + " [" + contador + "]",allElementsTemp2); // new
                    allElements.add(objTipoTree);
                    allElements.addAll(allElementsTemp);
                    lstArbol.add(allElements);
                  }  
             }
              
             for(int j=0;j<lstArbol.size();j++){
                  lstNodo = (List<NodoArbol>)lstArbol.get(j);
                  for(int k=0;k<lstNodo.size();k++){
                      NodoArbol nodito = lstNodo.get(k);
                      lstNodoFinal.add(nodito);
                  }
             } 
                    
                  
              return lstNodoFinal;
          }
         
            @Transactional
            public Documento moverDocumentoSIDECO(Integer iIdDocumento, String nombrePC){
                log.debug("-> [Service] DocumentoService - moverDocumentoSIDECO():Documento ");
                Documento documento = this.findByIdDocumento(iIdDocumento);

                mapSession = ActionContext.getContext().getSession();
                Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO); 
                documento.setFechaModificacion(new Date());
                documento.setUsuarioModificacion(usuario.getIdusuario());
                documento.setNombrePCLecturaDocumento(nombrePC);

                documento.setFlagsideco("1");
                documento = this.saveDocumento(documento);

                return documento;
            }
           
            
          public List<Documento> findByIdDocVirtual(Integer codigoVirtual){
              return documentoDao.findByIdDocVirtual(codigoVirtual);
          }  
          
          public List<Documento> getReferenciaDocumento(Integer idDocumento){
              List<DocumentoReferencia> lista = documentoReferenciaDAO.getReferenciaDocumento(idDocumento);
              List<DocumentoReferencia> listaTemp = null;
              List<Documento> listaDocumento = new ArrayList<Documento>();
              Documento d = null;
              Documento temp = null;
              
              if (lista!=null){
                  for(int i=0;i<lista.size();i++){
                     if (lista.get(i).getIdDocumentoReferencia()!=null){
                        d = documentoDao.findByIdDocumento(lista.get(i).getIdDocumentoReferencia());
                        temp = new Documento();
                        temp.setIdDocumento(d.getIdDocumento());
                        temp.setNumeroDocumento(d.getTipoDocumento().getNombre()+ " - " + d.getNumeroDocumento());
                        temp.setVER_DOCUMENTO(lista.get(i).getVerDocumento());
                        temp.setNroReferencias("");
                                
                        if (d.getDocumentoreferencia()==null)
                            listaTemp = documentoReferenciaDAO.getReferenciaDocumento(d.getIdDocumento());
                        else
                            listaTemp = documentoReferenciaDAO.getReferenciaDocumento(d.getDocumentoreferencia());
                        
                        if (listaTemp!=null &&  listaTemp.size()>0)
                             temp.setNroReferencias(String.valueOf(listaTemp.size()));
                            
                        listaDocumento.add(temp);    
                     }  
                  }
              }
              
              return listaDocumento;
          }
          
         @Transactional
        public Documento updateNoLeido(Integer iIdDocumento, String nombrePC){
            log.debug("-> [Service] DocumentoService - updateNoLeido():Documento ");
            Documento documento = this.findByIdDocumento(iIdDocumento);
            
            if (documento.getLeido().equals('S')){
                mapSession = ActionContext.getContext().getSession();
                               Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO); 
                               documento.setFechaModificacion(new Date());
                documento.setUsuarioModificacion(usuario.getIdusuario());
                               documento.setNombrePCLecturaDocumento(nombrePC);
            }
            
            documento.setLeido('N');
            documento = this.saveDocumento(documento);
            
            return documento;
        }


          
           public List<DocumentoReferencia> getDocumentoRespuesta(Documento documento){
             List<DocumentoReferencia> lista = null;
             
             if (documento.getDocumentoreferencia()==null){
                 List<Documento> listDocumentoMultiple =  documentoDao.consultaDocumentoReferencia(documento.getIdDocumento());
                 
                 if (listDocumentoMultiple==null ||  listDocumentoMultiple.size()==0){
                    lista = documentoReferenciaDAO.getDocumentoRespuestaSimple(documento);    
                 }else{
                    lista = documentoReferenciaDAO.getDocumentoRespuestaMultiple(documento);  
                 }
            }else{
                 Documento d = new Documento();
                 d.setIdDocumento(documento.getDocumentoreferencia());
                 lista = documentoReferenciaDAO.getDocumentoRespuestaMultiple(d);  
            } 
              
              
             return lista;
          }
                  
	public Boolean aplicarJerarquia(Usuario objJefe, Proceso objProceso, Integer iIdDocumento) {
		log.debug("-> [Service] DocumentoService - aplicarJerarquia():Boolean ");

		List<Usuario> lstSubordinado = null;
		String sTipoAcceso = objProceso.getIdtipoacceso().getCodigo();
		if (sTipoAcceso.equals(Constantes.PROCESO_ACCESO_1)) {
			return true;
		}
		lstSubordinado = usuarioService.findUsuariosByIdJefe(objJefe.getIdusuario());
		if (lstSubordinado != null) {
			for (Usuario objSubordinado : lstSubordinado) {
				if (sTipoAcceso.equals(Constantes.PROCESO_ACCESO_2)) {
					for (Usuario objParticipante : objProceso.getUsuarioList()) {
						if (objParticipante.getIdusuario().intValue() == objSubordinado.getIdusuario().intValue()) {
							return true;
						}
					}
				} else if (sTipoAcceso.equals(Constantes.PROCESO_ACCESO_3)) {
					Documento objDocumento = this.findByIdDocumento(iIdDocumento);
					if (objSubordinado.getIdusuario().intValue() == objDocumento.getPropietario().getIdusuario().intValue()) {
						return true;
					}
				} else if (sTipoAcceso.equals(Constantes.PROCESO_ACCESO_4)) {
					for (Usuario objParticipante : objProceso.getUsuarioList1()) {
						if (objParticipante.getIdusuario().intValue() == objSubordinado.getIdusuario().intValue()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public List<Documento> backup(){
		return documentoDao.backup();
	}

	public Integer getNroConsultaTramiteDocumentario(){
		return documentoDao.getNroConsultaTramiteDocumentario();
	}

	public Integer getNroSiguienteConsultaTramiteDocumentario(){
		return documentoDao.getNroSiguienteConsultaTramiteDocumentario();
	}

	public Documento buscarDocumentoMasAntiguoPor(Integer iIdExpediente) {
		log.debug("-> [Service] DocumentoService - buscarDocumentoMasAntiguoPor():Documento ");

		return documentoDao.buscarDocumentoMasAntiguoPor(iIdExpediente);
	}

	@Override
	@Transactional
	public void anexarDocumento(Integer iIdDocumentoPrincipal, Integer[] arrIdDocumento) {
		log.debug("-> [Service] DocumentoService - anexarDocumento():void ");

		Documento objDocumentoPrincipal = this.findByIdDocumento(iIdDocumentoPrincipal);
		log.debug("Se asignaran [" + arrIdDocumento.length + "] documentos al expediente [" + objDocumentoPrincipal.getExpediente().getNroexpediente() + "]");
		for (Integer iIdDocToAnexar : arrIdDocumento) {
			log.debug("Documento a anexar con ID [" + iIdDocToAnexar + "]");
			Documentoxexpediente objDocXExp = new Documentoxexpediente(iIdDocToAnexar, objDocumentoPrincipal.getExpediente().getIdexpediente());
			objDocXExp.setEstado(Constantes.ESTADO_ACTIVO);
			documentoPorExpedienteService.guardarObj(objDocXExp);
		}
	}

	public Documento clonarDocumento(Documento objDocumento, Usuario objPropietario, Expediente objExpediente) {
		log.debug("-> [Service] DocumentoService - clonarDocumento():Documento ");

		Documento objNewDocumento = new Documento();
		objNewDocumento.setIdDocumento(null);
		objNewDocumento.setPrincipal(objDocumento.getPrincipal());
		objNewDocumento.setDelExpediente(objDocumento.getDelExpediente());
		objNewDocumento.setNumeroDocumento(objDocumento.getNumeroDocumento());
		objNewDocumento.setNumeroFolios(objDocumento.getNumeroFolios());
		objNewDocumento.setNumeroCaja(objDocumento.getNumeroCaja());
		objNewDocumento.setNumeroMesaPartes(objDocumento.getNumeroMesaPartes());
		//objNewDocumento.setReferencia(objDocumento.getReferencia());
		objNewDocumento.setAsunto(objDocumento.getAsunto());
		objNewDocumento.setUltimoAsunto(objDocumento.getAsunto());
		objNewDocumento.setContenido(objDocumento.getContenido());
		objNewDocumento.setObservacion(objDocumento.getObservacion());
		objNewDocumento.setFechaDocumento(objDocumento.getFechaDocumento());
		objNewDocumento.setFechaAccion(objDocumento.getFechaAccion());
		objNewDocumento.setPlazo(objDocumento.getPlazo());
		objNewDocumento.setFechaLimiteAtencion(objDocumento.getFechaLimiteAtencion());
		objNewDocumento.setFechaCreacion(new Date());
		objNewDocumento.setEstado(objDocumento.getEstado());
		objNewDocumento.setAccion(objDocumento.getAccion());
		objNewDocumento.setExpediente(objExpediente);
		objNewDocumento.setTipoDocumento(objDocumento.getTipoDocumento());
		objNewDocumento.setPropietario(objPropietario);
		//objNewDocumento.setArchivos(objDocumento.getArchivos());
		return objNewDocumento; 
	}
        
       
	public Documento findByIdDocumento(Integer iIdDocumento) {
		log.debug("-> [Service] DocumentoService - findByIdDocumento():iIdDocumento: "+iIdDocumento);

		return documentoDao.findByIdDocumento(iIdDocumento);
	}

	public Documento findPropietarioByIdDocumento(Integer iIdDocumento) {
		log.debug("-> [Service] DocumentoService - findPropietarioByIdDocumento():Documento ");

		return documentoDao.findPropietarioByIdDocumento(iIdDocumento);
	}

	/**REN devuelve solo el expediente y el usuario propietario ----------------------------------------------------------*/
	public Documento findDocExpedienteByIdDocumento(Integer iIdDocumento) {
		log.debug("-> [Service] DocumentoService - findDocExpedienteByIdDocumento():Documento ");

		return documentoDao.findDocExpedienteByIdDocumento(iIdDocumento);
	}

	public Documento findDocumentoPrincipal(Integer iIdExpediente) {
		log.debug("-> [Service] DocumentoService - findDocumentoPrincipal():Documento ");

		return documentoDao.findDocumentoPrincipal(iIdExpediente);
	}

	public DocumentoDetail getExpedienteData(Integer iIdExp) throws RuntimeException {
		log.debug("-> [Service] DocumentoService - getExpedienteData():DocumentoDetail ");

		try {
			Expediente objE = expedienteService.findByIdExpediente(iIdExp);
			DocumentoDetail objDD = null;// new DocumentoDetail(objE.getIdexpediente(), objE.getProceso().getIdproceso(), objE.getProceso().getResponsable().getIdusuario(), objE.getProceso().getNombre(), objE.getNroexpediente(), objE.getProceso().getResponsable().getNombres() + " " + objE.getProceso().getResponsable().getApellidos(), objE.getProceso().getResponsable().getUnidad().getNombre());
			if (objE.getConcesionario() != null) {
				objDD.setIIdCorrentista(objE.getConcesionario().getIdConcesionario());
				objDD.setStrRUC(objE.getConcesionario().getRuc());
				objDD.setStrCorrentista(objE.getConcesionario().getRazonSocial());
				objDD.setStrDireccionConcesionario(objE.getConcesionario().getDireccion());
			}
			if (objE.getCliente() != null) {
				objDD.setIIdCliente(objE.getCliente().getIdCliente());
				objDD.setIIdTipoIdentificacion(objE.getCliente().getTipoIdentificacion().getIdtipoidentificacion());
				objDD.setStrDireccionAlternativa(objE.getCliente().getDireccionAlternativa());
				objDD.setStrDireccionPrincipal(objE.getCliente().getDireccionPrincipal());
				objDD.setStrNroIdentificacion(objE.getCliente().getNumeroIdentificacion());
				objDD.setStrRazonSocial(objE.getCliente().getRazonSocial());
				objDD.setStrTipoIdentificacion(objE.getCliente().getTipoIdentificacion().getNombre());
				objDD.setStrRepresentanteLegal(objE.getCliente().getRepresentanteLegal());
				/*objDD.setIIdDepartamento(objE.getCliente().getUbigeoPrincipal().getProvincia().getDepartamento().getIddepartamento());
				objDD.setStrDepartamento(objE.getCliente().getUbigeoPrincipal().getProvincia().getDepartamento().getNombre());
				objDD.setIIdProvincia(objE.getCliente().getUbigeoPrincipal().getProvincia().getIdprovincia());
				objDD.setStrProvincia(objE.getCliente().getUbigeoPrincipal().getProvincia().getNombre());
				objDD.setIIdDistrito(objE.getCliente().getUbigeoPrincipal().getIddistrito());
				objDD.setStrDistrito(objE.getCliente().getUbigeoPrincipal().getNombre());*/
				objDD.setStrTelefonoCliente(objE.getCliente().getTelefono());
				objDD.setStrCorreoCliente(objE.getCliente().getCorreo());
			}
			//objDD.setStrAbreviado(objE.getProceso().getTipoproceso().getNombre());
			/*
			 * if
			 * (objE.getIdproceso().getAbreviado().equals(Proceso.ABREV_STOR)) {
			 * if (objE.getExpedientestor() != null) { List<Submotivo> lstSM =
			 * objE.getExpedientestor().getSubmotivoList();
			 *
			 * for (Submotivo objSM : lstSM) {
			 * objDD.setIIdMotivo(objSM.getIdmotivo().getIdmotivo());
			 * objDD.setStrMotivo(objSM.getIdmotivo().getDescripcion());
			 * objDD.setIIdSubMotivo(objSM.getIdsubmotivo());
			 * objDD.setStrSubMotivo(objSM.getDescripcion()); } }
			 *
			 * }
			 */
			return objDD;
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}

	public Date getFechaLimiteAtencion(Integer idDocumento) {
		log.debug("-> [Service] DocumentoService - getFechaLimiteAtencion():Date ");

		Documento doc = documentoDao.findByIdDocumento(idDocumento);
		return doc == null ? null : doc.getFechaLimiteAtencion();
	}


	//jbengoa
	/*public List<Documento> buscarLstDocumentoPorExpediente(Integer iIdExpediente){
		return documentoDao.buscarLstDocumentoPorExpediente(iIdExpediente);
	}*/


	//public List<TrazabilidadEnlace> getTrazabilidadEnlace(Integer iIdDocumento) {
	//	return documentoReferenciaDAO.getListEnlace(iIdDocumento);
	//}
        
        @Override
	public DocumentoDetail getDocumentDetailOptimizedAR(Integer iIdDoc, String strRol) {
		log.debug("-> [Service] DocumentoService - getDocumentDetailOptimizedAR():DocumentoDetail ");
		DocumentoDetail objDocumentoDetail = documentoDao.findDocumentoDetailByAR(iIdDoc);
                
                return objDocumentoDetail;
        }        

	@Transactional
	@Override
	public DocumentoDetail getDocumentDetailOptimized(Integer iIdDoc, String strRol) {
		log.debug("-> [Service] DocumentoService - getDocumentDetailOptimized():DocumentoDetail ");
		DocumentoDetail objDocumentoDetail = documentoDao.findDocumentoDetailBy(iIdDoc);

                if (strRol != null && strRol.toLowerCase().equals(Constantes.ROL_MESA_PARTES)) {
			objDocumentoDetail.setStrRemitente(objDocumentoDetail.getClienterazonsocial());
		}

		if (archivoService.checkEstadoDigitalizacion(iIdDoc) > 0) {
			objDocumentoDetail.setCDisponible(Constantes.DOCUMENTO_DISPONIBLE);
		} else {
			objDocumentoDetail.setCDisponible(Constantes.DOCUMENTO_NO_DISPONIBLE);
		}

                objDocumentoDetail.setStrRol(strRol);
                
                return objDocumentoDetail;
	}

	@Override
	public DocumentoDetail getDocumentDetail(Integer iIdDoc, String strRol) throws RuntimeException {
		log.debug("-> [Service] DocumentoService - getDocumentDetail():DocumentoDetail ");

		String vacio="";
		Documento objD = documentoDao.findByIdDocumento(iIdDoc);
		Distrito objDistrito = null;
		String sNombre = null;
		String sApellidoPaterno = null;
		String sApellidoMaterno = null;
		Trazabilidaddocumento objT = null;
		if(objD.getDocumentoreferencia() != null){
			/**Es un documento copia para trabajo ----------------------------------------------------------------------------*/
			objT = trazabilidadDocumentoService.findByMaxNroRegistro(objD.getDocumentoreferencia(), Constantes.ACCION_CONSULTAR, null, null);
		}else{
			objT = trazabilidadDocumentoService.findByMaxNroRegistro(objD.getIdDocumento(), Constantes.ACCION_CONSULTAR, null, null);
		}

                DocumentoDetail objDD = new DocumentoDetail(objD.getFechaAccion(), objD.getIdDocumento(), objD.getExpediente().getIdexpediente(), objD.getTipoDocumento().getIdtipodocumento(), objD.getNumeroFolios(), objT.getAsunto(), objD.getNumeroCaja(), objD.getNumeroDocumento(), objD.getObservacion(), "", objD.getExpediente().getNroexpediente(), "", strRol, objD.getTipoDocumento().getNombre(),"", objT.getContenido());
		objDD.setObservacionDocumento(objD.getObservacion());
                objDD.setObservacionDigitalizador(objD.getObservacionDigitalizador());
		
		objDD.setFechacreacion(objD.getFechaCreacion());
		
        int tiempoProceso = 100;//procesoService.findByIdProceso(objD.getExpediente().getProceso().getIdproceso()).getTiempoatencion().intValue();
		Date dNuevaFechaLimiteAtencion = fechaLimite.calcularFechaLimite(tiempoProceso, 0);
		objDD.setDateFechaLimiteAtencion(dNuevaFechaLimiteAtencion);
		objDD.setPropietario(objD.getPropietario());
		objDD.setAccion(objD.getAccion());
		objDD.setStrAccion(objD.getAccion().getNombre());
		objDD.setStrNroMesaPartes("" + (objD.getNumeroMesaPartes() != null ? objD.getNumeroMesaPartes() : objD.getIdDocumento()));
		if (objD.getExpediente().getCliente() != null) {
			objDD.setIIdCliente(objD.getExpediente().getCliente().getIdCliente());
			objDD.setIIdTipoIdentificacion(objD.getExpediente().getCliente().getTipoIdentificacion().getIdtipoidentificacion());
			objDD.setStrTipoIdentificacion(objD.getExpediente().getCliente().getTipoIdentificacion().getNombre());
			objDD.setStrNroIdentificacion(objD.getExpediente().getCliente().getNumeroIdentificacion());
			log.debug("Cliente con Nro de Identificacion [" + objDD.getStrNroIdentificacion() + "]");
			if (objDD.getStrTipoIdentificacion().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
				objDD.setStrRazonSocial(objD.getExpediente().getCliente() != null ? objD.getExpediente().getCliente().getNombreRazon() : "");
			} else if (objDD.getStrTipoIdentificacion().equals(Constantes.TIPO_IDENTIFICACION_DNI) || objDD.getStrTipoIdentificacion().equals(Constantes.TIPO_IDENTIFICACION_OTRO)) {
				sNombre = objD.getExpediente().getClientenombres() != null ? objD.getExpediente().getClientenombres() : objD.getExpediente().getCliente().getNombres()!=null ? objD.getExpediente().getCliente().getNombres() : vacio;
				sApellidoPaterno = objD.getExpediente().getClienteapellidopaterno() != null ? objD.getExpediente().getClienteapellidopaterno() : objD.getExpediente().getCliente().getApellidoPaterno()!=null ? objD.getExpediente().getCliente().getApellidoPaterno(): vacio;
				sApellidoMaterno = objD.getExpediente().getClienteapellidomaterno() != null ? objD.getExpediente().getClienteapellidomaterno() : objD.getExpediente().getCliente().getApellidoMaterno()!=null ? objD.getExpediente().getCliente().getApellidoMaterno(): vacio ;
				objDD.setStrRazonSocial(objD.getExpediente().getCliente() != null ? objD.getExpediente().getCliente().getNombreRazon() : "");
				//objDD.setStrRazonSocial(objDD.getStrRazonSocial().trim());
				objDD.setClientenombres(sNombre);
				objDD.setClienteapellidopaterno(sApellidoPaterno);
				objDD.setClienteapellidomaterno(sApellidoMaterno);
			}
			objDD.setStrRepresentanteLegal(objD.getExpediente().getClienterepresentantelegal() != null ? objD.getExpediente().getClienterepresentantelegal() : objD.getExpediente().getCliente().getRepresentanteLegal());
			objDD.setStrDireccionPrincipal(objD.getExpediente().getClientedireccionprincipal() != null ? objD.getExpediente().getClientedireccionprincipal() : objD.getExpediente().getCliente().getDireccionPrincipal());
			if (objD.getExpediente().getClienteubigeoprincipal() != null) {
				objDistrito = distritoService.findById(objD.getExpediente().getClienteubigeoprincipal());
				objDD.setStrDepartamento(objDistrito.getProvincia().getDepartamento().getNombre());
				objDD.setStrProvincia(objDistrito.getProvincia().getNombre());
				objDD.setStrDistrito(objDistrito.getNombre());
				objDD.setIIdDepartamento(objDistrito.getProvincia().getDepartamento().getIddepartamento());
				objDD.setIIdProvincia(objDistrito.getProvincia().getIdprovincia());
				objDD.setIIdDistrito(objDistrito.getIddistrito());
			} /*else if (objD.getExpediente().getCliente().getUbigeoPrincipal() != null) {
				objDD.setStrDepartamento(objD.getExpediente().getCliente().getUbigeoPrincipal().getProvincia().getDepartamento().getNombre());
				objDD.setStrProvincia(objD.getExpediente().getCliente().getUbigeoPrincipal().getProvincia().getNombre());
				objDD.setStrDistrito(objD.getExpediente().getCliente().getUbigeoPrincipal().getNombre());
				if (objD.getExpediente().getCliente().getUbigeoPrincipal().getProvincia() != null) {
					objDD.setIIdProvincia(objD.getExpediente().getCliente().getUbigeoPrincipal().getProvincia().getIdprovincia());
					if (objD.getExpediente().getCliente().getUbigeoPrincipal().getProvincia().getDepartamento() != null) {
						objDD.setIIdDepartamento(objD.getExpediente().getCliente().getUbigeoPrincipal().getProvincia().getDepartamento().getIddepartamento());
					}

				}
				objDD.setIIdDistrito(objD.getExpediente().getCliente().getUbigeoPrincipal().getIddistrito());
			}*/
			objDD.setStrDireccionAlternativa(objD.getExpediente().getClientedireccionalternativa() != null ? objD.getExpediente().getClientedireccionalternativa() : objD.getExpediente().getCliente().getDireccionAlternativa());
			if (objD.getExpediente().getClienteubigeoalternativo() != null) {
				objDistrito = distritoService.findById(objD.getExpediente().getClienteubigeoalternativo());
				objDD.setsDepartamentoAlt(objDistrito.getProvincia().getDepartamento().getNombre());
				objDD.setsProvinciaAlt(objDistrito.getProvincia().getNombre());
				objDD.setsDistritoAlt(objDistrito.getNombre());
				objDD.setiIdDepartamentoAlt(objDistrito.getProvincia().getDepartamento().getIddepartamento());
				objDD.setiIdProvinciaAlt(objDistrito.getProvincia().getIdprovincia());
				objDD.setiIdDistritoAlt(objDistrito.getIddistrito());
			} else if (objD.getExpediente().getCliente().getUbigeoAlternativo() != null) {
				objDD.setsDepartamentoAlt(objD.getExpediente().getCliente().getUbigeoAlternativo().getProvincia().getDepartamento().getNombre());
				objDD.setsProvinciaAlt(objD.getExpediente().getCliente().getUbigeoAlternativo().getProvincia().getNombre());
				objDD.setsDistritoAlt(objD.getExpediente().getCliente().getUbigeoAlternativo().getNombre());
				objDD.setiIdDepartamentoAlt(objD.getExpediente().getCliente().getUbigeoAlternativo().getProvincia().getDepartamento().getIddepartamento());
				objDD.setiIdProvinciaAlt(objD.getExpediente().getCliente().getUbigeoAlternativo().getProvincia().getIdprovincia());
				objDD.setiIdDistritoAlt(objD.getExpediente().getCliente().getUbigeoAlternativo().getIddistrito());
			}
			objDD.setStrTelefonoCliente(objD.getExpediente().getClientetelefono() != null ? objD.getExpediente().getClientetelefono() : objD.getExpediente().getCliente().getTelefono());
			objDD.setStrCorreoCliente(objD.getExpediente().getClientecorreo() != null ? objD.getExpediente().getClientecorreo() : objD.getExpediente().getCliente().getCorreo());
		}
		if (objD.getExpediente().getConcesionario() != null) {
			objDD.setStrCorrentista(objD.getExpediente().getConcesionario().getRazonSocial());
			objDD.setIIdCorrentista(objD.getExpediente().getConcesionario().getIdConcesionario());
			objDD.setStrCorrentista(objD.getExpediente().getConcesionario().getRazonSocial());
			objDD.setStrRUC(objD.getExpediente().getConcesionario().getRuc());
			objDD.setStrDireccionConcesionario(objD.getExpediente().getConcesionario().getDireccion());
			objDD.setStrCorreoConcesionario(objD.getExpediente().getConcesionario().getCorreo());
		}
		objDD.setStrDestinatario(objD.getPropietario().getNombres() + " " + objD.getPropietario().getApellidos());
		//objDD.setIIdProceso(objD.getExpediente().getProceso().getIdproceso());
		//objDD.setStrAbreviado(objD.getExpediente().getProceso().getTipoproceso().getNombre());
		objDD.setCEstado(objD.getEstado());
		objDD.setCPrincipal(objD.getPrincipal());
		objDD.setSObservacionRechazo(objD.getObservacionRechazo());
		if (objD.getFechaDocumento() != null) {
			objDD.setStrFechaDocumento(new SimpleDateFormat("dd/MM/yyyy").format(objD.getFechaDocumento()));
		} else {
			objDD.setStrFechaDocumento(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		}
		if (strRol != null && strRol.toLowerCase().equals(Constantes.ROL_MESA_PARTES)) {
			objDD.setStrRemitente(objD.getExpediente().getCliente().getRazonSocial());
		} else {
			objDD.setStrRemitente(((objT != null && objT.getRemitente() != null) ? objT.getRemitente().getNombres() : "") + " " + (((objT != null && objT.getRemitente() != null) ? objT.getRemitente().getApellidos() : "")));
		}
		if (objT != null && objT.getDestinatario() != null) {
			objDD.setStrDestinatario(objT.getDestinatario().getNombres() + objT.getDestinatario().getApellidos());
		}
		// Seteando la disponibilidad de Aprobar/Rechazar el documento
		//if (archivoService.checkEstadoDigitalizacion(iIdDoc) > 0) {
		objDD.setCDisponible(Constantes.DOCUMENTO_DISPONIBLE);
		/*} else {
            objDD.setCDisponible(Constantes.DOCUMENTO_NO_DISPONIBLE);
        }*/
		// if (strUsuario.equals(Usuario.STOR_SECRETARIO_TECNICO) ||
		// strUsuario.equals(Usuario.STOR_REVISOR_LEGAL)) {
		if (objD.getExpediente().getExpedientestor() != null && objD.getExpediente().getExpedientestor().getSala() != null) {
			objDD.setIIdSala(objD.getExpediente().getExpedientestor().getSala().getIdsala());
			objDD.setStrSala(objD.getExpediente().getExpedientestor().getSala().getNombre());
			//Para validar cuando el ExpedienteStor no tenga guardada la etapa.
			objDD.setIIdEtapa(objD.getExpediente().getExpedientestor().getEtapa() == null ? 0 : objD.getExpediente().getExpedientestor().getEtapa().getIdetapa());
		}
		objDD.setsPropietarioExpediente(objD.getExpediente().getIdpropietario().getNombres() + " " + objD.getExpediente().getIdpropietario().getApellidos());
		objDD.setEstaenflujo(objD.getExpediente().getEstaenflujo());
		objDD.setHistorico(objD.getExpediente().getHistorico() == null ? 'N' : objD.getExpediente().getHistorico());

		return objDD;
	}
        
        public DocumentoDetail setearCliente(DocumentoDetail objDD){
             Tipodocumento objTD =  tipoDocumentoDao.findByIdTipoDocumento(objDD.getIIdTipoDocumento());
                
            if (objDD.getOpcion().equals(Constantes.COD_TRAMITE_INTERNO)){
                    if (objTD!=null && objTD.getExterno()!=null && objTD.getExterno().equals("1")){
                        if (objDD.getIdTipoCliente().equals(Constantes.COD_TIPOCLIENTE_EMPRESA)){
                           objDD.setIIdCliente(objDD.getiIdEmpresa());
                        }else{
                            if (objDD.getIdTipoCliente().equals(Constantes.COD_TIPOCLIENTE_INSTITUCION)){
                               objDD.setIIdCliente(objDD.getiIdInstitucion());         
                            } else{
                                objDD.setIIdCliente(objDD.getIdPersonaNatural()); 
                            }
                        }
                    }else{
                        objDD.setIIdCliente(Constantes.CLIENTE_OSINERGMIN_ID);
                    }
            }else{
                  if (objDD.getIdTipoCliente().equals(Constantes.COD_TIPOCLIENTE_EMPRESA)){
                         objDD.setIIdCliente(objDD.getiIdEmpresa());
                    }else{
                          if (objDD.getIdTipoCliente().equals(Constantes.COD_TIPOCLIENTE_INSTITUCION)){
                                 objDD.setIIdCliente(objDD.getiIdInstitucion());                 
                          } else{
                                   objDD.setIIdCliente(objDD.getIdPersonaNatural()); 
                          }
                    }
            }
                     
            return objDD;
        }

	
	@Transactional
	public Documento crearDocumentoPorArchivo(DocumentoDetail objDD, Usuario objUsuario, Map<String, List<ArchivoTemporal>> mapUpload, Boolean changePrincipal, boolean quitarCorchete, String modulo, String opcion, boolean masivo) {
		log.debug("-> [Service] DocumentoService - crearDocumentoPorArchivo():Documento ");

		Accion objAccion = null;
		Boolean bFirstOne = changePrincipal;
		Documento objDocumentoPrincipal = this.findByIdDocumento(objDD.getIIdDocumento());
		Documento objNuevoDocumentoPrincipal = null;
		List<ArchivoTemporal> lstArchivoTemporal = mapUpload.get("upload1");
		Tipodocumento objTipodocumento = tipoDocumentoDao.findByNombre("Otros");
		if (changePrincipal) {
			objDocumentoPrincipal.setPrincipal(Constantes.DOCUMENTO_NO_PRINCIPAL);
		}
		objDocumentoPrincipal.setContenido(objDD.getStrContenido());
		objDocumentoPrincipal = this.saveDocumento(objDocumentoPrincipal);
		log.debug("Numero de documentos de contenido  [" + objDD.getStrContenido() + "]");
		int creados = 0;
		if (lstArchivoTemporal != null && (!masivo)) {
			for (ArchivoTemporal objArchivoTemporal : lstArchivoTemporal) {
				try {
					archivoService.checkInToAlfresco(objUsuario, objArchivoTemporal, objDocumentoPrincipal, 1, quitarCorchete);
				} catch (Exception e) {
					Documento objDocumento = new Documento();
					objDocumento.setTipoDocumento(objTipodocumento);
					objDocumento.setNumeroFolios(1);
					objDocumento.setPropietario(objUsuario);
					objDocumento.setExpediente(objDocumentoPrincipal.getExpediente());
					objDocumento.setContenido(objDD.getStrContenido());
					objDocumento.setFechaAccion(new Date());
					objDocumento.setFechaCreacion(new Date());
					objDocumento.setEstado(Constantes.ESTADO_ACTIVO);
					if (bFirstOne) {
						objAccion = accionService.findByNombre(Constantes.ACCION_REENVIAR);
						objDocumento.setAccion(objAccion);
						objDocumento.setPrincipal(Constantes.DOCUMENTO_PRINCIPAL);
					} else {
						objAccion = accionService.findByNombre(Constantes.ACCION_REGISTRAR);
						objDocumento.setAccion(objAccion);
						objDocumento.setPrincipal(Constantes.DOCUMENTO_NO_PRINCIPAL);
					}
					objDocumento = this.saveDocumento(objDocumento);
					// auditoria
					if (StringUtils.isNotEmpty(opcion) && StringUtils.isEmpty(modulo)) {
						Auditoria au = new Auditoria();
						au.setCampo("archivo");
						au.setDocumento(objDocumento);
						au.setExpediente(objDocumento.getExpediente());
						au.setFechaAudioria(new Date());
						au.setModulo(modulo);
						au.setNuevoValor(objArchivoTemporal.getSNombre());
						au.setOpcion(opcion);
						au.setTipoAuditoria("registro");
						au.setUsuario(objDocumento.getPropietario().getUsuario());
						auditoriaDao.SaveAuditoria(au);
					}
					log.debug(" ############################## error haciendo checkin ######################## ");
					log.warn(e.getMessage());
					log.warn("El archivo no existe, subiendo nuevo archivo");
					archivoService.uploadToAlfresco(objArchivoTemporal, objDocumento, 1);
					log.debug("Documento creado con ID [" + objDocumento.getIdDocumento() + "] Accion [" + objAccion.getNombre() + "] Adjunto [" + objArchivoTemporal.getSNombre() + "]");
					if (bFirstOne) {
						objNuevoDocumentoPrincipal = objDocumento;
						bFirstOne = false;
					}
					creados++;
				}
			}
		}
		if (creados == 0) {
			objDocumentoPrincipal.setPrincipal(Constantes.DOCUMENTO_PRINCIPAL);
			objDocumentoPrincipal = this.saveDocumento(objDocumentoPrincipal);
			objNuevoDocumentoPrincipal = objDocumentoPrincipal;
		}
		return objNuevoDocumentoPrincipal;
	}

	public void inactivarDocumentos(Integer iIdExpediente) {
		log.debug("-> [Service] DocumentoService - inactivarDocumentos():void ");

		List<Documento> lstDocumento = documentoDao.findByIdExpediente(iIdExpediente);
		if (lstDocumento == null) {
			log.debug("No hay documentos a inactivar del expediente con ID [" + iIdExpediente + "]");
			return;
		}
		log.debug("Lista de documentos a inactivar con size [" + lstDocumento.size() + "]");
		for (Documento objDocumento : lstDocumento) {
			log.debug("ID [" + objDocumento.getIdDocumento() + "] Documento [" + objDocumento.getTipoDocumento().getNombre() + " - " + objDocumento.getNumeroDocumento() + "]");
			objDocumento.setEstado(Constantes.ESTADO_INACTIVO);
			// this.saveDocumento(objDocumento);
		}
	}

	@Transactional
	public void saveDesactivarAlerta(String[] arrDocumentArea, Usuario objUsuario) {
		Integer numDocumentoAlert = null;
        Integer numDestinatarioAlert = null;
        String tableAlert = null;
        String cadena = null;

		for(int i=0;i<arrDocumentArea.length;i++){
             cadena = arrDocumentArea[i].substring(0, arrDocumentArea[i].indexOf("-"));
	         numDocumentoAlert = new Integer(cadena);
	         cadena = arrDocumentArea[i].substring(arrDocumentArea[i].indexOf("-") + 1, arrDocumentArea[i].lastIndexOf("-"));
	         numDestinatarioAlert = new Integer(cadena);
	         cadena = arrDocumentArea[i].substring(arrDocumentArea[i].lastIndexOf("-") + 1,arrDocumentArea[i].length());
	         tableAlert = cadena;

	         if (tableAlert!=null && !tableAlert.trim().equals("")){
               	if (tableAlert.equals("TD")){
               		List<Trazabilidaddocumento> listTrazabilidadDocumento = trazabilidadDocumentoService.findByIdDocumento(numDocumentoAlert);
               		for(int j=0;j<listTrazabilidadDocumento.size();j++){
               			if (listTrazabilidadDocumento.get(j).getDocumento().getIdDocumento().toString().equals(numDocumentoAlert.toString()) &&
               				listTrazabilidadDocumento.get(j).getRemitente().getIdusuario().toString().equals(objUsuario.getIdusuario().toString())){
                             listTrazabilidadDocumento.get(j).setIndalerta("0");
                             trazabilidaddocumentoDAO.updateTrazabilidadDocumento(listTrazabilidadDocumento.get(j));
               		   }
               		 }
               	}else{
               		List<Trazabilidadapoyo> listTrazabilidadApoyo = trazabilidadapoyoService.buscarDocumentos(numDocumentoAlert);
               		for(int j=0;j<listTrazabilidadApoyo.size();j++){
               		   if (listTrazabilidadApoyo.get(j).getDocumento().toString().equals(numDocumentoAlert.toString()) &&
               			   listTrazabilidadApoyo.get(j).getRemitente().getIdusuario().toString().equals(objUsuario.getIdusuario().toString()) &&
               			   listTrazabilidadApoyo.get(j).getDestinatario().getIdusuario().toString().equals(numDestinatarioAlert.toString())){
               			    listTrazabilidadApoyo.get(j).setIndalerta("0");
               			    trazabilidadapoyoService.guardar(listTrazabilidadApoyo.get(j));
               		   }
               		}
                }
             }
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Documento saveDocumento(Documento objDocumento) {
               log.debug("-> [Service] DocumentoService - saveDocumento():Documento ");
               return documentoDao.saveDocumento(objDocumento);
	}


	@Transactional
	public Documentofedateado registrarDocumentoFedatario(Documentofedateado objDocFed)  throws Exception{
		log.debug("-> [Service] DocumentoService - registrarDocumentoFedatario():Documento ");
		return documentoFedatarioDao.registrar(objDocFed);
	}

	public List<Documentofedateado> buscarLstDocumentoFedateado(Integer idUsuario, String fecInferior , String fecSuperior, String servicio){
		return documentoFedatarioDao.buscarLstDocumentoFedateado(idUsuario,fecInferior , fecSuperior, servicio);
	}

	@Transactional
	public Documento registrarDocumento(Documento objDocumento) {
		log.debug("-> [Service] DocumentoService - registrarDocumento():Documento ");

		return documentoDao.registrarDocumento(objDocumento);
	}
        
       

	public List<Documento> buscarLstPor(Integer iIdDocPrincipal, ExpedienteSearch objExpedienteSearch) {
		log.debug("-> [Service] DocumentoService - buscarLstPor():List<Documento> ");

		Documento objDocumentoPrincipal = this.findByIdDocumento(iIdDocPrincipal);
		List<Documento> lstDocumento = null;
		log.debug("Expediente a excluir [" + objDocumentoPrincipal.getExpediente().getNroexpediente() + "] con ID [" + objDocumentoPrincipal.getExpediente().getIdexpediente() + "]");
		log.debug("Filtro de Busqueda");
		log.debug("Nro Expediente [" + objExpedienteSearch.getSNroExpediente() + "]");
		log.debug("Nro Documento [" + objExpedienteSearch.getSNroDocumento() + "]");
		log.debug("ID Tipo Documento [" + objExpedienteSearch.getIIdTipoDocumento() + "]");
		log.debug("Nro Caja [" + objExpedienteSearch.getSNroCaja() + "]");
		if (objExpedienteSearch.getIIdTipoDocumento() == null || objExpedienteSearch.getIIdTipoDocumento() == 0) {
			lstDocumento = documentoDao.buscarLstPor(objDocumentoPrincipal.getExpediente().getIdexpediente(), objExpedienteSearch.getSNroExpediente(), objExpedienteSearch.getSNroDocumento(), objExpedienteSearch.getSNroCaja());
		} else {
			lstDocumento = documentoDao.buscarLstPor(objDocumentoPrincipal.getExpediente().getIdexpediente(), objExpedienteSearch.getSNroExpediente(), objExpedienteSearch.getSNroDocumento(), objExpedienteSearch.getSNroCaja(), objExpedienteSearch.getIIdTipoDocumento());
		}
		if (lstDocumento != null) {
			log.debug("Numero de Documentos encontrados [" + lstDocumento.size() + "]");
		} else {
			log.debug("Numero de Documentos encontrados [0]");
		}
		// Usamos el atributo "observacion" para guardar el URL del archivo
		for (Documento objDocumento : lstDocumento) {
			log.debug("Documento [" + objDocumento.getTipoDocumento().getNombre() + " " + objDocumento.getNumeroDocumento() + "] con ID [" + objDocumento.getIdDocumento() + "]");
			log.debug("Numero de archivos adjuntos [" + objDocumento.getArchivos().size() + "]");
			for (Archivo objArchivo : objDocumento.getArchivos()) {
				log.debug("[" + objArchivo.getNombre() + "]");
				if (objArchivo.getRutaAlfresco() != null) {
					objArchivo.setSURL(alfrescoWebServiceClient.obtenerLinkContenido(objArchivo.getRutaAlfresco()));
				}
				log.debug("URL [" + objArchivo.getSURL() + "]");
			}
		}
		return lstDocumento;
	}

	public List<Documento> buscarLstDocumentoPor(char cEstado, char cPrincipal, Integer iIdPropietario) {
		log.debug("-> [Service] DocumentoService - buscarLstDocumentoPor(char, char, Integer):List<Documento> ");

		log.debug("Estado [" + cEstado + "]");
		log.debug("Principal [" + cPrincipal + "]");
		log.debug("Propietario [" + iIdPropietario + "]");
		return documentoDao.buscarLstDocumentoPor(cEstado, cPrincipal, iIdPropietario);
	}
        
       //  public List<Usuario> buscarUsuariosDocumentosPendientes(Integer idUnidad){
       //      return documentoDao.buscarUsuariosDocumentosPendientes(idUnidad);
       //  }

	public List<Documento> buscarLstDocumentoPor(char cPrincipal, Integer idReponsable) {
		log.debug("-> [Service] DocumentoService - buscarLstDocumentoPor(char, Integer):List<Documento> ");

		log.debug("Principal [" + cPrincipal + "]");
		log.debug("Responsable [" + idReponsable + "]");
		return documentoDao.buscarLstDocumentoPor(cPrincipal, idReponsable);
	}

	@SuppressWarnings("rawtypes")
	public List findDocumentos(Usuario objUsuario) {
		log.debug("-> [Service] DocumentoService - findDocumentos():List ");

		List<Documento> lstDocumento = null;
		Perfil perfil = null;
		Rol rol = objUsuario.getRol();
		if (rol != null) {
			perfil = rol.getIdperfil();
		}
		if (perfil != null) {
			Sede objSede = sedeDao.getSedePorUuario(objUsuario);
			// El rol MP es lector de los documentos de Digitalizador
			if (perfil.getNombre().equals(Constantes.PERFIL_MP)) {
				Rol dig = rolDao.encontrarPorNombre(Constantes.ROL_DIGITALIZADOR);
				Integer idRol = null;
				if (dig != null) {
					idRol = dig.getIdrol();
				}
				// la accion para los documentos creados en mesa de partes es
				// REGISTRAR
				int accion = accionService.findByNombre(Constantes.ACCION_REGISTRAR).getIdAccion();
				lstDocumento = documentoDao.findByIdPropietario(null, idRol, null, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL, "todo", objSede.getIdsede(), accion, 0);
			} else if (perfil.getNombre().equals(Constantes.PERFIL_DIG)) {
				// la accion para los documentos registrados es REGISTRAR y
				// RECHAZAR
				int accion = accionService.findByNombre(Constantes.ACCION_REGISTRAR).getIdAccion();
				int accion2 = accionService.findByNombre(Constantes.ACCION_RECHAZARQAS).getIdAccion();
				lstDocumento = documentoDao.findByIdPropietario(null, rol.getIdrol(), null, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL, "todo", objSede.getIdsede(), accion, accion2);
			} else if (perfil.getNombre().equals(Constantes.PERFIL_QAS)) {
				// la accion para los documentos digitalizados es DIGITALIZAR y
				// RECHAZAR\
				Accion acciondata = accionService.findByNombre(Constantes.ACCION_DIGITALIZAR);
				int accion = acciondata.getIdAccion();
				int accion2 = accionService.findByNombre(Constantes.ACCION_RECHAZAR).getIdAccion();
				lstDocumento = documentoDao.findByIdPropietario(null, rol.getIdrol(), null, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL, "todo", objSede.getIdsede(), accion, accion2);
			}
		} else {
			lstDocumento = documentoDao.findByIdPropietario(objUsuario.getIdusuario(), null, null, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL, null, null, 0, 0);

		}
		// log.debug(" findDocumentos hora fin : "+new Date()) ;
		return lstDocumento;
	}


	 public List<TramiteDocumentario> buscarTramiteDocumentario(String nroTramitedocumentario){
		 return documentoDao.buscarTramiteDocumentario(nroTramitedocumentario);
	 }

	@SuppressWarnings("rawtypes") //mis documentos
	public List findDocumentosUsuarioFinal(Usuario objUsuario, String enviados) {
		log.debug("-> [Service] DocumentoService - findDocumentosUsuarioFinal():List ");

		return documentoDao.findByDataUF(objUsuario, enviados,true);
	}
        
        @SuppressWarnings("rawtypes") //mis documentos
	public List findDocumentosPendientesUsuarioFinal(Usuario objUsuario) {
		log.debug("-> [Service] DocumentoService - findDocumentosPendientesUsuarioFinal():List ");

		return documentoDao.findByPendientesDataUF(objUsuario,true);
	}
        
  
        
	@SuppressWarnings("rawtypes")
	public List findDocumentosXExpediente(Usuario objUsuario, Integer idDocumento, boolean enviados){
		log.debug("-> [Service] DocumentoService - findDocumentosXExpediente():List ");

		return documentoDao.findFilasDXE(objUsuario.getIdusuario(), idDocumento, enviados);
	}

	@SuppressWarnings("rawtypes")
	public List findDocumentosAtendidosUsuarioFinal(Usuario objUsuario) {
		log.debug("-> [Service] DocumentoService - findDocumentosAtendidosUsuarioFinal():List ");

		return documentoDao.findByDataAttendedUF(objUsuario);
	}

	@SuppressWarnings("rawtypes")
	public List findDocumentosAtendidosUsuarioFinal(Usuario objUsuario, BusquedaAvanzada objFiltro) {
		log.debug("-> [Service] DocumentoService - findDocumentosAtendidosUsuarioFinal():List ");

		return documentoDao.findByDataAttendedUF(objUsuario, objFiltro);
	}

	@SuppressWarnings("rawtypes")
	public List findByDataUFWithoutSharedInbox(Integer iIdUsuario) {
		log.debug("-> [Service] DocumentoService - findByDataUFWithoutSharedInbox():List ");

		return documentoDao.findByDataUFWithoutSharedInbox(iIdUsuario);

	}
	public Integer findCantDocumentos(Usuario objUsuario) {
		log.debug("-> [Service] DocumentoService - findCantDocumentos():Integer ");

                Integer lstDocumento = 0;
		Perfil perfil = null;
		Rol rol = objUsuario.getRol();
		
                if (rol != null) {
                	perfil = rol.getIdperfil();
		}
                
                if (perfil != null) {
                	Sede objSede = sedeDao.getSedePorUuario(objUsuario);
			// El rol MP es lector de los documentos de Digitalizador
                	if (perfil.getNombre().equals(Constantes.PERFIL_MP)) {
                		Rol dig = rolDao.encontrarPorNombre(Constantes.ROL_DIGITALIZADOR);
				Integer idRol = null;
				if (dig != null) {
					idRol = dig.getIdrol();
				}
				// la accion para los documentos creados en mesa de partes es
				// REGISTRAR
				int accion = accionService.findByNombre(Constantes.ACCION_REGISTRAR).getIdAccion();
				lstDocumento = documentoDao.findCantByIdPropietario(null, idRol, null, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL, "todo", objSede.getIdsede(), accion, 0);
		        } else if (perfil.getNombre().equals(Constantes.PERFIL_DIG)) {
				// la accion para los documentos registrados es REGISTRAR y
				// RECHAZAR
                		int accion = accionService.findByNombre(Constantes.ACCION_REGISTRAR).getIdAccion();
				int accion2 = accionService.findByNombre(Constantes.ACCION_RECHAZAR).getIdAccion();
				lstDocumento = documentoDao.findCantByIdPropietario(null, rol.getIdrol(), null, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL, "todo", objSede.getIdsede(), accion, accion2);
		        } else if (perfil.getNombre().equals(Constantes.PERFIL_QAS)) {
				// la accion para los documentos digitalizados es DIGITALIZAR y
				// RECHAZAR\
                		Accion acciondata = accionService.findByNombre(Constantes.ACCION_DIGITALIZAR);
				int accion = acciondata.getIdAccion();
				int accion2 = accionService.findByNombre(Constantes.ACCION_RECHAZAR).getIdAccion();
				lstDocumento = documentoDao.findCantByIdPropietario(null, rol.getIdrol(), null, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL, "todo", objSede.getIdsede(), accion, accion2);
		        }
		} else {
                	lstDocumento = documentoDao.findCantByIdPropietario(objUsuario.getIdusuario(), null, null, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL, null, null, 0, 0);
		}
		// log.debug(" findDocumentos hora fin : "+new Date()) ;
		return lstDocumento;
	}

	public Integer findCantDocumentosRecibidos(Usuario objUsuario) {
		log.debug("-> [Service] DocumentoService - findCantDocumentosRecibidos():Integer ");

		Integer lstDocumento = 0;
		lstDocumento = documentoDao.findCantDocumentosRecibidos(objUsuario, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL);
		return lstDocumento;
	}
        
        public Integer findCantMisRecepcionVirtualObservados(Usuario objUsuario) {
		log.debug("-> [Service] DocumentoService - findCantMisRecepcionVirtualObservados():Integer ");

		Integer lstDocumento = 0;
		lstDocumento = documentoDao.findCantMisRecepcionVirtualObservados(objUsuario, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL);
		return lstDocumento;
	}
        
        public Integer findCantFirmas(Usuario objUsuario) {
		log.debug("-> [Service] DocumentoService - findCantFirmas():Integer ");

		Integer lstDocumento = 0;
		lstDocumento = documentoDao.findCantFirmas(objUsuario);
		return lstDocumento;
	}
        
        public Integer findCantMisRecepcionVirtual(Usuario objUsuario) {
		log.debug("-> [Service] DocumentoService - findCantRecepcionVirtual():Integer ");

		Integer lstDocumento = 0;
		lstDocumento = documentoDao.findCantMisRecepcionVirtual(objUsuario, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL);
		return lstDocumento;
	} 

	public Integer findCantMisExpedientes(Usuario objUsuario) {
		log.debug("-> [Service] DocumentoService - findCantMisExpedientes():Integer ");

		Integer cantidad = 0;
		cantidad = documentoDao.findCantMisExpedientes(objUsuario, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL);
		return cantidad;
	}

	public List<Documento> findCantidadDocumentos(Usuario objUsuario) {
		log.debug("-> [Service] DocumentoService - findCantidadDocumentos():List<Documento> ");

		List<Documento> lstDocumento = null;
		Perfil perfil = null;
		Rol rol = objUsuario.getRol();
		if (rol != null) {
			perfil = rol.getIdperfil();
		}
		if (perfil != null) {
			Sede objSede = objUsuario.getUnidad().getSede();
			// El rol MP es lector de los documentos de Digitalizador
			if (perfil.getNombre().equals(Constantes.PERFIL_MP)) {
				Rol dig = rolDao.encontrarPorNombre(Constantes.ROL_DIGITALIZADOR);
				Integer idRol = null;
				if (dig != null) {
					idRol = dig.getIdrol();
				}
				// la accion para los documentos creados en mesa de partes es
				// REGISTRAR
				int accion = accionService.findByNombre(Constantes.ACCION_REGISTRAR).getIdAccion();
				lstDocumento = documentoDao.findByIdPropietario(null, idRol, null, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL, "todo", objSede.getIdsede(), accion, 0);
			} else if (perfil.getNombre().equals(Constantes.PERFIL_DIG)) {
				// la accion para los documentos registrados es REGISTRAR y
				// RECHAZAR
				int accion = accionService.findByNombre(Constantes.ACCION_REGISTRAR).getIdAccion();
				int accion2 = accionService.findByNombre(Constantes.ACCION_RECHAZAR).getIdAccion();
				lstDocumento = documentoDao.findByIdPropietario(null, rol.getIdrol(), null, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL, "todo", objSede.getIdsede(), accion, accion2);
			} else if (perfil.getNombre().equals(Constantes.PERFIL_QAS)) {
				// la accion para los documentos digitalizados es DIGITALIZAR y
				// RECHAZAR\
				Accion acciondata = accionService.findByNombre(Constantes.ACCION_DIGITALIZAR);
				int accion = acciondata.getIdAccion();
				int accion2 = accionService.findByNombre(Constantes.ACCION_RECHAZAR).getIdAccion();
				lstDocumento = documentoDao.findByIdPropietario(null, rol.getIdrol(), null, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL, "todo", objSede.getIdsede(), accion, accion2);
			}
		} else {
			lstDocumento = documentoDao.findByIdPropietario(objUsuario.getIdusuario(), null, null, Constantes.ESTADO_ACTIVO, Constantes.DOCUMENTO_PRINCIPAL, null, null, 0, 0);
		}
		// log.debug(" findDocumentos hora fin : "+new Date()) ;
		return lstDocumento;
	}
        
        public List<Documento> findDocumentoPorNumerar(Usuario objUsuario, Documento d){
           List<Documento> lstDocumento = null;
	   lstDocumento = documentoDao.findDocumentoPorNumerar(objUsuario, d);
	   return lstDocumento;
        }

	public List<Documento> findDocumentosPorNumerar(Usuario objUsuario, Expediente expediente) {
		log.debug("-> [Service] DocumentoService - findDocumentosPorNumerar():List<Documento> ");

		List<Documento> lstDocumento = null;
		lstDocumento = documentoDao.findDocumentosPorNumerar(objUsuario, expediente);
		return lstDocumento;
	}

	public List<Documento> findDocumentosPorFirmar(Usuario objUsuario, Expediente expediente) {
		log.debug("-> [Service] DocumentoService - findDocumentosPorFirmar():List<Documento> ");

		List<Documento> lstDocumento = null;
		lstDocumento = documentoDao.findDocumentosPorFirmar(objUsuario, expediente);
		return lstDocumento;
	}

	@Transactional 
	private Documento guardarDocEnumerados(Documento objDocumento, String tipoNumeracion, Usuario usuarioSession)  throws Exception{
                Unidad unid = new Unidad(usuarioSession.getIdUnidadPerfil());
               
                try{ 
                    if (objDocumento.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.TIPO_INFORME_CONJUNTO) || objDocumento.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.TIPO_MEMORANDO_CONJUNTO))
                       unid = new Unidad(new Integer(parametroService.findByTipo(Constantes.PARAMETRO_UNIDAD_INFORME_CONJUNTO).get(0).getValor()));

                    List<Numeracion> listaNum= numeracionDAO.findAllUnidadAndTipoDoc(unid.getIdunidad(),objDocumento.getTipoDocumento().getIdtipodocumento());

                    if (listaNum != null && listaNum.size() > 0 && listaNum.get(0).getTiponumeracion() != null) {
                      String nro = numeracionDAO.guardarObjProcedure(listaNum.get(0), unid.getIdunidad(), usuarioSession.getIdusuario().intValue());
                      objDocumento.setNumeroDocumento(nro);
                      objDocumento.setEnumerado(Constantes.Si);
                      objDocumento.setFechaModificacion(new Date());
                      objDocumento.setUsuarioModificacion(usuarioSession.getIdusuario());
                      objDocumento.setUnidadenumera(usuarioSession.getIdUnidadPerfil());
                      
                      if (objDocumento.getProyecto().toString().equals(Constantes.DOCUMENTO_PROYECTO.toString()))
                         objDocumento.setProyecto(Constantes.DOCUMENTO_FINAL);
                                 
                      this.saveDocumento(objDocumento);
                    }
                }catch(Exception e){
                    throw e;
                }                       
                return objDocumento;
         
	}
        
        private byte[] toByteArray(InputStream intput) throws  IOException{
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;

            while ((len= intput.read(buffer))!=-1){
                os.write(buffer,0,len);
            }

            return os.toByteArray();
       }
        
        @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
        public void actualizarDatosRecepcionVirtual(Integer idExterno) throws  Exception{
            try{
                IotdtmDocExterno iotdtmDocExterno = documentoExternoVirtualDAO.buscarDocumentoVirtual(idExterno);
                Documento d = documentoDao.findByIdDocumento(iotdtmDocExterno.getSidrecext().getIddocumento());
                mapSession = ActionContext.getContext().getSession();
                Usuario objUsuarioSession = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                 
                DocumentoDerivacion documentoDerivacion = new DocumentoDerivacion();
                documentoDerivacion.setTipo("P");
                documentoDerivacion.setIddocumento(iotdtmDocExterno.getSidrecext().getIddocumento());

                List<DocumentoDerivacion> lista = documentoDerivacionDAO.getUsuarioDerivacion(documentoDerivacion);
                 
                if (lista==null || lista.size()==0){
                    new Exception();
                }
                 
                AlfrescoApiWs alfrescoApiWs;
                Session sesionAlfresco = null;
                String alfrescoHostPublico = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_HOST);
                String alfrescoHostPort = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PORT);
                String alfrescoProtocolo = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PROTOCOLO);
                String URL_ALFRESCO = alfrescoProtocolo+"://"+alfrescoHostPublico+":"+alfrescoHostPort+"/alfresco/cmisatom";
                List<Archivo> lstArchivo = archivoService.findLstByIdDocumento(iotdtmDocExterno.getSidrecext().getIddocumento());
                String objectId = null;
                
                for(int i=0;i<lstArchivo.size();i++){
                  if (lstArchivo.get(i).getPrincipal()=='M'){
                        objectId = lstArchivo.get(i).getObjectId();
                  }      
                }
                
                if (objectId==null)
                    new Exception();
                
                alfrescoApiWs = new AlfrescoApiWs(URL_ALFRESCO, USERCONSULTA, USERCONSULTA_CLAVE, REPOSITORIO_ID);
                sesionAlfresco = alfrescoApiWs.getSessionAlfresco();
                Document documento = (Document)sesionAlfresco.getObject(objectId);
                InputStream intput = documento.getContentStream().getStream();
                byte[] bytes = toByteArray(intput);     

                IotdtcRecepcion iotdtcRecepcion = iotdtmDocExterno.getSidrecext(); 
                iotdtcRecepcion.setVnumregstd(d.getID_CODIGO().toString());
                iotdtcRecepcion.setVuniorgstd(unidadService.buscarObjPor(lista.get(0).getUnidadpropietario()).getNombre().toString());
                iotdtcRecepcion.setCcoduniorgstd(lista.get(0).getUnidadpropietario().toString());
                iotdtcRecepcion.setVusuregstd(objUsuarioSession.getUsuario());
                iotdtcRecepcion.setDfecregstd(new Date());
                iotdtcRecepcion.setVanioregstd(new SimpleDateFormat("yyyy").format(d.getFechaCreacion()));
                iotdtcRecepcion.setCflgest(d.getRecepcionado().charAt(0));  
                iotdtcRecepcion.setVobs(d.getObservacion());
                iotdtcRecepcion.setBcarstd(bytes);
                recepcionVirtualDAO.registrarDocumento(iotdtcRecepcion);

            }catch(Exception e){
                throw e;
            } 
        }
        
        @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
        public String enviarDocumentoVirtual(Integer idExterno, Usuario objUsuario) throws  Exception{
            
            try{
                 WSPideTramite pideTramite = new WSPideTramite();
                 EndPoint endPoint = new EndPoint();
                 IotdtmDocExterno iotdtmDocExterno = documentoExternoVirtualDAO.buscarDocumentoVirtual(idExterno);
                 Documento d = documentoDao.findByIdDocumento(iotdtmDocExterno.getSidemiext().getIddocumento());
                
                 RecepcionTramite recepcionTramite = new RecepcionTramite();
                 //JC-RUC
                 recepcionTramite.setVrucentrem(parametroService.findByTipoUnico("RUC_OSITRAN").getValor());
                 recepcionTramite.setVrucentrec(iotdtmDocExterno.getSidemiext().getVrucentrec());
                 //JC-RUC
                 recepcionTramite.setVnomentemi(iotdtmDocExterno.getVnomentemi());
                 recepcionTramite.setCcodtipdoc(iotdtmDocExterno.getCcodtipdoc());
                 recepcionTramite.setVnumdoc(iotdtmDocExterno.getVnumdoc());
                 
                 GregorianCalendar gc = new GregorianCalendar();
		 gc.setTimeInMillis(iotdtmDocExterno.getDfecdoc().getTime());
		 DatatypeFactory df = DatatypeFactory.newInstance();
		 XMLGregorianCalendar dfecdoc = df.newXMLGregorianCalendar(gc);
                 
                 recepcionTramite.setDfecdoc(dfecdoc);
                 recepcionTramite.setVuniorgrem(iotdtmDocExterno.getSidemiext().getVuniorgrem());
                 recepcionTramite.setCtipdociderem(iotdtmDocExterno.getSidemiext().getCtipdociderem().toString());
                 recepcionTramite.setVnumdociderem(iotdtmDocExterno.getSidemiext().getVnumdociderem());
                 recepcionTramite.setVcuoref(iotdtmDocExterno.getSidemiext().getVcuoref());
                 recepcionTramite.setVuniorgdst(iotdtmDocExterno.getVuniorgdst());
                 recepcionTramite.setVnomdst(iotdtmDocExterno.getVnomdst());
                 recepcionTramite.setVnomcardst(iotdtmDocExterno.getVnomcardst());
                 recepcionTramite.setVasu(iotdtmDocExterno.getVasu());
                 recepcionTramite.setBpdfdoc(Base64.encodeBase64(iotdtmDocExterno.getIotdtdDocPrincipalList().get(0).getBpdfdoc()));
                 recepcionTramite.setVnomdoc(iotdtmDocExterno.getIotdtdDocPrincipalList().get(0).getVnomdoc());
                 recepcionTramite.setSnumfol(iotdtmDocExterno.getSnumfol().intValue());
                 int contador = 0;
               
                 if (iotdtmDocExterno.getIotdtdAnexoList()!=null && iotdtmDocExterno.getIotdtdAnexoList().size()>0){
                     DocumentoAnexo anexoBean = null;
                     for(int i=0;i<iotdtmDocExterno.getIotdtdAnexoList().size();i++){
                         anexoBean = new DocumentoAnexo();
			 anexoBean.setVnomdoc(iotdtmDocExterno.getIotdtdAnexoList().get(i).getVnomdoc());
                         recepcionTramite.getLstanexos().add(anexoBean);
                         contador++;
                     }
                 
                     recepcionTramite.setVurldocanx(iotdtmDocExterno.getVurldocanx());        
                 }
                 
                 recepcionTramite.setSnumanx(contador);
                 String vcuo = null;
                 String vcuoRef = null;
               
                 if (!iotdtmDocExterno.getSidemiext().getCflgenv().equals("E")){
                    IotdtcDespacho iotdtcDespacho = iotdtmDocExterno.getSidemiext();
                    iotdtcDespacho.setDfecmod(new Date());
                    try{
                        List<String> lst = documentoExternoVirtualDAO.buscarTramiteVirtual(d.getID_CODIGO().toString());
                        if (lst!=null){
                           if (lst.size()>1){ 
                                for (int i=0;i<lst.size();i++){
                                    IotdtmDocExterno iotdtmDocExternoRef = documentoExternoVirtualDAO.buscarDocumentoVirtual(new Integer(lst.get(i)));
                                    if (iotdtmDocExternoRef!=null && iotdtmDocExternoRef.getSidemiext().getCflgest() == 'O'){
                                        vcuoRef = iotdtmDocExternoRef.getSidemiext().getVcuo();
                                        recepcionTramite.setVcuoref(vcuoRef);
                                        break;
                                    }
                                }
                           }     
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }     
                         
                    //JC-RUC
                    //vcuo = endPoint.getCuo(parametroService.findByTipoUnico("RUC_OSITRAN").getValor() , "3011");
                    vcuo = endPoint.getCuo("1");
                    //JC-RUC
                    if(!vcuo.equals("-1")) {
                         iotdtcDespacho.setVcuo(vcuo);
                         iotdtcDespacho.setDfecenv(new Date());
                         iotdtcDespacho.setVcuoref(vcuoRef);
                         iotdtcDespacho.setVusumod(objUsuario.getUsuario());
                         despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                         
                         try{
                                recepcionTramite.setVcuo(vcuo);
                                RespuestaTramite respuestaTramite = pideTramite.recepcionarTramite(recepcionTramite, Constantes.AMBIENTE_WS_PIDE_TRAMIE);
		                if(respuestaTramite.getVcodres().equals("0000")){
                                   iotdtcDespacho.setCflgest('E');
                                   iotdtcDespacho.setDfecmod(new Date());
                                   iotdtcDespacho.setVusumod(objUsuario.getUsuario());          
                                   despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                                   try{
                                        if (iotdtcDespacho.getVcuoref()!=null && !iotdtcDespacho.getVcuoref().trim().equals("")){
                                            IotdtcDespacho iotdtcDespachoRef = despachoVirtualDAO.findByVcuo(iotdtcDespacho.getVcuoref());
                                            if (iotdtcDespachoRef!=null){
                                              iotdtcDespachoRef.setCflgest('S');
                                              iotdtcDespachoRef.setDfecmod(new Date());
                                              iotdtcDespachoRef.setVusumod(objUsuario.getUsuario());
                                              despachoVirtualDAO.registrarDocumento(iotdtcDespachoRef);
                                            } 
                                        }
                                   }catch(Exception e){
                                     e.printStackTrace();
                                   }
                                   
                                   return respuestaTramite.getVdesres();
				}else{
                                   iotdtcDespacho.setCflgenv('E');	
                                   iotdtcDespacho.setDfecmod(new Date());
                                   iotdtcDespacho.setVusumod(objUsuario.getUsuario());
                                   despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                                   return respuestaTramite.getVdesres();
				}  
                                 
                         }catch(Exception e){
                             e.printStackTrace();
                             iotdtcDespacho.setCflgenv('E');	
                             iotdtcDespacho.setDfecmod(new Date());
                             iotdtcDespacho.setVusumod(objUsuario.getUsuario());
                             despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                             return parametroService.findByTipoAndValue("MENSAJE_PIDE_CLIENTE", "MC_5").getDescripcion();
                         }
                     }else{
                         return parametroService.findByTipoAndValue("MENSAJE_PIDE_CLIENTE", "MC_4").getDescripcion();
                     }
                 }else{
                     vcuo = iotdtmDocExterno.getSidemiext().getVcuo();
                     recepcionTramite.setVcuo(vcuo);
                     IotdtcDespacho iotdtcDespacho = iotdtmDocExterno.getSidemiext();
                      
                     ConsultaTramite consultaTramite = new ConsultaTramite();
                     consultaTramite.setVcuo(vcuo);
                     consultaTramite.setVrucentrec(iotdtmDocExterno.getSidemiext().getVrucentrec());
                     consultaTramite.setVrucentrem(parametroService.findByTipoUnico("RUC_OSITRAN").getValor());
                     RespuestaConsultaTramite respuestaConsultaTramite = pideTramite.consultarTramite(consultaTramite, Constantes.AMBIENTE_WS_PIDE_TRAMIE);
                     
                     try{
                           if(respuestaConsultaTramite.getVcodres().equals("0000")) {
                               iotdtcDespacho.setCflgest('E');
                               iotdtcDespacho.setDfecmod(new Date());
                               iotdtcDespacho.setVusumod(objUsuario.getUsuario());
                               despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                           }else{
                                   if (respuestaConsultaTramite.getVcodres().equals("0001")){
                                        //JC-RUC
                                        //vcuo = endPoint.getCuo(parametroService.findByTipoUnico("RUC_OSITRAN").getValor() , "3011");
                                        vcuo = endPoint.getCuo("1");
                                        //JC-RUC
                                        RespuestaTramite respuestaTramite = pideTramite.recepcionarTramite(recepcionTramite, Constantes.AMBIENTE_WS_PIDE_TRAMIE);
                                        if(respuestaTramite.getVcodres().equals("0000")){
                                            iotdtcDespacho.setCflgest('E');
                                            iotdtcDespacho.setDfecmod(new Date());
                                            iotdtcDespacho.setVusumod(objUsuario.getUsuario());
                                            despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                                            
                                            try{
                                                if (iotdtcDespacho.getVcuoref()!=null && !iotdtcDespacho.getVcuoref().trim().equals("")){
                                                    IotdtcDespacho iotdtcDespachoRef = despachoVirtualDAO.findByVcuo(iotdtcDespacho.getVcuoref());
                                                    if (iotdtcDespachoRef!=null){
                                                        iotdtcDespachoRef.setCflgest('S');
                                                        iotdtcDespachoRef.setDfecmod(new Date());
                                                        iotdtcDespachoRef.setVusumod(objUsuario.getUsuario());
                                                        despachoVirtualDAO.registrarDocumento(iotdtcDespachoRef);
                                                    }    
                                                }
                                            }catch(Exception e){
                                              e.printStackTrace();
                                            }
                                            
                                            return respuestaTramite.getVdesres();
                                         }else {
                                            iotdtcDespacho.setDfecmod(new Date());
                                            iotdtcDespacho.setCflgenv('E');	
                                            iotdtcDespacho.setVusumod(objUsuario.getUsuario());
                                            despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                                            return respuestaTramite.getVdesres();
                                         }
                                   }else{
                                           return respuestaConsultaTramite.getVdesres();
                                   } 
                            }     
                         }catch(Exception e){
                             e.printStackTrace();
                             return parametroService.findByTipoAndValue("MENSAJE_PIDE_CLIENTE", "MC_5").getDescripcion();
                         }  
                 }
                 
            }catch(Exception e){
                e.printStackTrace();
                throw e;
            }
            
            return "";
        }

        @Transactional(propagation=Propagation.REQUIRED , rollbackFor=Exception.class)
        public String enviarCargoRecepcionVirtual(Integer idExterno) throws Exception{
            
            try{
                 CargoTramite cargoTramite = new CargoTramite();
                 IotdtmDocExterno iotdtmDocExterno = documentoExternoVirtualDAO.buscarDocumentoVirtual(idExterno);
                 IotdtcRecepcion iotdtcRecepcion = iotdtmDocExterno.getSidrecext();
                 cargoTramite.setVnumregstd(iotdtcRecepcion.getVnumregstd());
		 cargoTramite.setVanioregstd(iotdtcRecepcion.getVanioregstd());
		 ///JC-RUC-X
                 //cargoTramite.setVrucentrem(parametroDao.findByTipoUnico("RUC_OSITRAN").getValor());
		 //cargoTramite.setVrucentrec(iotdtcRecepcion.getVrucentrem());
                 ///JC-RUC
		 cargoTramite.setVusuregstd(iotdtcRecepcion.getVusuregstd());
		 cargoTramite.setVuniorgstd(iotdtcRecepcion.getVuniorgstd());
		 cargoTramite.setBcarstd(Base64.encodeBase64(iotdtcRecepcion.getBcarstd()));
                 
                 cargoTramite.setVcuo(iotdtcRecepcion.getVcuo());
                 cargoTramite.setVcuoref(iotdtcRecepcion.getVcuoref());
		 cargoTramite.setVobs(iotdtcRecepcion.getVobs());
		 cargoTramite.setCflgest(iotdtcRecepcion.getCflgest().toString());
                 //cargoTramite.setVdesanxstdrec(iotdtcRecepcion.getVdesanxstd()==null?"":iotdtcRecepcion.getVdesanxstd());
                 
		 GregorianCalendar gregory = new GregorianCalendar();
	         gregory.setTime(iotdtcRecepcion.getDfecregstd());
		 DatatypeFactory df = DatatypeFactory.newInstance();
		 XMLGregorianCalendar dfecdoc = df.newXMLGregorianCalendar(gregory);
		 cargoTramite.setDfecregstd(dfecdoc);
                 
                 WSPideTramite wsPideTramite = new WSPideTramite();
	         RespuestaCargoTramite respuestaCargoTramite = new RespuestaCargoTramite();
		 respuestaCargoTramite = wsPideTramite.cargoTramite(cargoTramite, Constantes.AMBIENTE_WS_PIDE_TRAMIE);
			
	         if(respuestaCargoTramite.getVcodres().equals("0000")){
                    iotdtcRecepcion.setCflgenvcar('S');
                    iotdtcRecepcion.setDfecmod(new Date());
		    recepcionVirtualDAO.registrarDocumento(iotdtcRecepcion);
                    return respuestaCargoTramite.getVdesres();
                 }else{
                    return respuestaCargoTramite.getVdesres();
                 }       
            }catch(Exception e){
                e.printStackTrace();
                throw e;
            }  
 
        }
        
         public Documento findByIdNroTramite(Integer nroTramite){
             return documentoDao.findByIdNroTramite(nroTramite);
         }

	/**REN Numera los documentos de Word con el nmero generado ---------------------------------------------------------------*/
	@Override
	public boolean numerarDocumentoFisico(Usuario usuario, Documento documento, int whatToDo) {
		log.debug("-> [Service] DocumentoService - numerarDocumentoFisico():boolean ");

		log.debug("Numerando documentos fisicos");
		List<Archivo> archivos = archivoService.findLstByIdDocumento(documento.getIdDocumento());

		if (archivos != null && archivos.size() > 0) {
			for (Archivo archivoObj : archivos) {
				if (archivoObj.getNombreArchivo().endsWith(".doc") || archivoObj.getNombreArchivo().endsWith(".DOC")) {
					String rutaAlfresco = repositorioService.obtenerRutaExpediente(documento.getExpediente());
					String archivo = archivoObj.getNombreArchivo();
					String rutaDestino = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_TEMPORAL_ALFRESCO);

					if (!rutaDestino.endsWith("/")) {
						rutaDestino += "/";
					}

					if (!rutaAlfresco.endsWith("/")) {
						rutaAlfresco += "/";
					}

					if (alfrescoWebServiceClient.descargarArchivo(rutaAlfresco, archivo, rutaDestino)) {
						log.debug("Se guardo el archivo {} en la ruta {}", archivo, rutaDestino);
						Map<String, Object> campos = new HashMap<String, Object>();

						campos.put("BOOKMARK", "osinumero");
						campos.put("TEXTO", documento.getNumeroDocumento());
						File fisico = new File(rutaDestino + archivo);
						log.debug("Inicio de la modificacion del Template");
						boolean resp = TemplateUtils.modifyDocument(fisico.getAbsolutePath(), campos, whatToDo);
						log.debug("Termino de la modificacion del Template");

						if (resp) {
							log.info("Se enumero el documento fisico {}", documento.getNumeroDocumento());
							String urlCompleteSpaceExpediente = repositorioService.obtenerRutaCompletaExpediente(documento.getExpediente());
							alfrescoWebscriptClient.modificarContenidoArchivo(usuario, fisico, urlCompleteSpaceExpediente, archivo);
						}

						fisico.delete();
						log.info("Eliminado el archivo " + rutaDestino + archivo);

						if (!resp) {
							return resp;
						}
					}
				}
			}
		}

		return true;
	}

	@Override
	public boolean firmarDocumentoFisico(Usuario usuario, Documento documento, int whatToDo) {
		log.debug("-> [Service] DocumentoService - firmarDocumentoFisico():boolean ");

		List<Archivo> archivos = documento.getArchivos();

		if (archivos != null && archivos.size() > 0) {
			for (Archivo archivoObj : archivos) {
				if (archivoObj.getNombreArchivo().endsWith(".doc") || archivoObj.getNombreArchivo().endsWith(".DOC")) {
					String rutaAlfresco = repositorioService.obtenerRutaExpediente(documento.getExpediente());
					String archivo = archivoObj.getNombreArchivo();
					String rutaDestino = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_TEMPORAL_ALFRESCO);

					if (!rutaDestino.endsWith("/")) {
						rutaDestino += "/";
					}

					if (!rutaAlfresco.endsWith("/")) {
						rutaAlfresco += "/";
					}

					if (alfrescoWebServiceClient.descargarArchivo(rutaAlfresco, archivo, rutaDestino)) {
						File fisico = new File(rutaDestino + archivo);
						Map<String, Object> metadataFirma = this.getMetadataFirma(documento, rutaDestino + archivo);
						boolean resp = TemplateUtils.modifyDocument(fisico.getAbsolutePath(), metadataFirma, whatToDo);

						if (resp) {
							log.info("El documento fue modificado con una firma");
							String urlCompleteSpaceExpediente = repositorioService.obtenerRutaCompletaExpediente(documento.getExpediente());
							alfrescoWebscriptClient.modificarContenidoArchivo(usuario, fisico, urlCompleteSpaceExpediente, archivo);
						}

						fisico.delete();

						if (metadataFirma != null) {
							File firmaDigital = (File) metadataFirma.get("file");

							if (firmaDigital != null) {
								firmaDigital.delete();
							}
						}

						log.info("Eliminado el archivo " + rutaDestino + archivo);

						if (!resp) {
							return resp;
						}
					}
				}
			}
		}

		return true;
	}

	@Override
	public Map<String, Object> getMetadataFirma(Documento documento, String rutaArchivo) {
		log.debug("-> [Service] DocumentoService - getMetadataFirma():Map<String, Object> ");

		byte[] firmaDigitalData = null;
		File fFirmaDigital = null;
		Map<String, Object> metadataFirma = new HashMap<String, Object>();
		Parametro bookmarkFirma = parametroDao.findByTipoUnico(Constantes.PARAMETRO_TIPO_BOOKMARKFIRMA);
		Usuario firmante = documento.getFirmante();
		StringBuilder sbRutaFirmaDigital = new StringBuilder(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_TEMPORAL_ALFRESCO)).append("/firmaDigital");

		if (firmante == null) {
			log.info("No se inserto firma en el archivo [" + rutaArchivo + "] pues no hay firmante para el documento [" + documento.getIdDocumento() + "]");

			return null;
		}

		if (log.isDebugEnabled()) {
			log.debug("Firmante [" + firmante.getUsuario() + "]");
		}

		if ((firmaDigitalData = documentoDao.getFirmaDigital(firmante)) == null) {
			log.info("No se inserto firma en el archivo [" + rutaArchivo + "] pues no hay firma digital en la BD asociado al firmante [" + firmante.getUsuario() + "]");

			return null;
		}

		FileOutputStream fos = null;
		try {
			fFirmaDigital = new File(sbRutaFirmaDigital.toString());
			fos = new FileOutputStream(fFirmaDigital);
			fos.write(firmaDigitalData);
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);

			return null;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ex) {
				}
			}
		}

		metadataFirma.put("file", fFirmaDigital);
		metadataFirma.put("bookmarkFirma", bookmarkFirma.getValor());
		metadataFirma.put("rutaArchivo", rutaArchivo);
		metadataFirma.put("rutaFirmaDigital", sbRutaFirmaDigital.toString());

		return metadataFirma;
	}

	public List<DocumentoList> getDocumentList(List<Documento> lstD, String strRol) throws RuntimeException {
		log.debug("-> [Service] DocumentoService - getDocumentList():List<DocumentoList> ");

		try {
			List<DocumentoList> lstDL = new ArrayList<DocumentoList>();
			Trazabilidaddocumento objT;
			for (Documento objD : lstD) {
				DocumentoList objDL = new DocumentoList(objD.getIdDocumento(), (objD.getExpediente() != null && objD.getExpediente().getAsunto() != null ? objD.getExpediente().getAsunto() : objD.getAsunto()), (objD.getTipoDocumento() != null && objD.getTipoDocumento().getNombre() != null ? objD.getTipoDocumento().getNombre() : objD.getNombreTipoDocumento()) + " - " + (objD.getNumeroDocumento() != null ? objD.getNumeroDocumento() : objD.getNroexpediente()));
				if (objD.getExpediente() != null && objD.getExpediente().getConcesionario() != null) {
					objDL.setStrCorrentista(objD.getExpediente().getConcesionario().getRazonSocial());
				}
				objDL.setStrFecha(objD.getFechaAccion());
				if (objD.getExpediente() != null) {
					objDL.setExpediente(objD.getExpediente());
				}
				objT = trazabilidadDocumentoService.findByMaxNroRegistro(objD.getIdDocumento(), Constantes.ACCION_CONSULTAR, null, null);
				if (strRol.equals(Constantes.ROL_MESA_PARTES) && objD.getExpediente() != null) {
					objDL.setStrRemitente(objD.getExpediente().getCliente().getRazonSocial());
				} else {
					objDL.setStrRemitente(objT.getRemitente().getNombres() + " " + objT.getRemitente().getApellidos());
				}
				objDL.setSAccion(objD.getAccion().getNombre());
				lstDL.add(objDL);
			}
			return lstDL;
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}

	@Override
	public List<DocumentoList> getDocuments(Usuario usuario, char habilitado) throws RuntimeException {
		log.debug("-> [Service] DocumentoService - getDocuments():List<DocumentoList> ");

		try {
			List<Documento> lstD;
			String sTempo = null;
			Usuario objUsuario = usuarioService.findByIdUsuario(usuario.getIdusuario());
			Perfil objPerfil = (objUsuario.getRol() != null ? objUsuario.getRol().getIdperfil() : usuario.getRol().getIdperfil());
			Sede objSede = objUsuario.getUnidad().getSede();
			if (objPerfil.getNombre().equals(Constantes.PERFIL_MP) || objPerfil.getNombre().equals(Constantes.PERFIL_DIG) || objPerfil.getNombre().equals(Constantes.PERFIL_QAS)) {
				sTempo = "todo";
			}
			// El rol MP es lector de los documentos de Digitalizador
			if (objPerfil.getNombre().equals(Constantes.PERFIL_MP)) {
				// la accion para los documentos de mesa de partes es REGISTRAR
				int accion = accionService.findByNombre(Constantes.ACCION_REGISTRAR).getIdAccion();
				lstD = documentoDao.findByIdPropietario(null, null, Constantes.ROL_DIGITALIZADOR, habilitado, Constantes.DOCUMENTO_PRINCIPAL, sTempo, objSede.getIdsede(), accion, 0);
			} else {
				lstD = documentoDao.findByIdPropietario(usuario.getIdusuario(), null, null, habilitado, Constantes.DOCUMENTO_PRINCIPAL, sTempo, objSede.getIdsede(), 0, 0);
			}
			return this.getDocumentList(lstD, objPerfil.getNombre());
		} catch (RuntimeException re) {
			log.error(re.getMessage(), re);
			return null;
		}
	}

	public String fillContenido(DocumentoDetail objDD) {
		log.debug("-> [Service] DocumentoService - fillContenido():String ");

		try {
			String contenido = "Ud. ha registrado un " + objDD.getStrTipoDocumento() + " Nro. " + objDD.getStrNroDocumento() + " el dia " + objDD.getStrFecha() + " de " + objDD.getStrRemitente() + ", referente al proceso " + objDD.getStrProceso() + ".\n\r";
			if(!objDD.getCodProceso().equals(Constantes.CODIGO_COMUNICACIONES_INT)){
				contenido += "El cliente " + objDD.getStrRazonSocial() + " se identifica con " + objDD.getStrTipoIdentificacion() + ": " + objDD.getStrNroIdentificacion() + " y direcci&oacute;n domiciliaria " + objDD.getStrDireccionPrincipal() + ".";
			}
			return contenido;
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}

	

	@Deprecated //usar el metodo sobrecargado
	@Override
	@Transactional
	public void registrarDIG(Integer iIdDocumento, Map<String, List<ArchivoTemporal>> mapUpload, Usuario objRemitente) {
		log.debug("-> [Service] DocumentoService - registrarDIG():void DEPRECATED");

		Accion objAccion = accionService.findByNombre(Constantes.ACCION_DIGITALIZAR);
		Documento objDocumento = this.findByIdDocumento(iIdDocumento);
		List<ArchivoTemporal> lstArchivoTemporal = null;
		Sede objSede = sedeDao.getSedePorUuario(objRemitente);

		if (mapUpload != null) {
			log.debug("Trabajando con upload2 - Archivos rechazados");
			lstArchivoTemporal = mapUpload.get("upload2");
			log.debug("Inactivando archivos del Documento con ID [" + iIdDocumento + "]");
			archivoService.updateEstadoByDocumento(iIdDocumento, Constantes.ARCHIVO_ESTADO_DIGITALIZACION_INACTIVO);

			if (lstArchivoTemporal != null) {
				for (ArchivoTemporal objArchivoTemporal : lstArchivoTemporal) {
					Archivo objArchivo = null;
					objArchivo = archivoService.findByCriteria(iIdDocumento, objArchivoTemporal.getFArchivo().getName());
					log.debug("Reactivando archivo [" + objArchivoTemporal.getFArchivo().getName() + "] con ID [" + objArchivo.getIdArchivo() + "]");
					archivoService.updateEstadoByArchivo(objArchivo.getIdArchivo(), Constantes.ARCHIVO_ESTADO_DIGITALIZACION_YES);
				}
			}

			log.debug("Trabajando con upload1 - Nuevos archivos");
			lstArchivoTemporal = mapUpload.get("upload1");

			if (lstArchivoTemporal != null) {
				log.debug("Numero de archivos a mover [" + lstArchivoTemporal.size() + "]");
				// Grabando los archivos temporales a la Base de Datos. Nueva
				// carpeta : IrisPDFIn
				archivoService.saveArhivoFromSessionToDB(lstArchivoTemporal, objDocumento, objRemitente);
			} else {
				log.warn("No hay archivos nuevos");
			}
		}

		objDocumento.setPropietario(usuarioService.findByUsuario(Constantes.USUARIO_QAS, objSede.getIdsede()));
		objDocumento.setRemitente(objRemitente.getNombres() + " " + objRemitente.getApellidos());
		objDocumento.setAccion(objAccion);
		objDocumento.setFechaAccion(new Date());
		this.saveDocumento(objDocumento);
		trazabilidadDocumentoService.saveTrazabilidadDocumento(objDocumento, objRemitente, false, false);
		log.info("Documento registrado desde DIG. Nuevo propietario [" + objDocumento.getPropietario().getNombres() + " " + objDocumento.getPropietario().getApellidos() + "]");
		// Registrar Trazabilidad Expediente
		Usuario objDestinatario = usuarioService.findByUsuario(Constantes.USUARIO_QAS, objSede.getIdsede());

                /*
		if (objDocumento.getExpediente().getProceso().getTipoproceso().getNombre().equalsIgnoreCase("stor")) {
			char tipoDocumento = documentoDao.findByIdDocumento(iIdDocumento).getPrincipal();
			log.debug("Tipo de Documento [" + tipoDocumento + "]");
			if (tipoDocumento == 'S') {
				log.debug("REGISTRAR TRAZABILIDAD EXPEDIENTE: DIG - QAS");
				trazabilidadExpedienteService.saveTrazabilidadExpediente(objDocumento.getExpediente(), objRemitente, objDestinatario, objAccion, "");
			} else {
				log.debug("INGRESO DE DOCUMENTO ADICIONAL: NO SE REGISTRA TRAZABILIDAD EXPEDIENTE");
			}
		}*/
	}

	@Override
	@Transactional
	public void registrarDIG(Integer iIdDocumento, Map<String, List<ArchivoTemporal>> mapUpload, Usuario objRemitente, String observacionDIG) {
		log.debug("-> [Service] DocumentoService - registrarDIG():void ");

		Accion objAccion = accionService.findByNombre(Constantes.ACCION_DIGITALIZAR);
		Documento objDocumento = this.findByIdDocumento(iIdDocumento);
		List<ArchivoTemporal> lstArchivoTemporal = null;
		Sede objSede = sedeDao.getSedePorUuario(objRemitente);

		if (mapUpload != null) {
			log.debug("Trabajando con upload2 - Archivos rechazados");
			lstArchivoTemporal = mapUpload.get("upload2");
			log.debug("Inactivando archivos del Documento con ID [" + iIdDocumento + "]");
			archivoService.updateEstadoByDocumento(iIdDocumento, Constantes.ARCHIVO_ESTADO_DIGITALIZACION_INACTIVO);

			if (lstArchivoTemporal != null) {
				for (ArchivoTemporal objArchivoTemporal : lstArchivoTemporal) {
					Archivo objArchivo = null;
					objArchivo = archivoService.findByCriteria(iIdDocumento, objArchivoTemporal.getFArchivo().getName());
					log.debug("Reactivando archivo [" + objArchivoTemporal.getFArchivo().getName() + "] con ID [" + objArchivo.getIdArchivo() + "]");
					archivoService.updateEstadoByArchivo(objArchivo.getIdArchivo(), Constantes.ARCHIVO_ESTADO_DIGITALIZACION_YES);
				}
			}

			log.debug("Trabajando con upload1 - Nuevos archivos");
			lstArchivoTemporal = mapUpload.get("upload1");

			if (lstArchivoTemporal != null) {
				log.debug("Numero de archivos a mover [" + lstArchivoTemporal.size() + "]");
				// Grabando los archivos temporales a la Base de Datos. Nueva
				// carpeta : IrisPDFIn
				archivoService.saveArhivoFromSessionToDB(lstArchivoTemporal, objDocumento, objRemitente);
			} else {
				log.warn("No hay archivos nuevos");
			}
		}

		objDocumento.setPropietario(usuarioService.findByUsuario(Constantes.USUARIO_QAS, objSede.getIdsede()));
		objDocumento.setRemitente(objRemitente.getNombres() + " " + objRemitente.getApellidos());
		objDocumento.setAccion(objAccion);
		objDocumento.setFechaAccion(new Date());
		objDocumento.setObservacionDigitalizador(observacionDIG);
		this.saveDocumento(objDocumento);
		trazabilidadDocumentoService.saveTrazabilidadDocumento(objDocumento, objRemitente, false, false);
		log.info("Documento registrado desde DIG. Nuevo propietario [" + objDocumento.getPropietario().getNombres() + " " + objDocumento.getPropietario().getApellidos() + "]");
		// Registrar Trazabilidad Expediente
		Usuario objDestinatario = usuarioService.findByUsuario(Constantes.USUARIO_QAS, objSede.getIdsede());

		
	}

	public List<Archivo> getArchivoList(Integer iIdDoc) {
		log.debug("-> [Service] DocumentoService - getArchivoList():List<Archivo> ");

		Map<String, List<Archivo>> archivos = archivoService.findByIdDocumento(iIdDoc);
		if (archivos != null) {
			return archivos.get("upload1");
		}
		return null;
	}

	@Transactional
	public void aplicarPlazosADocumentoPrincipal(DocumentoDetail objDD, Documento objDocumento, Usuarioxunidadxfuncion usuarioDestinatario, String sOpcion, Usuario objUsuario, Date fechaLimiteAtencionAnterior,Boolean horarioPermitido, Boolean horarioPermitidoRecepcion) {
	      log.debug("-> [Service] DocumentoService - aplicarPlazosADocumentoPrincipal():void ");
	 
              Trazabilidaddocumento trazdoc = trazabilidadDocumentoService.findByMaxNroRegistro(objDocumento.getIdDocumento(), "derivar", null, null);
              trazdoc.setFechalimiteatencion(objDocumento.getFechaLimiteAtencion());

	      Date fechaFueraHorario = new Date();
	      Date fechaFueraHorarioRecepcion = new Date();
	      Date fechaValida = new Date();
              
              if(horarioPermitido == false || horarioPermitidoRecepcion ==false){
            	 fechaFueraHorarioRecepcion = fechaLimite.fechaFueraHorarioRecepcionHabil(fechaFueraHorario, usuarioDestinatario.getIdunidad());
		 fechaValida = fechaFueraHorarioRecepcion;
	     }
	    
              objDocumento.setFechaAccion(fechaValida);
              saveDocumento(objDocumento);
	      trazdoc.setFechacreacion(fechaValida);
              trazabilidadDocumentoService.saveTrazabilidadDocumento(trazdoc);
	}

	public String[] obtenerListaFeriados() {
		log.debug("-> [Service] DocumentoService - obtenerListaFeriados():String[] ");

		SimpleDateFormat formatDateHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String[] theStringArray = null;
		List<String> theStrings = new ArrayList<String>();
		List<DiaFestivo> arrFeriadoss = diaFestivoService.ViewAll();
		for (int i = 0; i < arrFeriadoss.size(); i++) {
			DiaFestivo busdiafestivo = arrFeriadoss.get(i);
			theStrings.add(formatDateHHmm.format(busdiafestivo.getFechanolaborable()));
			theStringArray = theStrings.toArray((String[]) Array.newInstance(java.lang.String.class, theStrings.size()));
		}
		return theStringArray;
	}

	

    public int existsDocumentoPendiente(Documento documento){
        List<Documento> listDocumento = null;
        int exist = 0;
       // List<Documento> listDocumentoPendiente = null;
        List<Documento> listDocumentoMultiple = null;
       // List<FilaBandejaAtendido> listAtendido = null;
       
        try{            
            if (documento.getDocumentoreferencia()==null){
               listDocumentoMultiple =  documentoDao.consultaDocumentoReferencia(documento.getIdDocumento());
               if (listDocumentoMultiple==null ||  listDocumentoMultiple.size()==0){
                  Documento d = documentoDao.findByIdDocumento(documento.getIdDocumento());
                  if (d.getEstado() == 'T'){
                	  exist = 0;
                  }else{
                	  exist = 1;
                  }    
               }else{
                  listDocumento = documentoDao.consultaDocumentoMultipleAtendido(documento.getIdDocumento()); 
                  if (listDocumento.size()>0)
                	  exist = listDocumento.size();
                  else
                	  exist = 0;
                  /* jc
                  listDocumentoPendiente = documentoDao.buscarPendientePorDocumentoMultiple(documento);//jc documentoPendienteDAO.buscarPendientePorDocumentoMultiple(documento);
                  if (listDocumentoPendiente==null || listDocumentoPendiente.size()==0){
                      listDocumento = documentoDao.consultaDocumentoMultipleAtendido(documento.getIdDocumento());
                      if (listDocumento.size()>0)
                         return listDocumento.size();
                      else
                         return 0;
                  }else{
                      return listDocumentoPendiente.size();
                  } */   
               }
            }else{
                  listDocumento = documentoDao.consultaDocumentoMultipleAtendido(documento.getDocumentoreferencia());
                  if (listDocumento.size()>0){
                	  exist =  listDocumento.size();
                  }else{
                	  exist = 0;
                  }
                /*jc
                  Documento d = new Documento();
                  d.setIdDocumento(documento.getDocumentoreferencia());
                  listDocumentoPendiente = documentoPendienteDAO.buscarPendientePorDocumentoMultiple(d);
                  if (listDocumentoPendiente==null || listDocumentoPendiente.size()==0){
                      listDocumento = documentoDao.consultaDocumentoMultipleAtendido(documento.getDocumentoreferencia());
                      if (listDocumento.size()>0){
                         return listDocumento.size();
                      }else{
                         return 0;
                      }   
                  }else{
                      return listDocumentoPendiente.size();
                  } */  
            }              
        }catch(Exception e){
            e.printStackTrace();
        	//throw e; 
        }
        
        return exist;
    }
        
    @SuppressWarnings({ "unused", "rawtypes" })
	@Transactional
	@Override
        public void  derivarDocumentoMasivo(Integer[] arrIdDoc, Documento documento, Usuario objUsuario, Usuarioxunidadxfuncion usuarioDestinatario, DocumentoDetail documentoDetail, String nombrePC, Boolean horarioPermitido, Boolean horarioPermitidoRecepcion, String strAcc) throws Exception{
            String[] strAcciones = new String[2];
           
            for (int i = 0; i < arrIdDoc.length; i++) {                
              Documento d = documentoDao.findByIdDocumento(arrIdDoc[i]);
              
              if (!d.getPropietario().getIdusuario().toString().equals(objUsuario.getIdUsuarioPerfil().toString()))
                  continue;
              
              if (d.getDocumentoreferencia() == null){
                  Trazabilidaddocumento t = trazabilidaddocumentoDAO.findByMaxNroRegistroAR(d.getIdDocumento(), null, objUsuario.getIdUsuarioPerfil(), objUsuario.getIdUnidadPerfil(), objUsuario.getIdFuncionPerfil());
                  documentoDetail.setStrAsunto(d.getAsunto());
                  documentoDetail.setPrioridad(t.getPrioridad());
                  documentoDetail.setDateFechaLimiteAtencion(t.getFechalimiteatencion());
                  
                  if (strAcc==null){
                    strAcciones[0] = t.getProveido().getIdProveido()==null?null:t.getProveido().getIdProveido().toString();
                  }else{
                    strAcciones[0] = strAcc;
                  }
                  
                  documentoDetail.setStrAccion(Constantes.ACCION_REENVIAR);
                  derivarDocumento(documentoDetail, objUsuario, usuarioDestinatario, null, null, null,  strAcciones, d , nombrePC, horarioPermitido, horarioPermitidoRecepcion, null);
              }else{
                  Usuarioxunidadxfuncion usuarioxunidadxfuncion  = new Usuarioxunidadxfuncion();
                  usuarioxunidadxfuncion.setIdusuario(objUsuario.getIdUsuarioPerfil());
                  usuarioxunidadxfuncion.setIdunidad(objUsuario.getIdUnidadPerfil());
                  usuarioxunidadxfuncion.setIdfuncion(objUsuario.getIdFuncionPerfil());
                  Trazabilidadapoyo t = trazabilidadapoyoService.buscarUltimaDelegacionUsuarioAR(usuarioxunidadxfuncion, d.getIdDocumento());                
                  documentoDetail.setStrAsunto(d.getAsunto());
                  documentoDetail.setPrioridad(t.getPrioridad());
                  documentoDetail.setDateFechaLimiteAtencion(t.getFechalimiteatencion());
                  strAcciones[0] = "";
                  
                  if (strAcc==null){
                     strAcciones[1] = t.getProveido().getIdProveido()==null?null:t.getProveido().getIdProveido().toString();
                  }else{
                     strAcciones[1] = strAcc;
                  }    
                  documentoDetail.setStrAccion(strAcciones[1]);
                  Proveido p = proveidoDAO.buscarPorId(new Integer(strAcciones[1])); 
                  
                  List<Usuario> usuariosNotificados = new ArrayList<Usuario>();
                  Usuario usuarioNotificado = usuarioService.findByIdUsuario(usuarioDestinatario.getIdusuario());
                  usuarioNotificado.setIdUnidadPerfil(usuarioDestinatario.getIdunidad());
                  usuarioNotificado.setProveido(p.getNombre());
                  usuariosNotificados.add(usuarioNotificado);
                  crearCopiaApoyo(d, documentoDetail, objUsuario, usuarioDestinatario, strAcciones, d.getPrioridad(),  documentoDetail.getStrTexto(),  nombrePC, horarioPermitido,  horarioPermitidoRecepcion, usuariosNotificados, null);
              }
            }
        }
	
        
	@SuppressWarnings({ "unused", "rawtypes" })
        @Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Documento derivarDocumento(DocumentoDetail objDD, Usuario objRemitenteSession, Usuarioxunidadxfuncion objDestino, String sTipoDerivacion, DocumentoDetail objDDD, List<String> conCopia, String[] strAcciones, Documento documento, String nombrePC,Boolean horarioPermitido, Boolean horarioPermitidoRecepcion, Integer codigoVirtual) throws Exception{
	       log.debug("-> [Service] DocumentoService - derivarDocumento():Documento ");
               Accion objAccion = null;
               String asunto = null;
	       String contenido = null;
	       Trazabilidaddocumento trazdoc = null;
	       Proveido p = proveidoDAO.buscarPorId(new Integer(strAcciones[0]));
               
               documento.setDespachado('N');
               documento.setFirmado('N');
	       objAccion = accionService.findByNombre(objDD.getStrAccion());
               
	       if (documento.getContenido() == null) {
	        documento.setContenido(objDD.getStrTexto());
	       }
                
               asunto = objDD.getStrAsunto();
	       contenido = objDD.getStrContenido();
		
               Usuario u = usuarioService.findByIdUsuario(objRemitenteSession.getIdUsuarioPerfil());
               documento.setPropietario(new Usuario(objDestino.getIdusuario()));
               documento.setUnidadpropietario(objDestino.getIdunidad());
               documento.setCargopropietario(objDestino.getIdfuncion());
	       documento.setRemitente(u.getNombres() + " " + u.getApellidos());
	       documento.setAccion(objAccion);	
               documento.setLeido(Constantes.ESTADO_NO_LEIDO);
               documento.setIndAlerta("0");    
               
               Date fechaValida = new Date();
               documento.setFechaAccion(fechaValida); 
               documento.setEstado(Constantes.ESTADO_PENDIENTE);
               
               if ((documento.getID_EXTERNO()!=null && documento.getID_EXTERNO()==0) || !objDestino.getIdunidad().toString().equals(Constantes.UNIDAD_TRAMITE.toString())){
                   if (p.getIdProveido().toString().equals(Constantes.CODIGO_PROVEIDO_FIRMAR)){
                      documento.setFirmado('S');
                   }
               }
               
               documento = this.saveDocumento(documento);
               
               Usuario objUsuario = new Usuario();
               objUsuario.setIdUnidadPerfil(objDestino.getIdunidad());
               objUsuario.setIdFuncionPerfil(objDestino.getIdfuncion());
               objUsuario.setIdusuario(objDestino.getIdusuario());
              
               trazdoc = trazabilidadDocumentoService.saveTrazabilidadDocumento(documento, objRemitenteSession, documento.getPlazo(), 0, objDD.getStrFechaLimiteAtencion(), asunto, contenido, strAcciones, nombrePC,horarioPermitido,objDD.getStrSinPlazo(),horarioPermitidoRecepcion, objDestino, objDD.getPrioridad());
               Documentoenviado documentoenviado = new Documentoenviado();
	       documentoenviado.setIdTrazabilidadEnvio(trazdoc.getIdtrazabilidaddocumento());
	       documentoenviado.setUsuario(new Usuario(objRemitenteSession.getIdUsuarioPerfil()));
               documentoenviado.setUnidadpropietario(objRemitenteSession.getIdUnidadPerfil());
               documentoenviado.setCargopropietario(objRemitenteSession.getIdFuncionPerfil());
	       documentoenviado.setEstado("" + Constantes.ESTADO_ACTIVO);
	       documentoenviado.setTipoEnvio(""+Constantes.TIPO_ENVIO_TRANSFERIR);
               documentoenviado.setUsuariocreacion(objRemitenteSession.getIdusuario());
               documentoEnviadoDao.saveDocumento(documentoenviado);
               
               int iEvento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_REENVIAR;
	       int iCCEvento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_CCREENVIAR;

	       if (objAccion.getNombre().equals(Constantes.ACCION_PARA_APROBAR)) {
			iEvento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_PORAPROBAR;
			iCCEvento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_CCPORAPROBAR;
	       } else if (objAccion.getNombre().equals(Constantes.ACCION_APROBAR)) {
			iEvento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_APROBAR;
			iCCEvento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_CCAPROBAR;
	       }
               
               Set usuariosNotificados = notificacionService.informarViaNotifAndMail(objRemitenteSession, documento, iEvento, Constantes.TIPO_NOTIFICACION_DERIVACION, nombrePC, objDD.getStrContenido(), p.getNombre());
	       if (conCopia != null) {
                   for (String sID : conCopia) {
                    if (!StringUtil.isEmpty(sID)) {
                 	String[] datosCopia = sID.split("-");
			Integer iID = Integer.valueOf(datosCopia[0]);
			Usuario usuarioReceptor = usuarioService.findByIdUsuario(new Integer(datosCopia[0]));
                        usuarioReceptor.setIdUnidadPerfil(new Integer(datosCopia[1]));
                        usuarioReceptor.setIdFuncionPerfil(new Integer(datosCopia[2]));
                        if (!usuariosNotificados.contains(usuarioReceptor) && (objDestino.getIdusuario()).intValue() != iID.intValue()) {
		           notificacionService.enviarNotificacion(objRemitenteSession, usuarioReceptor, documento, Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA, nombrePC,horarioPermitido,null,null);
		           mailService.ChaskiMail(iCCEvento, objRemitenteSession, usuarioReceptor, documento, contenido ,"");
			}else {
                            notificacionService.updateTipoNotificacion(documento.getIdDocumento(), usuarioReceptor.getIdusuario(), Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA);
			}
		     }
		   }
		}
               
                try{
                       boolean bandera = false;
                       String respuesta = null;
                       List<Usuarioxunidadxfuncion> lista = null;
                       Tipodocumento t = tipoDocumentoDao.findByIdTipoDocumento(documento.getTipoDocumento().getIdtipodocumento());
                       
                       if (documento.getID_CLIENTE()!=null && t.getEstadoPIDE()!=null && t.getEstadoPIDE().equals(String.valueOf(Constantes.ESTADO_ACTIVO))){
                            try{
                                lista  = usuarioxunidadxfuncionDAO.getUsuarioByUnidadByFuncionListRol(objUsuario);
                            }catch(Exception e){
                              e.printStackTrace();
                            }   

                            if (lista!=null && lista.size()>0){
                               for (int i=0;i<lista.size();i++){
                                  if (lista.get(i).getIdrol()!=null && lista.get(i).getIdrol().toString().equals(Constantes.COD_ROL_MENSAJERIA.toString())){
                                      bandera = true;
                                      break;
                                  }
                               }

                               if (bandera){
                                     try{
                                         Cliente c = clienteService.findByIdCliente(documento.getID_CLIENTE());
                                         EndPointRUC endPointRUC = new EndPointRUC();
                                         respuesta = endPointRUC.validarEntidad(c.getNumeroIdentificacion(), Constantes.AMBIENTE_WS_PIDE_RUC);
                                     }catch(Exception e){
                                         e.printStackTrace();
                                         respuesta = "-1";
                                         try{
                                             Cliente c = clienteService.findByIdCliente(documento.getID_CLIENTE());
                                             if (c!=null){
                                                 if (c.getFlagPide()!=null && c.getFlagPide().equals("1")){
                                                    respuesta = "0000"; 
                                                 }
                                             }
                                         }catch(Exception ex){
                                             ex.printStackTrace();;
                                             respuesta = "-1";
                                         }
                                     }
                               }else{
                                   bandera = false; 
                               }

                               if (bandera && t!=null && respuesta!=null && respuesta.equals("0000")){
                                 int contador = 0;
                                 IotdtcDespacho iotdtcDespacho = new IotdtcDespacho();
                                 Cliente c = clienteService.findByIdCliente(documento.getID_CLIENTE());
                                 List<Usuario> lst = firmaArchivoDAO.findUltimaFirma(documento.getIdDocumento(), "F");
                                 if (lst!=null && lst.size()>0){
                                     Usuario usuario = usuarioService.findByIdUsuario(lst.get(0).getIdUsuarioPerfil());
                                     iotdtcDespacho.setCtipdociderem(usuario.getTipoDocumento().charAt(0));
                                     iotdtcDespacho.setVnumdociderem(usuario.getNroDocumento());
                                     iotdtcDespacho.setVcoduniorgrem(lst.get(0).getIdUnidadPerfil().toString());
                                     iotdtcDespacho.setVuniorgrem(unidadService.buscarObjPor(lst.get(0).getIdUnidadPerfil()).getNombre());
                                 }else{
                                     throw new Exception();
                                 }    

                                 iotdtcDespacho.setVnumregstd(documento.getID_CODIGO().toString());
                                 iotdtcDespacho.setVanioregstd(documento.getID_CODIGO().toString().substring(0, 4));
                                 iotdtcDespacho.setVrucentrec(c.getNumeroIdentificacion());
                                 iotdtcDespacho.setVnomentrec(c.getRazonSocial());
                                 iotdtcDespacho.setCflgest('P');
                                 iotdtcDespacho.setCflgenv('N');
                                 iotdtcDespacho.setDfecreg(new Date());
                                 iotdtcDespacho.setIddocumento(documento.getIdDocumento());
                                 iotdtcDespacho.setVusureg(objRemitenteSession.getUsuario());
                                 iotdtcDespacho = despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                               
                                 List<Archivo> lstArchivo = archivoService.buscarDocumentosPublicar(documento.getID_CODIGO().toString());
                                 for(int i=0;i<lstArchivo.size();i++){
                                   if (!lstArchivo.get(i).getPrincipal().equals('S')){
                                      contador ++;    
                                   }   
                                 }

                                 IotdtmDocExterno iotdtmDocExterno = new IotdtmDocExterno();
                                 iotdtmDocExterno.setSidemiext(iotdtcDespacho);
                                 iotdtmDocExterno.setVnomentemi(parametroService.findByTipoAndValue("RAZON_SOCIAL_OSITRAN", "20420248645").getDescripcion());
                                 iotdtmDocExterno.setCcodtipdoc(documento.getTipoDocumento().getPide());
                                 iotdtmDocExterno.setVnumdoc(documento.getNumeroDocumento());
                                 iotdtmDocExterno.setDfecdoc(documento.getFechaDocumento());
                                 iotdtmDocExterno.setVuniorgdst(documento.getDesUnidadRemitente()); 
                                 iotdtmDocExterno.setVnomdst(documento.getDesRemitente());
                                 iotdtmDocExterno.setVnomcardst(documento.getDesCargoRemitente());
                                 iotdtmDocExterno.setVasu(documento.getAsunto());
                                 iotdtmDocExterno.setSnumanx(BigInteger.valueOf(contador));
                                 iotdtmDocExterno.setSnumfol(BigInteger.valueOf(documento.getNumeroFolios()));
                                 if (contador>0) iotdtmDocExterno.setVurldocanx(parametroService.findByTipoAndValue("URL_ANEXOS_PIDE", "PIDE").getDescripcion());
                                 iotdtmDocExterno = documentoExternoVirtualDAO.registrarDocumento(iotdtmDocExterno);
                      
                                 AlfrescoApiWs alfrescoApiWs;
                                 Session sesionAlfresco = null;
                                 String alfrescoHostPublico = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_HOST);
                                 String alfrescoHostPort = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PORT);
                                 String alfrescoProtocolo = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PROTOCOLO);
                                 String URL_ALFRESCO = alfrescoProtocolo+"://"+alfrescoHostPublico+":"+alfrescoHostPort+"/alfresco/cmisatom";

                                 String objectId = null;
                                 String nombreDocumento = null;

                                 for(int i=0;i<lstArchivo.size();i++){
                                   if (lstArchivo.get(i).getPrincipal().equals('S')){
                                      objectId = lstArchivo.get(i).getObjectId();
                                      nombreDocumento = lstArchivo.get(i).getNombre();
                                      break;
                                   }         
                                 }

                                 alfrescoApiWs = new AlfrescoApiWs(URL_ALFRESCO, USERCONSULTA, USERCONSULTA_CLAVE, REPOSITORIO_ID);
                                 sesionAlfresco = alfrescoApiWs.getSessionAlfresco();
                                 Document document = (Document)sesionAlfresco.getObject(objectId);
                                 InputStream intput = document.getContentStream().getStream();
                                 byte[] bytes = toByteArray(intput);
                                 IotdtdDocPrincipal iotdtdDocPrincipal = new IotdtdDocPrincipal();
                                 iotdtdDocPrincipal.setSiddocext(iotdtmDocExterno); 
                                 iotdtdDocPrincipal.setSiddocext(iotdtmDocExterno);
                                 iotdtdDocPrincipal.setCcodest('A');
                                 iotdtdDocPrincipal.setVnomdoc(nombreDocumento);
                                 iotdtdDocPrincipal.setBpdfdoc(bytes);   
                                 iotdtdDocPrincipal.setDfecreg(new Date());
                                 iotdtdDocPrincipal = docPrincipalVirtualDAO.registrarPrincipal(iotdtdDocPrincipal);
                                 
                                 if (contador>0){
                                     for(int i=0;i<lstArchivo.size();i++){
                                         if (!lstArchivo.get(i).getPrincipal().equals('S')){
                                            IotdtdAnexo iotdtdAnexo = new IotdtdAnexo();
                                            nombreDocumento = lstArchivo.get(i).getNombre();
                                            iotdtdAnexo.setSiddocext(iotdtmDocExterno);
                                            iotdtdAnexo.setVnomdoc(nombreDocumento);
                                            iotdtdAnexo.setDfecreg(new Date());
                                            iotdtdAnexo = docAnexoVirtualDAO.registrarAnexo(iotdtdAnexo);
                                         }         
                                     }
                                 }    
                               }  
                            }
                       }
                
                       if (codigoVirtual!=null){
                           IotdtmDocExterno iotdtmDocExterno = documentoExternoVirtualDAO.buscarDocumentoVirtual(codigoVirtual);
                           if (iotdtmDocExterno.getSidemiext().getCflgest()=='P'){
                                IotdtcDespacho iotdtcDespacho = iotdtmDocExterno.getSidemiext();
                                iotdtcDespacho.setCflgest('X');
                                iotdtcDespacho.setDfecmod(new Date());
                                despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                           }
                       }
                }catch(Exception e){
                   e.printStackTrace();
                   throw e;
                }
                
                return documento;
	}


	/**
	 * Devuelve los documentos que tienen notificacion amarilla.
	 *
	 * @author German Enriquez
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Documento> obtieneDocumentosNotificacionAmarilla() {
		log.debug("-> [Service] DocumentoService - obtieneDocumentosNotificacionAmarilla():List<Documento> ");

		double notificacionAmarilla = 0.5;
		double notificacionRoja = 0.75;
		log.warn(" obtieneDocumentosNotificacionAmarilla ");
		List<Proceso> listaProcesos = procesoDao.findPorcentajesProcesos();
		List<Documento> todos = documentoDao.getTodos();
		List<Documento> documentos = new ArrayList<Documento>();
		Calendar hoy = Calendar.getInstance();
		for (Documento doc : todos) {
			Date limite = doc.getFechaLimiteAtencion();
			Date accion = doc.getFechaAccion();
			//Integer idProceso = doc.getExpediente().getProceso().getIdproceso();

			/*for (Proceso objProceso : listaProcesos) {
				if (objProceso.getIdproceso().toString().equals(idProceso.toString())) {
					if (objProceso.getPorcentajealertaA() != null && !objProceso.getPorcentajealertaA().equals("")) {
						notificacionAmarilla = Double.parseDouble(objProceso.getPorcentajealertaA());
					}
					if (objProceso.getPorcentajealertaR() != null && !objProceso.getPorcentajealertaR().equals("")) {
						notificacionRoja = Double.parseDouble(objProceso.getPorcentajealertaR());
					}
				}
			}*/
			if (limite != null && accion != null) {
				Calendar fechaLimite = Calendar.getInstance();
				Calendar fechaAccion = Calendar.getInstance();
				fechaLimite.setTime(limite);
				fechaAccion.setTime(accion);
				int atencionAccion = fechaLimite.get(Calendar.DAY_OF_YEAR) - fechaAccion.get(Calendar.DAY_OF_YEAR);
				int atencionHoy = fechaLimite.get(Calendar.DAY_OF_YEAR) - hoy.get(Calendar.DAY_OF_YEAR);
				double index = 1d - (double) atencionHoy / atencionAccion;
				if (notificacionAmarilla < index && index < notificacionRoja) {
					documentos.add(doc);
				}
			}
		}
		return documentos;
	}

	/**
	 * Devuelve los documentos que tienen notificacion roja.
	 *
	 * @author German Enriquez
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Documento> obtieneDocumentosNotificacionRoja() {
		log.debug("-> [Service] DocumentoService - obtieneDocumentosNotificacionRoja():List<Documento> ");

		List<Proceso> listaProcesos = procesoDao.findPorcentajesProcesos();

		double notificacionRoja = 0.75;
		//		Parametro roja=parametroDao.findByTipoUnico(Constantes.PARAMETRO_NOTIFICACION_ROJA);
		//		if(roja!=null){
		//			notificacionRoja=Double.parseDouble(roja.getValor());
		//		}
		//		else{
		//			log.warn("No se encontro el parametro " + Constantes.PARAMETRO_NOTIFICACION_ROJA + ". El valor por defecto es: " + notificacionRoja);
		//		}
		List<Documento> todos = documentoDao.getTodos();
		List<Documento> documentos = new ArrayList<Documento>();
		Calendar hoy = Calendar.getInstance();
		for (Documento doc : todos) {
			Date limite = doc.getFechaLimiteAtencion();
			Date accion = doc.getFechaAccion();
			//Integer idProceso = doc.getExpediente().getProceso().getIdproceso();

			/*for (Proceso objProceso : listaProcesos) {
				if (objProceso.getIdproceso().toString().equals(idProceso.toString())) {
					if (objProceso.getPorcentajealertaR() != null && !objProceso.getPorcentajealertaR().equals("")) {
						notificacionRoja = Double.parseDouble(objProceso.getPorcentajealertaR());
						break;
					}

				}
			}*/

			if (limite != null && accion != null) {
				Calendar fechaLimite = Calendar.getInstance();
				Calendar fechaAccion = Calendar.getInstance();
				fechaLimite.setTime(limite);
				fechaAccion.setTime(accion);
				int atencionAccion = fechaLimite.get(Calendar.DAY_OF_YEAR) - fechaAccion.get(Calendar.DAY_OF_YEAR);
				int atencionHoy = fechaLimite.get(Calendar.DAY_OF_YEAR) - hoy.get(Calendar.DAY_OF_YEAR);
				double index = 1d - (double) atencionHoy / atencionAccion;
				if (index > notificacionRoja) {
					documentos.add(doc);
				}
			}
		}
		return documentos;
	}

	public String diasNoLaborables(String fechaInicio, String fechaFinal) throws RuntimeException {
		log.debug("-> [Service] DocumentoService - diasNoLaborables():String ");

		try {
			return documentoDao.diasNoLaborables(fechaInicio, fechaFinal);
		} catch (RuntimeException re) {
			log.error("", re);
			return null;
		}
	}

	public String calculaFechaLimite(String sDiaLimiteAtencion, String shorasLimiteAtencion) {
		log.debug("-> [Service] DocumentoService - calculaFechaLimite():String ");

		log.debug("dia: " + sDiaLimiteAtencion);
		log.debug("hora: " + shorasLimiteAtencion);
		/*
		 * SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		 * Date trialTime = new Date(); Calendar calendar = new
		 * GregorianCalendar(); calendar.setTime(trialTime);
		 *
		 * if (!sFechaLimiteAtencion.equals("")) {
		 * log.debug("calculo con fecha "); getObjDD().getDoc().setPlazo(0);
		 * getObjDD().setStrFechaLimiteAtencion(sFechaLimiteAtencion); } else {
		 * log.debug("calculo con horas"); int horasxdia = (sDiaLimiteAtencion
		 * 24) + shorasLimiteAtencion; String fechaInicio =
		 * formatoFecha.format(calendar.getTime());
		 * calendar.add(Calendar.HOUR_OF_DAY, horasxdia); String fechaFinal =
		 * formatoFecha.format(calendar.getTime()); String diasNoAbiles =
		 * getSrvD().diasNoLaborables(fechaInicio, fechaFinal);
		 * log.debug("diasNoLaborables== " + diasNoAbiles); if
		 * (!diasNoAbiles.equals("0")) { log.debug("si"); int horasLaborables =
		 * horasxdia + (Integer.parseInt(diasNoAbiles) 24); Calendar
		 * fechaLinAten = new GregorianCalendar();
		 * fechaLinAten.setTime(trialTime);
		 * fechaLinAten.add(Calendar.HOUR_OF_DAY, horasLaborables);
		 * getObjDD().setStrPlazo(horasxdia); log.debug("numero de horas " +
		 * horasxdia);
		 * getObjDD().setStrFechaLimiteAtencion(formatoFecha.format(fechaLinAten
		 * .getTime())); log.debug("fecha de plazo " +
		 * formatoFecha.format(fechaLinAten.getTime()));
		 *
		 * } else { log.debug("no"); log.debug("numro de horas " + horasxdia);
		 * getObjDD().setStrPlazo(horasxdia); log.debug("fecha de plazo " +
		 * formatoFecha.format(calendar.getTime()));
		 * getObjDD().setStrFechaLimiteAtencion
		 * (formatoFecha.format(calendar.getTime()));
		 * log.debug("final de calculo"); } }
		 */
		return shorasLimiteAtencion;
	}

	/**REN Metodo encargado de guardar el documento adjunto y subir los adjuntos a Alfresco ------------------------------*/
	@Transactional
	@Override
	public Documento subirConMetadata(Usuario usuario, Map<String, List<ArchivoTemporal>> mapUpload, Integer idDocumento, Documento objDocumento, DocumentoDetail objDD, String versionar, Integer[] fversionar,String nombrePDFprincipal) {
		log.debug("-> [Service] DocumentoService - subirConMetadata():Documento ");

		Documento ref = null;
		//try{
		log.debug("### dosubirConMetadata");
		// FIXME esta logica pertenece al action
		Documento principal = this.findByIdDocumento(idDocumento);
		List<ArchivoTemporal> lstArchivoTemporal = null;
		log.debug(" ####  fversionar:" + versionar);
		if (mapUpload == null) {
			log.info("No se adjunto ningun archivo");
		} else {
			lstArchivoTemporal = mapUpload.get("upload2");
			log.debug("Archivos adjuntos [" + lstArchivoTemporal.size() + "]");

		if (log.isDebugEnabled()) {
			for (int i = 0; fversionar != null && i < fversionar.length; i++) {
				log.debug(" ####  fversionar:" + "[" + i + "]:" + fversionar[i]);
			}
		}
		List<ArchivoTemporal> newArchivosTemporal = new ArrayList<ArchivoTemporal>();
		if (versionar.equalsIgnoreCase("" + true)) {
			// si es que hay algunarhcivo para versionar
			for (ArchivoTemporal objArchivoTemporal : lstArchivoTemporal) {
				try {
					archivoService.checkInToAlfresco(usuario, objArchivoTemporal, principal, 1, true);
				} catch (Exception e) {
					log.warn(" no se pudo  versionar:" + objArchivoTemporal.getFArchivo().getName());
					newArchivosTemporal.add(objArchivoTemporal);
				}
			}
			lstArchivoTemporal = newArchivosTemporal;
		}
		}

		/*if (lstArchivoTemporal.size() > 0) {*/
			Usuario propietario = usuarioService.findByIdUsuario(principal.getPropietario().getIdusuario());
			Tipodocumento tipodo = objDocumento.getTipoDocumento();
			String fecha = objDD.getStrFechaDocumento();
			Documento objDocumentoTemporal = objDocumento;
			objDocumentoTemporal.setAsunto(objDocumento.getAsunto());
			objDocumentoTemporal.setUltimoAsunto(objDocumento.getAsunto());
			Object obj = ServletActionContext.getRequest().getSession().getAttribute("UsuarioCompartido");
			if (obj != null) {
				Usuario p = (Usuario) obj;
				objDocumentoTemporal.setRemitente(p.getNombres() + " " + p.getApellidos());
			} else {
				objDocumentoTemporal.setRemitente(propietario.getNombres() + " " + propietario.getApellidos());
			}
			objDocumentoTemporal.setNumeroDocumento(objDocumento.getNumeroDocumento());
			objDocumentoTemporal.setNumeroFolios(1);
			objDocumentoTemporal.setTiponumeracion(objDocumento.getTiponumeracion());
			objDocumentoTemporal.setExpediente(principal.getExpediente());
			objDocumentoTemporal.setPrincipal(Constantes.DOCUMENTO_NO_PRINCIPAL);
			objDocumentoTemporal.setPropietario(propietario);
			objDocumentoTemporal.setAccion(accionService.findByNombre(Constantes.ACCION_REGISTRAR));
			objDocumentoTemporal.setCondestinatarios(objDocumento.getCondestinatarios());
			objDocumentoTemporal.setConcopias(objDocumento.getConcopias());
			objDocumentoTemporal.setTiponumeracion(objDocumento.getTiponumeracion());
			objDocumentoTemporal.setEnumerador(usuario);
			objDocumentoTemporal.setFirmado(Constantes.No);
			objDocumentoTemporal.setEnumerado(Constantes.No);
			objDocumentoTemporal.setEnumerarDocumento(objDocumento.getEnumerarDocumento());

			try {
				// Esto porque asi manda la fecha dojo
				objDocumentoTemporal.setFechaDocumento(new SimpleDateFormat("yyyy-MM-dd").parse(fecha));
			} catch (ParseException e) {
				log.warn("No se pudo parsear la fecha " + fecha + " al momento de registrar el documento");
			}
			objDocumentoTemporal.setFechaAccion(new Date());
			objDocumentoTemporal.setFechaCreacion(new Date());
			objDocumentoTemporal.setEstado(Constantes.ESTADO_ACTIVO);
			objDocumentoTemporal.setEstaEnFlujo(Constantes.ESTAENFLUJO_S);
			objDocumentoTemporal.setTipoDocumento(tipodo);

		if(lstArchivoTemporal != null && lstArchivoTemporal.size() > 0){
			objDocumentoTemporal.setNumeroFolios(lstArchivoTemporal.size());
		}else{
			objDocumentoTemporal.setNumeroFolios(1);
		}

			objDocumentoTemporal.setIdUsuarioLogeado(usuario.getIdusuario());
			objDocumentoTemporal.setAutor(propietario);

			Unidad unid = null;// principal.getExpediente().getProceso().getResponsable().getUnidad();
			if (objDocumentoTemporal.getTiponumeracion().equals("A") && objDocumentoTemporal.getEnumerado().equals(Constantes.No) && objDocumento.getEnumerarDocumento()){
				while(unid != null){
					Integer idunidad = propietario.getUnidad().getIdunidad();
					try {
						Numeracion num = numeracionService.findById(idunidad, tipodo.getIdtipodocumento());
						if (StringUtils.isBlank(objDocumentoTemporal.getNumeroDocumento())) {
							objDocumentoTemporal.setNumeroDocumento(num.formatearNumero());
							num.setNumeroactual(num.getNumeroactual() + 1);
							numeracionService.guardarObj(num);
							unid = null;
						}
					} catch (NoResultException nre) {
						//log.warn("No se encontro numeracion para idTipoDocumento [" + idtipodoc + "] idUnidad [" + idunidad + "]");
						unid = unid.getDependencia();
						log.info("Se buscara en la unidad [" + (unid != null ? unid.getNombre() : "null") + "]");
					} catch (Exception ex) {
						unid = null;
						log.error(ex.getMessage(), ex);
					}
				}
			}

			if(objDocumentoTemporal.getFirmante() != null && objDocumentoTemporal.getFirmante().getIdusuario() == null){
				objDocumentoTemporal.setFirmante(null);
			}

			ref = objDocumentoTemporal;
			objDocumentoTemporal = this.saveDocumento(objDocumentoTemporal);
                        ref.setAccion(accionService.findByNombre(Constantes.ACCION_PARA_APROBAR));

			//registrarAuditoriaDocumento(usuario, objDocumentoTemporal, Constantes.TO_AdjuntarMetadata, Constantes.TM_UserFinal, Constantes.TO_Registrar);
			trazabilidadDocumentoService.saveTrazabilidadDocumento(objDocumentoTemporal, propietario, false, true);
			// int nrfolios=0;
		if(lstArchivoTemporal!= null && lstArchivoTemporal.size() > 0){
			int y = 1;
			List<Archivo> archivosSubidos = new ArrayList<Archivo>();
			for (ArchivoTemporal arch : lstArchivoTemporal) {
				log.debug(" hay archivos para upload : " + y);
				Archivo file = null; // archivoService.guardarArchivoTemporal(arch, objDocumentoTemporal, y++, usuario,nombrePDFprincipal);
				//aca recien puedo numerar...
				//registrarAuditoriaArchivos(usuario, objDocumentoTemporal, arch, Constantes.TO_AdjuntarMetadata, Constantes.TM_UserFinal, Constantes.TO_Registrar);
				archivosSubidos.add(file);
			}
			//repositorioService.subirArchivosTransformadosARepositorio(objDocumentoTemporal.getIdDocumento(), false);
			ref.setArchivos(archivosSubidos);
/*Numera documentos en Open Office
			if (objDocumento.getEnumerarDocumento()) {
				log.debug("Numeramos documentos adjuntos");
				if (objDocumentoTemporal != null && !archivosSubidos.isEmpty()) {
					this.numerarDocumentoFisico(usuario, objDocumentoTemporal, Integer.valueOf(Constantes.DO_ENUMERAR));
				}
			}*/
		}
		/*
		} else {
			ref = principal;
		}
		}catch(ParseException e){
        log.error(e.getMessage(),e);
        }*/
		return ref;
	}

	/**REN Metodo encargado de anexar archivos a un documento ya existente ---------------------------------------------------*/
	@Transactional
	@Override
	public Documento anexarDocumento (Usuario usuario, Map<String, List<ArchivoTemporal>> mapUpload, Integer idDocumento) {
		log.debug("-> [Service] DocumentoService - anexarDocumento():Documento ");
                //Unidad unidad = unidadService.buscarObjPor(usuario.getIdUnidadPerfil());
                Unidad unidad = null;
                Documento documento = this.findByIdDocumento(idDocumento);
                
		if(documento.getDocumentoreferencia() != null)
		  documento = this.findByIdDocumento(documento.getDocumentoreferencia());
		
                if(documento.getTipoDocumento().getIdtipodocumento() == Integer.parseInt(parametroService.findByTipoUnico("TIPO_DOCUMENTO_REQUERIMIENTO_TRIBUTARIO").getValor()) ||
                        documento.getTipoDocumento().getIdtipodocumento() == Integer.parseInt(parametroService.findByTipoUnico("TIPO_DOCUMENTO_RESULTADO_TRIBUTARIO").getValor()))
                {
                    unidad = unidadService.buscarObjPor(Integer.parseInt(parametroService.findByTipoUnico("UNIDAD_GSF").getValor()));
                }else{
                    unidad = unidadService.buscarObjPor(usuario.getIdUnidadPerfil());
                }
                
		List<ArchivoTemporal> lstArchivoTemporal = null;

		if (mapUpload == null) {
		   log.info("No se adjunto ningun archivo");
		}else{
		   lstArchivoTemporal = mapUpload.get("upload2");
		   log.debug("Archivos adjuntos [" + lstArchivoTemporal.size() + "]");
		}

		if (lstArchivoTemporal.size() > 0){
                    int y = 1;
                    int x = 1;
                    List<Archivo> archivosSubidos = new ArrayList<Archivo>();
                    String[] nombrePDFPrincipal = new String[250];
                    //String nombrePDFPrincipal="";
                   
                    for (ArchivoTemporal arch : lstArchivoTemporal) {
                        if (arch.getPrincipal()!=null && arch.getPrincipal().equals("P")){
                            nombrePDFPrincipal[x] = arch.getSNombre();
                        }else{
                            nombrePDFPrincipal[x] = "";
                        }
                        x++;
                    }
                      
                    for (ArchivoTemporal arch : lstArchivoTemporal) {
                        Archivo file = archivoService.guardarArchivoTemporal(arch, documento, y, usuario, nombrePDFPrincipal[y], unidad.getRutaSite());
                        archivosSubidos.add(file);
                        y++;
                    }  
                   repositorioService.subirArchivosTransformadosARepositorio(documento, archivosSubidos, false, usuario, unidad.getRutaSite(), documento.getTipoDocumento().getCodigo());
                }
  
		return documento;
	}

	public Integer getDocumentosPrincipalesNoLeidos(Integer iIdUsuario) {
		log.debug("-> [Service] DocumentoService - getDocumentosPrincipalesNoLeidos():Integer ");

		return documentoDao.getDocumentosPrincipalesNoLeidos(iIdUsuario);
	}

	@Transactional
	public Documento updateLeido(Integer iIdDocumento, Usuario remitente, String nombrePC) {
		log.debug("-> [Service] DocumentoService - updateLeido():Documento ");
		Documento documento = this.findByIdDocumento(iIdDocumento);

		if (documento.getLeido().equals('N')){
                   mapSession = ActionContext.getContext().getSession();
		   Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO); 
		   documento.setFechaModificacion(new Date());
                   documento.setUsuarioModificacion(usuario.getIdusuario());
                   documento.setFechaLecturaDocumento(new Date());
		   documento.setNombrePCLecturaDocumento(nombrePC);
		}

		documento.setLeido('S');
		documento = this.saveDocumento(documento);

		return documento;
	}
        
	

	@Transactional
	public void registrarDerivacionAuditoriaDocumento(Documento doc, Usuario remitente, Usuario destinatario, String tipoauditoria, String modulo, String opcion) {
		log.debug("-> [Service] DocumentoService - registrarDerivacionAuditoriaDocumento():void ");

		/*******************************************************/
		// Registrando la auditoria del Expediente
		/*******************************************************/
		/*******************************************************/
		// Registrando la Auditoria del Nro Documento
		/*******************************************************/
		Auditoria ObjAuditoriaND = new Auditoria();
		ObjAuditoriaND.setTipoAuditoria(tipoauditoria);
		ObjAuditoriaND.setModulo(modulo);
		ObjAuditoriaND.setOpcion(opcion);
		ObjAuditoriaND.setFechaAudioria(new Date());
		ObjAuditoriaND.setUsuario(remitente.getUsuario());
		ObjAuditoriaND.setCampo("Propietario");
		ObjAuditoriaND.setAntiguoValor(remitente.getUsuario());
		ObjAuditoriaND.setNuevoValor(destinatario.getUsuario());
		ObjAuditoriaND.setExpediente(doc.getExpediente());
		ObjAuditoriaND.setDocumento(doc);
		auditoriaService.SaveAuditoria(ObjAuditoriaND);
		// auditoriaDao.SaveAuditoria(ObjAuditoriaND);
	}

	@Transactional
	private void registrarAuditoriaArchivos(Usuario usuario, Documento doc, ArchivoTemporal archivo, String tipoauditoria, String modulo, String opcion) {
		log.debug("-> [Service] DocumentoService - registrarAuditoriaArchivos():void ");

		//Registrando la auditoria del Expediente
		/*******************************************************/
		Auditoria ObjAuditoriaArchivo = new Auditoria();
		ObjAuditoriaArchivo.setTipoAuditoria(tipoauditoria);
		ObjAuditoriaArchivo.setModulo(modulo);
		ObjAuditoriaArchivo.setOpcion(opcion);
		ObjAuditoriaArchivo.setFechaAudioria(new Date());
		ObjAuditoriaArchivo.setUsuario(usuario.getUsuario());
		ObjAuditoriaArchivo.setCampo("Archivo");
		ObjAuditoriaArchivo.setNuevoValor(archivo.getSNombre());
		ObjAuditoriaArchivo.setExpediente(doc.getExpediente());
		ObjAuditoriaArchivo.setDocumento(doc);
		auditoriaService.SaveAuditoria(ObjAuditoriaArchivo);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void rechazarDocumento(Usuario remitenteSession, Usuarioxunidadxfuncion objDestinatario, Documento documento, String asunto, String contenido, String accion, String nombrePC, Boolean horarioPermitido, Boolean horarioPermitidoRecepcion, DocumentoDetail documentoDetail, Integer codigoVirtual) throws Exception{
		log.debug("-> [Service] DocumentoService - rechazarDocumento():void ");
                Trazabilidaddocumento trazabilidad = trazabilidadDocumentoService.findByMaxNroRegistro(documento.getIdDocumento(), "", accionService.findByNombre("reenviar").getIdAccion(), remitenteSession.getIdUsuarioPerfil(), remitenteSession.getIdUnidadPerfil(), remitenteSession.getIdFuncionPerfil());
               
                try{
                    if (trazabilidad != null) {
                          Usuario u = usuarioService.findByIdUsuario(remitenteSession.getIdUsuarioPerfil());
                          documento.setPropietario(new Usuario(objDestinatario.getIdusuario()));
                          documento.setUnidadpropietario(objDestinatario.getIdunidad());
                          documento.setCargopropietario(objDestinatario.getIdfuncion());
                          documento.setRemitente(u.getNombres() + " " + u.getApellidos());
                          documento.setUltimoAsunto(asunto);
                          Accion idaccion = accionService.findByNombre(accion);
                          documento.setAccion(idaccion);
                          Date fechaValida = new Date();
                            
                          documento.setFechaAccion(fechaValida);
                          documento.setFirmado('N');
                          documento = documentoDao.saveDocumento(documento);

                          trazabilidad = trazabilidadDocumentoService.guardarTrazabilidad(documento,  remitenteSession, objDestinatario, idaccion, asunto, contenido, nombrePC,horarioPermitido, horarioPermitidoRecepcion, documentoDetail);
                          
                          Usuario usuario = new Usuario();
                          usuario.setIdUsuarioPerfil(objDestinatario.getIdusuario());
                          usuario.setIdUnidadPerfil(objDestinatario.getIdunidad());
                          usuario.setIdFuncionPerfil(objDestinatario.getIdfuncion());

                          int iEvento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_RECHAZAR;

                          notificacionService.informarViaNotifAndMail(remitenteSession, documento, iEvento, Constantes.TIPO_NOTIFICACION_RECHAZO, nombrePC, contenido, null);
                          
                          try{
                              if (codigoVirtual!=null){
                                   IotdtmDocExterno iotdtmDocExterno = documentoExternoVirtualDAO.buscarDocumentoVirtual(codigoVirtual);
                                   if (iotdtmDocExterno.getSidemiext().getCflgest()=='P'){
                                     IotdtcDespacho iotdtcDespacho = iotdtmDocExterno.getSidemiext();
                                     iotdtcDespacho.setCflgest('X');
                                     despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                                   }
                              }
                          }catch(Exception e){
                              e.printStackTrace();
                          }     
                          
                          log.info("Se rechazo el documento " + documento + " satisfactoriamente");
                    } else {
                            log.warn("No se encontro ninguna trazabilidad para el documento " + documento.getNumeroDocumento() + ". No se rechazara el documento");
                    }
                }catch(Exception e){
                    log.error(e.getMessage(), e);
		    throw e;
                }    
	}

	/**
	 *
	 *

	/**
	 * Anula o quita la referencia a un documento para el expediente dado.
	 *
	 * @param documento
	 * 			El documento a anular
	 * @param expediente
	 * 			El expediente que contiene el documento
	 * @param unico
	 * 			Indica si el documento es el unico perteneciente al documento
	 */
	@Transactional
	@Override
	public void anularDocumento(Usuario remitente, Documento documento, Expediente expediente, boolean unico, Usuario usuarioLogeado, String nombrePC, String observacion) {
		log.debug("-> [Service] DocumentoService - anularDocumento():void ");
                        Date fecha = new Date();
			documento.setEstado(Constantes.ESTADO_ANULADO);
			//wcarrasco 24-10-2011 actualiza fechaAccion
			documento.setFechaAccion(fecha);
			documento.setDespachado('N');
			documentoDao.updateDocumento(documento);

			if(documento.getDocumentoreferencia() == null){
                            Usuarioxunidadxfuncion un = new Usuarioxunidadxfuncion();
                            un .setIdusuario(documento.getPropietario().getIdusuario());
                            un.setIdunidad(documento.getUnidadpropietario());
                            un.setIdfuncion(documento.getCargopropietario());
                            trazabilidadDocumentoService.guardarTrazabilidad(documento, remitente, un, Constantes.ACCION_ANULAR, "Anulado el documento : "+documento.getNumeroDocumento(), observacion, nombrePC);
			}else{

				try{

					Trazabilidadapoyo tapoyo = new Trazabilidadapoyo();
					tapoyo.setAccion(accionService.findByNombre(Constantes.ACCION_ANULAR));
					tapoyo.setDestinatario(documento.getPropietario());
                                        tapoyo.setUnidaddestinatario(documento.getUnidadpropietario());
                                        tapoyo.setCargodestinatario(documento.getCargopropietario());
					tapoyo.setRemitente(new Usuario(remitente.getIdUsuarioPerfil()));
                                        tapoyo.setUnidadremitente(remitente.getIdUnidadPerfil());
                                        tapoyo.setCargoremitente(remitente.getIdFuncionPerfil());
					tapoyo.setFechacreacion(new Date());
					tapoyo.setDocumento(documento.getIdDocumento());
					//Trazabilidadapoyo tapoyoorigen =  trazabilidadapoyoService.buscarUltimaDelegacionUsuario(documento.getPropietario().getIdusuario(), documento.getIdDocumento());
                                        Trazabilidadapoyo tapoyoorigen =  trazabilidadapoyoService.buscarUltimaDelegacionUsuario(documento); 
					
                                        if(tapoyoorigen != null && tapoyoorigen.getTrazabilidad() != null){
						Trazabilidaddocumento trazabilidad = trazabilidadDocumentoService.findTrabilidadbyId(tapoyoorigen.getTrazabilidad().getIdtrazabilidaddocumento());
						tapoyo.setTrazabilidad(trazabilidad);
                                                tapoyo.setUsuariocreacion(remitente.getIdusuario());
						tapoyo.setEstado(estadoService.findByCodigo(Constantes.ESTADO_CODIGO_ANULADO));
						tapoyo.setTexto(documento.getObservacion() != null ? documento.getObservacion() : "");
						tapoyo.setAsunto("Anulando documento "+documento.getNumeroDocumento());
						tapoyo.setNombrePC(nombrePC);
						tapoyo.setFechalimiteatencion(tapoyo.getFechacreacion());
						trazabilidadapoyoService.guardar(tapoyo);
					}else{
						log.debug("No se encontro traza de apoyo");
					}
				}catch(Exception e){
					log.debug("No se ha podido guardar la traza del documento delegado");
					e.printStackTrace();
				}

			}
                        
                        /*
                        List<DocumentoPendiente> listDp = documentoPendienteDAO.buscarPendientePorDocumentoSimple(documento);
                        if (listDp!=null){
                           for(int j=0;j<listDp.size();j++){
                             listDp.get(j).setEstado("N");
                             listDp.get(j).setFechamodificacion(fecha);
                             listDp.get(j).setUsuariomodificacion(remitente.getIdusuario());
                             listDp.get(j).setIddocumentorespuesta(null);
                             documentoPendienteDAO.saveDocumento(listDp.get(j));
                           }
                        }*/
                        
                        DocumentoAnulado documentoAnulado = new DocumentoAnulado();
                        documentoAnulado.setEstado("A");
                        documentoAnulado.setIddocumento(documento.getIdDocumento());
                        documentoAnulado.setFechacreacion(fecha);
                        documentoAnulado.setIdusuario(remitente.getIdUsuarioPerfil());
                        documentoAnulado.setUnidadpropietario(remitente.getIdUnidadPerfil());
                        documentoAnulado.setCargopropietario(remitente.getIdFuncionPerfil());
                        documentoAnulado.setUsuariocreacion(remitente.getIdusuario());
                        documentoAnuladoDAO.saveDocumento(documentoAnulado);
	}

	@Transactional
	@Override
	public void reabrirDocumentoAtendido(Usuario remitente, Documento documento, String nombrePC) {
                documento.setEstado(Constantes.ESTADO_PENDIENTE);
		documento.setFechaAccion(new Date());
                documento.setFlagatendido(null);
                documento.setFlagMultiple(null);
		documento = documentoDao.updateDocumento(documento);

		String asunto = "Reapertura del documento "+documento.getNumeroDocumento();
		String contenido = "Documento reabierto"; //+ documento.getPropietario().getNombreCompleto();

                Usuarioxunidadxfuncion un = new Usuarioxunidadxfuncion();
                un.setIdusuario(remitente.getIdUsuarioPerfil());
                un.setIdunidad(remitente.getIdUnidadPerfil());
                un.setIdfuncion(remitente.getIdFuncionPerfil());
                
		if(documento.getDocumentoreferencia() == null){ 
                       trazabilidadDocumentoService.guardarTrazabilidad(documento, remitente, un, Constantes.ACCION_REABRIR, asunto, contenido, nombrePC);
               }else{
			try{
			      Trazabilidadapoyo tapoyo = new Trazabilidadapoyo();
			      tapoyo.setAccion(accionService.findByNombre(Constantes.ACCION_REABRIR));
			      tapoyo.setDestinatario(documento.getPropietario());
                              tapoyo.setUnidadremitente(documento.getUnidadpropietario());
                              tapoyo.setUnidaddestinatario(documento.getUnidadpropietario());
			      tapoyo.setRemitente(documento.getPropietario());
			      tapoyo.setFechacreacion(new Date());
			      tapoyo.setDocumento(documento.getIdDocumento());
                              tapoyo.setCargoremitente(remitente.getIdFuncionPerfil());
                              tapoyo.setCargodestinatario(remitente.getIdFuncionPerfil());
                              
			      Trazabilidadapoyo tapoyoorigen =  trazabilidadapoyoService.buscarUltimaDelegacionUsuario(un, documento.getIdDocumento());
			      if(tapoyoorigen != null && tapoyoorigen.getTrazabilidad() != null){
				 Trazabilidaddocumento trazabilidad = trazabilidadDocumentoService.findTrabilidadbyId(tapoyoorigen.getTrazabilidad().getIdtrazabilidaddocumento());
				 tapoyo.setTrazabilidad(trazabilidad);
                                 tapoyo.setUsuariocreacion(remitente.getIdusuario());
				 tapoyo.setEstado(estadoService.findByCodigo(Constantes.ESTADO_CODIGO_PENDIENTE));
				 tapoyo.setAsunto(asunto);
				 tapoyo.setTexto(contenido);
				 tapoyo.setNombrePC(nombrePC);
				 trazabilidadapoyoService.guardar(tapoyo);
			      }else{
				log.debug("No se encontro traza de apoyo");
			      }
			}catch(Exception e){
				log.debug("No se ha podido guardar la traza del documento delegado");
				e.printStackTrace();
			}
		}
	}

        
        @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void atenderDocumento(Usuario remitente, Documento documento, Expediente expediente, boolean unico, Usuario usuarioLogeado, String nombrePC, String[] listaReferencias, String observacion, Integer codigoVirtual) {
	        log.debug("-> [Service] DocumentoService - atenderDocumento():void ");
                Date fecha = new Date();
                boolean atendidos = false;
                
                try{
                    if(documento.getDocumentoreferencia() == null){
                        Usuarioxunidadxfuncion un = new Usuarioxunidadxfuncion();
                        un.setIdusuario(remitente.getIdUsuarioPerfil());
                        un.setIdunidad(remitente.getIdUnidadPerfil());
                        un.setIdfuncion(remitente.getIdFuncionPerfil());
                        trazabilidadDocumentoService.guardarTrazabilidad(documento, remitente, un, Constantes.ACCION_ATENDER, documento.getAsunto(), observacion, nombrePC);
                    }else{
                            try{
                                Trazabilidadapoyo tapoyo = new Trazabilidadapoyo();
                                tapoyo.setAccion(accionService.findByNombre(Constantes.ACCION_ATENDER));
                                tapoyo.setDestinatario(new Usuario(remitente.getIdUsuarioPerfil()));
                                tapoyo.setCargodestinatario(remitente.getIdFuncionPerfil());
                                tapoyo.setUnidaddestinatario(remitente.getIdUnidadPerfil());
                                tapoyo.setRemitente(new Usuario(remitente.getIdUsuarioPerfil()));
                                tapoyo.setUnidadremitente(remitente.getIdUnidadPerfil());
                                tapoyo.setCargoremitente(remitente.getIdFuncionPerfil());
                                tapoyo.setFechacreacion(new Date());
                                tapoyo.setDocumento(documento.getIdDocumento());
                                Trazabilidadapoyo tapoyoorigen =  trazabilidadapoyoService.buscarUltimaDelegacionUsuario(documento); 

                                if(tapoyoorigen != null && tapoyoorigen.getTrazabilidad() != null){
                                  Trazabilidaddocumento trazabilidad = trazabilidadDocumentoService.findTrabilidadbyId(tapoyoorigen.getTrazabilidad().getIdtrazabilidaddocumento());
                                  tapoyo.setTrazabilidad(trazabilidad);
                                  tapoyo.setUsuariocreacion(remitente.getIdusuario());
                                  tapoyo.setEstado(estadoService.findByCodigo(Constantes.ESTADO_CODIGO_ATENDER));
                                  tapoyo.setTexto(observacion != null ? observacion : "");
                                  tapoyo.setAsunto(documento.getAsunto());
                                  tapoyo.setNombrePC(nombrePC);
                                  tapoyo.setFechalimiteatencion(tapoyo.getFechacreacion());
                                  trazabilidadapoyoService.guardar(tapoyo);
                                }else{
                                  log.debug("No se encontro traza de apoyo");
                                }
                            }catch(Exception e){
                                log.debug("No se ha podido guardar la traza del documento delegado");
                                e.printStackTrace();
                                throw e;
                            }
                    }

                    if (listaReferencias!=null && listaReferencias.length>0){
                        for(int i=0;i<listaReferencias.length;i++){
                           DocumentoReferencia documentoReferencia = documentoReferenciaDAO.getReferenciaDocumento(new Integer(listaReferencias[i]), documento.getIdDocumento());
                           if (documentoReferencia!=null){
                                documentoReferencia.setUsuarioModificacion(remitente.getIdusuario());
                                documentoReferencia.setRespondido("R");
                                documentoReferencia.setFechaModificacion(fecha);
                                documentoReferenciaDAO.updateDocumentoReferencia(documentoReferencia);

                                atendidos = true;
                                DocumentoAtendido documentoAtendido = new DocumentoAtendido();
                                documentoAtendido.setEstado("A");
                                documentoAtendido.setIddocumento(documento.getIdDocumento());//listDp.get(j).getIddocumento());
                                documentoAtendido.setFechacreacion(fecha);
                                documentoAtendido.setUsuariocreacion(remitente.getIdusuario());
                                documentoAtendido.setIdusuario(remitente.getIdUsuarioPerfil());
                                documentoAtendido.setCargopropietario(remitente.getIdFuncionPerfil());
                                documentoAtendido.setUnidadpropietario(remitente.getIdUnidadPerfil());
                                documentoAtendido.setIddocumentorespuesta(new Integer(listaReferencias[i]));
                                documentoAtendidoDAO.saveDocumento(documentoAtendido);
                            }
                        }
                    }else{
                                atendidos = true; 
                                DocumentoAtendido documentoAtendido = new DocumentoAtendido();
                                documentoAtendido.setEstado("A");
                                documentoAtendido.setIddocumento(documento.getIdDocumento());//listDp.get(j).getIddocumento());
                                documentoAtendido.setFechacreacion(fecha);
                                documentoAtendido.setUsuariocreacion(remitente.getIdusuario());
                                documentoAtendido.setIdusuario(remitente.getIdUsuarioPerfil());
                                documentoAtendido.setCargopropietario(remitente.getIdFuncionPerfil());
                                documentoAtendido.setUnidadpropietario(remitente.getIdUnidadPerfil());
                                documentoAtendidoDAO.saveDocumento(documentoAtendido); 
                    }

                    try{
                        if (codigoVirtual!=null){
                           IotdtmDocExterno iotdtmDocExterno = documentoExternoVirtualDAO.buscarDocumentoVirtual(codigoVirtual);
                           if (iotdtmDocExterno.getSidemiext().getCflgest()=='P'){
                                IotdtcDespacho iotdtcDespacho = iotdtmDocExterno.getSidemiext();
                                if (iotdtcDespacho!=null){
                                    iotdtcDespacho.setCflgest('X');
                                    despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                                }    
                           }     
                         }
                    }catch(Exception e){
                        e.printStackTrace();
                    }        

                    for(int z=0;z<2;z++){
                       try{ 
                            if (existsDocumentoPendiente(documento)==0){
                               if (documento.getDocumentoreferencia()==null){
                                 List<Documento>  listDocumentoMultiple =  documentoDao.consultaDocumentoReferencia(documento.getIdDocumento());
                                 if (listDocumentoMultiple==null ||  listDocumentoMultiple.size()==0){
                                    documento.setFlagatendido("1");  
                                    documento.setEstado(Constantes.ESTADO_ATENDER); //AQUI SI 1
                                    documento.setUsuarioModificacion(remitente.getIdusuario());
                                    documento.setFechaModificacion(fecha);
                                    documentoDao.updateDocumento(documento);

                                    if (!atendidos){
                                        DocumentoAtendido documentoAtendido = new DocumentoAtendido();
                                        documentoAtendido.setEstado("A");
                                        documentoAtendido.setIddocumento(documento.getIdDocumento());
                                        documentoAtendido.setFechacreacion(fecha);
                                        documentoAtendido.setUsuariocreacion(remitente.getIdusuario());
                                        documentoAtendido.setIdusuario(remitente.getIdUsuarioPerfil());
                                        documentoAtendido.setCargopropietario(remitente.getIdFuncionPerfil());
                                        documentoAtendido.setUnidadpropietario(remitente.getIdUnidadPerfil());
                                        documentoAtendidoDAO.saveDocumento(documentoAtendido);
                                    }    
                                 }else{
                                    documento.setFlagatendido("1");  
                                    documento.setEstado(Constantes.ESTADO_ATENDER); //AQUI SI 1
                                    documento.setUsuarioModificacion(remitente.getIdusuario());
                                    documento.setFechaModificacion(fecha);
                                    documentoDao.updateDocumento(documento); 

                                    if (!atendidos){
                                        DocumentoAtendido documentoAtendido = new DocumentoAtendido();
                                        documentoAtendido.setEstado("A");
                                        documentoAtendido.setIddocumento(documento.getIdDocumento());
                                        documentoAtendido.setFechacreacion(fecha);
                                        documentoAtendido.setUsuariocreacion(remitente.getIdusuario());
                                        documentoAtendido.setIdusuario(remitente.getIdUsuarioPerfil());
                                        documentoAtendido.setCargopropietario(remitente.getIdFuncionPerfil());
                                        documentoAtendido.setUnidadpropietario(remitente.getIdUnidadPerfil());
                                        documentoAtendidoDAO.saveDocumento(documentoAtendido);
                                    }

                                    for(int i=0;i<listDocumentoMultiple.size();i++){
                                        listDocumentoMultiple.get(i).setFlagatendido("1");  
                                        listDocumentoMultiple.get(i).setEstado(Constantes.ESTADO_ATENDER);
                                        listDocumentoMultiple.get(i).setFechaModificacion(fecha);
                                        listDocumentoMultiple.get(i).setUsuarioModificacion(remitente.getIdusuario());
                                        documentoDao.updateDocumento(listDocumentoMultiple.get(i));
                                        //documentoDao.updateDocumento(documento);
                                    } 
                                 }
                               }else{
                                  List<Documento>  listDocumentoMultiple =  documentoDao.consultaDocumentoReferencia(documento.getDocumentoreferencia()); 
                                  Documento d = documentoDao.findByIdDocumento(documento.getDocumentoreferencia());
                                  d.setFlagatendido("1");  
                                  d.setEstado(Constantes.ESTADO_ATENDER); //AQUI SI 1
                                  documentoDao.updateDocumento(d);
                                  if (!atendidos){
                                    DocumentoAtendido documentoAtendido = new DocumentoAtendido();
                                    documentoAtendido.setEstado("A");
                                    documentoAtendido.setIddocumento(documento.getIdDocumento());
                                    documentoAtendido.setFechacreacion(fecha);
                                    documentoAtendido.setUsuariocreacion(remitente.getIdusuario());
                                    documentoAtendido.setIdusuario(remitente.getIdUsuarioPerfil());
                                    documentoAtendido.setCargopropietario(remitente.getIdFuncionPerfil());
                                    documentoAtendido.setUnidadpropietario(remitente.getIdUnidadPerfil());
                                    documentoAtendidoDAO.saveDocumento(documentoAtendido);
                                  }

                                  for(int i=0;i<listDocumentoMultiple.size();i++){
                                        listDocumentoMultiple.get(i).setFlagatendido("1");  
                                        listDocumentoMultiple.get(i).setFechaModificacion(fecha);
                                        listDocumentoMultiple.get(i).setUsuarioModificacion(remitente.getIdusuario());
                                        listDocumentoMultiple.get(i).setEstado(Constantes.ESTADO_ATENDER);
                                        documentoDao.updateDocumento(listDocumentoMultiple.get(i));
                                    }
                                }  

                                break;
                            }else{
                                if (z==1) break;
                                //QUIEN LO TIENE EN SU BANDEJA PARA QUE DESAPAREZACA DE SUS RECIBIDOS.
                                if (documento.getPropietario().getIdusuario().toString().equals(remitente.getIdUsuarioPerfil().toString()) &&
                                    documento.getUnidadpropietario().toString().equals(remitente.getIdUnidadPerfil().toString())){
                                    documento.setFlagatendido("1");
                                    documento.setEstado(Constantes.ESTADO_ATENDER);
                                    documento.setFechaModificacion(fecha);
                                    documento.setUsuarioModificacion(remitente.getIdusuario());
                                    documentoDao.updateDocumento(documento);
                                    if (!atendidos){ 
                                        atendidos = true;
                                        DocumentoAtendido documentoAtendido = new DocumentoAtendido();
                                        documentoAtendido.setEstado("A");
                                        documentoAtendido.setIddocumento(documento.getIdDocumento());
                                        documentoAtendido.setFechacreacion(fecha);
                                        documentoAtendido.setUsuariocreacion(remitente.getIdusuario());
                                        documentoAtendido.setIdusuario(remitente.getIdUsuarioPerfil());
                                        documentoAtendido.setCargopropietario(remitente.getIdFuncionPerfil());
                                        documentoAtendido.setUnidadpropietario(remitente.getIdUnidadPerfil());
                                        documentoAtendidoDAO.saveDocumento(documentoAtendido);
                                    }
                                }else{
                                       Usuario u = usuarioService.findByIdUsuario(remitente.getIdUsuarioPerfil());
                                       if (u.getUsuario().equals(Constantes.USUARIO_MENSAJERIA_VIRTUAL)){
                                           documento.setFlagatendido("1");
                                           documento.setEstado(Constantes.ESTADO_ATENDER);
                                           documento.setFechaModificacion(fecha);
                                           documento.setUsuarioModificacion(remitente.getIdusuario());
                                           documentoDao.updateDocumento(documento);
                                       }
                                }  
                            }
                       }catch(Exception e){
                           throw e;
                       }   
                   }
                }catch(Exception e){
                    e.printStackTrace();
                	//throw e;
                }    
	}

	@Override
	public List<Documento> busquedaDocumentoSimple(String asunto, Usuario usuario) {
		log.debug("-> [Service] DocumentoService - busquedaDocumentoSimple():List<Documento> ");

		if (asunto != null) {
			try{
				asunto = "%"+asunto.toLowerCase()+"%";
				List<Documento> documentos = documentoDao.busquedaRapidaDocumento(asunto);
				List<Documento> salida = new ArrayList<Documento>();

				Unidad funcional = usuario.getUnidad();
				while(funcional.getDependencia() != null){
					funcional = funcional.getDependencia();
				}

				for(Documento doc : documentos){
					Unidad funcionalDoc = doc.getAutor().getUnidad();
					while(funcionalDoc.getDependencia() != null){
						funcionalDoc = funcionalDoc.getDependencia();
					}

					if(funcional.getIdunidad().intValue() == funcionalDoc.getIdunidad().intValue()){
						salida.add(doc);
					}

				}

				return salida;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<DocumentoAreaFuncional> busquedaDocumentoAreaFuncionalSimple(String campo) {
		log.debug("-> [Service] DocumentoService - busquedaDocumentoAreaFuncionalSimple():List<DocumentoAreaFuncional> ");

		if (campo != null) {
			campo = campo.toLowerCase();
			String asuntoDocumento = "%"+campo+"%";
			String asuntoExpediente = "%"+campo+"%";
			Date fechaDesde = null;
			Date fechaHasta = null;
			Integer IdAreaFuncional;
			SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");

			mapSession = ActionContext.getContext().getSession();
			Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
			usuario = usuarioService.findByIdUsuario(usuario.getIdusuario());
			Integer idAreaUsuario = usuario.getUnidad().getIdunidad();

			Unidad unidad = new Unidad();
			unidad= unidadService.buscarObjPor(Integer.parseInt(idAreaUsuario.toString().trim()));
			if(unidad.getDependencia() != null){
				IdAreaFuncional= unidad.getDependencia().getIdunidad();
			}else{
				IdAreaFuncional=null;
			}

			try {
				List<Parametro> resultadoFechaBusquedaRapida  = parametroService.findAll();
				for(Parametro parametro: resultadoFechaBusquedaRapida ){
					if(parametro.getTipo().equals(Constantes.PARAMETRO_TIPO_FECHA_DESDE_BUSQUEDA_RAPIDA)){
						fechaDesde= fechita.parse(parametro.getValor().toString().trim());
					}
					if(parametro.getTipo().equals(Constantes.PARAMETRO_TIPO_FECHA_HASTA_BUSQUEDA_RAPIDA)){
						fechaHasta= fechita.parse(parametro.getValor().toString().trim());
						fechaHasta.setHours(24);
						fechaHasta.setMinutes(0);
						fechaHasta.setSeconds(0);
					}
				}
				log.debug("busquedaDocumentoAreaFuncionalSimple(Campo)  "+ "Fecha Desde: "+ fechaDesde +"Fecha Hasta: " +fechaHasta);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			log.debug("busquedaDocumentoAreaFuncionalSimple(Campo)  "+ "asuntoDocumento "+ asuntoDocumento +"asuntoExpediente: " +asuntoExpediente+"idAreaUsuario: "+idAreaUsuario+"IdAreaFuncional: "+IdAreaFuncional+"");
			return documentoDao.busquedaDocumento(asuntoDocumento, asuntoExpediente, fechaDesde, fechaHasta,idAreaUsuario,IdAreaFuncional,"OR");
			}
		return null;
	}

	@Deprecated
	/** usar el metodo sobrecargado*/
	@Override
	public List<Documento> busquedaDocumentoAvanzada(boolean alfresco, String contenido, String numeroDocumento, String numeroMesaPartes, String asuntoDocumento, String numeroExpediente, String asuntoExpediente, String estadoExpediente, String concesionario, String cliente, String proceso, String propietario, String areaDestino, String tipoDocumento, String fechaDocumentoDesde, String fechaDocuentoHasta, String fechaExpedienteDesde, String fechaExpedienteHasta, String operador) {
		log.debug("-> [Service] DocumentoService - busquedaDocumentoAvanzada():List<Documento> ");

		// para buscar por contenido, es necesario haber iniciado sesion en alfresco.
		// alfresco devolvera los id de documento que contienen el texto buscado,
		// luego habra que incorporar esos documentos a la busqueda normal

		List<Integer> idDocumentos = null;
		if (contenido != null && !contenido.equals("")) {
			if (alfresco) {
				idDocumentos = repositorioService.busquedaIdDocumentos(contenido.toLowerCase(), null);
			}
		}
		if (numeroDocumento != null && numeroDocumento.equals("")) {
			numeroDocumento = null;
		}
		if (numeroMesaPartes != null && numeroMesaPartes.equals("")) {
			numeroMesaPartes = null;
		}
		if (asuntoDocumento != null && asuntoDocumento.equals("")) {
			asuntoDocumento = null;
		}
		if (numeroExpediente != null && numeroExpediente.equals("")) {
			numeroExpediente = null;
		}
		if (asuntoExpediente != null && asuntoExpediente.equals("")) {
			asuntoExpediente = null;
		}
		if (estadoExpediente != null && estadoExpediente.equals("")) {
			estadoExpediente = null;
		}
		if (concesionario != null && concesionario.equals("")) {
			concesionario = null;
		}
		if (cliente != null && cliente.equals("")) {
			cliente = null;
		}
		if (proceso != null && proceso.equals("")) {
			proceso = null;
		}
		if (propietario != null && propietario.equals("")) {
			propietario = null;
		}
		if (areaDestino != null && areaDestino.equals("")) {
			areaDestino = null;
		}
		if (tipoDocumento != null && tipoDocumento.equals("")) {
			tipoDocumento = null;
		}
		if (fechaDocumentoDesde != null && fechaDocumentoDesde.equals("")) {
			fechaDocumentoDesde = null;
		}
		if (fechaDocuentoHasta != null && fechaDocuentoHasta.equals("")) {
			fechaDocuentoHasta = null;
		}
		if (fechaExpedienteDesde != null && fechaExpedienteDesde.equals("")) {
			fechaExpedienteDesde = null;
		}
		if (fechaExpedienteHasta != null && fechaExpedienteHasta.equals("")) {
			fechaExpedienteHasta = null;
		}

		if (numeroDocumento != null) {
			numeroDocumento = "%" + numeroDocumento.toLowerCase() + "%";
		}
		if (numeroMesaPartes != null) {
			numeroMesaPartes = "%" + numeroMesaPartes.toLowerCase() + "%";
			;
		}
		if (asuntoDocumento != null) {
			asuntoDocumento = "%" + asuntoDocumento.toLowerCase() + "%";
			;
		}
		if (numeroExpediente != null) {
			numeroExpediente = "%" + numeroExpediente.toLowerCase() + "%";
			;
		}
		if (asuntoExpediente != null) {
			asuntoExpediente = "%" + asuntoExpediente.toLowerCase() + "%";
			;
		}
		if (estadoExpediente != null) {
			estadoExpediente = "%" + estadoExpediente.toLowerCase() + "%";
			;
		}
		if (concesionario != null) {
			concesionario = "%" + concesionario.toLowerCase() + "%";
			;
		}
		if (cliente != null) {
			cliente = "%" + cliente.toLowerCase() + "%";
			;
		}
		if (proceso != null) {
			proceso = "%" + proceso.toLowerCase() + "%";
			;
		}
		if (propietario != null) {
			propietario = "%" + propietario.toLowerCase() + "%";
			;
		}
		if (areaDestino != null) {
			areaDestino = "%" + areaDestino.toLowerCase() + "%";
			;
		}
		if (tipoDocumento != null) {
			tipoDocumento = "%" + tipoDocumento.toLowerCase() + "%";
			;
		}
		SimpleDateFormat formato = Constantes.FORAMATEADOR_FECHA;
		Date documentoDesde = null;
		Date documentoHasta = null;
		Date expedienteDesde = null;
		Date expedienteHasta = null;
		if (fechaDocumentoDesde != null) {
			try {
				documentoDesde = formato.parse(fechaDocumentoDesde);
			} catch (ParseException e) {
			}
		}
		if (fechaDocuentoHasta != null) {
			try {
				documentoHasta = formato.parse(fechaDocuentoHasta);
			} catch (ParseException e) {
			}
		}
		if (fechaExpedienteDesde != null) {
			try {
				expedienteDesde = formato.parse(fechaExpedienteDesde);
			} catch (ParseException e) {
			}
		}
		if (fechaExpedienteHasta != null) {
			try {
				expedienteHasta = formato.parse(fechaExpedienteHasta);
			} catch (ParseException e) {
			}
		}
		return documentoDao.busquedaDocumento(idDocumentos, numeroDocumento, numeroMesaPartes, asuntoDocumento, numeroExpediente, asuntoExpediente, estadoExpediente, concesionario, cliente, proceso, propietario, areaDestino, tipoDocumento, documentoDesde, documentoHasta, expedienteDesde, expedienteHasta, operador);
	}

	@SuppressWarnings("deprecation")
	public List<Documento> busquedaDocumentoAvanzada(String contenido,
			String numeroDocumento, String numeroMesaPartes, String asuntoDocumento,
			String numeroExpediente, String asuntoExpediente, String estadoExpediente,
			String concesionario, String cliente, String proceso, String propietario,
			String areaDestino, String tipoDocumento, String fechaDocumentoDesde,
			String fechaDocuentoHasta, String fechaExpedienteDesde, String fechaExpedienteHasta,
			String nroSuministro, String operador, String areaOrigen, String areaAutor, String sqlQueryDinamico[], String nroHT, String nroRS, String sNroLegajo, String tipoLegajo, String tipoConsulta, String unidadUsuario, String cargoUsuario, String autor) {

		log.debug("-> [Service] DocumentoService - busquedaDocumentoAvanzada():List<Documento> ");

		// para buscar por contenido, es necesario haber iniciado sesion en alfresco.
		// alfresco devolvera los id de documento que contienen el texto buscado,
		// luego habra que incorporar esos documentos a la busqueda normal

		List<Integer> idDocumentos = null;
		if (contenido != null && !contenido.equals("")) {
			idDocumentos = repositorioService.busquedaIdDocumentos(contenido.toLowerCase(), null);
		}
		if (numeroDocumento != null && numeroDocumento.equals("")) {
			numeroDocumento = null;
		}
		if (numeroMesaPartes != null && numeroMesaPartes.equals("")) {
			numeroMesaPartes = null;
		}
		if (asuntoDocumento != null && asuntoDocumento.equals("")) {
			asuntoDocumento = null;
		}
		if (numeroExpediente != null && numeroExpediente.equals("")) {
			numeroExpediente = null;
		}
                if (sNroLegajo != null && sNroLegajo.equals("")) {
			sNroLegajo = null;
		}
                if (tipoLegajo != null && tipoLegajo.equals("")) {
			tipoLegajo = null;
		}
		if (asuntoExpediente != null && asuntoExpediente.equals("")) {
			asuntoExpediente = null;
		}
		if (estadoExpediente != null && (estadoExpediente.equals("")) || estadoExpediente.equals("%")) {
			estadoExpediente = null;
		}
		if (concesionario != null && concesionario.equals("")) {
			concesionario = null;
		}
		if (cliente != null && cliente.equals("")) {
			cliente = null;
		}
		if (proceso != null && proceso.equals("")) {
			proceso = null;
		}
		if (propietario != null && propietario.equals("")) {
			propietario = null;
		}
                if(autor != null && autor.equals("")){
                    autor = null;
                }
		if (areaDestino != null && areaDestino.equals("")) {
			areaDestino = null;
		}
		if (areaOrigen != null && areaOrigen.equals("")) {
			areaOrigen = null;
		}
		if (areaAutor != null && areaAutor.equals("")) {
			areaAutor = null;
		}
		if (tipoDocumento != null && tipoDocumento.equals("")) {
			tipoDocumento = null;
		}
		if (nroSuministro != null && nroSuministro.equals("")) {
			nroSuministro = null;
		}

		if (fechaDocumentoDesde != null && fechaDocumentoDesde.equals("")) {
			fechaDocumentoDesde = null;
		}
		if (fechaDocuentoHasta != null && fechaDocuentoHasta.equals("")) {
			fechaDocuentoHasta = null;
		}
		if (fechaExpedienteDesde != null && fechaExpedienteDesde.equals("")) {
			fechaExpedienteDesde = null;
		}
		if (fechaExpedienteHasta != null && fechaExpedienteHasta.equals("")) {
			fechaExpedienteHasta = null;
		}

		//formar para el like
		if (numeroDocumento != null) {
			numeroDocumento = "%" + numeroDocumento.toUpperCase() + "%";
		}
		if (numeroMesaPartes != null) {
			numeroMesaPartes = "%" + numeroMesaPartes.toUpperCase() + "%";
		}
                
                if (nroHT != null && nroHT.trim().equals("")) {
		   nroHT = null;
		}
                
                if (nroRS != null && nroRS.trim().equals("")) {
		   nroRS = null;
		}
                
		if (asuntoDocumento != null) {
			asuntoDocumento = "%" + asuntoDocumento.toUpperCase() + "%";
		}
		if (numeroExpediente != null) {
			numeroExpediente = "%" + numeroExpediente.toUpperCase() + "%";
		}
		if (asuntoExpediente != null) {
			asuntoExpediente = "%" + asuntoExpediente.toUpperCase() + "%";
		}
		if (estadoExpediente != null) {
			estadoExpediente = estadoExpediente.toUpperCase();
		}
		if (concesionario != null) {
			concesionario = "%" + concesionario.toUpperCase() + "%";
		}
		if (cliente != null) {
			cliente = "%" + cliente.toUpperCase() + "%";
		}
		//        if (proceso != null) {
		//            proceso = "%" + proceso.toUpperCase() + "%";
		//        }
		/*if (propietario != null) {
			propietario = "%" + propietario.toUpperCase() + "%";
		}*/
		
		if (nroSuministro != null) {
			nroSuministro = "%" + nroSuministro.toUpperCase() + "%";
		}
		SimpleDateFormat formato = Constantes.FORAMATEADOR_FECHA;
		Date documentoDesde = null;
		Date documentoHasta = null;
		Date expedienteDesde = null;
		Date expedienteHasta = null;
		if (fechaDocumentoDesde != null) {
			try {
				documentoDesde = formato.parse(fechaDocumentoDesde);
				documentoDesde.setHours(0);
				documentoDesde.setMinutes(0);
				documentoDesde.setSeconds(0);
			} catch (ParseException e) {
			}
		}
		if (fechaDocuentoHasta != null) {
			try {
				documentoHasta = formato.parse(fechaDocuentoHasta);
				documentoHasta.setHours(23);
				documentoHasta.setMinutes(59);
				documentoHasta.setSeconds(59);
			} catch (ParseException e) {
			}
		}
		if (fechaExpedienteDesde != null) {
			try {
				expedienteDesde = formato.parse(fechaExpedienteDesde);
				expedienteDesde.setHours(0);
				expedienteDesde.setMinutes(0);
				expedienteDesde.setSeconds(0);
			} catch (ParseException e) {
			}
		}
		if (fechaExpedienteHasta != null) {
			try {
				expedienteHasta = formato.parse(fechaExpedienteHasta);
				expedienteHasta.setHours(23);
				expedienteHasta.setMinutes(59);
				expedienteHasta.setSeconds(59); 
			} catch (ParseException e) {
			}
		}

		return documentoDao.busquedaDocumento(idDocumentos, numeroDocumento,
				numeroMesaPartes, asuntoDocumento, numeroExpediente, asuntoExpediente,
				estadoExpediente, concesionario, cliente, proceso, propietario,
				areaDestino, tipoDocumento, documentoDesde, documentoHasta,
				expedienteDesde, expedienteHasta, nroSuministro, operador, areaOrigen, areaAutor,sqlQueryDinamico, nroHT, nroRS, sNroLegajo, tipoLegajo, tipoConsulta, unidadUsuario, cargoUsuario, autor);
	}

	@Override
	public List<Documento> getDocumentosPorExpediente(int idExpediente) {
		log.debug("-> [Service] DocumentoService - getDocumentosPorExpediente():List<Documento> ");
                
		return documentoDao.getDocumentosPorExpediente(idExpediente);
	}
        
        @Override
	public List<DocumentoPublicar> getDocumentosPorPublicar(Integer idExpediente, Integer idDocumento) {
		log.debug("-> [Service] DocumentoService - getDocumentosPorPublicar():List<Documento> ");

                return documentoDao.getDocumentosPorPublicar(idExpediente, idDocumento);
	}

	public List<Documento> getDocumentosNoConfidencialesPorExpediente(int idExpediente){
		log.debug("-> [Service] DocumentoService - getDocumentosNoConfidencialesPorExpediente():List<Documento> ");

		return documentoDao.getDocumentosNoConfidencialesPorExpediente(idExpediente);
	}

	/**
	 * Cambia la referencia para un documento de un expediente a otro
	 *
	 * @author German Enriquez
	 * @throws Exception
	 */
	@Transactional
	@Override
	public void cambiarReferencia(Documento documento, Expediente expediente, Expediente expedienteNuevo, boolean unico,Usuario usuario, String nombrePC) throws RuntimeException {
		log.debug("-> [Service] DocumentoService - cambiarReferencia():void ");

                mapSession = ActionContext.getContext().getSession();
                Usuario objUsuarioSession = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                Date fecha = new Date();
                List<Documento> lst = null;
                
                /*
                if (documento.getDocumentoreferencia()==null){
                   lst = documentoDao.consultaDocumentoReferenciaMover(documento.getIdDocumento());
                   documento.setUsuarioModificacion(objUsuarioSession.getIdusuario());
                   documento.setFechaModificacion(fecha);
		   documento.setExpediente(expedienteNuevo);
		   documentoDao.updateDocumento(documento);
                  
                   if (lst!=null && lst.size()>0){
                      for(int i=0;i<lst.size();i++){
                         lst.get(i).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                         lst.get(i).setFechaModificacion(fecha);
                         lst.get(i).setExpediente(expedienteNuevo);
                         documentoDao.updateDocumento(lst.get(i));
                      }
                   }
                }else{
                  lst = documentoDao.consultaDocumentoReferenciaMover(documento.getDocumentoreferencia());
                  Documento d = documentoDao.findByIdDocumento(documento.getDocumentoreferencia());
                  d.setUsuarioModificacion(objUsuarioSession.getIdusuario());
                  d.setFechaModificacion(fecha);
		  d.setExpediente(expedienteNuevo);
		  documentoDao.updateDocumento(d);
                  
                  if (lst!=null && lst.size()>0){
                      for(int i=0;i<lst.size();i++){
                         lst.get(i).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                         lst.get(i).setFechaModificacion(fecha);
                         lst.get(i).setExpediente(expedienteNuevo);
                         documentoDao.updateDocumento(lst.get(i));
                      }
                  }
                }*/
                
                lst = documentoDao.consultaDocumentoOrigen(documento.getOrigen());
                if (lst!=null){
                    for(int i=0;i<lst.size();i++){
                       lst.get(i).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                       lst.get(i).setFechaModificacion(fecha);
                       lst.get(i).setExpediente(expedienteNuevo);
                       documentoDao.updateDocumento(lst.get(i));
                    }
                }
                
                    
		//si es el unico documento del expediente, anulamos el expediente
                lst = documentoDao.findByIdExpediente(expediente.getIdexpediente());
		if (lst==null || lst.size()==0) {
                   expediente.setFechamodificacion(new Date());
                   expediente.setUsuariomodificacion(objUsuarioSession.getIdusuario());
		   expediente.setEstado(Constantes.ESTADO_ANULADO);
		   expedienteService.saveExpediente(expediente);
		}

                /*
		TrazabilidadMovimiento tm = new TrazabilidadMovimiento();
		tm.setAccion(35);
		tm.setDocumento(documento.getIdDocumento());
		tm.setIdexpdestino(expedienteNuevo.getIdexpediente());
		tm.setIdexporigen(expediente.getIdexpediente());
		tm.setNombrepc(nombrePC);
		tm.setIdusuario(objUsuarioSession.getIdusuario());
		tm.setFechacreacion(fecha);
		trazabilidadMovimientoDAO.saveObject(tm);*/
	}

	/**
	 * Agrega la referencia de un documento a otro expediente
	 *
	 *  @author Jenny
	 * @throws Exception
	 */
	@Transactional
	@Override
	public void copiarReferencia(Documento documento, Expediente expediente, Expediente expedienteNuevo, boolean unico,Usuario usuario, String nombrePC) throws RuntimeException {

		log.debug("-> [Service] DocumentoService - copiarReferencia():void ");

		//Expediente expedienteNuevo2 = expedienteService.findByIdExpediente(expedienteNuevo.getIdexpediente());

		/*Creacion de la nueva Referencia para el documento*/
		DocumentoxexpedientePK documentoxexpedientePK = new DocumentoxexpedientePK(documento.getIdDocumento(), expedienteNuevo.getIdexpediente());
		Documentoxexpediente referenciaNueva = new Documentoxexpediente(documentoxexpedientePK, Constantes.ESTADO_ACTIVO);
		documentoPorExpedienteService.guardarObj(referenciaNueva);

		TrazabilidadMovimiento tm = new TrazabilidadMovimiento();
		tm.setAccion(27);
		tm.setDocumento(documento.getIdDocumento());
		tm.setIdexpdestino(expedienteNuevo.getIdexpediente());
		tm.setIdexporigen(expediente.getIdexpediente());
		tm.setNombrepc(nombrePC);
		tm.setIdusuario(usuario.getIdusuario());
		tm.setFechacreacion(new Date());
		trazabilidadMovimientoDAO.saveObject(tm);

		//
		//		/*Envio de notificaciones*/
		//		Usuario remitente=documento.getPropietario();
		//		notificacionService.enviarNotificacion(remitente,expedienteNuevo.getProceso().getResponsable(),documento,Constantes.TIPO_NOTIFICACION_REFERENCIA_DOCUMENTO);
		//		if(expedienteNuevo.getProceso().getResponsable()!= expedienteNuevo.getDocumentoPrincipal().getPropietario()){
		//			notificacionService.enviarNotificacion(remitente,expedienteNuevo.getDocumentoPrincipal().getPropietario(),documento,Constantes.TIPO_NOTIFICACION_REFERENCIA_DOCUMENTO);
		//		}
		//		Usuario remitente=(Usuario) sesion.get(Constantes.SESSION_USUARIO);
		Usuario remitente = documento.getPropietario();
		log.info("El usuario logueado es " + remitente.getApellidos() + " " + remitente.getNombres());
		//Usuario remitente =(Usuario) mapSession.get(Constantes.SESSION_USUARIO);

		//		/*Colocando el nuevo expediente en el documento para poder obtener datos en el proceso de envio de notificacion*/
		//		documento.setExpediente(expedienteNuevo);
		/*Envio de notificaciones al responsable del proceso y propietario del documento principal del expediente nuevo*/
		notificacionService.enviarNotificacion(remitente, expedienteNuevo.getDocumentoPrincipal().getPropietario(), documento, Constantes.TIPO_NOTIFICACION_REFERENCIA_DOCUMENTO, expedienteNuevo);
		//if (!expedienteNuevo.getDocumentoPrincipal().getPropietario().getIdusuario().equals(expediente.getProceso().getResponsable().getIdusuario())) {
		//	notificacionService.enviarNotificacion(remitente, expediente.getProceso().getResponsable(), documento, Constantes.TIPO_NOTIFICACION_REFERENCIA_DOCUMENTO, expedienteNuevo);
		//}
		//log.info("El usuario logueado es " + remitente.getApellidos() + " " + remitente.getNombres());
		//Usuario remitente =(Usuario) mapSession.get(Constantes.SESSION_USUARIO);

		//		/*Colocando el nuevo expediente en el documento para poder obtener datos en el proceso de envio de notificacion*/
		//		documento.setExpediente(expedienteNuevo);
		//		/*Envio de notificaciones al responsable del proceso y propietario del documento principal del expediente nuevo*/
		//		notificacionService.enviarNotificacion(remitente, expedienteNuevo.getDocumentoPrincipal().getPropietario(), documento, Constantes.TIPO_NOTIFICACION_REFERENCIA_DOCUMENTO);
		//		if(expedienteNuevo.getDocumentoPrincipal().getPropietario().getIdusuario()!=expediente.getProceso().getResponsable().getIdusuario()){
		//			notificacionService.enviarNotificacion(remitente, expediente.getProceso().getResponsable(), documento, Constantes.TIPO_NOTIFICACION_REFERENCIA_DOCUMENTO);
		//		}
	}
        
          public List<Documento> consultaDocumentoReferencia(Integer idDocumento){
              return documentoDao.consultaDocumentoReferencia(idDocumento);
          }

	/**
	 * Indica si existe al menos un archivo en el expediente al cual se movera
	 * el documento que tenga el mismo nombre que alguno de los archivos del
	 * documento que se esta moviendo.
	 *
	 * @return <strong>true</strong> si no hay archivos con el mismo nombre
	 *         <strong>false</strong> en otro caso
	 * @author German Enriquez
	 */
	@Override
	public boolean verificarArchivosParaExpedienteNuevo(Documento documento, Expediente expedienteNuevo) {
		log.debug("-> [Service] DocumentoService - verificarArchivosParaExpedienteNuevo():boolean ");

		List<Documento> documentos = expedienteNuevo.getDocumentoList();
		if (documentos != null) {
			for (Documento doc : documentos) {
				List<Archivo> archivos = doc.getArchivos();
				if (archivos != null) {
					for (Archivo arch : archivos) {
						List<Archivo> aMover = documento.getArchivos();
						if (aMover != null) {
							for (Archivo archivo : aMover) {
								if (arch.getNombreArchivo().equals(archivo.getNombreArchivo())) {
									return false;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Copia la referencia para un documento de un expediente a otro
	 *
	 * @author Erik Candela
	 * @throws Exception
	 */
	@Transactional
	@Override
	public void copiarArchivosToNuevoDocumento(List<Archivo> archivos, Expediente expediente, Expediente expedienteNuevo, Documento documentoDestino) throws RuntimeException {
		log.debug("-> [Service] DocumentoService - copiarArchivosToNuevoDocumento():void ");

		if (archivos != null) {

			//String rutaAlfresco=Constantes.RUTA_PADRE_ALFRESCO+expediente.getNroexpediente();
			String rutaAlfresco = repositorioService.obtenerRutaExpediente(expediente);
			String rutaDestino = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_TEMPORAL_ALFRESCO);
			if (!rutaDestino.endsWith("/")) {
				rutaDestino += "/";
			}
			if (!rutaAlfresco.endsWith("/")) {
				rutaAlfresco += "/";
			}
			Archivo nuevoArchivo;
			for (Archivo archivo : archivos) {

				// primero descargamos el archivo
				String nombreArchivo = archivo.getNombreArchivo();
				if (alfrescoWebServiceClient.descargarArchivo(rutaAlfresco, nombreArchivo, rutaDestino)) {
					log.debug("Se descargo correctamente el archivo " + archivo);
					// intentamos subir el archivo a alfresco
					//String ruta=Constantes.RUTA_PADRE_ALFRESCO+expedienteNuevo.getNroexpediente();
					String ruta = repositorioService.obtenerRutaExpediente(expedienteNuevo);
					File subir = new File(rutaDestino + nombreArchivo);
					Map<String, String> propiedades = null;//repositorioService.obtenerMetadata(documentoDestino);
					boolean archivoCreado = alfrescoWebServiceClient.subirArchivo(ruta, subir, subir.getName(), propiedades);

					if (archivoCreado) {
						log.info("Se subio correctamente el archivo " + ruta + "/" + nombreArchivo);
						String rutaAntigua = archivo.getRutaAlfresco();
						String[] partes = rutaAntigua.split("/");
						String rutaNueva = partes[0] + "/" + expedienteNuevo.getNroexpediente() + "/" + partes[2];
						nuevoArchivo = new Archivo();
						nuevoArchivo.setFechaCreacion(new Date());
						nuevoArchivo.setNombre(nombreArchivo);
						nuevoArchivo.setSURL(archivo.getSURL());
						nuevoArchivo.setRutaArchivoPdf(archivo.getRutaArchivoPdf());
						nuevoArchivo.setEstadoDigitalizacion(archivo.getEstadoDigitalizacion());
						nuevoArchivo.setDescripcion(archivo.getDescripcion());
						nuevoArchivo.setRutaAlfresco(rutaNueva);
						nuevoArchivo.setDocumento(documentoDestino);
						archivoService.saveArchivo(nuevoArchivo);
					} else {
						log.error("No se pudo subir el archivo " + nombreArchivo);
						throw new RuntimeException("No se pudo subir el archivo a Alfresco");
					}
				}
			}
		}
	}
        
        @SuppressWarnings({ "unchecked" })
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public DocumentoDetail updateDocumentoUserFinal(DocumentoDetail documentoDetail, Map<String, Object> session, String iddestinatario, Integer idccdestinatario, String strAcc, boolean bBandeja, ArchivoPendiente archivoPendiente, String nombrePC, String nombrePDFprincipal) throws RemoteException, InvalidInputMessageFaultException, InvalidParticipantTokenFaultException, UnavailableTaskFaultException, XMLStreamException, Exception {
            log.debug("-> [Service] DocumentoService - updateDocumentoUserFinal():DocumentoDetail ");
            mapSession = ActionContext.getContext().getSession(); 
                  
            try{
                  Date fecha = new Date();
                  Usuario objUsuarioSession = (Usuario) session.get(Constantes.SESSION_USUARIO);
                  Documento doc = this.findByIdDocumento(new Integer(documentoDetail.getIIdDocumento()));
                  Tipodocumento objTD = tipoDocumentoDao.findByIdTipoDocumento(documentoDetail.getIIdTipoDocumento());
                  documentoDetail = setearCliente(documentoDetail);
                  
                  doc.setID_CLIENTE(null);
                  doc.setCodRemitente(null);
                  doc.setCodTipoInstitucion(null);
                  doc.setCodCargoRemitente(null);
                  doc.setDesUnidadRemitente(null);
                  
                  doc.setFechaReunion(null);
                  doc.setLugar(null);
                  doc.setObjetivo(null);
                  doc.setCodInfraestructura(null);
                  doc.setCodMateria(null);
                  doc.setIdConcesionario(null);
                        
                  /*List<Parametro> lstParametro = parametroService.findByTipoActivo("TIPO_DOCUMENTO_REQUERIMIENTO_TRIBUTARIO");
                  if (lstParametro!=null && lstParametro.size()>0 && documentoDetail.getOpcion().equals(Constantes.COD_TRAMITE_INTERNO)){
                      for(int i=0;i<lstParametro.size();i++){
                        if (lstParametro.get(i).getValor().equals(objTD.getIdtipodocumento().toString())){
                             doc.setIdConcesionario(new Integer(documentoDetail.getConcesionario()));   
                        }    
                      }
                   }*/
                  
                  if (documentoDetail.getConcesionario()!=null)
                     doc.setIdConcesionario(new Integer(documentoDetail.getConcesionario()));
                  
                  if (documentoDetail.getStrUnidad()==null || documentoDetail.getStrUnidad().trim().equals(""))
                     doc.setUnidadenumera(objUsuarioSession.getIdUnidadPerfil());
                  else
                     doc.setUnidadenumera(Integer.valueOf(documentoDetail.getStrUnidad()));
                  
                  if (documentoDetail.getOpcion().equals(Constantes.COD_TRAMITE_INTERNO)){
                     if (objTD!=null && objTD.getExterno()!=null && objTD.getExterno().equals("1")){
                        if (documentoDetail.getIdTipoCliente().equals(Constantes.COD_TIPOCLIENTE_EMPRESA)){
                           Cliente c = clienteService.findByIdCliente(documentoDetail.getIIdCliente());
                           doc.setCodTipoInstitucion(c.getCodtipoinstitucion());
                           doc.setID_CLIENTE(documentoDetail.getIIdCliente());
                           doc.setDesRemitente(documentoDetail.getIdPersonaEmpresa());
                           doc.setDesCargoRemitente(documentoDetail.getCodCargoPersonaEmpresa());         
                        }else{
                              if (documentoDetail.getIdTipoCliente().equals(Constantes.COD_TIPOCLIENTE_INSTITUCION)){
                                 Cliente c = clienteService.findByIdCliente(documentoDetail.getIIdCliente());
                                 doc.setCodTipoInstitucion(c.getCodtipoinstitucion());
                                 doc.setID_CLIENTE(documentoDetail.getIIdCliente());
                                 doc.setDesRemitente(documentoDetail.getIdPersonaInstitucion());
                                 doc.setDesCargoRemitente(documentoDetail.getCodCargoPersonaInstitucion());   
                                 doc.setDesUnidadRemitente(documentoDetail.getDesUnidadOrganica());
                                 doc.setNumeroFolios(documentoDetail.getINroFolios() == null ? 0 : documentoDetail.getINroFolios());
                              } else{
                                 Cliente c = clienteService.findByIdCliente(documentoDetail.getIIdCliente());
                                 doc.setCodTipoInstitucion(c.getCodtipoinstitucion()); 
                                 doc.setID_CLIENTE(documentoDetail.getIIdCliente().toString().equals(Constantes.CLIENTE_OSINERGMIN_ID.toString())?null:documentoDetail.getIIdCliente());
                                 doc.setDesCargoRemitente(documentoDetail.getCodCargoPersonaNatural()); 
                              }
                        }
                     }
                         
                     if (objTD.getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_402)){
                        Cliente c = clienteService.findByIdCliente(documentoDetail.getIdInstitucionSicor());
                        doc.setCodTipoInstitucion(c.getCodtipoinstitucion());
                        doc.setID_CLIENTE(documentoDetail.getIdInstitucionSicor());
                        doc.setCodInfraestructura(documentoDetail.getiIdInfraestructura());
                        doc.setCodMateria(documentoDetail.getiIdMateria());
                     }
                         
                     if (objTD.getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_404)){
                        //Date fecha = new Date();
                                        
                        if (documentoDetail.getListaIntegrantesInternos()!=null){
                            DocumentoReunion documentoReunion = new DocumentoReunion();
                            documentoReunion.setTipo("0");
                            documentoReunion.setIdDocumento(doc.getIdDocumento());
                            List<DocumentoReunion> listDocumentoReunion = documentoReunionDAO.getDocumentoReunion(documentoReunion);
                            String[] lista = documentoDetail.getListaIntegrantesInternos().trim().equals("")?null: StringUtil.stringToArrayPersonalizado(documentoDetail.getListaIntegrantesInternos().trim(),'|');
                       
                            if (documentoDetail.getListaIntegrantesInternos()!=null){
                               if (lista!=null){
                                  for(String fila : lista){
                                    boolean bandera = false;
                                    for(int j=0;j<listDocumentoReunion.size();j++){
                                       if (fila.equals(listDocumentoReunion.get(j).getNombres())){
                                            listDocumentoReunion.get(j).setFechaModificacion(fecha);
                                            listDocumentoReunion.get(j).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                                            documentoReunionDAO.saveDocumentoReunion(listDocumentoReunion.get(j));
                                            bandera = true;
                                            break;
                                       }
                                    }

                                    if (!bandera){
                                       documentoReunion = new DocumentoReunion();
                                       documentoReunion.setEstado("A");
                                       documentoReunion.setFechaCreacion(fecha);
                                       documentoReunion.setIdDocumento(doc.getIdDocumento());
                                       documentoReunion.setNombres(fila);
                                       documentoReunion.setUsuarioCreacion(objUsuarioSession.getIdusuario());
                                       documentoReunion.setTipo("0");
                                       documentoReunionDAO.saveDocumentoReunion(documentoReunion);
                                    }
                                  }
                               }
                                                 
                               if (listDocumentoReunion!=null){
                                    for(int i=0;i<listDocumentoReunion.size();i++){
                                       boolean bandera = false;
                                       if (lista!=null){
                                          for(String fila : lista){
                                            if (fila.equals(listDocumentoReunion.get(i).getNombres())){
                                               bandera = true;
                                               break;
                                            }
                                          }
                                       }
                                       if (!bandera){
                                          listDocumentoReunion.get(i).setFechaModificacion(fecha);
                                          listDocumentoReunion.get(i).setEstado("I");
                                          listDocumentoReunion.get(i).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                                          documentoReunionDAO.saveDocumentoReunion(listDocumentoReunion.get(i));
                                       }
                                    }
                               }
                            }else{
                                   if (listDocumentoReunion!=null){
                                      for(int j=0;j<listDocumentoReunion.size();j++){
                                         listDocumentoReunion.get(j).setFechaModificacion(fecha);
                                         listDocumentoReunion.get(j).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                                         listDocumentoReunion.get(j).setEstado("I");
                                         documentoReunionDAO.saveDocumentoReunion(listDocumentoReunion.get(j));
                                      }
                                  }
                            }         
                        }
                                        
                        if (documentoDetail.getListaIntegrantesExternos()!=null){
                            DocumentoReunion documentoReunion = new DocumentoReunion();
                            documentoReunion.setTipo("1");
                            documentoReunion.setIdDocumento(doc.getIdDocumento());
                            List<DocumentoReunion> listDocumentoReunion = documentoReunionDAO.getDocumentoReunion(documentoReunion);
                            String[] lista = documentoDetail.getListaIntegrantesExternos().trim().equals("")?null: StringUtil.stringToArrayPersonalizado(documentoDetail.getListaIntegrantesExternos().trim(),'|');
                                     
                            if (lista!=null){
                               for(String fila : lista){
                                  boolean bandera = false;
                                  for(int j=0;j<listDocumentoReunion.size();j++){
                                     if (fila.equals(listDocumentoReunion.get(j).getNombres())){
                                       listDocumentoReunion.get(j).setFechaModificacion(fecha);
                                       listDocumentoReunion.get(j).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                                       documentoReunionDAO.saveDocumentoReunion(listDocumentoReunion.get(j));
                                       bandera = true;
                                       break;
                                     }
                                  }
                                                    
                                  if (!bandera){
                                     documentoReunion = new DocumentoReunion();
                                     documentoReunion.setEstado("A");
                                     documentoReunion.setFechaCreacion(fecha);
                                     documentoReunion.setIdDocumento(doc.getIdDocumento());
                                     documentoReunion.setNombres(fila);
                                     documentoReunion.setUsuarioCreacion(objUsuarioSession.getIdusuario());
                                     documentoReunion.setTipo("1");
                                    documentoReunionDAO.saveDocumentoReunion(documentoReunion);
                                  }
                               }
                                                 
                               if (listDocumentoReunion!=null){
                                   for(int i=0;i<listDocumentoReunion.size();i++){
                                      boolean bandera = false;
                                      if (lista!=null){
                                          for(String fila : lista){
                                            if (fila.equals(listDocumentoReunion.get(i).getNombres())){
                                               bandera = true;
                                               break;
                                            }
                                          }
                                      }    
                                      if (!bandera){
                                         listDocumentoReunion.get(i).setFechaModificacion(fecha);
                                         listDocumentoReunion.get(i).setEstado("I");
                                         listDocumentoReunion.get(i).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                                         documentoReunionDAO.saveDocumentoReunion(listDocumentoReunion.get(i));
                                      }
                                   }
                                }
                            }else{
                                if (listDocumentoReunion!=null){
                                  for(int j=0;j<listDocumentoReunion.size();j++){
                                     listDocumentoReunion.get(j).setFechaModificacion(fecha);
                                     listDocumentoReunion.get(j).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                                     listDocumentoReunion.get(j).setEstado("I");
                                     documentoReunionDAO.saveDocumentoReunion(listDocumentoReunion.get(j));
                                  }
                                }
                            }
                        }
                     }
                     
                     if (objTD.getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_405)){
                        Cliente c = clienteService.findByIdCliente(documentoDetail.getIdInstitucionSicor());
                        doc.setCodTipoInstitucion(c.getCodtipoinstitucion());
                        doc.setID_CLIENTE(documentoDetail.getIdInstitucionSicor());
                        doc.setFechaReunion(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(documentoDetail.getStrFechaReunion() + " "  + documentoDetail.getStrHoraReunion().substring(1)));
                        doc.setLugar(documentoDetail.getStrLugar());
                        doc.setObjetivo(documentoDetail.getStrObjetivo());
                     }
                     
                     if (!objTD.getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_404)){
                         DocumentoReunion documentoReunion = new DocumentoReunion();
                         documentoReunion.setTipo("0");
                         documentoReunion.setIdDocumento(doc.getIdDocumento());
                         
                         List<DocumentoReunion> listDocumentoReunion = documentoReunionDAO.getDocumentoReunion(documentoReunion);
                         if (listDocumentoReunion!=null){
                             for(int i=0;i<listDocumentoReunion.size();i++){
                                listDocumentoReunion.get(i).setEstado("I");
                                listDocumentoReunion.get(i).setFechaModificacion(fecha);
                                listDocumentoReunion.get(i).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                                documentoReunionDAO.saveDocumentoReunion(documentoReunion);
                             }
                         }
                         documentoReunion.setTipo("1");
                         listDocumentoReunion = documentoReunionDAO.getDocumentoReunion(documentoReunion);
                         if (listDocumentoReunion!=null){
                             for(int i=0;i<listDocumentoReunion.size();i++){
                                listDocumentoReunion.get(i).setEstado("I");
                                listDocumentoReunion.get(i).setFechaModificacion(fecha);
                                listDocumentoReunion.get(i).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                                documentoReunionDAO.saveDocumentoReunion(documentoReunion);
                             }
                         }
                     }
                  }
                                
                  if (documentoDetail.getOpcion().equals(Constantes.COD_TRAMITE_EXTERNO)){
                      if (documentoDetail.getIdTipoCliente().equals(Constantes.COD_TIPOCLIENTE_EMPRESA)){
                        Cliente c = clienteService.findByIdCliente(documentoDetail.getIIdCliente());
                        doc.setCodTipoInstitucion(c.getCodtipoinstitucion());
                           
                        doc.setID_CLIENTE(documentoDetail.getIIdCliente());
                        doc.setDesRemitente(documentoDetail.getIdPersonaEmpresa());
                        doc.setDesCargoRemitente(documentoDetail.getCodCargoPersonaEmpresa());         
                      }else{
                        if (documentoDetail.getIdTipoCliente().equals(Constantes.COD_TIPOCLIENTE_INSTITUCION)){
                           Cliente c = clienteService.findByIdCliente(documentoDetail.getIIdCliente());
                           doc.setCodTipoInstitucion(c.getCodtipoinstitucion());
                           doc.setID_CLIENTE(documentoDetail.getIIdCliente());
                           doc.setDesRemitente(documentoDetail.getIdPersonaInstitucion());
                           doc.setDesCargoRemitente(documentoDetail.getCodCargoPersonaInstitucion());
                           doc.setDesUnidadRemitente(documentoDetail.getDesUnidadOrganica());
                        } else{
                           Cliente c = clienteService.findByIdCliente(documentoDetail.getIIdCliente());
                           doc.setCodTipoInstitucion(c.getCodtipoinstitucion());
                           doc.setID_CLIENTE(documentoDetail.getIIdCliente());
                           doc.setDesCargoRemitente(documentoDetail.getCodCargoPersonaNatural()); 
                        }
                      }
                   }
                  
                  doc.setConfidencial(documentoDetail.getConfidencial());
                  doc.setTipoDocumento(objTD);
                  doc.setAsunto(documentoDetail.getStrAsunto());
                  doc.setObservacion(documentoDetail.getStrObservacion());
                  doc.setReferenciados(documentoDetail.getStrReferencia());//
                  doc.setUltimoAsunto(documentoDetail.getStrAsunto());
                  doc.setContenido(documentoDetail.getStrContenido());
                  doc.setFechaModificacion(new Date());
                  doc.setUsuarioModificacion(objUsuarioSession.getIdusuario());
                  doc.setPrioridad(documentoDetail.getPrioridad());
                  doc.setPlazo(null);
                  doc.setFechaLimiteAtencion(null);
                  doc.setRecepcionado(documentoDetail.getEsTipoRecepcion());
                  
                  if (documentoDetail.getPlazo()!=null){ 
                     if (documentoDetail.getPlazo().equals("0") && documentoDetail.getIPlazoDia()!=null && !documentoDetail.getIPlazoDia().trim().equals("")){
                        doc.setPlazo(new Integer(documentoDetail.getIPlazoDia()));
                        doc.setFechaLimiteAtencion(fechaLimite.getFechaLimite(fecha, Integer.parseInt(documentoDetail.getIPlazoDia()))); 
                      }  
                          
                     if (documentoDetail.getPlazo().equals("1") && documentoDetail.getStrFechaLimiteAtencion()!=null && !documentoDetail.getStrFechaLimiteAtencion().trim().equals("")){
                         doc.setFechaLimiteAtencion(new SimpleDateFormat("yyyy-MM-dd").parse(documentoDetail.getStrFechaLimiteAtencion()));
                         doc.setPlazo(fechaLimite.getPlazo(fecha , doc.getFechaLimiteAtencion()));
                     }
                  } 
                  
                  //doc.setFechaLimiteAtencion(new SimpleDateFormat("yyyy-MM-dd").parse(documentoDetail.getStrFechaLimiteAtencion()));
                  
                  /*if (documentoDetail.getStrFechaLimiteAtencion()!=null && !documentoDetail.getStrFechaLimiteAtencion().trim().equals(""))
                     doc.setFechaLimiteAtencion(new SimpleDateFormat("yyyy-MM-dd").parse(documentoDetail.getStrFechaLimiteAtencion()));
                  else
                     doc.setFechaLimiteAtencion(null);
                  */
                  
                  if (documentoDetail.getINroFolios()==null)
                    doc.setNumeroFolios(0);
                  else
                    doc.setNumeroFolios(documentoDetail.getINroFolios());  
                  
                  doc.setNumeroFoliosOriginales(documentoDetail.getiNroFoliosOriginales());
                  doc.setNumeroFoliosCopias(documentoDetail.getiNroFoliosCopias());
                  doc.setImagenesDigitalizadas(documentoDetail.getiNroFoliosDigitalizados());
                  
                  try {
                  	doc.setFechaDocumento(new SimpleDateFormat("yyyy-MM-dd").parse(documentoDetail.getStrFechaDocumento()));
		  } catch (ParseException e) {
			e.printStackTrace();
                        throw e;
		  }
                  
                  
                  documentoDetail.setNroTramite(doc.getID_CODIGO().toString());
                  documentoDetail.setFechacreacion(doc.getFechaCreacion());
                  documentoDetail.setIdExterno(doc.getID_EXTERNO().toString());
                
                  if (doc.getEnumerado().toString().equals("N")){
                     doc.setEnumerarDocumento("S".equals(documentoDetail.getEnumerarDocumento()) ? true : false);
                     doc.setNumeroDocumento(documentoDetail.getStrNroDocumento());
                    
                     if(!StringUtils.isBlank(doc.getNumeroDocumento())){
                       doc.setEnumerado(Constantes.Si);
		     }
                  
                     if(doc.getEnumerarDocumento() && documentoDetail.getTipoNumeracion().equals(Constantes.NUMERACION_AUTOMATICA) && doc.getEnumerado().equals(Constantes.No)){
			 Unidad unid = null;
                                    
                         if (documentoDetail.getStrUnidad()==null || documentoDetail.getStrUnidad().trim().equals(""))
                            unid = new Unidad(objUsuarioSession.getIdUnidadPerfil());
                         else{
                            if (doc!=null && doc.getIdConcesionario()!=null){
                               unid = new Unidad(Integer.parseInt(parametroService.findByTipoUnico("UNIDAD_REQUERIMIENTO_TRIBUTARIO").getValor()));         
                             }else{ 
                               unid = new Unidad(Integer.parseInt(documentoDetail.getStrUnidad()));
                            }
                         }
                         
                         if (doc.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.TIPO_INFORME_CONJUNTO) || doc.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.TIPO_MEMORANDO_CONJUNTO))
                           unid = new Unidad(new Integer(parametroService.findByTipo(Constantes.PARAMETRO_UNIDAD_INFORME_CONJUNTO).get(0).getValor()));

                           List<Numeracion> listaNum=numeracionDAO.findAllUnidadAndTipoDoc(unid.getIdunidad(),doc.getTipoDocumento().getIdtipodocumento());
                           if (listaNum != null && listaNum.size() > 0 && listaNum.get(0).getTiponumeracion() != null) {
                              String nro = numeracionDAO.guardarObjProcedure(listaNum.get(0), unid.getIdunidad(), objUsuarioSession.getIdusuario().intValue());
                              doc.setNumeroDocumento(nro);
                              doc.setEnumerado(Constantes.Si);
                           }
                                       
                            documentoDetail.setStrNroDocumento(doc.getNumeroDocumento());
		     }
                     
                  }else{
                        doc.setEnumerarDocumento("S".equals(documentoDetail.getEnumerarDocumento()) ? true : false);
                        if(!StringUtils.isBlank(documentoDetail.getStrNroDocumento())){
                           doc.setNumeroDocumento(documentoDetail.getStrNroDocumento());
                        }
                  }
                 
                  List<DocumentoAdjunto> list = documentoAdjuntoDAO.findByListDocumentoAdjunto(doc.getIdDocumento());
                  
                  if (documentoDetail.getOpcion().equals(Constantes.COD_TRAMITE_EXTERNO)){ 
                        List<Item> items = (List<Item>)mapSession.get("AdjuntosDocumento");
                        //Date fecha = new Date();
                        if (items!=null){
                              for(int i=0;i<items.size();i++){
                                 boolean bandera = false;

                                 for(int j=0;j<list.size();j++){
                                   if (items.get(i).getIdId().toString().equals(list.get(j).getIdDocumentoAdjunto().toString())){
                                       list.get(i).setFechaModificacion(fecha);
                                       list.get(i).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                                       documentoAdjuntoDAO.registrarDocumentoAdjunto(list.get(i));
                                       bandera = true;
                                       break;
                                   }    
                                 }

                                 if (!bandera){
                                    DocumentoAdjunto d = new DocumentoAdjunto();
                                    d.setCodTipoAdj(items.get(i).getTipo());
                                    d.setCopOrig(items.get(i).getTipoAdjunto());
                                    d.setEstado("A");
                                    d.setIdDocumento(doc.getIdDocumento());
                                    d.setNroAdj(new Integer(items.get(i).getNro()));
                                    d.setUsuarioCreacion(objUsuarioSession.getIdusuario());
                                    documentoAdjuntoDAO.registrarDocumentoAdjunto(d);
                                 }
                              }
                              
                              if (list!=null){
                                  for(int i=0;i<list.size();i++){
                                      boolean bandera = false;
                                       for(int j=0;j<items.size();j++){
                                           if (items.get(j).getIdId().toString().equals(list.get(i).getIdDocumentoAdjunto().toString())){
                                               bandera = true;
                                               break;
                                           } 
                                       }
                                       if (!bandera){
                                         list.get(i).setFechaModificacion(fecha);
                                         list.get(i).setEstado("I");
                                         list.get(i).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                                          documentoAdjuntoDAO.registrarDocumentoAdjunto(list.get(i));
                                       }
                                  }
                              }
                        }else{
                            if (list!=null){
                                for(int i=0;i<list.size();i++){
                                   list.get(i).setFechaModificacion(fecha);
                                   list.get(i).setEstado("I");
                                   list.get(i).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                                   documentoAdjuntoDAO.registrarDocumentoAdjunto(list.get(i));
                                }
                            }
                        } 
                        
                        
                        if (documentoDetail.getListaDerivacionPara()!=null){
                            DocumentoDerivacion documentoDerivacion = new DocumentoDerivacion();
                            documentoDerivacion.setTipo("P");
                            documentoDerivacion.setIddocumento(doc.getIdDocumento());
                            List<DocumentoDerivacion> listDocumentoDerivacion = documentoDerivacionDAO.getUsuarioDerivacion(documentoDerivacion);
                            String[] lista = documentoDetail.getListaDerivacionPara().trim().equals("")?null: StringUtil.stringToArrayPersonalizado(documentoDetail.getListaDerivacionPara().trim(),'|');
                                     
                            if (lista!=null){
                               for(String fila : lista){
                                  boolean bandera = false;
                                   String[] datos = fila.split("-");
                                   for(int j=0;j<listDocumentoDerivacion.size();j++){
                                      if (datos[0].equals(listDocumentoDerivacion.get(j).getIdusuario().toString()) &&
                                          datos[1].equals(listDocumentoDerivacion.get(j).getUnidadpropietario().toString()) &&
                                          datos[2].equals(listDocumentoDerivacion.get(j).getCargopropietario().toString())){
                                           listDocumentoDerivacion.get(j).setFechamodificacion(fecha);
                                           listDocumentoDerivacion.get(j).setUsuariomodificacion(objUsuarioSession.getIdusuario());
                                           documentoDerivacionDAO.guardar(listDocumentoDerivacion.get(j));
                                           bandera = true;
                                           break;
                                      }
                                   }
                                                    
                                  if (!bandera){
                                     documentoDerivacion = new DocumentoDerivacion();
                                     documentoDerivacion.setEstado("A");
                                     documentoDerivacion.setFechacreacion(fecha);
                                     documentoDerivacion.setIddocumento(doc.getIdDocumento());
                                     documentoDerivacion.setUnidadpropietario(new Integer(datos[1]));
                                     documentoDerivacion.setIdusuario(new Integer(datos[0]));
                                     documentoDerivacion.setCargopropietario(new Integer(datos[2]));
                                     documentoDerivacion.setUsuariocreacion(objUsuarioSession.getIdusuario());
                                     documentoDerivacion.setTipo("P");
                                     documentoDerivacionDAO.guardar(documentoDerivacion);
                                  }
                               }
                                                 
                               if (listDocumentoDerivacion!=null){
                                   for(int i=0;i<listDocumentoDerivacion.size();i++){
                                      boolean bandera = false;
                                      if (lista!=null){
                                          for(String fila : lista){
                                            String[] datos = fila.split("-");  
                                            if (datos[0].equals(listDocumentoDerivacion.get(i).getIdusuario().toString()) &&
                                                datos[1].equals(listDocumentoDerivacion.get(i).getUnidadpropietario().toString()) &&
                                                datos[2].equals(listDocumentoDerivacion.get(i).getCargopropietario().toString())){
                                                bandera = true;
                                                break;
                                            }
                                          }
                                      }    
                                      if (!bandera){
                                         listDocumentoDerivacion.get(i).setFechamodificacion(fecha);
                                         listDocumentoDerivacion.get(i).setEstado("I");
                                         listDocumentoDerivacion.get(i).setUsuariomodificacion(objUsuarioSession.getIdusuario());
                                         documentoDerivacionDAO.guardar(listDocumentoDerivacion.get(i));
                                      }
                                   }
                                }
                            }else{
                                if (listDocumentoDerivacion!=null){
                                  for(int j=0;j<listDocumentoDerivacion.size();j++){
                                     listDocumentoDerivacion.get(j).setFechamodificacion(fecha);
                                     listDocumentoDerivacion.get(j).setUsuariomodificacion(objUsuarioSession.getIdusuario());
                                     listDocumentoDerivacion.get(j).setEstado("I");
                                     documentoDerivacionDAO.guardar(listDocumentoDerivacion.get(j));
                                  }
                                }
                            }
                        }
                        
                        if (documentoDetail.getListaDerivacionCC()!=null){
                            DocumentoDerivacion documentoDerivacion = new DocumentoDerivacion();
                            documentoDerivacion.setTipo("C");
                            documentoDerivacion.setIddocumento(doc.getIdDocumento());
                            List<DocumentoDerivacion> listDocumentoDerivacion = documentoDerivacionDAO.getUsuarioDerivacion(documentoDerivacion);
                            String[] lista = documentoDetail.getListaDerivacionCC().trim().equals("")?null: StringUtil.stringToArrayPersonalizado(documentoDetail.getListaDerivacionCC().trim(),'|');
                                     
                            if (lista!=null){
                               for(String fila : lista){
                                  boolean bandera = false;
                                   String[] datos = fila.split("-");
                                   for(int j=0;j<listDocumentoDerivacion.size();j++){
                                      if (datos[0].equals(listDocumentoDerivacion.get(j).getIdusuario().toString()) &&
                                         datos[1].equals(listDocumentoDerivacion.get(j).getUnidadpropietario().toString()) &&
                                         datos[2].equals(listDocumentoDerivacion.get(j).getCargopropietario().toString())){
                                         listDocumentoDerivacion.get(j).setFechamodificacion(fecha);
                                         listDocumentoDerivacion.get(j).setUsuariomodificacion(objUsuarioSession.getIdusuario());
                                         documentoDerivacionDAO.guardar(listDocumentoDerivacion.get(j));
                                         bandera = true;
                                         break;
                                      }
                                   }
                                                    
                                  if (!bandera){
                                     documentoDerivacion = new DocumentoDerivacion();
                                     documentoDerivacion.setEstado("A");
                                     documentoDerivacion.setFechacreacion(fecha);
                                     documentoDerivacion.setIddocumento(doc.getIdDocumento());
                                     documentoDerivacion.setUnidadpropietario(new Integer(datos[1]));
                                     documentoDerivacion.setIdusuario(new Integer(datos[0]));
                                     documentoDerivacion.setCargopropietario(new Integer(datos[2]));
                                     documentoDerivacion.setUsuariocreacion(objUsuarioSession.getIdusuario());
                                     documentoDerivacion.setTipo("C");
                                     documentoDerivacionDAO.guardar(documentoDerivacion);
                                  }
                               }
                                                 
                               if (listDocumentoDerivacion!=null){
                                   for(int i=0;i<listDocumentoDerivacion.size();i++){
                                      boolean bandera = false;
                                      if (lista!=null){
                                          for(String fila : lista){
                                            String[] datos = fila.split("-");  
                                            if (datos[0].equals(listDocumentoDerivacion.get(i).getIdusuario().toString()) &&
                                                datos[1].equals(listDocumentoDerivacion.get(i).getUnidadpropietario().toString()) &&
                                                datos[2].equals(listDocumentoDerivacion.get(i).getCargopropietario().toString())){
                                                bandera = true;
                                                break;
                                            }
                                          }
                                      }    
                                      if (!bandera){
                                         listDocumentoDerivacion.get(i).setFechamodificacion(fecha);
                                         listDocumentoDerivacion.get(i).setEstado("I");
                                         listDocumentoDerivacion.get(i).setUsuariomodificacion(objUsuarioSession.getIdusuario());
                                         documentoDerivacionDAO.guardar(listDocumentoDerivacion.get(i));
                                      }
                                   }
                                }
                            }else{
                                if (listDocumentoDerivacion!=null){
                                  for(int j=0;j<listDocumentoDerivacion.size();j++){
                                     listDocumentoDerivacion.get(j).setFechamodificacion(fecha);
                                     listDocumentoDerivacion.get(j).setUsuariomodificacion(objUsuarioSession.getIdusuario());
                                     listDocumentoDerivacion.get(j).setEstado("I");
                                     documentoDerivacionDAO.guardar(listDocumentoDerivacion.get(j));
                                  }
                                }
                            }
                        }
                        
                        
                  }
                  
                  String[] listDocReferenciados = null;
                  List<DocumentoReferencia> lista = documentoReferenciaDAO.getReferenciaDocumento(doc.getIdDocumento());  //getAllReferenciaDocumento(doc.getIdDocumento());
               
                  if (documentoDetail.getListReferenciados()!=null)
                    listDocReferenciados = documentoDetail.getListReferenciados().trim().equals("")?null: StringUtil.stringToArrayPersonalizado(documentoDetail.getListReferenciados().trim(),'|');
                         
                  if (listDocReferenciados!=null && listDocReferenciados.length>0){
                      for(int i=0;i<listDocReferenciados.length;i++){
                         boolean bandera = false;
                         //bd
                         if (lista!=null){
                            for(int j=0;j<lista.size();j++){
                                if (doc.getIdDocumento().toString().equals(lista.get(j).getIdDocumento().toString()) &&
                                    listDocReferenciados[i].equals(lista.get(j).getIdDocumentoReferencia().toString())){
                                    bandera = true ;
                                    lista.get(j).setEstado("A");
                                    lista.get(j).setRespondido(null);
                                    lista.get(j).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                                    lista.get(j).setFechaModificacion(fecha);
                                    documentoReferenciaDAO.saveDocumentoReferencia(lista.get(j));
                                    break;
                                }
                            }
                         }
                         
                         if (!bandera){
                            DocumentoReferencia docReferencia  = new DocumentoReferencia();
                            docReferencia.setIdDocumento(doc.getIdDocumento()); 
                            docReferencia.setIdDocumentoReferencia(new Integer(listDocReferenciados[i]));
                            docReferencia.setUsuarioCreacion(objUsuarioSession.getIdusuario());
                            docReferencia.setEstado("A");
                            docReferencia.setFechaCreacion(fecha);
                            docReferencia.setVerDocumento(verDocumento(new Integer(listDocReferenciados[i]),objUsuarioSession));
                            documentoReferenciaDAO.saveDocumentoReferencia(docReferencia);  
                            
                            if (doc.getTipoDocumento().getExternoQR()!= null && doc.getTipoDocumento().getExternoQR().equals("1")){
                                Documento d = documentoDao.findByIdDocumento(new Integer(listDocReferenciados[i]));
                                List<Archivo> lst = archivoService.findLstByIdDocumento(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia());

                                if (lst!=null && lst.size()>0){
                                  for(int k=0;k<lst.size();k++){
                                      ReferenciaArchivo referenciaArchivo = new ReferenciaArchivo();
                                      referenciaArchivo.setIdDocumento(doc.getIdDocumento());
                                      referenciaArchivo.setIdDocumentoReferencia(new Integer(listDocReferenciados[i]));
                                      referenciaArchivo.setEstado("A");
                                      referenciaArchivo.setFechaCreacion(new Date());
                                      referenciaArchivo.setUsuarioCreacion(objUsuarioSession.getIdusuario());
                                      referenciaArchivo.setIdArchivo(lst.get(k).getIdArchivo());
                                      referenciaArchivoDAO.saveReferenciaArchivo(referenciaArchivo);
                                  }
                                }
                            }
                         } 
                      }
                      
                      if (lista!=null){
                         for(int i=0;i<lista.size();i++){
                             boolean bandera = false;
                             for(int j=0;j<listDocReferenciados.length;j++){
                                 if (doc.getIdDocumento().toString().equals(lista.get(i).getIdDocumento().toString()) &&
                                    listDocReferenciados[j].equals(lista.get(i).getIdDocumentoReferencia().toString())){
                                    bandera = true; 
                                    break;
                                 }
                             }
                             
                             if (!bandera){
                                lista.get(i).setEstado("I");
                                lista.get(i).setRespondido(null);
                                lista.get(i).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                                lista.get(i).setFechaModificacion(fecha);
                                documentoReferenciaDAO.updateDocumentoReferencia(lista.get(i));
                             }
                         }   
                      }
                  }else{//bd
                      if (lista!=null){
                         for(int i=0;i<lista.size();i++){
                            lista.get(i).setEstado("I");
                            lista.get(i).setUsuarioModificacion(objUsuarioSession.getIdusuario());
                            lista.get(i).setFechaModificacion(fecha);
                            documentoReferenciaDAO.saveDocumentoReferencia(lista.get(i));
                         }    
                      }
                  }
                  
                  
                  Expediente ex = expedienteService.findByIdExpediente(documentoDetail.getIIdExpediente());
                  Serie serie = new Serie();
                  serie.setIdserie(documentoDetail.getiIdSerie());
                  ex.setSerie(serie);
                  doc.setExpediente(ex);
                  documentoDetail.setsNroExpediente(ex.getNroexpediente());
                  documentoDetail.setStrNroDocumento(doc.getNumeroDocumento());
                  documentoDao.saveDocumento(doc);
                  
            }catch(Exception e){
                e.printStackTrace();
                throw e;
            }
            
            return documentoDetail;
        }
        
        private String verDocumento(Integer id, Usuario objUsuarioSession){
            try{
                    Trazabilidaddocumento t = new Trazabilidaddocumento();
                    Documento d = documentoDao.findByIdDocumento(id);
                    Usuario u = new Usuario();
                    Expediente exp = new Expediente();
                    u.setIdusuario(objUsuarioSession.getIdUsuarioPerfil());
                    exp.setId(d.getExpediente().getId());
                    d.setExpediente(exp);
                    t.setDocumento(d);
                    t.setRemitente(u);
                    t.setDestinatario(u);

                    Usuario usuarioFinal = usuarioService.findByIdUsuario(objUsuarioSession.getIdUsuarioPerfil()); 

                    if (trazabilidadDocumentoService.contarListTotalTrazabilidadesExpediente(t)==0 && usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("0")){
                        return "I";      
                    }

                    if(d.getConfidencial().equals(Constantes.Si)){
                        List<Integer> permitidos = documentoDao.getUsuariosPermitidos(d.getDocumentoreferencia() != null ? d.getDocumentoreferencia() : d.getIdDocumento());
                        if(!permitidos.contains(new BigDecimal(objUsuarioSession.getIdUsuarioPerfil()))){
                            return "I";
                        }   
                    }
                    
                    return "A";
            }catch(Exception e){
                return "I";
            }
        }

	/**REN Guarda el nuevo documento creado en Usuario Final -----------------------------------------------------------------*/
	@SuppressWarnings({ "unchecked" })
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public DocumentoDetail saveNuevoDocumentoUserFinal(DocumentoDetail documentoDetail, Map<String, Object> session, String iddestinatario, Integer idccdestinatario, String strAcc, boolean bBandeja, ArchivoPendiente archivoPendiente, String nombrePC, String nombrePDFprincipal) throws RemoteException, InvalidInputMessageFaultException, InvalidParticipantTokenFaultException, UnavailableTaskFaultException, XMLStreamException, Exception {
		log.debug("-> [Service] DocumentoService - saveNuevoDocumentoUserFinal():DocumentoDetail ");
                IotdtmDocExterno iotdtmDocExterno = null;
                DocumentoDetail objDD = documentoDetail;

		try {
                        Boolean tipoDocReqTri = false;
                        Integer idDocumento = null;
                        Date fecha = new Date();
                        Expediente expediente = null;
                        Documento objD = new Documento();
                        Tipodocumento objTD =  tipoDocumentoDao.findByIdTipoDocumento(objDD.getIIdTipoDocumento());
                        objDD = setearCliente(objDD);
                        mapSession = ActionContext.getContext().getSession();
                        Usuario objUsuarioSession = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                        expediente = expedienteService.prepareExpediente(objDD, objUsuarioSession);
                        objDD.setIIdExpediente(expediente.getIdexpediente());
                        objDD.setStrFecha(expediente.getFechacreacion());
                        objDD.setObjAutor(new Usuario(objUsuarioSession.getIdUsuarioPerfil()));
                        objD.setAutor(objDD.getObjAutor());
                        objD.setRecepcionado(objDD.getEsTipoRecepcion());
                        objD.setConfidencial(objDD.getConfidencial());
                        objD.setIdUsuarioLogeado(objUsuarioSession.getIdusuario());
                        objD.setUnidadpropietario(objUsuarioSession.getIdUnidadPerfil());
                        objD.setUnidadautor(objUsuarioSession.getIdUnidadPerfil());
                        objD.setCargopropietario(objUsuarioSession.getIdFuncionPerfil());
                        objD.setUsuariocreacion(objUsuarioSession.getIdusuario());
                        objD.setFechaCreacion(fecha);
                        objD.setTipoDocumento(objTD);
                        objD.setPropietario(new Usuario(objUsuarioSession.getIdUsuarioPerfil()));
                        objD.setExpediente(expediente);
                        objD.setAccion(accionService.findByNombre(strAcc));
                        objD.setEstado(objDD.getCEstado());
                        objD.setAsunto(objDD.getStrAsunto());
                        objD.setUltimoAsunto(objDD.getStrAsunto());
                        objD.setEstaEnFlujo(Constantes.ESTAENFLUJO_S);
                        objD.setReferenciados(objDD.getStrReferencia());
                        Usuario u = usuarioService.findByIdUsuario(objUsuarioSession.getIdUsuarioPerfil());
                        objD.setRemitente(u.getNombres() + " " + u.getApellidos());
                        objD.setContenido(objDD.getStrContenido());
                        objD.setPrincipal(objDD.getCPrincipal());
                        objD.setNumeroDocumento(objDD.getStrNroDocumento());
                        objD.setPrioridad(objDD.getPrioridad());
                        objD.setProyecto(objDD.getProyecto()==null? Constantes.DOCUMENTO_FINAL: objDD.getProyecto());
                        objD.setDespachado('N');
                        objD.setFirmado('N');
                        objD.setIdConcesionario(null);
                        objD.setNumeroFoliosPIDE(objDD.getiNroFoliosPIDE());
                        
                        List<Parametro> lstParametro = parametroService.findByTipoActivo("TIPO_DOCUMENTO_REQUERIMIENTO_TRIBUTARIO");
                        if (lstParametro!=null && lstParametro.size()>0 && objDD.getOpcion().equals(Constantes.COD_TRAMITE_INTERNO)){
                            for(int i=0;i<lstParametro.size();i++){
                               if (lstParametro.get(i).getValor().equals(objTD.getIdtipodocumento().toString())){
                                 //objD.setIdConcesionario(new Integer(objDD.getConcesionario()));
                                   tipoDocReqTri=true;
                               }    
                            }
                        }    
                            
                        if (objDD.getConcesionario()!=null && !objDD.getConcesionario().trim().equals("")){
                          objD.setIdConcesionario(new Integer(objDD.getConcesionario()));
                        }
                        
                        if (objDD.getAnioFiscal()!=null && !objDD.getAnioFiscal().trim().equals(""))
                        {
                            objD.setAnioFiscal(new Integer(objDD.getAnioFiscal()));
                        }
                        
                        if (objDD.getStrUnidad()==null || objDD.getStrUnidad().trim().equals("")){
                          objD.setUnidadenumera(objUsuarioSession.getIdUnidadPerfil());
                        }else{
                            objD.setUnidadenumera(Integer.valueOf(objDD.getStrUnidad()));
                        }
                        
                        if (objDD.getPlazo()!=null){ 
                          if (objDD.getPlazo().equals("0") && objDD.getIPlazoDia()!=null && !objDD.getIPlazoDia().trim().equals("")){
                            objD.setPlazo(new Integer(objDD.getIPlazoDia()));
                            objD.setFechaLimiteAtencion(fechaLimite.getFechaLimite(fecha, Integer.parseInt(objDD.getIPlazoDia()))); 
                          }  
                          if (objDD.getPlazo().equals("1") && objDD.getStrFechaLimiteAtencion()!=null && !objDD.getStrFechaLimiteAtencion().trim().equals("")){
                            objD.setFechaLimiteAtencion(new SimpleDateFormat("yyyy-MM-dd").parse(objDD.getStrFechaLimiteAtencion()));
                            objD.setPlazo(fechaLimite.getPlazo(fecha , objD.getFechaLimiteAtencion()));
                          }
                        }
                        
                        objD.setID_CODIGO(null);
                        objD.setIdUsuarioLogeado(objUsuarioSession.getIdusuario());
                        objD.setCodRemitente(null);
                        objD.setCodTipoInstitucion(null);
                        objD.setCodCargoRemitente(null);
                        objD.setID_CLIENTE(null);
                        objD.setUsuariocreacion(objUsuarioSession.getIdusuario());
                        objD.setUnidadpropietario(objUsuarioSession.getIdUnidadPerfil());
                        objD.setCargopropietario(objUsuarioSession.getIdFuncionPerfil());
                        objD.setUnidadautor(objUsuarioSession.getIdUnidadPerfil());
                        objD.setBandeja(objDD.getBandeja());
                        
                        if(!StringUtils.isBlank(objD.getNumeroDocumento())){
                           objD.setEnumerado(Constantes.Si);
                        }

                        objD.setEnumerarDocumento("S".equals(objDD.getEnumerarDocumento()) ? true : false);

                        if (objD.getEnumerarDocumento()) {
                           objD.setTiponumeracion(objDD.getTipoNumeracion());
                        }

                        objD.setNumeroFolios(objDD.getINroFolios() == null ? 0 : objDD.getINroFolios());
                        
                        /////////////////////////////
                        objD.setNumeroFoliosOriginales(objDD.getiNroFoliosOriginales());
                        objD.setNumeroFoliosCopias(objDD.getiNroFoliosCopias());
                        objD.setImagenesDigitalizadas(objDD.getiNroFoliosDigitalizados());
                        ////////////////////////////
                        
                        objD.setObservacion(objDD.getStrObservacion());
                        objD.setFechaAccion(fecha);

                        try {
                                // Esto porque asi manda la fecha dojo
                                objD.setFechaDocumento(new SimpleDateFormat("yyyy-MM-dd").parse(objDD.getStrFechaDocumento()));
                        } catch (ParseException e) {
                                e.printStackTrace();
                                throw e;
                        }

                        objD.setFirmante(new Usuario(objUsuarioSession.getIdUsuarioPerfil()));
                        objD.setFirmado(Constantes.No);
                        objD.setLeido(Constantes.ESTADO_NO_LEIDO);
                        objD.setFechaCreacion(fecha);
                        objD.setCondestinatarios(objDD.getCondestinatarios());
                        objD.setConcopias(objDD.getConcopias());

                        if(objD.getEnumerado()==null){
                           objD.setEnumerado(Constantes.No);
                        }

                        if (objDD.getOpcion().equals(Constantes.COD_TRAMITE_INTERNO)){
                            if (objTD!=null && objTD.getExterno()!=null && objTD.getExterno().equals("1")){
                                if (objDD.getIdTipoCliente().equals(Constantes.COD_TIPOCLIENTE_EMPRESA)){
                                    Cliente c = clienteService.findByIdCliente(objDD.getIIdCliente());
                                    objD.setCodTipoInstitucion(c.getCodtipoinstitucion());
                                    objD.setID_CLIENTE(objDD.getIIdCliente());
                                    objD.setDesRemitente(objDD.getIdPersonaEmpresa());
                                    objD.setDesCargoRemitente(objDD.getCodCargoPersonaEmpresa());
                                }else{
                                    if (objDD.getIdTipoCliente().equals(Constantes.COD_TIPOCLIENTE_INSTITUCION)){
                                       Cliente c = clienteService.findByIdCliente(objDD.getIIdCliente());
                                       objD.setCodTipoInstitucion(c.getCodtipoinstitucion());
                                       objD.setID_CLIENTE(objDD.getIIdCliente());
                                       objD.setDesRemitente(objDD.getIdPersonaInstitucion());
                                       objD.setDesCargoRemitente(objDD.getCodCargoPersonaInstitucion()); 
                                       objD.setDesUnidadRemitente(objDD.getDesUnidadOrganica());
                                       objD.setNumeroFolios(objDD.getINroFolios() == null ? 0 : objDD.getINroFolios());
                                    } else{
                                       Cliente c = clienteService.findByIdCliente(objDD.getIIdCliente());
                                       objD.setCodTipoInstitucion(c.getCodtipoinstitucion());
                                       objD.setID_CLIENTE(objDD.getIIdCliente());
                                       objD.setDesCargoRemitente(objDD.getCodCargoPersonaNatural()); 
                                    }
                                }
                            }
                        }

                        if (objDD.getOpcion().equals(Constantes.COD_TRAMITE_EXTERNO)){
                            if (objDD.getIdTipoCliente().equals(Constantes.COD_TIPOCLIENTE_EMPRESA)){
                                Cliente c = clienteService.findByIdCliente(objDD.getIIdCliente());
                                objD.setCodTipoInstitucion(c.getCodtipoinstitucion());
                                objD.setID_CLIENTE(objDD.getIIdCliente());
                                objD.setDesRemitente(objDD.getIdPersonaEmpresa());
                                objD.setDesCargoRemitente(objDD.getCodCargoPersonaEmpresa());         
                            }else{
                                  if (objDD.getIdTipoCliente().equals(Constantes.COD_TIPOCLIENTE_INSTITUCION)){
                                    Cliente c = clienteService.findByIdCliente(objDD.getIIdCliente());
                                    objD.setCodTipoInstitucion(c.getCodtipoinstitucion());
                                    objD.setID_CLIENTE(objDD.getIIdCliente());
                                    objD.setDesRemitente(objDD.getIdPersonaInstitucion());
                                    objD.setDesCargoRemitente(objDD.getCodCargoPersonaInstitucion());  
                                    objD.setDesUnidadRemitente(objDD.getDesUnidadOrganica());
                                  } else{
                                         Cliente c = clienteService.findByIdCliente(objDD.getIIdCliente());
                                         objD.setCodTipoInstitucion(c.getCodtipoinstitucion());
                                         objD.setID_CLIENTE(objDD.getIIdCliente());
                                         objD.setDesCargoRemitente(objDD.getCodCargoPersonaNatural()); 
                                  }
                            }
                        }

                        ////////////////////////////////////

                        if (objDD.getTipoTransaccion().equals("N") || objDD.getTipoTransaccion().equals("R")){//NUEVO DOCUMENTO
                           objD.setFlaginicioflujo("1");
                           if (objDD.getOpcion().equals(Constantes.COD_TRAMITE_EXTERNO)){ 
                             objD.setID_EXTERNO(1);
                           }else{
                             objD.setID_EXTERNO(0);
                           }
                        }else{
                           objD.setID_EXTERNO(0);   
                        }    

                        if (objD.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_402)){
                            Cliente c = clienteService.findByIdCliente(objDD.getIdInstitucionSicor());
                            objD.setCodTipoInstitucion(c.getCodtipoinstitucion());
                            
                            objD.setID_CLIENTE(objDD.getIdInstitucionSicor());
                            objD.setCodInfraestructura(objDD.getiIdInfraestructura());
                            objD.setCodMateria(objDD.getiIdMateria());
                        }

                        if (objD.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_405)){
                            Cliente c = clienteService.findByIdCliente(objDD.getIdInstitucionSicor());
                            objD.setCodTipoInstitucion(c.getCodtipoinstitucion());
                            
                            objD.setID_CLIENTE(objDD.getIdInstitucionSicor());
                            objD.setFechaReunion(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(objDD.getStrFechaReunion() + " " + objDD.getStrHoraReunion().substring(1)));
                            objD.setLugar(objDD.getStrLugar());
                            objD.setObjetivo(objDD.getStrObjetivo());
                        }

                        objDD.setIdExterno(objD.getID_EXTERNO().toString());
                        //JCLOG
                        objD.setIdDocumento(documentoDao.getNroDocumento());
                        objDD.setDoc(objD);
                        objDD.setFechacreacion(objD.getFechaCreacion());
                        ///////////////////////////////////fin////////////////////////////////////////
                        idDocumento = objD.getIdDocumento();
                        
                        if (idDocumento != null && idDocumento.intValue() != 0) {
                                    //Documento doc = this.findByIdDocumento(idDocumento);
                                    if (objD.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_404)){
                                        if (objDD.getListaIntegrantesInternos()!=null && objDD.getListaIntegrantesInternos().trim().length()>0){
                                             String[] lista = objDD.getListaIntegrantesInternos().trim().equals("")?null: StringUtil.stringToArrayPersonalizado(documentoDetail.getListaIntegrantesInternos().trim(),'|');
                                            for(String fila : lista){
                                                DocumentoReunion documentoReunion = new DocumentoReunion();
                                                documentoReunion.setEstado("A");
                                                documentoReunion.setFechaCreacion(fecha);
                                                documentoReunion.setIdDocumento(objD.getIdDocumento());
                                                documentoReunion.setNombres(fila);
                                                documentoReunion.setUsuarioCreacion(objUsuarioSession.getIdusuario());
                                                documentoReunion.setTipo("0");
                                                documentoReunionDAO.saveDocumentoReunion(documentoReunion);
                                            }
                                        }
                                        if (objDD.getListaIntegrantesExternos()!=null && objDD.getListaIntegrantesExternos().trim().length()>0){
                                            String[] lista = objDD.getListaIntegrantesExternos().trim().equals("")?null: StringUtil.stringToArrayPersonalizado(documentoDetail.getListaIntegrantesExternos().trim(),'|');
                                            for(String fila : lista){
                                                //String[] lista = StringUtil.stringToArray(fila);    
                                                DocumentoReunion documentoReunion = new DocumentoReunion();
                                                documentoReunion.setEstado("A");
                                                documentoReunion.setFechaCreacion(fecha);
                                                documentoReunion.setIdDocumento(objD.getIdDocumento());
                                                documentoReunion.setNombres(fila);
                                                documentoReunion.setUsuarioCreacion(objUsuarioSession.getIdusuario());
                                                documentoReunion.setTipo("1");
                                                documentoReunionDAO.saveDocumentoReunion(documentoReunion);
                                            }
                                        }
                                    }
                               // }
                            
                               // doc = this.saveDocumento(doc);
                                //TIPO TRANSACCION: NUEVO/AGREGAR/MODIFICAR
                                //OPCION: QUIEN CREA EL DOCUMENTO INTERNO (AREAS) O EXTERNO(TRAMITE)
                                //INTERNOEXTERNO: INTERNO SI EL DOCUMENTO VA DENTRO DE LAS AREAS O A UNA INSTITUCION

				if (objDD.getTipoTransaccion().equals("N") || objDD.getTipoTransaccion().equals("R")){//NUEVO DOCUMENTO 
                                      if (objDD.getOpcion().equals(Constantes.COD_TRAMITE_EXTERNO)){ 
                                         List<Item> items = (List<Item>)mapSession.get("AdjuntosDocumento");  
                                
                                         if (items!=null && items.size()>0){
                                            for(int i=0;i<items.size();i++){
                                                DocumentoAdjunto d = new DocumentoAdjunto();
                                                d.setCopOrig(items.get(i).getTipoAdjunto());
                                                d.setCodTipoAdj(items.get(i).getTipo());
                                                d.setNroAdj(new Integer(items.get(i).getNro()));
                                                d.setEstado("A");
                                                d.setIdDocumento(idDocumento);
                                                d.setFechaCreacion(fecha);
                                                d.setUsuarioCreacion(objUsuarioSession.getIdusuario());
                                                documentoAdjuntoDAO.registrarDocumentoAdjunto(d);  
                                           }
                                         }
                                         
                                         String[] listaPrincipal = StringUtil.stringToArrayPersonalizado(objDD.getListaDerivacionPara().trim(),'|');
                                         String[] listaCC        = objDD.getListaDerivacionCC().trim().equals("")? null : StringUtil.stringToArrayPersonalizado(objDD.getListaDerivacionCC().trim(),'|');
                                        
                                         if (listaPrincipal!=null && listaPrincipal.length>0){
                                            for (int i=0;i<listaPrincipal.length;i++){
                                                 String[] datos = listaPrincipal[i].split("-");
                                                 DocumentoDerivacion documentoDerivacion = new DocumentoDerivacion();
                                                 documentoDerivacion.setEstado("A");
                                                 documentoDerivacion.setIddocumento(idDocumento);
                                                 documentoDerivacion.setTipo("P");
                                                 documentoDerivacion.setIdusuario(new Integer(datos[0]));
                                                 documentoDerivacion.setUnidadpropietario(new Integer(datos[1]));
                                                 documentoDerivacion.setCargopropietario(new Integer(datos[2]));
                                                 documentoDerivacion.setFechacreacion(fecha);
                                                 documentoDerivacion.setUsuariocreacion(objUsuarioSession.getIdusuario());
                                                 documentoDerivacionDAO.guardar(documentoDerivacion);
                                            }    
                                         }
                                         if (listaCC!=null && listaCC.length>0){
                                            for(int i=0;i<listaCC.length;i++){
                                               String[] datos = listaCC[i].split("-");
                                                 DocumentoDerivacion documentoDerivacion = new DocumentoDerivacion();
                                                 documentoDerivacion.setEstado("A");
                                                 documentoDerivacion.setIddocumento(idDocumento);
                                                 documentoDerivacion.setTipo("C");
                                                 documentoDerivacion.setIdusuario(new Integer(datos[0]));
                                                 documentoDerivacion.setUnidadpropietario(new Integer(datos[1]));
                                                 documentoDerivacion.setCargopropietario(new Integer(datos[2]));
                                                 documentoDerivacion.setFechacreacion(fecha);
                                                 documentoDerivacion.setUsuariocreacion(objUsuarioSession.getIdusuario());
                                                 documentoDerivacionDAO.guardar(documentoDerivacion); 
                                            } 
                                         }
                                        
                                       }
                                      
                                      objD.setOrigen(objD.getIdDocumento());
                                      String[] listDocReferenciados = null;
                                      //fecha = new Date();
                                      if (objDD.getListReferenciados()!=null){
                                           listDocReferenciados = objDD.getListReferenciados().trim().equals("")?null:StringUtil.stringToArrayPersonalizado(objDD.getListReferenciados().trim(),'|');
                                      }
                                      
                                      if (listDocReferenciados!=null && listDocReferenciados.length>0){
                                          for(int i=0;i<listDocReferenciados.length;i++){
                                             DocumentoReferencia docReferencia  = new DocumentoReferencia();
                                             docReferencia.setIdDocumento(idDocumento);
                                             docReferencia.setIdDocumentoReferencia(new Integer(listDocReferenciados[i]));
                                             docReferencia.setEstado("A");
                                             docReferencia.setFechaCreacion(fecha);
                                             docReferencia.setVerDocumento(verDocumento(new Integer(listDocReferenciados[i]),objUsuarioSession));
                                             docReferencia.setUsuarioCreacion(objUsuarioSession.getIdusuario());
                                             documentoReferenciaDAO.saveDocumentoReferencia(docReferencia);
                                             
                                             if (objD.getTipoDocumento().getExternoQR()!= null && objD.getTipoDocumento().getExternoQR().equals("1")){
                                                Documento d = documentoDao.findByIdDocumento(new Integer(listDocReferenciados[i]));
                                                List<Archivo> lst = archivoService.findLstByIdDocumento(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia());

                                                if (lst!=null && lst.size()>0){
                                                  for(int k=0;k<lst.size();k++){
                                                      ReferenciaArchivo referenciaArchivo = new ReferenciaArchivo();
                                                      referenciaArchivo.setIdDocumento(idDocumento);
                                                      referenciaArchivo.setIdDocumentoReferencia(new Integer(listDocReferenciados[i]));
                                                      referenciaArchivo.setEstado("A");
                                                      referenciaArchivo.setFechaCreacion(new Date());
                                                      referenciaArchivo.setUsuarioCreacion(objUsuarioSession.getIdusuario());
                                                      referenciaArchivo.setIdArchivo(lst.get(k).getIdArchivo());
                                                      referenciaArchivoDAO.saveReferenciaArchivo(referenciaArchivo);
                                                  }
                                                }
                                             }
                                          }  
                                      } 
                                      
				}else{  //AGREGAR DOCUMENTO
                                        String[] listDocReferenciados = null; 
                                        objD.setOrigen(objDD.getOrigen());
                                        //fecha = new Date();
                                        if (objDD.getiIdLegajo()!=null && objDD.getiIdLegajo()!=0){
                                           LegajoDocumento legajoDocumento = new LegajoDocumento();  
                                           legajoDocumento.setIdLegajo(objDD.getiIdLegajo());
                                           legajoDocumento.setEstado("A");
                                           legajoDocumento.setIdDocumento(idDocumento);
                                           legajoDocumento.setFechaCreacion(new Date());
                                           legajoDocumento.setUsuarioCreacion(objUsuarioSession.getIdusuario());
                                           legajoDocumentoDAO.saveLegajoDocumento(legajoDocumento);
                                        }
                                        
                                        if (objDD.getListReferenciados()!=null)
                                            listDocReferenciados =  objDD.getListReferenciados().trim().equals("")?null:StringUtil.stringToArrayPersonalizado(objDD.getListReferenciados().trim(),'|');
                                        if (listDocReferenciados!=null && listDocReferenciados.length>0){
                                            for (int i=0;i<listDocReferenciados.length;i++){
                                                DocumentoReferencia docReferencia  = new DocumentoReferencia();
                                                docReferencia.setIdDocumento(objD.getIdDocumento()); 
                                                docReferencia.setIdDocumentoReferencia(new Integer(listDocReferenciados[i]));
                                                docReferencia.setUsuarioCreacion(objUsuarioSession.getIdusuario());
                                                docReferencia.setFechaCreacion(fecha);
                                                docReferencia.setVerDocumento(verDocumento(new Integer(listDocReferenciados[i]),objUsuarioSession));
                                                docReferencia.setEstado("A");
                                                documentoReferenciaDAO.saveDocumentoReferencia(docReferencia);
                                                
                                                if (objD.getTipoDocumento().getExternoQR()!= null && objD.getTipoDocumento().getExternoQR().equals("1")){
                                                    Documento d = documentoDao.findByIdDocumento(new Integer(listDocReferenciados[i]));
                                                    List<Archivo> lst = archivoService.findLstByIdDocumento(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia());

                                                    if (lst!=null && lst.size()>0){
                                                      for(int k=0;k<lst.size();k++){
                                                          ReferenciaArchivo referenciaArchivo = new ReferenciaArchivo();
                                                          referenciaArchivo.setIdDocumento(idDocumento);
                                                          referenciaArchivo.setIdDocumentoReferencia(new Integer(listDocReferenciados[i]));
                                                          referenciaArchivo.setEstado("A");
                                                          referenciaArchivo.setFechaCreacion(new Date());
                                                          referenciaArchivo.setUsuarioCreacion(objUsuarioSession.getIdusuario());
                                                          referenciaArchivo.setIdArchivo(lst.get(k).getIdArchivo());
                                                          referenciaArchivoDAO.saveReferenciaArchivo(referenciaArchivo);
                                                      }
                                                    } 
                                                } 
                                            }
                                        }
                                }
                                
                                if (objD.getID_EXTERNO()!=null && objD.getID_EXTERNO()==0){ // && (objTD.getSicor()==null || !objTD.getSicor().equals("1"))){
                                     List<Usuarioxunidadxfuncion> lista =usuarioxunidadxfuncionDAO.getUsuarioByUnidadByFuncionRol(objUsuarioSession);
                                     if (lista!=null && lista.size()>0 && objD.getProyecto()!=null && !objD.getProyecto().toString().equals(Constantes.DOCUMENTO_PROYECTO.toString()) && !lista.get(0).getIdrol().toString().equals(Constantes.COD_ROL_LOCADOR.toString())){
                                          objD.setFirmado('S');
                                     }
                                }
                                
                                /**Numeracion--------------------------------------------------------------------------------------------------------*/
                                if(objD.getEnumerarDocumento() && objD.getTiponumeracion().equals(Constantes.NUMERACION_AUTOMATICA) && objD.getEnumerado().equals(Constantes.No)){
                                    Unidad unid = null;
                                    if (objDD.getStrUnidad()==null || objDD.getStrUnidad().trim().equals(""))
                                    {
                                        unid = new Unidad(objUsuarioSession.getIdUnidadPerfil());
                                    }
                                    else{
                                        if (objD!=null && objD.getIdConcesionario()!=null && tipoDocReqTri){
                                           //unid = new Unidad(Integer.parseInt(parametroService.findByTipoUnico("UNIDAD_REQUERIMIENTO_TRIBUTARIO").getValor()));         
                                           Cliente cliente = clienteService.findByIdCliente(objD.getIdConcesionario());
                                           Integer idUnidad = cliente.getIdUnidad();
                                           unid = new Unidad(idUnidad);

                                        }else{
                                           unid = new Unidad(Integer.parseInt(objDD.getStrUnidad()));
                                        }  
                                    }    
                                    
                                    if (objD.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.TIPO_INFORME_CONJUNTO) || objD.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.TIPO_MEMORANDO_CONJUNTO))
                                      unid = new Unidad(new Integer(parametroService.findByTipo(Constantes.PARAMETRO_UNIDAD_INFORME_CONJUNTO).get(0).getValor()));
                                    
                                    //List<Numeracion> listaNum = numeracionDAO.findAllUnidadAndTipoDoc(unid.getIdunidad(),objD.getTipoDocumento().getIdtipodocumento());
                                    List<Numeracion> listaNum = null;
                                    if(objD.getTipoDocumento().getIdtipodocumento().toString().equals(parametroService.findByTipoUnico("TIPO_DOCUMENTO_REQUERIMIENTO_TRIBUTARIO").getValor()))
                                    {
                                        listaNum = numeracionDAO.findAllUnidadAndTipoDocAndAnioFiscal(unid.getIdunidad(),objD.getTipoDocumento().getIdtipodocumento(),objD.getAnioFiscal());
                                    }else
                                    {
                                        listaNum = numeracionDAO.findAllUnidadAndTipoDoc(unid.getIdunidad(),objD.getTipoDocumento().getIdtipodocumento());
                                    }                                    
                                    
                                    if (listaNum != null && listaNum.size() > 0 && listaNum.get(0).getTiponumeracion() != null) {
                                        String nro = numeracionDAO.guardarObjProcedure(listaNum.get(0), unid.getIdunidad(), objUsuarioSession.getIdusuario().intValue());
                                        objD.setNumeroDocumento(nro);
                                        objD.setEnumerado(Constantes.Si);
                                    }
                                       
                                    DateFormat formatoAno = new SimpleDateFormat("yyyy");
                                    String sPrefijo = Constantes.PARAMETRO_TIPO_PREFIJONROTRAMITEPRODUCCION;
                                    Integer nro = documentoDao.getNroTramiteDocumentario();
                                    String sFormato = formatoAno.format(fecha).concat(parametroService.findByTipoUnico(sPrefijo).getValor());
                                    StringBuilder sbNroTramite = new StringBuilder((StringUtil.isEmpty(sFormato)) ? "" : sFormato);
                                    sbNroTramite.replace(sFormato.length() - nro.toString().length(), sFormato.length(), nro.toString());
                                    objD.setID_CODIGO(new Integer(sbNroTramite.toString()));
                                    documentoDao.saveDocumentoSequence(objD);
                                    objDD.setNroTramite(objD.getID_CODIGO().toString());
                                    objDD.setStrNroDocumento(objD.getNumeroDocumento());
                                }else{
                                    DateFormat formatoAno = new SimpleDateFormat("yyyy");
                                    Integer nro = documentoDao.getNroTramiteDocumentario();
                                    String sPrefijo = Constantes.PARAMETRO_TIPO_PREFIJONROTRAMITEPRODUCCION;
                                    String sFormato = formatoAno.format(fecha).concat(parametroService.findByTipoUnico(sPrefijo).getValor());
                                    StringBuilder sbNroTramite = new StringBuilder((StringUtil.isEmpty(sFormato)) ? "" : sFormato);
                                    sbNroTramite.replace(sFormato.length() - nro.toString().length(), sFormato.length(), nro.toString());
                                    objD.setID_CODIGO(new Integer(sbNroTramite.toString()));
                                    objDD.setNroTramite(objD.getID_CODIGO().toString());
                                    documentoDao.saveDocumentoSequence(objD);
                                   
                                    if (objDD.getTipoTransaccion().equals("R") && objDD.getCodigoVirtual()!=null && !objDD.getCodigoVirtual().trim().equals("")){
                                        try{
                                            iotdtmDocExterno = documentoExternoVirtualDAO.buscarDocumentoVirtual(new Integer(objDD.getCodigoVirtual().trim()));
                                            IotdtcRecepcion iotdtcRecepcion = iotdtmDocExterno.getSidrecext();
                                            iotdtcRecepcion.setIddocumento(objD.getIdDocumento());
                                            recepcionVirtualDAO.registrarDocumento(iotdtcRecepcion);
                                            objD.setNroVirtual(new Integer(objDD.getCodigoVirtual()));
                                            objD.setFirmado('S');

                                            //JBENGOA INICIO
                                            List<Archivo> archivosSubidos = new ArrayList<Archivo>();
                                            Usuario usuario = (Usuario) session.get(Constantes.SESSION_USUARIO);        
                                            Unidad unidad = null;

                                            if(objD.getTipoDocumento().getIdtipodocumento() == Integer.parseInt(parametroService.findByTipoUnico("TIPO_DOCUMENTO_REQUERIMIENTO_TRIBUTARIO").getValor()) ||
                                               objD.getTipoDocumento().getIdtipodocumento() == Integer.parseInt(parametroService.findByTipoUnico("TIPO_DOCUMENTO_RESULTADO_TRIBUTARIO").getValor())){
                                               unidad = unidadService.buscarObjPor(Integer.parseInt(parametroService.findByTipoUnico("UNIDAD_GSF").getValor()));
                                            }else{
                                               unidad = unidadService.buscarObjPor(usuario.getIdUnidadPerfil());
                                            }

                                            iotdtmDocExterno = documentoExternoVirtualDAO.buscarDocumentoVirtual(new Integer(objDD.getCodigoVirtual().trim()));
                                            int pos = iotdtmDocExterno.getIotdtdDocPrincipalList().get(0).getVnomdoc().lastIndexOf(".");
                                            String extension = iotdtmDocExterno.getIotdtdDocPrincipalList().get(0).getVnomdoc().substring(pos+1, iotdtmDocExterno.getIotdtdDocPrincipalList().get(0).getVnomdoc().length());

                                            String sNuevoNombrePrincipal="["+objD.getIdDocumento()+"_"+DateFormatUtils.format(fecha,"yyyyMMddHHmmss")+"_"+"1"+"]"+objD.getID_CODIGO() + "_" + objD.getTipoDocumento().getNombre() + "." + extension;
                                            String sNuevoNombreCargo="["+objD.getIdDocumento()+"_"+DateFormatUtils.format(fecha,"yyyyMMddHHmmss")+"_"+"1"+"]"+objD.getID_CODIGO() + "_CARGO_VIRTUAL_" + objD.getTipoDocumento().getNombre() + "." + extension;
                                            String rutaDig=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_TEMPORAL_ALFRESCO);

                                            OutputStream out = new FileOutputStream(rutaDig + sNuevoNombrePrincipal); 
                                            out.write(iotdtmDocExterno.getIotdtdDocPrincipalList().get(0).getBpdfdoc()); 
                                            out.close();

                                            OutputStream out_ = new FileOutputStream(rutaDig + sNuevoNombreCargo); 
                                            out_.write(iotdtmDocExterno.getIotdtdDocPrincipalList().get(0).getBpdfdoc()); 
                                            out_.close();
                                            
                                            File f=new File(rutaDig,sNuevoNombreCargo);

                                            Archivo objArchivo = new Archivo();
                                            objArchivo.setDocumento(objD);
                                            objArchivo.setNombre(sNuevoNombrePrincipal);
                                            objArchivo.setPrincipal('S');
                                            objArchivo.setEstadoDigitalizacion(Constantes.ARCHIVO_ESTADO_DIGITALIZACION_YES);
                                            objArchivo.setFechaCreacion(new Date());
                                            objArchivo.setRutaArchivoPdf(rutaDig + sNuevoNombrePrincipal);
                                            objArchivo.setRutaAlfresco(repositorioService.obtenerRutaDocumento(objD, unidad.getRutaSite(), objD.getTipoDocumento().getCodigo())+ objD.getID_CODIGO() + "_" + objD.getTipoDocumento().getNombre() + "." + extension);
                                            objArchivo.setAutor(new Usuario(usuario.getIdUsuarioPerfil()));
                                            objArchivo.setUnidadAutor(usuario.getIdUnidadPerfil());
                                            objArchivo.setUsuariocreacion(usuario.getIdusuario());
                                            objArchivo.setUsuariomodificacion(usuario.getIdusuario());
                                            objArchivo.setClave(null);
                                            try{
                                                objArchivo.setTamano(Integer.valueOf((int)f.length()));
                                            }catch(Exception e){
                                                e.printStackTrace();
                                                objArchivo.setTamano(null);
                                            }
                                            objArchivo.setEstado(Constantes.ESTADO_ACTIVO);
                                            objArchivo = archivoService.saveArchivo(objArchivo);
                                            archivosSubidos.add(objArchivo);

                                            objArchivo = new Archivo();
                                            objArchivo.setDocumento(objD);
                                            objArchivo.setNombre(sNuevoNombreCargo);
                                            objArchivo.setPrincipal('M');
                                            objArchivo.setEstadoDigitalizacion(Constantes.ARCHIVO_ESTADO_DIGITALIZACION_YES);
                                            objArchivo.setFechaCreacion(new Date());
                                            objArchivo.setRutaArchivoPdf(rutaDig + sNuevoNombreCargo);
                                            objArchivo.setRutaAlfresco(repositorioService.obtenerRutaDocumento(objD, unidad.getRutaSite(), objD.getTipoDocumento().getCodigo())+objD.getID_CODIGO() + "_CARGO_VIRTUAL_" + objD.getTipoDocumento().getNombre() + "." + extension);
                                            objArchivo.setAutor(new Usuario(usuario.getIdUsuarioPerfil()));
                                            objArchivo.setUnidadAutor(usuario.getIdUnidadPerfil());
                                            objArchivo.setUsuariocreacion(usuario.getIdusuario());
                                            objArchivo.setUsuariomodificacion(usuario.getIdusuario());
                                            objArchivo.setClave(null);
                                            objArchivo.setEstado(Constantes.ESTADO_ACTIVO);
                                            objArchivo = archivoService.saveArchivo(objArchivo);
                                            archivosSubidos.add(objArchivo);
                                            repositorioService.subirArchivosTransformadosARepositorio(objD, archivosSubidos, false, usuario, unidad.getRutaSite(), objD.getTipoDocumento().getCodigo());
                                        }catch(Exception e){
                                            throw e;
                                        }
                                    } ////JBENGOA FIN
                                }
                                log.info("trazabilidadDocumentoService is " + trazabilidadDocumentoService);
                                trazabilidadDocumentoService.saveTrazabilidadDocumento(objD, objUsuarioSession, false, true);
                                objDD.setIIdDocumento(objD.getIdDocumento());
                                objDD.setsNroExpediente(expediente.getNroexpediente());
                                String nrazon = expediente.getCliente().getNombreRazon();
                                objDD.setStrTipoIdentificacion(expediente.getCliente().getTipoIdentificacion().getNombre());
                                objDD.setStrRazonSocial(nrazon);
                                objDD.setStrNroIdentificacion(expediente.getCliente().getNumeroIdentificacion());
                                objDD.setStrDireccionPrincipal(expediente.getCliente().getDireccionPrincipal());
                                objDD.setStrDireccionAlternativa(expediente.getCliente().getDireccionAlternativa());
                                objDD.setStrTelefonoCliente(expediente.getCliente().getTelefono());
                                objDD.setStrCorreoCliente(expediente.getCliente().getCorreo());
                                objDD.setStrRepresentanteLegal(expediente.getCliente().getRepresentanteLegal());
                       }
		    return objDD;
		
		} catch (Exception e) {
                    log.error(e.getMessage(), e);
		    throw e;
		}
	}

	public List<Recurso> getCountDocuments(Usuario usuario) {
		log.debug("-> [Service] DocumentoService - getCountDocuments():List<Recurso> ");

		try {
			return documentoDao.getCountDocuments(usuario);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			return new ArrayList<Recurso>();
		}
	}

	@Override
	@Transactional
	public void guardarDocumento(Documento documento) {
		log.debug("-> [Service] DocumentoService - guardarDocumento():void ");

		try {
			documentoDao.guardarDocumento(documento);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public boolean perteneceAccesoProcAcc3(Usuario u, Documento d) {
		log.debug("-> [Service] DocumentoService - perteneceAccesoProcAcc3():boolean ");

		boolean forward = false;

		List<Integer> data = trazabilidadDocumentoService.findLstUsuarioDelProcAcc3(d);
		for (Integer id : data) {
			if (u.getIdusuario().equals(id)) {
				forward = true;
			}
		}
		return forward;
	}

	public boolean perteneceAccesoProcAcc2(Usuario u, Proceso p) {
		log.debug("-> [Service] DocumentoService - perteneceAccesoProcAcc2():boolean ");

		boolean forward = false;

		List<Integer> data = trazabilidadDocumentoService.findLstUsuarioDelProcAcc2(p);
		for (Integer id : data) {
			if (u.getIdusuario().equals(id)) {
				forward = true;
			}
		}
		return forward;
	}

	private Notificacion getNotificacionDestinatario(Documento objDocumento, Usuario destinatario, Date fecha, char estado) {
		log.debug("-> [Service] DocumentoService - getNotificacionDestinatario():Notificacion ");

		Notificacion noti = new Notificacion();
		noti.setEstado(estado);
		noti.setIdusuario(destinatario);
		noti.setIddocumento(objDocumento);
		noti.setFechanotificacion(fecha);
		noti.setTiponotificacion(Constantes.TIPO_NOTIFICACION_NUMERACION_DESTINATARIO);
		noti.setAsunto("Enumeracion de Documento ");
		noti.setContenido(destinatario.getNombres() + " " + destinatario.getApellidos() + ":<br /><br/>"
				+ "Se le notifica que "
				+ " <strong>" + objDocumento.getPropietario().getNombres() + " " + objDocumento.getPropietario().getApellidos()
				+ "</strong>, ha realizado la numeracion del del documento  " + objDocumento.getTipoDocumento() + " "
				+ objDocumento.getNumeroDocumento() + " perteneciente al expediente "
				+ "<strong>" + objDocumento.getExpediente().getNroexpediente() + "</strong>");
		noti.setLeido(Constantes.ESTADO_NO_LEIDO);

		return noti;
	}

	private Notificacion getNotificacionCopia(Documento objDocumento, Usuario destinatario, Date fecha, char estado) {
		log.debug("-> [Service] DocumentoService - getNotificacionCopia():Notificacion ");

		Notificacion noti = new Notificacion();
		noti.setEstado(estado);
		noti.setIdusuario(destinatario);
		noti.setIddocumento(objDocumento);
		noti.setFechanotificacion(fecha);
		noti.setTiponotificacion(Constantes.TIPO_NOTIFICACION_NUMERACION_DOCUMENTOCONCOPIA);
		noti.setAsunto("Enumeracion de Documento ");
		noti.setContenido(destinatario.getNombres() + " " + destinatario.getApellidos() + ":<br /><br/>"
				+ "Se le notifica que "
				+ " <strong>" + objDocumento.getPropietario().getNombres() + " " + objDocumento.getPropietario().getApellidos()
				+ "</strong>, ha realizado la numeracion del del documento  " + objDocumento.getTipoDocumento() + " "
				+ objDocumento.getNumeroDocumento() + " perteneciente al expediente "
				+ "<strong>" + objDocumento.getExpediente().getNroexpediente() + "</strong>");
		noti.setLeido(Constantes.ESTADO_NO_LEIDO);

		return noti;
	}

	@Override
	@Transactional
	public List<Documento> modifyDocuments(Usuario usuario, Map<Integer, Integer> documentsToModify,String tipoNumeracion) throws Exception{
		log.debug("-> [Service] DocumentoService - modifyDocuments():List<Documento> ");
               
		List<Documento> lstDocumento = new ArrayList<Documento>();
                try{
                    for (Integer idDocumento : documentsToModify.keySet()) {
                            Documento documento = this.findByIdDocumento(idDocumento);
                            documento = guardarDocEnumerados(documento, tipoNumeracion, usuario);
                            lstDocumento.add(documento);		
                    }
                }catch(Exception e){
                    throw e;
                }
		return lstDocumento;
	}
	public Documento aprobarDocumentoMasivo(Documento docAntiguo, Documento docNuevo, List<String> conCopia, Etapa objEtapa){
		log.debug("-> [Service] DocumentoService - aprobarDocumentoMasivo():Documento ");

		return null;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	public Documento aprobarDocumentoMasivo(DocumentoDetail objDD, Usuario objRemitente, Integer iIdDestinatario, String sTipoDerivacion, DocumentoDetail objDDD, List<String> conCopia, String nombrePC,Boolean horarioPermitido){
		log.debug("-> [Service] DocumentoService - aprobarDocumentoMasivo():Documento ");

		Accion objAccion = null;
		Documento objDocumentoPrincipal = null;
		Expediente objExpediente = null;
		List<Documento> lstDocumento = null;
		Usuario objDestinatario = null;
		Trazabilidaddocumento trazdoc = new Trazabilidaddocumento();
		log.debug(" derivar accion : " + objDD.getStrAccion());
		objAccion = accionService.findByNombre(objDD.getStrAccion());
		objDocumentoPrincipal = this.findByIdDocumento(objDD.getIIdDocumento());
		if (objDocumentoPrincipal.getContenido() == null) {
			objDocumentoPrincipal.setContenido(objDD.getStrTexto());
		}

		log.debug("princpal:" + objDocumentoPrincipal.getPrincipal());
		objExpediente = objDocumentoPrincipal.getExpediente();
		/*objExpediente.setIdetapa(objEtapa);
		if (objExpediente.getExpedientestor() != null) {
			objExpediente.getExpedientestor().setEtapa(objEtapa);
		}*/
		expedienteService.saveExpediente(objExpediente);
		String asunto = null;
		String contenido = null;
		if (sTipoDerivacion.equalsIgnoreCase(Constantes.DERIVAR_NORMAL)) {
			asunto = objDD.getStrAsunto();
			contenido = objDD.getStrTexto();
		} else if (sTipoDerivacion.equalsIgnoreCase(Constantes.DERIVAR_MASIVO)) {
			asunto = objExpediente.getDocumentoPrincipal().getAsunto();
			String strAux = "<br><br><br><br><br><br>";
			strAux += "<p>--------------- Mensaje Original ---------------</p> "
				+ "<p>De : " + objDDD.getStrRemitente() + "</p>"
				+ "<p>Para : " + objDDD.getStrDestinatario() + "</p>"
				+ "<p>CC : " + "</p>"
				+ "<p>Enviado : " + objDDD.getStrFecha() + "</p>"
				+ "<p>Enviado : " + objDDD.getStrAsunto() + "</p>"
				+ "<p>----------------------------------------------</p>";
			contenido = objDD.getStrTexto() + strAux + " " + this.fillContenido(objDDD);
		}
		objDestinatario = usuarioService.findByIdUsuario(iIdDestinatario);
		log.debug("asunto:" + objDD.getStrAsunto());
		List<Documento> documentosExpediente = new ArrayList<Documento>();
		lstDocumento = documentoDao.getDocumentosPorExpediente(objExpediente.getIdexpediente());
		Documento ultimo = lstDocumento.get(0);
		Calendar ultimoDocumento = Calendar.getInstance();
		ultimoDocumento.setTime(ultimo.getFechaCreacion());
		for (Documento doc : lstDocumento) {
			doc.setPrincipal(Constantes.DOCUMENTO_NO_PRINCIPAL);
			Calendar actual = Calendar.getInstance();
			actual.setTime(doc.getFechaCreacion());
			if (actual.compareTo(ultimoDocumento) > 0) {
				ultimo = doc;
			}
			documentosExpediente.add(doc);
		}
		ultimo.setPrincipal(Constantes.DOCUMENTO_PRINCIPAL);
		// Derivar todos los documentos del expediente al destinatario
		for (Documento objDocumentoAMover : documentosExpediente) {
			// Actualizando datos del documento
			/*Si el documento aun esta en mp/dig/qas no cambiar los datos del documento*/
			/* if ((!objDocumentoAMover.getAccion().getNombre().equals(Constantes.ACCION_APROBAR)
                    && !objDocumentoAMover.getAccion().getNombre().equals(Constantes.ACCION_DIGITALIZAR))
                    || (objDocumentoAMover.getAccion().getNombre().equals(Constantes.ACCION_APROBAR)
                    && objDocumentoAMover.getPropietario().getRol() == null))*/
			//
			//@Danna Incidencia 135 no se guarda la trabalidad de todos los documentos cuadno es aprobar
			//

			if ((!objDocumentoAMover.getAccion().getNombre().equals(Constantes.ACCION_DIGITALIZAR))
					|| (objDocumentoAMover.getPropietario().getRol() == null))

			{
				objDocumentoAMover.setPropietario(objDestinatario);
				objDocumentoAMover.setRemitente(objRemitente.getNombres() + " " + objRemitente.getApellidos());
				objDocumentoAMover.setAccion(objAccion);
				if (objDocumentoAMover.getPrincipal() == Constantes.DOCUMENTO_PRINCIPAL) {
					if (sTipoDerivacion.equalsIgnoreCase(Constantes.DERIVAR_NORMAL)) {
						// David says:segun jona el asunto de los documentos no cambia
						// a menos de que este sea nuevo  :P
						if (objDocumentoAMover.getAsunto() == null) {
							objDocumentoAMover.setAsunto(objDD.getStrAsunto());
						}
						if (objDocumentoAMover.getContenido() == null) {
							objDocumentoAMover.setContenido(objDD.getStrTexto());
						}

						objDocumentoAMover.setUltimoAsunto(objDD.getStrAsunto());
					} else if (sTipoDerivacion.equalsIgnoreCase(Constantes.DERIVAR_MASIVO)) {
						String strAux = "<br><br><br><br><br><br>";
						strAux += "<p>--------------- Mensaje Original ---------------</p> " + "<p>De : " + objDDD.getStrRemitente() + "</p>" + "<p>Para : " + objDDD.getStrDestinatario() + "</p>" + "<p>CC : " + "</p>" + "<p>Enviado : " + objDDD.getStrFecha() + "</p>" + "<p>Enviado : " + objDDD.getStrAsunto() + "</p>" + "<p>----------------------------------------------</p>";
						if (objDocumentoAMover.getContenido() == null) {
							objDocumentoAMover.setContenido(objDD.getStrTexto() + strAux + " " + this.fillContenido(objDDD));
						}
					}
					objDocumentoAMover.setEstadoAlarma('V');
				}
				objDocumentoAMover.setFechaAccion(new Date());
			}
			objDocumentoAMover.setLeido(Constantes.ESTADO_NO_LEIDO);
			objDocumentoAMover = this.saveDocumento(objDocumentoAMover);
			log.debug("Generando trazabilidad Nro Expediente [" + objExpediente.getNroexpediente() + "] ID Documento [" + objDocumentoAMover.getIdDocumento() + "]");
			// / Modificacion David Aranda para los documento enviados :INICIO

			/*Si el documento aun esta en mp/dig/qas no cambiar datos ni guardar trazabilidad*/
			/*if (!objDocumentoAMover.getAccion().getNombre().equals(Constantes.ACCION_APROBAR)
                    && !objDocumentoAMover.getAccion().getNombre().equals(Constantes.ACCION_DIGITALIZAR)) */
			//
			//@Danna Incidencia 135 no se guarda la trabalidad de todos los documentos cuadno es aprobar
			//

			if (!objDocumentoAMover.getAccion().getNombre().equals(Constantes.ACCION_DIGITALIZAR))
			{   // verificar horariorecepcion wcarrasco 30-03-2012
                                //JBENGOACC
				trazdoc = null;//trazabilidadDocumentoService.saveTrazabilidadDocumento(objDocumentoAMover, objRemitente, objDD.getIPlazoDia(), objDD.getIPlazoHora(), objDD.getStrFechaLimiteAtencion(), asunto, contenido, null, nombrePC,horarioPermitido,"revisar sin plazo?",true,null, null );
				// Trazabilidaddocumento trazdoc =
				// trazabilidadDocumentoService.saveTrazabilidadDocumento(objD,
				// usuarioService.findByIdUsuario(iIdUsuario));
				// log.debug(" Accion y doc princ o no:" + strAcc + "  , " +
				// objD.getPrincipal());
				if (objDocumentoAMover.getPrincipal() == Constantes.DOCUMENTO_PRINCIPAL) {
					Documentoenviado documentoenviado = new Documentoenviado();
					//wvcarrasco descomentar documentoenviado.setTrazabilidaddocumento(trazdoc);
					documentoenviado.setUsuario(objRemitente);
					documentoenviado.setEstado("" + Constantes.ESTADO_ACTIVO);
					documentoEnviadoDao.saveDocumento(documentoenviado);
				}
				//registrarDerivacionAuditoriaDocumento(objDocumentoAMover, objRemitente, objDestinatario, Constantes.TA_DerivarUserFinal, Constantes.TM_UserFinal, objAccion.getNombre().equals(Constantes.TO_Derivar) ? Constantes.TO_Derivar : objAccion.getNombre());
			}

			/**Cambio de Renzo para enumerar todos los documentos al aprobar el expediente---------------------------------------------------*
            if(objDocumentoAMover.getAccion().getNombre().equals(Constantes.ACCION_APROBAR)){
            log.debug("Numeramos todos los documentos");
            lstDocumento=objExpediente.getDocumentoList();
            List<Integer> idDocumentos = new ArrayList<Integer>();
            for(Documento doc : lstDocumento){
            idDocumentos.add(doc.getIdDocumento());
            }
            numerarDocumentos(objRemitente, idDocumentos);
            }*/
			// / Modificacion David Aranda para los documento enviados :FIN
		}
		// Registrando las notificaciones
		ultimo.setPropietario(objDestinatario);
		int iEvento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_REENVIAR;
		int iCCEvento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_CCREENVIAR;

		if (objAccion.getNombre().equals(Constantes.ACCION_PARA_APROBAR)) {
			iEvento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_PORAPROBAR;
			iCCEvento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_CCPORAPROBAR;
		} else if (objAccion.getNombre().equals(Constantes.ACCION_APROBAR)) {
			iEvento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_APROBAR;
			iCCEvento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_CCAPROBAR;
		}

		Set usuariosNotificados = null;//notificacionService.informarViaNotifAndMail(objRemitente, ultimo, iEvento, Constantes.TIPO_NOTIFICACION_DERIVACION, nombrePC);
		Usuario usuarioReceptor = new Usuario();

		if (conCopia != null) {
			Accion accionCopia = accionService.findByNombre(Constantes.ACCION_COPIAR);
			for (String sID : conCopia) {
				if (!StringUtil.isEmpty(sID)) {
					log.debug("Working with ID [" + sID + "]");

					if (sID.startsWith("LISTA")) {
						Integer iID = Integer.valueOf(sID.substring(sID.indexOf("_") + 1));
						Lista objLista = listaService.findByIdLista(iID);

						log.debug("Working with Lista [" + objLista.getNombre() + "]");

						for (Usuario objParticipante : objLista.getParticipanteListaList()) {
							//No se ha enviado notificacion a ese usuario
							if (!usuariosNotificados.contains(objParticipante) && iIdDestinatario.intValue() != iID.intValue()) {
								//wcarrasco 19-10-2010 falta revisar  al aprobarDocumentoMasivo La Fecha Notificacion
								notificacionService.enviarNotificacion(objRemitente, objParticipante, ultimo, Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA, nombrePC,true, null,null);
								//mailService.ChaskiMail(iCCEvento, objRemitente, objParticipante, ultimo);
							} //Ya se envio notificacion a ese usuario, modificar el tipo de notificacion a derivacionconcopia
							else {
								notificacionService.updateTipoNotificacion(ultimo.getIdDocumento(), objParticipante.getIdusuario(), Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA);
							}
						}
					} else {
						Integer iID = Integer.valueOf(sID);
						usuarioReceptor = usuarioService.findByIdUsuario(iID);
						//No se ha enviado notificacion a ese usuario
						if (log.isDebugEnabled()) {
							log.debug("iIdDestinatario [" + iIdDestinatario + "] iID [" + iID + "]");
						}
						//trazabilidadcopiaService.guardarTrazabilidadcopia(trazdoc.getIdtrazabilidaddocumento(), objRemitente, usuarioReceptor, ultimo, accionCopia, objEtapa, Constantes.TIPO_ORIGEN_TRAZADOCUMENTO);
						if (!usuariosNotificados.contains(usuarioReceptor) && iIdDestinatario.intValue() != iID.intValue()) {
							//wcarrasco 19-10-2010 falta revisar  al aprobarDocumentoMasivo La Fecha Notificacion
							notificacionService.enviarNotificacion(objRemitente, usuarioReceptor, ultimo, Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA, nombrePC,true, null,null);
							//mailService.ChaskiMail(iCCEvento, objRemitente, usuarioReceptor, ultimo);
						} //Ya se envio notificacion a ese usuario, modificar el tipo de notificacion a derivacionconcopia
						else {
							notificacionService.updateTipoNotificacion(ultimo.getIdDocumento(), usuarioReceptor.getIdusuario(), Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA);
						}
					}
				}
			}
		}
		log.debug("Nuevo propietario [" + ultimo.getPropietario().getNombres() + " " + ultimo.getPropietario().getApellidos() + "]");
		// return objDocumentoPrincipal;
		return ultimo;
	}

	/**Crea copias de apoyo tomando como base un documento original ya existente ---------------------------------------------*/
	@Transactional
	public void crearCopiaApoyo(Documento doc, DocumentoDetail objDD, Usuario usuario, Usuarioxunidadxfuncion datosDestinatarios, String[] acciones, Integer prioridad, String mensaje, String nombrePC,Boolean horarioPermitido, Boolean horarioPermitidoRecepcion, List<Usuario> usuariosNotificados, Integer codigoVirtual){
		log.debug("-> [Service] DocumentoService - crearCopiaApoyo():void ");
                
                Documento newDoc = new Documento();
		try {
			PropertyUtils.copyProperties(newDoc, doc);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
                 
                Date fechaValida = new Date();
                Proveido p = proveidoDAO.buscarPorId(new Integer(acciones[1]));
                doc.setFlagMultiple("1");
                
                if (doc.getEstado()!=Constantes.ESTADO_ATENDER)
                  doc.setEstado(Constantes.ESTADO_PENDIENTE);
                
                documentoDao.guardarDocumento(doc);
                Usuario destinatario = usuarioService.findByIdUsuario(datosDestinatarios.getIdusuario());
                newDoc.setIdDocumento(documentoDao.getNroDocumento());
                newDoc.setPropietario(destinatario);
                newDoc.setUnidadpropietario(datosDestinatarios.getIdunidad());
                newDoc.setCargopropietario(datosDestinatarios.getIdfuncion());
		newDoc.setDocumentoreferencia(doc.getDocumentoreferencia() != null ? doc.getDocumentoreferencia() : new Integer(doc.getIdDocumento()));
		newDoc.setOrigen(doc.getOrigen() != null ? doc.getOrigen() : new Integer(doc.getIdDocumento()));
		newDoc.setArchivos(null);
		newDoc.setLeido('N');
		newDoc.setDespachado('N');
                newDoc.setFirmado('N');
		newDoc.setPrioridad(doc.getPrioridad());
		newDoc.setFechaAccion(new Date());
		newDoc.setID_CODIGO(doc.getID_CODIGO());
		newDoc.setID_EXTERNO(doc.getID_EXTERNO());
                newDoc.setFlaginicioflujo(null);
                newDoc.setFlagMultiple(null);
                newDoc.setFechaAccion(fechaValida);
                newDoc.setEstado(Constantes.ESTADO_PENDIENTE);
                newDoc.setFechaLimiteAtencion(doc.getFechaLimiteAtencion());
                newDoc.setFlagatendido(null);
                
                if ((doc.getID_EXTERNO()!=null && doc.getID_EXTERNO()==0) || !datosDestinatarios.getIdunidad().toString().equals(Constantes.UNIDAD_TRAMITE.toString())){  
                    if (p.getIdProveido().toString().equals(Constantes.CODIGO_PROVEIDO_FIRMAR)){
                        newDoc.setFirmado('S');
                    } 
                }
                
		newDoc = documentoDao.saveDocumentoSequence(newDoc);
                Accion accionApoyo = accionService.findByNombre(Constantes.ACCION_APOYO);
		Trazabilidadapoyo tapoyo = new Trazabilidadapoyo();
		tapoyo.setAccion(accionApoyo);
		tapoyo.setDestinatario(destinatario);
                tapoyo.setUnidaddestinatario(datosDestinatarios.getIdunidad());
                tapoyo.setCargodestinatario(datosDestinatarios.getIdfuncion());
		tapoyo.setRemitente(new Usuario(usuario.getIdUsuarioPerfil()));
                tapoyo.setUnidadremitente(usuario.getIdUnidadPerfil());
                tapoyo.setCargoremitente(usuario.getIdFuncionPerfil());
		tapoyo.setIndalerta(doc.getIndAlerta());
                tapoyo.setUsuariocreacion(usuario.getIdusuario());
		tapoyo.setAsunto(objDD != null ? objDD.getStrAsunto().toUpperCase() : "");
                
                Trazabilidaddocumento trazabilidad = new Trazabilidaddocumento();
		trazabilidad.setDestinatario(destinatario);

                tapoyo.setFechacreacion(fechaValida);
		tapoyo.setDocumento(newDoc.getIdDocumento());
		tapoyo.setNombrePC(nombrePC);
                
                try{
                    if (objDD.getStrFechaLimiteAtencion()!=null && !objDD.getStrFechaLimiteAtencion().trim().equals("")){
                       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                       tapoyo.setFechalimiteatencion(formatter.parse(objDD.getStrFechaLimiteAtencion()));
                    }else
                       tapoyo.setFechalimiteatencion(null);
                }catch(Exception e){
                    e.printStackTrace();
                }    
               
                tapoyo.setPrioridad(prioridad);
                tapoyo.setPlazo(doc.getPlazo());
                
                if(doc.getDocumentoreferencia() != null){
		   trazabilidad = trazabilidadDocumentoService.encontrarUltimaTrazabilidadPorDocumento(doc.getDocumentoreferencia());
		}else{
		   trazabilidad = trazabilidadDocumentoService.encontrarUltimaTrazabilidadPorDocumento(doc.getIdDocumento());
		}

                tapoyo.setTrazabilidad(trazabilidad);
                tapoyo.setEstado(estadoService.findByCodigo(Constantes.ESTADO_CODIGO_PENDIENTE));
                
                if(acciones != null){
	          tapoyo.setIdproveido(new Integer(acciones[1]));
		  String texto = "";
		  texto = !mensaje.equals("") ? texto+mensaje : texto;
		  tapoyo.setTexto(texto);
		}
                
                Trazabilidadapoyo temp =trazabilidadapoyoService.guardar(tapoyo);
                Documentoenviado documentoenviado = new Documentoenviado();
                documentoenviado.setIdTrazabilidadEnvio(temp.getIdtrazabilidadapoyo());//buscar multiple
                documentoenviado.setUsuario(temp.getRemitente());
                documentoenviado.setUnidadpropietario(usuario.getIdUnidadPerfil());
                documentoenviado.setCargopropietario(usuario.getIdFuncionPerfil());
                documentoenviado.setUsuariocreacion(usuario.getIdusuario());
                documentoenviado.setEstado("" + Constantes.ESTADO_ACTIVO);
                documentoenviado.setTipoEnvio(""+ Constantes.TIPO_ENVIO_MULTIPLE);
                documentoEnviadoDao.saveDocumento(documentoenviado);
                
                try{
                    Usuario objUsuario = new Usuario();
                    Tipodocumento t = tipoDocumentoDao.findByIdTipoDocumento(doc.getTipoDocumento().getIdtipodocumento());
                    String respuesta = null;
                    
                    objUsuario.setIdUnidadPerfil(datosDestinatarios.getIdunidad());
                    objUsuario.setIdFuncionPerfil(datosDestinatarios.getIdfuncion());
                    objUsuario.setIdusuario(datosDestinatarios.getIdusuario());
                    
                    if (doc.getID_CLIENTE()!=null && t.getEstadoPIDE()!=null && t.getEstadoPIDE().equals(String.valueOf(Constantes.ESTADO_ACTIVO))){ 
                            boolean bandera = false;
                            List<Usuarioxunidadxfuncion> lista = null;
                            
                            try{
                                  lista = usuarioxunidadxfuncionDAO.getUsuarioByUnidadByFuncionListRol(objUsuario);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            
                            if (lista!=null && lista.size()>0){
                                for (int i=0;i<lista.size();i++){
                                  if (lista.get(i).getIdrol().toString().equals(Constantes.COD_ROL_MENSAJERIA.toString())){
                                      bandera = true;
                                      break;
                                  }
                                }

                                if (bandera){
                                    try{
                                         Cliente c = clienteService.findByIdCliente(doc.getID_CLIENTE());
                                         EndPointRUC endPointRUC = new EndPointRUC();
                                         respuesta = endPointRUC.validarEntidad(c.getNumeroIdentificacion(), Constantes.AMBIENTE_WS_PIDE_RUC);
                                    }catch(Exception e){
                                         e.printStackTrace();
                                         respuesta = "-1";
                                         try{
                                             Cliente c = clienteService.findByIdCliente(doc.getID_CLIENTE());
                                             if (c!=null){
                                                if (c.getFlagPide()!=null && c.getFlagPide().equals("1")){
                                                   respuesta = "0000"; 
                                                }
                                             }
                                         }catch(Exception ex){
                                             ex.printStackTrace();;
                                             respuesta = "-1";
                                         }
                                    }
                                }else{
                                    bandera = false; 
                                }    

                               if (bandera && t!=null && respuesta!=null && respuesta.equals("0000")){
                                 int contador = 0;
                                 Documento d = documentoDao.findByIdDocumento(doc.getIdDocumento());
                                 Cliente c = clienteService.findByIdCliente(doc.getID_CLIENTE());
                                 IotdtcDespacho iotdtcDespacho = new IotdtcDespacho();
                                 List<Usuario> lst = firmaArchivoDAO.findUltimaFirma(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia(), "F");

                                 if (lst!=null && lst.size()>0){
                                        Usuario usu = usuarioService.findByIdUsuario(lst.get(0).getIdUsuarioPerfil());    
                                        iotdtcDespacho.setCtipdociderem(usu.getTipoDocumento().charAt(0));
                                        iotdtcDespacho.setVnumdociderem(usu.getNroDocumento());
                                        iotdtcDespacho.setVcoduniorgrem(lst.get(0).getIdUnidadPerfil().toString());
                                        iotdtcDespacho.setVuniorgrem(unidadService.buscarObjPor(lst.get(0).getIdUnidadPerfil()).getNombre());
                                  }else{
                                        throw new Exception();
                                  }    

                                 iotdtcDespacho.setVnumregstd(doc.getID_CODIGO().toString());
                                 iotdtcDespacho.setVanioregstd(doc.getID_CODIGO().toString().substring(0, 4));
                                 iotdtcDespacho.setVrucentrec(c.getNumeroIdentificacion());
                                 iotdtcDespacho.setVnomentrec(c.getRazonSocial());
                                 iotdtcDespacho.setCflgest('P');
                                 iotdtcDespacho.setCflgenv('N');
                                 iotdtcDespacho.setDfecreg(new Date());
                                 iotdtcDespacho.setVusureg(usuario.getUsuario());
                                 iotdtcDespacho.setIddocumento(newDoc.getIdDocumento());
                                 iotdtcDespacho = despachoVirtualDAO.registrarDocumento(iotdtcDespacho);

                                 List<Archivo> lstArchivo = archivoService.buscarDocumentosPublicar(doc.getID_CODIGO().toString());

                                 for(int i=0;i<lstArchivo.size();i++){
                                   if (!lstArchivo.get(i).getPrincipal().equals('S'))
                                      contador ++;    
                                 }

                                 IotdtmDocExterno iotdtmDocExterno = new IotdtmDocExterno();
                                 iotdtmDocExterno.setSidemiext(iotdtcDespacho);
                                 iotdtmDocExterno.setVnomentemi(parametroService.findByTipoAndValue("RAZON_SOCIAL_OSITRAN", "20420248645").getDescripcion());
                                 iotdtmDocExterno.setCcodtipdoc(doc.getTipoDocumento().getPide());
                                 iotdtmDocExterno.setVnumdoc(doc.getNumeroDocumento());
                                 iotdtmDocExterno.setDfecdoc(doc.getFechaDocumento());
                                 iotdtmDocExterno.setVuniorgdst(doc.getDesUnidadRemitente()); 
                                 iotdtmDocExterno.setVnomdst(doc.getDesRemitente());
                                 iotdtmDocExterno.setVnomcardst(doc.getDesCargoRemitente());
                                 iotdtmDocExterno.setVasu(doc.getAsunto());
                                 iotdtmDocExterno.setSnumanx(BigInteger.valueOf(contador));
                                 iotdtmDocExterno.setSnumfol(BigInteger.valueOf(doc.getNumeroFolios()));
                                 if (contador>0) iotdtmDocExterno.setVurldocanx(parametroService.findByTipoAndValue("URL_ANEXOS_PIDE", "PIDE").getDescripcion());
                                 iotdtmDocExterno = documentoExternoVirtualDAO.registrarDocumento(iotdtmDocExterno);

                                 AlfrescoApiWs alfrescoApiWs;
                                 Session sesionAlfresco = null;
                                 String alfrescoHostPublico = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_HOST);
                                 String alfrescoHostPort = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PORT);
                                 String alfrescoProtocolo = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PROTOCOLO);
                                 String URL_ALFRESCO = alfrescoProtocolo+"://"+alfrescoHostPublico+":"+alfrescoHostPort+"/alfresco/cmisatom";

                                 String objectId = null;
                                 String nombreDocumento = null;

                                 for(int i=0;i<lstArchivo.size();i++){
                                   if (lstArchivo.get(i).getPrincipal().equals('S')){
                                      objectId = lstArchivo.get(i).getObjectId();
                                      nombreDocumento = lstArchivo.get(i).getNombre();
                                      break;
                                   }         
                                 }

                                 alfrescoApiWs = new AlfrescoApiWs(URL_ALFRESCO, USERCONSULTA, USERCONSULTA_CLAVE, REPOSITORIO_ID);
                                 sesionAlfresco = alfrescoApiWs.getSessionAlfresco();
                                 Document document = (Document)sesionAlfresco.getObject(objectId);
                                 InputStream intput = document.getContentStream().getStream();
                                 byte[] bytes = toByteArray(intput);
                                 IotdtdDocPrincipal iotdtdDocPrincipal = new IotdtdDocPrincipal();
                                 iotdtdDocPrincipal.setSiddocext(iotdtmDocExterno); 
                                 iotdtdDocPrincipal.setSiddocext(iotdtmDocExterno);
                                 iotdtdDocPrincipal.setCcodest('A');
                                 iotdtdDocPrincipal.setVnomdoc(nombreDocumento);
                                 iotdtdDocPrincipal.setBpdfdoc(bytes);   
                                 iotdtdDocPrincipal.setDfecreg(new Date());
                                 iotdtdDocPrincipal = docPrincipalVirtualDAO.registrarPrincipal(iotdtdDocPrincipal);

                                 if (contador>0){
                                     for(int i=0;i<lstArchivo.size();i++){
                                         if (!lstArchivo.get(i).getPrincipal().equals('S')){
                                            IotdtdAnexo iotdtdAnexo = new IotdtdAnexo();
                                            nombreDocumento = lstArchivo.get(i).getNombre();
                                            iotdtdAnexo.setSiddocext(iotdtmDocExterno);
                                            iotdtdAnexo.setVnomdoc(nombreDocumento);
                                            iotdtdAnexo.setDfecreg(new Date());
                                            iotdtdAnexo = docAnexoVirtualDAO.registrarAnexo(iotdtdAnexo);
                                         }         
                                     }
                                 }    
                               }  
                            }
                    }       
                    
                    if (codigoVirtual!=null){
                           IotdtmDocExterno iotdtmDocExterno = documentoExternoVirtualDAO.buscarDocumentoVirtual(codigoVirtual);
                           if (iotdtmDocExterno.getSidemiext().getCflgest()=='P'){
                                IotdtcDespacho iotdtcDespacho = iotdtmDocExterno.getSidemiext();
                                if (iotdtcDespacho!=null){
                                    iotdtcDespacho.setCflgest('X');
                                    iotdtcDespacho.setDfecmod(new Date());
                                    despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                                }    
                           }
                    }
                    
                }catch(Exception e){
                    e.printStackTrace();
                }
                     
                try{
                       if (usuariosNotificados!=null && usuariosNotificados.size()>0){
                           Lista objLista = null;
                           objLista = new Lista(); 
                           objLista.setParticipanteListaList(usuariosNotificados);
                           doc.setUsuariosDestinatarios(objLista.getParticipanteListaList());
                           notificacionService.informarViaNotifAndMail(usuario, doc, Constantes.CONFIGNOTIFMAIL_DOCUMENTO_MULTIPLE, Constantes.TIPO_NOTIFICACION_MULTIPLE, nombrePC, mensaje, null); 
                       }
                       
                   }catch(Exception e){
                        e.printStackTrace();
                   }
                
	}

	@Transactional
	public void eliminarDocumento(Integer idDocumento){
		log.debug("-> [Service] DocumentoService - eliminarDocumento():void ");

		documentoDao.eliminarDocumento(idDocumento);
	}


	/**REN Se llama cuando se va a guardar la traza referente al termino del apoyo, ya sea aprobando o rechazando*/
	@Transactional
	public void guardarTrazaFinalizarApoyo(Documento doc, String codigoEstado){
		log.debug("-> [Service] DocumentoService - guardarTrazaFinalizarApoyo():void ");

                Trazabilidadapoyo tapoyoorigen =  trazabilidadapoyoService.buscarUltimaDelegacionUsuario(doc);
		tapoyoorigen.setEstado(estadoService.findByCodigo(codigoEstado));
                tapoyoorigen = trazabilidadapoyoService.guardar(tapoyoorigen);
                
	} 

	@Transactional
	public void archivarDocumento(Integer idDocumento, Usuario usuarioSession, String observacion, String tipoArchivar, String nombrePC){
		log.debug("-> [Service] DocumentoService - archivarDocumento():void ");

		Documento doc = this.findByIdDocumento(idDocumento);
		doc.setEstado(Constantes.ESTADO_CERRADO);//podriamos cambiar por .ESTADO_ARCHIVAR o se queda con ESTADO_CERRADO
		doc.setDespachado('N');
		doc.setFechaAccion(new Date());
		doc = documentoDao.updateDocumento(doc);
                 
                Usuarioxunidadxfuncion usu = new Usuarioxunidadxfuncion();
                usu.setIdusuario(doc.getPropietario().getIdusuario());
                usu.setIdunidad(doc.getUnidadpropietario());
                usu.setIdfuncion(doc.getCargopropietario());
                
		if(doc.getDocumentoreferencia() == null){
                           trazabilidadDocumentoService.guardarTrazabilidad(doc, usuarioSession, usu, Constantes.ACCION_ARCHIVAR, "Archivo del documento "+doc.getNumeroDocumento(), observacion, nombrePC);
		}else{
			try{
				Trazabilidadapoyo tapoyo = new Trazabilidadapoyo();
                                tapoyo.setUsuariocreacion(usuarioSession.getIdusuario());
				tapoyo.setAccion(accionService.findByNombre(Constantes.ACCION_ARCHIVAR));
				tapoyo.setDestinatario(doc.getPropietario());
                                tapoyo.setUnidaddestinatario(doc.getUnidadpropietario());
                                tapoyo.setCargodestinatario(doc.getCargopropietario());
				tapoyo.setRemitente(new Usuario(usuarioSession.getIdUsuarioPerfil()));
                                tapoyo.setUnidadremitente(usuarioSession.getIdUnidadPerfil());
                                tapoyo.setCargoremitente(usuarioSession.getIdFuncionPerfil());
				tapoyo.setFechacreacion(new Date());
				tapoyo.setDocumento(doc.getIdDocumento());
				Trazabilidadapoyo tapoyoorigen =  trazabilidadapoyoService.buscarUltimaDelegacionUsuario(doc);
                                
				if(tapoyoorigen != null && tapoyoorigen.getTrazabilidad() != null){
					log.debug("en trazabilidadapoyo");
					Trazabilidaddocumento trazabilidad = trazabilidadDocumentoService.findTrabilidadbyId(tapoyoorigen.getTrazabilidad().getIdtrazabilidaddocumento());
					log.debug("trazabilidad.getIdtrazabilidaddocumento()"+ trazabilidad.getIdtrazabilidaddocumento());
					tapoyo.setTrazabilidad(trazabilidad);
					tapoyo.setEstado(estadoService.findByCodigo(Constantes.ESTADO_CODIGO_ARCHIVADO));
					tapoyo.setAsunto("Archivo del documento "+doc.getNumeroDocumento());
					tapoyo.setTexto(observacion != null ? observacion : "");
					tapoyo.setNombrePC(nombrePC);
					tapoyo.setFechalimiteatencion(tapoyo.getFechacreacion());
					trazabilidadapoyoService.guardar(tapoyo);
				}else{
					log.debug("No se encontro traza de apoyo");
				}
			}catch(Exception e){
				log.debug("No se ha podido guardar la traza del documento delegado");
				e.printStackTrace();
			}
		}
	}

	@Transactional
	public void reabrirDocumento(Documento documento, Usuario usuario, String nombrePC){
		log.debug("-> [Service] DocumentoService - reabrirDocumento():void ");

		documento.setEstado(Constantes.ESTADO_ACTIVO);
		documento.setFechaAccion(new Date());
		documento = documentoDao.updateDocumento(documento);
                
                Usuarioxunidadxfuncion destinatario = new Usuarioxunidadxfuncion(); 
                destinatario.setIdusuario(usuario.getIdUsuarioPerfil());
                destinatario.setIdunidad(usuario.getIdUnidadPerfil());
                destinatario.setIdfuncion(usuario.getIdFuncionPerfil());
                
		String asunto = "Reapertura del documento "+documento.getNumeroDocumento();
		String contenido = "Documento reabierto por el usuario " + documento.getPropietario().getNombreCompleto();

                //JBENGOA POR REVISAR EL NULL
		if(documento.getDocumentoreferencia() == null){
			trazabilidadDocumentoService.guardarTrazabilidad(documento, usuario, destinatario, Constantes.ACCION_REABRIR, asunto, contenido, nombrePC);
		}else{
			try{
				Trazabilidadapoyo tapoyo = new Trazabilidadapoyo();
				tapoyo.setAccion(accionService.findByNombre(Constantes.ACCION_REABRIR));
				tapoyo.setDestinatario(documento.getPropietario());
				tapoyo.setUnidaddestinatario(documento.getUnidadpropietario());
                                tapoyo.setCargodestinatario(documento.getCargopropietario());
                                tapoyo.setRemitente(documento.getPropietario());
				tapoyo.setUnidadremitente(documento.getUnidadpropietario());
                                tapoyo.setCargoremitente(documento.getCargopropietario());
                                tapoyo.setFechacreacion(new Date());
				tapoyo.setDocumento(documento.getIdDocumento());
				Trazabilidadapoyo tapoyoorigen =  trazabilidadapoyoService.buscarUltimaDelegacionUsuario(documento); 
                                
                                if(tapoyoorigen != null && tapoyoorigen.getTrazabilidad() != null){
					Trazabilidaddocumento trazabilidad = trazabilidadDocumentoService.findTrabilidadbyId(tapoyoorigen.getTrazabilidad().getIdtrazabilidaddocumento());
					tapoyo.setTrazabilidad(trazabilidad);
					tapoyo.setFechalimiteatencion(tapoyo.getFechacreacion());
                                        Estado estado = new Estado();
                                        estado.setEstado(Constantes.ESTADO_PENDIENTE);
					tapoyo.setEstado(estado);
					tapoyo.setAsunto(asunto);
					tapoyo.setTexto(contenido);
					tapoyo.setNombrePC(nombrePC);
					trazabilidadapoyoService.guardar(tapoyo);
				}else{
					log.debug("No se encontro traza de apoyo");
				}
			}catch(Exception e){
				log.debug("No se ha podido guardar la traza del documento delegado");
				e.printStackTrace();
			}
		}
	}

	public List<Integer> getUsuariosPermitidos(Integer idDocumento){
		return documentoDao.getUsuariosPermitidos(idDocumento);
	}

	public void setAuditoriaDao(AuditoriaDAO auditoriaDao) {
		this.auditoriaDao = auditoriaDao;
	}

	public void setDocumentoDao(DocumentoDAO documentoDao) {
		this.documentoDao = documentoDao;
	}

	public void setDocumentoEnviadoDao(DocumentoEnviadoDAO documentoEnviadoDao) {
		this.documentoEnviadoDao = documentoEnviadoDao;
	}

	

        /*
	public void setSubmotivoDao(SubmotivoDAO submotivoDao) {
		this.submotivoDao = submotivoDao;
	}*/

	//public void setSuministroDao(SuministroDAO suministroDao) {
	//	this.suministroDao = suministroDao;
	//}

	public void setTipoDocumentoDao(TipodocumentoDAO tipoDocumentoDao) {
		this.tipoDocumentoDao = tipoDocumentoDao;
	}

	public void setListaService(ListaService listaService) {
		this.listaService = listaService;
	}

	public void setDocumentoPorExpedienteService(DocumentoxexpedienteService documentoPorExpedienteService) {
		this.documentoPorExpedienteService = documentoPorExpedienteService;
	}

	//public void setTrazabilidadExpedienteService(TrazabilidadexpedienteService trazabilidadExpedienteService) {
	//	this.trazabilidadExpedienteService = trazabilidadExpedienteService;
	//}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	//public void setUsuarioStorService(UsuariostorService usuarioStorService) {
	//	this.usuarioStorService = usuarioStorService;
	//}

	public void setDistritoService(DistritoService distritoService) {
		this.distritoService = distritoService;
	}

	public void setDiaFestivoService(DiafestivoService diaFestivoService) {
		this.diaFestivoService = diaFestivoService;
	}

	public void setAccionService(AccionService accionService) {
		this.accionService = accionService;
	}

	public void setExpedienteService(ExpedienteService expedienteService) {
		this.expedienteService = expedienteService;
	}

	public void setTrazabilidadDocumentoService(TrazabilidaddocumentoService trazabilidadDocumentoService) {
		this.trazabilidadDocumentoService = trazabilidadDocumentoService;
	}

	public void setusuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public void setArchivoService(ArchivoService archivoService) {
		this.archivoService = archivoService;
	}

	/**
	 * @param repositorioService
	 *            the repositorioService to set
	 */
	 public void setRepositorioService(RepositorioService repositorioService) {
		this.repositorioService = repositorioService;
	}

	public void setFechaLimite(FechaLimite fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public void setDiafestivoService(DiafestivoService diaFestivoService) {
		this.diaFestivoService = diaFestivoService;
	}

	public void setAuditoriaService(AuditoriaService auditoriaService) {
		this.auditoriaService = auditoriaService;
	}

	public void setNotificacionService(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}

	public void setProcesoDao(ProcesoDAO procesoDao) {
		this.procesoDao = procesoDao;
	}

	public void setSedeDao(SedeDAO sedeDao) {
		this.sedeDao = sedeDao;
	}

	public ArchivopendienteService getSrvArchivoPendiente() {
		return srvArchivoPendiente;
	}

	public void setSrvArchivoPendiente(ArchivopendienteService srvArchivoPendiente) {
		this.srvArchivoPendiente = srvArchivoPendiente;
	}

	public TrazabilidadcopiaService getTrazabilidadcopiaService() {
		return trazabilidadcopiaService;
	}

	public void setTrazabilidadcopiaService(
			TrazabilidadcopiaService trazabilidadcopiaService) {
		this.trazabilidadcopiaService = trazabilidadcopiaService;
	}

	/*public void setIntalioService(IntalioService intalioService) {
		this.intalioService = intalioService;
	}*/

	/**
	 * @return the mailService
	 */
	 public ManejoDeEmailService getMailService() {
		return mailService;
	}

	/**
	 * @param mailService the mailService to set
	 */
	 public void setMailService(ManejoDeEmailService mailService) {
		 this.mailService = mailService;
	 }

	 public CargoReporte obtenerCargo(Integer iddocumento) {
		 return documentoDao.obtenerCargo(iddocumento);
	 }

	 public AlfrescoWSService getAlfrescoWebServiceClient() {
		 return alfrescoWebServiceClient;
	 }

	 public void setAlfrescoWebServiceClient(AlfrescoWSService alfrescoWebServiceClient) {
		 this.alfrescoWebServiceClient = alfrescoWebServiceClient;
	 }

	 @SuppressWarnings("rawtypes")
	 public List getBandejaDocAtendidosUsuarioFinal(Usuario objUsuario) {
		 throw new UnsupportedOperationException("Not supported yet.");
	 }

	 public ClienteService getClienteService() {
		 return clienteService;
	 }

	 public void setClienteService(ClienteService clienteService) {
		 this.clienteService = clienteService;
	 }

	 public DocumentoxclienteService getDocumentoxclienteService() {
		 return documentoxclienteService;
	 }

	 public void setDocumentoxclienteService(
			 DocumentoxclienteService documentoxclienteService) {
		 this.documentoxclienteService = documentoxclienteService;
	 }

	 public NotificacionxEnumerarDAO getNotificacionxEnumerarDAO() {
		 return notificacionxEnumerarDAO;
	 }

	 public void setNotificacionxEnumerarDAO(
			 NotificacionxEnumerarDAO notificacionxEnumerarDAO) {
		 this.notificacionxEnumerarDAO = notificacionxEnumerarDAO;
	 }

	 public NumeracionService getNumeracionService() {
		 return numeracionService;
	 }

	 public void setNumeracionService(NumeracionService numeracionService) {
		 this.numeracionService = numeracionService;
	 }

	 public void setAlfrescoWebscriptClient(AlfrescoWebscriptService alfrescoWebscriptClient) {
		 this.alfrescoWebscriptClient = alfrescoWebscriptClient;
	 }

	 public TrazabilidadapoyoService getTrazabilidadapoyoService() {
		 return trazabilidadapoyoService;
	 }

	 public void setTrazabilidadapoyoService(
			 TrazabilidadapoyoService trazabilidadapoyoService) {
		 this.trazabilidadapoyoService = trazabilidadapoyoService;
	 }

	 public EstadoService getEstadoService() {
		 return estadoService;
	 }

	 public void setEstadoService(EstadoService estadoService) {
		 this.estadoService = estadoService;
	 }

	public ProveidoDAO getProveidoDAO() {
		return proveidoDAO;
	}

	public void setProveidoDAO(ProveidoDAO proveidoDAO) {
		this.proveidoDAO = proveidoDAO;
	}

	public void setParametroDao(ParametroDAO parametroDao) {
		this.parametroDao = parametroDao;
	}

	public void setRolDao(RolDAO rolDao) {
		this.rolDao = rolDao;
	}

	public Map<String, Object> getMapSession() {
		return mapSession;
	}

	public void setMapSession(Map<String, Object> mapSession) {
		this.mapSession = mapSession;
	}

	public UnidadService getUnidadService() {
		return unidadService;
	}

	public void setUnidadService(UnidadService unidadService) {
		this.unidadService = unidadService;
	}

	public ParametroService getParametroService() {
		return parametroService;
	}

	public void setParametroService(ParametroService parametroService) {
		this.parametroService = parametroService;
	}

	public FechaLimite getFechaLimite() {
		return fechaLimite;
	}

	public TrazabilidadapoyoDAO getTrazabilidadDocumentoDao() {
		return trazabilidadDocumentoDao;
	}

	public void setTrazabilidadDocumentoDao(
			TrazabilidadapoyoDAO trazabilidadDocumentoDao) {
		this.trazabilidadDocumentoDao = trazabilidadDocumentoDao;
	}

	public ProcesoService getProcesoService() {
		return procesoService;
	}

	public void setProcesoService(ProcesoService procesoService) {
		this.procesoService = procesoService;

	}
        
         public DocumentoAtendidoDAO getDocumentoAtendidoDAO() {
            return documentoAtendidoDAO;
        }

        public void setDocumentoAtendidoDAO(DocumentoAtendidoDAO documentoAtendidoDAO) {
            this.documentoAtendidoDAO = documentoAtendidoDAO;
        }

        
                
        @Override
	public List findDocumentosPendientesUsuarioFinalFiltro(Usuario objUsuario,
		 BusquedaAvanzada objFiltro) {
		log.debug("-> [Service] DocumentoService - findDocumentosPendientesUsuarioFinalFiltro():List ");
		List<ItemUF> lstItemUFTemp = documentoDao.findByPendientesDataUFFiltro(objUsuario,objFiltro);
		
		if(lstItemUFTemp != null){
			return lstItemUFTemp;
		}else{
			return null;
		}

	}
        
                
	@Override
	public List findDocumentosUsuarioFinalFiltro(Usuario objUsuario,
		String enviados, BusquedaAvanzada objFiltro) {
		log.debug("-> [Service] DocumentoService - findDocumentosUsuarioFinalFiltro():List ");
		List<ItemUF> lstItemUFTemp = null; //new ArrayList<ItemUF>();
                lstItemUFTemp = documentoDao.findByDataUFFiltro(objUsuario, enviados,objFiltro);
		
		if(lstItemUFTemp != null){
			return lstItemUFTemp;
		}else{
			return null;
		}

	}

	@Override
	public List<Nodo> getTreeDocumentosRecibidos(Usuario objUsuario,
			boolean enviados) {
		log.debug("-> [Service] DocumentoService - getTreeDocumentosRecibidos():List<Nodo> ");

		List<Nodo> lstArbol = null;
		List<FilaBandejaUF> lstDocumentosRecibidos = documentoDao.findByDataUFDocumentosRecibidos(objUsuario.getIdusuario(), enviados);

		if (lstDocumentosRecibidos != null) {
			log.debug("Numero de documentos recibidos encontradas  [" + lstDocumentosRecibidos.size() + "]");

			lstArbol = new ArrayList<Nodo>();

			for (FilaBandejaUF objDocumentosRecibidos : lstDocumentosRecibidos) {
				Nodo objNodo = new Nodo(Boolean.TRUE, objDocumentosRecibidos.getId(), objDocumentosRecibidos.getDocumento(), null);
				log.debug("Nodo con ID [" + objDocumentosRecibidos.getId() + "] documento [" + objDocumentosRecibidos.getDocumento() + "]");
				lstArbol.add(objNodo);
			}
		}

		return lstArbol;
	}

	@Override
	public List getContDocUsuarioFinal(Usuario objUsuario, boolean enviados,BusquedaAvanzada objFiltro) {

		List<FilaBandejaUF> lstDocumentosRecibidos = new ArrayList<FilaBandejaUF>();
		List<ItemUF> lstItemUF;
		if(objFiltro==null){
			lstDocumentosRecibidos=documentoDao.findByDataUFDocumentosRecibidos(objUsuario.getIdusuario(), enviados);
		}else{
			log.debug("VER FILTROSSS  objFiltro.getAreaOrigen()"+ objFiltro.getAreaOrigen()+ "objFiltro.getPrioridad()"+ objFiltro.getPrioridad()+ "objFiltro.getAsuntodocumento()"+objFiltro.getAsuntodocumento());
			if(objFiltro.getAreaOrigen().equals("0")){
				log.debug("ENTRO A AREA TODOS");
				lstDocumentosRecibidos=documentoDao.findByDataUFDocumentosRecibidosFiltro(objUsuario.getIdusuario(), enviados,objFiltro);
			}else{
				log.debug("Debug ");
				Unidad unidad = unidadService.buscarObjPor(Integer.parseInt(objFiltro.getAreaOrigen()));
				lstItemUF = documentoDao.findByDataUFFiltroIG(objUsuario.getIdusuario(), enviados,objFiltro);
				for (ItemUF filaBandejaUF : lstItemUF) {
					FilaBandejaUF item = new FilaBandejaUF();
					if((unidad.getNombre().equals(filaBandejaUF.getArea().trim()))){
						item.setId(filaBandejaUF.getId());
						lstDocumentosRecibidos.add(item);
					}
				}
			}
		}

		if(lstDocumentosRecibidos.size() != 0){
			log.debug("con tamanno "+ lstDocumentosRecibidos.size());
			return lstDocumentosRecibidos;
		}else{
			log.debug("con NULL" );
			return null;
		}
	}

	@Override
	public List<Nodo> getTreeDocumentosRecibidosFiltro(Usuario objUsuario,
			boolean enviados, BusquedaAvanzada objFiltro) {
		log.debug("-> [Service] DocumentoService - getTreeDocumentosRecibidos():List<Nodo> ");

		List<Nodo> lstArbol = null;
		List<FilaBandejaUF> lstDocumentosRecibidos = documentoDao.findByDataUFDocumentosRecibidosFiltro(objUsuario.getIdusuario(), enviados,objFiltro);

		if (lstDocumentosRecibidos != null) {
			log.debug("Numero de documentos recibidos encontradas  [" + lstDocumentosRecibidos.size() + "]");

			lstArbol = new ArrayList<Nodo>();

			for (FilaBandejaUF objDocumentosRecibidos : lstDocumentosRecibidos) {
				Nodo objNodo = new Nodo(Boolean.TRUE, objDocumentosRecibidos.getId(), objDocumentosRecibidos.getDocumento(), null);
				log.debug("Nodo con ID [" + objDocumentosRecibidos.getId() + "] documento [" + objDocumentosRecibidos.getDocumento() + "]");
				lstArbol.add(objNodo);
			}
		}

		return lstArbol;


	}

	public ExpedienteDAO getExpedienteDAO() {
		return expedienteDAO;
	}

	public void setExpedienteDAO(ExpedienteDAO expedienteDAO) {
		this.expedienteDAO = expedienteDAO;
	}

	@Override
	public List<NodoArbol> getDojoDocumentoTree(BusquedaAvanzada objFiltro,Integer idExpediente,
			Integer idDocumento) {
		log.debug("-> [Service] DocumentoService - getDojoDocumentoTree():List<Nodo> ");
		//Expediente objExpediente = expedienteDAO.findByIdExpediente(idExpediente);

        List<NodoArbol> lstTreeDocumento = new ArrayList<NodoArbol>();
        List<NodoArbol> allElements = new ArrayList<NodoArbol>();

        Usuario objUsuario = null;
        mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);


		List<FilaBandejaUF> listDoc = null;

		if (objFiltro == null) {
			log.debug("SIN FILTRO");
			listDoc = documentoDao.findFilasDXETree(objUsuario.getIdusuario(), idDocumento, true);

		}else{
			listDoc = documentoDao.findFilasDXETreeFiltro(objFiltro, objUsuario.getIdusuario(), idDocumento, true);

		}


        for (FilaBandejaUF documentoItemUF : listDoc) {
    	        Documento documento = findByIdDocumento(documentoItemUF.getId());
    	        if(idDocumento != documento.getIdDocumento().intValue()){
    	        	NodoArbol objETDocumento = new NodoArbol(false, "D-" + documento.getIdDocumento(), documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento(), null);
           			lstTreeDocumento.add(objETDocumento);
    			}
        }

        Documento documento = findByIdDocumento(idDocumento);
        NodoArbol objExpedienteTree = new NodoArbol(true, "E-" + idDocumento, documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento(), lstTreeDocumento); // new

        allElements.add(objExpedienteTree);
        allElements.addAll(lstTreeDocumento);
        return allElements;
	}

	//estamos probando desde aqui
	public List <List<NodoArbol>> getDojoDocumentoTreeS(BusquedaAvanzada objFiltro,Integer idExpediente,
			Integer idDocumento) {
		log.debug("-> [Service] DocumentoService - getDojoDocumentoTree():List<Nodo> ");
		//Expediente objExpediente = expedienteDAO.findByIdExpediente(idExpediente);

        List<NodoArbol> lstTreeDocumento = new ArrayList<NodoArbol>();
        List<NodoArbol> allElements = new ArrayList<NodoArbol>();


        List <List<NodoArbol>> lstTreeDocumentoL = null ;
        List <List<NodoArbol>> allElementsL = null ;

        Usuario objUsuario = null;
        mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);


		List<FilaBandejaUF> listDoc = null;

		if (objFiltro == null) {
			log.debug("SIN FILTRO");
			listDoc = documentoDao.findFilasDXETree(objUsuario.getIdusuario(), idDocumento, true);

		}else{
			listDoc = documentoDao.findFilasDXETreeFiltro(objFiltro, objUsuario.getIdusuario(), idDocumento, true);

		}

                for (FilaBandejaUF documentoItemUF : listDoc) {
                        Documento documento = findByIdDocumento(documentoItemUF.getId());
                        if(idDocumento != documento.getIdDocumento().intValue()){
                                NodoArbol objETDocumento = new NodoArbol(false, "D-" + documento.getIdDocumento(), documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento(), null);
                                        lstTreeDocumento.add(objETDocumento);
                                }
                }

                Documento documento = findByIdDocumento(idDocumento);
                NodoArbol objExpedienteTree = new NodoArbol(true, "E-" + idDocumento, documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento(), lstTreeDocumento); // new

                allElements.add(objExpedienteTree);
                allElements.addAll(lstTreeDocumento);

                NodoArbol D = new NodoArbol(true, "E-" + idDocumento, documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento(), lstTreeDocumento); // new


                return allElementsL;
	}

         

		@Override
		public List<Documento> getDocumentosDelExpediente(Integer idUsuario,
				Integer idDocumento) {
			log.debug("-> [Service] DocumentoService - getDocumentosDelExpediente():List<Documento> ");

	        List<Documento> lstDocumento = new ArrayList<Documento>();

			List<FilaBandejaUF> listDoc = null;

			listDoc = documentoDao.findFilasDXETree(idUsuario, idDocumento, true);

	        for (FilaBandejaUF documentoItemUF : listDoc) {
	    	        Documento documento = findByIdDocumento(documentoItemUF.getId());
	    	        if(idDocumento != documento.getIdDocumento().intValue()){
	           			lstDocumento.add(documento);
	    			}
	        }

	        return  lstDocumento;

		}

		@Override
		public List<SimpleNode> getDojoDocumentoRecibidoTree(
				BusquedaAvanzada objFiltro) {
			log.debug("-> [Service] DocumentoService - getDojoDocumentoRecibidoTree():List<Nodo> ");

	        Usuario objUsuario = null;
	        mapSession = ActionContext.getContext().getSession();
			objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

			if (objUsuario == null) {
				log.error("Expiro la sesion");
				return null;
			}

			List<FilaBandejaUF> listDoc = null;
			List<FilaBandejaUF> listDocumentosRecibidos = new ArrayList<FilaBandejaUF>();
			//listDocumentosRecibidos =null;

			listDocumentosRecibidos = getContDocUsuarioFinal(objUsuario, true,objFiltro);

			List<SimpleNode> nodo3 = new ArrayList<SimpleNode>();

			//nodo3 = null;

			if(listDocumentosRecibidos !=null){
				log.error("Retorna Valores");
				for (int i =0; i<listDocumentosRecibidos.size();i++) {
	    	        Documento documento = findByIdDocumento(listDocumentosRecibidos.get(i).getId());
	    	        listDoc = documentoDao.findFilasDXETree(objUsuario.getIdusuario(), documento.getIdDocumento(), true);
	    	        List <SimpleNodeLeaf> list = new ArrayList <SimpleNodeLeaf>();

	    	        for (int k =0;k<listDoc.size();k++) {
		    	        Documento documentoTree = findByIdDocumento(listDoc.get(k).getId());
		    	        if(documento.getIdDocumento().intValue() != documentoTree.getIdDocumento().intValue()){
		    	        	SimpleNodeLeaf n = new SimpleNodeLeaf();
			    			n.setId(String.valueOf(i+"."+documentoTree.getIdDocumento()));
			    			n.setLabel(documentoTree.getTipoDocumento().getNombre() + " - " + documentoTree.getNumeroDocumento());
			    			list.add(n);
		    			}
		             }
	    	        SimpleNode m = new SimpleNode();
	    			m.setId(String.valueOf(i+"."+documento.getIdDocumento()));
	    			m.setLabel(documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento());
	    			m.setChildren(list);
	    			nodo3.add(m);
	        }
				return nodo3;

			}else {
				log.error("Retorna Nulo");
				nodo3 = null;
				return nodo3;

			}
		}

		//@Override
               

		public TrazabilidaddocumentoDAO getTrazabilidaddocumentoDAO() {
			return trazabilidaddocumentoDAO;
		}

		public void setTrazabilidaddocumentoDAO(
				TrazabilidaddocumentoDAO trazabilidaddocumentoDAO) {
			this.trazabilidaddocumentoDAO = trazabilidaddocumentoDAO;
		}

		public TrazabilidadmovimientoDAO getTrazabilidadMovimientoDAO() {
			return trazabilidadMovimientoDAO;
		}

		public void setTrazabilidadMovimientoDAO(
				TrazabilidadmovimientoDAO trazabilidadMovimientoDAO) {
			this.trazabilidadMovimientoDAO = trazabilidadMovimientoDAO;
		}
                
                 public DocumentoPendienteDAO getDocumentoPendienteDAO() {
                    return documentoPendienteDAO;
                }

                public void setDocumentoPendienteDAO(DocumentoPendienteDAO documentoPendienteDAO) {
                    this.documentoPendienteDAO = documentoPendienteDAO;
                }

                public DocumentoFedatarioDAO getDocumentoFedatarioDao() {
                        return documentoFedatarioDao;
                }

                public void setDocumentoFedatarioDao(DocumentoFedatarioDAO documentoFedatarioDao) {
                        this.documentoFedatarioDao = documentoFedatarioDao;
                }
                
                public DocumentoReferenciaDAO getDocumentoReferenciaDAO() {
		return documentoReferenciaDAO;
            }

            public void setDocumentoReferenciaDAO(
                            DocumentoReferenciaDAO documentoReferenciaDAO) {
                    this.documentoReferenciaDAO = documentoReferenciaDAO;
            }
            public DocumentoReunionDAO getDocumentoReunionDAO() {
                return documentoReunionDAO;
            }

            public void setDocumentoReunionDAO(DocumentoReunionDAO documentoReunionDAO) {
                this.documentoReunionDAO = documentoReunionDAO;
            }
            
            public NumeracionDAO getNumeracionDAO() {
                return numeracionDAO;
            }

            public void setNumeracionDAO(NumeracionDAO numeracionDAO) {
                this.numeracionDAO = numeracionDAO;
            }
            
            public List<Documento> getDocumentoByTipoByUnidad(String tipodocuento, String unidadautor, Integer expediente) {
                                return documentoDao.getDocumentoByTipoByUnidad(tipodocuento, unidadautor, expediente);
            }

}

