/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ositran.utils.Constantes;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Departamento;

@Repository
public class DepartamentoDAOImpl implements DepartamentoDAO {

   private EntityManager em;

   public List<Departamento> findAll() {
      String sQuery = "SELECT NEW Departamento(" +
                      "d.iddepartamento," +
                      "d.nombre" +
                      ") ";
      sQuery += "FROM Departamento d ";
      sQuery += "ORDER BY d.nombre ASC";

      Query qQuery = em.createQuery(sQuery);

      return qQuery.getResultList();
   }

   public Departamento lisNomdiafestivo(String Nombreregion) {
      return (Departamento) em.createNamedQuery("Departamento.findByNombreregion")
                              .setParameter("nombreregion", Nombreregion)
                              .getSingleResult();
   }

   public Departamento findObjById(Integer iIdDepartamento) {
      return em.find(Departamento.class, iIdDepartamento);
   }

   public Departamento guardarObj(Departamento objDepartamento, char cMode) {
      if (cMode == Constantes.OPERACION_CREATE) {
         em.persist(objDepartamento);
         em.flush();
         em.refresh(objDepartamento);
      } else if (cMode == Constantes.OPERACION_UPDATE) {
         em.merge(objDepartamento);
         em.flush();
      }

      return objDepartamento;
   }

   public EntityManager getEm() {
      return em;
   }

   @PersistenceContext(unitName = "sigedPU")
   public void setEm(EntityManager em) {
      this.em = em;
   }
}
