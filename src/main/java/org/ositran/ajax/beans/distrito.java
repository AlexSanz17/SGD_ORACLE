/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import org.apache.commons.lang.builder.ToStringBuilder;

public class distrito {

	private String iddistrito;
	private String distrito;
	
	public String getIddistrito() {
		return iddistrito;
	}
	public void setIddistrito(String iddistrito) {
		this.iddistrito = iddistrito;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	
	public String toString() 
	{
	  return new ToStringBuilder(this).append("iddistrito", iddistrito).append("distrito", distrito).toString();
	}		
	
}
