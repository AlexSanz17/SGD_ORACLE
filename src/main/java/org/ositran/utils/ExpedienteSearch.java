package org.ositran.utils;

public class ExpedienteSearch {

   private Integer iIdTipoDocumento;
   private String strAsunto;
   private String strNroIdentificacion;
   private String strRazonSocial;
   private String sNroExpediente;
   private String sNroCaja;
   private String sNroDocumento;

   //<-- busqueda avanzada
   private String strNrodocu;

   public ExpedienteSearch() {
   }

   public ExpedienteSearch(String strA, String strNI, String strRS, String strR) {
      setStrAsunto(strA);
      setStrNroIdentificacion(strNI);
      setStrRazonSocial(strRS);
      setSNroExpediente(strR);
   }

   ////////////////////////
   // Getters and Setters
   ////////////////////////
   public String getStrAsunto() {
      return strAsunto;
   }

   public void setStrAsunto(String strAsunto) {
      this.strAsunto = strAsunto;
   }

   public String getStrNroIdentificacion() {
      return strNroIdentificacion;
   }

   public void setStrNroIdentificacion(String strNroIdentificacion) {
      this.strNroIdentificacion = strNroIdentificacion;
   }

   public String getStrRazonSocial() {
      return strRazonSocial;
   }

   public void setStrRazonSocial(String strRazonSocial) {
      this.strRazonSocial = strRazonSocial;
   }

   public String getSNroExpediente() {
      return sNroExpediente;
   }

   public void setSNroExpediente(String sNroExpediente) {
      this.sNroExpediente = sNroExpediente;
   }

   public String getStrNrodocu() {
      return strNrodocu;
   }

   public void setStrNrodocu(String strNrodocu) {
      this.strNrodocu = strNrodocu;
   }

   public Integer getIIdTipoDocumento() {
      return iIdTipoDocumento;
   }

   public void setIIdTipoDocumento(Integer iIdTipoDocumento) {
      this.iIdTipoDocumento = iIdTipoDocumento;
   }

   public String getSNroCaja() {
      return sNroCaja;
   }

   public void setSNroCaja(String sNroCaja) {
      this.sNroCaja = sNroCaja;
   }

   public String getSNroDocumento() {
      return sNroDocumento;
   }

   public void setSNroDocumento(String sNroDocumento) {
      this.sNroDocumento = sNroDocumento;
   }
}
