/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Campo;

/**
 *
 * @author Himizu
 */
public interface CampoDAO {

    public List<Campo> findAll() ; 
   public Campo guardarObj(Campo objCampo);
}
