package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Tipoidentificacion;

public interface TipoidentificacionService {

   public List<Tipoidentificacion> getTipoIdentificacionMap();

   public Tipoidentificacion buscarObjPor(String sNombre);
}
