/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Submotivo;

public interface SubmotivoDAO {

   public Submotivo findByIdSubmotivo(Integer iIdSubmotivo);
   public List<Submotivo> findByMotivo(Integer iIdMotivo);
   public Submotivo saveMotivo(Submotivo submotivo) ;
}
