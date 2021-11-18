package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Proveido;

public interface ProveidoService {
   public List<Proveido> buscarPorCodigo(String codigoProveido);
   public List<Proveido> buscarProveidosActivos();
   public Proveido buscarPorId(Integer id);
}
