
package com.ositran.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para peticionActualizarCredencial complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="peticionActualizarCredencial">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="credencialAnterior" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="credencialNueva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nuDni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nuRuc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "peticionActualizarCredencial", propOrder = {
    "credencialAnterior",
    "credencialNueva",
    "nuDni",
    "nuRuc"
})
public class PeticionActualizarCredencial {

    protected String credencialAnterior;
    protected String credencialNueva;
    protected String nuDni;
    protected String nuRuc;

    /**
     * Obtiene el valor de la propiedad credencialAnterior.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCredencialAnterior() {
        return credencialAnterior;
    }

    /**
     * Define el valor de la propiedad credencialAnterior.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCredencialAnterior(String value) {
        this.credencialAnterior = value;
    }

    /**
     * Obtiene el valor de la propiedad credencialNueva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCredencialNueva() {
        return credencialNueva;
    }

    /**
     * Define el valor de la propiedad credencialNueva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCredencialNueva(String value) {
        this.credencialNueva = value;
    }

    /**
     * Obtiene el valor de la propiedad nuDni.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNuDni() {
        return nuDni;
    }

    /**
     * Define el valor de la propiedad nuDni.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNuDni(String value) {
        this.nuDni = value;
    }

    /**
     * Obtiene el valor de la propiedad nuRuc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNuRuc() {
        return nuRuc;
    }

    /**
     * Define el valor de la propiedad nuRuc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNuRuc(String value) {
        this.nuRuc = value;
    }

}
