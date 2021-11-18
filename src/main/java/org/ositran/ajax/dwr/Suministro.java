/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.dwr;

public class Suministro {

   private int id;
   private String ssuministro;

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

      Suministro that = (Suministro) obj;

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
      return "Suministro[id=" + getId() + ",ssuministro=" + getSsuministro() + "]";
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

   public String getSsuministro() {
      return ssuministro;
   }

   public void setSsuministro(String ssuministro) {
      this.ssuministro = ssuministro;
   }
}
