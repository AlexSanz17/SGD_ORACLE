package org.ositran.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.ositran.utils.ArchivoTemporal;

public interface UploadService{
	
	public ArchivoTemporal findFileTempoByName(List<ArchivoTemporal> lstArchivoTemporal,String sFileName);

	public ArchivoTemporal downloadFileTempo(Map<String,List<ArchivoTemporal>> mapUpload,Integer iIdUpload,String sFileName);

	public Map<String,Object> deleteFile(Map<String,Object> mapUpload,Integer iIdUpload,String sFileName);

	public Map<String,List<ArchivoTemporal>> moveFileToTempoDir(Map<String,List<ArchivoTemporal>> mapUpload,Integer iIdUpload,File file,String filename,String contentType,String sUsuario, String principal) throws IOException;

	public Map<String,List<ArchivoTemporal>> refrescarListaUpload(Map<String,List<ArchivoTemporal>> mapUpload,Integer iIdUpload);
}
