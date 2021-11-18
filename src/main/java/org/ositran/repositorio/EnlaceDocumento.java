/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.repositorio;

/**
 * Este bean solo contiene 2 parámetros que indican tanto el nombre como el link
 * al contenido de un archivo.
 * 
 * @author armando
 */
public class EnlaceDocumento{

	private String URL;
	private String nombreArchivo;
	
	public EnlaceDocumento(){}

	/**
	 * 
	 * @param URL
	 * @param nombreArchivo
	 */
	public EnlaceDocumento(String URL,String nombreArchivo){
		setURL(URL);
		setNombreArchivo(nombreArchivo);
	}

	/**
	 * @return the URL
	 */
	public String getURL(){
		return URL;
	}

	/**
	 * @param URL
	 *            the URL to set
	 */
	public void setURL(String URL){
		this.URL=URL;
	}

	/**
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo(){
		return nombreArchivo;
	}

	/**
	 * @param nombreArchivo
	 *            the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo){
		this.nombreArchivo=nombreArchivo;
	}
}
