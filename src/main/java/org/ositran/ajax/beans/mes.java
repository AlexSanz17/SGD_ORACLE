/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class mes implements Serializable {

	private String idmes;
	private String mes;
	
    
    public String toString()
	{
	  return new ToStringBuilder(this).append("idmes",getMes()).append("mes",getMes()).toString();
	}

    public String getIdmes() {
        return idmes;
    }

    public void setIdmes(String idmes) {
        this.idmes = idmes;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
	
}
