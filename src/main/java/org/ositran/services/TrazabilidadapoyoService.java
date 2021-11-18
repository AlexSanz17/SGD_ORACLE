package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Trazabilidadapoyo;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;

public interface TrazabilidadapoyoService {

	public Trazabilidadapoyo findByIdTrazabilidadApoyo(Integer iIdTrazabilidadApoyo);

	Trazabilidadapoyo guardar(Trazabilidadapoyo trazabilidadapoyo);

	void eliminar(Integer idTrazabilidadapoyo);
	public List<Trazabilidadapoyo> buscarDocumentos(Integer idDocumento);

	//Trazabilidadapoyo buscarUltimaDelegacionUsuario(Integer idDestinatario, Integer idDocumento);
        
        Trazabilidadapoyo buscarUltimaDelegacionUsuario(Documento d);
        
        Trazabilidadapoyo buscarUltimaDelegacionUsuario(Usuarioxunidadxfuncion u, Integer idDocumento);
        
        //public Trazabilidadapoyo buscarUltimaDelegacionUsuarioAR(Usuarioxunidadxfuncion usuarioxUnidadxFuncion, Integer idDocumento);

        public Trazabilidadapoyo buscarUltimaDelegacionUsuarioAR(Usuarioxunidadxfuncion usuarioxUnidadxFuncion, Integer idDocumento);
                
        public Trazabilidadapoyo buscarUltimaDelegacionPendiente(Trazabilidadapoyo trazabilidadapoyo);
        
	Long numeroApoyos(Integer idtrazabilidaddocumento);

	List<Trazabilidadapoyo> buscarPorOrigen(Integer idTrazabilidad);

	Long numeroApoyosPendientes(Integer idtrazabilidaddocumento);
}
