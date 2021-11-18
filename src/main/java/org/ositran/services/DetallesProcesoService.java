/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;

import org.ositran.dojo.grid.Estructura;

public interface DetallesProcesoService {

	public List<Estructura> getEstructuraAltU(String grupoclasificacion);
	public String hello();
	public String getGrupoclasificacion();
	public void setGrupoclasificacion(String grupoclasificacion);
}
