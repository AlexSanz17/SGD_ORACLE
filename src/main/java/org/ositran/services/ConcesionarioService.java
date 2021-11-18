/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;
import java.util.Map;

import com.btg.ositran.siged.domain.Concesionario;

public interface ConcesionarioService {
   public Concesionario findByIdConcesionario(Integer iId) throws RuntimeException;
   public Map getConcesionarioList() throws RuntimeException;
   public List<Concesionario> findAll();
   public Concesionario findByRUC(String strRUC);
}
