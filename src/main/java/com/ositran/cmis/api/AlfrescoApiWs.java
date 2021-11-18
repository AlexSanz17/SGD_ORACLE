/*LICENCIA DE USO DEL SGD .TXT*/package com.ositran.cmis.api;


import com.ositran.cmis.api.cnx.DatosConexionAlfresco;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;

import java.util.List;
import org.apache.chemistry.opencmis.client.api.Repository;

public class AlfrescoApiWs extends DatosConexionAlfresco {

	private String repositorioId;
	
	public AlfrescoApiWs(String urlAlfresco, String usuarioAlfresco, String claveAlfresco, String repositorioId) {
		super.urlCmisRest     = urlAlfresco;
		super.usuarioAlfresco = usuarioAlfresco;
		super.claveAlfresco   = claveAlfresco;
		this.repositorioId    = repositorioId;
	}
        
        public AlfrescoApiWs(String urlAlfresco, String usuarioAlfresco, String claveAlfresco) {
		super.urlCmisRest     = urlAlfresco;
		super.usuarioAlfresco = usuarioAlfresco;
		super.claveAlfresco   = claveAlfresco;
	}
	
	public Session getSessionAlfresco() throws Exception{
		try {
			SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
			Session session = sessionFactory.createSession(getParametrosConexionWsAlfresco(repositorioId));
			return session;
		} catch (Exception excepcion) {
			throw new Exception("Ocurrio un error al obtener la sesion de alfresco", excepcion);
		}
	}
        
        public List<Repository> getRepositoriosAlfresco() throws Exception{
            try {
			SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
                        return sessionFactory.getRepositories(getParametrosConexionWsAlfresco(null));
		} catch (Exception excepcion) {
			throw new Exception("Ocurri�n un error al obtener la lista de repositorios", excepcion);
		}
        }
	
	
	
}
