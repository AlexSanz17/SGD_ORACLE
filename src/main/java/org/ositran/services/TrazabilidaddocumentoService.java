package org.ositran.services;

import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;

import org.ositran.dojo.grid.Item;

import com.btg.ositran.siged.domain.Accion;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Etapa;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import org.ositran.utils.DocumentoDetail;

public interface TrazabilidaddocumentoService{
    
        public int contarListTotalTrazabilidadesDocumento(Trazabilidaddocumento t);

	public List<Trazabilidaddocumento> findByIdDocumento(Integer iIdDocumento);

	public Trazabilidaddocumento findByIdTrazabilidadDocumento(Integer iIdTrazabilidadDocumento);

	List<Trazabilidaddocumento> buscarTrazaCompleta(Integer idExpediente);

	public List<Trazabilidaddocumento> findByMaxtrazabyIddocumento(Integer iIdDocumento);

	public Trazabilidaddocumento findByDocumentoDestinatario(Integer iIdDocumento,Integer iIdDestinatario) throws NoResultException;

	public Trazabilidaddocumento findByMaxNroRegistro(Integer iIdDocumento,String sAccion,Integer idaccion,Integer destinatariod) throws NoResultException;
        
        public Trazabilidaddocumento findByMaxNroRegistro(Integer iIdDocumento,String sAccion,Integer idaccion,Integer destinatariod, Integer unidadd, Integer cargod) throws NoResultException;

	public Trazabilidaddocumento saveTrazabilidadDocumento(Documento objD,Usuario objUsuario,boolean copiarfechaLimite,boolean copiarasuntodocumento);

	public Trazabilidaddocumento saveTrazabilidadDocumento(Documento objD,Usuario objUsuario,Integer iPlazoDia,Integer iPlazoHora,String fechaLimiteAtencion,String asunto,String contenido, String[] strAcciones, String nombrePC, Boolean horarioPermitido,String sinPlazo, Boolean horarioPermitidoRecepcion, Usuarioxunidadxfuncion objDestino, Integer prioridad) throws Exception;

	public Trazabilidaddocumento guardarTrazabilidad(Documento documento,Usuario remitente,Usuarioxunidadxfuncion destinatario,String accion,String asunto,String contenido, String nombrePC);

	public Trazabilidaddocumento guardarTrazabilidad(Documento documento,Usuario remitente,Usuarioxunidadxfuncion destinatario,Accion accion,String asunto,String contenido, String nombrePC, Boolean horarioPermitido, Boolean horarioPermitidoRecepcion, DocumentoDetail documentoDetail) throws Exception;

	public Trazabilidaddocumento guardarTrazabilidad(Documento documento,Usuario remitente,Usuarioxunidadxfuncion destinatario,Accion accion, String nombrePC);

        public Trazabilidaddocumento guardarTrazabilidad(Documento documento,Usuario remitenteSession,Usuarioxunidadxfuncion destinatario,Accion accion,String asunto,String contenido, String nombrePC , Boolean horarioPermitido, Boolean horarioPermitidoRecepcion);
        
	public Trazabilidaddocumento saveTrazabilidadDocumento(Trazabilidaddocumento td);

	public Trazabilidaddocumento encontrarUltimaTrazabilidadPorDocumento(int idDocumento);

	public Trazabilidaddocumento getTrazabilidadAnterior(Trazabilidaddocumento trazabilidad);

	public Trazabilidaddocumento findTrabilidadbyId(Integer idtrazabilidaddocumento);

	public void cambiarFechaLimite(Integer idtrazabilidaddocumento,Date nuevafecha);

	public List<Trazabilidaddocumento> getListTrazabilidadExpedienteByIdExpediente(Integer expedienteId);

	public Trazabilidaddocumento saveTrazabilidadDocumento(Documento informeTecnico,Usuario remitente);

	public Trazabilidaddocumento findRemitenteAccionByIddoc(Integer iIdDocumento);

	public List<Item> filtrarReporteDocumentos(String sFechaDesde, String sFechaHasta, String sDiasReporte, Integer idSede, Integer idProceso);

	public List<Integer> findLstUsuarioDelProcAcc3(Documento d);

	public List<Integer> findLstUsuarioDelProcAcc2(Proceso p);

	public Integer totalCompartidos(int idUsuarioLogeado);

	public Integer totalCompartidos(int idUsuarioLogeado,int idPropietarioDoc);

	public Trazabilidaddocumento saveTrazabilidadDocumentoMasivos(Documento objD, Usuario objUsuario,Integer iPlazoDia,Integer iPlazoHora,String fechaLimiteAtencion, String asunto, String contenido, Etapa objEtapa,Accion accionOpt,Usuario destinatario);

	public boolean esPrimeraEtapaExpediente(Integer idExpediente);

	Trazabilidaddocumento aplicarPlazosADocumento(Trazabilidaddocumento objT,Integer iPlazoDia,Integer iPlazoHora,String fechaLimiteAtencion,String sinPlazo, Integer prioridad, Documento doc);
	Trazabilidaddocumento aplicarPlazosADocumento(Trazabilidaddocumento objT,Integer iPlazoDia,Integer iPlazoHora,String fechaLimiteAtencion,String sinPlazo, Documento docuemnto );

	public Trazabilidaddocumento updateTrazabilidadDocumento(Trazabilidaddocumento objT);

	public int contarListTotalTrazabilidades(Trazabilidaddocumento t);

	public int contarListTotalTrazabilidadesExpediente(Trazabilidaddocumento t);
        
        public int contarListTotalTrazabilidadesUnidad(Trazabilidaddocumento t, List<Unidad> lst);
}
