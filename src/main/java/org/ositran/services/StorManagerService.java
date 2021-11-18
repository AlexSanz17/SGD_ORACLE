/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ositran.services;

import java.util.List;
import java.util.Map;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoStor;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Motivo;
import com.btg.ositran.siged.domain.Resolucionjaru;
import com.btg.ositran.siged.domain.Submotivo;
import com.btg.ositran.siged.domain.Suministro;
import com.btg.ositran.siged.domain.Usuario;

/**
 * 
 * @author Usuario
 */
public interface StorManagerService{

	public Usuario findUserSCalificador();

	public List<Suministro> getListSuministroXExpediente(Integer idExpediente);

	public List<Submotivo> getListSubmotivosXExpediente(Integer idExpediente);

	public List<Motivo> getListMotivosxExpediente(Integer idExpediente);

	public void updateDatosExpediente(Integer idExpediente,Integer idSubMotivo,String monto);

	public void updateDatosExpediente(Integer idExpediente,Integer[] listIdSubMotivos,Integer[] listNewIdSubMotivos,String monto,String observacion);

	public void uploadArchivoToAlfresco(Documento objDocumento,Map mapUpload);

	public Documento changeDocumentoPrincipal(Expediente expediente,Usuario destinatario,String asunto,String accion);

	public Documento createDocumento(Expediente expediente,Usuario destinatario,String asunto,String accion,char principal);

	public void updateEstadoDocumentoPrincipal(Documento documento,char estado);

	public DocumentoStor getDocumentoStorByExpediente(Integer idExpediente);

	public void updateResolucionJaru(Resolucionjaru resolucionjaru);
}
