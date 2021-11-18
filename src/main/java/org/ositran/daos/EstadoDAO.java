/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Estado;

/**
 *
 * @author ANGEL
 */
public interface EstadoDAO {

    public List<Estado> findAll();
    public Estado findByIdestado(Integer idEstado);
    public Estado findByCodigo(String codigoEstado);
    public List<Estado> findByTipo(String tipoEstado);
    public List<Estado> findByIdProceso(Integer idProceso);
    public void saveObject(Estado estado);
    public Estado find(String codigo, String tipo);
}
