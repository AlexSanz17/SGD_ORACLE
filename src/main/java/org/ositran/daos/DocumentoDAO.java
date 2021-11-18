/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.Recurso;
import org.ositran.pojos.jasper.CargoReporte;
import org.ositran.utils.DocumentoDetail;

import com.btg.ositran.siged.domain.Alerta;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoAreaFuncional;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.FilaSeguimiento;
import com.btg.ositran.siged.domain.DocumentoPendiente;
import com.btg.ositran.siged.domain.TramiteDocumentario;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Usuario;
import org.ositran.utils.DocumentoPublicar;

public interface DocumentoDAO{
       // public List<String> buscarPendienteVirtual(String nroTramite);
       
        public List<Documento> buscarPendientePorDocumentoMultiple(Documento d);
    
        public List<Documento> getDocumentosPorLegajo(int idLegajo);

        public DocumentoDetail findDocumentoDetailByUser(Integer iIdDocumento, Usuario usuario);
        
        public Documento findByIdNroTramite(Integer nroTramite);
        
        public DocumentoDetail findDocumentoDetailByPendiente(DocumentoPendiente documentoPendiente);

        public List<Documento> findByIdDocVirtual(Integer codigoVirtual);
        
        public Integer getNroTramiteDocumentario();
        
	public Documento buscarDocumentoMasAntiguoPor(Integer iIdExpediente);

        public Integer findCantMisRecepcionVirtual(Usuario usuario, char estado, char docPrincipal);
        
	public Documento findByFechaCreacion(Date datFechaCreacion);
        
        public DocumentoDetail findDocumentoDetailByAR(Integer iIdDocumento);

	public List<Documento> buscarLstDocumentoPorExpediente(Integer iIdExpediente);
        
        public List<Documento> consultaDocumentoMultipleAtendido(Integer idDocumento);
        
        public List<Documento> consultaDocumentoReferencia(Integer idDocumento);
        
         public List<Documento> consultaDocumentoReferenciaMover(Integer idDocumento);

	public Integer getNroConsultaTramiteDocumentario();

	public Integer getNroSiguienteConsultaTramiteDocumentario();
        
        public List<Documento> consultaDocumentoOrigen(Integer idDocumento);

	public List<Documento> backup();

	public Documento findByIdDocumento(Integer iIdDoc);
      
	public List<Alerta> findAlertas(Integer idUsuario, Integer idUnidad,boolean banderaDescarga,String orden);

	public Documento findPropietarioByIdDocumento(Integer iIdDocumento);

	public Documento findDocExpedienteByIdDocumento(Integer iIdDocumento);
        
        public List<DocumentoPublicar> getDocumentosPorPublicar(Integer idExpediente, Integer idDocumento);
        
        public List<Documento> findDocumentoPorNumerar(Usuario objUsuario, Documento d);

	public Documento findDocumentoPrincipal(Integer iIdExpediente);

	public List<Documento> buscarLstPor(Integer iIdExpediente,String sNroExpediente,String sNroDocumento,String sNroCaja);

	public List<Documento> buscarLstPor(Integer iIdExpediente,String sNroExpediente,String sNroDocumento,String sNroCaja,Integer iIdTipoDocumento);

	public List<Documento> buscarLstDocumentoPor(char cEstado,char cPrincipal,Integer iIdPropietario);

	public List<Documento> findByIdExpediente(Integer iIdExpediente);

	public List<Documento> findByIdPropietario(Integer iIdUsuario,Integer iIdRol,String sRol,char cEstado,char cPrincipal,String sTempo,Integer iIdSede,int accion,int accion2);

	public Integer findCantByIdPropietario(Integer iIdUsuario,Integer iIdRol,String sRol,char cEstado,char cPrincipal,String sTempo,Integer iIdSede,int accion,int accion2);

	public Documento saveDocumento(Documento objDocumento);
        
        public Documento saveDocumentoSequence(Documento objDocumento); 

	public List<FilaSeguimiento> findByfechaLimiteAtencion(Date fechaInicio, Date fechaFin, Integer idUsuario, Integer idProceso, boolean propios);

	public String diasNoLaborables(String fechaInicio,String fechaFinal);

	public List<Documento> obtenciondefechaslimites(Date fechanolaborable);

	public List<Documento> obtenciondefechasfijas(Date fechanolaborable);

	public List<Documento> buscarLstDocumentoPor(char cPrincipal,Integer IdResponsable);

	/**
	 * INICIO: JOSE ZENTENO
	 */
	public List<Trazabilidaddocumento> devuelveSeguimientoUsuario(Date fechainicio,Date fechafin,Integer idusuario,char cEstado,char cPrincipal);

	public List <Documento> devuelveSeguimientoUsuarioFiltros(Date fechainicio,Date fechafin,Integer idusuario,char cEstado,char cPrincipal,Integer idProceso,boolean todosUsuarios);

        public List findByPendientesDataUF(Usuario objUsuario, boolean items);
	/**
	 * FIN: JOSE ZENTENO
	 */
	public String getSiguienteNumeroMesaPartes();

	public List<Documento> getTodos();

	public Documento updateDocumento(Documento documento);

	public Integer getDocumentosPrincipalesNoLeidos(Integer iIdUsuario);

	public List<Documento> findDocumentosPorNumerar(Usuario objUsuario,Expediente expediente);

	public List<Documento> obtenerDoc_enumerar(List<Integer> idDocumentos);

	public Documento numerarDocumento(Documento documento);

	public List<Documento> busquedaDocumento(List<Integer> idDocumentos,String numeroDocumento,String numeroMesaPartes,String asuntoDocumento,String numeroExpediente,String asuntoExpediente,String estadoExpediente,String concesionario,String cliente,String proceso,String propietario,String areaDestino,String tipoDocumento,Date documentoDesde,Date documentoHasta,Date expedienteDesde,Date expedienteHasta,String operador);

	public List<DocumentoAreaFuncional> busquedaDocumento(String asuntoDocumento,String asuntoExpediente,Date documentoDesde,Date documentoHasta, Integer idAreaUsuario, Integer idAreaFuncional, String operador);

	public List<Documento> getDocumentosPorExpediente(int idExpediente);

	List<Documento> getDocumentosNoConfidencialesPorExpediente(int idExpediente);

	public Documento getDocumentoMasReciente(int idExpediente, int idDocumento);

   public DocumentoDetail findDocumentoDetailBy(Integer iIdDocumento);

   public byte[] getFirmaDigital(Usuario firmante);

   public Integer findCantDocumentosRecibidos(Usuario usuario, char estadoActivo, char documentoPrincipal);

   public Integer findCantMisRecepcionVirtualObservados(Usuario usuario, char estadoActivo, char documentoPrincipal);
   
   public Documento guardarDocumento(Documento documento);

   public List<Recurso> getCountDocuments(Usuario usuario);

   @SuppressWarnings("rawtypes")
   public List findByDataUF(Usuario objUsuario, String enviados, boolean items);

   public CargoReporte obtenerCargo(Integer iddocumento);

   public List<Documento> busquedaDocumento(List<Integer> idDocumentos,
            String numeroDocumento, String numeroMesaPartes, String asuntoDocumento,
            String numeroExpediente, String asuntoExpediente, String estadoExpediente,
            String concesionario, String cliente, String proceso, String propietario,
            String areaDestino, String tipoDocumento, Date documentoDesde,
            Date documentoHasta, Date expedienteDesde, Date expedienteHasta,
            String numeroSuministro, String operador, String areaOrigen, String areaAutor, String sqlQueryDinamico[], String nroHT, String nroRS,String nroLegajo, String tipoLegajo, String tipoConsulta, String unidadUsuario, String cargoUsuario, String autor);

   @SuppressWarnings("rawtypes")
   public List findByDataAttendedUF(Usuario objUsuario);

   public List findByDataAttendedUF(Usuario objUsuario, BusquedaAvanzada objFiltro);

   public List<Documento> getTodosDocumentosPorExpediente(int idExpediente);

   @SuppressWarnings("rawtypes")
   public List findByDataUFWithoutSharedInbox(Integer iIdUsuario);

   public List<Documento> findDocumentosPorFirmar(Usuario objUsuario, Expediente expediente);

   public Usuario findUsuarioById(Integer idUsuario);

   @SuppressWarnings("rawtypes")
   void sendNotificacionXEnumerar(Documento documento, Usuario remitente, Set informed);

   public Documento registrarDocumento(Documento objDocumento);

   void eliminarDocumento(Integer idDocumento);

   public Integer findCantMisExpedientes(Usuario usuario, char estadoActivo, char documentoPrincipal);

   public List<Documento> busquedaCarpeta(String sNumeroDocumento,
		   String sNumeroMesaPartes, String sAsuntoDocumento,
		   String sNumeroExpediente, String sAsuntoExpediente,
		   String sEstadoExpediente, String sConcesionario, String sCliente,
		   Integer proceso, String sPropietario, Integer areaDestino,
		   Integer tipoDocumento, Date fechaDocumentoInicio,
		   Date fechaDocumentoFinal, Date fechaExpedienteInicio,
		   Date fechaExpedienteFinal, Integer areaOrigen, String remitente, String pendiente);

   public List<Documento> busquedaRapidaDocumento(String asunto);

   List findFilasDXE(Integer idUsuario, Integer idDocumento, boolean enviados);

   List<Integer> getUsuariosPermitidos(Integer idDocumento);

   public List findByDataUFFiltro(Usuario usuario, String enviados, BusquedaAvanzada objFiltro);

   public List findByDataUFDocumentosRecibidos(Integer iIdUsuario, boolean enviados);

   public List findByDataUFDocumentosRecibidosFiltro(Integer iIdUsuario, boolean enviados, BusquedaAvanzada objFiltro);

   public List findFilasDXETree(Integer idUsuario, Integer idDocumento, boolean enviados);

   public List findFilasDXETreeFiltro(BusquedaAvanzada objFiltro, Integer idUsuario, Integer idDocumento, boolean enviados);

   public List findByDataUFFiltroIG(Integer iIdUsuario, boolean enviados, BusquedaAvanzada objFiltro);

   public List<TramiteDocumentario> buscarTramiteDocumentario(String expediente);

   public String getAtendidoTramiteDocumentario(Documento d);
   
   public boolean verificarDuplicidadDocumento(Integer tipo, String nro, Integer idUnidad);
   
   public Integer getNroDocumento();
   
    public Integer findCantFirmas(Usuario objUsuario);
    
    public List<Documento> getDocumentoByTipoByUnidad(String tipodocuento, String unidadautor, Integer expediente);
    
    public List findByPendientesDataUFFiltro(Usuario objUsuario, BusquedaAvanzada objFiltro);

}