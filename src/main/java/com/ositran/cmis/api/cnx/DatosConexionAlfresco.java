/*LICENCIA DE USO DEL SGD .TXT*/package com.ositran.cmis.api.cnx;

import gob.ositran.siged.config.SigedProperties;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

public class DatosConexionAlfresco {

    String alfrescoHostPublico = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_HOST);
    String alfrescoHostPort = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PORT);
    String alfrescoProtocolo = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PROTOCOLO);
    //protected String urlCmisRest     = alfrescoProtocolo+"://"+alfrescoHostPublico+":"+alfrescoHostPort+"/alfresco/cmisatom";
    protected String urlCmisRest     = alfrescoProtocolo+"://"+alfrescoHostPublico+":"+alfrescoHostPort+"/alfresco/cmisws/cmis";

	protected String usuarioAlfresco = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_USUARIOADMIN);
	protected String claveAlfresco   = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_USUARIOADMIN_CLAVE);

	
	protected Map<String, String> getParametrosConexionWsAlfresco(String repositorioId){
		Map<String, String> parameter = new HashMap<String, String>();

                parameter.put(SessionParameter.USER, usuarioAlfresco);
		parameter.put(SessionParameter.PASSWORD, claveAlfresco);

		// connection settings
                parameter.put(SessionParameter.ATOMPUB_URL, urlCmisRest);
                parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
           
                if( repositorioId!=null && !"".equals(repositorioId) ){
                    parameter.put(SessionParameter.REPOSITORY_ID, repositorioId);
                }
		
		return parameter;
	}
        
}
