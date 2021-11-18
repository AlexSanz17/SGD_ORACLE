/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;
/*DUMMY*/
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.ositran.dojo.grid.Estructura;
import org.ositran.services.DetallesProcesoService;
import org.ositran.services.FilaRDetalleProcesoService;
import org.ositran.services.FilaRResumenProcesoService;
import org.ositran.services.GrupoprocesoService;
import org.ositran.services.MensajeriaService;
import org.ositran.services.ProcesoService;
import org.ositran.utils.Constantes;
import org.ositran.utils.usuariosReporte;
import org.ositran.utils.utilReporte;

import com.btg.ositran.siged.domain.FilaRDetalleProceso;
import com.btg.ositran.siged.domain.FilaRResumenProceso;
import com.btg.ositran.siged.domain.Grupoproceso;
import com.btg.ositran.siged.domain.Mensajeria;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import org.ositran.utils.detallesProcesoReporte;

public class ReporteSigedAction {
	/******Referencias a Services necesarios***************************************************************************/
	private FilaRResumenProcesoService 			filaRResumenProcesoService ;
	private FilaRDetalleProcesoService 			filaRDetalleProcesoService ;
	private DetallesProcesoService				detallesProcesoService;
	private ProcesoService 						procesoService;
	private GrupoprocesoService					grupoprocesoService;
	private MensajeriaService					mensajeriaService;
	/******Referencias a Stores necesarios*****************************************************************************/
	private static List<detallesProcesoReporte> detallesProceso 		= new ArrayList<detallesProcesoReporte>();
	private static List<detallesProcesoReporte> detallesResponsables 	= new ArrayList<detallesProcesoReporte>();
	private List<String> 						expedientesRevisados 	= new ArrayList<String>();
	private List<String> 						procesosRevisados 		= new ArrayList<String>();
	private static Log 							log 					= LogFactory.getLog(ReporteSigedAction.class);
	private List<FilaRDetalleProceso>  			resultadoD;
	private List<FilaRResumenProceso>			resultadoR;

	private List<Mensajeria>					listaMensajeria= new ArrayList<Mensajeria>();
	/******Referencias a variables necesarias**************************************************************************/
	private String procesoClasificacion ;
	private String procesoElegido;
	private String estado ;
	private String clasificar ;
	private String fechaDesde ;
	private String fechaHasta ;
	private String horaDesde ;
	private String horaHasta ;
	private List<List<FilaRResumenProceso>> resultado;
	private List<List<FilaRDetalleProceso>> resultadoDetalle;
	private List<List<usuariosReporte>> tiemposXProceso;
	private static boolean busquedaFin = false;

	/************* Respuesta data **************************************************************************************/
	private List<Estructura> dataResponse;
	private String html;
	/***************PROCESOS********************************************************************************************/

	List<Proceso> procesos;
	List<Grupoproceso> gruposproceso;
	List<utilReporte> storeUsuarios;
	List<String> usuariosDistintos;
	List<Integer> expedientesUsuario;
	List<usuariosReporte> totalTiempoUsuarios;
	/*******************************************************************************************************************/
	private static List<utilReporte> responsables = new ArrayList<utilReporte>();
	/***************Metodos de Navegacion*****************/

	private Map<String,Object> mapSession;

	public String inicio (){
		return "inicio";
	}
	public String mensajeria(){
		return "mensajeria";
	}
	public String showFiltros (){
		return "filtros";
	}
	public String showFiltrosMensajeria (){
		return "filtrosMensajeria";
	}
	public String showMenu(){
		return "menu";
	}
   public String execute() throws Exception {
		      // try{
		      return Action.SUCCESS;
		      /*
		       * }catch(Exception e){ log.error(e.getMessage(),e); return
		       * Action.ERROR; }
		       */
		   }

   public String buscarReporte1(){
	   	HttpServletRequest request=ServletActionContext.getRequest();
		try {
			this.procesoClasificacion = new String(request.getParameter("procesoClasificacion").getBytes("ISO-8859-1"),"UTF-8");
			this.procesoElegido = new String(request.getParameter("procesoElegido").getBytes("ISO-8859-1"),"UTF-8");
			this.fechaDesde = new String(request.getParameter("fechaDesde").getBytes("ISO-8859-1"),"UTF-8");
			this.fechaHasta = new String(request.getParameter("fechaHasta").getBytes("ISO-8859-1"),"UTF-8");
			this.clasificar = new String(request.getParameter("clasificar").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(),e);
		}
		/*log.debug("*******************************************");
		log.debug("procesoClasificacion:"+procesoClasificacion);
		log.debug("procesoElegido:"+procesoElegido);
		log.debug("estado:"+estado);
		log.debug("fechaDesde:"+fechaDesde);
		log.debug("fechaHasta:"+fechaHasta);
		log.debug("clasificar:"+clasificar);
		log.debug("*******************************************");*/
		if(checkErrorFechas()){
			if(clasificar==null||clasificar.equals("")){
				clasificar=Constantes.REPORTE_RESUMEN;
			}
			if(clasificar!=null&&clasificar.equalsIgnoreCase(Constantes.REPORTE_RESUMEN) ) {

				resultadoR = filaRResumenProcesoService.generarReporteResumen(procesoClasificacion, procesoElegido, estado, fechaDesde, fechaHasta);
				clasificarTablas();

			}else if (clasificar.equalsIgnoreCase(Constantes.REPORTE_DETALLE) ) {
				resultadoD = filaRDetalleProcesoService.generarReporteDetalle(procesoClasificacion, procesoElegido, estado, fechaDesde, fechaHasta);
				setearTiempos();
				clasificarTablasDetalle();
				formatoUsuarios();
				sumarTiempos();
			}
			busquedaFin=true;
			return Action.SUCCESS;
		}else{
			return "errorFechas";
		}
   }

   public String buscarReporteMensajeria(){
	   	HttpServletRequest request=ServletActionContext.getRequest();
		try {
			this.fechaDesde = new String(request.getParameter("fechaDesde").getBytes("ISO-8859-1"),"UTF-8");
			this.fechaHasta = new String(request.getParameter("fechaHasta").getBytes("ISO-8859-1"),"UTF-8");
			this.horaDesde = new String(request.getParameter("horaDesde").getBytes("ISO-8859-1"),"UTF-8");
			this.horaHasta = new String(request.getParameter("horaHasta").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(),e);
		}
		if(checkErrorFechas()){
			mapSession=ActionContext.getContext().getSession();
			Usuario objUsuario=(Usuario) mapSession.get(Constantes.SESSION_USUARIO);
			listaMensajeria = mensajeriaService.filtrarCriterios(objUsuario, fechaDesde, fechaHasta, horaDesde, horaHasta);

			return Action.SUCCESS;
		}else{
			return "errorFechas";
		}

   }

   	/*************Metodo de Busqueda Antiguo*************************/
    /*
	@SMDMethod
	public  ObjetoJSON  buscar (String clasificar ,String procesoClasificacion, String procesoElegido,String estado,String fechaDesde, String fechaHasta){
		detallesProceso.clear();
		detallesResponsables.clear();
		busquedaFin = false;
		ObjetoJSON objJSON = new ObjetoJSON();
		this.procesoClasificacion = procesoClasificacion;
		this.procesoElegido = procesoElegido;
		this.estado = estado;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.clasificar =  clasificar ;
		if(clasificar==null||clasificar.equals("")){
			clasificar=Constantes.REPORTE_RESUMEN;
		}
		if(clasificar!=null&&clasificar.equalsIgnoreCase(Constantes.REPORTE_RESUMEN) ) {
			resultadoR = filaRResumenProcesoService.generarReporteResumen(procesoClasificacion, procesoElegido, estado, fechaDesde, fechaHasta);
			/**ACA SE PASA DEL RESULTADO AL OBJETO JSON**/
	/*		objJSON.setStructure(filaRResumenProcesoService.getStructura(resultadoR));
			objJSON.setItems(resultadoR) ;
		}else if (clasificar.equalsIgnoreCase(Constantes.REPORTE_DETALLE) ) {
			resultadoD = filaRDetalleProcesoService.generarReporteDetalle(procesoClasificacion, procesoElegido, estado, fechaDesde, fechaHasta);
			setearTiempos();
			//formatoDetalles();
			//formatoDetallesUsuario();
			objJSON.setStructure( filaRDetalleProcesoService.getStructura(resultadoD));
			formatResultado();
			objJSON.setItems(resultadoD);
		}
		busquedaFin=true;
		return objJSON ;
	}*/

	/*****Metodos para devolver las listas de responsables e informacion por proceso**************************/
	/*
	@SMDMethod
	public  ObjetoJSON  informacionAdicional (){
		ObjetoJSON objJSON =new ObjetoJSON();
		/**DEBE ESPERAR A QUE TERMINE EL BUSCAR**/
    /* 	do{}while (!busquedaFin);
        /**DEBE ESPERAR A QUE TERMINE EL BUSCAR**/
	/*		objJSON.setStructure(filaRDetalleProcesoService.getStructuraAlt1(procesoClasificacion));
			objJSON.setItems(detallesProceso) ;
		return objJSON ;
	}*/
	/*
	@SMDMethod
	public  ObjetoJSON  informacionResponsables (){
		ObjetoJSON objJSON =new ObjetoJSON();
		/**DEBE ESPERAR A QUE TERMINE EL BUSCAR**/
	/*	do{}while (!busquedaFin);
        /**DEBE ESPERAR A QUE TERMINE EL BUSCAR**/
	/*		objJSON.setStructure(filaRDetalleProcesoService.getStructuraAlt1(procesoClasificacion));
			objJSON.setItems(detallesResponsables) ;
		return objJSON ;
	}*/

	/*****Metodo para darle formato al resultado en la vista Detalle****************************************/
	/*
	public void formatResultado(){
			if(resultadoD.size()>0){
				List<FilaRDetalleProceso> resultadoFormat = new ArrayList <FilaRDetalleProceso> ();
				String exActual, exModelo;
				exModelo=resultadoD.get(0).getExpediente();
				resultadoFormat.add(resultadoD.get(0));
				for (int i=1; i<resultadoD.size();i++){
					exActual=resultadoD.get(i).getExpediente();
					if(exActual.equals(exModelo)){
						resultadoD.get(i).setGrupoproceso("");
						resultadoD.get(i).setProceso("");
						resultadoD.get(i).setAsunto("");
						resultadoD.get(i).setPropietario("");
						resultadoD.get(i).setCliente("");
						resultadoD.get(i).setFechaexpediente(null);
						resultadoD.get(i).setEstado("");
						resultadoD.get(i).setExpediente("");
					}else{
						exModelo=exActual;
					}
				}
			}
	}*/

	@SuppressWarnings("unused")
	public void setearTiempos(){
		Boolean last = true;
		if(resultadoD.size()>0){
			String exModelo, exActual;
			Date anterior, actual;
			exModelo = resultadoD.get(0).getExpediente();
			anterior = resultadoD.get(0).getFecharecibido();
			for (int i=1; i<resultadoD.size();i++){
				//log.debug(i+"-");
				if(resultadoD.get(i)!=null){
				try{
				exActual = resultadoD.get(i).getExpediente();
				actual = resultadoD.get(i).getFecharecibido()==null?new Date():resultadoD.get(i).getFecharecibido();
				if(exModelo.equals(exActual)){
					//Sigue en la trazabilidad del mismo expediente
					resultadoD.get(i-1).setTiempoatencion(actual.getTime()-anterior.getTime());
					resultadoD.get(i-1).setResponsable(resultadoD.get(i).getRemitente());
					anterior = actual;
					last = false;
				}else{
					//Es un nuevo expediente
					/*Funcion para poner el tiempo ahora*/
					/*****Corregir esto para que no use el tiempo del sistema***********/
					resultadoD.get(i-1).setTiempoatencion(System.currentTimeMillis()-resultadoD.get(i-1).getFecharecibido().getTime());
					/*******************************************************************/
					//resultadoD.get(i-1).setTiempoatencion(-1l);
					exModelo = exActual;
					resultadoD.get(i-1).setResponsable(resultadoD.get(i-1).getDestinatario());
					anterior=actual;
					last = true;
				}
				}catch(NullPointerException e){
					log.debug("Error NPE");
				}
			}
			}
			resultadoD.get(resultadoD.size()-1).setTiempoatencion(System.currentTimeMillis()-resultadoD.get(resultadoD.size()-1).getFecharecibido().getTime());
			resultadoD.get(resultadoD.size()-1).setResponsable(resultadoD.get(resultadoD.size()-1).getDestinatario());
		}
	}

	private void formatoUsuarios(){
		boolean primero = true;
		int indice, indTemp;
		if(resultadoD.size()>0){
			for(FilaRDetalleProceso fila: resultadoD){
				if(primero){
					//Primer elemento
					storeUsuarios = new ArrayList<utilReporte>();
					usuariosDistintos = new ArrayList<String>();
					storeUsuarios.add(new utilReporte(fila.getExpediente()));
					usuariosDistintos.add(new String(fila.getResponsable()));
					storeUsuarios.get(0).addUsuarios(fila.getResponsable(), fila.getTiempo());
					indice = 0;
					primero=false;
				}else{
					//Siguientes elementos
					if(!usuarioGuardado(fila.getResponsable())){
						usuariosDistintos.add(new String(fila.getResponsable()));
					}
					indTemp = buscarExpedienteStoreUsuarios(fila.getExpediente());
					if(indTemp>=0){
						//Ya existe el expediente
						indice = indTemp;
						storeUsuarios.get(indice).addUsuarios(fila.getResponsable(), fila.getTiempo());
					}else{
						//El expediente aun no existe
						storeUsuarios.add(new utilReporte(fila.getExpediente()));
						indice = storeUsuarios.size()-1;
						storeUsuarios.get(indice).addUsuarios(fila.getResponsable(), fila.getTiempo());
					}
				}
			}
		}
	}
	//Expedientes Revisados
	private void sumarTiempos(){
		tiemposXProceso = new ArrayList<List<usuariosReporte>>();
		totalTiempoUsuarios = new ArrayList<usuariosReporte>();
		for (String proceso: procesosRevisados){
			int i=0;
			for(String nombre : usuariosDistintos){
				totalTiempoUsuarios.add(new usuariosReporte());
				totalTiempoUsuarios.get(i).setUsuario(nombre);
				totalTiempoUsuarios.get(i).setTiempo(0l);
				totalTiempoUsuarios.get(i).setVeces(0);
				expedientesRevisados.clear();
				for(FilaRDetalleProceso frd: resultadoD){
					if (procesoClasificacion.equals("grupoproceso")){
						if(proceso.equals(frd.getGrupoproceso())){
							//Es parte de la lista actual
							if(nombre.equals(frd.getResponsable())){
								//Es el mismo usuario
								if(!existeExpediente(frd.getExpediente())){
									//No existe el expediente
									expedientesRevisados.add(frd.getExpediente());
									totalTiempoUsuarios.get(i).incExpedientes();
								}
								totalTiempoUsuarios.get(i).incTiempo(frd.getTiempo());
								totalTiempoUsuarios.get(i).incVeces();
							}
						}
					}else{
						//if(proceso.equals(frd.getProceso())){
							if(proceso.equals(frd.getProceso())){
								//Es parte de la lista actual
								if(nombre.equals(frd.getResponsable())){
									//Es el mismo usuario
									if(!existeExpediente(frd.getExpediente())){
										//No existe el expediente
										expedientesRevisados.add(frd.getExpediente());
										totalTiempoUsuarios.get(i).incExpedientes();
									}
									totalTiempoUsuarios.get(i).incTiempo(frd.getTiempo());
									totalTiempoUsuarios.get(i).incVeces();
								}
							}
						//}
					}
				}
				i++;
			}
			//agrega una lista por cada proceso que revisa
			tiemposXProceso.add(new ArrayList<usuariosReporte>(totalTiempoUsuarios));
			totalTiempoUsuarios.clear();
		}

	}

	private boolean usuarioGuardado(String nombre){
		for (String usuario : usuariosDistintos){
			if(usuario.equals(nombre)){
				return true;
			}
		}
		return false;
	}

	private int buscarExpedienteStoreUsuarios (String expediente){
		if(storeUsuarios.size()>0){
			int i=0;
			for(utilReporte util : storeUsuarios){
				if(util.getExpediente().equals(expediente)){
					return i;
				}
				i++;
			}
		}
		return -1;
	}

	/*****Metodos para llenar las listas de responsables e informacion por proceso**************************/

	private void clasificarTablas(){
		if(resultadoR.size()>0){
			resultado = new ArrayList<List<FilaRResumenProceso>>();
			String procesoModelo, procesoActual;
			int indice, indTemp;
			if(procesoClasificacion.equals("grupoproceso")){
				procesoModelo = resultadoR.get(0).getGrupoproceso();
				indice = 0;
				boolean primero = true;
				resultado.add(new ArrayList<FilaRResumenProceso>());
				resultado.get(0).add(resultadoR.get(0));
				for (FilaRResumenProceso fila: resultadoR){
					if(!primero){
						procesoActual = fila.getGrupoproceso();
						if(procesoActual.equals(procesoModelo)){
							//Es parte del mismo proceso
							resultado.get(indice).add(fila);
						}else{
							//Buscar proceso
							indTemp = buscarIndiceGrupoProceso ("resumen", procesoActual);
							if(indTemp>=0){
								//Ya esta el proceso
								indice = indTemp;
								resultado.get(indice).add(fila);
							}else{
								resultado.add(new ArrayList<FilaRResumenProceso>());
								indice = resultado.size()-1;
								resultado.get(indice).add(fila);
							}
							procesoModelo = procesoActual;
						}
					}else{
						primero = false;
					}
				}
			}else{
				procesoModelo = resultadoR.get(0).getProceso();
				indice = 0;
				boolean primero = true;
				resultado.add(new ArrayList<FilaRResumenProceso>());
				resultado.get(0).add(resultadoR.get(0));
				for (FilaRResumenProceso fila: resultadoR){
					if(!primero){
						procesoActual = fila.getProceso();
						if(procesoActual.equals(procesoModelo)){
							//Es parte del mismo proceso
							resultado.get(indice).add(fila);
						}else{
							//Buscar proceso
							indTemp = buscarIndiceProceso ("resumen", procesoActual);
							if(indTemp>=0){
								//Ya esta el proceso
								indice = indTemp;
								resultado.get(indice).add(fila);
							}else{
								resultado.add(new ArrayList<FilaRResumenProceso>());
								indice = resultado.size()-1;
								resultado.get(indice).add(fila);
							}
							procesoModelo = procesoActual;
						}
					}else{
						primero = false;
					}
				}
			}
		}
	}

	private void clasificarTablasDetalle(){
		if(resultadoD.size()>0){
			resultadoDetalle = new ArrayList<List<FilaRDetalleProceso>>();
			String procesoModelo, procesoActual, expediente;
			int indice, indTemp;
			if(procesoClasificacion.equals("grupoproceso")){
				procesoModelo = resultadoD.get(0).getGrupoproceso();
				//procesosRevisados.add(procesoModelo);
				addDif(procesosRevisados, procesoModelo);
				indice = 0;
				boolean primero = true;
				resultadoDetalle.add(new ArrayList<FilaRDetalleProceso>());
				resultadoDetalle.get(0).add(resultadoD.get(0));
				expediente = resultadoD.get(0).getExpediente();
				for (FilaRDetalleProceso fila: resultadoD){
					if(!primero){
						procesoActual = fila.getGrupoproceso();
						if(procesoActual.equals(procesoModelo)){
							//Es parte del mismo proceso
							if(!fila.getExpediente().equals(expediente)){
								resultadoDetalle.get(indice).add(fila);
								expediente = fila.getExpediente();
							}

						}else{
							//Buscar proceso
							indTemp = buscarIndiceGrupoProceso ("detalle", procesoActual);
							if(indTemp>=0){
								//Ya esta el proceso
								indice = indTemp;
								if(!fila.getExpediente().equals(expediente)){
									resultadoDetalle.get(indice).add(fila);
									expediente = fila.getExpediente();
								}
							}else{
								resultadoDetalle.add(new ArrayList<FilaRDetalleProceso>());
								indice = resultadoDetalle.size()-1;
								if(!fila.getExpediente().equals(expediente)){
									resultadoDetalle.get(indice).add(fila);
									expediente = fila.getExpediente();
								}
							}
							procesoModelo = procesoActual;
							//procesosRevisados.add(procesoModelo);
							addDif(procesosRevisados, procesoModelo);
						}
					}else{
						primero = false;
					}
				}
			}else{
				procesoModelo = resultadoD.get(0).getProceso();
				//procesosRevisados.add(procesoModelo);
				addDif(procesosRevisados, procesoModelo);
				indice = 0;
				boolean primero = true;
				resultadoDetalle.add(new ArrayList<FilaRDetalleProceso>());
				resultadoDetalle.get(0).add(resultadoD.get(0));
				expediente = resultadoD.get(0).getExpediente();
				for (FilaRDetalleProceso fila: resultadoD){
					if(!primero){
						procesoActual = fila.getProceso();
						if(procesoActual.equals(procesoModelo)){
							//Es parte del mismo proceso
							if(!fila.getExpediente().equals(expediente)){
								resultadoDetalle.get(indice).add(fila);
								expediente = fila.getExpediente();
							}
						}else{
							//Buscar proceso
							indTemp = buscarIndiceProceso ("detalle", procesoActual);
							if(indTemp>=0){
								//Ya esta el proceso
								indice = indTemp;
								if(!fila.getExpediente().equals(expediente)){
									resultadoDetalle.get(indice).add(fila);
									expediente = fila.getExpediente();
								}
							}else{
								resultadoDetalle.add(new ArrayList<FilaRDetalleProceso>());
								indice = resultadoDetalle.size()-1;
								if(!fila.getExpediente().equals(expediente)){
									resultadoDetalle.get(indice).add(fila);
									expediente = fila.getExpediente();
								}
							}
							procesoModelo = procesoActual;
							//procesosRevisados.add(procesoModelo);
							addDif(procesosRevisados, procesoModelo);
						}
					}else{
						primero = false;
					}
				}
			}
		}
	}

	private void addDif(List<String> obj, String add){
		for(Object o : obj){
			if(o.equals(add)){
				return;
			}
		}
		obj.add(add);
	}

	private int buscarIndiceGrupoProceso(String detalle, String grupoproceso){
		int j=0;
		if(detalle.equals("resumen")){
			for(List<FilaRResumenProceso> lista: resultado){
				if(lista.get(0).getGrupoproceso().equals(grupoproceso)){
					return j;
				}
				j++;
			}
		}else{
			for(List<FilaRDetalleProceso> lista: resultadoDetalle){
				if(lista.get(0).getGrupoproceso().equals(grupoproceso)){
					return j;
				}
				j++;
			}
		}
		return -1;
	}
	private int buscarIndiceProceso(String detalle, String proceso){
		int j=0;
		if(detalle.equals("resumen")){
			for(List<FilaRResumenProceso> lista: resultado){
				if(lista.get(0).getProceso().equals(proceso)){
					return j;
				}
				j++;
			}
		}else{
			for(List<FilaRDetalleProceso> lista: resultadoDetalle){
				if(lista.get(0).getProceso().equals(proceso)){
					return j;
				}
				j++;
			}
		}
		return -1;
	}

	/******Metodos para buscar en las listas de procesos y responsables***************************************/

	/*public int buscarProceso(String proceso){
		try{
			for (int i=0; i<detallesProceso.size();i++){
				if(proceso.equals(detallesProceso.get(i).getNombre())){
					return i;
				}
			}
			return -1;
		}catch (NullPointerException e){
			return -1;
		}
	}*/

	public boolean existeExpediente(String expediente){
		if(expedientesRevisados.isEmpty()){
			return false;
		}
		try{
			for (int i=0; i<expedientesRevisados.size();i++){
				if(expediente.equals(expedientesRevisados.get(i))){
					return true;
				}
			}
			return false;
		}catch (NullPointerException e){
			return false;
		}
	}

	/*public int buscarUsuario(String nombre){
		try{
			for (int i=0; i<detallesResponsables.size();i++){
				if(nombre.equals(detallesResponsables.get(i).getNombre())){
					return i;
				}
			}
			return -1;
		}catch (NullPointerException e){
			return -1;
		}
	}*/

	//Rutina para exportar la data a excel.
	public String exportarExcel()
	{
		//Con el servlet response se pasa el parametro "html" declarado en la pagina hacia el String de este action
		HttpServletResponse response=ServletActionContext.getResponse();

		//log.debug(html);

        String content;
		try {
			//Pasa al content el contenido del String pasandolo a bytes usando el ISO y luego lo convierte a UTF-8
			content = new String(html.getBytes("UTF-8"),"UTF-8");
			//Estos modifican la cabecera del documento html.
			//Aca se pasa el formato a excel.
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Pragma", "no-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setHeader("Content-Type", "application/vnd.ms-excel");
	        response.addHeader("Content-Disposition", "inline; filename=\"ReporteExportacion.xls\"");
	        //Esta es una salida en la que se envia todo el HTML listo para ser guardado como excel.
	        ServletOutputStream out;
	        //Usa los mismos datos que el response que hemos seteado arriba
			out = response.getOutputStream();
			//guarda el contenido del html que quiere almacenar
			out.write(content.getBytes());
			//Envia
	        out.flush();
		}
		catch (IOException e1) {
			log.error(e1.getMessage(),e1);
		}

		return null;
	}
	/************************************************************/
	private boolean checkErrorFechas(){
		/**
		 * Este error es causado cuando hay un conflicto entre el nombre de variables
		 * que son usadas en el reporte. Para que desaparezca debes revisar el nombre
		 * de las variables de los javascript y JSP.
		 */
		if((fechaDesde.equals("")&&fechaHasta.equals(""))||(fechaDesde==null&&fechaHasta==null)){
			return true;
		}
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
	/*****Getters & Setters************************************/

	public FilaRDetalleProcesoService getFilaRDetalleProcesoService() {
		return filaRDetalleProcesoService;
	}
	public void setFilaRDetalleProcesoService(
			FilaRDetalleProcesoService filaRDetalleProcesoService) {
		this.filaRDetalleProcesoService = filaRDetalleProcesoService;
	}
	public FilaRResumenProcesoService getFilaRResumenProcesoService() {
		return filaRResumenProcesoService;
	}
	public void setFilaRResumenProcesoService(
			FilaRResumenProcesoService filaRResumenProcesoService) {
		this.filaRResumenProcesoService = filaRResumenProcesoService;
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

	public String getProcesoClasificacion() {
		return procesoClasificacion;
	}
	public void setProcesoClasificacion(String procesoClasificacion) {
		this.procesoClasificacion = procesoClasificacion;
	}
	public String getProcesoElegido() {
		return procesoElegido;
	}
	public void setProcesoElegido(String procesoElegido) {
		this.procesoElegido = procesoElegido;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getClasificar() {
		return clasificar;
	}
	public void setClasificar(String clasificar) {
		this.clasificar = clasificar;
	}
	public DetallesProcesoService getDetallesProcesoService() {
		return detallesProcesoService;
	}
	public void setDetallesProcesoService(
			DetallesProcesoService detallesProcesoService) {
		this.detallesProcesoService = detallesProcesoService;
	}
	public List<Estructura> getDataResponse() {
		return dataResponse;
	}
	public void setDataResponse(List<Estructura> dataResponse) {
		this.dataResponse = dataResponse;
	}
	public List<FilaRDetalleProceso> getResultadoD() {
		return resultadoD;
	}
	public void setResultadoD(List<FilaRDetalleProceso> resultadoD) {
		this.resultadoD = resultadoD;
	}
	public List<FilaRResumenProceso> getResultadoR() {
		return resultadoR;
	}
	public void setResultadoR(List<FilaRResumenProceso> resultadoR) {
		this.resultadoR = resultadoR;
	}
	public ProcesoService getProcesoService() {
		return procesoService;
	}
	public void setProcesoService(ProcesoService procesoService) {
		this.procesoService = procesoService;
	}
	public List<Grupoproceso> getGruposproceso() {
		return gruposproceso;
	}
	public void setGruposproceso(List<Grupoproceso> gruposproceso) {
		this.gruposproceso = gruposproceso;
	}
	public List<List<FilaRResumenProceso>> getResultado() {
		return resultado;
	}
	public void setResultado(List<List<FilaRResumenProceso>> resultado) {
		this.resultado = resultado;
	}

	public GrupoprocesoService getGrupoprocesoService() {
		return grupoprocesoService;
	}
	public void setGrupoprocesoService(GrupoprocesoService grupoprocesoService) {
		this.grupoprocesoService = grupoprocesoService;
	}
	public List<List<FilaRDetalleProceso>> getResultadoDetalle() {
		return resultadoDetalle;
	}
	public void setResultadoDetalle(List<List<FilaRDetalleProceso>> resultadoDetalle) {
		this.resultadoDetalle = resultadoDetalle;
	}
	public List<utilReporte> getStoreUsuarios() {
		return storeUsuarios;
	}
	public void setStoreUsuarios(List<utilReporte> storeUsuarios) {
		this.storeUsuarios = storeUsuarios;
	}
	public List<String> getUsuariosDistintos() {
		return usuariosDistintos;
	}
	public void setUsuariosDistintos(List<String> usuariosDistintos) {
		this.usuariosDistintos = usuariosDistintos;
	}
	public List<usuariosReporte> getTotalTiempoUsuarios() {
		return totalTiempoUsuarios;
	}
	public void setTotalTiempoUsuarios(List<usuariosReporte> totalTiempoUsuarios) {
		this.totalTiempoUsuarios = totalTiempoUsuarios;
	}
	public List<List<usuariosReporte>> getTiemposXProceso() {
		return tiemposXProceso;
	}
	public void setTiemposXProceso(List<List<usuariosReporte>> tiemposXProceso) {
		this.tiemposXProceso = tiemposXProceso;
	}
	public List<String> getProcesosRevisados() {
		return procesosRevisados;
	}
	public void setProcesosRevisados(List<String> procesosRevisados) {
		this.procesosRevisados = procesosRevisados;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getHoraDesde() {
		return horaDesde;
	}
	public void setHoraDesde(String horaDesde) {
		this.horaDesde = horaDesde;
	}
	public String getHoraHasta() {
		return horaHasta;
	}
	public void setHoraHasta(String horaHasta) {
		this.horaHasta = horaHasta;
	}
	public MensajeriaService getMensajeriaService() {
		return mensajeriaService;
	}
	public void setMensajeriaService(MensajeriaService mensajeriaService) {
		this.mensajeriaService = mensajeriaService;
	}
	public static List<detallesProcesoReporte> getDetallesProceso() {
		return detallesProceso;
	}
	public static void setDetallesProceso(
			List<detallesProcesoReporte> detallesProceso) {
		ReporteSigedAction.detallesProceso = detallesProceso;
	}
	public static List<detallesProcesoReporte> getDetallesResponsables() {
		return detallesResponsables;
	}
	public static void setDetallesResponsables(
			List<detallesProcesoReporte> detallesResponsables) {
		ReporteSigedAction.detallesResponsables = detallesResponsables;
	}
	public List<String> getExpedientesRevisados() {
		return expedientesRevisados;
	}
	public void setExpedientesRevisados(List<String> expedientesRevisados) {
		this.expedientesRevisados = expedientesRevisados;
	}
	public List<Mensajeria> getListaMensajeria() {
		return listaMensajeria;
	}
	public void setListaMensajeria(List<Mensajeria> listaMensajeria) {
		this.listaMensajeria = listaMensajeria;
	}
	public static boolean isBusquedaFin() {
		return busquedaFin;
	}
	public static void setBusquedaFin(boolean busquedaFin) {
		ReporteSigedAction.busquedaFin = busquedaFin;
	}
	public List<Proceso> getProcesos() {
		return procesos;
	}
	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}
	public List<Integer> getExpedientesUsuario() {
		return expedientesUsuario;
	}
	public void setExpedientesUsuario(List<Integer> expedientesUsuario) {
		this.expedientesUsuario = expedientesUsuario;
	}
	public static List<utilReporte> getResponsables() {
		return responsables;
	}
	public static void setResponsables(List<utilReporte> responsables) {
		ReporteSigedAction.responsables = responsables;
	}

}
