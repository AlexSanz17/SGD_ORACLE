package org.ositran.utils;

import java.util.List;

public class DocumentoTemporal {

   private Integer iIdTipoDocumento;
   private Integer iNroFolios;
   private String sNroDocumento;
   private String sAsunto;
   private String sFechaDocumento;
   private List<ArchivoTemporal> lstArchivo;

   public DocumentoTemporal() {
   }

   public Integer getIIdTipoDocumento() {
      return iIdTipoDocumento;
   }

   public void setIIdTipoDocumento(Integer iIdTipoDocumento) {
      this.iIdTipoDocumento = iIdTipoDocumento;
   }

   public Integer getINroFolios() {
      return iNroFolios;
   }

   public void setINroFolios(Integer iNroFolios) {
      this.iNroFolios = iNroFolios;
   }

   public String getSNroDocumento() {
      return sNroDocumento;
   }

   public void setSNroDocumento(String sNroDocumento) {
      this.sNroDocumento = sNroDocumento;
   }

   public String getSAsunto() {
      return sAsunto;
   }

   public void setSAsunto(String sAsunto) {
      this.sAsunto = sAsunto;
   }

   public String getSFechaDocumento() {
      return sFechaDocumento;
   }

   public void setSFechaDocumento(String sFechaDocumento) {
      this.sFechaDocumento = sFechaDocumento;
   }

   public List<ArchivoTemporal> getLstArchivo() {
      return lstArchivo;
   }

   public void setLstArchivo(List<ArchivoTemporal> lstArchivo) {
      this.lstArchivo = lstArchivo;
   }
}
