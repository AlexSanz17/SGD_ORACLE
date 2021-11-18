/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Favorito;

public interface FavoritoDAO {

   public Favorito findObjectBy(Integer idPropietario, Integer idUnidadContacto, Integer idCargoContacto, Integer idContacto, Character tipoContacto, Character estado);

   public List<Favorito> findLstBy(Integer idPropietario, Character estado, String mode, Integer idunidad, String jefe, Integer idfuncion);

   public void saveFavorito(Favorito favorito);
}
