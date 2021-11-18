package org.ositran.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.ositran.daos.SedeDAO;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Sede;

public class SedeServiceImpl implements SedeService {

   private static Logger log = Logger.getLogger(SedeServiceImpl.class);
   private SedeDAO dao;
   private AuditoriaService srvAuditoria;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public SedeServiceImpl(SedeDAO dao) {
      setDao(dao);
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public List<Sede> obtenerTodo() {
      return dao.buscarTodo();
   }

   public Map<Integer,String> obtenerMapTodo() {
      Map<Integer,String> mapSede = new LinkedHashMap<Integer,String>();
      List<Sede> lstSede = dao.buscarTodo();

      for (Sede objSede : lstSede) {
         mapSede.put(objSede.getIdsede(), objSede.getNombre());
      }

      return mapSede;
   }

   public Sede buscarPorId(Integer iIdSede) {
      return dao.buscarPorId(iIdSede);
   }

	@Transactional
	public void guardarObj(Sede objSedeOld, Sede objSedeNew, String sUsuarioSesion, String sTipoAuditoria) {
      try {
         srvAuditoria.aplicarAuditoria(objSedeOld, objSedeNew, sUsuarioSesion, Constantes.AUDITORIA_OPCION_GUARDAR, sTipoAuditoria);
      } catch (ClassNotFoundException e) {
         log.error(e.getMessage(), e);
      }

      objSedeNew = dao.guardarObj(objSedeNew);

      log.debug("Sede guardado con ID [" + objSedeNew.getIdsede() + "] Nombre [" + objSedeNew.getNombre() + "]");
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public SedeDAO getDao() {
      return dao;
   }

   public void setDao(SedeDAO dao) {
      this.dao = dao;
   }

   public AuditoriaService getSrvAuditoria() {
      return srvAuditoria;
   }

   public void setSrvAuditoria(AuditoriaService srvAuditoria) {
      this.srvAuditoria = srvAuditoria;
   }
}
