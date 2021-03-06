package org.ositran.services;

import java.util.List;
import org.ositran.daos.UsuariostorDAO;
import org.ositran.utils.Constantes;

import com.btg.ositran.siged.domain.Rol;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.UsuarioStor;

public class UsuariostorServiceImpl implements UsuariostorService{
	// private static Logger log =
	// Logger.getLogger("org.ositran.services.UsuariostorServiceImpl");
	private UsuariostorDAO dao;

	// ////////////////////////////////
	// Constructors //
	// ////////////////////////////////
	public UsuariostorServiceImpl(UsuariostorDAO dao){
		setDao(dao);
	}

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	public UsuarioStor getResponsableSala(Integer iIdSala,char cResponsable){
		return getDao().getResponsableSala(iIdSala,cResponsable);
	}

	public List<Usuario> getAnalistasBySala(String sala){
		return dao.getAnalistasBySala(sala);
	}

	public List<Usuario> getRevisoresTecnicosBySala(String sala){
		return dao.getRevisoresTecnicosBySala(sala);
	}

	public List<Usuario> getRevisoresLegalesBySala(String sala){
		return dao.getRevisoresLegalesBySala(sala);
	}

	public List<Usuario> getListUsurioStorByRol(String nameRol){
		return dao.getListUsurioStorByRol(nameRol);
	}

	public Usuario getAsistenteSala(Integer idSala){
		return dao.getAsistenteSala(idSala);
	}

	public boolean esSupervisorCalificador(Usuario usuario){
		List<Rol> listRoles= null;//usuario.getRoles();
		boolean validarRol=false;
		if(listRoles!=null){
			for(int i=0;i<listRoles.size();i++){
				if(listRoles.get(i).getNombre().equalsIgnoreCase(Constantes.ROL_USUARIO_SCALIFICADOR_STOR)){
					validarRol=true;
					break;
				}
			}
		}
		return validarRol;
	}

	public boolean validarRol(Usuario usuario,Rol rol){
		List<Rol> listRoles= null;//usuario.getRoles();
		boolean validarRol=false;
		if(listRoles!=null){
			for(int i=0;i<listRoles.size();i++){
				if(listRoles.get(i).getNombre().equalsIgnoreCase(rol.getNombre())){
					validarRol=true;
					break;
				}
			}
		}
		return validarRol;
	}

	// ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public UsuariostorDAO getDao(){
		return dao;
	}

	public void setDao(UsuariostorDAO dao){
		this.dao=dao;
	}
}
