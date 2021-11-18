/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import java.util.List;

import org.ositran.utils.ArchivoTemporal;

import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.Campo;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Plantilla;
import com.btg.ositran.siged.domain.Usuario;

/**
 * 
 * @author Himizu
 */
public interface PlantillaService{
	
	public List<Plantilla> findAll();

	public List<Campo> listCamposByTipoPlantilla(Integer tipoPlantilla);

	public List<Plantilla> listSeccionesByTipoPlantilla(String tipoPlantilla);

	public String generarArchivo(String nombreArhivo,List<Campo> campos,String tipoPlantilla,String nombreUsuario,Integer idDocumento);

	public void saveArchivo(String nombreArchivo);

	public Plantilla findByIdplantilla(Integer idplantilla);
	
	public void registrarAuditoriaArchivo(Usuario usuario,Documento doc,Archivo archivo,String tipoauditoria,String modulo,String opcion);
	
	public void registrarAuditoriaNuevoDocumento(Usuario usuario,Documento doc);
	
	public void registrarAuditoriaArchivos(Usuario usuario,Documento doc,ArchivoTemporal archivo,String tipoauditoria,String modulo,String opcion);

	public Plantilla guardarObj(Plantilla objPlantilla);
}
