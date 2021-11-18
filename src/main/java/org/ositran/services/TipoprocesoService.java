package org.ositran.services;

import java.util.Map;

import com.btg.ositran.siged.domain.Tipoproceso;

public interface TipoprocesoService {

   public Map<Integer,String> getTipoProcesoMap();

   public Tipoproceso buscarObjPor(String sNombre);

   public Tipoproceso findById(Integer iIdTipoProceso);
}
