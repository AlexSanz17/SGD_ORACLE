/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.IotdtcRecepcion;

/**
 *
 * @author consultor_jti15
 */
public interface RecepcionVirtualService {
    public IotdtcRecepcion registrarDocumento(IotdtcRecepcion recepcion);
}
