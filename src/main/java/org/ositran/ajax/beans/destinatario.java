/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class destinatario implements Serializable {

	private String iddestinatario;
	private String destinatario;
   private String idccdestinatario;
   private String ccdestinatario;
	
	public String getIddestinatario() {
		return iddestinatario;
	}
	public void setIddestinatario(String iddestinatario) {
		this.iddestinatario = iddestinatario;
	}
	public String getdestinatario() {
		return destinatario;
	}
	public void setdestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	
	public String toString() 
	{
	  return new ToStringBuilder(this).append("iddestinatario", iddestinatario).append("destinatario", destinatario).toString();
	}

   public String getCcdestinatario() {
      return ccdestinatario;
   }

   public void setCcdestinatario(String ccdestinatario) {
      this.ccdestinatario = ccdestinatario;
   }

   public String getIdccdestinatario() {
      return idccdestinatario;
   }

   public void setIdccdestinatario(String idccdestinatario) {
      this.idccdestinatario = idccdestinatario;
   }
	
}
