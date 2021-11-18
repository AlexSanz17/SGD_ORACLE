package org.ositran.services;


import com.btg.ositran.siged.domain.Resolucionjaru;

public interface ResolucionjaruService {

	public Resolucionjaru saveOrUpdateResolucionJaru(Resolucionjaru resolucionjaru);
	
	public Resolucionjaru findByIdExpedienteStor(Integer idExpediente);
}
