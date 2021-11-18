/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.AmbitoEnvio;

public interface AmbitoenvioDAO {

   public AmbitoEnvio findId(Integer id);
   
   public List<AmbitoEnvio> findAll();
   

}
