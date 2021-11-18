/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.dojo;

import java.util.List;
import org.ositran.dojo.grid.Estructura;

/**
 *
 * @author consultor_jti15
 */
public class ArchivoJSON {
   private List<?> items;
   private List<Estructura> structure;
   
   public List<Estructura> getStructure() {
	   return structure;
	}

	public void setStructure(List<Estructura> structure) {
	   this.structure = structure;
	}
   
   public List<?> getItems() {
     return items;
   }

   public void setItems(List<?> items) {
     this.items = items;
   }
    
  /*  
    private String idDocumento;
    private String rutaAlfresco;
    private String idAlfresco;
    private String objectId;
    private String numTramite;
    private String clave;
    private String qr;
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }
    
    public String getNumTramite() {
        return numTramite;
    }

    public void setNumTramite(String numTramite) {
        this.numTramite = numTramite;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getIdAlfresco() {
        return idAlfresco;
    }

    public void setIdAlfresco(String idAlfresco) {
        this.idAlfresco = idAlfresco;
    }
   
    /*
    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getRutaAlfresco() {
        return rutaAlfresco;
    }

    public void setRutaAlfresco(String rutaAlfresco) {
        this.rutaAlfresco = rutaAlfresco;
    }*/

}
