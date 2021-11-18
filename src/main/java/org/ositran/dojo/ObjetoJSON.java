/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.dojo;

import org.ositran.dojo.grid.Estructura;
import java.util.List;

public class ObjetoJSON {

	private String identifier;
	private String label;
	private List<?> items;
	private List<Estructura> structure;
	private String horaservidor;
	private String parametroalerta1;
	private String parametroalerta2;

	//////////////////////////////////
	// Getters and Setters          //
	//////////////////////////////////
	public String getIdentifier() {
	   return identifier;
	}

	public void setIdentifier(String identifier) {
	   this.identifier = identifier;
	}

	public String getLabel() {
	   return label;
	}

	public void setLabel(String label) {
	   this.label = label;
	}

	public List<?> getItems() {
	   return items;
	}

	public void setItems(List<?> items) {
	   this.items = items;
	}

	public List<Estructura> getStructure() {
	   return structure;
	}

	public void setStructure(List<Estructura> structure) {
	   this.structure = structure;
	}

	public String getHoraservidor() {
		return horaservidor;
	}
	
	public void setHoraservidor(String horaservidor) {
		this.horaservidor = horaservidor;
	}
	
	public String getParametroalerta1() {
		return parametroalerta1;
	}
	
	public void setParametroalerta1(String parametroAlerta1) {
		this.parametroalerta1 = parametroAlerta1;
	}
	
	public String getParametroalerta2() {
		return parametroalerta2;
	}
	
	public void setParametroalerta2(String parametroAlerta2) {
		this.parametroalerta2 = parametroAlerta2;
	}
	
}
