/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.rmi.RemoteException;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.ositran.utils.DocumentoDetail;
import org.ositran.webservice.clientes.intalio.InvalidInputMessageFaultException;
import org.ositran.webservice.clientes.intalio.InvalidParticipantTokenFaultException;
import org.ositran.webservice.clientes.intalio.UnavailableTaskFaultException;

import com.btg.ositran.siged.domain.Usuario;

public interface ControlDeCalidadService{
	
	public boolean aprobarDocumento(DocumentoDetail documentoDetail,Usuario calidad,Map<String,Object> sesion) throws RemoteException, InvalidInputMessageFaultException, InvalidParticipantTokenFaultException, UnavailableTaskFaultException, XMLStreamException;
	
	public boolean rechazarDocumento();

}
