/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.utils;

/**
 *
 * @author jbengoa
 */
public class DocumentoPublicar implements java.io.Serializable{
    private Integer idrefarc;
    private Integer idDocumento;
    private Integer idDocumentoRef;
    private Integer idArchivo;
    private String estado;
    private String nombre;
    private String orden;

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }
    
    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Integer getIdDocumentoRef() {
        return idDocumentoRef;
    }

    public void setIdDocumentoRef(Integer idDocumentoRef) {
        this.idDocumentoRef = idDocumentoRef;
    }

    public Integer getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Integer idArchivo) {
        this.idArchivo = idArchivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the idrefarc
     */
    public Integer getIdrefarc() {
        return idrefarc;
    }

    /**
     * @param idrefarc the idrefarc to set
     */
    public void setIdrefarc(Integer idrefarc) {
        this.idrefarc = idrefarc;
    }
    
    
}
