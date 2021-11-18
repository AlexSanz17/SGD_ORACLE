/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import org.apache.log4j.Logger;
import org.ositran.daos.DepartamentoDAO;
import org.ositran.daos.DiafestivoDAO;
import org.ositran.daos.DocumentoDAO;
import org.ositran.utils.Busdiafestivo;
import org.ositran.utils.Constantes;
import org.ositran.utils.StringUtil;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Departamento;
import com.btg.ositran.siged.domain.DiaFestivo;
import com.btg.ositran.siged.domain.Documento;

public class DiafestivoServiceImpl implements DiafestivoService {

	private static Logger log = Logger.getLogger(DiafestivoServiceImpl.class);
	private DiafestivoDAO dao;
	private DepartamentoDAO dao2;
	private DiaFestivo objDf;
	private DocumentoDAO daodoc;
	private AuditoriaService srvAuditoria;
	/*************************************/
	private List<Date> intervaloFeriados;
	private static int i;
	/*************************************/
	public DiafestivoServiceImpl(DiafestivoDAO dao, DepartamentoDAO dao2, DocumentoDAO daodoc) {
		setDao(dao);
		setDao2(dao2);
		setDaodoc(daodoc);
	}

	public DiafestivoServiceImpl() {
	}

	public List<DiaFestivo> ViewAll() {
		log.debug("-> [Service] DiafestivoService - ViewAll():List<DiaFestivo> ");

		List<DiaFestivo> data = getDao().findAll();
		log.debug("list of data==" + data);
		return data;
	}

	@Transactional
	public String saveDiafestivo(String strregion, String stranio, String strmes, String fech, String Strmotivo, Integer iIdMes, String sUsuarioSesion) {
		log.debug("-> [Service] DiafestivoService - saveDiafestivo():String ");

		Date dFecha = null;
		String aviso = "no";
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String sFecha = stranio + "-" + iIdMes + "-" + fech;
			dFecha = formatoDelTexto.parse(sFecha);
			Departamento regi = new Departamento();
			DiaFestivo objdiafes = new DiaFestivo();
			List<Departamento> listregion = null;
			String sTipoAuditoria = null;
			DiaFestivo ObjnewDf = new DiaFestivo();
			DiaFestivo ObjoldDf = new DiaFestivo();

			sTipoAuditoria = Constantes.AUDITORIA_TIPO_REGISTRO;

			if (StringUtil.isEmpty(strregion)) {
				//ingresos masivos de Dias Feriados

				listregion = getDao2().findAll();
				Departamento objreg = new Departamento();


				int tamo = listregion.size();
				for (int i = 0; i < tamo; i++) {
					objreg = (Departamento) listregion.get(i);
					if (this.validarfecha(objreg.getNombre(), fech, strmes, stranio)) {
						regi = getDao2().lisNomdiafestivo(objreg.getNombre());
						DiaFestivo ObjDf = new DiaFestivo();




						ObjDf.setDia(fech);
						ObjDf.setMes(strmes);
						ObjDf.setAnio(stranio);
						ObjDf.setMotivo(Strmotivo);
						ObjDf.setFechanolaborable(dFecha);
						ObjDf.setIdregion(regi);

						ObjnewDf = ObjDf;

						srvAuditoria.aplicarAuditoria(ObjoldDf, ObjnewDf, sUsuarioSesion, Constantes.AUDITORIA_OPCION_GUARDAR, sTipoAuditoria);
						objdiafes = getDao().saveDiafestivo(ObjDf);


						this.cambiarfecha(ObjDf.getFechanolaborable(), objreg.getNombre());
						this.cambiarfechafija(ObjDf.getFechanolaborable(), objreg.getNombre());
						aviso = "si";
					} else {
						aviso = "no";
					}

				}
			} else {
				if (this.validarfecha(strregion, fech, strmes, stranio)) {

					regi = getDao2().lisNomdiafestivo(strregion);
					DiaFestivo ObjDf = new DiaFestivo();
					//log.debug("Region [" + regi.getNombreregion() + "]");
					ObjDf.setIdregion(regi);
					ObjDf.setDia(fech);
					ObjDf.setMes(strmes);
					ObjDf.setAnio(stranio);
					ObjDf.setMotivo(Strmotivo);
					ObjDf.setFechanolaborable(dFecha);
					ObjDf.setIdregion(regi);

					List<DiaFestivo> lstDiaFestivo = regi.getDiafestivoList();


					lstDiaFestivo.add(ObjDf);

					ObjnewDf = ObjDf;

					srvAuditoria.aplicarAuditoria(ObjoldDf, ObjnewDf, sUsuarioSesion, Constantes.AUDITORIA_OPCION_GUARDAR, sTipoAuditoria);

					objdiafes = getDao().saveDiafestivo(ObjDf);

					//this.cambiarfecha(ObjDf.getFechanolaborable(), strregion);
					//this.cambiarfechafija(ObjDf.getFechanolaborable(), strregion);
					aviso = "si";

				} else {
					aviso = "no";
				}
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		return aviso;
	}

	public List<Busdiafestivo> findlisdiafestivo(String Nomregion) {
		log.debug("-> [Service] DiafestivoService - findlisdiafestivo():List<Busdiafestivo> ");

		List<Busdiafestivo> data = getDao().findlisdiafestivo(Nomregion);
		return data;

	}

	@Transactional
	public void deleteDiafestivo(int iddiafestivo) {
		log.debug("-> [Service] DiafestivoService - deleteDiafestivo():void ");

		getDao().deleteDiafestivo(iddiafestivo);
	}

	@Transactional
	private void cambiarfecha(Date fechanolaborable, String Strnomregion) {
		log.debug("-> [Service] DiafestivoService - cambiarfecha():void ");

		List<Documento> lisdoc = getDaodoc().obtenciondefechaslimites(fechanolaborable);
		List<DiaFestivo> listdiaf = getDao().findxnombreregion(Strnomregion);

		int tmlisdoc = lisdoc.size();
		int tmlisdiaf = listdiaf.size();
		int aviso = 0;
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

		Date Nfecha = null;
		try {
			for (int i = 0; i < tmlisdoc; i++) {
				String fechax = String.valueOf(lisdoc.get(i).getFechaLimiteAtencion());
				Date fecha1;

				fecha1 = formatoFecha.parse(fechax);
				fecha1 = lisdoc.get(i).getFechaLimiteAtencion();
				Calendar cal = new GregorianCalendar();


				cal.setTime(fecha1);
				cal.add(cal.DATE, 1);

				fecha1 = cal.getTime();

				for (int j = 0; j < tmlisdiaf; j++) {
					Date fecha2 = listdiaf.get(j).getFechanolaborable();

					if (formatoFecha.format(fecha1).equals(formatoFecha.format(fecha2))) {

						cal.setTime(fecha1);
						cal.add(cal.DATE, +1);

						fecha1 = cal.getTime();
						log.debug(fecha1);


					}

				}
				Documento Objdoc = getDaodoc().findByIdDocumento(lisdoc.get(i).getIdDocumento());
				Objdoc.setFechaLimiteAtencion(fecha1);
				getDaodoc().saveDocumento(Objdoc);
			}
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
		}

	}

	@Transactional
	private void cambiarfechafija(Date fechanolaborable, String Strnomregion) {
		log.debug("-> [Service] DiafestivoService - cambiarfechafija():void ");

		String das = String.valueOf(fechanolaborable);
		List<Documento> lisdoc = getDaodoc().obtenciondefechasfijas(fechanolaborable);
		List<DiaFestivo> listdiaf = getDao().findxnombreregion(Strnomregion);

		int tmlisdoc = lisdoc.size();
		int tmlisdiaf = listdiaf.size();
		int aviso = 0;
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

		Date Nfecha = null;
		try {
			for (int i = 0; i < tmlisdoc; i++) {
				//String fechax = formatoFecha.format(lisdoc.get(i).getFechaLimiteAtencion());
				Date fecha1;

				//fecha1 = formatoFecha.parse(fechax);
				fecha1 = lisdoc.get(i).getFechaLimiteAtencion();
				Calendar cal = new GregorianCalendar();

				cal.setTime(fecha1);
				cal.add(cal.DATE, 1);

				fecha1 = cal.getTime();

				for (int j = 0; j < tmlisdiaf; j++) {
					Date fecha2 = listdiaf.get(j).getFechanolaborable();



					if (formatoFecha.format(fecha1).equals(formatoFecha.format(fecha2))) {

						cal.setTime(fecha1);
						cal.add(cal.DATE, +1);

						fecha1 = cal.getTime();
						log.debug(fecha1);


					}

				}
				Documento Objdoc = getDaodoc().findByIdDocumento(lisdoc.get(i).getIdDocumento());
				Objdoc.setFechaLimiteAtencion(fecha1);
				getDaodoc().saveDocumento(Objdoc);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	 public List<Date> getDiasFeriados(Date fechaInicio, Date fechaFin, String idRegion){
		 return dao.findFeriadosXRegion(fechaInicio, fechaFin, idRegion);
	 }
	
	public List<DiaFestivo> getDiasFestivosPorUsuario() {
                return dao.getDiasFestivosPorUsuario();
	}

	private boolean validarfecha(String strregion, String dia, String mes, String anio) {
		log.debug("-> [Service] DiafestivoService - validarfecha():boolean ");

		boolean valor = true;
		String mesint = "";
		String Strfecha = "";
		//SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
		if (mes.equals("Enero")) {
			mesint = "01";
		}
		if (mes.equals("Febrero")) {
			mesint = "02";
		}
		if (mes.equals("Marzo")) {
			mesint = "03";
		}
		if (mes.equals("Abril")) {
			mesint = "04";
		}
		if (mes.equals("Mayo")) {
			mesint = "05";
		}
		if (mes.equals("Junio")) {
			mesint = "06";
		}
		if (mes.equals("Julio")) {
			mesint = "07";
		}
		if (mes.equals("Agosto")) {
			mesint = "08";
		}
		if (mes.equals("Setiembre")) {
			mesint = "09";
		}
		if (mes.equals("Octubre")) {
			mesint = "10";
		}
		if (mes.equals("Noviembre")) {
			mesint = "11";
		}
		if (mes.equals("Diciembre")) {
			mesint = "12";
		}
		Strfecha = dia + "/" + mesint + "/" + anio;
		valor = this.isDate(Strfecha);
		if (valor == true) {
			//Strfecha = anio + "-" + mesint + "-" + dia;
			Date dFecha;
			try {

				dFecha = formatoDelTexto.parse(Strfecha);

				valor = this.comparar(strregion, Strfecha);
			} catch (ParseException e) {
				log.error(e.getMessage(),e);
			}
		}

		return valor;
	}

	private boolean isDate(String fechax) {
		log.debug("-> [Service] DiafestivoService - isDate():boolean ");

		try {
			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
			formatoFecha.setLenient(false);
			Date fecha = formatoFecha.parse(fechax);

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	private boolean comparar(String nomreg, String fecha) {
		log.debug("-> [Service] DiafestivoService - comparar():boolean ");

		List<Busdiafestivo> lisdiafes = null;
		try {
			lisdiafes = dao.findlisdiafxnomregion(nomreg, fecha);
			if (lisdiafes.size() > 0) {
				//log.debug("SI ES FESTIVO EL "+fecha);
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return true;
		}

	}
	/*******************************************************************************************/
	public void setIntervaloFeriados(Date fechaDesde, Date fechaHasta, String nomreg){
		log.debug("-> [Service] DiafestivoService - setIntervaloFeriados():void ");

		intervaloFeriados = dao.findIntervloXRegion(fechaDesde, fechaHasta, nomreg);
		i=0;
	}
	/********************************************************************************************************************************************************************************************************/
	public long tiempoTranscurrido(Date fechaDesde, Date fechaHasta, String nomreg){
		log.debug("-> [Service] DiafestivoService - tiempoTranscurrido():long ");

		SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
		boolean normalizado = false;
		long tiempo = 0l;
		Calendar cal1, cal2;
		cal1 = new GregorianCalendar();
		cal2 = new GregorianCalendar();
		cal1.setTime(fechaDesde);
		cal2.setTime(fechaHasta);
		i=0;
		//Se compara hasta que el tiempo sea el mismo que la fechaHasta
		while(cal1.compareTo(cal2)<0){
			if(!normalizado){
				//Vemos si se hizo la entrada fuera de la hora de trabajo.
				if((cal1.get(Calendar.HOUR_OF_DAY)<Constantes.HORA_ENTRADA)||((cal1.get(Calendar.HOUR_OF_DAY)==Constantes.HORA_ENTRADA)&&(cal1.get(Calendar.MINUTE)<Constantes.MINUTOS_ENTRADA))){
					//Se hizo antes de la hora de entrada
					cal1.set(Calendar.HOUR_OF_DAY, Constantes.HORA_ENTRADA);
					cal1.set(Calendar.MINUTE, Constantes.MINUTOS_ENTRADA);
					cal1.set(Calendar.SECOND, 0);
					//No se incrementa la fecha actual pues se hizo antes de la hora de entrada.
				}else{
					if((cal1.get(Calendar.HOUR_OF_DAY)>Constantes.HORA_SALIDA)||((cal1.get(Calendar.HOUR_OF_DAY)==Constantes.HORA_SALIDA)&&(cal1.get(Calendar.MINUTE)>Constantes.MINUTOS_SALIDA))){
						//Se hizo luego de la hora de salida
						cal1.set(Calendar.HOUR_OF_DAY, Constantes.HORA_ENTRADA);
						cal1.set(Calendar.MINUTE, Constantes.MINUTOS_ENTRADA);
						cal1.set(Calendar.SECOND, 0);
						//Se le agrega 1 porque se empieza a contar el tiempo del siguiente dia
						cal1.add(Calendar.DAY_OF_MONTH, 1);
					}else{
						//Fue hecho en uno de los extremos.
						if(cal1.get(Calendar.HOUR_OF_DAY)==Constantes.HORA_SALIDA){
							//Se incrementa 1 para que cuente desde el dia siguiente
							cal1.set(Calendar.HOUR_OF_DAY, Constantes.HORA_ENTRADA);
							cal1.set(Calendar.MINUTE, Constantes.MINUTOS_ENTRADA);
							cal1.set(Calendar.SECOND, 0);
							cal1.add(Calendar.DAY_OF_MONTH, 1);
						}
						//Sino no se hace nada, se empieza a contar en la siguiente parte del codigo
					}
				}
				//Lo dejamos puesto a la hora de entrada del siguiente dia a contar
				normalizado = true;
			}else{
				if(esFeriado(cal1.getTime())){
					//Feriado por region
					cal1.add(Calendar.DAY_OF_MONTH, 1);
				}else{
					//Dia laborable
					if(fechita.format(cal1.getTime()).equals(fechita.format(cal2.getTime()))){
						//Es el mismo dia, contar las horas
						tiempo += cal2.getTimeInMillis() - cal1.getTimeInMillis();
						//Con esto no volvera a entrar en el while
					}else{
						//Es un dia normal
						tiempo += Constantes.MILISEGUNDOS_DIA;
					}
					cal1.add(Calendar.DAY_OF_MONTH, 1);
				}
			}
		}
		return tiempo;
	}
	/********************************************************************************************************************************************************************************************************/
	private boolean esFeriado(Date fecha){
		log.debug("-> [Service] DiafestivoService - esFeriado():boolean ");

		int j;
		for(j=i; j<intervaloFeriados.size(); j++){
			if(fecha.equals(intervaloFeriados.get(j))){
				//Es un feriado
				i = j++;
				return true;
			}
		}
		//No es feriado
		return false;
	}


	/********************************************************************************************************************************************************************************************************/
	////////////////////////
	// Getters and Setters
	////////////////////////

	public DiafestivoDAO getDao() {
		return dao;
	}

	public void setDao(DiafestivoDAO dao) {
		this.dao = dao;
	}

	public DepartamentoDAO getDao2() {
		return dao2;
	}

	public void setDao2(DepartamentoDAO dao2) {
		this.dao2 = dao2;
	}

	public DiaFestivo getObjDf() {
		return objDf;
	}

	public void setObjDf(DiaFestivo objDf) {
		this.objDf = objDf;
	}

	public void setDaodoc(DocumentoDAO daodoc) {
		this.daodoc = daodoc;
	}

	public DocumentoDAO getDaodoc() {
		return daodoc;
	}

	public void setSrvAuditoria(AuditoriaService srvAuditoria) {
		this.srvAuditoria = srvAuditoria;
	}

	public AuditoriaService getSrvAuditoria() {
		return srvAuditoria;
	}


}
