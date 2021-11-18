/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;


import org.ositran.utils.MensajeriaDatos;

import com.btg.ositran.siged.domain.Datos;
import com.btg.ositran.siged.domain.Usuario;

public interface DatosService {

	public MensajeriaDatos saveDatos(Usuario usuario,String Strfecha,String Strnumdoc,String Strtipdoc, String Strmensa,String Strnumin,String Strambitoenvio,String Stremdes,String Strusudes,String Strdirdes,String Strdepart,String Strprovin,String Strdistri,String Strrefere);
	
	public Datos findId(int Id);
	
	//public void registrarAuditoria(Usuario usuario,String tipoEnvio,String empresaDestino,String referencia,String tipoAuditoria,String modulo,String opcion);
}
