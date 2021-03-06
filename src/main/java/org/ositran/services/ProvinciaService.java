package org.ositran.services;

import java.util.List;
import java.util.Map;

import com.btg.ositran.siged.domain.Provincia;

public interface ProvinciaService {

   public List<Provincia> findAll();

   public Map<Integer, String> findMapAll();

   public Provincia findObjById(Integer iIdProvincia);

   public Provincia guardarObj(Provincia objProvincia, char cMode);

   public List<Provincia> findLstBy(Integer iIdDepartamento);
}
