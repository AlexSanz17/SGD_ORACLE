/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.Funcion;
import org.ositran.daos.FuncionDAO;

/**
 *
 * @author consultor_jti15
 */
public class FuncionServiceImpl implements FuncionService{
     private FuncionDAO funcionDAO;

    public FuncionDAO getFuncionDAO() {
        return funcionDAO;
    }

    public void setFuncionDAO(FuncionDAO funcionDAO) {
        this.funcionDAO = funcionDAO;
    }

   @Override
   public Funcion findByIdFuncion(Integer idFuncion) {
      return funcionDAO.findByIdFuncion(idFuncion);
   }
      
  
}
