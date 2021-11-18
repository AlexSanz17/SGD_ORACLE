package org.ositran.siged.service.impl;

import gob.ositran.siged.config.SigedProperties;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.siged.service.AlfrescoWSService;
import org.alfresco.webservice.authoring.AuthoringServiceSoapBindingStub;
import org.alfresco.webservice.content.ContentFault;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.QueryResult;
import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.CMLDelete;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.Node;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Query;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.ResultSet;
import org.alfresco.webservice.types.ResultSetRow;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.types.Version;
import org.alfresco.webservice.types.VersionHistory;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.ContentUtils;
import org.alfresco.webservice.util.ContentUtils2;
import org.alfresco.webservice.util.Utils;
import org.alfresco.webservice.util.WebServiceFactory;
import org.ositran.pojos.VersionView;
import org.ositran.repositorio.EnlaceDocumento;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import org.apache.axis.utils.StringUtils;
import org.ositran.common.alfresco.AlfrescoWebServiceCaller;

/**
 *
 * @author javier castillo
 */
@AlfrescoWebServiceCaller
public class AlfrescoWSServiceImpl implements AlfrescoWSService {

    private static final Logger log = Logger.getLogger(AlfrescoWSServiceImpl.class);
    private static final Store STORE = new Store(Constants.WORKSPACE_STORE, "SpacesStore");

    @Override
    public List<EnlaceDocumento> listarDocumentos(String ruta) {
        List<EnlaceDocumento> resultados = null;
        Node nodo = obtenerNodo(ruta);
        if (nodo == null) {
            throw new RuntimeException("No existe la ruta:\"" + ruta + "\" en el repositorio");
        }
        log.debug("Ruta a listar es del tipo:" + nodo.getType());
        RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();
        Reference reference = nodo.getReference();
        try {
            QueryResult result = repositoryService.queryChildren(reference);
            ResultSet rs = result.getResultSet();
            ResultSetRow[] rows = rs.getRows();
            if (rows != null) {
                resultados = obtenerEnlaceDocumentos(rows);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return resultados;
    }

    @Override
    public List<VersionView> getVersions(String ruta) {
        List<VersionView> lista = new ArrayList<VersionView>();
        try {
            Reference contentReference = new Reference(STORE, null, traducirXPath(ruta));
            //Predicate itemsToCheckOut=new Predicate(new Reference[]{contentReference},null,null);
            AuthoringServiceSoapBindingStub authoringService = WebServiceFactory.getAuthoringService();
            ContentServiceSoapBindingStub contentService = WebServiceFactory.getContentService();
            //Store store=new Store(Constants.WORKSPACE_STORE,"SpacesStore");
            //Content[] readResult=contentService.read(new Predicate(new Reference[]{contentReference},store,null),Constants.PROP_CONTENT);
            //Content content=readResult[0];
            //log.debug("This is the checked-in content:");
            //log.debug(ContentUtils.getContentAsString(content));
            // Get the version history
            //log.debug("The version history:");
            VersionHistory versionHistory = authoringService.getVersionHistory(contentReference);
            for (Version version : versionHistory.getVersions()) {
                // Output the version details
                // / version.
                //outputVersion(version);
                VersionView ver = new VersionView();
                ver.setVersion(version);
                Predicate predicate = new Predicate(new Reference[]{version.getId()}, null, null);
                String enlace = "";
                Content[] contents;
                try {
                    contents = contentService.read(predicate, Constants.PROP_CONTENT);
                    enlace = contents[0].getUrl() + "?ticket=" + AuthenticationUtils.getTicket();
                    log.debug("version label " + version.getLabel());
                    log.debug("version enlace " + enlace);
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
                ver.setEnlace(enlace);
                lista.add(ver);
            }
        } catch (Exception exd) {
            log.error(exd.getMessage(), exd);
        }
        return lista;
    }

    @Override
    public String obtenerLinkContenido(String ruta) {
        Predicate predicate = new Predicate(new Reference[]{new Reference(STORE, null, traducirXPath(ruta))}, null, null);
        ContentServiceSoapBindingStub contentService = WebServiceFactory.getContentService();
        Content[] contents;
        try {
            contents = contentService.read(predicate, Constants.PROP_CONTENT);
            String url = contents[0].getUrl().replace("+", "%20");
            return url + "?ticket=" + AuthenticationUtils.getTicket();
        } catch (RemoteException ex) {
            log.error(ex.getMessage(), ex);
        }
        // Node n=obtenerNodo(ruta);
        // for(NamedValue namedValue:n.getProperties()){
        // //Si la propiedad es cm:content, lo tomamos como el acceso hacia la
        // ruta respectiva
        // //El cm:content contine un nombre parecido a la URL, pero no es la
        // URL en si, así
        // //que hay que hacer una transformación. --No sirve :s
        // if(namedValue.getName().endsWith(Constants.PROP_CONTENT)){
        // String contentString = namedValue.getValue();
        // log.debug("cm:content="+contentString);
        // String[] values = contentString.split("[|=]");
        // Predicate predicate=new Predicate(new Reference[]{
        // n.getReference()},null,null);
        // ContentServiceSoapBindingStub contentService =
        // WebServiceFactory.getContentService();
        // Content[]
        // contents=contentService.read(predicate,Constants.PROP_CONTENT );
        // return
        // contents[0].getUrl()+"?ticket="+AuthenticationUtils.getTicket();
        // }
        // }
        return null;
    }

    @Override
    public boolean existeRuta(String ruta) {
        return obtenerNodo(ruta) != null;
    }

    @Override
    public boolean eliminarNodo(String sExpediente) {
        /*Node nodoPadre = obtenerNodo(sExpediente);

        if (nodoPadre == null) {
            log.info("La ruta especificada [" + sExpediente + "] no existe en el repositorio");

            return false;
        }

        log.debug("Eliminando nodo [" + sExpediente + "]");
        CMLDelete delete = new CMLDelete(new Predicate(new Reference[]{nodoPadre.getReference()}, null, null));
        CML cml = new CML();
        cml.setDelete(new CMLDelete[]{delete});

        try {
            WebServiceFactory.getRepositoryService().update(cml);
        } catch (Exception e2) {
            log.error(e2.getMessage(), e2);
            return false;
        }*/

        return true;
    }

    @Override
    public List<EnlaceDocumento> buscarContenidos(String QNameTipo, String texto, Map<String, String> properties) {
        List<EnlaceDocumento> lista = null;
        RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();
        Query query;
        try {
            query = new Query(Constants.QUERY_LANG_LUCENE, armarQuery(QNameTipo, texto, properties));
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException("Ocurrió un error al tratar de armar el query", ex);
        }
        try {
            QueryResult queryResult = repositoryService.query(STORE, query, false);
            ResultSetRow[] rows = queryResult.getResultSet().getRows();
            if (rows != null) {
                lista = obtenerEnlaceDocumentos(rows);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException("Ocurrió un error al tratar de acceder al repositorio", ex);
        }
        return lista;
    }



    /**
     * Descarga un archivo desde alfresco para la <code>rutaAlfresco</code> y lo almacena en la
     * <code>rutaDestino</code>.
     *
     * @param rutaAlfresco
     * 			expediente donde se encuentra el archivo
     * @param archivo
     * 			nombre del archivo
     * @param rutaDestino ruta
     * 			donde se almacenara el archivo
     * @return si se descargo correctamente el archivo
     * @author German Enriquez
     */
    
    public String prueba(){
        System.out.println("prueba.............");
        return "prueba";
    }
    
    @Override
    public boolean descargarArchivo(String rutaAlfresco, String archivo, String rutaDestino) {
        if (!rutaAlfresco.endsWith("/")) {
            rutaAlfresco += "/";
        }
        //archivo=archivo.replace(" ","%20");
        ContentServiceSoapBindingStub contentService = WebServiceFactory.getContentService();
        Reference contentReference = new Reference(STORE, null, traducirXPath(rutaAlfresco + archivo));
        try {
            Content[] resultado = contentService.read(new Predicate(new Reference[]{contentReference}, STORE, null), Constants.PROP_CONTENT);
            for (Content contenido : resultado) {
                String[] url = contenido.getUrl().split("/");
                String nombre = URLDecoder.decode(url[url.length - 1], "UTF-8");
                if (nombre.equals(archivo)) {
                    if (!rutaDestino.endsWith("/")) {
                        rutaDestino += "/";
                    }
                    InputStream in = null;
                    OutputStream salida = null;
                    try {
                        in = ContentUtils.getContentAsInputStream(contenido);
                        salida = new FileOutputStream(rutaDestino + archivo);
                        int data = 999;
                        while (data >= 0) {
                            data = in.read();
                            salida.write(data);
                        }
                        salida.flush();
                        return true;
                    } catch (FileNotFoundException e) {
                        log.error("No se encontro la ruta " + rutaDestino);
                        return false;
                    } catch (IOException e) {
                        log.error("Ocurrio un error leyendo el archivo descargado desde alfresco");
                        return false;
                    } finally {
                        try {
                            if (salida != null) {
                                salida.close();
                            }
                            if (in != null) {
                                in.close();
                            }
                        } catch (IOException ex) {
                            log.error(ex.getMessage(), ex);
                            return false;
                        }
                    }
                }
                log.warn("No se encontro el archivo " + rutaAlfresco + archivo + " en alfresco");
                return false;
            }
            log.warn("No se encontro el archivo " + rutaAlfresco + archivo + " en alfresco");
            return false;
        } catch (ContentFault e) {
            log.error("Ocurrio un error al tratar de acceder al archivo " + rutaAlfresco + archivo, e);
            return false;
        } catch (RemoteException e) {
            log.error("Ocurrio un error al tratar de acceder al archivo " + rutaAlfresco + File.separator + archivo, e);
            return false;
        } catch (UnsupportedEncodingException e) {
            log.error("No se pudo leer el archivo proveninete de alfresco", e);
            return false;
        }
    }

    @Override
    public List<EnlaceDocumento> buscarContenidos(String texto, Map<String, String> properties) {
        return buscarContenidos(TYPE_ARCHIVO, texto, properties);
    }

    @Override
    public List<Integer> buscarIdDocumentos(String texto, Map<String, String> properties) {
        return buscarIdDocumentos(TYPE_ARCHIVO, texto, properties);
    }

    @Override
    public boolean subirArchivo(String ruta, File archivo, String nombreArchivo, Map<String, String> propiedades) {
        Reference ref = anexarArchivo(ruta, archivo, TYPE_ARCHIVO, nombreArchivo, propiedades);
        if (ref == null) {
            return false;
        }
        return true;
    }

    private Node obtenerNodo(String ruta) {
        if (ruta != null) {
            Reference reference = new Reference(STORE, null, traducirXPath(ruta));
            try {
                // Get the repository
                RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();
                Predicate predicate = new Predicate(new Reference[]{reference}, null, null);
                Node[] nodes = repositoryService.get(predicate);
                log.debug("Nodes:" + nodes);
                if (nodes != null) {
                    if (nodes.length > 1) {
                        log.warn("Más de 1 nodo para el reference:" + reference.getPath());
                    }
                    return nodes[0];
                }
                return null;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    private Reference crearNodo(String rutaPadre, String nombre, String QNameStringTipo, Map<String, String> propiedades) {
        log.debug(" en crearNodo String String String Map <> ");
        Node nodoPadre = obtenerNodo(rutaPadre);
        log.debug(" obtenerNodo ");
        if (nodoPadre == null) {
            throw new RuntimeException("La ruta especificada: /" + rutaPadre + " no existe en el repositorio.");
        }
        ParentReference parentReference = new ParentReference(STORE, nodoPadre.getReference().getUuid(), null, Constants.ASSOC_CONTAINS, Constants.ASSOC_CONTAINS);
        ArrayList<NamedValue> lProperties = new ArrayList<NamedValue>();
        lProperties.add(Utils.createNamedValue(Constants.PROP_NAME, nombre));
        if (propiedades != null) {
            for (Map.Entry<String, String> entry : propiedades.entrySet()) {
                lProperties.add(Utils.createNamedValue(entry.getKey(), entry.getValue()));
            }
        }
        NamedValue[] properties = lProperties.toArray(new NamedValue[lProperties.size()]);
        // Creamos el nodo TYPE_DOCUMENTO
        log.debug(" Creamos el nodo ");
        CMLCreate create = new CMLCreate("1", parentReference, null, null, null, QNameStringTipo, properties);
        CML cml = new CML();
        cml.setCreate(new CMLCreate[]{create});
        Reference nodoCreado = null;
        try {
            RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();
            // Execute the update
            UpdateResult[] updateResults = repositoryService.update(cml);
            if (log.isDebugEnabled()) {
                log.debug("Cantidad de update results:" + updateResults.length);
                log.debug("source:" + updateResults[0].getSource());
                log.debug("mensje:" + updateResults[0].getStatement());
                log.debug("destination:" + updateResults[0].getDestination());
            }
            nodoCreado = updateResults[0].getDestination();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            nodoCreado = null;
        }
        return nodoCreado;
    }

    // aca las propiedades las vamos a armar desde el properties de webservice
    private String armarQuery(String QNameType, String texto, Map<String, String> properties) throws IOException {
        String query = "";
        if (!StringUtils.isEmpty(QNameType)) {
            log.debug("Query con tipo:" + QNameType);
            query = "+TYPE:\"" + QNameType + '"';
        }
        if (!StringUtils.isEmpty(texto)) {
            log.debug("Query al texto:" + texto);
            query += " +TEXT:\"" + texto + '"';
        }
        if (properties != null) {
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                try {
                    String rkey = SigedProperties.getPropertyByKey("alfresco.search.propertyname." + entry.getKey());
                    if (!StringUtils.isEmpty(rkey)) {
                        query += " +@" + escapeQName(rkey) + ":\"" + entry.getValue() + '"';
                    }
                } catch (MissingResourceException mre) {
                    log.debug("No se encontro una llave del nombre:" + entry.getKey());
                }
            }
        }
        log.debug("FULL QUERY:\n\n" + query);
        return query;
    }

    private List<EnlaceDocumento> obtenerEnlaceDocumentos(ResultSetRow[] resultadosQuery) throws RemoteException {
        ContentServiceSoapBindingStub contentService = WebServiceFactory.getContentService();
        ArrayList<EnlaceDocumento> resultados = new ArrayList<EnlaceDocumento>();
        ArrayList<Reference> referenceList = new ArrayList<Reference>();
        for (ResultSetRow row : resultadosQuery) {
            // Por cada row tenemos un unico id que identifica el nodo hijo en
            // el repositorio
            String id = row.getNode().getId();
            // El arreglo de "columns" contiene todas las propiedades de un
            // nodo.
            EnlaceDocumento ed = new EnlaceDocumento();
            for (NamedValue namedValue : row.getColumns()) {
                // Si la propiedad es cm:name, lo tomamos como nombre de archivo
                if (namedValue.getName().endsWith(Constants.PROP_NAME)) {
                    ed.setNombreArchivo(namedValue.getValue());
                    // break;
                }
                // Si la propiedad es cm:content, lo tomamos como el acceso
                // hacia la ruta respectiva
                // El cm:content contine un nombre parecido a la URL, pero no es
                // la URL en si, así
                // que hay que hacer una transformación. --No sirve :s
                if (namedValue.getName().endsWith(Constants.PROP_CONTENT)) {
                    String contentString = namedValue.getValue();
                    log.debug("cm:content=" + contentString);
                    String[] values = contentString.split("[|=]");
                    ed.setURL(values[1] + "?ticket=" + AuthenticationUtils.getTicket());
                    log.debug("URL=" + ed.getURL());
                    break;
                }
            }
            if (ed.getURL() != null) {
                referenceList.add(new Reference(STORE, id, null));
                resultados.add(ed);
            }
        }
        // Esta es la otra forma para obtener la referencia al link.
        Reference childReferences[] = referenceList.toArray(new Reference[referenceList.size()]);
        Predicate childPredicate = new Predicate(childReferences, null, null);
        Content[] contents = contentService.read(childPredicate, Constants.PROP_CONTENT);
        if (contents != null) {
            Iterator<EnlaceDocumento> it = resultados.iterator();
            for (Content content : contents) {
                EnlaceDocumento ed = it.next();
                ed.setURL(content.getUrl() + "?ticket=" + AuthenticationUtils.getTicket());
            }
        }
        return resultados;
    }

    private List<Integer> buscarIdDocumentos(String QNameTipo, String texto, Map<String, String> properties) {
        List<Integer> lista = null;
        RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();
        Query query;
        try {
            query = new Query(Constants.QUERY_LANG_LUCENE, armarQuery(QNameTipo, texto, properties));
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException("Ocurrió un error al tratar de armar el query", ex);
        }
        try {
            QueryResult queryResult = repositoryService.query(STORE, query, false);
            ResultSetRow[] rows = queryResult.getResultSet().getRows();
            if (rows != null) {
                lista = obtenerIdDocumentos(rows);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new RuntimeException("Ocurrió un error al tratar de acceder al repositorio", ex);
        }
        return lista;
    }

    private List<Integer> obtenerIdDocumentos(ResultSetRow[] resultadosQuery) {
        Set<Integer> resultados = new HashSet<Integer>();
        String QNameProperty = Constants.createQNameString(NAMESPACE_OSINERG_MODEL, "idDocumento");
        for (ResultSetRow row : resultadosQuery) {
            // Por cada row tenemos un unico id que identifica el nodo hijo en
            // el repositorio
            // String id=row.getNode().getId();
            // El arreglo de "columns" contiene todas las propiedades de un
            // nodo.
            for (NamedValue namedValue : row.getColumns()) {
                // Si la propiedad es sgd:idDocumento, lo agregamos al Set de
                // Ids.
                if (namedValue.getName().endsWith(QNameProperty)) {
                    try {
                        if (!StringUtils.isEmpty(namedValue.getValue())) {
                            resultados.add(Integer.valueOf(namedValue.getValue()));
                        }
                    } catch (NumberFormatException nfe) {
                        log.warn("Error al transformar el idDocumento", nfe);
                    }
                    break;
                }
            }
        }
        ArrayList<Integer> rstd = new ArrayList<Integer>();
        rstd.addAll(resultados);
        return rstd;
    }

    private Reference crearNodo(String ruta, String QNameStringTipo, Map<String, String> propiedades) {
        String r[] = ruta.split("/");
        return crearNodo(ruta.substring(0, ruta.lastIndexOf('/')), r[r.length - 1], QNameStringTipo, propiedades);
    }

    private List<Reference> anexarArchivos(String ruta, List<File> archivos, List<String> QNameTipoContenido) {
        List<Reference> resultados = new ArrayList<Reference>();
        for (int i = 0; i < archivos.size(); i++) {
            resultados.add(anexarArchivo(ruta, archivos.get(i), QNameTipoContenido.get(i)));
        }
        return resultados;
    }

    private Reference anexarArchivo(String ruta, File archivo, String QNameTipoContenido) {
        return anexarArchivo(ruta, archivo, QNameTipoContenido, archivo.getName());
    }

    private Reference anexarArchivo(String ruta, File archivo, String QNameTipoContenido, String nombreArchivo) {
        return anexarArchivo(ruta, archivo, QNameTipoContenido, nombreArchivo, null);
    }

    private Reference anexarArchivo(String ruta, File archivo, String QNameTipoContenido, String nombreArchivo, Map<String, String> propiedades) {
        Reference resultado = null;
        try {
            String rutaCompleta = ruta + "/" + nombreArchivo;
            log.debug("Existe el archivo:" + archivo.getAbsolutePath() + "::" + archivo.exists());
            if (!archivo.exists() && !archivo.isFile()) {
                throw new IllegalArgumentException("La ruta " + archivo.getAbsolutePath() + " no existe o no es un archivo");
            }
            String contentData = ContentUtils2.putContent(archivo);
            //log.info("Puse data en el repositorio:"+contentData);
            if (!StringUtils.isEmpty(contentData)) {
                if (propiedades == null) {
                    propiedades = new HashMap<String, String>();
                }
                propiedades.put(Constants.PROP_CONTENT, contentData);
                resultado = crearNodo(rutaCompleta, QNameTipoContenido, propiedades);
                if (resultado == null) {
                    throw new RuntimeException("No se pudo crear la ruta :" + rutaCompleta);
                }
            } else {
                throw new RuntimeException("No subió la data");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Ocurrió un error al tratar de acceder al repositorio");
        }
        return resultado;
    }

    private String escapeQName(String qname) {
        return qname.replace(":", "\\:");
    }

    private String traducirXPath(String ruta) {
        if (ruta != null) {
            String sruta[] = ruta.split("/");
            StringBuilder sb = new StringBuilder("/app:company_home");
            for (String dir : sruta) {
                sb.append("/*[@cm:name=\"").append(dir).append("\"]");
            }
            //log.debug("ruta XPATH="+sb.toString());
            return sb.toString();
        }
        return null;
    }
}
