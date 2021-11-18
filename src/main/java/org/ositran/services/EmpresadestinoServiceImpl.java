package org.ositran.services;

import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.daos.EmpresadestinoDAO;

import com.btg.ositran.siged.domain.Empresadestino;

public class EmpresadestinoServiceImpl implements EmpresadestinoService {

   private static Logger log = Logger.getLogger(EmpresadestinoServiceImpl.class);
   private EmpresadestinoDAO dao;
   
   public Empresadestino Viewcod(String codigo){
	   List<Empresadestino> tms=getDao().findAll();
	   Empresadestino Obed=new Empresadestino();
	   Empresadestino Obedp=new Empresadestino();
		  
		  
		  
		  int tam=tms.size();
		  
		  for(int i=0;i<tam;i++){
			  Obedp=(Empresadestino)tms.get(i);
			  if (codigo.equals(Obedp.getCodigo())){
				  Obed=Obedp;
			}  
		  }
		  
		  return Obed;
   }
   public Empresadestino findkey(int Id){
	   return getDao().findkey(Id);
   }


   //////////////////////
   //getter and setters//
   /////////////////////
   
   public void setDao(EmpresadestinoDAO dao) {
	   this.dao = dao;
   }

   public EmpresadestinoDAO getDao() {
	   return dao;
   }
   
}
