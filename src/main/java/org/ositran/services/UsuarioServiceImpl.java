package org.ositran.services;

import gob.ositran.siged.service.SeguridadService;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.ositran.daos.PerfilDAO;
import org.ositran.daos.RolDAO;
import org.ositran.daos.SalaDAO;
import org.ositran.daos.SedeDAO;
import org.ositran.daos.UnidadDAO;
import org.ositran.daos.UsuarioDAO;
import org.ositran.daos.UsuariostorDAO;
import org.ositran.daos.seguridad.DaoUsuario;
import org.ositran.dojo.tree.Nodo;
import org.ositran.dojo.tree.Referencia;
import org.ositran.ldap.ControladorLdap;
import org.ositran.utils.Constantes;
import org.ositran.utils.StringUtil;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Rol;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.UsuarioStor;
import com.btg.ositran.siged.domain.seguridad.RolSeguridad;
import com.btg.ositran.siged.domain.seguridad.UnidadSeguridad;
import com.btg.ositran.siged.domain.seguridad.UsuarioSeguridad;

public class UsuarioServiceImpl implements UsuarioService {

    private static Logger log = Logger.getLogger(UsuarioServiceImpl.class);
    private UsuarioDAO dao;
    private ProcesoService srvProceso;
    private AuditoriaService srvAuditoria;
    private DaoUsuario daoUsuario;
    private RolDAO rolDao;
    private UnidadDAO unidadDao;
    private PerfilDAO perfilDao;
    private SedeDAO sedeDao;
    private UsuariostorDAO usuarioStorDao;
    private SalaDAO salaDao;
    private SeguridadService seguridadService;

    // ////////////////////////////////
    // Constructors //
    // ////////////////////////////////
    public UsuarioServiceImpl(UsuarioDAO dao) {
        setDao(dao);
    }

    // ////////////////////////////////
    // Methods //
    // ////////////////////////////////
    public List<Usuario> findUsuariosxUnidad(Integer idUnidad){
        return getDao().findUsuariosxUnidad(idUnidad);
    }
    
    public Boolean esResponsableProceso(Integer iIdUsuario) {
        List<Proceso> lstProceso = srvProceso.buscarLstPor(iIdUsuario);
        log.debug("esResponsableProceso.llstProceso :" + lstProceso);
        if (lstProceso != null) {
            log.debug("El usuario [" + iIdUsuario + "] es responsable de [" + lstProceso.size() + "] procesos");
        }
        return (lstProceso == null || lstProceso.size() < 1) ? false : true;
    }
    
     public Usuario findByUsuarioClave(String strUsuario, String strClave) throws RuntimeException {
    	  return getDao().buscarObjPor(strUsuario, strClave);
    }
     
     public List<Map<String, String>> findUnidadOrgSession(int id){
         List<Map<String, String>> ls = dao.findUnidadOrgSession(id);

        return ls;
   }


    public List<Usuario> getUsuariosByUnidad(String unidad){
    	return dao.getUsuariosByUnidad(unidad);
    }

    public List<Nodo> getJerarquia() {
        List<Nodo> lstArbol = null;
        List<Referencia> lstChildren = null;
        List<Usuario> lstUsuario = this.getUsuarios();
        if (lstUsuario != null) {
            log.debug("Numero de Usuarios en el sistema [" + lstUsuario.size() + "]");
            lstArbol = new ArrayList<Nodo>();
            for (Usuario objUsuario : lstUsuario) {
                Nodo objNodo = new Nodo(Boolean.FALSE, objUsuario.getIdusuario(), objUsuario.getUsuario(), null);
                List<Usuario> lstEmpleado = this.findUsuariosByIdJefe(objUsuario.getIdusuario());
                log.debug("Nodo con ID [" + objUsuario.getIdusuario() + "] usuario [" + objUsuario.getUsuario() + "]");
                if (objUsuario.getJefe() == null) {
                    objNodo.setTop(Boolean.TRUE);
                }
                if (lstEmpleado != null && lstEmpleado.size() > 0) {
                    log.debug("SubNodos [" + lstEmpleado.size() + "]");
                    lstChildren = new ArrayList<Referencia>();
                    for (Usuario objEmpleado : lstEmpleado) {
                        Referencia objReferencia = new Referencia(objEmpleado.getIdusuario());
                        lstChildren.add(objReferencia);
                        log.debug("[" + objEmpleado.getIdusuario() + "]");
                    }
                    objNodo.setChildren(lstChildren);
                }
                lstArbol.add(objNodo);
            }
        }
        return lstArbol;
    }

    public List<Usuario> getDestinatariosXJerarquia(Usuario usuario){
    	List<Usuario> lista = new ArrayList<Usuario>();
    	Usuario jefe = usuario.getJefe();
    	/**REN Agregamos a todos los usuarios que estan debajo de el ------------------------------*/
    	lista = getUsuariosDependientes(lista, usuario);
    	if(jefe != null){
    		/**REN Agregamos al jefe a la lista de destinatarios ----------------------------------*/
    		lista.add(jefe);
    		/**REN Agregamos a todos los usuarios de su mismo nivel--------------------------------*/
    		List<Usuario> lstEmpleados = this.findUsuariosByIdJefe(jefe.getIdusuario());
    		if(lstEmpleados != null && !lstEmpleados.isEmpty()){
    			for(Usuario objUsuario : lstEmpleados){
    				if(objUsuario.getIdusuario().intValue() != usuario.getIdusuario().intValue()){
    					lista.add(objUsuario);
    				}
    			}
    		}
    	}
    	return lista;
    }

    private List<Usuario> getUsuariosDependientes(List<Usuario> lista, Usuario usuario){
    	List<Usuario> lstEmpleado = this.findUsuariosByIdJefe(usuario.getIdusuario());
    	if(lstEmpleado != null && !lstEmpleado.isEmpty()){
    		for(Usuario empleado : lstEmpleado){
    			lista.add(empleado);
    			lista = getUsuariosDependientes(lista, empleado);
    		}
    	}
    	return lista;
    }

    public List<Usuario> findByApellidosNombresLike(String like){
    	return dao.findByApellidosNombresLike(like);
    }



    public boolean findValidarUsuario(String usuario, String clave){
    	return dao.findValidarUsuario(usuario, clave);
    }

    public List<Usuario> findListByUsuarioFinal(char cUsuarioFinal) {
        return getDao().findListByUsuarioFinal(cUsuarioFinal);
    }

    public List<Usuario> findUsuariosByIdJefe(Integer iIdJefe) {
        return getDao().findUsuariosByIdJefe(iIdJefe);
    }

    public List<Usuario> getUsuarios() {
        return getDao().findAll();
    }

    public List<Usuario> getUsuarioList(String strRol) throws RuntimeException {
        try {
            if (strRol == null) {
                return getDao().findAll();
            }
            return getDao().findByRol(strRol);
        } catch (RuntimeException re) {
            log.error("", re);
            return null;
        }
    }

    public Map<Integer, String> getMapByUsuarioFinal(char cUsuarioFinal) {
        Map<Integer, String> mapUsuarioFinal = new LinkedHashMap<Integer, String>();
        log.debug("Flag usuario final [" + cUsuarioFinal + "]");
        List<Usuario> lstUsuarioFinal = this.findListByUsuarioFinal(cUsuarioFinal);
        log.debug("Lista de usuarios finales con size [" + lstUsuarioFinal.size() + "]");
        for (Usuario objUsuario : lstUsuarioFinal) {
            mapUsuarioFinal.put(objUsuario.getIdusuario(), objUsuario.getApellidos() + " " + objUsuario.getNombres());
        }
        return mapUsuarioFinal;
    }

    public Map<Integer, String> getMapUsuarioFinalLeft(List<Usuario> lstContacto) {
        Boolean bFound;
        Iterator<Entry<Integer, String>> it = null;
        Map<Integer, String> mapUsuario = this.getMapByUsuarioFinal(Constantes.FLAG_USUARIO_FINAL_S);
        Map<Integer, String> mapUsuarioReturn = new LinkedHashMap<Integer, String>();
        it = mapUsuario.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Integer, String> entry = it.next();
            bFound = false;
            Integer iKey = entry.getKey();
            if (!lstContacto.isEmpty()) {
                for (Usuario objUsuario : lstContacto) {
                    if (objUsuario.getIdusuario() == iKey.intValue()) {
                        bFound = true;
                        break;
                    }
                }
            }
            if (bFound == false) {
                mapUsuarioReturn.put(entry.getKey(), entry.getValue());
            }
        }
        return mapUsuarioReturn;
    }

    public Map<Integer, String> getMapUsuarioFinalRight(List<Usuario> lstContacto) {
        Map<Integer, String> mapUsuarioReturn = new LinkedHashMap<Integer, String>();
        for (Usuario objU : lstContacto) {
            mapUsuarioReturn.put(objU.getIdusuario(), objU.getNombres() + " " + objU.getApellidos());
        }
        return mapUsuarioReturn;
    }

    public Usuario findByIdUsuario(Integer iIdU) throws RuntimeException {
        try {
            return getDao().findByIdUsuario(iIdU);
        } catch (RuntimeException re) {
            log.error("", re);
            return null;
        }
    }

    public List<Usuario> findByRol(String strR) {
        return dao.findByRol(strR);
    }

    public Usuario findByUsuario(String sUsuario, Integer iIdSede) {
        return dao.findByUsuario(sUsuario, iIdSede);
    }

    public Usuario findByUsuario(String sUsuario) {
    	return dao.findByUsuario(sUsuario);
    }

    @Transactional
    public void saveUsuario(Usuario objUsuarioOld, Usuario objUsuarioNew, List<Integer> rolesUsuario, String sUsuarioSesion, String sTipoAuditoria, String usuarioAnterior) {
        List<Rol> roles = new ArrayList<Rol>();
        if (rolesUsuario != null) {
            for (Integer idRol : rolesUsuario) {
                roles.add(rolDao.findByIdRol(idRol));
            }
        }
       /* if (objUsuarioNew.getIdusuario() != null) {
            if (objUsuarioNew.getRoles() != null) {
                objUsuarioNew.getRoles().clear();
            }
        }
        objUsuarioNew.setRoles(roles);*/
        try {
            srvAuditoria.aplicarAuditoria(objUsuarioOld, objUsuarioNew, sUsuarioSesion, Constantes.AUDITORIA_OPCION_GUARDAR, sTipoAuditoria);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        objUsuarioNew = dao.saveUsuario(objUsuarioNew);
        log.debug("Usuario guardado con ID [" + objUsuarioNew.getIdusuario() + "] Usuario [" + objUsuarioNew.getUsuario() + "] Nombre [" + objUsuarioNew.getNombres() + " " + objUsuarioNew.getApellidos() + "]");
        //List<Rol> roles=objUsuarioNew.getRoles();
        String[] rol = new String[roles.size()];
        for (int i = 0; i < rol.length; i++) {
            rol[i] = roles.get(i).getNombre();
        }
        /**REN Quitar el comentario si es que se quiere guardar el usuario en LDAP ---------------------------------------------*/
        /*if (!ControladorLdap.guardarUsuario(objUsuarioNew.getUsuario(), seguridadService.desencriptar(objUsuarioNew.getUsuario(), objUsuarioNew.getClave()), objUsuarioNew.getNombres(), objUsuarioNew.getApellidos(), objUsuarioNew.getCorreo(), rol, usuarioAnterior)) {
            log.error("No se pudo guardar el usuario en el directorio.");
        }*/
    }

    public List<Usuario> findByParticipacionProceso(Proceso proceso) {
        return dao.findByParticipacionProceso(proceso);
    }

    @Transactional
    public Usuario guardarUsuario(Usuario objUsuario) {
        return dao.saveUsuario(objUsuario);
    }

    public Map<String, Integer> resolveSomeResources(Usuario objUsuario, Map<String, Integer> mapRecurso) {
        if (mapRecurso.containsKey("UsuFinMnuLista")) {
            log.debug("Modificando accesibilidad del recurso [UsuFinMnuLista] para el usuario [" + objUsuario.getUsuario() + "]. Administra [" + objUsuario.getListaAdministrador().size() + "] lista(s)");

            if (objUsuario.getListaAdministrador().size() <= 0) {
                mapRecurso.put("UsuFinMnuLista", 0);
            }
        }

        return mapRecurso;
    }

    // ////////////////////////////////
    // Getters and Setters //
    // ////////////////////////////////
    public UsuarioDAO getDao() {
        return dao;
    }

    public void setDao(UsuarioDAO dao) {
        this.dao = dao;
    }

    public ProcesoService getSrvProceso() {
        return srvProceso;
    }

    public void setSrvProceso(ProcesoService srvProceso) {
        this.srvProceso = srvProceso;
    }

    public AuditoriaService getSrvAuditoria() {
        return srvAuditoria;
    }

    public void setSrvAuditoria(AuditoriaService srvAuditoria) {
        this.srvAuditoria = srvAuditoria;
    }

    /**
     * Retorna si este usuario es o no jefe
     *
     * @param usuario
     *            el usuario a analizar
     * @return es o no jefe
     * @author German Enriquez
     */
    @Override
    public boolean esJefe(Usuario usuario) {
        List<Usuario> aCargo = usuario.getUsuariosACargo();
        if (aCargo != null) {
            if (aCargo.size() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void sincronizarUsuariosSeguridad() {
        List<UsuarioSeguridad> usuariosSeguridad = daoUsuario.getTodos();
        log.info("***********************************************");
        log.info("Total de Usuarios a Sincronizar:" + usuariosSeguridad.size());
        log.info("***********************************************");
        int x = 1;
        try {
            for (UsuarioSeguridad usuarioSeguridad : usuariosSeguridad) {
                log.info("Usuario[" + x + "]=" + usuarioSeguridad.getUsuario());
                log.info("Nombre[" + x + "]=" + usuarioSeguridad.getNombre());
                if (esUsuarioSiged(usuarioSeguridad)) {
                    sincronizarUsuarioSeguridad(usuarioSeguridad);
                }
                x++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e.fillInStackTrace());
        }
        log.info("***********************************************");
    }

    private boolean esUsuarioSiged(UsuarioSeguridad usuario) {
        List<RolSeguridad> roles = usuario.getRoles();
        for (RolSeguridad rol : roles) {
            if (esRolSiged(rol)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void sincronizarUsuarioSeguridad(UsuarioSeguridad usuarioSeguridad) {
        //primero registramos la unidad
        UnidadSeguridad unidadSeguridad = usuarioSeguridad.getUnidad();
        Unidad unidad = unidadDao.encontrarPorNombre(unidadSeguridad.getNombre());
        if (unidad == null) {
            unidad = crearUnidad(unidadSeguridad);
        }
        //asignamos los roles al usuario
        List<RolSeguridad> rolesSeguridad = usuarioSeguridad.getRoles();
        List<Rol> roles = new ArrayList<Rol>();
        boolean esUsuarioStor = false;
        for (RolSeguridad rolSeguridad : rolesSeguridad) {
            if (esRolSiged(rolSeguridad)) {
                if (!esUsuarioStor) {
                    esUsuarioStor = esRolStor(rolSeguridad);
                }
                Rol rol = rolDao.encontrarPorNombre(rolSeguridad.getNombre());
                if (rol == null) {
                    rol = crearRol(rolSeguridad);
                }
                roles.add(rol);
            }
        }
        Usuario usuario = null;
        String nombreUsuario = usuarioSeguridad.getUsuario().toUpperCase();
        if (!esUsuarioStor) {
            usuario = dao.findByUsuario(nombreUsuario);
            if (usuario == null) {
                usuario = crearUsuario(nombreUsuario);
            }
        } else {
            usuario = usuarioStorDao.encontrarPorUsuario(nombreUsuario);
            if (usuario == null) {
                Usuario sinStor = dao.findByUsuario(nombreUsuario);
                if (sinStor == null) {
                    usuario = crearUsuarioStor(nombreUsuario);
                } else {
                    usuarioStorDao.crearUsuarioExistente(sinStor.getIdusuario(), 1);
                    usuario = sinStor;
                }
            }
        }
        if (usuario != null) {
            usuario.setNombres(usuarioSeguridad.getNombre());
            usuario.setApellidos(" ");
            //necesitamos asignarle una contraseña para poder loguear en alfresco
            usuario.setClave(usuarioSeguridad.getClave());
            //TODO validar los usuarios que tienen un solo rol especial
            usuario.setUsuariofinal('S');
            usuario.setEstado(Constantes.ESTADO_ACTIVO);
//			usuario.setCorreo(usuarioSeguridad.getCorreo().toLowerCase());
            if (usuarioSeguridad.getCorreo() != null) {
                usuario.setCorreo(usuarioSeguridad.getCorreo().toLowerCase());
            }
            usuario.setUnidad(unidad);
            //usuario.setRoles(roles);
            dao.saveUsuario(usuario);

            String[] rol = new String[roles.size()];
            for (int i = 0; i < rol.length; i++) {
                rol[i] = roles.get(i).getNombre();
            }
            if (!ControladorLdap.guardarUsuario(usuario.getUsuario(), seguridadService.desencriptar(usuario.getUsuario(), usuario.getClave()), usuario.getNombres(), usuario.getApellidos(), usuario.getCorreo(), rol, null)) {
                log.error("No se pudo guardar el usuario " + usuario.getUsuario() + " en el directorio.");
            }
            log.info("Se sincronizo satisfactoriamente al usuario " + usuario.getUsuario());
        } else {
            log.error("No se pudo sincronizar al usuario " + usuarioSeguridad.getUsuario());
        }
    }

    private boolean esRolSiged(RolSeguridad rol) {
        return rol.getNombre().startsWith("Siged -");
    }

    private Unidad crearUnidad(UnidadSeguridad fuente) {
        Unidad unidad = new Unidad();
        unidad.setNombre(fuente.getNombre());
        unidad.setDescripcion(fuente.getNombre());
        unidad.setSede(sedeDao.buscarPorId(1));
        unidad.setDependencia(unidadDao.encontrarPorNombre(Constantes.UNIDAD_PRESIDENCIA));
        return unidadDao.guardarObj(unidad);
    }

    private Rol crearRol(RolSeguridad fuente) {
        Rol rol = new Rol();
        rol.setNombre(fuente.getNombre());
        rol.setDescripcion(fuente.getNombre());
        //FIXME ya no deberian existir perfiles
        rol.setIdperfil(perfilDao.findByIdPerfil(1));
        return rolDao.guardarObj(rol);
    }

    private boolean esRolStor(RolSeguridad rol) {
        String nombre = rol.getNombre();
        if (nombre.equals(Constantes.ROL_USUARIO_ANALISTA_STOR)
                || nombre.equals(Constantes.ROL_USUARIO_ASALA_STOR)
                || nombre.equals(Constantes.ROL_USUARIO_FINAL_STOR)
                || nombre.equals(Constantes.ROL_USUARIO_REVLEG_STOR)
                || nombre.equals(Constantes.ROL_USUARIO_REVTEC_STOR)
                || nombre.equals(Constantes.ROL_USUARIO_SCALIFICADOR_STOR)
                || nombre.equals(Constantes.ROL_USUARIO_SGENERAL_STOR)) {
            return true;
        }
        return false;
    }

    /*private boolean esRolEspecial(String rol) {
        if (rol.equals(Constantes.ROL_ADMINISTRADOR)
                || rol.equals(Constantes.ROL_DIGITALIZADOR)
                || rol.equals(Constantes.ROL_MESA_PARTES)
                || rol.equals(Constantes.ROL_QAS)
                || rol.equals(Constantes.ROL_MENSAJERIA)) {
            return true;
        }
        return false;
    }*/

    private Usuario crearUsuario(String nombreUsuario) {
        Usuario usuario = new Usuario();
        usuario.setUsuario(nombreUsuario);
        usuario.setEnviocorreo('N');
        return usuario;
    }

    private UsuarioStor crearUsuarioStor(String nombreUsuario) {
        UsuarioStor usuario = new UsuarioStor();
        usuario.setUsuario(nombreUsuario);
        usuario.setSala(salaDao.findByIdSala(1));
        return usuario;
    }

    /*private boolean esUsuarioFinal(List<Rol> roles) {
        if (roles.size() > 1) {
            return true;
        }
        return !esRolEspecial(roles.get(0).getNombre());
    }*/

    public void setRolDao(RolDAO rolDao) {
        this.rolDao = rolDao;
    }

    public void setDaoUsuario(DaoUsuario daoUsuario) {
        this.daoUsuario = daoUsuario;
    }

    public void setUnidadDao(UnidadDAO unidadDao) {
        this.unidadDao = unidadDao;
    }

    public void setPerfilDao(PerfilDAO perfilDao) {
        this.perfilDao = perfilDao;
    }

    public void setSedeDao(SedeDAO sedeDao) {
        this.sedeDao = sedeDao;
    }

    public void setUsuarioStorDao(UsuariostorDAO usuarioStorDao) {
        this.usuarioStorDao = usuarioStorDao;
    }

    public void setSalaDao(SalaDAO salaDao) {
        this.salaDao = salaDao;
    }

    @Override
    public Usuario findByRolUnico(String rol) {
        return dao.findByRolUnico(rol);
    }

    @SuppressWarnings("unused")
	@Override
    public boolean validarSesionEnIntalio(Map<String, Object> sesion) {
        String token = (String) sesion.get(Constantes.SESSION_INTALIO);
        if (token == null) {
            Usuario usuario = (Usuario) sesion.get(Constantes.SESSION_USUARIO);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void guardarUsuariosLDAP() {
        List<Usuario> todos = dao.findAll();
        for (Usuario usuario : todos) {
            List<Rol> roles = null;// usuario.getRoles();
            String[] rol = new String[roles.size()];
            for (int i = 0; i < rol.length; i++) {
                rol[i] = roles.get(i).getNombre();
            }
            if (!ControladorLdap.guardarUsuario(usuario.getUsuario(), seguridadService.desencriptar(usuario.getUsuario(), usuario.getClave()), usuario.getNombres(), usuario.getApellidos(), usuario.getCorreo(), rol, null)) {
                log.error("No se pudo guardar el usuario " + usuario.getUsuario() + " en el directorio.");
            }
        }
    }

    @Override
    public Usuario findObjectBy(String usuario, Character estado) {
        if (log.isDebugEnabled()) {
            log.debug("Buscando usuario con usuario [" + usuario + "] estado [" + estado + "]");
        }

        if (StringUtil.isEmpty(usuario) || estado == null) {
            return null;
        }

        return dao.findObjectBy(usuario, estado);
    }

    @Transactional
    public Usuario guardarObj(Usuario objUsuario, List<Integer> lstAdministradorListaRight, List<Integer> lstParticipanteListaRight) {
        List<Usuario> lstAdministrador = new ArrayList<Usuario>();
        List<Usuario> lstParticipante = new ArrayList<Usuario>();

        if (lstAdministradorListaRight != null) {
            for (Integer i : lstAdministradorListaRight) {
                Usuario u = findByIdUsuario(i);
                lstAdministrador.add(u);
            }
        }

        if (lstParticipanteListaRight != null) {
            for (Integer j : lstParticipanteListaRight) {
                Usuario u = findByIdUsuario(j);
                lstParticipante.add(u);
            }
        }
        objUsuario.setAdministradorxusuario(lstAdministrador);
        objUsuario.setCompartidoxusuario(lstParticipante);
        objUsuario = dao.saveUsuario(objUsuario);
        return objUsuario;
    }

    public List<Usuario> buscarUsuariosCompartidos(Integer id) {
        return getDao().buscarUsuariosCompartidos(id);
    }

    public List<Usuario> findAllUF() {
        return getDao().findAllUF();
    }

    public void setSeguridadService(SeguridadService seguridadService) {
        this.seguridadService = seguridadService;
    }

	@Override
	public List<Usuario> findJefes() {
		
                return dao.findJefes();
	}
    public List<Map<String, String>> findUsuariosxUniOrg(int id) {
        List<Map<String, String>> ls = dao.findUsuariosxUniOrg(id);

        return ls;
    }
}