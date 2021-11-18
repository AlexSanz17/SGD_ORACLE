package org.ositran.utils;

import java.util.Date;

public class DocumentoEnviadoTemporal {
	private Integer idTrazabilidadEnvio;
	private String asunto;
	private String contenido;
	private String destinatarioNombres;
	private String destinatarioApellidos;
	private String remitenteNombres;
	private String remitenteApellidos;
	private Integer nroRegistro;
	private Date fechaCreacion;
        private String strAccion;
        private Integer prioridad;
        private char tipoEnvio;

        public char getTipoEnvio() {
            return tipoEnvio;
        }

        public void setTipoEnvio(char tipoEnvio) {
            this.tipoEnvio = tipoEnvio;
        }

       
        public Integer getPrioridad() {
            return prioridad;
        }

        public void setPrioridad(Integer prioridad) {
            this.prioridad = prioridad;
        }

        public String getFechaLimite() {
            return fechaLimite;
        }

        public void setFechaLimite(String fechaLimite) {
            this.fechaLimite = fechaLimite;
        }
        private String fechaLimite;

        public String getStrAccion() {
            return strAccion;
        }

        public void setStrAccion(String strAccion) {
            this.strAccion = strAccion;
        }
        
	public DocumentoEnviadoTemporal() {
		super();
	}
	public Integer getIdTrazabilidadEnvio() {
		return idTrazabilidadEnvio;
	}
	public void setIdTrazabilidadEnvio(Integer idTrazabilidadEnvio) {
		this.idTrazabilidadEnvio = idTrazabilidadEnvio;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public String getDestinatarioNombres() {
		return destinatarioNombres;
	}
	public void setDestinatarioNombres(String destinatarioNombres) {
		this.destinatarioNombres = destinatarioNombres;
	}
	public String getDestinatarioApellidos() {
		return destinatarioApellidos;
	}
	public void setDestinatarioApellidos(String destinatarioApellidos) {
		this.destinatarioApellidos = destinatarioApellidos;
	}
	public String getRemitenteNombres() {
		return remitenteNombres;
	}
	public void setRemitenteNombres(String remitenteNombres) {
		this.remitenteNombres = remitenteNombres;
	}
	public String getRemitenteApellidos() {
		return remitenteApellidos;
	}
	public void setRemitenteApellidos(String remitenteApellidos) {
		this.remitenteApellidos = remitenteApellidos;
	}
	public Integer getNroRegistro() {
		return nroRegistro;
	}
	public void setNroRegistro(Integer nroRegistro) {
		this.nroRegistro = nroRegistro;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	
}
