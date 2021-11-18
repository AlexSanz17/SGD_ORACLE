/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.reporte;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import org.apache.commons.beanutils.ConversionException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class LBTRUtil {
	
	private static Logger logger=Logger.getLogger(LBTRUtil.class);  
	
    public static String getFormatoFechayHora(Timestamp fecha){
    	String fechaS="";
    	
    	if (fecha==null) return fechaS; 
    	
    	String anio = String.valueOf(fecha).substring(0, 4);
    	String mesA = String.valueOf(fecha).substring(5, 7);
    	String diaA = String.valueOf(fecha).substring(8,10);
    	String hora = String.valueOf(fecha).substring(10,16);

    	fechaS = diaA + "/" + mesA + "/" + anio + " "+ hora;

    	return fechaS;
    }
    
    public static Timestamp stringToTimeStamp(String dateStr, String pattern) throws ConversionException {
        Timestamp  date = new Timestamp(System.currentTimeMillis()); 
        if (dateStr == null || dateStr.trim().equals("")) {
            throw new ConversionException("Invalid value for parameter dateStr");
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        
            try {
            	logger.debug(" *************** dateStr  "+dateStr+  " pattern "+pattern) ;    
				Date date2 = formatter.parse(dateStr);
				date.setTime(date2.getTime());                
				
			} catch (java.text.ParseException e) {
				  
				e.printStackTrace();
			}
        
       
        return date;
    }
    
	public static void ExportExcel(HSSFWorkbook wb , HttpServletResponse response,
			String filename) {
		try {     
			  response.reset(); response.setContentType("application/excel");
			  response
			  .setHeader("Content-Disposition","attachment; filename="+filename+".xls"); response.setHeader("Pragma","no-cache");
			  response.setHeader("Cache-control", "no-cache");
			  response.setHeader("Cache-Control", "no-store");
			  wb.write(response.getOutputStream());
			  response.getOutputStream().flush();
			  response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public static String getFormatHora(Timestamp fecha){
    	String hora="";
    	
    	if (fecha==null) return hora; 
    	
    	hora = String.valueOf(fecha).substring(10,16);

    	return hora;
    }
    
    public static Double parseDouble(String valorConFormato) throws ParseException{
    	DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        DecimalFormat formatter = new DecimalFormat();
        formatter.setDecimalFormatSymbols(symbols);
        Number nu = formatter.parse(valorConFormato);
        return nu.doubleValue();
    }
    
    public static String formatString(Double d){
    	DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###,###,###,###,##0.00");
        formatter.setDecimalFormatSymbols(symbols);
        return  formatter.format(d);
    }
    
    public static java.util.Date stringToDate(String dateStr, String pattern) throws ConversionException {
        java.util.Date date = null;
        if (dateStr == null || dateStr.trim().equals("")) {
            throw new ConversionException("Invalid value for parameter dateStr");
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        
            try {
				date = formatter.parse(dateStr);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
       
        return date;
    }

    public static String formatDouble(Double d){
		String strFormat="";
		//NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
		DecimalFormat df = (DecimalFormat)nf;
		//df.applyPattern("#########0.00");
		df.applyPattern("#,###,###,###,###,##0.00");
		//DecimalFormat df=new DecimalFormat("#,###,###,##0.00"); 
		try{
			strFormat=df.format(d.doubleValue());
		}catch(Exception ignore){
			strFormat="";
		}
		return strFormat;
  }
    public static String formatBigDecimalToString(BigDecimal d){
 		String strFormat="";
		//NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
		DecimalFormat df = (DecimalFormat)nf;
		//df.applyPattern("#########0.00");
		df.applyPattern("#,###,###,###,###,##0.00");
		//DecimalFormat df=new DecimalFormat("#,###,###,##0.00"); 
		try{
			strFormat=df.format(d.doubleValue());
		}catch(Exception ignore){
			strFormat="";
		}
		return strFormat;
  }

    public static String getXMLGregorianToFormatoString(String fecha){
    	//2009-06-14T11:45:17.637   XMLGregorianCalendar
    	StringTokenizer tokens=new StringTokenizer(fecha, "-");
    	String date="";
             String str1=tokens.nextToken();
             String str2=tokens.nextToken();
             String str3=tokens.nextToken();
             String hora=str3.substring(4,8);
             String str4=str3.substring(0, 2);
             
             date=str4+"/"+str2+"/"+str1+"/"+hora;
             
        return date;
         
    }
    

    public static List getDias(String i){
    		ArrayList<ListParametros> lista = new ArrayList<ListParametros>();
    		int cod= Integer.parseInt(i);
    		switch(cod){
    		case 1: 
    			for(int x=0;x<=10;x++){
    				ListParametros listParametros=new ListParametros();
    				listParametros.setKey(x);
    				listParametros.setValue(x);
    				lista.add(listParametros);
    			}
    			break;
       		case 2: 
    			for(int x=0;x<=30;x++){
    				ListParametros listParametros=new ListParametros();
       				listParametros.setKey(x);
    				listParametros.setValue(x);
    				lista.add(listParametros);
    			}
    			break;
    		case 3: 
    			for(int x=0;x<=90;x++){
    				ListParametros listParametros=new ListParametros();
       				listParametros.setKey(x);
    				listParametros.setValue(x);
    				lista.add(listParametros);
    			}
    			break;

    		default  : 
    			ListParametros listParametros = new ListParametros();
    			listParametros.setKey(0);listParametros.setValue(null);
    			lista.add(listParametros);
    		} 
    		return lista;
    }		 
    
    public static String getIp(String cadena){
    	
    	StringTokenizer tokens=new StringTokenizer(cadena, ":");
          int i=1;
            String cc="";
            while(tokens.hasMoreTokens()){
                String str=tokens.nextToken();
                System.out.println(str);
                i++;
                if(i==3){
                	cc=tokens.nextToken();
                }
            }
            String ip="";
            StringTokenizer tokens2=new StringTokenizer(cc, ";");
            ip=tokens2.nextToken();
            
          return ip;
    }
    public static String getFecha(String fecha){
    	StringTokenizer tokens=new StringTokenizer(fecha, "-");
    	String date="";
             String str1=tokens.nextToken();
             String str2=tokens.nextToken();
             String str3=tokens.nextToken();
             
             date=str3+"/"+str2+"/"+str1;
             
        return date;
         
    }

    public static String getFechaSubstring(Timestamp fecha){
	   
	  String fechaString=String.valueOf(fecha).substring(0,10);
	  return fechaString;
	   
   } 
   public static String getFechaSubstringFormat(Timestamp fecha){
	   
		  String fechaString=String.valueOf(fecha).substring(0,10);
		  StringTokenizer tokens=new StringTokenizer(fechaString,"-");
	    	String date="";
	             String str1=tokens.nextToken();
	             String str2=tokens.nextToken();
	             String str3=tokens.nextToken();
	             
	             date=str3.concat("/").concat(str2).concat("/").concat(str1);
	             
		  return date;
		   
  } 

    
   public static String validarCaracteres(String cadena){
	   
	   logger.debug("cadena validar"+cadena);
	   
	   int cad=cadena.length();
	   String[] mayuscula={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","�","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	   String[] minusculas={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","�","o","p","q","r","s","t","v","w","x","y","z"};
	   String[] numero={"0","1","2","3","4","5","6","7","8","9"};
	   
	   String mayus="";
	   String min="";
	   String num="";
	   
	   String valida="";
	   
	   if(cad>=8){
		   for(int i=0;i<cad;i++){
			   for(int x=0; x < mayuscula.length; x++){
				   if((String.valueOf(cadena.charAt(i))).equals(mayuscula[x].toString())){
					   mayus="1";
					   logger.debug("mayusculas "+mayus);
				   }
			   }
			   for(int x=0; x < minusculas.length; x++){
				   if((String.valueOf(cadena.charAt(i))).equals(minusculas[x].toString())){
					   min="1";
					   logger.debug("minusculas "+min);
				   }
			   }
			   for(int x=0; x < numero.length; x++){
				   if((String.valueOf(cadena.charAt(i))).equals(numero[x].toString())){
					   num="1";
					   logger.debug("numeros "+num);
				   }
			   }
		   }
	   }
	   
	   if(mayus!="" || min!="" || num!=""){
		   valida="1";
	   }
	   return valida;
   } 
   
   
}