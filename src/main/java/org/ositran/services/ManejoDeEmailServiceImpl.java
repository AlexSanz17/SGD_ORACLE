package org.ositran.services;

import com.btg.ositran.siged.domain.Correo;
import gob.ositran.siged.config.SigedProperties;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletContext;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.ositran.utils.Constantes;
import org.ositran.utils.StringUtil;
import org.ositran.utils.UtilEncrip;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Unidad;
import java.net.URLEncoder;
import org.ositran.daos.ManejoDeEmailDAO;
import org.springframework.transaction.annotation.Transactional;

public class ManejoDeEmailServiceImpl implements ManejoDeEmailService{

	private static Logger _log=Logger.getLogger(ManejoDeEmailServiceImpl.class);

	private ParametroService parametroService;

	private UsuarioService usuarioService;
        
        private UnidadService unidadService;
        
        private ManejoDeEmailDAO manejoDeEmailDAO;

        public ManejoDeEmailDAO getManejoDeEmailDAO() {
            return manejoDeEmailDAO;
        }

        public void setManejoDeEmailDAO(ManejoDeEmailDAO manejoDeEmailDAO) {
            this.manejoDeEmailDAO = manejoDeEmailDAO;
        }

	public void EnviarEmail(String to,String from,String Subject,String msgText,String hostSmtp,boolean debug){
		_log.debug("-> [Service] ManejoDeEmailService - EnviarEmail():void ");  
                Correo correo = new Correo();
                correo.setPara(to);
                correo.setFechaCreacion(new Date());
                correo.setMsgText(msgText);
                correo.setEstado("A");
                correo.setTema(Subject);
                
                manejoDeEmailDAO.guardarCorreo(correo);
               // manejoDeEmailDAO.enviarEmail();
              /*
                Properties props=new Properties();
		props.put("mail.smtp.host",hostSmtp);
		if(debug){
			props.put("mail.debug",true);
		}
		Session session=Session.getInstance(props,null);
		session.setDebug(debug);
		if(!StringUtil.isEmpty(to)&&!StringUtil.isEmpty(from)){
			try{
				Message msg=new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
                                String copia = "";
				String cad = to;

				if (to.indexOf(",")>0){
				  to = to.substring(0, to.indexOf(","));
				  copia = cad.substring(cad.indexOf(",")+1, cad.length());
				}

				InternetAddress[] address ={new InternetAddress(to)};
				msg.setRecipients(Message.RecipientType.TO,address);

				if (copia.length()>0)
				   msg.setRecipients(Message.RecipientType.CC,InternetAddress.parse(copia));

				msg.setSubject(Subject);
				msg.setSentDate(new Date());
				msg.setContent(msgText,"text/html;charset=UTF-8");
                                
				Transport.send(msg);
			}catch(MessagingException mex){
				_log.error("\n--Exception handling in msgsendsample.java",mex);
				Exception ex=mex;
				do{
					if(ex instanceof SendFailedException){
						SendFailedException sfex=(SendFailedException) ex;
						Address[] invalid=sfex.getInvalidAddresses();
						if(invalid!=null){
							if(invalid!=null){
								for(int i=0;i<invalid.length;i++){
									_log.debug("         "+invalid[i]);
								}
							}
						}
						Address[] validUnsent=sfex.getValidUnsentAddresses();
						if(validUnsent!=null){
							if(validUnsent!=null){
								for(int i=0;i<validUnsent.length;i++){
									_log.debug("         "+validUnsent[i]);
								}
							}
						}
						Address[] validSent=sfex.getValidSentAddresses();
						if(validSent!=null){
							if(validSent!=null){
								for(int i=0;i<validSent.length;i++){
									_log.debug("         "+validSent[i]);
								}
							}
						}
					}
					if(ex instanceof MessagingException){
						ex=((MessagingException) ex).getNextException();
					}else{
						ex=null;
					}
				}while(ex!=null);
			}
		}else{
			_log.debug("Falta validar");
		}*/
	}

	/**
	 * Envio de mensajes por defecto
	 *
	 * @author Germán Enríquez
	 *
	 * @param to
	 * @param from
	 * @param Subject
	 * @param msgText
	 */
	public void enviarMail(String to,String from,String Subject,String msgText){
		_log.debug("-> [Service] ManejoDeEmailService - enviarMail():void ");

		EnviarEmail(to,from,Subject,msgText,SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.SMTP_HOST),false);
	}


        @Transactional
	@Override
	public void ChaskiMail(int iTipoMail, Usuario objRemitente, Usuario objDestinatario, Documento objDocumento, String carpeta) {
		_log.debug("-> [Service] ManejoDeEmailService - ChaskiMail():void ");

		String sFilePath = null;

		if (objRemitente == null) {
			_log.error("No se recibio remitente");
		}

		if (objDestinatario == null) {
			_log.error("No se recibio destinatario");

			return;
		}

		if (objDocumento == null) {
			_log.error("No se recibio documento");

			return;
		}

		if (objDestinatario.getEnviocorreo() == Constantes.FLAG_ENVIO_CORREO_N) {
			_log.info("El usuario [" + objDestinatario.getUsuario() + "] no desea que le envien correo. No se enviara nada");

			return;
		}

		if (objDestinatario.getCorreo() == null) {
			_log.warn("El usuario [" + objDestinatario.getUsuario() + "] no cuenta con correo");

			return;
		}

		String sTipoParametro = null;



		Parametro objParametro = parametroService.findByTipoUnico(sTipoParametro);
		StringBuilder sbMailTemplatePath = new StringBuilder(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.FOLDER_MAIL)).append(Constantes.GENERAL_SLASH);

		if (objParametro != null) {
			sFilePath = objParametro.getValor();
		}

		if (StringUtil.isEmpty(sFilePath)) {
			_log.error("No hay archivo de Manejo de Mail");

			return;
		}

		sbMailTemplatePath.append(sFilePath);

		if (sFilePath != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Archivo de Manejo de Mail [" + sFilePath + "]");
				_log.debug("Ruta completa del archivo de manejo de mail [" + sbMailTemplatePath.toString() + "]");
			}

			FileReader input = null;
			BufferedReader bufRead = null;
			try {
				input = new FileReader(sbMailTemplatePath.toString());
				bufRead = new BufferedReader(input);
				int iContLinea = 0;
				String sLinea;
				String sAParams[] = null;
				String sAsunto = "";
				String sCParams[] = null;
				StringBuilder sContenido = new StringBuilder("");
				String sMailAdmin = parametroService.findByTipoUnico(Constantes.PARAMETRO_MAIL_ADMIN).getValor();

				while ((sLinea = bufRead.readLine()) != null) {
					if (!sLinea.startsWith("#")) {
						iContLinea++;
						_log.debug(iContLinea + ": " + sLinea);

						switch (iContLinea) {
						case 1: // Linea 1 - Parametros del asunto
							sAParams = sLinea.split(":");
							break;
						case 2: // Linea 2 - Asunto
							sAsunto = sLinea;
							break;
						case 3: // Linea 3 - Parametros del Contenido
							sCParams = sLinea.split(":");
							break;
						default: // Resto de lineas - Contenido
							sContenido.append(sLinea);
							sContenido.append(Constantes.GENERAL_CAMBIO_LINEA);
							break;
						}
					}
				}

				_log.debug("AsuntoParams [" + sAParams.length + "] Asunto [" + sAsunto + "]");

				for (int i = 0; i < sAParams.length; i++) {
					sAParams[i] = getData(Integer.valueOf(sAParams[i]), objRemitente, objDestinatario, objDocumento);
				}

				sAsunto = String.format(sAsunto, (Object[]) sAParams);
				_log.debug("ContenidoParams [" + sCParams.length + "] Contenido [" + sContenido + "]");

				for (int i = 0; i < sCParams.length; i++) {
					sCParams[i] = getData(Integer.valueOf(sCParams[i]), objRemitente, objDestinatario, objDocumento);
				}

				String sContenidoFinal = String.format(sContenido.toString(), (Object[]) sCParams);

				_log.debug("Remitente [" + (objRemitente == null ? null : objRemitente.getUsuario()) + "]");
				_log.debug("Destinatario [" + objDestinatario.getUsuario() + "]");
				_log.debug("Asunto [" + sAsunto + "]");
				_log.debug("Contenido [" + sContenidoFinal + "]");
				_log.debug("Correo del Admin [" + sMailAdmin + "]");
                                
                                Correo correo = new Correo();
                                correo.setPara(objDestinatario.getCorreo());
                                correo.setFechaCreacion(new Date());
                                correo.setMsgText(sContenidoFinal);
                                correo.setEstado("A");
                                correo.setTema(sAsunto);

                                manejoDeEmailDAO.guardarCorreo(correo);
				//EnviarEmail(objDestinatario.getCorreo(), sMailAdmin, sAsunto, sContenidoFinal, SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.SMTP_HOST), true);
			} catch (ArrayIndexOutOfBoundsException be) {
				_log.error(be.getMessage());
			} catch (IOException e) {
				_log.error(e.getMessage(), e);
			} catch (NullPointerException ne) {
				_log.error(ne.getMessage(), ne);
			}finally{
				try{
					if (bufRead!=null){
						bufRead.close();
					}
					if (input!=null){
						input.close();
					}
				}catch(IOException ex){
					_log.error(ex.getMessage(), ex);
				}
			}
		}
	}

        @Transactional
	@Override
	public void ChaskiMail(int iTipoMail, Usuario objRemitente, Usuario objDestinatario, Documento objDocumento, String contenido, String accion) {
		_log.debug("-> [Service] ManejoDeEmailService - ChaskiMail():void ");

                String sFilePath = null;

		if (objRemitente == null) {
			_log.error("No se recibio remitente");
		}

		if (objDestinatario == null) {
			_log.error("No se recibio destinatario");
			return;
		}

		if (objDocumento == null) {
			_log.error("No se recibio documento");
			return;
		}

		if (objDestinatario.getEnviocorreo() == Constantes.FLAG_ENVIO_CORREO_N) {
			_log.info("El usuario [" + objDestinatario.getUsuario() + "] no desea que le envien correo. No se enviara nada");

			return;
		}

		if (objDestinatario.getCorreo() == null) {
			_log.warn("El usuario [" + objDestinatario.getUsuario() + "] no cuenta con correo");

			return;
		}

		String sTipoParametro = null;

		switch (iTipoMail) {
		case Constantes.CONFIGNOTIFMAIL_CREACION_EXPEDIENTE:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_CREACION_EXPEDIENTE;
			break;
		case Constantes.CONFIGNOTIFMAIL_INGRESO_DOCUMENTO_QAS:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_INGRESO_DOCUMENTO_QAS;
			break;
		case Constantes.CONFIGNOTIFMAIL_INGRESO_DOCUMENTO_USERFINAL:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_INGRESO_DOCUMENTO_USERFINAL;
			break;
		case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_REENVIAR:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_DOCUMENTO_REENVIAR;
			break;
                case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_PARTICULAR_MTC:
                  	sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_DOCUMENTO_PARTICULAR_MTC;
			break;  
                case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_PARTICULAR_CR:
                  	sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_DOCUMENTO_PARTICULAR_CR; 
			break;  
                case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_PARTICULAR_PCM:
                  	sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_DOCUMENTO_PARTICULAR_PCM; 
			break;      
                    
		case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_CCREENVIAR:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_DOCUMENTO_CCREENVIAR;
			break;
		case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_PORAPROBAR:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_DOCUMENTO_PORAPROBAR;
			break;
		case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_CCPORAPROBAR:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_DOCUMENTO_CCPORAPROBAR;
			break;
		case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_APROBAR:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_DOCUMENTO_APROBAR;
			break;
		case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_CCAPROBAR:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_DOCUMENTO_CCAPROBAR;
			break;
		case Constantes.CONFIGNOTIFMAIL_ALARMA_AMARILLA:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_ALARMA_AMARILLA;
			break;
		case Constantes.CONFIGNOTIFMAIL_ALARMA_ROJA:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_ALARMA_ROJA;
			break;
		case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_ARCHIVADO:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_DOCUMENTO_ARCHIVADO;
			break;
		case Constantes.CONFIGNOTIFMAIL_ENUMERACION_DESTINATARIO:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_ENUMERACION_DESTINATARIO;
			break;
		case Constantes.CONFIGNOTIFMAIL_ENUMERACION_COPIA:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_ENUMERACION_COPIA;
			break;
		case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_RECHAZAR:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_DOCUMENTO_RECHAZAR;
			break;
		case Constantes.CONFIGNOTIFMAIL_LECTURA_EXPEDIENTE:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_LECTURA_EXPEDIENTE;
			break;
		case Constantes.CONFIGNOTIFMAIL_DOCUMENTO_MULTIPLE:
			sTipoParametro = Constantes.PARAMETRO_MAIL_FILEPATH_DOCUMENTO_MULTIPLE;
			break;

	}

		Parametro objParametro = parametroService.findByTipoUnico(sTipoParametro);
		StringBuilder sbMailTemplatePath = new StringBuilder(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.FOLDER_MAIL)).append(Constantes.GENERAL_SLASH);

		if (objParametro != null) {
			sFilePath = objParametro.getValor();
		}

		if (StringUtil.isEmpty(sFilePath)) {
			_log.error("No hay archivo de Manejo de Mail");

			return;
		}

		sbMailTemplatePath.append(sFilePath);

		if (sFilePath != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Archivo de Manejo de Mail [" + sFilePath + "]");
				_log.debug("Ruta completa del archivo de manejo de mail [" + sbMailTemplatePath.toString() + "]");
			}

			FileReader input = null;
			BufferedReader bufRead = null;
			try {
				input = new FileReader(sbMailTemplatePath.toString());
				bufRead = new BufferedReader(input);
				int iContLinea = 0;
				String sLinea;
				String sAParams[] = null;
				String sAsunto = "";
				String sCParams[] = null;
				StringBuilder sContenido = new StringBuilder("");
				String sMailAdmin = parametroService.findByTipoUnico(Constantes.PARAMETRO_MAIL_ADMIN).getValor();
                           
                                while ((sLinea = bufRead.readLine()) != null) {
					if (!sLinea.startsWith("#")) {
						iContLinea++;
						
                                                switch (iContLinea) {
						case 1: // Linea 1 - Parametros del asunto
							sAParams = sLinea.split(":");
							break;
						case 2: // Linea 2 - Asunto
							sAsunto = sLinea;
							break;
						case 3: // Linea 3 - Parametros del Contenido
							sCParams = sLinea.split(":");
							break;
						default: // Resto de lineas - Contenido
							sContenido.append(sLinea);
							sContenido.append(Constantes.GENERAL_CAMBIO_LINEA);
							break;
						}
					}
				}

                                for (int i = 0; i < sAParams.length; i++) {
                                       sAParams[i] = getData(Integer.valueOf(sAParams[i]), objRemitente, objDestinatario, objDocumento);
                                }

				sAsunto = String.format(sAsunto, (Object[]) sAParams);
				
                           	for (int i = 0; i < sCParams.length; i++) {
                                  sCParams[i] = getData(Integer.valueOf(sCParams[i]), objRemitente, objDestinatario, objDocumento);
                                }
                                String sContenidoFinal = String.format(sContenido.toString(), (Object[]) sCParams);
                           
                                if (accion!=null && !accion.trim().equals(""))
                                   sContenidoFinal = sContenidoFinal.replace("º", "&deg;") + "<br/><b>Acción: </b>" + accion; 
                                
                                if (contenido!=null && !contenido.trim().equals(""))
                                   sContenidoFinal = sContenidoFinal.replace("º", "&deg;") + "<br/><b>Proveido: </b>" + contenido;
                                
                                sAsunto = sAsunto.replaceAll("º", "&deg;");
                                
                                Correo correo = new Correo();
                                correo.setPara(objDestinatario.getCorreo());
                                correo.setFechaCreacion(new Date());
                                correo.setMsgText(sContenidoFinal);
                                correo.setEstado("A");
                                correo.setTema(sAsunto);
                                manejoDeEmailDAO.guardarCorreo(correo);
                                
				//EnviarEmail(objDestinatario.getCorreo(), sMailAdmin, sAsunto, sContenidoFinal, SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.SMTP_HOST), true);
			} catch (ArrayIndexOutOfBoundsException be) {
				_log.error(be.getMessage());
			} catch (IOException e) {
				_log.error(e.getMessage(), e);
			} catch (NullPointerException ne) {
				_log.error(ne.getMessage(), ne);
			}finally{
				try{
					if (bufRead!=null){
						bufRead.close();
					}
					if (input!=null){
						input.close();
					}
				}catch(IOException ex){
					_log.error(ex.getMessage(), ex);
				}
			}
		}
	}

	/*
	 * Data del Mail
	 * 1 - Nombre del tipo de documento
	 * 2 - Numero del documento
	 * 3 - Numero del expediente
	 * 4 - Nombre del proceso
	 * 5 - Nombres del usuario remitente
	 * 6 - Apellidos del usuario remitente
	 * 7 - URL del documento
	 * 8 - Fecha de accion del documento
	 * 9 - Observacion del expediente archivado
	 * 10 - Asunto del expediente
	 */
	public String getData(int iCase, Usuario objRemitente, Usuario destinatario, Documento objDocumento) {
		_log.debug("-> [Service] ManejoDeEmailService - getData():String ");

		String sData = "";

		switch (iCase) {
		case 1: sData = objDocumento.getTipoDocumento().getNombre();
			break;
		case 2: sData = objDocumento.getNumeroDocumento();
			break;
		case 3: sData = objDocumento.getExpediente().getNroexpediente();
			break;
		case 7: sData = "";//objDocumento.getExpediente().getProceso().getNombre();
			break;
		case 5: sData = usuarioService.findByIdUsuario(objRemitente.getIdUsuarioPerfil()).getNombres();
			break;
		case 6: ServletContext sc=ServletActionContext.getServletContext();
			Usuario usu=usuarioService.findByIdUsuario(objRemitente.getIdUsuarioPerfil());
                        Unidad u = unidadService.buscarObjPor(objRemitente.getIdUnidadPerfil());
			String text = usu.getApellidos() +" - area: "+u.getNombre()+",";
                        sData = text;
			break;
		case 4: String key = parametroService.findByTipoUnico(Constantes.KEY_SGD).getValor();
                      try {
                                Usuario usuDestinatario = usuarioService.findByIdUsuario(destinatario.getIdusuario());                                
                                sData = "<a href=" + SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.SIGED_CORREO) + "?idDoc=" + URLEncoder.encode(UtilEncrip.encrypt(key,objDocumento.getIdDocumento().toString()), "UTF-8").replace("+", "%2B") + "&xxyyxxx=" + URLEncoder.encode(UtilEncrip.encrypt(key, usuDestinatario.getUsuario()), "UTF-8") + ">Ver Documento</a>";	        
                       
                                
                        } catch (Exception e) {
				e.printStackTrace();
				_log.error("No se pudo generar la url para mandar el correo.");
			}
                       break;
		case 8:sData = objDocumento.getFechaAccion().toString();
			break;
		case 9:sData = objDocumento.getObservacion().toString();
			break;
		case 10:sData = objDocumento.getExpediente().getAsunto();
			break;
		case 11:  Unidad u_ = unidadService.buscarObjPor(destinatario.getIdUnidadPerfil());
                        
			if(destinatario != null){
                               sData = destinatario.getNombres() + " " + destinatario.getApellidos()+" - area: "+u_.getNombre();
                            
			}else{
				sData = "-";
			}
                        break;
                case 12: sData =  objDocumento.getID_CODIGO().toString();
                        break;  
                case 13: sData = objDocumento.getAsunto();
                        break;      
		}
                

		return sData;
	}

	/**
	 * @return the parametroService
	 */
	public ParametroService getParametroService() {
		return parametroService;
	}

	/**
	 * @param parametroService the parametroService to set
	 */
	public void setParametroService(ParametroService parametroService) {
		this.parametroService = parametroService;
	}

	/**
	 * @return the usuarioService
	 */
	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
        
          public UnidadService getUnidadService() {
                return unidadService;
            }

            public void setUnidadService(UnidadService unidadService) {
                this.unidadService = unidadService;
            }
}
