/**
 * DatosRucWSFacadeRemote.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.ositran.webservice.clientes.consultaRuc;

public interface DatosRucWSFacadeRemote extends java.rmi.Remote {
    public org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp getDatosPrincipales(java.lang.String in0) throws java.rmi.RemoteException;
    public java.lang.String getDomicilioLegal(java.lang.String in0) throws java.rmi.RemoteException;
    public org.ositran.webservice.clientes.consultaRuc.beans.BeanT1144 getDatosT1144(java.lang.String in0) throws java.rmi.RemoteException;
    public org.ositran.webservice.clientes.consultaRuc.beans.BeanDds getDatosSecundarios(java.lang.String in0) throws java.rmi.RemoteException;
    public org.ositran.webservice.clientes.consultaRuc.beans.BeanRso[] getRepLegales(java.lang.String in0) throws java.rmi.RemoteException;
    public org.ositran.webservice.clientes.consultaRuc.beans.BeanSpr[] getEstablecimientosAnexos(java.lang.String in0) throws java.rmi.RemoteException;
    public org.ositran.webservice.clientes.consultaRuc.beans.BeanT1150[] getEstAnexosT1150(java.lang.String in0) throws java.rmi.RemoteException;
    public org.ositran.webservice.clientes.consultaRuc.beans.BeanDdp[] buscaRazonSocial(java.lang.String in0) throws java.rmi.RemoteException;
    public org.ositran.webservice.clientes.consultaRuc.beans.BeanT362[] getDatosT362(java.lang.String in0) throws java.rmi.RemoteException;
}
