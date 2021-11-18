/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.DocumentoAtendido;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.FilaBandejaAtendido;
import com.btg.ositran.siged.domain.Usuario;
import java.util.List;
import org.ositran.dojo.BusquedaAvanzada;

/**
 *
 * @author consultor_jti15
 */
public interface DocumentoAtendidoDAO {
   public void saveDocumento(DocumentoAtendido documentoAtendido);
   public List<FilaBandejaAtendido> buscarAtendidosPorUsuario(Usuario objUsuario);
   public List<FilaBandejaAtendido> buscarAtendidosPorUsuario(Usuario objUsuario, BusquedaAvanzada objFiltro);
   public List<FilaBandejaAtendido> buscarAtendidosPorDocumento(Documento d);
   public DocumentoAtendido findByIdDocumentoAtendido(Integer idDocumentoAtendido);
   
   //public List<Usuario> buscarUsuariosDocumentosAtendidos(Usuario objUsuario);                 
}
