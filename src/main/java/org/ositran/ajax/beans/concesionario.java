/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class concesionario implements Serializable {

	private String idconcesionario;
	private String concesionario;
	

	
	public String toString() 
	{
	  return new ToStringBuilder(this).append("idconcesionario", idconcesionario).append("concesionario", concesionario).toString();
	}

   public String getIdconcesionario() {
      return idconcesionario;
   }

   public void setIdconcesionario(String idconcesionario) {
      this.idconcesionario = idconcesionario;
   }

   public String getConcesionario() {
      return concesionario;
   }

   public void setConcesionario(String concesionario) {
      this.concesionario = concesionario;
   }
	
}
