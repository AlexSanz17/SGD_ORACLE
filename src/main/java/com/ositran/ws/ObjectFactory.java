
package com.ositran.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ositran.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ConsultarDocumento_QNAME = new QName("http://ws.consulta.usuarios.migraciones.gob.pe/", "consultarDocumento");
    private final static QName _ConsultarDocumentoResponse_QNAME = new QName("http://ws.consulta.usuarios.migraciones.gob.pe/", "consultarDocumentoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ositran.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsultarDocumentoResponse }
     * 
     */
    public ConsultarDocumentoResponse createConsultarDocumentoResponse() {
        return new ConsultarDocumentoResponse();
    }

    /**
     * Create an instance of {@link ConsultarDocumento }
     * 
     */
    public ConsultarDocumento createConsultarDocumento() {
        return new ConsultarDocumento();
    }

    /**
     * Create an instance of {@link SolicitudBean }
     * 
     */
    public SolicitudBean createSolicitudBean() {
        return new SolicitudBean();
    }

    /**
     * Create an instance of {@link RespuestaBean }
     * 
     */
    public RespuestaBean createRespuestaBean() {
        return new RespuestaBean();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultarDocumento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.consulta.usuarios.migraciones.gob.pe/", name = "consultarDocumento")
    public JAXBElement<ConsultarDocumento> createConsultarDocumento(ConsultarDocumento value) {
        return new JAXBElement<ConsultarDocumento>(_ConsultarDocumento_QNAME, ConsultarDocumento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsultarDocumentoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.consulta.usuarios.migraciones.gob.pe/", name = "consultarDocumentoResponse")
    public JAXBElement<ConsultarDocumentoResponse> createConsultarDocumentoResponse(ConsultarDocumentoResponse value) {
        return new JAXBElement<ConsultarDocumentoResponse>(_ConsultarDocumentoResponse_QNAME, ConsultarDocumentoResponse.class, null, value);
    }

}
