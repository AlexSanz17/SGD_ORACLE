/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class solicitante implements Serializable {

	private String idsolicitante;
	private String solicitante;
   private String nroidentificacion;

	public String getIdsolicitante() {
		return idsolicitante;
	}
	public void setIdsolicitante(String idsolicitante) {
		this.idsolicitante = idsolicitante;
	}
	public String getsolicitante() {
		return solicitante;
	}
	public void setsolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

   public String getNroidentificacion() {
      return nroidentificacion;
   }

   public void setNroidentificacion(String nroidentificacion) {
      this.nroidentificacion = nroidentificacion;
   }
	
	public String toString() 
	{
	  return new ToStringBuilder(this).append("idsolicitante", idsolicitante).append("solicitante", solicitante).toString();
	}	
	
}
