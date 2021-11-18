package org.ositran.services;

import java.util.List;
import java.util.Map;

import com.btg.ositran.siged.domain.Grupoproceso;

public interface GrupoprocesoService {

   public Grupoproceso buscarObjPor(String sCodigo);

   public Grupoproceso buscarObjPorId(Integer iIdGrupoproceso);

   public Grupoproceso guardarObj(Grupoproceso objGrupoProceso);

   public List<Grupoproceso> findAll();

   public Map<Integer, String> getMapAll();
}
