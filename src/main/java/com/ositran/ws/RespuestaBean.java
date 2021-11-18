
package com.ositran.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para respuestaBean complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="respuestaBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="strCalidadMigratoria" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strNombres" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strNumRespuesta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strPrimerApellido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strSegundoApellido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaBean", propOrder = {
    "strCalidadMigratoria",
    "strNombres",
    "strNumRespuesta",
    "strPrimerApellido",
    "strSegundoApellido"
})
public class RespuestaBean {

    protected String strCalidadMigratoria;
    protected String strNombres;
    protected String strNumRespuesta;
    protected String strPrimerApellido;
    protected String strSegundoApellido;

    /**
     * Obtiene el valor de la propiedad strCalidadMigratoria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrCalidadMigratoria() {
        return strCalidadMigratoria;
    }

    /**
     * Define el valor de la propiedad strCalidadMigratoria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrCalidadMigratoria(String value) {
        this.strCalidadMigratoria = value;
    }

    /**
     * Obtiene el valor de la propiedad strNombres.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrNombres() {
        return strNombres;
    }

    /**
     * Define el valor de la propiedad strNombres.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrNombres(String value) {
        this.strNombres = value;
    }

    /**
     * Obtiene el valor de la propiedad strNumRespuesta.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrNumRespuesta() {
        return strNumRespuesta;
    }

    /**
     * Define el valor de la propiedad strNumRespuesta.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrNumRespuesta(String value) {
        this.strNumRespuesta = value;
    }

    /**
     * Obtiene el valor de la propiedad strPrimerApellido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrPrimerApellido() {
        return strPrimerApellido;
    }

    /**
     * Define el valor de la propiedad strPrimerApellido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrPrimerApellido(String value) {
        this.strPrimerApellido = value;
    }

    /**
     * Obtiene el valor de la propiedad strSegundoApellido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrSegundoApellido() {
        return strSegundoApellido;
    }

    /**
     * Define el valor de la propiedad strSegundoApellido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrSegundoApellido(String value) {
        this.strSegundoApellido = value;
    }

}
