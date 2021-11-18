/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.ArchivoPendiente;
import com.btg.ositran.siged.domain.ArchivoTemporal;
import com.btg.ositran.siged.domain.Campo;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Plantilla;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Valorcampo;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import gob.ositran.siged.config.SigedProperties;
import gob.ositran.siged.util.MessagePropertiesEnum;
import gob.ositran.siged.util.SigedMessageSource;
import java.io.File; 
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.ositran.common.util.Base64;
import org.ositran.services.ArchivopendienteService;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.ositran.services.AccionService;
import org.ositran.services.ArchivoService;
import org.ositran.services.DocumentoService;
import org.ositran.services.NotificacionService;
import org.ositran.services.ParametroService;
import org.ositran.services.PlantillaService;
import org.ositran.services.RepositorioService;
import org.ositran.services.TipodocumentoService;
import org.ositran.services.TrazabilidaddocumentoService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;
import org.ositran.ui.utils.WebFileStreamUtil;
import org.ositran.ui.utils.WebFileStreamUtil.AplicationType;
import org.ositran.utils.StringUtil;

public class PlantillaAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID=-1520820132930443616L;
	private ArchivopendienteService archivoPendienteService;
	private DocumentoService documentoService;
	private PlantillaService plantillaService;
	private RepositorioService repositorioService;
	private UsuarioService usuarioService;
	private ArchivoService archivoService;
	private AccionService accionService;
	private ParametroService parametroService;
	private NotificacionService srvNotificacion;
	private File upload;
	private String fullFileName;
	private Integer idtipoDocumento;
	private String uploadContentType;// The content type of the file
	private String uploadFileName;// The uploaded file name
	private String fileCaption;// The caption of the file entered by user
	private String marcaDeAgua;
	private List<Parametro> listaParametros;
	private String ruta;
	private String mensaje;
	private String nombreReal ;
	private String refrescar  ;
	private String viewFile ;
	private Integer firmante ;
	private String autor ;
	private boolean enumerarDocumento ;
	private String nrodocumento  ;
	private String fechadocumentopendiente ;
	private String enumerardocumento;
	private String tiponumeracion;
	private SigedMessageSource messageSource;

	public String getRuta(){
		return ruta;
	}

	public void setRuta(String ruta){
		this.ruta=ruta;
	}

	public String verPrevioN(){
		return "viewDocN";
	}

	public File getUpload(){
		return upload;
	}

	public void setUpload(File upload){
		this.upload=upload;
	}

	public String getFileCaption(){
		return fileCaption;
	}

	public void setFileCaption(String fileCaption){
		this.fileCaption=fileCaption;
	}

	public ParametroService getParametroService(){
		return parametroService;
	}

	public void setParametroService(ParametroService parametroService){
		this.parametroService=parametroService;
	}

	public String getFullFileName(){
		return fullFileName;
	}

	public void setFullFileName(String fullFileName){
		this.fullFileName=fullFileName;
	}

	public String getUploadContentType(){
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType){
		this.uploadContentType=uploadContentType;
	}

	public String getUploadFileName(){
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName){
		this.uploadFileName=uploadFileName;
	}

	public ArchivoService getArchivoService(){
		return archivoService;
	}

	public void setArchivoService(ArchivoService archivoService){
		this.archivoService=archivoService;
	}

	public List<Parametro> getListaParametros(){
		return listaParametros;
	}

	public void setListaParametros(List<Parametro> listaParametros){
		this.listaParametros=listaParametros;
	}

	public TipodocumentoService getTipoDocumentoService(){
		return tipodocumentoService;
	}

	public void setTipoDocumentoService(TipodocumentoService tipodocumentoService){
		this.tipodocumentoService=tipodocumentoService;
	}
	private TipodocumentoService tipodocumentoService;

	public AccionService getAccionService(){
		return accionService;
	}

	public String getMarcaDeAgua(){
		return marcaDeAgua;
	}

	public void setMarcaDeAgua(String marcaDeAgua){
		this.marcaDeAgua=marcaDeAgua;
	}

	public void setAccionService(AccionService accionService){
		this.accionService=accionService;
	}

	public TrazabilidaddocumentoService getTrazabilidaddocumentoService(){
		return trazabilidaddocumentoService;
	}

	public void setTrazabilidaddocumentoService(TrazabilidaddocumentoService trazabilidaddocumentoService){
		this.trazabilidaddocumentoService=trazabilidaddocumentoService;
	}
	private TrazabilidaddocumentoService trazabilidaddocumentoService;
	private Integer idDocumento;
	private Integer idArchivoPendiente;
	private Documento documento;
	private Integer idPlantilla;
	private Integer idproceso;
	private Integer tipoDocumento;
	private String rutaArchivo;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String cerrar;
	private ArchivoPendiente archivopendiente;
	private Date atenderAntesDe;
	private String asunto;

	// private Map informacion=new HashMap();
	private static Logger log=Logger.getLogger(PlantillaAction.class);

	public void setServletRequest(HttpServletRequest request){
		this.request=request;
	}

	public void setServletResponse(HttpServletResponse response){
		this.response=response;
	}

	public PlantillaAction(ArchivopendienteService archivoPendienteService,DocumentoService documentoService,PlantillaService plantillaService,RepositorioService repositorioService,UsuarioService usuarioService,ArchivoService archivoService,AccionService accionService,TrazabilidaddocumentoService trazabilidaddocumentoService,TipodocumentoService tipodocumentoService){
		this.archivoPendienteService=archivoPendienteService;
		this.documentoService=documentoService;
		this.plantillaService=plantillaService;
		this.repositorioService=repositorioService;
		this.usuarioService=usuarioService;
		this.archivoService=archivoService;
		this.accionService=accionService;
		this.trazabilidaddocumentoService=trazabilidaddocumentoService;
		this.tipodocumentoService=tipodocumentoService;
	}

	@SuppressWarnings("unchecked")
	public String inicio(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		List<ArchivoTemporal> l=(List<ArchivoTemporal>) session.get("uploaded_list2");
		l=new ArrayList<ArchivoTemporal>();
		session.put("uploaded_list2",l);
		return "view";
	}

	public String verNuevo(){
		// String id = request.getParameter("idDocumento");
		Map<String,Object> session=ActionContext.getContext().getSession();
		Integer[] arrIdDoc= {idDocumento};
		session.put("arrIdDoc", arrIdDoc);
		//Integer[] arrIdDoc=(Integer[]) getMapSession().get("arrIdDoc");

		// log.debug(" %%%%%%%%  "+id) ;
		if(idDocumento!=null&&idDocumento.intValue()!=0){
			// log.debug(" %%%%% "+idDocumento);
			documento=documentoService.findByIdDocumento(idDocumento);
		}

		//this.archivopendiente  = new ArchivoPendiente(Constantes.NUEVO) ;

		this.setListaParametros(parametroService.findByTipo(Constantes.NUEVO_DOC_MARCA_DE_AGUA));
		this.setMarcaDeAgua("");
		return "new";
	}

	public String verPrevio(){
		this.refrescar = "OK";
		return "viewDoc";
	}

	public String verPrevioBotones(){
		return "viewDocButt";
	}

	public String verPrevioDocumento(){
		log.debug(" aquiiiiiiiii 000 ")  ;
		return "viewDocDoc";
	}

	@SuppressWarnings("unchecked")
	public String enviarArchivo(){
		// / aqui guardar en bbdd y enviar a alfresco
		Map<String,Object> session=ActionContext.getContext().getSession();
		//HttpServletRequest parameters=ServletActionContext.getRequest();
		String nombrePC = (String)session.get("nombrePC");
		Usuario origen=(Usuario) session.get(Constantes.SESSION_USUARIO);
		origen = usuarioService.findByIdUsuario(origen.getIdusuario());
		// String strRol=(String) session.get("rol");
		if(idDocumento!=null && idDocumento.intValue()!=0){
			String ruta=(String) request.getSession().getAttribute("rutaArchivo");
			Archivo archivo=new Archivo();
			Plantilla plantilla=plantillaService.findByIdplantilla(tipoDocumento);
			Documento doc=documentoService.findByIdDocumento(idDocumento);
			String asunt=(String) request.getSession().getAttribute("asunto");
			Timestamp fech=(Timestamp) request.getSession().getAttribute("fechalimite");
			if(fech!=null){
				doc.setFechaLimiteAtencion(new Date(fech.getTime()));
			}
			if(asunt!=null){
				doc.setAsunto(asunt);
			}
			log.debug(" values fech:"+fech+"   asunt:"+asunt);
			documentoService.saveDocumento(doc);
			archivo.setDescripcion(plantilla.getDescripcion());
			Documento objNuevoDocumento=new Documento();
			objNuevoDocumento.setTipoDocumento(tipodocumentoService.findByIdTipoDocumento(tipoDocumento));
			objNuevoDocumento.setPropietario(origen);
			objNuevoDocumento.setExpediente(doc.getExpediente());
			//objNuevoDocumento.setAsunto(archivo.getDescripcion());
			objNuevoDocumento.setNumeroFolios(1);
			objNuevoDocumento.setAccion(getAccionService().findByNombre("registrar"));
			objNuevoDocumento.setPrincipal(Constantes.DOCUMENTO_NO_PRINCIPAL);
			objNuevoDocumento.setFechaAccion(new Date());
			objNuevoDocumento.setFechaCreacion(new Date());
			try {
				log.debug(" parseando "+this.fechadocumentopendiente) ;
				objNuevoDocumento.setFechaDocumento(new SimpleDateFormat("yyyy-MM-dd").parse(this.fechadocumentopendiente) )	;
			}catch(Exception  ex ) {
				log.error(ex.getMessage(), ex);
			}


			if(condestinatarios!=null && condestinatarios.size()==1){
				String array=this.condestinatarios.get(0);
				condestinatarios.clear();
				condestinatarios=Arrays.asList(array.split(","));
			}

			if(concopias!=null && concopias.size()==1){
				String array=this.concopias.get(0);
				concopias.clear();
				concopias=Arrays.asList(array.split(","));
			}

			objNuevoDocumento.setCondestinatarios(this.condestinatarios);
			objNuevoDocumento.setConcopias(this.concopias);

			log.debug(" destinatario: "+condestinatarios.size()+ "; copia : "+concopias.size());

			if(this.firmante!=null&&this.firmante!=0){
				Usuario firm =  usuarioService.findByIdUsuario(this.firmante);
				objNuevoDocumento.setFirmante(firm) ;
			}

			if (log.isDebugEnabled()) {
				log.debug("enumerarDocumento [" + enumerardocumento + "]");
				log.debug("tiponumeracion [" + tiponumeracion + "]");
				log.debug("nrodocumento [" + nrodocumento + "]");
				log.debug("condestinatarios [" + condestinatarios + "]");
				log.debug("concopias [" + concopias + "]");
			}

			objNuevoDocumento.setEnumerarDocumento(StringUtil.isEmpty(enumerardocumento) ? false : true);
			objNuevoDocumento.setTiponumeracion(tiponumeracion);
			//FIXME revisar donde se llena esto
			//objNuevoDocumento.setAutor(this.autor);

			objNuevoDocumento.setNumeroDocumento(this.nrodocumento) ;
			objNuevoDocumento.setEstado(Constantes.ESTADO_ACTIVO);
			getDocumentoService().saveDocumento(objNuevoDocumento);
			trazabilidaddocumentoService.saveTrazabilidadDocumento(objNuevoDocumento,origen, false, false);
			archivo.setEstadoDigitalizacion(Archivo.ESTADO_DISPONIBLE);
			archivo.setFechaCreacion(new Date());
			archivo.setDocumento(objNuevoDocumento);
			String nombre[]=(ruta.replace(File.separator,"&").split("&"));
			archivo.setNombre(nombre[nombre.length-1]);
			archivo.setRutaArchivoPdf(ruta);
			//archivo.setRutaAlfresco(RepositorioServiceWebservice.RUTA_PADRE_EXPEDIENTE+"/"+objNuevoDocumento.getExpediente().getNroexpediente()+"/"+nombre[nombre.length-1]);
			archivo.setRutaAlfresco(repositorioService.obtenerRutaExpediente(objNuevoDocumento.getExpediente())+nombre[nombre.length-1]);
			log.debug(" antes de guardar ");
			log.debug(" %% antes de guardar el archivo ");
			archivoService.saveArchivo(archivo);
			try{
				plantillaService.registrarAuditoriaArchivo(origen,doc,archivo,Constantes.TA_Adjuntar,Constantes.TM_UserFinal,Constantes.TO_Registrar);
			}catch(Exception exd){
				log.error("No se pudo registrar la auditoria del archivo",exd);
			}
			log.debug(" %% despues de guardar el archivo ");
			// ahora trato de subirlo al repositorio
			log.debug("subir el archivo del documento :"+objNuevoDocumento.getIdDocumento());
			//repositorioService.subirArchivosTransformadosARepositorio(objNuevoDocumento.getIdDocumento(),false);
			/* aqui para subir archivos ingresados por el adjuntar */
			List<org.ositran.utils.ArchivoTemporal> l=(List<org.ositran.utils.ArchivoTemporal>) session.get("uploaded_list2");
			if(l!=null){
				log.debug(" %% antes de guardar temporal size : "+l.size());
				int y=1;
				for(org.ositran.utils.ArchivoTemporal arch : l){
					log.debug(" %% entro en iteracuion: "+y);
					Documento objNuevoDocumento2=new Documento();
					objNuevoDocumento2.setTipoDocumento(tipodocumentoService.findByNombre("Otros"));
					objNuevoDocumento2.setPropietario(origen);
					objNuevoDocumento2.setExpediente(doc.getExpediente());
					objNuevoDocumento2.setAsunto(arch.getFArchivo().getName());
					objNuevoDocumento2.setAccion(getAccionService().findByNombre("registrar"));
					objNuevoDocumento2.setPrincipal(Constantes.DOCUMENTO_NO_PRINCIPAL);
					objNuevoDocumento2.setFechaAccion(new Date());
					objNuevoDocumento2.setNumeroFolios(1);
					objNuevoDocumento2.setFechaCreacion(new Date());
					objNuevoDocumento2.setEstado(Constantes.ESTADO_ACTIVO);
					getDocumentoService().saveDocumento(objNuevoDocumento2);
					trazabilidaddocumentoService.saveTrazabilidadDocumento(objNuevoDocumento2,origen, false, false);
					// Fin
					// getSrvArchivo().uploadFile(arch, objNuevoDocumento, y++);
					log.debug(" %% uploaded antes de grabar : "+y);
					//archivoService.guardarArchivoTemporal(arch,objNuevoDocumento2,y++, origen, "ES_nombrePDFprincipal");
					log.debug(" %% uploaded despues de grabar : "+y);
					//repositorioService.subirArchivosTransformadosARepositorio(objNuevoDocumento2.getIdDocumento(),false);
					try{
						plantillaService.registrarAuditoriaArchivos(origen,objNuevoDocumento2,arch,Constantes.TA_Adjuntar,Constantes.TM_UserFinal,Constantes.TO_Registrar);
					}catch(Exception exd){
						log.error("No se pudo registrar la auditoria de los archivos.",exd);
					}
				}
			}
			if(idArchivoPendiente!=null){
				archivopendiente=archivoPendienteService.findByIdarchivopendiente(idArchivoPendiente);
				if(archivopendiente!=null && archivopendiente.getIdArchivoPendiente()!=null){
					archivoPendienteService.deleteArchivopendiente(archivopendiente.getIdArchivoPendiente());
				}
			}
			addActionMessage(" Archivo Subido Correctamente ");
			this.mensaje= messageSource.getMessage(MessagePropertiesEnum.NRODOC_GENERATED, new String[] {objNuevoDocumento.getNumeroDocumento()});
			this.cerrar="ok";
			try{
				plantillaService.registrarAuditoriaNuevoDocumento(origen,doc);
			}catch(Exception exd){
				log.error("No se pudo registrar la auditoria del nuevo documento",exd);
			}
			//srvNotificacion.informarViaNotifAndMail(origen, objNuevoDocumento, Constantes.CONFIGNOTIFMAIL_INGRESO_DOCUMENTO_USERFINAL, Constantes.TIPO_NOTIFICACION_NUEVO_DOCUMENTO, nombrePC);
		}
		log.debug("returning rout : "+ruta);
		if(ruta!=null&&ruta.equalsIgnoreCase("edit")){
			return ruta;
		}
		return "new";
	}

	public String verDocumentoPrevio() throws IOException{

		Map<String,Object> session=ActionContext.getContext().getSession();
		Usuario user=(Usuario) session.get(Constantes.SESSION_USUARIO);
		log.debug(" aquiiiiiiiii :"+tipoDocumento)  ;
		if(tipoDocumento!=null && tipoDocumento.intValue()!=0){

			log.debug(" %%%%% verDocumentoPrevio  "+tipoDocumento);
			List<Campo> campos=plantillaService.listCamposByTipoPlantilla(tipoDocumento);

			String nombreArchivo="";
			String username=user.getApellidos()+user.getNombres();
			String tipoPlantilla="";

			for(Campo c : campos){

				String valor=request.getParameter("valor"+c.getIdCampo());
				valor = (valor==null ? "" : valor);
				valor  =  Base64.decode(valor);
				//log.debug("---> valor"+c.getIdcampo()+":"+valor);


				log.debug("^^^^^ valor"+c.getIdCampo()+":"+valor);
				c.setValor(valor);
				nombreArchivo=c.getPlantilla().getDescripcion()+new SimpleDateFormat("yyyyMMddHHmmsss").format(new Timestamp(System.currentTimeMillis()));
				tipoPlantilla=c.getPlantilla().getDescripcion();
			}
			if(StringUtils.isBlank(tipoPlantilla)){
				tipoPlantilla=plantillaService.findByIdplantilla(tipoDocumento).getDescripcion();
			}
			log.debug("NOMBRE FINAL DE LA PLANTILLA :"+tipoPlantilla);
			log.debug("MARCA DE AGUA "+request.getParameter("marcaDeAgua"));
			tipoPlantilla+=((request.getParameter("marcaDeAgua")!=null&&(!request.getParameter("marcaDeAgua").equals("false"))) ? request.getParameter("marcaDeAgua") : "");
			log.debug("NOMBRE FINAL DE LA PLANTILLA :"+tipoPlantilla);
			String nommbreOriginal=nombreArchivo;
			log.debug(" ###  idDoc : "+idDocumento);
			String nombrecompleto=plantillaService.generarArchivo(nombreArchivo,campos,tipoPlantilla,username,idDocumento);
			log.debug(" ###  genero nombre completo : "+nombrecompleto);

			// para ambos metodos
			this.rutaArchivo=nombrecompleto;
			this.nombreReal = nommbreOriginal+".doc";
			request.getSession().setAttribute("rutaArchivo",nombrecompleto);
			request.getSession().setAttribute("asunto",this.asunto);
			request.getSession().setAttribute("fechalimite",this.atenderAntesDe);

			// a partir de aca sera en otro metodo                        try{
			WebFileStreamUtil.sendDocumentToResponse(response, AplicationType.RTF, nombrecompleto, nombreReal);

		}
		this.refrescar = null ;
		this.viewFile = "OK" ;
		return  null;
	}

	public String  verFile(){
		String nombreFinal=nombreReal + ".doc ";
		WebFileStreamUtil.sendDocumentToResponse(response, AplicationType.RTF, rutaArchivo, nombreFinal);
		return null;
	}

	public String verDocumentoPrevioN() throws IOException{
		Map<String,Object> session=ActionContext.getContext().getSession();
		// AuthenticationDetails authenticationDetails=(AuthenticationDetails)
		// session.get("objAD");
		// Integer iIdUsuario=(Integer) session.get("idusuario");
		Usuario user=(Usuario) session.get(Constantes.SESSION_USUARIO);
		//log.debug("Usuario con ID ["+objUsuario.getIdusuario()+"]");
		//Usuario user=usuarioService.findByIdUsuario(objUsuario.getIdusuario());
		// decomentar para base 64
		// String valores = (String)request.getParameter("data");
		// log.debug("  %%%%%  Data 64 code: "+valores);
		// valores = Base64Coder.decodeString(valores) ;
		// log.debug("  %%%%%  Data decoded: "+valores);
		// this.informacion = recuperarValores(valores) ;
		// this.idtipodocumento = new Integer(
		// (String)this.informacion.get("idtipodocumento") ) ;
		// log.debug(" //////// aquiii") ;
		// Plantilla plantilla ;
		idtipoDocumento=new Integer(request.getParameter("idtipodocumento"));
		if(idtipoDocumento!=null&&idtipoDocumento.intValue()!=0){
			log.debug(" %%%%% verDocumentoPrevio  "+idtipoDocumento);
			List<Campo> campos=plantillaService.listCamposByTipoPlantilla(idtipoDocumento);
			// List secciones =
			// plantillaService.listSeccionesByTipoPlantilla(idtipodocumento.toString())
			// ;
			String nombreArchivo="";
			String username=user.getApellidos()+user.getNombres();
			// Iterator i=campos.iterator();
			String tipoPlantilla="";
			for(Campo c : campos){

				// decomentar para base 64
				// String valor =
				// (String)this.informacion.get("valor"+c.getIdcampo());
				String valor=request.getParameter("valor"+c.getIdCampo());
				valor = (valor==null ? "" : valor);
				valor  =  Base64.decode(valor);
				//log.debug("---> valor"+c.getIdcampo()+":"+valor);
				//valor = new String(valor.getBytes(),"UTF-8")  ;
				log.debug("===> valor"+c.getIdCampo()+":"+valor);
				c.setValor(valor);
				nombreArchivo=c.getPlantilla().getDescripcion()+new SimpleDateFormat("yyyyMMddHHmmsss").format(new Timestamp(System.currentTimeMillis()));
				tipoPlantilla=c.getPlantilla().getDescripcion();
			}
			if(StringUtils.isBlank(tipoPlantilla)){
				try{
					tipoPlantilla=plantillaService.findByIdplantilla(this.idtipoDocumento).getDescripcion();
				}catch(Exception e){
					log.error(e.getMessage(), e);
				}
			}
			log.debug("NOMBRE FINAL DE LA PLANTILLA :"+tipoPlantilla);
			log.debug("MARCA DE AGUA "+request.getParameter("marcaDeAgua"));
			tipoPlantilla+=((request.getParameter("marcaDeAgua")!=null&&(!request.getParameter("marcaDeAgua").equals("false"))) ? request.getParameter("marcaDeAgua") : "");
			log.debug("NOMBRE FINAL DE LA PLANTILLA :"+tipoPlantilla);
			String nommbreOriginal=nombreArchivo;
			log.debug(" ###  idDoc : "+idDocumento);
			String nombrecompleto=plantillaService.generarArchivo(nombreArchivo,campos,tipoPlantilla,username,idDocumento);
			log.debug(" ###  genero nombre completo : "+nombrecompleto);
			this.rutaArchivo=nombrecompleto;
			request.getSession().setAttribute("rutaArchivo",nombrecompleto);
			request.getSession().setAttribute("asunto",this.asunto);
			request.getSession().setAttribute("fechalimite",this.atenderAntesDe);

			String nombreFinal=nommbreOriginal + ".doc ";
			WebFileStreamUtil.sendDocumentToResponse(response, AplicationType.RTF, nombrecompleto, nombreFinal);

		}
		return null;
	}


	public Map<String,String> recuperarValores(String data){
		Map<String,String> mapa=new HashMap<String,String>();
		String valores=data.replace("%3F","");
		String[] vals=valores.split("%26");
		for(int y=0;y<vals.length;y++){
			String vs[]=vals[y].split("%3D");
			log.debug("###### "+vs[0]+":"+vs[1]);
			mapa.put(vs[0],vs[1]);
		}
		return mapa;
	}

	private List<String> condestinatarios;
	private List<String> concopias;




	public List<String> getCondestinatarios() {
		return condestinatarios;
	}

	public void setCondestinatarios(List<String> condestinatarios) {
		this.condestinatarios = condestinatarios;
	}

	public List<String> getConcopias() {
		return concopias;
	}

	public void setConcopias(List<String> concopias) {
		this.concopias = concopias;
	}

	@SuppressWarnings("unchecked")
	public String guardarPendiente() throws UnsupportedEncodingException{

		// AuthenticationDetails authenticationDetails=(AuthenticationDetails)
		// session.get("objAD");
		// Integer iIdUsuario=(Integer) session.get("idusuario");

		// Usuario user=usuarioService.findByIdUsuario(iIdUsuario);
		// log.debug(" //////// aquiii user "+user.getApellidos());
		// Plantilla plantilla ;
		if(tipoDocumento!=null && tipoDocumento.intValue()!=0){

			HttpServletRequest parameters=ServletActionContext.getRequest();

			Map<String,Object> session=ActionContext.getContext().getSession();
			Usuario user=(Usuario) session.get(Constantes.SESSION_USUARIO);
			log.debug(" %%%%% guardando documento previo  "+tipoDocumento);
			List<Campo> campos=plantillaService.listCamposByTipoPlantilla(tipoDocumento);
			Documento doc=documentoService.findByIdDocumento(idDocumento);
			Plantilla p=plantillaService.findByIdplantilla(tipoDocumento);
			// if(this.getAtenderAntesDe()!=null){
			// doc.setFechaLimiteAtencion( this.getAtenderAntesDe() );
			// }
			// if(this.getAsunto()!=null){
			// doc.setAsunto( this.getAsunto() );
			// }
			// log.debug(" values fech:"+this.getAtenderAntesDe()+"   asunt:"+this.getAsunto());
			// this.documentoService.saveDocumento(doc);
			ArchivoPendiente ap= new ArchivoPendiente() ;
			try {
				log.debug(" parseando "+this.fechadocumentopendiente) ;
				ap.setFechaDocumento(new SimpleDateFormat("yyyy-MM-dd").parse(parameters.getParameter("fechadocumentopendiente")) )	;
			}catch(Exception  ex ) {
				log.error(ex.getMessage(), ex);
			}

			ap.setDocumento(doc);
			ap.setEnumerarDocumento(parameters.getParameter("enumerarDocumento"));
			ap.setAutor(parameters.getParameter("autor"));
			try {
				this.firmante = new Integer( parameters.getParameter("firmante") ) ;
			}catch(Exception e) {
				log.error(e.getMessage(), e);
			}

			if(this.firmante!=null&&this.firmante!=0){
				Usuario firm =  usuarioService.findByIdUsuario(this.firmante);
				ap.setFirmante(firm) ;
			}
			ap.setNumeroDocumento(parameters.getParameter("nroDocumento")) ;


			ap.setPlantilla(p);
			List<Valorcampo> valores=new ArrayList<Valorcampo>();
			// ap.se
			// log.debug(" before saving archivoPendiente ... ");
			// log.debug(" after  saving archivoPendiente ... ok  ");

			//log.debug("////// enumeration size  "+parameters.size());
			/*
			 * Iterator ee=parameters.keySet().iterator(); while(ee.hasNext()){
			 * Object code=ee.next();
			 * log.debug(code+":"+parameters.get(
			 * code)+""+parameters.get
			 * (code).toString()+" class :"+parameters.get(code).getClass()); }
			 */
			for(Campo c : campos){
				String vals=parameters.getParameter("valor"+c.getIdCampo());
				String valor=(vals!=null ? vals : "");
				valor  =  Base64.decode(valor);
				valor = new String(valor.getBytes(),"UTF-8")  ;

				c.setValor(valor);
				// log.debug(" !!!!!! valor"+c.getIdcampo()+": '"+c.getValor().toString()+"'");
				Valorcampo vc=new Valorcampo();
				vc.setCampo(c);
				vc.setArchivopendiente(ap);
				vc.setValor(c.getValor());
				valores.add(vc);
			}
			//String asunto = parameters.getParameter("asunto");
			ap.setAsunto(this.asunto);
			log.debug("asunto :"+this.asunto) ;
			//log.debug("asunto2 :"+this.asunto) ;
			ap.setValoresCampo(valores);
			ap.setUsuario(user);
			List<org.ositran.utils.ArchivoTemporal> l=(List<org.ositran.utils.ArchivoTemporal>) session.get("uploaded_list2");
			if(l!=null){
				List<ArchivoTemporal> lista=new ArrayList<ArchivoTemporal>();
				for(org.ositran.utils.ArchivoTemporal arch : l){
					ArchivoTemporal archdest=new ArchivoTemporal();
					archdest.setRuta(arch.getFArchivo().getPath());
					archdest.setArchivoPendiente(ap);
					lista.add(archdest);
				}
				ap.setArchivosTemporales(lista);
			}
			ap.setEstado(Constantes.DOC_PENDIENTE_DOCUMENTO_EXISTENTE);
			log.debug(" before saving all archivoPendiente ... "+ap.getValoresCampo().size());
			getArchivoPendienteService().saveArchivopendiente(ap,null,null);
			log.debug(" after  saving all archivoPendiente ... ok  ");
			this.setArchivopendiente(archivopendiente);
			this.setCerrar("ok");
		}
		log.debug("returning rout : "+ruta);
		if(ruta!=null&&ruta.equalsIgnoreCase("edit")){
			return ruta;
		}
		return "new";
	}

	public String verEdicion(){


		log.debug(" retrieving archivoPendiente : "+this.getIdArchivoPendiente());
		archivopendiente=archivoPendienteService.findByIdarchivopendiente(this.getIdArchivoPendiente());
		archivopendiente.setStrTipoDocumento(archivopendiente.getPlantilla().getDescripcion());
		Documento doc=archivopendiente.getDocumento();
		idDocumento=doc.getIdDocumento();
		Map<String,Object> session=ActionContext.getContext().getSession();
		Integer[] arrIdDoc= {idDocumento};
		session.put("arrIdDoc", arrIdDoc);
		log.debug("verEdicion.asunto: "+archivopendiente.getAsunto());
		asunto=archivopendiente.getAsunto();
		atenderAntesDe = doc.getFechaLimiteAtencion();
		// archivoPendienteService.
		List<ArchivoTemporal> temporales=archivopendiente.getArchivosTemporales();
		List<org.ositran.utils.ArchivoTemporal> files=new ArrayList<org.ositran.utils.ArchivoTemporal>();
		for(ArchivoTemporal at : temporales){
			org.ositran.utils.ArchivoTemporal att=new org.ositran.utils.ArchivoTemporal(at.getRuta(),new File(at.getRuta()));
			files.add(att);
		}

		session.put(Constantes.SESSION_UPLOAD_LIST,files);
		return "edit";
	}

	@SuppressWarnings("unchecked")
	public String actualizarPendiente() throws UnsupportedEncodingException{
		if(idArchivoPendiente!=null){
			archivopendiente=archivoPendienteService.findByIdarchivopendiente(idArchivoPendiente);
			Map<String,Object> session=ActionContext.getContext().getSession();
			// AuthenticationDetails authenticationDetails=(AuthenticationDetails)
			// session.get("objAD");
			//Integer iIdUsuario=(Integer) session.get("idusuario");
			Usuario user=(Usuario) session.get(Constantes.SESSION_USUARIO);
			log.debug(" //////// aquiii user "+user.getApellidos());
			// Plantilla plantilla ;
			if(tipoDocumento!=null&&tipoDocumento.intValue()!=0){
				HttpServletRequest parameters=ServletActionContext.getRequest();

				log.debug(" %%%%% guardando documento previo  "+tipoDocumento);
				List<Campo> campos=plantillaService.listCamposByTipoPlantilla(tipoDocumento);
				Documento doc=documentoService.findByIdDocumento(this.idDocumento);
				Plantilla p=plantillaService.findByIdplantilla(this.tipoDocumento);
				if(this.getAtenderAntesDe()!=null){
					doc.setFechaLimiteAtencion(this.getAtenderAntesDe());
				}
				if(this.getAsunto()!=null){
					doc.setAsunto(this.getAsunto());
				}

				log.debug(" values fech:"+this.getAtenderAntesDe()+"   asunt:"+this.getAsunto());
				this.documentoService.saveDocumento(doc);
				getArchivoPendienteService().deleterchildren(this.getArchivopendiente().getIdArchivoPendiente());
				ArchivoPendiente ap=this.getArchivopendiente();
				try {
					ap.setFechaDocumento(new SimpleDateFormat("yyyy-MM-dd").parse(parameters.getParameter("fechadocumentopendiente")) )	;
				}catch(Exception  ex ) {
					log.error(ex.getMessage(), ex);
				}
				ap.setDocumento(doc);
				ap.setEnumerarDocumento(parameters.getParameter("enumerarDocumento"));
				ap.setAutor(parameters.getParameter("autor"));
				try {
					this.firmante = new Integer( parameters.getParameter("firmante") ) ;
				}catch(Exception ex) {
					log.error(ex.getMessage(), ex);
				}
				if(this.firmante!=null&&this.firmante!=0){
					Usuario firm =  usuarioService.findByIdUsuario(this.firmante);
					ap.setFirmante(firm) ;
				}
				ap.setNumeroDocumento(parameters.getParameter("nroDocumento")) ;


				ap.setPlantilla(p);
				List<Valorcampo> valores=new ArrayList<Valorcampo>();
				//log.debug(" ////// enumeration size  "+parameters.size());
				/*
				 * Iterator ee=parameters.keySet().iterator(); while(ee.hasNext()){
				 * Object code=ee.next();
				 * log.debug(code+":"+parameters.get(
				 * code)+""+parameters.get
				 * (code).toString()+" class :"+parameters.get(code).getClass()); }
				 */
				for(Campo c : campos){
					if(c!=null){
						String valor=parameters.getParameter("valor"+c.getIdCampo());
						valor  =  Base64.decode(valor);
						log.debug("---> valor"+c.getIdCampo()+":"+valor);
						valor = new String(valor.getBytes(),"UTF-8")  ;

						c.setValor(valor);
						log.debug(" !!!!!! valor"+c.getIdCampo()+": '"+c.getValor().toString()+"'");
						Valorcampo vc=new Valorcampo();
						vc.setCampo(c);
						vc.setArchivopendiente(ap);
						vc.setValor(c.getValor().toString());
						valores.add(vc);
					}
				}
				// ap.setValorcampoList(valores);
				ap.setAsunto(this.asunto);
				ap.setUsuario(user);
				List<org.ositran.utils.ArchivoTemporal> l=(List<org.ositran.utils.ArchivoTemporal>) session.get(Constantes.SESSION_UPLOAD_LIST);
				List<ArchivoTemporal> lista=new ArrayList<ArchivoTemporal>();
				if(l!=null){
					for(org.ositran.utils.ArchivoTemporal arch : l){
						ArchivoTemporal archdest=new ArchivoTemporal();
						archdest.setRuta(arch.getFArchivo().getPath());
						archdest.setArchivoPendiente(ap);
						lista.add(archdest);
					}
				}
				// ap.setArchivotemporalList(lista);
				ap.setEstado(Constantes.DOC_PENDIENTE_DOCUMENTO_EXISTENTE);
				// log.debug(" before saving all archivoPendiente ... " +
				// ap.getValorcampoList().size());
				getArchivoPendienteService().saveArchivopendiente(ap,lista,valores);
				//
				log.debug(" after  saving all archivoPendiente ... ok  ");
				this.setArchivopendiente(ap);
				this.setCerrar("ok");
			}else{
				log.debug(" //////// tipoDocumento!=null&&tipoDocumento.intValue()!=0 "+(tipoDocumento!=null&&tipoDocumento.intValue()!=0));
			}
			/* */
			return "edit";
		}
		return Action.ERROR;
	}

	@SuppressWarnings("unchecked")
	public String upload() throws Exception{
		// setLstRadio(getTipoidentificacionService().getTipoIdentificacionMap()
		// );
		// this.setOcultar("NO");
		try{
			Map<String,Object> session=ActionContext.getContext().getSession();
			// AuthenticationDetails objAD=(AuthenticationDetails)
			// session.get("objAD");
			// Documento objDocumento=null;
			Integer iContador=(Integer) session.get("contador");
			// Integer iIdUsuario=(Integer) session.get("idusuario");
			// String sRol=(String) session.get("rol");
			String strTempo=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_TEMPORAL);
			log.debug("ENTRO AL UPLOAD ["+upload+"]");
			fullFileName=ServletActionContext.getServletContext().getRealPath("/")+strTempo+uploadFileName;
			log.debug("uploadFileName ["+uploadFileName+"]");
			log.debug("fullFileName ["+fullFileName+"]");
			File theFile=new File(fullFileName);
			// copiar a uno temporal ... en este caso sera RealPah/upload/ para
			// aprovechar
			FileUtils.copyFile(upload,theFile);
			org.ositran.utils.ArchivoTemporal at=new org.ositran.utils.ArchivoTemporal(uploadFileName,theFile);
			if(iContador==null){
				iContador=1;
			}else{
				iContador++;
			}
			session.put("contador",iContador);
			List<org.ositran.utils.ArchivoTemporal> l=(List<org.ositran.utils.ArchivoTemporal>) session.get("uploaded_list2");
			if(l==null){
				l=new ArrayList<org.ositran.utils.ArchivoTemporal>();
			}
			l.add(at);
			session.put("uploaded_list2",l);
			if(idDocumento!=null&&idDocumento.intValue()!=0){
				log.debug(" %%%%% "+idDocumento);
				documento=documentoService.findByIdDocumento(idDocumento);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return "new";
	}

	public ArchivopendienteService getArchivoPendienteService(){
		return archivoPendienteService;
	}

	public void setArchivoPendienteService(ArchivopendienteService archivoPendienteService){
		this.archivoPendienteService=archivoPendienteService;
	}

	/**
	 * @return the idDocumento
	 */
	public Integer getIdDocumento(){
		return idDocumento;
	}

	/**
	 * @param idDocumento
	 *            the idDocumento to set
	 */
	public void setIdDocumento(Integer idDocumento){
		this.idDocumento=idDocumento;
	}

	/**
	 * @return the documento
	 */
	public Documento getDocumento(){
		return documento;
	}

	/**
	 * @param documento
	 *            the documento to set
	 */
	public void setDocumento(Documento documento){
		this.documento=documento;
	}

	/**
	 * @return the documentoService
	 */
	public DocumentoService getDocumentoService(){
		return documentoService;
	}

	/**
	 * @param documentoService
	 *            the documentoService to set
	 */
	public void setDocumentoService(DocumentoService documentoService){
		this.documentoService=documentoService;
	}

	/**
	 * @return the idproceso
	 */
	public Integer getIdproceso(){
		return idproceso;
	}

	/**
	 * @param idproceso
	 *            the idproceso to set
	 */
	public void setIdproceso(Integer idproceso){
		this.idproceso=idproceso;
	}

	public Integer getIdtipoDocumento() {
		return idtipoDocumento;
	}

	public void setIdtipoDocumento(Integer idtipoDocumento) {
		this.idtipoDocumento = idtipoDocumento;
	}

	/**
	 * @return the idPlantilla
	 */
	public Integer getIdPlantilla(){
		return idPlantilla;
	}

	/**
	 * @param idPlantilla
	 *            the idPlantilla to set
	 */
	public void setIdPlantilla(Integer idPlantilla){
		this.idPlantilla=idPlantilla;
	}

	/**
	 * @return the tipoDocumento
	 */
	public Integer getTipoDocumento(){
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento
	 *            the tipoDocumento to set
	 */
	public void setTipoDocumento(Integer tipoDocumento){
		this.tipoDocumento=tipoDocumento;
	}

	public String getRutaArchivo(){
		return rutaArchivo;
	}

	/**
	 * @param rutaArchivo
	 *            the rutaArchivo to set
	 */
	public void setRutaArchivo(String rutaArchivo){
		this.rutaArchivo=rutaArchivo;
	}

	/**
	 * @return the usuarioService
	 */
	public UsuarioService getUsuarioService(){
		return usuarioService;
	}

	/**
	 * @param usuarioService
	 *            the usuarioService to set
	 */
	public void setUsuarioService(UsuarioService usuarioService){
		this.usuarioService=usuarioService;
	}

	/**
	 * @return the repositorioService
	 */
	public RepositorioService getRepositorioService(){
		return repositorioService;
	}

	/**
	 * @param repositorioService
	 *            the repositorioService to set
	 */
	public void setRepositorioService(RepositorioService repositorioService){
		this.repositorioService=repositorioService;
	}

	/**
	 * @return the cerrar
	 */
	public String getCerrar(){
		return cerrar;
	}

	/**
	 * @param cerrar
	 *            the cerrar to set
	 */
	public void setCerrar(String cerrar){
		this.cerrar=cerrar;
	}

	/**
	 * @return the archivopendiente
	 */
	public ArchivoPendiente getArchivopendiente(){
		return archivopendiente;
	}

	/**
	 * @param archivopendiente
	 *            the archivopendiente to set
	 */
	public void setArchivopendiente(ArchivoPendiente archivopendiente){
		this.archivopendiente=archivopendiente;
	}

	/**
	 * @return the idArchivoPendiente
	 */
	public Integer getIdArchivoPendiente(){
		return idArchivoPendiente;
	}

	/**
	 * @param idArchivoPendiente
	 *            the idArchivoPendiente to set
	 */
	public void setIdArchivoPendiente(Integer idArchivoPendiente){
		this.idArchivoPendiente=idArchivoPendiente;
	}

	/**
	 * @return the atenderAntesDe
	 */
	public Date getAtenderAntesDe(){
		return atenderAntesDe;
	}

	/**
	 * @param atenderAntesDe
	 *            the atenderAntesDe to set
	 */
	public void setAtenderAntesDe(Date atenderAntesDe){
		this.atenderAntesDe=atenderAntesDe;
	}

	/**
	 * @return the asunto
	 */
	public String getAsunto(){
		return asunto;
	}

	/**
	 * @param asunto
	 *            the asunto to set
	 */
	public void setAsunto(String asunto){
		this.asunto=asunto;
	}

	public NotificacionService getSrvNotificacion(){
		return srvNotificacion;
	}

	public void setSrvNotificacion(NotificacionService srvNotificacion){
		this.srvNotificacion=srvNotificacion;
	}

	public String getMensaje(){
		return mensaje;
	}

	public void setMensaje(String mensaje){
		this.mensaje=mensaje;
	}

	public String getNombreReal() {
		return nombreReal;
	}

	public void setNombreReal(String nombreReal) {
		this.nombreReal = nombreReal;
	}

	public String getRefrescar() {
		return refrescar;
	}

	public void setRefrescar(String refrescar) {
		this.refrescar = refrescar;
	}

	public String getViewFile() {
		return viewFile;
	}

	public void setViewFile(String viewFile) {
		this.viewFile = viewFile;
	}

	public Integer getFirmante() {
		return firmante;
	}

	public void setFirmante(Integer firmante) {
		this.firmante = firmante;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public boolean getEnumerarDocumento() {
		return enumerarDocumento;
	}

	public void setEnumerarDocumento(boolean enumerarDocumento) {
		this.enumerarDocumento = enumerarDocumento;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public String getFechadocumentopendiente() {
		return fechadocumentopendiente;
	}

	public void setFechadocumentopendiente(String fechadocumentopendiente) {
		this.fechadocumentopendiente = fechadocumentopendiente;
	}

	public String getEnumerardocumento() {
		return enumerardocumento;
	}

	public void setEnumerardocumento(String enumerardocumento) {
		this.enumerardocumento = enumerardocumento;
	}

	public String getTiponumeracion() {
		return tiponumeracion;
	}

	public void setTiponumeracion(String tiponumeracion) {
		this.tiponumeracion = tiponumeracion;
	}

	public void setMessageSource(SigedMessageSource ms) {
		this.messageSource = ms;
	}
}