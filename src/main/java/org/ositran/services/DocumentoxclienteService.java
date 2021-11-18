/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Documentoxcliente;

public interface DocumentoxclienteService {

   public Documentoxcliente guardarObj(Documentoxcliente objDocXCli);
   
   public List<Documentoxcliente> findClientesbyIdDocumento (Integer idDocumento);
}
