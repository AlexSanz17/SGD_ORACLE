/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Modulo;

public interface ModuloDAO {

   public List<Modulo> findAll();
   
   public List<Modulo> findModuloActivos();

   public Modulo findByIdModulo(Integer iIdModulo);

   public Modulo saveModulo(Modulo objModulo);
}
