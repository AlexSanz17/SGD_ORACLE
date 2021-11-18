package org.ositran.services;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Usuario;

/**
 *
 * @author developer
 */
public interface ManejoDeEmailService {

    public void EnviarEmail(String to, String from, String Subject, String msgText, String hostSmtp, boolean debug);
    public void enviarMail(String to,String from,String Subject,String msgText);
    public void ChaskiMail(int iTipoMail, Usuario objRemitente, Usuario objDestinatario, Documento objDocumento, String contenido, String accion);
    public void ChaskiMail(int iTipoMail, Usuario objRemitente, Usuario objDestinatario, Documento objDocumento, String carpeta);
}
