package org.ositran.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.ositran.daos.UnidadDAO;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Unidad;

public class UnidadServiceImpl implements UnidadService {

   private static Logger log = Logger.getLogger(UnidadServiceImpl.class);
   private UnidadDAO dao;
   private AuditoriaService srvAuditoria;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public UnidadServiceImpl(UnidadDAO dao) {
      this.dao = dao;
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public List<Unidad> obtenerLstTodo() {
      return dao.findAll();
   }
   
   public List<Unidad> buscarUnidadesFuncionales(){
	   return dao.buscarUnidadesFuncionales();
   }
   
   public List<Unidad> findByNombreLike(String like){
	   return dao.findByNombreLike(like);
   }
   
   public Map<Integer,String> getUnidadMap() {
      List<Unidad> lstU = getDao().findAll();
      Map<Integer,String> mapUnidad = new LinkedHashMap<Integer,String>();

      for (Unidad objU : lstU) {
         mapUnidad.put(objU.getIdunidad(), objU.getNombre());
      }

      return mapUnidad;
   }

   public Unidad buscarObjPor(Integer iIdUnidad) {
      return dao.findByIdunidad(iIdUnidad);
   }

   public Unidad findDependenciaByIdunidad(Integer iIdUnidad){
	   return dao.findDependenciaByIdunidad(iIdUnidad);	   
   }
   
	@Transactional
	public void guardarObj(Unidad objUnidadOld, Unidad objUnidadNew, String sUsuarioSesion, String sTipoAuditoria) {
      try {
         srvAuditoria.aplicarAuditoria(objUnidadOld, objUnidadNew, sUsuarioSesion, Constantes.AUDITORIA_OPCION_GUARDAR, sTipoAuditoria);
      } catch (ClassNotFoundException e) {
         log.error(e.getMessage(), e);
      }

      objUnidadNew = dao.guardarObj(objUnidadNew);

      log.debug("Unidad guardada con ID [" + objUnidadNew.getIdunidad() + "] Nombre [" + objUnidadNew.getNombre() + "]");
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public UnidadDAO getDao() {
      return dao;
   }

   public void setDao(UnidadDAO dao) {
      this.dao = dao;
   }

   public AuditoriaService getSrvAuditoria() {
      return srvAuditoria;
   }

   public void setSrvAuditoria(AuditoriaService srvAuditoria) {
      this.srvAuditoria = srvAuditoria;
   }
}
