/*LICENCIA DE USO DEL SGD .TXT*/package gob.ositran.siged.service;

/**
 *
 * @author javier castillo
 */
public interface SeguridadService {

    String encriptar(String usuario, String clave);

    String desencriptar(String usuario, String clave);
}
