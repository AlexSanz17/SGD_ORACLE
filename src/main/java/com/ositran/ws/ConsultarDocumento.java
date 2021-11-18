
package com.ositran.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para consultarDocumento complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="consultarDocumento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="solicitud" type="{http://ws.consulta.usuarios.migraciones.gob.pe/}solicitudBean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultarDocumento", propOrder = {
    "solicitud"
})
public class ConsultarDocumento {

    protected SolicitudBean solicitud;

    /**
     * Obtiene el valor de la propiedad solicitud.
     * 
     * @return
     *     possible object is
     *     {@link SolicitudBean }
     *     
     */
    public SolicitudBean getSolicitud() {
        return solicitud;
    }

    /**
     * Define el valor de la propiedad solicitud.
     * 
     * @param value
     *     allowed object is
     *     {@link SolicitudBean }
     *     
     */
    public void setSolicitud(SolicitudBean value) {
        this.solicitud = value;
    }

}
