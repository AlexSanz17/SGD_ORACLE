/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoPendiente;
import com.btg.ositran.siged.domain.FilaBandejaPendientesRespuesta;
import com.btg.ositran.siged.domain.Usuario;
import java.util.List;
import org.ositran.dojo.BusquedaAvanzada;

/**
 *
 * @author consultor_jti15
 */
public interface DocumentoPendienteDAO {
    public DocumentoPendiente findByIdDocumentoPendiente(Integer idDocumentoPendiente);
    public List<DocumentoPendiente> findByIdDocumento(Integer idDocumento, Usuario usuario, String estado);
    public List  buscarPendientePorUsuario(Usuario objUsuario);
     public List buscarPendientePorUsuario(Usuario objUsuario, BusquedaAvanzada objFiltro);
    public void saveDocumento(DocumentoPendiente documentoPendiente);
    public List<DocumentoPendiente> buscarPendientePorDocumentoSimple(Documento d);
    public List<DocumentoPendiente> buscarPendientePorDocumentoMultiple(Documento d);
   // public List<FilaBandejaPendientesRespuesta> buscarAtendidosPorUsuario(Usuario objUsuario);
  //  public List<FilaBandejaPendientesRespuesta> buscarAtendidosPorUsuario(Usuario objUsuario,  BusquedaAvanzada objFiltro);
}
