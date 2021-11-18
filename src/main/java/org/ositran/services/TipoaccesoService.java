package org.ositran.services;

import java.util.Map;

import com.btg.ositran.siged.domain.Tipoacceso;

public interface TipoaccesoService {

   public Map<Integer,String> getMapTipoAcceso();

   public Tipoacceso buscarObjPor(String sCodigo);

   public Tipoacceso buscarObjPorID(Integer iIdTipoAcceso);
}
