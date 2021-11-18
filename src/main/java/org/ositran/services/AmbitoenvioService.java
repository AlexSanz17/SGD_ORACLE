/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.AmbitoEnvio;


public interface AmbitoenvioService {
   
   public AmbitoEnvio findId(int Id);
   
   public List<AmbitoEnvio> findAll();
   
}
