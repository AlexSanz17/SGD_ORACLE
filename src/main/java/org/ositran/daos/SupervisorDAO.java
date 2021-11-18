/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Supervisor;

public interface SupervisorDAO {

   public Supervisor findByCriteria(Integer iIdDoc, String strNombre);

   public Supervisor findById(Integer idArchivo);

   public Supervisor saveSupervisor(Supervisor objA);
   
   public List<Supervisor> findAll();

}
