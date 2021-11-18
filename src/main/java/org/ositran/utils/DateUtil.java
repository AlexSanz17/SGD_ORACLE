package org.ositran.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	private static Calendar c;
	
	static{
		c=Calendar.getInstance();
	}
	
	public static final String getDia(Date fecha){
		c.setTime(fecha);
		switch (c.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:return "Lunes";
		case Calendar.TUESDAY:return "Martes";
		case Calendar.WEDNESDAY:return "Miercoles";
		case Calendar.THURSDAY:return "Jueves";
		case Calendar.FRIDAY:return "Viernes";
		case Calendar.SATURDAY:return "Sabado";
		case Calendar.SUNDAY:return "Domingo";
		default:return null;
		}
	}
	
	public static final int getDiadeMes(Date fecha){
		c.setTime(fecha);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	public static final String getMes(Date fecha){
		c.setTime(fecha);
		switch (c.get(Calendar.MONTH)) {
		case Calendar.JANUARY:return "Enero";
		case Calendar.FEBRUARY:return "Febrero";
		case Calendar.MARCH:return "Marzo";
		case Calendar.APRIL:return "Abril";
		case Calendar.MAY:return "Mayo";
		case Calendar.JUNE:return "Junio";
		case Calendar.JULY:return "Julio";
		case Calendar.AUGUST:return "Agosto";
		case Calendar.SEPTEMBER:return "Setiembre";
		case Calendar.OCTOBER:return "Octubre";
		case Calendar.NOVEMBER:return "Noviembre";
		case Calendar.DECEMBER:return "Diciembre";
		default:return null;
		}
	}
	
	public static final int getAnio(Date fecha){
		c.setTime(fecha);
		return c.get(Calendar.YEAR);
	}
	
	public static final String getHora(Date fecha){
		c.setTime(fecha);
		return c.get(Calendar.HOUR_OF_DAY)+":"+(c.get(Calendar.MINUTE)<10?"0"+c.get(Calendar.MINUTE):c.get(Calendar.MINUTE));
	}
	
	public static final Date getInicioSemana(Date fecha){
		c.setTime(fecha);
		
		if(c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
			return fecha;
		}
		
		int amount = Calendar.MONDAY - c.get(Calendar.DAY_OF_WEEK);
		c.add(Calendar.DAY_OF_WEEK, amount);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		
		return c.getTime();
	}
	
	public static final Date getFinSemana(Date fecha){
		c.setTime(fecha);
		
		if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			return fecha;
		}
		
		int amount = 7 - (c.get(Calendar.DAY_OF_WEEK) -1);
		c.add(Calendar.DAY_OF_WEEK, amount);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		
		return c.getTime();
	}
	
	public static final String getFormatoFecha(Date fecha){
		c.setTime(fecha);
		
		return getDia(fecha) + " " + c.get(Calendar.DAY_OF_MONTH) + " de " + getMes(fecha);
	}
	
	public static final boolean compararDiasFecha(Date fechaA, Date fechaB){
		if(fechaA == null || fechaB == null){
			return false;
		}
		
		SimpleDateFormat fechita = new SimpleDateFormat("ddMMyyyy");
		return fechita.format(fechaA).equals(fechita.format(fechaB));
	}
	
	public static String milisegundosADias(Long total){
		String tmp="";
		int d, h, m;
		Long milisegundos = total;
		h=(int)(milisegundos/3600000);
		m = (int)((milisegundos%3600000)/60000);
		if(h>24){
			d = h/24;
			h=h%24;
			tmp = Integer.toString(d)+"d ";
		}
		tmp += Integer.toString(h)+":";
		if(m==0){
			tmp+="00";
		}else{
			if(m<10){
				tmp+="0"+Integer.toString(m);
			}else{
				tmp+=Integer.toString(m);
			}
		}
		if(tmp.equals("0:00")){
			tmp = Long.toString((milisegundos%60000)/1000)+"s";
		}
		return tmp;
	}
}
