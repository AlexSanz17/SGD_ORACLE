/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;

import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.Consulta;
import org.ositran.dojo.grid.Item;

import com.btg.ositran.siged.domain.ConsultaAPN3;


public interface ConsultaService {
	
	public List<Item> getItemsDocumentosRecepcionados(Consulta objFiltro,String arrFecha[], Integer IdAreaDestino);
	public List<Item> getItemsDocumentosEmitidos(Integer remitente);
	public List<Item> getItemsMisExpedientes( Integer IdAreaDestino, BusquedaAvanzada objFiltro);
}
