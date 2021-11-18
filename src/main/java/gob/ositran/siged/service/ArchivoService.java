/*LICENCIA DE USO DEL SGD .TXT*/package gob.ositran.siged.service;

import java.util.List;

import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.Usuario;

/**
 *
 * @author diego.gil
 */
public interface ArchivoService {

    void bloquearArchivo(Integer idArchivo, Usuario usuario, String nodeId);

    void desbloquearArchivo(String nodeId);

    List<Archivo> listarArchivosBloqueadosPorUsuario(Integer idUsuario);
}
