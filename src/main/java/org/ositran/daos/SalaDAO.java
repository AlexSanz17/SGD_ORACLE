/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Sala;

public interface SalaDAO {
   public List<Sala> findAll();
   public Sala findByIdSala(Integer iIdSala);
}
