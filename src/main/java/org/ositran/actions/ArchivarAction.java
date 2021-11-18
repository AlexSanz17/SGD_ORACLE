/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoReferencia;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.ositran.services.DocumentoService;
import org.ositran.services.ExpedienteService;
import org.ositran.services.NotificacionService;
import org.ositran.services.UsuarioService;


import org.ositran.utils.Constantes;
import org.ositran.utils.ExpedienteTree;

import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.json.annotations.SMDMethod;
import org.ositran.services.DocumentoReferenciaService;
import org.ositran.services.UnidadService;
import org.ositran.utils.StringUtil;

/**
 *
 * @author Himizu
 */
public class ArchivarAction implements ServletRequestAware, ServletResponseAware {

    private ExpedienteTree arbol;
    private static Logger log = Logger.getLogger(ArchivarAction.class);
    HttpServletRequest request;
    private String cerrar;
    private String location;
    private String sObservacion;
    private List<String> condestinatarios;
    private NotificacionService notificacionService;
    private Map<String, Object> mapSession;
    private String strReferencia;
    private String tipoArchivar;
    private List<DocumentoReferencia> documentosReferencia;
    private Integer[] arrIdDoc;
    List<Documento> documentos;
    private String listaDocumentos = "";
    private Documento documento;
    private UnidadService unidadService;
    HttpServletResponse response;
    Integer idDocumento;
    Integer idExpediente;
    Integer idResponsableProceso;
    String nombreResponsableProceso;
    Integer idResponsableExpediente;
    String nombreResponsableExpediente;
    private Integer codigoVirtual;
    private DocumentoReferenciaService documentoReferenciaService;
    DocumentoService documentoService;
    UsuarioService usuarioService;
    ExpedienteService expedienteService;

    
    public DocumentoReferenciaService getDocumentoReferenciaService() {
        return documentoReferenciaService;
    }
   
    public void setDocumentoReferenciaService(DocumentoReferenciaService documentoReferenciaService) {
        this.documentoReferenciaService = documentoReferenciaService;
    }
    public Integer getCodigoVirtual() {
        return codigoVirtual;
    }

    public void setCodigoVirtual(Integer codigoVirtual) {
        this.codigoVirtual = codigoVirtual;
    }
    
    
    public String inicio() {
        log.debug("-> [Action] ArchivarAction - inicio()");
        Integer[] arrIdDoc = new Integer[1];
        documento = documentoService.findByIdDocumento(idDocumento);
        try {
            log.debug(" idDocumento : " + idDocumento);
            mapSession = ActionContext.getContext().getSession();
            arrIdDoc[0] = documento.getIdDocumento();
            mapSession.put("arrIdDoc", arrIdDoc);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }

        return "inicio";
    }

    public String inicio1() {
        log.debug("-> [Action] ArchivarAction - inicio1()");
        Integer[] arrIdDoc = new Integer[1];
        documento = documentoService.findByIdDocumento(idDocumento);
         
        try {
             
             if (tipoArchivar.equals("atender"))
                documentosReferencia = documentoReferenciaService.getDocumentoAtenderRespuesta(idDocumento); //where idDocumentoreferencoa=?
              
             mapSession = ActionContext.getContext().getSession();
             arrIdDoc[0] = documento.getIdDocumento();
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }

        return "inicio1";
    }

    public String inicioMasivo() {
        Documento doc = null;

        try {
            mapSession = ActionContext.getContext().getSession();
            String codigos = (String) this.mapSession.get("lista");
            String[] listaCod = codigos.split(";");
            this.mapSession.put("arrIdDoc", listaCod);
            documentos = new ArrayList<Documento>();
            this.condestinatarios = new ArrayList<String>();
            for (int i = 0; i < listaCod.length; i++) {
                log.debug("ID a archivar " + listaCod[i]);
                if (listaCod[i].trim().length() > 0) {
                    idDocumento = new Integer(listaCod[i]);
                } else {
                    continue;
                }
             
                doc = documentoService.findByIdDocumento(idDocumento);
                documentos.add(doc);

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(e.getMessage(), e);
        }

        return "inicioMasivo";
    }

    public String inicio2() {
        Integer[] arrIdDoc = new Integer[1];

        try {
            if (log.isDebugEnabled()) {
                log.debug("idExpediente [" + idExpediente + "]");
            }

            Expediente expediente = expedienteService.findByIdExpediente(idExpediente);

            arbol = expedienteService.getExpedienteTreeArchivar(idExpediente);
           // idResponsableProceso = expediente.getProceso().getResponsable().getIdusuario();
           // nombreResponsableProceso = expediente.getProceso().getResponsable().getNombres();
            idResponsableExpediente = expediente.getIdpropietario().getIdusuario();
            nombreResponsableExpediente = expediente.getIdpropietario().getNombres();

            mapSession = ActionContext.getContext().getSession();
            arrIdDoc[0] = expediente.getDocumentoPrincipal().getIdDocumento();
            mapSession.put("arrIdDoc", arrIdDoc);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return "inicio";
    }

    public String reabrirExpediente() {
        log.debug("-> [Action] ArchivarAction - reabrirExpediente():String ");
        try {
           documento = documentoService.findByIdDocumento(idDocumento);
            Map<String, Object> session = ActionContext.getContext().getSession();
            Usuario usuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
            String nombrePC = (String) session.get("nombrePC");
            documentoService.reabrirDocumento(documento, usuario, nombrePC);
            return "reabrir";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Action.ERROR;
        }
    }

    public void reabrirMasivo(){
    	if(arrIdDoc != null && arrIdDoc.length > 0){
    		for(Integer idDoc : arrIdDoc){
    			Documento objDoc = documentoService.findByIdDocumento(idDoc);
    			if(objDoc.getEstado() == Constantes.ESTADO_CERRADO){
	                Map<String, Object> session = ActionContext.getContext().getSession();
	                Usuario usuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
	                String nombrePC = (String) session.get("nombrePC");
	                documentoService.reabrirDocumento(objDoc, usuario, nombrePC);
    			}
    		}
    	}
    }

    /**REN Metodo usado en el Archivar masivamente ----------------------------------------------------------------------------*/
    @SuppressWarnings("unused")
	public String guardarArchivamientoMasivo() {
        try {
            log.debug("ARCHIVAR_MASIVO");
            List<Usuario> listUsuarios = null;
            List<Documento> documentosArchivar = new ArrayList<Documento>();
            Documento objDocumento = new Documento();
            String[] documentos = this.getRequest().getParameterValues("documentos");
            int[] idDocumentos;
            if (documentos != null && documentos.length > 0) {
                idDocumentos = new int[documentos.length];
                for (int i = 0; i < idDocumentos.length; i++) {
                    idDocumentos[i] = Integer.parseInt(documentos[i]);
                }
            } else {
                idDocumentos = new int[0];
            }
            Map<String, Object> session = ActionContext.getContext().getSession();
            Usuario usuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
            String nombrePC = (String) session.get("nombrePC");
            String[] lista = (String[]) session.get("arrIdDoc");
            this.tipoArchivar = "archivar";
            
            for (int i = 0; i < lista.length; i++) {
                log.debug("ID a archivar " + lista[i]);
                idDocumento = new Integer(lista[i]);
                if (idDocumento != null) {
                	documentoService.archivarDocumento(idDocumento, usuario, sObservacion, tipoArchivar, nombrePC);
                }

                if (idDocumento != null) {
                    objDocumento = documentoService.findByIdDocumento(idDocumento);
                }
                if (getsObservacion() != null && !getsObservacion().equals("")) {
                    objDocumento.setObservacion(getsObservacion());
                }
                if (condestinatarios == null) {
                    this.condestinatarios = new ArrayList<String>();
                }

                if (condestinatarios != null && condestinatarios.size() > 0) {
                    listUsuarios = new ArrayList<Usuario>();
                    
                    for (String destinatario : condestinatarios) {
                        if (destinatario != null) {
                            String[] datosUsuarios = destinatario.substring(8).split("-");
                            Usuario objUsuario = usuarioService.findByIdUsuario(new Integer(datosUsuarios[0]));
                            objUsuario.setIdUnidadPerfil(new Integer(datosUsuarios[1]));
                            objUsuario.setIdFuncionPerfil(new Integer(datosUsuarios[2]));
                            
                            if (objUsuario != null) {
                                listUsuarios.add(objUsuario);
                            }
                        }
                    }
                }
                if (objDocumento != null && listUsuarios != null && listUsuarios.size() > 0) {
                    objDocumento.setUsuariosDestinatarios(listUsuarios);
                }
                //Define el tipo de envio de notificacion si es de tipo Archivar o Envio a OEFA
                if (Constantes.ACCION_ARCHIVAR.equals(tipoArchivar)) {
                    //notificacionService.informarViaNotifAndMail(usuario, objDocumento, Constantes.CONFIGNOTIFMAIL_DOCUMENTO_ARCHIVADO, Constantes.TIPO_NOTIFICACION_DOCUMENTO_ARCHIVADO, nombrePC);
                } 
                
                this.cerrar = "ok";
            }
            return "resultadoMasivo";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            this.cerrar = null;

            return Action.ERROR;
        }
    }


    public String guardar() {
        log.debug("-> [Action] ArchivarAction - guardar():String ");
        try {
            log.debug("TipoArchivar [" + tipoArchivar + "]");
            log.debug("Documento a archivar ["+idDocumento+"]");
            List<Usuario> listUsuarios = new ArrayList<Usuario>();
            Documento objDocumento = new Documento();
          
            Map<String, Object> session = ActionContext.getContext().getSession();
            Usuario usuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
            String nombrePC = (String) session.get("nombrePC");

            if (idDocumento != null) {
            	documentoService.archivarDocumento(idDocumento, usuario, getsObservacion(), "archivar", nombrePC);
            }
            
            if (condestinatarios != null && condestinatarios.size() == 1) {
                String array = this.condestinatarios.get(0);
                condestinatarios.clear();
                condestinatarios = Arrays.asList(array.split(","));
            }
            if (condestinatarios != null && condestinatarios.size() > 0) {
                Integer idUsuario = 0;
                Usuario objUsuario;

                for (String destinatario : condestinatarios) {
                    if (destinatario.startsWith("USUARIO_")) {
                        String[] datosUsuarios = destinatario.substring(8).split("-");
                        objUsuario = usuarioService.findByIdUsuario(new Integer(datosUsuarios[0]));
                        objUsuario.setIdUnidadPerfil(new Integer(datosUsuarios[1]));
                        objUsuario.setIdFuncionPerfil(new Integer(datosUsuarios[2]));
                        listUsuarios.add(objUsuario);
                    } else if (destinatario.startsWith("LISTA_")) {
                        idUsuario = new Integer(destinatario.substring(6));
                    }
                   
                }
            }
            if (idDocumento != null) {
                objDocumento = documentoService.findByIdDocumento(idDocumento);
            } else {
                objDocumento = expedienteService.findByIdExpediente(idExpediente).getDocumentoPrincipal();
            }
            if (getsObservacion() != null && !getsObservacion().equals("")) {
                objDocumento.setObservacion(getsObservacion());
            }
            if (objDocumento != null && listUsuarios != null && listUsuarios.size() > 0) {
                objDocumento.setUsuariosDestinatarios(listUsuarios);
            }
            if (Constantes.ACCION_ARCHIVAR.equals(tipoArchivar)) {
               // notificacionService.informarViaNotifAndMail(usuario, objDocumento, Constantes.CONFIGNOTIFMAIL_DOCUMENTO_ARCHIVADO, Constantes.TIPO_NOTIFICACION_DOCUMENTO_ARCHIVADO, nombrePC);
            }

            this.cerrar = "ok";

            return "resultado";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            this.cerrar = null;

            return Action.ERROR;
        }
    }


    public String atender() {
        try {
            log.debug("ArchivarAction::atender");
            log.debug("Documento a atender ["+idDocumento+"]");
            String[] listaReferencias = StringUtil.stringToArray(strReferencia);
            
            Map<String, Object> session = ActionContext.getContext().getSession();
            Usuario usuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
            String nombrePC = (String) session.get("nombrePC");
            Documento documento = documentoService.findByIdDocumento(idDocumento);
            
            if (idDocumento != null){  
              if (listaReferencias!=null && listaReferencias.length>0){
                  for(int i=0;i<listaReferencias.length;i++){
                     String[] arreglo = new String[1];
                     arreglo[0] = listaReferencias[i];
                     documentoService.atenderDocumento(usuario, documento, null, false, null, nombrePC, arreglo, getsObservacion(), codigoVirtual);
                  }
              }else{
                     documentoService.atenderDocumento(usuario, documento, null, false, null, nombrePC, listaReferencias, getsObservacion(),  codigoVirtual);
              }    
            }
            
            this.cerrar = "ok";

            return "resultado";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            this.cerrar = null;

            return Action.ERROR;
        }
    }

    /*
    public String marcarAtendido() {
        try {
            log.debug("TipoArchivar [" + tipoArchivar + "]");
            log.debug("Documento a archivar ["+idDocumento+"]");

            Map<String, Object> session = ActionContext.getContext().getSession();
            Usuario usuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
            String nombrePC = (String) session.get("nombrePC");

            if (idDocumento != null) {
            	documento = documentoService.findByIdDocumento(idDocumento);
            	documentoService.atenderDocumento(usuario, documento, null, false, usuario, nombrePC);
            }

            return "resultado";

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Action.ERROR;
        }
    }
    */
    public String clasificar() {

        String[] documentos = this.getRequest().getParameterValues("documentos");
        log.debug(" %%%%% tama;o " + ((documentos != null) ? documentos.length : 0));

        Expediente exp;
        if (idDocumento != null) {
            Documento doc = documentoService.findByIdDocumento(idDocumento);
            exp = expedienteService.findByIdExpediente(doc.getExpediente().getIdexpediente());
        } else {
            exp = expedienteService.findByIdExpediente(idExpediente);
        }


        List<Documento> docs = exp.getDocumentoList();

        Iterator<Documento> i = docs.iterator();

        while (i.hasNext()) {
            Documento d = i.next();

            if (documentos != null) {
                for (int e = 0; e < documentos.length; e++) {

                    log.debug(" %%%%% dociD " + documentos[e]);

                    if (d.getIdDocumento().intValue() == new Integer(documentos[e]).intValue()) {

                        d.setDelExpediente(Constantes.DOCUMENTO_DEL_EXPEDIENTE);
                        try {
                            //  auditoriaArchivarDocumentos(d,Constantes.TA_ClasificarDocs,Constantes.TM_UserFinal,Constantes.TO_Clasificar,"No Seleccionado","Seleccionado");
                        } catch (Exception ex) {
                            log.error(ex.getMessage(), ex);
                        }
                        break;

                    }
                    d.setDelExpediente(Constantes.DOCUMENTO_NO_DEL_EXPEDIENTE);

                }
            } else {
                d.setDelExpediente(Constantes.DOCUMENTO_NO_DEL_EXPEDIENTE);
                try {
                    // auditoriaArchivarDocumentos(d,Constantes.TA_ClasificarDocs,Constantes.TM_UserFinal,Constantes.TO_Clasificar,"Seleccionado","No Seleccionado");
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }

            
            documentoService.saveDocumento(d);

        }

        this.cerrar = "ok";

        return "inicio";
    }

    public DocumentoService getDocumentoService() {
        return documentoService;
    }

    public void setDocumentoService(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    public NotificacionService getNotificacionService() {
        return notificacionService;
    }

    public void setNotificacionService(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
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

    public String getsObservacion() {
        return sObservacion;
    }

    public void setsObservacion(String sObservacion) {
        this.sObservacion = sObservacion;
    }

    public String getTipoArchivar() {
        return tipoArchivar;
    }

    public void setTipoArchivar(String tipoArchivar) {
        this.tipoArchivar = tipoArchivar;
    }

    public ArchivarAction(DocumentoService documentoService, UsuarioService usuarioService, ExpedienteService expedienteService) {
        this.documentoService = documentoService;
        this.usuarioService = usuarioService;
        this.expedienteService = expedienteService;
    }

    public Integer[] getArrIdDoc() {
        return arrIdDoc;
    }

    public void setArrIdDoc(Integer[] arrIdDoc) {
        this.arrIdDoc = arrIdDoc;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public String getListaDocumentos() {
        return listaDocumentos;
    }

    @SMDMethod
    public void setListaDocumentos(String listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }
    
     public UnidadService getUnidadService() {
        return unidadService;
    }

    public void setUnidadService(UnidadService unidadService) {
        this.unidadService = unidadService;
    }
    

    /*@SuppressWarnings("unchecked")
    private void auditoriaArchivarDocumentos(Documento doc,String tipoauditoria,String modulo,String opcion,String antiguoValor,String nuevoValor)
    {     /*******************************************************/
    //		Registrando la auditoria del Expediente
    /*******************************************************/
    /*AuditoriaDAO daoauditoria = (AuditoriaDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("auditoriaDAO");

    Map<String,Object> session = ActionContext.getContext().getSession();
    Usuario usuario=(Usuario) session.get(Constantes.SESSION_USUARIO);

    /*******************************************************/
    //		Registrando la auditoria del Expediente
    /*******************************************************/
    /*Auditoria ObjAuditoriaArchivo = new Auditoria();
    ObjAuditoriaArchivo.setTipoauditoria(tipoauditoria);
    ObjAuditoriaArchivo.setModulo(modulo);
    ObjAuditoriaArchivo.setOpcion(opcion);
    ObjAuditoriaArchivo.setFechaaudioria(new Date());
    ObjAuditoriaArchivo.setUsuario(usuario.getUsuario());
    ObjAuditoriaArchivo.setCampo("Seleccionado");
    ObjAuditoriaArchivo.setOldvalor(antiguoValor);
    ObjAuditoriaArchivo.setNuevovalor(nuevoValor);
    ObjAuditoriaArchivo.setExpediente(doc.getExpediente());
    ObjAuditoriaArchivo.setDocumento(doc);
    daoauditoria.SaveAuditoria(ObjAuditoriaArchivo);

    }*/

    public List<String> getCondestinatarios() {
        return condestinatarios;
    }

    public void setCondestinatarios(List<String> condestinatarios) {
        this.condestinatarios = condestinatarios;
    }

    public String getCerrar() {
        return cerrar;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCerrar(String cerrar) {
        this.cerrar = cerrar;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public ExpedienteTree getArbol() {
        return arbol;
    }

    public void setArbol(ExpedienteTree arbol) {
        this.arbol = arbol;
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Integer getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(Integer idExpediente) {
        this.idExpediente = idExpediente;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Integer getIdResponsableProceso() {
        return idResponsableProceso;
    }

    public void setIdResponsableProceso(Integer idResponsableProceso) {
        this.idResponsableProceso = idResponsableProceso;
    }

    public String getNombreResponsableProceso() {
        return nombreResponsableProceso;
    }

    public void setNombreResponsableProceso(String nombreResponsableProceso) {
        this.nombreResponsableProceso = nombreResponsableProceso;
    }

    public Integer getIdResponsableExpediente() {
        return idResponsableExpediente;
    }

    public void setIdResponsableExpediente(Integer idResponsableExpediente) {
        this.idResponsableExpediente = idResponsableExpediente;
    }

    public String getNombreResponsableExpediente() {
        return nombreResponsableExpediente;
    }

    public void setNombreResponsableExpediente(String nombreResponsableExpediente) {
        this.nombreResponsableExpediente = nombreResponsableExpediente;
    }

    public ExpedienteService getExpedienteService() {
        return expedienteService;
    }

    public void setExpedienteService(ExpedienteService expedienteService) {
        this.expedienteService = expedienteService;
    }

    public Documento getDocumento() {
	return documento;
    }

    public void setDocumento(Documento documento) {
	this.documento = documento;
    }
        
    public List<DocumentoReferencia> getDocumentosReferencia() {
        return documentosReferencia;
    }

    public void setDocumentosReferencia(List<DocumentoReferencia> documentosReferencia) {
        this.documentosReferencia = documentosReferencia;
    }
    
    public String getStrReferencia() {
        return strReferencia;
    }

    public void setStrReferencia(String strReferencia) {
        this.strReferencia = strReferencia;
    }
    
}
