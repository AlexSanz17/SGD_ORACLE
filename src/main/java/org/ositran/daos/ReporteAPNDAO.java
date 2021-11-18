/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.FilaHojaFirma;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ositran.dojo.BusquedaAvanzada;

import com.btg.ositran.siged.domain.FilaHojaRuta;
import com.btg.ositran.siged.domain.FilaReporteAPN2;
import com.btg.ositran.siged.domain.NodoDocConsolidadoReporteAPN4;
import com.btg.ositran.siged.domain.NodoDocReporteAPN3;
import com.btg.ositran.siged.domain.NodoDocReporteAPN4;
import com.btg.ositran.siged.domain.NodoDocReporteAPN8;
import com.btg.ositran.siged.domain.NodoExpReporteAPN3;
import com.btg.ositran.siged.domain.NodoExpReporteAPN4;
import com.btg.ositran.siged.domain.NodoExpReporteAPN8;
import com.btg.ositran.siged.domain.ReporteAPN1;
import com.btg.ositran.siged.domain.ReporteAPN9;
import com.btg.ositran.siged.domain.ReporteAPN10;
import com.btg.ositran.siged.domain.TramiteDocumentario;
import com.btg.ositran.siged.domain.TrazabilidadDocumentaria;

public interface ReporteAPNDAO {
	@PersistenceContext(unitName = "sigedPU")
	public abstract void setEm(EntityManager em);
	public abstract List<ReporteAPN1> getListaReporteAPN1(String idareaorigen,String idtipodocumento,String idprioridad,String fechaDesde, String fechaHasta, String idareadestino);
	List<FilaHojaRuta> getHojaRuta(Integer idDocumento);
	List<FilaHojaRuta> getHojaRutaExpediente(Integer idExpediente);
	List<FilaReporteAPN2> getListaReporteAPN2(BusquedaAvanzada objFiltro);
	List<ReporteAPN9> getListaReporteAPN9(String area, String ano, String mes, String tipodocumento);
	List<NodoExpReporteAPN3> getListaNodosExpedienteReporteAPN3(BusquedaAvanzada objFiltro);
	List<NodoExpReporteAPN8> getListaNodosExpedienteReporteAPN8(BusquedaAvanzada objFiltro);
	List<NodoExpReporteAPN4> getListaReporteAPN4(BusquedaAvanzada objFiltro);
	List<NodoDocReporteAPN3> getListaNodosDocumentoReporteAPN3(Integer idExpediente);
	List<NodoDocReporteAPN8> getListaNodosDocumentoReporteAPN8(Integer idExpediente);
	List<NodoDocReporteAPN4> getListaDocReporteAPN4(String idAreaOrigen);
	List<NodoDocConsolidadoReporteAPN4> getListaConsolidadoReporteAPN4(String objFiltro,Integer unidad);
	public List<TrazabilidadDocumentaria> getTramiteDocumentario(Integer grupo);
        public List<FilaHojaFirma> getHojaFirma(Integer idDocumento);
	public List<ReporteAPN10> getListaReporteAPN10(String idAreaOrigen,
			String fechaDesde, String fechaHasta);
}
