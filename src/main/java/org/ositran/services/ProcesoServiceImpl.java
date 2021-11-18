package org.ositran.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.daos.ProcesoDAO;
import org.ositran.siged.dto.ProcesoDTO;
import org.ositran.utils.Constantes;
import org.ositran.utils.StringUtil;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Lista;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Tipoproceso;
import com.btg.ositran.siged.domain.Usuario;

public class ProcesoServiceImpl implements ProcesoService {

   private static Logger log = Logger.getLogger(ProcesoServiceImpl.class);
   private ProcesoDAO dao;
   private AuditoriaService srvAuditoria;
   private UsuarioService srvUsuario;
   private TipoprocesoService srvTipoProceso;
   private ParametroService parametroService;
   private ListaService srvLista;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public ProcesoServiceImpl(ProcesoDAO dao) {
      setDao(dao);
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public List<Proceso> buscarLstPor(Integer iIdResponsable) {
      return dao.bsucarLstPor(iIdResponsable);
   }

   public List<Proceso> findByIdResponsableOrAsistente(Integer iIdUsuario, char cEstado) {
      return getDao().findByIdResponsableOrAsistente(iIdUsuario, cEstado);
   }

   public List<Proceso> findPorcentajesProcesos() {
	   return getDao().findPorcentajesProcesos();
   }

   public List<Proceso> getProcesoList(String sTipoProceso) {
	      if (StringUtil.isEmpty(sTipoProceso)) {
	         return dao.findAll();
	      } else if (sTipoProceso.equals(Constantes.TIPO_PROCESO_STOR)) {
	         Tipoproceso objTipoProceso = srvTipoProceso.buscarObjPor(sTipoProceso);

	         return dao.findAllBy(objTipoProceso.getIdtipoproceso());
	      } else {
	         return dao.findAll();
	      }
	}

   public List<Proceso> getProcesoListxOrigen(String origen) {

	  log.debug("getProcesoListxOrigen: "+origen) ;

	  List <Parametro> tprocesos = parametroService.findByTipo(Constantes.PARAMETRO_ORIGEN_EXPEDIENTE);

      if ( StringUtil.isEmpty(origen) ) {

         return dao.findAll();

      } else if (origen.equals(Constantes.ORIGEN_EXPEDIENTE_NUEVO) ) {

         return dao.findAllByTypes(tprocesos);

      } else {

         return dao.findAll();

      }
   }

   /*Joseph*/
   public List<Proceso> getProcesoListxOrigen(Integer iIdUsuario,String origen) {

		  log.debug("getProcesoListxOrigen: "+origen) ;

		  List <Parametro> tprocesos = parametroService.findByTipo(Constantes.PARAMETRO_ORIGEN_EXPEDIENTE);

	      if ( StringUtil.isEmpty(origen) ) {

	         return dao.findAll();

	      } else if (origen.equals(Constantes.ORIGEN_EXPEDIENTE_NUEVO) ) {

	         return dao.findByProcesoOrIdResponsable_Asistente(iIdUsuario, tprocesos, Constantes.ESTADO_ACTIVO);

	      } else {

	         return dao.findAll();

	      }
	   }

   public List<Proceso> getProcesosPorTipoProcesoYestado(String sTipoProceso, Character cEstado){
	   return dao.findAllByTipoProcesoAndEstado(sTipoProceso, cEstado);
   }

   public List<Proceso> getProcesosActivos()throws RuntimeException{
	   try {
	         return getDao().buscarProcesosActivos();
	      } catch (RuntimeException re) {
	         log.error("", re);
	         return null;
	      }
   }


   public List<Proceso> getProcesosActivosSAS()throws RuntimeException{
	   try {
	         return getDao().buscarProcesosSancionadorActivos();
	      } catch (RuntimeException re) {
	         log.error("", re);
	         return null;
	      }
   }


   public Proceso findByIdProceso(Integer iId) throws RuntimeException {
      try {
         return getDao().findByIdProceso(iId);
      } catch (RuntimeException re) {
         log.error("", re);

         return null;
      }
   }


   public ArrayList<Object> cargaUsuarioMasivo(Integer[] var) throws RuntimeException {
      try {
         return getDao().cargaUsuarioMasivo(var);
      } catch (RuntimeException re) {
         log.error("", re);

         return null;
      }
   }

   public List<Proceso> buscarProcesosxUsuarioParticipante(Usuario usuario) {
      try {
         return getDao().buscarProcesosxUsuarioParticipante(usuario);
      } catch (RuntimeException re) {
         log.error("", re);

         return null;
      }
   }

   public List<ProcesoDTO> buscarProcesosDTOxUsuarioParticipante(Usuario usuario) {
	      try {
	         List<Proceso> procesos = getDao().buscarProcesosActivos();//getDao().buscarProcesosxUsuarioParticipante(usuario);
                 List<ProcesoDTO> salida = new ArrayList<ProcesoDTO>();
	         for(Proceso proceso : procesos){
	        	 ProcesoDTO dto = new ProcesoDTO();
	        	 dto.setIdProceso(proceso.getIdproceso());
	        	 dto.setNombre(proceso.getNombre());
	        	 dto.setUnidadId(proceso.getResponsable().getUnidad().getIdunidad());
	        	 dto.setUnidadNombre(proceso.getResponsable().getUnidad().getNombre());
	        	 dto.setResponsableNombres(proceso.getResponsable().getNombres());
	        	 dto.setResponsableApellidos(proceso.getResponsable().getApellidos());
	        	 dto.setResponsableIdUsuario(proceso.getResponsable().getIdusuario());
	        	 dto.setTipoNombre(proceso.getTipoproceso().getNombre());
	        	 dto.setPermiteSumilla(proceso.getPermitesumilla());
	        	 dto.setCodigo(proceso.getCodigo());
	        	 dto.setProduccion(proceso.getProduccion());

	        	 salida.add(dto);
	         }

	         return salida;
	      } catch (RuntimeException re) {
	         log.error("", re);

	         return null;
	      }
	   }


   public List<ProcesoDTO> buscarProcesosDTOxUsuarioParticipanteN2(Usuario usuario) {
	      try {
	         List<Proceso> procesos = getDao().buscarProcesosxUsuarioParticipanteN2(usuario);
	         List<ProcesoDTO> salida = new ArrayList<ProcesoDTO>();
	         for(Proceso proceso : procesos){
	        	 ProcesoDTO dto = new ProcesoDTO();
	        	 dto.setIdProceso(proceso.getIdproceso());
	        	 dto.setNombre(proceso.getNombre());
	        	 dto.setUnidadId(proceso.getResponsable().getUnidad().getIdunidad());
	        	 dto.setUnidadNombre(proceso.getResponsable().getUnidad().getNombre());
	        	 dto.setResponsableNombres(proceso.getResponsable().getNombres());
	        	 dto.setResponsableApellidos(proceso.getResponsable().getApellidos());
	        	 dto.setResponsableIdUsuario(proceso.getResponsable().getIdusuario());
	        	 dto.setTipoNombre(proceso.getTipoproceso().getNombre());
	        	 dto.setPermiteSumilla(proceso.getPermitesumilla());
	        	 dto.setCodigo(proceso.getCodigo());
	        	 dto.setProduccion(proceso.getProduccion());

	        	 salida.add(dto);
	         }

	         return salida;
	      } catch (RuntimeException re) {
	         log.error("", re);

	         return null;
	      }
	   }

   @Transactional
   public void saveProceso(Proceso objProcesoOld, Proceso objProcesoNew, List<Integer> lstIdParticipante, List<Integer> lstIdLista, List<Integer> lstIdParticipanteConfidencial, List<Integer> lstIdNotificado, String sUsuarioSesion, String sTipoAuditoria) {
      List<Lista> lstLista = new ArrayList<Lista>();
      List<Usuario> lstParticipante = new ArrayList<Usuario>();
      List<Usuario> lstParticipanteConfidencial = new ArrayList<Usuario>();
      List<Usuario> lstNotificado = new ArrayList<Usuario>();

      for (Integer iIdParticipante : lstIdParticipante) {
         Usuario objUsr = srvUsuario.findByIdUsuario(iIdParticipante);

         lstParticipante.add(objUsr);
      }

      if (lstIdLista != null) {
         for (Integer iIdLista : lstIdLista) {
            lstLista.add(srvLista.findByIdLista(iIdLista));
         }
      }

      if (objProcesoNew.getIdtipoacceso().getCodigo().equals(Constantes.TIPO_ACCESO_4) && lstIdParticipanteConfidencial != null) {
         for (Integer iIdParticipante : lstIdParticipanteConfidencial) {
            Usuario objUsr = srvUsuario.findByIdUsuario(iIdParticipante);

            lstParticipanteConfidencial.add(objUsr);
         }
      }

      if (lstIdNotificado != null) {
         for (Integer iIdNotificado : lstIdNotificado) {
            lstNotificado.add(srvUsuario.findByIdUsuario(iIdNotificado));
         }
      }

      //Solo se hace clear cuando haya un proceso existente
      if (objProcesoNew.getIdproceso() != null) {
         if (objProcesoNew.getUsuarioList() != null) {
            objProcesoNew.getUsuarioList().clear();
         }

         if (objProcesoNew.getUsuarioList1() != null) {
            objProcesoNew.getUsuarioList1().clear();
         }

         if (objProcesoNew.getNotificadoList() != null) {
            objProcesoNew.getNotificadoList().clear();
         }
      }

      log.debug("Usuarios Participantes [" + lstParticipante.size() + "]");
      log.debug("Listas Participante [" + lstLista.size() + "]");
      log.debug("Usuarios Confidenciales [" + lstParticipanteConfidencial.size() + "]");
      log.debug("Usuarios Notificados [" + lstNotificado.size() + "]");

      objProcesoNew.setUsuarioList(lstParticipante);
      objProcesoNew.setListaList(lstLista);
      objProcesoNew.setUsuarioList1(lstParticipanteConfidencial);
      objProcesoNew.setNotificadoList(lstNotificado);

      try {
         srvAuditoria.aplicarAuditoria(objProcesoOld, objProcesoNew, sUsuarioSesion, Constantes.AUDITORIA_OPCION_GUARDAR, sTipoAuditoria);
      } catch (ClassNotFoundException e) {
         log.error(e.getMessage(), e);
      }

      objProcesoNew = dao.saveProceso(objProcesoNew);

      log.debug("Proceso guardado con ID [" + objProcesoNew.getIdproceso() + "] Nombre [" + objProcesoNew.getNombre() + "] Participantes [" + objProcesoNew.getUsuarioList().size() + "] Listas Participante [" + objProcesoNew.getListaList().size() + "] Participantes Confidenciales [" + objProcesoNew.getUsuarioList1().size() + "] Notificados [" + objProcesoNew.getNotificadoList().size() + "]");
   }

   @Override
   @Transactional
   public void updateEstadoProceso(Integer idProceso,Character estado) {
	   if(idProceso==null || estado==null){
		   log.error("fallo en update Estado Proceso");
		   return;
	   }

    Proceso objProceso= this.findByIdProceso(idProceso);
    objProceso.setEstado(estado);
    objProceso= getDao().saveProceso(objProceso);
    log.debug("Estado del Proceso con ID [" + objProceso.getIdproceso()+ "] actualizado a [" + objProceso.getEstado() + "]");
   }

   public List<Proceso> findLstBy(Usuario objParticipante) {
      return dao.findLstBy(objParticipante);
   }

   public List<ProcesoDTO> buscarProcesosActivosResumen() {
      return dao.buscarProcesosActivosResumen();
   }

   public List<Proceso> getLstAutoCompleteVigente(){
	   return dao.getLstAutoCompleteVigente();
   }

   public List<Proceso> getLstAutoComplete() {
      return dao.getLstAutoComplete();
   }
   public Proceso getProcesoxId(Integer idProceso){
	   return dao.getProcesoxId(idProceso);
   }
   @Override
   public Proceso findObjectBy(String nombre, Character estado) {
      if (log.isDebugEnabled()) {
         log.debug("Buscando proceso con nombre [" + nombre + "] estado [" + estado + "]");
      }

      if (StringUtil.isEmpty(nombre) || estado == null) {
         return null;
      }

      return dao.findObjectBy(nombre, estado);
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public ProcesoDAO getDao() {
      return dao;
   }

   public void setDao(ProcesoDAO dao) {
      this.dao = dao;
   }

   public AuditoriaService getSrvAuditoria() {
      return srvAuditoria;
   }

   public void setSrvAuditoria(AuditoriaService srvAuditoria) {
      this.srvAuditoria = srvAuditoria;
   }

   public UsuarioService getSrvUsuario() {
      return srvUsuario;
   }

   public void setSrvUsuario(UsuarioService srvUsuario) {
      this.srvUsuario = srvUsuario;
   }

   public TipoprocesoService getSrvTipoProceso() {
      return srvTipoProceso;
   }

   public void setSrvTipoProceso(TipoprocesoService srvTipoProceso) {
      this.srvTipoProceso = srvTipoProceso;
   }

	public ParametroService getParametroService() {
		return parametroService;
	}

	public void setParametroService(ParametroService parametroService) {
		this.parametroService = parametroService;
	}

   public ListaService getSrvLista() {
      return srvLista;
   }

   public void setSrvLista(ListaService srvLista) {
      this.srvLista = srvLista;
   }

   public List<Proceso> findbyProcesosStor(){
	   return dao.findbyProcesosStor();
   }

}
