/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.cxf.common.util.StringUtils;
import org.jfree.util.Log;
import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.utils.Constantes;

import com.btg.ositran.siged.domain.ConsultaAPN3;
import com.btg.ositran.siged.domain.ConsultaAPN4;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Expediente;

public class ConsultaDAOImpl implements ConsultaDAO {
	 private EntityManager em;
	   public EntityManager getEm() {
		      return em;
		   }

		   @PersistenceContext(unitName="sigedPU")
		   public void setEm(EntityManager em) {
		      this.em = em;
		   }

		@SuppressWarnings("unchecked")
		@Override
		public List<ConsultaAPN3> documentosRecepcionados(String tipodocumento,
				String areaOrigen,String fechaDesde, String fechaHasta,Integer idAreaDestino, String nroDocumento, String asuntoDocumento) {
			Integer area_=0;
			Integer tipodocumento_ =0;
			Date fechaDesde_ = null;
			Date fechaHasta_ = null;

			SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
			String sQuery = "SELECT f FROM ConsultaAPN3 f ";
			boolean flagWhere = false;

			if(!((areaOrigen.toString().trim()).equals("Todos"))){
				area_= Integer.parseInt(areaOrigen.toString().trim());
				sQuery += "WHERE (f.idAreaOrigenTd like :idAreaOrigenTd OR f.idAreaOrigenTa like :idAreaOrigenTa OR f.idAreaOrigenTc like :idAreaOrigenTc)";
				flagWhere=true;
			}

			if(!((tipodocumento.toString().trim()).equals("Todos"))){
				tipodocumento_= Integer.parseInt(tipodocumento.toString().trim());
				if(flagWhere){
					sQuery +="AND f.idTipoDocumento like :tipoDocumento ";
				}else{
					sQuery +="WHERE f.idTipoDocumento like :tipoDocumento ";
					flagWhere = true;
				}
			}
			if(!((fechaDesde.toString().trim()).equals("Todos")) && !((fechaHasta.toString().trim()).equals("Todos"))){
				try {
					fechaDesde_= fechita.parse(fechaDesde);

					fechaHasta_= fechita.parse(fechaHasta);
					fechaHasta_.setHours(24);
					fechaHasta_.setMinutes(0);
					fechaHasta_.setSeconds(0);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(flagWhere){
                    sQuery +="AND (( f.fechaCreacionTd >= :fechaDesde AND f.fechaCreacionTd <= :fechaHasta ) OR ( f.fechaCreacionTa >= :fechaDesde AND f.fechaCreacionTa <= :fechaHasta ) OR ( f.fechaCreacionTc >= :fechaDesde AND f.fechaCreacionTc <= :fechaHasta ) ) ";
                }else{
                    sQuery +="WHERE (( f.fechaCreacionTd >= :fechaDesde AND f.fechaCreacionTd <= :fechaHasta ) OR ( f.fechaCreacionTa >= :fechaDesde AND f.fechaCreacionTa <= :fechaHasta ) OR ( f.fechaCreacionTc >= :fechaDesde AND f.fechaCreacionTc <= :fechaHasta ) ) ";
                    flagWhere = true;
                }
			}

			if(!(idAreaDestino == null)){

				if(flagWhere){
					sQuery +="AND (f.idAreaDestinoTd like :idAreaDestinoTd OR f.idAreaDestinoTa like :idAreaDestinoTa OR f.idAreaDestinoTc like :idAreaDestinoTc  )";
				}else{
					sQuery +="WHERE (f.idAreaDestinoTd like :idAreaDestinoTd OR f.idAreaDestinoTa like :idAreaDestinoTa OR f.idAreaDestinoTc like :idAreaDestinoTc  )";
					flagWhere = true;
				}
			}

			if(!((nroDocumento.toString().trim()).equals("Todos"))){
			    nroDocumento = "%"+nroDocumento+"%";
				if(flagWhere){
					sQuery +="AND LOWER(f.nroDocumento) like :nroDocumento ";
				}else{
					sQuery +="WHERE LOWER(f.nroDocumento) like :nroDocumento ";
					flagWhere = true;
				}
			}
			if(!((asuntoDocumento.toString().trim()).equals("Todos"))){
				asuntoDocumento="%"+asuntoDocumento+"%";
				if(flagWhere){
					sQuery +="AND LOWER(f.asuntoDocumento) like :asuntoDocumento ";
				}else{
					sQuery +="WHERE LOWER(f.asuntoDocumento) like :asuntoDocumento ";
					flagWhere = true;
				}
			}

			//-----------------------------------------------------
			Query obj = em.createQuery(sQuery);
			//-----------------------------------------------------
                        

			if(!((areaOrigen.toString().trim()).equals("Todos"))){
				obj.setParameter("idAreaOrigenTd", area_);
			}
			if(!((areaOrigen.toString().trim()).equals("Todos"))){
				obj.setParameter("idAreaOrigenTa", area_);
			}
			if(!((areaOrigen.toString().trim()).equals("Todos"))){
				obj.setParameter("idAreaOrigenTc", area_);
			}
			if(!((tipodocumento.toString().trim()).equals("Todos"))){
				obj.setParameter("tipoDocumento", tipodocumento_);
			}
			if(!((fechaDesde.toString().trim()).equals("Todos")) && !((fechaHasta.toString().trim()).equals("Todos"))){
				obj.setParameter("fechaDesde", fechaDesde_);
				obj.setParameter("fechaHasta", fechaHasta_);
			}
			if(!(idAreaDestino == null)){
				obj.setParameter("idAreaDestinoTd", idAreaDestino);
			}
			if(!(idAreaDestino == null)){
				obj.setParameter("idAreaDestinoTa", idAreaDestino);
			}
			if(!(idAreaDestino == null)){
				obj.setParameter("idAreaDestinoTc", idAreaDestino);
			}
			if(!((nroDocumento.toString().trim()).equals("Todos"))){
				obj.setParameter("nroDocumento", nroDocumento.toLowerCase());
			}
			if(!((asuntoDocumento.toString().trim()).equals("Todos"))){
				obj.setParameter("asuntoDocumento", asuntoDocumento.toLowerCase());
			}
			return obj.getResultList();


		}


		@SuppressWarnings("unchecked")
		@Override
		public List<ConsultaAPN4> documentosEmitidos(Integer remitente) {

			String sQuery = "SELECT f FROM ConsultaAPN4 f ";

			if(!(remitente == null)){
				sQuery += "WHERE f.remitente like :remitente ";
			}

			//-----------------------------------------------------
			Query obj = em.createQuery(sQuery);
			//-----------------------------------------------------

			if(!(remitente == null)){
				obj.setParameter("remitente", remitente);
			}

			return obj.getResultList();
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<Expediente> misExpedientes(Integer idUsuario, BusquedaAvanzada objFiltro) {
			String sQuery = "SELECT e FROM Expediente e WHERE e.idpropietario.idusuario = :idUsuario ";

            //1
			if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
				sQuery += "AND LOWER(e.nroexpediente) LIKE :numeroExpediente ";
			}
            //2
			if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
				sQuery += "AND LOWER(e.asunto) LIKE :asuntoExpediente ";
			}
			

			sQuery += "ORDER BY e.fechacreacion DESC ";
			//-----------------------------------------------------
			Query q = em.createQuery(sQuery);
                        
                        System.out.println("Mis Expedientes=" + sQuery);
			//-----------------------------------------------------

			//1
			if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
				q.setParameter("numeroExpediente", "%"+objFiltro.getNumeroExpediente()+"%");
			}
        		if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
				q.setParameter("asuntoExpediente", "%"+objFiltro.getAsuntoexpediente()+"%");
			}
			
			
			if(idUsuario != null){
				q.setParameter("idUsuario", idUsuario);
			}

			return q.getResultList();
		}

}
