/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Courier;


public interface CourierDAO {

   public Courier findCod(String codigo);
   
   public List<Courier> findAll();
   
   public Courier findkey(int Id);

}
