/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.pojos;

import java.io.Serializable;
import java.util.List;

import org.ositran.dojo.ObjetoJSON;
import org.ositran.dojo.grid.Estructura;

public class Grilla implements Serializable{
	private static final long serialVersionUID=1L;
	 List<Estructura> estructura ;
	 ObjetoJSON data ;
	 
	  
	public List<Estructura> getEstructura() {
		return estructura;
	}
	public void setEstructura(List<Estructura> estructura) {
		this.estructura = estructura;
	}
	public ObjetoJSON getData() {
		return data;
	}
	public void setData(ObjetoJSON data) {
		this.data = data;
	}
	 
	 

}
