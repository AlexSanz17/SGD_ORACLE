/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class tipoidentificacion implements Serializable {

	private String idtipoidentificacion;
	private String tipoidentificacion;
	
	public String getIdtipoidentificacion() {
		return idtipoidentificacion;
	}
	public void setIdtipoidentificacion(String idtipoidentificacion) {
		this.idtipoidentificacion = idtipoidentificacion;
	}
	public String gettipoidentificacion() {
		return tipoidentificacion;
	}
	public void settipoidentificacion(String tipoidentificacion) {
		this.tipoidentificacion = tipoidentificacion;
	}
	
	public String toString() 
	{
	  return new ToStringBuilder(this).append("idtipoidentificacion", idtipoidentificacion).append("tipoidentificacion", tipoidentificacion).toString();
	}	
	
}
