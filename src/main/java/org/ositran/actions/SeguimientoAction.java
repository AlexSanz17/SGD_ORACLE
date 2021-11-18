/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.FilaSeguimiento;
import com.btg.ositran.siged.domain.SeguimientoHoraDia;
import com.btg.ositran.siged.domain.SeguimientoHoraSemana;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.log4j.Logger;
import org.ositran.services.DocumentoService;
import org.ositran.services.RepositorioService;
import org.ositran.services.SeguimientoService;
import org.ositran.services.TrazabilidaddocumentoService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;
import org.ositran.utils.DateUtil;

public class SeguimientoAction{

	private static Logger log=Logger.getLogger(SeguimientoAction.class);

	public static final String PATH_INICIO="inicio";
	public static final String PATH_DIARIO="diario";
	public static final String PATH_SEMANAL="semanal";
	public static final String PATH_DOCUMENTO="documento";
	public static final String PATH_PLAZO="plazo";

	private Boolean bResponsable;
	private Boolean propios;

	private Integer variacion;
	private Integer iIdProceso;
	private Integer idtrazabilidaddocumento;

	private String cerrar;
	private String filtro1;
	private String nuevaFechaLimite;
	private String fechaActualString;
	private String fechaInicioStr;
	private String fechaFinStr;

	private Timestamp fechaActual;
	private Date fechaInicio;
	private Date fechaFin;
	private Date[] semana;

	private Trazabilidaddocumento trazabilidad ;
	private List<SeguimientoHoraDia> documentosPendiente;
	private List<SeguimientoHoraSemana> documentosPendienteSemana;
	private List<FilaSeguimiento> seguimiento;

	DocumentoService documentoService;
	RepositorioService repositorioService;
	UsuarioService usuarioService;
	SeguimientoService seguimientoService;
	TrazabilidaddocumentoService trazabilidaddocumentoService ;

	public SeguimientoAction(){}

	public SeguimientoAction(DocumentoService documentoService,RepositorioService repositorioService,UsuarioService usuarioService,SeguimientoService seguimientoService,TrazabilidaddocumentoService trazabilidaddocumentoService){
		this.documentoService=documentoService;
		this.repositorioService=repositorioService;
		this.usuarioService=usuarioService;
		this.seguimientoService=seguimientoService;
		this.trazabilidaddocumentoService = trazabilidaddocumentoService ;
	}

	public String mostrarInicio(){
		log.debug("-> [Action] SeguimientoAction - mostrarInicio():String ");
        this.fechaActualString=new SimpleDateFormat("yyyyMMdd").format(new Date());
		this.fechaActual=new Timestamp(System.currentTimeMillis());
		this.fechaInicio = new Timestamp(System.currentTimeMillis() -1000*60*60*24*90);//DateUtil.getInicioSemana(new Date());
		this.fechaFin = new Timestamp(System.currentTimeMillis() + 1000*60*60*24*15);//DateUtil.getFinSemana(new Date());
		return PATH_INICIO;
	}

    public String viewAlerta() throws Exception {
    	return Action.SUCCESS;
    }

	public String mostrarDiario(){
		log.debug("-> [Action] SeguimientoAction - mostrarDiario():String ");

		try{
			Map<String,Object> session=ActionContext.getContext().getSession();
			Integer idusuario= (((Usuario) session.get(Constantes.SESSION_USUARIO) ).getIdusuario());
			if(this.fechaActualString==null){
				this.fechaActual=new Timestamp(System.currentTimeMillis());
			}else{
				this.fechaActual=new Timestamp((new SimpleDateFormat("yyyyMMdd").parse(this.fechaActualString)).getTime());
			}
			log.debug(" fechaActual: "+this.fechaActual);
			this.documentosPendiente=seguimientoService.getListaDocumentosPendientesByDia(fechaActual,idusuario,filtro1,iIdProceso);
		}catch(Exception ex){
			log.error(ex.getMessage(), ex);
		}
		return PATH_DIARIO;
	}


	public String mostrarSemanal(){
		log.debug("-> [Action] SeguimientoAction - mostrarSemanal():String ");

		log.debug(" metodo  ::mostrarSemanal ");
		log.debug("** Filtro Seguimiento **");
		log.debug("filtro1 ["+filtro1+"]");
		log.debug("iIdProceso ["+iIdProceso+"]");
		log.debug("bResponsable ["+bResponsable+"]");
		try{
			Map<String,Object> session=ActionContext.getContext().getSession();
			Integer idusuario= (((Usuario) session.get(Constantes.SESSION_USUARIO) ).getIdusuario());
			Locale locale = new Locale("hi", "IN");
			session.put("org.apache.tiles.LOCALE", locale);

			bResponsable=usuarioService.esResponsableProceso(idusuario);
			log.debug("Responsable ["+bResponsable+"]");

			if(this.fechaInicioStr != null && this.fechaFinStr != null){
				SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
				this.fechaInicio = fechita.parse(fechaInicioStr);
				this.fechaFin = fechita.parse(fechaFinStr);
				this.fechaFin.setHours(23);
				this.fechaFin.setMinutes(59);
				this.fechaFin.setSeconds(59);
			}else{
				this.fechaInicio = new Timestamp(System.currentTimeMillis() - 1000*60*60*24*15 - 1000*60*60*24*15- 1000*60*60*24*15 - 1000*60*60*24*15- 1000*60*60*24*15 - 1000*60*60*24*15);//DateUtil.getInicioSemana(new Date());
				this.fechaFin =    new Timestamp(System.currentTimeMillis()  + 1000*60*60*24*15);//DateUtil.getFinSemana(new Date());
				
			}

			if(propios == null){
				propios = true;
			}

			if(iIdProceso == null){
				iIdProceso = 0;
			}

			
			seguimiento = seguimientoService.getListaDocumentosPendientesBySemana(fechaInicio, fechaFin,idusuario,filtro1,iIdProceso, propios);

		}catch(Exception ex){
			log.error(ex.getMessage(), ex);
		}
		return PATH_SEMANAL;
	}

	public String verCambiarPlazo(){
		log.debug("-> [Action] SeguimientoAction - verCambiarPlazo():String ");

		//Map<String,Object> session=ActionContext.getContext().getSession();
		//Integer idusuario = (((Usuario) session.get(Constantes.SESSION_USUARIO) ).getIdusuario());
		this.trazabilidad = trazabilidaddocumentoService.findTrabilidadbyId(idtrazabilidaddocumento) ;
		return PATH_PLAZO;
	}

	@SuppressWarnings("unused")
	public String cambiarPlazo(){
		log.debug("-> [Action] SeguimientoAction - cambiarPlazo():String ");

		Map<String,Object> session=ActionContext.getContext().getSession();
		Integer idusuario = (((Usuario) session.get(Constantes.SESSION_USUARIO) ).getIdusuario());
		this.trazabilidad = trazabilidaddocumentoService.findTrabilidadbyId(idtrazabilidaddocumento) ;

		Date nuevafecha = null;

		try{
			nuevafecha = new SimpleDateFormat("yyyy-MM-dd").parse(this.nuevaFechaLimite);
		}catch(Exception ex){
			log.error(ex.getMessage(), ex);
		}

		log.debug(" nueva fecha "+nuevafecha) ;

		trazabilidaddocumentoService.cambiarFechaLimite(idtrazabilidaddocumento, nuevafecha);
		this.cerrar ="ok";
		return PATH_PLAZO;
	}

	public String mostrarDocumento(){
		log.debug("-> [Action] SeguimientoAction - mostrarDocumento():String ");

		return PATH_DOCUMENTO;
	}

	public DocumentoService getDocumentoService(){
		return documentoService;
	}

	public void setDocumentoService(DocumentoService documentoService){
		this.documentoService=documentoService;
	}

	public RepositorioService getRepositorioService(){
		return repositorioService;
	}

	public void setRepositorioService(RepositorioService repositorioService){
		this.repositorioService=repositorioService;
	}

	public UsuarioService getUsuarioService(){
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService){
		this.usuarioService=usuarioService;
	}

	public String getFiltro1(){
		return filtro1;
	}

	public void setFiltro1(String filtro1){
		this.filtro1=filtro1;
	}

	public Integer getIIdProceso(){
		return iIdProceso;
	}

	public void setIIdProceso(Integer iIdProceso){
		this.iIdProceso=iIdProceso;
	}

	public Boolean getBResponsable(){
		return bResponsable;
	}

	public void setBResponsable(Boolean bResponsable){
		this.bResponsable=bResponsable;
	}

	public Integer getVariacion(){
		return variacion;
	}

	public void setVariacion(Integer variacion){
		this.variacion=variacion;
	}

	public String getNuevaFechaLimite() {
		return nuevaFechaLimite;
	}

	public void setNuevaFechaLimite(String nuevaFechaLimite) {
		this.nuevaFechaLimite = nuevaFechaLimite;
	}

	public TrazabilidaddocumentoService getTrazabilidaddocumentoService() {
		return trazabilidaddocumentoService;
	}

	public void setTrazabilidaddocumentoService(
			TrazabilidaddocumentoService trazabilidaddocumentoService) {
		this.trazabilidaddocumentoService = trazabilidaddocumentoService;
	}

	public Integer getIdtrazabilidaddocumento() {
		return idtrazabilidaddocumento;
	}

	public void setIdtrazabilidaddocumento(Integer idtrazabilidaddocumento) {
		this.idtrazabilidaddocumento = idtrazabilidaddocumento;
	}

	public Trazabilidaddocumento getTrazabilidad() {
		return trazabilidad;
	}

	public void setTrazabilidad(Trazabilidaddocumento trazabilidad) {
		this.trazabilidad = trazabilidad;
	}

	public String getCerrar() {
		return cerrar;
	}

	public void setCerrar(String cerrar) {
		this.cerrar = cerrar;
	}

	public SeguimientoService getSeguimientoService(){
		return seguimientoService;
	}

	public void setSeguimientoService(SeguimientoService seguimientoService){
		this.seguimientoService=seguimientoService;
	}

	public Date[] getSemana(){
		return semana;
	}

	public void setSemana(Date[] semana){
		this.semana=semana;
	}

	public String getFechaActualString(){
		return fechaActualString;
	}

	public void setFechaActualString(String fechaActualString){
		this.fechaActualString=fechaActualString;
	}

	public List<SeguimientoHoraDia> getDocumentosPendiente(){
		return documentosPendiente;
	}

	public List<SeguimientoHoraSemana> getDocumentosPendienteSemana(){
		return documentosPendienteSemana;
	}

	public void setDocumentosPendienteSemana(List<SeguimientoHoraSemana> documentosPendienteSemana){
		this.documentosPendienteSemana=documentosPendienteSemana;
	}

	public void setDocumentosPendiente(List<SeguimientoHoraDia> documentosPendiente){
		this.documentosPendiente=documentosPendiente;
	}

	public Timestamp getFechaActual(){
		return fechaActual;
	}

	public void setFechaActual(Timestamp fechaActual){
		this.fechaActual=fechaActual;
	}

	public List<FilaSeguimiento> getSeguimiento() {
		return seguimiento;
	}

	public void setSeguimiento(List<FilaSeguimiento> seguimiento) {
		this.seguimiento = seguimiento;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaInicioStr() {
		return fechaInicioStr;
	}

	public void setFechaInicioStr(String fechaInicioStr) {
		this.fechaInicioStr = fechaInicioStr;
	}

	public String getFechaFinStr() {
		return fechaFinStr;
	}

	public void setFechaFinStr(String fechaFinStr) {
		this.fechaFinStr = fechaFinStr;
	}

	public Boolean getPropios() {
		return propios;
	}

	public void setPropios(Boolean propios) {
		this.propios = propios;
	}
}
