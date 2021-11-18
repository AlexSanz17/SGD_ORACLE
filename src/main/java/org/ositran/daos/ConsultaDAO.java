/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import org.ositran.dojo.BusquedaAvanzada;

import com.btg.ositran.siged.domain.ConsultaAPN3;
import com.btg.ositran.siged.domain.ConsultaAPN4;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Expediente;

public interface ConsultaDAO {
	public List<ConsultaAPN3> documentosRecepcionados(String tipoDocumento, String areaOrigen,String fechaDesde, String fechaHasta,Integer idAreaDestino,String nroDocumento, String asuntoDocumento);
	public List<ConsultaAPN4> documentosEmitidos(Integer remitente);
	public List<Expediente> misExpedientes(Integer idAreaDestino, BusquedaAvanzada objFiltro); 
}
