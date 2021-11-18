package org.ositran.services;



import com.btg.ositran.siged.domain.Envio;


public interface EnvioService {

	public Envio saveDatos(String Strmensa,Envio objenvio);
	
	public Envio findkey(int Id);
}
