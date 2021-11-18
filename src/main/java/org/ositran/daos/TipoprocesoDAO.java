/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Tipoproceso;

public interface TipoprocesoDAO {

   public List<Tipoproceso> findAll();

   public Tipoproceso buscarObjPor(String sNombre);

   public Tipoproceso findById(Integer iIdTipoProceso);
}
