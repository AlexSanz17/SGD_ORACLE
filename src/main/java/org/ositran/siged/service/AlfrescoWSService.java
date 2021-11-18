package org.ositran.siged.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.ositran.pojos.VersionView;
import org.ositran.repositorio.EnlaceDocumento;
import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.util.Constants;
import java.io.InputStream;
import java.io.OutputStream;
/**
 *
 * @author javier castillo
 */
public interface AlfrescoWSService {
    public static final String NAMESPACE_OSINERG_MODEL = "http://www.osinergmin.gob.pe/model/siged/1.0";
    public static final String TYPE_ARCHIVO = Constants.createQNameString(NAMESPACE_OSINERG_MODEL, "archivo");
    public static final String TYPE_EXPEDIENTE = Constants.createQNameString(NAMESPACE_OSINERG_MODEL, "expediente");
    public static final String ASPECT_EXPEDIENTE_DATA = Constants.createQNameString(NAMESPACE_OSINERG_MODEL, "metadataExpediente");

    List<EnlaceDocumento> listarDocumentos(String ruta);

    List<VersionView> getVersions(String ruta);

    String obtenerLinkContenido(String ruta);

    boolean eliminarNodo(String sExpediente);

    boolean existeRuta(String ruta);

    List<EnlaceDocumento> buscarContenidos(String QNameTipo, String texto, Map<String, String> properties);
    
    public String prueba();

    boolean descargarArchivo(String rutaAlfresco, String archivo, String rutaDestino);

    boolean subirArchivo(String ruta, File archivo, String nombreArchivo, Map<String, String> propiedades);

    List<Integer> buscarIdDocumentos(String texto, Map<String, String> properties);

    public List<EnlaceDocumento> buscarContenidos(String texto, Map<String, String> properties);
}
