/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import org.apache.commons.lang.builder.ToStringBuilder;

public class analista {

   private String idanalista;
   private String analista;

   public String toString() {
      return new ToStringBuilder(this).append("idanalista", getIdanalista()).append("analista", getAnalista()).toString();
   }

   public String getIdanalista() {
      return idanalista;
   }

   public void setIdanalista(String idanalista) {
      this.idanalista = idanalista;
   }

   public String getAnalista() {
      return analista;
   }

   public void setAnalista(String analista) {
      this.analista = analista;
   }
}
