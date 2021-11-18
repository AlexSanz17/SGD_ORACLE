/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class departamento implements Serializable {

	private String iddepartamento;
	private String departamento;
	
	public String getIddepartamento() {
		return iddepartamento;
	}
	public void setIddepartamento(String iddepartamento) {
		this.iddepartamento = iddepartamento;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
	public String toString() 
	{
	  return new ToStringBuilder(this).append("iddepartamento", iddepartamento).append("departamento", departamento).toString();
	}	
	
}
