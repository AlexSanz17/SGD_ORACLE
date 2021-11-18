package org.ositran.services;

import java.util.List;


import org.ositran.dojo.grid.ItemUF;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.SeguimientoXUsuario;

public interface SeguimientoXUsuarioService {
	SeguimientoXUsuario guardarSeguimiento(SeguimientoXUsuario seguimiento);
	void eliminarSeguimiento(SeguimientoXUsuario seguimiento);
	List<SeguimientoXUsuario> buscarSeguimiento(SeguimientoXUsuario seguimiento);
	List<ItemUF> getItemsSeguimientoXUsuario(Usuario objUsuario);
        SeguimientoXUsuario findByIdSeguimiento(Integer idSeguimiento);
        public List<SeguimientoXUsuario> buscarSeguimientoXUsuario(SeguimientoXUsuario seguimiento);
        
}
