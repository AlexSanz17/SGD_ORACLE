/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.Map;

import com.btg.ositran.siged.domain.Actividad;

public interface ActividadDAO {

   public Actividad buscarObjPor(String sCodigo);

   public Actividad buscarObjPorId(Integer iIdActividad);

   public Map<String,Integer> obtenerMapRecursoXActividad(Integer iIdActividad);
}
