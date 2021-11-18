package org.ositran.services;

import java.util.List;
import java.util.Map;
import org.ositran.dojo.tree.Nodo;

import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.seguridad.UsuarioSeguridad;

public interface UsuarioService{
        public List<Usuario> findUsuariosxUnidad(Integer idUnidad);
        
        public List<Map<String, String>> findUnidadOrgSession(int id);
        
        public Usuario findByUsuarioClave(String strUsuario,String strClave);
        
        public boolean findValidarUsuario(String usuario, String clave);

	public Boolean esResponsableProceso(Integer iIdUsuario);

	public boolean validarSesionEnIntalio(Map<String,Object> sesion);

	public List<Usuario> getUsuariosByUnidad(String unidad);

	public List<Nodo> getJerarquia();

	public List<Usuario> findListByUsuarioFinal(char cUsuarioFinal);

	public List<Usuario> findUsuariosByIdJefe(Integer iIdJefe);

	public List<Usuario> getUsuarios();

	public List<Usuario> getUsuarioList(String strRol);

	List<Usuario> getDestinatariosXJerarquia(Usuario usuario);

	List<Usuario> findByApellidosNombresLike(String like);

	public Map<Integer,String> getMapByUsuarioFinal(char cUsuarioFinal);

	public Map<Integer,String> getMapUsuarioFinalLeft(List<Usuario> lstContacto);

	public Map<Integer,String> getMapUsuarioFinalRight(List<Usuario> lstContacto);

	public Usuario findByIdUsuario(Integer iIdU);

	public List<Usuario> findByRol(String strR);

	public Usuario findByRolUnico(String rol);

	public Usuario findByUsuario(String sUsuario,Integer iIdSede);

	public Usuario findByUsuario(String sUsuario);

	public void saveUsuario(Usuario objUsuarioOld,Usuario objUsuarioNew,List<Integer> rolesUsuario,String sUsuarioSesion,String sTipoAuditoria,String usuarioAnterior);

	public List<Usuario> findByParticipacionProceso(Proceso proceso);

	public boolean esJefe(Usuario usuario);

	public void sincronizarUsuariosSeguridad();

	public void sincronizarUsuarioSeguridad(UsuarioSeguridad usuarioSeguridad);

	public Usuario guardarUsuario(Usuario objUsuario);

	public void guardarUsuariosLDAP();

   public Map<String, Integer> resolveSomeResources(Usuario objUsuario, Map<String, Integer> mapRecurso);

   public Usuario findObjectBy(String usuario, Character estado);

   public Usuario guardarObj(Usuario objUsuario,List<Integer> lstAdministradorListaRight,List<Integer> lstParticipanteListaRight);

   public List<Usuario> buscarUsuariosCompartidos(Integer id);

   public List<Usuario> findAllUF();

   public List<Usuario> findJefes();
   public List<Map<String, String>> findUsuariosxUniOrg(int id);
}