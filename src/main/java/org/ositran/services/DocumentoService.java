/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.Recurso;
import org.ositran.dojo.tree.Nodo;
import org.ositran.dojo.tree.NodoArbol;
import org.ositran.dojo.tree.SimpleNode;
import org.ositran.pojos.jasper.CargoReporte;
import org.ositran.utils.ArchivoTemporal;
import org.ositran.utils.DocumentoDetail;
import org.ositran.utils.DocumentoList;
import org.ositran.utils.ExpedienteSearch;
import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.ArchivoPendiente;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoReferencia;
import com.btg.ositran.siged.domain.DocumentoAreaFuncional;
import com.btg.ositran.siged.domain.Documentofedateado;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.TramiteDocumentario;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import org.ositran.dojo.grid.ItemUF;
import org.ositran.utils.DocumentoPublicar;

@SuppressWarnings("rawtypes")
public interface DocumentoService{
        public List<NodoArbol> getArbolTipoDocumentosNavegador(Usuario usuario, String tipoVista); 
        public List<NodoArbol> getArbolDocumentosNavegador(Usuario usuario, String tipoVista) ;
        public List<NodoArbol> getArbolUnidadNavegador(Usuario usuario, String tipoVista);
        public Integer findCantMisRecepcionVirtualObservados(Usuario objUsuario);
        
        public Documentofedateado registrarDocumentoFedatario(Documentofedateado objDocFed)  throws Exception;
        public List<DocumentoReferencia> getDocumentoRespuesta(Documento documento);
        
        public List<Documento> consultaDocumentoReferencia(Integer idDocumento);
        
        public List<DocumentoPublicar> getDocumentosPorPublicar(Integer idExpediente, Integer idDocumento);

	public void saveDesactivarAlerta(String[] arrDocumentArea, Usuario objUsuario);
        
        public void actualizarDatosRecepcionVirtual(Integer idExterno) throws  Exception;

        public String enviarDocumentoVirtual(Integer idExterno, Usuario objUsuario) throws  Exception;
        
        public String enviarCargoRecepcionVirtual(Integer idExterno) throws Exception;
	
        public Documento findByIdDocumento(Integer iIdDocumento);
        
        public Documento findByIdNroTramite(Integer nroTramite);
         
        public Documento updateNoLeido(Integer iIdDocumento, String nombrePC);

	public Boolean aplicarJerarquia(Usuario objJefe,Proceso objProceso,Integer iIdDocumento);

	public Documento buscarDocumentoMasAntiguoPor(Integer iIdExpediente);
        
        public Integer findCantFirmas(Usuario objUsuario);
        
        public Integer findCantMisRecepcionVirtual(Usuario objUsuario);
        
        public List<Documento> backup();

	public void anexarDocumento(Integer iIdDocumentoPrincipal,Integer[] arrIdDocumento);

	public Documento clonarDocumento(Documento objDocumento,Usuario objPropietario,Expediente objExpediente);

        public List<Documento> getReferenciaDocumento(Integer idDocumento);
        
	public Documento findPropietarioByIdDocumento(Integer iIdDocumento);

	public Documento findDocExpedienteByIdDocumento(Integer iIdDocumento);

	public Documento findDocumentoPrincipal(Integer iIdExpediente);

	public DocumentoDetail getExpedienteData(Integer iIdExp) throws RuntimeException;

	public DocumentoDetail getDocumentDetailOptimized (Integer iIdDoc,String strRol);

	public DocumentoDetail getDocumentDetail(Integer iIdDoc,String strRol) throws RuntimeException;
        
         public List<Documento> findDocumentoPorNumerar(Usuario objUsuario, Documento d);

	public Documento crearDocumentoPorArchivo(DocumentoDetail objDD,Usuario objUsuario,Map<String,List<ArchivoTemporal>> mapUpload,Boolean changePrincipal,boolean quitarCorchete,String modulo,String opcion,boolean masivo);

	public Integer findCantDocumentos(Usuario objUsuario);

	public Integer getNroConsultaTramiteDocumentario();
 
	public Integer getNroSiguienteConsultaTramiteDocumentario();

	public List<Documento> buscarLstPor(Integer iIdDocPrincipal,ExpedienteSearch objExpedienteSearch);

	public List<Documento> buscarLstDocumentoPor(char cEstado,char cPrincipal,Integer iIdPropietario);

	public List<Documentofedateado> buscarLstDocumentoFedateado(Integer idUsuario, String fecInferior , String fecSuperior, String servicio);

	public List findDocumentos(Usuario objUsuario);

	public List<DocumentoList> getDocumentList(List<Documento> lstD,String strRol) throws RuntimeException;

	public List<DocumentoList> getDocuments(Usuario objUsuario,char habilitado) throws RuntimeException;

	public List<Archivo> getArchivoList(Integer iIdDoc);
        
        public Documento moverDocumentoSIDECO(Integer iIdDocumento, String nombrePC);

	public void aplicarPlazosADocumentoPrincipal(DocumentoDetail objDD,Documento objDocumento,Usuarioxunidadxfuncion usuarioDestinatario,String sOpcion, Usuario objUsuario, Date fechaLimiteAtencionAnterior,Boolean horarioPermitido, Boolean horarioPermitidoRecepcion);

	public Documento derivarDocumento(DocumentoDetail objDD,Usuario objRemitente,Usuarioxunidadxfuncion objDestino,String sTipoDerivacion,DocumentoDetail objDDD,List<String> conCopia, String[] strAcciones, Documento documento, String nombrePC,Boolean horarioPermitido, Boolean horarioPermitidoRecepcion, Integer codigoVirtual) throws Exception;

	public void  derivarDocumentoMasivo(Integer[] arrIdDoc, Documento documento, Usuario objUsuario, Usuarioxunidadxfuncion usuarioDestinatario, DocumentoDetail documentoDetail, String nombrePC, Boolean horarioPermitido, Boolean horarioPermitidoRecepcion, String strAcc) throws Exception;
	public void inactivarDocumentos(Integer iIdExpediente);

        @Deprecated /** Usar el metodo sobrecargado -------------------------------------------------------------------------------*/
	public void registrarDIG(Integer iIdDocumento,Map<String,List<ArchivoTemporal>> mapUpload,Usuario objRemitente);

        public void registrarDIG(Integer iIdDocumento,Map<String,List<ArchivoTemporal>> mapUpload,Usuario objRemitente, String observacionDIG);

	public Documento saveDocumento(Documento objDocumento);

	public String diasNoLaborables(String fechaInicio,String fechaFinal) throws RuntimeException;

	public List<Documento> obtieneDocumentosNotificacionAmarilla();

	public List<Documento> obtieneDocumentosNotificacionRoja();

	public List<Documento> buscarLstDocumentoPor(char cPrincipal,Integer idReponsable);

	public Documento subirConMetadata(Usuario usuario,Map<String,List<ArchivoTemporal>> mapUpload,Integer idDocumento,Documento objDocumento,DocumentoDetail objDD,String versionar,Integer[] fverionar, String nombrePDFprincipal);

	public String[] obtenerListaFeriados();

	public Date getFechaLimiteAtencion(Integer idDocumento);

	public Integer getDocumentosPrincipalesNoLeidos(Integer iIdUsuario);

	public Documento updateLeido(Integer iIdDocumento,Usuario remitente, String nombrePC);

	public void rechazarDocumento(Usuario remitente, Usuarioxunidadxfuncion destinatario, Documento documento, String asunto, String contenido, String accion, String nombrePC, Boolean horarioPermitido, Boolean horarioPermitidoRecepcion, DocumentoDetail d, Integer codigoVirtual) throws Exception;

	public void reabrirDocumentoAtendido(Usuario remitente, Documento documento, String  nombrePC);

	public void anularDocumento(Usuario remitente, Documento documento, Expediente expediente, boolean unico, Usuario usuarioLogeado, String nombrePC, String observacion);

	public void atenderDocumento(Usuario remitente, Documento documento, Expediente expediente, boolean unico, Usuario usuarioLogeado, String nombrePC, String[] listaReferencias, String observacion, Integer codigoVirtual);

	public List<Documento> findDocumentosPorNumerar(Usuario logeado,Expediente expediente);

	public List<Documento> busquedaDocumentoSimple(String campo, Usuario usuario);

	public List<DocumentoAreaFuncional> busquedaDocumentoAreaFuncionalSimple(String campo );

    @Deprecated /**Usar el metodo sobrecargado --------------------------------------------------------------------------------*/
	public List<Documento> busquedaDocumentoAvanzada(boolean alfresco,String contenido,String numeroDocumento,String numeroMesaPartes,String asuntoDocumento,String numeroExpediente,String asuntoExpediente,String estadoExpediente,String concesionario,String cliente,String proceso,String propietario,String areaDestino,String tipoDocumento,String fechaDocumentoDesde,String fechaDocuentoHasta,String FechaExpedienteDesde,String fechaExpedienteHasta,String operador);

    public List<Documento> busquedaDocumentoAvanzada(String contenido,
            String numeroDocumento, String numeroMesaPartes, String asuntoDocumento,
            String numeroExpediente, String asuntoExpediente, String estadoExpediente,
            String concesionario, String cliente, String proceso, String propietario,
            String areaDestino, String tipoDocumento, String fechaDocumentoDesde,
            String fechaDocuentoHasta, String fechaExpedienteDesde, String fechaExpedienteHasta,
            String nroSuministro, String operador, String areaOrigen, String areaAutor, String sqlQueryDinamico[], String nroHT, String nroRS, String sNroLegajo, String tipoLegajo, String tipoConsulta, String unidadUsuario, String cargoUsuario, String autor);

	public List<Documento> getDocumentosPorExpediente(int idExpediente);

	List<Documento> getDocumentosNoConfidencialesPorExpediente(int idExpediente);

	public void cambiarReferencia(Documento documento,Expediente expediente,Expediente expedienteNuevo,boolean unico,Usuario usuario, String nombrePC);

	public boolean verificarArchivosParaExpedienteNuevo(Documento documento,Expediente expedienteNuevo) throws RuntimeException;

	//public Documento doSaveExpedienteUF(DocumentoDetail objDD,Usuario objUsuarioLogeado,Expediente objExp,Integer[] idsDocumentoPorExSeleccionados,Integer idDocPrincipalExpediente);

	public void copiarArchivosToNuevoDocumento(List<Archivo> archivos,Expediente expediente,Expediente expedienteNuevo,Documento documentoDestino) throws RuntimeException;

	public void copiarReferencia(Documento documento, Expediente expediente,Expediente expedienteNuevo, boolean unico,Usuario usuario, String nombrePC) throws RuntimeException;

        public DocumentoDetail saveNuevoDocumentoUserFinal(DocumentoDetail documentoDetail, Map<String,Object> session, String iddestinatario, Integer idccdestinatario, String strAcc, boolean bBandeja, ArchivoPendiente archivoPendiente, String nombrePC, String nombrePDFprincipal) throws Exception;

        public DocumentoDetail updateDocumentoUserFinal(DocumentoDetail documentoDetail, Map<String,Object> session, String iddestinatario, Integer idccdestinatario, String strAcc, boolean bBandeja, ArchivoPendiente archivoPendiente, String nombrePC, String nombrePDFprincipal) throws Exception;
        
        public Map<String, Object> getMetadataFirma(Documento documento, String rutaArchivo);

        public Integer findCantDocumentosRecibidos(Usuario objUsuario);

        public void guardarDocumento(Documento documento);

        public boolean numerarDocumentoFisico(Usuario usuario,Documento documento, int whatToDo);

        public List<Recurso> getCountDocuments(Usuario usuario);

        public boolean perteneceAccesoProcAcc3(Usuario u,Documento d);

        public boolean perteneceAccesoProcAcc2(Usuario u,Proceso p);

        public List findDocumentosUsuarioFinal(Usuario objUsuario, String enviados);
        
        public List findDocumentosPendientesUsuarioFinal(Usuario objUsuario);

        public CargoReporte obtenerCargo(Integer iddocumento);
        
        public List findDocumentosPendientesUsuarioFinalFiltro(Usuario objUsuario,BusquedaAvanzada objFiltro);

        public List getBandejaDocAtendidosUsuarioFinal(Usuario objUsuario);

        public List findDocumentosAtendidosUsuarioFinal(Usuario objUsuario);

        public List findDocumentosAtendidosUsuarioFinal(Usuario objUsuario, BusquedaAvanzada objFiltro);

        public List findByDataUFWithoutSharedInbox(Integer iIdUsuario);

        public List<Documento> findDocumentosPorFirmar(Usuario objUsuario, Expediente expediente);

        public boolean firmarDocumentoFisico(Usuario usuario, Documento documento, int whatToDo);

        List<Documento> modifyDocuments(Usuario usuario, Map<Integer, Integer> documentsToModify,String tipoNumeracion) throws Exception;

        public Documento registrarDocumento(Documento objDocumento);

        public void registrarDerivacionAuditoriaDocumento(Documento doc, Usuario remitente, Usuario destinatario, String tipoauditoria, String modulo, String opcion);

        public void crearCopiaApoyo(Documento doc, DocumentoDetail objDD, Usuario usuario, Usuarioxunidadxfuncion datosDestinatarios, String[] acciones, Integer prioridad, String mensaje, String nombrePC,Boolean horarioPermitido, Boolean horarioPermitidoRecepcion, List<Usuario> usuariosNotificados, Integer codigoVirtual);   

        void eliminarDocumento(Integer idDocumento);

        //void guardarTrazaDelegacion(Documento doc, Integer idRemitente, Integer idDestinatario, String texto, Integer idDocOriginal, String nombrePC,Boolean horarioPermitido);

        void guardarTrazaFinalizarApoyo(Documento documento, String codSigoEstado);

        Documento anexarDocumento(Usuario usuario,Map<String, List<ArchivoTemporal>> mapUpload, Integer idDocumento);

        public Integer findCantMisExpedientes(Usuario objUsuario);

        void archivarDocumento(Integer idDocumento, Usuario remitente, String observacion, String tipoArchivar, String nombrePC);

        void reabrirDocumento(Documento documento, Usuario usuario, String nombrePC);

        List findDocumentosXExpediente(Usuario objUsuario, Integer idDocumento, boolean enviados);

        //void crearCopiaDelegacion(Documento doc, Usuario remitente, Integer idDestinatario, String mensaje, String nombrePC,Boolean horarioPermitido);

        List<Integer> getUsuariosPermitidos(Integer idDocumento);

        public List findDocumentosUsuarioFinalFiltro(Usuario objUsuario, String enviados, BusquedaAvanzada objFiltro);

        public List<Nodo> getTreeDocumentosRecibidos(Usuario objUsuario, boolean enviados);

        public List getContDocUsuarioFinal(Usuario objUsuario, boolean enviados,BusquedaAvanzada objFiltro);

        public List<Nodo> getTreeDocumentosRecibidosFiltro(Usuario objUsuario, boolean enviados, BusquedaAvanzada objFiltro);

        public List<NodoArbol> getDojoDocumentoTree(BusquedaAvanzada objFiltro, Integer idExpediente, Integer idDocumento);

        public List<Documento> getDocumentosDelExpediente(Integer idUsuario, Integer idDocumento);

        public List<SimpleNode> getDojoDocumentoRecibidoTree(BusquedaAvanzada objFiltro);

        public List<TramiteDocumentario> buscarTramiteDocumentario(String nroTramitedocumentario);

        public String getAtendidoTramiteDocumentario(Documento d);
        
        public int existsDocumentoPendiente(Documento documento);
        
        public List<Documento> getDocumentoByTipoByUnidad(String tipodocuento, String unidadautor, Integer expediente);
        
        public DocumentoDetail getDocumentDetailOptimizedAR(Integer iIdDoc, String strRol);
        
        public List<Documento> findByIdDocVirtual(Integer codigoVirtual);

}