/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Documentoxcliente;
import com.btg.ositran.siged.domain.Documentoxexpediente;

@Repository
public class DocumentoxclienteDAOImpl implements DocumentoxclienteDAO {

   private EntityManager em;

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public Documentoxcliente guardarObj(Documentoxcliente objDocXCli) {
      if (objDocXCli.getDocumentoxclientePK() == null) {
         em.persist(objDocXCli);
         em.flush();
         em.refresh(objDocXCli);
      } else {
         em.merge(objDocXCli);
         em.flush();
      }

      return objDocXCli;
   }

   @Override
   public List<Documentoxcliente> findClientesbyIdDocumento(Integer idDocumento) {
//		String sQuery = " SELECT dxc FROM Documentoxcliente dxc LEFT JOIN dxc.documentoxclientePK dxcPK ";
//		sQuery += " WHERE dxcPK.iddocumento = :iddocumento ";
		String sQuery = " SELECT dxc FROM Documentoxcliente dxc LEFT JOIN dxc.documento doc ";
		sQuery += " WHERE doc.idDocumento = :iddocumento ";
		Query obj = em.createQuery(sQuery).setParameter("iddocumento",idDocumento);

		return obj.getResultList();
   }
   
   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public EntityManager getEm() {
      return em;
   }

   @PersistenceContext(unitName="sigedPU")
   public void setEm(EntityManager em) {
      this.em = em;
   }


}
