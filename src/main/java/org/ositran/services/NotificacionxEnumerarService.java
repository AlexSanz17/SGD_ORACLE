package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Etapa;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.NotificacionxEnumerar;
import com.btg.ositran.siged.domain.Usuario;

public interface NotificacionxEnumerarService {

   
   public List<NotificacionxEnumerar> buscarLastNotificacionesbyDocumento(Integer iIdDocumento, Integer iTipoNotificacion);

  }
