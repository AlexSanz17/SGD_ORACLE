/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Proveido;

public interface ProveidoDAO {

	List<Proveido> buscarPorCodigo(String codigoProveido);
        public List<Proveido> buscarProveidosActivos();
	public Proveido buscarPorId(Integer id);
}
