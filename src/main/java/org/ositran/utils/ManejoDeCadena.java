package org.ositran.utils;

public class ManejoDeCadena {

    public static String[] parsearCadenaID(String cadena,String total)
    {
		//String cadena="id0=4|id1=5|id2=6|id3=7|id4=45|";
		//String total="5";
        
		String Resultado[]=new String[Integer.parseInt(total)];
		
		int x=0;
		int temp=0;
		
		for(int i=0;i<cadena.length();i++)
		{
			if(cadena.substring(i, i+1).equals("|"))
			{
				String cd=cadena.substring(temp,i);
				
				for(int j=0;j<cd.length();j++)
				{
					Resultado[x]=cd.substring(j,j+1);
				}
				temp=i+1;
				x++;
			}
		}
        return Resultado;
    }

	public static String cortarCAD(String cad1,int max){
		String cad="";
		  if(cad1.length()>max){ cad=cad1.substring(0, max);}
		else{cad=cad1;}
		return cad;
	}

}
