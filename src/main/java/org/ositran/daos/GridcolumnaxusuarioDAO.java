/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Gridcolumnaxusuario;

public interface GridcolumnaxusuarioDAO {

	public List<Gridcolumnaxusuario> findByIdUsuario(Integer iIdUsuario);
	
	public Gridcolumnaxusuario find(int idUsuario,int idGridColumna);
	
	public void guardar(Gridcolumnaxusuario gridColumnaPorUsuario);
	
	public Gridcolumnaxusuario findById(int idUsuario);
}
