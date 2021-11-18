package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Rol;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.UsuarioStor;

public interface UsuariostorService {

   public UsuarioStor getResponsableSala(Integer iIdSala, char cResponsable);

   public List<Usuario> getAnalistasBySala(String sala);

   public List<Usuario> getRevisoresTecnicosBySala(String sala);

   public List<Usuario> getRevisoresLegalesBySala(String sala);

   public List<Usuario> getListUsurioStorByRol(String nameRol);

   public Usuario getAsistenteSala(Integer idSala);

   public boolean esSupervisorCalificador(Usuario usuario);

   public boolean validarRol(Usuario usuario, Rol rol);
}
