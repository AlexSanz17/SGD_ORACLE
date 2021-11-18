package org.ositran.services;

import java.util.List;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.ositran.daos.TipoidentificacionDAO;

import com.btg.ositran.siged.domain.Tipoidentificacion;

public class TipoidentificacionServiceImpl implements TipoidentificacionService {

   private static Logger _log = Logger.getLogger(TipoidentificacionServiceImpl.class);
   private TipoidentificacionDAO dao;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public TipoidentificacionServiceImpl(TipoidentificacionDAO dao) {
      this.dao = dao;
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public List<Tipoidentificacion> getTipoIdentificacionMap() {
      //Map mapAux = new HashMap();
      List<Tipoidentificacion> lstTI = getDao().findAll();

      //for (Tipoidentificacion objTI : lstTI) {
      // mapAux.put(objTI.getIdtipoidentificacion(), objTI.getNombre());
      //}

      //return mapAux;
      return lstTI;
   }

   public Tipoidentificacion buscarObjPor(String sNombre) {
      try {
         return dao.buscarObjPor(sNombre);
      } catch (NoResultException nre) {
         _log.warn(nre.getMessage());

         return null;
      }

   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public TipoidentificacionDAO getDao() {
      return dao;
   }

   public void setDao(TipoidentificacionDAO dao) {
      this.dao = dao;
   }
}
