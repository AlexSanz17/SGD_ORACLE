/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Vocal;

public interface VocalDAO {

	   public Vocal findById(Integer idVocal);
	   
	   public List<Vocal> findAll();
}
