/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Usuario;
import java.util.Map;

public interface UsuarioDAO{
    
        public Usuario buscarObjPor(String sUsuario,String sClave);
    public List<Map<String, String>> findUnidadOrgSession(int id);


	public List<Usuario> findAll();

	public boolean findValidarUsuario(String usuario, String clave);

	public List<Usuario> findListByUsuarioFinal(char cUsuarioFinal);

	public List<Usuario> findParticipanteXProceso(Integer iProceso);

	public List<Usuario> findUsuariosByIdJefe(Integer iIdJefe);

	List<Usuario> findByApellidosNombresLike(String like);
        
        public List<Usuario> findUsuariosxUnidad(Integer idUnidad);

	public Usuario findByIdUsuario(Integer iIdU);

	public List<Usuario> findByRol(String strR);

	public Usuario findByRolUnico(String rol);

	public Usuario findByUsuario(String sUsuario,Integer iIdSede);

	public Usuario findByUsuario(String usuario);

	public Usuario findByUnidad(String unidad);

	public Usuario saveUsuario(Usuario objUsuario);

	public List<Usuario> getListaUsuariosFinales(char usuariofinal);

	public List<Usuario> getUsuariosByUnidad(String unidad);

	public List<Usuario> findByParticipacionProceso(Proceso proceso);

	public Usuario findFirstByRol(String descripcionrol);

	public Usuario findObjectBy(String usuario, Character estado);

   public List<Usuario> buscarUsuariosCompartidos(Integer id);

   public List<Usuario> findAllUF();

   public List<Usuario> findJefes();

    public List<Map<String, String>> findUsuariosxUniOrg(int id);

}