/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.TipoLegajo;
import java.util.List;
import org.ositran.daos.TipoLegajoDAO;
/**
 *
 * @author jbengoa
 */
public class TipoLegajoServiceImpl implements TipoLegajoService{
     private TipoLegajoDAO tipoLegajoDAO;
  
     public List<TipoLegajo> findTipoLegajo(Integer iWithoutStor, Integer idUnidad){
         if (iWithoutStor == 0) {
             return tipoLegajoDAO.findAllTipoLegajoActive(idUnidad);
         } else {
             return tipoLegajoDAO.findAllTipoLegajo(idUnidad);
         }
     }
     
      public List<TipoLegajo> findTipoLegajoBusqueda(){
             return tipoLegajoDAO.findTipoLegajoBusqueda();
     }
     
     public TipoLegajoDAO getTipoLegajoDAO() {
        return tipoLegajoDAO;
     }

     public void setTipoLegajoDAO(TipoLegajoDAO tipoLegajoDAO) {
        this.tipoLegajoDAO = tipoLegajoDAO;
     }
}
