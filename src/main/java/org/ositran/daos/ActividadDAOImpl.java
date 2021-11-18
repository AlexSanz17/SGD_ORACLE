/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Actividad;
import com.btg.ositran.siged.domain.Recurso;

@Repository
public class ActividadDAOImpl implements ActividadDAO {

   private static Logger _log = Logger.getLogger(ActividadDAOImpl.class);
   private EntityManager em;

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public Actividad buscarObjPor(String sCodigo) {
      Actividad objActividad = null;

      try {
         objActividad = (Actividad) em.createNamedQuery("Actividad.findByCodigo")
                                      .setParameter("codigo", sCodigo)
                                      .getSingleResult();
      } catch (NoResultException e) {
         _log.error(e.getMessage(), e);
      }

      return objActividad;
   }

   public Actividad buscarObjPorId(Integer iIdActividad) {
      Actividad objActividad = null;

      try {
         objActividad = em.find(Actividad.class, iIdActividad);
         em.refresh(objActividad);
      } catch (EntityNotFoundException e) {
         _log.error(e.getMessage(), e);
      }

      return objActividad;
   }

   public Map<String,Integer> obtenerMapRecursoXActividad(Integer iIdActividad) {
      Actividad objActividad = null;
      List<Recurso> lstRecursoXActividad = null;
      Map<String,Integer> mapRecursoXActividad = new HashMap<String,Integer>();

      objActividad = this.buscarObjPorId(iIdActividad);

      if (objActividad == null) {
         _log.debug("No se encontro Actividad con ID [" + iIdActividad + "]");
      } else {
         _log.debug("Actividad encontrada con ID [" + objActividad.getIdActividad() + "] Nombre [" + objActividad.getNombre() + "]");
         lstRecursoXActividad = objActividad.getRecursos();
         
         if (lstRecursoXActividad == null) {
            _log.debug("No se encontro recursos para la actividad");
         } else {
            _log.debug("Numero de recursos asignados a la actividad [" + lstRecursoXActividad.size() + "]");

            for (Recurso objRecurso : lstRecursoXActividad) {
               mapRecursoXActividad.put(objRecurso.getCodigo(), 1);
            }
         }
      }

      return mapRecursoXActividad;
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public EntityManager getEm() {
      return em;
   }

   @PersistenceContext(unitName="sigedPU")
   public void setEm(EntityManager em) {
      this.em = em;
   }
}
