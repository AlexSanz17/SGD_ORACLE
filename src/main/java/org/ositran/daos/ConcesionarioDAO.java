/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Concesionario;

public interface ConcesionarioDAO {
   public Concesionario findByIdConcesionario(Integer iId);
   public Concesionario findByRUC(String strRUC);
   public List<Concesionario> findAll();
}
