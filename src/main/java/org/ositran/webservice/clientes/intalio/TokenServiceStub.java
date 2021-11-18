/**
 * TokenServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */
package org.ositran.webservice.clientes.intalio;

import gob.ositran.siged.config.SigedProperties;
import org.apache.axis2.transport.http.HTTPConstants;

/*
 *  TokenServiceStub java implementation
 */

public class TokenServiceStub extends org.apache.axis2.client.Stub{
	protected org.apache.axis2.description.AxisOperation[] _operations;

	// hashmaps to keep the fault mapping
	private java.util.HashMap faultExceptionNameMap=new java.util.HashMap();
	private java.util.HashMap faultExceptionClassNameMap=new java.util.HashMap();
	private java.util.HashMap faultMessageMap=new java.util.HashMap();

	private static int counter=0;

	private static synchronized java.lang.String getUniqueSuffix(){
		// reset the counter if it is greater than 99999
		if(counter > 99999){
			counter=0;
		}
		counter=counter + 1;
		return java.lang.Long.toString(System.currentTimeMillis()) + "_" + counter;
	}

	private void populateAxisService() throws org.apache.axis2.AxisFault{

		// creating the Service with a unique name
		_service=new org.apache.axis2.description.AxisService("TokenService" + getUniqueSuffix());
		addAnonymousOperations();

		// creating the operations
		org.apache.axis2.description.AxisOperation __operation;

		_operations=new org.apache.axis2.description.AxisOperation[4];

		__operation=new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","authenticateUser"));
		_service.addOperation(__operation);

		_operations[0]=__operation;

		__operation=new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","authenticateUserWithCredentials"));
		_service.addOperation(__operation);

		_operations[1]=__operation;

		__operation=new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","getTokenProperties"));
		_service.addOperation(__operation);

		_operations[2]=__operation;

		__operation=new org.apache.axis2.description.OutInAxisOperation();

		__operation.setName(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","getTokenFromTicket"));
		_service.addOperation(__operation);

		_operations[3]=__operation;

	}

	// populates the faults
	private void populateFaults(){

	}

	/**
	 *Constructor that takes in a configContext
	 */

	public TokenServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault{
		this(configurationContext,targetEndpoint,false);
	}

	/**
	 * Constructor that takes in a configContext and useseperate listner
	 */
	public TokenServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,java.lang.String targetEndpoint,boolean useSeparateListener) throws org.apache.axis2.AxisFault{
		// To populate AxisService
		populateAxisService();
		populateFaults();

		_serviceClient=new org.apache.axis2.client.ServiceClient(configurationContext,_service);

		_serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(targetEndpoint));
		_serviceClient.getOptions().setUseSeparateListener(useSeparateListener);

		// Set the soap version
		_serviceClient.getOptions().setSoapVersionURI(org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
		_serviceClient.getOptions().setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION,HTTPConstants.HEADER_PROTOCOL_10);
	}

	/**
	 * Default Constructor
	 */
	public TokenServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault{

		this(configurationContext,"http://"+SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.INTALIO_HOST)+":"+SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.INTALIO_PORT)+"/axis2/services/TokenService");

	}

	/**
	 * Default Constructor
	 */
	public TokenServiceStub() throws org.apache.axis2.AxisFault{

		this("http://"+SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.INTALIO_HOST)+":"+SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.INTALIO_PORT)+"/axis2/services/TokenService");

	}

	/**
	 * Constructor taking the target endpoint
	 */
	public TokenServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault{
		this(null,targetEndpoint);
	}

	/**
	 * Auto generated method signature
	 * 
	 * @see org.ositran.webservice.clientes.intalio.TokenService#authenticateUser
	 * @param authenticateUser2
	 */

	public org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse authenticateUser(

	org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUser authenticateUser2)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext=null;
		try{
			org.apache.axis2.client.OperationClient _operationClient=_serviceClient.createClient(_operations[0].getName());
			_operationClient.getOptions().setAction("authenticateUser");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");

			// create a message context
			_messageContext=new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env=null;

			env=toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),authenticateUser2,optimizeContent(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","authenticateUser")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext=_operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv=_returnMessageContext.getEnvelope();

			java.lang.Object object=fromOM(_returnEnv.getBody().getFirstElement(),org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse.class,getEnvelopeNamespaces(_returnEnv));

			return (org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse) object;

		}
		catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt=f.getDetail();
			if(faultElt != null){
				if(faultExceptionNameMap.containsKey(faultElt.getQName())){
					// make the fault by reflection
					try{
						java.lang.String exceptionClassName=(java.lang.String) faultExceptionClassNameMap.get(faultElt.getQName());
						java.lang.Class exceptionClass=java.lang.Class.forName(exceptionClassName);
						java.lang.Exception ex=(java.lang.Exception) exceptionClass.newInstance();
						// message class
						java.lang.String messageClassName=(java.lang.String) faultMessageMap.get(faultElt.getQName());
						java.lang.Class messageClass=java.lang.Class.forName(messageClassName);
						java.lang.Object messageObject=fromOM(faultElt,messageClass,null);
						java.lang.reflect.Method m=exceptionClass.getMethod("setFaultMessage",new java.lang.Class[]{messageClass});
						m.invoke(ex,new java.lang.Object[]{messageObject});

						throw new java.rmi.RemoteException(ex.getMessage(),ex);
					}
					catch(java.lang.ClassCastException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.ClassNotFoundException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.NoSuchMethodException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.reflect.InvocationTargetException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.IllegalAccessException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.InstantiationException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				}
				else{
					throw f;
				}
			}
			else{
				throw f;
			}
		}
		finally{
			_messageContext.getTransportOut().getSender().cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see org.ositran.webservice.clientes.intalio.TokenService#startauthenticateUser
	 * @param authenticateUser2
	 */
	public void startauthenticateUser(

	org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUser authenticateUser2,

	final org.ositran.webservice.clientes.intalio.TokenServiceCallbackHandler callback)

	throws java.rmi.RemoteException{

		org.apache.axis2.client.OperationClient _operationClient=_serviceClient.createClient(_operations[0].getName());
		_operationClient.getOptions().setAction("authenticateUser");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env=null;
		final org.apache.axis2.context.MessageContext _messageContext=new org.apache.axis2.context.MessageContext();

		// Style is Doc.

		env=toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),authenticateUser2,optimizeContent(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","authenticateUser")));

		// adding SOAP soap_headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		_messageContext.setEnvelope(env);

		// add the message context to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback(){
			public void onMessage(org.apache.axis2.context.MessageContext resultContext){
				try{
					org.apache.axiom.soap.SOAPEnvelope resultEnv=resultContext.getEnvelope();

					java.lang.Object object=fromOM(resultEnv.getBody().getFirstElement(),org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse.class,getEnvelopeNamespaces(resultEnv));
					callback.receiveResultauthenticateUser((org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse) object);

				}
				catch(org.apache.axis2.AxisFault e){
					callback.receiveErrorauthenticateUser(e);
				}
			}

			public void onError(java.lang.Exception error){
				if(error instanceof org.apache.axis2.AxisFault){
					org.apache.axis2.AxisFault f=(org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt=f.getDetail();
					if(faultElt != null){
						if(faultExceptionNameMap.containsKey(faultElt.getQName())){
							// make the fault by reflection
							try{
								java.lang.String exceptionClassName=(java.lang.String) faultExceptionClassNameMap.get(faultElt.getQName());
								java.lang.Class exceptionClass=java.lang.Class.forName(exceptionClassName);
								java.lang.Exception ex=(java.lang.Exception) exceptionClass.newInstance();
								// message class
								java.lang.String messageClassName=(java.lang.String) faultMessageMap.get(faultElt.getQName());
								java.lang.Class messageClass=java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject=fromOM(faultElt,messageClass,null);
								java.lang.reflect.Method m=exceptionClass.getMethod("setFaultMessage",new java.lang.Class[]{messageClass});
								m.invoke(ex,new java.lang.Object[]{messageObject});

								callback.receiveErrorauthenticateUser(new java.rmi.RemoteException(ex.getMessage(),ex));
							}
							catch(java.lang.ClassCastException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorauthenticateUser(f);
							}
							catch(java.lang.ClassNotFoundException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorauthenticateUser(f);
							}
							catch(java.lang.NoSuchMethodException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorauthenticateUser(f);
							}
							catch(java.lang.reflect.InvocationTargetException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorauthenticateUser(f);
							}
							catch(java.lang.IllegalAccessException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorauthenticateUser(f);
							}
							catch(java.lang.InstantiationException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorauthenticateUser(f);
							}
							catch(org.apache.axis2.AxisFault e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorauthenticateUser(f);
							}
						}
						else{
							callback.receiveErrorauthenticateUser(f);
						}
					}
					else{
						callback.receiveErrorauthenticateUser(f);
					}
				}
				else{
					callback.receiveErrorauthenticateUser(error);
				}
			}

			public void onFault(org.apache.axis2.context.MessageContext faultContext){
				org.apache.axis2.AxisFault fault=org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
				onError(fault);
			}

			public void onComplete(){
				try{
					_messageContext.getTransportOut().getSender().cleanup(_messageContext);
				}
				catch(org.apache.axis2.AxisFault axisFault){
					callback.receiveErrorauthenticateUser(axisFault);
				}
			}
		});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver=null;
		if(_operations[0].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()){
			_callbackReceiver=new org.apache.axis2.util.CallbackReceiver();
			_operations[0].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);

	}

	/**
	 * Auto generated method signature
	 * 
	 * @see org.ositran.webservice.clientes.intalio.TokenService#authenticateUserWithCredentials
	 * @param authenticateUserWithCredentials4
	 */

	public org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse authenticateUserWithCredentials(

	org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserWithCredentials authenticateUserWithCredentials4)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext=null;
		try{
			org.apache.axis2.client.OperationClient _operationClient=_serviceClient.createClient(_operations[1].getName());
			_operationClient.getOptions().setAction("authenticateUserWithCredentials");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");

			// create a message context
			_messageContext=new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env=null;

			env=toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),authenticateUserWithCredentials4,optimizeContent(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","authenticateUserWithCredentials")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext=_operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv=_returnMessageContext.getEnvelope();

			java.lang.Object object=fromOM(_returnEnv.getBody().getFirstElement(),org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse.class,getEnvelopeNamespaces(_returnEnv));

			return (org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse) object;

		}
		catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt=f.getDetail();
			if(faultElt != null){
				if(faultExceptionNameMap.containsKey(faultElt.getQName())){
					// make the fault by reflection
					try{
						java.lang.String exceptionClassName=(java.lang.String) faultExceptionClassNameMap.get(faultElt.getQName());
						java.lang.Class exceptionClass=java.lang.Class.forName(exceptionClassName);
						java.lang.Exception ex=(java.lang.Exception) exceptionClass.newInstance();
						// message class
						java.lang.String messageClassName=(java.lang.String) faultMessageMap.get(faultElt.getQName());
						java.lang.Class messageClass=java.lang.Class.forName(messageClassName);
						java.lang.Object messageObject=fromOM(faultElt,messageClass,null);
						java.lang.reflect.Method m=exceptionClass.getMethod("setFaultMessage",new java.lang.Class[]{messageClass});
						m.invoke(ex,new java.lang.Object[]{messageObject});

						throw new java.rmi.RemoteException(ex.getMessage(),ex);
					}
					catch(java.lang.ClassCastException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.ClassNotFoundException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.NoSuchMethodException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.reflect.InvocationTargetException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.IllegalAccessException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.InstantiationException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				}
				else{
					throw f;
				}
			}
			else{
				throw f;
			}
		}
		finally{
			_messageContext.getTransportOut().getSender().cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see org.ositran.webservice.clientes.intalio.TokenService#startauthenticateUserWithCredentials
	 * @param authenticateUserWithCredentials4
	 */
	public void startauthenticateUserWithCredentials(

	org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserWithCredentials authenticateUserWithCredentials4,

	final org.ositran.webservice.clientes.intalio.TokenServiceCallbackHandler callback)

	throws java.rmi.RemoteException{

		org.apache.axis2.client.OperationClient _operationClient=_serviceClient.createClient(_operations[1].getName());
		_operationClient.getOptions().setAction("authenticateUserWithCredentials");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env=null;
		final org.apache.axis2.context.MessageContext _messageContext=new org.apache.axis2.context.MessageContext();

		// Style is Doc.

		env=toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),authenticateUserWithCredentials4,optimizeContent(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","authenticateUserWithCredentials")));

		// adding SOAP soap_headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		_messageContext.setEnvelope(env);

		// add the message context to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback(){
			public void onMessage(org.apache.axis2.context.MessageContext resultContext){
				try{
					org.apache.axiom.soap.SOAPEnvelope resultEnv=resultContext.getEnvelope();

					java.lang.Object object=fromOM(resultEnv.getBody().getFirstElement(),org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse.class,getEnvelopeNamespaces(resultEnv));
					callback.receiveResultauthenticateUserWithCredentials((org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse) object);

				}
				catch(org.apache.axis2.AxisFault e){
					callback.receiveErrorauthenticateUserWithCredentials(e);
				}
			}

			public void onError(java.lang.Exception error){
				if(error instanceof org.apache.axis2.AxisFault){
					org.apache.axis2.AxisFault f=(org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt=f.getDetail();
					if(faultElt != null){
						if(faultExceptionNameMap.containsKey(faultElt.getQName())){
							// make the fault by reflection
							try{
								java.lang.String exceptionClassName=(java.lang.String) faultExceptionClassNameMap.get(faultElt.getQName());
								java.lang.Class exceptionClass=java.lang.Class.forName(exceptionClassName);
								java.lang.Exception ex=(java.lang.Exception) exceptionClass.newInstance();
								// message class
								java.lang.String messageClassName=(java.lang.String) faultMessageMap.get(faultElt.getQName());
								java.lang.Class messageClass=java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject=fromOM(faultElt,messageClass,null);
								java.lang.reflect.Method m=exceptionClass.getMethod("setFaultMessage",new java.lang.Class[]{messageClass});
								m.invoke(ex,new java.lang.Object[]{messageObject});

								callback.receiveErrorauthenticateUserWithCredentials(new java.rmi.RemoteException(ex.getMessage(),ex));
							}
							catch(java.lang.ClassCastException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorauthenticateUserWithCredentials(f);
							}
							catch(java.lang.ClassNotFoundException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorauthenticateUserWithCredentials(f);
							}
							catch(java.lang.NoSuchMethodException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorauthenticateUserWithCredentials(f);
							}
							catch(java.lang.reflect.InvocationTargetException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorauthenticateUserWithCredentials(f);
							}
							catch(java.lang.IllegalAccessException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorauthenticateUserWithCredentials(f);
							}
							catch(java.lang.InstantiationException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorauthenticateUserWithCredentials(f);
							}
							catch(org.apache.axis2.AxisFault e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorauthenticateUserWithCredentials(f);
							}
						}
						else{
							callback.receiveErrorauthenticateUserWithCredentials(f);
						}
					}
					else{
						callback.receiveErrorauthenticateUserWithCredentials(f);
					}
				}
				else{
					callback.receiveErrorauthenticateUserWithCredentials(error);
				}
			}

			public void onFault(org.apache.axis2.context.MessageContext faultContext){
				org.apache.axis2.AxisFault fault=org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
				onError(fault);
			}

			public void onComplete(){
				try{
					_messageContext.getTransportOut().getSender().cleanup(_messageContext);
				}
				catch(org.apache.axis2.AxisFault axisFault){
					callback.receiveErrorauthenticateUserWithCredentials(axisFault);
				}
			}
		});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver=null;
		if(_operations[1].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()){
			_callbackReceiver=new org.apache.axis2.util.CallbackReceiver();
			_operations[1].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);

	}

	/**
	 * Auto generated method signature
	 * 
	 * @see org.ositran.webservice.clientes.intalio.TokenService#getTokenProperties
	 * @param getTokenProperties6
	 */

	public org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenPropertiesResponse getTokenProperties(

	org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenProperties getTokenProperties6)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext=null;
		try{
			org.apache.axis2.client.OperationClient _operationClient=_serviceClient.createClient(_operations[2].getName());
			_operationClient.getOptions().setAction("getTokenProperties");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");

			// create a message context
			_messageContext=new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env=null;

			env=toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),getTokenProperties6,optimizeContent(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","getTokenProperties")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext=_operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv=_returnMessageContext.getEnvelope();

			java.lang.Object object=fromOM(_returnEnv.getBody().getFirstElement(),org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenPropertiesResponse.class,getEnvelopeNamespaces(_returnEnv));

			return (org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenPropertiesResponse) object;

		}
		catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt=f.getDetail();
			if(faultElt != null){
				if(faultExceptionNameMap.containsKey(faultElt.getQName())){
					// make the fault by reflection
					try{
						java.lang.String exceptionClassName=(java.lang.String) faultExceptionClassNameMap.get(faultElt.getQName());
						java.lang.Class exceptionClass=java.lang.Class.forName(exceptionClassName);
						java.lang.Exception ex=(java.lang.Exception) exceptionClass.newInstance();
						// message class
						java.lang.String messageClassName=(java.lang.String) faultMessageMap.get(faultElt.getQName());
						java.lang.Class messageClass=java.lang.Class.forName(messageClassName);
						java.lang.Object messageObject=fromOM(faultElt,messageClass,null);
						java.lang.reflect.Method m=exceptionClass.getMethod("setFaultMessage",new java.lang.Class[]{messageClass});
						m.invoke(ex,new java.lang.Object[]{messageObject});

						throw new java.rmi.RemoteException(ex.getMessage(),ex);
					}
					catch(java.lang.ClassCastException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.ClassNotFoundException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.NoSuchMethodException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.reflect.InvocationTargetException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.IllegalAccessException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.InstantiationException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				}
				else{
					throw f;
				}
			}
			else{
				throw f;
			}
		}
		finally{
			_messageContext.getTransportOut().getSender().cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see org.ositran.webservice.clientes.intalio.TokenService#startgetTokenProperties
	 * @param getTokenProperties6
	 */
	public void startgetTokenProperties(

	org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenProperties getTokenProperties6,

	final org.ositran.webservice.clientes.intalio.TokenServiceCallbackHandler callback)

	throws java.rmi.RemoteException{

		org.apache.axis2.client.OperationClient _operationClient=_serviceClient.createClient(_operations[2].getName());
		_operationClient.getOptions().setAction("getTokenProperties");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env=null;
		final org.apache.axis2.context.MessageContext _messageContext=new org.apache.axis2.context.MessageContext();

		// Style is Doc.

		env=toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),getTokenProperties6,optimizeContent(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","getTokenProperties")));

		// adding SOAP soap_headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		_messageContext.setEnvelope(env);

		// add the message context to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback(){
			public void onMessage(org.apache.axis2.context.MessageContext resultContext){
				try{
					org.apache.axiom.soap.SOAPEnvelope resultEnv=resultContext.getEnvelope();

					java.lang.Object object=fromOM(resultEnv.getBody().getFirstElement(),org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenPropertiesResponse.class,getEnvelopeNamespaces(resultEnv));
					callback.receiveResultgetTokenProperties((org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenPropertiesResponse) object);

				}
				catch(org.apache.axis2.AxisFault e){
					callback.receiveErrorgetTokenProperties(e);
				}
			}

			public void onError(java.lang.Exception error){
				if(error instanceof org.apache.axis2.AxisFault){
					org.apache.axis2.AxisFault f=(org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt=f.getDetail();
					if(faultElt != null){
						if(faultExceptionNameMap.containsKey(faultElt.getQName())){
							// make the fault by reflection
							try{
								java.lang.String exceptionClassName=(java.lang.String) faultExceptionClassNameMap.get(faultElt.getQName());
								java.lang.Class exceptionClass=java.lang.Class.forName(exceptionClassName);
								java.lang.Exception ex=(java.lang.Exception) exceptionClass.newInstance();
								// message class
								java.lang.String messageClassName=(java.lang.String) faultMessageMap.get(faultElt.getQName());
								java.lang.Class messageClass=java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject=fromOM(faultElt,messageClass,null);
								java.lang.reflect.Method m=exceptionClass.getMethod("setFaultMessage",new java.lang.Class[]{messageClass});
								m.invoke(ex,new java.lang.Object[]{messageObject});

								callback.receiveErrorgetTokenProperties(new java.rmi.RemoteException(ex.getMessage(),ex));
							}
							catch(java.lang.ClassCastException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetTokenProperties(f);
							}
							catch(java.lang.ClassNotFoundException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetTokenProperties(f);
							}
							catch(java.lang.NoSuchMethodException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetTokenProperties(f);
							}
							catch(java.lang.reflect.InvocationTargetException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetTokenProperties(f);
							}
							catch(java.lang.IllegalAccessException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetTokenProperties(f);
							}
							catch(java.lang.InstantiationException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetTokenProperties(f);
							}
							catch(org.apache.axis2.AxisFault e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetTokenProperties(f);
							}
						}
						else{
							callback.receiveErrorgetTokenProperties(f);
						}
					}
					else{
						callback.receiveErrorgetTokenProperties(f);
					}
				}
				else{
					callback.receiveErrorgetTokenProperties(error);
				}
			}

			public void onFault(org.apache.axis2.context.MessageContext faultContext){
				org.apache.axis2.AxisFault fault=org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
				onError(fault);
			}

			public void onComplete(){
				try{
					_messageContext.getTransportOut().getSender().cleanup(_messageContext);
				}
				catch(org.apache.axis2.AxisFault axisFault){
					callback.receiveErrorgetTokenProperties(axisFault);
				}
			}
		});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver=null;
		if(_operations[2].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()){
			_callbackReceiver=new org.apache.axis2.util.CallbackReceiver();
			_operations[2].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);

	}

	/**
	 * Auto generated method signature
	 * 
	 * @see org.ositran.webservice.clientes.intalio.TokenService#getTokenFromTicket
	 * @param proxyTicket8
	 */

	public org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse getTokenFromTicket(

	org.ositran.webservice.clientes.intalio.TokenServiceStub.ProxyTicket proxyTicket8)

	throws java.rmi.RemoteException

	{
		org.apache.axis2.context.MessageContext _messageContext=null;
		try{
			org.apache.axis2.client.OperationClient _operationClient=_serviceClient.createClient(_operations[3].getName());
			_operationClient.getOptions().setAction("getTokenFromTicket");
			_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

			addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");

			// create a message context
			_messageContext=new org.apache.axis2.context.MessageContext();

			// create SOAP envelope with that payload
			org.apache.axiom.soap.SOAPEnvelope env=null;

			env=toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),proxyTicket8,optimizeContent(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","getTokenFromTicket")));

			// adding SOAP soap_headers
			_serviceClient.addHeadersToEnvelope(env);
			// set the message context with that soap envelope
			_messageContext.setEnvelope(env);

			// add the message contxt to the operation client
			_operationClient.addMessageContext(_messageContext);

			// execute the operation client
			_operationClient.execute(true);

			org.apache.axis2.context.MessageContext _returnMessageContext=_operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
			org.apache.axiom.soap.SOAPEnvelope _returnEnv=_returnMessageContext.getEnvelope();

			java.lang.Object object=fromOM(_returnEnv.getBody().getFirstElement(),org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse.class,getEnvelopeNamespaces(_returnEnv));

			return (org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse) object;

		}
		catch(org.apache.axis2.AxisFault f){

			org.apache.axiom.om.OMElement faultElt=f.getDetail();
			if(faultElt != null){
				if(faultExceptionNameMap.containsKey(faultElt.getQName())){
					// make the fault by reflection
					try{
						java.lang.String exceptionClassName=(java.lang.String) faultExceptionClassNameMap.get(faultElt.getQName());
						java.lang.Class exceptionClass=java.lang.Class.forName(exceptionClassName);
						java.lang.Exception ex=(java.lang.Exception) exceptionClass.newInstance();
						// message class
						java.lang.String messageClassName=(java.lang.String) faultMessageMap.get(faultElt.getQName());
						java.lang.Class messageClass=java.lang.Class.forName(messageClassName);
						java.lang.Object messageObject=fromOM(faultElt,messageClass,null);
						java.lang.reflect.Method m=exceptionClass.getMethod("setFaultMessage",new java.lang.Class[]{messageClass});
						m.invoke(ex,new java.lang.Object[]{messageObject});

						throw new java.rmi.RemoteException(ex.getMessage(),ex);
					}
					catch(java.lang.ClassCastException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.ClassNotFoundException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.NoSuchMethodException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.reflect.InvocationTargetException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.IllegalAccessException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
					catch(java.lang.InstantiationException e){
						// we cannot intantiate the class - throw the original
						// Axis fault
						throw f;
					}
				}
				else{
					throw f;
				}
			}
			else{
				throw f;
			}
		}
		finally{
			_messageContext.getTransportOut().getSender().cleanup(_messageContext);
		}
	}

	/**
	 * Auto generated method signature for Asynchronous Invocations
	 * 
	 * @see org.ositran.webservice.clientes.intalio.TokenService#startgetTokenFromTicket
	 * @param proxyTicket8
	 */
	public void startgetTokenFromTicket(

	org.ositran.webservice.clientes.intalio.TokenServiceStub.ProxyTicket proxyTicket8,

	final org.ositran.webservice.clientes.intalio.TokenServiceCallbackHandler callback)

	throws java.rmi.RemoteException{

		org.apache.axis2.client.OperationClient _operationClient=_serviceClient.createClient(_operations[3].getName());
		_operationClient.getOptions().setAction("getTokenFromTicket");
		_operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

		addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");

		// create SOAP envelope with that payload
		org.apache.axiom.soap.SOAPEnvelope env=null;
		final org.apache.axis2.context.MessageContext _messageContext=new org.apache.axis2.context.MessageContext();

		// Style is Doc.

		env=toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),proxyTicket8,optimizeContent(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","getTokenFromTicket")));

		// adding SOAP soap_headers
		_serviceClient.addHeadersToEnvelope(env);
		// create message context with that soap envelope
		_messageContext.setEnvelope(env);

		// add the message context to the operation client
		_operationClient.addMessageContext(_messageContext);

		_operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback(){
			public void onMessage(org.apache.axis2.context.MessageContext resultContext){
				try{
					org.apache.axiom.soap.SOAPEnvelope resultEnv=resultContext.getEnvelope();

					java.lang.Object object=fromOM(resultEnv.getBody().getFirstElement(),org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse.class,getEnvelopeNamespaces(resultEnv));
					callback.receiveResultgetTokenFromTicket((org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse) object);

				}
				catch(org.apache.axis2.AxisFault e){
					callback.receiveErrorgetTokenFromTicket(e);
				}
			}

			public void onError(java.lang.Exception error){
				if(error instanceof org.apache.axis2.AxisFault){
					org.apache.axis2.AxisFault f=(org.apache.axis2.AxisFault) error;
					org.apache.axiom.om.OMElement faultElt=f.getDetail();
					if(faultElt != null){
						if(faultExceptionNameMap.containsKey(faultElt.getQName())){
							// make the fault by reflection
							try{
								java.lang.String exceptionClassName=(java.lang.String) faultExceptionClassNameMap.get(faultElt.getQName());
								java.lang.Class exceptionClass=java.lang.Class.forName(exceptionClassName);
								java.lang.Exception ex=(java.lang.Exception) exceptionClass.newInstance();
								// message class
								java.lang.String messageClassName=(java.lang.String) faultMessageMap.get(faultElt.getQName());
								java.lang.Class messageClass=java.lang.Class.forName(messageClassName);
								java.lang.Object messageObject=fromOM(faultElt,messageClass,null);
								java.lang.reflect.Method m=exceptionClass.getMethod("setFaultMessage",new java.lang.Class[]{messageClass});
								m.invoke(ex,new java.lang.Object[]{messageObject});

								callback.receiveErrorgetTokenFromTicket(new java.rmi.RemoteException(ex.getMessage(),ex));
							}
							catch(java.lang.ClassCastException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetTokenFromTicket(f);
							}
							catch(java.lang.ClassNotFoundException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetTokenFromTicket(f);
							}
							catch(java.lang.NoSuchMethodException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetTokenFromTicket(f);
							}
							catch(java.lang.reflect.InvocationTargetException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetTokenFromTicket(f);
							}
							catch(java.lang.IllegalAccessException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetTokenFromTicket(f);
							}
							catch(java.lang.InstantiationException e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetTokenFromTicket(f);
							}
							catch(org.apache.axis2.AxisFault e){
								// we cannot intantiate the class - throw the
								// original Axis fault
								callback.receiveErrorgetTokenFromTicket(f);
							}
						}
						else{
							callback.receiveErrorgetTokenFromTicket(f);
						}
					}
					else{
						callback.receiveErrorgetTokenFromTicket(f);
					}
				}
				else{
					callback.receiveErrorgetTokenFromTicket(error);
				}
			}

			public void onFault(org.apache.axis2.context.MessageContext faultContext){
				org.apache.axis2.AxisFault fault=org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
				onError(fault);
			}

			public void onComplete(){
				try{
					_messageContext.getTransportOut().getSender().cleanup(_messageContext);
				}
				catch(org.apache.axis2.AxisFault axisFault){
					callback.receiveErrorgetTokenFromTicket(axisFault);
				}
			}
		});

		org.apache.axis2.util.CallbackReceiver _callbackReceiver=null;
		if(_operations[3].getMessageReceiver() == null && _operationClient.getOptions().isUseSeparateListener()){
			_callbackReceiver=new org.apache.axis2.util.CallbackReceiver();
			_operations[3].setMessageReceiver(_callbackReceiver);
		}

		// execute the operation client
		_operationClient.execute(false);

	}

	/**
	 * A utility method that copies the namepaces from the SOAPEnvelope
	 */
	private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
		java.util.Map returnMap=new java.util.HashMap();
		java.util.Iterator namespaceIterator=env.getAllDeclaredNamespaces();
		while(namespaceIterator.hasNext()){
			org.apache.axiom.om.OMNamespace ns=(org.apache.axiom.om.OMNamespace) namespaceIterator.next();
			returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
		}
		return returnMap;
	}

	private javax.xml.namespace.QName[] opNameArray=null;

	private boolean optimizeContent(javax.xml.namespace.QName opName){

		if(opNameArray == null){
			return false;
		}
		for(int i=0;i < opNameArray.length;i++){
			if(opName.equals(opNameArray[i])){
				return true;
			}
		}
		return false;
	}

	// http://11.160.124.247:8080/axis2/services/TokenService
	public static class GetTokenResponse implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME=new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","getTokenResponse","ns1");

		private static java.lang.String generatePrefix(java.lang.String namespace){
			if(namespace.equals("http://tempo.intalio.org/security/tokenService/")){
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}

		/**
		 * field for Out
		 */

		protected java.lang.String localOut;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getOut(){
			return localOut;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Out
		 */
		public void setOut(java.lang.String param){

			this.localOut=param;

		}

		/**
		 * isReaderMTOMAware
		 * 
		 * @return true if the reader supports MTOM
		 */
		public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader){
			boolean isReaderMTOMAware=false;

			try{
				isReaderMTOMAware=java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
			}
			catch(java.lang.IllegalArgumentException e){
				isReaderMTOMAware=false;
			}
			return isReaderMTOMAware;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{

			org.apache.axiom.om.OMDataSource dataSource=new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

				public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
					GetTokenResponse.this.serialize(MY_QNAME,factory,xmlWriter);
				}
			};
			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(MY_QNAME,factory,dataSource);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{
			serialize(parentQName,factory,xmlWriter,false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,boolean serializeType) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{

			java.lang.String prefix=null;
			java.lang.String namespace=null;

			prefix=parentQName.getPrefix();
			namespace=parentQName.getNamespaceURI();

			if((namespace != null) && (namespace.trim().length() > 0)){
				java.lang.String writerPrefix=xmlWriter.getPrefix(namespace);
				if(writerPrefix != null){
					xmlWriter.writeStartElement(namespace,parentQName.getLocalPart());
				}
				else{
					if(prefix == null){
						prefix=generatePrefix(namespace);
					}

					xmlWriter.writeStartElement(prefix,parentQName.getLocalPart(),namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);
				}
			}
			else{
				xmlWriter.writeStartElement(parentQName.getLocalPart());
			}

			if(serializeType){

				java.lang.String namespacePrefix=registerPrefix(xmlWriter,"http://tempo.intalio.org/security/tokenService/");
				if((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",namespacePrefix + ":getTokenResponse",xmlWriter);
				}
				else{
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type","getTokenResponse",xmlWriter);
				}

			}

			namespace="http://tempo.intalio.org/security/tokenService/";
			if(!namespace.equals("")){
				prefix=xmlWriter.getPrefix(namespace);

				if(prefix == null){
					prefix=generatePrefix(namespace);

					xmlWriter.writeStartElement(prefix,"out",namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);

				}
				else{
					xmlWriter.writeStartElement(namespace,"out");
				}

			}
			else{
				xmlWriter.writeStartElement("out");
			}

			if(localOut == null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("out cannot be null!!");

			}
			else{

				xmlWriter.writeCharacters(localOut);

			}

			xmlWriter.writeEndElement();

			xmlWriter.writeEndElement();

		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(xmlWriter.getPrefix(namespace) == null){
				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);

			}

			xmlWriter.writeAttribute(namespace,attName,attValue);

		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,java.lang.String attName,javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			java.lang.String attributeNamespace=qname.getNamespaceURI();
			java.lang.String attributePrefix=xmlWriter.getPrefix(attributeNamespace);
			if(attributePrefix == null){
				attributePrefix=registerPrefix(xmlWriter,attributeNamespace);
			}
			java.lang.String attributeValue;
			if(attributePrefix.trim().length() > 0){
				attributeValue=attributePrefix + ":" + qname.getLocalPart();
			}
			else{
				attributeValue=qname.getLocalPart();
			}

			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attributeValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			java.lang.String namespaceURI=qname.getNamespaceURI();
			if(namespaceURI != null){
				java.lang.String prefix=xmlWriter.getPrefix(namespaceURI);
				if(prefix == null){
					prefix=generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix,namespaceURI);
					xmlWriter.setPrefix(prefix,namespaceURI);
				}

				if(prefix.trim().length() > 0){
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
				else{
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}

			}
			else{
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			if(qnames != null){
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite=new java.lang.StringBuffer();
				java.lang.String namespaceURI=null;
				java.lang.String prefix=null;

				for(int i=0;i < qnames.length;i++){
					if(i > 0){
						stringToWrite.append(" ");
					}
					namespaceURI=qnames[i].getNamespaceURI();
					if(namespaceURI != null){
						prefix=xmlWriter.getPrefix(namespaceURI);
						if((prefix == null) || (prefix.length() == 0)){
							prefix=generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix,namespaceURI);
							xmlWriter.setPrefix(prefix,namespaceURI);
						}

						if(prefix.trim().length() > 0){
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
						else{
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					}
					else{
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter,java.lang.String namespace) throws javax.xml.stream.XMLStreamException{
			java.lang.String prefix=xmlWriter.getPrefix(namespace);

			if(prefix == null){
				prefix=generatePrefix(namespace);

				while(xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null){
					prefix=org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);
			}

			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName) throws org.apache.axis2.databinding.ADBException{

			java.util.ArrayList elementList=new java.util.ArrayList();
			java.util.ArrayList attribList=new java.util.ArrayList();

			elementList.add(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","out"));

			if(localOut != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOut));
			}
			else{
				throw new org.apache.axis2.databinding.ADBException("out cannot be null!!");
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,elementList.toArray(),attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory{

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static GetTokenResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				GetTokenResponse object=new GetTokenResponse();

				int event;
				java.lang.String nillableValue=null;
				java.lang.String prefix="";
				java.lang.String namespaceuri="";
				try{

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type") != null){
						java.lang.String fullTypeName=reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type");
						if(fullTypeName != null){
							java.lang.String nsPrefix=null;
							if(fullTypeName.indexOf(":") > -1){
								nsPrefix=fullTypeName.substring(0,fullTypeName.indexOf(":"));
							}
							nsPrefix=nsPrefix == null ? "" : nsPrefix;

							java.lang.String type=fullTypeName.substring(fullTypeName.indexOf(":") + 1);

							if(!"getTokenResponse".equals(type)){
								// find namespace for the prefix
								java.lang.String nsUri=reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (GetTokenResponse) ExtensionMapper.getTypeObject(nsUri,type,reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes=new java.util.Vector();

					reader.next();

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement() && new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","out").equals(reader.getName())){

						java.lang.String content=reader.getElementText();

						object.setOut(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
					}

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());

				}
				catch(javax.xml.stream.XMLStreamException e){
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class AuthenticateUserWithCredentials implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME=new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","authenticateUserWithCredentials","ns1");

		private static java.lang.String generatePrefix(java.lang.String namespace){
			if(namespace.equals("http://tempo.intalio.org/security/tokenService/")){
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}

		/**
		 * field for User
		 */

		protected java.lang.String localUser;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getUser(){
			return localUser;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            User
		 */
		public void setUser(java.lang.String param){

			this.localUser=param;

		}

		/**
		 * field for Credentials
		 */

		protected PropertyType localCredentials;

		/**
		 * Auto generated getter method
		 * 
		 * @return PropertyType
		 */
		public PropertyType getCredentials(){
			return localCredentials;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Credentials
		 */
		public void setCredentials(PropertyType param){

			this.localCredentials=param;

		}

		/**
		 * isReaderMTOMAware
		 * 
		 * @return true if the reader supports MTOM
		 */
		public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader){
			boolean isReaderMTOMAware=false;

			try{
				isReaderMTOMAware=java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
			}
			catch(java.lang.IllegalArgumentException e){
				isReaderMTOMAware=false;
			}
			return isReaderMTOMAware;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{

			org.apache.axiom.om.OMDataSource dataSource=new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

				public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
					AuthenticateUserWithCredentials.this.serialize(MY_QNAME,factory,xmlWriter);
				}
			};
			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(MY_QNAME,factory,dataSource);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{
			serialize(parentQName,factory,xmlWriter,false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,boolean serializeType) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{

			java.lang.String prefix=null;
			java.lang.String namespace=null;

			prefix=parentQName.getPrefix();
			namespace=parentQName.getNamespaceURI();

			if((namespace != null) && (namespace.trim().length() > 0)){
				java.lang.String writerPrefix=xmlWriter.getPrefix(namespace);
				if(writerPrefix != null){
					xmlWriter.writeStartElement(namespace,parentQName.getLocalPart());
				}
				else{
					if(prefix == null){
						prefix=generatePrefix(namespace);
					}

					xmlWriter.writeStartElement(prefix,parentQName.getLocalPart(),namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);
				}
			}
			else{
				xmlWriter.writeStartElement(parentQName.getLocalPart());
			}

			if(serializeType){

				java.lang.String namespacePrefix=registerPrefix(xmlWriter,"http://tempo.intalio.org/security/tokenService/");
				if((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",namespacePrefix + ":authenticateUserWithCredentials",xmlWriter);
				}
				else{
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type","authenticateUserWithCredentials",xmlWriter);
				}

			}

			namespace="http://tempo.intalio.org/security/tokenService/";
			if(!namespace.equals("")){
				prefix=xmlWriter.getPrefix(namespace);

				if(prefix == null){
					prefix=generatePrefix(namespace);

					xmlWriter.writeStartElement(prefix,"user",namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);

				}
				else{
					xmlWriter.writeStartElement(namespace,"user");
				}

			}
			else{
				xmlWriter.writeStartElement("user");
			}

			if(localUser == null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("user cannot be null!!");

			}
			else{

				xmlWriter.writeCharacters(localUser);

			}

			xmlWriter.writeEndElement();

			if(localCredentials == null){
				throw new org.apache.axis2.databinding.ADBException("credentials cannot be null!!");
			}
			localCredentials.serialize(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","credentials"),factory,xmlWriter);

			xmlWriter.writeEndElement();

		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(xmlWriter.getPrefix(namespace) == null){
				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);

			}

			xmlWriter.writeAttribute(namespace,attName,attValue);

		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,java.lang.String attName,javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			java.lang.String attributeNamespace=qname.getNamespaceURI();
			java.lang.String attributePrefix=xmlWriter.getPrefix(attributeNamespace);
			if(attributePrefix == null){
				attributePrefix=registerPrefix(xmlWriter,attributeNamespace);
			}
			java.lang.String attributeValue;
			if(attributePrefix.trim().length() > 0){
				attributeValue=attributePrefix + ":" + qname.getLocalPart();
			}
			else{
				attributeValue=qname.getLocalPart();
			}

			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attributeValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			java.lang.String namespaceURI=qname.getNamespaceURI();
			if(namespaceURI != null){
				java.lang.String prefix=xmlWriter.getPrefix(namespaceURI);
				if(prefix == null){
					prefix=generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix,namespaceURI);
					xmlWriter.setPrefix(prefix,namespaceURI);
				}

				if(prefix.trim().length() > 0){
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
				else{
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}

			}
			else{
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			if(qnames != null){
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite=new java.lang.StringBuffer();
				java.lang.String namespaceURI=null;
				java.lang.String prefix=null;

				for(int i=0;i < qnames.length;i++){
					if(i > 0){
						stringToWrite.append(" ");
					}
					namespaceURI=qnames[i].getNamespaceURI();
					if(namespaceURI != null){
						prefix=xmlWriter.getPrefix(namespaceURI);
						if((prefix == null) || (prefix.length() == 0)){
							prefix=generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix,namespaceURI);
							xmlWriter.setPrefix(prefix,namespaceURI);
						}

						if(prefix.trim().length() > 0){
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
						else{
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					}
					else{
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter,java.lang.String namespace) throws javax.xml.stream.XMLStreamException{
			java.lang.String prefix=xmlWriter.getPrefix(namespace);

			if(prefix == null){
				prefix=generatePrefix(namespace);

				while(xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null){
					prefix=org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);
			}

			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName) throws org.apache.axis2.databinding.ADBException{

			java.util.ArrayList elementList=new java.util.ArrayList();
			java.util.ArrayList attribList=new java.util.ArrayList();

			elementList.add(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","user"));

			if(localUser != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUser));
			}
			else{
				throw new org.apache.axis2.databinding.ADBException("user cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","credentials"));

			if(localCredentials == null){
				throw new org.apache.axis2.databinding.ADBException("credentials cannot be null!!");
			}
			elementList.add(localCredentials);

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,elementList.toArray(),attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory{

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static AuthenticateUserWithCredentials parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				AuthenticateUserWithCredentials object=new AuthenticateUserWithCredentials();

				int event;
				java.lang.String nillableValue=null;
				java.lang.String prefix="";
				java.lang.String namespaceuri="";
				try{

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type") != null){
						java.lang.String fullTypeName=reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type");
						if(fullTypeName != null){
							java.lang.String nsPrefix=null;
							if(fullTypeName.indexOf(":") > -1){
								nsPrefix=fullTypeName.substring(0,fullTypeName.indexOf(":"));
							}
							nsPrefix=nsPrefix == null ? "" : nsPrefix;

							java.lang.String type=fullTypeName.substring(fullTypeName.indexOf(":") + 1);

							if(!"authenticateUserWithCredentials".equals(type)){
								// find namespace for the prefix
								java.lang.String nsUri=reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (AuthenticateUserWithCredentials) ExtensionMapper.getTypeObject(nsUri,type,reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes=new java.util.Vector();

					reader.next();

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement() && new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","user").equals(reader.getName())){

						java.lang.String content=reader.getElementText();

						object.setUser(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
					}

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement() && new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","credentials").equals(reader.getName())){

						object.setCredentials(PropertyType.Factory.parse(reader));

						reader.next();

					} // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
					}

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());

				}
				catch(javax.xml.stream.XMLStreamException e){
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class AuthenticateUser implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME=new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","authenticateUser","ns1");

		private static java.lang.String generatePrefix(java.lang.String namespace){
			if(namespace.equals("http://tempo.intalio.org/security/tokenService/")){
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}

		/**
		 * field for User
		 */

		protected java.lang.String localUser;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getUser(){
			return localUser;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            User
		 */
		public void setUser(java.lang.String param){

			this.localUser=param;

		}

		/**
		 * field for Password
		 */

		protected java.lang.String localPassword;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getPassword(){
			return localPassword;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Password
		 */
		public void setPassword(java.lang.String param){

			this.localPassword=param;

		}

		/**
		 * isReaderMTOMAware
		 * 
		 * @return true if the reader supports MTOM
		 */
		public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader){
			boolean isReaderMTOMAware=false;

			try{
				isReaderMTOMAware=java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
			}
			catch(java.lang.IllegalArgumentException e){
				isReaderMTOMAware=false;
			}
			return isReaderMTOMAware;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{

			org.apache.axiom.om.OMDataSource dataSource=new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

				public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
					AuthenticateUser.this.serialize(MY_QNAME,factory,xmlWriter);
				}
			};
			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(MY_QNAME,factory,dataSource);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{
			serialize(parentQName,factory,xmlWriter,false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,boolean serializeType) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{

			java.lang.String prefix=null;
			java.lang.String namespace=null;

			prefix=parentQName.getPrefix();
			namespace=parentQName.getNamespaceURI();

			if((namespace != null) && (namespace.trim().length() > 0)){
				java.lang.String writerPrefix=xmlWriter.getPrefix(namespace);
				if(writerPrefix != null){
					xmlWriter.writeStartElement(namespace,parentQName.getLocalPart());
				}
				else{
					if(prefix == null){
						prefix=generatePrefix(namespace);
					}

					xmlWriter.writeStartElement(prefix,parentQName.getLocalPart(),namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);
				}
			}
			else{
				xmlWriter.writeStartElement(parentQName.getLocalPart());
			}

			if(serializeType){

				java.lang.String namespacePrefix=registerPrefix(xmlWriter,"http://tempo.intalio.org/security/tokenService/");
				if((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",namespacePrefix + ":authenticateUser",xmlWriter);
				}
				else{
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type","authenticateUser",xmlWriter);
				}

			}

			namespace="http://tempo.intalio.org/security/tokenService/";
			if(!namespace.equals("")){
				prefix=xmlWriter.getPrefix(namespace);

				if(prefix == null){
					prefix=generatePrefix(namespace);

					xmlWriter.writeStartElement(prefix,"user",namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);

				}
				else{
					xmlWriter.writeStartElement(namespace,"user");
				}

			}
			else{
				xmlWriter.writeStartElement("user");
			}

			if(localUser == null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("user cannot be null!!");

			}
			else{

				xmlWriter.writeCharacters(localUser);

			}

			xmlWriter.writeEndElement();

			namespace="http://tempo.intalio.org/security/tokenService/";
			if(!namespace.equals("")){
				prefix=xmlWriter.getPrefix(namespace);

				if(prefix == null){
					prefix=generatePrefix(namespace);

					xmlWriter.writeStartElement(prefix,"password",namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);

				}
				else{
					xmlWriter.writeStartElement(namespace,"password");
				}

			}
			else{
				xmlWriter.writeStartElement("password");
			}

			if(localPassword == null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("password cannot be null!!");

			}
			else{

				xmlWriter.writeCharacters(localPassword);

			}

			xmlWriter.writeEndElement();

			xmlWriter.writeEndElement();

		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(xmlWriter.getPrefix(namespace) == null){
				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);

			}

			xmlWriter.writeAttribute(namespace,attName,attValue);

		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,java.lang.String attName,javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			java.lang.String attributeNamespace=qname.getNamespaceURI();
			java.lang.String attributePrefix=xmlWriter.getPrefix(attributeNamespace);
			if(attributePrefix == null){
				attributePrefix=registerPrefix(xmlWriter,attributeNamespace);
			}
			java.lang.String attributeValue;
			if(attributePrefix.trim().length() > 0){
				attributeValue=attributePrefix + ":" + qname.getLocalPart();
			}
			else{
				attributeValue=qname.getLocalPart();
			}

			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attributeValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			java.lang.String namespaceURI=qname.getNamespaceURI();
			if(namespaceURI != null){
				java.lang.String prefix=xmlWriter.getPrefix(namespaceURI);
				if(prefix == null){
					prefix=generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix,namespaceURI);
					xmlWriter.setPrefix(prefix,namespaceURI);
				}

				if(prefix.trim().length() > 0){
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
				else{
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}

			}
			else{
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			if(qnames != null){
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite=new java.lang.StringBuffer();
				java.lang.String namespaceURI=null;
				java.lang.String prefix=null;

				for(int i=0;i < qnames.length;i++){
					if(i > 0){
						stringToWrite.append(" ");
					}
					namespaceURI=qnames[i].getNamespaceURI();
					if(namespaceURI != null){
						prefix=xmlWriter.getPrefix(namespaceURI);
						if((prefix == null) || (prefix.length() == 0)){
							prefix=generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix,namespaceURI);
							xmlWriter.setPrefix(prefix,namespaceURI);
						}

						if(prefix.trim().length() > 0){
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
						else{
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					}
					else{
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter,java.lang.String namespace) throws javax.xml.stream.XMLStreamException{
			java.lang.String prefix=xmlWriter.getPrefix(namespace);

			if(prefix == null){
				prefix=generatePrefix(namespace);

				while(xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null){
					prefix=org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);
			}

			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName) throws org.apache.axis2.databinding.ADBException{

			java.util.ArrayList elementList=new java.util.ArrayList();
			java.util.ArrayList attribList=new java.util.ArrayList();

			elementList.add(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","user"));

			if(localUser != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUser));
			}
			else{
				throw new org.apache.axis2.databinding.ADBException("user cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","password"));

			if(localPassword != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPassword));
			}
			else{
				throw new org.apache.axis2.databinding.ADBException("password cannot be null!!");
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,elementList.toArray(),attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory{

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static AuthenticateUser parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				AuthenticateUser object=new AuthenticateUser();

				int event;
				java.lang.String nillableValue=null;
				java.lang.String prefix="";
				java.lang.String namespaceuri="";
				try{

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type") != null){
						java.lang.String fullTypeName=reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type");
						if(fullTypeName != null){
							java.lang.String nsPrefix=null;
							if(fullTypeName.indexOf(":") > -1){
								nsPrefix=fullTypeName.substring(0,fullTypeName.indexOf(":"));
							}
							nsPrefix=nsPrefix == null ? "" : nsPrefix;

							java.lang.String type=fullTypeName.substring(fullTypeName.indexOf(":") + 1);

							if(!"authenticateUser".equals(type)){
								// find namespace for the prefix
								java.lang.String nsUri=reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (AuthenticateUser) ExtensionMapper.getTypeObject(nsUri,type,reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes=new java.util.Vector();

					reader.next();

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement() && new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","user").equals(reader.getName())){

						java.lang.String content=reader.getElementText();

						object.setUser(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
					}

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement() && new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","password").equals(reader.getName())){

						java.lang.String content=reader.getElementText();

						object.setPassword(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
					}

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());

				}
				catch(javax.xml.stream.XMLStreamException e){
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class GetTokenPropertiesResponse implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME=new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","getTokenPropertiesResponse","ns1");

		private static java.lang.String generatePrefix(java.lang.String namespace){
			if(namespace.equals("http://tempo.intalio.org/security/tokenService/")){
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}

		/**
		 * field for Credentials
		 */

		protected PropertyType localCredentials;

		/**
		 * Auto generated getter method
		 * 
		 * @return PropertyType
		 */
		public PropertyType getCredentials(){
			return localCredentials;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Credentials
		 */
		public void setCredentials(PropertyType param){

			this.localCredentials=param;

		}

		/**
		 * isReaderMTOMAware
		 * 
		 * @return true if the reader supports MTOM
		 */
		public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader){
			boolean isReaderMTOMAware=false;

			try{
				isReaderMTOMAware=java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
			}
			catch(java.lang.IllegalArgumentException e){
				isReaderMTOMAware=false;
			}
			return isReaderMTOMAware;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{

			org.apache.axiom.om.OMDataSource dataSource=new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

				public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
					GetTokenPropertiesResponse.this.serialize(MY_QNAME,factory,xmlWriter);
				}
			};
			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(MY_QNAME,factory,dataSource);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{
			serialize(parentQName,factory,xmlWriter,false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,boolean serializeType) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{

			java.lang.String prefix=null;
			java.lang.String namespace=null;

			prefix=parentQName.getPrefix();
			namespace=parentQName.getNamespaceURI();

			if((namespace != null) && (namespace.trim().length() > 0)){
				java.lang.String writerPrefix=xmlWriter.getPrefix(namespace);
				if(writerPrefix != null){
					xmlWriter.writeStartElement(namespace,parentQName.getLocalPart());
				}
				else{
					if(prefix == null){
						prefix=generatePrefix(namespace);
					}

					xmlWriter.writeStartElement(prefix,parentQName.getLocalPart(),namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);
				}
			}
			else{
				xmlWriter.writeStartElement(parentQName.getLocalPart());
			}

			if(serializeType){

				java.lang.String namespacePrefix=registerPrefix(xmlWriter,"http://tempo.intalio.org/security/tokenService/");
				if((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",namespacePrefix + ":getTokenPropertiesResponse",xmlWriter);
				}
				else{
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type","getTokenPropertiesResponse",xmlWriter);
				}

			}

			if(localCredentials == null){
				throw new org.apache.axis2.databinding.ADBException("credentials cannot be null!!");
			}
			localCredentials.serialize(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","credentials"),factory,xmlWriter);

			xmlWriter.writeEndElement();

		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(xmlWriter.getPrefix(namespace) == null){
				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);

			}

			xmlWriter.writeAttribute(namespace,attName,attValue);

		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,java.lang.String attName,javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			java.lang.String attributeNamespace=qname.getNamespaceURI();
			java.lang.String attributePrefix=xmlWriter.getPrefix(attributeNamespace);
			if(attributePrefix == null){
				attributePrefix=registerPrefix(xmlWriter,attributeNamespace);
			}
			java.lang.String attributeValue;
			if(attributePrefix.trim().length() > 0){
				attributeValue=attributePrefix + ":" + qname.getLocalPart();
			}
			else{
				attributeValue=qname.getLocalPart();
			}

			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attributeValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			java.lang.String namespaceURI=qname.getNamespaceURI();
			if(namespaceURI != null){
				java.lang.String prefix=xmlWriter.getPrefix(namespaceURI);
				if(prefix == null){
					prefix=generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix,namespaceURI);
					xmlWriter.setPrefix(prefix,namespaceURI);
				}

				if(prefix.trim().length() > 0){
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
				else{
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}

			}
			else{
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			if(qnames != null){
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite=new java.lang.StringBuffer();
				java.lang.String namespaceURI=null;
				java.lang.String prefix=null;

				for(int i=0;i < qnames.length;i++){
					if(i > 0){
						stringToWrite.append(" ");
					}
					namespaceURI=qnames[i].getNamespaceURI();
					if(namespaceURI != null){
						prefix=xmlWriter.getPrefix(namespaceURI);
						if((prefix == null) || (prefix.length() == 0)){
							prefix=generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix,namespaceURI);
							xmlWriter.setPrefix(prefix,namespaceURI);
						}

						if(prefix.trim().length() > 0){
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
						else{
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					}
					else{
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter,java.lang.String namespace) throws javax.xml.stream.XMLStreamException{
			java.lang.String prefix=xmlWriter.getPrefix(namespace);

			if(prefix == null){
				prefix=generatePrefix(namespace);

				while(xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null){
					prefix=org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);
			}

			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName) throws org.apache.axis2.databinding.ADBException{

			java.util.ArrayList elementList=new java.util.ArrayList();
			java.util.ArrayList attribList=new java.util.ArrayList();

			elementList.add(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","credentials"));

			if(localCredentials == null){
				throw new org.apache.axis2.databinding.ADBException("credentials cannot be null!!");
			}
			elementList.add(localCredentials);

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,elementList.toArray(),attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory{

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static GetTokenPropertiesResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				GetTokenPropertiesResponse object=new GetTokenPropertiesResponse();

				int event;
				java.lang.String nillableValue=null;
				java.lang.String prefix="";
				java.lang.String namespaceuri="";
				try{

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type") != null){
						java.lang.String fullTypeName=reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type");
						if(fullTypeName != null){
							java.lang.String nsPrefix=null;
							if(fullTypeName.indexOf(":") > -1){
								nsPrefix=fullTypeName.substring(0,fullTypeName.indexOf(":"));
							}
							nsPrefix=nsPrefix == null ? "" : nsPrefix;

							java.lang.String type=fullTypeName.substring(fullTypeName.indexOf(":") + 1);

							if(!"getTokenPropertiesResponse".equals(type)){
								// find namespace for the prefix
								java.lang.String nsUri=reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (GetTokenPropertiesResponse) ExtensionMapper.getTypeObject(nsUri,type,reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes=new java.util.Vector();

					reader.next();

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement() && new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","credentials").equals(reader.getName())){

						object.setCredentials(PropertyType.Factory.parse(reader));

						reader.next();

					} // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
					}

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());

				}
				catch(javax.xml.stream.XMLStreamException e){
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class AuthenticateUserResponse implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME=new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","authenticateUserResponse","ns1");

		private static java.lang.String generatePrefix(java.lang.String namespace){
			if(namespace.equals("http://tempo.intalio.org/security/tokenService/")){
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}

		/**
		 * field for Token
		 */

		protected java.lang.String localToken;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getToken(){
			return localToken;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Token
		 */
		public void setToken(java.lang.String param){

			this.localToken=param;

		}

		/**
		 * isReaderMTOMAware
		 * 
		 * @return true if the reader supports MTOM
		 */
		public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader){
			boolean isReaderMTOMAware=false;

			try{
				isReaderMTOMAware=java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
			}
			catch(java.lang.IllegalArgumentException e){
				isReaderMTOMAware=false;
			}
			return isReaderMTOMAware;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{

			org.apache.axiom.om.OMDataSource dataSource=new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

				public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
					AuthenticateUserResponse.this.serialize(MY_QNAME,factory,xmlWriter);
				}
			};
			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(MY_QNAME,factory,dataSource);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{
			serialize(parentQName,factory,xmlWriter,false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,boolean serializeType) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{

			java.lang.String prefix=null;
			java.lang.String namespace=null;

			prefix=parentQName.getPrefix();
			namespace=parentQName.getNamespaceURI();

			if((namespace != null) && (namespace.trim().length() > 0)){
				java.lang.String writerPrefix=xmlWriter.getPrefix(namespace);
				if(writerPrefix != null){
					xmlWriter.writeStartElement(namespace,parentQName.getLocalPart());
				}
				else{
					if(prefix == null){
						prefix=generatePrefix(namespace);
					}

					xmlWriter.writeStartElement(prefix,parentQName.getLocalPart(),namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);
				}
			}
			else{
				xmlWriter.writeStartElement(parentQName.getLocalPart());
			}

			if(serializeType){

				java.lang.String namespacePrefix=registerPrefix(xmlWriter,"http://tempo.intalio.org/security/tokenService/");
				if((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",namespacePrefix + ":authenticateUserResponse",xmlWriter);
				}
				else{
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type","authenticateUserResponse",xmlWriter);
				}

			}

			namespace="http://tempo.intalio.org/security/tokenService/";
			if(!namespace.equals("")){
				prefix=xmlWriter.getPrefix(namespace);

				if(prefix == null){
					prefix=generatePrefix(namespace);

					xmlWriter.writeStartElement(prefix,"token",namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);

				}
				else{
					xmlWriter.writeStartElement(namespace,"token");
				}

			}
			else{
				xmlWriter.writeStartElement("token");
			}

			if(localToken == null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");

			}
			else{

				xmlWriter.writeCharacters(localToken);

			}

			xmlWriter.writeEndElement();

			xmlWriter.writeEndElement();

		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(xmlWriter.getPrefix(namespace) == null){
				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);

			}

			xmlWriter.writeAttribute(namespace,attName,attValue);

		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,java.lang.String attName,javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			java.lang.String attributeNamespace=qname.getNamespaceURI();
			java.lang.String attributePrefix=xmlWriter.getPrefix(attributeNamespace);
			if(attributePrefix == null){
				attributePrefix=registerPrefix(xmlWriter,attributeNamespace);
			}
			java.lang.String attributeValue;
			if(attributePrefix.trim().length() > 0){
				attributeValue=attributePrefix + ":" + qname.getLocalPart();
			}
			else{
				attributeValue=qname.getLocalPart();
			}

			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attributeValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			java.lang.String namespaceURI=qname.getNamespaceURI();
			if(namespaceURI != null){
				java.lang.String prefix=xmlWriter.getPrefix(namespaceURI);
				if(prefix == null){
					prefix=generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix,namespaceURI);
					xmlWriter.setPrefix(prefix,namespaceURI);
				}

				if(prefix.trim().length() > 0){
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
				else{
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}

			}
			else{
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			if(qnames != null){
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite=new java.lang.StringBuffer();
				java.lang.String namespaceURI=null;
				java.lang.String prefix=null;

				for(int i=0;i < qnames.length;i++){
					if(i > 0){
						stringToWrite.append(" ");
					}
					namespaceURI=qnames[i].getNamespaceURI();
					if(namespaceURI != null){
						prefix=xmlWriter.getPrefix(namespaceURI);
						if((prefix == null) || (prefix.length() == 0)){
							prefix=generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix,namespaceURI);
							xmlWriter.setPrefix(prefix,namespaceURI);
						}

						if(prefix.trim().length() > 0){
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
						else{
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					}
					else{
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter,java.lang.String namespace) throws javax.xml.stream.XMLStreamException{
			java.lang.String prefix=xmlWriter.getPrefix(namespace);

			if(prefix == null){
				prefix=generatePrefix(namespace);

				while(xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null){
					prefix=org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);
			}

			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName) throws org.apache.axis2.databinding.ADBException{

			java.util.ArrayList elementList=new java.util.ArrayList();
			java.util.ArrayList attribList=new java.util.ArrayList();

			elementList.add(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","token"));

			if(localToken != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localToken));
			}
			else{
				throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,elementList.toArray(),attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory{

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static AuthenticateUserResponse parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				AuthenticateUserResponse object=new AuthenticateUserResponse();

				int event;
				java.lang.String nillableValue=null;
				java.lang.String prefix="";
				java.lang.String namespaceuri="";
				try{

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type") != null){
						java.lang.String fullTypeName=reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type");
						if(fullTypeName != null){
							java.lang.String nsPrefix=null;
							if(fullTypeName.indexOf(":") > -1){
								nsPrefix=fullTypeName.substring(0,fullTypeName.indexOf(":"));
							}
							nsPrefix=nsPrefix == null ? "" : nsPrefix;

							java.lang.String type=fullTypeName.substring(fullTypeName.indexOf(":") + 1);

							if(!"authenticateUserResponse".equals(type)){
								// find namespace for the prefix
								java.lang.String nsUri=reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (AuthenticateUserResponse) ExtensionMapper.getTypeObject(nsUri,type,reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes=new java.util.Vector();

					reader.next();

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement() && new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","token").equals(reader.getName())){

						java.lang.String content=reader.getElementText();

						object.setToken(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
					}

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());

				}
				catch(javax.xml.stream.XMLStreamException e){
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class ExtensionMapper{

		public static java.lang.Object getTypeObject(java.lang.String namespaceURI,java.lang.String typeName,javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

			if("http://tempo.intalio.org/security/tokenService/".equals(namespaceURI) && "PropertyType".equals(typeName)){

				return PropertyType.Factory.parse(reader);

			}

			throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
		}

	}

	public static class Properties implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME=new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","properties","ns1");

		private static java.lang.String generatePrefix(java.lang.String namespace){
			if(namespace.equals("http://tempo.intalio.org/security/tokenService/")){
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}

		/**
		 * field for Property This was an Array!
		 */

		protected PropertyType[] localProperty;

		/*
		 * This tracker boolean wil be used to detect whether the user called
		 * the set method for this attribute. It will be used to determine
		 * whether to include this field in the serialized XML
		 */
		protected boolean localPropertyTracker=false;

		/**
		 * Auto generated getter method
		 * 
		 * @return PropertyType[]
		 */
		public PropertyType[] getProperty(){
			return localProperty;
		}

		/**
		 * validate the array for Property
		 */
		protected void validateProperty(PropertyType[] param){

		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Property
		 */
		public void setProperty(PropertyType[] param){

			validateProperty(param);

			if(param != null){
				// update the setting tracker
				localPropertyTracker=true;
			}
			else{
				localPropertyTracker=false;

			}

			this.localProperty=param;
		}

		/**
		 * Auto generated add method for the array for convenience
		 * 
		 * @param param
		 *            PropertyType
		 */
		public void addProperty(PropertyType param){
			if(localProperty == null){
				localProperty=new PropertyType[]{};
			}

			// update the setting tracker
			localPropertyTracker=true;

			java.util.List list=org.apache.axis2.databinding.utils.ConverterUtil.toList(localProperty);
			list.add(param);
			this.localProperty=(PropertyType[]) list.toArray(new PropertyType[list.size()]);

		}

		/**
		 * isReaderMTOMAware
		 * 
		 * @return true if the reader supports MTOM
		 */
		public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader){
			boolean isReaderMTOMAware=false;

			try{
				isReaderMTOMAware=java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
			}
			catch(java.lang.IllegalArgumentException e){
				isReaderMTOMAware=false;
			}
			return isReaderMTOMAware;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{

			org.apache.axiom.om.OMDataSource dataSource=new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

				public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
					Properties.this.serialize(MY_QNAME,factory,xmlWriter);
				}
			};
			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(MY_QNAME,factory,dataSource);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{
			serialize(parentQName,factory,xmlWriter,false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,boolean serializeType) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{

			java.lang.String prefix=null;
			java.lang.String namespace=null;

			prefix=parentQName.getPrefix();
			namespace=parentQName.getNamespaceURI();

			if((namespace != null) && (namespace.trim().length() > 0)){
				java.lang.String writerPrefix=xmlWriter.getPrefix(namespace);
				if(writerPrefix != null){
					xmlWriter.writeStartElement(namespace,parentQName.getLocalPart());
				}
				else{
					if(prefix == null){
						prefix=generatePrefix(namespace);
					}

					xmlWriter.writeStartElement(prefix,parentQName.getLocalPart(),namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);
				}
			}
			else{
				xmlWriter.writeStartElement(parentQName.getLocalPart());
			}

			if(serializeType){

				java.lang.String namespacePrefix=registerPrefix(xmlWriter,"http://tempo.intalio.org/security/tokenService/");
				if((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",namespacePrefix + ":properties",xmlWriter);
				}
				else{
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type","properties",xmlWriter);
				}

			}
			if(localPropertyTracker){
				if(localProperty != null){
					for(int i=0;i < localProperty.length;i++){
						if(localProperty[i] != null){
							localProperty[i].serialize(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","property"),factory,xmlWriter);
						}
						else{

							// we don't have to do any thing since minOccures is
							// zero

						}

					}
				}
				else{

					throw new org.apache.axis2.databinding.ADBException("property cannot be null!!");

				}
			}
			xmlWriter.writeEndElement();

		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(xmlWriter.getPrefix(namespace) == null){
				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);

			}

			xmlWriter.writeAttribute(namespace,attName,attValue);

		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,java.lang.String attName,javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			java.lang.String attributeNamespace=qname.getNamespaceURI();
			java.lang.String attributePrefix=xmlWriter.getPrefix(attributeNamespace);
			if(attributePrefix == null){
				attributePrefix=registerPrefix(xmlWriter,attributeNamespace);
			}
			java.lang.String attributeValue;
			if(attributePrefix.trim().length() > 0){
				attributeValue=attributePrefix + ":" + qname.getLocalPart();
			}
			else{
				attributeValue=qname.getLocalPart();
			}

			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attributeValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			java.lang.String namespaceURI=qname.getNamespaceURI();
			if(namespaceURI != null){
				java.lang.String prefix=xmlWriter.getPrefix(namespaceURI);
				if(prefix == null){
					prefix=generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix,namespaceURI);
					xmlWriter.setPrefix(prefix,namespaceURI);
				}

				if(prefix.trim().length() > 0){
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
				else{
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}

			}
			else{
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			if(qnames != null){
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite=new java.lang.StringBuffer();
				java.lang.String namespaceURI=null;
				java.lang.String prefix=null;

				for(int i=0;i < qnames.length;i++){
					if(i > 0){
						stringToWrite.append(" ");
					}
					namespaceURI=qnames[i].getNamespaceURI();
					if(namespaceURI != null){
						prefix=xmlWriter.getPrefix(namespaceURI);
						if((prefix == null) || (prefix.length() == 0)){
							prefix=generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix,namespaceURI);
							xmlWriter.setPrefix(prefix,namespaceURI);
						}

						if(prefix.trim().length() > 0){
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
						else{
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					}
					else{
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter,java.lang.String namespace) throws javax.xml.stream.XMLStreamException{
			java.lang.String prefix=xmlWriter.getPrefix(namespace);

			if(prefix == null){
				prefix=generatePrefix(namespace);

				while(xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null){
					prefix=org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);
			}

			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName) throws org.apache.axis2.databinding.ADBException{

			java.util.ArrayList elementList=new java.util.ArrayList();
			java.util.ArrayList attribList=new java.util.ArrayList();

			if(localPropertyTracker){
				if(localProperty != null){
					for(int i=0;i < localProperty.length;i++){

						if(localProperty[i] != null){
							elementList.add(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","property"));
							elementList.add(localProperty[i]);
						}
						else{

							// nothing to do

						}

					}
				}
				else{

					throw new org.apache.axis2.databinding.ADBException("property cannot be null!!");

				}

			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,elementList.toArray(),attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory{

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static Properties parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				Properties object=new Properties();

				int event;
				java.lang.String nillableValue=null;
				java.lang.String prefix="";
				java.lang.String namespaceuri="";
				try{

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type") != null){
						java.lang.String fullTypeName=reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type");
						if(fullTypeName != null){
							java.lang.String nsPrefix=null;
							if(fullTypeName.indexOf(":") > -1){
								nsPrefix=fullTypeName.substring(0,fullTypeName.indexOf(":"));
							}
							nsPrefix=nsPrefix == null ? "" : nsPrefix;

							java.lang.String type=fullTypeName.substring(fullTypeName.indexOf(":") + 1);

							if(!"properties".equals(type)){
								// find namespace for the prefix
								java.lang.String nsUri=reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (Properties) ExtensionMapper.getTypeObject(nsUri,type,reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes=new java.util.Vector();

					reader.next();

					java.util.ArrayList list1=new java.util.ArrayList();

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement() && new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","property").equals(reader.getName())){

						// Process the array and step past its final element's
						// end.
						list1.add(PropertyType.Factory.parse(reader));

						// loop until we find a start element that is not part
						// of this array
						boolean loopDone1=false;
						while(!loopDone1){
							// We should be at the end element, but make sure
							while(!reader.isEndElement())
								reader.next();
							// Step out of this element
							reader.next();
							// Step to next element event.
							while(!reader.isStartElement() && !reader.isEndElement())
								reader.next();
							if(reader.isEndElement()){
								// two continuous end elements means we are
								// exiting the xml structure
								loopDone1=true;
							}
							else{
								if(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","property").equals(reader.getName())){
									list1.add(PropertyType.Factory.parse(reader));

								}
								else{
									loopDone1=true;
								}
							}
						}
						// call the converter utility to convert and set the
						// array

						object.setProperty((PropertyType[]) org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(PropertyType.class,list1));

					} // End of if for expected property start element

					else{

					}

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());

				}
				catch(javax.xml.stream.XMLStreamException e){
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class ProxyTicket implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME=new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","proxyTicket","ns1");

		private static java.lang.String generatePrefix(java.lang.String namespace){
			if(namespace.equals("http://tempo.intalio.org/security/tokenService/")){
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}

		/**
		 * field for Ticket
		 */

		protected java.lang.String localTicket;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getTicket(){
			return localTicket;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Ticket
		 */
		public void setTicket(java.lang.String param){

			this.localTicket=param;

		}

		/**
		 * field for ServiceURL
		 */

		protected java.lang.String localServiceURL;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getServiceURL(){
			return localServiceURL;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            ServiceURL
		 */
		public void setServiceURL(java.lang.String param){

			this.localServiceURL=param;

		}

		/**
		 * isReaderMTOMAware
		 * 
		 * @return true if the reader supports MTOM
		 */
		public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader){
			boolean isReaderMTOMAware=false;

			try{
				isReaderMTOMAware=java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
			}
			catch(java.lang.IllegalArgumentException e){
				isReaderMTOMAware=false;
			}
			return isReaderMTOMAware;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{

			org.apache.axiom.om.OMDataSource dataSource=new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

				public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
					ProxyTicket.this.serialize(MY_QNAME,factory,xmlWriter);
				}
			};
			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(MY_QNAME,factory,dataSource);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{
			serialize(parentQName,factory,xmlWriter,false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,boolean serializeType) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{

			java.lang.String prefix=null;
			java.lang.String namespace=null;

			prefix=parentQName.getPrefix();
			namespace=parentQName.getNamespaceURI();

			if((namespace != null) && (namespace.trim().length() > 0)){
				java.lang.String writerPrefix=xmlWriter.getPrefix(namespace);
				if(writerPrefix != null){
					xmlWriter.writeStartElement(namespace,parentQName.getLocalPart());
				}
				else{
					if(prefix == null){
						prefix=generatePrefix(namespace);
					}

					xmlWriter.writeStartElement(prefix,parentQName.getLocalPart(),namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);
				}
			}
			else{
				xmlWriter.writeStartElement(parentQName.getLocalPart());
			}

			if(serializeType){

				java.lang.String namespacePrefix=registerPrefix(xmlWriter,"http://tempo.intalio.org/security/tokenService/");
				if((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",namespacePrefix + ":proxyTicket",xmlWriter);
				}
				else{
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type","proxyTicket",xmlWriter);
				}

			}

			namespace="http://tempo.intalio.org/security/tokenService/";
			if(!namespace.equals("")){
				prefix=xmlWriter.getPrefix(namespace);

				if(prefix == null){
					prefix=generatePrefix(namespace);

					xmlWriter.writeStartElement(prefix,"ticket",namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);

				}
				else{
					xmlWriter.writeStartElement(namespace,"ticket");
				}

			}
			else{
				xmlWriter.writeStartElement("ticket");
			}

			if(localTicket == null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("ticket cannot be null!!");

			}
			else{

				xmlWriter.writeCharacters(localTicket);

			}

			xmlWriter.writeEndElement();

			namespace="http://tempo.intalio.org/security/tokenService/";
			if(!namespace.equals("")){
				prefix=xmlWriter.getPrefix(namespace);

				if(prefix == null){
					prefix=generatePrefix(namespace);

					xmlWriter.writeStartElement(prefix,"serviceURL",namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);

				}
				else{
					xmlWriter.writeStartElement(namespace,"serviceURL");
				}

			}
			else{
				xmlWriter.writeStartElement("serviceURL");
			}

			if(localServiceURL == null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("serviceURL cannot be null!!");

			}
			else{

				xmlWriter.writeCharacters(localServiceURL);

			}

			xmlWriter.writeEndElement();

			xmlWriter.writeEndElement();

		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(xmlWriter.getPrefix(namespace) == null){
				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);

			}

			xmlWriter.writeAttribute(namespace,attName,attValue);

		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,java.lang.String attName,javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			java.lang.String attributeNamespace=qname.getNamespaceURI();
			java.lang.String attributePrefix=xmlWriter.getPrefix(attributeNamespace);
			if(attributePrefix == null){
				attributePrefix=registerPrefix(xmlWriter,attributeNamespace);
			}
			java.lang.String attributeValue;
			if(attributePrefix.trim().length() > 0){
				attributeValue=attributePrefix + ":" + qname.getLocalPart();
			}
			else{
				attributeValue=qname.getLocalPart();
			}

			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attributeValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			java.lang.String namespaceURI=qname.getNamespaceURI();
			if(namespaceURI != null){
				java.lang.String prefix=xmlWriter.getPrefix(namespaceURI);
				if(prefix == null){
					prefix=generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix,namespaceURI);
					xmlWriter.setPrefix(prefix,namespaceURI);
				}

				if(prefix.trim().length() > 0){
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
				else{
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}

			}
			else{
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			if(qnames != null){
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite=new java.lang.StringBuffer();
				java.lang.String namespaceURI=null;
				java.lang.String prefix=null;

				for(int i=0;i < qnames.length;i++){
					if(i > 0){
						stringToWrite.append(" ");
					}
					namespaceURI=qnames[i].getNamespaceURI();
					if(namespaceURI != null){
						prefix=xmlWriter.getPrefix(namespaceURI);
						if((prefix == null) || (prefix.length() == 0)){
							prefix=generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix,namespaceURI);
							xmlWriter.setPrefix(prefix,namespaceURI);
						}

						if(prefix.trim().length() > 0){
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
						else{
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					}
					else{
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter,java.lang.String namespace) throws javax.xml.stream.XMLStreamException{
			java.lang.String prefix=xmlWriter.getPrefix(namespace);

			if(prefix == null){
				prefix=generatePrefix(namespace);

				while(xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null){
					prefix=org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);
			}

			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName) throws org.apache.axis2.databinding.ADBException{

			java.util.ArrayList elementList=new java.util.ArrayList();
			java.util.ArrayList attribList=new java.util.ArrayList();

			elementList.add(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","ticket"));

			if(localTicket != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTicket));
			}
			else{
				throw new org.apache.axis2.databinding.ADBException("ticket cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","serviceURL"));

			if(localServiceURL != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localServiceURL));
			}
			else{
				throw new org.apache.axis2.databinding.ADBException("serviceURL cannot be null!!");
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,elementList.toArray(),attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory{

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static ProxyTicket parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				ProxyTicket object=new ProxyTicket();

				int event;
				java.lang.String nillableValue=null;
				java.lang.String prefix="";
				java.lang.String namespaceuri="";
				try{

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type") != null){
						java.lang.String fullTypeName=reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type");
						if(fullTypeName != null){
							java.lang.String nsPrefix=null;
							if(fullTypeName.indexOf(":") > -1){
								nsPrefix=fullTypeName.substring(0,fullTypeName.indexOf(":"));
							}
							nsPrefix=nsPrefix == null ? "" : nsPrefix;

							java.lang.String type=fullTypeName.substring(fullTypeName.indexOf(":") + 1);

							if(!"proxyTicket".equals(type)){
								// find namespace for the prefix
								java.lang.String nsUri=reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (ProxyTicket) ExtensionMapper.getTypeObject(nsUri,type,reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes=new java.util.Vector();

					reader.next();

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement() && new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","ticket").equals(reader.getName())){

						java.lang.String content=reader.getElementText();

						object.setTicket(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
					}

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement() && new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","serviceURL").equals(reader.getName())){

						java.lang.String content=reader.getElementText();

						object.setServiceURL(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
					}

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());

				}
				catch(javax.xml.stream.XMLStreamException e){
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class GetTokenProperties implements org.apache.axis2.databinding.ADBBean{

		public static final javax.xml.namespace.QName MY_QNAME=new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","getTokenProperties","ns1");

		private static java.lang.String generatePrefix(java.lang.String namespace){
			if(namespace.equals("http://tempo.intalio.org/security/tokenService/")){
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}

		/**
		 * field for Token
		 */

		protected java.lang.String localToken;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getToken(){
			return localToken;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Token
		 */
		public void setToken(java.lang.String param){

			this.localToken=param;

		}

		/**
		 * isReaderMTOMAware
		 * 
		 * @return true if the reader supports MTOM
		 */
		public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader){
			boolean isReaderMTOMAware=false;

			try{
				isReaderMTOMAware=java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
			}
			catch(java.lang.IllegalArgumentException e){
				isReaderMTOMAware=false;
			}
			return isReaderMTOMAware;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{

			org.apache.axiom.om.OMDataSource dataSource=new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME){

				public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
					GetTokenProperties.this.serialize(MY_QNAME,factory,xmlWriter);
				}
			};
			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(MY_QNAME,factory,dataSource);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{
			serialize(parentQName,factory,xmlWriter,false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,boolean serializeType) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{

			java.lang.String prefix=null;
			java.lang.String namespace=null;

			prefix=parentQName.getPrefix();
			namespace=parentQName.getNamespaceURI();

			if((namespace != null) && (namespace.trim().length() > 0)){
				java.lang.String writerPrefix=xmlWriter.getPrefix(namespace);
				if(writerPrefix != null){
					xmlWriter.writeStartElement(namespace,parentQName.getLocalPart());
				}
				else{
					if(prefix == null){
						prefix=generatePrefix(namespace);
					}

					xmlWriter.writeStartElement(prefix,parentQName.getLocalPart(),namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);
				}
			}
			else{
				xmlWriter.writeStartElement(parentQName.getLocalPart());
			}

			if(serializeType){

				java.lang.String namespacePrefix=registerPrefix(xmlWriter,"http://tempo.intalio.org/security/tokenService/");
				if((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",namespacePrefix + ":getTokenProperties",xmlWriter);
				}
				else{
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type","getTokenProperties",xmlWriter);
				}

			}

			namespace="http://tempo.intalio.org/security/tokenService/";
			if(!namespace.equals("")){
				prefix=xmlWriter.getPrefix(namespace);

				if(prefix == null){
					prefix=generatePrefix(namespace);

					xmlWriter.writeStartElement(prefix,"token",namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);

				}
				else{
					xmlWriter.writeStartElement(namespace,"token");
				}

			}
			else{
				xmlWriter.writeStartElement("token");
			}

			if(localToken == null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");

			}
			else{

				xmlWriter.writeCharacters(localToken);

			}

			xmlWriter.writeEndElement();

			xmlWriter.writeEndElement();

		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(xmlWriter.getPrefix(namespace) == null){
				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);

			}

			xmlWriter.writeAttribute(namespace,attName,attValue);

		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,java.lang.String attName,javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			java.lang.String attributeNamespace=qname.getNamespaceURI();
			java.lang.String attributePrefix=xmlWriter.getPrefix(attributeNamespace);
			if(attributePrefix == null){
				attributePrefix=registerPrefix(xmlWriter,attributeNamespace);
			}
			java.lang.String attributeValue;
			if(attributePrefix.trim().length() > 0){
				attributeValue=attributePrefix + ":" + qname.getLocalPart();
			}
			else{
				attributeValue=qname.getLocalPart();
			}

			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attributeValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			java.lang.String namespaceURI=qname.getNamespaceURI();
			if(namespaceURI != null){
				java.lang.String prefix=xmlWriter.getPrefix(namespaceURI);
				if(prefix == null){
					prefix=generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix,namespaceURI);
					xmlWriter.setPrefix(prefix,namespaceURI);
				}

				if(prefix.trim().length() > 0){
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
				else{
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}

			}
			else{
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			if(qnames != null){
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite=new java.lang.StringBuffer();
				java.lang.String namespaceURI=null;
				java.lang.String prefix=null;

				for(int i=0;i < qnames.length;i++){
					if(i > 0){
						stringToWrite.append(" ");
					}
					namespaceURI=qnames[i].getNamespaceURI();
					if(namespaceURI != null){
						prefix=xmlWriter.getPrefix(namespaceURI);
						if((prefix == null) || (prefix.length() == 0)){
							prefix=generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix,namespaceURI);
							xmlWriter.setPrefix(prefix,namespaceURI);
						}

						if(prefix.trim().length() > 0){
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
						else{
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					}
					else{
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter,java.lang.String namespace) throws javax.xml.stream.XMLStreamException{
			java.lang.String prefix=xmlWriter.getPrefix(namespace);

			if(prefix == null){
				prefix=generatePrefix(namespace);

				while(xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null){
					prefix=org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);
			}

			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName) throws org.apache.axis2.databinding.ADBException{

			java.util.ArrayList elementList=new java.util.ArrayList();
			java.util.ArrayList attribList=new java.util.ArrayList();

			elementList.add(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","token"));

			if(localToken != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localToken));
			}
			else{
				throw new org.apache.axis2.databinding.ADBException("token cannot be null!!");
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,elementList.toArray(),attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory{

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static GetTokenProperties parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				GetTokenProperties object=new GetTokenProperties();

				int event;
				java.lang.String nillableValue=null;
				java.lang.String prefix="";
				java.lang.String namespaceuri="";
				try{

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type") != null){
						java.lang.String fullTypeName=reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type");
						if(fullTypeName != null){
							java.lang.String nsPrefix=null;
							if(fullTypeName.indexOf(":") > -1){
								nsPrefix=fullTypeName.substring(0,fullTypeName.indexOf(":"));
							}
							nsPrefix=nsPrefix == null ? "" : nsPrefix;

							java.lang.String type=fullTypeName.substring(fullTypeName.indexOf(":") + 1);

							if(!"getTokenProperties".equals(type)){
								// find namespace for the prefix
								java.lang.String nsUri=reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (GetTokenProperties) ExtensionMapper.getTypeObject(nsUri,type,reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes=new java.util.Vector();

					reader.next();

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement() && new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","token").equals(reader.getName())){

						java.lang.String content=reader.getElementText();

						object.setToken(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
					}

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());

				}
				catch(javax.xml.stream.XMLStreamException e){
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	public static class PropertyType implements org.apache.axis2.databinding.ADBBean{
		/*
		 * This type was generated from the piece of schema that had name =
		 * PropertyType Namespace URI =
		 * http://tempo.intalio.org/security/tokenService/ Namespace Prefix =
		 * ns1
		 */

		private static java.lang.String generatePrefix(java.lang.String namespace){
			if(namespace.equals("http://tempo.intalio.org/security/tokenService/")){
				return "ns1";
			}
			return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
		}

		/**
		 * field for Name
		 */

		protected java.lang.String localName;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getName(){
			return localName;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Name
		 */
		public void setName(java.lang.String param){

			this.localName=param;

		}

		/**
		 * field for Value
		 */

		protected java.lang.String localValue;

		/**
		 * Auto generated getter method
		 * 
		 * @return java.lang.String
		 */
		public java.lang.String getValue(){
			return localValue;
		}

		/**
		 * Auto generated setter method
		 * 
		 * @param param
		 *            Value
		 */
		public void setValue(java.lang.String param){

			this.localValue=param;

		}

		/**
		 * isReaderMTOMAware
		 * 
		 * @return true if the reader supports MTOM
		 */
		public static boolean isReaderMTOMAware(javax.xml.stream.XMLStreamReader reader){
			boolean isReaderMTOMAware=false;

			try{
				isReaderMTOMAware=java.lang.Boolean.TRUE.equals(reader.getProperty(org.apache.axiom.om.OMConstants.IS_DATA_HANDLERS_AWARE));
			}
			catch(java.lang.IllegalArgumentException e){
				isReaderMTOMAware=false;
			}
			return isReaderMTOMAware;
		}

		/**
		 * 
		 * @param parentQName
		 * @param factory
		 * @return org.apache.axiom.om.OMElement
		 */
		public org.apache.axiom.om.OMElement getOMElement(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{

			org.apache.axiom.om.OMDataSource dataSource=new org.apache.axis2.databinding.ADBDataSource(this,parentQName){

				public void serialize(org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
					PropertyType.this.serialize(parentQName,factory,xmlWriter);
				}
			};
			return new org.apache.axiom.om.impl.llom.OMSourcedElementImpl(parentQName,factory,dataSource);

		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{
			serialize(parentQName,factory,xmlWriter,false);
		}

		public void serialize(final javax.xml.namespace.QName parentQName,final org.apache.axiom.om.OMFactory factory,org.apache.axis2.databinding.utils.writer.MTOMAwareXMLStreamWriter xmlWriter,boolean serializeType) throws javax.xml.stream.XMLStreamException,org.apache.axis2.databinding.ADBException{

			java.lang.String prefix=null;
			java.lang.String namespace=null;

			prefix=parentQName.getPrefix();
			namespace=parentQName.getNamespaceURI();

			if((namespace != null) && (namespace.trim().length() > 0)){
				java.lang.String writerPrefix=xmlWriter.getPrefix(namespace);
				if(writerPrefix != null){
					xmlWriter.writeStartElement(namespace,parentQName.getLocalPart());
				}
				else{
					if(prefix == null){
						prefix=generatePrefix(namespace);
					}

					xmlWriter.writeStartElement(prefix,parentQName.getLocalPart(),namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);
				}
			}
			else{
				xmlWriter.writeStartElement(parentQName.getLocalPart());
			}

			if(serializeType){

				java.lang.String namespacePrefix=registerPrefix(xmlWriter,"http://tempo.intalio.org/security/tokenService/");
				if((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",namespacePrefix + ":PropertyType",xmlWriter);
				}
				else{
					writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type","PropertyType",xmlWriter);
				}

			}

			namespace="http://tempo.intalio.org/security/tokenService/";
			if(!namespace.equals("")){
				prefix=xmlWriter.getPrefix(namespace);

				if(prefix == null){
					prefix=generatePrefix(namespace);

					xmlWriter.writeStartElement(prefix,"name",namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);

				}
				else{
					xmlWriter.writeStartElement(namespace,"name");
				}

			}
			else{
				xmlWriter.writeStartElement("name");
			}

			if(localName == null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("name cannot be null!!");

			}
			else{

				xmlWriter.writeCharacters(localName);

			}

			xmlWriter.writeEndElement();

			namespace="http://tempo.intalio.org/security/tokenService/";
			if(!namespace.equals("")){
				prefix=xmlWriter.getPrefix(namespace);

				if(prefix == null){
					prefix=generatePrefix(namespace);

					xmlWriter.writeStartElement(prefix,"value",namespace);
					xmlWriter.writeNamespace(prefix,namespace);
					xmlWriter.setPrefix(prefix,namespace);

				}
				else{
					xmlWriter.writeStartElement(namespace,"value");
				}

			}
			else{
				xmlWriter.writeStartElement("value");
			}

			if(localValue == null){
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException("value cannot be null!!");

			}
			else{

				xmlWriter.writeCharacters(localValue);

			}

			xmlWriter.writeEndElement();

			xmlWriter.writeEndElement();

		}

		/**
		 * Util method to write an attribute with the ns prefix
		 */
		private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(xmlWriter.getPrefix(namespace) == null){
				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);

			}

			xmlWriter.writeAttribute(namespace,attName,attValue);

		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeAttribute(java.lang.String namespace,java.lang.String attName,java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attValue);
			}
		}

		/**
		 * Util method to write an attribute without the ns prefix
		 */
		private void writeQNameAttribute(java.lang.String namespace,java.lang.String attName,javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			java.lang.String attributeNamespace=qname.getNamespaceURI();
			java.lang.String attributePrefix=xmlWriter.getPrefix(attributeNamespace);
			if(attributePrefix == null){
				attributePrefix=registerPrefix(xmlWriter,attributeNamespace);
			}
			java.lang.String attributeValue;
			if(attributePrefix.trim().length() > 0){
				attributeValue=attributePrefix + ":" + qname.getLocalPart();
			}
			else{
				attributeValue=qname.getLocalPart();
			}

			if(namespace.equals("")){
				xmlWriter.writeAttribute(attName,attributeValue);
			}
			else{
				registerPrefix(xmlWriter,namespace);
				xmlWriter.writeAttribute(namespace,attName,attributeValue);
			}
		}

		/**
		 * method to handle Qnames
		 */

		private void writeQName(javax.xml.namespace.QName qname,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
			java.lang.String namespaceURI=qname.getNamespaceURI();
			if(namespaceURI != null){
				java.lang.String prefix=xmlWriter.getPrefix(namespaceURI);
				if(prefix == null){
					prefix=generatePrefix(namespaceURI);
					xmlWriter.writeNamespace(prefix,namespaceURI);
					xmlWriter.setPrefix(prefix,namespaceURI);
				}

				if(prefix.trim().length() > 0){
					xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}
				else{
					// i.e this is the default namespace
					xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
				}

			}
			else{
				xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
			}
		}

		private void writeQNames(javax.xml.namespace.QName[] qnames,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{

			if(qnames != null){
				// we have to store this data until last moment since it is not
				// possible to write any
				// namespace data after writing the charactor data
				java.lang.StringBuffer stringToWrite=new java.lang.StringBuffer();
				java.lang.String namespaceURI=null;
				java.lang.String prefix=null;

				for(int i=0;i < qnames.length;i++){
					if(i > 0){
						stringToWrite.append(" ");
					}
					namespaceURI=qnames[i].getNamespaceURI();
					if(namespaceURI != null){
						prefix=xmlWriter.getPrefix(namespaceURI);
						if((prefix == null) || (prefix.length() == 0)){
							prefix=generatePrefix(namespaceURI);
							xmlWriter.writeNamespace(prefix,namespaceURI);
							xmlWriter.setPrefix(prefix,namespaceURI);
						}

						if(prefix.trim().length() > 0){
							stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
						else{
							stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
						}
					}
					else{
						stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
					}
				}
				xmlWriter.writeCharacters(stringToWrite.toString());
			}

		}

		/**
		 * Register a namespace prefix
		 */
		private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter,java.lang.String namespace) throws javax.xml.stream.XMLStreamException{
			java.lang.String prefix=xmlWriter.getPrefix(namespace);

			if(prefix == null){
				prefix=generatePrefix(namespace);

				while(xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null){
					prefix=org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
				}

				xmlWriter.writeNamespace(prefix,namespace);
				xmlWriter.setPrefix(prefix,namespace);
			}

			return prefix;
		}

		/**
		 * databinding method to get an XML representation of this object
		 * 
		 */
		public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName) throws org.apache.axis2.databinding.ADBException{

			java.util.ArrayList elementList=new java.util.ArrayList();
			java.util.ArrayList attribList=new java.util.ArrayList();

			elementList.add(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","name"));

			if(localName != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localName));
			}
			else{
				throw new org.apache.axis2.databinding.ADBException("name cannot be null!!");
			}

			elementList.add(new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","value"));

			if(localValue != null){
				elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localValue));
			}
			else{
				throw new org.apache.axis2.databinding.ADBException("value cannot be null!!");
			}

			return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName,elementList.toArray(),attribList.toArray());

		}

		/**
		 * Factory class that keeps the parse method
		 */
		public static class Factory{

			/**
			 * static method to create the object Precondition: If this object
			 * is an element, the current or next start element starts this
			 * object and any intervening reader events are ignorable If this
			 * object is not an element, it is a complex type and the reader is
			 * at the event just after the outer start element Postcondition: If
			 * this object is an element, the reader is positioned at its end
			 * element If this object is a complex type, the reader is
			 * positioned at the end element of its outer element
			 */
			public static PropertyType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
				PropertyType object=new PropertyType();

				int event;
				java.lang.String nillableValue=null;
				java.lang.String prefix="";
				java.lang.String namespaceuri="";
				try{

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type") != null){
						java.lang.String fullTypeName=reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type");
						if(fullTypeName != null){
							java.lang.String nsPrefix=null;
							if(fullTypeName.indexOf(":") > -1){
								nsPrefix=fullTypeName.substring(0,fullTypeName.indexOf(":"));
							}
							nsPrefix=nsPrefix == null ? "" : nsPrefix;

							java.lang.String type=fullTypeName.substring(fullTypeName.indexOf(":") + 1);

							if(!"PropertyType".equals(type)){
								// find namespace for the prefix
								java.lang.String nsUri=reader.getNamespaceContext().getNamespaceURI(nsPrefix);
								return (PropertyType) ExtensionMapper.getTypeObject(nsUri,type,reader);
							}

						}

					}

					// Note all attributes that were handled. Used to differ
					// normal attributes
					// from anyAttributes.
					java.util.Vector handledAttributes=new java.util.Vector();

					reader.next();

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement() && new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","name").equals(reader.getName())){

						java.lang.String content=reader.getElementText();

						object.setName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
					}

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement() && new javax.xml.namespace.QName("http://tempo.intalio.org/security/tokenService/","value").equals(reader.getName())){

						java.lang.String content=reader.getElementText();

						object.setValue(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));

						reader.next();

					} // End of if for expected property start element

					else{
						// A start element we are not expecting indicates an
						// invalid parameter was passed
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());
					}

					while(!reader.isStartElement() && !reader.isEndElement())
						reader.next();

					if(reader.isStartElement())
						// A start element we are not expecting indicates a
						// trailing invalid property
						throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getLocalName());

				}
				catch(javax.xml.stream.XMLStreamException e){
					throw new java.lang.Exception(e);
				}

				return object;
			}

		}// end of factory class

	}

	private org.apache.axiom.om.OMElement toOM(org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUser param,boolean optimizeContent) throws org.apache.axis2.AxisFault{

		try{
			return param.getOMElement(org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUser.MY_QNAME,org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse param,boolean optimizeContent) throws org.apache.axis2.AxisFault{

		try{
			return param.getOMElement(org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse.MY_QNAME,org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserWithCredentials param,boolean optimizeContent) throws org.apache.axis2.AxisFault{

		try{
			return param.getOMElement(org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserWithCredentials.MY_QNAME,org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenProperties param,boolean optimizeContent) throws org.apache.axis2.AxisFault{

		try{
			return param.getOMElement(org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenProperties.MY_QNAME,org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenPropertiesResponse param,boolean optimizeContent) throws org.apache.axis2.AxisFault{

		try{
			return param.getOMElement(org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenPropertiesResponse.MY_QNAME,org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.om.OMElement toOM(org.ositran.webservice.clientes.intalio.TokenServiceStub.ProxyTicket param,boolean optimizeContent) throws org.apache.axis2.AxisFault{

		try{
			return param.getOMElement(org.ositran.webservice.clientes.intalio.TokenServiceStub.ProxyTicket.MY_QNAME,org.apache.axiom.om.OMAbstractFactory.getOMFactory());
		}
		catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUser param,boolean optimizeContent) throws org.apache.axis2.AxisFault{

		try{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope=factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUser.MY_QNAME,factory));
			return emptyEnvelope;
		}
		catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserWithCredentials param,boolean optimizeContent) throws org.apache.axis2.AxisFault{

		try{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope=factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserWithCredentials.MY_QNAME,factory));
			return emptyEnvelope;
		}
		catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenProperties param,boolean optimizeContent) throws org.apache.axis2.AxisFault{

		try{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope=factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenProperties.MY_QNAME,factory));
			return emptyEnvelope;
		}
		catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory,org.ositran.webservice.clientes.intalio.TokenServiceStub.ProxyTicket param,boolean optimizeContent) throws org.apache.axis2.AxisFault{

		try{

			org.apache.axiom.soap.SOAPEnvelope emptyEnvelope=factory.getDefaultEnvelope();
			emptyEnvelope.getBody().addChild(param.getOMElement(org.ositran.webservice.clientes.intalio.TokenServiceStub.ProxyTicket.MY_QNAME,factory));
			return emptyEnvelope;
		}
		catch(org.apache.axis2.databinding.ADBException e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}

	}

	/* methods to provide back word compatibility */

	/**
	 * get the default envelope
	 */
	private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
		return factory.getDefaultEnvelope();
	}

	private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,java.lang.Class type,java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

		try{

			if(org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUser.class.equals(type)){

				return org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUser.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if(org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse.class.equals(type)){

				return org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if(org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserWithCredentials.class.equals(type)){

				return org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserWithCredentials.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if(org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse.class.equals(type)){

				return org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if(org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenProperties.class.equals(type)){

				return org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenProperties.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if(org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenPropertiesResponse.class.equals(type)){

				return org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenPropertiesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if(org.ositran.webservice.clientes.intalio.TokenServiceStub.ProxyTicket.class.equals(type)){

				return org.ositran.webservice.clientes.intalio.TokenServiceStub.ProxyTicket.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

			if(org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse.class.equals(type)){

				return org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());

			}

		}
		catch(java.lang.Exception e){
			throw org.apache.axis2.AxisFault.makeFault(e);
		}
		return null;
	}

}
