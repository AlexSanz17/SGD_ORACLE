package org.ositran.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.ositran.daos.RecursoDAO;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Recurso;

public class RecursoServiceImpl implements RecursoService {

   private static Logger log = Logger.getLogger(RecursoServiceImpl.class);
   private RecursoDAO dao;
   private AuditoriaService srvAuditoria;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public RecursoServiceImpl(RecursoDAO dao) {
      setDao(dao);
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public List<Recurso> findAll() {
      return getDao().findAll();
   }

   public List<Recurso> buscarRecursosActivos(){
	   return getDao().buscarRecursosActivos();
   }
   
   public Map<String,Integer> findMapAllByCodigo() {
      List<Recurso> lstRecurso = null;
      Map<String,Integer> mapRecurso = new LinkedHashMap<String,Integer>();

      if ((lstRecurso = this.findAll()) == null) {
         log.debug("No se encontro recursos en la Base de Datos");

         return null;
      }

      log.debug("Numero de recurssos disponibles en el sistema [" + lstRecurso.size() + "]");

      for (Recurso objRecurso : lstRecurso) {
         mapRecurso.put(objRecurso.getCodigo(), 0);
      }

      return mapRecurso;
   }

   public Map<Integer,String> findMapAllByIdRecurso() {
      List<Recurso> lstRecurso = null;
      Map<Integer,String> mapRecurso = new LinkedHashMap<Integer,String>();

      if ((lstRecurso = this.findAll()) == null) {
         log.debug("No se encontro recursos en la Base de Datos");

         return null;
      }

      log.debug("Numero de recurssos disponibles en el sistema [" + lstRecurso.size() + "]");

      for (Recurso objRecurso : lstRecurso) {
         mapRecurso.put(objRecurso.getIdrecurso(), objRecurso.getNombre());
      }

      return mapRecurso;
   }

   public Map<Integer,String> findMapRecursoLeft(List<Recurso> lstRecurso) {
      Boolean bFound;
      Iterator<Entry<Integer,String>> it = null;
      Map<Integer,String> mapRecurso = findMapAllByIdRecurso();
      Map<Integer,String> mapRecursoReturn = new HashMap<Integer,String>();

      it = mapRecurso.entrySet().iterator();

      while (it.hasNext()) {
         Entry<Integer,String> entry = it.next();
         bFound = false;
         Integer iKey = entry.getKey();

         if (!lstRecurso.isEmpty()) {
            for (Recurso objRecurso : lstRecurso) {
               if (objRecurso.getIdrecurso() == iKey.intValue()) {
                  bFound = true;
                  break;
               }
            }
         }

         if (bFound == false) {
            mapRecursoReturn.put(entry.getKey(), entry.getValue());
         }
      }

      return mapRecursoReturn;
   }

   public Map<Integer,String> findMapRecursoRight(List<Recurso> lstRecurso) {
      Map<Integer,String> mapRecursoReturn = new HashMap<Integer,String>();

      for (Recurso objRecurso : lstRecurso) {
         mapRecursoReturn.put(objRecurso.getIdrecurso(), objRecurso.getNombre());
      }

      return mapRecursoReturn;
   }

   public Recurso findByIdRecurso(Integer iIdRecurso) {
      return getDao().findByIdRecurso(iIdRecurso);
   }

   @Transactional
   public void saveRecurso(Recurso objRecursoOld, Recurso objRecursoNew, String sUsuarioSesion, String sTipoAuditoria) {
      try {
         srvAuditoria.aplicarAuditoria(objRecursoOld, objRecursoNew, sUsuarioSesion, Constantes.AUDITORIA_OPCION_GUARDAR, sTipoAuditoria);
      } catch (ClassNotFoundException e) {
         log.error(e.getMessage(), e);
      }

      objRecursoNew = dao.saveRecurso(objRecursoNew);

      log.debug("Recurso guardado con ID [" + objRecursoNew.getIdrecurso() + "] Nombre [" + objRecursoNew.getNombre() + "] Codigo [" + objRecursoNew.getCodigo() + "]");
   }
   
   @Transactional
   public void updateEstadoRecurso(Integer idRecurso, Character estado){
	   if(idRecurso==null || estado==null){
		   log.error("fallo en update Estado Recurso");
		   return;
	   }

    Recurso obj= this.findByIdRecurso(idRecurso);
    obj.setEstado(estado);
    obj= getDao().saveRecurso(obj); 
    log.debug("Estado del Recurso con ID [" + obj.getIdrecurso()+ "] actualizado a [" + obj.getEstado() + "]");
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public RecursoDAO getDao() {
      return dao;
   }

   public void setDao(RecursoDAO dao) {
      this.dao = dao;
   }

   public AuditoriaService getSrvAuditoria() {
      return srvAuditoria;
   }

   public void setSrvAuditoria(AuditoriaService srvAuditoria) {
      this.srvAuditoria = srvAuditoria;
   }
}
