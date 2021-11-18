/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.common.alfresco;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.alfresco.webservice.util.AuthenticationDetails;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.ositran.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.btg.ositran.siged.domain.Usuario;

/**
 *
 * @author javier castillo
 */
public class AlfrescoTicketFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AlfrescoTicketFilter.class);
    public static final String ALFRESCO_AUTH_DETAILS = "ALFRESCO_AUTH_DETAILS";

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain fc) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session != null) {
            Usuario usuario = (Usuario) session.getAttribute(Constantes.SESSION_USUARIO);
            if (usuario != null) {
                // colocar el usuario en ThreadLocal
                AuthThreadLocalHolder.setUsuario(usuario);
            }
            AuthenticationDetailsExtended ade = (AuthenticationDetailsExtended) session.getAttribute(ALFRESCO_AUTH_DETAILS);
            if (ade != null) {
                // colocar el AuthenticationDetailsExtended obtenido de sesion web en el ThreadLocal
                // mediante el metodo de AuthenticationUtils de Alfresco, con esto aseguramos que estara
                // disponible en el hilo actual para las llamadas a los web service de Alfresco
                AuthenticationUtils.setAuthenticationDetails(ade);
                log.debug("Ticket de Alfresco encontrado en sesion web y asignado en ThreadLocal. {}", ade);
            } else {
                // si el ticket de Alfresco no se encuentra en sesion hay que verificar si existe en ThreadLocal
                AuthenticationDetails ad = AuthenticationUtils.getAuthenticationDetails();
                if (ad != null) {
                    try {
                        log.debug("Ticket de Alfresco NO encontrado en sesion web pero si ThreadLocal. Se reemplaza el ticket de ThreadLocal. Ticket: {}", AuthenticationUtils.getTicket());
                        AuthenticationUtils.setAuthenticationDetails(new AuthenticationDetailsExtended(true));
                    } catch (Exception ex) {
                        log.error("Error al terminar session de Alfresco. Ticket: " + AuthenticationUtils.getTicket(), ex);
                    }
                }
            }
        }
        try {
            fc.doFilter(request, response);
        } finally {
            // eliminar el usuario del ThreadLocal
            AuthThreadLocalHolder.removeUsuario();

            try {
                session = request.getSession();
            } catch (Exception e) {
                session = null;
            }
            if (session != null) {
                // actualizar el valor de AuthenticationDetailsExtended en sesion web porque puede haber cambiado
                AuthenticationDetails ad = AuthenticationUtils.getAuthenticationDetails();

                if (ad != null && ad instanceof AuthenticationDetailsExtended) {
                    session.setAttribute(ALFRESCO_AUTH_DETAILS, ad);
                    log.debug("Ticket de Alfresco encontrado en ThreadLocal se coloco en sesion web. {}", ad);
                }
            }
        }
    }
}
