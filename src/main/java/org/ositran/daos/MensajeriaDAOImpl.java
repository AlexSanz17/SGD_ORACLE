/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ositran.utils.Constantes;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Mensajeria;
import com.btg.ositran.siged.domain.Usuario;

@Repository
public class MensajeriaDAOImpl implements MensajeriaDAO {

   private EntityManager em;

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public List<Mensajeria> findEstado(String Est) {
      char Std = Est.charAt(0);
      return getEm().createNamedQuery("Mensajeria.findByEstadoDocPrincipal")
      .setParameter("estado", Std)
      .setParameter("docprincipal", Constantes.DOCUMENTO_PRINCIPAL)
      .getResultList();
   }

   public Mensajeria findId(int Id) {
      return getEm().find(Mensajeria.class, Id);
   }

   public Mensajeria guardarObj(Mensajeria objMensajeria) {
      if (objMensajeria.getIdmensajeria() == null) {
         em.persist(objMensajeria);
         em.flush();
         em.refresh(objMensajeria);
      } else {
         em.merge(objMensajeria);
         em.flush();
      }

      return objMensajeria;
   }

   public List<Mensajeria> FindusuarioMensajeria(int idusuario) {


      return getEm().createNamedQuery("Mensajeria.findByidusuarioDocPrincipal")
      .setParameter("idusuario", idusuario)
      .setParameter("docprincipal", Constantes.DOCUMENTO_PRINCIPAL)
      .getResultList();

   }

   @Override
   public List<Mensajeria> filtrarCriterios(Usuario objUsuario, String fechaDesde, String fechaHasta, String horaDesde, String horaHasta) {
		StringBuilder sQuery= new StringBuilder();
		
		List<Mensajeria> listaMensajeria = new ArrayList<Mensajeria>();
		//SimpleDateFormat formatoFecha=new SimpleDateFormat("dd/MM/yyyy");
		//Date fechaInicio = new Timestamp(formatoFecha.parse(fechaDesde).getTime());
		//Date fechaFin = new Timestamp(formatoFecha.parse(fechaHasta).getTime());
		
		//String fechaInicio = fechaDesde+""
		sQuery.append("SELECT m FROM Mensajeria m LEFT JOIN m.idusuario u ");
		sQuery.append("WHERE u.idusuario = :idusuario AND ");
		sQuery.append(" m.fechaderivacion BETWEEN ").append(" TO_DATE('").append(fechaDesde).append(" ").append(horaDesde).append("','DD/MM/YYYY HH12:MI:AM') AND ");
		sQuery.append(" TO_DATE('").append(fechaHasta).append(" ").append(horaHasta).append("','DD/MM/YYYY HH12:MI:AM') ORDER BY m.destinatario ASC , m.fechaderivacion ASC");

		Query obj1=em.createQuery(sQuery.toString()).setParameter("idusuario",objUsuario.getIdusuario());
		listaMensajeria = obj1.getResultList();
		return listaMensajeria;
   }
   
   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   private EntityManager getEm() {
      return em;
   }

   @PersistenceContext(unitName="sigedPU")
   private void setEm(EntityManager em) {
      this.em = em;
   }


}
