/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.ArchivoPendiente;

/**
 *
 * @author Himizu
 */
@Repository
public interface ArchivopendienteDAO {
    
public List<ArchivoPendiente> findAll(); 
public void saveArchivopendiente(ArchivoPendiente archivopendiente)  ;
public List<ArchivoPendiente> findByIdusuario(Integer idusuario) ;
public ArchivoPendiente findByIdarchivopendiente(Integer idArchivopendiente) ;
public void deleteArchivopendiente(Integer idArchivoPendiente );
 public void deleterchildren(Integer idarchivopendiente );
public EntityManager getEm();
public void refresh (ArchivoPendiente archivopendiente) ; 
@PersistenceContext(unitName="sigedPU")
public void setEm(EntityManager em);
}
