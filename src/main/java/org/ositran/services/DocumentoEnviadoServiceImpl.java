/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.daos.AuditoriaDAO;
import org.ositran.daos.DocumentoEnviadoDAO;
import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Documentoenviado;
import com.btg.ositran.siged.domain.FilaBandejaEnviados;
import com.btg.ositran.siged.domain.Usuario;

public class DocumentoEnviadoServiceImpl implements DocumentoEnviadoService {

	DocumentoEnviadoDAO dao;
	private AuditoriaDAO daoauditoria;
	
	private static Logger log = Logger.getLogger(DocumentoEnviadoServiceImpl.class);
	
	public DocumentoEnviadoDAO getDao() {
		return dao;
	}
	public void setDao(DocumentoEnviadoDAO dao) {
		this.dao = dao;
	}

	public List<Documentoenviado> findActivosByUsuario(Integer idusuario) {
		return getDao().findByUsuario(idusuario , ""+Constantes.ESTADO_ACTIVO) ;   
	}
	
	public List<Documentoenviado> findActivosFiltrados(Integer idusuario, BusquedaAvanzada objFiltro){
		return dao.findActivosFiltrados(idusuario, objFiltro);
	}
	
	public Documentoenviado findByIddocumentoenviado(Integer Iddocumentoenviado) {
		return getDao().findByIddocumentoenviado(Iddocumentoenviado) ;  
	}
        
         public List<Usuario> buscarListDestinatarios(Usuario objUsuario){
                return getDao().buscarListDestinatarios(objUsuario);
         }

        @Transactional
	public void  eliminarDocumentosEnviados (String [] ids) {
		
		for(int i =0 ; i<ids.length ; i++ ){
			log.debug(" id"+i+":"+ids[i]) ; 
			Documentoenviado doc = getDao().findByIddocumentoenviado(new Integer(ids[i]));
			doc.setEstado(""+Constantes.ESTADO_INACTIVO) ;

			this.savedocumentoenviado(doc);
			
		}   
	}
	
	@Transactional
	public void savedocumentoenviado(Documentoenviado objdocenviado){
		
		getDao().saveDocumento(objdocenviado);
		
	}
	
	public List<FilaBandejaEnviados> buscarEnviadosPorUsuario(Usuario  objUsuario){
		return dao.buscarEnviadosPorUsuario(objUsuario);
	}
	
    public List<FilaBandejaEnviados> buscarEnviadosFiltrados(Usuario objusuario, BusquedaAvanzada objFiltro){
    	return dao.buscarEnviadosFiltrados(objusuario, objFiltro);
    }
	
	public void setDaoauditoria(AuditoriaDAO daoauditoria) {
		this.daoauditoria = daoauditoria;
	}
	
	public AuditoriaDAO getDaoauditoria() {
		return daoauditoria;
	}	

}
