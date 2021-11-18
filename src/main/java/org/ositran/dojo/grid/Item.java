/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.dojo.grid;

import java.util.Date;

import org.ositran.utils.Constantes;

public class Item { 
        private String archivos; 
        private int nroTramite;
        private String idArchivo;
        private String recepcionado;
	private Date fecharecepcion;
	private Character leido;
	private Integer id;
        private String externo;
        private String idPropietario;
        private String iconoDocumento;
	private String asunto;
	private String remitente;
	private String documento;
	private String concesionario;
	private String urlarchivo;
	private String tipoalerta;
	private Date fechalimite;
	private String expediente;
        private String legajo;
	private String proceso;
	private String cliente;
	private Date fechaexpediente;
        private Date fechaatender;
        private Date fechaanulacion;
        private String principal;
        private String llave;
        private Date fechaenvio;
	private long lfecharecepcion;
	private long lfechalimite;
	private long lfechaactual;
	// para Mensajeria???
	private String usuario;
	private String nrointerno;
	private String tipodocumento;
	private String destinatario;
        private String idDestinatario;

	private String estado;
	private String etapa;
	// para Carpeta busqueda???
	private String area;
	private String asuntoExpediente;
	private String asuntodocumento;
	private Character estadoexpediente;
	// para Proceso???
	private String nombre;
	private Integer tiempoatencion;
	// para Reemplazo???
	private Date fechainicio;
	private Date fechafin;
	private String usuarioreemplazante;
	// para Busqueda Simple???
	private String nrodocumento;
	private Integer idproceso;
	private String tiponotificacion;
	// para Campos
	private String codigo;
	private String descripcion;
	private String tipo;
	private String valor;
	private String propietario;
	private String numeroMesaPartes;
	private Character historico;
	// para Listas
	private Date fechacreacion;
	// para Mantenimiento de Reemplazos
	private String reemplazado;
	private String reemplazante;
	// para manejo de Alertas
	private String porcentajealertaA;
	private String porcentajealertaR;
	// para manejo de Mensajeria
	private String identificacion;
	private String razonsocial;
	private String direccion;
	private String departamento;
	private String provincia;
	private String distrito;
	// para manejo de Reportes Supervisor
	private String horarioReporte;
	private Integer reporteMP;
	private Integer reporteDIG;
	private Integer reporteQAS;

	private Integer reporteFolios;
	private String responsable;
	private String correo;
	private String representante;
	private String direccionAlternativa;
	private String departamentoAlternativo;
	private String provinciaAlternativa;
	private String distritoAlternativo;
	private String ubigeo;
	private String ubigeoAlternativo;
	private String telefono;
	private String motivo;
	private String submotivo;
	private String monto;
	private String actividad;

   	private String accion;
	// para STOR
	private String nroSuministro;
        private Integer codTipoInstitucion;
        private int origen;
        private String firma;
        
        private String asuntolegajo;
        private String tipolegajo;
        private String arealegajo;
        private String autor;
        private String idProcedimiento;
        private String idMetodo;
        
       

        public String getIdProcedimiento() {
            return idProcedimiento;
        }

        public void setIdProcedimiento(String idProcedimiento) {
            this.idProcedimiento = idProcedimiento;
        }

        public String getIdMetodo() {
            return idMetodo;
        }

        public void setIdMetodo(String idMetodo) {
            this.idMetodo = idMetodo;
        }
        
        public String getIconoDocumento() {
            return iconoDocumento;
        }

        public void setIconoDocumento(String iconoDocumento) {
            this.iconoDocumento = iconoDocumento;
        }
        
        public String getExterno() {
           return externo;
        }

        public void setExterno(String externo) {
            this.externo = externo;
        }
        
        public String getIdDestinatario() {
            return idDestinatario;
        }

        public void setIdDestinatario(String idDestinatario) {
            this.idDestinatario = idDestinatario;
        }

        public void setAutor(String autor) {
            this.autor = autor;
        }

        public String getAutor() {
            return autor;
        }
        
        public String getIdPropietario() {
            return idPropietario;
        }

        public void setIdPropietario(String idPropietario) {
            this.idPropietario = idPropietario;
        }

        public String getAsuntolegajo() {
            return asuntolegajo;
        }

        public void setAsuntolegajo(String asuntolegajo) {
            this.asuntolegajo = asuntolegajo;
        }

        public String getTipolegajo() {
            return tipolegajo;
        }

        public void setTipolegajo(String tipolegajo) {
            this.tipolegajo = tipolegajo;
        }

        public String getArealegajo() {
            return arealegajo;
        }

        public void setArealegajo(String arealegajo) {
            this.arealegajo = arealegajo;
        }
        
         
        public String getLegajo() {
            return legajo;
        }

        public void setLegajo(String legajo) {
            this.legajo = legajo;
        }
        
        
        public String getArchivos() {
            return archivos;
        }

        public void setArchivos(String archivos) {
            this.archivos = archivos;
        }
        
        public String getPrincipal() {
            return principal;
        }

        public void setPrincipal(String principal) {
            this.principal = principal;
        }
    
        
        public String getFirma() {
            return firma;
        }

        public void setFirma(String firma) {
            this.firma = firma;
        }


        public int getOrigen() {
            return origen;
        }

        public void setOrigen(int origen) {
            this.origen = origen;
        }

	/*
	 * Constructors
	 */
        
         public Date getFechaenvio() {
            return fechaenvio;
        }

        public void setFechaenvio(Date fechaenvio) {
            this.fechaenvio = fechaenvio;
        }

        public Integer getCodTipoInstitucion() {
            return codTipoInstitucion;
        }

        public void setCodTipoInstitucion(Integer codTipoInstitucion) {
            this.codTipoInstitucion = codTipoInstitucion;
        }

	//

	private String ambitoEnvio;
	private String tipoEnvio;
	private String referencia;
	//

	private String iddocumento;
        private String areadestino;

	private String prioridad;

	// para consulta
	private String areaorigen;
    private Integer documentoReferencia;
    private String excprioridad;
    
    
    //para adjuntos
    
        private String idId;
        private String tipoAdjunto;
        private String desTipo;
        private String desOrigCop;
        private String origcop;
        private String nro;
            

        public String getIdId() {
            return idId;
        }

        public void setIdId(String idId) {
            this.idId = idId;
        }

        public String getTipoAdjunto() {
            return tipoAdjunto;
        }

        public void setTipoAdjunto(String tipoAdjunto) {
            this.tipoAdjunto = tipoAdjunto;
        }

        public String getDesTipo() {
            return desTipo;
        }

        public void setDesTipo(String desTipo) {
            this.desTipo = desTipo;
        }

        public String getDesOrigCop() {
            return desOrigCop;
        }

        public void setDesOrigCop(String desOrigCop) {
            this.desOrigCop = desOrigCop;
        }

        public String getOrigcop() {
            return origcop;
        }

        public void setOrigcop(String origcop) {
            this.origcop = origcop;
        }

        public String getNro() {
            return nro;
        }

        public void setNro(String nro) {
            this.nro = nro;
        }
        
         public String getIdArchivo() {
            return idArchivo;
        }

        public void setIdArchivo(String idArchivo) {
            this.idArchivo = idArchivo;
        }
        
             
	public Item() {
	}

	// Expedientes
	public Item(Integer iIdExpediente, String sNroExpediente,
			Date dFechaCreacion, String sAsunto, String sProceso,
			String sClienteTipoIdentificacion, String sRazonSocial1,
			String sNombres1, String sApellidoPaterno1,
			String sApellidoMaterno1, String sRazonSocial2, String sNombres2,
			String sApellidoPaterno2, String sApellidoMaterno2,
			Date dFechaLimite, Character estado, String propietarioNombres,
			String propietarioApellidos, String responsableNombres,
			String responsableApellidos) {
            
               this.id = iIdExpediente;
		this.expediente = sNroExpediente;
		this.fechaexpediente = dFechaCreacion;
		this.asunto = sAsunto;
		this.proceso = sProceso;
		this.cliente = (sClienteTipoIdentificacion != null
				&& sClienteTipoIdentificacion
						.equalsIgnoreCase(Constantes.TIPO_IDENTIFICACION_RUC) ? (sRazonSocial1 == null ? sRazonSocial2
				: sRazonSocial1)
				: (sNombres1 == null ? sNombres2 + " " + sApellidoPaterno2
						+ " " + sApellidoMaterno2 : sNombres1 + " "
						+ sApellidoPaterno1 + " " + sApellidoMaterno1));
		this.fechalimite = dFechaLimite;
		this.estado = estado.toString();
		this.propietario = propietarioNombres + " " + propietarioApellidos;
		this.responsable = responsableNombres + " " + responsableApellidos;
	}

	// Informativos
	public Item(Integer iIdNotificacion, Character cLeido, String sAsunto,
			Date dFechaNotificacion, int iTipoNotificacion,
			Integer iIdDocumento, String sConcesionario, String sNroExpediente,
			String sProceso, String sClienteTipoIdentificacion,
			String sClienteRazonSocial, String sClienteNombres,
			String sClienteApellidoPaterno, String sClienteApellidoMaterno) {
		this.id = iIdNotificacion;
		this.leido = cLeido;
		this.asunto = sAsunto;
		this.fecharecepcion = dFechaNotificacion;
		this.tipo = String.valueOf(iTipoNotificacion);
		this.documento = iIdDocumento.toString();
		this.concesionario = sConcesionario;
		this.expediente = sNroExpediente;
		this.proceso = sProceso;
		this.cliente = (sClienteTipoIdentificacion != null
				&& sClienteTipoIdentificacion
						.equalsIgnoreCase(Constantes.TIPO_IDENTIFICACION_RUC) ? sClienteRazonSocial
				: sClienteNombres + " " + sClienteApellidoPaterno + " "
						+ sClienteApellidoMaterno);
	}

	// Informativos con tipo
	public Item(Integer iIdNotificacion, Character cLeido, String sAsunto,
			Date dFechaNotificacion, int iTipoNotificacion,
			Integer iIdDocumento, String sConcesionario, String sNroExpediente,
			String sProceso, String sClienteTipoIdentificacion,
			String sClienteRazonSocial, String sClienteNombres,
			String sClienteApellidoPaterno, String sClienteApellidoMaterno,
			String td) {
            
                this.id = iIdNotificacion;
		this.leido = cLeido;
		this.asunto = sAsunto;
		this.fecharecepcion = dFechaNotificacion;
		this.tipo = String.valueOf(iTipoNotificacion);
		this.documento = iIdDocumento.toString();
		this.concesionario = sConcesionario;
		this.expediente = sNroExpediente;
		this.proceso = sProceso;
		this.tipodocumento = td;
		this.cliente = (sClienteTipoIdentificacion != null
				&& sClienteTipoIdentificacion
						.equalsIgnoreCase(Constantes.TIPO_IDENTIFICACION_RUC) ? sClienteRazonSocial
				: sClienteNombres + " " + sClienteApellidoPaterno + " "
						+ sClienteApellidoMaterno);
		// this.Iddocumento = iIdDocumento.toString();///programado
	}

	// nueva mejora para mejorar informativos

	public Item(Integer iIdNotificacion, Character cLeido, String sAsunto,
			Date dFechaNotificacion, int iTipoNotificacion,
			Integer iIdDocumento, String sConcesionario, String sNroExpediente,
			String sProceso, String sClienteTipoIdentificacion,
			String sClienteRazonSocial, String sClienteNombres,
			String sClienteApellidoPaterno, String sClienteApellidoMaterno,
			String tipoDocumento, String documento,
                        char estado, String asuntoExpediente, Integer codTipoInstitucion, Integer nroTramite, Integer nroOrigen){
            
                this.id = iIdNotificacion;
		this.leido = cLeido;
		this.asunto = sAsunto;
		this.fecharecepcion = dFechaNotificacion;
		this.tipo = String.valueOf(iTipoNotificacion);
		this.iddocumento = iIdDocumento != null ? iIdDocumento.toString()
				: null;
		this.concesionario = sConcesionario;
		this.expediente = sNroExpediente;
		this.proceso = sProceso;
		this.tipodocumento = tipoDocumento;
                this.nroTramite = nroTramite; 
                this.origen = nroOrigen;
                
                sClienteRazonSocial = sClienteRazonSocial ==null?"":sClienteRazonSocial;
                sClienteNombres = sClienteNombres==null?"": sClienteNombres;
                sClienteApellidoPaterno = sClienteApellidoPaterno == null? "":sClienteApellidoPaterno;
                sClienteApellidoMaterno = sClienteApellidoMaterno == null ? "":sClienteApellidoMaterno;
                
		/*this.cliente = (sClienteTipoIdentificacion != null && sClienteTipoIdentificacion.equalsIgnoreCase(Constantes.TIPO_IDENTIFICACION_RUC) ? sClienteRazonSocial
				: sClienteNombres + " " + sClienteApellidoPaterno + " "
						+ sClienteApellidoMaterno);*/
                
                if (codTipoInstitucion==null){
                   String nombres = sClienteNombres == null? "":sClienteNombres;
                   String paterno = sClienteApellidoPaterno == null? "": sClienteApellidoPaterno;
                   String materno = sClienteApellidoMaterno == null?"":sClienteApellidoMaterno;       
                   this.cliente = nombres + " " + paterno + " " + materno;
                }else{
                   this.cliente =  sClienteRazonSocial;
                }
                    
		this.documento = documento;
                this.asuntoExpediente = asuntoExpediente;
                
                if (estado=='A') 
                  this.estado =   "Registrado";
                if (estado=='C') 
                  this.estado =   "Archivado";
                if (estado=='N') 
                  this.estado =   "Anulado";
                if (estado == 'I') 
                  this.estado =   "Inactivo";
                if (estado =='T') 
                  this.estado =   "Atendido";
                if (estado =='P') 
                  this.estado =   "Pendiente";
                
                this.codTipoInstitucion = codTipoInstitucion;
                
	}

	// informativo con tipos
	public Item(Integer iIdNotificacion, Character cLeido, String sAsunto,
			Date dFechaNotificacion, int iTipoNotificacion, String documento,
			String sConcesionario, String sNroExpediente, String sProceso,
			String sClienteTipoIdentificacion, String sClienteRazonSocial,
			String sClienteNombres, String sClienteApellidoPaterno,
			String sClienteApellidoMaterno, String td) {
            	this.id = iIdNotificacion;
		this.leido = cLeido;
		this.asunto = sAsunto;
		this.fecharecepcion = dFechaNotificacion;
		this.tipo = String.valueOf(iTipoNotificacion);
		this.documento = documento;
		this.concesionario = sConcesionario;
		this.expediente = sNroExpediente;
		this.proceso = sProceso;
		this.tipodocumento = td;
		this.cliente = (sClienteTipoIdentificacion != null
				&& sClienteTipoIdentificacion
						.equalsIgnoreCase(Constantes.TIPO_IDENTIFICACION_RUC) ? sClienteRazonSocial
				: sClienteNombres + " " + sClienteApellidoPaterno + " "
						+ sClienteApellidoMaterno);
	}

	// Notificaciones
	public Item(Integer iIdNotificacion, Character cLeido, String sAsunto,
			Date dFechaNotificacion, Integer iIdDocumento,
			String sTipoNotificacion) {
         	this.id = iIdNotificacion;
		this.leido = cLeido;
		this.asunto = sAsunto;
		this.fecharecepcion = dFechaNotificacion;
		this.documento = iIdDocumento.toString();
		this.tiponotificacion = sTipoNotificacion;
	}

	// Documentos
	public Item(Integer iIdDocumento, String sTipoDocumento,
			String sNroDocumento) {
		this.id = iIdDocumento;
		this.documento = sTipoDocumento + " - " + sNroDocumento;
	}

	// Consulta3DocumentosRecepcionados
	public Item(String expediente, String proceso,
			String cliente, String tipodocumento, String estado,
			String asuntoExpediente, String asuntodocumento,
			String nrodocumento, Date fechacreacion, String iddocumento,
			String prioridad,String areaorigen) {
                this.expediente = expediente;
		this.proceso = proceso;
		this.cliente = cliente;
		this.tipodocumento = tipodocumento;
		this.estado = estado;
		this.asuntoExpediente = asuntoExpediente;
		this.asuntodocumento = asuntodocumento;
		this.nrodocumento = nrodocumento;
		this.fechacreacion = fechacreacion;
		this.iddocumento = iddocumento;
		this.prioridad = prioridad;
		this.areaorigen = areaorigen;
	}

	/*
	 * Getters & Setters
	 */

	public Date getFecharecepcion() {
		return fecharecepcion;
	}

	public void setFecharecepcion(Date fecharecepcion) {
		this.fecharecepcion = fecharecepcion;
	}

	public String getAreaorigen() {
		return areaorigen;
	}

	public void setAreaorigen(String areaorigen) {
		this.areaorigen = areaorigen;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAsunto() {
		return asunto;
	}

	public String getTiponotificacion() {
		return tiponotificacion;
	}

	public void setTiponotificacion(String tiponotificacion) {
		this.tiponotificacion = tiponotificacion;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getRemitente() {
		return remitente;
	}

	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getConcesionario() {
		return concesionario;
	}

	public void setConcesionario(String concesionario) {
		this.concesionario = concesionario;
	}

	public String getUrlarchivo() {
		return urlarchivo;
	}

	public void setUrlarchivo(String urlarchivo) {
		this.urlarchivo = urlarchivo;
	}

	public String getTipoalerta() {
		return tipoalerta;
	}

	public void setTipoalerta(String tipoalerta) {
		this.tipoalerta = tipoalerta;
	}

	public Date getFechalimite() {
		return fechalimite;
	}

	public void setFechalimite(Date fechalimite) {
		this.fechalimite = fechalimite;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
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

	public Date getFechaexpediente() {
		return fechaexpediente;
	}

	public void setFechaexpediente(Date fechaexpediente) {
		this.fechaexpediente = fechaexpediente;
	}

	public long getLfecharecepcion() {
		return lfecharecepcion;
	}

	public void setLfecharecepcion(long lfecharecepcion) {
		this.lfecharecepcion = lfecharecepcion;
	}

	public long getLfechalimite() {
		return lfechalimite;
	}

	public void setLfechalimite(long lfechalimite) {
		this.lfechalimite = lfechalimite;
	}

	public long getLfechaactual() {
		return lfechaactual;
	}

	public void setLfechaactual(long lfechaactual) {
		this.lfechaactual = lfechaactual;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNrointerno() {
		return nrointerno;
	}

	public void setNrointerno(String nrointerno) {
		this.nrointerno = nrointerno;
	}

	public String getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getTiempoatencion() {
		return tiempoatencion;
	}

	public void setTiempoatencion(Integer tiempoatencion) {
		this.tiempoatencion = tiempoatencion;
	}

	public Date getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}

	public Date getFechafin() {
		return fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public String getUsuarioreemplazante() {
		return usuarioreemplazante;
	}

	public void setUsuarioreemplazante(String usuarioreemplazante) {
		this.usuarioreemplazante = usuarioreemplazante;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public Integer getIdproceso() {
		return idproceso;
	}

	public void setIdproceso(Integer idproceso) {
		this.idproceso = idproceso;
	}

	public Character getLeido() {
		return leido;
	}

	public void setLeido(Character leido) {
		this.leido = leido;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getAsuntoExpediente() {
		return asuntoExpediente;
	}

	public void setAsuntoExpediente(String asuntoexpediente) {
		this.asuntoExpediente = asuntoexpediente;
	}

	public String getAsuntodocumento() {
		return asuntodocumento;
	}

	public void setAsuntodocumento(String asuntodocumento) {
		this.asuntodocumento = asuntodocumento;
	}

	public Character getEstadoexpediente() {
		return estadoexpediente;
	}

	public void setEstadoexpediente(Character estadoexpediente) {
		this.estadoexpediente = estadoexpediente;
	}

	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}

	public String getNumeroMesaPartes() {
		return numeroMesaPartes;
	}

	public void setNumeroMesaPartes(String numeroMesaPartes) {
		this.numeroMesaPartes = numeroMesaPartes;
	}

	public Character getHistorico() {
		return historico;
	}

	public void setHistorico(Character historico) {
		this.historico = historico;
	}

	public Date getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public String getReemplazado() {
		return reemplazado;
	}

	public void setReemplazado(String reemplazado) {
		this.reemplazado = reemplazado;
	}

	public String getReemplazante() {
		return reemplazante;
	}

	public void setReemplazante(String reemplazante) {
		this.reemplazante = reemplazante;
	}

	public String getPorcentajealertaA() {
		return porcentajealertaA;
	}

	public void setPorcentajealertaA(String porcentajealertaA) {
		this.porcentajealertaA = porcentajealertaA;
	}

	public String getPorcentajealertaR() {
		return porcentajealertaR;
	}

	public void setPorcentajealertaR(String porcentajealertaR) {
		this.porcentajealertaR = porcentajealertaR;
	}

	public String getRazonsocial() {
		return razonsocial;
	}

	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getHorarioReporte() {
		return horarioReporte;
	}

	public void setHorarioReporte(String horarioReporte) {
		this.horarioReporte = horarioReporte;
	}

	public Integer getReporteMP() {
		return reporteMP;
	}

	public void setReporteMP(Integer reporteMP) {
		this.reporteMP = reporteMP;
	}

	public Integer getReporteDIG() {
		return reporteDIG;
	}

	public void setReporteDIG(Integer reporteDIG) {
		this.reporteDIG = reporteDIG;
	}

	public Integer getReporteQAS() {
		return reporteQAS;
	}

	public void setReporteQAS(Integer reporteQAS) {
		this.reporteQAS = reporteQAS;
	}

	public Integer getReporteFolios() {
		return reporteFolios;
	}

	public void setReporteFolios(Integer reporteFolios) {
		this.reporteFolios = reporteFolios;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getDireccionAlternativa() {
		return direccionAlternativa;
	}

	public void setDireccionAlternativa(String direccionAlternativa) {
		this.direccionAlternativa = direccionAlternativa;
	}

	public String getDepartamentoAlternativo() {
		return departamentoAlternativo;
	}

	public void setDepartamentoAlternativo(String departamentoAlternativo) {
		this.departamentoAlternativo = departamentoAlternativo;
	}

	public String getProvinciaAlternativa() {
		return provinciaAlternativa;
	}

	public void setProvinciaAlternativa(String provinciaAlternativa) {
		this.provinciaAlternativa = provinciaAlternativa;
	}

	public String getDistritoAlternativo() {
		return distritoAlternativo;
	}

	public void setDistritoAlternativo(String distritoAlternativo) {
		this.distritoAlternativo = distritoAlternativo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}

	public String getUbigeoAlternativo() {
		return ubigeoAlternativo;
	}

	public void setUbigeoAlternativo(String ubigeoAlternativo) {
		this.ubigeoAlternativo = ubigeoAlternativo;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getSubmotivo() {
		return submotivo;
	}

	public void setSubmotivo(String submotivo) {
		this.submotivo = submotivo;
	}

	public String getMonto() {
		return monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getNroSuministro() {
		return nroSuministro;
	}

	public void setNroSuministro(String nroSuministro) {
		this.nroSuministro = nroSuministro;
	}

	/**
	 * @return the ambitoEnvio
	 */
	public String getAmbitoEnvio() {
		return ambitoEnvio;
	}

	/**
	 * @param ambitoEnvio
	 *            the ambitoEnvio to set
	 */
	public void setAmbitoEnvio(String ambitoEnvio) {
		this.ambitoEnvio = ambitoEnvio;
	}

	/**
	 * @return the tipoEnvio
	 */
	public String getTipoEnvio() {
		return tipoEnvio;
	}

	/**
	 * @param tipoEnvio
	 *            the tipoEnvio to set
	 */
	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}

	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}

	/**
	 * @param referencia
	 *            the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getIddocumento() {
		return iddocumento;
	}

	public void setIddocumento(String iddocumento) {
		this.iddocumento = iddocumento;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	
	public String getAreadestino() {
		return areadestino;
	}

	public void setAreadestino(String areadestino) {
		this.areadestino = areadestino;
	}

	public Integer getDocumentoReferencia() {
		return documentoReferencia;
	}

	public void setDocumentoReferencia(Integer documentoReferencia) {
		this.documentoReferencia = documentoReferencia;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getExcprioridad() {
		return excprioridad;
	}

	public void setExcprioridad(String excprioridad) {
		this.excprioridad = excprioridad;
	}
        
        public int getNroTramite() {
            return nroTramite;
        }

        public void setNroTramite(int nroTramite) {
            this.nroTramite = nroTramite;
        }
        
         public Date getFechaatender() {
            return fechaatender;
        }

        public void setFechaatender(Date fechaatender) {
            this.fechaatender = fechaatender;
        }
        
        public Date getFechaanulacion() {
            return fechaanulacion;
        }

        public void setFechaanulacion(Date fechaanulacion) {
            this.fechaanulacion = fechaanulacion;
        }
        
        public String getLlave() {
            return llave;
        }

        public void setLlave(String llave) {
            this.llave = llave;
        }
        
         public String getRecepcionado() {
            return recepcionado;
        }

        public void setRecepcionado(String recepcionado) {
            this.recepcionado = recepcionado;
        }
}