package org.ositran.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.btg.ositran.siged.domain.Accion;
import com.btg.ositran.siged.domain.Distrito;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Legajo;
import com.btg.ositran.siged.domain.Usuario;
import java.text.DecimalFormat;

public class DocumentoDetail {
        private String tamanoPrincipal;
        private String tamanoCargo;
        private String archivoPrincipal;
        private String archivoAnexo;
        private String archivoCargo;
	private boolean delegado = false;
        private String desUnidadOrganica;
        private String cuo;
        private char flagCodigoVirtual;
        private String codigoVirtual;
        private Accion accion;
	private Documento doc;
	private Usuario objAutor;
	private Integer idCodigo;
	private String nroDocumentoReferenciado;
	private String tupa;
        private String remitente;
        private String opcion;
        private String opcionMenu="";
        private String tipoTransaccion;
        private String listReferenciados;
        private String idExterno;
        private String listaDerivacionPara;
        private String listaDerivacionCC;
        private Integer proyecto;
        private String cantAnexos;
        private String listaIntegrantesInternos;
        private String listaIntegrantesExternos;
        private String raiz;
        private Integer origen;
        private Integer bandeja;
        private String  desDocumentoOrigen;
        private String cargoRemitente;
        private char cDisponible;
	private char cEstado;
	private char cPrincipal;
	private Integer clienteubigeoalternativo;
	private Integer clienteubigeoprincipal;
	private Integer idEnumerador;
	private Integer idFirmante;
	private Integer idUsuarioLogeado;
	private Integer iIdAccion;
	private Integer iIdCliente;
        private String  esTipoRecepcion;
	private Integer iIdCorrentista;
	private Integer iIdCumpleRequisito;
	private Integer iIdDepartamento;
	private Integer iIdDepartamentoAlt;
	private Integer iIdDestinatario;
	private Integer iIdDistrito;
	private Integer iIdDistritoAlt;
	private Integer iIdDocumento;
        private Integer iIdDocumentoReferencia;
	private Integer iIdEtapa;
	private Integer iIdExpediente;
        private Integer iIdLegajo;
        private Integer iIdLegajoOrigen;
	private Integer iIdExpedienteClienteUbigeoAlternativo;
	private Integer iIdExpedienteClienteUbigeoPrincipal;
	private Integer iIdMotivo;
	private Integer iIdProceso;
        private Integer iIdSerie;
	private Integer iIdProvincia;
	private Integer iIdProvinciaAlt;
	private Integer iIdRemitente;
	private Integer iIdResponsable;
	private Integer iIdSala;
	private Integer iIdSubMotivo;
	private Integer iIdTipoDocumento;
	private Integer iIdTipoIdentificacion;
	private Integer iNroFolios;
        private Integer iNroFoliosPIDE;     
        private Integer iNroFoliosOriginales;
        private Integer iNroFoliosCopias;
        private Integer iNroFoliosDigitalizados;
        
	private Integer iNroResolucion;
	private String iPlazoDia;
	private Integer iPlazoHora;
	private Integer strPlazo;
	private Integer prioridad;
        private String plazo;
        private String  strPrioridad;
	private Integer[] arrIdSubMotivo;
	private Integer[] idSubmotivos;
	private Integer[] idSuministros;
	private Integer idAreaDerivada;
        private String listAtendidos;

	private String autor;
	private String asuntoExpediente;
	private String clienteapellidomaterno;
	private String clienteapellidopaterno;
	private String clientecorreo;
	private String clientedireccionalternativa;
	private String clientedireccionprincipal;
	private String clientenombres;
	private String clienterazonsocial;
	private String clienterepresentantelegal;
	private String clientetelefono;
	private String codProceso;
	private String enumerarDocumento;
	private String observacionDigitalizador;
	private String observacionDocumento;
	private String observacionExpediente;
	private String paraAprobar;
	private String sDepartamentoAlt;
	private String sDistritoAlt;
	private String sNroExpediente;
	private String sObservacionRechazo;
	private String sPropietarioExpediente;
	private String sProvinciaAlt;
	private String sRequiereVerificacion;
	private String sSumilla;
        private String strHoraReunion;
	private String strAbreviado;
	private String strAccion;
	private String strAnalista;
	private String strAsunto;
	private String strContenido;
	private String strCorrentista;
	private String strCorreoCliente;
	private String strCorreoConcesionario;
	private String strDepartamento;
	private String strDestinatario;
	private String strDiaLimetesAtension;
	private String strDireccionAlternativa;
	private String strDireccionConcesionario;
	private String strDireccionPrincipal;
	private String strDistrito;
	private String strFechaAccion;
	private String strFechaAprobacion;
	private String strFechaConcesionario;
	private String strFechaDocumento;
	private String strFechaLimiteAtencion;
	private String strFechaReclamante;
	private String strFechaResolucion;
	private String strHorasLimetesAtension;
	private String strMontoReclamo;
	private String strMotivo;
	private String strNroCaja;
	private String strNroDocumento;
	private String strNroIdentificacion;
	private String strNroMesaPartes;
	private String strObservacion;
	private String strObservacionDoc;
	private String strProceso;
	private String strProvincia;
	private String strRazonSocial;
	private String strReferencia;
	private String strRemitente;
	private String strRepresentanteLegal;
	private String strResponsable;
	private String strResultado;
	private String strRevisorLegal;
	private String strRevisorTecnico;
	private String strRol;
	private String strRUC;
	private String strSala;
	private String strTelefonoCliente;
	private String strTelefonoConcesionario;
	private String strTexto;
	private String strTipoAnalisis;
	private String strTipoDocumento;
	private String strTipoIdentificacion;
	private String strUnidad;
        private String concesionario;
	private String tipoNumeracion;
	private String ultimoAsunto;
	private transient String cadenaCC;
	private List<String> concopias;
	private List<String> condestinatarios;
	private String[] arrSuministro;
	private String[] suministros;
	private String strSinPlazo;
        private String idDestinatario;
	private String aprobarIG;
        private String articulo;
        private String ley;
        private String inciso;
        private String criteriosConfidencialidad;
        private Character enumerado;
        private String unidadRemitente;
        private String unidadDestinatario;
        public List<String> listAnexos;
        private String ruc;
        private Legajo legajo;

        //*****************INSTITUCION**********************
        private Integer  idTipoInstitucion;
        private Integer  iIdInstitucion;
        private String  codCargoPersonaInstitucion;
        private String  idPersonaInstitucion;

        //*****************PERSONA NATURAL*******************
        private Integer  idPersonaNatural;
        private String  codCargoPersonaNatural;
        
        //*********************EMPRESA***********************
        private Integer  idTipoEmpresa;
        private Integer  iIdEmpresa;
        private String  codCargoPersonaEmpresa;
        private String  idPersonaEmpresa;
        
        //*******************SICOR**************************
        
        private Integer iIdMateria;
        private Integer iIdInfraestructura;
        private Integer idInstitucionSicor;
        private String  strLugar;
        private String  strObjetivo;
        private String  strFechaReunion;
     
        //**************************************************        
        
        private String   idInternoExterno;
        private String   idTipoCliente; 
	private Usuario propietario;
	private Usuario usuarioDelegado;
        private String listDoc;

	private Character estaenflujo;
	private Character historico;
	private Character confidencial;
	private String tramite;
        private String nroTramite;

	private Date dateFechaLimiteAtencion;
	private Date fechacreacion;
	private Date strFecha;
        private String numeroReqTributario; 
        private String anioFiscal;
        private char flagExpediente;
        private String flagsideco;
        
        public String getFlagsideco() {
            return flagsideco;
        }

        public void setFlagsideco(String flagsideco) {
            this.flagsideco = flagsideco;
        }
        
        public Integer getiIdLegajo() {
            return iIdLegajo;
        }

        public void setiIdLegajo(Integer iIdLegajo) {
            this.iIdLegajo = iIdLegajo;
        }

        public char getFlagExpediente() {
            return flagExpediente;
        }

        public void setFlagExpediente(char flagExpediente) {
            this.flagExpediente = flagExpediente;
        }

        public String getNumeroReqTributario() {
            return numeroReqTributario;
        }
        
        public void setNumeroReqTributario(String numeroReqTributario){
            this.numeroReqTributario = numeroReqTributario;
        }
        
        public String getAnioFiscal() {
            return anioFiscal;
        }
        
        public void setAnioFiscal(String anioFiscal){
            this.anioFiscal = anioFiscal;
        }
        
        public String getRuc() {
            return ruc;
        }

        public void setRuc(String ruc) {
            this.ruc = ruc;
        }
        
         public String getCuo() {
            return cuo;
        }

        public void setCuo(String cuo) {
            this.cuo = cuo;
        }
        
         public String getDesUnidadOrganica() {
            return desUnidadOrganica;
        }

        public void setDesUnidadOrganica(String desUnidadOrganica) {
            this.desUnidadOrganica = desUnidadOrganica;
        }
        
        public String getCodigoVirtual() {
            return codigoVirtual;
        }

        public void setCodigoVirtual(String codigoVirtual) {
            this.codigoVirtual = codigoVirtual;
        }

        public List<String> getListAnexos() {
            return listAnexos;
        }

        public void setListAnexos(List<String> listAnexos) {
            this.listAnexos = listAnexos;
        }
        
        public String getConcesionario() {
            return concesionario;
        }

        public void setConcesionario(String concesionario) {
            this.concesionario = concesionario;
        }
        
        public String getCantAnexos() {
            return cantAnexos;
        }

        public void setCantAnexos(String cantAnexos) {
            this.cantAnexos = cantAnexos;
        }

        public String getArchivoAnexo() {
            return archivoAnexo;
        }

        public void setArchivoAnexo(String archivoAnexo) {
            this.archivoAnexo = archivoAnexo;
        }
        
        public Integer getProyecto() {
            return proyecto;
        }

        public void setProyecto(Integer proyecto) {
            this.proyecto = proyecto;
        }
        
        public String getIdExterno() {
            return idExterno;
        }

        public void setIdExterno(String idExterno) {
            this.idExterno = idExterno;
        }
        
        
        public String getListaDerivacionPara() {
            return listaDerivacionPara;
        }

        public void setListaDerivacionPara(String listaDerivacionPara) {
            this.listaDerivacionPara = listaDerivacionPara;
        }

        public String getListaDerivacionCC() {
            return listaDerivacionCC;
        }

        public Integer getOrigen() {
            return origen;
        }

        public void setOrigen(Integer origen) {
            this.origen = origen;
        }

        public void setListaDerivacionCC(String listaDerivacionCC) {
            this.listaDerivacionCC = listaDerivacionCC;
        }
       
        public String getDesDocumentoOrigen() {
            return desDocumentoOrigen;
        }

        public void setDesDocumentoOrigen(String desDocumentoOrigen) {
            this.desDocumentoOrigen = desDocumentoOrigen;
        }

        public Integer getBandeja() {
            return bandeja;
        }

        public void setBandeja(Integer bandeja) {
            this.bandeja = bandeja;
        }

        
        
        public String getNroTramite() {
            return nroTramite;
        }

        public void setNroTramite(String nroTramite) {
            this.nroTramite = nroTramite;
        }

        public String getPlazo() {
            return plazo;
        }

        public void setPlazo(String plazo) {
            this.plazo = plazo;
        }
        
        
        public String getEsTipoRecepcion() {
            return esTipoRecepcion;
        }

        public void setEsTipoRecepcion(String esTipoRecepcion) {
            this.esTipoRecepcion = esTipoRecepcion;
        }
        
        public String getArchivoPrincipal() {
            return archivoPrincipal;
        }

        public void setArchivoPrincipal(String archivoPrincipal) {
            this.archivoPrincipal = archivoPrincipal;
        }

        public String getUnidadDestinatario() {
            return unidadDestinatario;
        }

        public void setUnidadDestinatario(String unidadDestinatario) {
            this.unidadDestinatario = unidadDestinatario;
        }

        public String getUnidadRemitente() {
            return unidadRemitente;
        }

        public void setUnidadRemitente(String unidadRemitente) {
            this.unidadRemitente = unidadRemitente;
        }
        
        public String getIdDestinatario() {
            return idDestinatario;
        }

        public void setIdDestinatario(String idDestinatario) {
            this.idDestinatario = idDestinatario;
        }

        public Character getEnumerado() {
            return enumerado;
        }

        public void setEnumerado(Character enumerado) {
            this.enumerado = enumerado;
        }

	public String getCriteriosConfidencialidad() {
		return criteriosConfidencialidad;
	}

	public void setCriteriosConfidencialidad(String criteriosConfidencialidad) {
		this.criteriosConfidencialidad = criteriosConfidencialidad;
	}

	public String getArticulo() {
		return articulo;
	}

	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}

	public String getLey() {
		return ley;
	}

	public void setLey(String ley) {
		this.ley = ley;
	}

	public String getInciso() {
		return inciso;
	}

	public void setInciso(String inciso) {
		this.inciso = inciso;
	}

	public DocumentoDetail() {
		super();
	}

	public String getCadenaCC() {
		return cadenaCC;
	}

	public void setCadenaCC(String cadenaCC) {
		this.cadenaCC = cadenaCC;
	}

	public DocumentoDetail(
			Integer iIdDocumento,
			Date dFechaAccion,
			Date dFechaCreacion,
			Date dFechaLimiteAtencion,
			Date dFechaDocumento,
			String sNumeroMesaPartes,
			Integer iNumeroFolios,
			String sNumeroCaja,
			String sNumeroDocumento,
			String sObservacion,
			Character cEstado,
			Character cPrincipal,
			String sObservacionRechazo,
			Integer iIdTipoDocumento,
			String sTipoDocumento,
			String sPropietarioDocumentoNombres,
			String sPropietarioDocumentoApellidos,
			Integer iIdExpediente,
			String sNroExpediente,
			Character cEstaEnFlujo,
			Character cHistorico,
			String sExpedienteClienteRazonSocial,
			String sExpedienteClienteNombres,
			String sExpedienteClienteApellidoPaterno,
			String sExpedienteClienteApellidoMaterno,
			String sExpedienteClienteRepresentanteLegal,
			String sExpedienteClienteDireccionPrincipal,
			Integer iExpedienteClienteUbigeoPrincipal,
			String sExpedienteClienteDireccionAlternativa,
			Integer iExpedienteClienteUbigeoAlternativo,
			String sExpedienteClienteTelefono,
			String sExpedienteClienteCorreo,
			String sPropietarioExpedienteNombres,
			String sPropietarioExpedienteApellidos,
			Integer iIdProceso,
			String sProceso,
			String sTipoProcesoNombre,
			Integer iIdResponsableProceso,
			String sResponsableProcesoNombres,
			String sResponsableProcesoApellidos,
			String sResponsableProcesoUnidad,
			Integer iIdCliente,
			String sClienteNumeroIdentificacion,
			String sClienteRazonSocial,
			String sClienteNombres,
			String sClienteApellidoPaterno,
			String sClienteApellidoMaterno,
			String sClienteRepresentanteLegal,
			String sClienteDireccionPrincipal,
			Distrito objClienteUbigeoPrincipal,
			String sClienteDireccionAlternativa,
			Distrito objClienteUbigeoAlternativo,
			String sClienteTelefono,
			String sClienteCorreo,
			Integer iIdTipoIdentificacion,
			String sTipoIdentificacion,
			Integer iIdConcesionario,
			String sConcesionarioRazonSocial,
			String sConcesionarioRuc,
			String sConcesionarioDireccion,
			String sConcesionarioCorreo,
			Integer iIdSala,
			String sSala,
			Integer iIdEtapa,
			String sAsunto,
			String sContenido,
			String sRemitenteNombres,
			String sRemitenteApellidos,
			String sDestinatarioNombres,
			String sDestinatarioApellidos,
			Integer iIdRemitente) {
		this.iIdDocumento = iIdDocumento;
		this.strFecha = dFechaAccion;
		this.fechacreacion = dFechaCreacion;
		this.dateFechaLimiteAtencion = dFechaLimiteAtencion;
		dFechaDocumento = (dFechaDocumento == null) ? new Date() : dFechaDocumento;
		this.strFechaDocumento = new SimpleDateFormat(Constantes.FORMATO_FECHA).format(dFechaDocumento);
		this.strFechaLimiteAtencion = (dFechaLimiteAtencion == null) ? "" : new SimpleDateFormat(Constantes.FORMATO_FECHA).format(dFechaLimiteAtencion);
		this.strNroMesaPartes = (sNumeroMesaPartes == null) ? iIdDocumento.toString() : sNumeroMesaPartes;
		this.iNroFolios = iNumeroFolios;
		this.strNroCaja = sNumeroCaja;
		this.strNroDocumento = sNumeroDocumento;
		this.strObservacion = sObservacion;
		this.cEstado = cEstado.charValue();
		this.cPrincipal = cPrincipal.charValue();
		this.sObservacionRechazo = sObservacionRechazo;
		this.iIdTipoDocumento = iIdTipoDocumento;
		this.strTipoDocumento = sTipoDocumento;
		this.strDestinatario = sPropietarioDocumentoNombres + " " + sPropietarioDocumentoApellidos;
		this.iIdExpediente = iIdExpediente;
		this.sNroExpediente = sNroExpediente;
		this.estaenflujo = cEstaEnFlujo;
		this.historico = (cHistorico == null) ? 'N' : cHistorico;

		this.strRazonSocial = "";

		if(sTipoIdentificacion.equals(Constantes.TIPO_IDENTIFICACION_RUC)){
			this.strRazonSocial += (sExpedienteClienteRazonSocial == null ? sClienteRazonSocial : sExpedienteClienteRazonSocial != null ? sExpedienteClienteRazonSocial : "");
		}else{
			if(sExpedienteClienteNombres == null){
				this.strRazonSocial += sClienteNombres != null ? sClienteNombres + " " : "";
				this.strRazonSocial += sClienteApellidoPaterno != null ? sClienteApellidoPaterno + " " : "";
				this.strRazonSocial += sClienteApellidoMaterno != null ? sClienteApellidoMaterno + " " : "";
			}else{
				this.strRazonSocial += sExpedienteClienteNombres != null ? sExpedienteClienteNombres + " " : "";
				this.strRazonSocial += sExpedienteClienteApellidoPaterno != null ? sExpedienteClienteApellidoPaterno + " " : "";
				this.strRazonSocial += sExpedienteClienteApellidoMaterno != null ? sExpedienteClienteApellidoMaterno + " " : "";
			}
		}

		this.clientenombres = (sExpedienteClienteNombres == null) ? sClienteNombres : sExpedienteClienteNombres;
		this.clienteapellidopaterno = (sExpedienteClienteApellidoPaterno == null) ? sClienteApellidoPaterno : sExpedienteClienteApellidoPaterno;
		this.clienteapellidomaterno = (sExpedienteClienteApellidoMaterno == null) ? sClienteApellidoMaterno : sExpedienteClienteApellidoMaterno;
		this.strRepresentanteLegal = (sExpedienteClienteRepresentanteLegal == null) ? sClienteRepresentanteLegal : sExpedienteClienteRepresentanteLegal;
		this.strTelefonoCliente = (sExpedienteClienteTelefono == null) ? sClienteTelefono : sExpedienteClienteTelefono;
		this.strCorreoCliente = (sExpedienteClienteCorreo == null) ? sClienteCorreo : sExpedienteClienteCorreo;
		this.sPropietarioExpediente = sPropietarioExpedienteNombres + " " + sPropietarioExpedienteApellidos;
		this.iIdProceso = iIdProceso;
		this.strProceso = sProceso;
		this.strAbreviado = sTipoProcesoNombre;
		this.iIdResponsable = iIdResponsableProceso;
		this.strResponsable = sResponsableProcesoNombres + " " + sResponsableProcesoApellidos;
		this.strUnidad = sResponsableProcesoUnidad;
		this.iIdCliente = iIdCliente;
		this.strNroIdentificacion = sClienteNumeroIdentificacion;
		this.iIdExpedienteClienteUbigeoPrincipal = iExpedienteClienteUbigeoPrincipal;
		this.strDireccionPrincipal = (sExpedienteClienteDireccionPrincipal == null) ? sClienteDireccionPrincipal : sExpedienteClienteDireccionPrincipal;
		this.iIdDistrito = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getIddistrito();
		this.strDistrito = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getNombre();
		this.iIdProvincia = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getProvincia().getIdprovincia();
		this.strProvincia = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getProvincia().getNombre();
		this.iIdDepartamento = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getProvincia().getDepartamento().getIddepartamento();
		this.strDepartamento = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getProvincia().getDepartamento().getNombre();
		this.iIdExpedienteClienteUbigeoAlternativo = iExpedienteClienteUbigeoAlternativo;
		this.strDireccionAlternativa = (sExpedienteClienteDireccionAlternativa == null) ? sClienteDireccionAlternativa : sExpedienteClienteDireccionAlternativa;
		this.iIdDistritoAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getIddistrito();
		this.sDistritoAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getNombre();
		this.iIdProvinciaAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getProvincia().getIdprovincia();
		this.sProvinciaAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getProvincia().getNombre();
		this.iIdDepartamentoAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getProvincia().getDepartamento().getIddepartamento();
		this.sDepartamentoAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getProvincia().getDepartamento().getNombre();
		this.iIdTipoIdentificacion = iIdTipoIdentificacion;
		this.strTipoIdentificacion = sTipoIdentificacion;
		this.iIdCorrentista = iIdConcesionario;
		this.strCorrentista = sConcesionarioRazonSocial;
		this.strRUC = sConcesionarioRuc;
		this.strDireccionConcesionario = sConcesionarioDireccion;
		this.strCorreoConcesionario = sConcesionarioCorreo;
		this.iIdSala = iIdSala;
		this.strSala = sSala;
		this.iIdEtapa = iIdEtapa;
		this.strAsunto = sAsunto;
		this.strContenido = sContenido;
		this.strRemitente = sRemitenteNombres + " " + sRemitenteApellidos;
		this.strDestinatario = sDestinatarioNombres + " " + sDestinatarioApellidos;
		this.iIdRemitente = iIdRemitente;
	}

	/**Para agregar la observacion del digitalizador -------------------------------------------------------------------------*/
	public DocumentoDetail(
			Integer iIdDocumento,
			Date dFechaAccion,
			Date dFechaCreacion,
			Date dFechaLimiteAtencion,
			Date dFechaDocumento,
			String sNumeroMesaPartes,
			Integer iNumeroFolios,
			String sNumeroCaja,
			String sNumeroDocumento,
			String sObservacion,
			Character cEstado,
			Character cPrincipal,
			String sObservacionRechazo,
			Integer iIdTipoDocumento,
			String sTipoDocumento,
			String sPropietarioDocumentoNombres,
			String sPropietarioDocumentoApellidos,
			Integer iIdExpediente,
			String sNroExpediente,
			Character cEstaEnFlujo,
			Character cHistorico,
			String sExpedienteClienteRazonSocial,
			String sExpedienteClienteNombres,
			String sExpedienteClienteApellidoPaterno,
			String sExpedienteClienteApellidoMaterno,
			String sExpedienteClienteRepresentanteLegal,
			String sExpedienteClienteDireccionPrincipal,
			Integer iExpedienteClienteUbigeoPrincipal,
			String sExpedienteClienteDireccionAlternativa,
			Integer iExpedienteClienteUbigeoAlternativo,
			String sExpedienteClienteTelefono,
			String sExpedienteClienteCorreo,
			String sPropietarioExpedienteNombres,
			String sPropietarioExpedienteApellidos,
			Integer iIdProceso,
			String sProceso,
			String sTipoProcesoNombre,
			Integer iIdResponsableProceso,
			String sResponsableProcesoNombres,
			String sResponsableProcesoApellidos,
			String sResponsableProcesoUnidad,
			Integer iIdCliente,
			String sClienteNumeroIdentificacion,
			String sClienteRazonSocial,
			String sClienteNombres,
			String sClienteApellidoPaterno,
			String sClienteApellidoMaterno,
			String sClienteRepresentanteLegal,
			String sClienteDireccionPrincipal,
			Distrito objClienteUbigeoPrincipal,
			String sClienteDireccionAlternativa,
			Distrito objClienteUbigeoAlternativo,
			String sClienteTelefono,
			String sClienteCorreo,
			Integer iIdTipoIdentificacion,
			String sTipoIdentificacion,
			Integer iIdConcesionario,
			String sConcesionarioRazonSocial,
			String sConcesionarioRuc,
			String sConcesionarioDireccion,
			String sConcesionarioCorreo,
			Integer iIdSala,
			String sSala,
			Integer iIdEtapa,
			String sAsunto,
			String sContenido,
			String sRemitenteNombres,
			String sRemitenteApellidos,
			String sDestinatarioNombres,
			String sDestinatarioApellidos,
			Integer iIdRemitente,
			String observacionDigitalizador,
			String codProceso,
			Integer prioridad) {
		this.iIdDocumento = iIdDocumento;
		this.strFecha = dFechaAccion;
		this.fechacreacion = dFechaCreacion;
		this.dateFechaLimiteAtencion = dFechaLimiteAtencion;
		dFechaDocumento = (dFechaDocumento == null) ? new Date() : dFechaDocumento;
		this.strFechaDocumento = new SimpleDateFormat(Constantes.FORMATO_FECHA).format(dFechaDocumento);
		this.strFechaLimiteAtencion = (dFechaLimiteAtencion == null) ? "" : new SimpleDateFormat(Constantes.FORMATO_FECHA).format(dFechaLimiteAtencion);
		this.strNroMesaPartes = (sNumeroMesaPartes == null) ? iIdDocumento.toString() : sNumeroMesaPartes;
		this.iNroFolios = iNumeroFolios;
		this.strNroCaja = sNumeroCaja;
		this.strNroDocumento = sNumeroDocumento;
		this.strObservacion = sObservacion;
		this.cEstado = cEstado.charValue();
		this.cPrincipal = cPrincipal.charValue();
		this.sObservacionRechazo = sObservacionRechazo;
		this.iIdTipoDocumento = iIdTipoDocumento;
		this.strTipoDocumento = sTipoDocumento;
		this.strDestinatario = sPropietarioDocumentoNombres + " " + sPropietarioDocumentoApellidos;
		this.iIdExpediente = iIdExpediente;
		this.sNroExpediente = sNroExpediente;
		this.estaenflujo = cEstaEnFlujo;
		this.historico = (cHistorico == null) ? 'N' : cHistorico;
		this.strRazonSocial = (sTipoIdentificacion.equals(Constantes.TIPO_IDENTIFICACION_RUC)) ? (sExpedienteClienteRazonSocial == null ? sClienteRazonSocial : sExpedienteClienteRazonSocial) : (sExpedienteClienteNombres == null ? sClienteNombres + " " + sClienteApellidoPaterno + " " + sClienteApellidoMaterno : sExpedienteClienteNombres + " " + sExpedienteClienteApellidoPaterno + " " + sExpedienteClienteApellidoMaterno);
		this.clientenombres = (sExpedienteClienteNombres == null) ? sClienteNombres : sExpedienteClienteNombres;
		this.clienteapellidopaterno = (sExpedienteClienteApellidoPaterno == null) ? sClienteApellidoPaterno : sExpedienteClienteApellidoPaterno;
		this.clienteapellidomaterno = (sExpedienteClienteApellidoMaterno == null) ? sClienteApellidoMaterno : sExpedienteClienteApellidoMaterno;
		this.strRepresentanteLegal = (sExpedienteClienteRepresentanteLegal == null) ? sClienteRepresentanteLegal : sExpedienteClienteRepresentanteLegal;
		this.strTelefonoCliente = (sExpedienteClienteTelefono == null) ? sClienteTelefono : sExpedienteClienteTelefono;
		this.strCorreoCliente = (sExpedienteClienteCorreo == null) ? sClienteCorreo : sExpedienteClienteCorreo;
		this.sPropietarioExpediente = sPropietarioExpedienteNombres + " " + sPropietarioExpedienteApellidos;
		this.iIdProceso = iIdProceso;
		this.strProceso = sProceso;
		this.strAbreviado = sTipoProcesoNombre;
		this.iIdResponsable = iIdResponsableProceso;
		this.strResponsable = sResponsableProcesoNombres + " " + sResponsableProcesoApellidos;
		this.strUnidad = sResponsableProcesoUnidad;
		this.iIdCliente = iIdCliente;
		this.strNroIdentificacion = sClienteNumeroIdentificacion;
		this.iIdExpedienteClienteUbigeoPrincipal = iExpedienteClienteUbigeoPrincipal;
		this.strDireccionPrincipal = (sExpedienteClienteDireccionPrincipal == null) ? sClienteDireccionPrincipal : sExpedienteClienteDireccionPrincipal;
		this.iIdDistrito = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getIddistrito();
		this.strDistrito = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getNombre();
		this.iIdProvincia = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getProvincia().getIdprovincia();
		this.strProvincia = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getProvincia().getNombre();
		this.iIdDepartamento = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getProvincia().getDepartamento().getIddepartamento();
		this.strDepartamento = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getProvincia().getDepartamento().getNombre();
		this.iIdExpedienteClienteUbigeoAlternativo = iExpedienteClienteUbigeoAlternativo;
		this.strDireccionAlternativa = (sExpedienteClienteDireccionAlternativa == null) ? sClienteDireccionAlternativa : sExpedienteClienteDireccionAlternativa;
		this.iIdDistritoAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getIddistrito();
		this.sDistritoAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getNombre();
		this.iIdProvinciaAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getProvincia().getIdprovincia();
		this.sProvinciaAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getProvincia().getNombre();
		this.iIdDepartamentoAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getProvincia().getDepartamento().getIddepartamento();
		this.sDepartamentoAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getProvincia().getDepartamento().getNombre();
		this.iIdTipoIdentificacion = iIdTipoIdentificacion;
		this.strTipoIdentificacion = sTipoIdentificacion;
		this.iIdCorrentista = iIdConcesionario;
		this.strCorrentista = sConcesionarioRazonSocial;
		this.strRUC = sConcesionarioRuc;
		this.strDireccionConcesionario = sConcesionarioDireccion;
		this.strCorreoConcesionario = sConcesionarioCorreo;
		this.iIdSala = iIdSala;
		this.strSala = sSala;
		this.iIdEtapa = iIdEtapa;
		this.strAsunto = sAsunto;
		this.strContenido = sContenido;
		this.strRemitente = sRemitenteNombres + " " + sRemitenteApellidos;
		this.strDestinatario = sDestinatarioNombres + " " + sDestinatarioApellidos;
		this.iIdRemitente = iIdRemitente;
		this.observacionDigitalizador = observacionDigitalizador;
		this.prioridad = prioridad;
		if(codProceso != null){
			this.codProceso = codProceso;
		}else{
			this.codProceso = "-";
		}
	}
        
        
        
        
        /**Para agregar la observacion del digitalizador xxx-------------------------------------------------------------------------*/
	public DocumentoDetail(
			Integer iIdDocumento,
			Date dFechaAccion,
			Date dFechaCreacion,
			Date dFechaLimiteAtencion,
			Date dFechaDocumento,
			String sNumeroMesaPartes,
			Integer iNumeroFolios,
			String sNumeroCaja,
			String sNumeroDocumento,
			String sObservacion,
			Character cEstado,
			Character cPrincipal,
			String sObservacionRechazo,
			Integer iIdTipoDocumento,
			String sTipoDocumento,
			String sPropietarioDocumentoNombres,
			String sPropietarioDocumentoApellidos,
			Integer iIdExpediente,
			String sNroExpediente,
			Character cEstaEnFlujo,
			Character cHistorico,
			String sExpedienteClienteRazonSocial,
			String sExpedienteClienteNombres,
			String sExpedienteClienteApellidoPaterno,
			String sExpedienteClienteApellidoMaterno,
			String sExpedienteClienteRepresentanteLegal,
			String sExpedienteClienteDireccionPrincipal,
			Integer iExpedienteClienteUbigeoPrincipal,
			String sExpedienteClienteDireccionAlternativa,
			Integer iExpedienteClienteUbigeoAlternativo,
			String sExpedienteClienteTelefono,
			String sExpedienteClienteCorreo,
			String sPropietarioExpedienteNombres,
			String sPropietarioExpedienteApellidos,
			//Integer iIdProceso,
			//String sProceso,
			//String sTipoProcesoNombre,
			//Integer iIdResponsableProceso,
			//String sResponsableProcesoNombres,
			//String sResponsableProcesoApellidos,
			//String sResponsableProcesoUnidad,
			Integer iIdCliente,
			String sClienteNumeroIdentificacion,
			String sClienteRazonSocial,
			String sClienteNombres,
			String sClienteApellidoPaterno,
			String sClienteApellidoMaterno,
			String sClienteRepresentanteLegal,
			String sClienteDireccionPrincipal,
			Distrito objClienteUbigeoPrincipal,
			String sClienteDireccionAlternativa,
			Distrito objClienteUbigeoAlternativo,
			String sClienteTelefono,
			String sClienteCorreo,
			Integer iIdTipoIdentificacion,
			String sTipoIdentificacion,
			String sAsunto,
			String sContenido,
                        String strAccion,
			String sRemitenteNombres,
			String sRemitenteApellidos,
			String sDestinatarioNombres,
			String sDestinatarioApellidos,
			Integer iIdRemitente,
			String observacionDigitalizador,
			//String codProceso,
			Integer prioridad,
                        Character  enumerado,
                        Integer strPlazo) {
               this.iIdDocumento = iIdDocumento;
		this.strFecha = dFechaAccion;
		this.fechacreacion = dFechaCreacion;
		this.dateFechaLimiteAtencion = dFechaLimiteAtencion;
		dFechaDocumento = (dFechaDocumento == null) ? new Date() : dFechaDocumento;
		this.strFechaDocumento = new SimpleDateFormat(Constantes.FORMATO_FECHA).format(dFechaDocumento);
		this.strFechaLimiteAtencion = (dFechaLimiteAtencion == null) ? "" : new SimpleDateFormat(Constantes.FORMATO_FECHA).format(dFechaLimiteAtencion);
		this.strNroMesaPartes = (sNumeroMesaPartes == null) ? iIdDocumento.toString() : sNumeroMesaPartes;
		this.iNroFolios = iNumeroFolios;
		this.strNroCaja = sNumeroCaja;
		this.strNroDocumento = sNumeroDocumento;
		this.strObservacion = sObservacion;
		this.cEstado = cEstado.charValue();
		this.cPrincipal = cPrincipal.charValue();
		this.sObservacionRechazo = sObservacionRechazo;
		this.iIdTipoDocumento = iIdTipoDocumento;
		this.strTipoDocumento = sTipoDocumento;
		this.strDestinatario = sPropietarioDocumentoNombres + " " + sPropietarioDocumentoApellidos;
		this.iIdExpediente = iIdExpediente;
		this.sNroExpediente = sNroExpediente;
		this.estaenflujo = cEstaEnFlujo;
		this.historico = (cHistorico == null) ? 'N' : cHistorico;
		this.strRazonSocial = (sTipoIdentificacion.equals(Constantes.TIPO_IDENTIFICACION_RUC)) ? (sExpedienteClienteRazonSocial == null ? sClienteRazonSocial : sExpedienteClienteRazonSocial) : (sExpedienteClienteNombres == null ? sClienteNombres + " " + sClienteApellidoPaterno + " " + sClienteApellidoMaterno : sExpedienteClienteNombres + " " + sExpedienteClienteApellidoPaterno + " " + sExpedienteClienteApellidoMaterno);
		this.clientenombres = (sExpedienteClienteNombres == null) ? sClienteNombres : sExpedienteClienteNombres;
		this.clienteapellidopaterno = (sExpedienteClienteApellidoPaterno == null) ? sClienteApellidoPaterno : sExpedienteClienteApellidoPaterno;
		this.clienteapellidomaterno = (sExpedienteClienteApellidoMaterno == null) ? sClienteApellidoMaterno : sExpedienteClienteApellidoMaterno;
		this.strRepresentanteLegal = (sExpedienteClienteRepresentanteLegal == null) ? sClienteRepresentanteLegal : sExpedienteClienteRepresentanteLegal;
		this.strTelefonoCliente = (sExpedienteClienteTelefono == null) ? sClienteTelefono : sExpedienteClienteTelefono;
		this.strCorreoCliente = (sExpedienteClienteCorreo == null) ? sClienteCorreo : sExpedienteClienteCorreo;
		this.sPropietarioExpediente = sPropietarioExpedienteNombres + " " + sPropietarioExpedienteApellidos;
		this.iIdProceso = iIdProceso;
                this.strAccion = strAccion;
		this.iIdCliente = iIdCliente;
		this.strNroIdentificacion = sClienteNumeroIdentificacion;
		this.iIdExpedienteClienteUbigeoPrincipal = iExpedienteClienteUbigeoPrincipal;
		this.strDireccionPrincipal = (sExpedienteClienteDireccionPrincipal == null) ? sClienteDireccionPrincipal : sExpedienteClienteDireccionPrincipal;
		this.iIdDistrito = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getIddistrito();
		this.strDistrito = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getNombre();
		this.iIdProvincia = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getProvincia().getIdprovincia();
		this.strProvincia = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getProvincia().getNombre();
		this.iIdDepartamento = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getProvincia().getDepartamento().getIddepartamento();
		this.strDepartamento = (objClienteUbigeoPrincipal == null) ? null : objClienteUbigeoPrincipal.getProvincia().getDepartamento().getNombre();
		this.iIdExpedienteClienteUbigeoAlternativo = iExpedienteClienteUbigeoAlternativo;
		this.strDireccionAlternativa = (sExpedienteClienteDireccionAlternativa == null) ? sClienteDireccionAlternativa : sExpedienteClienteDireccionAlternativa;
		this.iIdDistritoAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getIddistrito();
		this.sDistritoAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getNombre();
		this.iIdProvinciaAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getProvincia().getIdprovincia();
		this.sProvinciaAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getProvincia().getNombre();
		this.iIdDepartamentoAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getProvincia().getDepartamento().getIddepartamento();
		this.sDepartamentoAlt = (objClienteUbigeoAlternativo == null) ? null : objClienteUbigeoAlternativo.getProvincia().getDepartamento().getNombre();
		this.iIdTipoIdentificacion = iIdTipoIdentificacion;
		this.strTipoIdentificacion = sTipoIdentificacion;
		this.strAsunto = sAsunto;
		this.strContenido = sContenido;
		this.strRemitente = sRemitenteNombres + " " + sRemitenteApellidos;
		this.strDestinatario = sDestinatarioNombres + " " + sDestinatarioApellidos;
		this.iIdRemitente = iIdRemitente;
		this.observacionDigitalizador = observacionDigitalizador;
		this.prioridad = prioridad;
                this.enumerado = enumerado;
                this.strPlazo = strPlazo;
               
	}

	/**Para datos del documento ----------------------------------------------------------------------------------------------*/
	public DocumentoDetail(Date strF, Integer iIdD, Integer iIdE,
			Integer iIdTD, Integer iNF, String strA, String strNC,
			String strND, String strO, String strP, String strR, String strRe,
			String strRo, String strTD, String strU) {
		setStrFecha(strF);
		setIIdDocumento(iIdD);
		setIIdExpediente(iIdE);
		setIIdTipoDocumento(iIdTD);
		setINroFolios(iNF);
		setStrAsunto(strA);
		setStrNroCaja(strNC);
		setStrNroDocumento(strND);
		setStrObservacion(strO);
		setStrProceso(strP);
		setStrReferencia(strR);
		setStrResponsable(strRe);
		setStrRol(strRo);
		setStrTipoDocumento(strTD);
		setStrUnidad(strU);
	}

	public DocumentoDetail(Date strF, Integer iIdD, Integer iIdE,
			Integer iIdTD, Integer iNF, String strA, String strNC,
			String strND, String strO, String strP, String strR, String strRe,
			String strRo, String strTD, String strU, String strCont) {
		setStrFecha(strF);
		setIIdDocumento(iIdD);
		setIIdExpediente(iIdE);
		setIIdTipoDocumento(iIdTD);
		setINroFolios(iNF);
		setStrAsunto(strA);
		setStrNroCaja(strNC);
		setStrNroDocumento(strND);
		setStrObservacion(strO);
		setStrProceso(strP);
		setStrReferencia(strR);
		setStrResponsable(strRe);
		setStrRol(strRo);
		setStrTipoDocumento(strTD);
		setStrUnidad(strU);
		setStrContenido(strCont);
	}

	/**Cuando se busca un expediente y se obtiene parte de la data del documento ---------------------------------------------*/
	public DocumentoDetail(Integer iIdE, Integer iIdP, Integer iIdR,
			String strP, String strRef, String strRes, String strU) {
		setIIdExpediente(iIdE);
		setIIdProceso(iIdP);
		setIIdResponsable(iIdR);
		setStrProceso(strP);
		setStrReferencia(strRef);
		setStrResponsable(strRes);
		setStrUnidad(strU);
		setStrUnidad(strU);
	}

	public Documento getDoc() {
		return doc;
	}

	public void setDoc(Documento doc) {
		this.doc = doc;
	}

	public Date getDateFechaLimiteAtencion() {
		return dateFechaLimiteAtencion;
	}

	public void setDateFechaLimiteAtencion(Date dateFechaLimiteAtencion) {
		this.dateFechaLimiteAtencion = dateFechaLimiteAtencion;
	}

	public String getStrAccion() {
		return strAccion;
	}

	public void setStrAccion(String strAccion) {
		this.strAccion = strAccion;
	}

	public Integer getStrPlazo() {
		return strPlazo;
	}

	public void setStrPlazo(Integer strPlazo) {
		this.strPlazo = strPlazo;
	}

	public String getStrHorasLimetesAtension() {
		return strHorasLimetesAtension;
	}

	public void setStrHorasLimetesAtension(String strHorasLimetesAtension) {
		this.strHorasLimetesAtension = strHorasLimetesAtension;
	}

	public String getStrDiaLimetesAtension() {
		return strDiaLimetesAtension;
	}

	public void setStrDiaLimetesAtension(String strDiaLimetesAtension) {
		this.strDiaLimetesAtension = strDiaLimetesAtension;
	}

	public Date getStrFecha() {
		return strFecha;
	}

	public void setStrFecha(Date strFecha) {
		this.strFecha = strFecha;
	}

	public Integer getIIdCorrentista() {
		return iIdCorrentista;
	}

	public void setIIdCorrentista(Integer iIdCorrentista) {
		this.iIdCorrentista = iIdCorrentista;
	}

	public Integer getIIdDestinatario() {
		return iIdDestinatario;
	}

	public void setIIdDestinatario(Integer iIdDestinatario) {
		this.iIdDestinatario = iIdDestinatario;
	}

	public Integer getIIdDocumento() {
		return iIdDocumento;
	}

	public void setIIdDocumento(Integer iIdDocumento) {
		this.iIdDocumento = iIdDocumento;
	}

	public Integer getIIdExpediente() {
		return iIdExpediente;
	}

	public void setIIdExpediente(Integer iIdExpediente) {
		this.iIdExpediente = iIdExpediente;
	}

	public Integer getIIdProceso() {
		return iIdProceso;
	}

	public void setIIdProceso(Integer iIdProceso) {
		this.iIdProceso = iIdProceso;
	}

	public Integer getIIdResponsable() {
		return iIdResponsable;
	}

	public void setIIdResponsable(Integer iIdResponsable) {
		this.iIdResponsable = iIdResponsable;
	}

	public Integer getIIdCliente() {
		return iIdCliente;
	}

	public void setIIdCliente(Integer iIdCliente) {
		this.iIdCliente = iIdCliente;
	}

	public Integer getIIdTipoDocumento() {
		return iIdTipoDocumento;
	}

	public void setIIdTipoDocumento(Integer iIdTipoDocumento) {
		this.iIdTipoDocumento = iIdTipoDocumento;
	}

	public Integer getIIdTipoIdentificacion() {
		return iIdTipoIdentificacion;
	}

	public void setIIdTipoIdentificacion(Integer iIdTipoIdentificacion) {
		this.iIdTipoIdentificacion = iIdTipoIdentificacion;
	}

	public Integer getIIdDepartamento() {
		return iIdDepartamento;
	}

	public void setIIdDepartamento(Integer iIdDepartamento) {
		this.iIdDepartamento = iIdDepartamento;
	}

	public Integer getIIdProvincia() {
		return iIdProvincia;
	}

	public void setIIdProvincia(Integer iIdProvincia) {
		this.iIdProvincia = iIdProvincia;
	}

	public Integer getIIdDistrito() {
		return iIdDistrito;
	}

	public void setIIdDistrito(Integer iIdDistrito) {
		this.iIdDistrito = iIdDistrito;
	}

	public Integer getINroFolios() {
		return iNroFolios;
	}

	public void setINroFolios(Integer iNroFolios) {
		this.iNroFolios = iNroFolios;
	}

	public String getStrAsunto() {
		return strAsunto;
	}

	public void setStrAsunto(String strAsunto) {
		this.strAsunto = strAsunto;
	}

	public String getsSumilla() {
		return sSumilla;
	}

	public void setsSumilla(String sSumilla) {
		this.sSumilla = sSumilla;
	}

	public String getStrAbreviado() {
		return strAbreviado;
	}

	public void setStrAbreviado(String strAbreviado) {
		this.strAbreviado = strAbreviado;
	}

	public String getStrContenido() {
		return strContenido;
	}

	public void setStrContenido(String strContenido) {
		this.strContenido = strContenido;
	}

	public String getStrCorrentista() {
		return strCorrentista;
	}

	public void setStrCorrentista(String strCorrentista) {
		this.strCorrentista = strCorrentista;
	}

	public String getStrCorreoCliente() {
		return strCorreoCliente;
	}

	public void setStrCorreoCliente(String strCorreoCliente) {
		this.strCorreoCliente = strCorreoCliente;
	}

	public String getStrCorreoConcesionario() {
		return strCorreoConcesionario;
	}

	public void setStrCorreoConcesionario(String strCorreoConcesionario) {
		this.strCorreoConcesionario = strCorreoConcesionario;
	}

	public String getStrDestinatario() {
		return strDestinatario;
	}

	public void setStrDestinatario(String strDestinatario) {
		this.strDestinatario = strDestinatario;
	}

	public String getStrDireccionAlternativa() {
		return strDireccionAlternativa;
	}

	public void setStrDireccionAlternativa(String strDireccionAlternativa) {
		this.strDireccionAlternativa = strDireccionAlternativa;
	}

	public String getStrDireccionConcesionario() {
		return strDireccionConcesionario;
	}

	public void setStrDireccionConcesionario(String strDireccionConcesionario) {
		this.strDireccionConcesionario = strDireccionConcesionario;
	}

	public String getStrDireccionPrincipal() {
		return strDireccionPrincipal;
	}

	public void setStrDireccionPrincipal(String strDireccionPrincipal) {
		this.strDireccionPrincipal = strDireccionPrincipal;
	}

	public String getStrNroCaja() {
		return strNroCaja;
	}

	public void setStrNroCaja(String strNroCaja) {
		this.strNroCaja = strNroCaja;
	}

	public String getStrNroDocumento() {
		return strNroDocumento;
	}

	public void setStrNroDocumento(String strNroDocumento) {
		this.strNroDocumento = strNroDocumento;
	}

	public String getStrNroIdentificacion() {
		return strNroIdentificacion;
	}

	public void setStrNroIdentificacion(String strNroIdentificacion) {
		this.strNroIdentificacion = strNroIdentificacion;
	}

	public String getStrNroMesaPartes() {
		return strNroMesaPartes;
	}

	public void setStrNroMesaPartes(String strNroMesaPartes) {
		this.strNroMesaPartes = strNroMesaPartes;
	}

	public String getStrObservacion() {
		return strObservacion;
	}

	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}

	public String getStrProceso() {
		return strProceso;
	}

	public void setStrProceso(String strProceso) {
		this.strProceso = strProceso;
	}

	public String getStrRazonSocial() {
		return strRazonSocial;
	}

	public void setStrRazonSocial(String strRazonSocial) {
		this.strRazonSocial = strRazonSocial;
	}

	public String getStrReferencia() {
		return strReferencia;
	}

	public void setStrReferencia(String strReferencia) {
		this.strReferencia = strReferencia;
	}

	public String getStrRemitente() {
		return strRemitente;
	}

	public void setStrRemitente(String strRemitente) {
		this.strRemitente = strRemitente;
	}

	public String getStrRepresentanteLegal() {
		return strRepresentanteLegal;
	}

	public void setStrRepresentanteLegal(String strRepresentanteLegal) {
		this.strRepresentanteLegal = strRepresentanteLegal;
	}

	public String getStrResponsable() {
		return strResponsable;
	}

	public void setStrResponsable(String strResponsable) {
		this.strResponsable = strResponsable;
	}

	public String getStrRol() {
		return strRol;
	}

	public void setStrRol(String strRol) {
		this.strRol = strRol;
	}

	public String getStrRUC() {
		return strRUC;
	}

	public void setStrRUC(String strRUC) {
		this.strRUC = strRUC;
	}

	public String getStrTelefonoCliente() {
		return strTelefonoCliente;
	}

	public void setStrTelefonoCliente(String strTelefonoCliente) {
		this.strTelefonoCliente = strTelefonoCliente;
	}

	public String getStrTelefonoConcesionario() {
		return strTelefonoConcesionario;
	}

	public void setStrTelefonoConcesionario(String strTelefonoConcesionario) {
		this.strTelefonoConcesionario = strTelefonoConcesionario;
	}

	public String getStrTipoDocumento() {
		return strTipoDocumento;
	}

	public void setStrTipoDocumento(String strTipoDocumento) {
		this.strTipoDocumento = strTipoDocumento;
	}

	public String getStrTipoIdentificacion() {
		return strTipoIdentificacion;
	}

	public void setStrTipoIdentificacion(String strTipoIdentificacion) {
		this.strTipoIdentificacion = strTipoIdentificacion;
	}

	public String getStrUnidad() {
		return strUnidad;
	}

	public void setStrUnidad(String strUnidad) {
		this.strUnidad = strUnidad;
	}

	public String getStrDepartamento() {
		return strDepartamento;
	}

	public void setStrDepartamento(String strDepartamento) {
		this.strDepartamento = strDepartamento;
	}

	public String getStrProvincia() {
		return strProvincia;
	}

	public void setStrProvincia(String strProvincia) {
		this.strProvincia = strProvincia;
	}

	public String getStrDistrito() {
		return strDistrito;
	}

	public void setStrDistrito(String strDistrito) {
		this.strDistrito = strDistrito;
	}

	public Integer getIIdMotivo() {
		return iIdMotivo;
	}

	public void setIIdMotivo(Integer iIdMotivo) {
		this.iIdMotivo = iIdMotivo;
	}

	public String getStrSala() {
		return strSala;
	}

	public void setStrSala(String strSala) {
		this.strSala = strSala;
	}

	public Integer getIIdSubMotivo() {
		return iIdSubMotivo;
	}

	public void setIIdSubMotivo(Integer iIdSubMotivo) {
		this.iIdSubMotivo = iIdSubMotivo;
	}

	public String getStrMotivo() {
		return strMotivo;
	}

	public void setStrMotivo(String strMotivo) {
		this.strMotivo = strMotivo;
	}

	public String getStrMontoReclamo() {
		return strMontoReclamo;
	}

	public void setStrMontoReclamo(String strMontoReclamo) {
		this.strMontoReclamo = strMontoReclamo;
	}

	public String getStrTipoAnalisis() {
		return strTipoAnalisis;
	}

	public void setStrTipoAnalisis(String strTipoAnalisis) {
		this.strTipoAnalisis = strTipoAnalisis;
	}

	public Integer getIIdSala() {
		return iIdSala;
	}

	public void setIIdSala(Integer iIdSala) {
		this.iIdSala = iIdSala;
	}

	public Integer getIIdEtapa() {
		return iIdEtapa;
	}

	public void setIIdEtapa(Integer iIdEtapa) {
		this.iIdEtapa = iIdEtapa;
	}

	public Integer getINroResolucion() {
		return iNroResolucion;
	}

	public void setINroResolucion(Integer iNroResolucion) {
		this.iNroResolucion = iNroResolucion;
	}

	public String getStrRevisorTecnico() {
		return strRevisorTecnico;
	}

	public void setStrRevisorTecnico(String strRevisorTecnico) {
		this.strRevisorTecnico = strRevisorTecnico;
	}

	public String getStrRevisorLegal() {
		return strRevisorLegal;
	}

	public void setStrRevisorLegal(String strRevisorLegal) {
		this.strRevisorLegal = strRevisorLegal;
	}

	public String getStrAnalista() {
		return strAnalista;
	}

	public void setStrAnalista(String strAnalista) {
		this.strAnalista = strAnalista;
	}

	public String getStrResultado() {
		return strResultado;
	}

	public void setStrResultado(String strResultado) {
		this.strResultado = strResultado;
	}

	public String getStrFechaAprobacion() {
		return strFechaAprobacion;
	}

	public void setStrFechaAprobacion(String strFechaAprobacion) {
		this.strFechaAprobacion = strFechaAprobacion;
	}

	public String getStrFechaReclamante() {
		return strFechaReclamante;
	}

	public void setStrFechaReclamante(String strFechaReclamante) {
		this.strFechaReclamante = strFechaReclamante;
	}

	public String getStrFechaConcesionario() {
		return strFechaConcesionario;
	}

	public void setStrFechaConcesionario(String strFechaConcesionario) {
		this.strFechaConcesionario = strFechaConcesionario;
	}

	public String getStrFechaResolucion() {
		return strFechaResolucion;
	}

	public void setStrFechaResolucion(String strFechaResolucion) {
		this.strFechaResolucion = strFechaResolucion;
	}

	public String getStrFechaLimiteAtencion() {
		return strFechaLimiteAtencion;
	}

	public void setStrFechaLimiteAtencion(String strFechaLimiteAtencion) {
		this.strFechaLimiteAtencion = strFechaLimiteAtencion;
	}

	public char getCDisponible() {
		return cDisponible;
	}

	public void setCDisponible(char cDisponible) {
		this.cDisponible = cDisponible;
	}

	public char getCEstado() {
		return cEstado;
	}

	public void setCEstado(char cEstado) {
		this.cEstado = cEstado;
	}

	public String getSRequiereVerificacion() {
		return sRequiereVerificacion;
	}

	public void setSRequiereVerificacion(String sRequiereVerificacion) {
		this.sRequiereVerificacion = sRequiereVerificacion;
	}

	public Integer getIIdCumpleRequisito() {
		return iIdCumpleRequisito;
	}

	public void setIIdCumpleRequisito(Integer iIdCumpleRequisito) {
		this.iIdCumpleRequisito = iIdCumpleRequisito;
	}

	public String getStrFechaDocumento() {
		return strFechaDocumento;
	}

	public void setStrFechaDocumento(String strFechaDocumento) {
		this.strFechaDocumento = strFechaDocumento;
	}

	public char getCPrincipal() {
		return cPrincipal;
	}

	public void setCPrincipal(char cPrincipal) {
		this.cPrincipal = cPrincipal;
	}

	public boolean isDelegado() {
		return delegado;
	}

	public void setDelegado(boolean delegado) {
		this.delegado = delegado;
	}

	public Usuario getUsuarioDelegado() {
		return usuarioDelegado;
	}

	public void setUsuarioDelegado(Usuario usuarioDelegado) {
		this.usuarioDelegado = usuarioDelegado;
	}

	public String[] getArrSuministro() {
		return arrSuministro;
	}

	public void setArrSuministro(String[] arrSuministro) {
		this.arrSuministro = arrSuministro;
	}

	public Integer[] getArrIdSubMotivo() {
		return arrIdSubMotivo;
	}

	public void setArrIdSubMotivo(Integer[] arrIdSubMotivo) {
		this.arrIdSubMotivo = arrIdSubMotivo;
	}

	public String getIPlazoDia() {
		return iPlazoDia;
	}

	public void setIPlazoDia(String iPlazoDia) {
		this.iPlazoDia = iPlazoDia;
	}

	public Integer getIPlazoHora() {
		return iPlazoHora;
	}

	public void setIPlazoHora(Integer iPlazoHora) {
		this.iPlazoHora = iPlazoHora;
	}

	public String getStrTexto() {
		return strTexto;
	}

	public void setStrTexto(String strTexto) {
		this.strTexto = strTexto;
	}

	public String getSObservacionRechazo() {
		return sObservacionRechazo;
	}

	public void setSObservacionRechazo(String sObservacionRechazo) {
		this.sObservacionRechazo = sObservacionRechazo;
	}

	public Date getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public String getClienterazonsocial() {
		return clienterazonsocial;
	}

	public void setClienterazonsocial(String clienterazonsocial) {
		this.clienterazonsocial = clienterazonsocial;
	}

	public String getClienterepresentantelegal() {
		return clienterepresentantelegal;
	}

	public void setClienterepresentantelegal(String clienterepresentantelegal) {
		this.clienterepresentantelegal = clienterepresentantelegal;
	}

	public String getClientedireccionprincipal() {
		return clientedireccionprincipal;
	}

	public void setClientedireccionprincipal(String clientedireccionprincipal) {
		this.clientedireccionprincipal = clientedireccionprincipal;
	}

	public Integer getClienteubigeoprincipal() {
		return clienteubigeoprincipal;
	}

	public void setClienteubigeoprincipal(Integer clienteubigeoprincipal) {
		this.clienteubigeoprincipal = clienteubigeoprincipal;
	}

	public String getClientedireccionalternativa() {
		return clientedireccionalternativa;
	}

	public void setClientedireccionalternativa(String clientedireccionalternativa) {
		this.clientedireccionalternativa = clientedireccionalternativa;
	}

	public Integer getClienteubigeoalternativo() {
		return clienteubigeoalternativo;
	}

	public void setClienteubigeoalternativo(Integer clienteubigeoalternativo) {
		this.clienteubigeoalternativo = clienteubigeoalternativo;
	}

	public String getClientetelefono() {
		return clientetelefono;
	}

	public void setClientetelefono(String clientetelefono) {
		this.clientetelefono = clientetelefono;
	}

	public String getClientecorreo() {
		return clientecorreo;
	}

	public void setClientecorreo(String clientecorreo) {
		this.clientecorreo = clientecorreo;
	}

	public String getClientenombres() {
		return clientenombres;
	}

	public void setClientenombres(String clientenombres) {
		this.clientenombres = clientenombres;
	}

	public String getClienteapellidopaterno() {
		return clienteapellidopaterno;
	}

	public void setClienteapellidopaterno(String clienteapellidopaterno) {
		this.clienteapellidopaterno = clienteapellidopaterno;
	}

	public String getClienteapellidomaterno() {
		return clienteapellidomaterno;
	}

	public void setClienteapellidomaterno(String clienteapellidomaterno) {
		this.clienteapellidomaterno = clienteapellidomaterno;
	}

	public String getsDepartamentoAlt() {
		return sDepartamentoAlt;
	}

	public void setsDepartamentoAlt(String sDepartamentoAlt) {
		this.sDepartamentoAlt = sDepartamentoAlt;
	}

	public String getsProvinciaAlt() {
		return sProvinciaAlt;
	}

	public void setsProvinciaAlt(String sProvinciaAlt) {
		this.sProvinciaAlt = sProvinciaAlt;
	}

	public String getsDistritoAlt() {
		return sDistritoAlt;
	}

	public void setsDistritoAlt(String sDistritoAlt) {
		this.sDistritoAlt = sDistritoAlt;
	}

	public Integer getiIdDepartamentoAlt() {
		return iIdDepartamentoAlt;
	}

	public void setiIdDepartamentoAlt(Integer iIdDepartamentoAlt) {
		this.iIdDepartamentoAlt = iIdDepartamentoAlt;
	}

	public Integer getiIdProvinciaAlt() {
		return iIdProvinciaAlt;
	}

	public void setiIdProvinciaAlt(Integer iIdProvinciaAlt) {
		this.iIdProvinciaAlt = iIdProvinciaAlt;
	}

	public Integer getiIdDistritoAlt() {
		return iIdDistritoAlt;
	}

	public void setiIdDistritoAlt(Integer iIdDistritoAlt) {
		this.iIdDistritoAlt = iIdDistritoAlt;
	}

	public String getsPropietarioExpediente() {
		return sPropietarioExpediente;
	}

	public void setsPropietarioExpediente(String sPropietarioExpediente) {
		this.sPropietarioExpediente = sPropietarioExpediente;
	}

	public Character getEstaenflujo() {
		return estaenflujo;
	}

	public void setEstaenflujo(Character estaenflujo) {
		this.estaenflujo = estaenflujo;
	}

	public String getsNroExpediente() {
		return sNroExpediente;
	}

	public void setsNroExpediente(String sNroExpediente) {
		this.sNroExpediente = sNroExpediente;
	}

	public Character getHistorico() {
		return historico;
	}

	public void setHistorico(Character historico) {
		this.historico = historico;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Integer getIdFirmante() {
		return idFirmante;
	}

	public void setIdFirmante(Integer idFirmante) {
		this.idFirmante = idFirmante;
	}

	public Integer getiIdRemitente() {
		return iIdRemitente;
	}

	public void setiIdRemitente(Integer iIdRemitente) {
		this.iIdRemitente = iIdRemitente;
	}

	public Integer getiIdExpedienteClienteUbigeoPrincipal() {
		return iIdExpedienteClienteUbigeoPrincipal;
	}

	public void setiIdExpedienteClienteUbigeoPrincipal(Integer iIdExpedienteClienteUbigeoPrincipal) {
		this.iIdExpedienteClienteUbigeoPrincipal = iIdExpedienteClienteUbigeoPrincipal;
	}

	public Integer getiIdExpedienteClienteUbigeoAlternativo() {
		return iIdExpedienteClienteUbigeoAlternativo;
	}

	public void setiIdExpedienteClienteUbigeoAlternativo(Integer iIdExpedienteClienteUbigeoAlternativo) {
		this.iIdExpedienteClienteUbigeoAlternativo = iIdExpedienteClienteUbigeoAlternativo;
	}

	public Integer[] getIdSuministros() {
		return idSuministros;
	}

	public void setIdSuministros(Integer[] idSuministros) {
		this.idSuministros = idSuministros;
	}

	public String[] getSuministros() {
		return suministros;
	}

	public void setSuministros(String[] suministros) {
		this.suministros = suministros;
	}

	public Integer[] getIdSubmotivos() {
		return idSubmotivos;
	}

	public void setIdSubmotivos(Integer[] idSubmotivos) {
		this.idSubmotivos = idSubmotivos;
	}

	public List<String> getCondestinatarios() {
		return condestinatarios;
	}

	public void setCondestinatarios(List<String> condestinatarios) {
		this.condestinatarios = condestinatarios;
	}

	public List<String> getConcopias() {
		return concopias;
	}

	public void setConcopias(List<String> concopias) {
		this.concopias = concopias;
	}

	public String getEnumerarDocumento() {
		return enumerarDocumento;
	}

	public void setEnumerarDocumento(String enumerarDocumento) {
		this.enumerarDocumento = enumerarDocumento;
	}

	public String getTipoNumeracion() {
		return tipoNumeracion;
	}

	public void setTipoNumeracion(String tipoNumeracion) {
		this.tipoNumeracion = tipoNumeracion;
	}

	public String getUltimoAsunto() {
		return ultimoAsunto;
	}

	public void setUltimoAsunto(String ultimoAsunto) {
		this.ultimoAsunto = ultimoAsunto;
	}

	public Integer getIdUsuarioLogeado() {
		return idUsuarioLogeado;
	}

	public void setIdUsuarioLogeado(Integer idUsuarioLogeado) {
		this.idUsuarioLogeado = idUsuarioLogeado;
	}

	public String getStrFechaAccion() {
		return strFechaAccion;
	}

	public void setStrFechaAccion(String strFechaAccion) {
		this.strFechaAccion = strFechaAccion;
	}

	public String getParaAprobar() {
		return paraAprobar;
	}

	public void setParaAprobar(String paraAprobar) {
		this.paraAprobar = paraAprobar;
	}

	public String getObservacionDocumento() {
		return observacionDocumento;
	}

	public void setObservacionDocumento(String observacionDocumento) {
		this.observacionDocumento = observacionDocumento;
	}

	public String getObservacionExpediente() {
		return observacionExpediente;
	}

	public void setObservacionExpediente(String observacionExpediente) {
		this.observacionExpediente = observacionExpediente;
	}

	public Integer getiIdAccion() {
		return iIdAccion;
	}

	public void setiIdAccion(Integer iIdAccion) {
		this.iIdAccion = iIdAccion;
	}

	public String getObservacionDigitalizador() {
		return observacionDigitalizador;
	}

	public void setObservacionDigitalizador(String observacionDigitalizador) {
		this.observacionDigitalizador = observacionDigitalizador;
	}

	public String getStrObservacionDoc() {
		return strObservacionDoc;
	}

	public void setStrObservacionDoc(String strObservacionDoc) {
		this.strObservacionDoc = strObservacionDoc;
	}

	public Integer getIdEnumerador() {
		return idEnumerador;
	}

	public void setIdEnumerador(Integer idEnumerador) {
		this.idEnumerador = idEnumerador;
	}

	public Usuario getPropietario() {
		return propietario;
	}

	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
	}

	public String getCodProceso() {
		return codProceso;
	}

	public void setCodProceso(String codProceso) {
		this.codProceso = codProceso;
	}

	public Usuario getObjAutor() {
		return objAutor;
	}

	public void setObjAutor(Usuario objAutor) {
		this.objAutor = objAutor;
		this.autor = objAutor.getNombreCompleto();
	}

	public Integer getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	public String getAsuntoExpediente() {
		return asuntoExpediente;
	}

	public void setAsuntoExpediente(String asuntoExpediente) {
		this.asuntoExpediente = asuntoExpediente;
	}

	public Character getConfidencial() {
		return confidencial;
	}

	public void setConfidencial(Character confidencial) {
		this.confidencial = confidencial;
	}

	public String getStrSinPlazo() {
		return strSinPlazo;
	}

	public void setStrSinPlazo(String strSinPlazo) {
		this.strSinPlazo = strSinPlazo;
	}

	public String getAprobarIG() {
		return aprobarIG;
	}

	public void setAprobarIG(String aprobarIG) {
		this.aprobarIG = aprobarIG;
	}

	  public String getRemitente() {
            return remitente;
        }

        public void setRemitente(String remitente) {
            this.remitente = remitente;
        }

        public String getCargoRemitente() {
            return cargoRemitente;
        }

        public void setCargoRemitente(String cargoRemitente) {
            this.cargoRemitente = cargoRemitente;
        }
        
        public String getOpcion() {
           return opcion;
        }

        public void setOpcion(String opcion) {
            this.opcion = opcion;
        }
        
         public String getTipoTransaccion() {
            return tipoTransaccion;
        }

        public void setTipoTransaccion(String tipoTransaccion) {
            this.tipoTransaccion = tipoTransaccion;
        }
        
         public String getListReferenciados() {
        return listReferenciados;
        }

        public void setListReferenciados(String listReferenciados) {
            this.listReferenciados = listReferenciados;
        }
        
        public Integer getIdTipoEmpresa() {
          return idTipoEmpresa;
        }

        public void setIdTipoEmpresa(Integer idTipoEmpresa) {
           this.idTipoEmpresa = idTipoEmpresa;
        }
        
        public Integer getiIdEmpresa() {
          return iIdEmpresa;
        }

        public void setiIdEmpresa(Integer iIdEmpresa) {
          this.iIdEmpresa = iIdEmpresa;
        }
        public String getCodCargoPersonaInstitucion() {
          return codCargoPersonaInstitucion;
        }

        public void setCodCargoPersonaInstitucion(String codCargoPersonaInstitucion) {
           this.codCargoPersonaInstitucion = codCargoPersonaInstitucion;
        }

        public String getIdPersonaInstitucion() {
            return idPersonaInstitucion;
        }

        public void setIdPersonaInstitucion(String idPersonaInstitucion) {
           this.idPersonaInstitucion = idPersonaInstitucion;
        }

        public String getCodCargoPersonaEmpresa() {
            return codCargoPersonaEmpresa;
        }

        public void setCodCargoPersonaEmpresa(String codCargoPersonaEmpresa) {
           this.codCargoPersonaEmpresa = codCargoPersonaEmpresa;
        }

        public String getIdPersonaEmpresa() {
           return idPersonaEmpresa;
        }

        public void setIdPersonaEmpresa(String idPersonaEmpresa) {
            this.idPersonaEmpresa = idPersonaEmpresa;
        }

        
        public String getCodCargoPersonaNatural() {
            return codCargoPersonaNatural;
        }

        public void setCodCargoPersonaNatural(String codCargoPersonaNatural) {
            this.codCargoPersonaNatural = codCargoPersonaNatural;
        }

        public Integer getIdPersonaNatural() {
            return idPersonaNatural;
        }

        public void setIdPersonaNatural(Integer idPersonaNatural) {
            this.idPersonaNatural = idPersonaNatural;
        }
        
        public String getIdTipoCliente() {
            return idTipoCliente;
        }

        public void setIdTipoCliente(String idTipoCliente) {
            this.idTipoCliente = idTipoCliente;
        }

        public String getIdInternoExterno() {
            return idInternoExterno;
        }

        public void setIdInternoExterno(String idInternoExterno) {
            this.idInternoExterno = idInternoExterno;
        }

        public Integer getIdTipoInstitucion() {
            return idTipoInstitucion;
        }

        public void setIdTipoInstitucion(Integer idTipoInstitucion) {
            this.idTipoInstitucion = idTipoInstitucion;
        }

        public Integer getiIdInstitucion() {
            return iIdInstitucion;
        }

        public void setiIdInstitucion(Integer iIdInstitucion) {
            this.iIdInstitucion = iIdInstitucion;
        }

	public String getTupa() {
		return tupa;
	}

	public void setTupa(String tupa) {
		this.tupa = tupa;
	}

	public String getNroDocumentoReferenciado() {
		return nroDocumentoReferenciado;
	}

	public void setNroDocumentoReferenciado(String nroDocumentoReferenciado) {
		this.nroDocumentoReferenciado = nroDocumentoReferenciado;
	}

	public Integer getIdCodigo() {
	   return idCodigo;
	}

	public void setIdCodigo(Integer idCodigo) {
	   this.idCodigo = idCodigo;
	}

	private String strRespuesta;

	public String getStrRespuesta() {
	   return strRespuesta;
	}

	public void setStrRespuesta(String strRespuesta) {
	   this.strRespuesta = strRespuesta;
	}
        
        public String getRaiz() {
	   return raiz;
	}

	public void setRaiz(String raiz) {
	   this.raiz = raiz;
	}

	public String getListDoc() {
	   return listDoc;
	}

	public void setListDoc(String listDoc) {
	    this.listDoc = listDoc;
	}
        
        public String getTramite() {
	    return tramite;
	}

	public void setTramite(String tramite) {
	    this.tramite = tramite;
	}
        
        public Integer getIdAreaDerivada() {
	    return idAreaDerivada;
	}

	public void setIdAreaDerivada(Integer idAreaDerivada) {
	    this.idAreaDerivada = idAreaDerivada;
	}
        
         public String getListAtendidos() {
            return listAtendidos;
        }

        public void setListAtendidos(String listAtendidos) {
            this.listAtendidos = listAtendidos;
        }
        public Integer getiIdSerie() {
            return iIdSerie;
        }

        public void setiIdSerie(Integer iIdSerie) {
            this.iIdSerie = iIdSerie;
        }
        
        public Integer getiIdMateria() {
            return iIdMateria;
        }

        public void setiIdMateria(Integer iIdMateria) {
            this.iIdMateria = iIdMateria;
        }

        public Integer getiIdInfraestructura() {
            return iIdInfraestructura;
        }

        public void setiIdInfraestructura(Integer iIdInfraestructura) {
            this.iIdInfraestructura = iIdInfraestructura;
        }

        public Integer getIdInstitucionSicor() {
            return idInstitucionSicor;
        }

        public void setIdInstitucionSicor(Integer idInstitucionSicor) {
            this.idInstitucionSicor = idInstitucionSicor;
        }

        public String getStrLugar() {
            return strLugar;
        }

        public void setStrLugar(String strLugar) {
            this.strLugar = strLugar;
        }

        public String getStrObjetivo() {
            return strObjetivo;
        }

        public void setStrObjetivo(String strObjetivo) {
            this.strObjetivo = strObjetivo;
        }

        public String getListaIntegrantesInternos() {
            return listaIntegrantesInternos;
        }

        public void setListaIntegrantesInternos(String listaIntegrantesInternos) {
            this.listaIntegrantesInternos = listaIntegrantesInternos;
        }

        public String getListaIntegrantesExternos() {
            return listaIntegrantesExternos;
        }

        public void setListaIntegrantesExternos(String listaIntegrantesExternos) {
            this.listaIntegrantesExternos = listaIntegrantesExternos;
        }
        
        public String getStrFechaReunion() {
            return strFechaReunion;
        }

        public void setStrFechaReunion(String strFechaReunion) {
            this.strFechaReunion = strFechaReunion;
        }
        
        public String getStrHoraReunion() {
            return strHoraReunion;
        }

        public void setStrHoraReunion(String strHoraReunion) {
            this.strHoraReunion = strHoraReunion;
        }
        
        public String getStrPrioridad() {
            return strPrioridad;
        }

        public void setStrPrioridad(String strPrioridad) {
            this.strPrioridad = strPrioridad;
        }
        
        public Integer getiNroFoliosOriginales() {
           return iNroFoliosOriginales;
        }

        public void setiNroFoliosOriginales(Integer iNroFoliosOriginales) {
            this.iNroFoliosOriginales = iNroFoliosOriginales;
        }

        public Integer getiNroFoliosCopias() {
            return iNroFoliosCopias;
        }

        public void setiNroFoliosCopias(Integer iNroFoliosCopias) {
            this.iNroFoliosCopias = iNroFoliosCopias;
        }

        public Integer getiNroFoliosDigitalizados() {
            return iNroFoliosDigitalizados;
        }

        public void setiNroFoliosDigitalizados(Integer iNroFoliosDigitalizados) {
            this.iNroFoliosDigitalizados = iNroFoliosDigitalizados;
        }
        
         public String getOpcionMenu() {
            return opcionMenu;
        }

        public void setOpcionMenu(String opcionMenu) {
            this.opcionMenu = opcionMenu;
        }
        
         public Integer getiIdLegajoOrigen() {
            return iIdLegajoOrigen;
        }

        public void setiIdLegajoOrigen(Integer iIdLegajoOrigen) {
            this.iIdLegajoOrigen = iIdLegajoOrigen;
        }
        
        public Legajo getLegajo() {
            return legajo;
        }

        public void setLegajo(Legajo legajo) {
            this.legajo = legajo;
        }
        
       public char getFlagCodigoVirtual() {
            return flagCodigoVirtual;
        }

        public void setFlagCodigoVirtual(char flagCodigoVirtual) {
            this.flagCodigoVirtual = flagCodigoVirtual;
        }
        
        public Integer getiIdDocumentoReferencia() {
            return iIdDocumentoReferencia;
        }

        public void setiIdDocumentoReferencia(Integer iIdDocumentoReferencia) {
            this.iIdDocumentoReferencia = iIdDocumentoReferencia;
        }
        public String getArchivoCargo() {
            return archivoCargo;
        }

        public void setArchivoCargo(String archivoCargo) {
            this.archivoCargo = archivoCargo;
        }
        
        public Integer getiNroFoliosPIDE() {
            return iNroFoliosPIDE;
        }

        public void setiNroFoliosPIDE(Integer iNroFoliosPIDE) {
            this.iNroFoliosPIDE = iNroFoliosPIDE;
        }
        
        public String getTamanoPrincipal() {
            return tamanoPrincipal;
        }

        public void setTamanoPrincipal(String tamanoPrincipal) {
            this.tamanoPrincipal = tamanoPrincipal;
        }

        public String getTamanoCargo() {
            return tamanoCargo;
        }

        public void setTamanoCargo(String tamanoCargo) {
            this.tamanoCargo = tamanoCargo;
        }
        
        public String getTamanoFormateado(String tamano) {
            if (tamano == null) return "";
            
            DecimalFormat df = new DecimalFormat("#.00");
            double valorsito = (double) new Integer(tamano)/1024;
            
            if (valorsito<1) return "1 KB";
            return String.valueOf(df.format(valorsito)) + " KB";
           
        }
}