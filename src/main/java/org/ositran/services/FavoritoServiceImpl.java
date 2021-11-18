package org.ositran.services;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.daos.FavoritoDAO;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Favorito;

public class FavoritoServiceImpl implements FavoritoService {

   private static Logger log = Logger.getLogger(FavoritoServiceImpl.class);
   private FavoritoDAO dao;

   /*
    * Constructors
    */
   public FavoritoServiceImpl(FavoritoDAO dao) {
      this.dao = dao;
   }

   /*
    * Methods
    */
   @Override
   public Favorito findObjectBy(Integer idPropietario, Integer idUnidad, Integer idCargo, Integer idContacto, Character tipoContacto, Character estado) {
     
       if (idPropietario == null || idContacto == null || tipoContacto == null || estado == null) {
         log.error("No se recibieron correctamente los parametros");

         return null;
      }

      return dao.findObjectBy(idPropietario, idUnidad, idCargo, idContacto, tipoContacto, estado);
   }

   @Override
   @Transactional
   public Favorito saveFavorito(Integer idPropietario, Integer idUnidadContacto, Integer idCargoContacto, Integer idContacto, Character tipoContacto) {
       
       if (idPropietario == null || idContacto == null || tipoContacto == null) {
         log.error("No se recibieron correctamente los parametros, no se grabara nada");

         return null;
      }

      Favorito favorito = new Favorito();
      favorito.setPropietario(idPropietario);
      favorito.setContacto(idContacto);
      favorito.setTipoContacto(tipoContacto);
      favorito.setFechaCreacion(new Date());
      favorito.setEstado(Constantes.ESTADO_ACTIVO);
      favorito.setIdunidadcontacto(idUnidadContacto);
      favorito.setIdcargocontacto(idCargoContacto);
      dao.saveFavorito(favorito);
      log.info("favorito guardado id [" + favorito.getIdFavorito() + "] propietario [" + favorito.getPropietario() + "] contacto [" + favorito.getContacto() + "] tipoContacto [" + favorito.getTipoContacto() + "]");
      return favorito;
   }

   @Override
   public List<Favorito> findLstBy(Integer idPropietario, Character estado, String mode, Integer idunidad, String jefe, Integer idfuncion) {
      if (log.isDebugEnabled()) {
         log.debug("Buscando favoritos del propietario [" + idPropietario + "] estado [" + estado + "] mode [" + mode + "]");
      }

      if (idPropietario == null || estado == null) {
         return null;
      }

      return dao.findLstBy(idPropietario, estado, mode,idunidad, jefe, idfuncion);
   }

   /*
    * Getters & Setters
    */
   public FavoritoDAO getDao() {
      return dao;
   }

   public void setDao(FavoritoDAO dao) {
      this.dao = dao;
   }
}
