/*LICENCIA DE USO DEL SGD .TXT*/package gob.ositran.siged.service;

import gob.ositran.siged.dto.ArchivoAlfrescoRemotoDTO;
import java.io.File;

import com.btg.ositran.siged.domain.Usuario;

/**
 *
 * @author javier castillo
 */
public interface AlfrescoWebscriptService {

    void modificarContenidoArchivo(Usuario usuario, File file, String basePath, String nombreArchivo);

    ArchivoAlfrescoRemotoDTO checkOutPostTicket(String user, String password, String rutaArchivoAlfresco);

    void endCheckOutConfirm(String nodeId, String ticket);

    void endCheckOutCancel(String nodeId, String ticket);
}
