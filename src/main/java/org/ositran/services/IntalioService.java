package org.ositran.services;


import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.ositran.webservice.clientes.intalio.InvalidInputMessageFaultException;
import org.ositran.webservice.clientes.intalio.InvalidParticipantTokenFaultException;
import org.ositran.webservice.clientes.intalio.UnavailableTaskFaultException;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Intalio;
import com.btg.ositran.siged.domain.Usuario;

public interface IntalioService{
	
	public boolean iniciarProceso(Usuario iniciante,Usuario receptor,String proceso,int idExpediente) throws RemoteException,InvalidInputMessageFaultException, InvalidParticipantTokenFaultException, UnavailableTaskFaultException, XMLStreamException;
	
	public String getAjaxFormURL(Usuario usuario,int idExpediente);

	public List<Intalio> listaProcesos(Usuario usuario)throws RemoteException, InvalidInputMessageFaultException, InvalidParticipantTokenFaultException, UnavailableTaskFaultException, UnsupportedEncodingException;
	
	public Integer crearExpediente(Usuario usuario,String proceso) throws Exception;
}
