/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.btg.ositran.siged.domain.GridXGridColumna;

public class GridXGridColumnaDAOImpl implements GridXGridColumnaDAO {

   private EntityManager em;

   /*
    * Methods
    */
   @SuppressWarnings("unchecked")
   @Override
   public List<GridXGridColumna> encontrarPorGrid(int idGrid) {
      String sQuery = "SELECT NEW GridXGridColumna(" +
                      "gxgc.gridColumna," +
                      "gxgc.id" +
                      ") ";

      sQuery += "FROM GridXGridColumna gxgc ";
      sQuery += "LEFT JOIN gxgc.grid g ";
      sQuery += "LEFT JOIN gxgc.gridColumna gc ";
      sQuery += "WHERE g.idGrid = :idgrid ";
      //sQuery += "ORDER BY gc.field ASC";
      sQuery += "ORDER BY gxgc.id ASC";

      Query qQuery = em.createQuery(sQuery)
                       .setParameter("idgrid", idGrid);

      return qQuery.getResultList();
      
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
