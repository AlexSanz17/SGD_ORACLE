package org.ositran.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.ositran.utils.DateUtil;
import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.daos.AccionDAO;
import org.ositran.daos.DocumentoDAO;
import org.ositran.daos.ParametroDAO;
import org.ositran.utils.Constantes;

import com.btg.ositran.siged.domain.Alerta;
import com.btg.ositran.siged.domain.FilaSeguimiento;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.SeguimientoHoraDia;
import com.btg.ositran.siged.domain.SeguimientoHoraSemana;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;

public class SeguimientoServiceImpl implements SeguimientoService{

	private static Logger log=Logger.getLogger(SeguimientoServiceImpl.class);

	AccionDAO accionDAO;
	DocumentoDAO documentoDAO;
	ParametroDAO parametroDAO;

	public SeguimientoServiceImpl(DocumentoDAO documentoDAO,ParametroDAO parametroDAO,AccionDAO accionDAO){
		this.documentoDAO=documentoDAO;
		this.parametroDAO=parametroDAO;
		this.accionDAO=accionDAO;
	}

	   public List<Alerta> getListaAlerta(Integer idUsuario, Integer idUnidad, String orden){
		   List<Alerta> listAlerta = null;
		   SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm");

		   boolean banderaDescarga = false;
    	   List<Parametro> list = parametroDAO.findByTipo("DESCARGAR_PENDIENTES");

    	   for(int i=0;i<list.size();i++){
			   if (list.get(i).getValor().equals(idUnidad.toString())){
				  banderaDescarga = true;
				  break;
			   }
		   }

		   try{
			   //String unidadPendientes = list.get(0).getValor();
			   if (orden==null || orden.equals(""))
				   orden = " order by 1 asc";

			   listAlerta =  this.documentoDAO.findAlertas(idUsuario, idUnidad,banderaDescarga,orden);

			   if (listAlerta!=null){
				   for(int i=0;i<listAlerta.size();i++){
					   if (banderaDescarga){
						   listAlerta.get(i).setArea(listAlerta.get(i).getAreadestino());
					   }else{
						   listAlerta.get(i).setArea(listAlerta.get(i).getAreaorigen());
					   }

					   if (listAlerta.get(i).getUserremitente()!=null && listAlerta.get(i).getUserremitente().toString().equals(idUsuario.toString())){
						   listAlerta.get(i).setEnabledCheck('X');
					   }else{
						   listAlerta.get(i).setEnabledCheck(' ');
					   }

					   if (listAlerta.get(i).getFechalimiteatencion()!=null){
						   listAlerta.get(i).setDesfase(DateUtil.milisegundosADias(Math.abs(listAlerta.get(i).getFechalimiteatencion().getTime()-System.currentTimeMillis())));
						   if (listAlerta.get(i).getFechalimiteatencion().getTime()> System.currentTimeMillis()){
							   listAlerta.get(i).setDesplazo('D');
						   }else{
							   listAlerta.get(i).setDesplazo('F');
						   }
						   listAlerta.get(i).setFechalimite(f.format(listAlerta.get(i).getFechalimiteatencion()));
					   }else{
						   listAlerta.get(i).setDesplazo(' ');
						   listAlerta.get(i).setDesfase("");
					   }
				   }
			   }

			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getCause());
			}
			return listAlerta;
	   }

	public List<SeguimientoHoraDia> getListaDocumentosPendientesByDia(Timestamp fecha,Integer idusuario,String filtro1,Integer iIdProceso){
		log.debug("-> [Service] SeguimientoService - getListaDocumentosPendientesByDia():List<SeguimientoHoraDia> ");
		/*
		List<SeguimientoHoraDia> lista=new ArrayList<SeguimientoHoraDia>();
		try{
			Parametro p=this.parametroDAO.findByTipo(Constantes.INICIOHORARIO).get(0);
			int horainicio=new Integer(p.getValor());
			p=this.parametroDAO.findByTipo(Constantes.FINHORARIO).get(0);
			int horafin=new Integer(p.getValor());
			// p =
			// this.parametroDAO.findByTipo(Constantes.ACCION_DERIVAR_ID).get(0);
			Integer accionDerivarId=accionDAO.findByNombre(Constantes.ACCION_REENVIAR).getIdAccion();
			SimpleDateFormat s1=new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat s2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
			//SimpleDateFormat s3=new SimpleDateFormat("HH:mm");
			SimpleDateFormat s4=new SimpleDateFormat("HH");
			Date fechainicio=s2.parse(s1.format(fecha)+" "+(horainicio<10 ? "0"+horainicio : ""+horainicio)+":00");
			Date fechafin=s2.parse(s1.format(fecha)+" "+(horafin<10 ? "0"+horafin : ""+horafin)+":59");
			// Timestamp fechafin
			List<Integer> idacciones=new ArrayList<Integer>();
			idacciones.add(accionDAO.findByNombre(Constantes.ACCION_REENVIAR).getIdAccion());
			idacciones.add(accionDAO.findByNombre(Constantes.ACCION_RECHAZAR).getIdAccion());
			idacciones.add(accionDAO.findByNombre(Constantes.ACCION_APROBAR_QAS).getIdAccion());
			idacciones.add(accionDAO.findByNombre(Constantes.ACCION_DERIVAR2).getIdAccion());
			List<FilaSeguimiento> listainterna=this.documentoDAO.findByfechaLimiteAtencion(fechainicio,fechafin,idusuario,iIdProceso,true);
			for(int e=horainicio;e<=horafin;e++){
				SeguimientoHoraDia seg=new SeguimientoHoraDia();
				Timestamp hora=new Timestamp(s4.parse((e<10 ? "0"+e : ""+e)).getTime());
				seg.setHora(hora);
				List<Trazabilidaddocumento> documentos=new ArrayList<Trazabilidaddocumento>();
				for(FilaSeguimiento objTrazabilidad : listainterna){
					int horadoc=new Integer(s4.format(objTrazabilidad.getFechalimiteatencion())).intValue();
					if(horadoc==e){
						documentos.add(objTrazabilidad);
					}
				}
				seg.setTrazabilidadDocumentos(documentos);
				lista.add(seg);
			}
		}catch(Exception e){
			log.error(e.getCause());
		}
		return lista;*/
		return null;
	}

	public List<FilaSeguimiento> getListaDocumentosPendientesBySemana(Date fechaInicio, Date fechaFin,Integer idusuario,String filtro1,Integer iIdProceso, Boolean propios){
		log.debug("-> [Service] SeguimientoService - getListaDocumentosPendientesBySemana():List<SeguimientoHoraSemana> ");

		List<FilaSeguimiento> listainterna = new ArrayList<FilaSeguimiento>();
		try{

			listainterna=this.documentoDAO.findByfechaLimiteAtencion(fechaInicio,fechaFin,idusuario,iIdProceso, propios);

		}catch(Exception e){
			log.error(e.getCause());
		}
		return listainterna;
	}

	public List<SeguimientoHoraSemana> getListaDocumentosPendientesBySemanaFiltros(Timestamp fecha,Integer idusuario,Integer idProceso,boolean todosUsuarios){
		log.debug("-> [Service] SeguimientoService - getListaDocumentosPendientesBySemanaFiltros():List<SeguimientoHoraSemana> ");

		List<SeguimientoHoraSemana> lista=new ArrayList<SeguimientoHoraSemana>();
		try{
			Parametro p=this.parametroDAO.findByTipo(Constantes.INICIOHORARIO).get(0);
			int horainicio=new Integer(p.getValor());
			p=this.parametroDAO.findByTipo(Constantes.FINHORARIO).get(0);
			int horafin=new Integer(p.getValor());
			SimpleDateFormat s1=new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat s2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
			//SimpleDateFormat s3=new SimpleDateFormat("HH:mm");
			SimpleDateFormat s4=new SimpleDateFormat("HH");
			SimpleDateFormat s5=new SimpleDateFormat("yyyyMMddHH");
			SimpleDateFormat s6=new SimpleDateFormat("yyyyMMdd");
			Date[] fechas=this.getSemana(fecha);
			Date fechainicio=s2.parse(s1.format(fechas[0])+" "+(horainicio<10 ? "0"+horainicio : ""+horainicio)+":00");
			Date fechafin=s2.parse(s1.format(fechas[fechas.length-1])+" "+(horafin<10 ? "0"+horafin : ""+horafin)+":59");
			/*
			 * INICIO: JOSE ZENTENO
			 */
			// List listainterna = this.documentoDAO.findByfechaLimiteAtencion
			// (fechainicio, fechafin , idusuario ,
			// Constantes.ESTADO_ACTIVO,Constantes.DOCUMENTO_PRINCIPAL) ;
			List<Trazabilidaddocumento> listainterna=documentoDAO.devuelveSeguimientoUsuario(fechainicio,fechafin,idusuario,Constantes.ESTADO_ACTIVO,Constantes.DOCUMENTO_PRINCIPAL);
			/*
			 * FIN: JOSE ZENTENO
			 */
			for(int e=horainicio;e<=horafin;e++){
				SeguimientoHoraSemana seg=new SeguimientoHoraSemana();
				Timestamp hora=new Timestamp(s4.parse((e<10 ? "0"+e : ""+e)).getTime());
				seg.setHora(hora);
				for(int f=0;f<fechas.length;f++){
					List<Trazabilidaddocumento> documentos=new ArrayList<Trazabilidaddocumento>();
					String comp=s6.format(fechas[f]);
					comp+=(e<10 ? "0"+e : ""+e);
					int compint=new Integer(comp);
					for(Trazabilidaddocumento doc : listainterna){
						int horadoc=new Integer(s5.format(doc.getFechalimiteatencion())).intValue();
						if(horadoc==compint){
							documentos.add(doc);
						}
					}
					if(f==0){
						seg.setLunes(documentos);
					}else if(f==1){
						seg.setMartes(documentos);
					}else if(f==2){
						seg.setMiercoles(documentos);
					}else if(f==3){
						seg.setJueves(documentos);
					}else if(f==4){
						seg.setViernes(documentos);
					}
				}
				lista.add(seg);
			}
		}catch(Exception e){
			log.error(e.getCause());
		}
		return lista;
	}

	public Date[] getSemana(Date actual){
		log.debug("-> [Service] SeguimientoService - getSemana():Date[] ");

		//FIXME este metodo esta hasta las patas!!!
		// Date actual = new SimpleDateFormat("yyyyMMdd").parse("20090118") ;
		Date[] resultado=new Date[7];
		for(int i=0;i<resultado.length;i++){
			resultado[i]=new Date();
		}
		Calendar cal=Calendar.getInstance();
		//SimpleDateFormat normal=new SimpleDateFormat("dd/MM/yyyy");
		try{
			//log.debug(" dayy "+actual.getDay()+" fecha "+normal.format(actual));
			if(actual.getDay()<1){
				//log.debug("actual.getDay() < 1");
				//log.debug("procesing  "+actual.getDay()+" fecha "+actual);
				// int val = new Integer(new
				// SimpleDateFormat("yyyyMMdd").format(actual)).intValue();
				// val-=6;
				// actual = (new
				// SimpleDateFormat("yyyyMMdd").parse(String.valueOf(val)));
				cal.add(Calendar.DATE,-6);
				//log.debug("result  "+actual.getDay()+" fecha "+actual);
			}else if(actual.getDay()>1){
				//log.debug("actual.getDay() > 1 , actual:"+actual.getDay());
				while(actual.getDay()>1){
					//log.debug("incrementing   "+actual.getDay()+" fecha "+normal.format(actual));
					int val=new Integer(new SimpleDateFormat("yyyyMMdd").format(actual)).intValue();
					val--;
					actual=(new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(val)));
					//log.debug("nre actual    "+actual.getDay()+" fecha "+normal.format(actual));
				}
			}/*
			 * else if (actual.getDay() == 1) { int val = new Integer(new
			 * SimpleDateFormat("yyyyMMdd").format(actual)).intValue(); val-=7;
			 * actual = (new
			 * SimpleDateFormat("yyyyMMdd").parse(String.valueOf(val))); }
			 */
			// log.debug("lunes seria  "+actual.getDay()+" fecha "
			// +actual ) ;
			int t=0;
			// int r=0 ;
			while(t<resultado.length){
				// r++ ;
				resultado[t++]=actual;
				int val=new Integer(new SimpleDateFormat("yyyyMMdd").format(actual)).intValue();
				val++;
				actual=(new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(val)));
			}
			//log.debug("dias de la semana ");
			/*for(int y=0;y<resultado.length;y++){
				log.debug(" "+actual.getDay()+" fecha "+resultado[y]);
			}*/
		}catch(Exception e){
                    log.error(e.getMessage(),e);
		}
		return resultado;
	}

	public void setDocumentoDAO(DocumentoDAO documentoDAO){
		this.documentoDAO=documentoDAO;
	}

	public void setParametroDAO(ParametroDAO parametroDAO){
		this.parametroDAO=parametroDAO;
	}
}