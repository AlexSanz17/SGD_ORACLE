/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Empresadestino;


public interface EmpresadestinoDAO {

   public Empresadestino findCod(String codigo);
   
   public List<Empresadestino> findAll();
   
   public Empresadestino findkey(int Id);

}
