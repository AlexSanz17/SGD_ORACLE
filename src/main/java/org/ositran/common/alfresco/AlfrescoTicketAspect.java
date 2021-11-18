/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.common.alfresco;

import gob.ositran.siged.config.SigedProperties;
import gob.ositran.siged.service.SeguridadService;
import org.alfresco.webservice.authentication.AuthenticationFault;
import org.alfresco.webservice.util.AuthenticationDetails;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.btg.ositran.siged.domain.Usuario;

/**
 * Aspecto que mantiene un ticket de Alfresco en sesión web
 * y en el hilo actual mediante el uso de AuthenticationUtils de Alfresco
 * que coloca el ticket en ThreadLocal.
 *
 * @author javier castillo
 */
public class AlfrescoTicketAspect {

    private static final Logger logger = LoggerFactory.getLogger(AlfrescoTicketAspect.class);

    private SeguridadService seguridadService;

    public void refrescarTicketAlfresco() {
        AuthenticationDetails authenticationDetails = AuthenticationUtils.getAuthenticationDetails();
        // verificar si existe authentication details de Alfresco
        if (authenticationDetails != null) {
            if (authenticationDetails instanceof AuthenticationDetailsExtended) {
                AuthenticationDetailsExtended ade = (AuthenticationDetailsExtended) authenticationDetails;
                if (ade.isNoValido()) {
                    iniciarSesionAlfresco();
                } else if (ade.isTicketExpirado()) {
                    // el ticket ya expiro en Alfresco, se procede a removerlo y crear uno nuevo
                    try {
                        AuthenticationUtils.endSession();
                        logger.info("Se cerro sesion en Alfresco por time-out. Ticket: {}", authenticationDetails);
                    } catch (Exception ex) {
                        logger.info("Error al terminar sesion de Alfresco. " + authenticationDetails);
                    }
                    iniciarSesionAlfresco();
                } else {
                    // refrescar ultimo acceso
                    ade.resetearTiempoTimeout();
                    logger.info("Ticket de Alfresco reseteado. Ticket: {}", authenticationDetails);
                }
            } else {
                // se ha iniciado sesion en Alfresco pero no se puede garantizar de que la sesion es valida
                // porque no se ha usado la clase AuthenticationDetailsExtended
                try {
                    AuthenticationUtils.endSession();
                    logger.info("Se cerro sesion en Alfresco por ticket posiblemente invalido. Ticket: {} {}", authenticationDetails.getUserName(), authenticationDetails.getTicket());
                } catch (Exception ex) {
                    logger.info("Error al terminar sesion de Alfresco.");
                }
                iniciarSesionAlfresco();
            }
        } else {
            // no se ha iniciado sesion en Alfresco, proceder a hacerlo
            iniciarSesionAlfresco();
        }
    }

    private void iniciarSesionAlfresco() {
        Usuario usuario = (Usuario) AuthThreadLocalHolder.getUsuario();
        if (usuario == null) {
            throw new RuntimeException("Se intento iniciar sesion en Alfresco sin tener un usuario AuthThreadLocalHolder.");
        }
        String user = usuario.getUsuario();
        String pass = usuario.getClave();
        try {
            AuthenticationUtils.startSession(user, seguridadService.desencriptar(user, pass));
            AuthenticationDetails ad = AuthenticationUtils.getAuthenticationDetails();
            // colocar en el ThreadLocal la version extendida de AuthenticationDetails con datos de tiempo de expiracion
            AuthenticationDetailsExtended ade = new AuthenticationDetailsExtended(ad, SigedProperties.getLongProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_VALID_DURATION_IN_MINUTES) * 60000);
            AuthenticationUtils.setAuthenticationDetails(ade);    
            logger.info("Sesion en Alfresco iniciada. " + ade);
        } catch (AuthenticationFault ex) {
            logger.error("Error al iniciar sesion de alfresco con usuario: " + user, ex);
        }
    }

    public void setSeguridadService(SeguridadService seguridadService) {
        this.seguridadService = seguridadService;
    }

}
