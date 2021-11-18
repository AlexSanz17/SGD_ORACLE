package org.ositran.services;

import java.util.List;
import java.util.Map;

import com.btg.ositran.siged.domain.Unidad;

public interface UnidadService {

   public List<Unidad> obtenerLstTodo();

   public List<Unidad> buscarUnidadesFuncionales();
   
   List<Unidad> findByNombreLike(String like);
   
   public Map<Integer,String> getUnidadMap();

   public Unidad buscarObjPor(Integer iIdUnidad);
   
   public Unidad findDependenciaByIdunidad(Integer idunidad);

   public void guardarObj(Unidad objUnidadOld, Unidad objUnidadNew, String sUsuarioSesion, String sTipoAuditoria);
}
