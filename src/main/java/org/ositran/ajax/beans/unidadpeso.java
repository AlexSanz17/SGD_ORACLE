/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class unidadpeso implements Serializable {

	//private String idanio;
	//private String anio;
	
	private String idunidadpeso;
	private String nombreunidad;

    
    public String toString()
	{
	  return new ToStringBuilder(this).append("idunidadpeso",getNombreunidad()).append("nombreunidad",getNombreunidad()).toString();
	}


	public void setIdunidadpeso(String idunidadpeso) {
		this.idunidadpeso = idunidadpeso;
	}


	public String getIdunidadpeso() {
		return idunidadpeso;
	}


	public void setNombreunidad(String nombreunidad) {
		this.nombreunidad = nombreunidad;
	}


	public String getNombreunidad() {
		return nombreunidad;
	}


	
}
