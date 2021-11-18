package org.ositran.utils;

public class usuariosReporte {
	private String usuario;
	private long tiempo;
	private int veces;
	private int cantidadExpedientes;
	
	public usuariosReporte(){
		
	}
	
	public usuariosReporte(String usuario, long tiempo){
		this.usuario = usuario;
		this.tiempo = tiempo;
		veces = 1;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public long getTiempo() {
		return tiempo;
	}
	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}
	public void incTiempo(long tiempo) {
		this.tiempo += tiempo;
	}
	public int getVeces() {
		return veces;
	}
	public void setVeces(int veces) {
		this.veces = veces;
	}
	public void incVeces() {
		this.veces++;
	}
	public void incVecesEn(int veces) {
		this.veces+=veces;
	}
	public String getTiempoatencion(){
		if(tiempo<0){
			return "TBA";
		}else{
			String tmp="";
			int d, h, m;
			Long milisegundos = tiempo;
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

	public int getCantidadExpedientes() {
		return cantidadExpedientes;
	}

	public void setCantidadExpedientes(int cantidadExpedientes) {
		this.cantidadExpedientes = cantidadExpedientes;
	}
	
	public void incExpedientes(){
		this.cantidadExpedientes++;
	}
}
