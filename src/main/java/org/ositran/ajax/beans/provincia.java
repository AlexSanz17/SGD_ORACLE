/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import org.apache.commons.lang.builder.ToStringBuilder;

public class provincia {

	private String idprovincia;
	private String provincia;
	
	public String getIdprovincia() {
		return idprovincia;
	}
	public void setIdprovincia(String idprovincia) {
		this.idprovincia = idprovincia;
	}
	
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public String toString() 
	{
	  return new ToStringBuilder(this).append("idprovincia", idprovincia).append("provincia", provincia).toString();
	}		
	
}
