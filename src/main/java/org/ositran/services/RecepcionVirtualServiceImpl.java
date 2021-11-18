/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.IotdtcRecepcion;
import org.ositran.daos.RecepcionVirtualDAO;

/**
 *
 * @author consultor_jti15
 */
public class RecepcionVirtualServiceImpl implements RecepcionVirtualService{
    RecepcionVirtualDAO recepcionVirtualDAO;

    public RecepcionVirtualDAO getRecepcionVirtualDAO() {
        return recepcionVirtualDAO;
    }

    public void setRecepcionVirtualDAO(RecepcionVirtualDAO recepcionVirtualDAO) {
        this.recepcionVirtualDAO = recepcionVirtualDAO;
    }

    public IotdtcRecepcion registrarDocumento(IotdtcRecepcion recepcion){
       return recepcionVirtualDAO.registrarDocumento(recepcion);
    }
}
