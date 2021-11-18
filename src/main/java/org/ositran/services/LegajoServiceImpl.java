/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.LegajoDocumento;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Legajo;
import com.btg.ositran.siged.domain.TipoLegajo;
import com.btg.ositran.siged.domain.Documento;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.ositran.daos.DocumentoDAO;
import org.ositran.daos.LegajoDAO;
import org.ositran.daos.LegajoDocumentoDAO;
import org.ositran.dojo.tree.NodoArbol;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jbengoa
 */
public class LegajoServiceImpl implements LegajoService{
    private LegajoDAO legajoDAO;
    private LegajoDocumentoDAO legajoDocumentoDAO;
    private DocumentoDAO documentoDAO;

    public DocumentoDAO getDocumentoDAO() {
        return documentoDAO;
    }

    public List<Legajo> findByNroLegajo(Legajo legajo){
       return  legajoDAO.findByNroLegajo(legajo);
    }
    
    public void setDocumentoDAO(DocumentoDAO documentoDAO) {
        this.documentoDAO = documentoDAO;
    }

    public LegajoDocumentoDAO getLegajoDocumentoDAO() {
        return legajoDocumentoDAO;
    }

    public void setLegajoDocumentoDAO(LegajoDocumentoDAO legajoDocumentoDAO) {
        this.legajoDocumentoDAO = legajoDocumentoDAO;
    }

    public LegajoDAO getLegajoDAO() {
        return legajoDAO;
    }

    public void setLegajoDAO(LegajoDAO legajoDAO) {
        this.legajoDAO = legajoDAO;
    }
    
    public List findLegajosUsuarioFinal(Usuario objUsuario){
        return legajoDAO.findLegajosUsuarioFinal(objUsuario);
    }
    
    public List findLegajosCompartidos(Usuario objUsuario){
        return legajoDAO.findLegajosCompartidos(objUsuario);
    }
    
     public List findByCriteria(Usuario usuario, String sNroExpediente, String sNroHT, Integer tipoDocumento, String sNroDocumento, String sTipoLegajo){
         return legajoDAO.findByCriteria(usuario, sNroExpediente, sNroHT, tipoDocumento, sNroDocumento, sTipoLegajo);
     }
     
     @Transactional
     public Legajo saveLegajo(Legajo legajo, LegajoDocumento legajoDocumento){
           legajo = legajoDAO.saveLegajo(legajo);
           
           if (legajoDocumento!=null){
             legajoDocumento.setIdLegajo(legajo.getIdLegajo());
             legajoDocumento = legajoDocumentoDAO.saveLegajoDocumento(legajoDocumento);
           }
           
           return legajo;
     }
     
     public Integer generateNroLegajoProduccion(TipoLegajo tipoLegajo){
          return legajoDAO.generateNroLegajoProduccion(tipoLegajo);
     }
     
     public List<NodoArbol> getArbolLegajo(Integer idLegajo, Integer idDocumento, String valor){
        Legajo objLegajo = new Legajo();
        objLegajo.setIdLegajo(idLegajo);
        objLegajo.setEstado("");
        objLegajo = legajoDAO.findByIdLegajo(objLegajo);
        List<NodoArbol> lstTreeDocumento = new ArrayList<NodoArbol>();
        List<NodoArbol> allElements = new ArrayList<NodoArbol>();
        List<Documento> documentos = documentos =  documentoDAO.getDocumentosPorLegajo(idLegajo);
       // boolean contieneDoc = false; 
        
        for (Documento documento : documentos) {
           	if(documento.getDocumentoreferencia() == null){
        		/*if(documento.getIdDocumento().intValue() == idDocumento.intValue()){
        			contieneDoc = true;
        		}*/
                        if(String.valueOf(documento.getEstado()).equals(String.valueOf(Constantes.ESTADO_ANULADO))){
                                NodoArbol objETDocumento = new NodoArbol(false, "D-" + documento.getIdDocumento() + "-" + (valor==null?"":valor) + "-" + UUID.randomUUID().toString().replace("-", "U2") + "-" + idLegajo, documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento() + " (Anulado)", null);
                                lstTreeDocumento.add(objETDocumento);
                        }else{
                                NodoArbol objETDocumento = new NodoArbol(false, "D-" + documento.getIdDocumento() + "-" + (valor==null?"":valor) + "-" + UUID.randomUUID().toString().replace("-", "U2") + "-" + idLegajo, documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento(), null);
                                lstTreeDocumento.add(objETDocumento);
                        }
                            
        	}
        }
        
        /*
        if(!contieneDoc){
        	Documento documento = documentoDAO.findByIdDocumento(idDocumento);
        	if(documento.getDocumentoreferencia() == null){
        		if(String.valueOf(documento.getEstado()).equals(String.valueOf(Constantes.ESTADO_ANULADO))){
                                NodoArbol objETDocumento = new NodoArbol(false, "D-" + documento.getIdDocumento() + "-" + (valor==null?"":valor), documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento() + " (Anulado)", null);
                                lstTreeDocumento.add(objETDocumento);
                        }else{
                                NodoArbol objETDocumento = new NodoArbol(false, "D-" + documento.getIdDocumento() + "-" + (valor==null?"":valor), documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento(), null);
                                    lstTreeDocumento.add(objETDocumento);
                            }
                            
        	}
        }*/
       
        NodoArbol objExpedienteTree = new NodoArbol(true, "E-" + idLegajo, objLegajo.getNroLegajo() , lstTreeDocumento); // new
        allElements.add(objExpedienteTree);
        allElements.addAll(lstTreeDocumento);
        return allElements;
     }
     
     public Legajo findByIdLegajo(Legajo legajo){
         return legajoDAO.findByIdLegajo(legajo);
     }
     
}
