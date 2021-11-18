/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.ArchivoPendiente;
import com.btg.ositran.siged.domain.ArchivoTemporal;
import com.btg.ositran.siged.domain.Valorcampo;

/**
 * 
 * @author Himizu
 */
@Transactional
public interface ArchivopendienteService{

	public void saveArchivopendiente(ArchivoPendiente archivopendiente,List<ArchivoTemporal> archivosTemporales,List<Valorcampo> valoresCampo);

	public List<ArchivoPendiente> findByIdusuario(Integer idusuario);

	public ArchivoPendiente findByIdarchivopendiente(Integer idArchivopendiente);

	public void deleteArchivopendiente(Integer idArchivoPendiente);

	public void deleterchildren(Integer idarchivopendiente);
}
