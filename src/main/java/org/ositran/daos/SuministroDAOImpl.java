/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoStor;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Suministro;

@Repository
public class SuministroDAOImpl implements SuministroDAO{

	private EntityManager em;

	private static Log log=LogFactory.getLog(SuministroDAOImpl.class);

	public void saveSuministro(Suministro objSum){
		if(objSum.getIdsuministro() == null){
			em.persist(objSum); // Nuevo
			em.flush();
			em.refresh(objSum);
		}
		else{
			em.merge(objSum); // Actualizacion
			em.flush();
		}
	}

	public List<Suministro> findByExpediente(Integer idExpediente){
		Expediente expediente=(Expediente) em.createNamedQuery("Expediente.findByIdexpediente").setParameter("idexpediente",idExpediente).getSingleResult();
		List<Documento> listDocumentos=expediente.getDocumentoList();
		String tipoExpediente=expediente.getExpedientestor().getTipoexpediente();

		DocumentoStor recursoStor=null;
		if(tipoExpediente.equalsIgnoreCase("apelacion")){
			for(Iterator itDocs=listDocumentos.iterator();itDocs.hasNext();){
				Documento doc=(Documento) itDocs.next();
				if(doc.getTipoDocumento().getNombre().equalsIgnoreCase("Recurso de Apelacion")){
					recursoStor=doc.getDocumentoStor();
					break;
				}
			}
		}
		else if(tipoExpediente.equalsIgnoreCase("queja")){
			for(Iterator itDocs=listDocumentos.iterator();itDocs.hasNext();){
				Documento doc=(Documento) itDocs.next();
				if(doc.getTipoDocumento().getNombre().equalsIgnoreCase("Recurso de Queja")){
					recursoStor=doc.getDocumentoStor();
					break;
				}
			}
		}
		else if(tipoExpediente.equalsIgnoreCase("medida cautelar")){
			for(Iterator itDocs=listDocumentos.iterator();itDocs.hasNext();){
				Documento doc=(Documento) itDocs.next();
				if(doc.getTipoDocumento().getNombre().equalsIgnoreCase("Recurso de Medida Cautelar")){
					recursoStor=doc.getDocumentoStor();
					break;
				}
			}
		}

		log.debug("NUMERO DE SUMINISTROS: " + recursoStor.getSuministros().size());
		return recursoStor.getSuministros();
	}

	@SuppressWarnings("unchecked")
	public List<String> findByIdDocumento(Integer idDocumento){
		try{
			String sQuery="SELECT s.nrosuministro FROM Suministro s WHERE s.documentostor.iddocumento like :campoIdDoc";
			Query obj=em.createQuery(sQuery);
			obj.setParameter("campoIdDoc",idDocumento);
			return obj.getResultList();
		}
		catch(Exception e){
			return null;
		}
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}

	@Override
	public Suministro getSuministro(int idSuministro){
		return em.find(Suministro.class,idSuministro);
	}

}
