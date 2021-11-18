/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Valorcampo;

/**
 *
 * @author Himizu
 */
public interface ValorcampoDAO {
	
	public List<Valorcampo> findAll() ;
	
	public Valorcampo getValorCampo(int idValorCampo);
	
	public List<Valorcampo> getPorCampo(int idCampo);
}
