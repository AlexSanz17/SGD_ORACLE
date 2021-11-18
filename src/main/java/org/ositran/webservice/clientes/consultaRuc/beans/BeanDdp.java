/**
 * BeanDdp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.ositran.webservice.clientes.consultaRuc.beans;

public class BeanDdp  implements java.io.Serializable {
    private java.lang.String cod_dep;

    private java.lang.String cod_dist;

    private java.lang.String cod_prov;

    private java.lang.String ddp_ciiu;

    private java.lang.String ddp_doble;

    private java.lang.String ddp_estado;

    private java.lang.String ddp_fecact;

    private java.lang.String ddp_fecalt;

    private java.lang.String ddp_fecbaj;

    private java.lang.String ddp_flag22;

    private java.lang.String ddp_identi;

    private java.lang.String ddp_inter1;

    private java.lang.String ddp_lllttt;

    private java.lang.String ddp_mclase;

    private java.lang.String ddp_nombre;

    private java.lang.String ddp_nomvia;

    private java.lang.String ddp_nomzon;

    private java.lang.String ddp_numer1;

    private java.lang.String ddp_numreg;

    private java.lang.String ddp_numruc;

    private java.lang.String ddp_reacti;

    private java.lang.String ddp_refer1;

    private int ddp_secuen;

    private java.lang.String ddp_tamano;

    private java.lang.String ddp_tipvia;

    private java.lang.String ddp_tipzon;

    private java.lang.String ddp_tpoemp;

    private java.lang.String ddp_ubigeo;

    private java.lang.String ddp_userna;

    private java.lang.String desc_ciiu;

    private java.lang.String desc_dep;

    private java.lang.String desc_dist;

    private java.lang.String desc_estado;

    private java.lang.String desc_flag22;

    private java.lang.String desc_identi;

    private java.lang.String desc_numreg;

    private java.lang.String desc_prov;

    private java.lang.String desc_tamano;

    private java.lang.String desc_tipvia;

    private java.lang.String desc_tipzon;

    private java.lang.String desc_tpoemp;

    private boolean esActivo;

    private boolean esHabido;

    public BeanDdp() {
    }

    public BeanDdp(
           java.lang.String cod_dep,
           java.lang.String cod_dist,
           java.lang.String cod_prov,
           java.lang.String ddp_ciiu,
           java.lang.String ddp_doble,
           java.lang.String ddp_estado,
           java.lang.String ddp_fecact,
           java.lang.String ddp_fecalt,
           java.lang.String ddp_fecbaj,
           java.lang.String ddp_flag22,
           java.lang.String ddp_identi,
           java.lang.String ddp_inter1,
           java.lang.String ddp_lllttt,
           java.lang.String ddp_mclase,
           java.lang.String ddp_nombre,
           java.lang.String ddp_nomvia,
           java.lang.String ddp_nomzon,
           java.lang.String ddp_numer1,
           java.lang.String ddp_numreg,
           java.lang.String ddp_numruc,
           java.lang.String ddp_reacti,
           java.lang.String ddp_refer1,
           int ddp_secuen,
           java.lang.String ddp_tamano,
           java.lang.String ddp_tipvia,
           java.lang.String ddp_tipzon,
           java.lang.String ddp_tpoemp,
           java.lang.String ddp_ubigeo,
           java.lang.String ddp_userna,
           java.lang.String desc_ciiu,
           java.lang.String desc_dep,
           java.lang.String desc_dist,
           java.lang.String desc_estado,
           java.lang.String desc_flag22,
           java.lang.String desc_identi,
           java.lang.String desc_numreg,
           java.lang.String desc_prov,
           java.lang.String desc_tamano,
           java.lang.String desc_tipvia,
           java.lang.String desc_tipzon,
           java.lang.String desc_tpoemp,
           boolean esActivo,
           boolean esHabido) {
           this.cod_dep = cod_dep;
           this.cod_dist = cod_dist;
           this.cod_prov = cod_prov;
           this.ddp_ciiu = ddp_ciiu;
           this.ddp_doble = ddp_doble;
           this.ddp_estado = ddp_estado;
           this.ddp_fecact = ddp_fecact;
           this.ddp_fecalt = ddp_fecalt;
           this.ddp_fecbaj = ddp_fecbaj;
           this.ddp_flag22 = ddp_flag22;
           this.ddp_identi = ddp_identi;
           this.ddp_inter1 = ddp_inter1;
           this.ddp_lllttt = ddp_lllttt;
           this.ddp_mclase = ddp_mclase;
           this.ddp_nombre = ddp_nombre;
           this.ddp_nomvia = ddp_nomvia;
           this.ddp_nomzon = ddp_nomzon;
           this.ddp_numer1 = ddp_numer1;
           this.ddp_numreg = ddp_numreg;
           this.ddp_numruc = ddp_numruc;
           this.ddp_reacti = ddp_reacti;
           this.ddp_refer1 = ddp_refer1;
           this.ddp_secuen = ddp_secuen;
           this.ddp_tamano = ddp_tamano;
           this.ddp_tipvia = ddp_tipvia;
           this.ddp_tipzon = ddp_tipzon;
           this.ddp_tpoemp = ddp_tpoemp;
           this.ddp_ubigeo = ddp_ubigeo;
           this.ddp_userna = ddp_userna;
           this.desc_ciiu = desc_ciiu;
           this.desc_dep = desc_dep;
           this.desc_dist = desc_dist;
           this.desc_estado = desc_estado;
           this.desc_flag22 = desc_flag22;
           this.desc_identi = desc_identi;
           this.desc_numreg = desc_numreg;
           this.desc_prov = desc_prov;
           this.desc_tamano = desc_tamano;
           this.desc_tipvia = desc_tipvia;
           this.desc_tipzon = desc_tipzon;
           this.desc_tpoemp = desc_tpoemp;
           this.esActivo = esActivo;
           this.esHabido = esHabido;
    }


    /**
     * Gets the cod_dep value for this BeanDdp.
     * 
     * @return cod_dep
     */
    public java.lang.String getCod_dep() {
        return cod_dep;
    }


    /**
     * Sets the cod_dep value for this BeanDdp.
     * 
     * @param cod_dep
     */
    public void setCod_dep(java.lang.String cod_dep) {
        this.cod_dep = cod_dep;
    }


    /**
     * Gets the cod_dist value for this BeanDdp.
     * 
     * @return cod_dist
     */
    public java.lang.String getCod_dist() {
        return cod_dist;
    }


    /**
     * Sets the cod_dist value for this BeanDdp.
     * 
     * @param cod_dist
     */
    public void setCod_dist(java.lang.String cod_dist) {
        this.cod_dist = cod_dist;
    }


    /**
     * Gets the cod_prov value for this BeanDdp.
     * 
     * @return cod_prov
     */
    public java.lang.String getCod_prov() {
        return cod_prov;
    }


    /**
     * Sets the cod_prov value for this BeanDdp.
     * 
     * @param cod_prov
     */
    public void setCod_prov(java.lang.String cod_prov) {
        this.cod_prov = cod_prov;
    }


    /**
     * Gets the ddp_ciiu value for this BeanDdp.
     * 
     * @return ddp_ciiu
     */
    public java.lang.String getDdp_ciiu() {
        return ddp_ciiu;
    }


    /**
     * Sets the ddp_ciiu value for this BeanDdp.
     * 
     * @param ddp_ciiu
     */
    public void setDdp_ciiu(java.lang.String ddp_ciiu) {
        this.ddp_ciiu = ddp_ciiu;
    }


    /**
     * Gets the ddp_doble value for this BeanDdp.
     * 
     * @return ddp_doble
     */
    public java.lang.String getDdp_doble() {
        return ddp_doble;
    }


    /**
     * Sets the ddp_doble value for this BeanDdp.
     * 
     * @param ddp_doble
     */
    public void setDdp_doble(java.lang.String ddp_doble) {
        this.ddp_doble = ddp_doble;
    }


    /**
     * Gets the ddp_estado value for this BeanDdp.
     * 
     * @return ddp_estado
     */
    public java.lang.String getDdp_estado() {
        return ddp_estado;
    }


    /**
     * Sets the ddp_estado value for this BeanDdp.
     * 
     * @param ddp_estado
     */
    public void setDdp_estado(java.lang.String ddp_estado) {
        this.ddp_estado = ddp_estado;
    }


    /**
     * Gets the ddp_fecact value for this BeanDdp.
     * 
     * @return ddp_fecact
     */
    public java.lang.String getDdp_fecact() {
        return ddp_fecact;
    }


    /**
     * Sets the ddp_fecact value for this BeanDdp.
     * 
     * @param ddp_fecact
     */
    public void setDdp_fecact(java.lang.String ddp_fecact) {
        this.ddp_fecact = ddp_fecact;
    }


    /**
     * Gets the ddp_fecalt value for this BeanDdp.
     * 
     * @return ddp_fecalt
     */
    public java.lang.String getDdp_fecalt() {
        return ddp_fecalt;
    }


    /**
     * Sets the ddp_fecalt value for this BeanDdp.
     * 
     * @param ddp_fecalt
     */
    public void setDdp_fecalt(java.lang.String ddp_fecalt) {
        this.ddp_fecalt = ddp_fecalt;
    }


    /**
     * Gets the ddp_fecbaj value for this BeanDdp.
     * 
     * @return ddp_fecbaj
     */
    public java.lang.String getDdp_fecbaj() {
        return ddp_fecbaj;
    }


    /**
     * Sets the ddp_fecbaj value for this BeanDdp.
     * 
     * @param ddp_fecbaj
     */
    public void setDdp_fecbaj(java.lang.String ddp_fecbaj) {
        this.ddp_fecbaj = ddp_fecbaj;
    }


    /**
     * Gets the ddp_flag22 value for this BeanDdp.
     * 
     * @return ddp_flag22
     */
    public java.lang.String getDdp_flag22() {
        return ddp_flag22;
    }


    /**
     * Sets the ddp_flag22 value for this BeanDdp.
     * 
     * @param ddp_flag22
     */
    public void setDdp_flag22(java.lang.String ddp_flag22) {
        this.ddp_flag22 = ddp_flag22;
    }


    /**
     * Gets the ddp_identi value for this BeanDdp.
     * 
     * @return ddp_identi
     */
    public java.lang.String getDdp_identi() {
        return ddp_identi;
    }


    /**
     * Sets the ddp_identi value for this BeanDdp.
     * 
     * @param ddp_identi
     */
    public void setDdp_identi(java.lang.String ddp_identi) {
        this.ddp_identi = ddp_identi;
    }


    /**
     * Gets the ddp_inter1 value for this BeanDdp.
     * 
     * @return ddp_inter1
     */
    public java.lang.String getDdp_inter1() {
        return ddp_inter1;
    }


    /**
     * Sets the ddp_inter1 value for this BeanDdp.
     * 
     * @param ddp_inter1
     */
    public void setDdp_inter1(java.lang.String ddp_inter1) {
        this.ddp_inter1 = ddp_inter1;
    }


    /**
     * Gets the ddp_lllttt value for this BeanDdp.
     * 
     * @return ddp_lllttt
     */
    public java.lang.String getDdp_lllttt() {
        return ddp_lllttt;
    }


    /**
     * Sets the ddp_lllttt value for this BeanDdp.
     * 
     * @param ddp_lllttt
     */
    public void setDdp_lllttt(java.lang.String ddp_lllttt) {
        this.ddp_lllttt = ddp_lllttt;
    }


    /**
     * Gets the ddp_mclase value for this BeanDdp.
     * 
     * @return ddp_mclase
     */
    public java.lang.String getDdp_mclase() {
        return ddp_mclase;
    }


    /**
     * Sets the ddp_mclase value for this BeanDdp.
     * 
     * @param ddp_mclase
     */
    public void setDdp_mclase(java.lang.String ddp_mclase) {
        this.ddp_mclase = ddp_mclase;
    }


    /**
     * Gets the ddp_nombre value for this BeanDdp.
     * 
     * @return ddp_nombre
     */
    public java.lang.String getDdp_nombre() {
        return ddp_nombre;
    }


    /**
     * Sets the ddp_nombre value for this BeanDdp.
     * 
     * @param ddp_nombre
     */
    public void setDdp_nombre(java.lang.String ddp_nombre) {
        this.ddp_nombre = ddp_nombre;
    }


    /**
     * Gets the ddp_nomvia value for this BeanDdp.
     * 
     * @return ddp_nomvia
     */
    public java.lang.String getDdp_nomvia() {
        return ddp_nomvia;
    }


    /**
     * Sets the ddp_nomvia value for this BeanDdp.
     * 
     * @param ddp_nomvia
     */
    public void setDdp_nomvia(java.lang.String ddp_nomvia) {
        this.ddp_nomvia = ddp_nomvia;
    }


    /**
     * Gets the ddp_nomzon value for this BeanDdp.
     * 
     * @return ddp_nomzon
     */
    public java.lang.String getDdp_nomzon() {
        return ddp_nomzon;
    }


    /**
     * Sets the ddp_nomzon value for this BeanDdp.
     * 
     * @param ddp_nomzon
     */
    public void setDdp_nomzon(java.lang.String ddp_nomzon) {
        this.ddp_nomzon = ddp_nomzon;
    }


    /**
     * Gets the ddp_numer1 value for this BeanDdp.
     * 
     * @return ddp_numer1
     */
    public java.lang.String getDdp_numer1() {
        return ddp_numer1;
    }


    /**
     * Sets the ddp_numer1 value for this BeanDdp.
     * 
     * @param ddp_numer1
     */
    public void setDdp_numer1(java.lang.String ddp_numer1) {
        this.ddp_numer1 = ddp_numer1;
    }


    /**
     * Gets the ddp_numreg value for this BeanDdp.
     * 
     * @return ddp_numreg
     */
    public java.lang.String getDdp_numreg() {
        return ddp_numreg;
    }


    /**
     * Sets the ddp_numreg value for this BeanDdp.
     * 
     * @param ddp_numreg
     */
    public void setDdp_numreg(java.lang.String ddp_numreg) {
        this.ddp_numreg = ddp_numreg;
    }


    /**
     * Gets the ddp_numruc value for this BeanDdp.
     * 
     * @return ddp_numruc
     */
    public java.lang.String getDdp_numruc() {
        return ddp_numruc;
    }


    /**
     * Sets the ddp_numruc value for this BeanDdp.
     * 
     * @param ddp_numruc
     */
    public void setDdp_numruc(java.lang.String ddp_numruc) {
        this.ddp_numruc = ddp_numruc;
    }


    /**
     * Gets the ddp_reacti value for this BeanDdp.
     * 
     * @return ddp_reacti
     */
    public java.lang.String getDdp_reacti() {
        return ddp_reacti;
    }


    /**
     * Sets the ddp_reacti value for this BeanDdp.
     * 
     * @param ddp_reacti
     */
    public void setDdp_reacti(java.lang.String ddp_reacti) {
        this.ddp_reacti = ddp_reacti;
    }


    /**
     * Gets the ddp_refer1 value for this BeanDdp.
     * 
     * @return ddp_refer1
     */
    public java.lang.String getDdp_refer1() {
        return ddp_refer1;
    }


    /**
     * Sets the ddp_refer1 value for this BeanDdp.
     * 
     * @param ddp_refer1
     */
    public void setDdp_refer1(java.lang.String ddp_refer1) {
        this.ddp_refer1 = ddp_refer1;
    }


    /**
     * Gets the ddp_secuen value for this BeanDdp.
     * 
     * @return ddp_secuen
     */
    public int getDdp_secuen() {
        return ddp_secuen;
    }


    /**
     * Sets the ddp_secuen value for this BeanDdp.
     * 
     * @param ddp_secuen
     */
    public void setDdp_secuen(int ddp_secuen) {
        this.ddp_secuen = ddp_secuen;
    }


    /**
     * Gets the ddp_tamano value for this BeanDdp.
     * 
     * @return ddp_tamano
     */
    public java.lang.String getDdp_tamano() {
        return ddp_tamano;
    }


    /**
     * Sets the ddp_tamano value for this BeanDdp.
     * 
     * @param ddp_tamano
     */
    public void setDdp_tamano(java.lang.String ddp_tamano) {
        this.ddp_tamano = ddp_tamano;
    }


    /**
     * Gets the ddp_tipvia value for this BeanDdp.
     * 
     * @return ddp_tipvia
     */
    public java.lang.String getDdp_tipvia() {
        return ddp_tipvia;
    }


    /**
     * Sets the ddp_tipvia value for this BeanDdp.
     * 
     * @param ddp_tipvia
     */
    public void setDdp_tipvia(java.lang.String ddp_tipvia) {
        this.ddp_tipvia = ddp_tipvia;
    }


    /**
     * Gets the ddp_tipzon value for this BeanDdp.
     * 
     * @return ddp_tipzon
     */
    public java.lang.String getDdp_tipzon() {
        return ddp_tipzon;
    }


    /**
     * Sets the ddp_tipzon value for this BeanDdp.
     * 
     * @param ddp_tipzon
     */
    public void setDdp_tipzon(java.lang.String ddp_tipzon) {
        this.ddp_tipzon = ddp_tipzon;
    }


    /**
     * Gets the ddp_tpoemp value for this BeanDdp.
     * 
     * @return ddp_tpoemp
     */
    public java.lang.String getDdp_tpoemp() {
        return ddp_tpoemp;
    }


    /**
     * Sets the ddp_tpoemp value for this BeanDdp.
     * 
     * @param ddp_tpoemp
     */
    public void setDdp_tpoemp(java.lang.String ddp_tpoemp) {
        this.ddp_tpoemp = ddp_tpoemp;
    }


    /**
     * Gets the ddp_ubigeo value for this BeanDdp.
     * 
     * @return ddp_ubigeo
     */
    public java.lang.String getDdp_ubigeo() {
        return ddp_ubigeo;
    }


    /**
     * Sets the ddp_ubigeo value for this BeanDdp.
     * 
     * @param ddp_ubigeo
     */
    public void setDdp_ubigeo(java.lang.String ddp_ubigeo) {
        this.ddp_ubigeo = ddp_ubigeo;
    }


    /**
     * Gets the ddp_userna value for this BeanDdp.
     * 
     * @return ddp_userna
     */
    public java.lang.String getDdp_userna() {
        return ddp_userna;
    }


    /**
     * Sets the ddp_userna value for this BeanDdp.
     * 
     * @param ddp_userna
     */
    public void setDdp_userna(java.lang.String ddp_userna) {
        this.ddp_userna = ddp_userna;
    }


    /**
     * Gets the desc_ciiu value for this BeanDdp.
     * 
     * @return desc_ciiu
     */
    public java.lang.String getDesc_ciiu() {
        return desc_ciiu;
    }


    /**
     * Sets the desc_ciiu value for this BeanDdp.
     * 
     * @param desc_ciiu
     */
    public void setDesc_ciiu(java.lang.String desc_ciiu) {
        this.desc_ciiu = desc_ciiu;
    }


    /**
     * Gets the desc_dep value for this BeanDdp.
     * 
     * @return desc_dep
     */
    public java.lang.String getDesc_dep() {
        return desc_dep;
    }


    /**
     * Sets the desc_dep value for this BeanDdp.
     * 
     * @param desc_dep
     */
    public void setDesc_dep(java.lang.String desc_dep) {
        this.desc_dep = desc_dep;
    }


    /**
     * Gets the desc_dist value for this BeanDdp.
     * 
     * @return desc_dist
     */
    public java.lang.String getDesc_dist() {
        return desc_dist;
    }


    /**
     * Sets the desc_dist value for this BeanDdp.
     * 
     * @param desc_dist
     */
    public void setDesc_dist(java.lang.String desc_dist) {
        this.desc_dist = desc_dist;
    }


    /**
     * Gets the desc_estado value for this BeanDdp.
     * 
     * @return desc_estado
     */
    public java.lang.String getDesc_estado() {
        return desc_estado;
    }


    /**
     * Sets the desc_estado value for this BeanDdp.
     * 
     * @param desc_estado
     */
    public void setDesc_estado(java.lang.String desc_estado) {
        this.desc_estado = desc_estado;
    }


    /**
     * Gets the desc_flag22 value for this BeanDdp.
     * 
     * @return desc_flag22
     */
    public java.lang.String getDesc_flag22() {
        return desc_flag22;
    }


    /**
     * Sets the desc_flag22 value for this BeanDdp.
     * 
     * @param desc_flag22
     */
    public void setDesc_flag22(java.lang.String desc_flag22) {
        this.desc_flag22 = desc_flag22;
    }


    /**
     * Gets the desc_identi value for this BeanDdp.
     * 
     * @return desc_identi
     */
    public java.lang.String getDesc_identi() {
        return desc_identi;
    }


    /**
     * Sets the desc_identi value for this BeanDdp.
     * 
     * @param desc_identi
     */
    public void setDesc_identi(java.lang.String desc_identi) {
        this.desc_identi = desc_identi;
    }


    /**
     * Gets the desc_numreg value for this BeanDdp.
     * 
     * @return desc_numreg
     */
    public java.lang.String getDesc_numreg() {
        return desc_numreg;
    }


    /**
     * Sets the desc_numreg value for this BeanDdp.
     * 
     * @param desc_numreg
     */
    public void setDesc_numreg(java.lang.String desc_numreg) {
        this.desc_numreg = desc_numreg;
    }


    /**
     * Gets the desc_prov value for this BeanDdp.
     * 
     * @return desc_prov
     */
    public java.lang.String getDesc_prov() {
        return desc_prov;
    }


    /**
     * Sets the desc_prov value for this BeanDdp.
     * 
     * @param desc_prov
     */
    public void setDesc_prov(java.lang.String desc_prov) {
        this.desc_prov = desc_prov;
    }


    /**
     * Gets the desc_tamano value for this BeanDdp.
     * 
     * @return desc_tamano
     */
    public java.lang.String getDesc_tamano() {
        return desc_tamano;
    }


    /**
     * Sets the desc_tamano value for this BeanDdp.
     * 
     * @param desc_tamano
     */
    public void setDesc_tamano(java.lang.String desc_tamano) {
        this.desc_tamano = desc_tamano;
    }


    /**
     * Gets the desc_tipvia value for this BeanDdp.
     * 
     * @return desc_tipvia
     */
    public java.lang.String getDesc_tipvia() {
        return desc_tipvia;
    }


    /**
     * Sets the desc_tipvia value for this BeanDdp.
     * 
     * @param desc_tipvia
     */
    public void setDesc_tipvia(java.lang.String desc_tipvia) {
        this.desc_tipvia = desc_tipvia;
    }


    /**
     * Gets the desc_tipzon value for this BeanDdp.
     * 
     * @return desc_tipzon
     */
    public java.lang.String getDesc_tipzon() {
        return desc_tipzon;
    }


    /**
     * Sets the desc_tipzon value for this BeanDdp.
     * 
     * @param desc_tipzon
     */
    public void setDesc_tipzon(java.lang.String desc_tipzon) {
        this.desc_tipzon = desc_tipzon;
    }


    /**
     * Gets the desc_tpoemp value for this BeanDdp.
     * 
     * @return desc_tpoemp
     */
    public java.lang.String getDesc_tpoemp() {
        return desc_tpoemp;
    }


    /**
     * Sets the desc_tpoemp value for this BeanDdp.
     * 
     * @param desc_tpoemp
     */
    public void setDesc_tpoemp(java.lang.String desc_tpoemp) {
        this.desc_tpoemp = desc_tpoemp;
    }


    /**
     * Gets the esActivo value for this BeanDdp.
     * 
     * @return esActivo
     */
    public boolean isEsActivo() {
        return esActivo;
    }


    /**
     * Sets the esActivo value for this BeanDdp.
     * 
     * @param esActivo
     */
    public void setEsActivo(boolean esActivo) {
        this.esActivo = esActivo;
    }


    /**
     * Gets the esHabido value for this BeanDdp.
     * 
     * @return esHabido
     */
    public boolean isEsHabido() {
        return esHabido;
    }


    /**
     * Sets the esHabido value for this BeanDdp.
     * 
     * @param esHabido
     */
    public void setEsHabido(boolean esHabido) {
        this.esHabido = esHabido;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BeanDdp)) return false;
        BeanDdp other = (BeanDdp) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cod_dep==null && other.getCod_dep()==null) || 
             (this.cod_dep!=null &&
              this.cod_dep.equals(other.getCod_dep()))) &&
            ((this.cod_dist==null && other.getCod_dist()==null) || 
             (this.cod_dist!=null &&
              this.cod_dist.equals(other.getCod_dist()))) &&
            ((this.cod_prov==null && other.getCod_prov()==null) || 
             (this.cod_prov!=null &&
              this.cod_prov.equals(other.getCod_prov()))) &&
            ((this.ddp_ciiu==null && other.getDdp_ciiu()==null) || 
             (this.ddp_ciiu!=null &&
              this.ddp_ciiu.equals(other.getDdp_ciiu()))) &&
            ((this.ddp_doble==null && other.getDdp_doble()==null) || 
             (this.ddp_doble!=null &&
              this.ddp_doble.equals(other.getDdp_doble()))) &&
            ((this.ddp_estado==null && other.getDdp_estado()==null) || 
             (this.ddp_estado!=null &&
              this.ddp_estado.equals(other.getDdp_estado()))) &&
            ((this.ddp_fecact==null && other.getDdp_fecact()==null) || 
             (this.ddp_fecact!=null &&
              this.ddp_fecact.equals(other.getDdp_fecact()))) &&
            ((this.ddp_fecalt==null && other.getDdp_fecalt()==null) || 
             (this.ddp_fecalt!=null &&
              this.ddp_fecalt.equals(other.getDdp_fecalt()))) &&
            ((this.ddp_fecbaj==null && other.getDdp_fecbaj()==null) || 
             (this.ddp_fecbaj!=null &&
              this.ddp_fecbaj.equals(other.getDdp_fecbaj()))) &&
            ((this.ddp_flag22==null && other.getDdp_flag22()==null) || 
             (this.ddp_flag22!=null &&
              this.ddp_flag22.equals(other.getDdp_flag22()))) &&
            ((this.ddp_identi==null && other.getDdp_identi()==null) || 
             (this.ddp_identi!=null &&
              this.ddp_identi.equals(other.getDdp_identi()))) &&
            ((this.ddp_inter1==null && other.getDdp_inter1()==null) || 
             (this.ddp_inter1!=null &&
              this.ddp_inter1.equals(other.getDdp_inter1()))) &&
            ((this.ddp_lllttt==null && other.getDdp_lllttt()==null) || 
             (this.ddp_lllttt!=null &&
              this.ddp_lllttt.equals(other.getDdp_lllttt()))) &&
            ((this.ddp_mclase==null && other.getDdp_mclase()==null) || 
             (this.ddp_mclase!=null &&
              this.ddp_mclase.equals(other.getDdp_mclase()))) &&
            ((this.ddp_nombre==null && other.getDdp_nombre()==null) || 
             (this.ddp_nombre!=null &&
              this.ddp_nombre.equals(other.getDdp_nombre()))) &&
            ((this.ddp_nomvia==null && other.getDdp_nomvia()==null) || 
             (this.ddp_nomvia!=null &&
              this.ddp_nomvia.equals(other.getDdp_nomvia()))) &&
            ((this.ddp_nomzon==null && other.getDdp_nomzon()==null) || 
             (this.ddp_nomzon!=null &&
              this.ddp_nomzon.equals(other.getDdp_nomzon()))) &&
            ((this.ddp_numer1==null && other.getDdp_numer1()==null) || 
             (this.ddp_numer1!=null &&
              this.ddp_numer1.equals(other.getDdp_numer1()))) &&
            ((this.ddp_numreg==null && other.getDdp_numreg()==null) || 
             (this.ddp_numreg!=null &&
              this.ddp_numreg.equals(other.getDdp_numreg()))) &&
            ((this.ddp_numruc==null && other.getDdp_numruc()==null) || 
             (this.ddp_numruc!=null &&
              this.ddp_numruc.equals(other.getDdp_numruc()))) &&
            ((this.ddp_reacti==null && other.getDdp_reacti()==null) || 
             (this.ddp_reacti!=null &&
              this.ddp_reacti.equals(other.getDdp_reacti()))) &&
            ((this.ddp_refer1==null && other.getDdp_refer1()==null) || 
             (this.ddp_refer1!=null &&
              this.ddp_refer1.equals(other.getDdp_refer1()))) &&
            this.ddp_secuen == other.getDdp_secuen() &&
            ((this.ddp_tamano==null && other.getDdp_tamano()==null) || 
             (this.ddp_tamano!=null &&
              this.ddp_tamano.equals(other.getDdp_tamano()))) &&
            ((this.ddp_tipvia==null && other.getDdp_tipvia()==null) || 
             (this.ddp_tipvia!=null &&
              this.ddp_tipvia.equals(other.getDdp_tipvia()))) &&
            ((this.ddp_tipzon==null && other.getDdp_tipzon()==null) || 
             (this.ddp_tipzon!=null &&
              this.ddp_tipzon.equals(other.getDdp_tipzon()))) &&
            ((this.ddp_tpoemp==null && other.getDdp_tpoemp()==null) || 
             (this.ddp_tpoemp!=null &&
              this.ddp_tpoemp.equals(other.getDdp_tpoemp()))) &&
            ((this.ddp_ubigeo==null && other.getDdp_ubigeo()==null) || 
             (this.ddp_ubigeo!=null &&
              this.ddp_ubigeo.equals(other.getDdp_ubigeo()))) &&
            ((this.ddp_userna==null && other.getDdp_userna()==null) || 
             (this.ddp_userna!=null &&
              this.ddp_userna.equals(other.getDdp_userna()))) &&
            ((this.desc_ciiu==null && other.getDesc_ciiu()==null) || 
             (this.desc_ciiu!=null &&
              this.desc_ciiu.equals(other.getDesc_ciiu()))) &&
            ((this.desc_dep==null && other.getDesc_dep()==null) || 
             (this.desc_dep!=null &&
              this.desc_dep.equals(other.getDesc_dep()))) &&
            ((this.desc_dist==null && other.getDesc_dist()==null) || 
             (this.desc_dist!=null &&
              this.desc_dist.equals(other.getDesc_dist()))) &&
            ((this.desc_estado==null && other.getDesc_estado()==null) || 
             (this.desc_estado!=null &&
              this.desc_estado.equals(other.getDesc_estado()))) &&
            ((this.desc_flag22==null && other.getDesc_flag22()==null) || 
             (this.desc_flag22!=null &&
              this.desc_flag22.equals(other.getDesc_flag22()))) &&
            ((this.desc_identi==null && other.getDesc_identi()==null) || 
             (this.desc_identi!=null &&
              this.desc_identi.equals(other.getDesc_identi()))) &&
            ((this.desc_numreg==null && other.getDesc_numreg()==null) || 
             (this.desc_numreg!=null &&
              this.desc_numreg.equals(other.getDesc_numreg()))) &&
            ((this.desc_prov==null && other.getDesc_prov()==null) || 
             (this.desc_prov!=null &&
              this.desc_prov.equals(other.getDesc_prov()))) &&
            ((this.desc_tamano==null && other.getDesc_tamano()==null) || 
             (this.desc_tamano!=null &&
              this.desc_tamano.equals(other.getDesc_tamano()))) &&
            ((this.desc_tipvia==null && other.getDesc_tipvia()==null) || 
             (this.desc_tipvia!=null &&
              this.desc_tipvia.equals(other.getDesc_tipvia()))) &&
            ((this.desc_tipzon==null && other.getDesc_tipzon()==null) || 
             (this.desc_tipzon!=null &&
              this.desc_tipzon.equals(other.getDesc_tipzon()))) &&
            ((this.desc_tpoemp==null && other.getDesc_tpoemp()==null) || 
             (this.desc_tpoemp!=null &&
              this.desc_tpoemp.equals(other.getDesc_tpoemp()))) &&
            this.esActivo == other.isEsActivo() &&
            this.esHabido == other.isEsHabido();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCod_dep() != null) {
            _hashCode += getCod_dep().hashCode();
        }
        if (getCod_dist() != null) {
            _hashCode += getCod_dist().hashCode();
        }
        if (getCod_prov() != null) {
            _hashCode += getCod_prov().hashCode();
        }
        if (getDdp_ciiu() != null) {
            _hashCode += getDdp_ciiu().hashCode();
        }
        if (getDdp_doble() != null) {
            _hashCode += getDdp_doble().hashCode();
        }
        if (getDdp_estado() != null) {
            _hashCode += getDdp_estado().hashCode();
        }
        if (getDdp_fecact() != null) {
            _hashCode += getDdp_fecact().hashCode();
        }
        if (getDdp_fecalt() != null) {
            _hashCode += getDdp_fecalt().hashCode();
        }
        if (getDdp_fecbaj() != null) {
            _hashCode += getDdp_fecbaj().hashCode();
        }
        if (getDdp_flag22() != null) {
            _hashCode += getDdp_flag22().hashCode();
        }
        if (getDdp_identi() != null) {
            _hashCode += getDdp_identi().hashCode();
        }
        if (getDdp_inter1() != null) {
            _hashCode += getDdp_inter1().hashCode();
        }
        if (getDdp_lllttt() != null) {
            _hashCode += getDdp_lllttt().hashCode();
        }
        if (getDdp_mclase() != null) {
            _hashCode += getDdp_mclase().hashCode();
        }
        if (getDdp_nombre() != null) {
            _hashCode += getDdp_nombre().hashCode();
        }
        if (getDdp_nomvia() != null) {
            _hashCode += getDdp_nomvia().hashCode();
        }
        if (getDdp_nomzon() != null) {
            _hashCode += getDdp_nomzon().hashCode();
        }
        if (getDdp_numer1() != null) {
            _hashCode += getDdp_numer1().hashCode();
        }
        if (getDdp_numreg() != null) {
            _hashCode += getDdp_numreg().hashCode();
        }
        if (getDdp_numruc() != null) {
            _hashCode += getDdp_numruc().hashCode();
        }
        if (getDdp_reacti() != null) {
            _hashCode += getDdp_reacti().hashCode();
        }
        if (getDdp_refer1() != null) {
            _hashCode += getDdp_refer1().hashCode();
        }
        _hashCode += getDdp_secuen();
        if (getDdp_tamano() != null) {
            _hashCode += getDdp_tamano().hashCode();
        }
        if (getDdp_tipvia() != null) {
            _hashCode += getDdp_tipvia().hashCode();
        }
        if (getDdp_tipzon() != null) {
            _hashCode += getDdp_tipzon().hashCode();
        }
        if (getDdp_tpoemp() != null) {
            _hashCode += getDdp_tpoemp().hashCode();
        }
        if (getDdp_ubigeo() != null) {
            _hashCode += getDdp_ubigeo().hashCode();
        }
        if (getDdp_userna() != null) {
            _hashCode += getDdp_userna().hashCode();
        }
        if (getDesc_ciiu() != null) {
            _hashCode += getDesc_ciiu().hashCode();
        }
        if (getDesc_dep() != null) {
            _hashCode += getDesc_dep().hashCode();
        }
        if (getDesc_dist() != null) {
            _hashCode += getDesc_dist().hashCode();
        }
        if (getDesc_estado() != null) {
            _hashCode += getDesc_estado().hashCode();
        }
        if (getDesc_flag22() != null) {
            _hashCode += getDesc_flag22().hashCode();
        }
        if (getDesc_identi() != null) {
            _hashCode += getDesc_identi().hashCode();
        }
        if (getDesc_numreg() != null) {
            _hashCode += getDesc_numreg().hashCode();
        }
        if (getDesc_prov() != null) {
            _hashCode += getDesc_prov().hashCode();
        }
        if (getDesc_tamano() != null) {
            _hashCode += getDesc_tamano().hashCode();
        }
        if (getDesc_tipvia() != null) {
            _hashCode += getDesc_tipvia().hashCode();
        }
        if (getDesc_tipzon() != null) {
            _hashCode += getDesc_tipzon().hashCode();
        }
        if (getDesc_tpoemp() != null) {
            _hashCode += getDesc_tpoemp().hashCode();
        }
        _hashCode += (isEsActivo() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isEsHabido() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BeanDdp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:ConsultaRuc", "BeanDdp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cod_dep");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cod_dep"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cod_dist");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cod_dist"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cod_prov");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cod_prov"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_ciiu");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_ciiu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_doble");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_doble"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_estado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_fecact");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_fecact"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_fecalt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_fecalt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_fecbaj");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_fecbaj"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_flag22");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_flag22"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_identi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_identi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_inter1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_inter1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_lllttt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_lllttt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_mclase");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_mclase"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_nombre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_nombre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_nomvia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_nomvia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_nomzon");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_nomzon"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_numer1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_numer1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_numreg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_numreg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_numruc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_numruc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_reacti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_reacti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_refer1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_refer1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_secuen");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_secuen"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_tamano");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_tamano"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_tipvia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_tipvia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_tipzon");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_tipzon"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_tpoemp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_tpoemp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_ubigeo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_ubigeo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ddp_userna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ddp_userna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc_ciiu");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc_ciiu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc_dep");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc_dep"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc_dist");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc_dist"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc_estado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc_estado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc_flag22");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc_flag22"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc_identi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc_identi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc_numreg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc_numreg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc_prov");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc_prov"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc_tamano");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc_tamano"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc_tipvia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc_tipvia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc_tipzon");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc_tipzon"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desc_tpoemp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desc_tpoemp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esActivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "esActivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esHabido");
        elemField.setXmlName(new javax.xml.namespace.QName("", "esHabido"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
