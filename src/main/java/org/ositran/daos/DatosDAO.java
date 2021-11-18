/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.Datos;

public interface DatosDAO {

   public Datos saveDatos(Datos Obdt);
   
   public Datos findId(int Id);

}
