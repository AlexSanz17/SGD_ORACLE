/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.FirmaArchivo;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import gob.ositran.siged.config.StrutsProperties;
import gob.ositran.siged.config.StrutsProperties.SigedPropertyEnum;
import gob.ositran.siged.util.MessagePropertiesEnum;
import gob.ositran.siged.util.SigedMessageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.activation.MimetypesFileTypeMap;
import org.apache.log4j.Logger;
import org.ositran.daos.ArchivoDAO;
import org.ositran.daos.FirmaArchivoDAO;
import org.ositran.daos.UnidadDAO;
import org.ositran.services.DocumentoService;
import org.ositran.services.RepositorioService;
import org.ositran.services.UploadService;
import org.ositran.utils.ArchivoTemporal;
import org.ositran.utils.Constantes;

public class UploadAction {

    private static Logger _log = Logger.getLogger(UploadAction.class);
    private File file;
    private List<File> filePrincipal;
    private List<File> fileCargo;
    private File fileProyecto;
    private Integer iIdUpload;
    private Map<String, Object> mapSession;
    private String contentType;
    private String filename;
    private String filenamePrincipal;
    private String filenameCargo;
    private String filenameProyecto;
    private String sNombre;
    private String tipo;
    private UploadService uploadService;
    private String origen;
    private String mensaje;
    private ArchivoDAO archivoDAO;
    private UnidadDAO unidadDAO;
    private RepositorioService repositorioService;
    private InputStream inputStream;
    private String name;
    private long size;
    private String contenttype;
    private SigedMessageSource messageSource;
    private DocumentoService documentoService;
    private FirmaArchivoDAO firmaArchivoDAO;
    private String cargoTramite;


    public String getCargoTramite() {
        return cargoTramite;
    }

    public void setCargoTramite(String cargoTramite) {
        this.cargoTramite = cargoTramite;
    }
    
     public String getFilenameProyecto() {
        return filenameProyecto;
    }

    public void setFilenameProyecto(String filenameProyecto) {
        this.filenameProyecto = filenameProyecto;
    }
    
    public String getFilenameCargo() {
        return filenameCargo;
    }

    public void setFilenameCargo(String filenameCargo) {
        this.filenameCargo = filenameCargo;
    }

    public FirmaArchivoDAO getFirmaArchivoDAO() {
        return firmaArchivoDAO;
    }

    public void setFirmaArchivoDAO(FirmaArchivoDAO firmaArchivoDAO) {
        this.firmaArchivoDAO = firmaArchivoDAO;
    }
    
     public String getFilenamePrincipal() {
        return filenamePrincipal;
    }

    public void setFilenamePrincipal(String filenamePrincipal) {
        this.filenamePrincipal = filenamePrincipal;
    }

    public DocumentoService getDocumentoService() {
        return documentoService;
    }

    public void setDocumentoService(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }
    

    public RepositorioService getRepositorioService() {
        return repositorioService;
    }

    public void setRepositorioService(RepositorioService repositorioService) {
        this.repositorioService = repositorioService;
    }
    
    public UnidadDAO getUnidadDAO() {
        return unidadDAO;
    }

    public void setUnidadDAO(UnidadDAO unidadDAO) {
        this.unidadDAO = unidadDAO;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArchivoDAO getArchivoDAO() {
        return archivoDAO;
    }

    public void setArchivoDAO(ArchivoDAO archivoDAO) {
        this.archivoDAO = archivoDAO;
    }
    // StreamResult
    private Integer iIdDoc;

    public Integer getiIdDoc() {
        return iIdDoc;
    }

    public void setiIdDoc(Integer iIdDoc) {
        this.iIdDoc = iIdDoc;
    }


    @SuppressWarnings("unchecked")
    public String deleteFile() throws Exception {
        if (iIdUpload == null) {
            _log.error("No se recibio ningun upload con el cual trabajar en sesion");

            return Action.ERROR;
        }

        if (sNombre == null) {
            _log.error("No se recibio nombre del archivo a eliminar");

            return Action.ERROR;
        }

        _log.debug("Eliminando archivo [" + sNombre + "] del upload [" + iIdUpload + "]");

        try {
            mapSession = ActionContext.getContext().getSession();
            Map<String, Object> mapUpload = (Map<String, Object>) mapSession.get(Constantes.SESSION_UPLOAD_LIST);

            if (mapUpload == null) {
                _log.error("Mapa del UPLOAD es NULL");

                return Action.ERROR;
            }

            mapUpload = uploadService.deleteFile(mapUpload, iIdUpload, sNombre);
            mapSession.put(Constantes.SESSION_UPLOAD_LIST, mapUpload);

            return Action.SUCCESS;
        } catch (Exception e) {
            _log.error(e.getMessage(), e);

            return Action.ERROR;
        }
    }

    @SuppressWarnings("unchecked")
    public String downloadFileTempo() throws Exception {
       
        if (getIIdUpload() == null) {
            _log.error("No se recibio ningun Id Upload con el cual trabajar en sesion");
            return Action.ERROR;
        }
        
        if (getSNombre() == null) {
            _log.error("No se recibio nombre del archivo a descargar");
            return Action.ERROR;
        }
        try {
            ArchivoTemporal objArchivoTemporal = null;
            setMapSession(ActionContext.getContext().getSession());
            Map<String, List<ArchivoTemporal>> mapUpload = (Map<String, List<ArchivoTemporal>>) getMapSession().get(Constantes.SESSION_UPLOAD_LIST);
            if (mapUpload == null) {
                _log.error("Mapa en sesion que almacena temporalmente los uploads es NULL");
                return Action.ERROR;
            }
            if ((objArchivoTemporal = uploadService.downloadFileTempo(mapUpload, getIIdUpload(), getSNombre())) == null) {
                return Action.ERROR;
            }
            _log.debug("Armando InputStream con datos...");
            _log.debug("Nombre [" + objArchivoTemporal.getFArchivo().getName() + "]");
            _log.debug("Nombre Real [" + objArchivoTemporal.getSNombre() + "]");
            _log.debug("Size [" + objArchivoTemporal.getFArchivo().length() + "]");
            setInputStream(null);
            setInputStream(new FileInputStream(objArchivoTemporal.getFArchivo()));
            setName(objArchivoTemporal.getSNombre());
            setSize(objArchivoTemporal.getFArchivo().length());
            setContenttype(new MimetypesFileTypeMap().getContentType(objArchivoTemporal.getFArchivo()));
            if (getInputStream() != null) {
                return Action.SUCCESS;
            }
            return Action.ERROR;
        } catch (Exception e) {
            _log.error(e.getMessage(), e);
            return Action.ERROR;
        }
    }
    
     

    @SuppressWarnings("unchecked")
    public String uploadFile() throws Exception {
        String[] filenamePrincipalVarios = null;
        String[] filenameCargoVarios = null;
        try {
            if(filenamePrincipal!=null)
                filenamePrincipalVarios = filenamePrincipal.split(", ");
            if(filenameCargo!=null)
                filenameCargoVarios = filenameCargo.split(", ");
            mapSession = ActionContext.getContext().getSession();
            
            if (iIdUpload == null) {
                this.mensaje = messageSource.getMessage(MessagePropertiesEnum.STRUTS_ERROR_FILE_TOO_LARGE);
                _log.error("No se recibio ningun Id Upload con el cual trabajar en sesion");
                mapSession.put(Constantes.MENSAJE_UPLOAD, this.mensaje);
                return Action.SUCCESS;
            }
            
            this.mensaje = null;           
            Map<String, List<ArchivoTemporal>> mapUpload = null;
            Usuario objUsuario = null;
            mapUpload = (Map<String, List<ArchivoTemporal>>) mapSession.get(Constantes.SESSION_UPLOAD_LIST);
            
            if (cargoTramite!=null && cargoTramite.equals("S")){
                   mapSession.put(Constantes.SESSION_UPLOAD_LIST, new HashMap<String,List<org.ositran.utils.ArchivoTemporal>>());
                   mapUpload = (Map<String, List<ArchivoTemporal>>) mapSession.get(Constantes.SESSION_UPLOAD_LIST);
            }
             
            objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
            boolean val = false;
        
            if(filePrincipal!=null && tipo.equals("P")){
                if (mapUpload != null && !mapUpload.values().isEmpty()) {
                    List<ArchivoTemporal> lstArchivoTemporal = mapUpload.get("upload" + iIdUpload);
                    if (lstArchivoTemporal != null) {
                        for (ArchivoTemporal arc : lstArchivoTemporal) {
                            for(int i=0;i<filenamePrincipalVarios.length;i++){
                                if (arc.getSNombre().equalsIgnoreCase(filenamePrincipalVarios[i])) {
                                    val = true;
                                }
                            }
                        }
                    }
                }
            }else{
                    if(filename!=null && tipo.equals("A")){
                        if (mapUpload != null && !mapUpload.values().isEmpty()) {
                            List<ArchivoTemporal> lstArchivoTemporal = mapUpload.get("upload" + iIdUpload);
                            if (lstArchivoTemporal != null) {
                                for (ArchivoTemporal arc : lstArchivoTemporal) {
                                    if (arc.getSNombre().equalsIgnoreCase(filename)) {
                                       val = true;
                                    }
                                }
                            }
                        }
                    }
                    if(filenameCargo!=null && tipo.equals("C")){
                        if (mapUpload != null && !mapUpload.values().isEmpty()) {
                            List<ArchivoTemporal> lstArchivoTemporal = mapUpload.get("upload" + iIdUpload);
                            if (lstArchivoTemporal != null) {
                                for (ArchivoTemporal arc : lstArchivoTemporal) {
                                    for(int i=0;i<filenameCargoVarios.length;i++)
                                    {
                                        if (arc.getSNombre().equalsIgnoreCase(filenameCargoVarios[i])) {
                                            val = true;
                                        }  
                                    }
                                }
                            }
                        }
                    }
                   
                    if(filenameProyecto!=null && tipo.equals("Y")){
                        if (mapUpload != null && !mapUpload.values().isEmpty()) {
                            List<ArchivoTemporal> lstArchivoTemporal = mapUpload.get("upload" + iIdUpload);
                            if (lstArchivoTemporal != null) {
                                for (ArchivoTemporal arc : lstArchivoTemporal) {
                                    if (arc.getSNombre().equalsIgnoreCase(filenameProyecto)) {
                                       val = true;
                                    }
                                }
                            }
                        }
                    }
            }
            
            long max_file_size = StrutsProperties.getLongProperty(SigedPropertyEnum.MULTIPART_MAX_SIZE);
            String extension = "";
            
            int cantidadArchivos = 0;
            if(filenamePrincipal!=null && tipo.equals("P"))
                cantidadArchivos = filenamePrincipalVarios.length;
            else if (filename!=null && tipo.equals("A"))
                cantidadArchivos = 1;
                                               
            else if (filenameCargo!=null && tipo.equals("C"))
                cantidadArchivos = filenameCargoVarios.length;
            else if (filenameProyecto!=null && tipo.equals("Y"))
                cantidadArchivos = 1;
               
            for(int cantDoc=0;cantDoc<cantidadArchivos;cantDoc++)
            {
                if(filePrincipal!=null && tipo.equals("P")){
                    int indice_i = filenamePrincipalVarios[cantDoc].indexOf('.');
                    int indice_f = filenamePrincipalVarios[cantDoc].length();
                    extension = filenamePrincipalVarios[cantDoc].substring(indice_i, indice_f);
                }

                if(filenameCargo!=null && tipo.equals("C")){
                    int indice_i = filenameCargoVarios[cantDoc].indexOf('.');
                    int indice_f = filenameCargoVarios[cantDoc].length();
                    extension = filenameCargoVarios[cantDoc].substring(indice_i, indice_f);
                }

                if(filenameProyecto!=null && tipo.equals("Y")){
                    int indice_i = filenameProyecto.indexOf('.');
                    int indice_f = filenameProyecto.length();
                    extension = filenameProyecto.substring(indice_i, indice_f);
                }

                if(val){  
                    mapUpload = uploadService.refrescarListaUpload(mapUpload, iIdUpload);
                    this.mensaje = " <font color=\"#FF0000\"><b>No es posible agregar el mismo archivo </b></font>";
                    mapSession.put(Constantes.MENSAJE_UPLOAD, this.mensaje);
                }else if ((filenameCargo != null && filenameCargoVarios[cantDoc].length() >= max_file_size) || (filenameProyecto != null && filenameProyecto.length() >= max_file_size) || (file != null && file.length() >= max_file_size) || (filePrincipal != null && filePrincipal.get(cantDoc).length() >= max_file_size)) {
                    mapUpload = uploadService.refrescarListaUpload(mapUpload, iIdUpload);
                    this.mensaje = messageSource.getMessage(MessagePropertiesEnum.STRUTS_ERROR_FILE_TOO_LARGE);
                    mapSession.put(Constantes.MENSAJE_UPLOAD, "<font color=\"#FF0000\"><b>" + this.mensaje + "</b></font>");
                }else if (filePrincipal!=null && tipo.equals("P") && !extension.toUpperCase().equals(".PDF")){
                    mapUpload = uploadService.refrescarListaUpload(mapUpload, iIdUpload);
                    this.mensaje = " <font color=\"#FF0000\"><b>El documento Principal debe ser un archivo con formato PDF </b></font>";
                    mapSession.put(Constantes.MENSAJE_UPLOAD, this.mensaje);
                }else if (fileProyecto!=null && tipo.equals("Y") && !extension.toUpperCase().equals(".DOC") && !extension.toUpperCase().equals(".DOCX")){
                    mapUpload = uploadService.refrescarListaUpload(mapUpload, iIdUpload);
                    this.mensaje = " <font color=\"#FF0000\"><b>El documento Proyecto debe ser un archivo word </b></font>";
                    mapSession.put(Constantes.MENSAJE_UPLOAD, this.mensaje);
                }else if (filenameCargo!=null && tipo.equals("C") && !extension.toUpperCase().equals(".PDF")){
                    mapUpload = uploadService.refrescarListaUpload(mapUpload, iIdUpload);
                    if (cargoTramite!=null && cargoTramite.equals("S")){
                        this.mensaje = " El cargo debe ser un archivo con formato PDF ";
                    }else{
                        this.mensaje = " <font color=\"#FF0000\"><b>El cargo debe ser un archivo con formato PDF </b></font>";
                    }    
                    mapSession.put(Constantes.MENSAJE_UPLOAD, this.mensaje);
                }else{    
                    mapSession.remove(Constantes.MENSAJE_UPLOAD);

                    if (file!=null || filePrincipal!=null || fileCargo!=null || fileProyecto!=null){
                      if (file!=null && tipo.equals("A")){ 
                        if (filename != null && file != null) {
                                Documento documento = documentoService.findByIdDocumento(iIdDoc);
                                String ruta = documento.getID_CODIGO().toString() + "_ANX_" + filename.replace(" ", "_").replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u").replace("N°", "N").replace("°", "");
                                List<Archivo> lista = archivoDAO.buscarArchivosPorRutaDocumento(documento.getDocumentoreferencia()==null?documento.getIdDocumento():documento.getDocumentoreferencia(), ruta.toLowerCase());

                                if (lista!=null && lista.size()>0){
                                  for(int i=0;i<lista.size();i++){
                                    if (lista.get(i).getAutor().getIdusuario().toString().equals(objUsuario.getIdUsuarioPerfil().toString()) &&
                                       lista.get(i).getUnidadAutor().toString().equals(objUsuario.getIdUnidadPerfil().toString())){
                                       List<FirmaArchivo> lstFirmas = firmaArchivoDAO.findFirmado(lista.get(i).getIdArchivo());
                                       if (lstFirmas!=null && lstFirmas.size()>0){
                                          mapUpload = uploadService.refrescarListaUpload(mapUpload, iIdUpload);
                                          this.mensaje = "<font color=\"#FF0000\"><b>Usted no puede actualizar el archivo, porque ya se encuentra firmado.</b></font>";
                                          mapSession.put(Constantes.MENSAJE_UPLOAD, this.mensaje);
                                          break;
                                       }
                                       mapUpload = uploadService.moveFileToTempoDir(mapUpload, iIdUpload, file, filename, contentType, objUsuario.getUsuario(),"A");
                                       mapSession.put(Constantes.SESSION_UPLOAD_LIST, mapUpload);
                                       break;
                                    }else{
                                       mapUpload = uploadService.refrescarListaUpload(mapUpload, iIdUpload);
                                       this.mensaje = "<font color=\"#FF0000\"><b>Usted no puede actualizar el archivo, porque usted no es el propietario.</b></font>";
                                       mapSession.put(Constantes.MENSAJE_UPLOAD, this.mensaje);
                                    }
                                  }
                                }else{
                                       mapUpload = uploadService.moveFileToTempoDir(mapUpload, iIdUpload, file, filename, contentType, objUsuario.getUsuario(),"A");
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
                                       mapSession.put(Constantes.SESSION_UPLOAD_LIST, mapUpload);    
                                }
                          //jc  }
                        }else{
                           mensaje = "No se especifico ningun archivo a subir.";
                           mapSession.put(Constantes.MENSAJE_UPLOAD, mensaje);
                        }
                      }

                      if (filePrincipal!=null && tipo.equals("P")){  
                            if (filenamePrincipal != null && filePrincipal != null) {
                                List<ArchivoTemporal> lstArchivoTemporal = mapUpload.get("upload" + iIdUpload);
                                boolean existe = false;
                                Documento documento = documentoService.findByIdDocumento(iIdDoc);
                                if(!String.valueOf(documento.getTipoDocumento().getIdtipodocumento()).equals(Constantes.COD_TIPODOCUMENTO_OFICIO_CIRCULAR))
                                {
                                    if (lstArchivoTemporal!=null){
                                        for(int i=0;i<lstArchivoTemporal.size();i++){
                                            if (lstArchivoTemporal.get(i).getPrincipal().equals("P"))
                                               existe = true;
                                               break;
                                        }
                                    }      
                                }

                                if (existe){
                                     mapUpload = uploadService.refrescarListaUpload(mapUpload, iIdUpload);
                                     this.mensaje = "<font color=\"#FF0000\"><b>Ya existe un documento principal</b></font>";
                                     mapSession.put(Constantes.MENSAJE_UPLOAD, this.mensaje);
                                }else{
                                                //Documento documento = documentoService.findByIdDocumento(iIdDoc);
                                                String ruta = "";

                                                if (documento.getID_EXTERNO().toString().equals("1"))
                                                {
                                                   ruta =  documento.getID_CODIGO().toString() + "_" + documento.getTipoDocumento().getNombre() + ".pdf";       
                                                }
                                                else
                                                {
                                                    if(!String.valueOf(documento.getTipoDocumento().getIdtipodocumento()).equals(Constantes.COD_TIPODOCUMENTO_OFICIO_CIRCULAR))
                                                        ruta =  documento.getID_CODIGO().toString() + "_" + documento.getTipoDocumento().getNombre() + "_" + documento.getNumeroDocumento().replace("N°", "").trim() + ".pdf";
                                                    else
                                                        ruta =  documento.getID_CODIGO().toString() + "_" + documento.getTipoDocumento().getNombre() + "_" + documento.getNumeroDocumento().replace("N°", "").trim() + "_" + filenamePrincipalVarios[cantDoc].substring(0, 15) + ".pdf";
                                                }

                                                List<Archivo> lista = archivoDAO.buscarArchivosPorRutaDocumento(documento.getDocumentoreferencia()==null?documento.getIdDocumento():documento.getDocumentoreferencia(), ruta.toLowerCase());

                                                if (lista!=null && lista.size()>0){
                                                    for(int i=0;i<lista.size();i++){
                                                       if (lista.get(i).getAutor().getIdusuario().toString().equals(objUsuario.getIdUsuarioPerfil().toString()) &&
                                                           lista.get(i).getUnidadAutor().toString().equals(objUsuario.getIdUnidadPerfil().toString())){
                                                           List<FirmaArchivo> lstFirmas = firmaArchivoDAO.findFirmado(lista.get(i).getIdArchivo());
                                                           if (lstFirmas!=null && lstFirmas.size()>0){
                                                               mapUpload = uploadService.refrescarListaUpload(mapUpload, iIdUpload);
                                                               this.mensaje = "<font color=\"#FF0000\"><b>Usted no puede actualizar el archivo, porque ya se encuentra firmado.</b></font>";
                                                               mapSession.put(Constantes.MENSAJE_UPLOAD, this.mensaje);
                                                               break;
                                                           } 
                                                           mapUpload = uploadService.moveFileToTempoDir(mapUpload, iIdUpload, filePrincipal.get(cantDoc), filenamePrincipalVarios[cantDoc], contentType, objUsuario.getUsuario(),"P");                            
                                                           mapSession.put(Constantes.SESSION_UPLOAD_LIST, mapUpload);
                                                           break;
                                                       }else{
                                                           mapUpload = uploadService.refrescarListaUpload(mapUpload, iIdUpload);
                                                           this.mensaje = "<font color=\"#FF0000\"><b>Usted no puede actualizar el archivo, porque usted no es el propietario.</b></font>";
                                                           mapSession.put(Constantes.MENSAJE_UPLOAD, this.mensaje);
                                                       }
                                                    }
                                                }else{
                                                    if(documento.getAutor().getIdusuario().toString().equals(objUsuario.getIdUsuarioPerfil().toString()) &&
                                                            documento.getUnidadautor().toString().equals(objUsuario.getIdUnidadPerfil().toString())){
                                                        mapUpload = uploadService.moveFileToTempoDir(mapUpload, iIdUpload, filePrincipal.get(cantDoc), filenamePrincipalVarios[cantDoc], contentType, objUsuario.getUsuario(),"P");                            
                                                        mapSession.put(Constantes.SESSION_UPLOAD_LIST, mapUpload);
                                                    }else{
                                                        mapUpload = uploadService.refrescarListaUpload(mapUpload, iIdUpload);
                                                        this.mensaje = "<font color=\"#FF0000\"><b>Usted no puede registrar archivos, porque usted no es creador del documento.</b></font>";
                                                        mapSession.put(Constantes.MENSAJE_UPLOAD, this.mensaje);
                                                    }
                                                }
                                     //  }    
                                }
                            }else{
                                mensaje = "<font color=\"#FF0000\"><b> No se especifico ningun archivo a subir.</b></font>";
                                mapSession.put(Constantes.MENSAJE_UPLOAD, mensaje);
                            }                                                              
                       }

                       if (fileCargo!=null && tipo.equals("C")){  
                            if (filenameCargo != null && fileCargo != null) {
                                List<ArchivoTemporal> lstArchivoTemporal = mapUpload.get("upload" + iIdUpload);
                                boolean existe = false;
                                Documento documento = documentoService.findByIdDocumento(iIdDoc);

                                if(!String.valueOf(documento.getTipoDocumento().getIdtipodocumento()).equals(Constantes.COD_TIPODOCUMENTO_OFICIO_CIRCULAR))
                                {
                                    if (lstArchivoTemporal!=null){
                                        for(int i=0;i<lstArchivoTemporal.size();i++){
                                            if (lstArchivoTemporal.get(i).getPrincipal().equals("C"))
                                               existe = true;
                                               break;
                                        }
                                    }
                                }
                                if (existe){
                                     mapUpload = uploadService.refrescarListaUpload(mapUpload, iIdUpload);
                                     this.mensaje = "<font color=\"#FF0000\"><b>Ya existe un cargo</b></font>";
                                     mapSession.put(Constantes.MENSAJE_UPLOAD, this.mensaje);
                                }else{
                                        if (cargoTramite!=null && cargoTramite.equals("S")){
                                            mapUpload = uploadService.moveFileToTempoDir(mapUpload, iIdUpload, fileCargo.get(cantDoc), filenameCargoVarios[cantDoc], contentType, objUsuario.getUsuario(),"C");                            
                                            mapSession.put(Constantes.SESSION_UPLOAD_LIST, mapUpload);
                                        }else{
                                                //Documento documento = documentoService.findByIdDocumento(iIdDoc);
                                                String ruta = "";
                                                ruta =  documento.getID_CODIGO().toString() + "_CARGO_" +  filenameCargoVarios[cantDoc].replace(" ", "_").replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u").replace("N°", "N").replace("°", ""); 

                                                List<Archivo> lista = archivoDAO.buscarArchivosPorRutaDocumento(documento.getDocumentoreferencia()==null?documento.getIdDocumento():documento.getDocumentoreferencia(), ruta.toLowerCase());
                                                if (lista!=null && lista.size()>0){
                                                    for(int i=0;i<lista.size();i++){
                                                       if (lista.get(i).getAutor().getIdusuario().toString().equals(objUsuario.getIdUsuarioPerfil().toString()) &&
                                                           lista.get(i).getUnidadAutor().toString().equals(objUsuario.getIdUnidadPerfil().toString())){
                                                           //continue;
                                                           mapUpload = uploadService.moveFileToTempoDir(mapUpload, iIdUpload, fileCargo.get(cantDoc), filenameCargoVarios[cantDoc], contentType, objUsuario.getUsuario(),"C");                            
                                                           mapSession.put(Constantes.SESSION_UPLOAD_LIST, mapUpload);
                                                           break;
                                                       }else{ 
                                                           mapUpload = uploadService.refrescarListaUpload(mapUpload, iIdUpload);
                                                           this.mensaje = "<font color=\"#FF0000\"><b>Usted no puede actualizar el archivo, porque usted no es el propietario.</b></font>";
                                                           mapSession.put(Constantes.MENSAJE_UPLOAD, this.mensaje);
                                                       }
                                                    }
                                                }else{
                                                    mapUpload = uploadService.moveFileToTempoDir(mapUpload, iIdUpload, fileCargo.get(cantDoc), filenameCargoVarios[cantDoc], contentType, objUsuario.getUsuario(),"C");                            
                                                    mapSession.put(Constantes.SESSION_UPLOAD_LIST, mapUpload);                                                                             
                                                }
                                        }                                                                                                                               
                                }                                                                                                                                                                                                                                                                                                                     
                            }else{
                                mensaje = "<font color=\"#FF0000\"><b> No se especifico ningun archivo a subir.</b></font>";
                                mapSession.put(Constantes.MENSAJE_UPLOAD, mensaje);                                                                                   
                            }                                                                             
                       }

                       if (fileProyecto!=null && tipo.equals("Y")){
                            if (filenameProyecto != null && fileProyecto != null) {
                                Documento documento = documentoService.findByIdDocumento(iIdDoc);
                                String ruta = "";

                                ruta =  documento.getID_CODIGO().toString() + "_PROYECTO_" +  filenameProyecto.replace(" ", "_").replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u").replace("N°", "N").replace("°", "");

                                List<Archivo> lista = archivoDAO.buscarArchivosPorRutaDocumento(documento.getDocumentoreferencia()==null?documento.getIdDocumento():documento.getDocumentoreferencia(), ruta.toLowerCase());
                                if (lista!=null && lista.size()>0){
                                    for(int i=0;i<lista.size();i++){
                                       if (lista.get(i).getAutor().getIdusuario().toString().equals(objUsuario.getIdUsuarioPerfil().toString()) &&
                                           lista.get(i).getUnidadAutor().toString().equals(objUsuario.getIdUnidadPerfil().toString())){
                                           mapUpload = uploadService.moveFileToTempoDir(mapUpload, iIdUpload, fileProyecto, filenameProyecto, contentType, objUsuario.getUsuario(),"Y");                            
                                           mapSession.put(Constantes.SESSION_UPLOAD_LIST, mapUpload);
                                           break;
                                       }else{
                                           mapUpload = uploadService.refrescarListaUpload(mapUpload, iIdUpload);
                                           this.mensaje = "<font color=\"#FF0000\"><b>Usted no puede actualizar el archivo, porque usted no es el propietario.</b></font>";
                                           mapSession.put(Constantes.MENSAJE_UPLOAD, this.mensaje);
                                       }
                                    }
                                }else{
                                       mapUpload = uploadService.moveFileToTempoDir(mapUpload, iIdUpload, fileProyecto, filenameProyecto, contentType, objUsuario.getUsuario(),"Y");                            
                                       mapSession.put(Constantes.SESSION_UPLOAD_LIST, mapUpload);                                                                                                           
                                }
                            }else{
                                mensaje = "<font color=\"#FF0000\"><b> No se especifico ningun archivo a subir.</b></font>";
                                mapSession.put(Constantes.MENSAJE_UPLOAD, mensaje);
                            }
                       }
                    }else{
                        mensaje = "<font color=\"#FF0000\"><b>No se especifico ningun archivo a subir.</b></font>";
                        mapSession.put(Constantes.MENSAJE_UPLOAD, mensaje);
                    }                                                       
                }                                            
            }     
            return Action.SUCCESS;
        } catch (Exception e) {
            _log.error(e.getMessage(), e);
            return Action.ERROR;
        }
    }
    
  
    public String showUploadFiles() throws Exception {
        if (getIIdUpload() == null) {
            _log.error("No se recibio ningun Id Upload con el cual trabajar en sesion");
            return Action.ERROR;
        }
        return Action.SUCCESS;
    }
    
    public String showUploadFiles2() throws Exception {
        mapSession = ActionContext.getContext().getSession();
        mensaje = (String) mapSession.get(Constantes.MENSAJE_UPLOAD);
        _log.debug("mensaje : " + this.mensaje);
        if (getIIdUpload() == null) {
            _log.error("No se recibio ningun Id Upload con el cual trabajar en sesion");
            return Action.ERROR;
        }
        return Action.SUCCESS;
    }

    // ////////////////////////////////
    // Getters and Setters //
    // ////////////////////////////////
    public void setUpload(File file) {
        this.file = file;
    }
    
    public void setUploadPrincipal(List<File> filePrincipal) {
        this.filePrincipal = filePrincipal;
    }
    
    public void setUploadCargo(List<File> fileCargo) {
        this.fileCargo = fileCargo;
    }
    
    public void setUploadProyecto(File fileProyecto) {
        this.fileProyecto = fileProyecto;
    }

    public Integer getIIdUpload() {
        return iIdUpload;
    }

    public void setIIdUpload(Integer iIdUpload) {
        this.iIdUpload = iIdUpload;
    }

    public Map<String, Object> getMapSession() {
        return mapSession;
    }

    public void setMapSession(Map<String, Object> mapSession) {
        this.mapSession = mapSession;
    }

    public void setUploadContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setUploadPrincipalFileName(String filenamePrincipal) {
        this.filenamePrincipal = filenamePrincipal;
    }
    
    public void setUploadCargoFileName(String filenameCargo) {
        this.filenameCargo = filenameCargo;
    }
    
    public void setUploadProyectoFileName(String filenameProyecto) {
        this.filenameProyecto = filenameProyecto;
    }
    
    public void setUploadFileName(String filename) {
        this.filename = filename;
    }

    public String getSNombre() {
        return sNombre;
    }

    public void setSNombre(String sNombre) {
        this.sNombre = sNombre;
    }
    
    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setUploadService(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    public void setMessageSource(SigedMessageSource ms) {
        this.messageSource = ms;
    }
}
