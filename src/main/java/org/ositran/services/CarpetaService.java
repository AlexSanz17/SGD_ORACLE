/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import com.btg.ositran.siged.domain.Carpeta;
import com.btg.ositran.siged.domain.Documento;

public interface CarpetaService {
	
	public Carpeta guardar (Carpeta carpeta) ; 
	public Documento moverDocumento (Integer idDocumento , Integer idCarpeta) ;  
	public Carpeta findByIdcarpeta (Integer IdCarpeta) ;  
	public void deleteCarpeta(Carpeta carpeta) ; 
}
