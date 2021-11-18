package org.ositran.utils;

import java.util.Date;

public class ExpedienteList {
   private Date strFecha;
   private Integer iIdExpediente;
   private String strRazonSocial;
   private String strReferencia;
   private String strResponsable;
   private String strUnidad;

   public ExpedienteList() {
   }

   public ExpedienteList(Date strF, Integer iId, String strRS, String strRef, String strRes, String strU) {
      setStrFecha(strF);
      setIIdExpediente(iId);
      setStrRazonSocial(strRS);
      setStrReferencia(strRef);
      setStrResponsable(strRes);
      setStrUnidad(strU);
   }

   public Date getStrFecha() {
      return strFecha;
   }

   public void setStrFecha(Date strFecha) {
      this.strFecha = strFecha;
   }

   public Integer getIIdExpediente() {
      return iIdExpediente;
   }

   public void setIIdExpediente(Integer iIdExpediente) {
      this.iIdExpediente = iIdExpediente;
   }

   public String getStrRazonSocial() {
      return strRazonSocial;
   }

   public void setStrRazonSocial(String strRazonSocial) {
      this.strRazonSocial = strRazonSocial;
   }

   public String getStrReferencia() {
      return strReferencia;
   }

   public void setStrReferencia(String strReferencia) {
      this.strReferencia = strReferencia;
   }

   public String getStrResponsable() {
      return strResponsable;
   }

   public void setStrResponsable(String strResponsable) {
      this.strResponsable = strResponsable;
   }

   public String getStrUnidad() {
      return strUnidad;
   }

   public void setStrUnidad(String strUnidad) {
      this.strUnidad = strUnidad;
   }
}
