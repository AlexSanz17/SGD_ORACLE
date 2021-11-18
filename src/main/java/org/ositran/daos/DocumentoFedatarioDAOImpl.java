/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.ositran.dojo.Recurso;
import org.ositran.utils.Constantes;
import org.springframework.stereotype.Repository;
import com.btg.ositran.siged.domain.Documentofedateado;
import com.btg.ositran.siged.domain.Usuario;
import org.apache.commons.lang.StringUtils;

@Repository
public class DocumentoFedatarioDAOImpl implements DocumentoFedatarioDAO {

   private static Logger _log = Logger.getLogger(DocumentoFedatarioDAOImpl.class);
   private EntityManager em;
   private UsuarioDAO usuarioDAO;

   public UsuarioDAO getUsuarioDAO() {
	return usuarioDAO;
	}


	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}


public Documentofedateado registrar(Documentofedateado objDoc) throws Exception{
	   try{

		if (objDoc.getServicio().equals(Constantes.SERVICIO_CERTIFICACION_DE_FIRMAS)){
			Usuario usuario = usuarioDAO.findByIdUsuario(objDoc.getIdUsuario());
			String  sql = "SELECT " + usuario.getUsuario() + "_SEQ.NEXTVAL FROM DUAL";
			Query q = em.createNativeQuery(sql.toString());
			List<BigDecimal> result = q.getResultList();
			Calendar fecha = Calendar.getInstance();

			System.out.println("Respuesta=" + "N° " + StringUtils.leftPad(result.get(0).toString(),4,"0") + "-" + usuario.getSiglasFedatario() + "-" + fecha.get(Calendar.YEAR));
			objDoc.setIdCertFirmas("N° " + StringUtils.leftPad(result.get(0).toString(),4,"0") + "-" + usuario.getSiglasFedatario() + "-" + fecha.get(Calendar.YEAR));
			System.out.println("Valores=" + objDoc.getIdCertFirmas());
		}

		em.persist(objDoc); // Nuevo
		em.flush();
		em.refresh(objDoc);
	   }catch(Exception e){
		   e.printStackTrace();
		   throw e;
	   }
		return objDoc;
   }


   public List<Documentofedateado> buscarLstDocumentoFedateado(Integer idUsuario, String fecInferior , String fecSuperior, String servicio){
	     SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
	     String  sql = "select a.idoperacion as idoperacion, a.nrodocumento, a.asunto, a.nrofoliosdocumento, a.nrocopiasfedateadas, decode(idUnidad, null, desunidad, (SELECT NOMBRE FROM UNIDAD WHERE IDUNIDAD = a.idUnidadSolicitante)) as nombre, a.fechacreacion, a.servicio, a.idfirma from " +
			      " (select f.idoperacion as idoperacion, t.nombre || ' N° ' || f.nrodocumento as nrodocumento, f.asunto as asunto, f.nrofoliosdocumento as nrofoliosdocumento, f.nrocopiasfedateadas as nrocopiasfedateadas,  "  +
			      "       f.fechacreacion , f.idunidadsolicitante as idUnidadSolicitante , f.desunidad as desunidad, f.idunidadsolicitante as idUnidad , p.descripcion as servicio, f.idcertfirmas as idfirma " +
			      "     from documentofedateado f, tipodocumento t, parametro p where  f.idusuario = " + idUsuario + "  and f.estado = 'A' and t.idtipodocumento = f.idtipodocumentofedateado and " +
			      "         f.iddocumentofedateado is null  and p.tipo='SERVICIO_FEDATARIO' and f.servicio = p.valor ";
			      if (fecInferior!=null && fecSuperior!=null)
			    	  sql = sql + " and TO_CHAR(f.fechacreacion,'YYYYMMDD') >= :fecInferior and TO_CHAR(f.fechacreacion,'YYYYMMDD') <= :fecSuperior ";
			      if (!servicio.toUpperCase().equals("TODOS"))
			    	  sql = sql + " and f.servicio ='" + servicio + "'" + " ";
			      sql = sql + " union " +
			      " select f.idoperacion as idoperacion, t.nombre || ' ' || dd.nrodocumento as nrodocumento, dd.asunto as asunto, f.nrofoliosdocumento as nrofoliosdocumento, f.nrocopiasfedateadas as nrocopiasfedateadas, " +
			      "      f.fechacreacion , f.idunidadsolicitante as idUnidadSolicitante , f.desunidad as desunidad, f.idunidadsolicitante as idUnidad , p.descripcion as servicio, f.idcertfirmas as idfirma from documentofedateado f, tipodocumento t, documento dd, parametro p where dd.iddocumento= f.iddocumentofedateado and f.idusuario = "  + idUsuario +
			      "        and f.estado = 'A' and t.idtipodocumento = dd.tipodocumento and " +
			      "         f.iddocumentofedateado is not null and p.tipo='SERVICIO_FEDATARIO' and f.servicio = p.valor ";
			      if (fecInferior!=null && fecSuperior!=null)
			    	  sql = sql + " and TO_CHAR(f.fechacreacion,'YYYYMMDD') >= :fecInferior and TO_CHAR(f.fechacreacion,'YYYYMMDD') <= :fecSuperior ";
			      if (!servicio.toUpperCase().equals("TODOS"))
			    	  sql = sql + " and f.servicio ='" + servicio + "'" + " ";
			      sql = sql + ") a " +
			      " order by a.fechacreacion desc ";


		   Query q = em.createNativeQuery(sql.toString());
		   System.out.println("Query=" + sql.toString());
		   try{
		       if (fecInferior!=null && fecSuperior!=null) {
		    	    System.out.println("Valores=" +  (new SimpleDateFormat("yyyyMMdd")).format(fechita.parse(fecInferior)) + "||" + (new SimpleDateFormat("yyyyMMdd")).format(fechita.parse(fecSuperior)));
					q.setParameter("fecInferior", (new SimpleDateFormat("yyyyMMdd")).format(fechita.parse(fecInferior)));
					q.setParameter("fecSuperior", (new SimpleDateFormat("yyyyMMdd")).format(fechita.parse(fecSuperior)));
			   }
		   }catch(Exception e){
			   e.printStackTrace();
		   }

	       List<Object> res = (List<Object>) q.getResultList();
	       List<Documentofedateado> lista = new ArrayList<Documentofedateado>();

	       try{
			for (Object obj : res) {
				Documentofedateado f = new Documentofedateado();
				Object[] objectArray = (Object[]) obj;
				f.setNroDocumento(objectArray[1].toString());
				f.setAsunto(objectArray[2].toString());
				f.setNroCopiasFedateadas(new Integer(objectArray[4].toString()));
				f.setNroFoliosDocumento(new Integer(objectArray[3].toString()));
                f.setDesUnidad(objectArray[5].toString());
                f.setFechaDocumentoRegistro(objectArray[6].toString());
                f.setServicio(objectArray[7].toString());
                f.setIdCertFirmas(objectArray[8] == null ? null : objectArray[8].toString());
				lista.add(f);
			}
	       }catch(Exception e){
	    	   e.printStackTrace();
	       }

	      return lista;
	  }

   public EntityManager getEm() {
      return em;
   }

   @PersistenceContext(unitName = "sigedPU")
   public void setEm(EntityManager em) {
      this.em = em;
   }
}
