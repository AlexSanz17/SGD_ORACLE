/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.Legajo;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.TipoLegajo;
import java.util.List;

/**
 *
 * @author jbengoa
 */
public interface LegajoDAO {
     public List findLegajosUsuarioFinal(Usuario objUsuario);
     public List findLegajosCompartidos(Usuario objUsuario);
     public List findByCriteria(Usuario usuario, String sNroExpediente, String sNroHT, Integer tipoDocumento, String sNroDocumento, String sTipoLegajo);
     public Legajo saveLegajo(Legajo legajo);
     public Integer generateNroLegajoProduccion(TipoLegajo tipoLegajo);
     public Legajo findByIdLegajo(Legajo legajo);
     public List<Legajo> findByNroLegajo(Legajo legajo);
}
