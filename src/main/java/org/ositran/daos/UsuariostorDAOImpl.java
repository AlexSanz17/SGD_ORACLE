/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.ositran.utils.Constantes;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Rol;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.UsuarioStor;

@Repository
public class UsuariostorDAOImpl implements UsuariostorDAO{
	
	private EntityManager em;
	private static Logger log=Logger.getLogger(UsuariostorDAOImpl.class);

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	public UsuarioStor getResponsableSala(Integer iIdSala,char cResponsable){
		return (UsuarioStor) em.createNamedQuery("Usuariostor.getResponsableSala").setParameter("idsala",iIdSala).setParameter("responsable",cResponsable).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getAnalistasBySala(String sala){
		// List<UsuarioStor> lstAnalista = new ArrayList<UsuarioStor>();
		List<Usuario> lstAnalistaN=new ArrayList<Usuario>();
		Rol rol=(Rol) em.createNamedQuery("Rol.findByNombre").setParameter("nombre",Constantes.ROL_USUARIO_ANALISTA_STOR).getSingleResult();
		int idRolAnalista=rol.getIdrol().intValue();
		log.info("idRolAnalista: "+idRolAnalista);
		/*
		 * String sql1 = "SELECT NEW org.ositran.utils.UsuarioStor("
		 * +"u.idusuario," +"u.rol.idrol," +"u.nombres," +"u.apellidos,"
		 * +"us.sala.idsala," +"us.nroapelaciones," +"us.nroquejas,"
		 * +"us.nrocautelares)" +" FROM Usuario u" +" JOIN u.usuariostor us"
		 * +" WHERE us.sala="+sala +" AND u.rol="+idRolAnalista;
		 */
		String sql="SELECT u from UsuarioStor u WHERE "+idRolAnalista+" in elements(u.roles) and u.sala="+sala;
		log.debug("Nuevo sql JPA: "+sql);
		// lstAnalista = (List<UsuarioStor>)em.createQuery(sql).getResultList();
		lstAnalistaN=em.createQuery(sql).getResultList();
		log.info("Cantidad de Analistas: "+lstAnalistaN.size());
		return lstAnalistaN;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getRevisoresTecnicosBySala(String sala){
		// List<UsuarioStor> lstRevTecBySala = new ArrayList<UsuarioStor>();
		List<Usuario> lstRevTecBySala=new ArrayList<Usuario>();
		Rol rol=(Rol) em.createNamedQuery("Rol.findByNombre").setParameter("nombre",Constantes.ROL_USUARIO_REVTEC_STOR).getSingleResult();
		int idRolRevisorTecnico=rol.getIdrol().intValue();
		log.info("idRolRevisorTecnico: "+idRolRevisorTecnico);
		/*
		 * List<Usuario> lstRevtec =
		 * (List<Usuario>)getEm().createNamedQuery("Usuario.findByRol")
		 * .setParameter("rol",idRolRevisorTecnico) .getResultList(); Integer
		 * idSala = new Integer(sala);
		 */
		/*
		 * String sql = "SELECT NEW org.ositran.utils.UsuarioStor("
		 * +"u.idusuario," +"u.rol.idrol," +"u.nombres," +"u.apellidos,"
		 * +"us.sala.idsala," +"us.nroapelaciones," +"us.nroquejas,"
		 * +"us.nrocautelares)" +" FROM Usuario u" +" JOIN u.usuariostor us"
		 * +" WHERE us.sala="+sala +" AND u.rol="+idRolRevisorTecnico;
		 */
		String sql="SELECT u from UsuarioStor u WHERE "+idRolRevisorTecnico+" in elements(u.roles) and u.sala="+sala;
		log.info("sql JPA: "+sql);
		lstRevTecBySala=em.createQuery(sql).getResultList();
		/*
		 * List<Usuariostor> lstAllBySala =
		 * (List<Usuariostor>)getEm().createNamedQuery
		 * ("Usuariostor.getAllBySala") .setParameter("idsala", idSala)
		 * .getResultList();
		 * 
		 * log.info("Total usuarios por sala: "+lstAllBySala.size());
		 * 
		 * for(Iterator itAllSala = lstAllBySala.iterator();
		 * itAllSala.hasNext();){ Usuariostor usuariostor =
		 * (Usuariostor)itAllSala.next(); Usuario usuario =
		 * usuariostor.getUsuario();
		 * if(usuario.getRol().getIdrol().intValue()==idRolRevisorTecnico){
		 * lstRevTecBySala.add(usuario); } }
		 */
		log.info("Total de Revisores tecnicos por sala:"+sala+" "+lstRevTecBySala.size());
		return lstRevTecBySala;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getRevisoresLegalesBySala(String sala){
		// List<UsuarioStor> lstRevLegBySala = new ArrayList<UsuarioStor>();
		List<Usuario> lstRevLegBySala=new ArrayList<Usuario>();
		Rol rol=(Rol) em.createNamedQuery("Rol.findByNombre").setParameter("nombre",Constantes.ROL_USUARIO_REVLEG_STOR).getSingleResult();
		int idRolRevisorLegal=rol.getIdrol().intValue();
		log.info("idRolRevisorLegal: "+idRolRevisorLegal);
		/*
		 * Integer idSala = new Integer(sala); String sql =
		 * "SELECT NEW org.ositran.utils.UsuarioStor(" +"u.idusuario,"
		 * +"u.rol.idrol," +"u.nombres," +"u.apellidos," +"us.sala.idsala,"
		 * +"us.nroapelaciones," +"us.nroquejas," +"us.nrocautelares)"
		 * +" FROM Usuario u" +" JOIN u.usuariostor us" +" WHERE us.sala="+sala
		 * +" AND u.rol="+idRolRevisorLegal;
		 */
		String sql="SELECT u from UsuarioStor u WHERE "+idRolRevisorLegal+" in elements(u.roles) and u.sala="+sala;
		log.info("sql JPA: "+sql);
		lstRevLegBySala=em.createQuery(sql).getResultList();
		/*
		 * List<Usuariostor> lstAllBySala =
		 * (List<Usuariostor>)getEm().createNamedQuery
		 * ("Usuariostor.getAllBySala") .setParameter("idsala", idSala)
		 * .getResultList();
		 * 
		 * log.info("Total usuarios por sala: "+lstAllBySala.size());
		 * 
		 * for(Iterator itAllSala = lstAllBySala.iterator();
		 * itAllSala.hasNext();){ Usuariostor usuariostor =
		 * (Usuariostor)itAllSala.next(); Usuario usuario =
		 * usuariostor.getUsuario();
		 * if(usuario.getRol().getIdrol().intValue()==idRolRevisorLegal){
		 * lstRevLegBySala.add(usuario); } }
		 */
		log.info("Total de Revisores tecnicos por sala "+sala+": "+lstRevLegBySala.size());
		return lstRevLegBySala;
	}

	public Usuario getAsistenteSala(Integer idSala){
		Usuario asala=null;
		Rol rol=(Rol) em.createNamedQuery("Rol.findByNombre").setParameter("nombre",Constantes.ROL_USUARIO_ASALA_STOR).getSingleResult();
		int idRolAsistenteSala=rol.getIdrol().intValue();
		log.info("idRolAsistenteSala: "+idRolAsistenteSala);
		String sql="SELECT u from UsuarioStor u WHERE "+idRolAsistenteSala+" in elements(u.roles) and u.sala="+idSala;
		asala=(Usuario) em.createQuery(sql).getSingleResult();
		/*
		 * List<Usuariostor> lstAllBySala =
		 * (List<Usuariostor>)getEm().createNamedQuery
		 * ("Usuariostor.getAllBySala") .setParameter("idsala", idSala)
		 * .getResultList();
		 * 
		 * log.info("Total usuarios por sala: "+lstAllBySala.size());
		 * for(Iterator itAllSala = lstAllBySala.iterator();
		 * itAllSala.hasNext();){ Usuariostor usuariostor =
		 * (Usuariostor)itAllSala.next(); Usuario usuario =
		 * usuariostor.getUsuario();
		 * if(usuario.getRol().getIdrol().intValue()==idRolAsistenteSala){ asala
		 * = usuariostor; break; } }
		 */
		log.info("Id del asistente de sala: "+asala.getIdusuario());
		return asala;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getListUsurioStorByRol(String nameRol){
		List<Usuario> lstUsurioStor=new ArrayList<Usuario>();
		Rol rol=(Rol) em.createNamedQuery("Rol.findByNombre").setParameter("nombre",nameRol).getSingleResult();
		//Unidad unidad=(Unidad) em.createNamedQuery("Unidad.findByNombre").setParameter("nombre","stor").getSingleResult();
		log.debug("idRolUsurioStor: "+rol.getIdrol());
		//log.info("idUnidadStor: "+unidad.getIdunidad());
		String jpqlQuery="SELECT u FROM Usuario u WHERE "+rol.getIdrol()+" in elements(u.roles) order by idusuario";
		lstUsurioStor=em.createQuery(jpqlQuery).getResultList();
		log.debug("Query: "+jpqlQuery);
		log.debug("Cantidad de usuarios Stor con el rol: "+nameRol+" "+lstUsurioStor.size());
		return lstUsurioStor;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}

	@Override
	public UsuarioStor encontrarPorIdUsuario(int idUsuario){
		UsuarioStor usuario=null;
		try{
			usuario=(UsuarioStor) em.createNamedQuery("Usuariostor.findByIdusuario").setParameter("idusuario",idUsuario).getSingleResult();
		}
		catch(NoResultException e){
			log.warn("No se encontro al usuarioStor con idUsuario: "+idUsuario);
		}
		return usuario;
	}

	@Override
	public UsuarioStor guardarUsuarioStor(UsuarioStor usuario){
		return em.merge(usuario);
	}

	@Override
	public UsuarioStor encontrarPorUsuario(String usuario){
		UsuarioStor usu=null;
		try{
			usu=(UsuarioStor) em.createNamedQuery("Usuariostor.findByUsuario").setParameter("usuario",usuario).getSingleResult();
		}
		catch(NoResultException e){
			log.warn("No se encontro al usuarioStor con usuario: "+usuario);
		}
		return usu;
	}
	
	
	@Override
	public void crearUsuarioExistente(int idUsuario,int idSala){
		String sql="INSERT INTO usuariostor (idusuario,sala,responsable) VALUES ("+idUsuario+","+idSala+",'N')";
		em.createNativeQuery(sql).executeUpdate();		
	}
}
