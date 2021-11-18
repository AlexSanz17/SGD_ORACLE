/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Tipoidentificacion;

public interface TipoidentificacionDAO {

   public List<Tipoidentificacion> findAll();

   public Tipoidentificacion buscarObjPor(String sNombre);
}
