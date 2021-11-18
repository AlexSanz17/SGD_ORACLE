package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Vocal;

public interface VocalService {

   public Vocal findById(Integer idVocal);
   
   public List<Vocal> findAll();
}
