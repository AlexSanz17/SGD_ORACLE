/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.Resolucionjaru;

/**
 *
 * @author Usuario
 */
public interface ResolucionjaruDAO {

   public Resolucionjaru saveOrUpdateResolucionJaru(Resolucionjaru resolucionjaru);
   
   public Resolucionjaru findByIdExpedienteStor(Integer idExpediente);

}
