package org.ositran.services;

import gob.ositran.siged.config.SigedProperties;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import net.sourceforge.rtf.RTFTemplate;
import net.sourceforge.rtf.helper.RTFTemplateBuilder;
import org.apache.log4j.Logger;
import org.ositran.daos.AuditoriaDAO;
import org.ositran.daos.PlantillaDAO;
import org.ositran.daos.ValorcampoDAO;
import org.ositran.utils.ArchivoTemporal;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.Auditoria;
import com.btg.ositran.siged.domain.Campo;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Plantilla;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Valorcampo;

public class PlantillaServiceImpl implements PlantillaService{
	
	private PlantillaDAO plantillaDAO;
	private ValorcampoDAO valorCampoDao;
	public static String USER_NAME="&USER_NAME";
	public static String SYSDATE="&SYSDATE";
	public static String IZQUIERDA="IZ";
	public static String DERECHA="DE";
	public static String CENTRADO="CE";
	public static String JUSTIFICADO="JA";
	
	private AuditoriaDAO auditoriaDao;
	private static Logger log=Logger.getLogger(PlantillaServiceImpl.class);

	public PlantillaServiceImpl(PlantillaDAO plantillaDAO){
		this.plantillaDAO=plantillaDAO;
	}

	public List<Campo> listCamposByTipoPlantilla(Integer tipoPlantilla){
		List<Campo> campos=plantillaDAO.listCamposByTipoPlantilla(tipoPlantilla);
		if(campos!=null){
			for(Campo campo : campos){
				List<Valorcampo> valores=valorCampoDao.getPorCampo(campo.getIdCampo());
				campo.setValoresCampo(valores);
			}
		}
		return campos;
	}

	@SuppressWarnings("unchecked")
	public List<Plantilla> listSeccionesByTipoPlantilla(String tipoPlantilla){
		return plantillaDAO.listSeccionesByTipoPlantilla(tipoPlantilla);
	}

	public List<Plantilla> findAll(){
		return plantillaDAO.findAll();
	}

	public void saveArchivo(String nombreArchivo){
	// TODO aqui guardar el archivo creado
	}

	public Plantilla findByIdplantilla(Integer idplantilla){
		return plantillaDAO.findByIdplantilla(idplantilla);
	}

	public String generarArchivo(String nombreArhivo,List<Campo> campos,String tipoPlantilla,String nombreUsuario,Integer idDocumento){
		String archivo="";
		String base=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_TEMPORAL_ALFRESCO);
		int x=0;
		File f;
		do{
			Date d=new Date();
			archivo="["+(idDocumento==null ? "" : "idDocumento")+"_"+new SimpleDateFormat("yyyyMMddHHmm").format(d)+"_"+x+++"]"+nombreArhivo;
			f=new File(base+File.separator+archivo+(x-1)+".doc");
		}while(f.exists());
		nombreArhivo=base+File.separator+archivo+(x-1)+".doc";
		String rtfSource=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_PLANTILLAS)+File.separator+tipoPlantilla.toUpperCase()+".rtf";
		String rtfTarget=nombreArhivo;
		// String base = ValoresProperties.getProperty("folder.temporal") ;
		try{
			// 1. Get default RTFtemplateBuilder
			RTFTemplateBuilder builder=RTFTemplateBuilder.newRTFTemplateBuilder();
			// 2. Get RTFtemplate with default Implementation of template engine
			// (Velocity)
			RTFTemplate rtfTemplate=builder.newRTFTemplate();
			// 3. Set the RTF model source
			rtfTemplate.setTemplate(new File(rtfSource));
			// 4. Put the context
			Locale loc=new Locale("es");
			rtfTemplate.put("date",new SimpleDateFormat("dd MMMM yyyy",loc).format(new Date()));
			for(Campo c : campos){
				
				log.debug(" c.getCodigo() "+c.getCodigo()+" c.getValor() "+c.getValor());
				rtfTemplate.put(c.getCodigo(),getEncodedRTFString(c.getValor(),"ISO8859-1"));
			} 
			// 5. Merge the RTF source model and the context
			rtfTemplate.merge(rtfTarget);
		}catch(Exception e){
			log.error(e) ;
		}
		return nombreArhivo;
	}
 public static String getEncodedRTFString(String sourceStr, String charSet) throws Exception {  
       return java.net.URLEncoder.encode(sourceStr, charSet).  
	               replaceAll("%", "\\\\'").replaceAll("\\+", " ");  
   } 
	/**
	 * @param plantillaDAO
	 *            the plantillaDAO to set
	 */
	public void setPlantillaDAO(PlantillaDAO plantillaDAO){
		this.plantillaDAO=plantillaDAO;
	}

	public void setValorCampoDao(ValorcampoDAO valorCampoDao){
		this.valorCampoDao=valorCampoDao;
	}

	@Override
	@Transactional
	public void registrarAuditoriaArchivo(Usuario usuario,Documento doc,Archivo archivo,String tipoauditoria,String modulo,String opcion){
		/*******************************************************/
		// Registrando la auditoria del Expediente
		/*******************************************************/
		/*******************************************************/
		// Registrando la auditoria del Expediente
		/*******************************************************/
		Auditoria ObjAuditoriaArchivo=new Auditoria();
		ObjAuditoriaArchivo.setTipoAuditoria(tipoauditoria);
		ObjAuditoriaArchivo.setModulo(modulo);
		ObjAuditoriaArchivo.setOpcion(opcion);
		ObjAuditoriaArchivo.setFechaAudioria(new Date());
		ObjAuditoriaArchivo.setUsuario(usuario.getUsuario());
		ObjAuditoriaArchivo.setCampo("Archivo");
		ObjAuditoriaArchivo.setNuevoValor(archivo.getNombre());
		ObjAuditoriaArchivo.setExpediente(doc.getExpediente());
		ObjAuditoriaArchivo.setDocumento(doc);
		auditoriaDao.SaveAuditoria(ObjAuditoriaArchivo);
	}

	public void setAuditoriaDao(AuditoriaDAO auditoriaDao){
		this.auditoriaDao=auditoriaDao;
	}

	@Override
	@Transactional
	public void registrarAuditoriaNuevoDocumento(Usuario usuario,Documento doc){
		/*******************************************************/
		// Registrando la auditoria del Expediente
		/*******************************************************/
		/*******************************************************/
		// Registrando la auditoria del Expediente
		/*******************************************************/
		Auditoria ObjAuditoriaExpediente=new Auditoria();
		ObjAuditoriaExpediente.setTipoAuditoria(Constantes.TA_RegistrarNvoDoc_UserFinal);
		ObjAuditoriaExpediente.setModulo(Constantes.TM_UserFinal);
		ObjAuditoriaExpediente.setOpcion(Constantes.TO_Registrar);
		ObjAuditoriaExpediente.setFechaAudioria(new Date());
		ObjAuditoriaExpediente.setUsuario(usuario.getUsuario());
		ObjAuditoriaExpediente.setCampo("Nro Expediente");
		ObjAuditoriaExpediente.setNuevoValor(doc.getExpediente().getNroexpediente());
		ObjAuditoriaExpediente.setExpediente(doc.getExpediente());
		auditoriaDao.SaveAuditoria(ObjAuditoriaExpediente);
		/*******************************************************/
		// Registrando la auditoria del Proceso
		/*******************************************************/
		Auditoria ObjAuditoriaProceso=new Auditoria();
		ObjAuditoriaProceso.setTipoAuditoria(Constantes.TA_RegistrarNvoDoc_UserFinal);
		ObjAuditoriaProceso.setModulo(Constantes.TM_UserFinal);
		ObjAuditoriaProceso.setOpcion(Constantes.TO_Registrar);
		ObjAuditoriaProceso.setFechaAudioria(new Date());
		ObjAuditoriaProceso.setUsuario(usuario.getUsuario());
		ObjAuditoriaProceso.setCampo("Proceso");
		//ObjAuditoriaProceso.setNuevoValor(doc.getExpediente().getProceso().getNombre());
		ObjAuditoriaProceso.setExpediente(doc.getExpediente());
		auditoriaDao.SaveAuditoria(ObjAuditoriaProceso);
		/*******************************************************/
		// Registrando la auditoria del Area Destino
		/*******************************************************/
		Auditoria ObjAuditoriaAD=new Auditoria();
		ObjAuditoriaAD.setTipoAuditoria(Constantes.TA_RegistrarNvoDoc_UserFinal);
		ObjAuditoriaAD.setModulo(Constantes.TM_UserFinal);
		ObjAuditoriaAD.setOpcion(Constantes.TO_Registrar);
		ObjAuditoriaAD.setFechaAudioria(new Date());
		ObjAuditoriaAD.setUsuario(usuario.getUsuario());
		ObjAuditoriaAD.setCampo("Area Destino");
		//ObjAuditoriaAD.setNuevoValor(doc.getExpediente().getProceso().getResponsable().getUnidad().getNombre());
		ObjAuditoriaAD.setExpediente(doc.getExpediente());
		auditoriaDao.SaveAuditoria(ObjAuditoriaAD);
		/*******************************************************/
		// Registrando la auditoria del Destinatario
		/*******************************************************/
		Auditoria ObjAuditoriaDestinatario=new Auditoria();
		ObjAuditoriaDestinatario.setTipoAuditoria(Constantes.TA_RegistrarNvoDoc_UserFinal);
		ObjAuditoriaDestinatario.setModulo(Constantes.TM_UserFinal);
		ObjAuditoriaDestinatario.setOpcion(Constantes.TO_Registrar);
		ObjAuditoriaDestinatario.setFechaAudioria(new Date());
		ObjAuditoriaDestinatario.setUsuario(usuario.getUsuario());
		ObjAuditoriaDestinatario.setCampo("Destinatario");
		//ObjAuditoriaDestinatario.setNuevoValor(doc.getExpediente().getProceso().getResponsable().getNombres()+" - "+doc.getExpediente().getProceso().getResponsable().getApellidos());
		ObjAuditoriaDestinatario.setExpediente(doc.getExpediente());
		auditoriaDao.SaveAuditoria(ObjAuditoriaDestinatario);
		/*************************************************************************************************/
		// Registrando la Auditoria del Tipo Documento
		/**************************************************************************************************/
		Auditoria ObjAuditoriaTD=new Auditoria();
		ObjAuditoriaTD.setTipoAuditoria(Constantes.TA_RegistrarNvoDoc_UserFinal);
		ObjAuditoriaTD.setModulo(Constantes.TM_UserFinal);
		ObjAuditoriaTD.setOpcion(Constantes.TO_Registrar);
		ObjAuditoriaTD.setFechaAudioria(new Date());
		ObjAuditoriaTD.setUsuario(usuario.getUsuario());
		ObjAuditoriaTD.setCampo("Tipo Documento");
		ObjAuditoriaTD.setNuevoValor(doc.getTipoDocumento().getNombre());
		ObjAuditoriaTD.setExpediente(doc.getExpediente());
		ObjAuditoriaTD.setDocumento(doc);
		auditoriaDao.SaveAuditoria(ObjAuditoriaTD);
		/*******************************************************/
		// Registrando la Auditoria del Nro Documento
		/*******************************************************/
		Auditoria ObjAuditoriaND=new Auditoria();
		ObjAuditoriaND.setTipoAuditoria(Constantes.TA_RegistrarNvoDoc_UserFinal);
		ObjAuditoriaND.setModulo(Constantes.TM_UserFinal);
		ObjAuditoriaND.setOpcion(Constantes.TO_Registrar);
		ObjAuditoriaND.setFechaAudioria(new Date());
		ObjAuditoriaND.setUsuario(usuario.getUsuario());
		ObjAuditoriaND.setCampo("Nro Documento");
		ObjAuditoriaND.setNuevoValor(doc.getNumeroDocumento());
		ObjAuditoriaND.setExpediente(doc.getExpediente());
		ObjAuditoriaND.setDocumento(doc);
		auditoriaDao.SaveAuditoria(ObjAuditoriaND);
		/*******************************************************/
		// Registrando la Auditoria Nro Folio de Documento
		/*******************************************************/
		Auditoria ObjAuditoriaNFD=new Auditoria();
		ObjAuditoriaNFD.setTipoAuditoria(Constantes.TA_RegistrarNvoDoc_UserFinal);
		ObjAuditoriaNFD.setModulo(Constantes.TM_UserFinal);
		ObjAuditoriaNFD.setOpcion(Constantes.TO_Registrar);
		ObjAuditoriaNFD.setFechaAudioria(new Date());
		ObjAuditoriaNFD.setUsuario(usuario.getUsuario());
		ObjAuditoriaNFD.setCampo("Nro Folio");
		ObjAuditoriaNFD.setNuevoValor(String.valueOf(doc.getNumeroFolios()));
		ObjAuditoriaNFD.setExpediente(doc.getExpediente());
		ObjAuditoriaNFD.setDocumento(doc);
		auditoriaDao.SaveAuditoria(ObjAuditoriaNFD);
		/*******************************************************/
		// Registrando la Auditoria Asunto del Documento
		/*******************************************************/
		Auditoria ObjAuditoriaAsuntoDoc=new Auditoria();
		ObjAuditoriaAsuntoDoc.setTipoAuditoria(Constantes.TA_RegistrarNvoDoc_UserFinal);
		ObjAuditoriaAsuntoDoc.setModulo(Constantes.TM_UserFinal);
		ObjAuditoriaAsuntoDoc.setOpcion(Constantes.TO_Registrar);
		ObjAuditoriaAsuntoDoc.setFechaAudioria(new Date());
		ObjAuditoriaAsuntoDoc.setUsuario(usuario.getUsuario());
		ObjAuditoriaAsuntoDoc.setCampo("Asunto");
		ObjAuditoriaAsuntoDoc.setNuevoValor(doc.getAsunto());
		ObjAuditoriaAsuntoDoc.setExpediente(doc.getExpediente());
		ObjAuditoriaAsuntoDoc.setDocumento(doc);
		auditoriaDao.SaveAuditoria(ObjAuditoriaAsuntoDoc);
		/*******************************************************/
		// Registrando la Auditoria Fecha del Documento
		/*******************************************************/
		Auditoria ObjAuditoriaFechaDoc=new Auditoria();
		ObjAuditoriaFechaDoc.setTipoAuditoria(Constantes.TA_RegistrarNvoDoc_UserFinal);
		ObjAuditoriaFechaDoc.setModulo(Constantes.TM_UserFinal);
		ObjAuditoriaFechaDoc.setOpcion(Constantes.TO_Registrar);
		ObjAuditoriaFechaDoc.setFechaAudioria(new Date());
		ObjAuditoriaFechaDoc.setUsuario(usuario.getUsuario());
		ObjAuditoriaFechaDoc.setCampo("Fecha del Documento");
		ObjAuditoriaFechaDoc.setNuevoValor(String.valueOf(doc.getFechaDocumento()));
		ObjAuditoriaFechaDoc.setExpediente(doc.getExpediente());
		ObjAuditoriaFechaDoc.setDocumento(doc);
		auditoriaDao.SaveAuditoria(ObjAuditoriaFechaDoc);
		/*******************************************************/
		// Registrando la Auditoria Tipo Doc Cliente
		/*******************************************************/
		Auditoria ObjAuditoriaTDC=new Auditoria();
		ObjAuditoriaTDC.setTipoAuditoria(Constantes.TA_RegistrarNvoDoc_UserFinal);
		ObjAuditoriaTDC.setModulo(Constantes.TM_UserFinal);
		ObjAuditoriaTDC.setOpcion(Constantes.TO_Registrar);
		ObjAuditoriaTDC.setFechaAudioria(new Date());
		ObjAuditoriaTDC.setUsuario(usuario.getUsuario());
		ObjAuditoriaTDC.setCampo("Tipo Doc Identidad");
		ObjAuditoriaTDC.setNuevoValor(doc.getExpediente().getCliente().getTipoIdentificacion().getNombre());
		ObjAuditoriaTDC.setExpediente(doc.getExpediente());
		ObjAuditoriaTDC.setDocumento(doc);
		auditoriaDao.SaveAuditoria(ObjAuditoriaTDC);
		/*******************************************************/
		// Registrando la Auditoria Nro Doc Cliente
		/*******************************************************/
		Auditoria ObjAuditoriaNroCliente=new Auditoria();
		ObjAuditoriaNroCliente.setTipoAuditoria(Constantes.TA_RegistrarNvoDoc_UserFinal);
		ObjAuditoriaNroCliente.setModulo(Constantes.TM_UserFinal);
		ObjAuditoriaNroCliente.setOpcion(Constantes.TO_Registrar);
		ObjAuditoriaNroCliente.setFechaAudioria(new Date());
		ObjAuditoriaNroCliente.setUsuario(usuario.getUsuario());
		ObjAuditoriaNroCliente.setCampo("Nro Doc Identidad");
		ObjAuditoriaNroCliente.setNuevoValor(doc.getExpediente().getCliente().getNumeroIdentificacion());
		ObjAuditoriaNroCliente.setExpediente(doc.getExpediente());
		ObjAuditoriaNroCliente.setDocumento(doc);
		auditoriaDao.SaveAuditoria(ObjAuditoriaNroCliente);
	}

	@Override
	@Transactional
	public void registrarAuditoriaArchivos(Usuario usuario,Documento doc,ArchivoTemporal archivo,String tipoauditoria,String modulo,String opcion){
		/*******************************************************/
		// Registrando la auditoria del Expediente
		/*******************************************************/
		Auditoria ObjAuditoriaArchivo=new Auditoria();
		ObjAuditoriaArchivo.setTipoAuditoria(tipoauditoria);
		ObjAuditoriaArchivo.setModulo(modulo);
		ObjAuditoriaArchivo.setOpcion(opcion);
		ObjAuditoriaArchivo.setFechaAudioria(new Date());
		ObjAuditoriaArchivo.setUsuario(usuario.getUsuario());
		ObjAuditoriaArchivo.setCampo("Archivo");
		ObjAuditoriaArchivo.setNuevoValor(archivo.getSNombre());
		ObjAuditoriaArchivo.setExpediente(doc.getExpediente());
		ObjAuditoriaArchivo.setDocumento(doc);
		auditoriaDao.SaveAuditoria(ObjAuditoriaArchivo);
	}

   @Transactional
   public Plantilla guardarObj(Plantilla objPlantilla) {
      return plantillaDAO.guardarObj(objPlantilla);
   }
}
