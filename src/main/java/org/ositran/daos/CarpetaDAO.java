/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Carpeta;

public interface CarpetaDAO {
	
	 public List<Carpeta> findbyDocumento (Integer IdDocumento ) ; 
	 
	   public Carpeta saveCarpeta(Carpeta carpeta) ; 
	   public Carpeta findByIdcarpeta (Integer IdCarpeta); 
	   public void  deleteCarpeta(Carpeta carpeta) ; 

}
