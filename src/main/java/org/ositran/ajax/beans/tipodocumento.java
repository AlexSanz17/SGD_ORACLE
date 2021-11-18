/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class tipodocumento implements Serializable {

	private String idtipodocumento;
	private String tipodocumento;
	
	public String getIdtipodocumento() {
		return idtipodocumento;
	}
	public void setIdtipodocumento(String idtipodocumento) {
		this.idtipodocumento = idtipodocumento;
	}
	public String gettipodocumento() {
		return tipodocumento;
	}
	public void settipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}
	
	public String toString() 
	{
	  return new ToStringBuilder(this).append("idtipodocumento", idtipodocumento).append("tipodocumento", tipodocumento).toString();
	}	
	
}
