/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.remote.service.impl;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.ositran.dojo.ClienteJSon;
import org.ositran.utils.ArchivoTemporal;
import org.ositran.services.ArchivoService;
import org.ositran.services.ClienteService;
import org.ositran.services.DocumentoService;
import org.ositran.services.ExpedienteService;
import org.ositran.services.ProcesoService;
import org.ositran.services.RepositorioService;
import org.ositran.services.RolService;
import org.ositran.services.TipodocumentoService;
import org.ositran.services.TipoidentificacionService;
import org.ositran.services.UsuarioService;
import org.ositran.remote.dto.in.ExpedienteInRO;
import org.ositran.remote.dto.out.ExpedienteOutRO;
import org.ositran.remote.service.ExpedienteRemoteWS;
import org.ositran.utils.Constantes;
import org.ositran.utils.DocumentoDetail;
import org.ositran.remote.dto.enums.InvocationResult;
import org.ositran.remote.dto.in.ArchivoInRO;
import org.ositran.remote.dto.in.ClienteInRO;
import org.ositran.remote.util.BeanConverterUtil;

import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Rol;
import com.btg.ositran.siged.domain.Tipodocumento;
import com.btg.ositran.siged.domain.Tipoidentificacion;
import com.btg.ositran.siged.domain.Usuario;

/**
 *
 * @author atc_developer
 */
@WebService(endpointInterface = "org.ositran.remote.service.ExpedienteRemoteWS", serviceName = "ExpedienteRemoteWS")
public class ExpedienteRemoteWSImpl implements ExpedienteRemoteWS {

    private static Logger log = Logger.getLogger(ExpedienteRemoteWSImpl.class);
    private ClienteService clienteService;
    private DocumentoService documentoService;
    private ExpedienteService expedienteService;
    private ProcesoService procesoService;
    private RepositorioService repositorioService;
    private RolService rolService;
    private TipodocumentoService tipodocumentoService;
    private UsuarioService usuarioService;
    private ArchivoService archivoService;
    private TipoidentificacionService tipoIdentificacionService;

    private static final Integer RUC_SIZE_NUMBER = 11;
    private static final Integer DNI_SIZE_NUMBER = 8;
    
    @Override
    public ExpedienteOutRO createExpWithOneDocument(ExpedienteInRO expedienteInRO) {
        ExpedienteOutRO expedienteOutRO = new ExpedienteOutRO();
        ClienteInRO clienteInRO = null;
        Cliente cliente = null;
        Date currentDate = Calendar.getInstance().getTime();
        try {
            //busqueda del usuario creador
            String usuarioRol = expedienteInRO.getUsuarioRol();
            Rol rol = rolService.findByNombre(usuarioRol);
            String rolToWorkWith = Constantes.ROL_USUARIO_FINAL;
            Usuario usuario = usuarioService.findObjectBy(expedienteInRO.getUsuarioLogin(), Constantes.ESTADO_ACTIVO);
            if (rol != null) {
                if (rol.getNombre().equalsIgnoreCase(rolToWorkWith)){
                    usuario.setRol(null);
                }else{
                    usuario.setRol(rol);
                    rolToWorkWith = usuario.getRol().getNombre();
                }
                
                if (log.isDebugEnabled()) {
                    log.debug("usuario [" + usuario.getUsuario() + "] rol [" + usuario.getRol() + "]");
                }
            }

            clienteInRO = expedienteInRO.getClienteInRO();
            if (clienteInRO!=null){
                //buscamos al cliente si es que existe.
                if (clienteInRO.getIdCliente()!=null && clienteInRO.getIdCliente().intValue()>0){
                    cliente = clienteService.findByIdCliente(clienteInRO.getIdCliente());
                }else{
                    if (clienteInRO.getNumeroIdentificacion()!=null && !clienteInRO.getNumeroIdentificacion().trim().equalsIgnoreCase("")){
                        cliente = clienteService.findByNroIdentificacion(clienteInRO.getNumeroIdentificacion());
                    }
                }
            }
            //en caso que no exista este se crea, si es una empresa se valida que exista en la sunat.
            if (cliente == null){
                String nroIdentificacion = clienteInRO.getNumeroIdentificacion();
                String tipoIdentificacion = null;
                if (nroIdentificacion!=null && !nroIdentificacion.trim().equalsIgnoreCase("")){
                    if (nroIdentificacion.trim().length() == RUC_SIZE_NUMBER.intValue()){
                        ClienteJSon clienteJSon = null;//clienteService.getClientePorRUCSunat(nroIdentificacion.trim());
                        cliente = BeanConverterUtil.createClienteFromClienteJSON(clienteJSon);
                        tipoIdentificacion = Constantes.TIPO_IDENTIFICACION_RUC;
                    }else if (nroIdentificacion.trim().length() == DNI_SIZE_NUMBER.intValue()){
                        //persona natural
                        cliente = BeanConverterUtil.createClienteFromClienteInRO(clienteInRO);
                        tipoIdentificacion = Constantes.TIPO_IDENTIFICACION_DNI;
                    }else{
                        //otro
                        cliente = BeanConverterUtil.createClienteFromClienteInRO(clienteInRO);
                        tipoIdentificacion = Constantes.TIPO_IDENTIFICACION_OTRO;
                    }                        
                }else{
                    expedienteOutRO.setInvocationResult(InvocationResult.FAILED);
                    expedienteOutRO.setMessage("El cliente asignado al expediente no tiene un numero de identificacion.");
                    return expedienteOutRO;
                }
                if (cliente != null){
                    Tipoidentificacion objTipoidentificacion = tipoIdentificacionService.buscarObjPor(tipoIdentificacion);
                    cliente.setTipoIdentificacion(objTipoidentificacion);
                    cliente.setFechaCreacion(currentDate);
                    cliente.setEstado('A');
                    cliente = clienteService.guardarObj(cliente, cliente, usuario.getUsuario(), Constantes.AUDITORIA_TIPO_REGISTRO);
                }else{
                    expedienteOutRO.setInvocationResult(InvocationResult.FAILED);
                    expedienteOutRO.setMessage("El cliente asignado no pudo ser registrado verifique los datos ingresados.");
                    return expedienteOutRO;
                }
            }

            DocumentoDetail documentoDetail = new DocumentoDetail();
            Proceso proceso = procesoService.findObjectBy(expedienteInRO.getProcesoNombre(), Constantes.ESTADO_ACTIVO);
            
            Tipodocumento tipoDocumento = tipodocumentoService.findByNombre(expedienteInRO.getDocumentoTipo());

            documentoDetail.setIIdProceso(proceso.getIdproceso());
            documentoDetail.setIIdCliente(cliente.getIdCliente());
            documentoDetail.setIIdTipoDocumento(tipoDocumento.getIdtipodocumento());
            documentoDetail.setEnumerarDocumento("N");
            documentoDetail.setTipoNumeracion(expedienteInRO.getDocumentoTipoNumeracion());
            documentoDetail.setStrNroDocumento(expedienteInRO.getDocumentoNumero());
            documentoDetail.setINroFolios(expedienteInRO.getDocumentoNumeroFolios());
            documentoDetail.setStrAsunto(expedienteInRO.getAsunto());
            documentoDetail.setsSumilla(expedienteInRO.getAsunto());
            documentoDetail.setStrObservacion(expedienteInRO.getObservacion());
            documentoDetail.setCEstado(Constantes.ESTADO_ACTIVO);
            documentoDetail.setCPrincipal(Constantes.DOCUMENTO_PRINCIPAL);
            documentoDetail.setStrFechaDocumento(expedienteInRO.getDocumentoFecha());
            documentoDetail.setFechacreacion(new Date());
            documentoDetail.setIdUsuarioLogeado(usuario.getIdusuario());
            documentoDetail.setsNroExpediente(expedienteInRO.getNumeroExpediente());
           // documentoDetail = documentoService.saveDocumentTEMPORAL(documentoDetail, Constantes.ACCION_REGISTRAR, 2, usuario, rolToWorkWith, "-");
            Documento documento = documentoDetail.getDoc();
            List<ArchivoInRO> archivos = expedienteInRO.getArchivos();
            if (archivos!=null && !archivos.isEmpty()){
                for(ArchivoInRO archivo:archivos){
                    ArchivoTemporal archivoTemporal = new ArchivoTemporal(archivo.getFileName(), new File(archivo.getTemporalPath()));
                    //archivoService.guardarArchivoTemporal(archivoTemporal, documento, archivo.getId(), usuario, "ES_nombrePDFprincipal");
                }
                 //JUANCA 
                //repositorioService.subirArchivosTransformadosARepositorio(documento.getIdDocumento(), true);
            }

            expedienteOutRO = BeanConverterUtil.convertDocumentoToExpedienteOutRO(documento);
            expedienteOutRO.setNumeroExpediente(documentoDetail.getStrReferencia());
            expedienteOutRO.setIdExpediente(documentoDetail.getIIdExpediente());
            expedienteOutRO.setCreatedDate(documentoDetail.getFechacreacion());
            expedienteOutRO.setInvocationResult(InvocationResult.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            expedienteOutRO = new ExpedienteOutRO();
            expedienteOutRO.setInvocationResult(InvocationResult.FAILED);
            expedienteOutRO.setMessage("Ocurrio un error al tratar de crear el expediente");
        }
        return expedienteOutRO;
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public DocumentoService getDocumentoService() {
        return documentoService;
    }

    public void setDocumentoService(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    public ExpedienteService getExpedienteService() {
        return expedienteService;
    }

    public void setExpedienteService(ExpedienteService expedienteService) {
        this.expedienteService = expedienteService;
    }

    public ProcesoService getProcesoService() {
        return procesoService;
    }

    public void setProcesoService(ProcesoService procesoService) {
        this.procesoService = procesoService;
    }

    public RepositorioService getRepositorioService() {
        return repositorioService;
    }

    public void setRepositorioService(RepositorioService repositorioService) {
        this.repositorioService = repositorioService;
    }

    public RolService getRolService() {
        return rolService;
    }

    public void setRolService(RolService rolService) {
        this.rolService = rolService;
    }

    public TipodocumentoService getTipodocumentoService() {
        return tipodocumentoService;
    }

    public void setTipodocumentoService(TipodocumentoService tipodocumentoService) {
        this.tipodocumentoService = tipodocumentoService;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public ArchivoService getArchivoService() {
        return archivoService;
    }

    public void setArchivoService(ArchivoService archivoService) {
        this.archivoService = archivoService;
    }

    public TipoidentificacionService getTipoIdentificacionService() {
        return tipoIdentificacionService;
    }

    public void setTipoIdentificacionService(TipoidentificacionService tipoIdentificacionService) {
        this.tipoIdentificacionService = tipoIdentificacionService;
    }
}
