package org.ositran.services;

import java.util.List;
import java.util.Map;

import com.btg.ositran.siged.domain.Recurso;

public interface RecursoService {

   public List<Recurso> findAll();
   
   public List<Recurso> buscarRecursosActivos();

   public Map<String,Integer> findMapAllByCodigo();

   public Map<Integer,String> findMapAllByIdRecurso();

   public Map<Integer,String> findMapRecursoLeft(List<Recurso> lstRecurso);

   public Map<Integer,String> findMapRecursoRight(List<Recurso> lstRecurso);

   public Recurso findByIdRecurso(Integer iIdRecurso);

   public void saveRecurso(Recurso objRecursoOld, Recurso objRecursoNew, String sUsuarioSesion, String sTipoAuditoria);
   
   public void updateEstadoRecurso(Integer id, Character estado);
   
}
