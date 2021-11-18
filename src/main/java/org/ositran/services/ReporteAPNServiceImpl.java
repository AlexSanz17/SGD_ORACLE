package org.ositran.services;

import com.btg.ositran.siged.domain.FilaHojaFirma;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.ositran.daos.ReporteAPNDAO;
import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.utils.FechaLimite;

import com.btg.ositran.siged.domain.FilaHojaRuta;
import com.btg.ositran.siged.domain.FilaReporteAPN2;
import com.btg.ositran.siged.domain.NodoDocConsolidadoReporteAPN4;
import com.btg.ositran.siged.domain.NodoDocReporteAPN3;
import com.btg.ositran.siged.domain.NodoDocReporteAPN4;
import com.btg.ositran.siged.domain.NodoDocReporteAPN8;
import com.btg.ositran.siged.domain.NodoExpReporteAPN3;
import com.btg.ositran.siged.domain.NodoExpReporteAPN4;
import com.btg.ositran.siged.domain.NodoExpReporteAPN8;
import com.btg.ositran.siged.domain.ReporteAPN1;
import com.btg.ositran.siged.domain.ReporteAPN10;
import com.btg.ositran.siged.domain.ReporteAPN9;
import com.btg.ositran.siged.domain.TrazabilidadDocumentaria;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;

public class ReporteAPNServiceImpl implements ReporteAPNService {

	private static Logger log = Logger.getLogger(ReporteAPNServiceImpl.class);
	private FechaLimite fechaLimite;
	private DocumentoService documentoService;
	private TrazabilidaddocumentoService trazabilidadService;
	private TrazabilidadapoyoService trazabilidadApoyoService;

	private ReporteAPNDAO reporteAPNDAO;

	public List<FilaHojaRuta> generarHojaRuta(Integer idDocumento){
		return reporteAPNDAO.getHojaRuta(idDocumento);
	}
        
        public List<FilaHojaFirma> generarHojaFirma(Integer idDocumento){
		return reporteAPNDAO.getHojaFirma(idDocumento);
	}

	public List<TrazabilidadDocumentaria> generarTramiteDocumentario(Integer grupo){
		return reporteAPNDAO.getTramiteDocumentario(grupo);
	}

	public List<FilaHojaRuta> generarHojaRutaExpediente(Integer idExpediente){
		return reporteAPNDAO.getHojaRutaExpediente(idExpediente);
	}

	@Override
	public List<ReporteAPN10> getListaReporteAPN10(String idAreaOrigen,
			String fechaDesde, String fechaHasta){//, String idAreaDestino) {
        return  reporteAPNDAO.getListaReporteAPN10(idAreaOrigen,  fechaDesde, fechaHasta );
	}

	@Override
	public List<ReporteAPN1> getListaReporteAPN1(String idAreaOrigen,
			String idTipoDocumento, String idPrioridad,String fechaDesde, String fechaHasta, String idAreaDestino){//, String idAreaDestino) {
        return  reporteAPNDAO.getListaReporteAPN1(idAreaOrigen, idTipoDocumento, idPrioridad, fechaDesde, fechaHasta ,idAreaDestino);
	}

	@Override
	public List<ReporteAPN9> getListaReporteAPN9(String area, String ano, String mes, String tipodocumento){//, String idAreaDestino) {
        return  reporteAPNDAO.getListaReporteAPN9(area,  ano,  mes,  tipodocumento);
	}

	public List<FilaReporteAPN2> getListaReporteAPN2(BusquedaAvanzada objFiltro){
		return reporteAPNDAO.getListaReporteAPN2(objFiltro);
	}

	public List<NodoExpReporteAPN4> getListaReporteAPN4(BusquedaAvanzada objFiltro){
		return reporteAPNDAO.getListaReporteAPN4(objFiltro);
	}

	public List<NodoDocReporteAPN4> getListaDocReporteAPN4(String objFiltro){
		return reporteAPNDAO.getListaDocReporteAPN4(objFiltro);
	}
	public List<NodoDocConsolidadoReporteAPN4> getListaConsolidadoReporteAPN4(String objFiltro, Integer unidad){
		return reporteAPNDAO.getListaConsolidadoReporteAPN4(objFiltro, unidad);
	}

	public List<NodoExpReporteAPN3> getListaReporteAPN3(BusquedaAvanzada objFiltro){
		List<NodoExpReporteAPN3> lista = reporteAPNDAO.getListaNodosExpedienteReporteAPN3(objFiltro);
		List<NodoExpReporteAPN3> salida = new ArrayList<NodoExpReporteAPN3>();
        String tipoDocumento = "";

        try{
        if(lista != null && !lista.isEmpty()){
			for(NodoExpReporteAPN3 nodo : lista){

				if (objFiltro.getTipoDocumento().equals("0") ||	(objFiltro.getTipoDocumento().equals("1") && nodo.getIdGrupoProceso().toString().equals("57")) ||
				   (objFiltro.getTipoDocumento().equals("2") && (nodo.getIdGrupoProceso().toString().equals("1") || nodo.getIdGrupoProceso().toString().equals("58")))){
					nodo.setPrioridad("Normal");
				    tipoDocumento = nodo.getIdGrupoProceso().toString().equals("57") ? "1" : "2";
				    List<NodoDocReporteAPN3> documentos = reporteAPNDAO.getListaNodosDocumentoReporteAPN3(nodo.getIdExpediente());

					//////JBENGOA INICIO/////
					List<NodoDocReporteAPN3> documentosUltimo = new ArrayList<NodoDocReporteAPN3>();

					if (documentos!=null && documentos.size()>0){
					   nodo.setNroDocumento(documentos.get(0).getDocumento());
					   documentosUltimo.add(documentos.get(documentos.size()-1));
					 }else{
						continue;
					}

					//documentos!= null && !documentos.isEmpty() &&
				    ////// JBENGOA FIN /////

					if (objFiltro.getAreaOrigen().equals("0") && objFiltro.getAreaDestino().equals("0")){
						    if(documentos.get(0).getFechaLimite()!=null){
						    	nodo.setEstado(documentos.get(documentos.size()-1).getEstado());
						        nodo.setFechalimite(documentos.get(0).getFechaLimite());
						        nodo.setFechaCreacionExterna(documentos.get(0).getFechaCreacionExterna());
						        nodo.setDias(nodo.getFechalimite().getTime() - nodo.getFechaCreacionExterna().getTime()- fechaLimite.getDiasFeriados(nodo.getFechaCreacionExterna(),nodo.getFechalimite(), "150000")*24*3600*1000);
						        if (!nodo.getEstado().toUpperCase().equals("ATENDIDO") && !nodo.getEstado().toUpperCase().equals("ARCHIVADO"))
						          documentos.get(documentos.size()-1).setFechaTransferencia(new Date(System.currentTimeMillis()));
						    	for(int i=0;i<documentos.size();i++){
						    		if (documentos.get(i).getPrioridad() !=null &&
						    		   (documentos.get(i).getPrioridad().toString().equals("2") ||
						    			documentos.get(i).getPrioridad().toString().equals("3"))){
						    			if (documentos.get(i).getPrioridad().toString().equals("2"))
						    			  nodo.setPrioridad("Urgente");
						    			if (documentos.get(i).getPrioridad().toString().equals("3"))
							    		  nodo.setPrioridad("Muy Urgente");
						    		}

						    	}
						    	for(int i=0;i<documentos.size();i++)
						    		documentos.get(i).setDias(documentos.get(i).getFechaTransferencia().getTime() - documentos.get(i).getFechaCreacion().getTime() - fechaLimite.getDiasFeriados(documentos.get(i).getFechaCreacion(),documentos.get(i).getFechaTransferencia(), "150000")*24*3600*1000);

						    	if (tipoDocumento.equals("1")){
						    	    nodo.setDesArea(documentos.get(0).getAreaOrigen());
						    	}else{
						    	  if (documentos.get(0).getFechaCreacionExterna()!=null)
						    	    nodo.setDesArea(documentos.get(0).getAreaExterna());
						    	  else
						    		 continue;
						    	}

						    	if (objFiltro.getPrioridad().equals("0")){
									//nodo.setDocumentos(documentos);
						    		nodo.setDocumentos(documentosUltimo);
									salida.add(nodo);
						    	}else{
						    		  //if (objFiltro.getPrioridad().equals("1") && nodo.getPrioridad().equals("Urgente")){
						    		  if (objFiltro.getPrioridad().equals("1") && nodo.getPrioridad().equals("Normal")){
						    			  //nodo.setDocumentos(documentos);
								    	  nodo.setDocumentos(documentosUltimo);
										  salida.add(nodo);
						    		  }else{
						    			  //if (objFiltro.getPrioridad().equals("2") && nodo.getPrioridad().equals("Normal")){
						    			  if (objFiltro.getPrioridad().equals("2")  && nodo.getPrioridad().equals("Urgente")){
						    				//nodo.setDocumentos(documentos);
									    	 nodo.setDocumentos(documentosUltimo);
											 salida.add(nodo);
							    		  }else{
							    			  if (objFiltro.getPrioridad().equals("3")  && nodo.getPrioridad().equals("Muy Urgente")){
								    				//nodo.setDocumentos(documentos);
											    	 nodo.setDocumentos(documentosUltimo);
													 salida.add(nodo);
									    	  }
							    		  }
						    		  }
						    	}
							}
					}else{
						if(documentos.get(0).getFechaLimite()!=null){
								  if (!objFiltro.getAreaOrigen().equals("0") && !objFiltro.getAreaDestino().equals("0") &&
									   ((tipoDocumento.equals("1") && objFiltro.getAreaOrigen().equals(documentos.get(0).getIdAreaOrigen().toString())) ||
										(tipoDocumento.equals("2") && documentos.get(0).getFechaCreacionExterna()!=null && objFiltro.getAreaOrigen().equals(documentos.get(0).getIdAreaExterna().toString()))) &&
									    objFiltro.getAreaDestino().equals(documentos.get(documentos.size()-1).getIdAreaDestino().toString())){
									    nodo.setEstado(documentos.get(documentos.size()-1).getEstado());
									    nodo.setFechalimite(documentos.get(0).getFechaLimite());
									    nodo.setFechaCreacionExterna(documentos.get(0).getFechaCreacionExterna());
									    nodo.setDias(nodo.getFechalimite().getTime() - nodo.getFechaCreacionExterna().getTime()- fechaLimite.getDiasFeriados(nodo.getFechaCreacionExterna(),nodo.getFechalimite(), "150000")*24*3600*1000);
									    if (!nodo.getEstado().toUpperCase().equals("ATENDIDO") && !nodo.getEstado().toUpperCase().equals("ARCHIVADO"))
									       documentos.get(documentos.size()-1).setFechaTransferencia(new Date(System.currentTimeMillis()));
									  	for(int i=0;i<documentos.size();i++){
								    		if (documentos.get(i).getPrioridad()!=null &&
								    		   (documentos.get(i).getPrioridad().toString().equals("2") ||
								    			documentos.get(i).getPrioridad().toString().equals("3"))){
								    			if (documentos.get(i).getPrioridad().toString().equals("2"))
								    			  nodo.setPrioridad("Urgente");
								    			if (documentos.get(i).getPrioridad().toString().equals("3"))
									    		  nodo.setPrioridad("Muy Urgente");
								    		}

								    	 }

									  	for(int i=0;i<documentos.size();i++)
								    		documentos.get(i).setDias(documentos.get(i).getFechaTransferencia().getTime() - documentos.get(i).getFechaCreacion().getTime() - fechaLimite.getDiasFeriados(documentos.get(i).getFechaCreacion(),documentos.get(i).getFechaTransferencia(), "150000")*24*3600*1000);


									  	 if (tipoDocumento.equals("1"))
			                                nodo.setDesArea(documentos.get(0).getAreaOrigen());
									     else{
									    	if (documentos.get(0).getFechaCreacionExterna()!=null)
									    	  nodo.setDesArea(documentos.get(0).getAreaExterna());
									    	else
									    	  continue;
									     }

									  	 if (objFiltro.getPrioridad().equals("0")){
									  		//nodo.setDocumentos(documentos);
									    	  nodo.setDocumentos(documentosUltimo);
											salida.add(nodo);
								    	 }else{
								    		  if (objFiltro.getPrioridad().equals("1") && nodo.getPrioridad().equals("Normal")){
								    			//nodo.setDocumentos(documentos);
										    	  nodo.setDocumentos(documentosUltimo);
												  salida.add(nodo);
								    		  }else{
								    			  if (objFiltro.getPrioridad().equals("2")  && nodo.getPrioridad().equals("Urgente")){
								    				//nodo.setDocumentos(documentos);
											    	  nodo.setDocumentos(documentosUltimo);
													 salida.add(nodo);
									    		  }else{
									    			  if (objFiltro.getPrioridad().equals("3")  && nodo.getPrioridad().equals("Muy Urgente")){
										    				//nodo.setDocumentos(documentos);
													    	  nodo.setDocumentos(documentosUltimo);
															 salida.add(nodo);
											    	  }
									    		  }
								    		  }
								    	 }
								  }else{
									  if (objFiltro.getAreaOrigen().equals("0") && !objFiltro.getAreaDestino().equals("0") &&
									      objFiltro.getAreaDestino().equals(documentos.get(documentos.size()-1).getIdAreaDestino().toString())){
										  nodo.setEstado(documentos.get(documentos.size()-1).getEstado());
										  nodo.setFechalimite(documentos.get(0).getFechaLimite());
										  nodo.setFechaCreacionExterna(documentos.get(0).getFechaCreacionExterna());
										  nodo.setDias(nodo.getFechalimite().getTime() - nodo.getFechaCreacionExterna().getTime() - fechaLimite.getDiasFeriados(nodo.getFechaCreacionExterna(),nodo.getFechalimite(), "150000")*24*3600*1000);
										  if (!nodo.getEstado().toUpperCase().equals("ATENDIDO") && !nodo.getEstado().toUpperCase().equals("ARCHIVADO"))
									          documentos.get(documentos.size()-1).setFechaTransferencia(new Date(System.currentTimeMillis()));
										  for(int i=0;i<documentos.size();i++){
									    		if (documentos.get(i).getPrioridad()!=null &&
									    		   (documentos.get(i).getPrioridad().toString().equals("2") ||
									    			documentos.get(i).getPrioridad().toString().equals("3"))){
									    			if (documentos.get(i).getPrioridad().toString().equals("2"))
									    			   nodo.setPrioridad("Urgente");
									    			if (documentos.get(i).getPrioridad().toString().equals("3"))
										    		   nodo.setPrioridad("Muy Urgente");
									    		}

									    	}

										  for(int i=0;i<documentos.size();i++)
									    		documentos.get(i).setDias(documentos.get(i).getFechaTransferencia().getTime() - documentos.get(i).getFechaCreacion().getTime() - fechaLimite.getDiasFeriados(documentos.get(i).getFechaCreacion(),documentos.get(i).getFechaTransferencia(), "150000")*24*3600*1000);

										   if (tipoDocumento.equals("1"))
				                              nodo.setDesArea(documentos.get(0).getAreaOrigen());
										   else
											  if (documentos.get(0).getFechaCreacionExterna()!=null)
											     nodo.setDesArea(documentos.get(0).getAreaExterna());
											  else
											     continue;


											if (objFiltro.getPrioridad().equals("0")){
												//nodo.setDocumentos(documentos);
										    	  nodo.setDocumentos(documentosUltimo);
													salida.add(nodo);
										    	}else{
										    		  if (objFiltro.getPrioridad().equals("1") && nodo.getPrioridad().equals("Normal")){
										    			//nodo.setDocumentos(documentos);
												    	  nodo.setDocumentos(documentosUltimo);
														  salida.add(nodo);
										    		  }else{
										    			  if (objFiltro.getPrioridad().equals("2") && nodo.getPrioridad().equals("Urgente")){
										    				//nodo.setDocumentos(documentos);
													    	  nodo.setDocumentos(documentosUltimo);
															 salida.add(nodo);
											    		  }else{
											    			  if (objFiltro.getPrioridad().equals("3") && nodo.getPrioridad().equals("Muy Urgente")){
												    				//nodo.setDocumentos(documentos);
															    	  nodo.setDocumentos(documentosUltimo);
																	 salida.add(nodo);
													    	  }
											    		  }
										    		  }
										     }
									   }else{
											 if (!objFiltro.getAreaOrigen().equals("0") && objFiltro.getAreaDestino().equals("0") &&
												((tipoDocumento.equals("1") && objFiltro.getAreaOrigen().equals(documentos.get(0).getIdAreaOrigen().toString())) ||
												 (tipoDocumento.equals("2"))) && documentos.get(0).getFechaCreacionExterna()!=null){// && objFiltro.getAreaOrigen().equals(documentos.get(0).getIdAreaExterna().toString())))){

												 nodo.setEstado(documentos.get(documentos.size()-1).getEstado());
												 nodo.setFechalimite(documentos.get(0).getFechaLimite());
												 nodo.setFechaCreacionExterna(documentos.get(0).getFechaCreacionExterna());
												 nodo.setDias(nodo.getFechalimite().getTime() - nodo.getFechaCreacionExterna().getTime()- fechaLimite.getDiasFeriados(nodo.getFechaCreacionExterna(),nodo.getFechalimite(), "150000")*24*3600*1000);
												 if (!nodo.getEstado().toUpperCase().equals("ATENDIDO") && !nodo.getEstado().toUpperCase().equals("ARCHIVADO"))
											          documentos.get(documentos.size()-1).setFechaTransferencia(new Date(System.currentTimeMillis()));
												 for(int i=0;i<documentos.size();i++){
											    		if (documentos.get(i).getPrioridad()!=null &&
											    			(documentos.get(i).getPrioridad().toString().equals("2") ||
											    			documentos.get(i).getPrioridad().toString().equals("3"))){
											    			if (documentos.get(i).getPrioridad().toString().equals("2"))
												    			nodo.setPrioridad("Urgente");
												    		if (documentos.get(i).getPrioridad().toString().equals("3"))
													    		nodo.setPrioridad("Muy Urgente");
											    		}

											     }

												 for(int i=0;i<documentos.size();i++)
											    		documentos.get(i).setDias(documentos.get(i).getFechaTransferencia().getTime() - documentos.get(i).getFechaCreacion().getTime()- fechaLimite.getDiasFeriados(documentos.get(i).getFechaCreacion(),documentos.get(i).getFechaTransferencia(), "150000")*24*3600*1000);


												 if (tipoDocumento.equals("1"))
						                            nodo.setDesArea(documentos.get(0).getAreaOrigen());
												 else{
													if (documentos.get(0).getFechaCreacionExterna()!=null)
												      nodo.setDesArea(documentos.get(0).getAreaExterna());
													else
													  continue;
												 }

												 if (objFiltro.getPrioridad().equals("0")){
													   //nodo.setDocumentos(documentos);
											    	    nodo.setDocumentos(documentosUltimo);
														salida.add(nodo);
											    	}else{
											    		  if (objFiltro.getPrioridad().equals("1") && nodo.getPrioridad().equals("Normal")){
											    			//nodo.setDocumentos(documentos);
													    	  nodo.setDocumentos(documentosUltimo);
															  salida.add(nodo);
											    		  }else{
											    			  if (objFiltro.getPrioridad().equals("2") && nodo.getPrioridad().equals("Urgente")){
											    				//nodo.setDocumentos(documentos);
														    	  nodo.setDocumentos(documentosUltimo);
																 salida.add(nodo);
												    		  }else{
												    			  if (objFiltro.getPrioridad().equals("3") && nodo.getPrioridad().equals("Muy Urgente")){
													    				//nodo.setDocumentos(documentos);
																    	  nodo.setDocumentos(documentosUltimo);
																		 salida.add(nodo);
														    	  }
												    		  }
											    		  }
											    	}
											  }
									   }
								  }
						  }
					  }
				}
			}//fin
		 }
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return salida;
	}


	public List<NodoExpReporteAPN8> getListaReporteAPN8(BusquedaAvanzada objFiltro){
		List<NodoExpReporteAPN8> lista = reporteAPNDAO.getListaNodosExpedienteReporteAPN8(objFiltro);
		List<NodoExpReporteAPN8> salida = new ArrayList<NodoExpReporteAPN8>();
        String tipoDocumento = "";

        try{

        if(lista != null && !lista.isEmpty()){
			for(NodoExpReporteAPN8 nodo : lista){

					nodo.setPrioridad("Normal");
				    List<NodoDocReporteAPN8> documentos = reporteAPNDAO.getListaNodosDocumentoReporteAPN8(nodo.getIdExpediente());

					//////JBENGOA INICIO/////
					List<NodoDocReporteAPN8> documentosUltimo = new ArrayList<NodoDocReporteAPN8>();

					if (documentos!=null && documentos.size()>0){
					   nodo.setNroDocumento(documentos.get(0).getDocumento());
					  // System.out.println("-----------------------------xx--------------------------------");
					   //System.out.println("Documento=" + documentos.get(0).getDocumento() + "-" + documentos.get(0).getFechaCreacion() + "-" + documentoService.findByIdDocumento(documentos.get(0).getIdDocumento()).getFechaCreacion());
					   nodo.setFechaCreacion(documentoService.findByIdDocumento(documentos.get(0).getIdDocumento()).getFechaCreacion()); //CAMBIO DE FECHA JUAN BENGOA
					   documentosUltimo.add(documentos.get(documentos.size()-1));
					 }else{
						continue;
					}

				    if(documentos.get(0).getFechaLimite()!=null){
						    	nodo.setEstado(documentos.get(documentos.size()-1).getEstado());
						        nodo.setFechalimite(documentos.get(0).getFechaLimite());
						        nodo.setFechaCreacionExterna(documentos.get(0).getFechaCreacionExterna());
						        nodo.setDias(nodo.getFechalimite().getTime() - nodo.getFechaCreacionExterna().getTime()- fechaLimite.getDiasFeriados(nodo.getFechaCreacionExterna(),nodo.getFechalimite(), "150000")*24*3600*1000);
						        if (!nodo.getEstado().toUpperCase().equals("ATENDIDO") && !nodo.getEstado().toUpperCase().equals("ARCHIVADO"))
						          documentos.get(documentos.size()-1).setFechaTransferencia(new Date(System.currentTimeMillis()));
						    	for(int i=0;i<documentos.size();i++){
						    		if (documentos.get(i).getPrioridad() !=null &&
						    		   (documentos.get(i).getPrioridad().toString().equals("2") ||
						    			documentos.get(i).getPrioridad().toString().equals("3"))){
						    			if (documentos.get(i).getPrioridad().toString().equals("2"))
						    			   nodo.setPrioridad("Urgente");
						    			if (documentos.get(i).getPrioridad().toString().equals("3"))
						    			   nodo.setPrioridad("Muy Urgente");
						    		}

						    	}
						    	for(int i=0;i<documentos.size();i++)
						    		documentos.get(i).setDias(documentos.get(i).getFechaTransferencia().getTime() - documentos.get(i).getFechaCreacion().getTime() - fechaLimite.getDiasFeriados(documentos.get(i).getFechaCreacion(),documentos.get(i).getFechaTransferencia(), "150000")*24*3600*1000);

						    	if (tipoDocumento.equals("1")){
						    	    nodo.setDesArea(documentos.get(0).getAreaOrigen());
						    	}else{
						    	  if (documentos.get(0).getFechaCreacionExterna()!=null)
						    	    nodo.setDesArea(documentos.get(0).getAreaExterna());
						    	  else
						    		 continue;
						    	}

						    	if (objFiltro.getPrioridad().equals("0")){
									//nodo.setDocumentos(documentos);
						    		nodo.setDocumentos(documentosUltimo);
									salida.add(nodo);
						    	}else{
						    		  if (objFiltro.getPrioridad().equals("2") && (nodo.getPrioridad().equals("Urgente"))){
						    			  //nodo.setDocumentos(documentos);
								    	  nodo.setDocumentos(documentosUltimo);
										  salida.add(nodo);
						    		  }else{
						    			  if (objFiltro.getPrioridad().equals("1") && nodo.getPrioridad().equals("Normal")){
						    				//nodo.setDocumentos(documentos);
									    	 nodo.setDocumentos(documentosUltimo);
											 salida.add(nodo);
							    		  }else{
							    			  if (objFiltro.getPrioridad().equals("3") && nodo.getPrioridad().equals("Muy Urgente")){
								    				//nodo.setDocumentos(documentos);
											    	 nodo.setDocumentos(documentosUltimo);
													 salida.add(nodo);
									    		  }
							    		  }
						    		  }
						    	}
					}
			}//fin
		 }
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return salida;
	}

	public Trazabilidaddocumento buscarUltimaTraza(Integer idDocumento){
		return trazabilidadService.encontrarUltimaTrazabilidadPorDocumento(idDocumento);
	}

	public ReporteAPNDAO getReporteAPNDAO() {
		return reporteAPNDAO;
	}

	public void setReporteAPNDAO(ReporteAPNDAO reporteAPNDAO) {
		this.reporteAPNDAO = reporteAPNDAO;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		ReporteAPNServiceImpl.log = log;
	}

	public TrazabilidaddocumentoService getTrazabilidadService() {
		return trazabilidadService;
	}

	public void setTrazabilidadService(
			TrazabilidaddocumentoService trazabilidadService) {
		this.trazabilidadService = trazabilidadService;
	}

	public DocumentoService getDocumentoService() {
		return documentoService;
	}

	public void setDocumentoService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	public TrazabilidadapoyoService getTrazabilidadApoyoService() {
		return trazabilidadApoyoService;
	}

	public void setTrazabilidadApoyoService(
			TrazabilidadapoyoService trazabilidadApoyoService) {
		this.trazabilidadApoyoService = trazabilidadApoyoService;
	}

	public ReporteAPNServiceImpl(ReporteAPNDAO reporteAPNDAO) {
		this.reporteAPNDAO = reporteAPNDAO;
	}

	public FechaLimite getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(FechaLimite fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

}
