/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Tipoenvio;

public interface TipoenvioDAO {

   public Tipoenvio findbyNombre(String nombre);
   
   public List<Tipoenvio> findAll();
   
   public Tipoenvio findbyId(int Id);

}
