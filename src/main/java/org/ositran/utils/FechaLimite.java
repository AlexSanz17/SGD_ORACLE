package org.ositran.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import org.ositran.services.DiafestivoService;
import org.ositran.services.UnidadService;
import java.util.GregorianCalendar;
import org.ositran.services.UsuarioService;

import com.btg.ositran.siged.domain.DiaFestivo;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import com.opensymphony.xwork2.ActionContext;

public class FechaLimite {
	private static Logger _log=Logger.getLogger(FechaLimite.class);
	private DiafestivoService diafestivoService;
	private UnidadService unidadService;
	private UsuarioService usuarioService;
	private Map<String, Object> mapSession;

         public DiafestivoService getDiafestivoService() {
            return diafestivoService;
        }

        public void setDiafestivoService(DiafestivoService diafestivoService) {
            this.diafestivoService = diafestivoService;
        }

	
	public UnidadService getUnidadService() {
		return unidadService;
	}

	public void setUnidadService(UnidadService unidadService) {
		this.unidadService = unidadService;
	}

	public Map<String, Object> getMapSession() {
		return mapSession;
	}

	public void setMapSession(Map<String, Object> mapSession) {
		this.mapSession = mapSession;
	}


	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public long getDiasFeriados(Date fechaInicio, Date fechaFin, String idRegion){
                _log.debug("FechaLimite: getDiasFeriados()");
		List<Date> festivos = diafestivoService.getDiasFeriados(fechaInicio, fechaFin, idRegion);
		int numFeriados = festivos.size();
		Calendar fechaInicioC = new GregorianCalendar();

		if (festivos!=null)
			for(int i=0;i<festivos.size();i++){
				fechaInicioC.setTime(festivos.get(i));
				if (fechaInicioC.get(Calendar.DAY_OF_WEEK) ==Calendar.SATURDAY || fechaInicioC.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
					numFeriados = numFeriados - 1;
			}

		fechaInicioC.setTime(fechaInicio);
                Calendar fechaFinC = new GregorianCalendar();
                fechaFinC.setTime(fechaFin);
                int contadorFinSemana = 0;

                while(!fechaInicioC.after(fechaFinC)){
                        if (fechaInicioC.get(Calendar.DAY_OF_WEEK) ==Calendar.SATURDAY || fechaInicioC.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
                                contadorFinSemana ++;
                        fechaInicioC.add(Calendar.DATE, 1);
                }

        	return contadorFinSemana + numFeriados;
	}

	public Date calcularFechaLimite(int dias,int horas){
		   _log.debug("FechaLimite: calcularFechaLimite()");

		   mapSession = ActionContext.getContext().getSession();
		   Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		   
           String horaInicio = (unidadService.buscarObjPor(usuario.getIdUnidadPerfil().intValue())).getInicioEnvio();
		   String horaFin = (unidadService.buscarObjPor(usuario.getIdUnidadPerfil().intValue())).getFinEnvio();
		   
		   int horaInicioHoras=Integer.parseInt(horaInicio.substring(0,horaInicio.indexOf(':')));
		   int horaInicioMinutos=Integer.parseInt(horaInicio.substring(horaInicio.indexOf(':')+1));
		   int horaFinHoras=Integer.parseInt(horaFin.substring(0,horaFin.indexOf(':')));
		   int horaFinMinutos=Integer.parseInt(horaFin.substring(horaFin.indexOf(':')+1));
		   Calendar hoy=Calendar.getInstance();
		   Calendar temp=Calendar.getInstance();
		   int horaInicial=hoy.get(Calendar.HOUR_OF_DAY);
		   int minutosInicial=hoy.get(Calendar.MINUTE);
                   
		   if(horaInicial>horaFinHoras || (horaInicial==horaFinHoras && minutosInicial>=horaFinMinutos) || horaInicial<horaInicioHoras || (horaInicial==horaInicioHoras && minutosInicial<horaInicioMinutos)){
			   if(horaInicial>=horaFinHoras){
				   dias++;
			   }
			   horaInicial=horaInicioHoras;
			   minutosInicial=horaInicioMinutos;
			   temp.set(Calendar.HOUR_OF_DAY,horaInicioHoras);
			   temp.set(Calendar.MINUTE,horaInicioMinutos);
		   }
                   
		   int nuevaHora=horaInicial+horas;
		   int minutos=0;
		   while(nuevaHora>horaFinHoras){
			   int difHoras=horaFinHoras-horaInicial;
			   horas-=difHoras;
			   dias++;
			   horaInicial=horaInicioHoras;
			   nuevaHora=horaInicial+horas;
			   minutos+=horaInicioMinutos;
		   }
		   if(nuevaHora==horaFinHoras && minutosInicial+minutos>horaFinMinutos){
			   dias++;
			   nuevaHora=horaInicioHoras;
			   minutos+=horaInicioMinutos;
		   }
		   List<DiaFestivo> festivos=diafestivoService.getDiasFestivosPorUsuario();
		   int i=0;
		   while(i<dias){
			   temp.add(Calendar.DAY_OF_MONTH,1);
			   if(temp.get(Calendar.DAY_OF_WEEK) !=Calendar.SATURDAY && temp.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY && !esDiaFestivo(temp,festivos)){
				   i++;
			   }
		   }
		   return temp.getTime();
	}
        
	public Date calcularFechaLimite(Date fecha,int idDestinatario) {
                Calendar temp = Calendar.getInstance();
                temp.setTime( fecha );
                List<DiaFestivo> festivos=diafestivoService.getDiasFestivosPorUsuario();
                // Vamos al lunes siguiente si estamos en un fin de semana.
                // si es festivo pasa al dia siguiente.
                while(temp.get(Calendar.DAY_OF_WEEK) ==Calendar.SATURDAY || temp.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY || esDiaFestivo(temp,festivos)){
                    temp.add(Calendar.DAY_OF_MONTH, 1);
                }
                
                return temp.getTime();
	}
        
	public Date fechaFueraHorarioHabil(Date fechaFueraHorario, int idDestinatario) {
               _log.debug("FechaLimite: fechaFueraHorarioHabil()");
		   mapSession = ActionContext.getContext().getSession();
		   Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		   usuario = usuarioService.findByIdUsuario(usuario.getIdusuario());
		   String horaInicio = (unidadService.buscarObjPor(usuario.getUnidad().getIdunidad().intValue())).getInicioEnvio();
		   String horaFin = (unidadService.buscarObjPor(usuario.getUnidad().getIdunidad().intValue())).getFinEnvio();
		   _log.debug("fechaFueraHorarioHabil() Unidad "+usuario.getUnidad().getIdunidad().intValue()+"Hora Inicio Envio:"+horaInicio+"Hora Fin Envio:"+horaFin );

		//wcarrasco 08-11-2011 deprecado
		//String horaInicio = parametroService.findByTipo(Constantes.INICIO_ENVIO).get(0).getValor();
		//String horaFin = parametroService.findByTipo(Constantes.FIN_ENVIO).get(0).getValor();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
		Calendar temp = Calendar.getInstance();
		temp.setTime(fechaFueraHorario);
		List<DiaFestivo> festivos = diafestivoService.getDiasFestivosPorUsuario();

		if (horaInicio != null && horaFin != null) {
			try {
				Date actual = new Date();
				String fecha = formatFecha.format(actual) + " ";
				Date inicio = format.parse(fecha + horaInicio);
				Date fin = format.parse(fecha + horaFin);
				if (inicio.after(actual)) {
					_log.debug("antes de horario");
					String fechai = formatFecha.format(fechaFueraHorario) + " ";
					try {
						fechaFueraHorario = format.parse(fechai + horaInicio);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					_log.debug("despues de horario");
					if (temp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY	|| temp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || esDiaFestivo(temp, festivos)|| (temp.get(Calendar.DAY_OF_WEEK) ==Calendar.FRIDAY && actual.after(fin))){
					    while (temp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY	|| temp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || esDiaFestivo(temp, festivos)|| (temp.get(Calendar.DAY_OF_WEEK) ==Calendar.FRIDAY && actual.after(fin))) {
						   temp.add(Calendar.DAY_OF_MONTH, 1);
					    }
					} else {
						temp.add(Calendar.DAY_OF_MONTH, 1);
					}

					String fechaf = formatFecha.format(temp.getTime()) + " ";
					try {
						fechaFueraHorario = format.parse(fechaf + horaInicio);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			} catch (ParseException e) {
			}
		}
		_log.debug("fechaFueraHorarioHabil()-->Nuevo fechaFueraHorario"+fechaFueraHorario);
		return fechaFueraHorario;
	}
	   private boolean esDiaFestivo(Calendar dia,List<DiaFestivo> festivos){
                   _log.debug("FechaLimite: esDiaFestivo()");
		   for(DiaFestivo actual:festivos){
			   Calendar festivo=Calendar.getInstance();
			   festivo.setTime(actual.getFechanolaborable());
			   if(dia.get(Calendar.YEAR)==festivo.get(Calendar.YEAR)
					   && dia.get(Calendar.MONTH)==festivo.get(Calendar.MONTH)
					   && dia.get(Calendar.DAY_OF_MONTH)==festivo.get(Calendar.DAY_OF_MONTH))
				   return true;
		   }
		   return false;
	   }
           
	   public boolean validarHorarioPermitido(Usuario usuario){
                _log.debug("FechaLimite: validarHorarioPermitido()");
                String horaInicio = (unidadService.buscarObjPor(usuario.getUnidad().getIdunidad().intValue())).getInicioEnvio();
	        String horaFin = (unidadService.buscarObjPor(usuario.getUnidad().getIdunidad().intValue())).getFinEnvio();
	        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
                
                if(horaInicio != null && horaFin != null){
		   try{
			Date actual = new Date();
			String fecha = formatFecha.format(actual)+" ";
			Date inicio = format.parse(fecha + horaInicio);
			Date fin = format.parse(fecha + horaFin);
			Boolean fechaPermitido = true;
			Calendar temp = Calendar.getInstance();
			temp.setTime( new Date());
			List<DiaFestivo> festivos=diafestivoService.getDiasFestivosPorUsuario();
			if(temp.get(Calendar.DAY_OF_WEEK) ==Calendar.SATURDAY || temp.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY || esDiaFestivo(temp,festivos)){
			   _log.debug("Dia Sabado o Domingo o Festivo");
			   fechaPermitido = false;
			}
                        
                	return actual.after(inicio) && actual.before(fin) && fechaPermitido;
		    }catch(ParseException e){
                        e.printStackTrace();
			return false;
		    }
		}else{
		        return true;
		}
	   }

	   public boolean validarHorarioPermitidoRecepcion( Usuarioxunidadxfuncion usuarioDestinatario){
                         _log.debug("FechaLimite: validarHorarioPermitidoRecepcion()");
   		        String horaInicioRec = (unidadService.buscarObjPor(usuarioDestinatario.getIdunidad())).getInicioRecepcion();
	                String horaFinRec =    (unidadService.buscarObjPor(usuarioDestinatario.getIdunidad())).getFinRecepcion();
			
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
			SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

			if(horaInicioRec != null && horaFinRec != null){
			   try{
				Date actual = new Date();
				String fecha = formatFecha.format(actual)+" ";
				Date inicio = format.parse(fecha + horaInicioRec);
				Date fin = format.parse(fecha + horaFinRec);
				Boolean fechaPermitido = true;
				Calendar temp = Calendar.getInstance();
			        temp.setTime( new Date());
			        List<DiaFestivo> festivos=diafestivoService.getDiasFestivosPorUsuario();
			        
                                if(temp.get(Calendar.DAY_OF_WEEK) ==Calendar.SATURDAY || temp.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY || esDiaFestivo(temp,festivos)){
			          _log.debug("Sabado o Domingo o Festivo");
			          fechaPermitido = false;
			        }
                                
				return actual.after(inicio) && actual.before(fin) && fechaPermitido;
			    }catch(ParseException e){
                                e.printStackTrace();
				return false;
			    }
			}else{
				return true;
			}
		}
           
           public Date getFechaLimite(Date fecha, int plazo){
              SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy"); 
              Calendar temp = Calendar.getInstance();
	      temp.setTime(fecha);
              List<DiaFestivo> festivos = diafestivoService.getDiasFestivosPorUsuario();
              Date fechaLimite = null;
              try{
                    for(int i=0;i<plazo;i++){
                        temp.add(Calendar.DAY_OF_MONTH, 1);
                         
                        if (temp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || temp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || esDiaFestivo(temp, festivos)){
                          while (temp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY	|| temp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || esDiaFestivo(temp, festivos)) {
                            temp.add(Calendar.DAY_OF_MONTH, 1);
                          }
                        }    
                    }

                    String fechaf = formatFecha.format(temp.getTime()) + " ";
                    fechaLimite = formatFecha.parse(fechaf);
              }catch(Exception e){
                    e.printStackTrace();
              }
              
              return  fechaLimite;
           }   
           
           
           public int getPlazo(Date fechaInicio, Date fechaFin){
               List<DiaFestivo> festivos = diafestivoService.getDiasFestivosPorUsuario();
               Calendar temp = Calendar.getInstance();
               temp.setTime(fechaInicio);
               int plazo = 0;               
               try{
                    Calendar temp1 = Calendar.getInstance();
                    temp1.setTime(fechaFin);

                    while (temp1.get(Calendar.DAY_OF_YEAR)>temp.get(Calendar.DAY_OF_YEAR)){
                        plazo ++;
                        temp.add(Calendar.DAY_OF_MONTH, 1);

                        if (temp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || temp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || esDiaFestivo(temp, festivos)){
                            while (temp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || temp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || esDiaFestivo(temp, festivos)) {
                                 temp.add(Calendar.DAY_OF_MONTH, 1);
                            }
                        }
                    }   

               }catch(Exception e){
                   
               }
               return plazo;
           }

	   public Date fechaFueraHorarioRecepcionHabil(Date fechaFueraHorario, int idUnidadDestinatario) {
               _log.debug("FechaLimite: fechaFueraHorarioRecepcionHabil()");
                String horaInicioRec = (unidadService.buscarObjPor(idUnidadDestinatario)).getInicioRecepcion();
		String horaFinRec = (unidadService.buscarObjPor(idUnidadDestinatario)).getFinRecepcion();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
		Calendar temp = Calendar.getInstance();
		temp.setTime(fechaFueraHorario);
                List<DiaFestivo> festivos = diafestivoService.getDiasFestivosPorUsuario();
                // JC COMENTADA
                
                if (horaInicioRec != null && horaFinRec != null) {
			try {
                                
				Date actual = new Date();
				String fecha = formatFecha.format(actual) + " ";
				Date inicio = format.parse(fecha + horaInicioRec);
				Date fin = format.parse(fecha + horaFinRec);
                                boolean feriados =  temp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY	|| temp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || esDiaFestivo(temp, festivos);
				
                                if (inicio.after(actual) && !feriados) {
                                    	String fechai = formatFecha.format(fechaFueraHorario) + " ";
					try {
						fechaFueraHorario = format.parse(fechai + horaInicioRec);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
                                    	if (temp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY	|| temp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || esDiaFestivo(temp, festivos)|| (temp.get(Calendar.DAY_OF_WEEK) ==Calendar.FRIDAY && actual.after(fin))){
					    while (temp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY	|| temp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || esDiaFestivo(temp, festivos)|| (temp.get(Calendar.DAY_OF_WEEK) ==Calendar.FRIDAY && actual.after(fin))) {
						   temp.add(Calendar.DAY_OF_MONTH, 1);
					    }
					} else {
						temp.add(Calendar.DAY_OF_MONTH, 1);
					} 

					String fechaf = formatFecha.format(temp.getTime()) + " ";
					try {
                                            	fechaFueraHorario = format.parse(fechaf + horaInicioRec);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			} catch (ParseException e) {
			}
		} 
		
		return fechaFueraHorario;
	}

	
	    public Boolean antesHorario(Integer idDestinatario){
                    _log.debug("FechaLimite: antesHorario()");
	    	    Usuario usuario;
	            usuario = usuarioService.findByIdUsuario(idDestinatario);
		    String horaInicioRec = (unidadService.buscarObjPor(usuario.getUnidad().getIdunidad().intValue())).getInicioRecepcion();
		    String horaFinRec = (unidadService.buscarObjPor(usuario.getUnidad().getIdunidad().intValue())).getFinRecepcion();
		    Boolean antesHorario = false ;
		    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		    SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");

		    if (horaInicioRec != null && horaFinRec != null) {
			 try {
				Date actual = new Date();
				String fecha = formatFecha.format(actual) + " ";
				Date inicio = format.parse(fecha + horaInicioRec);
				if (inicio.after(actual)) {
				  _log.debug("antes de horario");
			   	  antesHorario = true;
				}
			  } catch (ParseException e) {
			  }
		    }
                    
		    return antesHorario;

		}

}
