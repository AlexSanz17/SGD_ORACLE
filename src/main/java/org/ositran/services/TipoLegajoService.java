/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.TipoLegajo;
import java.util.List;

/**
 *
 * @author jbengoa
 */
public interface TipoLegajoService {
    public List<TipoLegajo> findTipoLegajo(Integer iWithoutStor, Integer idUnidad);
     public List<TipoLegajo> findTipoLegajoBusqueda();
}
