package org.ositran.utils;

public class DestinatarioList {

   private String strNombres;

   public DestinatarioList() {
   }

   public DestinatarioList(String strN) {
      setStrNombres(strN);
   }

   public String getStrNombres() {
      return strNombres;
   }

   public void setStrNombres(String strNombres) {
      this.strNombres = strNombres;
   }
}
