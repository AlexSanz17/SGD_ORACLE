/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;
import java.util.List;

import com.btg.ositran.siged.domain.Tiporesultado;

/**
 *
 * @author Usuario
 */
public interface  TiporesultadoDAO {
   public Tiporesultado findByIdTiporesultado(Integer idTiporesultado);
   public List<Tiporesultado> findAll();

}
