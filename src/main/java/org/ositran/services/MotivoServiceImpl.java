package org.ositran.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ositran.daos.MotivoDAO;

import com.btg.ositran.siged.domain.Motivo;

public class MotivoServiceImpl implements MotivoService{
	
	private MotivoDAO dao;

	public MotivoServiceImpl(MotivoDAO dao){
		setDao(dao);
	}

	public Map<Integer,String> getMotivoMap(){
		Map<Integer,String> mapAux=new HashMap<Integer,String>();
		List<Motivo> lstM=getDao().findAll();
		for(Motivo objM : lstM){
			mapAux.put(objM.getIdmotivo(),objM.getDescripcion());
		}
		return mapAux;
	}

	public List<Motivo> findAll(){
		return dao.findAll();
	}

	public Object[] findAllAjax(){
		return dao.findAll().toArray();
	}

	// //////////////////////
	// Getters and Setters
	// //////////////////////
	public MotivoDAO getDao(){
		return dao;
	}

	public void setDao(MotivoDAO dao){
		this.dao=dao;
	}

	@Override
	public List<Motivo> encontrarPorProceso(int idProceso){
		return dao.encontrarPorProceso(idProceso);
	}
}
