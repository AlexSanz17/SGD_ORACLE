/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoStor;
import com.btg.ositran.siged.domain.Expediente;

@Repository
public class DocumentostorDAOImpl implements DocumentostorDAO{
	
	private EntityManager em;
	private static Log log=LogFactory.getLog(DocumentostorDAOImpl.class);

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	public DocumentoStor findByIdDocumentoStor(Integer iIdDS){
		return em.find(DocumentoStor.class,iIdDS);
	}

	public void saveDocumentoStor(DocumentoStor objDS){
		em.persist(objDS);
		em.flush();
		em.refresh(objDS);
	}

	public void updateDocumentoStor(DocumentoStor objDS){
		em.merge(objDS);
		em.flush();
	}

	public DocumentoStor findByIdExpediente(Integer idExpediente){
		Expediente expediente=(Expediente) em.createNamedQuery("Expediente.findByIdexpediente").setParameter("idexpediente",idExpediente).getSingleResult();
		List<Documento> listDocumentos=expediente.getDocumentoList();
		String tipoExpediente=expediente.getExpedientestor().getTipoexpediente();
		DocumentoStor documentostor=null;
		if(tipoExpediente.equalsIgnoreCase("apelacion")){
			for(Documento doc : listDocumentos){
				if(doc.getTipoDocumento().getCodigo().equalsIgnoreCase("RecApelacion")){
					documentostor=doc.getDocumentoStor();
					break;
				}
			}
		}else if(tipoExpediente.equalsIgnoreCase("queja")){
			for(Documento doc : listDocumentos){
				if(doc.getTipoDocumento().getCodigo().equalsIgnoreCase("RecQueja")){
					documentostor=doc.getDocumentoStor();
					break;
				}
			}
		}else if(tipoExpediente.equalsIgnoreCase("medida cautelar")){
			for(Documento doc : listDocumentos){
				if(doc.getTipoDocumento().getCodigo().equalsIgnoreCase("RecMedidaCautelar")){
					documentostor=doc.getDocumentoStor();
					break;
				}
			}
		}
		if(documentostor==null){
			log.warn("No existe DocumentoSTOR Asociado al Expediente "+idExpediente);
		}else{
			log.debug("DOCUMENTO STOR CON ID: "+documentostor.getIdDocumento()+" Y DEL TIPO: "+documentostor.getDocumento().getTipoDocumento().getNombre());
		}
		/*
		 * log.debug("DOCUMENTO STOR CON ID: "+(documentostor!=null?documentostor
		 * .getIddocumento():"docstor:null")+
		 * " Y DEL TIPO"+(documentostor.getDocumento()!=null?
		 * ((documentostor.getDocumento
		 * ().getTipodocumento()!=null?documentostor.
		 * getDocumento().getTipodocumento
		 * ().getNombre():"documentostor.getDocumento().getTipodocumento() null"
		 * )) :"documentostor.getDocumento null"));
		 */
		return documentostor;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}
}
