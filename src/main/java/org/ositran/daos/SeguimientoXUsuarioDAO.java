/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.FilaBandejaSeguimiento;
import java.util.List;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.SeguimientoXUsuario;

public interface SeguimientoXUsuarioDAO {
	SeguimientoXUsuario guardarSeguimiento(SeguimientoXUsuario seguimiento);
	
	public List<SeguimientoXUsuario> buscarSeguimientoXUsuario(SeguimientoXUsuario seguimiento);
	
	void eliminarSeguimiento(SeguimientoXUsuario seguimiento);
	
	List<FilaBandejaSeguimiento> buscarGridSeguimiento(Usuario objUsuario);
	
	String buscarAreaRemitente(String idRemitente);
        
        SeguimientoXUsuario findByIdSeguimiento(Integer idSeguimiento);
        
        
}
