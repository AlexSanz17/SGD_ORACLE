/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import org.apache.commons.lang.builder.ToStringBuilder;

public class motivo {

   private String idmotivo;
   private String nmotivo;

   public String getIdmotivo() {
      return idmotivo;
   }

   public void setIdmotivo(String idmotivo) {
      this.idmotivo = idmotivo;
   }

   public String toString() {
      return new ToStringBuilder(this).append("idmotivo", getIdmotivo()).append("nmotivo", getNmotivo()).toString();
   }

   public String getNmotivo() {
      return nmotivo;
   }

   public void setNmotivo(String nmotivo) {
      this.nmotivo = nmotivo;
   }
}
