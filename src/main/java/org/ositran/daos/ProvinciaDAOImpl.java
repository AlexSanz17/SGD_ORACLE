/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ositran.utils.Constantes;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Provincia;

@Repository
public class ProvinciaDAOImpl implements ProvinciaDAO {

   private EntityManager em;

   public List<Provincia> buscarTodo() {
      return em.createNamedQuery("Provincia.findAll")
               .getResultList();
   }

   public List<Provincia> findByDepartamento(Integer iIdDepartamento) {
      String sQuery = "SELECT NEW Provincia(" +
                      "p.idprovincia," +
                      "p.nombre" +
                      ") ";
      sQuery += "FROM Provincia p ";
      sQuery += "LEFT JOIN p.departamento d ";
      sQuery += "WHERE d.iddepartamento = :iddepartamento ";
      sQuery += "ORDER BY p.nombre ASC";

      Query qQuery = em.createQuery(sQuery)
                       .setParameter("iddepartamento", iIdDepartamento);

      return qQuery.getResultList();
   }

   public Provincia findObjById(Integer iIdProvincia) {
      return em.find(Provincia.class, iIdProvincia);
   }

   public Provincia guardarObj(Provincia objProvincia, char cMode) {
      if (cMode == Constantes.OPERACION_CREATE) {
         em.persist(objProvincia);
         em.flush();
         em.refresh(objProvincia);
      } else if (cMode == Constantes.OPERACION_UPDATE) {
         em.merge(objProvincia);
         em.flush();
      }

      return objProvincia;
   }

   public EntityManager getEm() {
      return em;
   }

   @PersistenceContext(unitName="sigedPU")
   public void setEm(EntityManager em) {
      this.em = em;
   }
}
