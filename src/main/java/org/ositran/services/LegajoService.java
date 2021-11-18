/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.Legajo;
import com.btg.ositran.siged.domain.LegajoDocumento;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.TipoLegajo;
import java.util.List;
import org.ositran.dojo.tree.NodoArbol;

/**
 *
 * @author jbengoa
 */
public interface LegajoService {
     public List findLegajosUsuarioFinal(Usuario objUsuario);
     public List findLegajosCompartidos(Usuario objUsuario);
     public List findByCriteria(Usuario usuario, String sNroExpediente, String sNroHT, Integer tipoDocumento, String sNroDocumento, String sTipoLegajo);
     public Legajo saveLegajo(Legajo legajo, LegajoDocumento legajoDocumento);
     public Integer generateNroLegajoProduccion(TipoLegajo tipoLegajo);
     public List<NodoArbol> getArbolLegajo(Integer idLegajo, Integer idDocumento, String valor); 
     public Legajo findByIdLegajo(Legajo legajo);
     public List<Legajo> findByNroLegajo(Legajo legajo);
}
