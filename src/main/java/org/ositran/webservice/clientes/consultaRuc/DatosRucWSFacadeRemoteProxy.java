package org.ositran.webservice.clientes.consultaRuc;

public class DatosRucWSFacadeRemoteProxy implements org.ositran.webservice.clientes.consultaRuc.DatosRucWSFacadeRemote {
  private String _endpoint = null;
  private org.ositran.webservice.clientes.consultaRuc.DatosRucWSFacadeRemote datosRucWSFacadeRemote = null;
  
  public DatosRucWSFacadeRemoteProxy() {
    _initDatosRucWSFacadeRemoteProxy();
  }
  
  public DatosRucWSFacadeRemoteProxy(String endpoint) {
    _endpoint = endpoint;
    _initDatosRucWSFacadeRemoteProxy();
  }
  
  private void _initDatosRucWSFacadeRemoteProxy() {
    try {
      datosRucWSFacadeRemote = (new org.ositran.webservice.clientes.consultaRuc.DatosRucWSFacadeRemoteServiceLocator()).getConsultaRuc();
      if (datosRucWSFacadeRemote != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)datosRucWSFacadeRemote)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)datosRucWSFacadeRemote)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (datosRucWSFacadeRemote != null)
      ((javax.xml.rpc.Stub)datosRucWSFacadeRemote)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.ositran.webservice.clientes.consultaRuc.DatosRucWSFacadeRemote getDatosRucWSFacadeRemote() {
    if (datosRucWSFacadeRemote == null)
      _initDatosRucWSFacadeRemoteProxy();
    return datosRucWSFacadeRemote;
  }
  
  public org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp getDatosPrincipales(java.lang.String in0) throws java.rmi.RemoteException{
    if (datosRucWSFacadeRemote == null)
      _initDatosRucWSFacadeRemoteProxy();
    return datosRucWSFacadeRemote.getDatosPrincipales(in0);
  }
  
  public java.lang.String getDomicilioLegal(java.lang.String in0) throws java.rmi.RemoteException{
    if (datosRucWSFacadeRemote == null)
      _initDatosRucWSFacadeRemoteProxy();
    return datosRucWSFacadeRemote.getDomicilioLegal(in0);
  }
  
  public org.ositran.webservice.clientes.consultaRuc.beans.BeanT1144 getDatosT1144(java.lang.String in0) throws java.rmi.RemoteException{
    if (datosRucWSFacadeRemote == null)
      _initDatosRucWSFacadeRemoteProxy();
    return datosRucWSFacadeRemote.getDatosT1144(in0);
  }
  
  public org.ositran.webservice.clientes.consultaRuc.beans.BeanDds getDatosSecundarios(java.lang.String in0) throws java.rmi.RemoteException{
    if (datosRucWSFacadeRemote == null)
      _initDatosRucWSFacadeRemoteProxy();
    return datosRucWSFacadeRemote.getDatosSecundarios(in0);
  }
  
  public org.ositran.webservice.clientes.consultaRuc.beans.BeanRso[] getRepLegales(java.lang.String in0) throws java.rmi.RemoteException{
    if (datosRucWSFacadeRemote == null)
      _initDatosRucWSFacadeRemoteProxy();
    return datosRucWSFacadeRemote.getRepLegales(in0);
  }
  
  public org.ositran.webservice.clientes.consultaRuc.beans.BeanSpr[] getEstablecimientosAnexos(java.lang.String in0) throws java.rmi.RemoteException{
    if (datosRucWSFacadeRemote == null)
      _initDatosRucWSFacadeRemoteProxy();
    return datosRucWSFacadeRemote.getEstablecimientosAnexos(in0);
  }
  
  public org.ositran.webservice.clientes.consultaRuc.beans.BeanT1150[] getEstAnexosT1150(java.lang.String in0) throws java.rmi.RemoteException{
    if (datosRucWSFacadeRemote == null)
      _initDatosRucWSFacadeRemoteProxy();
    return datosRucWSFacadeRemote.getEstAnexosT1150(in0);
  }
  
  public org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp[] buscaRazonSocial(java.lang.String in0) throws java.rmi.RemoteException{
    if (datosRucWSFacadeRemote == null)
      _initDatosRucWSFacadeRemoteProxy();
    return datosRucWSFacadeRemote.buscaRazonSocial(in0);
  }
  
  public org.ositran.webservice.clientes.consultaRuc.beans.BeanT362[] getDatosT362(java.lang.String in0) throws java.rmi.RemoteException{
    if (datosRucWSFacadeRemote == null)
      _initDatosRucWSFacadeRemoteProxy();
    return datosRucWSFacadeRemote.getDatosT362(in0);
  }
  
  
}