/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Tipoacceso;

public interface TipoaccesoDAO {

   public List<Tipoacceso> findAll();

   public Tipoacceso buscarObjPor(String sCodigo);

   public Tipoacceso buscarObjPorID(Integer iIdTipoAcceso);
}
