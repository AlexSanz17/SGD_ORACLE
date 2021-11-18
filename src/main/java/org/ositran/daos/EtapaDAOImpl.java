/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ositran.utils.Constantes;
import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.btg.ositran.siged.domain.Etapa;
@Repository
public class EtapaDAOImpl implements EtapaDAO {

   private EntityManager em;

    private static Logger log =  Logger.getLogger(EtapaDAOImpl.class);

   public EntityManager getEm() {  return em; }
   @PersistenceContext(unitName="sigedPU")
   public void setEm(EntityManager em) { this.em = em;}

   @SuppressWarnings("unchecked")
public List<Etapa> findAll()  
   { 
    return getEm()
    	   .createNamedQuery("Etapa.findAll")
    	   .getResultList();
   }

   public Etapa findByIdetapa(int Idetapa)
   {
      return (Etapa)getEm().createNamedQuery("Etapa.findByIdetapa")
      		  .setParameter("idetapa", Idetapa) .getSingleResult();
   }
   
   public Etapa findByCodigo(String codigo)
   {
      return (Etapa)getEm().createNamedQuery("Etapa.findByCodigo")
      		  .setParameter("codigo", codigo) .getSingleResult();
   }  
   
    public List<Etapa> getEtapaList(String descripcion, Integer duracion,String estado, String codigo) {
	   
	   StringBuffer jql = new StringBuffer();
	   
	   jql.append("\nselect et from Etapa et where 1 = 1");
	   
	   if(StringUtils.isNotBlank(descripcion))
		   jql.append("and UPPER(et.descripcion) like '%"+descripcion.toUpperCase()+"%' \n");
	   
	   if(duracion != null)
		   jql.append("and et.duracion = "+duracion+" \n");
	   
	   if(StringUtils.isNotBlank(estado))
		   jql.append("and et.estado = '"+estado.charAt(0)+"' \n");
	   
	   if(StringUtils.isNotBlank(codigo))
		   jql.append("and et.codigo = '"+codigo+"' \n");
	   
	   return em.createQuery(jql.toString()).getResultList();
   }   
   
    public void saveObject(Etapa etapa) {
 	   try {
 		   if(etapa.getIdetapa() == null)
 			   em.persist(etapa);
 		   else
 			   em.merge(etapa);
 	   	} catch (Exception e) {
 	   		log.error(e.getMessage(), e);
 	   	}
 	}
	@Override
	public List<Etapa> findEtapabyProceso(Integer idProceso) {
		   StringBuffer jql = new StringBuffer();
		   
		   jql.append("\nselect et from Etapa et where 1 = 1");
		   
		   if(idProceso != null)
			   jql.append("and et.proceso = "+idProceso+" \n");

		   jql.append("and et.estado = '"+ String.valueOf(Constantes.ESTADO_ACTIVO).charAt(0) +"' \n");
		   jql.append("order by et.descripcion asc");
		   
		   
		   return em.createQuery(jql.toString()).getResultList();
	}
        
}
