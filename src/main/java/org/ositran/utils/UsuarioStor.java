/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ositran.utils;

/**
 *
 * @author Usuario
 */
public class UsuarioStor {

   private Integer idusuario;
   private Integer rol;
   private String nombres;
   private String apellidos;
   private Integer sala;
   private Integer nroapelaciones;
   private Integer nroquejas;
   private Integer nrocautelares;

   public UsuarioStor() {
   }



   public UsuarioStor(Integer idusuario, Integer rol, String nombres, String apellidos, Integer sala, Integer nroapelaciones, Integer nroquejas, Integer nrocautelares) {
      this.idusuario = idusuario;
      this.rol = rol;
      this.nombres = nombres;
      this.apellidos = apellidos;
      this.sala = sala;
      this.nroapelaciones = nroapelaciones;
      this.nroquejas = nroquejas;
      this.nrocautelares = nrocautelares;
   }



   public String getApellidos() {
      return apellidos;
   }

   public void setApellidos(String apellidos) {
      this.apellidos = apellidos;
   }

   public Integer getIdusuario() {
      return idusuario;
   }

   public void setIdusuario(Integer idusuario) {
      this.idusuario = idusuario;
   }

   public String getNombres() {
      return nombres;
   }

   public void setNombres(String nombres) {
      this.nombres = nombres;
   }

   public Integer getNroapelaciones() {
      return nroapelaciones;
   }

   public void setNroapelaciones(Integer nroapelaciones) {
      this.nroapelaciones = nroapelaciones;
   }

   public Integer getNrocautelares() {
      return nrocautelares;
   }

   public void setNrocautelares(Integer nrocautelares) {
      this.nrocautelares = nrocautelares;
   }

   public Integer getNroquejas() {
      return nroquejas;
   }

   public void setNroquejas(Integer nroquejas) {
      this.nroquejas = nroquejas;
   }

   public Integer getRol() {
      return rol;
   }

   public void setRol(Integer rol) {
      this.rol = rol;
   }

   public Integer getSala() {
      return sala;
   }

   public void setSala(Integer sala) {
      this.sala = sala;
   }  


}
