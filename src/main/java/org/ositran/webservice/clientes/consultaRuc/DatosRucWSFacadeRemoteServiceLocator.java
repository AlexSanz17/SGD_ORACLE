/**
 * DatosRucWSFacadeRemoteServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package org.ositran.webservice.clientes.consultaRuc;

import gob.ositran.siged.config.SigedProperties;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

public class DatosRucWSFacadeRemoteServiceLocator extends org.apache.axis.client.Service implements org.ositran.webservice.clientes.consultaRuc.DatosRucWSFacadeRemoteService{
	/**
	 * 
	 */
	private static final long serialVersionUID=3923521690772108514L;

	public DatosRucWSFacadeRemoteServiceLocator(){}

	public DatosRucWSFacadeRemoteServiceLocator(org.apache.axis.EngineConfiguration config){
		super(config);
	}

	public DatosRucWSFacadeRemoteServiceLocator(java.lang.String wsdlLoc,javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException{
		super(wsdlLoc,sName);
	}
	// Use to get a proxy class for ConsultaRuc
	private java.lang.String ConsultaRuc_address=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT);

	public java.lang.String getConsultaRucAddress(){
		return ConsultaRuc_address;
	}
	// The WSDD service name defaults to the port name.
	private java.lang.String ConsultaRucWSDDServiceName="ConsultaRuc";

	public java.lang.String getConsultaRucWSDDServiceName(){
		return ConsultaRucWSDDServiceName;
	}

	public void setConsultaRucWSDDServiceName(java.lang.String name){
		ConsultaRucWSDDServiceName=name;
	}

	public org.ositran.webservice.clientes.consultaRuc.DatosRucWSFacadeRemote getConsultaRuc() throws javax.xml.rpc.ServiceException{
		java.net.URL endpoint;
		try{
			endpoint=new java.net.URL(ConsultaRuc_address);
		}catch(java.net.MalformedURLException e){
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getConsultaRuc(endpoint);
	}

	public org.ositran.webservice.clientes.consultaRuc.DatosRucWSFacadeRemote getConsultaRuc(java.net.URL portAddress) throws javax.xml.rpc.ServiceException{
		try{
			org.ositran.webservice.clientes.consultaRuc.ConsultaRucSoapBindingStub _stub=new org.ositran.webservice.clientes.consultaRuc.ConsultaRucSoapBindingStub(portAddress,this);
			_stub.setPortName(getConsultaRucWSDDServiceName());
			return _stub;
		}catch(org.apache.axis.AxisFault e){
			return null;
		}
	}

	public void setConsultaRucEndpointAddress(java.lang.String address){
		ConsultaRuc_address=address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws ServiceException{
		try{
			if(DatosRucWSFacadeRemote.class.isAssignableFrom(serviceEndpointInterface)){
				org.ositran.webservice.clientes.consultaRuc.ConsultaRucSoapBindingStub _stub=new org.ositran.webservice.clientes.consultaRuc.ConsultaRucSoapBindingStub(new java.net.URL(ConsultaRuc_address),this);
				_stub.setPortName(getConsultaRucWSDDServiceName());
				return _stub;
			}
		}catch(java.lang.Throwable t){
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "+(serviceEndpointInterface==null ? "null" : serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has
	 * no port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName,Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException{
		if(portName==null){
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName=portName.getLocalPart();
		if("ConsultaRuc".equals(inputPortName)){
			return getConsultaRuc();
		}
		Remote _stub=getPort(serviceEndpointInterface);
		((org.apache.axis.client.Stub) _stub).setPortName(portName);
		return _stub;
	}

	public javax.xml.namespace.QName getServiceName(){
		return new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"DatosRucWSFacadeRemoteService");
	}
	private HashSet<QName> ports=null;

	public Iterator<QName> getPorts(){
		if(ports==null){
			ports=new HashSet<QName>();
			ports.add(new QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"ConsultaRuc"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName,java.lang.String address) throws javax.xml.rpc.ServiceException{
		if("ConsultaRuc".equals(portName)){
			setConsultaRucEndpointAddress(address);
		}else{ // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port"+portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName,java.lang.String address) throws javax.xml.rpc.ServiceException{
		setEndpointAddress(portName.getLocalPart(),address);
	}
}
