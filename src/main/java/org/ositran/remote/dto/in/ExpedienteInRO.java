/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.remote.dto.in;

import java.util.List;
import org.ositran.remote.dto.base.BaseRemoteBean;

/**
 *
 * @author developer01
 */
public class ExpedienteInRO extends BaseRemoteBean{
 
   private Integer documentoNumeroFolios;
   private String asunto;
   private ClienteInRO clienteInRO;
   private String documentoEnumerar;
   private String documentoFecha;
   private String documentoNumero;
   private String documentoPrincipal;
   private String documentoTipo;
   private String documentoTipoNumeracion;
   private String observacion;
   private String procesoNombre;
   private String usuarioLogin;
   private String usuarioRol;
   private List<ArchivoInRO> archivos;
   private String numeroExpediente;

    /**
     * @return the documentoNumeroFolios
     */
    public Integer getDocumentoNumeroFolios() {
        return documentoNumeroFolios;
    }

    /**
     * @param documentoNumeroFolios the documentoNumeroFolios to set
     */
    public void setDocumentoNumeroFolios(Integer documentoNumeroFolios) {
        this.documentoNumeroFolios = documentoNumeroFolios;
    }

    /**
     * @return the asunto
     */
    public String getAsunto() {
        return asunto;
    }

    /**
     * @param asunto the asunto to set
     */
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    /**
     * @return the documentoEnumerar
     */
    public String getDocumentoEnumerar() {
        return documentoEnumerar;
    }

    /**
     * @param documentoEnumerar the documentoEnumerar to set
     */
    public void setDocumentoEnumerar(String documentoEnumerar) {
        this.documentoEnumerar = documentoEnumerar;
    }

    /**
     * @return the documentoFecha
     */
    public String getDocumentoFecha() {
        return documentoFecha;
    }

    /**
     * @param documentoFecha the documentoFecha to set
     */
    public void setDocumentoFecha(String documentoFecha) {
        this.documentoFecha = documentoFecha;
    }

    /**
     * @return the documentoNumero
     */
    public String getDocumentoNumero() {
        return documentoNumero;
    }

    /**
     * @param documentoNumero the documentoNumero to set
     */
    public void setDocumentoNumero(String documentoNumero) {
        this.documentoNumero = documentoNumero;
    }

    /**
     * @return the documentoPrincipal
     */
    public String getDocumentoPrincipal() {
        return documentoPrincipal;
    }

    /**
     * @param documentoPrincipal the documentoPrincipal to set
     */
    public void setDocumentoPrincipal(String documentoPrincipal) {
        this.documentoPrincipal = documentoPrincipal;
    }

    /**
     * @return the documentoTipo
     */
    public String getDocumentoTipo() {
        return documentoTipo;
    }

    /**
     * @param documentoTipo the documentoTipo to set
     */
    public void setDocumentoTipo(String documentoTipo) {
        this.documentoTipo = documentoTipo;
    }

    /**
     * @return the documentoTipoNumeracion
     */
    public String getDocumentoTipoNumeracion() {
        return documentoTipoNumeracion;
    }

    /**
     * @param documentoTipoNumeracion the documentoTipoNumeracion to set
     */
    public void setDocumentoTipoNumeracion(String documentoTipoNumeracion) {
        this.documentoTipoNumeracion = documentoTipoNumeracion;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    /**
     * @return the procesoNombre
     */
    public String getProcesoNombre() {
        return procesoNombre;
    }

    /**
     * @param procesoNombre the procesoNombre to set
     */
    public void setProcesoNombre(String procesoNombre) {
        this.procesoNombre = procesoNombre;
    }

    /**
     * @return the usuarioLogin
     */
    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    /**
     * @param usuarioLogin the usuarioLogin to set
     */
    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    /**
     * @return the usuarioRol
     */
    public String getUsuarioRol() {
        return usuarioRol;
    }

    /**
     * @param usuarioRol the usuarioRol to set
     */
    public void setUsuarioRol(String usuarioRol) {
        this.usuarioRol = usuarioRol;
    }

    /**
     * @return the archivos
     */
    public List<ArchivoInRO> getArchivos() {
        return archivos;
    }

    /**
     * @param archivos the archivos to set
     */
    public void setArchivos(List<ArchivoInRO> archivos) {
        this.archivos = archivos;
    }

    /**
     * @return the numeroExpediente
     */
    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    /**
     * @param numeroExpediente the numeroExpediente to set
     */
    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    /**
     * @return the clienteInRO
     */
    public ClienteInRO getClienteInRO() {
        return clienteInRO;
    }

    /**
     * @param clienteInRO the clienteInRO to set
     */
    public void setClienteInRO(ClienteInRO clienteInRO) {
        this.clienteInRO = clienteInRO;
    }
   
}
