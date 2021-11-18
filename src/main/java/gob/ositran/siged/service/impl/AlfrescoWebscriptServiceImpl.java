/*LICENCIA DE USO DEL SGD .TXT*/package gob.ositran.siged.service.impl;

import com.antartec.alfresco.AlfrescoConnector;
import com.antartec.alfresco.AlfrescoConnector.RETURN_CODE;
import com.btg.ositran.siged.domain.Usuario;

import gob.ositran.siged.config.SigedProperties;
import gob.ositran.siged.dto.ArchivoAlfrescoRemotoDTO;
import gob.ositran.siged.service.AlfrescoWebscriptService;
import gob.ositran.siged.service.SeguridadService;
import java.io.File;
import java.util.Date;
import org.ositran.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author javier castillo
 */
public class AlfrescoWebscriptServiceImpl implements AlfrescoWebscriptService {

    private static final Logger log = LoggerFactory.getLogger(AlfrescoWebscriptServiceImpl.class);
    private AlfrescoConnector alfrescoConnector;
    private SeguridadService seguridadService;

    public enum accionFinEdicion {
        CONFIRMAR("confirm"),
        CANCELAR("cancel");
        private String key;
        accionFinEdicion(String key) {
            this.key = key;
        }
        public String getKey() {
            return key;
        }
        @Override
        public String toString() {
            return key;
        }
    }

    public AlfrescoWebscriptServiceImpl() {
        StringBuilder sb = new StringBuilder();
        String alfrescoHost = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_HOST);
        String alfrescoPort = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PORT);
        sb.append(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PROTOCOLO) + "://");
        sb.append(alfrescoHost);
        sb.append(":");
        sb.append(alfrescoPort);
        sb.append("/alfresco");
        alfrescoConnector = AlfrescoConnector.getInstance();
        alfrescoConnector.setExternalServerURL(sb.toString());
        alfrescoConnector.setServerHost(alfrescoHost);
        alfrescoConnector.setServerPort(alfrescoPort);
        alfrescoConnector.setServerURL(sb.toString());
    }

    @Override
    public void modificarContenidoArchivo(Usuario usuario, File file, String basePath, String nombreArchivo) {
        String fullPath = basePath;
        if (!basePath.endsWith(Constantes.GENERAL_SLASH)) {
            fullPath += Constantes.GENERAL_SLASH;
        }
        fullPath += nombreArchivo;
        String clave = seguridadService.desencriptar(usuario.getUsuario(), usuario.getClave());
        boolean nodoExiste = alfrescoConnector.nodeExists(usuario.getUsuario(), clave, fullPath);
        if (nodoExiste) {
            RETURN_CODE result = alfrescoConnector.modifyBinaryContent(usuario.getUsuario(), clave, fullPath, file, "content");
            if (result != AlfrescoConnector.RETURN_CODE.SUCCESS) {
                throw new RuntimeException("No se pudo modificar el contenido del archivo.");
            }
        } else {
            throw new RuntimeException("No se pudo modificar el contenido del archivo porque no existe.");
        }
    }

    public void setSeguridadService(SeguridadService seguridadService) {
        this.seguridadService = seguridadService;
    }

    @Override
    public ArchivoAlfrescoRemotoDTO checkOutPostTicket(String user, String password, String rutaArchivoAlfresco) {
        log.info("comenzando chekOutPostTicket");
//        String ticket = null;
//        String rutaArchivo = rutaArchivoAlfresco;
//        AlfrescoNode node = null;
//        String realPath = null;
//        try {
//            ticket = alfrescoConnector.getTicket(user,password);
//            rutaArchivo = "/Company Home/" +rutaArchivoAlfresco;
//            node = alfrescoConnector.checkOutPostTicket(rutaArchivo, ticket);
//            realPath=node.getDisplayPath()+"/"+node.getName();
//        } catch(Exception ex){
//            throw new RuntimeException("Fallo al iniciar CheckOut de Archivo en:"+rutaArchivo,ex);
//        }
//        return new ArchivoAlfrescoRemotoDTO(ticket,node.getId(),new Date(),realPath);
        return new ArchivoAlfrescoRemotoDTO("TICKET","ID_NODO",new Date(),"PATH");
    }

    @Override
    public void endCheckOutConfirm(String nodeId, String ticket){
        endCheckOut(nodeId,ticket,accionFinEdicion.CONFIRMAR.toString());
    }

    @Override
    public void endCheckOutCancel(String nodeId, String ticket){
        endCheckOut(nodeId,ticket,accionFinEdicion.CANCELAR.toString());
    }

    private void endCheckOut(String nodeId, String ticket, String accion) {
        log.info("comenzando endCheckOut:"+nodeId);
        try {
//            alfrescoConnector.endCheckOut(nodeId, ticket, accion);
        } catch(Exception ex) {
            throw new RuntimeException("Error al finalizar checkOut nodeId:"+nodeId,ex);
        }
    }
}
