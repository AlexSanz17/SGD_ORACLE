/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Documentoxcliente;

public interface DocumentoxclienteDAO {

   public Documentoxcliente guardarObj(Documentoxcliente objDocXCli);

   public List<Documentoxcliente> findClientesbyIdDocumento(Integer idDocumento);
}
