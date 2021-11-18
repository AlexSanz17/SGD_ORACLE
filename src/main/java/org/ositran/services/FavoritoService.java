package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Favorito;

public interface FavoritoService {

   public Favorito findObjectBy(Integer idPropietario, Integer idUnidad, Integer IdCargo, Integer idContacto, Character tipoContacto, Character estado);

   public Favorito saveFavorito(Integer idPropietario, Integer idUnidadContacto, Integer idCargoContacto, Integer idContacto, Character tipoContacto);

   public List<Favorito> findLstBy(Integer idPropietario, Character estado, String mode, Integer idunidad, String jefe, Integer idfuncion);
}
