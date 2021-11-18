package org.ositran.services;

import java.util.List;

import javax.persistence.NoResultException;
import org.directwebremoting.util.Logger;
import org.ositran.daos.NumeracionDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Numeracion;
import com.btg.ositran.siged.domain.Unidad;

public class NumeracionServiceImpl implements NumeracionService {

   private static Logger _log = Logger.getLogger(NumeracionServiceImpl.class);
   private NumeracionDAO dao;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public NumeracionServiceImpl(NumeracionDAO dao) {
      this.dao = dao;
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public Numeracion findById(Integer iIdUnidad, Integer iIdTipoDocumento) throws NoResultException{
         return dao.findByIdNumeracion(iIdUnidad, iIdTipoDocumento);
   }

   public Numeracion findByIdbyUnidad(Unidad iIdUnidad, Integer iIdTipoDocumento) {
	     return  dao.findByIdNumeracionbyUnidad(iIdUnidad, iIdTipoDocumento);
   }

   @Transactional
   public Numeracion actualizarObj(Numeracion objNumeracion) {
      return dao.actualizarObj(objNumeracion);
   }

   @Transactional
   public Numeracion guardarObj(Numeracion objNumeracion) {
      return dao.guardarObj(objNumeracion);
   }

   public String guardarObjProcedure(Numeracion objNumeracion, int area_remitente, int idUsuario) throws Exception {
	   try{
	      return dao.guardarObjProcedure(objNumeracion, area_remitente, idUsuario);
	   }catch(Exception e){
		   throw e;
	   }
   }



   public List<Numeracion> findAll() {
      return dao.findAll();
   }

   public List<Numeracion> findAllUnidadAndTipoDoc(Integer idunidad,Integer idtipodoc){
	   return dao.findAllUnidadAndTipoDoc(idunidad, idtipodoc);
   }

   public Numeracion findByIdNumeracion(Integer idUnidad, Integer idTipoDocumento) {
      return dao.findByIdNumeracion(idUnidad, idTipoDocumento);
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public NumeracionDAO getDao() {
      return dao;
   }

   public void setDao(NumeracionDAO dao) {
      this.dao = dao;
   }
   
   public Numeracion findByIdNumeracionbyUnidadbyAnioFiscal(Unidad idUnidad, Integer IdTipoDocumento, Integer AnioFiscal) {
       return dao.findByIdNumeracionbyUnidadbyAnioFiscal(idUnidad, IdTipoDocumento, AnioFiscal);
   }
}
