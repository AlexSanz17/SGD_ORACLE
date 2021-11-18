/*LICENCIA DE USO DEL SGD .TXT*/package gob.ositran.siged.service.impl;

import gob.ositran.siged.service.SeguridadService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author javier castillo
 */
@Transactional
public class SeguridadServiceImpl implements SeguridadService {

    @SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(SeguridadServiceImpl.class);
    private static final String ENCRIPTAR = "ENC";
    private static final String DESENCRIPTAR = "DES";
    
    @SuppressWarnings("unused")
	@PersistenceContext(unitName = "sigedPU")
    private EntityManager em;

    @Override
    public String encriptar(String usuario, String clave) {
        return encriptarSP(usuario, clave, ENCRIPTAR);
    }

    @Override
    public String desencriptar(String usuario, String clave) {
        return encriptarSP(usuario, clave, DESENCRIPTAR);
    }

    private String encriptarSP(String usuario, String clave, String mode) {
        /*String resultado = null;
        if (StringUtil.isEmpty(usuario) || StringUtil.isEmpty(clave) || StringUtil.isEmpty(mode)) {
            log.error("Se recibieron incorrectamente los parametros...");
        } else {
            try {
                resultado = (String) em.createNativeQuery("SELECT S0120300(?,?,?) FROM DUAL").setParameter(1, clave).setParameter(2, usuario).setParameter(3, mode).getResultList().get(0);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return (StringUtil.isEmpty(resultado) ? clave : resultado);*/
        
        return clave;
    }
}
