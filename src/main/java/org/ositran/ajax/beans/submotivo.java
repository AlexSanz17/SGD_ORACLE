/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import org.apache.commons.lang.builder.ToStringBuilder;

public class submotivo {

   private String idsubmotivo;
   private String nsubmotivo;

   public String getIdsubmotivo() {
      return idsubmotivo;
   }

   public void setIdsubmotivo(String idsubmotivo) {
      this.idsubmotivo = idsubmotivo;
   }

   public String toString() {
      return new ToStringBuilder(this).append("idsubmotivo", getIdsubmotivo()).append("nsubmotivo", getNsubmotivo()).toString();
   }

   public String getNsubmotivo() {
      return nsubmotivo;
   }

   public void setNsubmotivo(String nsubmotivo) {
      this.nsubmotivo = nsubmotivo;
   }
}
