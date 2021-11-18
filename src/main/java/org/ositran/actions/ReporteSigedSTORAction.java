/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.ositran.services.DiafestivoService;
import org.ositran.services.ReporteSigedSTORService;

import com.btg.ositran.siged.domain.ExpedientesConcluidosStor;
import com.btg.ositran.siged.domain.ExpedientesIngresadosStor;
import com.btg.ositran.siged.domain.ExpedientesPendientesStor;
import com.opensymphony.xwork2.Action;

public class ReporteSigedSTORAction {
	private ReporteSigedSTORService serviceReporteSTOR;
	private DiafestivoService serviceDiaFestivo;
	private static Log 	log	= LogFactory.getLog(ReporteSigedAction.class);
	private SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
	/***************************************/
	private List<ExpedientesPendientesStor> listaPendientes;
	private List<ExpedientesIngresadosStor> listaIngresados;
	private List<ExpedientesConcluidosStor> listaConcluidos;
	private String fechaDesde ;
	private String fechaHasta ;
	/****EXPORTAR A EXCEL***********************************/
	private String html;
	/**************************************/
	
	public String inicioPendientes(){
		return "inicioPendientes";
	}
	
	public String inicioIngresados(){
		return "inicioIngresados";
	}
	
	public String inicioConcluidos(){
		return "inicioConcluidos";
	}
	
	public String pendientes(){
		return "todoPendientes";
	}
	public String ingresados(){
		return "todoIngresados";
	}
	
	public String concluidos(){
		return "todoConcluidos";
	}
	
	public String listarPendientes(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try{
			this.fechaDesde = new String(request.getParameter("fechaDesde").getBytes("ISO-8859-1"), "UTF-8");
			this.fechaHasta = new String(request.getParameter("fechaHasta").getBytes("ISO-8859-1"), "UTF-8");
		}catch(UnsupportedEncodingException e){
                        log.error(e.getMessage(),e);
			return "error";
		}
		if(checkErrorFechas()){
			listaPendientes = serviceReporteSTOR.getListaPendientes(fechaDesde, fechaHasta);
			try {
				Date ahora = new Date(System.currentTimeMillis());
				serviceDiaFestivo.setIntervaloFeriados(fechita.parse(fechaDesde), ahora, "LIMA");
				if(listaPendientes!=null||!listaPendientes.isEmpty()){
					for(ExpedientesPendientesStor exp : listaPendientes){
						if(exp.getFechaCreacionDate()==null){
							//Error absurdo
							exp.setDiasTranscurridos(-1l);
						}else{
							exp.setDiasTranscurridos(serviceDiaFestivo.tiempoTranscurrido(exp.getFechaCreacionDate(), ahora, "LIMA"));
						}
						
					}
				}
				return Action.SUCCESS+"Pendientes";
			} catch (ParseException e) {
				log.error(e.getMessage(),e);
				return "errorFechas";
			}
		}else{
			return "errorFechas";
		}
	}
	
	public String listarIngresados(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try{
			this.fechaDesde = new String (request.getParameter("fechaDesde").getBytes("ISO-8859-1"), "UTF-8");
			this.fechaHasta = new String (request.getParameter("fechaHasta").getBytes("ISO-8859-1"), "UTF-8");
		}catch(UnsupportedEncodingException e){
			log.error(e.getMessage(),e);
			return "error";
		}
		if(checkErrorFechas()){
			listaIngresados = serviceReporteSTOR.getListaIngresados(fechaDesde, fechaHasta);
			try {
				Date ahora = new Date(System.currentTimeMillis());
				serviceDiaFestivo.setIntervaloFeriados(fechita.parse(fechaDesde), ahora, "LIMA");
				if(listaIngresados!=null||!listaIngresados.isEmpty()){
					for(ExpedientesIngresadosStor exp : listaIngresados){
						if(exp.getFechaAprobacionDate()==null){
							//Aun no es aprobado
							if(exp.getFechaCreacionDate()==null){
								//Error absurdo
								exp.setDiasTranscurridos(-1l);
							}else{
								exp.setDiasTranscurridos(serviceDiaFestivo.tiempoTranscurrido(exp.getFechaCreacionDate(), ahora, "LIMA"));
							}
						}else{
							//Ya fue aprobado
							if(exp.getFechaCreacionDate()==null){
								//Error absurdo
								exp.setDiasTranscurridos(-1l);
							}else{
								exp.setDiasTranscurridos(serviceDiaFestivo.tiempoTranscurrido(exp.getFechaCreacionDate(), exp.getFechaAprobacionDate(), "LIMA"));
							}
						}
						
					}
				}
				return Action.SUCCESS+"Ingresados";
			} catch (ParseException e) {
				log.error(e.getMessage(),e);
				return "errorFechas";
			}
		}else{
			return "errorFechas";
		}
	}
	
	public String listarConcluidos(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try{
			this.fechaDesde = new String (request.getParameter("fechaDesde").getBytes("ISO-8859-1"), "UTF-8");
			this.fechaHasta = new String (request.getParameter("fechaHasta").getBytes("ISO-8859-1"), "UTF-8");
		}catch(UnsupportedEncodingException e){
			log.error(e.getMessage(),e);
			return "error";
		}
		if(checkErrorFechas()){
			listaConcluidos = serviceReporteSTOR.getListaConcluidos(fechaDesde, fechaHasta);
			return Action.SUCCESS+"Concluidos";
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
		return true;
	}
	
	public String exportarExcel (){
		HttpServletResponse response = ServletActionContext.getResponse();
		String content;
		try{
			content = new String(html.getBytes("UTF-8"), "UTF-8");
			response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Pragma", "no-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setHeader("Content-Type", "application/vnd.ms-excel");
	        response.addHeader("Content-Disposition", "inline; filename=\"ReporteExportacion.xls\"");
	        ServletOutputStream out;
	        out = response.getOutputStream();
			out.write(content.getBytes());
	        out.flush();
		}catch (IOException e){
			log.debug("Detecto la falta");
			log.error(e.getMessage(),e);
		}
		
		return null;
	}
	
	public ReporteSigedSTORService getServiceReporteSTOR() {
		return serviceReporteSTOR;
	}

	public void setServiceReporteSTOR(ReporteSigedSTORService serviceReporteSTOR) {
		this.serviceReporteSTOR = serviceReporteSTOR;
	}

	public List<ExpedientesPendientesStor> getListaPendientes() {
		return listaPendientes;
	}

	public void setListaPendientes(List<ExpedientesPendientesStor> listaPendientes) {
		this.listaPendientes = listaPendientes;
	}

	public List<ExpedientesIngresadosStor> getListaIngresados() {
		return listaIngresados;
	}

	public void setListaIngresados(List<ExpedientesIngresadosStor> listaIngresados) {
		this.listaIngresados = listaIngresados;
	}

	public List<ExpedientesConcluidosStor> getListaConcluidos() {
		return listaConcluidos;
	}

	public void setListaConcluidos(List<ExpedientesConcluidosStor> listaConcluidos) {
		this.listaConcluidos = listaConcluidos;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public DiafestivoService getServiceDiaFestivo() {
		return serviceDiaFestivo;
	}

	public void setServiceDiaFestivo(DiafestivoService serviceDiaFestivo) {
		this.serviceDiaFestivo = serviceDiaFestivo;
	}
	
}
