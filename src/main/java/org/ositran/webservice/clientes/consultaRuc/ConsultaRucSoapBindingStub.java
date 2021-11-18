/**
 * ConsultaRucSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.ositran.webservice.clientes.consultaRuc;

import gob.ositran.siged.config.SigedProperties;

public class ConsultaRucSoapBindingStub extends org.apache.axis.client.Stub implements org.ositran.webservice.clientes.consultaRuc.DatosRucWSFacadeRemote{

	private java.util.Vector cachedSerClasses=new java.util.Vector();
	private java.util.Vector cachedSerQNames=new java.util.Vector();
	private java.util.Vector cachedSerFactories=new java.util.Vector();
	private java.util.Vector cachedDeserFactories=new java.util.Vector();

	static org.apache.axis.description.OperationDesc[] _operations;

	static{
		_operations=new org.apache.axis.description.OperationDesc[9];
		_initOperationDesc1();
	}

	private static void _initOperationDesc1(){
		org.apache.axis.description.OperationDesc oper;
		org.apache.axis.description.ParameterDesc param;
		oper=new org.apache.axis.description.OperationDesc();
		oper.setName("getDatosPrincipales");
		param=new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("","in0"),org.apache.axis.description.ParameterDesc.IN,new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema","string"),java.lang.String.class,false,false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName("urn:ConsultaRuc","BeanDdp"));
		oper.setReturnClass(org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp.class);
		oper.setReturnQName(new javax.xml.namespace.QName("","getDatosPrincipalesReturn"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[0]=oper;

		oper=new org.apache.axis.description.OperationDesc();
		oper.setName("getDomicilioLegal");
		param=new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("","in0"),org.apache.axis.description.ParameterDesc.IN,new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema","string"),java.lang.String.class,false,false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema","string"));
		oper.setReturnClass(java.lang.String.class);
		oper.setReturnQName(new javax.xml.namespace.QName("","getDomicilioLegalReturn"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[1]=oper;

		oper=new org.apache.axis.description.OperationDesc();
		oper.setName("getDatosT1144");
		param=new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("","in0"),org.apache.axis.description.ParameterDesc.IN,new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema","string"),java.lang.String.class,false,false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName("urn:ConsultaRuc","BeanT1144"));
		oper.setReturnClass(org.ositran.webservice.clientes.consultaRuc.beans.BeanT1144.class);
		oper.setReturnQName(new javax.xml.namespace.QName("","getDatosT1144Return"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[2]=oper;

		oper=new org.apache.axis.description.OperationDesc();
		oper.setName("getDatosSecundarios");
		param=new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("","in0"),org.apache.axis.description.ParameterDesc.IN,new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema","string"),java.lang.String.class,false,false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName("urn:ConsultaRuc","BeanDds"));
		oper.setReturnClass(org.ositran.webservice.clientes.consultaRuc.beans.BeanDds.class);
		oper.setReturnQName(new javax.xml.namespace.QName("","getDatosSecundariosReturn"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[3]=oper;

		oper=new org.apache.axis.description.OperationDesc();
		oper.setName("getRepLegales");
		param=new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("","in0"),org.apache.axis.description.ParameterDesc.IN,new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema","string"),java.lang.String.class,false,false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"ArrayOf_tns1_BeanRso"));
		oper.setReturnClass(org.ositran.webservice.clientes.consultaRuc.beans.BeanRso[].class);
		oper.setReturnQName(new javax.xml.namespace.QName("","getRepLegalesReturn"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[4]=oper;

		oper=new org.apache.axis.description.OperationDesc();
		oper.setName("getEstablecimientosAnexos");
		param=new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("","in0"),org.apache.axis.description.ParameterDesc.IN,new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema","string"),java.lang.String.class,false,false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"ArrayOf_tns1_BeanSpr"));
		oper.setReturnClass(org.ositran.webservice.clientes.consultaRuc.beans.BeanSpr[].class);
		oper.setReturnQName(new javax.xml.namespace.QName("","getEstablecimientosAnexosReturn"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[5]=oper;

		oper=new org.apache.axis.description.OperationDesc();
		oper.setName("getEstAnexosT1150");
		param=new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("","in0"),org.apache.axis.description.ParameterDesc.IN,new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema","string"),java.lang.String.class,false,false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"ArrayOf_tns1_BeanT1150"));
		oper.setReturnClass(org.ositran.webservice.clientes.consultaRuc.beans.BeanT1150[].class);
		oper.setReturnQName(new javax.xml.namespace.QName("","getEstAnexosT1150Return"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[6]=oper;

		oper=new org.apache.axis.description.OperationDesc();
		oper.setName("buscaRazonSocial");
		param=new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("","in0"),org.apache.axis.description.ParameterDesc.IN,new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema","string"),java.lang.String.class,false,false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"ArrayOf_tns1_BeanDdp"));
		oper.setReturnClass(org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp[].class);
		oper.setReturnQName(new javax.xml.namespace.QName("","buscaRazonSocialReturn"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[7]=oper;

		oper=new org.apache.axis.description.OperationDesc();
		oper.setName("getDatosT362");
		param=new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("","in0"),org.apache.axis.description.ParameterDesc.IN,new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema","string"),java.lang.String.class,false,false);
		oper.addParameter(param);
		oper.setReturnType(new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"ArrayOf_tns1_BeanT362"));
		oper.setReturnClass(org.ositran.webservice.clientes.consultaRuc.beans.BeanT362[].class);
		oper.setReturnQName(new javax.xml.namespace.QName("","getDatosT362Return"));
		oper.setStyle(org.apache.axis.constants.Style.RPC);
		oper.setUse(org.apache.axis.constants.Use.ENCODED);
		_operations[8]=oper;

	}

	public ConsultaRucSoapBindingStub() throws org.apache.axis.AxisFault{
		this(null);
	}

	public ConsultaRucSoapBindingStub(java.net.URL endpointURL,javax.xml.rpc.Service service) throws org.apache.axis.AxisFault{
		this(service);
		super.cachedEndpoint=endpointURL;
	}

	public ConsultaRucSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault{
		if(service == null){
			super.service=new org.apache.axis.client.Service();
		}
		else{
			super.service=service;
		}
		((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
		java.lang.Class cls;
		javax.xml.namespace.QName qName;
		javax.xml.namespace.QName qName2;
		java.lang.Class beansf=org.apache.axis.encoding.ser.BeanSerializerFactory.class;
		java.lang.Class beandf=org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
		java.lang.Class enumsf=org.apache.axis.encoding.ser.EnumSerializerFactory.class;
		java.lang.Class enumdf=org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
		java.lang.Class arraysf=org.apache.axis.encoding.ser.ArraySerializerFactory.class;
		java.lang.Class arraydf=org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
		java.lang.Class simplesf=org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
		java.lang.Class simpledf=org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
		java.lang.Class simplelistsf=org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
		java.lang.Class simplelistdf=org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
		qName=new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"ArrayOf_tns1_BeanDdp");
		cachedSerQNames.add(qName);
		cls=org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp[].class;
		cachedSerClasses.add(cls);
		qName=new javax.xml.namespace.QName("urn:ConsultaRuc","BeanDdp");
		qName2=null;
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName=new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"ArrayOf_tns1_BeanRso");
		cachedSerQNames.add(qName);
		cls=org.ositran.webservice.clientes.consultaRuc.beans.BeanRso[].class;
		cachedSerClasses.add(cls);
		qName=new javax.xml.namespace.QName("urn:ConsultaRuc","BeanRso");
		qName2=null;
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName=new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"ArrayOf_tns1_BeanSpr");
		cachedSerQNames.add(qName);
		cls=org.ositran.webservice.clientes.consultaRuc.beans.BeanSpr[].class;
		cachedSerClasses.add(cls);
		qName=new javax.xml.namespace.QName("urn:ConsultaRuc","BeanSpr");
		qName2=null;
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName=new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"ArrayOf_tns1_BeanT1150");
		cachedSerQNames.add(qName);
		cls=org.ositran.webservice.clientes.consultaRuc.beans.BeanT1150[].class;
		cachedSerClasses.add(cls);
		qName=new javax.xml.namespace.QName("urn:ConsultaRuc","BeanT1150");
		qName2=null;
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName=new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"ArrayOf_tns1_BeanT362");
		cachedSerQNames.add(qName);
		cls=org.ositran.webservice.clientes.consultaRuc.beans.BeanT362[].class;
		cachedSerClasses.add(cls);
		qName=new javax.xml.namespace.QName("urn:ConsultaRuc","BeanT362");
		qName2=null;
		cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,qName2));
		cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

		qName=new javax.xml.namespace.QName("urn:ConsultaRuc","BeanDdp");
		cachedSerQNames.add(qName);
		cls=org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName=new javax.xml.namespace.QName("urn:ConsultaRuc","BeanDds");
		cachedSerQNames.add(qName);
		cls=org.ositran.webservice.clientes.consultaRuc.beans.BeanDds.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName=new javax.xml.namespace.QName("urn:ConsultaRuc","BeanRso");
		cachedSerQNames.add(qName);
		cls=org.ositran.webservice.clientes.consultaRuc.beans.BeanRso.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName=new javax.xml.namespace.QName("urn:ConsultaRuc","BeanSpr");
		cachedSerQNames.add(qName);
		cls=org.ositran.webservice.clientes.consultaRuc.beans.BeanSpr.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName=new javax.xml.namespace.QName("urn:ConsultaRuc","BeanT1144");
		cachedSerQNames.add(qName);
		cls=org.ositran.webservice.clientes.consultaRuc.beans.BeanT1144.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName=new javax.xml.namespace.QName("urn:ConsultaRuc","BeanT1150");
		cachedSerQNames.add(qName);
		cls=org.ositran.webservice.clientes.consultaRuc.beans.BeanT1150.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

		qName=new javax.xml.namespace.QName("urn:ConsultaRuc","BeanT362");
		cachedSerQNames.add(qName);
		cls=org.ositran.webservice.clientes.consultaRuc.beans.BeanT362.class;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);

	}

	protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException{
		try{
			org.apache.axis.client.Call _call=super._createCall();
			if(super.maintainSessionSet){
				_call.setMaintainSession(super.maintainSession);
			}
			if(super.cachedUsername != null){
				_call.setUsername(super.cachedUsername);
			}
			if(super.cachedPassword != null){
				_call.setPassword(super.cachedPassword);
			}
			if(super.cachedEndpoint != null){
				_call.setTargetEndpointAddress(super.cachedEndpoint);
			}
			if(super.cachedTimeout != null){
				_call.setTimeout(super.cachedTimeout);
			}
			if(super.cachedPortName != null){
				_call.setPortName(super.cachedPortName);
			}
			java.util.Enumeration keys=super.cachedProperties.keys();
			while(keys.hasMoreElements()){
				java.lang.String key=(java.lang.String) keys.nextElement();
				_call.setProperty(key,super.cachedProperties.get(key));
			}
			// All the type mapping information is registered
			// when the first call is made.
			// The type mapping information is actually registered in
			// the TypeMappingRegistry of the service, which
			// is the reason why registration is only needed for the first call.
			synchronized(this){
				if(firstCall()){
					// must set encoding style before registering serializers
					_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
					_call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
					for(int i=0;i < cachedSerFactories.size();++i){
						java.lang.Class cls=(java.lang.Class) cachedSerClasses.get(i);
						javax.xml.namespace.QName qName=(javax.xml.namespace.QName) cachedSerQNames.get(i);
						java.lang.Object x=cachedSerFactories.get(i);
						if(x instanceof Class){
							java.lang.Class sf=(java.lang.Class) cachedSerFactories.get(i);
							java.lang.Class df=(java.lang.Class) cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls,qName,sf,df,false);
						}
						else if(x instanceof javax.xml.rpc.encoding.SerializerFactory){
							org.apache.axis.encoding.SerializerFactory sf=(org.apache.axis.encoding.SerializerFactory) cachedSerFactories.get(i);
							org.apache.axis.encoding.DeserializerFactory df=(org.apache.axis.encoding.DeserializerFactory) cachedDeserFactories.get(i);
							_call.registerTypeMapping(cls,qName,sf,df,false);
						}
					}
				}
			}
			return _call;
		}
		catch(java.lang.Throwable _t){
			throw new org.apache.axis.AxisFault("Failure trying to get the Call object",_t);
		}
	}

	public org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp getDatosPrincipales(java.lang.String in0) throws java.rmi.RemoteException{
		if(super.cachedEndpoint == null){
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call=createCall();
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("ConsultaRuc");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"getDatosPrincipales"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try{
			java.lang.Object _resp=_call.invoke(new java.lang.Object[]{in0});

			if(_resp instanceof java.rmi.RemoteException){
				throw (java.rmi.RemoteException) _resp;
			}
			else{
				extractAttachments(_call);
				try{
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp) _resp;
				}
				catch(java.lang.Exception _exception){
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp) org.apache.axis.utils.JavaUtils.convert(_resp,org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp.class);
				}
			}
		}
		catch(org.apache.axis.AxisFault axisFaultException){
			throw axisFaultException;
		}
	}

	public java.lang.String getDomicilioLegal(java.lang.String in0) throws java.rmi.RemoteException{
		if(super.cachedEndpoint == null){
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call=createCall();
		_call.setOperation(_operations[1]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("ConsultaRuc");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"getDomicilioLegal"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try{
			java.lang.Object _resp=_call.invoke(new java.lang.Object[]{in0});

			if(_resp instanceof java.rmi.RemoteException){
				throw (java.rmi.RemoteException) _resp;
			}
			else{
				extractAttachments(_call);
				try{
					return (java.lang.String) _resp;
				}
				catch(java.lang.Exception _exception){
					return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp,java.lang.String.class);
				}
			}
		}
		catch(org.apache.axis.AxisFault axisFaultException){
			throw axisFaultException;
		}
	}

	public org.ositran.webservice.clientes.consultaRuc.beans.BeanT1144 getDatosT1144(java.lang.String in0) throws java.rmi.RemoteException{
		if(super.cachedEndpoint == null){
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call=createCall();
		_call.setOperation(_operations[2]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("ConsultaRuc");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"getDatosT1144"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try{
			java.lang.Object _resp=_call.invoke(new java.lang.Object[]{in0});

			if(_resp instanceof java.rmi.RemoteException){
				throw (java.rmi.RemoteException) _resp;
			}
			else{
				extractAttachments(_call);
				try{
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanT1144) _resp;
				}
				catch(java.lang.Exception _exception){
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanT1144) org.apache.axis.utils.JavaUtils.convert(_resp,org.ositran.webservice.clientes.consultaRuc.beans.BeanT1144.class);
				}
			}
		}
		catch(org.apache.axis.AxisFault axisFaultException){
			throw axisFaultException;
		}
	}

	public org.ositran.webservice.clientes.consultaRuc.beans.BeanDds getDatosSecundarios(java.lang.String in0) throws java.rmi.RemoteException{
		if(super.cachedEndpoint == null){
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call=createCall();
		_call.setOperation(_operations[3]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("ConsultaRuc");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"getDatosSecundarios"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try{
			java.lang.Object _resp=_call.invoke(new java.lang.Object[]{in0});

			if(_resp instanceof java.rmi.RemoteException){
				throw (java.rmi.RemoteException) _resp;
			}
			else{
				extractAttachments(_call);
				try{
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanDds) _resp;
				}
				catch(java.lang.Exception _exception){
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanDds) org.apache.axis.utils.JavaUtils.convert(_resp,org.ositran.webservice.clientes.consultaRuc.beans.BeanDds.class);
				}
			}
		}
		catch(org.apache.axis.AxisFault axisFaultException){
			throw axisFaultException;
		}
	}

	public org.ositran.webservice.clientes.consultaRuc.beans.BeanRso[] getRepLegales(java.lang.String in0) throws java.rmi.RemoteException{
		if(super.cachedEndpoint == null){
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call=createCall();
		_call.setOperation(_operations[4]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("ConsultaRuc");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"getRepLegales"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try{
			java.lang.Object _resp=_call.invoke(new java.lang.Object[]{in0});

			if(_resp instanceof java.rmi.RemoteException){
				throw (java.rmi.RemoteException) _resp;
			}
			else{
				extractAttachments(_call);
				try{
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanRso[]) _resp;
				}
				catch(java.lang.Exception _exception){
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanRso[]) org.apache.axis.utils.JavaUtils.convert(_resp,org.ositran.webservice.clientes.consultaRuc.beans.BeanRso[].class);
				}
			}
		}
		catch(org.apache.axis.AxisFault axisFaultException){
			throw axisFaultException;
		}
	}

	public org.ositran.webservice.clientes.consultaRuc.beans.BeanSpr[] getEstablecimientosAnexos(java.lang.String in0) throws java.rmi.RemoteException{
		if(super.cachedEndpoint == null){
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call=createCall();
		_call.setOperation(_operations[5]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("ConsultaRuc");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"getEstablecimientosAnexos"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try{
			java.lang.Object _resp=_call.invoke(new java.lang.Object[]{in0});

			if(_resp instanceof java.rmi.RemoteException){
				throw (java.rmi.RemoteException) _resp;
			}
			else{
				extractAttachments(_call);
				try{
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanSpr[]) _resp;
				}
				catch(java.lang.Exception _exception){
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanSpr[]) org.apache.axis.utils.JavaUtils.convert(_resp,org.ositran.webservice.clientes.consultaRuc.beans.BeanSpr[].class);
				}
			}
		}
		catch(org.apache.axis.AxisFault axisFaultException){
			throw axisFaultException;
		}
	}

	public org.ositran.webservice.clientes.consultaRuc.beans.BeanT1150[] getEstAnexosT1150(java.lang.String in0) throws java.rmi.RemoteException{
		if(super.cachedEndpoint == null){
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call=createCall();
		_call.setOperation(_operations[6]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("ConsultaRuc");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"getEstAnexosT1150"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try{
			java.lang.Object _resp=_call.invoke(new java.lang.Object[]{in0});

			if(_resp instanceof java.rmi.RemoteException){
				throw (java.rmi.RemoteException) _resp;
			}
			else{
				extractAttachments(_call);
				try{
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanT1150[]) _resp;
				}
				catch(java.lang.Exception _exception){
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanT1150[]) org.apache.axis.utils.JavaUtils.convert(_resp,org.ositran.webservice.clientes.consultaRuc.beans.BeanT1150[].class);
				}
			}
		}
		catch(org.apache.axis.AxisFault axisFaultException){
			throw axisFaultException;
		}
	}

	public org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp[] buscaRazonSocial(java.lang.String in0) throws java.rmi.RemoteException{
		if(super.cachedEndpoint == null){
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call=createCall();
		_call.setOperation(_operations[7]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("ConsultaRuc");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"buscaRazonSocial"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try{
			java.lang.Object _resp=_call.invoke(new java.lang.Object[]{in0});

			if(_resp instanceof java.rmi.RemoteException){
				throw (java.rmi.RemoteException) _resp;
			}
			else{
				extractAttachments(_call);
				try{
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp[]) _resp;
				}
				catch(java.lang.Exception _exception){
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp[]) org.apache.axis.utils.JavaUtils.convert(_resp,org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp[].class);
				}
			}
		}
		catch(org.apache.axis.AxisFault axisFaultException){
			throw axisFaultException;
		}
	}

	public org.ositran.webservice.clientes.consultaRuc.beans.BeanT362[] getDatosT362(java.lang.String in0) throws java.rmi.RemoteException{
		if(super.cachedEndpoint == null){
			throw new org.apache.axis.NoEndPointException();
		}
		org.apache.axis.client.Call _call=createCall();
		_call.setOperation(_operations[8]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("ConsultaRuc");
		_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new javax.xml.namespace.QName(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.WEB_SERVICE_SUNAT),"getDatosT362"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try{
			java.lang.Object _resp=_call.invoke(new java.lang.Object[]{in0});

			if(_resp instanceof java.rmi.RemoteException){
				throw (java.rmi.RemoteException) _resp;
			}
			else{
				extractAttachments(_call);
				try{
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanT362[]) _resp;
				}
				catch(java.lang.Exception _exception){
					return (org.ositran.webservice.clientes.consultaRuc.beans.BeanT362[]) org.apache.axis.utils.JavaUtils.convert(_resp,org.ositran.webservice.clientes.consultaRuc.beans.BeanT362[].class);
				}
			}
		}
		catch(org.apache.axis.AxisFault axisFaultException){
			throw axisFaultException;
		}
	}

}
