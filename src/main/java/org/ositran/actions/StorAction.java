/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ositran.services.ClienteService;
import org.ositran.services.ConcesionarioService;
import org.ositran.services.DocumentoEnviadoService;
import org.ositran.services.DocumentoService;
import org.ositran.services.EstadoService;
import org.ositran.services.ExpedienteService;
import org.ositran.services.ExpedientestorService;
import org.ositran.services.NotificacionService;
import org.ositran.services.RepositorioService;
import org.ositran.services.ResolucionjaruService;
import org.ositran.services.RolService;
import org.ositran.services.SalaService;
import org.ositran.services.StorManagerService;
import org.ositran.services.TiporesultadoService;
import org.ositran.services.TrazabilidaddocumentoService;
import org.ositran.services.TrazabilidadexpedienteService;
import org.ositran.services.UsuarioService;
import org.ositran.services.UsuariostorService;
import org.ositran.services.VocalService;
import org.ositran.utils.Constantes;

import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoStor;
import com.btg.ositran.siged.domain.Estado;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Expedientestor;
import com.btg.ositran.siged.domain.Motivo;
import com.btg.ositran.siged.domain.Resolucionjaru;
import com.btg.ositran.siged.domain.Sala;
import com.btg.ositran.siged.domain.SubmotivoXExpedienteStor;
import com.btg.ositran.siged.domain.SubmotivoXExpedienteStorPK;
import com.btg.ositran.siged.domain.Suministro;
import com.btg.ositran.siged.domain.Tiporesultado;
import com.btg.ositran.siged.domain.Trazabilidadexpediente;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Vocal;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author Usuario
 */
public class StorAction {
    // ///////////
    // Atributos//
    // ///////////
    // Pojos/7

    private Documento documento;
    private Expediente expediente;
    private Expediente objExpediente;
    private List<Motivo> listaMotivos;
    private DocumentoStor documentoStor;
    // private Actividad actividad;
    // Services
    private DocumentoService documentoService;
    private ExpedienteService expedienteService;
    private RolService rolService;
    private UsuarioService usuarioService;
    private UsuariostorService usuariostorService;
    private ExpedientestorService expedientestorService;
    private StorManagerService storManagerService;
    private RepositorioService srvRepositorio;
    private TrazabilidaddocumentoService trazabilidaddocumentoService;
    private DocumentoEnviadoService documentoEnviadoService;
    private TrazabilidadexpedienteService trazabilidadexpedienteService;
    private SalaService salaService;
    private EstadoService estadoService;
    private TiporesultadoService tipoResultadoService;
    private VocalService vocalService;
    private ResolucionjaruService resolucionjaruService;
    // Agregado por Germán Enríquez
    private NotificacionService servicioNotificacion;
    private ClienteService clienteService;
    private ConcesionarioService concesionarioService;
    // /////////////////////////////
    // Parametros - JSP - Usuarios//
    // /////////////////////////////
    private Integer idDocumento;
    private Integer idtipoanalisis;
    private String sala;
    private String salaanterior;
    private Integer idsala;
    private Integer idanalista;
    private String revisiontecnica;
    private String revisortecnico;
    private String revisionlegal;
    private String revisorlegal;
    private Integer idrevisortecnico;
    private Integer idrevisorlegal;
    private String tipoanalisis;
    private String derivarvbrst;
    private String fechaaprobacion;
    private String nroresolucion;
    private Integer idresultadoresolucion;
    private String fnusuario;
    private String fnconcesionario;
    private Integer plazo;
    private String refrescar;
    private String destinatario;
    private String asunto;
    private String observacionaprobar;
    private String observacioneditar;
    private String observacioncambiosala;
    private String observacionrechazar;
    private String observacionanular;
    private String observacionreformular;
    private String estadoexpedientestor;
    private String cumplerequisitosst;
    private String nroexpediente;
    private String motivocambiosala;
    private String aprobarcambiosala;
    private String reformulaciontecnica;
    private String reformulacionlegal;
    private String[] listsuministros;
    private Integer[] listidsubmotivos;
    private Integer[] listnuevosidsubmotivos;
    private String[] strAEliminar;
    private String requiredObservacion;
    // Parametros - Mensajes de Confirmacion y Alerta
    private String tipomensaje;
    private String valormensaje;
    // Parametros - Logica en Vista
    private Boolean existeRevisorTecnico;
    private Boolean existeRevisorLegal;
    private String tipoVentanaDetalle;
    // Parametros Expediente - Recurso de Apelacion, Queja, Medida Cautelar
    private String proceso;
    private String tipodocidentidad;
    private String nrosuministro;
    private String nmotivo;
    private String nsubmotivo;
    private Integer idsubmotivo;
    private String monto;
    private String radioRuc;
    private String nrodocidentidad;
    // Otros
    private int idActividadExpediente;
    private static Logger log = Logger.getLogger(StorAction.class);
    // Para el envio de notificaciones
    // Germán Enríquez
    private boolean infoAdicional;
    private boolean inspeccionCampo;
    private boolean audienciaConciliacion;
    // envio notificacionSTOR
    private Integer tipoNotificacionSTOR;
    private Trazabilidadexpediente ultimaTrazabilidad;
    private Cliente objReclamante;
    //Para el registro de Información Complementaria
    private String codSala;
    private String codEstado;
    private String codResultado;
    private String codVocal;
    private String fechaSesion;
    private String fechaNotiReclamante;
    private String fechaNotiConcesionario;
    private String etapaActual;
    private String fechaDerivacion;
    private String numeroResolucion;
    private String usuarioActual;
    private String remitenteObservacion;
    private String usuarioRegistro;
    private String campoA, campoB, mensajeError;
    private SubmotivoXExpedienteStor submotivoExpediente;
    private Suministro suministro;

    // //////////////////////////////////
    // Metodos - Administrar Interfaces//
    // //////////////////////////////////
    public String ventanaCompletarActividad() {
        // Obtener Actividad
        // Devolver codigo (mapeado con la jsp correspondiente)
        try {
            if (getIdDocumento() == null) {
                log.error("No hay documento a buscar");
                return Action.ERROR;
            }
            log.info("El ID del Documento es [" + getIdDocumento() + "]");
            setDocumento(getDocumentoService().findByIdDocumento(getIdDocumento()));
            setExpediente(getDocumento().getExpediente());
            // setExpediente(getExpedienteService().findByIdExpediente(getDocumento().getIdDocumento()));
            setIdActividadExpediente(getExpediente().getIdactividad().getIdActividad());
            String codigoActividad = getExpediente().getIdactividad().getCodigo();
            String codEstado = getExpedientestorService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getEstado().getCodigo();
            String codProceso = null;//expediente.getProceso().getCodigo();
            estadoexpedientestor = codEstado;
            Usuario usuario = null;
            if (!codigoActividad.equalsIgnoreCase("ap_calexp") && !codigoActividad.equalsIgnoreCase("mc_calexp") && !codigoActividad.equalsIgnoreCase("q_calexp")) {
                idsala = getExpedientestorService().findByIdExpediente(expediente.getIdexpediente()).getSala().getIdsala();
                sala = getExpedientestorService().findByIdExpediente(expediente.getIdexpediente()).getSala().getNombre();
                log.info("SALA ASIGNADA POR SUPERVISOR CALIFICADOR: " + " " + sala + " CON ID: " + idsala);
                if (codigoActividad.equalsIgnoreCase("ap_analexp") || codigoActividad.equalsIgnoreCase("mc_analexp") || codigoActividad.equalsIgnoreCase("q_analexp")) {
                    /*
                     * int idSalaAnalista =
                     * getExpedientestorService().findByIdExpediente
                     * (expediente.getIdexpediente
                     * ()).getAnalista().getSala().getIdsala(); if
                     * (idSalaAnalista != idsala.intValue()) { idsala =
                     * idSalaAnalista; }
                     */
                    log.debug("CODIGO DE ESTADO: " + codEstado);
                    if (codEstado.equalsIgnoreCase("rtec")) {
                        usuario = getExpedientestorService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getRevisortecnico();
                        revisortecnico = usuario.getNombres() + " " + usuario.getApellidos();
                        idrevisortecnico = usuario.getIdusuario();
                        estadoexpedientestor = codEstado;
                        if (codProceso.equalsIgnoreCase("apelacion")) {
                        } else if (codProceso.equalsIgnoreCase("queja")) {
                            log.debug("PROCESO DE QUEJA - IMPLEMENTADO - FALTA PROBAR");
                            revisiontecnica = "true";
                            revisionlegal = "false";
                            return "ap_analexp";
                        } else if (codProceso.equalsIgnoreCase("medida cautelar")) {
                            revisiontecnica = "true";
                            revisionlegal = "false";
                            return "ap_analexp";
                        }
                    } else if (codEstado.equalsIgnoreCase("rleg")) {
                        usuario = getExpedientestorService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getRevisorlegal();
                        idrevisorlegal = usuario.getIdusuario();
                        revisorlegal = usuario.getNombres() + " " + usuario.getApellidos();
                        estadoexpedientestor = codEstado;
                        proceso = null;//expediente.getProceso().getCodigo();
                        // log.debug("Id Revisor Legal: " + idrevisorlegal +
                        // " Id Revisor Tecnico:" + idrevisortecnico);
                        // BEING PRUEBA TEMPORAL
                        if (expediente.getExpedientestor().getRevisortecnico() == null) {
                            log.debug("NO SE HA ASIGNADO REVISOR TECNICO AL EXPEDIENTE " + expediente.getNroexpediente());
                        }
                        // END PRUEBA TEMPORAL
                        if (codProceso.equalsIgnoreCase("apelacion")) {
                            //setProceso(expediente.getProceso().getCodigo());
                            usuario = getExpedientestorService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getRevisortecnico();
                            idrevisortecnico = usuario.getIdusuario();
                            return "ap_revtecnica";
                        } else if (codProceso.equalsIgnoreCase("queja")) {
                            log.debug("PROCESO DE QUEJA - IMPLEMENTADO - FALTA PROBAR");
                            revisiontecnica = "false";
                            revisionlegal = "true";
                            return "q_analexp";
                        } else if (codProceso.equalsIgnoreCase("medida cautelar")) {
                            revisiontecnica = "false";
                            revisionlegal = "true";
                            return "mc_analexp";
                        }
                    }
                } else if (codigoActividad.equalsIgnoreCase("ap_vbfinal") || codigoActividad.equalsIgnoreCase("mc_vbfinal") || codigoActividad.equalsIgnoreCase("q_vbfinal")) {
                    existeRevisorTecnico = expediente.getExpedientestor().getRevisortecnico() != null ? true : false;
                    existeRevisorLegal = expediente.getExpedientestor().getRevisorlegal() != null ? true : false;
                    if (existeRevisorTecnico.booleanValue() && existeRevisorLegal.booleanValue()) {
                        usuario = getExpedientestorService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getRevisortecnico();
                        revisortecnico = usuario.getNombres() + " " + usuario.getApellidos();
                        idrevisortecnico = usuario.getIdusuario();
                        usuario = getExpedientestorService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getRevisorlegal();
                        idrevisorlegal = usuario.getIdusuario();
                        revisorlegal = usuario.getNombres() + " " + usuario.getApellidos();
                    } else {
                        log.debug("NO SE HA ASIGNADO REVISOR TECNICO AL EXPEDIENTE " + expediente.getNroexpediente());
                        usuario = getExpedientestorService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getRevisorlegal();
                        idrevisorlegal = usuario.getIdusuario();
                        revisorlegal = usuario.getNombres() + " " + usuario.getApellidos();
                    }
                } else if (codigoActividad.equalsIgnoreCase("ap_decamsala") || codigoActividad.equalsIgnoreCase("mc_decamsala") || codigoActividad.equalsIgnoreCase("q_decamsala")) {
                    aprobarcambiosala = "si";
                    salaanterior = sala;
                    sala = "";
                } else if (codigoActividad.equalsIgnoreCase("ap_calanalext") || codigoActividad.equalsIgnoreCase("mc_calanalext") || codigoActividad.equalsIgnoreCase("q_calanalext")) {
                    tipoanalisis = expediente.getExpedientestor().getTipoanalisis();
                } else if (codigoActividad.equalsIgnoreCase("mc_revtecnica") || codigoActividad.equalsIgnoreCase("q_revtecnica")) {
                    if (codEstado.equalsIgnoreCase("rleg")) {
                        estadoexpedientestor = codEstado;
                        usuario = getExpedientestorService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getRevisorlegal();
                        idrevisorlegal = usuario.getIdusuario();
                        revisorlegal = usuario.getNombres() + " " + usuario.getApellidos();
                        estadoexpedientestor = codEstado;
                    }
                } else if (codigoActividad.equalsIgnoreCase("ap_regnot") || codigoActividad.equalsIgnoreCase("q_regnot") || codigoActividad.equalsIgnoreCase("mc_regnot")) {
                    Resolucionjaru resolucionjaru = expediente.getExpedientestor().getResolucionjaruList().get(0);
                    fechaaprobacion = resolucionjaru.getFechaaprobacion().toString();
                    log.info("FECHA DE CUMPLIMIENTO: " + fechaaprobacion);
                    boolean usuarioNotificado = resolucionjaru.getFechanotificacionreclamante() != null ? true : false;
                    boolean concesionarioNotificado = resolucionjaru.getFechanotificacionconcesionario() != null ? true : false;
                    if (concesionarioNotificado) {
                        fnconcesionario = resolucionjaru.getFechanotificacionconcesionario().toString();
                        plazo = resolucionjaru.getPlazo();
                    }
                    if (usuarioNotificado) {
                        fnusuario = resolucionjaru.getFechanotificacionreclamante().toString();
                    }
                    if (expediente.getExpedientestor().getRequiereverificacion().toString().equalsIgnoreCase("N") && concesionarioNotificado && usuarioNotificado) {
                        return ("archivar");
                    }
                    log.debug("FECHA NOT USUARIO " + fnusuario + " FECHA NOT CONCESIONARIO " + fnconcesionario);
                }
            }
            log.info("Codigo de Actividad Actual: [" + getExpediente().getIdactividad().getCodigo() + "]");
            return codigoActividad;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Action.ERROR;
        }
    }

    public String ventanaRechazarStor() {
        log.info("Dentor de ventanaRechazar - StorAction");
        // Identificar usurio - etapa - estado
        setDocumento(getDocumentoService().findByIdDocumento(getIdDocumento()));
        String codEtapa = getExpedientestorService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getEtapa().getCodigo();
        String codEstado = getExpedientestorService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getEstado().getCodigo();
        String codActividad = getExpedienteService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getIdactividad().getCodigo();
        Usuario objDestinatario;
        String nombreDestinatario;
        // asunto = getDocumento().getAsunto();
        if (codActividad.equalsIgnoreCase("ap_revtecnica") || codActividad.equalsIgnoreCase("mc_revtecnica") || codActividad.equalsIgnoreCase("q_revtecnica")) {
            objDestinatario = getExpedientestorService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getAnalista();
            nombreDestinatario = objDestinatario.getNombres() + " " + objDestinatario.getApellidos();
            destinatario = nombreDestinatario;
            asunto = Constantes.ASUNTO_RECHAZO_TECNICO;
        } else if (codActividad.equalsIgnoreCase("ap_revlegal") || codActividad.equalsIgnoreCase("mc_revlegal") || codActividad.equalsIgnoreCase("q_revlegal")) {
            objDestinatario = getExpedientestorService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getAnalista();
            nombreDestinatario = objDestinatario.getNombres() + " " + objDestinatario.getApellidos();
            destinatario = nombreDestinatario;
            asunto = Constantes.ASUNTO_RECHAZO_LEGAL;
        } else if (codEtapa.equalsIgnoreCase("imp") && (codActividad.equalsIgnoreCase("ap_vbrst") || codActividad.equalsIgnoreCase("mc_vbrst") || codActividad.equalsIgnoreCase("q_vbrst"))) {
            idsala = getExpedientestorService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getSala().getIdsala();
            objDestinatario = getUsuariostorService().getResponsableSala(idsala, 'Y');
            destinatario = objDestinatario.getNombres() + " " + objDestinatario.getApellidos();
            asunto = Constantes.ASUNTO_RECHAZO_VB;
            log.debug("DESTINATARIO RECHAZO DEL SECRETARIO GENERAL: " + destinatario);
        } else if (codEtapa.equalsIgnoreCase("imp") && (codActividad.equalsIgnoreCase("ap_vbrsg") || codActividad.equalsIgnoreCase("mc_vbrsg") || codActividad.equalsIgnoreCase("q_vbrsg"))) {
            idsala = getExpedientestorService().findByIdExpediente(getDocumento().getExpediente().getIdexpediente()).getSala().getIdsala();
            objDestinatario = getUsuariostorService().getResponsableSala(idsala, 'Y');
            destinatario = objDestinatario.getNombres() + " " + objDestinatario.getApellidos();
            asunto = Constantes.ASUNTO_RECHAZO_VB;
            log.debug("DESTINATARIO RECHAZO DEL SECRETARIO GENERAL: " + destinatario);
        } else if (codActividad.equalsIgnoreCase("ap_decamsala") || codActividad.equalsIgnoreCase("mc_decamsala") || codActividad.equalsIgnoreCase("q_decamsala")) {
            aprobarcambiosala = "no";
            asunto = Constantes.ASUNTO_RECHAZO_CAMBIO_SALA;
            if (codEstado.equalsIgnoreCase("scsa")) {
                objDestinatario = getExpedientestorService().findByIdExpediente(documento.getExpediente().getIdexpediente()).getAnalista();
                destinatario = objDestinatario.getNombres() + " " + objDestinatario.getApellidos();
            } else if (codEstado.equalsIgnoreCase("scsrevtec")) {
                objDestinatario = getExpedientestorService().findByIdExpediente(documento.getExpediente().getIdexpediente()).getRevisortecnico();
                destinatario = objDestinatario.getNombres() + " " + objDestinatario.getApellidos();
            } else if (codEstado.equalsIgnoreCase("scsrevleg")) {
                objDestinatario = getExpedientestorService().findByIdExpediente(documento.getExpediente().getIdexpediente()).getRevisorlegal();
                destinatario = objDestinatario.getNombres() + " " + objDestinatario.getApellidos();
            }
        }
        return "rechazar";
    }

    public String ventanaAnularExpediente() {
        setDocumento(getDocumentoService().findByIdDocumento(getIdDocumento()));
        setExpediente(getDocumento().getExpediente());
        nroexpediente = getExpediente().getNroexpediente();
        return "anular";
    }

    public String ventanaCambioSala() {
        setDocumento(getDocumentoService().findByIdDocumento(idDocumento));
        setExpediente(getDocumento().getExpediente());
        sala = getExpedientestorService().findByIdExpediente(expediente.getIdexpediente()).getSala().getNombre();
        return "cambiosala";
    }
    
    public String ventanaDatosExpediente() {
        log.debug("VENTANA EDITAR EXPEDIENTE - IdDocumento: " + getIdDocumento());
        documento = getDocumentoService().findByIdDocumento(getIdDocumento());
        expediente = getDocumento().getExpediente();
        if (expediente != null) {
            log.error("el expediente esta nulllllll ");
        }
        setDocumentoStor(getStorManagerService().getDocumentoStorByExpediente(expediente.getIdexpediente()));
        radioRuc = "checked";
        tipodocidentidad = expediente.getCliente().getTipoIdentificacion().getNombre();
        nrodocidentidad = expediente.getCliente().getNumeroIdentificacion();
        try {
            setDocumentoStor(getStorManagerService().getDocumentoStorByExpediente(expediente.getIdexpediente()));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return Action.ERROR;
        }
        try {
            nrosuministro = getDocumentoStor().getSuministros().get(0).getNrosuministro();
        } catch (IndexOutOfBoundsException e) {
            // monto = null;
            log.error(e.getMessage(), e);
        }
        try {
            Double strMonto = getDocumentoStor().getMonto();
            NumberFormat numberFormat = NumberFormat.getInstance(new Locale("us"));
            numberFormat.setMaximumFractionDigits(2);
            monto = ((strMonto != null && !strMonto.toString().equalsIgnoreCase("0.0")) ? numberFormat.format(strMonto) : "0.00");
            monto = monto.replace(",", "");
            log.debug("Monto a Mostrar en la ventana Datos: " + monto);
        } catch (NullPointerException nPE) {
            log.error(nPE.getMessage(), nPE);
            monto = "0.00";
        }
        listaMotivos = getStorManagerService().getListMotivosxExpediente(expediente.getIdexpediente());
        // Mostrar Observcaion
        log.debug("BUSCAR ULTIMA TRAZABILIDAD PARA EXPEDIENTE " + expediente.getIdexpediente());
        try {
            // Trazabilidadexpediente
            // ultimaTrazabilidadExpediente=trazabilidadexpedienteService.findUltimoRegistroByExpediente(expediente.getIdexpediente());
            // observacioneditar=ultimaTrazabilidadExpediente.getObservacion()!=null
            // ? ultimaTrazabilidadExpediente.getObservacion() : "";
            observacioneditar = expediente.getObservacion() != null ? expediente.getObservacion() : "";
            log.debug("MOSTRAR OBSERVACION: " + observacioneditar);
        } catch (NullPointerException obsNula) {
            log.debug("LA OBSERVACION ES NULA");
            observacioneditar = "";
        }
        log.debug("NRO DE SUMINISTRO:" + nrosuministro);
        Map<String, Object> session = ActionContext.getContext().getSession();
        Usuario objUsuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
        /* objUsuario.getRol().getNombre().equalsIgnoreCase("scalif") */
        if (usuariostorService.esSupervisorCalificador(objUsuario)) {
            log.debug("EDITAR EXPEDIENTE - SCALIFICADOR");
            if (tipoVentanaDetalle.equalsIgnoreCase("resumen")) {
                return "editar_resumen_expediente";
            }
            return "editar_expediente";
        }
        log.debug("VER EXPEDIENTE - USUARIOS STOR " + "TIPO DETALLE: " + tipoVentanaDetalle);
        if (tipoVentanaDetalle.equalsIgnoreCase("resumen")) {
            return "ver_resumen_expediente";
        }
        return "ver_datos_expediente";
    }

    public String ventanaEditarResumenExpediente() {
        log.debug("VENTANA EDITAR EXPEDIENTE");
        documento = getDocumentoService().findByIdDocumento(getIdDocumento());
        expediente = getDocumento().getExpediente();
        radioRuc = "checked";
        tipodocidentidad = expediente.getCliente().getTipoIdentificacion().getNombre();
        nrodocidentidad = expediente.getCliente().getNumeroIdentificacion();
        nrosuministro = getDocumentoStor().getSuministros().get(0).getNrosuministro();
        listaMotivos = getStorManagerService().getListMotivosxExpediente(expediente.getIdexpediente());
        monto = getDocumentoStor().getMonto().toString();
        log.debug("NRO DE SUMINISTRO:" + nrosuministro);
        return "editar_Resumenexpediente";
    }

    public String ventanaEditarCompletoExpediente() {
        log.debug("VENTANA EDITAR EXPEDIENTE");
        documento = getDocumentoService().findByIdDocumento(getIdDocumento());
        expediente = getDocumento().getExpediente();
        radioRuc = "checked";
        tipodocidentidad = expediente.getCliente().getTipoIdentificacion().getNombre();
        nrodocidentidad = expediente.getCliente().getNumeroIdentificacion();
        nrosuministro = getDocumentoStor().getSuministros().get(0).getNrosuministro();
        listaMotivos = getStorManagerService().getListMotivosxExpediente(expediente.getIdexpediente());
        monto = getDocumentoStor().getMonto().toString();
        log.debug("NRO DE SUMINISTRO:" + nrosuministro);
        return "editar_Completoexpediente";
    }

    public String ventanaObservacion() {
        if (idDocumento != null) {
            setDocumento(getDocumentoService().findByIdDocumento(getIdDocumento()));
            setExpediente(getDocumento().getExpediente());
            nroexpediente = getExpediente().getNroexpediente();
            return "observacion";
        }
        log.error("Se debe especificar un documento a observar");
        return Action.ERROR;
    }

    /**
     * Muestra la ventana de Requerimientos
     *
     * @author Germán Enríquez
     * @return
     */
    public String ventanaRequerimientos() {
        if (log.isDebugEnabled()) {
            log.debug("Abriendo la Ventana de Requerimientos.");
        }
        if (getIdDocumento() == null) {
            log.error("No se pudo encontrar el documento.");
            return Action.ERROR;
        }
        setDocumento(getDocumentoService().findByIdDocumento(getIdDocumento()));
        idsala = getDocumento().getExpediente().getExpedientestor().getSala().getIdsala();
        if (log.isDebugEnabled()) {
            log.debug("Documento=" + getDocumento().getIdDocumento() + " ==> " + getDocumento().getAsunto());
            log.debug("idSala=" + idsala);
        }
        return "requerimientos";
    }

    /**
     * Envia las Notificaciones
     *
     * @author Germán Enríquez
     * @return
     */
    public String enviarNotificaciones() {
        if (getIdDocumento() == null) {
            log.error("No se pudo encontrar el documento.");
            return Action.ERROR;
        }
        if (getIdrevisortecnico() == null) {
            log.error("No se pudo encontrar el revisor tecnico.");
            return Action.ERROR;
        }
        setDocumento(getDocumentoService().findByIdDocumento(getIdDocumento()));
        Usuario revisorTecnico = getUsuarioService().findByIdUsuario(getIdrevisortecnico());
        if (log.isDebugEnabled()) {
            log.debug("Documento=" + getDocumento().getIdDocumento() + " ==> " + getDocumento().getAsunto());
            log.debug("Revisor Tecnico=" + revisorTecnico.getIdusuario() + " ==> " + revisorTecnico.getNombres() + " " + revisorTecnico.getApellidos());
        }
        // Enviamos las notificaciones
        if (isInfoAdicional()) {
            //getServicioNotificacion().enviarNotificacion(getDocumento().getPropietario(), revisorTecnico, getDocumento(), Constantes.TIPO_NOTIFICACION_INFOADICIONAL);
        }
        if (isInspeccionCampo()) {
            //getServicioNotificacion().enviarNotificacion(getDocumento().getPropietario(), revisorTecnico, getDocumento(), Constantes.TIPO_NOTIFICACION_INSPECCIONCAMPO);
        }
        if (isAudienciaConciliacion()) {
            //getServicioNotificacion().enviarNotificacion(getDocumento().getPropietario(), revisorTecnico, getDocumento(), Constantes.TIPO_NOTIFICACION_AUDIENCIACONCILIACION);
        }

        if (tipoNotificacionSTOR == Constantes.TIPO_NOTIFICACION_RECHAZOLEGAL) {
            this.ultimaTrazabilidad = trazabilidadexpedienteService.findByMaxNroRegistro(getDocumento().getExpediente().getIdexpediente(), null);
        }

        if (tipoNotificacionSTOR == Constantes.TIPO_NOTIFICACION_RECHAZOTECNICO || tipoNotificacionSTOR == Constantes.TIPO_NOTIFICACION_RECHAZOLEGAL || tipoNotificacionSTOR == Constantes.TIPO_NOTIFICACION_RECHAZO_VBRESOLUCIONST || tipoNotificacionSTOR == Constantes.TIPO_NOTIFICACION_RECHAZO_VBRESOLUCIONSG || tipoNotificacionSTOR == Constantes.TIPO_NOTIFICACION_RECHAZO_CAMBIOSALA) {
            // getDocumento().getPropietario().setCorreo("jennykaori@yahoo.com.mx");
            log.debug("Documento=" + getDocumento().getIdDocumento() + " ==> " + getDocumento().getAsunto());
            log.debug(" PARA =" + getDocumento().getPropietario().getIdusuario() + " ==> " + getDocumento().getPropietario().getNombres() + " " + getDocumento().getPropietario().getApellidos() + " ==> " + getDocumento().getPropietario().getCorreo());
            log.debug(" FROM Revisor Tecnico=" + revisorTecnico.getIdusuario() + " ==> " + revisorTecnico.getNombres() + " " + revisorTecnico.getApellidos());
            // getServicioNotificacion().enviarNotificacion(getDocumento().getPropietario(),
            // revisorTecnico, getDocumento(), tipoNotificacionSTOR);

            Trazabilidadexpediente trazaEx = this.ultimaTrazabilidad;

            Usuario asistenteResponsablProceso = getUsuarioService().findByRolUnico("aresproceso");
            if (asistenteResponsablProceso != null) {
                //getServicioNotificacion().enviarNotificacion(revisorTecnico, asistenteResponsablProceso, getDocumento(), tipoNotificacionSTOR);
            } else {
                log.warn("No se envia ninguna notificacion debido a el proceso no tiene un Asistente de Responsable.");
            }
            //this.getServicioNotificacion().enviarNotificacion(revisorTecnico,getDocumento().getPropietario(),getDocumento(),tipoNotificacionSTOR);
            if (tipoNotificacionSTOR == Constantes.TIPO_NOTIFICACION_RECHAZOLEGAL) {
                //this.getServicioNotificacion().enviarNotificacion(revisorTecnico, trazaEx.getDestinatario(), getDocumento(), tipoNotificacionSTOR);

            } else {
                //this.getServicioNotificacion().enviarNotificacion(revisorTecnico, trazaEx.getRemitente(), getDocumento(), tipoNotificacionSTOR);

            }

        }
        refrescar = "ok";
        return Action.SUCCESS;
    }

    public String updateExpediente() {
        // log.debug("Lista Suministros " + listsuministros.length);
        // log.debug("Length Lista de Id Submotivos " +
        // listidsubmotivos.length);
        log.debug("Length Lista de Id Submotivos NUEVOS " + listnuevosidsubmotivos.length);
        // log.debug("Datos Nuevos motivod: "+listnuevosidsubmotivos[0]+" ,"+listnuevosidsubmotivos[1]+" ,"+listnuevosidsubmotivos[2]);
        // log.debug("Primer Id Submotivo " + listidsubmotivos[0]);
        // monto = monto.replace(".", "");
        monto = monto.replace(",", ".");
        monto = monto.trim();
        log.debug("DATOS A ACTUALIZAR: MOTIVO" + nmotivo + " SUBMOTIVO" + idsubmotivo + " " + nsubmotivo + "MONTO" + monto);
        log.debug("IdDocumento" + idDocumento);
        setDocumento(getDocumentoService().findByIdDocumento(getIdDocumento()));
        setExpediente(getDocumento().getExpediente());
        // getStorManagerService().updateDatosExpediente(expediente.getIdexpediente(),
        // idsubmotivo, monto);
        try {
            log.info("ACTUALIZAR EXPEDIENTE");
            getStorManagerService().updateDatosExpediente(expediente.getIdexpediente(), listidsubmotivos, listnuevosidsubmotivos, monto, getObservacioneditar());
        } catch (Exception ex) {
            requiredObservacion = "Ingresar Motivos y Submotivos Válidos";
            log.debug("ERROR AL ACTUALIZAR DATOS ==> IdDocumento " + getIdDocumento());
            log.error(ex.getMessage(), ex);
            /*
             * documento =
             * getDocumentoService().findByIdDocumento(getIdDocumento());
             * expediente = getDocumento().getExpediente(); listaMotivos =
             * getStorManagerService
             * ().getListMotivosxExpediente(expediente.getIdexpediente());
             * log.debug("Longitud Lista Motivos: "+listaMotivos.size());
             */
            // ventanaDatosExpediente();
            tipoVentanaDetalle = "resumen";
            ventanaDatosExpediente();
            return "editar_resumen_expediente";
        }
        log.debug("Refrescar despues de Actualizar Expediente");
        // refrescar="ok";
        // ////DOCUMENTOS ENVIADOS
        // regitrarDocumentoEnviadoStor(session, numeroExpediente);
        return Action.SUCCESS;
    }

    public String registrarComplementaria() throws Exception {
        try {
            if (documento == null || documento.getExpediente() == null || documento.getExpediente().getIdexpediente() == null) {
                log.error("el expediente es null ");
            }
            if (codSala == null || codEstado == null || codVocal == null || codResultado == null) {
                log.error("los codigos son nulos ");
            }

            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
            Expediente objExpediente = expedienteService.findByIdExpediente(documento.getExpediente().getIdexpediente());

            Sala objSala = null;
            Estado objEstado = null;
            Vocal objVocal = null;
            Tiporesultado objTipoResultado = null;
            try {
                objSala = salaService.findByIdSala(Integer.parseInt(codSala));
            } catch (Exception e) {
                objSala = null;
            }
            try {
                objEstado = estadoService.findByIdestado(Integer.parseInt(codEstado));
            } catch (Exception e) {
                objEstado = null;
            }
            try {
                objVocal = vocalService.findById(Integer.parseInt(codVocal));
            } catch (Exception e) {
                objVocal = null;
            }
            try {
                objTipoResultado = tipoResultadoService.findByIdTiporesultado(Integer.parseInt(codResultado));
            } catch (Exception e) {
                objTipoResultado = null;
            }
            Expedientestor objExpedienteStor = expedientestorService.findByIdExpediente(documento.getExpediente().getIdexpediente());
            if (objExpedienteStor == null) {
                objExpedienteStor = new Expedientestor();
            }
            objExpedienteStor.setEstado(objEstado);
            objExpedienteStor.setSala(objSala);
            objExpedienteStor.setIdexpediente(objExpediente.getIdexpediente());
            objExpedienteStor.setExpediente(objExpediente);

            expedientestorService.saveExpedienteStor(objExpedienteStor);

            Resolucionjaru objResolucionJaru = resolucionjaruService.findByIdExpedienteStor(documento.getExpediente().getIdexpediente());
            if (objResolucionJaru == null) {
                objResolucionJaru = new Resolucionjaru();
            }
            for (int i = 0; i < 3; i++) {
                String fechaTemp = null;

                try {
                    switch (i) {
                        case 0:
                            fechaTemp = fechaSesion;
                            break;
                        case 1:
                            fechaTemp = fechaNotiReclamante;
                            break;
                        case 2:
                            fechaTemp = fechaNotiConcesionario;
                            break;
                    }

                    if (fechaTemp != null && fechaTemp.length() == 10) {
                        fechaTemp = fechaTemp + " 00:00";
                    }

                    switch (i) {
                        case 0:
                            objResolucionJaru.setFechasesion(formatoDelTexto.parse(fechaTemp));
                            break;
                        case 1:
                            objResolucionJaru.setFechanotificacionreclamante(formatoDelTexto.parse(fechaTemp));
                            break;
                        case 2:
                            objResolucionJaru.setFechanotificacionconcesionario(formatoDelTexto.parse(fechaTemp));
                            break;
                    }
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
            objResolucionJaru.setVocal(objVocal);
            objResolucionJaru.setResultado(objTipoResultado);
            objResolucionJaru.setNroresolucion(numeroResolucion);
            objResolucionJaru.setExpediente(objExpedienteStor);

            resolucionjaruService.saveOrUpdateResolucionJaru(objResolucionJaru);

            return "complementaria";

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return Action.ERROR;
        }
    }

    // ////////////////////
    // METODOS PRIVADOS////
    // ////////////////////
    /*private void uploadAlfresco(Map session) {
        Documento objDocumentoPrincipal = null;
        Usuario objUsuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
        Map mapUpload = (Map) session.get(Constantes.SESSION_UPLOAD_LIST);
        DocumentoDetail documentoDetail = new DocumentoDetail();
        documentoDetail.setIIdDocumento(documento.getIdDocumento());
        if (mapUpload == null) {
            log.info("No hay archivos a subir al Alfresco");
        } else {
            objDocumentoPrincipal = documentoService.crearDocumentoPorArchivo(documentoDetail, objUsuario, mapUpload, new Boolean("true"), true, Constantes.MODULO_USUARIO_FINAL, Constantes.OPCION_DERIVAR, false);
            log.debug("Nuevo Documento Principal con ID [" + objDocumentoPrincipal.getIdDocumento() + "]");
        }
    }*/

    /*private String convertCadena(String texto) {
        String nuevoTexto = null;
        if (texto != null) {
            nuevoTexto = texto.replace("á", "a");
            nuevoTexto = nuevoTexto.replace("é", "e");
            nuevoTexto = nuevoTexto.replace("í", "i");
            nuevoTexto = nuevoTexto.replace("ó", "o");
            nuevoTexto = nuevoTexto.replace("ú", "u");
            nuevoTexto = nuevoTexto.replace("ñ", "n");
        }
        return nuevoTexto;
    }*/

    /*private void regitrarDocumentoEnviadoStor(Map session, int numExpediente) {
        Documento docPrincipal = getDocumentoService().findDocumentoPrincipal(numExpediente);
        List<Trazabilidaddocumento> ListaTrazDoc = getTrazabilidaddocumentoService().findByIdDocumento(docPrincipal.getIdDocumento());
        Trazabilidaddocumento traz = ListaTrazDoc.get(ListaTrazDoc.size() - 1);
        Usuario usuarioRemitente = (Usuario) session.get(Constantes.SESSION_USUARIO);
        Documentoenviado docEnviado = new Documentoenviado();
        docEnviado.setTrazabilidaddocumento(traz);
        docEnviado.setUsuario(usuarioRemitente);
        docEnviado.setEstado("" + Constantes.ESTADO_ACTIVO);
        getDocumentoEnviadoService().savedocumentoenviado(docEnviado);
    }*/

    public String registrarDatosPrincipalesStor() {
        try {
            Cliente reclamante = clienteService.findByNroIdentificacion(campoB);
            expediente.getExpedientestor().setReclamante(reclamante);
            Expediente expedienteBD = expedienteService.findByIdExpediente(Integer.decode(campoA));
            if (expedienteBD.getExpedientestor() == null) {
                expediente.getExpedientestor().setIdexpediente(expedienteBD.getIdexpediente());
                expediente.getExpedientestor().setExpediente(expedienteBD);
                Expedientestor expedientestor = expedientestorService.saveExpedientestor(expediente.getExpedientestor());
                expedienteBD.setExpedientestor(expedientestor);
            } else {
                /**Como el Padre hijo de los 70's*/
                Expedientestor expPadre = expedienteBD.getExpedientestor();
                Expedientestor expHijo = expediente.getExpedientestor();
                expPadre.setNroresolucion(expHijo.getNroresolucion());
                if (expPadre.getReclamante() != null && expHijo.getReclamante() != null) {
                    if (!expPadre.getReclamante().getIdCliente().equals(expHijo.getReclamante().getIdCliente())) {
                        expPadre.setReclamante(expHijo.getReclamante());
                    }
                } else if (expHijo.getReclamante() != null) {
                    expPadre.setReclamante(new Cliente());
                    expPadre.getReclamante().setIdCliente(expHijo.getReclamante().getIdCliente());
                }
                expPadre.setReclamantecorreo(expHijo.getReclamantecorreo());
                expPadre.setReclamantedireccionprocesal(expHijo.getReclamantedireccionprocesal());
                expPadre.setReclamantedireccionreal(expHijo.getReclamantedireccionreal());
                expPadre.setReclamantefax(expHijo.getReclamantefax());
                expPadre.setReclamantenrosuministro(expHijo.getReclamantenrosuministro());
                expPadre.setReclamanterazonsocial(expHijo.getReclamanterazonsocial());
                expPadre.setReclamanterepresentantelegal(expHijo.getReclamanterepresentantelegal());
                expPadre.setReclamantetelefono(expHijo.getReclamantetelefono());
                //validar que pueda ingresar valores nulos en el ubigeo real y procesal
                if (expHijo.getReclamanteUbigeoReal() != null && expHijo.getReclamanteUbigeoReal().getIddistrito() != null) {
                    expPadre.setReclamanteUbigeoReal(expHijo.getReclamanteUbigeoReal());
                } else {
                    expPadre.setReclamanteUbigeoReal(null);
                }
                if (expHijo.getReclamanteUbigeoProcesal() != null && expHijo.getReclamanteUbigeoProcesal().getIddistrito() != null) {
                    expPadre.setReclamanteUbigeoProcesal(expHijo.getReclamanteUbigeoProcesal());
                } else {
                    expPadre.setReclamanteUbigeoProcesal(null);
                }
                Expedientestor expedientestor = expedientestorService.saveExpedientestor(expPadre);
                expedienteBD.setExpedientestor(expedientestor);
            }
            expedienteBD.setConcesionario(expediente.getConcesionario());
            expedienteService.saveExpediente(expedienteBD);
        } catch (Exception e) {
            e.printStackTrace();
            return Action.ERROR;
        }
        return "successSTOR";
    }

    public void agregarSubmotivo() {
        /**
         * CampoA es el idExpedientestor
         */
        try {
            this.mensajeError = "";
            SubmotivoXExpedienteStorPK expedienteSubmotivoPK = new SubmotivoXExpedienteStorPK();
            expedienteSubmotivoPK.setIdexpediente(Integer.decode(campoA));
            expedienteSubmotivoPK.setIdsubmotivo(submotivoExpediente.getSubmotivo().getIdsubmotivo());
            submotivoExpediente.setSubmotivoxexpedientePK(expedienteSubmotivoPK);
            expedientestorService.agregarSubmotivo(submotivoExpediente);
        } catch (Exception e) {
            e.printStackTrace();
            this.mensajeError = e.getMessage();
        } finally {
            submotivoExpediente = new SubmotivoXExpedienteStor();
        }

    }

    public void retirarSubmotivos() {
        /**CampoA es el idExpedientestor
         * strAEliinar son los id de los submotivos
         */
        if (!(strAEliminar == null || strAEliminar.length == 0)) {
            for (String s : strAEliminar) {
                expedientestorService.retirarSubmotivo(Integer.decode(s), Integer.decode(campoA));
            }
        }
    }

    public void agregarSuministros() {
        /**
         * CampoA es el idExpedientestor
         */
        try {
            this.mensajeError = "";
            Expediente e = this.expedienteService.findByIdExpediente(Integer.decode(campoA));
            suministro.setExpediente(e);
            expedientestorService.agregarSuministro(suministro);
        } catch (Exception e) {
            e.printStackTrace();
            this.mensajeError = e.getMessage();
        } finally {
            suministro = new Suministro();
        }

    }

    public void retirarSuministros() {
        /**CampoA es el idExpedientestor
         * strAEliinar son los id de los submotivos
         */
        if (!(strAEliminar == null || strAEliminar.length == 0)) {
            for (String s : strAEliminar) {
                expedientestorService.retirarSuministros(Integer.decode(s));
            }
        }
    }

    // ////////////////////
    // GETERS AND SETTERS//
    // ////////////////////
    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public List<Motivo> getListaMotivos() {
        return listaMotivos;
    }

    public void setListaMotivos(List<Motivo> listaMotivos) {
        this.listaMotivos = listaMotivos;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public DocumentoService getDocumentoService() {
        return documentoService;
    }

    public void setDocumentoService(DocumentoService objSrvDocumento) {
        this.documentoService = objSrvDocumento;
    }

    public Expediente getExpediente() {
        return expediente;
    }

    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }

    public ExpedienteService getExpedienteService() {
        return expedienteService;
    }

    public void setExpedienteService(ExpedienteService expedienteService) {
        this.expedienteService = expedienteService;
    }

    public int getIdActividadExpediente() {
        return idActividadExpediente;
    }

    public void setIdActividadExpediente(int idActividadExpediente) {
        this.idActividadExpediente = idActividadExpediente;
    }

    public RolService getRolService() {
        return rolService;
    }

    public void setRolService(RolService rolService) {
        this.rolService = rolService;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public ExpedientestorService getExpedientestorService() {
        return expedientestorService;
    }

    public void setExpedientestorService(ExpedientestorService expedientestorService) {
        this.expedientestorService = expedientestorService;
    }

    public Integer getIdanalista() {
        return idanalista;
    }

    public void setIdanalista(Integer idanalista) {
        this.idanalista = idanalista;
    }

    public Integer getIdrevisorlegal() {
        return idrevisorlegal;
    }

    public void setIdrevisorlegal(Integer idrevisorlegal) {
        this.idrevisorlegal = idrevisorlegal;
    }

    public Integer getIdrevisortecnico() {
        return idrevisortecnico;
    }

    public void setIdrevisortecnico(Integer idrevisortecnico) {
        this.idrevisortecnico = idrevisortecnico;
    }

    public Integer getIdsala() {
        return idsala;
    }

    public void setIdsala(Integer idsala) {
        this.idsala = idsala;
    }

    public Integer getIdtipoanalisis() {
        return idtipoanalisis;
    }

    public void setIdtipoanalisis(Integer idtipoanalisis) {
        this.idtipoanalisis = idtipoanalisis;
    }

    public String getTipoanalisis() {
        return tipoanalisis;
    }

    public void setTipoanalisis(String tipoanalisis) {
        this.tipoanalisis = tipoanalisis;
    }

    public UsuariostorService getUsuariostorService() {
        return usuariostorService;
    }

    public void setUsuariostorService(UsuariostorService usuariostorService) {
        this.usuariostorService = usuariostorService;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getDerivarvbrst() {
        return derivarvbrst;
    }

    public void setDerivarvbrst(String derivarvbrst) {
        this.derivarvbrst = derivarvbrst;
    }

    public String getFechaaprobacion() {
        return fechaaprobacion;
    }

    public void setFechaaprobacion(String fechaaprobacion) {
        this.fechaaprobacion = fechaaprobacion;
    }

    public String getFnconcesionario() {
        return fnconcesionario;
    }

    public void setFnconcesionario(String fnconcesionario) {
        this.fnconcesionario = fnconcesionario;
    }

    public String getFnusuario() {
        return fnusuario;
    }

    public void setFnusuario(String fnusuario) {
        this.fnusuario = fnusuario;
    }

    public String getNroresolucion() {
        return nroresolucion;
    }

    public void setNroresolucion(String nroresolucion) {
        this.nroresolucion = nroresolucion;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public Integer getIdresultadoresolucion() {
        return idresultadoresolucion;
    }

    public void setIdresultadoresolucion(Integer idresultadoresolucion) {
        this.idresultadoresolucion = idresultadoresolucion;
    }

    public String getRefrescar() {
        return refrescar;
    }

    public void setRefrescar(String refrescar) {
        this.refrescar = refrescar;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getObservacionaprobar() {
        return observacionaprobar;
    }

    public void setObservacionaprobar(String observacionaprobar) {
        this.observacionaprobar = observacionaprobar;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getObservacioneditar() {
        return observacioneditar;
    }

    public void setObservacioneditar(String observacioneditar) {
        this.observacioneditar = observacioneditar;
    }

    public String getObservacioncambiosala() {
        return observacioncambiosala;
    }

    public void setObservacioncambiosala(String observacioncambiosala) {
        this.observacioncambiosala = observacioncambiosala;
    }

    public String getObservacionrechazar() {
        return observacionrechazar;
    }

    public void setObservacionrechazar(String observacionrechazar) {
        this.observacionrechazar = observacionrechazar;
    }

    public String getObservacionanular() {
        return observacionanular;
    }

    public void setObservacionanular(String observacionanular) {
        this.observacionanular = observacionanular;
    }

    public String getObservacionreformular() {
        return observacionreformular;
    }

    public void setObservacionreformular(String observacionreformular) {
        this.observacionreformular = observacionreformular;
    }

    public String getRevisortecnico() {
        return revisortecnico;
    }

    public void setRevisortecnico(String revisortecnico) {
        this.revisortecnico = revisortecnico;
    }

    public String getEstadoexpedientestor() {
        return estadoexpedientestor;
    }

    public void setEstadoexpedientestor(String estadoexpedientestor) {
        this.estadoexpedientestor = estadoexpedientestor;
    }

    public String getRevisorlegal() {
        return revisorlegal;
    }

    public void setRevisorlegal(String revisorlegal) {
        this.revisorlegal = revisorlegal;
    }

    public String getCumplerequisitosst() {
        return cumplerequisitosst;
    }

    public void setCumplerequisitosst(String cumplerequisitosst) {
        this.cumplerequisitosst = cumplerequisitosst;
    }

    public String getNroexpediente() {
        return nroexpediente;
    }

    public void setNroexpediente(String nroexpediente) {
        this.nroexpediente = nroexpediente;
    }

    public String getMotivocambiosala() {
        return motivocambiosala;
    }

    public void setMotivocambiosala(String motivocambiosala) {
        this.motivocambiosala = motivocambiosala;
    }

    public String getSalaanterior() {
        return salaanterior;
    }

    public void setSalaanterior(String salaanterior) {
        this.salaanterior = salaanterior;
    }

    public String getAprobarcambiosala() {
        return aprobarcambiosala;
    }

    public void setAprobarcambiosala(String aprobarcambiosala) {
        this.aprobarcambiosala = aprobarcambiosala;
    }

    public String getReformulacionlegal() {
        return reformulacionlegal;
    }

    public void setReformulacionlegal(String reformulacionlegal) {
        this.reformulacionlegal = reformulacionlegal;
    }

    public String getReformulaciontecnica() {
        return reformulaciontecnica;
    }

    public void setReformulaciontecnica(String reformulaciontecnica) {
        this.reformulaciontecnica = reformulaciontecnica;
    }

    public String getRevisionlegal() {
        return revisionlegal;
    }

    public void setRevisionlegal(String revisionlegal) {
        this.revisionlegal = revisionlegal;
    }

    public String getRevisiontecnica() {
        return revisiontecnica;
    }

    public void setRevisiontecnica(String revisiontecnica) {
        this.revisiontecnica = revisiontecnica;
    }

    // /Geters and Setter de Datos Expediente
    public String getRadioRuc() {
        return radioRuc;
    }

    public void setRadioRuc(String radioRuc) {
        this.radioRuc = radioRuc;
    }

    /**
     * @return the radiotipodocidentidad
     */
    /*
     * public Map getRadiotipodocidentidad() { return radiotipodocidentidad; }
     */
    /**
     * @param radiotipodocidentidad
     *            the radiotipodocidentidad to set
     */
    /*
     * public void setRadiotipodocidentidad(Map radiotipodocidentidad) {
     * this.radiotipodocidentidad = radiotipodocidentidad; }
     */
    public String getTipodocidentidad() {
        return tipodocidentidad;
    }

    public void setTipodocidentidad(String tipodocidentidad) {
        this.tipodocidentidad = tipodocidentidad;
    }

    public String getNrodocidentidad() {
        return nrodocidentidad;
    }

    public void setNrodocidentidad(String nrodocidentidad) {
        this.nrodocidentidad = nrodocidentidad;
    }

    public StorManagerService getStorManagerService() {
        return storManagerService;
    }

    public void setStorManagerService(StorManagerService storManagerService) {
        this.storManagerService = storManagerService;
    }

    public RepositorioService getSrvRepositorio() {
        return srvRepositorio;
    }

    public void setSrvRepositorio(RepositorioService srvRepositorio) {
        this.srvRepositorio = srvRepositorio;
    }

    public String getNrosuministro() {
        return nrosuministro;
    }

    public void setNrosuministro(String nrosuministro) {
        this.nrosuministro = nrosuministro;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String[] getListsuministros() {
        return listsuministros;
    }

    public void setListsuministros(String[] listsuministros) {
        this.listsuministros = listsuministros;
    }

    public Integer[] getListidsubmotivos() {
        return listidsubmotivos;
    }

    public void setListidsubmotivos(Integer[] listidsubmotivos) {
        this.listidsubmotivos = listidsubmotivos;
    }

    public Integer[] getListnuevosidsubmotivos() {
        return listnuevosidsubmotivos;
    }

    public void setListnuevosidsubmotivos(Integer[] listnuevosidsubmotivos) {
        this.listnuevosidsubmotivos = listnuevosidsubmotivos;
    }

    public String getNmotivo() {
        return nmotivo;
    }

    public void setNmotivo(String nmotivo) {
        this.nmotivo = nmotivo;
    }

    public String getNsubmotivo() {
        return nsubmotivo;
    }

    public void setNsubmotivo(String nsubmotivo) {
        this.nsubmotivo = nsubmotivo;
    }

    public Integer getIdsubmotivo() {
        return idsubmotivo;
    }

    public void setIdsubmotivo(Integer idsubmotivo) {
        this.idsubmotivo = idsubmotivo;
    }

    public String getTipomensaje() {
        return tipomensaje;
    }

    public void setTipomensaje(String tipomensaje) {
        this.tipomensaje = tipomensaje;
    }

    public String getValormensaje() {
        return valormensaje;
    }

    public void setValormensaje(String valormensaje) {
        this.valormensaje = valormensaje;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getTipoVentanaDetalle() {
        return tipoVentanaDetalle;
    }

    public void setTipoVentanaDetalle(String tipoVentanaDetalle) {
        this.tipoVentanaDetalle = tipoVentanaDetalle;
    }

    public DocumentoStor getDocumentoStor() {
        return documentoStor;
    }

    public void setDocumentoStor(DocumentoStor documentoStor) {
        this.documentoStor = documentoStor;
    }

    /*
     * public Actividad getActividad() { return actividad; }
     *
     * public void setActividad(Actividad actividad) { this.actividad =
     * actividad; }
     */
    // Getters y Setters por Germán Enríquez
    public boolean isInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(boolean infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public boolean isInspeccionCampo() {
        return inspeccionCampo;
    }

    public void setInspeccionCampo(boolean inspeccionCampo) {
        this.inspeccionCampo = inspeccionCampo;
    }

    public boolean isAudienciaConciliacion() {
        return audienciaConciliacion;
    }

    public void setAudienciaConciliacion(boolean audienciaConciliacion) {
        this.audienciaConciliacion = audienciaConciliacion;
    }

    public NotificacionService getServicioNotificacion() {
        return servicioNotificacion;
    }

    public void setServicioNotificacion(NotificacionService servicioNotificacion) {
        this.servicioNotificacion = servicioNotificacion;
    }

    public Boolean getExisteRevisorTecnico() {
        return existeRevisorTecnico;
    }

    public void setExisteRevisorTecnico(Boolean existeRevisorTecnico) {
        this.existeRevisorTecnico = existeRevisorTecnico;
    }

    public Boolean getExisteRevisorLegal() {
        return existeRevisorLegal;
    }

    public void setExisteRevisorLegal(Boolean existeRevisorLegal) {
        this.existeRevisorLegal = existeRevisorLegal;
    }

    public TrazabilidaddocumentoService getTrazabilidaddocumentoService() {
        return trazabilidaddocumentoService;
    }

    public void setTrazabilidaddocumentoService(TrazabilidaddocumentoService trazabilidaddocumentoService) {
        this.trazabilidaddocumentoService = trazabilidaddocumentoService;
    }

    public DocumentoEnviadoService getDocumentoEnviadoService() {
        return documentoEnviadoService;
    }

    public void setDocumentoEnviadoService(DocumentoEnviadoService documentoEnviadoService) {
        this.documentoEnviadoService = documentoEnviadoService;
    }

    public TrazabilidadexpedienteService getTrazabilidadexpedienteService() {
        return trazabilidadexpedienteService;
    }

    public void setTrazabilidadexpedienteService(TrazabilidadexpedienteService trazabilidadexpedienteService) {
        this.trazabilidadexpedienteService = trazabilidadexpedienteService;
    }

    public String getRequiredObservacion() {
        return requiredObservacion;
    }

    public void setRequiredObservacion(String requiredObservacion) {
        this.requiredObservacion = requiredObservacion;
    }

    public Trazabilidadexpediente getUltimaTrazabilidad() {
        return ultimaTrazabilidad;
    }

    public void setUltimaTrazabilidad(Trazabilidadexpediente ultimaTrazabilidad) {
        this.ultimaTrazabilidad = ultimaTrazabilidad;
    }

    public String getCodSala() {
        return codSala;
    }

    public void setCodSala(String codSala) {
        this.codSala = codSala;
    }

    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado;
    }

    public String getCodResultado() {
        return codResultado;
    }

    public void setCodResultado(String codResultado) {
        this.codResultado = codResultado;
    }

    public String getCodVocal() {
        return codVocal;
    }

    public void setCodVocal(String codVocal) {
        this.codVocal = codVocal;
    }

    public String getFechaSesion() {
        return fechaSesion;
    }

    public void setFechaSesion(String fechaSesion) {
        this.fechaSesion = fechaSesion;
    }

    public String getFechaNotiReclamante() {
        return fechaNotiReclamante;
    }

    public void setFechaNotiReclamante(String fechaNotiReclamante) {
        this.fechaNotiReclamante = fechaNotiReclamante;
    }

    public String getFechaNotiConcesionario() {
        return fechaNotiConcesionario;
    }

    public void setFechaNotiConcesionario(String fechaNotiConcesionario) {
        this.fechaNotiConcesionario = fechaNotiConcesionario;
    }

    public String getEtapaActual() {
        return etapaActual;
    }

    public void setEtapaActual(String etapaActual) {
        this.etapaActual = etapaActual;
    }

    public String getFechaDerivacion() {
        return fechaDerivacion;
    }

    public void setFechaDerivacion(String fechaDerivacion) {
        this.fechaDerivacion = fechaDerivacion;
    }

    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    public String getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(String usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public String getRemitenteObservacion() {
        return remitenteObservacion;
    }

    public void setRemitenteObservacion(String remitenteObservacion) {
        this.remitenteObservacion = remitenteObservacion;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }

    public SalaService getSalaService() {
        return salaService;
    }

    public void setSalaService(SalaService salaService) {
        this.salaService = salaService;
    }

    public EstadoService getEstadoService() {
        return estadoService;
    }

    public void setEstadoService(EstadoService estadoService) {
        this.estadoService = estadoService;
    }

    public TiporesultadoService getTipoResultadoService() {
        return tipoResultadoService;
    }

    public void setTipoResultadoService(TiporesultadoService tipoResultadoService) {
        this.tipoResultadoService = tipoResultadoService;
    }

    public VocalService getVocalService() {
        return vocalService;
    }

    public void setVocalService(VocalService vocalService) {
        this.vocalService = vocalService;
    }

    public ResolucionjaruService getResolucionjaruService() {
        return resolucionjaruService;
    }

    public void setResolucionjaruService(ResolucionjaruService resolucionjaruService) {
        this.resolucionjaruService = resolucionjaruService;
    }

    public ConcesionarioService getConcesionarioService() {
        return concesionarioService;
    }

    public void setConcesionarioService(ConcesionarioService concesionarioService) {
        this.concesionarioService = concesionarioService;
    }

    public Cliente getObjReclamante() {
        return objReclamante;
    }

    public void setObjReclamante(Cliente objReclamante) {
        this.objReclamante = objReclamante;
    }

    public Expediente getObjExpediente() {
        return objExpediente;
    }

    public void setObjExpediente(Expediente objExpediente) {
        this.objExpediente = objExpediente;
    }

    public String getCampoA() {
        return campoA;
    }

    public void setCampoA(String campoA) {
        this.campoA = campoA;
    }

    public String getCampoB() {
        return campoB;
    }

    public void setCampoB(String campoB) {
        this.campoB = campoB;
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public String[] getStrAEliminar() {
        return strAEliminar;
    }

    public void setStrAEliminar(String[] strAEliminar) {
        this.strAEliminar = strAEliminar;
    }

    public SubmotivoXExpedienteStor getSubmotivoExpediente() {
        return submotivoExpediente;
    }

    public void setSubmotivoExpediente(SubmotivoXExpedienteStor submotivoExpediente) {
        this.submotivoExpediente = submotivoExpediente;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public Suministro getSuministro() {
        return suministro;
    }

    public void setSuministro(Suministro suministro) {
        this.suministro = suministro;
    }

}