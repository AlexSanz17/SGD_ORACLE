/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.utils;

import java.util.Date;


/**
 *
 * @author WMH
 */

public class ExpedientebusStor {

    private Integer idproceso;
    private String pro_nombre;
    private String pro_descrip;

    private Integer idexpediente;
    private String exp_referencia;
    private Date exp_fchcreacion;
    
    private String tipoanalisis;
    private String analista;
    private String suministro;

    private Integer idconcesionario;
    private String cn_rzsocial;
    private String cn_direc;

    private Integer iddocumento;
    private String doc_nrdoc;
    private String doc_asunto;
    private Integer doc_nrflio;
    private Date doc_fchcreacio;

    public ExpedientebusStor(Integer idproceso, String pro_nombre, String pro_descrip, Integer idexpediente, String exp_referencia, Date exp_fchcreacion, String tipoanalisis, String analista, String suministro, Integer idconcesionario, String cn_rzsocial, String cn_direc, Integer iddocumento, String doc_nrdoc, String doc_asunto, Integer doc_nrflio, Date doc_fchcreacio) {
        this.idproceso = idproceso;
        this.pro_nombre = pro_nombre;
        this.pro_descrip = pro_descrip;
        this.idexpediente = idexpediente;
        this.exp_referencia = exp_referencia;
        this.exp_fchcreacion = exp_fchcreacion;
        this.tipoanalisis = tipoanalisis;
        this.analista = analista;
        this.suministro = suministro;
        this.idconcesionario = idconcesionario;
        this.cn_rzsocial = cn_rzsocial;
        this.cn_direc = cn_direc;
        this.iddocumento = iddocumento;
        this.doc_nrdoc = doc_nrdoc;
        this.doc_asunto = doc_asunto;
        this.doc_nrflio = doc_nrflio;
        this.doc_fchcreacio = doc_fchcreacio;
    }

    public ExpedientebusStor() {
    }


    
    /**
     * @return the idproceso
     */
    public Integer getIdproceso() {
        return idproceso;
    }

    /**
     * @param idproceso the idproceso to set
     */
    public void setIdproceso(Integer idproceso) {
        this.idproceso = idproceso;
    }

    /**
     * @return the pro_nombre
     */
    public String getPro_nombre() {
        return pro_nombre;
    }

    /**
     * @param pro_nombre the pro_nombre to set
     */
    public void setPro_nombre(String pro_nombre) {
        this.pro_nombre = pro_nombre;
    }

    /**
     * @return the pro_descrip
     */
    public String getPro_descrip() {
        return pro_descrip;
    }

    /**
     * @param pro_descrip the pro_descrip to set
     */
    public void setPro_descrip(String pro_descrip) {
        this.pro_descrip = pro_descrip;
    }

    /**
     * @return the idexpediente
     */
    public Integer getIdexpediente() {
        return idexpediente;
    }

    /**
     * @param idexpediente the idexpediente to set
     */
    public void setIdexpediente(Integer idexpediente) {
        this.idexpediente = idexpediente;
    }

    /**
     * @return the exp_referencia
     */
    public String getExp_referencia() {
        return exp_referencia;
    }

    /**
     * @param exp_referencia the exp_referencia to set
     */
    public void setExp_referencia(String exp_referencia) {
        this.exp_referencia = exp_referencia;
    }

    /**
     * @return the exp_fchcreacion
     */
    public Date getExp_fchcreacion() {
        return exp_fchcreacion;
    }

    /**
     * @param exp_fchcreacion the exp_fchcreacion to set
     */
    public void setExp_fchcreacion(Date exp_fchcreacion) {
        this.exp_fchcreacion = exp_fchcreacion;
    }

    /**
     * @return the tipoanalisis
     */
    public String getTipoanalisis() {
        return tipoanalisis;
    }

    /**
     * @param tipoanalisis the tipoanalisis to set
     */
    public void setTipoanalisis(String tipoanalisis) {
        this.tipoanalisis = tipoanalisis;
    }

    /**
     * @return the analista
     */
    public String getAnalista() {
        return analista;
    }

    /**
     * @param analista the analista to set
     */
    public void setAnalista(String analista) {
        this.analista = analista;
    }

    /**
     * @return the suministro
     */
    public String getSuministro() {
        return suministro;
    }

    /**
     * @param suministro the suministro to set
     */
    public void setSuministro(String suministro) {
        this.suministro = suministro;
    }

    /**
     * @return the idconcesionario
     */
    public Integer getIdconcesionario() {
        return idconcesionario;
    }

    /**
     * @param idconcesionario the idconcesionario to set
     */
    public void setIdconcesionario(Integer idconcesionario) {
        this.idconcesionario = idconcesionario;
    }

    /**
     * @return the cn_rzsocial
     */
    public String getCn_rzsocial() {
        return cn_rzsocial;
    }

    /**
     * @param cn_rzsocial the cn_rzsocial to set
     */
    public void setCn_rzsocial(String cn_rzsocial) {
        this.cn_rzsocial = cn_rzsocial;
    }

    /**
     * @return the cn_direc
     */
    public String getCn_direc() {
        return cn_direc;
    }

    /**
     * @param cn_direc the cn_direc to set
     */
    public void setCn_direc(String cn_direc) {
        this.cn_direc = cn_direc;
    }

    /**
     * @return the iddocumento
     */
    public Integer getIddocumento() {
        return iddocumento;
    }

    /**
     * @param iddocumento the iddocumento to set
     */
    public void setIddocumento(Integer iddocumento) {
        this.iddocumento = iddocumento;
    }

    /**
     * @return the doc_nrdoc
     */
    public String getDoc_nrdoc() {
        return doc_nrdoc;
    }

    /**
     * @param doc_nrdoc the doc_nrdoc to set
     */
    public void setDoc_nrdoc(String doc_nrdoc) {
        this.doc_nrdoc = doc_nrdoc;
    }

    /**
     * @return the doc_asunto
     */
    public String getDoc_asunto() {
        return doc_asunto;
    }

    /**
     * @param doc_asunto the doc_asunto to set
     */
    public void setDoc_asunto(String doc_asunto) {
        this.doc_asunto = doc_asunto;
    }

    /**
     * @return the doc_nrflio
     */
    public Integer getDoc_nrflio() {
        return doc_nrflio;
    }

    /**
     * @param doc_nrflio the doc_nrflio to set
     */
    public void setDoc_nrflio(Integer doc_nrflio) {
        this.doc_nrflio = doc_nrflio;
    }

    /**
     * @return the doc_fchcreacio
     */
    public Date getDoc_fchcreacio() {
        return doc_fchcreacio;
    }

    /**
     * @param doc_fchcreacio the doc_fchcreacio to set
     */
    public void setDoc_fchcreacio(Date doc_fchcreacio) {
        this.doc_fchcreacio = doc_fchcreacio;
    }
}
