
package com.ositran.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para datosPersona complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="datosPersona">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="apPrimer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apSegundo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="direccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estadoCivil" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="foto" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="prenombres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="restriccion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ubigeo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datosPersona", propOrder = {
    "apPrimer",
    "apSegundo",
    "direccion",
    "estadoCivil",
    "foto",
    "prenombres",
    "restriccion",
    "ubigeo"
})
public class DatosPersona {

    protected String apPrimer;
    protected String apSegundo;
    protected String direccion;
    protected String estadoCivil;
    protected byte[] foto;
    protected String prenombres;
    protected String restriccion;
    protected String ubigeo;

    /**
     * Obtiene el valor de la propiedad apPrimer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApPrimer() {
        return apPrimer;
    }

    /**
     * Define el valor de la propiedad apPrimer.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApPrimer(String value) {
        this.apPrimer = value;
    }

    /**
     * Obtiene el valor de la propiedad apSegundo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApSegundo() {
        return apSegundo;
    }

    /**
     * Define el valor de la propiedad apSegundo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApSegundo(String value) {
        this.apSegundo = value;
    }

    /**
     * Obtiene el valor de la propiedad direccion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Define el valor de la propiedad direccion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDireccion(String value) {
        this.direccion = value;
    }

    /**
     * Obtiene el valor de la propiedad estadoCivil.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * Define el valor de la propiedad estadoCivil.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstadoCivil(String value) {
        this.estadoCivil = value;
    }

    /**
     * Obtiene el valor de la propiedad foto.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFoto() {
        return foto;
    }

    /**
     * Define el valor de la propiedad foto.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFoto(byte[] value) {
        this.foto = value;
    }

    /**
     * Obtiene el valor de la propiedad prenombres.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrenombres() {
        return prenombres;
    }

    /**
     * Define el valor de la propiedad prenombres.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrenombres(String value) {
        this.prenombres = value;
    }

    /**
     * Obtiene el valor de la propiedad restriccion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRestriccion() {
        return restriccion;
    }

    /**
     * Define el valor de la propiedad restriccion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRestriccion(String value) {
        this.restriccion = value;
    }

    /**
     * Obtiene el valor de la propiedad ubigeo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUbigeo() {
        return ubigeo;
    }

    /**
     * Define el valor de la propiedad ubigeo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUbigeo(String value) {
        this.ubigeo = value;
    }

}
