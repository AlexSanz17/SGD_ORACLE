/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ositran.daos.ConsultaDAO;
import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.Consulta;
import org.ositran.dojo.grid.Item;

import com.btg.ositran.siged.domain.ConsultaAPN3;
import com.btg.ositran.siged.domain.ConsultaAPN4;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Notificacion;

public class ConsultaServiceImpl implements ConsultaService {
	private ConsultaDAO consultaDAO;
	private DocumentoService documentoService;
	private NotificacionService notificacionService;

	private static Logger log = Logger.getLogger(ConsultaServiceImpl.class);

	public ConsultaDAO getConsultaDAO() {
		return consultaDAO;
	}

	public void setConsultaDAO(ConsultaDAO consultaDAO) {
		this.consultaDAO = consultaDAO;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		ConsultaServiceImpl.log = log;
	}

	@Override
	public List<Item> getItemsDocumentosRecepcionados(Consulta objFiltro,
			String arrFecha[], Integer idAreaDestino) {

		if (objFiltro.getTipoDocumento().equals("")) {
			objFiltro.setTipoDocumento("Todos");
		}
		if (objFiltro.getAreaOrigen().equals("")) {
			objFiltro.setAreaOrigen("Todos");
		}
		if (objFiltro.getNroDocumento().equals("")) {
			objFiltro.setNroDocumento("Todos");
		}
		if (objFiltro.getAsuntoDocumento().equals("")) {
			objFiltro.setAsuntoDocumento("Todos");
		}
		if (arrFecha[0].equals("")) {
			arrFecha[0] = "Todos";
		}
		if (arrFecha[1].equals("")) {
			arrFecha[1] = "Todos";
		}
		log.debug("Asunto  : " + objFiltro.getAsuntoDocumento());
		log.debug("Nro documento  : " + objFiltro.getNroDocumento());
		log.debug("Area Origen"+objFiltro.getAreaOrigen());
		log.debug("Area idAreaDestino"+idAreaDestino);
		List<ConsultaAPN3> resultados = consultaDAO.documentosRecepcionados(
				objFiltro.getTipoDocumento(), objFiltro.getAreaOrigen(),
				arrFecha[0], arrFecha[1], idAreaDestino,
				objFiltro.getNroDocumento(), objFiltro.getAsuntoDocumento());

               List<Item> items = new ArrayList<Item>();

      
		items = convertirConsultaAPN3aItem(resultados, idAreaDestino, objFiltro.getAccion());
               
		return items;

	}


	private List<Item> convertirConsultaAPN3aItem(
			List<ConsultaAPN3> lstConsultaAPN3, Integer idAreaDestino, String accion) {

		List<Item> items = new ArrayList<Item>();
		if (lstConsultaAPN3 == null || lstConsultaAPN3.size() == 0) {
			log.info("No se encontro ningun documento con el filtro de Consulta Documentos Recepcionados");
			return null;
		} else {
			log.debug("Se encontraron ["
					+ lstConsultaAPN3.size()
					+ "] documentos para el filtro de Consulta Documentos Recepcionados");
            Set setTd = new HashSet();
            Set setTa = new HashSet();
            Set setTc = new HashSet();
            setTd.add(0);
               int i =1;
			for (ConsultaAPN3 consultaAPN3 : lstConsultaAPN3) {
                    if((consultaAPN3.getIdTrazabilidadDocumento() != null) && (consultaAPN3.getIdAreaDestinoTd().intValue()==idAreaDestino) && !(setTd.contains(consultaAPN3.getId())) && (accion.equals("0") || accion.equals("1"))){
	                Item item = new Item();
					item.setExpediente(consultaAPN3.getNroExpediente());
					item.setProceso(consultaAPN3.getProceso());
					item.setCliente(consultaAPN3.getCliente());
					item.setTipodocumento(consultaAPN3.getTipoDocumento());
					item.setEstado(consultaAPN3.getEstadoDocumento());
					item.setAsuntoExpediente(consultaAPN3.getAsuntoExpediente());
					item.setAsunto(consultaAPN3.getAsuntoDocumento());
					item.setDocumento(consultaAPN3.getNroDocumento());
					item.setId(consultaAPN3.getId());
					item.setIdproceso(consultaAPN3.getIdProceso());
					item.setPrioridad(consultaAPN3.getPrioridad());
	            	item.setArea(consultaAPN3.getAreaOrigenTd());
	            	item.setAreadestino(consultaAPN3.getAreaDestinoTd());
	            	item.setFecharecepcion(consultaAPN3.getFechaCreacionTd());
	            	item.setAccion(consultaAPN3.getAccionTd());
	            	item.setFechalimite(consultaAPN3.getFechaLimiteTd());
	            	item.setRemitente(consultaAPN3.getRemintenteTd());
	            	items.add(item);
	            	setTd.add(consultaAPN3.getId());
	            }
	            if(consultaAPN3.getIdTrazabilidadApoyo() != null && consultaAPN3.getIdAreaDestinoTa().intValue()==idAreaDestino&& !(setTa.contains(consultaAPN3.getId())) && (accion.equals("0") || accion.equals("1"))){
	                Item item = new Item();
					item.setExpediente(consultaAPN3.getNroExpediente());
					item.setProceso(consultaAPN3.getProceso());
					item.setCliente(consultaAPN3.getCliente());
					item.setTipodocumento(consultaAPN3.getTipoDocumento());
					item.setEstado(consultaAPN3.getEstadoDocumento());
					item.setAsuntoExpediente(consultaAPN3.getAsuntoExpediente());
					item.setAsunto(consultaAPN3.getAsuntoDocumento());
					item.setDocumento(consultaAPN3.getNroDocumento());
					item.setId(consultaAPN3.getId());
					item.setIdproceso(consultaAPN3.getIdProceso());
					item.setPrioridad(consultaAPN3.getPrioridad());
	            	item.setArea(consultaAPN3.getAreaOrigenTa());
	            	item.setAreadestino(consultaAPN3.getAreaDestinoTa());
	            	item.setFecharecepcion(consultaAPN3.getFechaCreacionTa());
	            	item.setAccion(consultaAPN3.getAccionTa());
	            	item.setFechalimite(consultaAPN3.getFechaLimiteTa());
	            	item.setRemitente(consultaAPN3.getRemintenteTa());
	            	items.add(item);
	            	setTa.add(consultaAPN3.getId());
	            }if(consultaAPN3.getIdTrazabilidadCopia() != null && consultaAPN3.getIdAreaDestinoTc().intValue()==idAreaDestino&& !(setTa.contains(consultaAPN3.getId())) && (accion.equals("0") || accion.equals("2"))){
	                Item item = new Item();
					item.setExpediente(consultaAPN3.getNroExpediente());
					item.setProceso(consultaAPN3.getProceso());
					item.setCliente(consultaAPN3.getCliente());
					item.setTipodocumento(consultaAPN3.getTipoDocumento());
					item.setEstado(consultaAPN3.getEstadoDocumento());
					item.setAsuntoExpediente(consultaAPN3.getAsuntoExpediente());
					item.setAsunto(consultaAPN3.getAsuntoDocumento());
					item.setDocumento(consultaAPN3.getNroDocumento());
					item.setId(consultaAPN3.getId());
					item.setIdproceso(consultaAPN3.getIdProceso());
					item.setPrioridad(consultaAPN3.getPrioridad());
	            	item.setArea(consultaAPN3.getAreaOrigenTc());
	            	item.setAreadestino(consultaAPN3.getAreaDestinoTc());
	            	item.setFecharecepcion(consultaAPN3.getFechaCreacionTc());
	                item.setAccion(consultaAPN3.getAccionTc());
	            	item.setRemitente(consultaAPN3.getRemintenteTc());
	            	setTc.add(consultaAPN3.getId());
	            	items.add(item);
	            }/*if(consultaAPN3.getIdNotificacion() != null && consultaAPN3.getIdAreaDestinoNo().intValue()==idAreaDestino){
	            	Item item = new Item();
	            	Documento documento = new Documento();
	            	Notificacion notificacion = new Notificacion();
	            	//notificacion = notificacionService.buscarObjPorID(consultaAPN3.getIdNotificacion().intValue());

                    //documento = documentoService.findByIdDocumento(notificacion.getIddocumento().getIdDocumento());
                    //documento = documentoService.findByIdDocumento();
					item.setExpediente(consultaAPN3.getNroExpediente());
					item.setProceso(consultaAPN3.getProceso());
					item.setCliente(consultaAPN3.getCliente());
					item.setTipodocumento(consultaAPN3.getTipoDocumento());
					item.setEstado(consultaAPN3.getEstadoDocumento());
					item.setAsuntoExpediente(consultaAPN3.getAsuntoExpediente());
					item.setAsunto(consultaAPN3.getAsuntoDocumento());
					item.setDocumento(consultaAPN3.getNroDocumento());
					item.setId(consultaAPN3.getId());
					item.setIdproceso(consultaAPN3.getIdProceso());
					item.setPrioridad(consultaAPN3.getPrioridad());
	            	item.setArea("");
	            	item.setAreadestino(consultaAPN3.getAreaDestinoNo());
	            	item.setFecharecepcion(consultaAPN3.getFechaCreacionNo());
	                item.setAccion("Notificacion");
	            	item.setRemitente("");
	            	items.add(item);
	            }*/
			}
                        i++;
			return items;
		}
	}

	@Override
	public List<Item> getItemsDocumentosEmitidos(Integer remitente) {

		List<ConsultaAPN4> resultados = consultaDAO
				.documentosEmitidos(remitente);

		List<Item> items = new ArrayList<Item>();

		items = convertirConsultaAPN4aItem(resultados);

		return items;

	}

	private List<Item> convertirConsultaAPN4aItem(
			List<ConsultaAPN4> lstConsultaAPN4) {

		List<Item> items = new ArrayList<Item>();

		if (lstConsultaAPN4 == null || lstConsultaAPN4.size() == 0) {
			log.info("No se encontro ningun documento con el filtro de Consulta Documentos Remitidos");
			return null;
		} else {
			log.debug("Se encontraron ["
					+ lstConsultaAPN4.size()
					+ "] documentos para el filtro de Consulta Documentos Remitidos");

			for (ConsultaAPN4 consultaAPN4 : lstConsultaAPN4) {
				Item item = new Item();
				item.setExpediente(consultaAPN4.getNroExpediente());
				item.setProceso(consultaAPN4.getProceso());
				item.setCliente(consultaAPN4.getCliente());
				item.setTipodocumento(consultaAPN4.getTipoDocumento());
				item.setEstado(consultaAPN4.getEstadoExpediente());
				item.setAsuntoExpediente(consultaAPN4.getAsuntoExpediente());
				item.setAsunto(consultaAPN4.getAsuntoDocumento());
				item.setDocumento(consultaAPN4.getNroDocumento());//
				item.setFecharecepcion(consultaAPN4.getFechaCreacion());
				item.setId(consultaAPN4.getId());
				item.setIdproceso(consultaAPN4.getIdProceso());
				item.setPrioridad(consultaAPN4.getPrioridad());
				item.setArea(consultaAPN4.getAreaOrigen());
				item.setAreadestino(consultaAPN4.getAreaDestino());
				item.setDestinatario(consultaAPN4.getDestinatario());
				item.setDocumentoReferencia(consultaAPN4.getDocumentoReferencia());
				items.add(item);
			}
			return items;
		}

	}

	@Override
	public List<Item> getItemsMisExpedientes(Integer IdAreaDestino, BusquedaAvanzada objFiltro) {
		/*if (objFiltro.getProceso().equals("")) {
			objFiltro.setProceso("Todos");
		}
		if (objFiltro.getCliente().equals("")) {
			objFiltro.setCliente("Todos");
		}
		if (objFiltro.getNroExpediente().equals("")) {
			objFiltro.setNroExpediente("Todos");
		}
		if (objFiltro.getAsuntoExpediente().equals("")) {
			objFiltro.setAsuntoExpediente("Todos");
		}
		if (arrFecha[0].equals("")) {
			arrFecha[0] = "Todos";
		}
		if (arrFecha[1].equals("")) {
			arrFecha[1] = "Todos";
		}
		log.debug("objFiltro.getProceso()"+objFiltro.getProceso());
		log.debug("objFiltro.getCliente()"+objFiltro.getCliente());
		log.debug("objFiltro.getNroExpediente()"+objFiltro.getNroExpediente());
		log.debug("objFiltro.getAsuntoExpediente()"+objFiltro.getAsuntoExpediente());
		log.debug("arrFecha[0]"+arrFecha[0]);
		log.debug("arrFecha[1]"+arrFecha[1]);*/
		//List<Expediente> resultados = consultaDAO.misExpedientes(objFiltro.getProceso(), objFiltro.getCliente(), objFiltro.getNroExpediente(), objFiltro.getAsuntoExpediente(), arrFecha[0], arrFecha[1], IdAreaDestino);
		if(objFiltro == null){
			objFiltro = new BusquedaAvanzada();
		}
		List<Expediente> resultados = consultaDAO.misExpedientes(IdAreaDestino, objFiltro);

                System.out.println("========0Resultados============" + resultados.size());
                
		List<Item> items = new ArrayList<Item>();

		items = convertirConsultaMEaItem(resultados);

		return items;
	}

	private List<Item> convertirConsultaMEaItem(
		List<Expediente> lstConsultaME) {
		List<Item> items = new ArrayList<Item>();

		if (lstConsultaME == null || lstConsultaME.size() == 0) {
		   log.info("No se encontro ningun expediente");
		   return null;
		} else {
			log.debug("Se encontraron ["
					+ lstConsultaME.size()
					+ "] documentos para el filtro de Consulta Mis expedientes");

			for (Expediente expediente : lstConsultaME) {
			   Item item = new Item();
			   item.setExpediente(expediente.getNroexpediente());
			   item.setAsuntoExpediente(expediente.getAsunto());
                           item.setFechacreacion(expediente.getFechacreacion());
            	           items.add(item);
		        }
			
			return items;
		}

	}

	public DocumentoService getDocumentoService() {
		return documentoService;
	}

	public void setDocumentoService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	public NotificacionService getNotificacionService() {
		return notificacionService;
	}

	public void setNotificacionService(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}

}

