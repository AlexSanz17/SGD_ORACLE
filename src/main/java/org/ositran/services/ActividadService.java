/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.Map;

import com.btg.ositran.siged.domain.Actividad;

public interface ActividadService {

   public Actividad findByCodigo(String sCodigo);

   public Map<String,Integer> getMapRecursoXActividad(Integer idActividad);
}
