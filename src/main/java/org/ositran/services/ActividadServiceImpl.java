/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.Map;
import org.ositran.daos.ActividadDAO;

import com.btg.ositran.siged.domain.Actividad;

public class ActividadServiceImpl implements ActividadService {

   private ActividadDAO actividadDAO;

   public Actividad findByCodigo(String sCodigo) {
      return getActividadDAO().buscarObjPor(sCodigo);
   }

   public Map<String,Integer> getMapRecursoXActividad(Integer idActividad) {
      return actividadDAO.obtenerMapRecursoXActividad(idActividad);
   }

   public ActividadDAO getActividadDAO() {
      return actividadDAO;
   }

   public void setActividadDAO(ActividadDAO actividadDAO) {
      this.actividadDAO = actividadDAO;
   }
}
