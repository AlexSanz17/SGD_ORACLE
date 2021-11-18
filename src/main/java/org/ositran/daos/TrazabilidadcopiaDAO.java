/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Trazabilidadcopia;

public interface TrazabilidadcopiaDAO {
	//void guardarTrazabilidadcopia(Trazabilidadcopia trazabilidadcopia);
	List<Trazabilidadcopia> buscarPorOrigen(Integer idOrigen, Character tipoOrigen);
	Trazabilidadcopia buscarPorNotificacion(Integer idNotificacion);
	Long numeroCopias(Integer idOrigen, Character tipoOrigen);
	Trazabilidadcopia buscarPorId(Integer idTrazabilidadcopia);
	List<Trazabilidadcopia> buscarUsuarioCopia(Integer idDocumento, Integer traza);

   Trazabilidadcopia saveObject(Trazabilidadcopia trazabilidadCopia);
   List<Trazabilidadcopia> findLstByIdDocumento(Integer idDocumento);
}
