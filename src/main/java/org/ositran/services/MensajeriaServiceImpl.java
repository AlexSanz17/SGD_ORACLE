package org.ositran.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.daos.AccionDAO;
import org.ositran.daos.AuditoriaDAO;
import org.ositran.daos.ClienteDAO;
import org.ositran.daos.DocumentoDAO;
import org.ositran.daos.MensajeriaDAO;
import org.ositran.daos.NotificacionDAO;
import org.ositran.daos.ParametroDAO;
import org.ositran.daos.TrazabilidadcopiaDAO;
import org.ositran.daos.TrazabilidaddocumentoDAO;
import org.ositran.daos.UsuarioDAO;
import org.ositran.dojo.grid.Item;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Mensajeria;
import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.Tipoenvio;
import com.btg.ositran.siged.domain.Trazabilidadcopia;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Usuario;

public class MensajeriaServiceImpl implements MensajeriaService {

   private static Logger log = Logger.getLogger(MensajeriaServiceImpl.class);
   private MensajeriaDAO dao;
   private UsuarioDAO daousu;
   private DocumentoDAO daodoc;
   private AuditoriaDAO daoauditoria;
   private DocumentoService srvDocumento;
   private ParametroDAO parametroDAO;
   private TrazabilidaddocumentoDAO trazabilidaddocumentoDAO;
   private TrazabilidadcopiaDAO trazabilidadcopiaDAO;
   private AccionDAO accionDAO;
   private NotificacionDAO notificacionDAO;
   private ClienteDAO clienteDAO;

   public MensajeriaServiceImpl(MensajeriaDAO dao, UsuarioDAO daousu, DocumentoDAO daodoc) {
      setDao(dao);
      setDaousu(daousu);
      setDaodoc(daodoc);
   }

   public List<Mensajeria> ViewEstado(String Esta) {
      List<Mensajeria> data = getDao().findEstado(Esta);
      return data;
   }

   public Mensajeria ViewMensaje(int Id) {
      return getDao().findId(Id);
   }

   @Transactional
   public Mensajeria guardarObj(Usuario objUsuario, Integer iIdDocumento, char docPrincipal, Integer tipoEnvio, Item cliente, Integer idMensajeriaPrincipal) {
      Documento objDocumento = null;
      Mensajeria mensajeria = new Mensajeria();

      objDocumento = srvDocumento.findByIdDocumento(iIdDocumento);
      mensajeria.setIddocumento(objDocumento.getIdDocumento());
      //mensajeria.setAsunto(sAsunto);
      mensajeria.setEstado(Constantes.ESTADO_ACTIVO);
      mensajeria.setEstadoingreso('0'); //nose pa q es esto!!!
      mensajeria.setTipodocumento(objDocumento.getTipoDocumento().getNombre());
      mensajeria.setNumerodocumento(objDocumento.getNumeroDocumento());
      mensajeria.setNumerointerno(mensajeria.getIddocumento().toString());
      mensajeria.setDestinatario(cliente.getRazonsocial());
      mensajeria.setDirecciondestino(cliente.getDireccion());
      mensajeria.setDepartamento(cliente.getDepartamento());
      mensajeria.setProvincia(cliente.getProvincia());
      mensajeria.setDistrito(cliente.getDistrito());

      mensajeria.setIdcliente(Integer.parseInt(cliente.getIdentificacion()));

      mensajeria.setReferenciaDireccionCliente(cliente.getReferencia());

      mensajeria.setFechaderivacion(new Date());
      mensajeria.setIdusuario(objUsuario);
      mensajeria.setAviso('0'); //PARA Q ES ESTO???
      mensajeria.setDocprincipal(docPrincipal);
      mensajeria.setIdtipoenvio(new Tipoenvio(tipoEnvio));
      if (String.valueOf(Constantes.DOCUMENTO_NO_PRINCIPAL).equals(docPrincipal)){
    	  mensajeria.setIdmensajeriaprincipal(idMensajeriaPrincipal);
      }
      mensajeria = dao.guardarObj(mensajeria);
      log.info("Mensajeria generada con ID [" + mensajeria.getIdmensajeria() + "] asunto [" + mensajeria.getAsunto() + "]");

      return mensajeria;
   }

   public List<Mensajeria> FindusuarioMensajeria(String estado) {
      List<Mensajeria> data = new ArrayList<Mensajeria>();

      data = getDao().findEstado(estado);

      return data;
   }

   public List<Mensajeria> findidusuario(int Idusuario) {
      List<Mensajeria> data = new ArrayList<Mensajeria>();

      data = getDao().FindusuarioMensajeria(Idusuario);

      return data;
   }

   @Transactional
   public void savemensajeria(String Striddoc, String Strfecha, String Stridusu, String Striasunto) {
      Mensajeria ObjMsn = new Mensajeria();
      Mensajeria ObjMsn2 = new Mensajeria();
      int iddoc = Integer.parseInt(Striddoc);
      int idusu = Integer.parseInt(Stridusu);

      DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
      Date fecha;
      try {

         Usuario Objusuario = getDaousu().findByIdUsuario(idusu);
         Documento Objdocumento = getDaodoc().findByIdDocumento(iddoc);
         String avis = this.alerta(Objdocumento);
         fecha = df.parse(Strfecha);

         ObjMsn.setEstado('A');
         ObjMsn.setEstadoingreso('0');
         ObjMsn.setNumerointerno(Striddoc);
         ObjMsn.setTipodocumento(Objdocumento.getTipoDocumento().getNombre());
         ObjMsn.setNumerodocumento(Objdocumento.getNumeroDocumento());
         ObjMsn.setDestinatario(Objdocumento.getExpediente().getCliente().getRazonSocial());
         ObjMsn.setDirecciondestino(Objdocumento.getExpediente().getCliente().getDireccionPrincipal());
         ObjMsn.setAsunto(Striasunto);
         ObjMsn.setFechaderivacion(fecha);
         ObjMsn.setIdusuario(Objusuario);
         ObjMsn.setIddocumento(iddoc);
         ObjMsn.setAviso('0');

         getDao().guardarObj(ObjMsn);

         if (avis.equals("1")) {

            ObjMsn2.setEstado('A');
            ObjMsn2.setEstadoingreso('0');
            ObjMsn2.setNumerointerno(Striddoc);
            ObjMsn2.setTipodocumento(Objdocumento.getTipoDocumento().getNombre());
            ObjMsn2.setNumerodocumento(Objdocumento.getNumeroDocumento());
            ObjMsn2.setDestinatario(Objdocumento.getExpediente().getConcesionario().getRazonSocial());
            ObjMsn2.setDirecciondestino(Objdocumento.getExpediente().getConcesionario().getDireccion());
            ObjMsn2.setAsunto(Striasunto);
            ObjMsn2.setFechaderivacion(fecha);
            ObjMsn2.setIdusuario(Objusuario);
            ObjMsn2.setIddocumento(iddoc);
            ObjMsn2.setAviso('1');

            getDao().guardarObj(ObjMsn2);
         }


      } catch (ParseException e) {

         log.error(e.getMessage(),e);
      }

   }

   public String alerta(Documento Objdocumento) {
      //String nomproc = Objdocumento.getExpediente().getProceso().getNombre();
      String alert = "0";
      /*if (nomproc.equals("STOR_APELACION")) {
         alert = "1";
      }
      if (nomproc.equals("STOR_QUEJA")) {
         alert = "1";
      }
      if (nomproc.equals("STOR_MEDIDA_CAUTELAR")) {
         alert = "1";
      }*/

      return alert;
   }

   @Override
	public List<Mensajeria> filtrarCriterios(Usuario objUsuario, String fechaDesde, String fechaHasta, String horaDesde, String horaHasta) {
		List<Mensajeria> data = new ArrayList<Mensajeria>();
		data = getDao().filtrarCriterios(objUsuario, fechaDesde, fechaHasta, horaDesde, horaHasta);
		return data;
	}

   @Transactional
   public void guardarTrazaEnvioMensajeria(Integer[] idsDocumentos, List<Item> listaClientes, Usuario remitente, String nombrePC){
	   try{
		   Usuario usuarioMensajero = daousu.findByUsuario(parametroDAO.findByTipo(Constantes.PARAMETRO_TIPO_USUARIO_MENSAJERIA).get(0).getValor());
		   Usuario usuarioRemitente = daousu.findByUsuario(remitente.getUsuario());
	   	   String mensaje = "Se enviaron a mensajer&iacute;a los siguientes documentos: <ul>";
		   List<Documento> documentos = new ArrayList<Documento>();

	   	   for(Integer idDocumento : idsDocumentos){
	   		   Documento documento = daodoc.findByIdDocumento(idDocumento);
	   		   documentos.add(documento);
	   		   mensaje += "<li>"+documento.getTipoDocumento().getNombre()+" "+documento.getNumeroDocumento()+"</li>";
	   	   }

	   	   mensaje += "</ul>";
	   	   mensaje += "Los destinatarios a recibir la documentaci&oacute;n son: <ul>";

	   	   for(Item item : listaClientes){
	   		   mensaje += "<li>" + item.getRazonsocial() + "</li>";
	   	   }

	   	   mensaje +="</ul>";

	   	   for(Documento documento : documentos){
	   		   Trazabilidaddocumento trazaOrigen = trazabilidaddocumentoDAO.findByMaxNroRegistro(documento.getIdDocumento(),null, null);

	   		   Notificacion notificacion = new Notificacion();
			   notificacion.setIddocumento(documento);
	   		   notificacion.setFechanotificacion(new Date());
	   		   notificacion.setAsunto("Envio a mensajeria");
			   notificacion.setContenido(mensaje.length() > 4000 ? mensaje.substring(0, 3998) : mensaje);
			   notificacion.setTiponotificacion(Constantes.TIPO_NOTIFICACION_ENVIO_MENSAJERIA);
			   notificacion.setEstado(Constantes.ESTADO_ACTIVO);
			   notificacion.setLeido(Constantes.Si);
			   notificacion.setIdusuario(usuarioMensajero);
			   notificacionDAO.saveNotificacion(notificacion);

			   Trazabilidadcopia traza = new Trazabilidadcopia();
			   traza.setRemitente(usuarioRemitente);
			   traza.setDestinatario(usuarioMensajero);
			   traza.setFechacreacion(new Date());
			   traza.setTipo(Constantes.TIPO_ORIGEN_TRAZADOCUMENTO);
			   traza.setDocumento(documento);
			   traza.setIdorigen(trazaOrigen);
			   traza.setAccion(accionDAO.buscarObjPor(Constantes.ACCION_ENVIO_MENSAJERIA));
			   traza.setNotificacion(notificacion);
			   traza.setNombrePC(nombrePC);
			   trazabilidadcopiaDAO.saveObject(traza);
	   	   }

	   }catch(Exception e){
		   e.printStackTrace();
	   }
   }

   @Transactional
   public void guardarTrazaRecepcionCargo(Integer idDocumento, Integer idCliente, String nombrePC){
	   try{
		   Documento documento = daodoc.findByIdDocumento(idDocumento);
		   Cliente cliente = clienteDAO.findByIdCliente(idCliente);
		   Usuario usuarioMensajero = daousu.findByUsuario(parametroDAO.findByTipo(Constantes.PARAMETRO_TIPO_USUARIO_MENSAJERIA).get(0).getValor());

		   Trazabilidaddocumento trazaOrigen = trazabilidaddocumentoDAO.findByMaxNroRegistro(documento.getIdDocumento(),null, null);

		   String mensaje = "Mensajer&iacute;a ha recibido el cargo del cliente "+cliente.getNombreRazon()+" respecto " +
		   					"a la recepci&oacute;n del documento "+documento.getTipoDocumento().getNombre() + " "+documento.getNumeroDocumento();

   		   Notificacion notificacion = new Notificacion();
		   notificacion.setIddocumento(documento);
   		   notificacion.setFechanotificacion(new Date());
   		   notificacion.setAsunto("Envio a mensajeria");
		   notificacion.setContenido(mensaje.length() > 4000 ? mensaje.substring(0, 3998) : mensaje);
		   notificacion.setTiponotificacion(Constantes.TIPO_NOTIFICACION_ENVIO_MENSAJERIA);
		   notificacion.setEstado(Constantes.ESTADO_ACTIVO);
		   notificacion.setLeido(Constantes.Si);
		   notificacion.setIdusuario(trazaOrigen.getDestinatario());
		   notificacionDAO.saveNotificacion(notificacion);

		   Trazabilidadcopia traza = new Trazabilidadcopia();
		   traza.setRemitente(usuarioMensajero);
		   traza.setDestinatario(trazaOrigen.getDestinatario());
		   traza.setFechacreacion(new Date());
		   traza.setTipo(Constantes.TIPO_ORIGEN_TRAZADOCUMENTO);
		   traza.setDocumento(documento);
		   traza.setIdorigen(trazaOrigen);
		   traza.setAccion(accionDAO.buscarObjPor(Constantes.ACCION_RECEPCION_CARGO));
		   traza.setNotificacion(notificacion);
		   traza.setNombrePC(nombrePC);
		   trazabilidadcopiaDAO.saveObject(traza);

	   }catch(Exception e){
		   e.printStackTrace();
	   }
   }

//getter and setter
   private void setDao(MensajeriaDAO dao) {
      this.dao = dao;
   }

   private MensajeriaDAO getDao() {
      return dao;
   }

   public void setDaousu(UsuarioDAO daousu) {
      this.daousu = daousu;
   }

   public UsuarioDAO getDaousu() {
      return daousu;
   }

   public void setDaodoc(DocumentoDAO daodoc) {
      this.daodoc = daodoc;
   }

   public DocumentoDAO getDaodoc() {
      return daodoc;
   }

   public void setDaoauditoria(AuditoriaDAO daoauditoria) {
      this.daoauditoria = daoauditoria;
   }

   public AuditoriaDAO getDaoauditoria() {
      return daoauditoria;
   }

   public DocumentoService getSrvDocumento() {
      return srvDocumento;
   }

   public void setSrvDocumento(DocumentoService srvDocumento) {
      this.srvDocumento = srvDocumento;
   }

public static Logger getLog() {
	return log;
}

public static void setLog(Logger log) {
	MensajeriaServiceImpl.log = log;
}

public ParametroDAO getParametroDAO() {
	return parametroDAO;
}

public void setParametroDAO(ParametroDAO parametroDAO) {
	this.parametroDAO = parametroDAO;
}

public TrazabilidaddocumentoDAO getTrazabilidaddocumentoDAO() {
	return trazabilidaddocumentoDAO;
}

public void setTrazabilidaddocumentoDAO(
		TrazabilidaddocumentoDAO trazabilidaddocumentoDAO) {
	this.trazabilidaddocumentoDAO = trazabilidaddocumentoDAO;
}

public AccionDAO getAccionDAO() {
	return accionDAO;
}

public void setAccionDAO(AccionDAO accionDAO) {
	this.accionDAO = accionDAO;
}

public TrazabilidadcopiaDAO getTrazabilidadcopiaDAO() {
	return trazabilidadcopiaDAO;
}

public void setTrazabilidadcopiaDAO(TrazabilidadcopiaDAO trazabilidadcopiaDAO) {
	this.trazabilidadcopiaDAO = trazabilidadcopiaDAO;
}

public NotificacionDAO getNotificacionDAO() {
	return notificacionDAO;
}

public void setNotificacionDAO(NotificacionDAO notificacionDAO) {
	this.notificacionDAO = notificacionDAO;
}

public ClienteDAO getClienteDAO() {
	return clienteDAO;
}

public void setClienteDAO(ClienteDAO clienteDAO) {
	this.clienteDAO = clienteDAO;
}


}
