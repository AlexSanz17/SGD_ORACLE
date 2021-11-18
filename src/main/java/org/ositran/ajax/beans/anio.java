/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class anio implements Serializable {

	private String idanio;
	private String anio;
	
    
    
    public String toString()
	{
	  return new ToStringBuilder(this).append("idanio",getAnio()).append("anio",getAnio()).toString();
	}

    public String getIdanio() {
        return idanio;
    }

    public void setIdanio(String idanio) {
        this.idanio = idanio;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }
	
}
