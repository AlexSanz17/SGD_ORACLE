/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.ositran.daos.DepartamentoDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Departamento;

public class DepartamentoServiceImpl implements DepartamentoService {

   private static Logger log = Logger.getLogger(DepartamentoServiceImpl.class);
   private DepartamentoDAO dao;

   public DepartamentoServiceImpl() {
      super();
   }

   public DepartamentoServiceImpl(DepartamentoDAO dao) {
      setDao(dao);
   }

   public List<Departamento> findAll() {
      return dao.findAll();
   }

   public Map<Integer, String> findMapAll() {
      Map<Integer, String> mapDepartamento = new LinkedHashMap<Integer, String>();
      List<Departamento> lstDepartamento = this.findAll();

      for (Departamento objDepartamento : lstDepartamento) {
         mapDepartamento.put(objDepartamento.getIddepartamento(), objDepartamento.getNombre());
      }

      return mapDepartamento;
   }

   public Departamento findObjById(Integer iIdDepartamento) {
      try {
         return dao.findObjById(iIdDepartamento);
      } catch (NoResultException nre) {
         log.warn("No se encontro ningun departamento con ID [" + iIdDepartamento + "]");

         return null;
      }
   }

   @Transactional
   public Departamento guardarObj(Departamento objDepartamento, char cMode) {
      return dao.guardarObj(objDepartamento, cMode);
   }

   ////////////////////////
   // Getters and Setters
   ////////////////////////
   public DepartamentoDAO getDao() {
      return dao;
   }

   public void setDao(DepartamentoDAO dao) {
      this.dao = dao;
   }
}
