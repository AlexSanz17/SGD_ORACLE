package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Accion;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Etapa;
import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.Trazabilidadcopia;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Usuario;

public interface TrazabilidadcopiaService {
	void guardarTrazabilidadcopia(Trazabilidaddocumento idorigen, Usuario remitente, Usuario destinatario, Documento documento, Accion accion, Etapa idetapa, Character tipo, Notificacion notificacion, String nombrePC,Boolean horarioPermitido);
	List<Trazabilidadcopia> buscarPorOrigen(Integer idOrigen, Character tipoOrigen);
	Trazabilidadcopia buscarPorNotificacion(Integer idNotificacion);
	Long numeroCopias(Integer idOrigen, Character tipoOrigen);
	Trazabilidadcopia buscarPorId(Integer idTrazabilidad);
	List<Trazabilidadcopia> buscarUsuarioCopia(Integer idDocumento,Integer traza);

   Trazabilidadcopia saveObject(Trazabilidadcopia trazabilidadCopia);
   List<Trazabilidadcopia> findLstByIdDocumento(Integer idDocumento);
}
