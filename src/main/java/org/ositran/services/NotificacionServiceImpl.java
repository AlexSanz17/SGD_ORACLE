package org.ositran.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.ositran.daos.DocumentoEnviadoDAO;
import org.ositran.daos.NotificacionDAO;
import org.ositran.daos.TrazabilidaddocumentoDAO;
import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.grid.Item;
import org.ositran.dojo.tree.Nodo;
import org.ositran.utils.Constantes;
import org.ositran.utils.FechaLimite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Accion;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Documentoenviado;
import com.btg.ositran.siged.domain.Etapa;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.Trazabilidadapoyo;
import com.btg.ositran.siged.domain.Trazabilidadcopia;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Usuario;

public class NotificacionServiceImpl implements NotificacionService{

	private static Logger log=LoggerFactory.getLogger(NotificacionServiceImpl.class);
	private NotificacionDAO dao;
        private UnidadService unidadService;
        private TrazabilidaddocumentoService srvTrazDoc;
	private TrazabilidaddocumentoDAO trazabilidaddocumentoDAO ;
	private DocumentoEnviadoDAO documentoEnviadoDao;
        private DocumentoService documentoService;
   	private TrazabilidadcopiaService trazabilidadcopiaService;
        private AccionService accionService;
        private ManejoDeEmailService mailService;
        private FechaLimite fechaLimite;
        private TrazabilidadapoyoService trazabilidadapoyoService;
        private EstadoService estadoService;
        private GridcolumnaxusuarioService gridColumnaXUsuarioService;
	private UsuarioService usuarioService;

        public UsuarioService getUsuarioService() {
            return usuarioService;
        }

        public void setUsuarioService(UsuarioService usuarioService) {
            this.usuarioService = usuarioService;
        }
        // ////////////////////////////////
	// Constructors //
	// ////////////////////////////////
	public NotificacionServiceImpl(NotificacionDAO dao){
		this.dao=dao;
	}

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	public List<Notificacion> buscarLstPor(Integer iIdUsuario){
		log.debug("-> [Service] NotificacionService - buscarLstPor():List<Notificacion> ");

		return dao.obtenerNotificacionesxUsuario(iIdUsuario);
	}

	public Integer obtenerCantidadNotificacionesxUsuario(Integer iIdUsuario) {
		log.debug("-> [Service] NotificacionService - obtenerCantidadNotificacionesxUsuario():Integer ");

		return dao.obtenerCantidadNotificacionesxUsuario(iIdUsuario);
	}

//   public List<Notificacion> buscarLstPor(Integer iIdUsuario, Integer iTipoNotificacion) {
//      return dao.buscarLstPor(iIdUsuario, iTipoNotificacion);
//   }

   public List<Notificacion> buscarLstPor(Usuario usuario, Integer iTipoNotificacion, Character cLeido) {
	   log.debug("-> [Service] NotificacionService - buscarLstPor():List<Notificacion> ");

	   return dao.buscarLstPor(usuario, iTipoNotificacion, cLeido);
   }

   public List<Notificacion> buscarLastNotificacionesbyDocumento(Integer idDocumento, Integer tipoNotificacion) {
	   log.debug("-> [Service] NotificacionService - buscarLastNotificacionesbyDocumento():List<Notificacion> ");

	   return dao.buscarLastNotificacionesbyDocumento(idDocumento, tipoNotificacion);
   }

	public Notificacion buscarObjPorID(Integer iIdNotificacion){
		log.debug("-> [Service] NotificacionService - buscarObjPorID():Notificacion ");

		return dao.buscarObjPorID(iIdNotificacion);
	}

   public Notificacion findObjByIdDocumento(Integer iIdDocumento) {
	   log.debug("-> [Service] NotificacionService - findObjByIdDocumento():Notificacion ");

	   return dao.findObjByIdDocumento(iIdDocumento);
   }

	@Transactional
	public Notificacion saveNotificacion(Notificacion objNotificacion){
		log.debug("-> [Service] NotificacionService - saveNotificacion():Notificacion ");

		return dao.saveNotificacion(objNotificacion);
	}

	/**
	 * Metodo que se encarga del envio de notificaciones por bandeja y correo
	 *
	 * @author Germán Enríquez xxxxxxxxxxxxxxx
	 *
	 */
	@Transactional
	@Override
	public boolean enviarNotificacion(Usuario remitente,Usuario receptor,Documento docOriginal,int tipo, String nombrePC,Boolean horarioPermitido,Documento  documentocopia, String ta){
		log.debug("-> [Service] NotificacionService - enviarNotificacion():boolean ");
                
                Notificacion notificacion=new Notificacion();
		notificacion.setIdusuario(receptor);
		notificacion.setIddocumento(docOriginal);
		Date fechaFueraHorario = new Date();
                Integer id = null;
                
                if(horarioPermitido == false){ //JC-FECHA
	           /* fechaFueraHorario = fechaLimite.fechaFueraHorarioHabil(fechaFueraHorario,receptor.getIdusuario().intValue());
		    long n = fechaFueraHorario.getTime()-(new Date()).getTime();   
                    long valor = n/60000;
                    fechaFueraHorario = new Date(fechaFueraHorario.getTime() + (8010-valor));*/
                    notificacion.setFechanotificacion(fechaFueraHorario);
                }else{
                   notificacion.setFechanotificacion(new Date());
                }
                
                notificacion.setTiponotificacion(tipo);
		notificacion.setEstado('A');
                notificacion.setUnidadPropietario(receptor.getIdUnidadPerfil());
                notificacion.setCargoPropietario(receptor.getIdFuncionPerfil());
                notificacion.setUsuarioCreacion(remitente.getIdusuario());
		String asunto="";
		String contenido="";
		Accion accionCopia = accionService.findByNombre(Constantes.ACCION_COPIAR);
		boolean aTraza = false;
                
                id = docOriginal.getDocumentoreferencia()==null?docOriginal.getIdDocumento():docOriginal.getDocumentoreferencia();
                
                Trazabilidaddocumento trazabilidad=trazabilidaddocumentoDAO.findByMaxNroRegistro(id,null, null);
	        asunto=docOriginal.getAsunto();
                
                if(tipo==Constantes.TIPO_NOTIFICACION_INFOADICIONAL){
			//asunto="Aprobacion de Inicio de Procedimiento";
			contenido=receptor.getNombres()+" "+receptor.getApellidos()+":<br /><br/>";
			contenido+="Se le notifica que ";
			contenido+=" <strong>"+remitente.getNombres()+" "+remitente.getApellidos();
			contenido+="</strong>, solicita su aprobacion para el inicio del procedimiento: ";
			contenido+="<strong>Requerimiento de Informacion</strong>";
		}else if(tipo==Constantes.TIPO_NOTIFICACION_DERIVACIONMULTIPLECONCOPIA){
			contenido+= ta!=null? ta: "";
			aTraza = true;
		}
                else if(tipo==Constantes.TIPO_NOTIFICACION_INSPECCIONCAMPO){
			//asunto="Aprobacion de Inicio de Procedimiento";
			contenido=receptor.getNombres()+" "+receptor.getApellidos()+":<br /><br/>";
			contenido+="Se le notifica que ";
			contenido+=" <strong>"+remitente.getNombres()+" "+remitente.getApellidos();
			contenido+="</strong>, solicita su aprobacion para el inicio del procedimiento: ";
			contenido+="<strong>Inspeccion de Campo</strong>";
		}else if(tipo==Constantes.TIPO_NOTIFICACION_AUDIENCIACONCILIACION){
			//asunto="Aprobacion de Inicio de Procedimiento";
			contenido=receptor.getNombres()+" "+receptor.getApellidos()+":<br /><br/>";
			contenido+="Se le notifica que ";
			contenido+=" <strong>"+remitente.getNombres()+" "+remitente.getApellidos();
			contenido+="</strong>, solicita su aprobacion para el inicio del procedimiento: ";
			contenido+="<strong>Audiencia de Conciliacion</strong>";
		}else if(tipo>=Constantes.TIPO_NOTIFICACION_RECHAZOTECNICO&&tipo<=Constantes.TIPO_NOTIFICACION_RECHAZO_CAMBIOSALA){
			log.debug("Rechazo tecnico");
			switch(tipo){
			case 1004: {
				asunto=Constantes.ASUNTO_RECHAZO_TECNICO.substring(4);
				break;
			}
			case 1005: {
				asunto=Constantes.ASUNTO_RECHAZO_LEGAL.substring(4);
				break;
			}
			case 1006: {
				asunto=Constantes.ASUNTO_RECHAZO_VB.substring(4);
				break;
			}
			case 1007: {
				asunto=Constantes.ASUNTO_RECHAZO_VB.substring(4);
				break;
			}
			case 1008: {
				asunto=Constantes.ASUNTO_RECHAZO_CAMBIO_SALA.substring(4);
				break;
			}
			default: {
				return false;
			}
			}
			contenido=receptor.getNombres()+" "+receptor.getApellidos()+":<br /><br/>";
			contenido+="Se le notifica que el usuario ";
			contenido+=" <strong>"+remitente.getNombres()+" "+remitente.getApellidos();
			contenido+="</strong>, realiz&oacute; un: <strong>"+asunto+"</strong> ";
			contenido+="al "+docOriginal.getTipoDocumento().getNombre()+" Nro. "+docOriginal.getNumeroDocumento()+" .";
		}else if(tipo==Constantes.TIPO_NOTIFICACION_DUENO_EXPEDIENTE){
			//asunto="Registro del Documento Nro. "+documento.getNumeroDocumento()+" en Mesa de Partes";
			contenido=receptor.getNombres()+" "+receptor.getApellidos()+":<br /><br/>";
			contenido+="Se le notifica que ";
			contenido+=" <strong>"+remitente.getNombres()+" "+remitente.getApellidos();
			contenido+="</strong>, ha registrado un nuevo documento con referencia al  ";
			contenido+="Expediente Nro :  "+docOriginal.getExpediente().getNroexpediente()+"  - "+docOriginal.getExpediente().getAsunto()+" .";
		}else if(tipo==Constantes.TIPO_NOTIFICACION_NUEVO_DOCUMENTO){
			//asunto="Registro de un Nuevo Documento por "+remitente.getNombres()+" "+remitente.getApellidos();
			contenido=receptor.getNombres()+" "+receptor.getApellidos()+":<br /><br/>";
			contenido+="Se le notifica que ";
			contenido+=" <strong>"+remitente.getNombres()+" "+remitente.getApellidos();
			contenido+="</strong>, ha registrado un nuevo documento con referencia al  ";
			contenido+="Expediente Nro :  "+docOriginal.getExpediente().getNroexpediente()+"  - "+docOriginal.getExpediente().getAsunto()+" .";
		}else if(tipo==Constantes.TIPO_NOTIFICACION_DERIVACION || tipo==Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA){
			//asunto="Derivaci&oacute;n del Documento Nro. "+documento.getNumeroDocumento();
			contenido=docOriginal.getAcciones() != null ? docOriginal.getAcciones() : "" +"<br />";
			contenido+=trazabilidad!=null&&StringUtils.isNotBlank(trazabilidad.getContenido())?trazabilidad.getContenido():" ";
			aTraza = true;
		}
		else if(tipo==Constantes.TIPO_NOTIFICACION_APROBACION_QAS){
			//asunto="Se aprobo el Documento Nro. "+documento.getNumeroDocumento()+" en Control de Calidad";
			contenido="Se ha aprobado el documento "+docOriginal.getTipoDocumento().getNombre()+" - "+docOriginal.getNumeroDocumento()+" a "+docOriginal.getPropietario().getNombres()+" "+docOriginal.getPropietario().getApellidos();
		}
		else if(tipo==Constantes.TIPO_NOTIFICACION_RECHAZO){
			//asunto="Rechazo del Documento Nro. "+documento.getNumeroDocumento();
			contenido=trazabilidad!=null&&StringUtils.isNotBlank(trazabilidad.getContenido())?trazabilidad.getContenido():" ";
		} else if (tipo == Constantes.TIPO_NOTIFICACION_AMARILLA) {
                    //asunto = "Primer Aviso Fecha Limite: Documento [" + documento.getTipoDocumento().getNombre() + "-" + documento.getNumeroDocumento() + "]";
                    contenido = "Le recordamos que el documento " + docOriginal.getTipoDocumento().getNombre() + "-" + docOriginal.getNumeroDocumento() + " esta proximo a pasar de la fecha limite [" + docOriginal.getFechaLimiteAtencion().toString() + "] para ser atendido";
                 } else if (tipo == Constantes.TIPO_NOTIFICACION_ROJA) {
                    //asunto = "Segundo Aviso Fecha Limite: Documento [" + documento.getTipoDocumento().getNombre() + "-" + documento.getNumeroDocumento() + "]";
                    contenido = "Le recordamos que el documento numero " + docOriginal.getTipoDocumento().getNombre() + "-" + docOriginal.getNumeroDocumento() + " esta proximo a pasar de la fecha limite [" + docOriginal.getFechaLimiteAtencion().toString() + "] para ser atendido";
                 }  else if(tipo== Constantes.TIPO_NOTIFICACION_REFERENCIA_DOCUMENTO) {
                    //asunto = "Documento referenciado Nro. " +documento.getNumeroDocumento();
                    //contenido = "Le recordamos que el documento numero " + documento.getTipoDocumento().getNombre() + "-" + documento.getNumeroDocumento() + " esta proximo a pasar de la fecha limite [" + documento.getFechaLimiteAtencion().toString() + "] para ser atendido";
                    contenido="Se le informa que el documento "+docOriginal.getTipoDocumento().getNombre()+" - "+docOriginal.getNumeroDocumento()+" se ha referenciado al Expediente Nro :  "+docOriginal.getExpediente().getNroexpediente()+" - "+docOriginal.getExpediente().getAsunto()+ " por el usuario "+remitente.getNombres()+" "+remitente.getApellidos();
                 } else if(Constantes.TIPO_NOTIFICACION_DOCUMENTO_ARCHIVADO.equals(tipo)){
                     //asunto = "Expediente Terminado: Expediente [" + documento.getExpediente().getNroexpediente() + "]";
                     contenido+="Se le notifica que el usuario <strong> ";
                     contenido+=remitente.getNombres()+" "+remitente.getApellidos();
                     contenido+="</strong>, ha archivado el Expediente Nro : <strong>";
                     contenido+= docOriginal.getExpediente().getNroexpediente()+"  - "+docOriginal.getExpediente().getAsunto()+"<strong> .<br /><br />";
                     contenido+="Observacion: "+docOriginal.getObservacion();
                 } else if(Constantes.TIPO_NOTIFICACION_DOCUMENTO_OEFA.equals(tipo)){
                     //asunto = "Expediente Enviado a OEFA: Expediente [" + documento.getExpediente().getNroexpediente() + "]";
                     contenido+="Se le notifica que el usuario <strong> ";
                     contenido+=remitente.getNombres()+" "+remitente.getApellidos();
                     contenido+="</strong>, ha enviado a OEFA el Expediente Nro : <strong>";
                     contenido+= docOriginal.getExpediente().getNroexpediente()+"  - "+docOriginal.getExpediente().getAsunto()+"<strong> .<br /><br />";
                     contenido+="Observacion: "+docOriginal.getObservacion();
                 } else if(Constantes.TIPO_NOTIFICACION_FIN_APOYO.equals(tipo)){
                     //asunto = "Apoyo Concluido: Expediente [" + documento.getExpediente().getNroexpediente() + "]";
                     contenido+="Se le notifica que el usuario <strong> ";
                     contenido+=remitente.getNombres()+" "+remitente.getApellidos();
                     contenido+="</strong>, ha concluido con el trabajo sobre el Expediente Nro : <strong>";
                     contenido+= docOriginal.getExpediente().getNroexpediente()+"  - "+docOriginal.getExpediente().getAsunto()+"<strong> .<br /><br />";
                     contenido+=docOriginal.getObservacion();
                     //notificacion.setIddocumento(documentoService.findByIdDocumento(documento.getDocumentoreferencia()));
                 }else{
			// TODO crear el asunto y contenido para los demas tipos de notificacion
			notificacion=null;
			throw new UnsupportedOperationException();
		}
                
                notificacion.setAsunto(asunto);
		notificacion.setContenido(contenido);
		notificacion.setLeido(Constantes.ESTADO_NO_LEIDO);
		notificacion = dao.saveNotificacion(notificacion);
		
                if(aTraza){
                   trazabilidadcopiaService.guardarTrazabilidadcopia(trazabilidad, remitente, receptor, docOriginal, accionCopia, null, Constantes.TIPO_ORIGEN_TRAZADOCUMENTO, notificacion, nombrePC,horarioPermitido);
                }
                
                if(documentocopia!=null){
                        Accion accionApoyo = accionService.findByNombre(""+Constantes.ACCION_RESPONDER);
			Trazabilidadapoyo tapoyo = new Trazabilidadapoyo();
			tapoyo.setAccion(accionApoyo);
			tapoyo.setDestinatario(receptor);
                        tapoyo.setUnidaddestinatario(receptor.getIdUnidadPerfil());
                        tapoyo.setCargodestinatario(receptor.getIdFuncionPerfil());
			tapoyo.setRemitente(new Usuario(remitente.getIdUsuarioPerfil()));
                        tapoyo.setUnidadremitente(remitente.getIdUnidadPerfil());
                        tapoyo.setCargoremitente(remitente.getIdFuncionPerfil());
                        tapoyo.setDocumento(documentocopia.getIdDocumento());
                        fechaFueraHorario = new Date();
			if(horarioPermitido == false){
			     fechaFueraHorario = fechaLimite.fechaFueraHorarioHabil(fechaFueraHorario,receptor.getIdusuario());
		             tapoyo.setFechacreacion(fechaFueraHorario);
			}else{
			     tapoyo.setFechacreacion(new Date());
			}
                        
                        tapoyo.setTrazabilidad(trazabilidad);
                	tapoyo.setEstado(estadoService.findByCodigo(""+Constantes.ESTADO_CODIGO_RESPONDIDO));
                	tapoyo.setTexto(contenido);
                   	tapoyo.setAsunto(asunto);
                   	tapoyo.setNombrePC(nombrePC);
			tapoyo.setFechalimiteatencion(tapoyo.getFechacreacion());
                        tapoyo.setUsuariocreacion(remitente.getIdusuario());
                        trazabilidadapoyoService.guardar(tapoyo);
                   
		}

		return true;
	}


	@Override
	public void enviarNotificacion(Usuario remitente,Usuario receptor,Documento documento,Integer tipo,	Expediente expedienteNuevo) {
		log.debug("-> [Service] NotificacionService - enviarNotificacion():void ");
              
                Notificacion notificacion=new Notificacion();
		notificacion.setIdusuario(receptor);
                notificacion.setIddocumento(documento);
		notificacion.setFechanotificacion(new Date());
		notificacion.setTiponotificacion(tipo);
		notificacion.setEstado('A');
		String asunto = "Documento referenciado Nro. " +documento.getNumeroDocumento();
		String contenido="Se le informa que el documento "+documento.getTipoDocumento().getNombre()+" - "+documento.getNumeroDocumento()+" se ha referenciado al Expediente Nro :  "+documento.getExpediente().getNroexpediente()+" - "+documento.getExpediente().getAsunto()+ " por el usuario "+remitente.getNombres()+" "+remitente.getApellidos();
		notificacion.setAsunto(asunto);
		notificacion.setContenido(contenido);
                notificacion.setLeido(Constantes.ESTADO_NO_LEIDO);
                dao.saveNotificacion(notificacion);
	}
        
   @Transactional
   public Notificacion clonarNotificacion(Usuario remitente,Usuario receptor,Documento documento,int tipo,String asunto,String contenido, Integer idNotificacionPadre, String nombrePC, Boolean horarioPermitido) {
	   log.debug("-> [Service] NotificacionService - clonarNotificacion():Notificacion ");
           Accion accionCopia = accionService.findByNombre(Constantes.ACCION_COPIAR);
	   Notificacion notificacion=new Notificacion();
	   notificacion.setIdusuario(receptor);
           notificacion.setUnidadPropietario(receptor.getIdUnidadPerfil());
           notificacion.setCargoPropietario(receptor.getIdFuncionPerfil());
           notificacion.setUsuarioCreacion(remitente.getIdusuario());
	   notificacion.setIddocumento(documento);
	   Date fechaFueraHorario = new Date();
           
	   if(horarioPermitido == false){
	   	// fechaFueraHorario = fechaLimite.fechaFueraHorarioHabil(fechaFueraHorario,receptor.getIdusuario().intValue());
		 notificacion.setFechanotificacion(fechaFueraHorario);
	   }else{
	    	 notificacion.setFechanotificacion(new Date());
	   }

           notificacion.setTiponotificacion(tipo);
	   notificacion.setEstado('A');
	   notificacion.setAsunto(asunto);
	   notificacion.setContenido(contenido);
	   notificacion.setLeido(Constantes.ESTADO_NO_LEIDO);
	   notificacion = dao.saveNotificacion(notificacion);
	   int iEvento = Constantes.CONFIGNOTIFMAIL_ENUMERACION_COPIA;
	   
           Documento d = new Documento();
           d.setPropietario(new Usuario(receptor.getIdusuario()));
           d.setUnidadpropietario(receptor.getUnidad().getIdunidad());
           d.setExpediente(documento.getExpediente());
           d.setTipoDocumento(documento.getTipoDocumento());
           d.setNumeroDocumento(documento.getNumeroDocumento());
           d.setIdDocumento(documento.getIdDocumento());
           
           if(tipo==Constantes.TIPO_NOTIFICACION_DERIVACION || tipo==Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA || tipo==Constantes.TIPO_NOTIFICACION_DERIVACIONMULTIPLECONCOPIA){
                       try{
				if(idNotificacionPadre != null){
                   			Trazabilidadcopia trazacopia = trazabilidadcopiaService.buscarPorNotificacion(idNotificacionPadre);
					trazabilidadcopiaService.guardarTrazabilidadcopia(trazacopia.getIdorigen(), remitente, receptor, documento, accionCopia, null, Constantes.TIPO_ORIGEN_TRAZACOPIA, notificacion, nombrePC,horarioPermitido);
				}else{
                   			Trazabilidaddocumento origen = null;
                                        if (documento.getDocumentoreferencia()==null){
                                           origen = trazabilidaddocumentoDAO.encontrarUltimaTrazabilidadPorDocumento(documento.getIdDocumento());
                                         }else{
                                           origen = trazabilidaddocumentoDAO.encontrarUltimaTrazabilidadPorDocumento(documento.getDocumentoreferencia()); 
                                        }
                                        trazabilidadcopiaService.guardarTrazabilidadcopia(origen, remitente, receptor, documento, accionCopia, null, Constantes.TIPO_ORIGEN_TRAZADOCUMENTO, notificacion, nombrePC,horarioPermitido);
	                      }
			}catch(Exception e){
				e.printStackTrace();
				log.debug("No se ha podido guardar la traza de la copia",e.fillInStackTrace());
			}
		}

	   if (tipo==Constantes.TIPO_NOTIFICACION_FIN_APOYO){
                   try{
        			if(idNotificacionPadre != null){
					Trazabilidadcopia trazacopia = null;
					try{
                                                trazacopia = trazabilidadcopiaService.buscarPorNotificacion(idNotificacionPadre);
						trazabilidadcopiaService.guardarTrazabilidadcopia(trazacopia.getIdorigen(), remitente, receptor, documento, accionCopia, null, Constantes.TIPO_ORIGEN_TRAZACOPIA, notificacion, nombrePC,horarioPermitido);
					}catch(Exception e){
        					Trazabilidaddocumento origen = null;
						if (documento.getDocumentoreferencia()==null){
        					   origen = trazabilidaddocumentoDAO.encontrarUltimaTrazabilidadPorDocumento(documento.getIdDocumento());
                                                }else{
                                                    origen = trazabilidaddocumentoDAO.encontrarUltimaTrazabilidadPorDocumento(documento.getDocumentoreferencia());
                                                }
                                                 trazabilidadcopiaService.guardarTrazabilidadcopia(origen, remitente, receptor, documento, accionCopia, null, Constantes.TIPO_ORIGEN_TRAZADOCUMENTO, notificacion, nombrePC,horarioPermitido);
					}

				}else{
                                       Trazabilidaddocumento origen = null;
                                        if (documento.getDocumentoreferencia()==null){
                                              origen = trazabilidaddocumentoDAO.encontrarUltimaTrazabilidadPorDocumento(documento.getIdDocumento());
                                        }else{
                                              origen = trazabilidaddocumentoDAO.encontrarUltimaTrazabilidadPorDocumento(documento.getDocumentoreferencia());
                                        }

        				trazabilidadcopiaService.guardarTrazabilidadcopia(origen, remitente, receptor, documento, accionCopia, null, Constantes.TIPO_ORIGEN_TRAZADOCUMENTO, notificacion, nombrePC,horarioPermitido);
				}
			}catch(Exception e){
				e.printStackTrace();
				log.debug("No se ha podido guardar la traza de la copia",e.fillInStackTrace());
			}
	   }

            informarViaNotifAndMail(remitente, d, iEvento, Constantes.TIPO_NOTIFICACION_NUMERACION_DOCUMENTOCONCOPIA, nombrePC, contenido, null);
	  
		//wcarrasco 27-10-2011 Cuando se realiza una notificacion desde Informativos recibidos se registra en documentoenvio
            Documentoenviado documentoenviado = new Documentoenviado();
	    documentoenviado.setIdTrazabilidadEnvio(notificacion.getIdnotificacion());
            documentoenviado.setUsuario(remitente);
	    documentoenviado.setEstado("" + Constantes.ESTADO_ACTIVO);
	    documentoenviado.setTipoEnvio(""+ Constantes.TIPO_ENVIO_NOTIFICAR);
            documentoenviado.setUnidadpropietario(remitente.getIdUnidadPerfil());
            documentoenviado.setCargopropietario(remitente.getIdFuncionPerfil());
            documentoenviado.setUsuariocreacion(remitente.getIdusuario());
	    documentoEnviadoDao.saveDocumento(documentoenviado);
	    return notificacion;
   }

	@Transactional
	public void enviarNotificacionSAS(Usuario remitente,Usuario destinatario,Documento documento,String observacion,Etapa etapaRemiten){
		log.debug("-> [Service] NotificacionService - enviarNotificacionSAS():void ");
                
		Notificacion notificacion=new Notificacion();
		notificacion.setIdusuario(destinatario);
		notificacion.setIddocumento(documento);
		notificacion.setFechanotificacion(new Date());
		notificacion.setEstado('A');
		String asunto="";
		String contenido="";
		int tipo=0;
		if(documento.getAccion().getNombre().equals(Constantes.ACCION_APROBAR)||documento.getAccion().getNombre().equals(Constantes.ACCION_REGISTRAR)){
			asunto=(documento.getAccion().getNombre().equals(Constantes.ACCION_REGISTRAR)==true ? "Registro" : "Aprobacion")+" del "+documento.getExpediente().getNroexpediente()+" por "+"Usuario: "+remitente.getNombres(); //+" - "+documento.getExpediente().getProceso().getNombre();
			tipo=(documento.getAccion().getNombre().equals(Constantes.ACCION_REGISTRAR)==true ? Constantes.TIPO_NOTIFICACION_Resgistrado : Constantes.TIPO_NOTIFICACION_Aprobado);
			contenido=destinatario.getNombres()+" :<br/><br/>";
			contenido+="Se le notifica que el usuario ";
			contenido+=" <strong>"+remitente.getNombres();
			contenido+="</strong>, Aprobo el "+documento.getTipoDocumento().getNombre()+":"+documento.getNumeroDocumento()+" que pertenece al "+documento.getExpediente().getNroexpediente();
			//contenido+=" y tambien al proceso:<strong>"+documento.getExpediente().getProceso().getNombre()+"</strong>";
			contenido+=" ,fue aprobada en la etapa:<strong>"+etapaRemiten.getDescripcion()+"</strong> y le comunicamos que encuentra pendiente su revision";
		}else{
			asunto="Rechazo del "+documento.getExpediente().getNroexpediente()+" por Usuario: "+remitente.getNombres();//+" - "+documento.getExpediente().getProceso().getNombre();
			tipo=Constantes.TIPO_NOTIFICACION_Desaprobado;
			contenido=destinatario.getNombres()+" "+destinatario.getApellidos()+":<br/><br/>";
			contenido+="Se le notifica que el usuario ";
			contenido+=" <strong>"+remitente.getNombres();
			contenido+="</strong>, Rechazo el "+documento.getTipoDocumento().getNombre()+":"+documento.getNumeroDocumento()+" que pertenece al "+documento.getExpediente().getNroexpediente();
			//contenido+=",que pertenece al proceso:<strong>"+documento.getExpediente().getProceso().getNombre()+"</strong>";
			contenido+=",fue rechazada en la etapa:<strong>"+etapaRemiten.getDescripcion()+"</strong>.<br/><br/>";
			contenido+="Observacion:"+observacion;
		}
		notificacion.setTiponotificacion(tipo);
		notificacion.setLeido(Constantes.ESTADO_NO_LEIDO);
		notificacion.setAsunto(asunto);
		notificacion.setContenido(contenido);
		dao.saveNotificacion(notificacion);
		
	}

	@Override
	public int getNroNotificacionesNL(Usuario usuario){
		log.debug("-> [Service] NotificacionService - getNroNotificacionesNL():int ");

		return dao.nroNotificacionesNoLeidas(usuario);
	}

	@Transactional
	public void updateTipoNotificacion(Integer idDocumento, Integer idUsuario, Integer tipoNotificacion) {
		log.debug("-> [Service] NotificacionService - updateTipoNotificacion():void ");

		if(idUsuario==null||tipoNotificacion==null || idDocumento==null){
			log.error("Argumentos nulos. No se hara ninguna actualizacion");
			return;
		}
		List<Notificacion> listNotificaciones = this.findLastNotificacionbyUsuario(idDocumento, idUsuario);
		if(listNotificaciones!=null && listNotificaciones.size()>0){
			Notificacion objNotificacion = listNotificaciones.get(0);
			objNotificacion.setTiponotificacion(tipoNotificacion);
			objNotificacion=this.saveNotificacion(objNotificacion);
			log.debug("Tipo de la notificacion con ID ["+objNotificacion.getIdnotificacion()+"] actualizada a ["+objNotificacion.getTiponotificacion()+"]");
		}
	}

	@Transactional
	public List<Notificacion> findLastNotificacionbyUsuario(Integer idDocumento,Integer idUsuario) {
		log.debug("-> [Service] NotificacionService - findLastNotificacionbyUsuario():List<Notificacion> ");

		return dao.findLastNotificacionbyUsuario(idDocumento,idUsuario);
	}

	@Transactional
	public void updateEstadoNotificacion(Integer iIdNotificacion,Character cEstado){
		log.debug("-> [Service] NotificacionService - updateEstadoNotificacion():void ");

		if(iIdNotificacion==null||cEstado==null){
			log.error("Argumentos nulos. No se hara ninguna actualizacion");
			return;
		}
		Notificacion objNotificacion=this.buscarObjPorID(iIdNotificacion);
		objNotificacion.setEstado(cEstado);
		objNotificacion=this.saveNotificacion(objNotificacion);
		log.debug("Estado de la notificacion con ID ["+objNotificacion.getIdnotificacion()+"] actualizada a ["+objNotificacion.getEstado()+"]");
	}
	@Transactional
	public void eliminarDocumentosEnviados(String[] ids, String nombrePC){
		log.debug("-> [Service] NotificacionService - eliminarDocumentosEnviados():void ");

		for(int i =0 ; i<ids.length ; i++ ){
			log.debug(" id"+i+":"+ids[i]) ;
			Notificacion doc = getDao().buscarObjPorID(new Integer(ids[i]));
			doc.setEstado(Constantes.ESTADO_INACTIVO) ;
            doc.setFechaEliminaDocumento(new Date());
            doc.setNombrePCEliminaDocumento(nombrePC);
			this.saveNotificacion(doc);

		}

	}
	public Integer getNotificacionesNoLeidas(Integer iIdUsuario) {
		log.debug("-> [Service] NotificacionService - getNotificacionesNoLeidas():Integer ");

		return dao.getNotificacionesNoLeidas(iIdUsuario);
	}

	@Transactional
	public Notificacion updateLeido(Integer iIdNotificacion) {
		log.debug("-> [Service] NotificacionService - updateLeido():Notificacion ");
                Notificacion objNotificacionToUpdate = this.buscarObjPorID(iIdNotificacion);
                objNotificacionToUpdate.setLeido('S');
                objNotificacionToUpdate = this.saveNotificacion(objNotificacionToUpdate);
                return objNotificacionToUpdate;
	}

   @Override
   @Transactional
   public Set informarViaNotifAndMail(Usuario remitente, Documento documento, int iTipoEvento, int iTipoNotificacion, String nombrePC, String contenido, String accion) {
	   log.debug("-> [Service] NotificacionService - informarViaNotifAndMail():Set ");

           if (documento == null) {
		   log.error("Se recibio documento NULL, no se enviara nada");
		   return null;
	   }

	   try {
                   int iBegin = 1;
		   //int iConfig_Notif_Mail = documento.getExpediente().getProceso().getConfig_notif_mail().intValue();
		   int iEnd = 0;
		   int iMask = 1;
		   List<Usuario> lstUsersToBeInformed = new ArrayList<Usuario>();
		   Set usersInformed = new HashSet();
		   Set usersMailed = new HashSet();
		   String evento = null;
		   Usuario destinatario = null;

                   switch (iTipoEvento) {
		   case Constantes.CONFIGNOTIFMAIL_INGRESO_DOCUMENTO_QAS:
		   case Constantes.CONFIGNOTIFMAIL_INGRESO_DOCUMENTO_USERFINAL:
		   case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_REENVIAR: 
                   case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_PARTICULAR_MTC: 
                   case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_PARTICULAR_CR: 
                   case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_PARTICULAR_PCM: 
		   case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_PORAPROBAR:
		   case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_APROBAR:
                           Usuario usu_1 = usuarioService.findByIdUsuario(documento.getPropietario().getIdusuario());
                           destinatario = new Usuario(usu_1.getIdusuario());
                           destinatario.setApellidos(usu_1.getApellidos());
                           destinatario.setNombres(usu_1.getNombres());
                           destinatario.setCorreo(usu_1.getCorreo());
                           destinatario.setEnviocorreo(usu_1.getEnviocorreo());
                           destinatario.setIdUnidadPerfil(documento.getUnidadpropietario());
                           destinatario.setUsuario(usu_1.getUsuario());
                           
                           if (usersMailed.add(destinatario)) {
				   log.info("Enviando correo al usuario {}", destinatario.getUsuario());
                                   mailService.ChaskiMail(iTipoEvento, remitente, destinatario, documento, contenido, accion);
			   } else {
				   log.debug("Ya se envio un correo al usuario {} previamente, no se enviara nuevo correo", destinatario.getUsuario());
			   }

			   break;
		   case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_RECHAZAR:
                           Usuario usu_ = usuarioService.findByIdUsuario(documento.getPropietario().getIdusuario());
                           destinatario = new Usuario(usu_.getIdusuario());
                           destinatario.setApellidos(usu_.getApellidos());
                           destinatario.setNombres(usu_.getNombres());
                           destinatario.setCorreo(usu_.getCorreo());
                           destinatario.setEnviocorreo(usu_.getEnviocorreo());
                           destinatario.setIdUnidadPerfil(documento.getUnidadpropietario());
                           
                           if (usersMailed.add(destinatario)) {
				   log.info("Enviando correo al usuario {}", destinatario.getUsuario());
                                   mailService.ChaskiMail(iTipoEvento, remitente, destinatario, documento, contenido, accion);
			   } else {
				   log.debug("Ya se envio un correo al usuario {} previamente, no se enviara nuevo correo", destinatario.getUsuario());
			   }

			   break;
		   case Constantes.CONFIGNOTIFMAIL_ENUMERACION_COPIA:
                           Usuario usu = usuarioService.findByIdUsuario(documento.getPropietario().getIdusuario());
                           destinatario = new Usuario(usu.getIdusuario());
                           destinatario.setApellidos(usu.getApellidos());
                           destinatario.setNombres(usu.getNombres());
                           destinatario.setCorreo(usu.getCorreo());
                           destinatario.setEnviocorreo(usu.getEnviocorreo());
                           destinatario.setIdUnidadPerfil(documento.getUnidadpropietario());
                           
                           if (usersMailed.add(destinatario)) {
				   log.info("Enviando correo al usuario {}", destinatario.getUsuario());
				   mailService.ChaskiMail(iTipoEvento, remitente, destinatario, documento, contenido, accion);
			   } else {
				   log.debug("Ya se envio un correo al usuario {} previamente, no se enviara nuevo correo", destinatario.getUsuario());
			   }

			   break;
		   case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_ARCHIVADO:
                           if (documento.getUsuariosDestinatarios() != null) {
				   for (Usuario objDestinatario : documento.getUsuariosDestinatarios()) {
					   if ((documento.getPropietario() != null /*&& objDestinatario.getIdusuario() != documento.getPropietario().getIdusuario()*/) || documento.getPropietario() == null) {
						   if (usersInformed.add(objDestinatario)) {
							   log.info("Enviando informativo/correo al usuario {}", objDestinatario.getUsuario());
							 //wcarrasco 19-10-2010 falta revisar  al informarViaNotifAndMail La Fecha Notificacion
							   this.enviarNotificacion(remitente, objDestinatario, documento, iTipoNotificacion, nombrePC,true, null, null);
							   mailService.ChaskiMail(iTipoEvento, remitente, objDestinatario, documento, contenido, accion);
						   } else {
							   log.debug("Ya se envio un informativo/correo al usuario {} previamente, no se enviara nada", objDestinatario.getUsuario());
						   }
					   }
				   }
			   }
			   break;

		   case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_MULTIPLE:
                           if (documento.getUsuariosDestinatarios() != null) {
				   for (Usuario objDestinatario : documento.getUsuariosDestinatarios()) {
					   log.info("Enviando Multiple/correo al usuario {}", objDestinatario.getUsuario());
					   mailService.ChaskiMail(iTipoEvento, remitente, objDestinatario, documento, contenido, objDestinatario.getProveido());
				   }
			   }
			   break;

		   case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_OEFA:
                           if (documento.getUsuariosDestinatarios() != null) {
				   for (Usuario objDestinatario : documento.getUsuariosDestinatarios()) {
					   if (usersInformed.add(objDestinatario)) {
						   log.info("Enviando informativo/correo al usuario {}", objDestinatario.getUsuario());
						 //wcarrasco 19-10-2010 falta revisar  al informarViaNotifAndMail La Fecha Notificacion
						   this.enviarNotificacion(remitente, objDestinatario, documento, iTipoNotificacion, nombrePC,true,null, null);
						   mailService.ChaskiMail(iTipoEvento, remitente, objDestinatario, documento, contenido, accion);
					   } else {
						   log.debug("Ya se envio un informativo/correo al usuario {} previamente, no se enviara nada", objDestinatario.getUsuario());
					   }
				   }
			   }
			   break;
		   case Constantes.CONFIGNOTIFMAIL_CREACION_EXPEDIENTE:
                	   iBegin = Constantes.CONFIGNOTIFMAIL_CREACION_EXPEDIENTE_BEGIN;
			   iEnd = Constantes.CONFIGNOTIFMAIL_CREACION_EXPEDIENTE_END;
			   evento = "Creacion de Expediente";
			   break;
		   case Constantes.CONFIGNOTIFMAIL_ALARMA_AMARILLA:
			   iBegin = Constantes.CONFIGNOTIFMAIL_ALARMA_AMARILLA_BEGIN;
			   iEnd = Constantes.CONFIGNOTIFMAIL_ALARMA_AMARILLA_END;
			   evento = "Alarma Amarilla";
			   break;
		   case Constantes.CONFIGNOTIFMAIL_ALARMA_ROJA:
                	   iBegin = Constantes.CONFIGNOTIFMAIL_ALARMA_ROJA_BEGIN;
			   iEnd = Constantes.CONFIGNOTIFMAIL_ALARMA_ROJA_END;
			   evento = "Alarma Roja";
			   break;
		   }

		   if (iBegin > iEnd) {
			   log.warn("Error al configurar los indices inicio y fin, no se enviara nada");
                           return usersMailed;
		   }

		//   log.debug("Configuracion de envio de Correo {} evento {} indice inicio {} indice final {}", new Object[]{iConfig_Notif_Mail, evento, iBegin, iEnd});

		   if (Constantes.CONFIGNOTIFMAIL_CREACION_EXPEDIENTE == iTipoEvento) {
			   lstUsersToBeInformed = null;//documento.getExpediente().getProceso().getNotificadoList();

			   if (lstUsersToBeInformed != null && lstUsersToBeInformed.size() > 0) {
				   for (Usuario informed : lstUsersToBeInformed) {
					   if (usersMailed.add(informed)) {
						   log.info("Enviando correo al usuario {}", informed.getUsuario());
						   mailService.ChaskiMail(iTipoEvento, remitente, informed, documento, contenido, accion);
					   } else {
						   log.debug("Ya se envio un correo al usuario {} previamente, no se enviara nuevo correo", informed.getUsuario());
					   }

					   if (!usersInformed.add(informed)) {
						   log.debug("El usuario {} ya ha sido informado previamente, no se enviara nuevo informativo", informed.getUsuario());
						   continue;
					   }

					   log.info("Enviando informativo al usuario {}", informed.getUsuario());
					 //wcarrasco 19-10-2010 falta revisar  al informarViaNotifAndMail La Fecha Notificacion
					   this.enviarNotificacion(remitente, informed, documento, iTipoNotificacion, nombrePC,true,null, null);
				   }
			   }
		   }

		   iMask <<= iBegin;
		   Constantes.ConfigNotifMailDestinatario arrDestinatario[] = Constantes.ConfigNotifMailDestinatario.values();
                
                   return usersMailed;
	   } catch (Exception e) {
		   log.error(e.getMessage(), e);
                   e.printStackTrace();
                   return null;
	   }
   }

   // ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public NotificacionDAO getDao(){
		return dao;
	}

	public void setDao(NotificacionDAO dao){
		this.dao=dao;
	}

   public TrazabilidaddocumentoService getSrvTrazDoc() {
      return srvTrazDoc;
   }

   public void setSrvTrazDoc(TrazabilidaddocumentoService srvTrazDoc) {
      this.srvTrazDoc = srvTrazDoc;
   }

	public TrazabilidaddocumentoDAO getTrazabilidaddocumentoDAO() {
		return trazabilidaddocumentoDAO;
	}

	public void setTrazabilidaddocumentoDAO(
			TrazabilidaddocumentoDAO trazabilidaddocumentoDAO) {
		this.trazabilidaddocumentoDAO = trazabilidaddocumentoDAO;
	}

   public DocumentoService getDocumentoService() {
      return documentoService;
   }

   public void setDocumentoService(DocumentoService documentoService) {
      this.documentoService = documentoService;
   }

   public TrazabilidadcopiaService getTrazabilidadcopiaService() {
	   return trazabilidadcopiaService;
   }

   public void setTrazabilidadcopiaService(
		   TrazabilidadcopiaService trazabilidadcopiaService) {
	   this.trazabilidadcopiaService = trazabilidadcopiaService;
   }

   public AccionService getAccionService() {
	   return accionService;
   }

   public void setAccionService(AccionService accionService) {
	   this.accionService = accionService;
   }

    /**
     * @return the mailService
     */
    public ManejoDeEmailService getMailService() {
        return mailService;
    }

    /**
     * @param mailService the mailService to set
     */
    public void setMailService(ManejoDeEmailService mailService) {
        this.mailService = mailService;
    }

	public FechaLimite getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(FechaLimite fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public DocumentoEnviadoDAO getDocumentoEnviadoDao() {
		return documentoEnviadoDao;
	}

	public void setDocumentoEnviadoDao(DocumentoEnviadoDAO documentoEnviadoDao) {
		this.documentoEnviadoDao = documentoEnviadoDao;
	}

	public TrazabilidadapoyoService getTrazabilidadapoyoService() {
		return trazabilidadapoyoService;
	}
        
         public UnidadService getUnidadService() {
        return unidadService;
    }

    public void setUnidadService(UnidadService unidadService) {
        this.unidadService = unidadService;
    }

	public void setTrazabilidadapoyoService(
			TrazabilidadapoyoService trazabilidadapoyoService) {
		this.trazabilidadapoyoService = trazabilidadapoyoService;
	}

	public EstadoService getEstadoService() {
		return estadoService;
	}

	public void setEstadoService(EstadoService estadoService) {
		this.estadoService = estadoService;
	}

	public GridcolumnaxusuarioService getGridColumnaXUsuarioService() {
		return gridColumnaXUsuarioService;
	}

	public void setGridColumnaXUsuarioService(
			GridcolumnaxusuarioService gridColumnaXUsuarioService) {
		this.gridColumnaXUsuarioService = gridColumnaXUsuarioService;
	}

	@Override
	public List<Nodo> getDojoInformativoTree(BusquedaAvanzada objFiltro, Usuario objUsuario) {
		log.debug("-> [Service] NotificacionService - getDojoInformativoTree():List<Nodo> ");

		List<Nodo> lstArbol = null;
		List<Item> lstItemNotificacion = gridColumnaXUsuarioService.getItems_Informativo(objUsuario, objFiltro);

		if (lstItemNotificacion != null) {
			log.debug("Numero de informativos encontrados [" + lstItemNotificacion.size() + "]");
			lstArbol = new ArrayList<Nodo>();

			for (Item objItemInformativo : lstItemNotificacion) {
				Nodo objNodo = new Nodo(Boolean.TRUE, objItemInformativo.getId(), objItemInformativo.getDocumento(), null);
				log.debug("Nodo Informativo con ID [" + objItemInformativo.getId()+ "] documento [" + objItemInformativo.getDocumento() + "]");
				lstArbol.add(objNodo);
			}
		}

		return lstArbol;
	}




}
