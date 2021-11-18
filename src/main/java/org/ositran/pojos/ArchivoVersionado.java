/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.pojos;

import java.io.Serializable;
import java.util.List;

import com.btg.ositran.siged.domain.Archivo;

public class ArchivoVersionado implements Serializable{

	private static final long serialVersionUID=1L;
	Archivo archivo;
	List<VersionView> versiones;

	public Archivo getArchivo(){
		return archivo;
	}

	public void setArchivo(Archivo archivo){
		this.archivo=archivo;
	}

	public List<VersionView> getVersiones(){
		return versiones;
	}

	public void setVersiones(List<VersionView> versiones){
		this.versiones=versiones;
	}
}
