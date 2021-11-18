/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Grupoproceso;

public interface GrupoprocesoDAO {

   public Grupoproceso buscarObjPor(String sCodigo);

   public Grupoproceso buscarObjPorId(Integer iIdGrupoproceso);

   public Grupoproceso guardarObj(Grupoproceso objGrupoProceso);

   public List<Grupoproceso> findAll();
}
