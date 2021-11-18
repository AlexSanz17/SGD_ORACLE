/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.dojo;

import java.util.Date;

public class Consulta {
	   private String accion;
	   private String tipoDocumento;
	   private String areaOrigen;
	   private String areaDestino;
	   private String fechaDocumentoDesde;
	   private String fechaDocumentoHasta;
	   private String nroDocumento;
	   private String asuntoDocumento;
	   //adicional filtros Mis Expedientes
	   private String nroExpediente;
	   private String asuntoExpediente;
	   private String fechaExpedienteDesde;
	   private String fechaExpedienteHasta;
	   private String proceso;
	   private String cliente;

	public String getAreaDestino() {
		return areaDestino;
	}
	public void setAreaDestino(String areaDestino) {
		this.areaDestino = areaDestino;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getAreaOrigen() {
		return areaOrigen;
	}
	public void setAreaOrigen(String areaOrigen) {
		this.areaOrigen = areaOrigen;
	}
	public String getNroDocumento() {
		return nroDocumento;
	}
	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	public String getAsuntoDocumento() {
		return asuntoDocumento;
	}
	public void setAsuntoDocumento(String asuntoDocumento) {
		this.asuntoDocumento = asuntoDocumento;
	}
	public String getFechaDocumentoDesde() {
		return fechaDocumentoDesde;
	}
	public void setFechaDocumentoDesde(String fechaDocumentoDesde) {
		this.fechaDocumentoDesde = fechaDocumentoDesde;
	}
	public String getFechaDocumentoHasta() {
		return fechaDocumentoHasta;
	}
	public void setFechaDocumentoHasta(String fechaDocumentoHasta) {
		this.fechaDocumentoHasta = fechaDocumentoHasta;
	}
	public String getNroExpediente() {
		return nroExpediente;
	}
	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}
	public String getAsuntoExpediente() {
		return asuntoExpediente;
	}
	public void setAsuntoExpediente(String asuntoExpediente) {
		this.asuntoExpediente = asuntoExpediente;
	}
	public String getFechaExpedienteDesde() {
		return fechaExpedienteDesde;
	}
	public void setFechaExpedienteDesde(String fechaExpedienteDesde) {
		this.fechaExpedienteDesde = fechaExpedienteDesde;
	}
	public String getFechaExpedienteHasta() {
		return fechaExpedienteHasta;
	}
	public void setFechaExpedienteHasta(String fechaExpedienteHasta) {
		this.fechaExpedienteHasta = fechaExpedienteHasta;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}



}
