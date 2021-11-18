/*LICENCIA DE USO DEL SGD .TXT*/package gob.ositran.siged.service;

import gob.ositran.siged.dto.ArchivoAlfrescoRemotoDTO;

import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.Usuario;

/**
 *
 * @author diego.gil
 */
public interface RepositorioService {

    ArchivoAlfrescoRemotoDTO comenzarEdicionRemota(Usuario usuario, Archivo archivo);

    void finalizarEdicionRemotaConfirmar(String nodeId, String ticket);

    void finalizarEdicionRemotaCancelar(String nodeId, String ticket);

    String descargarArchivo(String rutaAlfresco, String archivo, String rutaDestino);

    String descargarArchivoTemporal(String rutaAlfresco, String archivo);
}
