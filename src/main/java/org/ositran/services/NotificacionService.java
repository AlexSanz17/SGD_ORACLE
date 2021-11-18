package org.ositran.services;

import java.util.List;
import java.util.Set;

import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.tree.Nodo;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Etapa;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.Usuario;

public interface NotificacionService {

   public List<Notificacion> buscarLstPor(Integer iIdUsuario);

//   public List<Notificacion> buscarLstPor(Integer iIdUsuario, Integer iTipoNotificacion);

   public List<Notificacion> buscarLstPor(Usuario usuario, Integer iTipoNotificacion, Character cLeido);
   
   public List<Notificacion> buscarLastNotificacionesbyDocumento(Integer iIdDocumento, Integer iTipoNotificacion);

   public Notificacion buscarObjPorID(Integer iIdNotificacion);

   public Notificacion findObjByIdDocumento(Integer iIdDocumento);

   public Notificacion saveNotificacion(Notificacion objNotificacion);

   public boolean enviarNotificacion(Usuario remitente, Usuario receptor, Documento docOriginal, int tipo, String nombrePC,Boolean horarioPermitido,Documento documento, String ta);
    
   
   public void enviarNotificacion(Usuario remitente, Usuario propietario,Documento documento, Integer tipoNotificacionReferenciaDocumento,	Expediente expedienteNuevo); 
  //public void enviarNotificacion(Usuario remitente, Usuario propietario,Documento documento, Integer tipoNotificacionReferenciaDocumento,	Expediente expedienteNuevo);

   public void enviarNotificacionSAS(Usuario remitente, Usuario destinatario, Documento documento, String observacion, Etapa etapaRemiten);

   public Notificacion clonarNotificacion(Usuario remitente,Usuario receptor,Documento documento,int tipo,String asunto,String contenido, Integer idNotificacionPadre, String nombrePC,Boolean horarioPermitido);

   public int getNroNotificacionesNL(Usuario user);

   public void updateEstadoNotificacion(Integer iIdNotificacion, Character cEstado);
   
   public void updateTipoNotificacion(Integer idDocumento, Integer idUsuario, Integer tipoNotificacion);

   public Integer getNotificacionesNoLeidas(Integer iIdUsuario);

   public Notificacion updateLeido(Integer iIdNotificacion);

   public void eliminarDocumentosEnviados(String[] ids, String nombrePC);

   public Integer obtenerCantidadNotificacionesxUsuario(Integer iIdUsuario)  ;

   Set informarViaNotifAndMail(Usuario remitente, Documento documento, int iTipoEvento, int iTipoNotificacion, String nombrePC, String contenido, String accion);

  
   
   public List<Notificacion> findLastNotificacionbyUsuario(Integer idDocumento,Integer idUsuario);
   
   public List<Nodo> getDojoInformativoTree(BusquedaAvanzada objFiltro, Usuario objUsuario);
}
