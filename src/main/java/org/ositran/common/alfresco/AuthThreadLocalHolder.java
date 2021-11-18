/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.common.alfresco;

import com.btg.ositran.siged.domain.Usuario;

/**
 *
 * @author javier castillo
 */
public class AuthThreadLocalHolder {

    /** Thread local con el usuario */
    private static ThreadLocal<Usuario> usuarioThreadLocal = new ThreadLocal<Usuario>();

    public static void setUsuario(Usuario usuario) {
        AuthThreadLocalHolder.usuarioThreadLocal.set(usuario);
    }

    public static Usuario getUsuario() {
        return AuthThreadLocalHolder.usuarioThreadLocal.get();
    }
    
    public static void removeUsuario() {
        AuthThreadLocalHolder.usuarioThreadLocal.remove();
    }
}
