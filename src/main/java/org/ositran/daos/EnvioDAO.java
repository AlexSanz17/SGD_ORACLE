/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Envio;


public interface EnvioDAO {

   public Envio saveDatos(Envio Obdt);
   
   public Envio findkey(int Id);
   
   public List<Envio> findall();

}
