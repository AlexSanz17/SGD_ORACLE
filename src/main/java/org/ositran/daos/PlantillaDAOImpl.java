/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Campo;
import com.btg.ositran.siged.domain.Plantilla;

@Repository
public class PlantillaDAOImpl implements PlantillaDAO{
	
	//private Logger log=Logger.getLogger(this.getClass());
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Plantilla> findAll(){
		return em.createNamedQuery("Plantilla.findAll").getResultList();
	}

	public Plantilla findByIdplantilla(Integer idplantilla){
		return em.find(Plantilla.class,idplantilla);
	}

	@SuppressWarnings("unchecked")
	public List<Campo> listCamposByTipoPlantilla(Integer tipoPlantilla){
		return em.createNamedQuery("Plantilla.listCamposByTipoPlantilla")
               .setParameter("tipoPlantilla", tipoPlantilla)
               .getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Plantilla> listSeccionesByTipoPlantilla(String tipoPlantilla){
		return em.createNamedQuery("Plantilla.listSeccionesByTipoPlantilla").setParameter("tipoPlantilla",tipoPlantilla).getResultList();
	}

   public Plantilla guardarObj(Plantilla objPlantilla) {
      if (objPlantilla.getIdplantilla() == null) {
         em.persist(objPlantilla);
         em.flush();
         em.refresh(objPlantilla);
      } else {
         em.merge(objPlantilla);
         em.flush();
      }

      return objPlantilla;
   }

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}
}
