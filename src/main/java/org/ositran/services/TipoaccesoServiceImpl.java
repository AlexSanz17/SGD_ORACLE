package org.ositran.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.ositran.daos.TipoaccesoDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Tipoacceso;
import com.btg.ositran.siged.domain.Tipodocumento;

public class TipoaccesoServiceImpl implements TipoaccesoService {

   private TipoaccesoDAO dao;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public TipoaccesoServiceImpl(TipoaccesoDAO dao) {
      setDao(dao);
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public Map<Integer,String> getMapTipoAcceso() {
      Map<Integer,String> mapTipoAcceso = new LinkedHashMap<Integer,String>();
      List<Tipoacceso> lstTipoAcceso = getDao().findAll();

      for (Tipoacceso objTipoAcceso : lstTipoAcceso) {
         mapTipoAcceso.put(objTipoAcceso.getIdtipoacceso(), objTipoAcceso.getNombre());
      }

      return mapTipoAcceso;
   }

   public Tipoacceso buscarObjPor(String sCodigo) {
      return dao.buscarObjPor(sCodigo);
   }
   
   public List<Tipodocumento> findByAllTipoDocumentoPIDE(){
       return null;
   }

   public Tipoacceso buscarObjPorID(Integer iIdTipoAcceso) {
      return dao.buscarObjPorID(iIdTipoAcceso);
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public TipoaccesoDAO getDao() {
      return dao;
   }

   public void setDao(TipoaccesoDAO dao) {
      this.dao = dao;
   }
}
