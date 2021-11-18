 package org.ositran.services;

import com.antartec.alfresco.AlfrescoConnector;
import com.antartec.alfresco.AlfrescoConnector.RETURN_CODE;
import com.antartec.alfresco.AlfrescoNode;
import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Usuario;
import com.ositran.cmis.api.AlfrescoApiWs;

 import org.apache.chemistry.opencmis.client.api.Session;
import gob.ositran.siged.config.SigedProperties;
import gob.ositran.siged.service.SeguridadService;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.alfresco.webservice.util.Constants;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.ositran.siged.service.AlfrescoWSService;
import org.ositran.daos.ArchivoDAO;
import org.ositran.daos.DocumentoDAO;
import org.ositran.daos.ExpedienteDAO;
import org.ositran.repositorio.EnlaceDocumento;
import org.ositran.repositorio.RepositorioUtils;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

public class RepositorioServiceWebservice implements RepositorioService{

	protected Logger log=Logger.getLogger(RepositorioServiceWebservice.class);
	private ExpedienteDAO expedienteDAO=null;
	private DocumentoDAO documentoDAO=null;
	private ArchivoDAO archivoDAO=null;
	private ArchivoService archivoService;
        
        private static String USERCREADOR=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_USUARIOCREADOR);
        private static String USERCREADOR_CLAVE=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_USUARIOCREADOR_CLAVE);
        
        //protected String usuarioAlfrescoAdmin = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_USUARIOADMIN);
	//protected String claveAlfrescoAdmin   = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_USUARIOADMIN_CLAVE);
        
	private static final String RUTA_PADRE_EXPEDIENTE=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_SPACE);
	private static final String COMPLETE_PATH_PADRE_EXPEDIENTE=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_ROOT) ;
        private AlfrescoConnector alfrescoConnector;
   	private AlfrescoWSService alfrescoWebServiceClient;
   	private SeguridadService seguridadService;
        private final String REPOSITORIO_ID  = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_ROOTID);
        private  String[] strMonths = new String[]{
						"ENERO",
						"FEBRERO",
						"MARZO",
						"ABRIL",
						"MAYO",
						"JUNIO",
						"JULIO",
						"AGOSTO",
						"SETIEMBRE",
						"OCTUBRE",
						"NOVIEMBRE",
						"DICIEMBRE"};

        public RepositorioServiceWebservice(){
            StringBuilder sb=new StringBuilder();
            String alfrescoHost=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_HOST);
            String alfrescoPort=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PORT);
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

	public List<EnlaceDocumento> listarDocumentos(Integer idexpediente){
		return listarDocumentos(getExpedienteDAO().findByIdExpediente(idexpediente));
	}

	public List<EnlaceDocumento> listarDocumentos(Expediente objExpediente){
		return alfrescoWebServiceClient.listarDocumentos(obtenerRutaExpediente(objExpediente));
	}
        
    public boolean crearEstructuraDocumento(String sUsuario, String sPassword, Integer iIdDocumento, String rutaSite, String tipoDocumento) {
      log.info("RepositorioServiceWebService :: crearEstructuraDocumento");
     
      try {
         Documento doc=getDocumentoDAO().findByIdDocumento(iIdDocumento);
         String sYear = new SimpleDateFormat("yyyy").format(doc.getFechaDocumento());
         StringBuilder  sbRutaSite = new StringBuilder(COMPLETE_PATH_PADRE_EXPEDIENTE).append(rutaSite).append(Constantes.GENERAL_SLASH);
         StringBuilder  sbRutaYear = new StringBuilder(sbRutaSite.toString()).append(sYear).append(Constantes.GENERAL_SLASH);
         StringBuilder  sbRutaTipoDocumento =  new StringBuilder(sbRutaYear.toString()).append(tipoDocumento).append(Constantes.GENERAL_SLASH);
         boolean existeNodo = alfrescoConnector.nodeExists(sUsuario, seguridadService.desencriptar(sUsuario, sPassword), sbRutaTipoDocumento.toString());
       
         if (!existeNodo){ 
           
            if (!this.crearFolder(sUsuario, sPassword, sbRutaSite.toString(), sYear)) {
               log.error("Ruta ANO NO ha sido creado en Alfresco");
               return false;
            }

            if (!this.crearFolder(sUsuario, sPassword, sbRutaYear.toString(), tipoDocumento)) {
               log.error("Ruta TIPO DOCUMENTO NO ha sido creado en Alfresco");
               return false;
            }   
         }
         
         return true;
         
      } catch (Exception e) {
         e.printStackTrace();
         log.error(e.getMessage(), e);
         return false;
      }
   }    
    
        public boolean eliminarEstructuraExpediente(Expediente objExpediente) {
                return true;
        }
        
        public boolean existsNodo(Expediente objExpediente) {
                return true;
        }

   	@Override
	@Transactional
	public void subirArchivosTransformadosARepositorio(Documento doc, List<Archivo> listaArchivos,boolean versionar,Usuario usuario, String rutaSite, String tipoDocumento) throws RuntimeException{
	   log.info("RepositorioServiceWebService :: subirArchivosTransformadosARepositorio");
           Map<String,String> propiedades=obtenerMetadata(doc, usuario);
	   Date inicio = null;
           Date fin = null;
           boolean bandera = false;
           
           for(Archivo arc : listaArchivos){
                        File f=new File(arc.getRutaArchivoPdf());
			if(f.exists() && f.isFile()){
                                String nombreArchivo=arc.getRutaArchivoPdf().substring(arc.getRutaArchivoPdf().indexOf(']')+1);
				RETURN_CODE result=null;
				AlfrescoNode node=null;
                                String userName=  USERCREADOR;//usuario.getUsuario();
                                String password=  USERCREADOR_CLAVE;//usuario.getClave();
                                crearEstructuraDocumento(userName, password, doc.getIdDocumento(), rutaSite, tipoDocumento);
                                String basePath=obtenerRutaCompletaDocumento(doc, rutaSite, tipoDocumento);
                                String fullPath=obtenerRutaCompletaDocumento(doc, rutaSite, tipoDocumento) + nombreArchivo;
                                if (log.isDebugEnabled()) {
                                        inicio = new Date();
                                }
                                boolean existeNodo = alfrescoConnector.nodeExists(userName, seguridadService.desencriptar(userName, password), fullPath);
                                if (log.isDebugEnabled()) {
                                            fin = new Date();
                                    log.debug("## alfrescoConnector.nodeExists [" + fullPath + "]: " + ((fin.getTime() - inicio.getTime()) / 1000.0) + " segundos" );
                                }
                                if (!existeNodo){
                                    if (log.isDebugEnabled()) {
                                            inicio = new Date();
                                    }

                                    node = alfrescoConnector.createCustomNode(userName, seguridadService.desencriptar(userName, password), basePath, nombreArchivo, AlfrescoWSService.TYPE_ARCHIVO, propiedades);
                                    if (log.isDebugEnabled()) {
                                            fin = new Date();
                                            log.debug("## alfrescoConnector.createCustomNode [" + fullPath + "]: " + ((fin.getTime() - inicio.getTime()) / 1000.0) + " segundos" );
                                    }

                                    if (node!=null){
                                       if (log.isDebugEnabled()) {
                                          inicio = new Date();
                                        }

                                        result=alfrescoConnector.modifyBinaryContent(userName, seguridadService.desencriptar(userName, password), fullPath, f, "content");
                                        if (log.isDebugEnabled()) {
                                            fin = new Date();
                                            log.debug("## alfrescoConnector.modifyBinaryContent [" + fullPath + "]: " + ((fin.getTime() - inicio.getTime()) / 1000.0) + " segundos" );
                                        }
                                       
                                        if(result!= AlfrescoConnector.RETURN_CODE.SUCCESS){
                                            throw new RuntimeException("No se pudo subir el archivo.");
                                        }
                                        result=alfrescoConnector.addAspect(userName, seguridadService.desencriptar(userName, password), fullPath, "cm:versionable");
                                        if(result!= AlfrescoConnector.RETURN_CODE.SUCCESS){
                                            throw new RuntimeException("No se pudo establecer como versionable el archivo.");
                                        }
                                    }
                                }else{
                                    if (log.isDebugEnabled()) {
                                        inicio = new Date();
                                    }
                                    
                                    result=alfrescoConnector.modifyBinaryContent(userName, seguridadService.desencriptar(userName, password), fullPath, f, "content");
                                    if (log.isDebugEnabled()) {
                                        fin = new Date();
                                        log.debug("## alfrescoConnector.modifyBinaryContent [" + fullPath + "]: " + ((fin.getTime() - inicio.getTime()) / 1000.0) + " segundos" );
                                    }
                                    
                                    if(result!= AlfrescoConnector.RETURN_CODE.SUCCESS){
                                        throw new RuntimeException("No se pudo subir el archivo.");
                                    }
                                }
                                
                                if((result==AlfrescoConnector.RETURN_CODE.SUCCESS)){
                                	AlfrescoApiWs alfrescoApiWs;
                                        String alfrescoHostPublico = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_HOST);
                                        String alfrescoHostPort = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PORT);
                                        String alfrescoProtocolo = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PROTOCOLO);
                                        String URL_ALFRESCO = alfrescoProtocolo+"://"+alfrescoHostPublico+":"+alfrescoHostPort+"/alfresco/cmisatom";
					String sruta[] = arc.getRutaAlfresco().split("/");
                                        String rutaCarpeta = "/"+sruta[0]+"/"+sruta[1]+"/"+sruta[2]+"/"+sruta[3]+"/"+sruta[4]+"/"+sruta[5]+"/"+sruta[6];
                                        String idDocumento="";
                                        Session sesionAlfresco = null;
                                        
                                        try{
                                                alfrescoApiWs = new AlfrescoApiWs(URL_ALFRESCO, USERCREADOR, USERCREADOR_CLAVE, REPOSITORIO_ID);
                                                sesionAlfresco = alfrescoApiWs.getSessionAlfresco();
                                                Document carpetaTestSistemas = (Document)sesionAlfresco.getObjectByPath(rutaCarpeta);
                                                idDocumento = carpetaTestSistemas.getId();
                                                String idDocRuta[] = idDocumento.split("//");
                                                String idDocRutaFinal[] = idDocRuta[1].split(";");
                                                String idDoc[] = idDocRutaFinal[0].split("/");
                                                Archivo archivo = new Archivo();
                                                PropertyUtils.copyProperties(archivo, arc);
                                                archivo.setObjectId(idDoc[1]);
                                                
                                                if (idDoc[1] == null || idDoc[1].equals("")){
                                                  bandera = true;  
                                                  throw new Exception();  
                                                }
                                                archivoService.saveArchivo(archivo);
                                                sesionAlfresco.clear();
                                                sesionAlfresco = null;
                                        }catch(Exception e){
                                            e.printStackTrace();
                                            
                                            if (sesionAlfresco!=null){
                                                try{
                                                     sesionAlfresco.clear();
                                                     sesionAlfresco = null;
                                                }catch(Exception ex){
                                                     ex.printStackTrace();
                                                }
                                            }    
                                            
                                             bandera = true; 
                                             break;
                                        }        
                                        
                                        
				}else{
                                    bandera = true; 
                                    break; 
                                }
			}else{
				log.warn("Archivo \""+f.getAbsolutePath()+"\" no encontrado");
			}
		}
           
                if (bandera){
                    for(Archivo arc : listaArchivos){
                        Archivo archivo = new Archivo();
                        try{
                            PropertyUtils.copyProperties(archivo, arc);
                            archivo.setEstado('I');
                            archivoService.saveArchivo(archivo);
                        }catch(Exception e){
                            e.printStackTrace();                                        
                        }
                    }
                }
	}

   public String obtenerRutaExpediente(Expediente objExpediente) {
      String sFecha = new SimpleDateFormat("yyyy/MM/dd").format(objExpediente.getFechacreacion());

      return RUTA_PADRE_EXPEDIENTE + Constantes.GENERAL_SLASH + sFecha + Constantes.GENERAL_SLASH + objExpediente.getNroexpediente() + Constantes.GENERAL_SLASH;
   }

   public String obtenerRutaCompletaExpediente(Expediente objExpediente) {
      String sFecha = new SimpleDateFormat("yyyy/MM/dd").format(objExpediente.getFechacreacion());

      return COMPLETE_PATH_PADRE_EXPEDIENTE + sFecha + Constantes.GENERAL_SLASH + objExpediente.getNroexpediente() + Constantes.GENERAL_SLASH;
   }

   //Expedientes_OSITRAN
   public String obtenerRutaDocumento(Documento objDocumento, String siglaSite, String tipoDocumento) {
      String ano = new SimpleDateFormat("yyyy").format(objDocumento.getFechaDocumento());
      return siglaSite + Constantes.GENERAL_SLASH + ano + Constantes.GENERAL_SLASH + tipoDocumento + Constantes.GENERAL_SLASH; 
   }

   public String obtenerRutaCompletaDocumento(Documento objDocumento, String siglaUnidad, String tipoDocumento) {
      String ano = new SimpleDateFormat("yyyy").format(objDocumento.getFechaDocumento());
     
      return COMPLETE_PATH_PADRE_EXPEDIENTE + siglaUnidad + Constantes.GENERAL_SLASH + ano + Constantes.GENERAL_SLASH + tipoDocumento + Constantes.GENERAL_SLASH; //+ mes + Constantes.GENERAL_SLASH; JC 
   }

	/**
	 * @return the expedienteDAO
	 */
	public ExpedienteDAO getExpedienteDAO(){
		return expedienteDAO;
	}

	/**
	 * @param expedienteDAO
	 *            the expedienteDAO to set
	 */
	public void setExpedienteDAO(ExpedienteDAO expedienteDAO){
		this.expedienteDAO=expedienteDAO;
	}

	/**
	 * @return the documentoDAO
	 */
	public DocumentoDAO getDocumentoDAO(){
		return documentoDAO;
	}

	/**
	 * @param documentoDAO
	 *            the documentoDAO to set
	 */
	public void setDocumentoDAO(DocumentoDAO documentoDAO){
		this.documentoDAO=documentoDAO;
	}

	/**
	 * @return the archivoDAO
	 */
	public ArchivoDAO getArchivoDAO(){
		return archivoDAO;
	}

	/**
	 * @param archivoDAO
	 *            the archivoDAO to set
	 */
	public void setArchivoDAO(ArchivoDAO archivoDAO){
		this.archivoDAO=archivoDAO;
	}

	@Override
	public Map<String,String> obtenerMetadata(Documento doc, Usuario usuario){
		Map<String,String> propiedades=new HashMap<String,String>();
		propiedades.put(Constants.createQNameString(AlfrescoWSService.NAMESPACE_OSINERG_MODEL,"idDocumento"),doc.getIdDocumento().toString());
                
                if (doc.getTipoDocumento().getNombre()!=null){
                    propiedades.put(Constants.createQNameString(AlfrescoWSService.NAMESPACE_OSINERG_MODEL,"tipoDocumento"),doc.getTipoDocumento().getNombre());
                }
		if (doc.getNumeroDocumento()!=null){
                    propiedades.put(Constants.createQNameString(AlfrescoWSService.NAMESPACE_OSINERG_MODEL,"numDocumento"),doc.getNumeroDocumento());
                }
               
                propiedades.put(Constants.createQNameString(AlfrescoWSService.NAMESPACE_OSINERG_MODEL, "numExpediente"), doc.getExpediente().getNroexpediente());
                propiedades.put(Constants.createQNameString(AlfrescoWSService.NAMESPACE_OSINERG_MODEL, "numTramite"), doc.getID_CODIGO().toString());
               
                propiedades.put(Constants.createQNameString(AlfrescoWSService.NAMESPACE_OSINERG_MODEL, "usuario"), usuario.getUsuario()); 
                propiedades.put(Constants.createQNameString(AlfrescoWSService.NAMESPACE_OSINERG_MODEL, "confidencial"), doc.getConfidencial().toString()); 
                
                try{
                        propiedades.put(Constants.createQNameString(AlfrescoWSService.NAMESPACE_OSINERG_MODEL,"fechaCreacionDocumento"),RepositorioUtils.darFormatoFecha(doc.getFechaCreacion()));
                }catch(Exception ex){
                        log.error(ex.getMessage(),ex);
                }
                
		return propiedades;
	}


	// TODO revisar esto cuando se obtengan los usuarios por AD
	private Unidad getUnidad(Proceso proc){
		return proc.getResponsable().getUnidad();
	}

	// TODO revisar esto cuando se obtengan los usuarios por AD
	private Usuario getUsuario(Proceso proc){
		return proc.getResponsable();
	}

	public List<EnlaceDocumento> busquedaArchivos(String textoABuscar,Map<String,String> propiedades){
		return alfrescoWebServiceClient.buscarContenidos(textoABuscar,propiedades);
	}

	public List<Integer> busquedaIdDocumentos(String textoABuscar,Map<String,String> propiedades){
		return alfrescoWebServiceClient.buscarIdDocumentos(textoABuscar,propiedades);
	}

	public void setArchivoService(ArchivoService archivoService){
		this.archivoService=archivoService;
	}

   public boolean existeNodo(String sUsuario, String sPassword, String sRutaNodo) throws Exception{
	   return alfrescoConnector.nodeExists(sUsuario, seguridadService.desencriptar(sUsuario, sPassword), sRutaNodo);
   }

   public boolean crearFolder(String sUsuario, String sPassword, String sRuta, String sFolder) {
      try {
          
         StringBuilder sbRutaFolder = new StringBuilder(sRuta).append(sFolder);
         
         
         if (!this.existeNodo(sUsuario, sPassword, sbRutaFolder.toString())) {
            alfrescoConnector.createDirectory(sUsuario, seguridadService.desencriptar(sUsuario, sPassword), sRuta, sFolder);
            
         }

         return true;
         
      } catch (Exception e) {
         log.error(e.getMessage(), e);
      }
         return false;
     
   }

   public boolean moverNodo(String sUsuario, String sPassword, String sNodo, String sRutaOrigen, String sRutaDestino) {
      try {
         StringBuilder sbRutaNodo = new StringBuilder(sRutaOrigen).append(sNodo);

         if (this.existeNodo(sUsuario, sPassword, sbRutaNodo.toString())) {
            alfrescoConnector.moveNode(sUsuario, seguridadService.desencriptar(sUsuario, sPassword), sbRutaNodo.toString(), sRutaDestino);

            return true;
         }

         return false;
      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return false;
      }
   }

   @Override
   public boolean eliminarNodo(String usuario, String password, String rutaNodo) {
 
	   return true;
   }

    public AlfrescoWSService getAlfrescoWebServiceClient() {
        return alfrescoWebServiceClient;
    }

    public void setAlfrescoWebServiceClient(AlfrescoWSService alfrescoWebServiceClient) {
        this.alfrescoWebServiceClient = alfrescoWebServiceClient;
    }

    public void setSeguridadService(SeguridadService seguridadService) {
        this.seguridadService = seguridadService;
    }
}
