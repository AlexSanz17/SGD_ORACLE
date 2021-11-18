/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Recurso;

public interface RecursoDAO {

   List<Recurso> findAll();
   
   List<Recurso> buscarRecursosActivos();

   Recurso findByIdRecurso(Integer iIdRecurso);

   Recurso saveRecurso(Recurso objRecurso);
}
