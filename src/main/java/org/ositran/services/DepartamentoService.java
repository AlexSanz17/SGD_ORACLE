/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;
import java.util.Map;

import com.btg.ositran.siged.domain.Departamento;

public interface DepartamentoService {

   public List<Departamento> findAll();

   public Map<Integer, String> findMapAll();

   public Departamento findObjById(Integer iIdDepartamento);

   public Departamento guardarObj(Departamento objDepartamento, char cMode);
}
