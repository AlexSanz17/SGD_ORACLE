package org.ositran.utils;

import java.util.Date;


/**
 *
 * @author WMH
 */

public class Expedienfindadvance {

    private Integer idunidad;
    private String u_nombre;

    private Integer idusuario;
    private String usu_nombres;
    private String usu_apellidos;

    private Integer idproceso;
    private String pro_nombre;
    private String pro_descrip;

    private Integer idexpediente;
    private String exp_referencia;
    private Date exp_fchcreacion;
    private String exp_asunto;
    private Character exp_estado;

    private Integer idcliente;
    private String cli_nrodoc;
    private String cli_rzsocial;
    private String cli_dirprin;
    private String cli_diralter;
    private String cli_nombres;
    private String cli_apePaterno;
    private String cli_apeMaterno;



    private Integer idconcesionario;
    private String cn_rzsocial;
    private String cn_direc;

    private Integer iddocumento;
    private String doc_nrdoc;
    private String doc_asunto;
    private Integer doc_nrflio;
    private Date doc_fchcreacio;

    private Integer idtipodocumento;
    private String td_nombre;

    private String tidcli_nom;


    public Expedienfindadvance(){

        this.idunidad = 0;
        this.u_nombre = "";
        this.idusuario = 0;
        this.usu_nombres = "";
        this.usu_apellidos = "";
        this.idproceso = 0;
        this.pro_nombre = "";
        this.pro_descrip = "";
        this.idexpediente = 0;
        this.exp_referencia = "";
        this.exp_fchcreacion = new Date();
        this.idcliente = 0;
        this.cli_nrodoc = "";
        this.cli_rzsocial = "";
        this.cli_dirprin = "";
        this.cli_diralter = "";
        this.cli_nombres = "";
        this.cli_apePaterno = "";
        this.cli_apeMaterno = "";
        this.idconcesionario = 0;
        this.cn_rzsocial = "";
        this.cn_direc = "";
        this.iddocumento = 0;
        this.doc_nrdoc = "";
        this.doc_asunto = "";
        this.doc_nrflio = 0;
        this.doc_fchcreacio = new Date();
        this.idtipodocumento = 0;
        this.td_nombre = "";
        this.tidcli_nom = "";
    }


    public Expedienfindadvance(Integer idunidad, String u_nombre, Integer idusuario, String usu_nombres, String usu_apellidos, Integer idproceso, String pro_nombre, String pro_descrip, Integer idexpediente, String exp_referencia, Date exp_fchcreacion, String exp_asunto, Character exp_estado, Integer idcliente, String cli_nrodoc, String cli_rzsocial, String cli_dirprin, String cli_diralter, String cli_nombres, String cli_apePaterno, String cli_apeMaterno, Integer idconcesionario, String cn_rzsocial, String cn_direc, Integer iddocumento, String doc_nrdoc, String doc_asunto, Integer doc_nrflio, Date doc_fchcreacio, Integer idtipodocumento, String td_nombre, String tidcli_nom) {
        this.idunidad = idunidad;
        this.u_nombre = u_nombre;
        this.idusuario = idusuario;
        this.usu_nombres = usu_nombres;
        this.usu_apellidos = usu_apellidos;
        this.idproceso = idproceso;
        this.pro_nombre = pro_nombre;
        this.pro_descrip = pro_descrip;
        this.idexpediente = idexpediente;
        this.exp_referencia = exp_referencia;
        this.exp_fchcreacion = exp_fchcreacion;
        this.exp_asunto = exp_asunto;
        this.exp_estado = exp_estado;
        this.idcliente = idcliente;
        this.cli_nrodoc = cli_nrodoc;
        this.cli_rzsocial = cli_rzsocial;
        this.cli_dirprin = cli_dirprin;
        this.cli_diralter = cli_diralter;
        this.cli_nombres = cli_nombres;
        this.cli_apePaterno = cli_apePaterno;
        this.cli_apeMaterno = cli_apeMaterno;
        this.idconcesionario = idconcesionario;
        this.cn_rzsocial = cn_rzsocial;
        this.cn_direc = cn_direc;
        this.iddocumento = iddocumento;
        this.doc_nrdoc = doc_nrdoc;
        this.doc_asunto = doc_asunto;
        this.doc_nrflio = doc_nrflio;
        this.doc_fchcreacio = doc_fchcreacio;
        this.idtipodocumento = idtipodocumento;
        this.td_nombre = td_nombre;
        this.tidcli_nom = tidcli_nom;
    }

    public Expedienfindadvance(Integer idunidad, String u_nombre, Integer idusuario, String usu_nombres, String usu_apellidos, Integer idproceso, String pro_nombre, String pro_descrip, Integer idexpediente, String exp_referencia, Date exp_fchcreacion, Integer idconcesionario, String cn_rzsocial, String cn_direc, Integer iddocumento, String doc_nrdoc, String doc_asunto, Integer doc_nrflio, Date doc_fchcreacio, Integer idtipodocumento, String td_nombre) {
        this.idunidad = idunidad;
        this.u_nombre = u_nombre;
        this.idusuario = idusuario;
        this.usu_nombres = usu_nombres;
        this.usu_apellidos = usu_apellidos;
        this.idproceso = idproceso;
        this.pro_nombre = pro_nombre;
        this.pro_descrip = pro_descrip;
        this.idexpediente = idexpediente;
        this.exp_referencia = exp_referencia;
        this.exp_fchcreacion = exp_fchcreacion;
        this.idconcesionario = idconcesionario;
        this.cn_rzsocial = cn_rzsocial;
        this.cn_direc = cn_direc;
        this.iddocumento = iddocumento;
        this.doc_nrdoc = doc_nrdoc;
        this.doc_asunto = doc_asunto;
        this.doc_nrflio = doc_nrflio;
        this.doc_fchcreacio = doc_fchcreacio;
        this.idtipodocumento = idtipodocumento;
        this.td_nombre = td_nombre;
    }
    
  
      /**
     * @return the idunidad
     */
    public Integer getIdunidad() {
        return idunidad;
    }

    /**
     * @param idunidad the idunidad to set
     */
    public void setIdunidad(Integer idunidad) {
        this.idunidad = idunidad;
    }

    /**
     * @return the u_nombre
     */
    public String getU_nombre() {
        return u_nombre;
    }

    /**
     * @param u_nombre the u_nombre to set
     */
    public void setU_nombre(String u_nombre) {
        this.u_nombre = u_nombre;
    }

    /**
     * @return the idusuario
     */
    public Integer getIdusuario() {
        return idusuario;
    }

    /**
     * @param idusuario the idusuario to set
     */
    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    /**
     * @return the usu_nombres
     */
    public String getUsu_nombres() {
        return usu_nombres;
    }

    /**
     * @param usu_nombres the usu_nombres to set
     */
    public void setUsu_nombres(String usu_nombres) {
        this.usu_nombres = usu_nombres;
    }

    /**
     * @return the usu_apellidos
     */
    public String getUsu_apellidos() {
        return usu_apellidos;
    }

    /**
     * @param usu_apellidos the usu_apellidos to set
     */
    public void setUsu_apellidos(String usu_apellidos) {
        this.usu_apellidos = usu_apellidos;
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
     * @return the idcliente
     */
    public Integer getIdcliente() {
        return idcliente;
    }

    /**
     * @param idcliente the idcliente to set
     */
    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    /**
     * @return the cli_nrodoc
     */
    public String getCli_nrodoc() {
        return cli_nrodoc;
    }

    /**
     * @param cli_nrodoc the cli_nrodoc to set
     */
    public void setCli_nrodoc(String cli_nrodoc) {
        this.cli_nrodoc = cli_nrodoc;
    }

    /**
     * @return the cli_rzsocial
     */
    public String getCli_rzsocial() {
        return cli_rzsocial;
    }

    /**
     * @param cli_rzsocial the cli_rzsocial to set
     */
    public void setCli_rzsocial(String cli_rzsocial) {
        this.cli_rzsocial = cli_rzsocial;
    }

    /**
     * @return the cli_dirprin
     */
    public String getCli_dirprin() {
        return cli_dirprin;
    }

    /**
     * @param cli_dirprin the cli_dirprin to set
     */
    public void setCli_dirprin(String cli_dirprin) {
        this.cli_dirprin = cli_dirprin;
    }

    /**
     * @return the cli_diralter
     */
    public String getCli_diralter() {
        return cli_diralter;
    }

    /**
     * @param cli_diralter the cli_diralter to set
     */
    public void setCli_diralter(String cli_diralter) {
        this.cli_diralter = cli_diralter;
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

    /**
     * @return the idtipodocumento
     */
    public Integer getIdtipodocumento() {
        return idtipodocumento;
    }

    /**
     * @param idtipodocumento the idtipodocumento to set
     */
    public void setIdtipodocumento(Integer idtipodocumento) {
        this.idtipodocumento = idtipodocumento;
    }

    /**
     * @return the td_nombre
     */
    public String getTd_nombre() {
        return td_nombre;
    }

    /**
     * @param td_nombre the td_nombre to set
     */
    public void setTd_nombre(String td_nombre) {
        this.td_nombre = td_nombre;
    }

    /**
     * @return the tidcli_nom
     */
    public String getTidcli_nom() {
        return tidcli_nom;
    }

    /**
     * @param tidcli_nom the tidcli_nom to set
     */
    public void setTidcli_nom(String tidcli_nom) {
        this.tidcli_nom = tidcli_nom;
    }

    public String getCli_apeMaterno() {
        return cli_apeMaterno;
    }

    public void setCli_apeMaterno(String cli_apeMaterno) {
        this.cli_apeMaterno = cli_apeMaterno;
    }

    public String getCli_nombres() {
        return cli_nombres;
    }

    public void setCli_nombres(String cli_nombres) {
        this.cli_nombres = cli_nombres;
    }

    public String getCli_apePaterno() {
        return cli_apePaterno;
    }

    public void setCli_apePaterno(String cli_pePaterno) {
        this.cli_apePaterno = cli_pePaterno;
    }

   public String getExp_asunto() {
      return exp_asunto;
   }

   public void setExp_asunto(String exp_asunto) {
      this.exp_asunto = exp_asunto;
   }

   public Character getExp_estado() {
      return exp_estado;
   }

   public void setExp_estado(Character exp_estado) {
      this.exp_estado = exp_estado;
   }



    /**
     * @return the condic
     */

   
}
