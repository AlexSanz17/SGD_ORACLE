/**
 * BeanT1150.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.ositran.webservice.clientes.consultaRuc.beans;

public class BeanT1150  implements java.io.Serializable {
    private java.lang.String cod_userna;

    private java.lang.String fec_act;  

    private java.lang.String ind_conleg;

    private short num_correl;

    private java.lang.String num_depar;

    private java.lang.String num_kilom;

    private java.lang.String num_lote;

    private java.lang.String num_manza;

    private java.lang.String num_ruc;

    public BeanT1150() {
    }

    public BeanT1150(
           java.lang.String cod_userna,
           java.lang.String fec_act,
           java.lang.String ind_conleg,
           short num_correl,
           java.lang.String num_depar,
           java.lang.String num_kilom,
           java.lang.String num_lote,
           java.lang.String num_manza,
           java.lang.String num_ruc) {
           this.cod_userna = cod_userna;
           this.fec_act = fec_act;
           this.ind_conleg = ind_conleg;
           this.num_correl = num_correl;
           this.num_depar = num_depar;
           this.num_kilom = num_kilom;
           this.num_lote = num_lote;
           this.num_manza = num_manza;
           this.num_ruc = num_ruc;
    }


    /**
     * Gets the cod_userna value for this BeanT1150.
     * 
     * @return cod_userna
     */
    public java.lang.String getCod_userna() {
        return cod_userna;
    }


    /**
     * Sets the cod_userna value for this BeanT1150.
     * 
     * @param cod_userna
     */
    public void setCod_userna(java.lang.String cod_userna) {
        this.cod_userna = cod_userna;
    }


    /**
     * Gets the fec_act value for this BeanT1150.
     * 
     * @return fec_act
     */
    public java.lang.String getFec_act() {
        return fec_act;
    }


    /**
     * Sets the fec_act value for this BeanT1150.
     * 
     * @param fec_act
     */
    public void setFec_act(java.lang.String fec_act) {
        this.fec_act = fec_act;
    }


    /**
     * Gets the ind_conleg value for this BeanT1150.
     * 
     * @return ind_conleg
     */
    public java.lang.String getInd_conleg() {
        return ind_conleg;
    }


    /**
     * Sets the ind_conleg value for this BeanT1150.
     * 
     * @param ind_conleg
     */
    public void setInd_conleg(java.lang.String ind_conleg) {
        this.ind_conleg = ind_conleg;
    }


    /**
     * Gets the num_correl value for this BeanT1150.
     * 
     * @return num_correl
     */
    public short getNum_correl() {
        return num_correl;
    }


    /**
     * Sets the num_correl value for this BeanT1150.
     * 
     * @param num_correl
     */
    public void setNum_correl(short num_correl) {
        this.num_correl = num_correl;
    }


    /**
     * Gets the num_depar value for this BeanT1150.
     * 
     * @return num_depar
     */
    public java.lang.String getNum_depar() {
        return num_depar;
    }


    /**
     * Sets the num_depar value for this BeanT1150.
     * 
     * @param num_depar
     */
    public void setNum_depar(java.lang.String num_depar) {
        this.num_depar = num_depar;
    }


    /**
     * Gets the num_kilom value for this BeanT1150.
     * 
     * @return num_kilom
     */
    public java.lang.String getNum_kilom() {
        return num_kilom;
    }


    /**
     * Sets the num_kilom value for this BeanT1150.
     * 
     * @param num_kilom
     */
    public void setNum_kilom(java.lang.String num_kilom) {
        this.num_kilom = num_kilom;
    }


    /**
     * Gets the num_lote value for this BeanT1150.
     * 
     * @return num_lote
     */
    public java.lang.String getNum_lote() {
        return num_lote;
    }


    /**
     * Sets the num_lote value for this BeanT1150.
     * 
     * @param num_lote
     */
    public void setNum_lote(java.lang.String num_lote) {
        this.num_lote = num_lote;
    }


    /**
     * Gets the num_manza value for this BeanT1150.
     * 
     * @return num_manza
     */
    public java.lang.String getNum_manza() {
        return num_manza;
    }


    /**
     * Sets the num_manza value for this BeanT1150.
     * 
     * @param num_manza
     */
    public void setNum_manza(java.lang.String num_manza) {
        this.num_manza = num_manza;
    }


    /**
     * Gets the num_ruc value for this BeanT1150.
     * 
     * @return num_ruc
     */
    public java.lang.String getNum_ruc() {
        return num_ruc;
    }


    /**
     * Sets the num_ruc value for this BeanT1150.
     * 
     * @param num_ruc
     */
    public void setNum_ruc(java.lang.String num_ruc) {
        this.num_ruc = num_ruc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BeanT1150)) return false;
        BeanT1150 other = (BeanT1150) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cod_userna==null && other.getCod_userna()==null) || 
             (this.cod_userna!=null &&
              this.cod_userna.equals(other.getCod_userna()))) &&
            ((this.fec_act==null && other.getFec_act()==null) || 
             (this.fec_act!=null &&
              this.fec_act.equals(other.getFec_act()))) &&
            ((this.ind_conleg==null && other.getInd_conleg()==null) || 
             (this.ind_conleg!=null &&
              this.ind_conleg.equals(other.getInd_conleg()))) &&
            this.num_correl == other.getNum_correl() &&
            ((this.num_depar==null && other.getNum_depar()==null) || 
             (this.num_depar!=null &&
              this.num_depar.equals(other.getNum_depar()))) &&
            ((this.num_kilom==null && other.getNum_kilom()==null) || 
             (this.num_kilom!=null &&
              this.num_kilom.equals(other.getNum_kilom()))) &&
            ((this.num_lote==null && other.getNum_lote()==null) || 
             (this.num_lote!=null &&
              this.num_lote.equals(other.getNum_lote()))) &&
            ((this.num_manza==null && other.getNum_manza()==null) || 
             (this.num_manza!=null &&
              this.num_manza.equals(other.getNum_manza()))) &&
            ((this.num_ruc==null && other.getNum_ruc()==null) || 
             (this.num_ruc!=null &&
              this.num_ruc.equals(other.getNum_ruc())));
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
        if (getCod_userna() != null) {
            _hashCode += getCod_userna().hashCode();
        }
        if (getFec_act() != null) {
            _hashCode += getFec_act().hashCode();
        }
        if (getInd_conleg() != null) {
            _hashCode += getInd_conleg().hashCode();
        }
        _hashCode += getNum_correl();
        if (getNum_depar() != null) {
            _hashCode += getNum_depar().hashCode();
        }
        if (getNum_kilom() != null) {
            _hashCode += getNum_kilom().hashCode();
        }
        if (getNum_lote() != null) {
            _hashCode += getNum_lote().hashCode();
        }
        if (getNum_manza() != null) {
            _hashCode += getNum_manza().hashCode();
        }
        if (getNum_ruc() != null) {
            _hashCode += getNum_ruc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BeanT1150.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:ConsultaRuc", "BeanT1150"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cod_userna");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cod_userna"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fec_act");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fec_act"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ind_conleg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ind_conleg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("num_correl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "num_correl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("num_depar");
        elemField.setXmlName(new javax.xml.namespace.QName("", "num_depar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("num_kilom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "num_kilom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("num_lote");
        elemField.setXmlName(new javax.xml.namespace.QName("", "num_lote"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("num_manza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "num_manza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("num_ruc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "num_ruc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
