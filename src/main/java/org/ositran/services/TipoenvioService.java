package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Tipoenvio;


public interface TipoenvioService {

   public Tipoenvio findbyNombre(String nombre);
   
   public Tipoenvio findbyId(int Id);
   
   public List<Tipoenvio> findAll();
   
}
