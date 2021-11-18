/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.utils;

import java.util.Date;

/**
 *
 * @author WMH
 */

public class UsuarioMensajeria {

	private int idmensajeria;
	private String tipocumento;
	private String numerodoc;
	private String destinatario;
	private String asunto;
	private Date fechaderiva;
	private int idusuario;
	private String nombresusuario;
	private String apellidosusuario;
	private char estado;
	private char estadoingreso;
	
	
	public UsuarioMensajeria() {
		
	}
	
	public UsuarioMensajeria(int idmensajeria,String tipocumento,String numerodoc,String destinatario,String asunto,Date fechaderiva,
			int idusuario,String nombresusuario,String apellidosusuario,char estado,char estadoingreso) {
		
		this.idmensajeria=idmensajeria;
		this.tipocumento=tipocumento;
		this.numerodoc=numerodoc;
		this.destinatario=destinatario;
		this.asunto=asunto;
		this.fechaderiva=fechaderiva;
		this.idusuario=idusuario;
		this.nombresusuario=nombresusuario;
		this.apellidosusuario=apellidosusuario;
		this.estado=estado;
		this.estadoingreso=estadoingreso;
		
		
	}
	
	  ///////////////////////
	 ///Getter and Setter///
	///////////////////////
	
	public void setIdmensajeria(int idmensajeria) {
		this.idmensajeria = idmensajeria;
	}
	public int getIdmensajeria() {
		return idmensajeria;
	}
	public void setTipocumento(String tipocumento) {
		this.tipocumento = tipocumento;
	}
	public String getTipocumento() {
		return tipocumento;
	}
	public void setNumerodoc(String numerodoc) {
		this.numerodoc = numerodoc;
	}
	public String getNumerodoc() {
		return numerodoc;
	}
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	public String getDestinatario() {
		return destinatario;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setFechaderiva(Date fechaderiva) {
		this.fechaderiva = fechaderiva;
	}
	public Date getFechaderiva() {
		return fechaderiva;
	}
	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	public int getIdusuario() {
		return idusuario;
	}
	public void setNombresusuario(String nombresusuario) {
		this.nombresusuario = nombresusuario;
	}
	public String getNombresusuario() {
		return nombresusuario;
	}
	public void setApellidosusuario(String apellidosusuario) {
		this.apellidosusuario = apellidosusuario;
	}
	public String getApellidosusuario() {
		return apellidosusuario;
	}

	public void setEstado(char estado) {
		this.estado = estado;
	}

	public char getEstado() {
		return estado;
	}

	public void setEstadoingreso(char estadoingreso) {
		this.estadoingreso = estadoingreso;
	}

	public char getEstadoingreso() {
		return estadoingreso;
	}

	
}
