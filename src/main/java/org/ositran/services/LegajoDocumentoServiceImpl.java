/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.LegajoDocumento;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Legajo;
import java.util.Date;
import java.util.List;
import org.ositran.daos.LegajoDAO;
import org.ositran.daos.LegajoDocumentoDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jbengoa
 */
public class LegajoDocumentoServiceImpl implements LegajoDocumentoService{
     private LegajoDocumentoDAO legajoDocumentoDAO;
     private LegajoDAO legajoDAO;

    public LegajoDAO getLegajoDAO() {
        return legajoDAO;
    }

    public void setLegajoDAO(LegajoDAO legajoDAO) {
        this.legajoDAO = legajoDAO;
    }

    public LegajoDocumentoDAO getLegajoDocumentoDAO() {
        return legajoDocumentoDAO;
    }

    public void setLegajoDocumentoDAO(LegajoDocumentoDAO legajoDocumentoDAO) {
        this.legajoDocumentoDAO = legajoDocumentoDAO;
    }
    
     public LegajoDocumento findLegajoDocumento(LegajoDocumento legajoDocumento){
         return legajoDocumentoDAO.findLegajoDocumento(legajoDocumento);
     }
     
     public List<LegajoDocumento> findAllDocumentos(LegajoDocumento legajoDocumento){
         return legajoDocumentoDAO.findAllDocumentos(legajoDocumento);
     }

    @Transactional
     public LegajoDocumento saveLegajoDocumento(LegajoDocumento legajoDocumento){
          legajoDocumento = legajoDocumentoDAO.saveLegajoDocumento(legajoDocumento);
          List<LegajoDocumento>  lst = legajoDocumentoDAO.findAllDocumentos(legajoDocumento);
          
          if (lst== null || lst.size()==0){
              Legajo legajo = new Legajo();
              legajo.setIdLegajo(legajoDocumento.getIdLegajo());
              legajo.setEstado("A");
              legajo = legajoDAO.findByIdLegajo(legajo);
              legajo.setEstado("I");
              legajo.setFechaModificacion(new Date());
              legajo.setUsuarioModificacion(legajoDocumento.getUsuarioModificacion());
              legajoDAO.saveLegajo(legajo);
          }
          
          return legajoDocumento;
     }
     
      public List<LegajoDocumento> findDocumento(LegajoDocumento legajoDocumento, Usuario usuario){
          return legajoDocumentoDAO.findDocumento(legajoDocumento, usuario);
      }
}
