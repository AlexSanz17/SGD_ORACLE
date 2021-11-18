/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import org.apache.log4j.Logger;
import org.ositran.services.CarpetaService;
import org.ositran.services.DocumentoService;

import com.btg.ositran.siged.domain.Carpeta;
import com.btg.ositran.siged.domain.Expediente;

public class CarpetaAction {
	
	CarpetaService carpetaService ;
	DocumentoService documentoService ;
    Integer idDocumento ;
    Integer id ; 
    String tipo ; 
    Integer idDocumentoPrincipal  ; 
	Carpeta carpeta ; 
	String cerrar ;
	String mensaje ;
	Integer idCarpeta ; 
	String nombre  ; 
 	
	private static Logger log =  Logger.getLogger(CarpetaAction.class) ;
	public Integer getIdCarpeta() {
		return idCarpeta;
	}

	public void setIdCarpeta(Integer idCarpeta) {
		this.idCarpeta = idCarpeta;
	}

	public DocumentoService getDocumentoService() {
		return documentoService;
	}

	public void setDocumentoService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	public String getCerrar() {
		return cerrar;
	}

	public void setCerrar(String cerrar) {
		this.cerrar = cerrar;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Integer getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	public Carpeta getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(Carpeta carpeta) {
		this.carpeta = carpeta;
	}

	public CarpetaService getCarpetaService() {
		return carpetaService;
	}

	public void setCarpetaService(CarpetaService carpetaService) {
		this.carpetaService = carpetaService;
	}
	public Integer getIdDocumentoPrincipal() {
		return idDocumentoPrincipal;
	}

	public void setIdDocumentoPrincipal(Integer idDocumentoPrincipal) {
		this.idDocumentoPrincipal = idDocumentoPrincipal;
	}
	
	
	
	public String verNueva (){
		// this.mensaje="hola" ;  
		Expediente exp = documentoService.findByIdDocumento(this.idDocumento).getExpediente() ;   
		Carpeta c = new Carpeta() ;
		c.setIdexpediente(exp);
        this.setCarpeta(c) ;   
        
        if(this.tipo.equalsIgnoreCase("E")){
        	this.nombre = exp.getNroexpediente() ;
        	
        }else if (this.tipo.equalsIgnoreCase("C")) {
			Carpeta c2  = carpetaService.findByIdcarpeta(id); 
			this.nombre =c2.getNombre() ;
			c.setCarpetapadre(c2);  
			
		} 
		return "nueva" ; 
	}
	
	public String verEdicion (){
		// this.mensaje="hola" ;  
		log.debug("id:"+id ) ;  
		Carpeta c = carpetaService.findByIdcarpeta(this.id) ; 
		
		//c.setIdexpediente(exp);
        this.setCarpeta(c) ;   
        if(c.getCarpetapadre()==null){
        	  this.nombre =c.getIdexpediente().getNroexpediente() ;  
        }else {
            this.nombre =c.getCarpetapadre().getNombre() ; 
		}

			
		
		return "editar" ;  
	}
	 
	public String guardar (){ 
		try {
		   
		   carpetaService.guardar(this.carpeta) ;
		   cerrar = "ok" ;   
		 }catch (Exception e){ 
			 log.error(e.getMessage(), e);
			 this.mensaje = e.getMessage() ;  
		 }
		     
		return "nueva" ;  
	}
	
	public String actualizar (){ 
		try {
		   
		   carpetaService.guardar(this.carpeta) ;
		   cerrar = "ok" ;      
		 }catch (Exception e){ 
			 log.error(e.getMessage(), e);
			 this.mensaje = e.getMessage() ;  
		 }
		     
		return "editar" ;   
	}
	
	public String eliminar (){ 
		try {
		   
		   carpetaService.deleteCarpeta(this.carpeta) ;
		   cerrar = "ok" ;      
		 }catch (Exception e){ 
			 log.error(e.getMessage(), e);
			 this.mensaje = e.getMessage() ;  
		 }
		     
		return "editar" ;   
	}
	
	public String moverDocumento (){ 
		
		try {
		   
		   carpetaService.moverDocumento(getIdDocumento(),getIdCarpeta()) ;
		   
		   cerrar = "movido" ;   
		     
		 }catch (Exception e){ 
			 
			log.error(e.getMessage(), e);
			this.mensaje = e.getMessage() ;
			 
		 }
		  
		return "documentosAdicionales" ;  
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

}
