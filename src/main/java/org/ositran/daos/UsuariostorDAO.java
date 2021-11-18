/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.UsuarioStor;

public interface UsuariostorDAO{
	
	public UsuarioStor getResponsableSala(Integer iIdSala,char cResponsable);

	public List<Usuario> getAnalistasBySala(String sala);

	public List<Usuario> getRevisoresTecnicosBySala(String sala);

	public List<Usuario> getRevisoresLegalesBySala(String sala);

	public Usuario getAsistenteSala(Integer idSala);

	public List<Usuario> getListUsurioStorByRol(String nameRol);
	
	public UsuarioStor encontrarPorIdUsuario(int idUsuario);
	
	public UsuarioStor encontrarPorUsuario(String usuario);
	
	public UsuarioStor guardarUsuarioStor(UsuarioStor usuario);
	
	public void crearUsuarioExistente(int idUsuario,int idSala);
}
