/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class proceso implements Serializable {

	private String idproceso;
	private String proceso;
	
	public String getIdproceso() {
		return idproceso;
	}
	public void setIdproceso(String idproceso) {
		this.idproceso = idproceso;
	}
	public String getproceso() {
		return proceso;
	}
	public void setproceso(String proceso) {
		this.proceso = proceso;
	}
	
	public String toString() 
	{
	  return new ToStringBuilder(this).append("idproceso", idproceso).append("proceso", proceso).toString();
	}	
	
}
