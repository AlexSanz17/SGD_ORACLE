package org.ositran.services;

import com.btg.ositran.siged.domain.FilaHojaFirma;
import java.util.List;

import org.ositran.dojo.BusquedaAvanzada;

import com.btg.ositran.siged.domain.FilaHojaRuta;
import com.btg.ositran.siged.domain.FilaReporteAPN2;
import com.btg.ositran.siged.domain.NodoDocConsolidadoReporteAPN4;
import com.btg.ositran.siged.domain.NodoDocReporteAPN4;
import com.btg.ositran.siged.domain.NodoExpReporteAPN3;
import com.btg.ositran.siged.domain.NodoExpReporteAPN4;
import com.btg.ositran.siged.domain.NodoExpReporteAPN8;
import com.btg.ositran.siged.domain.ReporteAPN1;
import com.btg.ositran.siged.domain.ReporteAPN9;
import com.btg.ositran.siged.domain.ReporteAPN10;
import com.btg.ositran.siged.domain.TrazabilidadDocumentaria;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;

public interface ReporteAPNService {

	//TODO Borrar cuando se compruebe que la hoja de ruta esta bien
	//Map<String, String> generarHojaRuta(Integer idDocumento);
	List<FilaHojaRuta> generarHojaRuta(Integer idDocumento);
        public List<FilaHojaFirma> generarHojaFirma(Integer idDocumento);
	List<FilaHojaRuta> generarHojaRutaExpediente(Integer idExpediente);

	Trazabilidaddocumento buscarUltimaTraza(Integer idDocumento);

	public List<ReporteAPN1> getListaReporteAPN1(String idAreaOrigen,String idTipoDocumento,String idPrioridad,String fechaDesde, String fechaHasta, String idAreaDestino);

	List<FilaReporteAPN2> getListaReporteAPN2(BusquedaAvanzada objFiltro);

	List<NodoExpReporteAPN3> getListaReporteAPN3(BusquedaAvanzada objFiltro);

	List<NodoExpReporteAPN8> getListaReporteAPN8(BusquedaAvanzada objFiltro);

	List<ReporteAPN9> getListaReporteAPN9(String area, String ano, String mes, String tipodocumento);

	List<NodoExpReporteAPN4> getListaReporteAPN4(BusquedaAvanzada objFiltro);

	List<NodoDocReporteAPN4> getListaDocReporteAPN4(String idAreaOrigen);

	List<NodoDocConsolidadoReporteAPN4> getListaConsolidadoReporteAPN4(String objFiltro,Integer unidad);

	public List<TrazabilidadDocumentaria> generarTramiteDocumentario(Integer grupo);

	public List<ReporteAPN10> getListaReporteAPN10(String idAreaOrigen,
			String fechaDesde, String fechaHasta);
}
