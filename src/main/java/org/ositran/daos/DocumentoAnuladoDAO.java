/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.DocumentoAnulado;
import com.btg.ositran.siged.domain.FilaBandejaAnulado;
import com.btg.ositran.siged.domain.Usuario;
import java.util.List;
import org.ositran.dojo.BusquedaAvanzada;

/**
 *
 * @author consultor_jti15
 */
public interface DocumentoAnuladoDAO {
   public void saveDocumento(DocumentoAnulado documentoAnulado);
   public List<FilaBandejaAnulado> buscarAnuladosPorUsuario(Usuario objUsuario);
   public List<FilaBandejaAnulado> buscarAnuladosPorUsuario(Usuario objUsuario, BusquedaAvanzada objFiltro);
   public DocumentoAnulado findByIdDocumentoAnulado(Integer idDocumentoAnulado);
}
