/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.Cargo;


public interface CargoDAO {

   public Cargo saveDatos(Cargo Obdt);
   
   public Cargo findkey(int Id);

}
