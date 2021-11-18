/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.dojo;

public class Recurso {

   private String recurso;
   private Integer valor;

   /*
    * Constructors
    */
   public Recurso() {
   }

   public Recurso(String sRecurso, Integer iValor) {
      this.recurso = sRecurso;
      this.valor = iValor;
   }

   /*
    * Getters & Setters
    */
   public String getRecurso() {
      return recurso;
   }

   public void setRecurso(String recurso) {
      this.recurso = recurso;
   }

   public Integer getValor() {
      return valor;
   }

   public void setValor(Integer valor) {
      this.valor = valor;
   }
}
