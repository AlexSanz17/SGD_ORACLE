/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.remote.dto.out;

import org.ositran.remote.dto.base.BaseRemoteBean;
import org.ositran.remote.dto.enums.InvocationResult;

/**
 *
 * @author atc_developer
 */
public class ExpedienteOutRO extends BaseRemoteBean{

    private Integer idDocumento;
    private Integer idExpediente;
    private String numeroExpediente;
    private char principal;
    private Integer numeroFolios;
    private String asunto;
    private char estado;
    private String numeroDocumento;
    private InvocationResult invocationResult;
    private String message;

    /**
     * @return the idDocumento
     */
    public Integer getIdDocumento() {
        return idDocumento;
    }

    /**
     * @param idDocumento the idDocumento to set
     */
    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
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
     * @return the principal
     */
    public char getPrincipal() {
        return principal;
    }

    /**
     * @param principal the principal to set
     */
    public void setPrincipal(char principal) {
        this.principal = principal;
    }

    /**
     * @return the numeroFolios
     */
    public Integer getNumeroFolios() {
        return numeroFolios;
    }

    /**
     * @param numeroFolios the numeroFolios to set
     */
    public void setNumeroFolios(Integer numeroFolios) {
        this.numeroFolios = numeroFolios;
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
     * @return the estado
     */
    public char getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(char estado) {
        this.estado = estado;
    }

    /**
     * @return the numeroDocumento
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * @param numeroDocumento the numeroDocumento to set
     */
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * @return the invocationResult
     */
    public InvocationResult getInvocationResult() {
        return invocationResult;
    }

    /**
     * @param invocationResult the invocationResult to set
     */
    public void setInvocationResult(InvocationResult invocationResult) {
        this.invocationResult = invocationResult;
    }

    /**
     * @return the idExpediente
     */
    public Integer getIdExpediente() {
        return idExpediente;
    }

    /**
     * @param idExpediente the idExpediente to set
     */
    public void setIdExpediente(Integer idExpediente) {
        this.idExpediente = idExpediente;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
