package org.ositran.services;

import com.btg.ositran.siged.domain.Empresadestino;
import com.btg.ositran.siged.domain.Tipoenvio;



public interface EmpresadestinoService {

   public Empresadestino Viewcod(String codigo);
   
   public Empresadestino findkey(int Id);
   
}
