/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.dojo.grid;

public class Estructura{

	private int idGridColumnaPorGrid;
	private String field;
	private String name;
	private Integer noresize;
	private String width;
	private Integer hidden;
	private String formato;
	private boolean ordenado;
	private boolean ascendente;

	// ////////////////////////////////
	// Constructors //
	// ////////////////////////////////
	public Estructura(){}

	public Estructura(int id,String sField,String sName,Integer iNoResize,String sWidth,Integer iHidden,String sFormatter,boolean ordenado,boolean ascendente){
		idGridColumnaPorGrid=id;
		this.field=sField;
		this.name=sName;
		this.noresize=iNoResize;
		this.width=sWidth;
		this.hidden=iHidden;
		this.formato=sFormatter;
		this.ordenado=ordenado;
		this.ascendente=ascendente;
	}

	public Estructura(int id,String sFormatter,Integer iHidden,Integer iNoResize,String sField,String sName,String sWidth){
		idGridColumnaPorGrid=id;
		this.formato=sFormatter;
		this.hidden=iHidden;
		this.noresize=iNoResize;
		this.field=sField;
		this.name=sName;
		this.width=sWidth;
	}

	// ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public String getField(){
		return field;
	}

	public void setField(String field){
		this.field=field;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name=name;
	}

	public Integer getNoresize(){
		return noresize;
	}

	public void setNoresize(Integer noresize){
		this.noresize=noresize;
	}

	public String getWidth(){
		return width;
	}

	public void setWidth(String width){
		this.width=width;
	}

	public Integer getHidden(){
		return hidden;
	}

	public void setHidden(Integer hidden){
		this.hidden=hidden;
	}

	public String getFormato(){
		return formato;
	}

	public void setFormato(String formato){
		this.formato=formato;
	}

	public int getIdGridColumnaPorGrid(){
		return idGridColumnaPorGrid;
	}

	public void setIdGridColumnaPorGrid(int idGridColumnaPorGrid){
		this.idGridColumnaPorGrid=idGridColumnaPorGrid;
	}

	public boolean isOrdenado(){
		return ordenado;
	}

	public void setOrdenado(boolean ordenado){
		this.ordenado=ordenado;
	}

	public boolean isAscendente(){
		return ascendente;
	}

	public void setAscendente(boolean ascendente){
		this.ascendente=ascendente;
	}

	@Override
	public String toString() {
		return "Estructura [idGridColumnaPorGrid=" + idGridColumnaPorGrid
				+ ", field=" + field + ", name=" + name + ", noresize="
				+ noresize + ", width=" + width + ", hidden=" + hidden
				+ ", formato=" + formato + ", ordenado=" + ordenado
				+ ", ascendente=" + ascendente + "]";
	}

}
