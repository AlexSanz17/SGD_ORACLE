/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoAnulado;
import com.btg.ositran.siged.domain.DocumentoPendiente;
import com.btg.ositran.siged.domain.DocumentoAtendido;
import com.btg.ositran.siged.domain.IotdtmDocExterno;
import com.btg.ositran.siged.domain.SeguimientoXUsuario;
import com.btg.ositran.siged.domain.TipoLegajoUnidad;
import com.btg.ositran.siged.domain.Trazabilidadapoyo;
import com.btg.ositran.siged.domain.Trazabilidadcopia;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import org.ositran.daos.DocumentoPendienteDAO;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import org.ositran.daos.DocumentoAnuladoDAO;
import org.ositran.daos.DocumentoAtendidoDAO;
import org.ositran.daos.DocumentoDAO;
import org.ositran.daos.DocumentoExternoVirtualDAO;
import org.ositran.daos.SeguimientoXFirmaDAO;
import org.ositran.daos.SeguimientoXUsuarioDAO;
import org.ositran.daos.TipoLegajoUnidadDAO;
import org.ositran.services.AccionService;
import org.ositran.services.ArchivoService;
import org.ositran.services.SeguimientoXFirmaService;
import org.ositran.services.TrazabilidadapoyoService;
import org.ositran.services.TrazabilidadcopiaService;
import org.ositran.services.TrazabilidaddocumentoService;
import org.ositran.utils.Constantes;
import org.ositran.utils.DocumentoDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author consultor_jti15
 */

public class PendienteAction {
    
    private final Logger log = LoggerFactory.getLogger(PendienteAction.class);   
    private DocumentoPendienteDAO documentoPendienteDAO;
    private DocumentoAtendidoDAO documentoAtendidoDAO;
    private DocumentoAnuladoDAO documentoAnuladoDAO;
    private SeguimientoXFirmaService seguimientoXFirmaService; 
    private SeguimientoXFirmaDAO seguimientoXFirmaDAO; 
    private SeguimientoXUsuarioDAO seguimientoXUsuarioDAO;
    private List<Trazabilidadcopia> lstTrazabilidadCopia;
    private DocumentoDAO documentoDAO;
    private DocumentoDetail objDD;
    private Documento documento;
    private Integer idpendientes;
    private Integer idatendidos;
    private Integer idfirmados;
    private Integer idanulados;
    private Integer idseguimientos;
    private Map<String, Object> mapSession;
    private TrazabilidadapoyoService trazabilidadapoyoService;
    private ArchivoService archivoService;
    private TrazabilidadcopiaService trazabilidadcopiaService;
    private boolean destinatarioIgualRemitente;
    private TrazabilidaddocumentoService trazabilidadDocumentoService;
    private char flag;
    private TipoLegajoUnidadDAO tipoLegajoUnidadDAO;
    private boolean controlDevolver;
    private AccionService accionService;
    private DocumentoExternoVirtualDAO documentoExternoVirtualDAO;

    public DocumentoExternoVirtualDAO getDocumentoExternoVirtualDAO() {
        return documentoExternoVirtualDAO;
    }

    public void setDocumentoExternoVirtualDAO(DocumentoExternoVirtualDAO documentoExternoVirtualDAO) {
        this.documentoExternoVirtualDAO = documentoExternoVirtualDAO;
    }

    public AccionService getAccionService() {
        return accionService;
    }

    public void setAccionService(AccionService accionService) {
        this.accionService = accionService;
    }

    public boolean isControlDevolver() {
        return controlDevolver;
    }

    public void setControlDevolver(boolean controlDevolver) {
        this.controlDevolver = controlDevolver;
    }

    public TipoLegajoUnidadDAO getTipoLegajoUnidadDAO() {
        return tipoLegajoUnidadDAO;
    }

    public void setTipoLegajoUnidadDAO(TipoLegajoUnidadDAO tipoLegajoUnidadDAO) {
        this.tipoLegajoUnidadDAO = tipoLegajoUnidadDAO;
    }

    public char getFlag() {
        return flag;
    }

    public void setFlag(char flag) {
        this.flag = flag;
    }

    public TrazabilidaddocumentoService getTrazabilidadDocumentoService() {
        return trazabilidadDocumentoService;
    }

    public void setTrazabilidadDocumentoService(TrazabilidaddocumentoService trazabilidadDocumentoService) {
        this.trazabilidadDocumentoService = trazabilidadDocumentoService;
    }



    public boolean isDestinatarioIgualRemitente() {
        return destinatarioIgualRemitente;
    }

    public void setDestinatarioIgualRemitente(boolean destinatarioIgualRemitente) {
        this.destinatarioIgualRemitente = destinatarioIgualRemitente;
    }

    public PendienteAction() {
	super();
    }
    
     public TrazabilidadcopiaService getTrazabilidadcopiaService() {
        return trazabilidadcopiaService;
    }

    public void setTrazabilidadcopiaService(TrazabilidadcopiaService trazabilidadcopiaService) {
        this.trazabilidadcopiaService = trazabilidadcopiaService;
    }

    public List<Trazabilidadcopia> getLstTrazabilidadCopia() {
        return lstTrazabilidadCopia;
    }

    public void setLstTrazabilidadCopia(List<Trazabilidadcopia> lstTrazabilidadCopia) {
        this.lstTrazabilidadCopia = lstTrazabilidadCopia;
    }
    
     public SeguimientoXUsuarioDAO getSeguimientoXUsuarioDAO() {
        return seguimientoXUsuarioDAO;
    }

    public void setSeguimientoXUsuarioDAO(SeguimientoXUsuarioDAO seguimientoXUsuarioDAO) {
        this.seguimientoXUsuarioDAO = seguimientoXUsuarioDAO;
    } 
    
    public Integer getIdseguimientos() {
        return idseguimientos;
    }

    public void setIdseguimientos(Integer idseguimientos) {
        this.idseguimientos = idseguimientos;
    }
    
    public SeguimientoXFirmaDAO getSeguimientoXFirmaDAO() {
        return seguimientoXFirmaDAO;
    }

    public void setSeguimientoXFirmaDAO(SeguimientoXFirmaDAO seguimientoXFirmaDAO) {
        this.seguimientoXFirmaDAO = seguimientoXFirmaDAO;
    }
    
    public SeguimientoXFirmaService getSeguimientoXFirmaService() {
        return seguimientoXFirmaService;
    }

    public void setSeguimientoXFirmaService(SeguimientoXFirmaService seguimientoXFirmaService) {
        this.seguimientoXFirmaService = seguimientoXFirmaService;
    }    
    
    public Integer getIdfirmados() {
        return idfirmados;
    }

    public void setIdfirmados(Integer idfirmados) {
        this.idfirmados = idfirmados;
    }

    public Integer getIdanulados() {
        return idanulados;
    }

    public void setIdanulados(Integer idanulados) {
        this.idanulados = idanulados;
    }
    
    
       @SuppressWarnings("unused")
      public String firmarDocumentos(){     
          mapSession = ActionContext.getContext().getSession();
  	  Map<String,Object> request=ActionContext.getContext().getParameters();	
          String[] ids=(String[])request.get("id");
          
          seguimientoXFirmaService.guardarSeguimiento(ids);
          return Action.NONE;
      }
    
     @SuppressWarnings("unused")
    public String viewDocPendiente() {
        log.debug("-> [Action] PendienteAction - viewDocPendiente():String ");
        DocumentoPendiente documentoPendiente = null;
        
        try{ 
              mapSession = ActionContext.getContext().getSession();
              Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
              if (usuario == null)
                  return "errorsession";
              
              documentoPendiente = documentoPendienteDAO.findByIdDocumentoPendiente(idpendientes);
              documento = documentoDAO.findByIdDocumento(documentoPendiente.getIddocumento());
                                  
              mapSession.put(Constantes.SESSION_IDDOCUMENTO, documento.getIdDocumento()); 
              mapSession.remove(Constantes.SESSION_UPLOAD_LIST);
              Integer idDoc = documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : documento.getIdDocumento();
	      mapSession.put(Constantes.SESSION_UPLOAD_LIST, archivoService.getArchivoListPorDocumento(idDoc));
              
              if(documento.getDocumentoreferencia() != null){
	          lstTrazabilidadCopia = trazabilidadcopiaService.buscarUsuarioCopia(documento.getDocumentoreferencia(),documentoPendiente.getIdTrazabilidad());
	      }else{
	          lstTrazabilidadCopia = trazabilidadcopiaService.buscarUsuarioCopia(documento.getIdDocumento(),documentoPendiente.getIdTrazabilidad());
	      }
              
              StringBuilder cadenaCC = new StringBuilder();
                        
	      if(lstTrazabilidadCopia!=null && lstTrazabilidadCopia.size()>0){
	         for(int i=0; i<lstTrazabilidadCopia.size(); i++){
	           if(i!=0) cadenaCC.append(", ");
		     cadenaCC.append( lstTrazabilidadCopia.get(i).getDestinatario().getApellidos()+" "+lstTrazabilidadCopia.get(i).getDestinatario().getNombres());
		   }
	      }
                                                        
              if(documento.getDocumentoreferencia() != null || documentoPendiente.getIdTipoTrazabilidad().equals("M")){
                  if (documento.getDocumentoreferencia()==null)
                     objDD = documentoDAO.findDocumentoDetailBy(documento.getIdDocumento());
                  else
                     objDD = documentoDAO.findDocumentoDetailBy(documento.getDocumentoreferencia());
                  
                  objDD.setDesDocumentoOrigen("");
                  Usuarioxunidadxfuncion usuarioxunidadxfuncion  = new Usuarioxunidadxfuncion();
                  usuarioxunidadxfuncion.setIdusuario(usuario.getIdUsuarioPerfil());
                  usuarioxunidadxfuncion.setIdunidad(usuario.getIdUnidadPerfil());
                  usuarioxunidadxfuncion.setIdfuncion(usuario.getIdFuncionPerfil());
                  Trazabilidadapoyo parametro = new Trazabilidadapoyo();
                  parametro.setIdtrazabilidadapoyo(documentoPendiente.getIdTrazabilidad());
	          Trazabilidadapoyo tapoyo = trazabilidadapoyoService.buscarUltimaDelegacionPendiente(parametro); //trazabilidadapoyoService.buscarUltimaDelegacionUsuario(usuarioxunidadxfuncion, documento.getIdDocumento());				 
            
                  if(tapoyo != null){
                    objDD.setStrAccion(tapoyo.getProveido()==null?"":tapoyo.getProveido().getNombre());
		    objDD.setStrContenido(tapoyo.getTexto() != null ? tapoyo.getTexto() : "");
                    objDD.setStrRemitente(tapoyo.getRemitente().getNombres() + " " + tapoyo.getRemitente().getApellidos());
		    objDD.setStrAsunto(tapoyo.getAsunto() != null ? tapoyo.getAsunto() : objDD.getStrAsunto());
		    objDD.setStrDestinatario(tapoyo.getDestinatario().getNombres() + " " + tapoyo.getDestinatario().getApellidos());
                    objDD.setPrioridad(tapoyo.getPrioridad());
		    objDD.setStrFechaLimiteAtencion(tapoyo.getFechalimiteatencion() == null ? "" : new SimpleDateFormat(Constantes.FORMATO_FECHA).format(tapoyo.getFechalimiteatencion()));			
		  }
	      }else{
                    objDD = documentoDAO.findDocumentoDetailByPendiente(documentoPendiente);
             }
              
             objDD.setDesDocumentoOrigen("");
        
             if (documento.getOrigen()!=null){
                 Documento origen = documentoDAO.findByIdDocumento(documento.getOrigen());
                 objDD.setDesDocumentoOrigen(origen.getTipoDocumento().getNombre() + " - " + origen.getNumeroDocumento());
             }
              
             if(cadenaCC.length()!=0){
		objDD.setCadenaCC(cadenaCC.toString());
	     }
              
             objDD.setStrRazonSocial("");
                                
             if (documento.getCliente()!=null){
                 if (documento.getCliente().getTipoinstitucion().getCod_tipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                    String nombres =  documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres();
                    String paterno =  documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno();
                    String materno =  documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno();
                    objDD.setStrRazonSocial(nombres + " " + paterno + " " + materno);
                 }else{
                    objDD.setStrRazonSocial(documento.getCliente().getRazonSocial());
                 }
             }
		
             objDD.setIIdDocumento(documentoPendiente.getIddocumento());
              
        }catch(Exception e){
            try{
                objDD = new DocumentoDetail();
                objDD.setIIdDocumento(documentoPendiente.getIddocumento());
                objDD.setDesDocumentoOrigen("");
        
                if (documento.getOrigen()!=null){
                 Documento origen = documentoDAO.findByIdDocumento(documento.getOrigen());
                 objDD.setDesDocumentoOrigen(origen.getTipoDocumento().getNombre() + " - " + origen.getNumeroDocumento());
                }
                
                objDD.setStrRazonSocial("");
                                
                if (documento.getCliente()!=null){
                   if (documento.getCliente().getTipoinstitucion().getCod_tipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                      String nombres =  documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres();
                      String paterno =  documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno();
                      String materno =  documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno();
                      objDD.setStrRazonSocial(nombres + " " + paterno + " " + materno);
                   }else{
                      objDD.setStrRazonSocial(documento.getCliente().getRazonSocial());
                   }
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }    
            e.printStackTrace();
        }    
        return Action.SUCCESS;
    }
   
    
    @SuppressWarnings("unused")
    public String viewDocAtendido() {
        log.debug("-> [Action] PendienteAction - viewDocAtendido():String ");
        DocumentoAtendido documentoAtendido = null;
        try{ 
              mapSession = ActionContext.getContext().getSession();
              Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
              if (usuario == null)
                  return "errorsession";
              
              documentoAtendido = documentoAtendidoDAO.findByIdDocumentoAtendido(idatendidos);
              documento = documentoDAO.findByIdDocumento(documentoAtendido.getIddocumento());
              mapSession.remove(Constantes.SESSION_UPLOAD_LIST);
              Integer idDoc = documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : documento.getIdDocumento();
	      mapSession.put(Constantes.SESSION_UPLOAD_LIST, archivoService.getArchivoListPorDocumento(idDoc));
              mapSession.put(Constantes.SESSION_IDDOCUMENTO, documento.getIdDocumento());
              
              if(documento.getDocumentoreferencia() != null){
                  objDD = documentoDAO.findDocumentoDetailBy(documento.getDocumentoreferencia());
                  Usuarioxunidadxfuncion usuarioxunidadxfuncion  = new Usuarioxunidadxfuncion();
                  usuarioxunidadxfuncion.setIdusuario(usuario.getIdUsuarioPerfil());
                  usuarioxunidadxfuncion.setIdunidad(usuario.getIdUnidadPerfil());
                  usuarioxunidadxfuncion.setIdfuncion(usuario.getIdFuncionPerfil());
	          Trazabilidadapoyo tapoyo = trazabilidadapoyoService.buscarUltimaDelegacionUsuario(usuarioxunidadxfuncion, documento.getIdDocumento());				 
             
                  if(tapoyo != null){
                    objDD.setStrContenido(tapoyo.getTexto() != null ? tapoyo.getTexto() : "");
		    objDD.setStrAsunto(tapoyo.getAsunto() != null ? tapoyo.getAsunto() : objDD.getStrAsunto());
		    objDD.setStrDestinatario(tapoyo.getDestinatario().getNombres() + " " + tapoyo.getDestinatario().getApellidos());
                    objDD.setPrioridad(tapoyo.getPrioridad());
		    objDD.setStrFechaLimiteAtencion(tapoyo.getFechalimiteatencion() == null ? "" : new SimpleDateFormat(Constantes.FORMATO_FECHA).format(tapoyo.getFechalimiteatencion()));			
		  
                    if (documento.getFlagatendido()!=null && documento.getFlagatendido().equals("1") && documento.getFlagMultiple()==null){
                      flag = '1';
                    }else{
                      flag = '0';  
                    }  
                  }
	      }else{
                  objDD = documentoDAO.findDocumentoDetailByUser(documento.getIdDocumento(), usuario);
                  if (documentoAtendido.getIdusuario().toString().equals(usuario.getIdUsuarioPerfil().toString()) && documento.getPropietario().getIdusuario().toString().equals(usuario.getIdUsuarioPerfil().toString()) && documento.getUnidadpropietario().toString().equals(usuario.getIdUnidadPerfil().toString())  && documento.getFlagatendido()!=null && documento.getFlagatendido().equals("1") &&  documento.getFlagMultiple()==null){  
                      flag = '1';      
                  }else{
                      flag = '0';
                  }
              }
              
              objDD.setIIdDocumento(documentoAtendido.getIddocumento());
              objDD.setStrRazonSocial("");
              objDD.setDesDocumentoOrigen("");
              
              if (documento.getOrigen()!=null){
                  Documento origen = documentoDAO.findByIdDocumento(documento.getOrigen());
                  objDD.setDesDocumentoOrigen(origen.getTipoDocumento().getNombre() + " - " + origen.getNumeroDocumento());
              }
                            
              if (documento.getCliente()!=null){
                 if (documento.getCliente().getTipoinstitucion().getCod_tipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                    String nombres =  documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres();
                    String paterno =  documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno();
                    String materno =  documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno();
                    objDD.setStrRazonSocial(nombres + " " + paterno + " " + materno);
                 }else{
                    objDD.setStrRazonSocial(documento.getCliente().getRazonSocial());
                 }
              }
              		
        }catch(Exception e){
          try{  
              objDD = new DocumentoDetail();
              objDD.setIIdDocumento(documentoAtendido.getIddocumento());
            
              objDD.setStrRazonSocial("");
              objDD.setDesDocumentoOrigen("");
              
              if (documento.getOrigen()!=null){
                Documento origen = documentoDAO.findByIdDocumento(documento.getOrigen());
                objDD.setDesDocumentoOrigen(origen.getTipoDocumento().getNombre() + " - " + origen.getNumeroDocumento());
              }
                            
              if (documento.getCliente()!=null){
                 if (documento.getCliente().getTipoinstitucion().getCod_tipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                    String nombres =  documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres();
                    String paterno =  documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno();
                    String materno =  documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno();
                    objDD.setStrRazonSocial(nombres + " " + paterno + " " + materno);
                 }else{
                    objDD.setStrRazonSocial(documento.getCliente().getRazonSocial());
                 }
              }
          }catch(Exception ex){
              ex.printStackTrace();
          }   
              
            e.printStackTrace();
        }    
        return Action.SUCCESS;
    }
    
    
    @SuppressWarnings("unused")
    public String viewDocAnulado() {
        log.debug("-> [Action] PendienteAction - viewDocAnulado():String ");
        DocumentoAnulado documentoAnulado = null;
        try{ 
              mapSession = ActionContext.getContext().getSession();
              Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
              if (usuario == null)
                  return "errorsession";
             
              documentoAnulado = documentoAnuladoDAO.findByIdDocumentoAnulado(idanulados);
              documento = documentoDAO.findByIdDocumento(documentoAnulado.getIddocumento());
              mapSession.remove(Constantes.SESSION_UPLOAD_LIST);
              Integer idDoc = documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : documento.getIdDocumento();
              mapSession.put(Constantes.SESSION_UPLOAD_LIST, archivoService.getArchivoListPorDocumento(idDoc));
              mapSession.put(Constantes.SESSION_IDDOCUMENTO, documento.getIdDocumento());
                                                        
              if(documento.getDocumentoreferencia() != null){
                  objDD = documentoDAO.findDocumentoDetailBy(documento.getDocumentoreferencia());
                  Usuarioxunidadxfuncion usuarioxunidadxfuncion  = new Usuarioxunidadxfuncion();
                  usuarioxunidadxfuncion.setIdusuario(usuario.getIdUsuarioPerfil());
                  usuarioxunidadxfuncion.setIdunidad(usuario.getIdUnidadPerfil());
                  usuarioxunidadxfuncion.setIdfuncion(usuario.getIdFuncionPerfil());
	          Trazabilidadapoyo tapoyo = trazabilidadapoyoService.buscarUltimaDelegacionUsuario(usuarioxunidadxfuncion, documento.getIdDocumento());				 
            
                  if(tapoyo != null){
                    objDD.setStrAccion(tapoyo.getProveido()==null?"":tapoyo.getProveido().getNombre());
		    objDD.setStrContenido(tapoyo.getTexto() != null ? tapoyo.getTexto() : "");
		    objDD.setStrAsunto(tapoyo.getAsunto() != null ? tapoyo.getAsunto() : objDD.getStrAsunto());
		    objDD.setStrDestinatario(tapoyo.getDestinatario().getNombres() + " " + tapoyo.getDestinatario().getApellidos());
                    objDD.setPrioridad(tapoyo.getPrioridad());
		    objDD.setStrFechaLimiteAtencion(tapoyo.getFechalimiteatencion() == null ? "" : new SimpleDateFormat(Constantes.FORMATO_FECHA).format(tapoyo.getFechalimiteatencion()));			
		  }
	      }else{
                  objDD = documentoDAO.findDocumentoDetailByUser(documento.getIdDocumento(), usuario);
	      }
              
              objDD.setStrRazonSocial("");
                                
              if (documento.getCliente()!=null){
                 if (documento.getCliente().getTipoinstitucion().getCod_tipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                    String nombres =  documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres();
                    String paterno =  documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno();
                    String materno =  documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno();
                    objDD.setStrRazonSocial(nombres + " " + paterno + " " + materno);
                 }else{
                    objDD.setStrRazonSocial(documento.getCliente().getRazonSocial());
                 }
              }
				
              objDD.setIIdDocumento(documentoAnulado.getIddocumento());
              
        }catch(Exception e){
            try{
                objDD = new DocumentoDetail();
                objDD.setIIdDocumento(documentoAnulado.getIddocumento());
            
                objDD.setStrRazonSocial("");
                objDD.setDesDocumentoOrigen("");

                if (documento.getOrigen()!=null){
                  Documento origen = documentoDAO.findByIdDocumento(documento.getOrigen());
                  objDD.setDesDocumentoOrigen(origen.getTipoDocumento().getNombre() + " - " + origen.getNumeroDocumento());
                }

                if (documento.getCliente()!=null){
                   if (documento.getCliente().getTipoinstitucion().getCod_tipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                      String nombres =  documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres();
                      String paterno =  documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno();
                      String materno =  documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno();
                      objDD.setStrRazonSocial(nombres + " " + paterno + " " + materno);
                   }else{
                      objDD.setStrRazonSocial(documento.getCliente().getRazonSocial());
                   }
                }

            }catch(Exception ex){
                
            }
            
            e.printStackTrace();
        }    
        return Action.SUCCESS;
    }
    
    @SuppressWarnings("unused")
    public String viewDocSeguimiento() {
        log.debug("-> [Action] PendienteAction - viewDocSeguimiento():String ");
        try{ 
              mapSession = ActionContext.getContext().getSession();
              Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
              if (usuario == null)
                  return "errorsession";
              
              SeguimientoXUsuario seguimientoXUsuario = seguimientoXUsuarioDAO.findByIdSeguimiento(idseguimientos);
              documento = documentoDAO.findByIdDocumento(seguimientoXUsuario.getIdDocumento());
             
              mapSession.remove(Constantes.SESSION_UPLOAD_LIST);
              Integer idDoc = documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : documento.getIdDocumento();
              mapSession.put(Constantes.SESSION_UPLOAD_LIST, archivoService.getArchivoListPorDocumento(idDoc));
              mapSession.put(Constantes.SESSION_IDDOCUMENTO, documento.getIdDocumento());
                 
              objDD = new DocumentoDetail();
              if (documento.getFechaLimiteAtencion()!=null)
                objDD.setStrFechaLimiteAtencion(new SimpleDateFormat(Constantes.FORMATO_FECHA).format(documento.getFechaLimiteAtencion()));
              
              objDD.setIIdDocumento(seguimientoXUsuario.getIdDocumento());
              objDD.setCEstado(documento.getEstado());
              objDD.setStrTipoDocumento(documento.getTipoDocumento().getNombre());
              objDD.setStrNroDocumento(documento.getNumeroDocumento());
              objDD.setStrFecha(documento.getFechaCreacion());
              objDD.setStrRazonSocial("");
                                
              if (documento.getCliente()!=null){
                 if (documento.getCliente().getTipoinstitucion().getCod_tipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                    String nombres =  documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres();
                    String paterno =  documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno();
                    String materno =  documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno();
                    objDD.setStrRazonSocial(nombres + " " + paterno + " " + materno);
                 }else{
                    objDD.setStrRazonSocial(documento.getCliente().getRazonSocial());
                 }
              }
		
             
              
        }catch(Exception e){
            e.printStackTrace();
        }    
        return Action.SUCCESS;
    }
    
    @SuppressWarnings("unused")
    public String viewDocFirmado() {
        log.debug("-> [Action] PendienteAction - viewDocFirmado():String ");
        //SeguimientoXFirma seguimientoXFirma = null;
        try{ 
              mapSession = ActionContext.getContext().getSession();
              Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
              Integer traza;
              
              if (usuario == null)
                  return "errorsession";
              
              //seguimientoXFirma = seguimientoXFirmaDAO.findByIdDocumentoFirmado(idfirmados);
              documento = documentoDAO.findByIdDocumento(idfirmados);
              mapSession.remove(Constantes.SESSION_UPLOAD_LIST);
              Integer idDoc = documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : documento.getIdDocumento();
              mapSession.put(Constantes.SESSION_UPLOAD_LIST, archivoService.getArchivoListPorDocumento(idDoc));  
              mapSession.put(Constantes.SESSION_IDDOCUMENTO, documento.getIdDocumento());
              
              destinatarioIgualRemitente = false;
              if(documento.getAutor().getIdusuario().intValue() == usuario.getIdUsuarioPerfil().intValue() &&
                 documento.getUnidadautor().intValue() == usuario.getIdUnidadPerfil())
                 destinatarioIgualRemitente = true; 
              
              try{
                   controlDevolver = false;
                   Trazabilidaddocumento trazabilidaddocumento = trazabilidadDocumentoService.findByMaxNroRegistro(documento.getIdDocumento(), "", accionService.findByNombre("reenviar").getIdAccion(), usuario.getIdUsuarioPerfil(), usuario.getIdUnidadPerfil(), usuario.getIdFuncionPerfil());
			     
                   if (trazabilidaddocumento!=null){
                        controlDevolver = false; 
                   }else{
                        controlDevolver = true;
                   }
              }catch(Exception e){
                   controlDevolver = true;
              }
            
              if(documento.getDocumentoreferencia() != null){
	         traza=trazabilidadDocumentoService.findByMaxtrazabyIddocumento(documento.getDocumentoreferencia()).get(0).getIdtrazabilidaddocumento();
	      }else{
	         traza=trazabilidadDocumentoService.findByMaxtrazabyIddocumento(idfirmados).get(0).getIdtrazabilidaddocumento();
	      }
			
	      lstTrazabilidadCopia = trazabilidadcopiaService.buscarUsuarioCopia(idfirmados,traza);
	      StringBuilder cadenaCC = new StringBuilder();
                        
			if(lstTrazabilidadCopia!=null && lstTrazabilidadCopia.size()>0){
			   for(int i=0; i<lstTrazabilidadCopia.size(); i++){
			    if(i!=0) cadenaCC.append(", ");
			     cadenaCC.append( lstTrazabilidadCopia.get(i).getDestinatario().getApellidos()+" "+lstTrazabilidadCopia.get(i).getDestinatario().getNombres());
			    }
			}
              
              if(documento.getDocumentoreferencia() != null){
                  objDD = documentoDAO.findDocumentoDetailBy(documento.getDocumentoreferencia());
                  objDD.setIdExterno(documento.getID_EXTERNO().toString());
                  objDD.setDesDocumentoOrigen("");
                  Usuarioxunidadxfuncion usuarioxunidadxfuncion  = new Usuarioxunidadxfuncion();
                  usuarioxunidadxfuncion.setIdusuario(usuario.getIdUsuarioPerfil());
                  usuarioxunidadxfuncion.setIdunidad(usuario.getIdUnidadPerfil());
                  usuarioxunidadxfuncion.setIdfuncion(usuario.getIdFuncionPerfil());
		  Trazabilidadapoyo tapoyo = trazabilidadapoyoService.buscarUltimaDelegacionUsuarioAR(usuarioxunidadxfuncion, idfirmados);
							 
                  if(tapoyo != null){
                      objDD.setStrAccion(tapoyo.getProveido()==null?"":tapoyo.getProveido().getNombre());
		      objDD.setStrContenido(tapoyo.getTexto() != null ? tapoyo.getTexto() : "");
                      objDD.setStrRemitente(tapoyo.getRemitente().getNombres() + " " + tapoyo.getRemitente().getApellidos());
                      objDD.setStrDestinatario(tapoyo.getDestinatario().getNombres() + " " + tapoyo.getDestinatario().getApellidos());
                      objDD.setPrioridad(tapoyo.getPrioridad());
		      objDD.setStrFechaLimiteAtencion(tapoyo.getFechalimiteatencion() == null ? "" : new SimpleDateFormat(Constantes.FORMATO_FECHA).format(tapoyo.getFechalimiteatencion()));			
		  }
                                    
                  if (documento.getOrigen()!=null){
                      Documento origen = documentoDAO.findByIdDocumento(documento.getOrigen());
                      objDD.setDesDocumentoOrigen(origen.getTipoDocumento().getNombre() + " - " + origen.getNumeroDocumento());
                  }
	       }else{
                      objDD = documentoDAO.findDocumentoDetailBy(idfirmados);
                      objDD.setDesDocumentoOrigen("");
                      objDD.setIdExterno(documento.getID_EXTERNO().toString());
                      if (documento.getOrigen()!=null){
                          Documento origen = documentoDAO.findByIdDocumento(documento.getOrigen());
                          objDD.setDesDocumentoOrigen(origen.getTipoDocumento().getNombre() + " - " + origen.getNumeroDocumento());
                      }
               }
                                
                objDD.setStrRazonSocial("");
               
                TipoLegajoUnidad tipoLegajoUnidad = new TipoLegajoUnidad();
                tipoLegajoUnidad.setAccion("A");
                tipoLegajoUnidad.setIdTipoLegajoUnidad(usuario.getIdUnidadPerfil());
                List<TipoLegajoUnidad> lstAgregar = tipoLegajoUnidadDAO.findTipoLegajoUnidad(tipoLegajoUnidad);

                tipoLegajoUnidad.setAccion("C");
                List<TipoLegajoUnidad> lstCrear = tipoLegajoUnidadDAO.findTipoLegajoUnidad(tipoLegajoUnidad);

                if ((lstAgregar==null || lstAgregar.size()==0) && (lstCrear==null && lstCrear.size()==0)){
                    objDD.setFlagExpediente('-');
                }
                if ((lstAgregar==null || lstAgregar.size()==0) && (lstCrear!=null && lstCrear.size()>0)){
                    objDD.setFlagExpediente('C');
                }
                if ((lstAgregar!=null || lstAgregar.size()>0) && (lstCrear==null && lstCrear.size()==0)){
                    objDD.setFlagExpediente('A');
                }
                if (lstAgregar!=null && lstAgregar.size()>0 && lstCrear!=null && lstCrear.size()>0){
                    objDD.setFlagExpediente('T');
                }
                
                if (documento.getNroVirtual()==null){
                    objDD.setFlagCodigoVirtual('2'); 
                }else{
                    IotdtmDocExterno recepcion = documentoExternoVirtualDAO.buscarDocumentoVirtual(documento.getNroVirtual());
                    if (recepcion!=null && recepcion.getSidrecext()!=null && (recepcion.getSidrecext().getVnumregstd()== null || recepcion.getSidrecext().getVnumregstd().trim().equals("")))
                        objDD.setFlagCodigoVirtual('1'); 
                    else{
                        if (recepcion!=null && (recepcion.getSidrecext().getCflgest() == 'O' || recepcion.getSidrecext().getCflgest() == 'S' )){
                           objDD.setFlagCodigoVirtual('1'); 
                        }else{
                           objDD.setFlagCodigoVirtual('0');
                        } 
                    }    
                }      
                                
               if (documento.getCliente()!=null){
                   if (documento.getCliente().getTipoinstitucion().getCod_tipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                       String nombres =  documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres();
                       String paterno =  documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno();
                       String materno =  documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno();
                       objDD.setStrRazonSocial(nombres + " " + paterno + " " + materno);
                   }else{
                       objDD.setStrRazonSocial(documento.getCliente().getRazonSocial());
                   }
               }
                                    	
               if(cadenaCC.length()!=0){
		  objDD.setCadenaCC(cadenaCC.toString());
	       }                            
		
               objDD.setIIdDocumento(idfirmados);
              
        }catch(Exception e){
            e.printStackTrace();
        }    
        return Action.SUCCESS;
    }
    
    public DocumentoPendienteDAO getDocumentoPendienteDAO() {
        return documentoPendienteDAO;
    }

    public void setDocumentoPendienteDAO(DocumentoPendienteDAO documentoPendienteDAO) {
        this.documentoPendienteDAO = documentoPendienteDAO;
    }
    
    public Integer getIdpendientes() {
        return idpendientes;
    }

    public void setIdpendientes(Integer idpendientes) {
        this.idpendientes = idpendientes;
    }
    
    public DocumentoDetail getObjDD() {
        return objDD;
    }

    public void setObjDD(DocumentoDetail objDD) {
        this.objDD = objDD;
    }
    
     public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
    
    public DocumentoDAO getDocumentoDAO() {
        return documentoDAO;
    }

    public void setDocumentoDAO(DocumentoDAO documentoDAO) {
        this.documentoDAO = documentoDAO;
    }
    
    public Map<String, Object> getMapSession() {
        return mapSession;
    }

    public void setMapSession(Map<String, Object> mapSession) {
        this.mapSession = mapSession;
    }
    
     public TrazabilidadapoyoService getTrazabilidadapoyoService() {
        return trazabilidadapoyoService;
    }

    public void setTrazabilidadapoyoService(TrazabilidadapoyoService trazabilidadapoyoService) {
        this.trazabilidadapoyoService = trazabilidadapoyoService;
    }

    public ArchivoService getArchivoService() {
        return archivoService;
    }

    public void setArchivoService(ArchivoService archivoService) {
        this.archivoService = archivoService;
    }
    
     public DocumentoAtendidoDAO getDocumentoAtendidoDAO() {
        return documentoAtendidoDAO;
    }

    public void setDocumentoAtendidoDAO(DocumentoAtendidoDAO documentoAtendidoDAO) {
        this.documentoAtendidoDAO = documentoAtendidoDAO;
    }
    public Integer getIdatendidos() {
        return idatendidos;
    }

    public void setIdatendidos(Integer idatendidos) {
        this.idatendidos = idatendidos;
    }
    
    public DocumentoAnuladoDAO getDocumentoAnuladoDAO() {
        return documentoAnuladoDAO;
    }

    public void setDocumentoAnuladoDAO(DocumentoAnuladoDAO documentoAnuladoDAO) {
        this.documentoAnuladoDAO = documentoAnuladoDAO;
    }

}
