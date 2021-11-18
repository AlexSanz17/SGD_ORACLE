/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;

import org.ositran.dojo.BusquedaAvanzada;

import com.btg.ositran.siged.domain.Documentoenviado;
import com.btg.ositran.siged.domain.FilaBandejaEnviados;
import com.btg.ositran.siged.domain.Usuario;

public interface DocumentoEnviadoService {
	
	List<Documentoenviado> findActivosByUsuario(Integer idusuario);
	List<Documentoenviado> findActivosFiltrados(Integer idusuario, BusquedaAvanzada objFiltro);
	Documentoenviado findByIddocumentoenviado(Integer Iddocumentoenviado) ;  
	void eliminarDocumentosEnviados (String [] ids) ;
        void savedocumentoenviado(Documentoenviado objdocenviado);
        List<FilaBandejaEnviados> buscarEnviadosPorUsuario(Usuario objUsuario);
        List<FilaBandejaEnviados> buscarEnviadosFiltrados(Usuario objUsuario, BusquedaAvanzada objFiltro);
        public List<Usuario> buscarListDestinatarios(Usuario objUsuario);
}
