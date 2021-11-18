/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Documentofedateado;

  public interface DocumentoFedatarioDAO {

	   public Documentofedateado registrar(Documentofedateado objDoc) throws Exception;
	   public List<Documentofedateado> buscarLstDocumentoFedateado(Integer idUsuario, String fecInferior , String fecSuperior, String servicio);

}
