/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.SeguimientoXFirma;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.ActionContext;
import java.util.Date;
import java.util.Map;
import org.ositran.daos.SeguimientoXFirmaDAO;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author consultor_jti15
 */
public class SeguimientoXFirmaServiceImpl implements  SeguimientoXFirmaService{
        private SeguimientoXFirmaDAO seguimientoXFirmaDAO;
        
        @Transactional
        public void guardarSeguimiento(SeguimientoXFirma seguimientoXFirma){
             seguimientoXFirmaDAO.guardarSeguimiento(seguimientoXFirma);
        }

        @Transactional
        public void guardarSeguimiento(String[] ids){
            
          Map<String, Object> session = ActionContext.getContext().getSession();
          Usuario usuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
		  
          for(int i=0;i<ids.length;i++){
              SeguimientoXFirma seguimientoXFirma = seguimientoXFirmaDAO.findByIdDocumentoFirmado(new Integer(ids[i])); 
              seguimientoXFirma.setEstado("F");
              seguimientoXFirma.setFechaModificacion(new Date());
              seguimientoXFirma.setUsuarioModificacion(usuario.getIdusuario());
              seguimientoXFirmaDAO.guardarSeguimiento(seguimientoXFirma);
          }
        }
        
        public SeguimientoXFirmaDAO getSeguimientoXFirmaDAO() {
            return seguimientoXFirmaDAO;
        }

        public void setSeguimientoXFirmaDAO(SeguimientoXFirmaDAO seguimientoXFirmaDAO) {
            this.seguimientoXFirmaDAO = seguimientoXFirmaDAO;
        }
    
      
}
