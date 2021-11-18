/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.pojos.jasper;

import java.util.Date;

public class CargoReporte {

	private String nromesapartes;
	private String tipodocumento;
	private String proceso;
	private String nroexpediente;
	private String remitente;
	private String asunto;
        private String nroTramite;
        private String nroFolios;
        private String usuarioRegistro;
        private String desUnidad;
        private String desCopia;

        public String getDesCopia() {
            return desCopia;
        }

        public void setDesCopia(String desCopia) {
            this.desCopia = desCopia;
        }

        public String getDesUnidad() {
            return desUnidad;
        }

        public void setDesUnidad(String desUnidad) {
            this.desUnidad = desUnidad;
        }

        public String getUsuarioRegistro() {
            return usuarioRegistro;
        }

        public void setUsuarioRegistro(String usuarioRegistro) {
            this.usuarioRegistro = usuarioRegistro;
        }

        public String getNroFolios() {
            return nroFolios;
        }

        public void setNroFolios(String nroFolios) {
            this.nroFolios = nroFolios;
        }

        public String getNroTramite() {
            return nroTramite;
        }

        public void setNroTramite(String nroTramite) {
            this.nroTramite = nroTramite;
        }
	private String observacion;
	private String nrodocumento;
	private String unidad;
	private Date fechacreacion;
	private String direccion;
	
	public String getNromesapartes() {
		return nromesapartes;
	}
	public void setNromesapartes(String nromesapartes) {
		this.nromesapartes = nromesapartes;
	}
	public String getTipodocumento() {
		return tipodocumento;
	}
	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public String getNroexpediente() {
		return nroexpediente;
	}
	public void setNroexpediente(String nroexpediente) {
		this.nroexpediente = nroexpediente;
	}
	public String getRemitente() {
		return remitente;
	}
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getNrodocumento() {
		return nrodocumento;
	}
	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	public Date getFechacreacion() {
		return fechacreacion;
	}
	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	
}
