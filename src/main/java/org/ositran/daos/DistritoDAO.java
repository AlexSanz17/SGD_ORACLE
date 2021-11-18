/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Distrito;

public interface DistritoDAO {

   public List<Distrito> buscarTodo();

   public List<Distrito> findByProvincia(Integer iIdProvincia);
   
   public Distrito findById(Integer idDistrito);

   public Distrito guardarObj(Distrito objDistrito, char cMode);
}
