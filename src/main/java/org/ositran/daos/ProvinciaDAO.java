/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Provincia;

public interface ProvinciaDAO {

   public List<Provincia> buscarTodo();

   public List<Provincia> findByDepartamento(Integer iIdDepartamento);

   public Provincia findObjById(Integer iIdProvincia);

   public Provincia guardarObj(Provincia objProvincia, char cMode);
}
