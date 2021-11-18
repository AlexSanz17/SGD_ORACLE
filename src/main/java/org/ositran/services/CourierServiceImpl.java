/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.daos.CourierDAO;

import com.btg.ositran.siged.domain.Courier;

public class CourierServiceImpl implements CourierService {

   private static Logger log = Logger.getLogger(CourierServiceImpl.class);
   private CourierDAO  dao;
   
   public Courier viewcod(String codigo){
	   
	   
	   List<Courier> tms=getDao().findAll();
	   Courier Obed=new Courier();
	   Courier Obedp=new Courier();
		  
		  int tam=tms.size();
		  
		  for(int i=0;i<tam;i++){
			  Obedp=(Courier)tms.get(i);
			  if (codigo.equals(Obedp.getCodigo())){
				  Obed=Obedp;
			}  
		  }
		  
		  return Obed;
   }
   public Courier findkey(int Id){
	   return getDao().findkey(Id);
   }


   //////////////////////
   //getter and setters//
   /////////////////////
   
   public void setDao(CourierDAO dao) {
		this.dao = dao;
	}
	public CourierDAO getDao() {
		return dao;
	}  
   
}
