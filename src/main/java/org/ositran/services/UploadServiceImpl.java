package org.ositran.services;

import gob.ositran.siged.config.SigedProperties;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.ositran.utils.ArchivoTemporal;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

public class UploadServiceImpl implements UploadService {

   private static Logger _log = Logger.getLogger(UploadServiceImpl.class);

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public ArchivoTemporal findFileTempoByName(List<ArchivoTemporal> lstArchivoTemporal, String sFileName) {
      int iBracket = -1;
      String sRealName = "";

      iBracket = sFileName.indexOf(Constantes.ARCHIVO_BRACKET_FIN);
      String aleatorio=sFileName.substring(0,sFileName.indexOf(Constantes.ARCHIVO_BRACKET_FIN));
      if (iBracket != -1) {
         sRealName = sFileName.substring(iBracket + 1);
      }
      
     for (ArchivoTemporal objArchivoTemporal : lstArchivoTemporal) {
    	   if(objArchivoTemporal.getFArchivo().getName().contains(aleatorio)){
    		   return objArchivoTemporal;
    	   }
      }

      _log.error("No se encontro el archivo con nombre [" + sFileName + "]");

      return null;
   }

   public ArchivoTemporal downloadFileTempo(Map<String,List<ArchivoTemporal>> mapUpload, Integer iIdUpload, String sFileName) {
      ArchivoTemporal objArchivoTemporalToDownload = null;
      List<ArchivoTemporal> lstArchivoTemporal = null;

      _log.debug("Mapa upload con size [" + mapUpload.size() + "]. Se usara el upload con ID [" + iIdUpload + "]");
      lstArchivoTemporal =mapUpload.get("upload" + iIdUpload);

      if ((objArchivoTemporalToDownload = this.findFileTempoByName(lstArchivoTemporal, sFileName)) == null) {
         _log.error("No se puede descargar el archivo [" + sFileName + "]");

         return null;
      }

      _log.debug("Archivo a descargar [" + objArchivoTemporalToDownload.getFArchivo().getAbsolutePath() + "]");

      return objArchivoTemporalToDownload;
   }

	@SuppressWarnings("unchecked")
	@Transactional
	public Map<String,Object> deleteFile(Map<String,Object> mapUpload, Integer iIdUpload, String sFileName) {
            ArchivoTemporal objArchivoTemporalToDelete = null;
            File fArchivo = null;
            List<ArchivoTemporal> lstArchivoTemporal = null;

            lstArchivoTemporal = (List<ArchivoTemporal>) mapUpload.get("upload" + iIdUpload);
            _log.debug("INICIO. upload" + iIdUpload + " contiene [" + lstArchivoTemporal.size() + "] archivo(s)");

            if ((objArchivoTemporalToDelete = this.findFileTempoByName(lstArchivoTemporal, sFileName)) == null) {
               _log.error("No se puede eliminar el archivo [" + sFileName + "]");

               return mapUpload;
            }

            fArchivo = objArchivoTemporalToDelete.getFArchivo();
            _log.debug("Archivo a eliminar [" + fArchivo.getAbsolutePath() + "]");

            if (fArchivo.delete()) {
               _log.debug("Archivo eliminado de la carpeta tempo");
            }
            
            if (lstArchivoTemporal.remove(objArchivoTemporalToDelete)) {
               _log.debug("Archivo eliminado del mapa upload");
            }
            
            
            mapUpload.put("upload" + iIdUpload, lstArchivoTemporal);
            _log.debug("FIN. upload" + iIdUpload + " contiene [" + lstArchivoTemporal.size() + "] archivo(s)");

            return mapUpload;
   }

   public Map<String,List<ArchivoTemporal>> moveFileToTempoDir(Map<String,List<ArchivoTemporal>> mapUpload, Integer iIdUpload, File file, String filename, String contentType, String sUsuario, String principal) throws IOException {
      ArchivoTemporal objArchivoTemporal = null;
      Date datFechaActual = new Date();
      File fArchivo = null;
      Integer iContador = null;
      List<ArchivoTemporal> lstArchivoTemporal = null;
      String sAbsolutePath = null;
      String sNewName = null;
   
      if (mapUpload != null) {
         _log.debug("Mapa upload con size [" + mapUpload.size() + "]. Se intentara usar el upload con ID [" + iIdUpload + "]");
         lstArchivoTemporal=mapUpload.get("upload" + iIdUpload);
      } else {
         _log.debug("Mapa upload es null. Se creara nuevo mapa");
         mapUpload = new HashMap<String,List<ArchivoTemporal>>();
      }

      if (lstArchivoTemporal == null) {
         lstArchivoTemporal = new ArrayList<ArchivoTemporal>();
      }
      
      _log.debug("Archivos existentes [" + lstArchivoTemporal.size() + "]");
      iContador = lstArchivoTemporal.size() + 1;

      _log.debug("Datos del archivo a subir");
      _log.debug("Nombre [" + filename + "] Size [" + file.length() + "] Tipo [" + contentType + "]");

      sAbsolutePath = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.UPLOAD_CARPETA_TEMPO);
      Random generador=new Random(datFechaActual.getTime());
      sNewName = Constantes.ARCHIVO_BRACKET_INICIO + sUsuario + '_' + DateFormatUtils.format(datFechaActual, "yyyyMMddHHmmss") +generador.nextLong()+ '_' + iContador + Constantes.ARCHIVO_BRACKET_FIN + filename;
      _log.debug("Destino [" + sAbsolutePath + "] Nombre [" + sNewName + "]");

      fArchivo = new File(sAbsolutePath, sNewName);
      FileUtils.moveFile(file, fArchivo);
      objArchivoTemporal = new ArchivoTemporal(filename, fArchivo, principal);
      lstArchivoTemporal.add(objArchivoTemporal);
      mapUpload.put("upload" + iIdUpload, lstArchivoTemporal);
      
      return mapUpload;
   }
   
   
   public Map refrescarListaUpload(Map mapUpload, Integer iIdUpload){
              List<ArchivoTemporal> lstArchivoTemporal = null;
	      if (mapUpload != null) {
	         _log.debug("Mapa upload con size [" + mapUpload.size() + "]. Se intentara usar el upload con ID [" + iIdUpload + "]");
	         lstArchivoTemporal = (List<ArchivoTemporal>) mapUpload.get("upload" + iIdUpload);
	      } else {
	         _log.debug("Mapa upload es null. Se creara nuevo mapa");
	         mapUpload = new HashMap();
	      }

	      if (lstArchivoTemporal == null) {
	         lstArchivoTemporal = new ArrayList<ArchivoTemporal>();
	      }
           
              mapUpload.put("upload" + iIdUpload, lstArchivoTemporal);

	      return mapUpload;
	   }
}
