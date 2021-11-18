package org.ositran.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
/*DUMMY*/
@Entity
public class detallesProcesoReporte {
	@Id
	@Column
	private String nombre;
	@Column
	private int cantidadexpedientes;
	@Column
	private int cantidadtotalexpedientes;
	@Column
	private long tiempototal;
	@Column
	private double tiempopromedio;
	@Column
	private double tiempopromediototal;
	@Transient
	private List<String> expedientes;
	@Transient
	private Date tiempoRecibido;
	@Transient
	private Date tiempoEnviado;

    public detallesProcesoReporte() {
    }
	
	
	public detallesProcesoReporte(String nombre){
		this.nombre=nombre;
		cantidadexpedientes=0;
		cantidadtotalexpedientes=0;
		tiempototal=0;
		expedientes = new ArrayList<String>();
	}

	public void incCantidadExpedientes() {
		this.cantidadexpedientes++;
	}
	
	public void incCantidadtotalExpedientes() {
		this.cantidadtotalexpedientes++;
	}

	public void incTiempoTotal(long tiempoTotal) {
		this.tiempototal += tiempoTotal;
	}

	public void setPromedioExpediente() {
		try{
			this.tiempopromedio = (double)(tiempototal)/(double)(cantidadexpedientes);
			
		}catch (Exception e){
			this.tiempopromedio = 0d;
		}
	}
	
	public void setPromedioAtencion() {
		try{
			this.tiempopromediototal = (double)(tiempototal)/(double)(cantidadtotalexpedientes);
			
		}catch (Exception e){
			this.tiempopromediototal = 0d;
		}
	}

	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre=nombre;
	}

	public int getCantidadexpedientes() {
		return cantidadexpedientes;
	}

	public int getCantidadtotalexpedientes() {
		return cantidadtotalexpedientes;
	}

	public String getTiempototal() {
		String tiempo="";
		int h, m;
		Long milisegundos = tiempototal;
		h=(int)(milisegundos/3600000);
		m = (int)((milisegundos%3600000)/60000);
		tiempo = Integer.toString(h)+":";
		if(m==0){
			tiempo+="00";
		}else{
			if(m<10){
				tiempo+="0"+Integer.toString(m);
			}else{
				tiempo+=Integer.toString(m);
			}
		}
		return tiempo;
	}

	public String getTiempopromedio() {
		this.setPromedioExpediente();
		String tiempo="";
		int h, m;
		Double milisegundos = tiempopromedio;
		h=(int)(milisegundos/3600000);
		m = (int)((milisegundos%3600000)/60000);
		tiempo = Integer.toString(h)+":";
		if(m==0){
			tiempo+="00";
		}else{
			if(m<10){
				tiempo+="0"+Integer.toString(m);
			}else{
				tiempo+=Integer.toString(m);
			}
		}
		return tiempo;
	}

	public String getTiempopromediototal() {
		String tiempo="";
		int h, m;
		this.setPromedioAtencion();
		Double milisegundos = tiempopromediototal;
		h=(int)(milisegundos/3600000);
		m = (int)((milisegundos%3600000)/60000);
		tiempo = Integer.toString(h)+":";
		if(m==0){
			tiempo+="00";
		}else{
			if(m<10){
				tiempo+="0"+Integer.toString(m);
			}else{
				tiempo+=Integer.toString(m);
			}
		}
		return tiempo;
	}
	
	public void addExpediente (String expediente){
		this.expedientes.add(expediente);
	}
	
	public boolean existeExpediente (String expediente){
		if(expedientes.size()>0){
			for (int i=0; i<expedientes.size();i++){
				if(expedientes.get(i).equals(expediente)){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}

	public Date getTiempoRecibido() {
		return tiempoRecibido;
	}

	public void setTiempoRecibido(Date tiempoRecibido) {
		this.tiempoRecibido = tiempoRecibido;
	}

	public Date getTiempoEnviado() {
		return tiempoEnviado;
	}

	public void setTiempoEnviado(Date tiempoEnviado) {
		this.tiempoEnviado = tiempoEnviado;
	}
}
