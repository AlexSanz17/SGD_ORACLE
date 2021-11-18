package org.ositran.utils;

import gob.ositran.siged.config.SigedProperties;
import java.io.File;
import org.apache.log4j.Logger;

public class ArchivoTemporal {

   private static Logger _log = Logger.getLogger(ArchivoTemporal.class);
   private File fArchivo;
   private String sNombre;
   private String sURL;
   private String sRutaAlfresco;
   private String extension;
   private String principal;

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

   /*
    * Constructors
    */
   public ArchivoTemporal(String sNombre, File fArchivo) {
      setSNombre(sNombre);
      setFArchivo(fArchivo);
   }
   
   public ArchivoTemporal(String sNombre, File fArchivo, String principal) {
      setSNombre(sNombre);
      setFArchivo(fArchivo);
      this.principal = principal;
   }

   public ArchivoTemporal(String sNombre, String sRutaAlfresco) {
      this.sNombre = sNombre;
      this.sRutaAlfresco = sRutaAlfresco;
   }
   
   public ArchivoTemporal(String sNombre, String sRutaAlfresco, String extension) {
	      this.sNombre = sNombre;
	      this.sRutaAlfresco = sRutaAlfresco;
	      this.extension = extension;
	   }

   /*
    * Methods
    */
   @Override
   protected void finalize() throws Throwable {
      super.finalize();

      if (fArchivo == null) {
         _log.debug("fArchivo es NULL");
      }

      if (fArchivo != null && fArchivo.exists() && fArchivo.isFile()) {
         _log.debug("Datos del archivo a eliminar");
         _log.debug("Ruta [" + fArchivo.getParent() + "] Nombre [" + fArchivo.getName() + "]");
         _log.debug("Existe [" + fArchivo.exists() + "] Es archivo [" + fArchivo.isFile() + "]");
         _log.debug("Carpeta Tempo [" + SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.UPLOAD_CARPETA_TEMPO) + "]");
         //Si existe el archivo borrarlo del directorio en caso el garbage colector pase por aqui

         String sPath1 = fArchivo.getParent() + fArchivo.separator;

         if (sPath1.equals(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.UPLOAD_CARPETA_TEMPO))) {
            fArchivo.delete();
         }
      }
   }

   /*
    * Getters & Setters
    */
   public File getFArchivo() {
      return fArchivo;
   }

   public void setFArchivo(File fArchivo) {
      this.fArchivo = fArchivo;
   }

   public String getSNombre() {
      return sNombre;
   }

   public void setSNombre(String sNombre) {
      this.sNombre = sNombre;
   }

   public String getSURL() {
      return sURL;
   }

   public void setSURL(String sURL) {
      this.sURL = sURL;
   }

   public String getsRutaAlfresco() {
      return sRutaAlfresco;
   }

   public void setsRutaAlfresco(String sRutaAlfresco) {
      this.sRutaAlfresco = sRutaAlfresco;
   }

public String getExtension() {
	return extension;
}

public void setExtension(String extension) {
	this.extension = extension;
}
   
   
}
