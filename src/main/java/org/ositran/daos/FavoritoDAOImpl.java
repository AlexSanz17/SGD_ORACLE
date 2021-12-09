/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Favorito;

@Repository
public class FavoritoDAOImpl implements FavoritoDAO {

   private static Logger log = Logger.getLogger(FavoritoDAOImpl.class);
   private EntityManager em;

   /*
    * Methods
    */
   @Override
   public Favorito findObjectBy(Integer idPropietario, Integer idUnidadContacto, Integer idCargoContacto,Integer idContacto, Character tipoContacto, Character estado) {
      try {
         return (Favorito) em.createNamedQuery("Favorito.findByPropietarioContactoTipocontactoEstado")
                             .setParameter("propietario", idPropietario)
                             .setParameter("idunidadcontacto", idUnidadContacto)
                             .setParameter("idcargocontacto", idCargoContacto)
                             .setParameter("contacto", idContacto)
                             .setParameter("tipocontacto", tipoContacto)
                             .setParameter("estado", estado)
                             .getSingleResult();
      } catch (NoResultException nre) {
         log.warn("No se encontro registro con propietario [" + idPropietario + "] contacto [" + idContacto + "] tipoContacto [" + tipoContacto + "] estado [" + estado + "]");

         return null;
      } catch (NonUniqueResultException nure) {
         log.warn("Se encontro mas de un registro con propietario [" + idPropietario + "] contacto [" + idContacto + "] tipoContacto [" + tipoContacto + "] estado [" + estado + "]");

         return null;
      }
   }

   @Override
   public List<Favorito> findLstBy(Integer idPropietario, Character estado, String mode, Integer idunidad , String jefe, Integer idfuncion) {
      List<Favorito> lstFavorito = new ArrayList<Favorito>();
      
      try {
         StringBuilder sbQuery = new StringBuilder();
         sbQuery.append("SELECT x.id, x.nombres || ' ' || x.apellidos || ' ['  || x.nombre  || '][' || x.siglaunidad  || ']'  label , x.idusuario,x.idunidad, x.idfuncion, x.nombre FROM  " +
                        "   (select u.idusuario || '-' || u.idunidad || '-' || u.idfuncion as id, u.nombres, u.apellidos, u.idusuario, u.idunidad, u.idfuncion, f.nombre , xx.siglaunidad " +
                        "        from usuario u, funcion f, unidad xx " +
                        "             where " +
                        "                 u.estado = 'A' and " +
                        "                 u.idunidad = xx.idunidad  and " +
                        "                 U.USUARIOFINAL in ('S') and f.idfuncion =  u.idfuncion and FNC_USUARIO_VALIDO(U.IDUNIDAD, F.JEFE, :idunidad, :idjefe, u.idfuncion, :idfuncion)>0 " +
                        "         union  " +
                        "     select uu.idusuario  || '-' || uu.idunidad || '-' || uu.idfuncion as id, u.nombres, u.apellidos, uu.idusuario, UU.IDUNIDAD, UU.IDFUNCION, f1.nombre, xx.siglaunidad  " +
                        "        from usuarioxunidadxfuncion uu, usuario u , funcion f1, unidad xx " +
                        "              where " +
                        "                  uu.idusuario = u.idusuario and " +
                        "                  uu.idunidad = xx.idunidad     and     " +
                        "                  u.estado = 'A' and FNC_USUARIO_VALIDO(UU.IDUNIDAD, F1.JEFE, :idunidad, :idjefe, uu.idfuncion, :idfuncion)>0 AND " +
                        "                  uu.ESTADO = 'A' and " +
                        "                  uu.USUARIOFINAL IN ('S') and " +
                        "                  f1.idfuncion = uu.idfuncion       " +
                        "          ) x ");
                        //"     , favorito f " +
                        //"       WHERE f.idcontacto = x.idusuario and f.idunidadcontacto = x.idunidad  and F.IDCARGOCONTACTO = x.idfuncion AND f.tipocontacto = 'U' " +
                        //"       AND f.idpropietario = :idpropietario AND f.estado = :idestado ");

         

         sbQuery.append(" ");
         sbQuery.append("ORDER BY LOWER(label) ASC ");
         
         
         log.info("findLstBy(Favoritos):"+sbQuery);
         log.info("findLstBy(Favoritos)idunidad:"+idunidad+",idPropietario:"+idPropietario+",estado:"+estado+",jefe:"+jefe+",idfuncion:"+idfuncion);
         
         Query query = em.createNativeQuery(sbQuery.toString(), "favoritoResult")
                         .setParameter("idunidad", idunidad)
                         //.setParameter("idpropietario", idPropietario)
                         //.setParameter("idestado", estado)
                         .setParameter("idjefe", jefe)
                         .setParameter("idfuncion", idfuncion)   ;

        
         List<Object[]> result = query.getResultList();
         
         //log.info("findLstBy(result):"+result.size());
       
         for (Object[] o : result) {
            int idx = 0;
            String id = (String) o[idx++];
            String label = (String) o[idx++];
            lstFavorito.add(new Favorito(id, label));
         }

         return lstFavorito;
         
      } catch (Exception e) {
         e.printStackTrace();
         log.warn("No se encontraron favoritos del propietario [" + idPropietario + "] estado [" + estado + "]");

         return null;
      }
   }

   @Override
   public void saveFavorito(Favorito favorito) {
      if (favorito.getIdFavorito() == null) {
         em.persist(favorito);
         em.flush();
         em.refresh(favorito);
      } else {
         em.merge(favorito);
         em.flush();
      }
   }

   /*
    * Getters & Setters
    */
   public EntityManager getEm() {
      return em;
   }

   @PersistenceContext(unitName = "sigedPU")
   public void setEm(EntityManager em) {
      this.em = em;
   }
}
