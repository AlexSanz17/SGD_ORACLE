/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import com.btg.ositran.siged.domain.Courier;



public interface CourierService {

   public Courier viewcod(String codigo);
   
   public Courier findkey(int Id);
   
}
