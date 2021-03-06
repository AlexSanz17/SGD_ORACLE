/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.dwr;

import java.util.HashSet;
import java.util.Set;

public class Motivo {

   private Set<Submotivo> setmotivo = new HashSet<Submotivo>();
   private static int nextId = 1;

   public Motivo() {
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public Set<Submotivo> getAllMotivo() {
      return getSetmotivo();
   }

   private static synchronized int getNextId() {
      return nextId++;
   }

   public void addSubMotivo(Submotivo submotivo) {
      if (submotivo.getId() == -1) {
         submotivo.setId(getNextId());
      }

      getSetmotivo().remove(submotivo);
      getSetmotivo().add(submotivo);
   }

   public void deleteSubMotivo(Submotivo submotivo) {
      getSetmotivo().remove(submotivo);
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public Set<Submotivo> getSetmotivo() {
      return setmotivo;
   }

   public void setSetmotivo(Set<Submotivo> aSetmotivo) {
      setmotivo = aSetmotivo;
   }
}
