/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;

public interface TrazabilidaddocumentoDAO{

	public List<Trazabilidaddocumento> buscarLstPor(Integer iIdDoc);
	public List<Trazabilidaddocumento>  buscarMaxTrazaPor(Integer iIdDocumento);
        public int contarListTotalTrazabilidadesDocumento(Trazabilidaddocumento t);
        public int contarListTotalTrazabilidadesUnidad(Trazabilidaddocumento t, List<Unidad> lst);
        
	public Trazabilidaddocumento findByDocumentoDestinatario(Integer iIdDocumento,Integer iIdDestinatario);

         public Trazabilidaddocumento findByMaxNroRegistroAR(Integer iIdDoc,Integer idaccion,Integer destinatarioId, Integer unidad , Integer funcion);
        
	public Trazabilidaddocumento findByMaxNroRegistro(Integer iIdDoc,Integer idaccion,Integer destinatarioId);
        
        public Trazabilidaddocumento findByMaxNroRegistro(Integer iIdDoc,Integer idaccion,Integer destinatarioId, Integer unidad, Integer funcion);

	public Trazabilidaddocumento saveTrazabilidadDocumento(Trazabilidaddocumento objT);

	public List<Trazabilidaddocumento> getListTrazabilidadExpedienteByIdExpediente(Integer expedienteId);

	public Trazabilidaddocumento encontrarUltimaTrazabilidadPorDocumento(int idDocumento);

	public Trazabilidaddocumento getTrazabilidadAnterior(Trazabilidaddocumento trazabilidad);

	public Trazabilidaddocumento findTrabilidadbyId(Integer idtrazabilidaddocumento);

	public Trazabilidaddocumento findRemitenteAccionByIddoc(Integer idDocumento);

	public List<Object[]> filtrarReporteDocumentos(List<String> nombreAcciones, String sFechaDesde, String sFechaHasta, Integer idSede, Integer idProceso);

	public List<Integer> findLstUsuarioDelProcAcc3(Documento d);

	public List<Integer> findLstUsuarioDelProcAcc2(Proceso p);

	public Integer totalUsuarioCompartidos(Integer idUsuarioLogeado,Integer idPropietarioDoc);

	public Integer totalUsuarioCompartidos(Integer idUsuarioLogeado);

	public List<Trazabilidaddocumento> buscarTrazaCompleta(Integer idExpediente);

	public Double ratioTrazabilidadDocumento(Integer idExpediente);

	public Trazabilidaddocumento updateTrazabilidadDocumento(Trazabilidaddocumento objT);

	public int contarListTotalTrazabilidades(Trazabilidaddocumento t);

	public int contarListTotalTrazabilidadesExpediente(Trazabilidaddocumento t);
}
