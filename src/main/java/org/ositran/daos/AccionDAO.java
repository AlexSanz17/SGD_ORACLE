/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.Accion;

public interface AccionDAO {

   public Accion buscarObjPor(String sNombre);

   public Accion findByNombre(String strA);
}
