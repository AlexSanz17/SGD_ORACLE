/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import java.util.List;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.UsuarioDerivacion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
/**
 *
 * @author consultor_jti15
 */

@Repository
public class UsuarioxunidadxfuncionDAOImpl implements UsuarioxunidadxfuncionDAO{
    private EntityManager em;
    
    
    public EntityManager getEm() {
	return em;
    }

    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em) {
		this.em = em;
    }
    
    public List<UsuarioDerivacion> getUsuarioDerivacion(){
            List<UsuarioDerivacion> l1 = new ArrayList<UsuarioDerivacion>();
            
            try{
                 String  sql = "select u.idusuario || '-' || uu.idunidad || '-' || u.idfuncion idp  , uu.nombre  || ' [' ||  u.nombres || ' ' || u.apellidos || ']' as usuario  from usuario u, unidad uu, funcion f " +
                           " where " +
                           "   U.IDUNIDAD = UU.IDUNIDAD and " +
                           "   U.IDFUNCION = F.IDFUNCION and " +
                           "   u.usuariofinal = 'S' AND " +
                           "   u.estado = 'A' and (F.jefe = '1' or  u.idRol = 8) "  +
                           " union "  +
                           " select u.idusuario || '-' || uu.idunidad || '-' || uf.idfuncion idp, uu.nombre  || ' [' || u.nombres || ' ' || u.apellidos || ']' as usuario   from usuario u, usuarioxunidadxfuncion uf, unidad uu, funcion f " +
                           " where " +
                           "   u.estado='A' AND " +
                           "   u.idusuario = uf.idusuario and " +
                           "   uf.idunidad = uu.idunidad and " +
                           "   uf.idfuncion = f.idfuncion and " +
                           "   uf.estado = 'A' and " +
                           "   uf.usuariofinal = 'S' and ((uf.idusuariocargo is null and f.jefe = '1') or (uf.idRol = 8)) ";
   
             Query q = em.createNativeQuery(sql.toString());
             List<Object> res = (List<Object>) q.getResultList();
	      
             for (Object obj : res) {     
                UsuarioDerivacion usuarioDerivacion = new UsuarioDerivacion(); 
		Object[] objectArray = (Object[]) obj;
            	usuarioDerivacion.setIdentificador(objectArray[0].toString());
                usuarioDerivacion.setNombreUsuario(objectArray[1].toString());
                l1.add(usuarioDerivacion);
	     }
             
        }catch(Exception e){
   	   e.printStackTrace();
	 }

	 return l1;
    }
    
     public List<Map<String, String>> getAllUsuario(Integer idunidad, String idjefe, Integer idfuncion){
          List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
            
          try{
                 String  sql = "select u.idusuario || '-' || uu.idunidad || '-' || u.idfuncion idp  , u.nombres, u.apellidos , f.nombre as cargo, uu.nombre as area, uu.siglaunidad  from usuario u, unidad uu, funcion f " +
                           " where " +
                           "   U.IDUNIDAD = UU.IDUNIDAD and " +
                           "   U.IDFUNCION = F.IDFUNCION and " +
                           "   u.usuariofinal in ('S','N') AND " +
                           "   u.estado = 'A' and FNC_USUARIO_VALIDO(U.IDUNIDAD, F.JEFE, :idunidad, :idjefe, U.IDFUNCION, :idfuncion)>0 "  +
                           " union "  +
                           " select u.idusuario || '-' || uu.idunidad || '-' || uf.idfuncion idp, u.nombres, u.apellidos,f.nombre as cargo, uu.nombre as area, uu.siglaunidad  from usuario u, usuarioxunidadxfuncion uf, unidad uu, funcion f " +
                           " where " +
                           "   u.estado='A' AND " +
                           "   u.idusuario = uf.idusuario and " +
                           "   uf.idunidad = uu.idunidad and " +
                           "   uf.idfuncion = f.idfuncion and " +
                           "   uf.estado = 'A' and " +
                           "   uf.usuariofinal IN ('S','N') and FNC_USUARIO_VALIDO(UF.IDUNIDAD, F.JEFE, :idunidad, :idjefe, uf.idfuncion, :idfuncion)>0 and uf.idusuariocargo is null";  
   
             Query q = em.createNativeQuery(sql.toString());
             q.setParameter("idunidad", idunidad).setParameter("idjefe", idjefe).setParameter("idfuncion", idfuncion);
             List<Object> res = (List<Object>) q.getResultList();
	     Map<String, String> datos = null;
              
             for (Object obj : res) {     
		datos = new HashMap<String, String>();
		Object[] objectArray = (Object[]) obj;
            	datos.put("id", objectArray[0].toString());
                datos.put("label", objectArray[1].toString() + " " + objectArray[2].toString() + " [" + objectArray[3].toString() + "][" + objectArray[5].toString() + "]");
                datos.put("nombres", objectArray[1].toString() + " " + objectArray[2].toString());
                datos.put("cargo", objectArray[3].toString());
                datos.put("area", objectArray[4].toString());
                l1.add(datos);
	     }
             
        }catch(Exception e){
   	   e.printStackTrace();
	 }

	 return l1;
     }
    
    // BUSCAR PARA
    public List<Map<String, String>> getAllUsuarioUnidadFuncion(Integer idunidad, String idjefe, Integer idfuncion){
            List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
            
            try{
                 String  sql = "select u.idusuario || '-' || uu.idunidad || '-' || u.idfuncion idp  , u.nombres, u.apellidos , f.nombre as cargo, uu.nombre as area, uu.siglaunidad  from usuario u, unidad uu, funcion f " +
                           " where " +
                           "   U.IDUNIDAD = UU.IDUNIDAD and " +
                           "   U.IDFUNCION = F.IDFUNCION and " +
                           "   u.usuariofinal = 'S' AND " +
                           "   u.estado = 'A' and FNC_USUARIO_VALIDO(U.IDUNIDAD, F.JEFE, :idunidad, :idjefe, U.IDFUNCION, :idfuncion)>0 "  +
                           " union "  +
                           " select u.idusuario || '-' || uu.idunidad || '-' || uf.idfuncion idp, u.nombres, u.apellidos,f.nombre as cargo, uu.nombre as area, uu.siglaunidad  from usuario u, usuarioxunidadxfuncion uf, unidad uu, funcion f " +
                           " where " +
                           "   u.estado='A' AND " +
                           "   u.idusuario = uf.idusuario and " +
                           "   uf.idunidad = uu.idunidad and " +
                           "   uf.idfuncion = f.idfuncion and " +
                           "   uf.estado = 'A' and " +
                           "   uf.usuariofinal = 'S' and FNC_USUARIO_VALIDO(UF.IDUNIDAD, F.JEFE, :idunidad, :idjefe, uf.idfuncion, :idfuncion)>0 and uf.idusuariocargo is null";  
   
             Query q = em.createNativeQuery(sql.toString());
             q.setParameter("idunidad", idunidad).setParameter("idjefe", idjefe).setParameter("idfuncion", idfuncion);
             List<Object> res = (List<Object>) q.getResultList();
	     Map<String, String> datos = null;
              
             for (Object obj : res) {     
		datos = new HashMap<String, String>();
		Object[] objectArray = (Object[]) obj;
            	datos.put("id", objectArray[0].toString());
                datos.put("label", objectArray[1].toString() + " " + objectArray[2].toString() + " [" + objectArray[3].toString() + "][" + objectArray[5].toString() + "]");
                datos.put("nombres", objectArray[1].toString() + " " + objectArray[2].toString());
                datos.put("cargo", objectArray[3].toString());
                datos.put("area", objectArray[4].toString());
                l1.add(datos);
	     }
             
        }catch(Exception e){
   	   e.printStackTrace();
	 }

	 return l1;
    }
    
    
    public List<Usuarioxunidadxfuncion> getUsuarioByUnidadByFuncionRol(Usuario usuario){
         List<Usuarioxunidadxfuncion> lista = new ArrayList<Usuarioxunidadxfuncion>();
         
         try{
               String  sql = "SELECT DISTINCT idRol, idusuario from " +
                            "(select idRol, idusuario from usuario where idUsuario = :idUsuario and idUnidad= :idUnidad and idFuncion= :idFuncion and estado = 'A' " +
                            " union " +
                            " select idRol, decode(idusuariocargo,null,idusuario,idusuariocargo) idusuario from usuarioxunidadxfuncion where idUsuario= :idUsuario and idUnidad= :idUnidad and idFuncion = :idFuncion and  " +
                            " estado = 'A'  ";
                       
               
               if (!usuario.getIdusuario().toString().equals(usuario.getIdUsuarioPerfil().toString())){
                   sql = sql +  " and idusuariocargo=" + usuario.getIdUsuarioPerfil() + " ) ";
               }else{
                   sql = sql + " )";
               }
               
               
               Query q = em.createNativeQuery(sql.toString());
	       q.setParameter("idUsuario", usuario.getIdusuario()).setParameter("idUnidad", usuario.getIdUnidadPerfil()).setParameter("idFuncion", usuario.getIdFuncionPerfil()); 
               List<Object> res = (List<Object>) q.getResultList();
	       Usuarioxunidadxfuncion f  = null;
               
               for (Object obj : res) {
		f = new Usuarioxunidadxfuncion();
                Object[] objectArray = (Object[]) obj;
                f.setIdrol(Integer.parseInt(objectArray[0].toString())); 
                lista.add(f);
	     }
               
         }catch(Exception e){
             e.printStackTrace();
             //throw e;
         }
             
         return lista;
     }
    
    
     public List<Usuarioxunidadxfuncion> getUsuarioByUnidadByFuncionListRol(Usuario usuario){
         List<Usuarioxunidadxfuncion> lista = new ArrayList<Usuarioxunidadxfuncion>();
         
         try{
               String  sql = "SELECT DISTINCT idRol, idusuario from " +
                            "(select idRol, idusuario from usuario where idUsuario = :idUsuario and idUnidad= :idUnidad and idFuncion= :idFuncion and estado = 'A' " +
                            " union " +
                            " select idRol, idusuario from usuarioxunidadxfuncion where idUsuario= :idUsuario and idUnidad= :idUnidad and idFuncion = :idFuncion and idusuariocargo is null and " +
                            " estado = 'A' ) ";
               
               Query q = em.createNativeQuery(sql.toString());
	       q.setParameter("idUsuario", usuario.getIdusuario()).setParameter("idUnidad", usuario.getIdUnidadPerfil()).setParameter("idFuncion", usuario.getIdFuncionPerfil()); 
               List<Object> res = (List<Object>) q.getResultList();
	       Usuarioxunidadxfuncion f  = null;
               
               for (Object obj : res) {
		 f = new Usuarioxunidadxfuncion();
                 Object[] objectArray = (Object[]) obj;
                 f.setIdrol(Integer.parseInt(objectArray[0].toString())); 
                 lista.add(f);
	      }
               
         }catch(Exception e){
             e.printStackTrace();
             //throw e;
         }
             
         return lista;
     }
    
    //combo principal
    public List<Usuarioxunidadxfuncion> getUsuarioByUnidadByFuncion(Usuario usuario){
        List<Usuarioxunidadxfuncion> lista = new ArrayList<Usuarioxunidadxfuncion>();
        
        try{    
	     String  sql =  "select distinct idunidad,desunidad,idfuncion,desfuncion,idusuario, jefe, idRol, datos from (" +
                            " SELECT u.idunidad, u.nombre as desunidad, f.idfuncion, f.nombre as desfuncion , idusuario, f.jefe, uf.idRol, '' datos  FROM usuario uf, unidad u, funcion f " +
                            " WHERE " + 
                            " uf.idusuario =:idusuario and  " +
                            "     uf.idunidad = u.idunidad and  " +
                            "      uf.idfuncion   = f.idfuncion "  +
                            "  union " +
                            "SELECT u.idunidad, u.nombre as desunidad, f.idfuncion, f.nombre as desfuncion, decode(idusuariocargo,null,idusuario,idusuariocargo), f.jefe, uf.idRol, decode(idusuariocargo, null, '', (select uu.nombres || ' ' || uu.apellidos  from usuario uu where uu.idusuario = idusuariocargo)) datos  " +
                            "  FROM usuarioxunidadxfuncion uf, unidad u, funcion f " +
                            "   where " + 
                            "      uf.idusuario =:idusuario and " +
                            "      uf.idunidad = u.idunidad and " +
                            "      uf.idfuncion   = f.idfuncion and uf.estado = 'A' )";


             Query q = em.createNativeQuery(sql.toString());
	     q.setParameter("idusuario", usuario.getIdusuario()); 
             List<Object> res = (List<Object>) q.getResultList();
	     Usuarioxunidadxfuncion f  = null;
          
             for (Object obj : res) {
                f = new Usuarioxunidadxfuncion();
		Object[] objectArray = (Object[]) obj;
		f.setIdunidad(Integer.parseInt(objectArray[0].toString()));
                f.setDescunidad(objectArray[1].toString());
                f.setIdfuncion(Integer.parseInt(objectArray[2].toString()));
                f.setDescfuncion(objectArray[3].toString());
                f.setIdusuariocargo(Integer.parseInt(objectArray[4].toString()));
                f.setJefe(objectArray[5].toString());
                f.setIdrol(Integer.parseInt(objectArray[6].toString()));
                f.setDatos(objectArray[7]==null?"":objectArray[7].toString());
                lista.add(f);
	     }
	 }catch(Exception e){
   	   e.printStackTrace();
	 }

	 return lista;
    }
    
     //combo principal
    public List<Usuarioxunidadxfuncion> getUsuarioByUnidadByFuncionDelegado(Usuario usuario){
        List<Usuarioxunidadxfuncion> lista = new ArrayList<Usuarioxunidadxfuncion>();
        
        try{ 
            String  sql =  "select distinct idunidad,desunidad,idfuncion,desfuncion,idusuario, jefe, idRol, datos from (" +
                            " SELECT u.idunidad, u.nombre as desunidad, f.idfuncion, f.nombre as desfuncion, decode(idusuariocargo,null,idusuario,idusuariocargo) idusuario, f.jefe, uf.idRol,  decode(idusuariocargo, null, '', (select uu.nombres || ' ' || uu.apellidos  from usuario uu where uu.idusuario = idusuariocargo)) datos " +
                            "  FROM usuarioxunidadxfuncion uf, unidad u, funcion f " +
                            "   where " + 
                            "      uf.idusuario =:idusuario and " +
                            "      uf.idunidad = u.idunidad and " +
                            "      uf.idfuncion   = f.idfuncion and uf.estado = 'A'  and f.jefe = '1' " +
                            "  union " +
                            " SELECT u.idunidad, u.nombre as desunidad, f.idfuncion, f.nombre as desfuncion, decode(idusuariocargo,null,idusuario,idusuariocargo) idusuario, f.jefe, uf.idRol , decode(idusuariocargo, null, '', (select uu.nombres || ' ' || uu.apellidos  from usuario uu where uu.idusuario = idusuariocargo)) datos " +
                            "  FROM usuarioxunidadxfuncion uf, unidad u, funcion f " +
                            "   where " + 
                            "      uf.idusuario =:idusuario and " +
                            "      uf.idunidad = u.idunidad and " +
                            "      uf.idfuncion   = f.idfuncion and uf.estado = 'A' and f.jefe = '0' " +
                            " union " +
                            " SELECT u.idunidad, u.nombre as desunidad, f.idfuncion, f.nombre as desfuncion , idusuario, f.jefe, uf.idRol, '' datos FROM usuario uf, unidad u, funcion f " +
                            " WHERE " + 
                            " uf.idusuario =:idusuario and  " +
                            " uf.idunidad = u.idunidad and  " +
                            " uf.idfuncion   = f.idfuncion )" ;   

            
            /*
	     String  sql =  "select distinct idunidad,desunidad,idfuncion,desfuncion,idusuario, jefe, idRol from (" +
                            " SELECT u.idunidad, u.nombre as desunidad, f.idfuncion, f.nombre as desfuncion, decode(idusuariocargo,null,idusuario,idusuariocargo) idusuario, f.jefe, uf.idRol " +
                            "  FROM usuarioxunidadxfuncion uf, unidad u, funcion f " +
                            "   where " + 
                            "      uf.idusuario =:idusuario and " +
                            "      uf.idunidad = u.idunidad and " +
                            "      uf.idfuncion   = f.idfuncion and uf.estado = 'A' and uf.idFuncion not in (5) and f.jefe = '1' " +
                            "  union " +
                            " SELECT u.idunidad, u.nombre as desunidad, f.idfuncion, f.nombre as desfuncion, decode(idusuariocargo,null,idusuario,idusuariocargo) idusuario, f.jefe, uf.idRol " +
                            "  FROM usuarioxunidadxfuncion uf, unidad u, funcion f " +
                            "   where " + 
                            "      uf.idusuario =:idusuario and " +
                            "      uf.idunidad = u.idunidad and " +
                            "      uf.idfuncion   = f.idfuncion and uf.estado = 'A' and uf.idFuncion in (5) " +
                            "  union " +
                            " SELECT u.idunidad, u.nombre as desunidad, f.idfuncion, f.nombre as desfuncion, decode(idusuariocargo,null,idusuario,idusuariocargo) idusuario, f.jefe, uf.idRol " +
                            "  FROM usuarioxunidadxfuncion uf, unidad u, funcion f " +
                            "   where " + 
                            "      uf.idusuario =:idusuario and " +
                            "      uf.idunidad = u.idunidad and " +
                            "      uf.idfuncion   = f.idfuncion and uf.estado = 'A' and f.jefe = '0' " +
                            " union " +
                            " SELECT u.idunidad, u.nombre as desunidad, f.idfuncion, f.nombre as desfuncion , idusuario, f.jefe, uf.idRol FROM usuario uf, unidad u, funcion f " +
                            " WHERE " + 
                            " uf.idusuario =:idusuario and  " +
                            " uf.idunidad = u.idunidad and  " +
                            " uf.idfuncion   = f.idfuncion )" ;   
              */
             Query q = em.createNativeQuery(sql.toString());
	     q.setParameter("idusuario", usuario.getIdusuario()); 
             List<Object> res = (List<Object>) q.getResultList();
	     Usuarioxunidadxfuncion f  = null;
             
             for (Object obj : res) {
		f = new Usuarioxunidadxfuncion();
		Object[] objectArray = (Object[]) obj;
		f.setIdunidad(Integer.parseInt(objectArray[0].toString()));
                f.setDescunidad(objectArray[1].toString());
                f.setIdfuncion(Integer.parseInt(objectArray[2].toString()));
                f.setDescfuncion(objectArray[3].toString());
                f.setIdusuariocargo(Integer.parseInt(objectArray[4].toString()));
                f.setJefe(objectArray[5].toString());
                f.setIdrol(Integer.parseInt(objectArray[6].toString()));
                f.setDatos(objectArray[7]==null?"":objectArray[7].toString());
                lista.add(f);
	     }
	 }catch(Exception e){
   	   e.printStackTrace();
	 }

	 return lista;
    }
}
