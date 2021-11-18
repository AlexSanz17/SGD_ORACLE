/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Trazabilidadapoyo;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;

public interface TrazabilidadapoyoDAO {
        public Trazabilidadapoyo buscarUltimaDelegacionPendiente(Trazabilidadapoyo trazabilidadapoyo);
	public Trazabilidadapoyo findTrabilidadbyId(Integer idtrazabilidadApoyo);

	public List<Trazabilidadapoyo> buscarDocumentos(Integer idDocumento);
	public Trazabilidadapoyo guardar(Trazabilidadapoyo trazabilidadapoyo);

	public Trazabilidadapoyo buscarPorId(Integer idTrazabilidadapoyo);

	void eliminar(Integer idTrazabilidadapoyo);

        public Trazabilidadapoyo buscarUltimaDelegacionUsuario(Usuarioxunidadxfuncion usuarioxUnidadxFuncion, Integer idDocumento);
	
        public Trazabilidadapoyo buscarUltimaDelegacionUsuario(Integer idDestinatario, Integer idDocumento);
        
        public Trazabilidadapoyo buscarUltimaDelegacionUsuario(Documento d);
        public Trazabilidadapoyo buscarUltimaDelegacionUsuarioAR(Usuarioxunidadxfuncion usuarioxUnidadxFuncion, Integer idDocumento) ;
      
	public Long numeroApoyos(Integer idtrazabilidaddocumento);

	public List<Trazabilidadapoyo> buscarPorOrigen(Integer idTrazabilidad);

	public Long numeroApoyosEstado(Integer idtrazabilidaddocumento, String codEstado);
        
        public Trazabilidadapoyo buscarUltimaDelegacion(Integer idDocumento);
}
