/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.ositran.utils.Constantes;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Usuario;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Repository
public class UsuarioDAOImpl implements UsuarioDAO{

	private static Logger _log=Logger.getLogger(UsuarioDAOImpl.class);
	private EntityManager em;

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	@SuppressWarnings("unchecked")
	public List<Usuario> findAll(){
		return em.createNamedQuery("Usuario.findAll").getResultList();
	}

	public List<Usuario> findAllUF(){
		return em.createNamedQuery("Usuario.findAllUF").getResultList();
	}
        
        public Usuario buscarObjPor(String sUsuario,String sClave){
		Usuario objUsuario=null;
		
		try{
                     objUsuario=(Usuario) em.createNamedQuery("Usuario.autenticarClaveSiged").setParameter("usr",sUsuario).getSingleResult();

		}catch(NoResultException e){
			e.printStackTrace();
			_log.warn("No se encontró el usuario: "+sUsuario+" con contraseña: "+sClave);
		}
                
                return objUsuario;
	}
        
        public List<Map<String, String>> findUnidadOrgSession(int id){
         List<Map<String, String>> s = new LinkedList<Map<String, String>>();
         try {
           
            String sql =" SELECT IDUNIDAD, NOMBRE " +
            " FROM UNIDAD WHERE UNIDADGRUPO = :uni"
                    + " AND ESTADO='A' ";
            
            Query q = em.createNativeQuery(sql);
            q.setParameter("uni",id);
            
             List<Object> res = (List<Object>) q.getResultList();
                     Map<String, String> datos = null;
              
             for (Object obj : res) {     
                               datos = new HashMap<String, String>();
                               Object[] objectArray = (Object[]) obj;
                datos.put("id", objectArray[0].toString());
                datos.put("label", objectArray[1].toString() );
               
                s.add(datos);
                     }
           
          } catch (Exception e) {
              e.printStackTrace();
             _log.error(UsuarioDAOImpl.class.toString().concat(": ").concat(e.getMessage()));
             return null;
          }
          return s;
        }     



	@SuppressWarnings("unchecked")
	public List<Usuario> findListByUsuarioFinal(char cUsuarioFinal){
		return em.createNamedQuery("Usuario.findByUsuariofinal").setParameter("usuariofinal",cUsuarioFinal).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findParticipanteXProceso(Integer iProceso){
		return em.createNamedQuery("Usuario.findParticipanteXProceso").setParameter("idproceso",iProceso.intValue()).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findUsuariosByIdJefe(Integer iIdJefe){
		return em.createNamedQuery("Usuario.findByIdJefe").setParameter("idjefe",iIdJefe).getResultList();
	}

	public Usuario findByIdUsuario(Integer iIdU){
		return em.find(Usuario.class,iIdU);
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findByRol(String strR){
                return null; //JC24
		//return em.createNamedQuery("Usuario.findByRol").setParameter("rol",strR).getResultList();
	}

	public Usuario findByUsuario(String sUsuario,Integer iIdSede){
		Usuario usuario=null;
		try{
			usuario=(Usuario) em.createNamedQuery("Usuario.findByUsuario").setParameter("usuario",sUsuario).setParameter("idsede",iIdSede).getSingleResult();
		}catch(NonUniqueResultException e){
			_log.warn("El usuario buscado ("+sUsuario+") con idSede="+iIdSede+" tiene mas de una coincidencia en la base de datos");
		}
		return usuario;
	}

	public Usuario saveUsuario(Usuario objUsuario){

		if(objUsuario.getIdusuario()==null){
			em.persist(objUsuario); // Nuevo
			em.flush();
			em.refresh(objUsuario);
		}else{
			em.merge(objUsuario); // Actualizacion
			em.flush();
		}

		return objUsuario;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getUsuariosByUnidad(String unidad){
		return em.createQuery("select u from Usuario u where u.unidad.idunidad = :unidadP").setParameter("unidadP",Integer.valueOf(unidad)).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getListaUsuariosFinales(char usuariofinal){
		return em.createNamedQuery("Usuario.findAllFinales").setParameter("usuariofinal",usuariofinal).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> findByParticipacionProceso(Proceso proceso){
		return em.createNamedQuery("Usuario.findByParticipacionProceso").setParameter("proceso",proceso).getResultList();
	}

	public Usuario findByUnidad(String unidad){
		Usuario usuario=null;
		try{
			usuario=(Usuario) em.createNamedQuery("Usuario.findByUnidad")
			                    .setParameter("idunidad",Integer.parseInt(unidad))
			                    .getSingleResult();
		}catch(NoResultException e){
			_log.warn("La Unidad  buscado ("+unidad+") no se puedo lograr encontrar");
		}
		return usuario;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}

	@Override
	public Usuario findByUsuario(String usuario){
		Query q=em.createQuery("SELECT u FROM Usuario u WHERE upper(u.usuario) = :usuario");
		q.setParameter("usuario",usuario);
		Usuario usu=null;
		try{
			usu=(Usuario) q.getSingleResult();
		}catch(NoResultException e){
			_log.warn("No se encontro al Usuario con usuario: "+usuario);
		}
		return usu;
	}

	@Override
	public Usuario findByRolUnico(String rol){
		List<Usuario> usuarios=findByRol(rol);
		if(usuarios!=null && usuarios.size()==1){
			return usuarios.get(0);
		}
		return null;
	}

	@Override
	public Usuario findFirstByRol(String descripcionrol) {
		return null;
                  //Query q = em.createNamedQuery("Usuario.findFirstByRol").setParameter("descripcionrol", descripcionrol);
		//return (Usuario) q.getSingleResult();
	}

   @Override
   public Usuario findObjectBy(String usuario, Character estado) {
      try {
         return (Usuario) em.createNamedQuery("Usuario.findByUsuarioEstado")
                            .setParameter("usuario", usuario.toLowerCase())
                            .setParameter("estado", String.valueOf(estado))
                            .getSingleResult();
      } catch (NoResultException nre) {
         _log.warn("No se encontro registro con usuario [" + usuario + "] estado [" + estado + "]");

         return null;
      } catch (NonUniqueResultException nure) {
         _log.warn("Se encontro mas de un registro con usuario [" + usuario + "] estado [" + estado + "]");

         return null;
      }
   }


   @Override
   public List<Usuario> buscarUsuariosCompartidos(Integer id) {
      try {
        String sql = "select u.idusuario, u.nombres, u.apellidos  from compartidoxusuario cu inner join ".concat(
                "usuario u on cu.usuario= u.idusuario where cu.asignado=:asignado");
        Query q = em.createNativeQuery(sql);
        q.setParameter("asignado", id);
        List temp = q.getResultList();
        Usuario u = null;
        List<Usuario> data = new ArrayList<Usuario>();
        for (int i = 0; i < temp.size(); i++) {
                u = new Usuario();
                Object obj[] = (Object[]) temp.get(i);
                u.setIdusuario(Integer.parseInt(obj[0].toString()));
                u.setNombres(obj[1].toString());
                u.setApellidos(obj[2].toString());
                data.add(u);
          }
        return data;
      } catch (Exception e) {
          e.printStackTrace();
         _log.error(UsuarioDAOImpl.class.toString().concat(": ").concat(e.getMessage()));
         return null;
      }
   }

   public boolean findValidarUsuario(String usuario, String clave){
	   try {
                 String sql = "select idusuario  from usuario where ".concat(
                       " usuario ='"  +  usuario + "'  and  clave_siged = '" + clave + "'  and estado='A'"); 
		  
	         Query q = em.createNativeQuery(sql);
	         List temp = q.getResultList();
 	         if (temp!=null && temp.size()>0)
	            return true;
 	      } catch (Exception e) {
	    	 e.printStackTrace();
	         return false;
	      }

	    return false; 
   }

   @SuppressWarnings("unchecked")
   public List<Usuario> findByApellidosNombresLike(String like){
	   if(like == null){
		   return new ArrayList<Usuario>();
	   }
	   String sql = "SELECT u FROM Usuario u WHERE (LOWER(u.nombres) LIKE :like OR LOWER(u.apellidos) LIKE :like) AND u.usuariofinal = :usuarioFinal";

	   return em.createQuery(sql)
	   			.setParameter("like", "%"+like.toLowerCase()+"%")
	   			.setParameter("usuarioFinal", Constantes.Si.charValue())
	   			.getResultList();
   }


   	@SuppressWarnings("unchecked")
	public List<Usuario> findJefes() {
   		//return em.createNamedQuery("Usuario.findJefes").setParameter("esJefe",Constantes.Si.charValue()).getResultList();

        try {
            String sql = "select Distinct(u.idusuario), u.nombres, u.apellidos  from usuario u inner join usuarioxrol ur on u.idusuario = ur.idusuario inner join rol r on r.idRol =  ur.idRol where r.esJefe =:esJefe AND u.estado='A' order by apellidos  ";
            Query q = em.createNativeQuery(sql);
            q.setParameter("esJefe",Constantes.ROL_ES_JEFE);
            List temp = q.getResultList();
            Usuario u = null;
            List<Usuario> data = new ArrayList<Usuario>();
            for (int i = 0; i < temp.size(); i++) {
                    u = new Usuario();
                    Object obj[] = (Object[]) temp.get(i);
                    u.setIdusuario(Integer.parseInt(obj[0].toString()));
                    u.setNombres(obj[1].toString());
                    u.setApellidos(obj[2].toString());
                    data.add(u);
              }
            return data;
          } catch (Exception e) {
              e.printStackTrace();
             _log.error(UsuarioDAOImpl.class.toString().concat(": ").concat(e.getMessage()));
             return null;
          }
   	}
        
  public List<Usuario> findUsuariosxUnidad(Integer idUnidad){
      List<Usuario> lstUsuario = new ArrayList<Usuario>();
      
      String sql = "select IDUSUARIO, NOMBRES || ' ' || APELLIDOS AS NOMBRES   from usuario where IDUNIDAD =:idUnidad AND ESTADO = 'A' " +
                   " UNION " +
                   " SELECT IDUSUARIO, (SELECT U.NOMBRES || ' ' || U.APELLIDOS  AS NOMBRES FROM USUARIO U  WHERE U.IDUSUARIO = UU.IDUSUARIO)  FROM USUARIOXUNIDADXFUNCION UU WHERE UU.IDUNIDAD = :idUnidad AND UU.ESTADO = 'A' AND UU.IDUSUARIOCARGO IS NULL ";
  
      Query q = em.createNativeQuery(sql.toString());
      q.setParameter("idUnidad", idUnidad);
      List<Object> res = (List<Object>) q.getResultList();
                  
      for (Object obj : res) {    
         Object[] objectArray = (Object[]) obj;
         Usuario usuario = new Usuario();
         usuario.setIdusuario(new Integer(objectArray[0].toString()));
         usuario.setNombres(objectArray[1].toString());
         lstUsuario.add(usuario);
      }
  
      return lstUsuario;
  }      
        
  public List<Map<String, String>> findUsuariosxUniOrg(int id){
         List<Map<String, String>> s = new LinkedList<Map<String, String>>();
         try {
           
            String sql =" SELECT M.idusuario|| '-' ||M.IDUNIDAD|| '-' ||M.idfuncion, M.nombres, M.apellidos " +
                        " FROM USUARIO M " +
                        " WHERE M.ESTADO = 'A' AND M.USUARIOFINAL = 'S' and M.idusuario not in (0) AND M.IDUNIDAD = :uni" +
                        " union " +
                        " select us.idusuario|| '-' ||us.IDUNIDAD|| '-' ||uf.idfuncion, us.nombres, us.apellidos " +
                        " from usuarioxunidadxfuncion uf, usuario us ,UNIDAD un" +
                        " where " +
                        " uf.idusuario = us.idusuario and uf.estado = 'A' AND " +
                        " uf.IDUNIDAD  = un.IDUNIDAD  and un.ESTADO = 'A' AND " +
                        " uf.USUARIOFINAL = 'S' and uf.idusuariocargo is null and " +
                        " un.unidadgrupo = :uni";
            
            Query q = em.createNativeQuery(sql);
            q.setParameter("uni",id);          
            
             List<Object> res = (List<Object>) q.getResultList();
	            Map<String, String> datos = null;
              
             for (Object obj : res) {     
		datos = new HashMap<String, String>();
		Object[] objectArray = (Object[]) obj;
            	datos.put("id", objectArray[0].toString());
                //datos.put("label", objectArray[1].toString() + " " + objectArray[2].toString() + " [" + objectArray[3].toString() + "][" + objectArray[5].toString() + "]");
                datos.put("nombres", objectArray[1].toString() + " " + objectArray[2].toString());
               
                s.add(datos);
	     }
           
          } catch (Exception e) {
              e.printStackTrace();
             _log.error(UsuarioDAOImpl.class.toString().concat(": ").concat(e.getMessage()));
             return null;
          }
          return s;
        }
}