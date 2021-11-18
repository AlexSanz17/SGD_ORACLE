/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.ArchivoPendiente;
import com.btg.ositran.siged.domain.ArchivoTemporal;
import com.btg.ositran.siged.domain.Valorcampo;

@Repository
public class ArchivopendienteDAOImpl implements ArchivopendienteDAO{
	
	private static Logger log=Logger.getLogger(ArchivopendienteDAOImpl.class);
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<ArchivoPendiente> findAll(){
		return em.createNamedQuery("Archivopendiente.findAll").getResultList();
	}

	public ArchivoPendiente findByIdarchivopendiente(Integer idArchivoPendiente){
		/*log.debug("#### idArchivoPendiente:"+idArchivoPendiente);
		ArchivoPendiente a=(ArchivoPendiente) getEm().createNamedQuery("Archivopendiente.findByIdarchivopendiente").setParameter("idarchivopendiente",idArchivoPendiente).getSingleResult();
		a.getValorcampoList().size();
		return a;*/
		return em.find(ArchivoPendiente.class,idArchivoPendiente);
	}

	@SuppressWarnings("unchecked")
	public List<ArchivoPendiente> findByIdusuario(Integer idusuario){
		return em.createNamedQuery("Archivopendiente.findByIdusuario").setParameter("idusuario",idusuario).getResultList();
	}

	public void deleteArchivopendiente(Integer idArchivoPendiente){
		ArchivoPendiente arch=getEm().find(ArchivoPendiente.class,idArchivoPendiente);
		getEm().remove(arch);
	}

	@SuppressWarnings("unchecked")
	public void deleterchildren(Integer idarchivopendiente){
		List<Valorcampo> lista1=em.createNamedQuery("Valorcampo.findByarchivopendiente").setParameter("idarchivopendiente",idarchivopendiente).getResultList();
		List<ArchivoTemporal> lista2=em.createNamedQuery("Archivotemporal.findByIdarchivopendiente").setParameter("idarchivopendiente",idarchivopendiente).getResultList();
		for(Valorcampo val : lista1){
			val.getArchivopendiente().getValoresCampo().remove(val);
			val.getCampo().getValoresCampo().remove(val);
			em.remove(val);
		}
		for(ArchivoTemporal a : lista2){
			a.getArchivoPendiente().getArchivosTemporales().remove(a);
			em.remove(a);
		}
		em.flush();
	}

	public void refresh(ArchivoPendiente archivopendiente){
		getEm().refresh(archivopendiente);
	}

	public void saveArchivopendiente(ArchivoPendiente archivopendiente){
		/*
		 * log.debug(" hiiiiiiiiiii "); List valores =
		 * archivopendiente.getValorcampoList() ;
		 * log.debug(" valores list: "+valores.size());
		 * log.debug(" valores list: "
		 * +archivopendiente.getIdarchivopendiente());
		 */
		if(archivopendiente.getIdArchivoPendiente()==null){
			getEm().persist(archivopendiente); // Nuevo
			getEm().flush();
			getEm().refresh(archivopendiente);
		}else{
			/*
			 * Iterator i =
			 * getEm().createNamedQuery("Valorcampo.findByarchivopendiente")
			 * .setParameter("idarchivopendiente",
			 * archivopendiente.getIdarchivopendiente
			 * ()).getResultList().iterator(); Iterator i2 =
			 * getEm().createNamedQuery
			 * ("Archivotemporal.findByIdarchivopendiente")
			 * .setParameter("idarchivopendiente",
			 * archivopendiente.getIdarchivopendiente
			 * ()).getResultList().iterator();
			 */
			// getEm().flush();
			getEm().merge(archivopendiente);
			getEm().flush();
			// getEm().refresh(archivopendiente);
			/*
			 * while(i.hasNext() ){ Object o = i.next(); Valorcampo val =
			 * (Valorcampo)o; log.debug(" vallll : "+val);
			 * log.debug(" vallll valor : "+val.getValor());
			 * log.debug(" vallll id : "+val.getIdvalorcampo());
			 * 
			 * if(o!=null){ getEm().remove(o);
			 * 
			 * } }
			 * 
			 * 
			 * 
			 * while(i2.hasNext() ){ Object o = i2.next(); if(o!=null){
			 * getEm().remove(o); //getEm().flush(); } }
			 * 
			 * 
			 * 
			 * getEm().flush(); getEm().refresh(archivopendiente);
			 */
		}
	}

	// //////////////////////
	// Getters and Setters
	// //////////////////////
	public EntityManager getEm(){
		return em;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}
}
