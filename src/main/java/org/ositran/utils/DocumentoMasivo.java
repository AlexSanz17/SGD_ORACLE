package org.ositran.utils;

import java.util.List;

import com.btg.ositran.siged.domain.Etapa;
import com.btg.ositran.siged.domain.Usuario;

public class DocumentoMasivo {
	private Integer[] arrIdDoc;
    private String rol;
    private Etapa etapa;
    private String asunto;
    private Usuario usuarioRemitente;
    private String contenido;
    private String tipoDerivacion;
    private boolean paraAprobar;
    private String sOpcion;
    private Usuario destinatario;
    private List<String> conCopia;
    
	public Integer[] getArrIdDoc() {
		return arrIdDoc;
	}
	public void setArrIdDoc(Integer[] arrIdDoc) {
		this.arrIdDoc = arrIdDoc;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public Etapa getEtapa() {
		return etapa;
	}
	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public Usuario getUsuarioRemitente() {
		return usuarioRemitente;
	}
	public void setUsuarioRemitente(Usuario usuarioRemitente) {
		this.usuarioRemitente = usuarioRemitente;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public String getTipoDerivacion() {
		return tipoDerivacion;
	}
	public void setTipoDerivacion(String tipoDerivacion) {
		this.tipoDerivacion = tipoDerivacion;
	}
	public boolean isParaAprobar() {
		return paraAprobar;
	}
	public void setParaAprobar(boolean paraAprobar) {
		this.paraAprobar = paraAprobar;
	}
	public String getsOpcion() {
		return sOpcion;
	}
	public void setsOpcion(String sOpcion) {
		this.sOpcion = sOpcion;
	}
	public Usuario getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}
	public List<String> getConCopia() {
		return conCopia;
	}
	public void setConCopia(List<String> conCopia) {
		this.conCopia = conCopia;
	}
    
    
}
