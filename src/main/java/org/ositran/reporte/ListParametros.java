/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.reporte;

import java.io.Serializable;


public class ListParametros implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer  key;  
    
	private Integer value;

	private String other;

	public ListParametros(Integer key, Integer value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public ListParametros(Integer key, Integer value, String other) {
		super();
		this.key = key;
		this.value = value;
		this.other = other;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public ListParametros() {
		
	}

}
