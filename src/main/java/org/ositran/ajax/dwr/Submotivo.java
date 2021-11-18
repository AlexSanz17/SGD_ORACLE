/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.dwr;

public class Submotivo {

   private int id;
   private Integer idmotivo;
   private String nmotivo;
   private Integer idsubmotivo;
   private String nsubmotivo;

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }

      if (obj == this) {
         return true;
      }

      if (!this.getClass().equals(obj.getClass())) {
         return false;
      }

      Submotivo that = (Submotivo) obj;

      if (this.getId() != that.getId()) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode() {
      return 5924 + getId();
   }

   @Override
   public String toString() {
      return "Submotivo[id=" + getId() + ",sSubMotivo=" + getNsubmotivo() + "]";
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public Integer getIdmotivo() {
      return idmotivo;
   }

   public void setIdmotivo(Integer idmotivo) {
      this.idmotivo = idmotivo;
   }

   public String getNmotivo() {
      return nmotivo;
   }

   public void setNmotivo(String nmotivo) {
      this.nmotivo = nmotivo;
   }

   public Integer getIdsubmotivo() {
      return idsubmotivo;
   }

   public void setIdsubmotivo(Integer idsubmotivo) {
      this.idsubmotivo = idsubmotivo;
   }

   public String getNsubmotivo() {
      return nsubmotivo;
   }

   public void setNsubmotivo(String nsubmotivo) {
      this.nsubmotivo = nsubmotivo;
   }
}
