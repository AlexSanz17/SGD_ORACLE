package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Estado;

public interface EstadoService {

    public List<Estado> findAll();
    
    public Estado findByIdestado(Integer idEstado);
    
    public Estado findByCodigo(String codigoEstado);
    
    public List<Estado> findByTipo(String tipoEstado);
    
    public List<Estado> findByIdProceso(Integer idProceso);
    
    public void saveObject(Estado estado);
}
