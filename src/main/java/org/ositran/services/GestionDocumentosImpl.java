package org.ositran.services;

import java.util.List;
import java.util.Map;

import org.ositran.services.property.GestionDocumentosProperty;
import org.ositran.utils.Constantes;
import org.ositran.utils.DocumentoDetail;
import org.ositran.utils.DocumentoMasivo;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Usuario;

public class GestionDocumentosImpl  extends GestionDocumentosProperty implements GestionDocumentos{
/*
	public boolean derivarMasivamente(DocumentoMasivo docmas, String nombrePC,Boolean horarioPermitido , Boolean horarioPermitidoRecepcion){
		boolean forward=false;
		//getLog().debug("Accion Aprobar tiene  ID=[{}]",+objDD.getAccion().getIdAccion());
		getLog().debug("Destinatario=[{}]"+docmas.getDestinatario().getIdusuario());
		
		//Recorremos los documentos seleccionado
        for (int i = 0; i < docmas.getArrIdDoc().length; i++) {

        	Documento objDocSeleccionado = getDocumentoService().findByIdDocumento(docmas.getArrIdDoc()[i]);
           
           //Aprobacion
           if(objDocSeleccionado.getAccion().getNombre().equals(Constantes.ACCION_PARA_APROBAR)){
        	   
        	   boolean flag=evaluarDestinatarios(objDocSeleccionado.getPropietario().getIdusuario(),docmas.getDestinatario().getIdusuario());
        	   //Si es al mismo propietario del documento
        	   if(flag){
        		   aprobarDocumento(docmas, objDocSeleccionado, nombrePC,horarioPermitido, horarioPermitidoRecepcion);
        	   }
        	   // si es otro propietario
        	   else{
        		    //SI selecciono check Para Aprobar
		       	   	if(docmas.isParaAprobar()){
		       	   		apruebaYparaAprobarDocumentos(docmas, objDocSeleccionado, nombrePC,horarioPermitido, horarioPermitidoRecepcion);
		    	   	}
		       	   	//NO selecciono check Para Aprobar
		       	   	else{
		       	   	     reenviarDocumento(docmas,objDocSeleccionado, nombrePC,horarioPermitido, horarioPermitidoRecepcion);
		       	   		//aprobarDocumento(docmas, objDocSeleccionado, nombrePC,horarioPermitido, horarioPermitidoRecepcion);
		    	   	}        		   
        	   }
           }
           //Reenvios
           else{
        	    //SI selecciono check Aprobar
        	   	if(docmas.isParaAprobar()){
        	   		reenviarParaAprobarDocumento(docmas, objDocSeleccionado, nombrePC,horarioPermitido, horarioPermitidoRecepcion);
        	   	}
        	    //NO selecciono check Aprobar
        	   	else{
        	   		reenviarDocumento(docmas,objDocSeleccionado, nombrePC,horarioPermitido, horarioPermitidoRecepcion);
        	   	}   
           }
        }
        
      return forward;
	}
*/

}
