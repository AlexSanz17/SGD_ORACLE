package org.ositran.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.ositran.daos.EstadoDAO;

import com.btg.ositran.siged.domain.Estado;

public class EstadoServiceImpl implements EstadoService {

   private static Logger log = Logger.getLogger(EstadoServiceImpl.class);
   private EstadoDAO dao;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public EstadoServiceImpl(EstadoDAO dao) {
      this.dao = dao;
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public List<Estado> findAll(){
	   return dao.findAll();
   }
   
   public Estado findByIdestado(Integer idEstado){
	   return dao.findByIdestado(idEstado);
   }
   
   public Estado findByCodigo(String codigoEstado){
           return dao.findByCodigo(codigoEstado);
   }
   
   public List<Estado> findByTipo(String tipoEstado){
	   return dao.findByTipo(tipoEstado);
   }
   
   public List<Estado> findByIdProceso(Integer idProceso){
	   return dao.findByIdProceso(idProceso);
   }
   
   public void saveObject(Estado estado){
	   dao.saveObject(estado);
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public EstadoDAO getDao() {
      return dao;
   }

   public void setDao(EstadoDAO dao) {
      this.dao = dao;
   }
}
