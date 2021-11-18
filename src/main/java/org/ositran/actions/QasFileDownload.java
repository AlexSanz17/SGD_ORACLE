/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.opensymphony.xwork2.Action;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.File;
import javax.activation.MimetypesFileTypeMap;
import org.apache.log4j.Logger;
import org.ositran.services.ArchivoService;

/**
 * 
 * @author armando
 */
public class QasFileDownload{
	// Estos 4 dampos son requeridos para el StreamResult!
	private InputStream inputStream;
	private String name;
	private long size;
	private String description;// content-type
	private Integer idArchivo;
	private ArchivoService archivoSrv;
	private String tipo;
        private static Logger log=Logger.getLogger(QasFileDownload.class);

	public String downloadFile(){
		setInputStream(null);
		try{
			File file=getArchivoSrv().getFile(getIdArchivo(),getTipo().charAt(0));
			this.setInputStream(new FileInputStream(file));
			this.setName(file.getName());
			this.setSize(file.length());
			this.setDescription(new MimetypesFileTypeMap().getContentType(file));
		}catch(FileNotFoundException ex){
			log.warn(ex.getMessage(),ex);
		}
		if(getInputStream()!=null){
			return Action.SUCCESS;
		}
		return Action.ERROR;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream(){
		return inputStream;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream){
		this.inputStream=inputStream;
	}

	/**
	 * @return the archivoSrv
	 */
	public ArchivoService getArchivoSrv(){
		return archivoSrv;
	}

	/**
	 * @param archivoSrv
	 *            the archivoSrv to set
	 */
	public void setArchivoSrv(ArchivoService archivoSrv){
		this.archivoSrv=archivoSrv;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo(){
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(String tipo){
		this.tipo=tipo;
	}

	/**
	 * @return the idArchivo
	 */
	public Integer getIdArchivo(){
		return idArchivo;
	}

	/**
	 * @param idArchivo
	 *            the idArchivo to set
	 */
	public void setIdArchivo(Integer idArchivo){
		this.idArchivo=idArchivo;
	}

	/**
	 * @return the name
	 */
	public String getName(){
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name){
		this.name=name;
	}

	/**
	 * @return the size
	 */
	public long getSize(){
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(long size){
		this.size=size;
	}

	/**
	 * @return the description
	 */
	public String getDescription(){
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description){
		this.description=description;
	}
}
