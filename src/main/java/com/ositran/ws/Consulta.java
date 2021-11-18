
package com.ositran.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para consulta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="consulta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="strCodInstitucion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strMac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strNroIp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strNumDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strTipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consulta", propOrder = {
    "strCodInstitucion",
    "strMac",
    "strNroIp",
    "strNumDocumento",
    "strTipoDocumento"
})
@XmlSeeAlso({
    SolicitudBean.class
})
public abstract class Consulta {

    protected String strCodInstitucion;
    protected String strMac;
    protected String strNroIp;
    protected String strNumDocumento;
    protected String strTipoDocumento;

    /**
     * Obtiene el valor de la propiedad strCodInstitucion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrCodInstitucion() {
        return strCodInstitucion;
    }

    /**
     * Define el valor de la propiedad strCodInstitucion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrCodInstitucion(String value) {
        this.strCodInstitucion = value;
    }

    /**
     * Obtiene el valor de la propiedad strMac.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrMac() {
        return strMac;
    }

    /**
     * Define el valor de la propiedad strMac.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrMac(String value) {
        this.strMac = value;
    }

    /**
     * Obtiene el valor de la propiedad strNroIp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrNroIp() {
        return strNroIp;
    }

    /**
     * Define el valor de la propiedad strNroIp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrNroIp(String value) {
        this.strNroIp = value;
    }

    /**
     * Obtiene el valor de la propiedad strNumDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrNumDocumento() {
        return strNumDocumento;
    }

    /**
     * Define el valor de la propiedad strNumDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrNumDocumento(String value) {
        this.strNumDocumento = value;
    }

    /**
     * Obtiene el valor de la propiedad strTipoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrTipoDocumento() {
        return strTipoDocumento;
    }

    /**
     * Define el valor de la propiedad strTipoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrTipoDocumento(String value) {
        this.strTipoDocumento = value;
    }

}
