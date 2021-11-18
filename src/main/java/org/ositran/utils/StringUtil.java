package org.ositran.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringUtil {

    private static Log log=LogFactory.getLog(StringUtil.class);

   public static boolean isEmpty(String obj) {
      if ((obj == null) || (obj.trim().length() == 0)) {
         return true;
      } else {
         return false;
      }
   }
     
   public static String trimEmptyLines(String obj ) {
		  StringBuilder result = new StringBuilder("");
	      if ((obj != null)) {
	    	 if(obj.contains("\n")) {
	    		 String [] lines = obj.split("\n");
	    		 boolean first =true ;
	    		 for (String line :lines){
	    			 log.debug("line:"+line);
	    			 if(!first){
	    				 line+="\n";
	    			 }else {
	    				 first=false;
	    			 }
	    			 
	    			 if(StringUtils.isNotBlank(line) ){
	    				 result.append(line.trim());
	    			 }
	    			 
	    		 }
	    		 return result.toString() ;
	    	  }else {
	    		  return obj ;    
	    	  }
	      } else {
	    	  return result.toString();
	      }
	   }
   
   /**REN Convierte un String de la forma "[x,x,x,x ... ]" a un arreglo --------------------------------------------------------*/
   
    public static String[] stringToArrayPersonalizado (String cadena, char caracter){
	   if(cadena != null && cadena.length() > 0){
		   List<String> salida = new ArrayList<String>();
		   String elemento = "";
		   char[] entrada = cadena.toCharArray();
		   for(char token : entrada){
			   if(token == caracter){
				   salida.add(elemento);
				   elemento = "";
			   }else{
				   elemento += token;
			   }
		   }
		   salida.add(elemento);
		   return salida.toArray(new String[0]);
	   }
	   return null;
   }
   
   
   public static String[] stringToArray (String cadena){
	   if(cadena != null && cadena.length() > 0){
		   List<String> salida = new ArrayList<String>();
		   String elemento = "";
		   char[] entrada = cadena.toCharArray();
		   for(char token : entrada){
			   if(token == ','){
				   salida.add(elemento);
				   elemento = "";
			   }else{
				   elemento += token;
			   }
		   }
		   salida.add(elemento);
		   return salida.toArray(new String[0]);
	   }
	   return null;
   }
}
