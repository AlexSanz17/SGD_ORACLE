/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.TipoLegajo;
import java.util.List;

/**
 *
 * @author jbengoa
 */
public interface TipoLegajoDAO {
    public List<TipoLegajo> findAllTipoLegajo(Integer idUnidad);
    public List<TipoLegajo> findAllTipoLegajoActive(Integer idUnidad);
    public List<TipoLegajo> findTipoLegajoBusqueda();
    public TipoLegajo findTipoLegajo(TipoLegajo tipoLegajo);
}
