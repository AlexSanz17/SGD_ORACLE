/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.Usuario;

public interface NotificacionDAO {

//   public List<Notificacion> buscarLstPor(Integer iIdUsuario, Integer iTipoNotificacion);

   public List<Notificacion> buscarLstPor(Usuario usuario, Integer iTipoNotificacion, Character cLeido);

   public List<Notificacion> obtenerNotificacionesxUsuario(Integer iIdUsuario);

   public Notificacion buscarObjPorID(Integer iIdNotificacion);

   public Notificacion findObjByIdDocumento(Integer iIdDocumento);

   public Notificacion saveNotificacion(Notificacion objNotificacion);

   public int nroNotificacionesNoLeidas(Usuario usuario);

   public Integer getNotificacionesNoLeidas(Integer iIdUsuario);

   public Integer obtenerCantidadNotificacionesxUsuario(Integer iIdUsuario);
   
   public List<Notificacion> findLastNotificacionbyUsuario(Integer idDocumento, Integer idUsuario);

   public List<Notificacion> buscarLastNotificacionesbyDocumento(Integer idDocumento, Integer tipoNotificacion);
}
