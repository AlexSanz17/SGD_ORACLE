package org.ositran.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.ositran.daos.EstadoDAO;
import org.ositran.daos.EtapaDAO;
import org.ositran.daos.ExpedienteDAO;
import org.ositran.daos.ExpedientestorDAO;
import org.ositran.dojo.tree.NodoArbol;
import org.ositran.utils.Constantes;
import org.ositran.utils.DocumentoDetail;
import org.ositran.utils.Expedienfindadvance;
import org.ositran.utils.ExpedienteList;
import org.ositran.utils.ExpedienteTree;
import org.ositran.utils.StringUtil;
import org.springframework.transaction.annotation.Transactional;
import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.Carpeta;
import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Documentoxexpediente;
import com.btg.ositran.siged.domain.Estado;
import com.btg.ositran.siged.domain.Etapa;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Expedientestor;
import com.btg.ositran.siged.domain.Serie;
import com.btg.ositran.siged.domain.Usuario;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.ositran.utils.DocumentoPublicar;

public class ExpedienteServiceImpl implements ExpedienteService {

    private static Logger log = Logger.getLogger(ExpedienteServiceImpl.class);
    private ActividadService srvActividad;
    private ConcesionarioService srvC;
    private DocumentoService documentoService;
    private ExpedienteDAO dao;
    private ExpedientestorDAO daoES;
    private ProcesoService srvP;
    private ClienteService srvS;
    private RepositorioService repositorioService;
    private TipodocumentoService srvTipoDocumento;
    private AuditoriaService srvAuditoria;
    //private TrazabilidaddocumentoService trazabilidadDocumentoService;
    private EtapaDAO etapaDAO;
    private EstadoDAO estadoDAO;
    private ParametroService srvParametro;

    public ExpedienteServiceImpl(ExpedienteDAO dao, ConcesionarioService srvC, ProcesoService srvP, ClienteService srvS) {
        setDao(dao);
        setSrvC(srvC);
        setSrvP(srvP);
        setSrvS(srvS);
    }

    public Expediente findByIdExpediente(Integer iIdExp) {
        return getDao().findByIdExpediente(iIdExp);
    }

    public Expediente findPropietarioByIdExpediente(Integer iIdExp) {
        return getDao().findPropietarioByIdExpediente(iIdExp);
    }
    /*public Expediente findByExpedienteSAS(Integer iId){
    return null ;// getDao().findByExpedienteSAS(iId);
    }*/

    @Transactional
    public void aplicarNumeracionInternaExpediente(Integer iIdExp) {
        this.getDao().aplicarNumeracionInternaExpediente(iIdExp);
    }

    @Transactional
    public String puedeRechazar(Integer idExpediente) {
        Integer cantTraza = this.getDao().getCantidadTrazabilidad(idExpediente);

        if (cantTraza != null && cantTraza.intValue() > 0) {
            return Constantes.PUEDE_RECHAZAR;
        }
        return Constantes.NO_PUEDE_RECHAZAR;

    }

    @Override
    @Transactional
    public Expediente prepareExpediente(DocumentoDetail objDD, Usuario objUsuario) {
          
        Cliente objCliente = null;
        Expediente objExpediente = null;
        objCliente = srvS.updateInfoCliente(objDD);
        Serie serie = new Serie();
        
        if (objDD.getIIdExpediente() != null) {
            objExpediente = dao.findByIdExpediente(objDD.getIIdExpediente());
            objExpediente.setNuevo(false);
            return objExpediente;
        }
        
        
        objExpediente = new Expediente();
        objExpediente.setCliente(objCliente);
        objExpediente.setObservacion(objDD.getObservacionExpediente());
        objExpediente.setIdpropietario(new Usuario(objUsuario.getIdUsuarioPerfil()));
        objExpediente.setFechacreacion(new Date());
        objExpediente.setUsuariocreacion(objUsuario.getIdusuario());
         
        if (StringUtil.isEmpty(objDD.getsNroExpediente())) {
            objExpediente.setNroexpediente("NOTYET");
            objExpediente.setHistorico('N');
        } else {
            objExpediente.setNroexpediente(objDD.getsNroExpediente());
            objExpediente.setHistorico('S');
        }

        objExpediente.setAsunto(objDD.getAsuntoExpediente());
        objExpediente.setContenido(objDD.getStrContenido());
        objExpediente.setFechacreacion(new Date());
        objExpediente.setEstado(Constantes.ESTADO_ACTIVO);
        objExpediente.setEstaenflujo(Constantes.ESTAENFLUJO_N);
        serie.setIdserie(objDD.getiIdSerie());
        objExpediente.setSerie(serie);
        
        if (StringUtil.isEmpty(objDD.getsNroExpediente())) {
            objExpediente.setNroexpediente(this.generateNroExpediente(objExpediente.getIdexpediente()));
        }
        
        objExpediente = this.saveExpediente(objExpediente);
        objExpediente.setNuevo(true);

        return objExpediente;
    }

    public ExpedienteTree getExpedienteTree(Integer iIdExpediente) {
        Expediente objExpediente = getDao().findByIdExpediente(iIdExpediente);
        List<Carpeta> lstCarpeta = new ArrayList<Carpeta>();
        List<ExpedienteTree> lstTreeDocumento = new ArrayList<ExpedienteTree>();
        // seleccionar las carpetas que se enceutnran en la raiz
        for (Carpeta objCarpeta : objExpediente.getCarpetaList()) {
            if (objCarpeta.getCarpetapadre() == null) {
                lstCarpeta.add(objCarpeta);
            }
        }
        lstTreeDocumento = this.getArbolCarpeta(lstTreeDocumento, lstCarpeta);
        List<Documentoxexpediente> lstDocXExp = objExpediente.getDocumentoxexpedienteList();
        for (Documentoxexpediente objDocXExp : lstDocXExp) {
            Documento objDocumento = objDocXExp.getDocumento();
            log.debug("Documento Virtual con ID [" + objDocumento.getIdDocumento() + "]");
            ExpedienteTree objETDocumento = new ExpedienteTree("D-" + objDocumento.getIdDocumento(), "<img src='images/flechita.gif' align='left' border='0' alt='Adjunto'>" + objDocumento.getTipoDocumento().getNombre() + " - " + objDocumento.getNumeroDocumento() + "\" onclick=\"alert('5555hooo id:" + objDocumento.getIdDocumento() + "')   ");
            lstTreeDocumento.add(objETDocumento);
        }
        ExpedienteTree objExpedienteTree = new ExpedienteTree("E-" + iIdExpediente, objExpediente.getNroexpediente());
        objExpedienteTree.setLstExpedienteChildren(lstTreeDocumento);
        return objExpedienteTree;
    }

    @Transactional
    public void actualizarResponsableExpediente(Integer idExpediente, Usuario idpropietario) {
        dao.actualizarResponsableExpediente(idExpediente, idpropietario);
    }

    public List<ExpedienteTree> getArbolCarpeta(List<ExpedienteTree> listallenar, List<Carpeta> carpetas) {
        for (Carpeta objCarpeta : carpetas) {
            List<Carpeta> lstinternaCarpetas = objCarpeta.getCarpetaList();
            List<ExpedienteTree> listaInterna = new ArrayList<ExpedienteTree>();
            ExpedienteTree objETCarpeta = new ExpedienteTree("C-" + objCarpeta.getIdcarpeta(), "<img src='images/folder.gif' align='left' border='0' alt='Adjunto'>" + objCarpeta.getNombre() + "\" onclick=\"alert('5555hooo id:" + objCarpeta.getIdcarpeta() + "')   ");
            
            if (lstinternaCarpetas != null && lstinternaCarpetas.size() > 0) {
                listaInterna = this.getArbolCarpeta(listaInterna, lstinternaCarpetas);
            }
            
            objETCarpeta.setLstExpedienteChildren(listaInterna);
            listallenar.add(objETCarpeta);
        }
        return listallenar;
    }

    /*
    public List<NodoArbol> getDojoExpedienteTreeX(Integer idExpediente, Integer idDocumento) {
        Expediente objExpediente = dao.findByIdExpediente(idExpediente);
        List<NodoArbol> lstTreeDocumento = new ArrayList<NodoArbol>();
        List<NodoArbol> allElements = new ArrayList<NodoArbol>();
        List<Documento> documentos = documentoService.getDocumentosPorExpediente(idExpediente);
        boolean contieneDoc = false; 

        for (Documento documento : documentos) {
        	if(documento.getDocumentoreferencia() == null){
        		if(documento.getIdDocumento().intValue() == idDocumento.intValue()){
        			contieneDoc = true;
        		}
                        if(String.valueOf(documento.getEstado()).equals(String.valueOf(Constantes.ESTADO_ANULADO))){
                                NodoArbol objETDocumento = new NodoArbol(false, "D-" + documento.getIdDocumento(), documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento() + " (Anulado)", null);
                                lstTreeDocumento.add(objETDocumento);
                        }else{
                                NodoArbol objETDocumento = new NodoArbol(false, "D-" + documento.getIdDocumento(), documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento(), null);
                                lstTreeDocumento.add(objETDocumento);
                        }
                            
        	}
        }
        if(!contieneDoc){
        	Documento documento = documentoService.findByIdDocumento(idDocumento);
        	if(documento.getDocumentoreferencia() == null){
        		if(String.valueOf(documento.getEstado()).equals(String.valueOf(Constantes.ESTADO_ANULADO))){
                                NodoArbol objETDocumento = new NodoArbol(false, "D-" + documento.getIdDocumento(), documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento() + " (Anulado)", null);
                                lstTreeDocumento.add(objETDocumento);
                        }else{
                                NodoArbol objETDocumento = new NodoArbol(false, "D-" + documento.getIdDocumento(), documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento(), null);
                                    lstTreeDocumento.add(objETDocumento);
                            }
                            
        	}
        }
       
        
        NodoArbol objExpedienteTree = new NodoArbol(true, "E-" + idExpediente, objExpediente.getNroexpediente(), lstTreeDocumento); // new
        allElements.add(objExpedienteTree);
        allElements.addAll(lstTreeDocumento);
        return allElements;
    }*/
    
     public List<NodoArbol> getDojoDocumentoPublicarTree(Integer idExpediente, Integer idDocumento) {
        Expediente objExpediente = dao.findByIdExpediente(idExpediente);
        List<NodoArbol> lstTreeDocumento = new ArrayList<NodoArbol>();
        List<NodoArbol> allElements = new ArrayList<NodoArbol>();
        List<NodoArbol> allElementsTemp = new ArrayList<NodoArbol>();
        List<NodoArbol> allElementsTemp2 = new ArrayList<NodoArbol>();
        
        Documento dp = documentoService.findByIdDocumento(new Integer(idDocumento));
        List<DocumentoPublicar> documentos = documentoService.getDocumentosPorPublicar(idExpediente, dp.getDocumentoreferencia()==null?dp.getIdDocumento():dp.getDocumentoreferencia());
        String origen = "";
        String campo  = "";
        
        for (DocumentoPublicar documentoPublicar : documentos) {
            if (!documentoPublicar.getIdDocumentoRef().toString().equals(origen)){
                 
                if (!origen.equals("")){
                    Documento d = documentoService.findByIdDocumento(new Integer(origen));
                    NodoArbol objFlujoTree = new NodoArbol(false, "U-" + campo, d.getTipoDocumento().getNombre() + " - " + d.getNumeroDocumento() , lstTreeDocumento);
                    allElementsTemp.add(objFlujoTree);
                    allElementsTemp2.add(objFlujoTree);
                    allElementsTemp.addAll(lstTreeDocumento);
                }
                
                lstTreeDocumento = new ArrayList<NodoArbol>();
                origen = documentoPublicar.getIdDocumentoRef().toString();
                campo = documentoPublicar.getIdDocumento() + "|" + documentoPublicar.getIdDocumentoRef() + "|" + documentoPublicar.getIdArchivo();
                        
                NodoArbol objETDocumento = new NodoArbol(false,documentoPublicar.getIdrefarc()+ "|D-" + documentoPublicar.getIdDocumento() + "|" + documentoPublicar.getIdDocumentoRef() + "|" + documentoPublicar.getIdArchivo(), documentoPublicar.getNombre() ,documentoPublicar.getEstado() ,documentoPublicar.getOrden(), null);
                lstTreeDocumento.add(objETDocumento);  
            }else{	
                NodoArbol objETDocumento = new NodoArbol(false,documentoPublicar.getIdrefarc()+ "|D-" + documentoPublicar.getIdDocumento() + "|" + documentoPublicar.getIdDocumentoRef() + "|" + documentoPublicar.getIdArchivo(), documentoPublicar.getNombre() ,documentoPublicar.getEstado(), documentoPublicar.getOrden(),null);
                lstTreeDocumento.add(objETDocumento);                       
            }
        }
        
        if (!origen.equals("")){
            Documento d = documentoService.findByIdDocumento(new Integer(origen));
            NodoArbol objFlujoTree = new NodoArbol(false, "U-" + campo, d.getTipoDocumento().getNombre() + " - " + d.getNumeroDocumento() , lstTreeDocumento);
            allElementsTemp.add(objFlujoTree);
            allElementsTemp2.add(objFlujoTree);
            allElementsTemp.addAll(lstTreeDocumento);
        }
        
        NodoArbol objExpedienteTree = new NodoArbol(true, "E-" + idExpediente, objExpediente.getNroexpediente(), allElementsTemp2); // new
        allElements.add(objExpedienteTree);
        allElements.addAll(allElementsTemp);
        
        return allElements;
     }
    
    
     public List<NodoArbol> getDojoExpedienteTree(Integer idExpediente, Integer idDocumento, String valor) {
        Expediente objExpediente = dao.findByIdExpediente(idExpediente);
        List<NodoArbol> lstTreeDocumento = new ArrayList<NodoArbol>();
        List<NodoArbol> allElements = new ArrayList<NodoArbol>();
        List<NodoArbol> allElementsTemp = new ArrayList<NodoArbol>();
        List<NodoArbol> allElementsTemp2 = new ArrayList<NodoArbol>();
        
        List<Documento> documentos = documentoService.getDocumentosPorExpediente(idExpediente);
        String origen = "";
        
        for (Documento documento : documentos) {
        	if(documento.getDocumentoreferencia() == null){
        		if (!documento.getOrigen().toString().equals(origen)){
                            if (!origen.equals("")){
                                Documento d = documentoService.findByIdDocumento(new Integer(origen));
                                NodoArbol objFlujoTree = null;
                                
                                if(String.valueOf(d.getEstado()).equals(String.valueOf(Constantes.ESTADO_ANULADO))){
                                  objFlujoTree = new NodoArbol(false, "U-" + origen + "-" + (valor==null?"":valor), d.getTipoDocumento().getNombre() + " - " + d.getNumeroDocumento() + " (Anulado)" , lstTreeDocumento);
                                }else{
                                  objFlujoTree = new NodoArbol(false, "U-" + origen + "-" + (valor==null?"":valor), d.getTipoDocumento().getNombre() + " - " + d.getNumeroDocumento() , lstTreeDocumento);
                                }
                                
                                allElementsTemp.add(objFlujoTree);
                                allElementsTemp2.add(objFlujoTree);
                                allElementsTemp.addAll(lstTreeDocumento);
                            }
                            origen = documento.getOrigen().toString();
                            lstTreeDocumento = new ArrayList<NodoArbol>();
                        }else{	
                           if(String.valueOf(documento.getEstado()).equals(String.valueOf(Constantes.ESTADO_ANULADO))){
                                NodoArbol objETDocumento = new NodoArbol(false, "D-" + documento.getIdDocumento() + "-" + (valor==null?"":valor), documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento() + " (Anulado)", null);
                                lstTreeDocumento.add(objETDocumento);
                           }else{
                                NodoArbol objETDocumento = new NodoArbol(false, "D-" + documento.getIdDocumento() + "-" + (valor==null?"":valor), documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento() , null);
                                lstTreeDocumento.add(objETDocumento);
                           }
                        }
        	}
        }
        
        Documento d = documentoService.findByIdDocumento(new Integer(origen));
        
        NodoArbol objFlujoTree = new NodoArbol(false, "U-" + origen + "-" + (valor==null?"":valor), d.getTipoDocumento().getNombre() + " - " + d.getNumeroDocumento() , lstTreeDocumento);
        allElementsTemp.add(objFlujoTree);
        allElementsTemp2.add(objFlujoTree);
        allElementsTemp.addAll(lstTreeDocumento);
        
        
        NodoArbol objExpedienteTree = new NodoArbol(true, "E-" + idExpediente, objExpediente.getNroexpediente(), allElementsTemp2); // new
        allElements.add(objExpedienteTree);
        allElements.addAll(allElementsTemp);
        
        return allElements;
    }

    public List<NodoArbol> getDojoArbolCarpeta(List<NodoArbol> listallenar, List<Carpeta> carpetas, List<NodoArbol> listaAll) {
        for (Carpeta objCarpeta : carpetas) {
            List<Carpeta> lstinternaCarpetas = objCarpeta.getCarpetaList();
            List<NodoArbol> listaInterna = new ArrayList<NodoArbol>();
            if (lstinternaCarpetas != null && lstinternaCarpetas.size() > 0) {
                listaInterna = this.getDojoArbolCarpeta(listaInterna, lstinternaCarpetas, listaAll);
                //
            }
            NodoArbol objETCarpeta = new NodoArbol(false, "C-" + objCarpeta.getIdcarpeta(), objCarpeta.getNombre(), (listaInterna));
            listaAll.addAll(listaInterna);
            listallenar.add(objETCarpeta);
        }
        return listallenar;
    }

    public ExpedienteTree getExpedienteTreeArchivar(Integer iIdExpediente) {
        Expediente objExpediente = getDao().findByIdExpediente(iIdExpediente);
        List<Archivo> lstArchivo = null;
        List<Documento> lstDocumento = objExpediente.getDocumentoList();
        List<ExpedienteTree> lstTreeDocumento = new ArrayList<ExpedienteTree>();
        // List<ExpedienteTree> lstTreeUnidad = new ArrayList<ExpedienteTree>();
        for (Documento objDocumento : lstDocumento) {
            List<ExpedienteTree> lstTreeArchivo = new ArrayList<ExpedienteTree>();
            lstArchivo = objDocumento.getArchivos();
            
            for (Archivo objArchivo : lstArchivo) {
                int iBracket = objArchivo.getNombre().indexOf("]");
                if (iBracket != -1) {
                    objArchivo.setNombre(objArchivo.getNombre().substring(iBracket + 1));
                }
                ExpedienteTree objETArchivo = new ExpedienteTree("" + objArchivo.getIdArchivo(), objArchivo.getNombre());
                lstTreeArchivo.add(objETArchivo);
            }
            ExpedienteTree objETDocumento = new ExpedienteTree("" + objDocumento.getIdDocumento(), objDocumento.getTipoDocumento().getNombre() + " - " + (objDocumento.getNumeroDocumento() != null ? objDocumento.getNumeroDocumento() : "s/n"));
            objETDocumento.setDelexpediente(objDocumento.getDelExpediente());
            log.debug(" setting docid:" + objDocumento.getIdDocumento() + " and delexpediente  " + objDocumento.getDelExpediente());
            log.debug(" objETDocumento: " + objETDocumento.getDelexpediente());
            objETDocumento.setLstExpedienteChildren(lstTreeArchivo);
            lstTreeDocumento.add(objETDocumento);
        }
        ExpedienteTree objExpedienteTree = new ExpedienteTree("" + iIdExpediente, objExpediente.getNroexpediente());
        objExpedienteTree.setLstExpedienteChildren(lstTreeDocumento);
        return objExpedienteTree;
    }

    @Transactional
    public Expedientestor saveExpedienteStor(Expediente objE, DocumentoDetail objDD) {
        Expedientestor objExpStor = new Expedientestor();
        if (getDaoES().findByIdExpediente(objE.getIdexpediente()) == null) {
            Estado objEstado = estadoDAO.findByCodigo("anls");
            Etapa objEtapa = etapaDAO.findByCodigo("traming");
            objExpStor.setExpediente(objE);
            objExpStor.setIdexpediente(objE.getIdexpediente());
            objExpStor.setEstado(objEstado);
            objExpStor.setIdestadosiged(objEstado);
            objExpStor.setEtapa(objEtapa);
            getDaoES().saveExpedienteStor(objExpStor);
        } else {
            objExpStor = getDaoES().findByIdExpediente(objE.getIdexpediente());
            objExpStor.setTipoexpediente(srvP.findByIdProceso(objDD.getIIdProceso()).getCodigo());
            log.debug("Tipo de Expediente [" + objExpStor.getTipoexpediente() + "]");
            getDaoES().updateExpedienteStor(objExpStor);
        }
        return objExpStor;
    }

    public List<Expediente> findAll() {
        return getDao().findAll();
    }

    public List<ExpedienteList> fillExpedienteList(List<Expediente> lstE) throws RuntimeException {
        try {
            List<ExpedienteList> lstEL = new ArrayList<ExpedienteList>();
            for (Expediente objE : lstE) {
                ExpedienteList objEL = null;//new ExpedienteList(objE.getFechacreacion(), objE.getIdexpediente(), objE.getCliente().getRazonSocial(), objE.getNroexpediente(), objE.getProceso().getResponsable().getNombres() + " " + objE.getProceso().getResponsable().getApellidos(), objE.getProceso().getResponsable().getUnidad().getNombre());
                lstEL.add(objEL);
            }
            return lstEL;
        } catch (RuntimeException re) {
            log.error("", re);
            return null;
        }
    }

    public List<Expediente> filtrarExpediente(Expediente objExpediente) {
        log.debug("** Filtro de Expediente **");
        log.debug("Nro Expediente [" + objExpediente.getNroexpediente() + "]");
        //log.debug("Proceso [" + objExpediente.getProceso().getNombre() + "]");
        log.debug("Cliente - Nro Identificacion [" + objExpediente.getCliente().getNumeroIdentificacion() + "]");
        log.debug("Cliente - Razon Social [" + objExpediente.getCliente().getRazonSocial() + "]");
        log.debug("Concesionario - RUC [" + objExpediente.getConcesionario().getRuc() + "]");
        log.debug("Concesionario - Razon Social [" + objExpediente.getConcesionario().getRazonSocial() + "]");
        return null;
        //return dao.filtrarExpediente(objExpediente.getNroexpediente(), objExpediente.getProceso().getNombre(), objExpediente.getCliente().getNumeroIdentificacion(), objExpediente.getCliente().getRazonSocial(), objExpediente.getConcesionario().getRuc(), objExpediente.getConcesionario().getRazonSocial());
    }

    public List<ExpedienteList> findByCriteria(String sNroIdentificacion, String sRazonSocial, String sNroExpediente, String sAsunto, Integer idusuario) {
        log.debug("** findByCriteria **");
        List<Expediente> lstExp;
        /*
         * if (strNI.isEmpty() && strRS.isEmpty() && strR.isEmpty()) { lstExp =
         * getDao().findAll(); } else {
         */
        lstExp = getDao().findByCriteria(sNroIdentificacion, sRazonSocial, sNroExpediente, sAsunto, 0, null, null, idusuario,null);
        // }
        return this.fillExpedienteList(lstExp);
    }

    public List<Expediente> findbyNroExpediente(String NrDoc) {
        log.debug("** findbyNroExpediente **");
        return dao.findByNroDocument(NrDoc);
    }

    public List<Expedienfindadvance> findbyadvancedinn(String Strcampo) {
        log.debug("** findbyadvancedinn **");
        List<Expedienfindadvance> listaExpediente = dao.findbyadvanced(Strcampo);
        log.debug(" listaExpediente.size:" + listaExpediente != null ? listaExpediente.size() : "null");
       
        return listaExpediente;
    }

    @Override
    public List<Expedienfindadvance> findbysuperadvanced(boolean alfresco, String numeroExpediente, String numeroDocumento, String numeroMesaPartes, String tipoDocumento, String concesionario, String cliente, String areaDestino, String propietario, String proceso, String contenido, String tipoBusqueda, String asuntoexpediente, String asuntodocumento, String estadoexpediente) {
        log.debug("estadoexpediente [" + estadoexpediente + "]");
        List<Expedienfindadvance> data = null;
        List<Integer> data2 = new ArrayList<Integer>();
        StringBuilder condicion = new StringBuilder("");
        
        if (!contenido.equals("")) {
            // Map propiedades=new HashMap();
            if (alfresco) {
                data2 = repositorioService.busquedaIdDocumentos(contenido.toLowerCase(), null);
                if (data2 != null) {
                    for (int i = 0; i < data2.size(); i++) {
                        if (i != 0) {
                            condicion.append(" OR ");
                        }
                        condicion.append("d.idDocumento=");
                        condicion.append(data2.get(i));
                    }
                    data = dao.findbyadvancedId(condicion.toString());
                } else {
                    data = new ArrayList<Expedienfindadvance>();
                }
            }
        } else {
            data = dao.findbySuperadvanced(numeroExpediente.toLowerCase(), numeroDocumento.toLowerCase(), numeroMesaPartes.toLowerCase(), tipoDocumento.toLowerCase(), concesionario.toLowerCase(), cliente.toLowerCase(), areaDestino.toLowerCase(), propietario.toLowerCase(), proceso.toLowerCase(), tipoBusqueda, asuntoexpediente.toLowerCase(), asuntodocumento.toLowerCase(), estadoexpediente.toLowerCase());
            log.debug("Numero de expedientes encontrados [" + data.size() + "]");
            
            return data;
        }
        return data;
    }

    
    @Override
    synchronized public String getMaxReferencia() {
        Integer iMaxIdExpediente = null;
        String sFormato = null;
        StringBuilder sbNroExpediente = null;

        try {
            iMaxIdExpediente = dao.getMaxIdExpediente();
            iMaxIdExpediente = (iMaxIdExpediente == null) ? Constantes.EXPEDIENTE_FIRST_ID : iMaxIdExpediente++;
            sFormato = srvParametro.findByTipoUnico(Constantes.PARAMETRO_TIPO_PREFIJONROEXPEDIENTE).getValor();
            sbNroExpediente = new StringBuilder((StringUtil.isEmpty(sFormato)) ? "" : sFormato);
            sbNroExpediente.replace(sFormato.length() - iMaxIdExpediente.toString().length(), sFormato.length(), iMaxIdExpediente.toString());

            if (log.isDebugEnabled()) {
                log.debug("formato [" + sFormato + "] max idExpediente [" + iMaxIdExpediente.toString() + "]");
            }

            log.info("Nro de Expediente generado [" + sbNroExpediente.toString() + "]");

            return sbNroExpediente.toString();
        } catch (IndexOutOfBoundsException ex) {
            return iMaxIdExpediente.toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            return null;
        }
    }

    @Override
    public String generateNroExpediente(Integer iIdExpediente) {
        String sFormato = null;
        StringBuilder sbNroExpediente = null;
        String sPrefijo = null;
        
        Date date = new Date();
        DateFormat formatoAno = new SimpleDateFormat("yyyy");
        
        sPrefijo = Constantes.PARAMETRO_TIPO_PREFIJONROEXPEDIENTEPRODUCCION;
        iIdExpediente = dao.generateNroExpedienteProduccion();
        
        sFormato = formatoAno.format(date).concat(srvParametro.findByTipoUnico(sPrefijo).getValor());
        sbNroExpediente = new StringBuilder((StringUtil.isEmpty(sFormato)) ? "" : sFormato);
        sbNroExpediente.replace(sFormato.length() - iIdExpediente.toString().length(), sFormato.length(), iIdExpediente.toString());
        
        return sbNroExpediente.toString();
    }

    @Transactional
    public void anularExpediente(Expediente objExpediente) {
        String sObservacion = objExpediente.getObservacion();
        objExpediente = this.findByIdExpediente(objExpediente.getIdexpediente());
        objExpediente.setObservacion(sObservacion);
        objExpediente.setEstado(Constantes.ESTADO_ANULADO);
        log.debug("Nro Expediente [" + objExpediente.getNroexpediente() + "]");
        log.debug("Observacion del Expediente [" + objExpediente.getObservacion() + "]");
        documentoService.inactivarDocumentos(objExpediente.getIdexpediente());
        this.saveExpediente(objExpediente);
    }

    @Transactional
    public Expediente saveExpediente(Expediente objExpediente) {
        return getDao().saveExpediente(objExpediente);
    }

    @Transactional
    public void registrarExpediente(Expediente objExpedienteOld, Expediente objExpedienteNew, String sUsuarioSesion, String sTipoAuditoria) {
        try {
            srvAuditoria.aplicarAuditoria(objExpedienteOld, objExpedienteNew, sUsuarioSesion, Constantes.AUDITORIA_OPCION_GUARDAR, sTipoAuditoria);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        objExpedienteNew = dao.saveExpediente(objExpedienteNew);
        log.debug("Expediente guardado con ID [" + objExpedienteNew.getIdexpediente() + "] Nro Expediente [" + objExpedienteNew.getNroexpediente() + "]");

    }

    public ActividadService getSrvActividad() {
        return srvActividad;
    }

    public void setSrvActividad(ActividadService srvActividad) {
        this.srvActividad = srvActividad;
    }

    @Transactional
    public Expediente buscarObjPor(String sNroExpediente) {
        try {
            return dao.findByNroExpediente(sNroExpediente);
        } catch (NoResultException nre) {
            return null;
        }
    }

    // //////////////////////
    // Getters and Setters
    // //////////////////////
    public ConcesionarioService getSrvC() {
        return srvC;
    }

    public void setSrvC(ConcesionarioService srvC) {
        this.srvC = srvC;
    }

    public void setDocumentoService(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    public ExpedienteDAO getDao() {
        return dao;
    }

    public void setDao(ExpedienteDAO dao) {
        this.dao = dao;
    }

    public ExpedientestorDAO getDaoES() {
        return daoES;
    }

    public void setDaoES(ExpedientestorDAO daoES) {
        this.daoES = daoES;
    }

    public ProcesoService getSrvP() {
        return srvP;
    }

    public void setSrvP(ProcesoService srvP) {
        this.srvP = srvP;
    }

    public ClienteService getSrvS() {
        return srvS;
    }

    public void setSrvS(ClienteService srvS) {
        this.srvS = srvS;
    }

    public RepositorioService getRepositorioService() {
        return repositorioService;
    }

    public void setRepositorioService(RepositorioService repositorioService) {
        this.repositorioService = repositorioService;
    }

    public TipodocumentoService getSrvTipoDocumento() {
        return srvTipoDocumento;
    }

    public void setSrvTipoDocumento(TipodocumentoService srvTipoDocumento) {
        this.srvTipoDocumento = srvTipoDocumento;
    }

    public AuditoriaService getSrvAuditoria() {
        return srvAuditoria;
    }

    public void setSrvAuditoria(AuditoriaService srvAuditoria) {
        this.srvAuditoria = srvAuditoria;
    }


    @Override
    public List<Expedienfindadvance> busquedaAvanzadaAdicional(boolean alfresco, String numeroExpediente, String tipoDocumento, String numeroDocumento, String identidadConcesionario, String numeroMesaPartes, String fechaInicioDocumento, String fechaFinDocumento, String concesionario, String fechaInicioExpediente, String fechaFinExpediente, String cliente, String direccionCliente, String areaDestino, String propietario, String proceso, String contenido, String tipoBusqueda, String asuntoexpediente, String asuntodocumento, String estadoexpediente) {
        log.debug(" findbysuperadvanced.contenido : " + contenido);
        if (!contenido.equals("")) {
            if (alfresco) {
                List<Integer> ids = getRepositorioService().busquedaIdDocumentos(contenido.toLowerCase(), null);
                if (ids != null) {
                    StringBuilder condicion = new StringBuilder("");
                    for (int i = 0; i < ids.size(); i++) {
                        Integer id = ids.get(i);
                        if (i != 0) {
                            condicion.append(" OR ");
                        }
                        condicion.append("d.iddocumento=");
                        condicion.append(id);
                    }
                    return dao.findbyadvancedId(condicion.toString());
                }
            }
        } else {
            return dao.busquedaAvanzadaAdicional(numeroExpediente, tipoDocumento, numeroDocumento, identidadConcesionario, numeroMesaPartes, fechaInicioDocumento, fechaFinDocumento, concesionario, fechaInicioExpediente, fechaFinExpediente, cliente, direccionCliente, areaDestino, propietario, proceso, tipoBusqueda, asuntoexpediente, asuntodocumento, estadoexpediente);
        }
        return null;
    }

  
    //public void setTrazabilidadDocumentoService(TrazabilidaddocumentoService trazabilidadDocumentoService) {
    //    this.trazabilidadDocumentoService = trazabilidadDocumentoService;
   // }

    public EstadoDAO getEstadoDAO() {
        return estadoDAO;
    }

    public void setEstadoDAO(EstadoDAO estadoDAO) {
        this.estadoDAO = estadoDAO;
    }

    public EtapaDAO getEtapaDAO() {
        return etapaDAO;
    }

    public void setEtapaDAO(EtapaDAO etapaDAO) {
        this.etapaDAO = etapaDAO;
    }
 
    public List<Expediente> findLstBy(Integer iIdResponsable) {
        return dao.findLstBy(iIdResponsable);
    }

    public ParametroService getSrvParametro() {
        return srvParametro;
    }

    public void setSrvParametro(ParametroService srvParametro) {
        this.srvParametro = srvParametro;
    }

    @Override
    public Expediente findObjectBy(String numeroExpediente, Character estado) {
        if (log.isDebugEnabled()) {
            log.debug("Buscando expediente con numeroExpediente [" + numeroExpediente + "] estado [" + estado + "]");
        }

        if (StringUtil.isEmpty(numeroExpediente) || estado == null) {
            return null;
        }

        return dao.findObjectBy(numeroExpediente, estado);
    }

}