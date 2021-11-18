/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.dojo;

/**
 *
 * @author consultor_jti15
 */
public class RemitenteJSON {
  
    private String idCargoAdministrado;
    private String nombres;
    private String apellidoMaterno;
    private String apellidoPaterno;
    private String codRespuesta;
    private String idDetalleCliente;
    private String desCargoAdministrado;

    public String getDesCargoAdministrado() {
        return desCargoAdministrado;
    }

    public void setDesCargoAdministrado(String desCargoAdministrado) {
        this.desCargoAdministrado = desCargoAdministrado;
    }

    public String getIdDetalleCliente() {
        return idDetalleCliente;
    }

    public void setIdDetalleCliente(String idDetalleCliente) {
        this.idDetalleCliente = idDetalleCliente;
    }
    
    public RemitenteJSON(){
        
    }
    public RemitenteJSON(String idCargoAdministrado, String nombres, String apellidoPaterno, String apellidoMaterno, String idDetalleCliente, String codRespuesta, String desCargoAdministrado){
       // this.idRemitente = idRemitente;
        this.idCargoAdministrado = idCargoAdministrado;
        this.nombres = nombres==null? "":nombres;
        this.apellidoPaterno = apellidoPaterno == null?"":apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno == null? "": apellidoMaterno;
        this.codRespuesta = codRespuesta;
        this.idDetalleCliente = idDetalleCliente;
        this.desCargoAdministrado = desCargoAdministrado;
    }

    public String getCodRespuesta() {
        return codRespuesta;
    }

    public void setCodRespuesta(String codRespuesta) {
        this.codRespuesta = codRespuesta;
    }

    public String getIdCargoAdministrado() {
        return idCargoAdministrado;
    }

    public void setIdCargoAdministrado(String idCargoAdministrado) {
        this.idCargoAdministrado = idCargoAdministrado;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    
}
