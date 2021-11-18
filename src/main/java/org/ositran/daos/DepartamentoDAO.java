/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Departamento;

public interface DepartamentoDAO {

   public List<Departamento> findAll();

   public Departamento lisNomdiafestivo(String Nombreregion);

   public Departamento findObjById(Integer iIdDepartamento);

   public Departamento guardarObj(Departamento objDepartamento, char cMode);
}
