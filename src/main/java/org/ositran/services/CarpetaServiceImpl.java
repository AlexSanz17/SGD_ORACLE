/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import com.btg.ositran.siged.domain.Auditoria;
import com.btg.ositran.siged.domain.Carpeta;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.ActionContext;
import java.util.Date;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.ositran.daos.AuditoriaDAO;
import org.ositran.daos.CarpetaDAO;
import org.ositran.daos.DocumentoDAO;
import org.ositran.utils.Constantes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.transaction.annotation.Transactional;

public class CarpetaServiceImpl implements CarpetaService {
	
	CarpetaDAO carpetaDAO ;
	DocumentoDAO documentoDAO ;
	
	 
	public CarpetaDAO getCarpetaDAO() {
		return carpetaDAO;
	}
	public void setCarpetaDAO(CarpetaDAO carpetaDAO) {
		this.carpetaDAO = carpetaDAO;
	}
	public DocumentoDAO getDocumentoDAO() {
		return documentoDAO;
	}
	public void setDocumentoDAO(DocumentoDAO documentoDAO) {
		this.documentoDAO = documentoDAO;
	} 
	
	
	@Transactional
	public Carpeta guardar (Carpeta carpeta){
		if(carpeta.getCarpetapadre().getIdcarpeta()==null){
			carpeta.setCarpetapadre(null);
		}
		carpeta.setFechacreacion(new Date()); 
		Carpeta tempCarpeta=getCarpetaDAO().saveCarpeta(carpeta);
		
		Map session = ActionContext.getContext().getSession();
        AuditoriaDAO daoauditoria = (AuditoriaDAO) WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getRequest().getSession().getServletContext()).getBean("auditoriaDAO");
        Usuario usuario=(Usuario) session.get(Constantes.SESSION_USUARIO); 
        
        Auditoria objAuditoriaCarpeta=new Auditoria();
        objAuditoriaCarpeta.setTipoAuditoria(Constantes.TA_RegistrarNuevaCarpeta);
        objAuditoriaCarpeta.setModulo(Constantes.TM_DocAdicionales);
        objAuditoriaCarpeta.setOpcion(Constantes.TO_Registrar);
        objAuditoriaCarpeta.setFechaAudioria(new Date());
        objAuditoriaCarpeta.setUsuario(usuario.getUsuario());        
        objAuditoriaCarpeta.setCampo("Nombre");
        objAuditoriaCarpeta.setNuevoValor(tempCarpeta.getNombre());
        objAuditoriaCarpeta.setExpediente(tempCarpeta.getIdexpediente());
        daoauditoria.SaveAuditoria(objAuditoriaCarpeta);
		
	 return  tempCarpeta;
		 
	}    
	 
	@Transactional
	public Documento moverDocumento (Integer idDocumento , Integer idCarpeta){
    	 	 
	 /*Documento  doc = documentoDAO.findByIdDocumento(idDocumento) ; 
	 
	 String tempDocCarpeta=doc.getIdcarpeta()==null ? null :doc.getIdcarpeta().getNombre();
	 if(idCarpeta.intValue()!=0){
	 Carpeta  carp = carpetaDAO.findByIdcarpeta(idCarpeta) ;
	 doc.setIdcarpeta(carp) ; 
	 }else {
		 doc.setIdcarpeta(null) ;   
	 }
	  
	 
	 Documento tempDoc = this.getDocumentoDAO().saveDocumento(doc); 
	 
	Map session = ActionContext.getContext().getSession();
    AuditoriaDAO daoauditoria = (AuditoriaDAO) WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getRequest().getSession().getServletContext()).getBean("auditoriaDAO");
    Usuario usuario=(Usuario) session.get(Constantes.SESSION_USUARIO); 
    
    Auditoria objAuditoriaCarpeta=new Auditoria();
    objAuditoriaCarpeta.setTipoAuditoria(Constantes.TA_MoverDocCarpeta);
    objAuditoriaCarpeta.setModulo(Constantes.TM_DocAdicionales);
    objAuditoriaCarpeta.setOpcion(Constantes.TO_Mover);
    objAuditoriaCarpeta.setFechaAudioria(new Date());
    objAuditoriaCarpeta.setUsuario(usuario.getUsuario());        
    objAuditoriaCarpeta.setCampo("Carpeta");
    objAuditoriaCarpeta.setAntiguoValor(doc.getIdcarpeta()==null ? "" :tempDocCarpeta); 
    objAuditoriaCarpeta.setNuevoValor(tempDoc.getIdcarpeta()!=null?tempDoc.getIdcarpeta().getNombre():tempDoc.getExpediente().getNroexpediente());
    objAuditoriaCarpeta.setExpediente(tempDoc.getExpediente());
    objAuditoriaCarpeta.setDocumento(tempDoc);
    daoauditoria.SaveAuditoria(objAuditoriaCarpeta);	 
 
	 return tempDoc;*/return null;   
	   
	
	}  
	
	public Carpeta findByIdcarpeta (Integer IdCarpeta){
		
		return this.getCarpetaDAO().findByIdcarpeta(IdCarpeta); 
	}
	
	@Transactional
	public void deleteCarpeta(Carpeta carpeta) { 
		
		 this.getCarpetaDAO().deleteCarpeta(carpeta); 
	}	

}