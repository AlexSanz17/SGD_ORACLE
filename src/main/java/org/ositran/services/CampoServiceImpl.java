/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import org.ositran.daos.CampoDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Campo;

public class CampoServiceImpl implements CampoService {

   private CampoDAO dao;

   public CampoServiceImpl(CampoDAO dao) {
      this.dao = dao;
   }

   @Transactional
   public Campo guardarObj(Campo objCampo) {
      return dao.guardarObj(objCampo);
   }

   public CampoDAO getDao() {
      return dao;
   }

   public void setDao(CampoDAO dao) {
      this.dao = dao;
   }
}
