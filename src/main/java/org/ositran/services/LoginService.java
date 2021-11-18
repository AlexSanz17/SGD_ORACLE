package org.ositran.services;

import javax.servlet.http.HttpServletRequest;

import com.btg.ositran.siged.domain.Usuario;


public interface LoginService{
	
	public boolean loginSeguridad(String usuario,String rol,HttpServletRequest request);
	
	public void asignarRol(Usuario usuario,String rol,HttpServletRequest request);

}
