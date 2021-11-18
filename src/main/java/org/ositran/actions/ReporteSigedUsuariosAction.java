/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.ositran.services.DiafestivoService;
import org.ositran.services.ReporteSigedUsuariosService;

import com.btg.ositran.siged.domain.ExpedientesPendientesAYQ;
import com.opensymphony.xwork2.Action;

public class ReporteSigedUsuariosAction {
	private ReporteSigedUsuariosService serviceReporteUsuarios;
	private DiafestivoService serviceDiaFestivo;
	private static Log 	log	= LogFactory.getLog(ReporteSigedAction.class);
	/***********************************************************************/
	private String fechaDesde;
	private String fechaHasta;
	private String tipoExpediente;
	private String sala;
	private String responsable;
	private String analista;
	private String vencimientoDesde;
	private String vencimientoHasta;
	/***********************************************************************/
	private List<ExpedientesPendientesAYQ> listaAYQ;
	
	public String inicioAYQ (){
		return "inicioAYQ";
	}
	
	public String listarAYQ(){
		HttpServletRequest request = ServletActionContext.getRequest();
		SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
		try{
			this.tipoExpediente = new String(request.getParameter("tipoExpediente").getBytes("ISO-8859-1"), "UTF-8");
			this.fechaDesde = new String(request.getParameter("fechaDesdeAYQ").getBytes("ISO-8859-1"), "UTF-8");
			this.fechaHasta = new String(request.getParameter("fechaHastaAYQ").getBytes("ISO-8859-1"), "UTF-8");
			this.sala = new String(request.getParameter("salaAYQ").getBytes("ISO-8859-1"), "UTF-8");
			this.responsable = new String(request.getParameter("responsableAYQ").getBytes("ISO-8859-1"), "UTF-8");
			this.analista = new String(request.getParameter("analistaAYQ").getBytes("ISO-8859-1"), "UTF-8");
			this.vencimientoDesde = new String(request.getParameter("vencimientoDesdeAYQ").getBytes("ISO-8859-1"), "UTF-8");
			this.vencimientoHasta = new String(request.getParameter("vencimientoHastaAYQ").getBytes("ISO-8859-1"), "UTF-8");
			/**************************************
			log.debug(tipoExpediente);
			log.debug(fechaDesde);
			log.debug(fechaHasta);
			log.debug(sala);
			log.debug(responsable);
			log.debug(analista);
			log.debug(vencimientoDesde);
			log.debug(vencimientoHasta);
			**************************************/
		}catch(UnsupportedEncodingException ex){
			log.error(ex.getMessage(), ex);
		}
		if(checkErrorFechas()){
			listaAYQ = serviceReporteUsuarios.generarListaAYQ(tipoExpediente, fechaDesde, fechaHasta, sala, responsable, analista, vencimientoDesde, vencimientoHasta);
			Date fechaD;
			if(fechaDesde==null||fechaDesde.equals("")){
				fechaD = listaAYQ.get(0).getFechaCreacionDate();
			}else{
				try {
					fechaD = fechita.parse(fechaDesde);
				} catch (ParseException e) {
					return "errorFechas";
				}
			}
			Date ahora = new Date(System.currentTimeMillis());
			serviceDiaFestivo.setIntervaloFeriados(fechaD, ahora, "LIMA");
			if(listaAYQ!=null||!listaAYQ.isEmpty()){
				for(ExpedientesPendientesAYQ exp : listaAYQ){
					if(exp.getFechaCreacionDate()==null){
						//Error absurdo
						exp.setDiasTranscurridos(-1l);
					}else{
						exp.setDiasTranscurridos(serviceDiaFestivo.tiempoTranscurrido(exp.getFechaCreacionDate(), ahora, "LIMA"));
					}					
				}
			}
			return Action.SUCCESS+"AYQ";
		}else{
			return "errorFechas";
		}
	}
	
	private boolean checkErrorFechas(){
		/**
		 * Este error es causado cuando hay un conflicto entre el nombre de variables
		 * que son usadas en el reporte. Para que desaparezca debes revisar el nombre
		 * de las variables de los javascript y JSP.
		 */
		SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
		if((fechaDesde.equals("")&&fechaHasta.equals(""))||(fechaDesde==null&&fechaHasta==null)){
			if(vencimientoDesde.equals("")&&vencimientoHasta.equals("")){
				return true;
			}
			if(vencimientoDesde==null&&vencimientoHasta==null){
				return true;
			}
			
			try{
				fechita.parse(vencimientoDesde);
			}catch(ParseException e){
				log.warn("ERROR!, ha ocurrido un error con el campo VencimientoDesde y se ha desactivado este filtro");
				return false;
			}
			try{
				fechita.parse(vencimientoHasta);
			}catch(ParseException e){
				log.warn("ERROR!, ha ocurrido un error con el campo VencimientoHasta y se ha desactivado este filtro");
				return false;
			}
		}else{			
			try{
				fechita.parse(fechaDesde);
			}catch(ParseException e){
				log.warn("ERROR!, ha ocurrido un error con el campo FechaDesde y se han desactivado los filtros");
				return false;
			}
			try{
				fechita.parse(fechaHasta);
			}catch(ParseException e){
				log.warn("ERROR!, ha ocurrido un error con el campo FechaHasta y se han desactivado los filtros");
				return false;
			}
		}
		return true;
	}
	
	public ReporteSigedUsuariosService getServiceReporteUsuarios() {
		return serviceReporteUsuarios;
	}

	public void setServiceReporteUsuarios(
			ReporteSigedUsuariosService serviceReporteUsuarios) {
		this.serviceReporteUsuarios = serviceReporteUsuarios;
	}

	public static Log getLog() {
		return log;
	}

	public static void setLog(Log log) {
		ReporteSigedUsuariosAction.log = log;
	}

	public List<ExpedientesPendientesAYQ> getListaAYQ() {
		return listaAYQ;
	}

	public void setListaAYQ(List<ExpedientesPendientesAYQ> listaAYQ) {
		this.listaAYQ = listaAYQ;
	}

	public DiafestivoService getServiceDiaFestivo() {
		return serviceDiaFestivo;
	}

	public void setServiceDiaFestivo(DiafestivoService serviceDiaFestivo) {
		this.serviceDiaFestivo = serviceDiaFestivo;
	}
	
}
