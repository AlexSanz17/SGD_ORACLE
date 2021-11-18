/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;


import com.btg.ositran.siged.domain.Cargo;


public interface CargoService {

	public Cargo saveDatos(String Strmensa,String Strgrupo,String Strestado, Cargo objcargox);
	
	public Cargo findkey(int Id);
}
