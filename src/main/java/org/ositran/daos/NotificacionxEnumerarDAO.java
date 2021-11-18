/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.NotificacionxEnumerar;

public interface NotificacionxEnumerarDAO {

	public void guardar(NotificacionxEnumerar notificacionxEnumerar);
	
	public List<NotificacionxEnumerar> buscarLastNotificacionesbyDocumento(Integer idDocumento, Integer tipoNotificacion);
	
	public List<NotificacionxEnumerar> findByDocumento(Documento objDocumento, Integer tn);
}
