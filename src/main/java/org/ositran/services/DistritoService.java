/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Distrito;

public interface DistritoService {

   public List<Distrito> findAll();

   public Distrito findById(Integer iIdDistrito);

   public Distrito guardarObj(Distrito objDistrito, char cMode);

   public List<Distrito> findLstBy(Integer iIdProvincia);
}
