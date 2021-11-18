/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import org.ositran.dojo.BusquedaAvanzada;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Documentoenviado;
import com.btg.ositran.siged.domain.FilaBandejaEnviados;

public interface DocumentoEnviadoDAO {
	
	public List<Documentoenviado> findByUsuario(Integer idusuario,String estado); 
	public void saveDocumento(Documentoenviado documentoenviado);
	public Documentoenviado findByIddocumentoenviado(Integer iddocumentoenviado) ;
	List<Documentoenviado> findActivosFiltrados(Integer idusuario, BusquedaAvanzada objFiltro);
	List<FilaBandejaEnviados> buscarEnviadosPorUsuario(Usuario objUsuario);
        List<FilaBandejaEnviados> buscarEnviadosFiltrados(Usuario objUsuario, BusquedaAvanzada objFiltro);
        public List<Usuario> buscarListDestinatarios(Usuario objUsuario);
}
