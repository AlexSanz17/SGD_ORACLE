/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.utils.Constantes;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Documentoenviado;
import com.btg.ositran.siged.domain.FilaBandejaEnviados;
import com.btg.ositran.siged.domain.Usuario;
import java.util.ArrayList;
import org.ositran.utils.DocumentoPublicar;

@Repository
public class DocumentoEnviadoDAOImpl implements DocumentoEnviadoDAO{

	private EntityManager em;
	private static Logger log=Logger.getLogger(DocumentoEnviadoDAOImpl.class);

	@SuppressWarnings("unchecked")
	public List<Documentoenviado> findByUsuario(Integer idUsuario,String estado){
		List<Documentoenviado> salida=null;
		if(idUsuario!=null){
			Query q=em.createNamedQuery("Documentoenviado.findByUsuario");
			q.setParameter("idusuario",idUsuario);
			q.setParameter("estado",estado);
			salida=q.getResultList();
		}
		return salida;
	}

	@SuppressWarnings("unchecked")
	public List<Documentoenviado> findActivosFiltrados(Integer idusuario, BusquedaAvanzada objFiltro){
		String sql = "SELECT e FROM Documentoenviado e WHERE e.usuario.idusuario = :idusuario AND e.estado = :estado ";
		//1
		if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
			sql += "AND LOWER(e.trazabilidaddocumento.documento.expediente.nroexpediente) LIKE :nroExpediente ";
		}
		//2
		if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
			sql += "AND LOWER(e.trazabilidaddocumento.documento.numeroDocumento) LIKE :nroDocumento ";
		}
		//3
		if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
			sql += "AND e.trazabilidaddocumento.documento.tipoDocumento.idtipodocumento = :idTipoDocumento ";
		}
		//4
		if(!StringUtils.isEmpty(objFiltro.getCliente())){
			sql += "AND e.trazabilidaddocumento.documento.expediente.cliente.idCliente = :idCliente ";
		}
		//5
		if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
			sql += "AND LOWER(e.trazabilidaddocumento.documento.asunto) LIKE :asuntoDocumento ";
		}
		//6
		if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
			sql += "AND LOWER(e.trazabilidaddocumento.documento.expediente.asunto) LIKE :asuntoExpediente ";
		}

		sql += "ORDER BY e.iddocumentoenviado DESC";
		Query q = em.createQuery(sql);
		q.setParameter("estado", String.valueOf(Constantes.ESTADO_ACTIVO));
		q.setParameter("idusuario", idusuario);

		//1
		if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
			q.setParameter("nroExpediente", "%"+objFiltro.getNumeroExpediente().toLowerCase()+"%");
		}
		//2
		if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
			q.setParameter("nroDocumento", "%"+objFiltro.getNumeroDocumento().toLowerCase()+"%");
		}
		//3
		if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
			q.setParameter("idTipoDocumento", Integer.parseInt(objFiltro.getTipoDocumento()));
		}
		//4
		if(!StringUtils.isEmpty(objFiltro.getCliente())){
			q.setParameter("idCliente", Integer.parseInt(objFiltro.getCliente()));
		}
		//5
		if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
			q.setParameter("asuntoDocumento", "%"+objFiltro.getAsuntodocumento().toLowerCase()+"%");
		}
		//6
		if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
			q.setParameter("asuntoExpediente", "%"+objFiltro.getAsuntoexpediente().toLowerCase()+"%");
		}

		return q.getResultList();
	}

	public Documentoenviado findByIddocumentoenviado(Integer iddocumentoenviado){
		Documentoenviado doc=null;
		try{
			doc=(Documentoenviado) em.createNamedQuery("Documentoenviado.findByIddocumentoenviado").setParameter("iddocumentoenviado",iddocumentoenviado).getSingleResult();
		}catch(NoResultException ne){
			log.warn("No se encontro el documento Enviado con id="+iddocumentoenviado);
		}
		return doc;
	}

	public void saveDocumento(Documentoenviado documentoenviado){
		if(documentoenviado.getIddocumentoenviado()==null){
			em.persist(documentoenviado); // Nuevo
			em.flush();
			em.refresh(documentoenviado);
		}else{
			em.merge(documentoenviado); // Actualizacion
			em.flush();
		}
	}
        
        public List<Usuario> buscarListDestinatarios(Usuario objUsuario){
                StringBuffer sql = new StringBuffer();
		sql.append("select distinct e.iddestinatario, e.destinatario from vistabandejaenviados e where e.idpropietario= :idusuario and e.unidadPropietario = :idunidadpropietario and e.cargoPropietario  =:idcargopropietario "); 
                sql.append( " and e.destinatario is not null");
                
                 Query q = em.createNativeQuery(sql.toString());
                 q.setParameter("idusuario", objUsuario.getIdUsuarioPerfil())
                 .setParameter("idunidadpropietario", objUsuario.getIdUnidadPerfil())
                 .setParameter("idcargopropietario", objUsuario.getIdFuncionPerfil());
                 
                 List<Object> res = (List<Object>) q.getResultList();
                 List<Usuario> lst = new ArrayList<Usuario>();
                 
                 for (Object obj : res) {     
                        Usuario p = new Usuario(); 
                         Object[] objectArray = (Object[]) obj;
                         p.setIdusuario(new Integer(objectArray[0]!=null?objectArray[0].toString():"-1"));
                         p.setNombres(objectArray[1].toString());
                         if (p.getIdusuario().toString().equals("-1")) continue;
                         lst.add(p);
                  }
                 
                 return lst;
        }

	@SuppressWarnings("unchecked")
	@Override
	public List<FilaBandejaEnviados> buscarEnviadosPorUsuario(Usuario objUsuario) {
                
                String sql = "SELECT e FROM FilaBandejaEnviados e WHERE e.idPropietario = :idusuario and e.unidadPropietario = :idunidadpropietario and e.cargoPropietario  =:idcargopropietario " +
					 " AND e.fechaRecepcion >= :fechaDesde AND e.fechaRecepcion <= :fechaHasta ORDER BY e.fechaRecepcion DESC";

		Calendar fechaDesde = Calendar.getInstance();
		fechaDesde.setTimeInMillis(fechaDesde.getTimeInMillis() - 90*Constantes.MILISEGUNDOS_DIA);
		fechaDesde.set(Calendar.HOUR_OF_DAY, 0);
		fechaDesde.set(Calendar.MINUTE, 0);
		fechaDesde.set(Calendar.SECOND, 0);

		Calendar fechaHasta = Calendar.getInstance();
		fechaHasta.set(Calendar.HOUR_OF_DAY, 23);
		fechaHasta.set(Calendar.MINUTE, 59);
		fechaHasta.set(Calendar.SECOND, 59);

		return em.createQuery(sql).setParameter("idusuario", objUsuario.getIdUsuarioPerfil())
                                                                  .setParameter("idunidadpropietario", objUsuario.getIdUnidadPerfil())
                                                                  .setParameter("idcargopropietario", objUsuario.getIdFuncionPerfil())
                                                                  .setParameter("fechaDesde", fechaDesde.getTime())
								  .setParameter("fechaHasta", fechaHasta.getTime())
								  .getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FilaBandejaEnviados> buscarEnviadosFiltrados(Usuario objUsuario, BusquedaAvanzada objFiltro) {
		String sql = "SELECT e FROM FilaBandejaEnviados e WHERE e.idPropietario = :idusuario and e.unidadPropietario = :idunidadpropietario and e.cargoPropietario =:idcargopropietario ";

               //1
		if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
			sql += "AND LOWER(e.nroExpediente) LIKE :nroExpediente ";
		}
		//2
		if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
			sql += "AND LOWER(e.nroDocumento) LIKE :nroDocumento ";
		}
		//3
		if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
			sql += "AND e.idTipoDocumento = :idTipoDocumento ";
		}
		//4
		if(!StringUtils.isEmpty(objFiltro.getCliente())){
			sql += "AND e.cliente.idCliente = :idCliente ";
		}
		//5
		if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
			sql += "AND LOWER(e.asunto) LIKE :asuntoDocumento ";
		}
		//6
		if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
			sql += "AND LOWER(e.asuntoExpediente) LIKE :asuntoExpediente ";
		}
		//7
		if(!StringUtils.isEmpty(objFiltro.getUsuarioDestinatario())){
			sql += "AND LOWER(e.idDestinatario) = :idDestinatario ";
		}
		//8
		if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
                       sql += "AND e.fechaRecepcion >= :fechaDesde ";
		}
		//9
		if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
			sql += "AND e.fechaRecepcion <= :fechaHasta ";
		}
                
                if(!StringUtils.isEmpty(objFiltro.getNroHT())){
			sql += " AND e.nroTramite = :nroHT ";
		}

		sql += "  ORDER BY e.fechaRecepcion DESC ";

		Query q = em.createQuery(sql);
		q.setParameter("idusuario", objUsuario.getIdUsuarioPerfil())
                .setParameter("idunidadpropietario", objUsuario.getIdUnidadPerfil())
                .setParameter("idcargopropietario", objUsuario.getIdFuncionPerfil());

		//1
		if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
			q.setParameter("nroExpediente", "%"+objFiltro.getNumeroExpediente().toLowerCase()+"%");
		}
		//2
		if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
			q.setParameter("nroDocumento", "%"+objFiltro.getNumeroDocumento().toLowerCase()+"%");
		}
		//3
		if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
			q.setParameter("idTipoDocumento", Integer.parseInt(objFiltro.getTipoDocumento()));
		}
		//4
		if(!StringUtils.isEmpty(objFiltro.getCliente())){
			q.setParameter("idCliente", Integer.parseInt(objFiltro.getCliente()));
		}
		//5
		if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
			q.setParameter("asuntoDocumento", "%"+objFiltro.getAsuntodocumento().toLowerCase()+"%");
		}
		//6
		if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
			q.setParameter("asuntoExpediente", "%"+objFiltro.getAsuntoexpediente().toLowerCase()+"%");
		}
		//7
		if(!StringUtils.isEmpty(objFiltro.getUsuarioDestinatario())){
			q.setParameter("idDestinatario", Integer.parseInt(objFiltro.getUsuarioDestinatario()));
		}
                
                if(!StringUtils.isEmpty(objFiltro.getNroHT())){
                        q.setParameter("nroHT", Integer.parseInt(objFiltro.getNroHT()));
                }
                
		//8
		if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
                    	SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
			Calendar fecha = Calendar.getInstance();
			try {
				fecha.setTime(fechita.parse(objFiltro.getFechaDesde()));
				fecha.set(Calendar.HOUR_OF_DAY, 0);
				fecha.set(Calendar.MINUTE, 0);
				fecha.set(Calendar.SECOND, 0);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			q.setParameter("fechaDesde", fecha.getTime());
		}
		//9
		if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
			SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
			Calendar fecha = Calendar.getInstance();
			try {
				fecha.setTime(fechita.parse(objFiltro.getFechaHasta()));
				fecha.set(Calendar.HOUR_OF_DAY, 23);
				fecha.set(Calendar.MINUTE, 59);
				fecha.set(Calendar.SECOND, 59);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			q.setParameter("fechaHasta", fecha.getTime());
		}

		return q.getResultList();
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}

}
