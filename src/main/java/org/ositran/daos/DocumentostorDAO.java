/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.DocumentoStor;

public interface DocumentostorDAO{

	public DocumentoStor findByIdDocumentoStor(Integer iIdDS);

	public void saveDocumentoStor(DocumentoStor objDS);

	public void updateDocumentoStor(DocumentoStor objDS);

	public DocumentoStor findByIdExpediente(Integer idExpediente);
}
