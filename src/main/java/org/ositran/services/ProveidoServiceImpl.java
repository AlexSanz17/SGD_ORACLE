package org.ositran.services;

import java.util.List;

import org.ositran.daos.ProveidoDAO;

import com.btg.ositran.siged.domain.Proveido;

public class ProveidoServiceImpl implements ProveidoService {
	
	private ProveidoDAO proveidoDAO;

	public List<Proveido> buscarPorCodigo(String codigoProveido){
	   return proveidoDAO.buscarPorCodigo(codigoProveido);
	}
        
        public List<Proveido> buscarProveidosActivos(){
           return proveidoDAO.buscarProveidosActivos();
        }
        
	public ProveidoDAO getProveidoDAO() {
	   return proveidoDAO;
	}

	public void setProveidoDAO(ProveidoDAO proveidoDAO) {
	  this.proveidoDAO = proveidoDAO;
	}
        
        public Proveido buscarPorId(Integer id){
             return proveidoDAO.buscarPorId(id);
        }
	
}
