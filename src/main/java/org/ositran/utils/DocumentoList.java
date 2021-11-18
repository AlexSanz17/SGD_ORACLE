package org.ositran.utils;

import java.util.Date;

import com.btg.ositran.siged.domain.Expediente;

public class DocumentoList {

   private Date strFecha;
   private Integer iIdDocumento;
   private String strAsunto;
   private String strCorrentista;
   private String strNroDocumento;
   private String strRemitente;
   private Expediente expediente;
   private String sAccion;

   public DocumentoList() {
   }

   public DocumentoList(Integer iId, String strA, String strND) {
      setIIdDocumento(iId);
      setStrAsunto(strA);
      setStrNroDocumento(strND);
   }

   public Date getStrFecha() {
      return strFecha;
   }

   public void setStrFecha(Date strFecha) {
      this.strFecha = strFecha;
   }

   public Integer getIIdDocumento() {
      return iIdDocumento;
   }

   public void setIIdDocumento(Integer iIdDocumento) {
      this.iIdDocumento = iIdDocumento;
   }

   public String getStrAsunto() {
      return strAsunto;
   }

   public void setStrAsunto(String strAsunto) {
      this.strAsunto = strAsunto;
   }

   public String getStrCorrentista() {
      return strCorrentista;
   }

   public void setStrCorrentista(String strCorrentista) {
      this.strCorrentista = strCorrentista;
   }

   public String getStrNroDocumento() {
      return strNroDocumento;
   }

   public void setStrNroDocumento(String strNroDocumento) {
      this.strNroDocumento = strNroDocumento;
   }

   public String getStrRemitente() {
      return strRemitente;
   }

   public void setStrRemitente(String strRemitente) {
      this.strRemitente = strRemitente;
   }

    /**
     * @return the expediente
     */
    public Expediente getExpediente() {
        return expediente;
    }

    /**
     * @param expediente the expediente to set
     */
    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }

   public String getSAccion() {
      return sAccion;
   }

   public void setSAccion(String sAccion) {
      this.sAccion = sAccion;
   }

   
}
