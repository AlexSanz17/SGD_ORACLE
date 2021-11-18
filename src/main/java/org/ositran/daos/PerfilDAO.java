/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Perfil;

public interface PerfilDAO {

   public List<Perfil> findAll();

   public Perfil findByIdPerfil(Integer iIdPerfil);

   public Perfil savePerfil(Perfil objPerfil);
}
