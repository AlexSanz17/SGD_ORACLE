package org.ositran.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.ositran.daos.AccionDAO;
import org.ositran.daos.AuditoriaDAO;
import org.ositran.daos.DocumentoDAO;
import org.ositran.daos.ParametroDAO;
import org.ositran.daos.ProveidoDAO;
import org.ositran.daos.TrazabilidaddocumentoDAO;
import org.ositran.dojo.grid.Item;
import org.ositran.utils.Constantes;
import org.ositran.utils.FechaLimite;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Accion;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Etapa;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import com.opensymphony.xwork2.ActionContext;
import org.ositran.utils.DocumentoDetail;

public class TrazabilidaddocumentoServiceImpl implements TrazabilidaddocumentoService{

	private static Logger log=Logger.getLogger(TrazabilidaddocumentoServiceImpl.class);
	private TrazabilidaddocumentoDAO dao;
	private AuditoriaDAO daoauditoria;
	private FechaLimite fechaLimite;
	private AccionDAO accionDao;
	private DocumentoDAO documentoDAO;
	private ParametroDAO parametroDAO;
	private ProveidoDAO proveidoDAO;

	// ////////////////////////////////
	// Constructors //
	// ////////////////////////////////
	public TrazabilidaddocumentoServiceImpl(TrazabilidaddocumentoDAO dao){
		setDao(dao);
	}

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	public List<Trazabilidaddocumento> findByIdDocumento(Integer iIdDocumento){
		log.debug("-> [Service] TrazabilidaddocumentoService - findByIdDocumento():List<Trazabilidaddocumento> ");

		return getDao().buscarLstPor(iIdDocumento);
	}

	@Override
	public Trazabilidaddocumento findByIdTrazabilidadDocumento(
			Integer iIdTrazabilidadDocumento) {
		log.debug("-> [Service] TrazabilidaddocumentoService - findByIdTrazabilidadDocumento():Trazabilidaddocumento ");
		return dao.findTrabilidadbyId(iIdTrazabilidadDocumento);
	}

	public List<Trazabilidaddocumento> buscarTrazaCompleta(Integer idExpediente){
		log.debug("-> [Service] TrazabilidaddocumentoService - buscarTrazaCompleta():List<Trazabilidaddocumento> ");

		return dao.buscarTrazaCompleta(idExpediente);
	}

	public List<Trazabilidaddocumento> findByMaxtrazabyIddocumento(Integer iIdDocumento){
		log.debug("-> [Service] TrazabilidaddocumentoService - findByMaxtrazabyIddocumento():List<Trazabilidaddocumento> ");

		return getDao().buscarMaxTrazaPor(iIdDocumento);
	}

	public Trazabilidaddocumento findByDocumentoDestinatario(Integer iIdDocumento,Integer iIdDestinatario) throws NoResultException{
		log.debug("-> [Service] TrazabilidaddocumentoService - findByDocumentoDestinatario():Trazabilidaddocumento ");

		try{
			return getDao().findByDocumentoDestinatario(iIdDocumento,iIdDestinatario);
		}
		catch(NoResultException nre){
			return null;
		}
	}

	public Trazabilidaddocumento findByMaxNroRegistro(Integer iIdDocumento,String sAccion, Integer idaccion, Integer destinatariod) throws NoResultException{
		log.debug("-> [Service] TrazabilidaddocumentoService - findByMaxNroRegistro():Trazabilidaddocumento ");
                
		try{
                        
			return getDao().findByMaxNroRegistro(iIdDocumento, idaccion,destinatariod);
		}catch(NoResultException nre){
			String sMensaje="No se encontro ninguna trazabilidad del documento con ID ["+iIdDocumento+"]. accion ["+sAccion+"]";

			log.warn(sMensaje);

			return null;
		
		}
	}
        
        public Trazabilidaddocumento findByMaxNroRegistro(Integer iIdDocumento,String sAccion, Integer idaccion, Integer destinatariod, Integer unidad, Integer funcion) throws NoResultException{
		log.debug("-> [Service] TrazabilidaddocumentoService - findByMaxNroRegistro():Trazabilidaddocumento ");
                
		try{
			return getDao().findByMaxNroRegistro(iIdDocumento, idaccion,destinatariod, unidad, funcion);
		}catch(NoResultException nre){
			String sMensaje="No se encontro ninguna trazabilidad del documento con ID ["+iIdDocumento+"]";

			log.warn(sMensaje);

			return null;
		
		}
	}

	public int contarListTotalTrazabilidadesExpediente(Trazabilidaddocumento t){
		return dao.contarListTotalTrazabilidadesExpediente(t);
	}
        
         public int contarListTotalTrazabilidadesUnidad(Trazabilidaddocumento t, List<Unidad> lst){
               return dao.contarListTotalTrazabilidadesUnidad(t, lst);
         }
        
        public int contarListTotalTrazabilidadesDocumento(Trazabilidaddocumento t){
		return dao.contarListTotalTrazabilidadesDocumento(t);
	}

	public Trazabilidaddocumento findRemitenteAccionByIddoc(Integer iIdDocumento){
		log.debug("-> [Service] TrazabilidaddocumentoService - findRemitenteAccionByIddoc():Trazabilidaddocumento ");

		try{
			return getDao().findRemitenteAccionByIddoc(iIdDocumento);
		}catch(NoResultException nre){
			String sMensaje="No se encontro ninguna trazabilidad del documento con ID ["+iIdDocumento+"]";
			log.warn(sMensaje);
			return null;
		}
	}

	@Transactional
	public Trazabilidaddocumento saveTrazabilidadDocumento(Documento objD,Usuario objUsuario, boolean copiarfechaLimite, boolean copiarasuntodocumento){
		log.debug("-> [Service] TrazabilidaddocumentoService - saveTrazabilidadDocumento():Trazabilidaddocumento (2)");
		log.info(" objD " + objD + " objUsuario " + objUsuario + " copiarfechaLimite " + copiarfechaLimite + " copiarasuntodocumento " + copiarasuntodocumento);
                Trazabilidaddocumento objMT=this.findByMaxNroRegistro(objD.getIdDocumento(),objD.getAccion().getNombre(), null, null);
        log.info(" objMT " + objMT);
		Trazabilidaddocumento objT=new Trazabilidaddocumento();

		Integer totalComp=0;
		
                if (objUsuario!=null){
                    totalComp=totalCompartidos(objUsuario.getIdUsuarioPerfil());
                }
		
                objT.setRemitente(new Usuario(objUsuario.getIdUsuarioPerfil()));
		objT.setCompartido(totalComp==0?null:new Usuario(objUsuario.getIdUsuarioPerfil()));
                objT.setUnidadremitente(objUsuario.getIdUnidadPerfil());
                objT.setCargoremitente(objUsuario.getIdFuncionPerfil());
		objT.setDestinatario(objD.getPropietario());
                objT.setUnidaddestinatario(objUsuario.getIdUnidadPerfil());
                objT.setCargodestinatario(objUsuario.getIdFuncionPerfil());
                objT.setUsuariocreacion(objUsuario.getIdusuario());
		objT.setDocumento(objD);
                
                if (objD.getIndAlerta()==null || objD.getIndAlerta().trim().equals(""))
		    objT.setIndalerta("0");
		else
		    objT.setIndalerta(objD.getIndAlerta());
                
		objT.setAccion(objD.getAccion());
		objT.setNroregistro((objMT==null) ? 1 : objMT.getNroregistro()+1);
                
		if(copiarasuntodocumento){
			objT.setAsunto(objD.getAsunto());
		}else{
			objT.setAsunto(objD.getExpediente().getAsunto());
		}

		objT.setContenido(objD.getExpediente().getContenido());
		objT.setObservacion(objD.getObservacion());
		objT.setFechacreacion(new Date());

		SimpleDateFormat sdfMes=new SimpleDateFormat("MM");
		Integer fechacreacionmonth=Integer.parseInt(sdfMes.format(objT.getFechacreacion()));
		objT.setFechacreacionmonth(fechacreacionmonth);
		SimpleDateFormat sdfAnio=new SimpleDateFormat("yyyy");
		Integer fechacreacionyear=Integer.parseInt(sdfAnio.format(objT.getFechacreacion()));
		objT.setFechacreacionyear(fechacreacionyear);
               
		if(copiarfechaLimite){
			objT.setFechalimiteatencion( objD.getFechaLimiteAtencion()  ) ;
		}
                
                return getDao().saveTrazabilidadDocumento(objT);
	}

	@SuppressWarnings("unused")
	@Transactional
	public Trazabilidaddocumento saveTrazabilidadDocumento(Documento objD, Usuario objUsuario,Integer iPlazoDia,Integer iPlazoHora,String fechaLimiteAtencion, String asunto, String contenido, String[] acciones, String nombrePC, Boolean horarioPermitido,String sinPlazo , Boolean horarioPermitidoRecepcion,  Usuarioxunidadxfuncion objDestino, Integer prioridad) throws Exception {
		log.debug("-> [Service] TrazabilidaddocumentoService - saveTrazabilidadDocumento():Trazabilidaddocumento (1)");
                
                Trazabilidaddocumento objMT = this.findByMaxNroRegistro(objD.getIdDocumento(), objD.getAccion().getNombre(), null, null);
		Trazabilidaddocumento objT = new Trazabilidaddocumento();
                
           	objT.setRemitente(new Usuario(objUsuario.getIdUsuarioPerfil()));
                objT.setUnidadremitente(objUsuario.getIdUnidadPerfil());
                objT.setCargoremitente(objUsuario.getIdFuncionPerfil());
		objT.setCompartido(null);
                objT.setUsuariocreacion(objUsuario.getIdusuario());
                objT.setDestinatario(new Usuario(objDestino.getIdusuario()));
                objT.setUnidaddestinatario(objDestino.getIdunidad());
                objT.setCargodestinatario(objDestino.getIdfuncion());
                objT.setDocumento(objD);
                objT.setAccion(objD.getAccion());
                objT.setNroregistro((objMT == null) ? 1 : objMT.getNroregistro() + 1);
                objT.setNombrePC(nombrePC);
                objT.setIndalerta("0");
                objT.setPlazo(objD.getPlazo());
                         
                if(acciones != null){
                    String txtAdicional = "";
                    
                    if(contenido != null){
                       contenido = txtAdicional + contenido;
                    }else{
                       contenido = txtAdicional;
                    }

                    objT.setIdproveido(new Integer(acciones[0]));
                }
              
                if(asunto!=null){
                    objT.setAsunto(asunto.length() > 500 ? asunto.substring(0,498) : asunto);
                }else{
                    objT.setAsunto(objD.getExpediente().getAsunto());
                }
                if(contenido!=null){
                    objT.setContenido(contenido.length() > 4000 ? contenido.substring(0, 4998) : contenido);
                }else{
                    objT.setContenido(objD.getExpediente().getContenido());
                }
                
                objT.setObservacion(objD.getObservacion());
                objT.setFechacreacion(objD.getFechaAccion());
                SimpleDateFormat sdfMes=new SimpleDateFormat("MM");
                Integer fechacreacionmonth=Integer.parseInt(sdfMes.format(objT.getFechacreacion()));
                objT.setFechacreacionmonth(fechacreacionmonth);
                SimpleDateFormat sdfAnio=new SimpleDateFormat("yyyy");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Integer fechacreacionyear=Integer.parseInt(sdfAnio.format(objT.getFechacreacion()));
                
                try{
                    objT.setFechacreacionyear(fechacreacionyear);
                    
                    if (fechaLimiteAtencion!=null && !fechaLimiteAtencion.trim().equals(""))
                      objT.setFechalimiteatencion(formatter.parse(fechaLimiteAtencion));
                    else
                      objT.setFechalimiteatencion(null);
                    
                    objT.setPrioridad(prioridad);
                }catch(Exception e){
                    throw e;
                }
                
                return getDao().saveTrazabilidadDocumento(objT);
   }

	@SuppressWarnings("unused")
	@Transactional
	public Trazabilidaddocumento saveTrazabilidadDocumentoMasivos(Documento objD, Usuario objUsuario,Integer iPlazoDia,Integer iPlazoHora,
	       String fechaLimiteAtencion, String asunto, String contenido, Etapa objEtapa,Accion accionOpt,Usuario destinatario) {
		
              log.debug("-> [Service] TrazabilidaddocumentoService - saveTrazabilidadDocumentoMasivos():Trazabilidaddocumento ");

              Trazabilidaddocumento objMT = this.findByMaxNroRegistro(objD.getIdDocumento(), objD.getAccion().getNombre(), null, null);
              Trazabilidaddocumento objT = new Trazabilidaddocumento();

              Integer totalComp=0;

              totalComp=totalCompartidos(objUsuario.getIdusuario());
              objT.setRemitente(objUsuario);
              objT.setCompartido(totalComp==0?null:objUsuario);
              objT.setDestinatario(destinatario);
              objT.setDocumento(objD);
              //objT.setIdproceso(objD.getExpediente().getProceso());
              objT.setAccion(accionOpt);
              objT.setNroregistro((objMT == null) ? 1 : objMT.getNroregistro() + 1);

              log.debug("asunto ["+asunto+"]"+asunto.length());
              log.debug("contenido ["+contenido+"]"+contenido.length());

              if(asunto!=null){
                  objT.setAsunto(asunto);
              }else{
                  objT.setAsunto(objD.getExpediente().getAsunto());
              }
              if(contenido!=null){
                  objT.setContenido(contenido);
              }else{
                  objT.setContenido(objD.getExpediente().getContenido());
              }
              if(objD.getAccion().getNombre().equals(Constantes.ACCION_DERIVAR2)){
                  objT.setVerSeguimiento(Constantes.VER_SEGUIMIENTO_SI.charAt(0));
              }
              objT.setObservacion(objD.getObservacion());
              objT.setFechacreacion(new Date());

                  SimpleDateFormat sdfMes=new SimpleDateFormat("MM");
              Integer fechacreacionmonth=Integer.parseInt(sdfMes.format(objT.getFechacreacion()));
              objT.setFechacreacionmonth(fechacreacionmonth);
              SimpleDateFormat sdfAnio=new SimpleDateFormat("yyyy");
              Integer fechacreacionyear = Integer.parseInt(sdfAnio.format(objT.getFechacreacion()));
              objT.setFechacreacionyear(fechacreacionyear);

              //aplicarPlazosADocumento(objT, iPlazoDia, iPlazoHora, fechaLimiteAtencion,"revisar sin plazo?", objD);
              //if (objEtapa!=null)
               //   objT.setIdetapa(objEtapa);

              return getDao().saveTrazabilidadDocumento(objT);
   }
	@Transactional
	public Trazabilidaddocumento saveTrazabilidadDocumento(Trazabilidaddocumento td){
		log.debug("-> [Service] TrazabilidaddocumentoService - saveTrazabilidadDocumento():Trazabilidaddocumento ");

		return  getDao().saveTrazabilidadDocumento(td);
	}

	public List<Trazabilidaddocumento> getListTrazabilidadExpedienteByIdExpediente(Integer expedienteId){
		log.debug("-> [Service] TrazabilidaddocumentoService - getListTrazabilidadExpedienteByIdExpediente():List<Trazabilidaddocumento> ");

		return dao.getListTrazabilidadExpedienteByIdExpediente(expedienteId) ;
	}

	public Trazabilidaddocumento findTrabilidadbyId(Integer  idtrazabilidaddocumento) {
		log.debug("-> [Service] TrazabilidaddocumentoService - findTrabilidadbyId():Trazabilidaddocumento ");

		return getDao().findTrabilidadbyId(idtrazabilidaddocumento);
	}


	@Override
	@Transactional
	public void cambiarFechaLimite(Integer idtrazabilidaddocumento,Date nuevafecha){
		log.debug("-> [Service] TrazabilidaddocumentoService - cambiarFechaLimite():void ");

		Trazabilidaddocumento trazabilidad = getDao().findTrabilidadbyId(idtrazabilidaddocumento);
		log.debug("idtrazabilidaddocumento [" + idtrazabilidaddocumento + "]");

		SimpleDateFormat sd1 = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sd2 = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sd3 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
		nuevafecha = sd3.parse(sd1.format(nuevafecha)+" "+sd2.format(trazabilidad.getFechalimiteatencion()) );
      log.debug("nuevafecha [" + nuevafecha + "]");
		}catch (Exception ex) {
                    log.error(ex.getMessage(),ex);
		}
		trazabilidad.setFechalimiteatencion(nuevafecha);

		List <Trazabilidaddocumento> trExpList = getDao().getListTrazabilidadExpedienteByIdExpediente(trazabilidad.getDocumento().getExpediente().getIdexpediente()) ;
		Trazabilidaddocumento last = trExpList.get(trExpList.size()-1) ;
		dao.saveTrazabilidadDocumento(trazabilidad);
		if(last.getDestinatario().getIdusuario().intValue() == last.getDocumento().getPropietario().getIdusuario().intValue() ){
			Documento doc = trazabilidad.getDocumento() ;
			doc.setFechaLimiteAtencion( nuevafecha ) ;
			documentoDAO.saveDocumento( doc ) ;
		}

	}


	public Trazabilidaddocumento aplicarPlazosADocumento(Trazabilidaddocumento objT,Integer iPlazoDia,Integer iPlazoHora,String fechaLimiteAtencion, String sinPlazo, Documento documento){
		//corregido wcarrasco;
		log.debug("-> [Service] TrazabilidaddocumentoService - aplicarPlazosADocumento():Trazabilidaddocumento ");

		Date dNuevaFechaLimiteAtencion = new Date();

		SimpleDateFormat formatDate=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		log.debug("Plazo Dia ["+iPlazoDia+"] Hora ["+iPlazoHora+"] Fecha Limite Atencion ["+fechaLimiteAtencion+"]");

		List<Parametro> listParametro =  parametroDAO.findByTipo("PRIORIDAD_DIAS");
		int  iDia = 0;


		if(sinPlazo!=null && sinPlazo.equals(Constantes.SIN_PLAZO)){
			log.debug("transferir sin Plazo");
			objT.setPlazo(null);
			objT.setFechalimiteatencion(null);

		}else{
                       objT.setFechalimiteatencion(documento.getFechaLimiteAtencion());
		    
		}

		return objT;
	}

	public Trazabilidaddocumento aplicarPlazosADocumento(Trazabilidaddocumento objT,Integer iPlazoDia,Integer iPlazoHora,String fechaLimiteAtencion, String sinPlazo, Integer prioridad, Documento doc){
		//corregido wcarrasco;
		log.debug("-> [Service] TrazabilidaddocumentoService - aplicarPlazosADocumento():Trazabilidaddocumento ");

		Date dNuevaFechaLimiteAtencion = new Date();

		SimpleDateFormat formatDate=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		log.debug("Plazo Dia ["+iPlazoDia+"] Hora ["+iPlazoHora+"] Fecha Limite Atencion ["+fechaLimiteAtencion+"]");

		List<Parametro> listParametro =  parametroDAO.findByTipo("PRIORIDAD_DIAS");
		int  iDia = 0;

		/*if (!doc.getExpediente().getProceso().getCodigo().equals("TUPA")){
			for(int i=0;i<listParametro.size();i++){
					if (prioridad.toString().equals(listParametro.get(i).getValor())){
						iDia = Integer.parseInt(listParametro.get(i).getDescripcion());
						break;
					}
			}

			try {
				 dNuevaFechaLimiteAtencion = fechaLimite.calcularFechaLimite(iDia,0);
				 //objT.setPlazo(0);
				 objT.setPlazo(iDia*Constantes.NUMERO_HORAS_LABORABLESXDIA+0);
				 objT.setFechalimiteatencion(dNuevaFechaLimiteAtencion);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{*/
			//  objT.setPlazo(doc.getExpediente().getProceso().getTiempoatencion()*Constantes.NUMERO_HORAS_LABORABLESXDIA+0);
			  objT.setFechalimiteatencion(doc.getFechaLimiteAtencion());
		//}

		return objT;
	}

	@Transactional
	public Trazabilidaddocumento guardarTrazabilidad(Documento documento,Usuario remitente,Usuarioxunidadxfuncion destinatario,String accion,String asunto,String contenido, String nombrePC){
		log.debug("-> [Service] TrazabilidaddocumentoService - guardarTrazabilidad():Trazabilidaddocumento ");
         
		log.debug(accion);
		Accion laAccion=accionDao.findByNombre(accion);
		return guardarTrazabilidad(documento,remitente,destinatario,laAccion,asunto,contenido, nombrePC, true, true); // horarrioFlexible
	}


	public Trazabilidaddocumento saveTrazabilidadDocumento(Documento objD,Usuario objUsuario){
		log.debug("-> [Service] TrazabilidaddocumentoService - saveTrazabilidadDocumento():Trazabilidaddocumento ");

		Trazabilidaddocumento objMT=this.findByMaxNroRegistro(objD.getIdDocumento(),null,null,null);
		Trazabilidaddocumento objT=new Trazabilidaddocumento();

		Integer totalComp=0;
		Map<String,Object> mapSession=ActionContext.getContext().getSession();
		Usuario user=(Usuario)mapSession.get(Constantes.SESSION_USUARIO);
		totalComp=totalCompartidos(user.getIdusuario());
		Object obj=ServletActionContext.getRequest().getSession().getAttribute("UsuarioCompartido");
		if(obj!=null){
			Usuario propietario=(Usuario)obj;
			objT.setRemitente(propietario);
		}else{
			objT.setRemitente(objUsuario);
		}
		objT.setCompartido(totalComp==0?null:objUsuario);

		objT.setDestinatario(objD.getPropietario());
		objT.setDocumento(objD);
		//objT.setIdproceso(objD.getExpediente().getProceso());
		objT.setAccion(objD.getAccion());
		objT.setNroregistro((objMT == null) ? 1 : objMT.getNroregistro() + 1);
		objT.setAsunto(objD.getExpediente().getAsunto());
		objT.setContenido(objD.getExpediente().getContenido());
		objT.setObservacion(objD.getObservacion());
		objT.setFechacreacion(new Date());

		SimpleDateFormat sdfMes=new SimpleDateFormat("MM");
		Integer fechacreacionmonth=Integer.parseInt(sdfMes.format(objT.getFechacreacion()));
		objT.setFechacreacionmonth(fechacreacionmonth);
		SimpleDateFormat sdfAnio=new SimpleDateFormat("yyyy");
		Integer fechacreacionyear=Integer.parseInt(sdfAnio.format(objT.getFechacreacion()));
		objT.setFechacreacionyear(fechacreacionyear);

		return getDao().saveTrazabilidadDocumento(objT);
	}

	@Override
	public Trazabilidaddocumento encontrarUltimaTrazabilidadPorDocumento(int idDocumento){
		log.debug("-> [Service] TrazabilidaddocumentoService - encontrarUltimaTrazabilidadPorDocumento():Trazabilidaddocumento ");

		return dao.encontrarUltimaTrazabilidadPorDocumento(idDocumento);
	}

	@Override
	public Trazabilidaddocumento getTrazabilidadAnterior(Trazabilidaddocumento trazabilidad){
		log.debug("-> [Service] TrazabilidaddocumentoService - getTrazabilidadAnterior():Trazabilidaddocumento ");

		return dao.getTrazabilidadAnterior(trazabilidad);
	}
        
        @Transactional
	@Override
	public Trazabilidaddocumento guardarTrazabilidad(Documento documento,Usuario remitenteSession,Usuarioxunidadxfuncion destinatario,Accion accion,String asunto,String contenido, String nombrePC , Boolean horarioPermitido, Boolean horarioPermitidoRecepcion){
		log.debug("-> [Service] TrazabilidaddocumentoService - guardarTrazabilidad():Trazabilidaddocumento [1] ");
                Trazabilidaddocumento ultima=dao.findByMaxNroRegistro(documento.getIdDocumento(),null,null);
		Trazabilidaddocumento trazabilidad=new Trazabilidaddocumento();
		trazabilidad.setAsunto(asunto);
		trazabilidad.setContenido(contenido);
		trazabilidad.setDocumento(documento);
                trazabilidad.setRemitente(new Usuario(remitenteSession.getIdUsuarioPerfil()));
                trazabilidad.setUnidadremitente(remitenteSession.getIdUnidadPerfil());
                trazabilidad.setCargoremitente(remitenteSession.getIdFuncionPerfil());
                trazabilidad.setCompartido(null);
		trazabilidad.setNombrePC(nombrePC);
		trazabilidad.setDestinatario(new Usuario(destinatario.getIdusuario())); 
                trazabilidad.setUnidaddestinatario(destinatario.getIdunidad());
                trazabilidad.setCargodestinatario(destinatario.getIdfuncion());
                trazabilidad.setUsuariocreacion(remitenteSession.getIdusuario());
                trazabilidad.setAccion(accion);
		//Date fechaFueraHorarioRecepcion = new Date();
	        Date fechaValida = new Date();
                
	        if(horarioPermitidoRecepcion ==false){
	            /*Date fechaFueraHorario = new Date();
                    Date fechaFueraHorarioRecepcion = fechaLimite.fechaFueraHorarioRecepcionHabil(fechaFueraHorario,destinatario.getIdunidad().intValue());
		    fechaValida = fechaFueraHorarioRecepcion;
                        
                    long n = fechaValida.getTime()-(new Date()).getTime();   
                    long valor = n/60000;
                    fechaValida = new Date(fechaValida.getTime() + (8000-valor));*/
	        }
	    
                trazabilidad.setFechacreacion(fechaValida);
	        trazabilidad.setNroregistro(ultima == null ? 1 : ultima.getNroregistro() + 1);
                trazabilidad.setPlazo(documento.getPlazo());
                
                if(Constantes.ACCION_ATENDER.equals(accion.getNombre())){
                    trazabilidad.setPrioridad(null);
                    trazabilidad.setFechalimiteatencion(null);
                }
                
                return dao.saveTrazabilidadDocumento(trazabilidad);
	}

	@Transactional
	@Override
	public Trazabilidaddocumento guardarTrazabilidad(Documento documento,Usuario remitenteSession,Usuarioxunidadxfuncion destinatario,Accion accion,String asunto,String contenido, String nombrePC , Boolean horarioPermitido, Boolean horarioPermitidoRecepcion, DocumentoDetail documentoDetail) throws Exception{
		log.debug("-> [Service] TrazabilidaddocumentoService - guardarTrazabilidad():Trazabilidaddocumento [1] ");
                Trazabilidaddocumento ultima=dao.findByMaxNroRegistro(documento.getIdDocumento(),null,null);
		Trazabilidaddocumento trazabilidad=new Trazabilidaddocumento();
		trazabilidad.setAsunto(asunto);
		trazabilidad.setContenido(contenido);
		trazabilidad.setDocumento(documento);
        	trazabilidad.setRemitente(new Usuario(remitenteSession.getIdUsuarioPerfil()));
                trazabilidad.setUnidadremitente(remitenteSession.getIdUnidadPerfil());
                trazabilidad.setCargoremitente(remitenteSession.getIdFuncionPerfil());
        	trazabilidad.setCompartido(null);
		trazabilidad.setNombrePC(nombrePC);
		trazabilidad.setDestinatario(new Usuario(destinatario.getIdusuario())); 
                trazabilidad.setUnidaddestinatario(destinatario.getIdunidad());
                trazabilidad.setCargodestinatario(destinatario.getIdfuncion());
                trazabilidad.setUsuariocreacion(remitenteSession.getIdusuario());
        	trazabilidad.setAccion(accion);
	        Date fechaValida = new Date();

	        if(horarioPermitidoRecepcion ==false){ //JC-FECHA
	        	/*Date fechaFueraHorario = new Date();
                        Date fechaFueraHorarioRecepcion = fechaLimite.fechaFueraHorarioRecepcionHabil(fechaFueraHorario,destinatario.getIdunidad().intValue());
		    	fechaValida = fechaFueraHorarioRecepcion;
                        
                        long n = fechaValida.getTime()-(new Date()).getTime();   
                        long valor = n/60000;
                        fechaValida = new Date(fechaValida.getTime() + (8000-valor));*/
	        }
	    
                trazabilidad.setFechacreacion(fechaValida);
	        trazabilidad.setNroregistro(ultima == null ? 1 : ultima.getNroregistro() + 1);
                trazabilidad.setPlazo(documento.getPlazo());
                
                try{
                
                    if(Constantes.ACCION_ATENDER.equals(accion.getNombre())){
                        trazabilidad.setPrioridad(null);
                        trazabilidad.setFechalimiteatencion(null);
                    }

                    if(Constantes.ACCION_RECHAZAR.equals(accion.getNombre())){
                        trazabilidad.setPrioridad(documentoDetail.getPrioridad());
                        if (documentoDetail.getStrFechaLimiteAtencion()!=null && !documentoDetail.getStrFechaLimiteAtencion().trim().equals(""))
                           trazabilidad.setFechalimiteatencion(new SimpleDateFormat("yyyy-MM-dd").parse(documentoDetail.getStrFechaLimiteAtencion()));
                    }
                }catch(Exception e){
                    throw e;
                }
               
                return dao.saveTrazabilidadDocumento(trazabilidad);
	}

	@Override
	public Trazabilidaddocumento guardarTrazabilidad(Documento documento,Usuario remitente,Usuarioxunidadxfuncion destinatario,Accion accion, String nombrePC){
		log.debug("-> [Service] TrazabilidaddocumentoService - guardarTrazabilidad():Trazabilidaddocumento ");

		return guardarTrazabilidad(documento,remitente,destinatario,accion,documento.getAsunto(),documento.getContenido(), nombrePC, true, true); // horarrioFlexible
	}

	@SuppressWarnings("static-access")
	public List<Item> filtrarReporteDocumentos(String sFechaDesde, String sFechaHasta, String sDiasReporte, Integer idSede, Integer idProceso){
		log.debug("-> [Service] TrazabilidaddocumentoService - filtrarReporteDocumentos():List<Item> ");

		List<String> nombreAcciones = new ArrayList<String>();
		nombreAcciones.add(Constantes.ACCION_APROBAR_QAS);

		Calendar calendar = GregorianCalendar.getInstance();
		SimpleDateFormat sdHora=new SimpleDateFormat("HH");
		SimpleDateFormat sdFechaAux=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdFecha=new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdNombreMes = new SimpleDateFormat("MM/yyyy");

		List<Item> listItem = new ArrayList<Item>();
		try{

			Date diaInicio=sdFecha.parse(sFechaDesde);
			calendar.setTime(diaInicio);
			Integer diaFinal = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

			calendar.set(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), 1);
			String sFechaDesdeAux =  sdFecha.format(calendar.getTime());
			calendar.set(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), diaFinal);
			String sFechaHastaAux = sdFecha.format(calendar.getTime());

			List<Object[]> listTrazabilidadAux= dao.filtrarReporteDocumentos(nombreAcciones, sFechaDesdeAux, sFechaHastaAux, idSede, idProceso);
			int totalFoliosMensual = 0;
			for(int i=0; i<listTrazabilidadAux.size(); i++){
				totalFoliosMensual += Integer.valueOf(listTrazabilidadAux.get(i)[3].toString());
			}
			Item item=new Item();
			item.setHorarioReporte("Total Folios: "+sdNombreMes.format(diaInicio).toString());
			item.setReporteMP(totalFoliosMensual);
			listItem.add(item);

			nombreAcciones.add(Constantes.ACCION_DIGITALIZAR);
			nombreAcciones.add(Constantes.ACCION_REGISTRAR);
			List<Object[]> listTrazabilidad = dao.filtrarReporteDocumentos(nombreAcciones, sFechaDesde, sFechaHasta, idSede, idProceso);

			calendar = new GregorianCalendar();
			Accion accionMP = accionDao.buscarObjPor(Constantes.ACCION_REGISTRAR);
			Accion accionDIG = accionDao.buscarObjPor(Constantes.ACCION_DIGITALIZAR);
			Accion accionQAS = accionDao.buscarObjPor(Constantes.ACCION_APROBAR_QAS);

			Parametro p=this.parametroDAO.findByTipo(Constantes.INICIOHORARIO).get(0);
			int horainicio=new Integer(p.getValor());
			p=this.parametroDAO.findByTipo(Constantes.FINHORARIO).get(0);
			int horafin=new Integer(p.getValor());
			log.debug("Fecha desde "+sFechaDesde);
			log.debug("Fecha hasta "+sFechaHasta);

			String[] listDias = new String[Integer.valueOf(sDiasReporte)];

			for (int i=0; i<listDias.length; i++){
				listDias[i]= sdFechaAux.format(diaInicio);
				calendar.setTime(diaInicio);
				calendar.add(Calendar.DATE, 1);
				diaInicio = calendar.getTime();
			}
			for (int i=0 ; i<listDias.length; i++){
				item=new Item();
				item.setHorarioReporte("Reporte Documentos: "+ sdFecha.format(sdFechaAux.parse(listDias[i])));
				listItem.add(item);
				int totalMP=0;
				int totalDIG=0;
				int totalQAS=0;
				int totalFolios=0;
				for(int e=horainicio;e<=horafin;e++){
					int reportesMP=0;
					int reportesDIG=0;
					int reportesQAS=0;
					int reportesFolios=0;

					item=new Item();
					//Timestamp hora=new Timestamp(sdHora.parse((e<10 ? "0"+e : ""+e)).getTime());
					item.setHorarioReporte(e+":00 - "+ (e+1)+":00");
					for(Object[] objTrazabilidad : listTrazabilidad){
						int horadoc=new Integer(sdHora.format(objTrazabilidad[2])).intValue();
						String fechaDoc= sdFechaAux.format(objTrazabilidad[2]);
						if(horadoc==e && fechaDoc.equals(listDias[i])){
							if(objTrazabilidad[1].toString().equals(accionMP.getIdAccion().toString())){
								reportesMP++;
							}
							if(objTrazabilidad[1].toString().equals(accionDIG.getIdAccion().toString())){
								reportesDIG++;
							}
							if(objTrazabilidad[1].toString().equals(accionQAS.getIdAccion().toString())){
								reportesQAS++;
								reportesFolios += Integer.valueOf(objTrazabilidad[3].toString());
							}
						}
					}
					item.setReporteMP(reportesMP);
					item.setReporteDIG(reportesDIG);
					item.setReporteQAS(reportesQAS);
					item.setReporteFolios(reportesFolios);
					totalMP= totalMP+reportesMP;
					totalDIG= totalDIG+reportesDIG;
					totalQAS= totalQAS+reportesQAS;
					totalFolios= totalFolios+reportesFolios;
					listItem.add(item);
				}
				item=new Item();
				item.setHorarioReporte("TOTAL");
				item.setReporteMP(totalMP);
				item.setReporteDIG(totalDIG);
				item.setReporteQAS(totalQAS);
				item.setReporteFolios(totalFolios);
				listItem.add(item);
			}
		}catch(Exception e){
			log.error(e.getCause());
		}
		return listItem;
	}

	public List<Integer> findLstUsuarioDelProcAcc3(Documento d){
		log.debug("-> [Service] TrazabilidaddocumentoService - findLstUsuarioDelProcAcc3():List<Integer> ");

		return dao.findLstUsuarioDelProcAcc3(d);
	}

	public List<Integer> findLstUsuarioDelProcAcc2(Proceso p){
		log.debug("-> [Service] TrazabilidaddocumentoService - findLstUsuarioDelProcAcc2():List<Integer> ");

		return dao.findLstUsuarioDelProcAcc2(p);
	}

	public Integer totalCompartidos(int idUsuarioLogeado){
		log.debug("-> [Service] TrazabilidaddocumentoService - totalCompartidos():Integer ");

		return dao.totalUsuarioCompartidos(idUsuarioLogeado);
	}

	public Integer totalCompartidos(int idUsuarioLogeado,int idPropietarioDoc){
		log.debug("-> [Service] TrazabilidaddocumentoService - totalCompartidos():Integer ");

		return dao.totalUsuarioCompartidos(idUsuarioLogeado,idPropietarioDoc);
	}

	public boolean esPrimeraEtapaExpediente(Integer idExpediente){
		log.debug("-> [Service] TrazabilidaddocumentoService - esPrimeraEtapaExpediente():boolean ");

		Double maximoNumeroTrazas = dao.ratioTrazabilidadDocumento(idExpediente);
		return maximoNumeroTrazas > 1d ? false : true;
	}

	// ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public TrazabilidaddocumentoDAO getDao(){
		return dao;
	}

	public void setDao(TrazabilidaddocumentoDAO dao){
		this.dao=dao;
	}

	public FechaLimite getFechaLimite(){
		return fechaLimite;
	}

	public void setFechaLimite(FechaLimite fechaLimite){
		this.fechaLimite=fechaLimite;
	}

	public void setDaoauditoria(AuditoriaDAO daoauditoria){
		this.daoauditoria=daoauditoria;
	}

	public AuditoriaDAO getDaoauditoria(){
		return daoauditoria;
	}

	public ProveidoDAO getProveidoDAO() {
		return proveidoDAO;
	}

	public void setProveidoDAO(ProveidoDAO proveidoDAO) {
		this.proveidoDAO = proveidoDAO;
	}

	public void setDocumentoDAO(DocumentoDAO documentoDAO){
		this.documentoDAO=documentoDAO;
	}

	public ParametroDAO getParametroDAO() {
		return parametroDAO;
	}

	public void setParametroDAO(ParametroDAO parametroDAO) {
		this.parametroDAO = parametroDAO;
	}

	public void setAccionDao(AccionDAO accionDao){
		this.accionDao=accionDao;
	}

	public int contarListTotalTrazabilidades(Trazabilidaddocumento t){
		return dao.contarListTotalTrazabilidades(t);
	}

	@Override
	public Trazabilidaddocumento updateTrazabilidadDocumento(
			Trazabilidaddocumento objT) {

		return dao.updateTrazabilidadDocumento(objT);
	}

}
