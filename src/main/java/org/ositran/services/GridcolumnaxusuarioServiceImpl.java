package org.ositran.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.ositran.daos.DatosDAO;
import org.ositran.daos.GridDAO;
import org.ositran.daos.GridXGridColumnaDAO;
import org.ositran.daos.GridcolumnaxusuarioDAO;
import org.ositran.daos.ParametroDAO;
import org.ositran.daos.RolDAO;
import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.grid.Estructura;
import org.ositran.dojo.grid.GridUsuario;
import org.ositran.dojo.grid.Item;
import org.ositran.dojo.grid.ItemUF;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;
import com.btg.ositran.siged.domain.ArchivoPendiente;
import com.btg.ositran.siged.domain.Campo;
import com.btg.ositran.siged.domain.Cargo;
import com.btg.ositran.siged.domain.Tipodocumento;
import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.FilaBandejaLegajo;
import com.btg.ositran.siged.domain.Concesionario;
import com.btg.ositran.siged.domain.Datos;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoAreaFuncional;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.FilaBandejaAnulado;
import com.btg.ositran.siged.domain.FilaBandejaEnviados;
import com.btg.ositran.siged.domain.FilaBandejaAtendido;
import com.btg.ositran.siged.domain.Grid;
import com.btg.ositran.siged.domain.GridColumna;
import com.btg.ositran.siged.domain.GridXGridColumna;
import com.btg.ositran.siged.domain.Gridcolumnaxusuario;
import com.btg.ositran.siged.domain.IotdtmDocExterno;
import com.btg.ositran.siged.domain.Lista;
import com.btg.ositran.siged.domain.Mensajeria;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Reemplazo;
import com.btg.ositran.siged.domain.Rol;
import com.btg.ositran.siged.domain.Tipoenvio;
import org.ositran.daos.TipodocumentoDAO;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Usuario;
import org.ositran.daos.DespachoVirtualDAO;
import org.ositran.daos.DocumentoAnuladoDAO;
import org.ositran.daos.DocumentoAtendidoDAO;
import org.ositran.daos.DocumentoExternoVirtualDAO;
import org.ositran.daos.DocumentoPendienteDAO;
import org.ositran.daos.FirmaArchivoDAO;
import org.ositran.daos.SeguimientoXFirmaDAO;

public class GridcolumnaxusuarioServiceImpl implements GridcolumnaxusuarioService {

	private static Logger _log = Logger.getLogger(GridcolumnaxusuarioServiceImpl.class);
	private ArchivoService srvArchivo;
	private ArchivopendienteService srvArchivoPendiente;
	private CarpetabusquedaService srvCarpetaBusqueda;
	private CargoService cargoService;
        private LegajoService legajoService;
	private DocumentoService documentoService;
	private DocumentoEnviadoService srvDocumentoEnviado;
	private ExpedienteService srvExpediente;
	private GridcolumnaxusuarioDAO dao; 
	private TrazabilidaddocumentoService srvTrazabilidadDocumento;
	private TrazabilidadapoyoService trazabilidadApoyoService;
	private MensajeriaService srvMensajeria;
	private NotificacionService srvNotificacion;
	private ProcesoService srvProceso;
	private ReemplazoService srvReemplazo;
	private RolDAO rolDao;
        private ClienteService clienteService;
	private GridDAO gridDao;
	private ParametroDAO parametroDAO;
        private DocumentoPendienteDAO documentoPendienteDAO;
        private DocumentoAtendidoDAO documentoAtendidoDAO;
        private DocumentoAnuladoDAO documentoAnuladoDAO;
        private SeguimientoXFirmaDAO seguimientoXFirmaDAO;
	private GridXGridColumnaDAO gridXGridColumnaDao;
	private PlantillaService srvPlantilla;
	private ListaService srvLista;
	private ItemService srvItem;
	private DatosDAO datosDAO;
	private SeguimientoXUsuarioService seguimientoXUsuarioService;
	private ParametroService srvParametro;
        private FirmaArchivoDAO firmaArchivoDAO;
        private DocumentoExternoVirtualDAO documentoExternoVirtualDAO;
        private DespachoVirtualDAO despachoVirtualDAO;
        private TipodocumentoDAO tipoDocumentoDAO;
        
        public LegajoService getLegajoService() {
            return legajoService;
        }

        public void setLegajoService(LegajoService legajoService) {
            this.legajoService = legajoService;
        }
        
        public DocumentoExternoVirtualDAO getDocumentoExternoVirtualDAO() {
            return documentoExternoVirtualDAO;
        }

        public void setDocumentoExternoVirtualDAO(DocumentoExternoVirtualDAO documentoExternoVirtualDAO) {
            this.documentoExternoVirtualDAO = documentoExternoVirtualDAO;
        }
        
        public ClienteService getClienteService() {
            return clienteService;
        }

        public void setClienteService(ClienteService clienteService) {
            this.clienteService = clienteService;
        }

        public TipodocumentoDAO getTipoDocumentoDAO() {
            return tipoDocumentoDAO;
        }

        public void setTipoDocumentoDAO(TipodocumentoDAO tipoDocumentoDAO) {
            this.tipoDocumentoDAO = tipoDocumentoDAO;
        }

        public DespachoVirtualDAO getDespachoVirtualDAO() {
            return despachoVirtualDAO;
        }

        public void setDespachoVirtualDAO(DespachoVirtualDAO despachoVirtualDAO) {
            this.despachoVirtualDAO = despachoVirtualDAO;
        }
       
        public FirmaArchivoDAO getFirmaArchivoDAO() {
            return firmaArchivoDAO;
        }

        public void setFirmaArchivoDAO(FirmaArchivoDAO firmaArchivoDAO) {
            this.firmaArchivoDAO = firmaArchivoDAO;
        }
	// ////////////////////////////////
	// Constructors //
	// ////////////////////////////////
	public GridcolumnaxusuarioServiceImpl(GridcolumnaxusuarioDAO dao) {
	     setDao(dao);
	}
        
        public SeguimientoXFirmaDAO getSeguimientoXFirmaDAO() {
             return seguimientoXFirmaDAO;
        }

        public void setSeguimientoXFirmaDAO(SeguimientoXFirmaDAO seguimientoXFirmaDAO) {
             this.seguimientoXFirmaDAO = seguimientoXFirmaDAO;
        }

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	// @Transactional (propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<Estructura> buildEstructura(List<Gridcolumnaxusuario> lstGridColumnaXUsuario, List<GridXGridColumna> columnas, Integer iIdUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - buildEstructura():List<Estructura> ");

                if (columnas == null) {
			_log.warn("No hay columnas");

			return null;
		}

                List<Estructura> lstEstructura = new ArrayList<Estructura>();
		Estructura[] estructura = new Estructura[columnas.size()];
                
                for (int i = 0; i < estructura.length; i++) {
			GridXGridColumna porGrid = columnas.get(i);
			GridColumna columna = porGrid.getGridColumna();
			int posicion = i;
			String width = columna.getWidth();
			int hidden = columna.getHidden();
			boolean ordenado = false;
			boolean ascendente = false;
                        Gridcolumnaxusuario objGridColumnaXUsuario = new Gridcolumnaxusuario(iIdUsuario, porGrid.getId());
                      
                        if (lstGridColumnaXUsuario != null && lstGridColumnaXUsuario.contains(objGridColumnaXUsuario)) {
                        	objGridColumnaXUsuario = lstGridColumnaXUsuario.get(lstGridColumnaXUsuario.indexOf(objGridColumnaXUsuario));
				width = objGridColumnaXUsuario.getWidth();
				hidden = objGridColumnaXUsuario.getHidden();
                                posicion = objGridColumnaXUsuario.getPosition() - 1;
                        	char orden = objGridColumnaXUsuario.getOrdenado();
				if (orden != 'N') {
					ordenado = true;
					if (orden == 'A') {
						ascendente = true;
					}
				}
			}
                        
                        if (posicion >= estructura.length) {
				posicion = i;
			}
                        if (estructura[posicion] != null) {
                          posicion = getUltimaPosicionDisponible(estructura);
			}
			estructura[posicion] = new Estructura(porGrid.getId(), columna.getField(), columna.getName(), columna.getNoresize(), width, hidden, columna.getFormater(), ordenado, ascendente);
		}
                
                for (Estructura e : estructura) {
			lstEstructura.add(e);
		}
                
                return lstEstructura;
	}

	private int getUltimaPosicionDisponible(Estructura[] estructuras) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getUltimaPosicionDisponible():int ");

		for (int i = 0; i < estructuras.length; i++) {
			if (estructuras[i] == null) {
				return i;
			}
		}
		return estructuras.length - 1;
	}

	public List<Item> buildItems(List<Estructura> lstEstructura, Usuario objUsuario, Integer iTipoGrid) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - buildItems():List<Item> ");

		List<Item> lstItem = null;

		switch (iTipoGrid) {
		case 1:
			lstItem = this.convertFromDocumentoToItem(lstEstructura, objUsuario);
			break;
		}

		return lstItem;
	}

	@SuppressWarnings("unchecked")
	public List<Item> convertFromDocumentoToItem(List<Estructura> lstEstructura, Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - convertFromDocumentoToItem():List<Item> ");

		List<Documento> lstDocumento = null;
		List<Item> lstItem = new ArrayList<Item>();
		Trazabilidaddocumento objTrazabilidadDocumento = null;
		_log.debug("Obteniendo documentos");
		lstDocumento = documentoService.findDocumentos(objUsuario);
		if (lstDocumento != null) {
			_log.debug("Se encontraron " + lstDocumento.size() + " documentos para el usuario " + objUsuario.getUsuario());
			for (Documento objDocumento : lstDocumento) {
				if (objDocumento != null) {
					Item objItem = new Item();

					if (objDocumento.isEsTransient()) {
						objItem.setHistorico(objDocumento.getHistorico());
					}
					//_log.debug("IdDocumento ["+objDocumento.getIdDocumento()+"]");
					for (Estructura estructura : lstEstructura) {
						//_log.debug("Campo [" + estructura.getField() + "]");
						if (estructura != null) {
							String campo = estructura.getField();
							if (campo != null) {
								if (campo.equals("estado")) {
									objItem.setEstado(objDocumento.getAccion().getDescripcion());
								} else if (campo.equals("asunto")) {
									Rol rol = objUsuario.getRol();
									String perfil = "";
									if (rol != null) {
										perfil = rol.getIdperfil().getNombre();
									}
									if (perfil.equals(Constantes.PERFIL_MP) || perfil.equals(Constantes.PERFIL_DIG) || perfil.equals(Constantes.PERFIL_QAS)) {
										objItem.setAsunto(objDocumento.getAsunto());
									} else {
										objItem.setAsunto(objDocumento.getExpediente().getAsunto());
									}
								} else if (campo.equals("concesionario")) {
									if (objDocumento.isEsTransient()) {
										if (objDocumento.getRazonSocialConcesionario() != null) {
											objItem.setConcesionario(objDocumento.getRazonSocialConcesionario());
										}
									} else {
										if (objDocumento.getExpediente().getConcesionario() != null) {
											objItem.setConcesionario(objDocumento.getExpediente().getConcesionario().getRazonSocial());
										}
									}
								} else if (campo.equals("documento")) {
									if (objDocumento.isEsTransient()) {
										objItem.setDocumento(objDocumento.getNombreTipoDocumento());
									} else {
										objItem.setDocumento(objDocumento.getTipoDocumento().getNombre() + " - " + objDocumento.getNumeroDocumento());
									}
								} else if (campo.equals("fecharecepcion")) {
									objItem.setFecharecepcion(objDocumento.getFechaAccion());
									objItem.setLfecharecepcion(objDocumento.getFechaAccion().getTime());
								} else if (campo.equals("id")) {
									objItem.setId(objDocumento.getIdDocumento());
								} else if (campo.equals("remitente")) {
									if (objDocumento.isEsTransient()) {
									} else {
									}
									if (!objDocumento.isEsTransient()) {
										objTrazabilidadDocumento = getSrvTrazabilidadDocumento().findByMaxNroRegistro(objDocumento.getIdDocumento(), Constantes.ACCION_CONSULTAR, null, null);
									}
									Rol rol = objUsuario.getRol();
									if (rol != null && rol.getIdperfil().getNombre().equals(Constantes.PERFIL_MP)) {
										if (objDocumento.isEsTransient()) {
											if (objDocumento.getClienteTipoIdentificacion().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
												objItem.setCliente(objDocumento.getClienteRazonSocial());
											} else {
												objItem.setCliente(objDocumento.getClienteNombre());
											}
										} else {
											if (objDocumento.getExpediente().getCliente().getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
												objItem.setRemitente(objDocumento.getExpediente().getCliente().getRazonSocial());
											} else if (objDocumento.getExpediente().getCliente().getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_DNI)) {
												objItem.setRemitente(objDocumento.getExpediente().getCliente().getNombres() + " " + objDocumento.getExpediente().getCliente().getApellidoPaterno());
											} else if (objDocumento.getExpediente().getCliente().getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_OTRO)) {
												objItem.setRemitente((objDocumento.getExpediente().getCliente().getRazonSocial() != null ? objDocumento.getExpediente().getCliente().getRazonSocial() : "") + " " + (objDocumento.getExpediente().getCliente().getNombres() != null ? objDocumento.getExpediente().getCliente().getNombres() : "") + " " + (objDocumento.getExpediente().getCliente().getApellidoPaterno() != null ? objDocumento.getExpediente().getCliente().getApellidoPaterno() : ""));
											}
										}
									} else {
										if (objDocumento.isEsTransient()) {
											objItem.setRemitente(objDocumento.getNombreRemitente());
										} else {
											objItem.setRemitente((objTrazabilidadDocumento != null && objTrazabilidadDocumento.getRemitente() != null ? objTrazabilidadDocumento.getRemitente().getNombres() : "") + " " + (objTrazabilidadDocumento != null && objTrazabilidadDocumento.getRemitente() != null ? objTrazabilidadDocumento.getRemitente().getApellidos() : ""));
										}
									}
								} else if (campo.equals("urlarchivo")) {
									/*
									 * List<Archivo> lstArchivo =
									 * getSrvArchivo().getArchivoList
									 * (objDocumento
									 * .getExpediente().getIdexpediente(),
									 * objDocumento.getIdDocumento(),
									 * objUsuario.getRol().getNombre());
									 *
									 * if (lstArchivo != null &&
									 * lstArchivo.size() > 0) {
									 * _log.debug("Numero de archivos [" +
									 * lstArchivo.size() + "]");
									 * objItem.setUrlarchivo(lstArchivo
									 * .get(0).getSURL()); }
									 */
									if (objDocumento.getArchivos() != null && objDocumento.getArchivos().size() > 0) {
										objItem.setUrlarchivo(objDocumento.getArchivos().get(0).getRutaAlfresco());
										_log.debug("Ruta Alfresco [" + objDocumento.getArchivos().get(0).getRutaAlfresco() + "]");
									}
								} else if (campo.equals("fechalimite")) {
									// lo siguiente es temporal solo para probar
									// en la
									// mesa de partes
									Date fechaLimite = objDocumento.getFechaLimiteAtencion();
									if (fechaLimite != null) {
										objItem.setFechalimite(fechaLimite);
										objItem.setLfechalimite(fechaLimite.getTime());
									}
								} else if (campo.equals("tipoalerta")) {
									objItem.setTipoalerta("<img src='images/bolitaRoja.gif' alt='Casi por expirar'/>"); // hardcode
									// temporal
								} else if (campo.equals("expediente")) {
									if (objDocumento.isEsTransient()) {
										objItem.setExpediente(objDocumento.getNroexpediente());
									} else {
										objItem.setExpediente(objDocumento.getExpediente().getNroexpediente());
									}
								} else if (campo.equals("proceso")) {
									objItem.setProceso("k11111");
								} else if (campo.equals("cliente")) {
									Cliente cliente = objDocumento.getExpediente().getCliente();
									if (cliente != null) {
										String sTipoIdentificacion = cliente.getTipoIdentificacion().getNombre();
										String sNombre = null;
										if (sTipoIdentificacion.equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
											sNombre = objDocumento.getExpediente().getCliente().getRazonSocial();
										} else {
											sNombre = objDocumento.getExpediente().getCliente().getNombres() + " " + objDocumento.getExpediente().getCliente().getApellidoPaterno();
										}
										objItem.setCliente(sNombre);
									}
									objItem.setCliente("");
								} else if (campo.equals("fechaexpediente")) {
									objItem.setFechaexpediente(objDocumento.getExpediente().getFechacreacion());
								} else if (campo.equals("numeroMesaPartes")) {
									objItem.setNumeroMesaPartes(objDocumento.getNumeroMesaPartes());
								}
							}
						}
					}
					lstItem.add(objItem);
				}
			}
		}
		return lstItem;
	}

	public List<Gridcolumnaxusuario> findByIdUsuario(Integer iIdUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - convertFromDocumentoToItem():List<Gridcolumnaxusuario> ");

		return getDao().findByIdUsuario(iIdUsuario);
	}

	public List<Item> convertFromCarpetaBusquedaToItem(Integer iIdCarpetaBusqueda) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - convertFromCarpetaBusquedaToItem():List<Item> ");

		//List<Expedienfindadvance> lstE = null;
		List<Documento> lstDocumento = null;
		List<Item> lstItem = new ArrayList<Item>();

		_log.debug("Se recibio carpeta de busqueda con ID [" + iIdCarpetaBusqueda + "]");

		lstDocumento = srvCarpetaBusqueda.findLstByFiltro(iIdCarpetaBusqueda);
		//lstE = srvCarpetaBusqueda.findbyAllid(iIdCarpetaBusqueda.intValue());

		if (lstDocumento != null) {
			_log.debug("Numero de documentos asociados a la carpeta de busqueda [" + lstDocumento.size() + "]");

			for (Documento objDocumento : lstDocumento) {
				Item objItem = new Item();

				objItem.setExpediente(objDocumento.getExpediente().getNroexpediente());
				objItem.setDocumento(objDocumento.getNumeroDocumento());
				objItem.setTipodocumento(objDocumento.getTipoDocumento().getNombre());
				objItem.setFecharecepcion(objDocumento.getFechaCreacion());
				//objItem.setProceso(objDocumento.getExpediente().getProceso().getNombre());
				objItem.setArea(objDocumento.getAutor().getUnidad().getNombre());

				if (objDocumento.getExpediente().getConcesionario() != null) {
					objItem.setConcesionario(objDocumento.getExpediente().getConcesionario().getRazonSocial());
				}

				objItem.setId(objDocumento.getIdDocumento());
				//objItem.setIdproceso(objDocumento.getExpediente().getProceso().getIdproceso());
				objItem.setAsunto(objDocumento.getAsunto());
				objItem.setAsuntoExpediente(objDocumento.getExpediente().getAsunto());

				objItem.setCliente(objDocumento.getExpediente().getCliente() != null ? objDocumento.getExpediente().getCliente().getNombreRazon() : "");
				Trazabilidaddocumento traza = srvTrazabilidadDocumento.findByMaxtrazabyIddocumento(objDocumento.getIdDocumento()).get(0);
				if(traza != null){
					objItem.setAreadestino(traza.getDestinatario() != null? traza.getDestinatario().getUnidad().getNombre() : "-");
				}else{
					objItem.setAreadestino("-");
				}
				if(objDocumento.getPrioridad() != null){
					objItem.setPrioridad("images/Prioridad_"+objDocumento.getPrioridad()+".png");

					List<Parametro> listaPrioridad = parametroDAO.getPrioridades();
					for(Parametro p: listaPrioridad){
						if(String.valueOf(objDocumento.getPrioridad()).equals(p.getValor())){
							objItem.setExcprioridad(String.valueOf(p.getDescripcion()));
						}
					}
				}

				String estado = "";
				switch (objDocumento.getEstado()){
				case Constantes.ESTADO_ACTIVO:
					estado = "Activo";
					break;
				case Constantes.ESTADO_ANULADO:
					estado = "Anulado";
					break;
				case Constantes.ESTADO_CERRADO:
					estado = "Archivado";
					break;
				case Constantes.ESTADO_INACTIVO:
					estado = "Inactivo";
					break;
				};

				objItem.setEstado(estado);

				lstItem.add(objItem);
			}
		}

		return lstItem;
	}

	public Map<String, List<Estructura>> getStructuresByRol(Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getStructuresByRol():Map<String, List<Estructura>> ");

                Integer idRol = objUsuario.getIdRolPerfil();
		Rol rol = rolDao.findByIdRol(idRol);
		
                if (rol == null) {
                   rol = rolDao.encontrarPorNombre(Constantes.ROL_USUARIO_FINAL);
		}
               
                List<Grid> lstGrid = gridDao.findByRol(rol.getIdrol());
	        Map<String, List<Estructura>> salida = new HashMap<String, List<Estructura>>();
		for (Grid g : lstGrid) {
                   salida.put(g.getCodigo(), buildEstructura(dao.findByIdUsuario(objUsuario.getIdusuario()), gridXGridColumnaDao.encontrarPorGrid(g.getIdGrid()), objUsuario.getIdusuario()));
		}
		return salida;
	
	}

	public void setRolDao(RolDAO rolDao) {
		this.rolDao = rolDao;
	}

	public void setGridDao(GridDAO gridDao) {
		this.gridDao = gridDao;
	}

	@SuppressWarnings("unused")
	private List<Item> getItems_Usuario_Mensajeria(Usuario objUsuario, char estadoActivo) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Usuario_Mensajeria():List<Item> ");

		List<Mensajeria> lstMensajeria = null;
		List<Item> lstItem = new ArrayList<Item>();
		try {
			//lstMensajeria = srvMensajeria.findidusuario(objUsuario.getIdusuario());
			lstMensajeria = srvMensajeria.ViewEstado(String.valueOf(estadoActivo));
			if (lstMensajeria == null) {
				_log.debug("El usuario [" + objUsuario.getIdusuario() + "] no tiene documentos en mensajeria");
				return null;
			}


			_log.debug("Se encontraron [" + lstMensajeria.size() + "] documentos en mensajeria pertenecientes al usuario [" + objUsuario.getUsuario() + "]");

			for (Mensajeria objMensajeria : lstMensajeria) {
				Item objItem = new Item();
				Datos datos = new Datos();
				//AmbitoEnvio idAmbitoEnvio = new AmbitoEnvio();
				objItem.setUsuario(objMensajeria.getIdusuario().getUsuario());
				//no mostrar //objItem.setNrointerno(objMensajeria.getNumerointerno());
				objItem.setTipodocumento(objMensajeria.getTipodocumento());
				objItem.setDestinatario(objMensajeria.getDestinatario());
				objItem.setEtapa(String.valueOf(objMensajeria.getEstadoingreso()));
				objItem.setFecharecepcion(objMensajeria.getFechaderivacion());
				//NO MOSTRAR//objItem.setId(objMensajeria.getIdmensajeria());

				Tipoenvio tipEnvio = objMensajeria.getIdtipoenvio();
				objItem.setTipoEnvio(tipEnvio.getTipoenvio2());
				//

				//                AmbitoEnvio idAmbitoEnvio = null;
				//                if (objMensajeria.getDatos() != null) {
				//                    idAmbitoEnvio = objMensajeria.getDatos().getIdambitoenvio();
				//                    objItem.setAmbitoEnvio(idAmbitoEnvio.getNomambitoenvio().toString());
				//                } else {
				//                    objItem.setAmbitoEnvio("");
				//                }



				//datos = getDatosDAO().findId(objMensajeria.getIdmensajeria());\
				//idAmbitoEnvio = datos.getIdambitoenvio();
				//objItem.setAmbitoEnvio(idAmbitoEnvio.getNomambitoenvio());
				Integer idDoc = objMensajeria.getIddocumento();
				Documento doc = documentoService.findByIdDocumento(idDoc);
				//NO MOSTRAR //  objItem.setDocumento(doc.getTipoDocumento().getNombre() + " - " + doc.getNumeroDocumento());
				objItem.setExpediente(doc.getExpediente().getNroexpediente());

				Cargo objCargo = cargoService.findkey(objMensajeria.getIdmensajeria());
				objItem.setEstado(objCargo != null && objCargo.getEstado() != null && !objCargo.getEstado().equals("") ? objCargo.getEstado() : "");

				objItem.setNrodocumento(objMensajeria.getNumerodocumento());

				lstItem.add(objItem);
				// objItem.setNrointerno(objMensajeria.getNumerointerno());
				objItem.setId(objMensajeria.getIdmensajeria());
				//objItem.setDocumento(doc.getTipoDocumento().getNombre() + " - " + doc.getNumeroDocumento());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstItem;
	}

	/**REN Se encarga de buscar los expedientes que ir�n en cada bandeja --------------------------*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getItems(String sTipoGrid, Usuario objUsuario, BusquedaAvanzada objFiltro) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems():List ");
                
                List lstItem = null;
                if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_DOCUMENTO)) {
                        lstItem = this.getBandejaDocUsuarioFinalFiltro(objUsuario, "R" ,objFiltro); //recibidos
		} else if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_DOCUMENTOS_ARCHIVADOS)) {
                        lstItem = this.getBandejaDocAtendidosPendientesUsuarioFinal(objUsuario, objFiltro);//this.getBandejaDocAtendidosUsuarioFinal(objUsuario, objFiltro);
		} else if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_DOCUMENTOENVIADO)) {
                       lstItem = this.getItems_DocumentoEnviado(objUsuario, objFiltro);
		} else if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_EXPEDIENTE)) {
                        lstItem = this.getBandejaDocUsuarioFinal(objUsuario, "M", objFiltro);
		} else if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_INFORMATIVO)) {
                        lstItem = this.getItems_Informativo(objUsuario, objFiltro);
		}else if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_SEGUIMIENTO)) {
                        lstItem = this.getItems_Usuario_Seguimiento(objUsuario);
		}else if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_PENDIENTES)) {
                        lstItem = this.getItems_Pendiente_Respuesta(objUsuario, objFiltro);
                }else if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_ANULADOS)) {
                        lstItem = this.getItems_Anulados(objUsuario, objFiltro);
                }else if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_PARA_FIRMAR)) {
                        lstItem = this.getItems_Firmar(objUsuario, "F" , objFiltro);
                }else if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_RECEPCION_VIRTUAL)) {
                        lstItem = this.getItems_Recepcion_Virtual(objUsuario, objFiltro);
                }else if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_DESPACHO_VIRTUAL)) {
                        lstItem = this.getItems_Despacho_Virtual(objUsuario, objFiltro);
                }if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_MI_LEGAJO)) {
                        lstItem = this.getBandejaLegajo(objUsuario, objFiltro);
		}else if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_LEGAJO_COMPARTIDO)) {
                        lstItem = this.getBandejaLegajoCompartido(objUsuario, objFiltro);
                }else if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_RECEPCION_VIRTUAL_OBSERVADOS)) {
                         lstItem = this.getBandejaObservadosRecepcionVirtual(objUsuario, "OBR", objFiltro);
                }
                
		return lstItem;
	}

	/**REN Se encarga de buscar los expedientes que ir�n en cada bandeja --------------------------*/
	@SuppressWarnings("rawtypes")
	public List getItemsDocumentosXExpediente(String sTipoGrid, Integer idDocumento, Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItemsDocumentosXExpediente():List ");

		if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_DOCUMENTO)) {
			return documentoService.findDocumentosXExpediente(objUsuario, idDocumento, true);
		} else if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_EXPEDIENTE)) {
			return documentoService.findDocumentosXExpediente(objUsuario, idDocumento, false);
		}

		return null;
	}

	/** No incluye los expedientes de cada usuario que ha compartido */
	@SuppressWarnings("rawtypes")
	public List getItemsSinBandejaCompartida(Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItemsSinBandejaCompartida():List ");

		return this.getBandejaUsuaruioSinBandejaCompartida(objUsuario);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getItems_Documento(Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Documento():List ");

		Cliente objCliente = null;
		List<Documento> lstDocumento = null;
		List<Item> lstItem = new ArrayList<Item>();
		String sNombre = null;
		String sApellido = null;
		String sRazonSocial = null;

		lstDocumento = documentoService.findDocumentos(objUsuario);

		if (lstDocumento == null) {
			_log.info("El usuario [" + objUsuario.getIdusuario() + "] no tiene documentos");

			return null;
		}

		_log.info("Se encontraron [" + lstDocumento.size() + "] documentos pertenecientes al usuario [" + objUsuario.getUsuario() + "]");

		for (Documento objDocumento : lstDocumento) {
			Item objItem = new Item();

			objItem.setLeido(objDocumento.getLeido());
			objItem.setId(objDocumento.getIdDocumento());
			objItem.setTipoalerta("images/bolitaRoja.gif");
			// objItem.setRemitente(objDocumento.getRemitente());
			objItem.setAsunto(objDocumento.getAsunto());
			objItem.setFecharecepcion(objDocumento.getFechaAccion());
			objItem.setLfecharecepcion(objDocumento.getFechaAccion().getTime());
			objItem.setFechalimite(objDocumento.getFechaLimiteAtencion());
			objItem.setLfechalimite(objDocumento.getFechaLimiteAtencion() != null ? objDocumento.getFechaLimiteAtencion().getTime() : 0L);
			objItem.setEtapa(objDocumento.getEtapa() != null ? objDocumento.getEtapa() : "");
			objItem.setEstado(String.valueOf(objDocumento.getEstado()).equals(String.valueOf(Constantes.ESTADO_ACTIVO)) ? Constantes.EXPEDIENTE_ESTADO_ACTIVO : String.valueOf(objDocumento.getEstado()));
			objItem.setActividad(objDocumento.getActividad() != null ? objDocumento.getActividad() : "");

			if (objDocumento.getPorcentajealertaA() != null) {
				objItem.setPorcentajealertaA(objDocumento.getPorcentajealertaA());
			}
			if (objDocumento.getPorcentajealertaR() != null) {
				objItem.setPorcentajealertaR(objDocumento.getPorcentajealertaR());
			}

			if (objDocumento.getArchivos() != null && objDocumento.getArchivos().size() > 0) {
				objItem.setUrlarchivo(objDocumento.getArchivos().get(0).getRutaAlfresco());
				_log.debug("Ruta Alfresco [" + objDocumento.getArchivos().get(0).getRutaAlfresco() + "]");
			}

			if (!objDocumento.isEsTransient()) {
				Trazabilidaddocumento trazabilidad = srvTrazabilidadDocumento.findByMaxNroRegistro(objDocumento.getIdDocumento(), null, null, null);
				objItem.setRemitente("");

				if (trazabilidad != null) {
					Usuario remitente = trazabilidad.getRemitente();

					if (remitente != null) {
						objItem.setRemitente(remitente.getNombres() + " " + remitente.getApellidos());
					}
				}

				objItem.setDocumento(objDocumento.getNumeroDocumento());
				objItem.setExpediente(objDocumento.getExpediente().getNroexpediente());
				if (objDocumento.getIdProceso() != null) {
					objItem.setIdproceso(objDocumento.getIdProceso());
				}
				//objItem.setProceso(objDocumento.getExpediente().getProceso().getNombre());



				Concesionario concesionario = objDocumento.getExpediente().getConcesionario();

				if (concesionario != null) {
					objItem.setConcesionario(concesionario.getRazonSocial());
				}

				objCliente = objDocumento.getExpediente().getCliente();
				sNombre = objDocumento.getExpediente().getClientenombres() != null ? objDocumento.getExpediente().getClientenombres() : objCliente.getNombres();
				sApellido = objDocumento.getExpediente().getClienteapellidopaterno() != null ? objDocumento.getExpediente().getClienteapellidopaterno() : objCliente.getApellidoPaterno();
				sRazonSocial = objDocumento.getExpediente().getClienterazonsocial() != null ? objDocumento.getExpediente().getClienterazonsocial() : objCliente.getRazonSocial();
				objItem.setCliente(objCliente != null ? objCliente.getTipoIdentificacion().getNombre().equalsIgnoreCase(Constantes.TIPO_IDENTIFICACION_RUC) ? sRazonSocial : sNombre + " " + sApellido : null);
			} else {
				objItem.setRemitente(objDocumento.getNombreRemitente());
				objItem.setDocumento(objDocumento.getNombreTipoDocumento());
				objItem.setExpediente(objDocumento.getNroexpediente());
				objItem.setProceso(objDocumento.getNombreProceso());

				if (objDocumento.getRazonSocialConcesionario() != null) {
					objItem.setConcesionario(objDocumento.getRazonSocialConcesionario());
				}
				if (objDocumento.getClienteTipoIdentificacion() != null) {
					if (objDocumento.getClienteTipoIdentificacion().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
						objItem.setCliente(objDocumento.getClienteRazonSocial());
					} else {
						objItem.setCliente(objDocumento.getClienteNombre());
					}
				}
				if (objDocumento.getIdProceso() != null) {
					objItem.setIdproceso(objDocumento.getIdProceso());
				}
			}

			lstItem.add(objItem);
		}

		return lstItem;
	}

	/**REN Busca los documentos para llenar la grilla -------------------------------------------------------------------------*/
	@SuppressWarnings("rawtypes")
	public List getBandejaDocUsuarioFinal(Usuario objUsuario, String bandeja, BusquedaAvanzada objFiltro) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getBandejaDocUsuarioFinal():List ");

                if(objFiltro == null){
			return documentoService.findDocumentosUsuarioFinal(objUsuario, bandeja);
		}else{
			return documentoService.findDocumentosUsuarioFinalFiltro(objUsuario, bandeja,objFiltro);
		}
                
	}
        
        @SuppressWarnings("rawtypes")
	public List getBandejaObservadosRecepcionVirtual(Usuario objUsuario, String bandeja, BusquedaAvanzada objFiltro) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getBandejaObservadosRecepcionVirtual():List ");

                if(objFiltro == null){
			return documentoService.findDocumentosUsuarioFinal(objUsuario, bandeja);
		}else{
			return documentoService.findDocumentosUsuarioFinalFiltro(objUsuario, bandeja,objFiltro);
		}
                
	}
        
        @SuppressWarnings("rawtypes")
	public List getBandejaDocUsuarioFinalRecepcionVirtual(Usuario objUsuario, String bandeja, BusquedaAvanzada objFiltro) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getBandejaDocUsuarioFinalRecepcionVirtual():List ");

                if(objFiltro == null){
			return documentoService.findDocumentosUsuarioFinal(objUsuario, bandeja);
		}else{
			return documentoService.findDocumentosUsuarioFinalFiltro(objUsuario, bandeja,objFiltro);
		}
                
	}
        
        

	public List<ItemUF> getItems_Usuario_Seguimiento(Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Usuario_Seguimiento():List ");

		return seguimientoXUsuarioService.getItemsSeguimientoXUsuario(objUsuario);
	}
        
        public List getBandejaLegajo(Usuario objUsuario, BusquedaAvanzada objFiltro) {
            _log.debug("-> [Service] GridcolumnaxusuarioService - getBandejaLegajo():List ");
            List<FilaBandejaLegajo> list = null;
            List<Item> lstItem = new ArrayList<Item>();
            
            if(objFiltro == null){
               list = legajoService.findLegajosUsuarioFinal(objUsuario);
            }
            
            if (list!=null){
                for (FilaBandejaLegajo fila : list) {
                   Item objItem = new Item();
                   objItem.setId(fila.getId());
                   objItem.setLegajo(fila.getNroLegajo());
                   objItem.setTipolegajo(fila.getDesTipo());
                   objItem.setArealegajo(fila.getDesUnidad());
                   objItem.setAsuntolegajo(fila.getAsunto());
                   objItem.setFechacreacion(fila.getFechaCreacion());
                   objItem.setEstado(fila.getEstado());
                   objItem.setIdProcedimiento(fila.getIdProcedimiento()==null?"":fila.getIdProcedimiento());
                   objItem.setIdMetodo(fila.getIdMetodo()==null?"":fila.getIdMetodo());
                   lstItem.add(objItem);
               }
            }    
              
            return lstItem;
        }
        
        public List getBandejaLegajoCompartido(Usuario objUsuario, BusquedaAvanzada objFiltro) {
            _log.debug("-> [Service] GridcolumnaxusuarioService - getBandejaLegajoCompartido():List ");
            List<FilaBandejaLegajo> list = null;
            List<Item> lstItem = new ArrayList<Item>();
            
            if(objFiltro == null){
               list = legajoService.findLegajosCompartidos(objUsuario);
            }
            
            if (list!=null){
                for (FilaBandejaLegajo fila : list) {
                   Item objItem = new Item();
                   objItem.setId(fila.getId());
                   objItem.setLegajo(fila.getNroLegajo());
                   objItem.setTipolegajo(fila.getDesTipo());
                   objItem.setArealegajo(fila.getDesUnidad());
                   objItem.setAsuntolegajo(fila.getAsunto());
                   objItem.setFechacreacion(fila.getFechaCreacion());
                   objItem.setEstado(fila.getEstado());
                   objItem.setIdProcedimiento(fila.getIdProcedimiento()==null?"":fila.getIdProcedimiento());
                   objItem.setIdMetodo(fila.getIdMetodo()==null?"":fila.getIdMetodo());
                   lstItem.add(objItem);
               }
            }    
              
            return lstItem;
        }
        
        public List getItems_Firmar(Usuario objUsuario, String bandeja, BusquedaAvanzada objFiltro) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Firmar():List ");
                List<ItemUF> list = null;
                if(objFiltro == null){
                         list = documentoService.findDocumentosUsuarioFinal(objUsuario, bandeja);
		}else{
                         list = documentoService.findDocumentosUsuarioFinalFiltro(objUsuario, bandeja,objFiltro);
		}
                
                if  (list!=null){
                    for(int i=0;i<list.size();i++){
                        if (list.get(i).getNumeroTrazabilidad()==1){
                            list.get(i).setRemitente("");
                            list.get(i).setArea("");
                        }
                    }
                }
                
                return list;
        
	}

	@SuppressWarnings("rawtypes")
	public List getBandejaUsuaruioSinBandejaCompartida(Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getBandejaUsuaruioSinBandejaCompartida():List ");

		return documentoService.findByDataUFWithoutSharedInbox(objUsuario.getIdusuario());
	}

	@SuppressWarnings("rawtypes") ///////////////////////////////JC
	public List getBandejaDocAtendidosUsuarioFinal(Usuario objUsuario, BusquedaAvanzada objFiltro) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getBandejaDocAtendidosUsuarioFinal():List ");

		if(objFiltro == null){
			return documentoService.findDocumentosAtendidosUsuarioFinal(objUsuario);
		}else{
			return documentoService.findDocumentosAtendidosUsuarioFinal(objUsuario, objFiltro);
		}

	}
        
        @SuppressWarnings("rawtypes")
	public List getBandejaDocAtendidosPendientesUsuarioFinal(Usuario objUsuario, BusquedaAvanzada objFiltro) {
	      _log.debug("-> [Service] GridcolumnaxusuarioService - getBandejaDocAtendidosPendientesUsuarioFinal():List ");
              List<FilaBandejaAtendido> lstAtendidos = null;
              List<Item> lstItem = new ArrayList<Item>();
                
              if(objFiltro == null){
                  lstAtendidos = documentoAtendidoDAO.buscarAtendidosPorUsuario(objUsuario);
	      }else{
                  lstAtendidos = documentoAtendidoDAO.buscarAtendidosPorUsuario(objUsuario, objFiltro);
	      }
                
              for (FilaBandejaAtendido fila : lstAtendidos) {
                  Item objItem = new Item();
		  objItem.setAsunto(fila.getAsunto());
                  objItem.setCliente(fila.getCliente() == null ? "": fila.getCliente());
		  objItem.setDocumento(fila.getDocumento());
		  objItem.setId(fila.getId());
                  objItem.setIdPropietario(fila.getUsuario().toString());
                  objItem.setPropietario(fila.getNombreUsuario());
                  objItem.setExpediente(fila.getExpediente());
		  objItem.setAsuntoExpediente(fila.getAsuntoExpediente());
                  objItem.setFechaatender(fila.getFechaCreacion());
                  objItem.setEstado(fila.getEstado());
                  objItem.setNroTramite(fila.getNroTramite());
                  lstItem.add(objItem);
              }
              
              return lstItem;

	}
        
        @SuppressWarnings("rawtypes")
	public List getItems_Recepcion_Virtual(Usuario objUsuario, BusquedaAvanzada objFiltro) {
	      _log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Recepcion_Virtual():List ");
              List<IotdtmDocExterno> lstRecepcion = null;
              List data = new ArrayList();
              
              if(objFiltro == null){  
                  lstRecepcion = documentoExternoVirtualDAO.buscarRecepcionVirtual(objUsuario);
	      }else{
                  lstRecepcion = documentoExternoVirtualDAO.buscarRecepcionVirtual(objUsuario);
	      }
              
              if (lstRecepcion!=null){
                for(int i=0; i<lstRecepcion.size();i++){
                    ItemUF uuf = new ItemUF();
                    Tipodocumento tipoDocumento = tipoDocumentoDAO.findByIdTipoDocumentoPIDE(lstRecepcion.get(i).getCcodtipdoc());
                    uuf.setId(lstRecepcion.get(i).getSiddocext().intValue());
                    uuf.setDocumento(tipoDocumento.getNombre() + " - " + lstRecepcion.get(i).getVnumdoc());
                    uuf.setAsunto(lstRecepcion.get(i).getVasu());
                    uuf.setEstado(parametroDAO.findByTipoAndValue(Constantes.ESTADOS_PIDE, lstRecepcion.get(i).getSidrecext().getCflgest().toString()).getDescripcion());
                    uuf.setIdEstado(lstRecepcion.get(i).getSidrecext().getCflgest().toString());
                    uuf.setCliente(lstRecepcion.get(i).getVnomentemi());
                    uuf.setFechacreacion(lstRecepcion.get(i).getSidrecext().getDfecreg());
                    uuf.setNroTramiteVirtual(lstRecepcion.get(i).getSidrecext().getVnumregstd()==null?"":lstRecepcion.get(i).getSidrecext().getVnumregstd());
                    uuf.setCuo(lstRecepcion.get(i).getSidrecext().getVcuo());
                    
                    if (lstRecepcion.get(i).getSidrecext().getCflgenvcar()=='S'){
                        uuf.setCargo("images/cargo.gif");
                    }else{
                        uuf.setCargo("images/ed_blank.gif");
                    }
                    
                    data.add(uuf);
                }
              }  
              return data;
        }
        
        @SuppressWarnings("rawtypes")
	public List getItems_Despacho_Virtual(Usuario objUsuario, BusquedaAvanzada objFiltro) {
	      _log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Despacho_Virtual():List ");
              List<IotdtmDocExterno> lstDespacho = null;
              List data = new ArrayList();
              
              if(objFiltro == null){
                  lstDespacho = documentoExternoVirtualDAO.buscarDespachoVirtual(objUsuario);
	      }else{
                  lstDespacho = documentoExternoVirtualDAO.buscarDespachoVirtual(objUsuario);
	      }
             
              if (lstDespacho!=null){
                for(int i=0; i<lstDespacho.size();i++){
                    ItemUF uuf = new ItemUF();
                    try{
                       Tipodocumento tipoDocumento = tipoDocumentoDAO.findByIdTipoDocumentoPIDE(lstDespacho.get(i).getCcodtipdoc());
                       uuf.setDocumento(tipoDocumento.getNombre() + " - " + lstDespacho.get(i).getVnumdoc());
                    }catch(Exception e){
                        e.printStackTrace();
                        uuf.setDocumento("    " + " - " + lstDespacho.get(i).getVnumdoc());
                    }
                    
                    try{
                       Cliente cliente =clienteService.findObjectBy(lstDespacho.get(i).getSidemiext().getVrucentrec(), 'A');
                       uuf.setCliente(cliente == null? "": cliente.getRazonSocial());
                    }catch(Exception e){
                       e.printStackTrace();
                       uuf.setCliente("  ");
                    }
              
                    if (lstDespacho.get(i).getSidemiext().getCflgest()=='O' || lstDespacho.get(i).getSidemiext().getCflgest()=='S'){
                         List<String> lst = documentoExternoVirtualDAO.buscarTramiteVirtual(lstDespacho.get(i).getSidemiext().getVnumregstd());
                         if (lst!=null && lst.get(0)!=null && !lst.get(0).equals("") && lst.get(0).equals(lstDespacho.get(i).getSiddocext().toString())){
                            IotdtmDocExterno despacho = documentoExternoVirtualDAO.buscarDocumentoVirtual(new Integer(lst.get(0))); 
                            Documento d = documentoService.findByIdDocumento(despacho.getSidemiext().getIddocumento());
                            if (d.getPropietario().getIdusuario().toString().equals(objUsuario.getIdUsuarioPerfil().toString()) && d.getUnidadpropietario().toString().equals(objUsuario.getIdUnidadPerfil().toString()) && d.getFlagMultiple()==null){
                              uuf.setDerivar("images/ed_blank.gif");
                            }else{
                              uuf.setDerivar("images/derivar.gif");
                            }  
                         }else{
                            uuf.setDerivar("images/derivar.gif");
                         }
                    }else{
                         uuf.setDerivar("images/ed_blank.gif");
                    }
                    
                  
                    if (lstDespacho.get(i).getSidemiext().getCflgenv() == 'E'){
                        uuf.setIntentos("images/rojo.png");
                    }else{
                        uuf.setIntentos("images/ed_blank.gif");
                    }

                    uuf.setNroTramiteEntidad(lstDespacho.get(i).getSidemiext().getVnumregstdrec()==null?"":lstDespacho.get(i).getSidemiext().getVnumregstdrec());
                    uuf.setNroTramiteVirtual(lstDespacho.get(i).getSidemiext().getVnumregstd()==null?"":lstDespacho.get(i).getSidemiext().getVnumregstd());
                    uuf.setId(lstDespacho.get(i).getSiddocext().intValue());
                    uuf.setAsunto(lstDespacho.get(i).getVasu());
                    uuf.setIdEstado(lstDespacho.get(i).getSidemiext().getCflgest().toString());
                    uuf.setEstado(parametroDAO.findByTipoAndValue(Constantes.ESTADOS_PIDE, lstDespacho.get(i).getSidemiext().getCflgest().toString()).getDescripcion());
                    uuf.setFechacreacion(lstDespacho.get(i).getSidemiext().getDfecreg());
                    uuf.setCuo(lstDespacho.get(i).getSidemiext().getVcuo()==null?"":lstDespacho.get(i).getSidemiext().getVcuo());
                    data.add(uuf);
                }
              }  
              
              return data;
        }
        
        @SuppressWarnings("rawtypes")
	public List getItems_Anulados(Usuario objUsuario, BusquedaAvanzada objFiltro) {
	      _log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Anulados():List ");
              List<FilaBandejaAnulado> lstAnulados = null;
              List<Item> lstItem = new ArrayList<Item>();
                
              if(objFiltro == null){  
                  lstAnulados = documentoAnuladoDAO.buscarAnuladosPorUsuario(objUsuario);
	      }else{
                  lstAnulados = documentoAnuladoDAO.buscarAnuladosPorUsuario(objUsuario, objFiltro);
	      }
                
              for (FilaBandejaAnulado fila : lstAnulados) {
                  Item objItem = new Item();
		  objItem.setAsunto(fila.getAsunto());
                  objItem.setCliente(fila.getCliente() == null ? "": fila.getCliente());
		  objItem.setDocumento(fila.getDocumento());
		  objItem.setId(fila.getId());
                  objItem.setExpediente(fila.getExpediente());
		  objItem.setAsuntoExpediente(fila.getAsuntoExpediente());
                  objItem.setFechaanulacion(fila.getFechaCreacion());
                  objItem.setEstado(fila.getEstado());
                  objItem.setNroTramite(fila.getNroTramite());
                  lstItem.add(objItem);
              }
              
              return lstItem;

	}

	public List<Item> getItems_ArchivoPendiente(Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_ArchivoPendiente():List<Item> ");

		List<ArchivoPendiente> lstArchivoPendiente = null;
		List<Item> lstItem = new ArrayList<Item>();

		lstArchivoPendiente = srvArchivoPendiente.findByIdusuario(objUsuario.getIdusuario());

		if (lstArchivoPendiente == null) {
			_log.debug("El usuario [" + objUsuario.getIdusuario() + "] no tiene archivos pendientes");

			return null;
		}

		_log.debug("Se encontraron [" + lstArchivoPendiente.size() + "] archivos pendientes pertenecientes al usuario [" + objUsuario.getUsuario() + "]");

		for (ArchivoPendiente objArchivoPendiente : lstArchivoPendiente) {
			Item objItem = new Item();
			Documento doc = objArchivoPendiente.getDocumento();

			//   Documento doc = objArchivoPendiente.getDocumento();
			objItem.setAsunto(objArchivoPendiente.getAsunto());
			objItem.setConcesionario(objArchivoPendiente.getConcesionario() != null ? objArchivoPendiente.getConcesionario().getRazonSocial() : null);
			objItem.setDocumento(doc.getNumeroDocumento());
			objItem.setFecharecepcion(doc.getFechaDocumento());
			objItem.setId(objArchivoPendiente.getIdArchivoPendiente());
			objItem.setEstado(objArchivoPendiente.getEstado());
			objItem.setRemitente(doc.getPropietario().getNombres() + " " + doc.getPropietario().getApellidos());

			lstItem.add(objItem);
		}

		return lstItem;
	}
        
        public List<Item> getItems_Pendiente_Respuesta(Usuario objUsuario, BusquedaAvanzada objFiltro) {
              _log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Pendiente Respuesta():List<Item> ");
              
              if(objFiltro == null){
                   return documentoService.findDocumentosPendientesUsuarioFinal(objUsuario);
	      }else{
		   return documentoService.findDocumentosPendientesUsuarioFinalFiltro(objUsuario , objFiltro);
	      }
        }
        

	public List<Item> getItems_DocumentoEnviado(Usuario objUsuario, BusquedaAvanzada objFiltro) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_DocumentoEnviado():List<Item> ");

                List<FilaBandejaEnviados> lstEnviados = null;
		List<Item> lstItem = new ArrayList<Item>();
		if(objFiltro == null){
                	lstEnviados = srvDocumentoEnviado.buscarEnviadosPorUsuario(objUsuario);
		}else{
                	lstEnviados = srvDocumentoEnviado.buscarEnviadosFiltrados(objUsuario, objFiltro);
		}

		if (lstEnviados == null) {
			_log.debug("El usuario [" + objUsuario.getIdusuario() + "] no tiene documentos enviados");

			return null;
		}

		_log.debug("Se encontraron [" + lstEnviados.size() + "] documentos enviados pertenecientes al usuario [" + objUsuario.getUsuario() + "]");

                for (FilaBandejaEnviados fila : lstEnviados) {
                      Item objItem = new Item();
                	objItem.setAsunto(fila.getAsunto());
                        objItem.setCliente("");
                        if (fila.getCliente()!=null){
                             if (fila.getCliente().getTipoinstitucion().getCod_tipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                                      String nombres =  fila.getCliente().getNombres()==null?"": fila.getCliente().getNombres();
                                      String paterno =  fila.getCliente().getApellidoPaterno()==null?"": fila.getCliente().getApellidoPaterno();
                                      String materno =  fila.getCliente().getApellidoMaterno()==null?"": fila.getCliente().getApellidoMaterno();
                                      objItem.setCliente(nombres + " " + paterno + " " + materno);
                                    }else{
                                      objItem.setCliente(fila.getCliente().getRazonSocial());
                                    }
                        }
                        objItem.setDocumento(fila.getNroDocumento());
                        objItem.setFechaenvio(fila.getFechaRecepcion());
			objItem.setId(fila.getId());
                        objItem.setDestinatario(fila.getDestinatario());
                        objItem.setIdDestinatario(fila.getIdDestinatario().toString());
			objItem.setExpediente(fila.getNroExpediente());
                        objItem.setNroTramite(fila.getNroTramite());
                        objItem.setAsuntoExpediente(fila.getAsuntoExpediente());
                        objItem.setTipoEnvio(fila.getTipoEnvio());
                        objItem.setEstado(fila.getEstado());
			objItem.setTipodocumento(fila.getIdTipoDocumento().toString());
                        objItem.setExterno("-1");  
                        
                        if (fila.getLlave()!=null && !fila.getLlave().trim().equals("")){
                            String valor = fila.getLlave().trim();
                            String valorAuxiliar = "";
                            try{
                                valor = valor.substring(0, valor.indexOf('|'));
                                valorAuxiliar = fila.getLlave().trim().substring(fila.getLlave().trim().indexOf('|') + 1, fila.getLlave().trim().length());
                                valor = valorAuxiliar.substring(0, valorAuxiliar.indexOf('|'));
                                valor = valorAuxiliar.substring(valorAuxiliar.indexOf('|') + 1, valorAuxiliar.length());
                                objItem.setIconoDocumento(fila.getLlave().trim());
                            }catch(Exception e){
                                objItem.setIconoDocumento("");  
                            }
                        }else{
                            objItem.setIconoDocumento("");  
                        }  
                        
                        if(fila.getPrioridad() != null){
			  objItem.setPrioridad("images/Prioridad_"+fila.getPrioridad()+".png");
			}
                        
                        objItem.setFechalimite(fila.getFechaLimite());
			objItem.setAccion(fila.getAccion());
                        lstItem.add(objItem);
		}
		return lstItem;
	}

	public List<Item> getItems_Mensajeria(Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Mensajeria():List<Item> ");

		List<Mensajeria> lstMensajeria = null;
		List<Item> lstItem = new ArrayList<Item>();

		lstMensajeria = srvMensajeria.findidusuario(objUsuario.getIdusuario());

		if (lstMensajeria == null) {
			_log.debug("El usuario [" + objUsuario.getIdusuario() + "] no tiene documentos en mensajeria");

			return null;
		}

		_log.debug("Se encontraron [" + lstMensajeria.size() + "] documentos en mensajeria pertenecientes al usuario [" + objUsuario.getUsuario() + "]");

		for (Mensajeria objMensajeria : lstMensajeria) {
			Item objItem = new Item();

			objItem.setUsuario(objMensajeria.getIdusuario().getUsuario());
			objItem.setNrointerno(objMensajeria.getNumerointerno());
			objItem.setTipodocumento(objMensajeria.getTipodocumento());
			objItem.setDestinatario(objMensajeria.getDestinatario());
			objItem.setAsunto(objMensajeria.getAsunto());
			objItem.setEstado(String.valueOf(objMensajeria.getEstado()));
			objItem.setEtapa(String.valueOf(objMensajeria.getEstadoingreso()));
			objItem.setFecharecepcion(objMensajeria.getFechaderivacion());
			objItem.setId(objMensajeria.getIdmensajeria());
			Documento doc = documentoService.findByIdDocumento(objMensajeria.getIddocumento());
			objItem.setExpediente(doc.getExpediente().getNroexpediente());
			objItem.setDocumento(doc.getTipoDocumento().getNombre() + "-" + doc.getNumeroDocumento());
			objItem.setNrodocumento(doc.getNumeroDocumento());
			lstItem.add(objItem);
		}

		return lstItem;
	}

	public List<Item> getItems_Notificacion(Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Notificacion():List<Item> ");

		List<Item> lstItem = srvItem.findLstNotificacionBy(objUsuario.getIdusuario(), Constantes.ESTADO_ACTIVO);

		if (lstItem == null) {
			_log.info("El usuario [" + objUsuario.getIdusuario() + "] no tiene notificaciones");

			return null;
		} else {
			_log.debug("Se encontraron [" + lstItem.size() + "] notificaciones pertenecientes al usuario [" + objUsuario.getUsuario() + "]");
		}


		return lstItem;

	}

	public List<Item> getItems_Proceso(Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Proceso():List<Item> ");

		List<Item> lstItem = new ArrayList<Item>();
		List<Proceso> lstProceso = null;

		lstProceso = srvProceso.findByIdResponsableOrAsistente(objUsuario.getIdusuario(), Constantes.ESTADO_ACTIVO);

		if (lstProceso == null) {
			_log.debug("El usuario [" + objUsuario.getIdusuario() + "] no tiene procesos");

			return null;
		}

		_log.debug("Se encontraron [" + lstProceso.size() + "] procesos pertenecientes al usuario [" + objUsuario.getUsuario() + "]");

		for (Proceso objProceso : lstProceso) {
			Item objItem = new Item();

			objItem.setId(objProceso.getIdproceso());
			objItem.setProceso(objProceso.getNombre());
			objItem.setTiempoatencion(objProceso.getTiempoatencion());
			objItem.setFecharecepcion(objProceso.getFechacreacion());

			lstItem.add(objItem);
		}

		return lstItem;
	}

	public List<Item> getItems_Expediente(Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Expediente():List<Item> ");
		/*Cliente objCliente = null;
      Expediente objExpediente = null;
      List<Documento> lstDocumento = null;
      List<Item> lstItem = new ArrayList<Item>();
      String sNombre = null;
      String sApellido = null;
      String sRazonSocial = null;

      lstDocumento = documentoService.buscarLstDocumentoPor(Constantes.DOCUMENTO_PRINCIPAL, objUsuario.getIdusuario());

      if (lstDocumento == null) {
      _log.debug("El usuario [" + objUsuario.getIdusuario() + "] no tiene expedientes");

      return null;
      }

      _log.debug("Se encontraron [" + lstDocumento.size() + "] expedientes pertenecientes al usuario [" + objUsuario.getUsuario() + "]");

      for (Documento objDocumento : lstDocumento) {
      Item objItem = new Item();

      objExpediente = objDocumento.getExpediente();
      objItem.setId(objExpediente.getIdexpediente());
      objItem.setExpediente(objExpediente.getNroexpediente());
      objItem.setProceso(objExpediente.getProceso().getNombre());
      objCliente = objExpediente.getCliente();
      sNombre = objExpediente.getClientenombres() != null ? objExpediente.getClientenombres() : objCliente.getNombres();
      sApellido = objExpediente.getClienteapellidopaterno() != null ? objExpediente.getClienteapellidopaterno() : objCliente.getApellidoPaterno();
      sRazonSocial = objExpediente.getClienterazonsocial() != null ? objExpediente.getClienterazonsocial() : objCliente.getRazonSocial();
      objItem.setCliente(objCliente != null ? objCliente.getTipoIdentificacion().getNombre().equalsIgnoreCase(Constantes.TIPO_IDENTIFICACION_RUC) ? sRazonSocial : sNombre + " " + sApellido : null);
      objItem.setFecharecepcion(objExpediente.getFechacreacion());

      lstItem.add(objItem);
      }

      return lstItem;*/

		/*List<Expediente> lstExpediente = null;
      List<Item> lstItem = new ArrayList<Item>();

      lstExpediente = srvExpediente.findLstBy(objUsuario.getIdusuario());

      if (lstExpediente == null) {
      _log.debug("El usuario [" + objUsuario.getIdusuario() + "] no tiene expedientes");

      return null;
      }

      _log.debug("Se encontraron [" + lstExpediente.size() + "] expedientes pertenecientes al usuario [" + objUsuario.getUsuario() + "]");

      for (Expediente objExpediente : lstExpediente) {
      Item objItem = new Item();

      objItem.setId(objExpediente.getIdexpediente());
      objItem.setExpediente(objExpediente.getNroexpediente());
      objItem.setProceso(objExpediente.getsProceso());
      objItem.setCliente(objExpediente.getsClienteTipoIdentificacion().equalsIgnoreCase(Constantes.TIPO_IDENTIFICACION_RUC) ? objExpediente.getClienterazonsocial() : objExpediente.getClientenombres() + " " + objExpediente.getClienteapellidopaterno() + " " + objExpediente.getClienteapellidomaterno());
      objItem.setFecharecepcion(objExpediente.getFechacreacion());

      lstItem.add(objItem);
      }

      return lstItem;*/

		List<Item> lstItem = null;

		lstItem = srvItem.findLstExpedienteBy(objUsuario.getIdusuario());

		if (lstItem == null) {
			_log.info("El usuario [" + objUsuario.getIdusuario() + "] no es ni propietario ni responsable ni asistente de ningun expediente");

			return null;
		}

		_log.debug("Se encontraron [" + lstItem.size() + "] expedientes asociados al usuario [" + objUsuario.getUsuario() + "]");

		return lstItem;
	}

	public List<Item> getItems_Informativo(Usuario objUsuario, BusquedaAvanzada objFiltro) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Informativo():List<Item> ");

		List<Item> lstItem = null;

		if(objFiltro == null){
                    	lstItem = srvItem.findLstInformativoBy(objUsuario, Constantes.ESTADO_ACTIVO);
		}else{
			lstItem = srvItem.findLstInformativoBy(objUsuario, objFiltro);
		}


		if (lstItem == null) {
			_log.info("El usuario [" + objUsuario.getIdusuario() + "] no tiene informativos");

			return null;
		}

		_log.debug("Se encontraron [" + lstItem.size() + "] informativos pertenecientes al usuario [" + objUsuario.getUsuario() + "]");
	
		return lstItem;
	}

	public List<Item> getItems_Reemplazo(Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Reemplazo():List<Item> ");

		List<Item> lstItem = new ArrayList<Item>();
		List<Proceso> lstProceso = null;
		List<Reemplazo> lstReemplazo = null;

		lstProceso = srvProceso.buscarProcesosxUsuarioParticipante(objUsuario);

		if (lstProceso == null) {
			_log.debug("El usuario [" + objUsuario.getIdusuario() + "] no participa en ningun proceso");

			return null;
		}

		_log.debug("Se encontraron [" + lstProceso.size() + "] procesos donde participa el usuario [" + objUsuario.getUsuario() + "]");

		for (Proceso objProceso : lstProceso) {
			Item objItem = new Item();

			objItem.setProceso(objProceso.getNombre());
			objItem.setIdproceso(objProceso.getIdproceso());
			//         objItem.setPorcentajealerta1(objProceso.getPorcentajealertaA());
			//         objItem.setPorcentajealerta2(objProceso.getPorcentajealertaR());
			lstReemplazo = srvReemplazo.findByIdreemplazadoIdproceso(objProceso.getIdproceso(), objUsuario.getIdusuario());

			if (lstReemplazo != null && lstReemplazo.size() > 0 && lstReemplazo.get(0) != null) {
				objItem.setId(lstReemplazo.get(0).getIdreemplazo());
				objItem.setFechainicio(lstReemplazo.get(0).getFechainicialreemplazo());
				objItem.setFechafin(lstReemplazo.get(0).getFechafinalreemplazo());
				objItem.setUsuarioreemplazante(lstReemplazo.get(0).getIdusuario().getNombres() + " " + lstReemplazo.get(0).getIdusuario().getApellidos());
			} else {
				_log.debug("No hay reemplazo para el proceso [" + objProceso.getNombre() + "]");
			}

			lstItem.add(objItem);
		}

		return lstItem;
	}

	public List<Item> getItems_MantReemplazo() {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_MantReemplazo():List<Item> ");

		List<Item> lstItem = new ArrayList<Item>();
		List<Reemplazo> lstReemplazo = null;

		lstReemplazo = srvReemplazo.findLstBy(Constantes.ESTADO_ACTIVO);

		if (lstReemplazo != null) {
			_log.debug("Se encontraron [" + lstReemplazo.size() + "] reemplazo(s)");

			for (Reemplazo objReemplazo : lstReemplazo) {
				Item objItem = new Item();

				objItem.setId(objReemplazo.getIdreemplazo());
				objItem.setReemplazado(objReemplazo.getNombreReemplazado());
				objItem.setProceso(objReemplazo.getNombreProceso());
				objItem.setReemplazante(objReemplazo.getIdusuario().getNombres() + " " + objReemplazo.getIdusuario().getApellidos());

				lstItem.add(objItem);
			}
		}

		return lstItem;
	}

	/**
	 * Busca documentos del area donde furon creados asi como los documentos del area funcional.
	 *
	 * @author Wilber Carrasco - 18-08-2011
	 */
	public List<Item> getItems_BusquedaSimple(String sParametroBusqueda, Usuario usuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_BusquedaSimple():List<Item> ");

		List<Item> items = new ArrayList<Item>();
		List<DocumentoAreaFuncional> resultados = documentoService.busquedaDocumentoAreaFuncionalSimple(sParametroBusqueda);//17-08-2011 wcarrasco requerimiento APN
		
		if (resultados == null || resultados.size() == 0) {
			_log.info("No se encontro ningun documento con el filtro de busqueda [" + sParametroBusqueda + "]");
			return null;
		}

		_log.debug("Se encontraron [" + resultados.size() + "] documentos para el filtro de busqueda [" + sParametroBusqueda + "]");


		for (DocumentoAreaFuncional documento : resultados) {
			Item item = new Item();
			item.setId(documento.getId());
			item.setIdproceso(documento.getIdProceso());
			item.setAsuntodocumento(documento.getAsuntoDocumento());
			item.setNrodocumento(documento.getNroDocumento());
			item.setFecharecepcion(documento.getFechaCreacion());
			item.setExpediente(documento.getNroExpediente());
			item.setAsuntoExpediente(documento.getAsuntoExpediente());
			item.setCliente(documento.getCliente());
			item.setPropietario(documento.getPropietario());
			item.setArea(documento.getAreaDestino());
			item.setAccion(documento.getAccion());
			item.setFechalimite(documento.getFechaLimite());
			item.setRemitente(documento.getRemitente());
			item.setPrioridad(documento.getPrioridad());
			items.add(item);
		}

		//17-08-2011 wcarrasco en desuso
		/*
      for (Documento documento : resultados) {
         Item item = new Item();
         item.setId(documento.getIdDocumento());
         item.setIdproceso(documento.getExpediente().getProceso().getIdproceso());
         item.setAsuntodocumento(documento.getAsunto());
         item.setTipodocumento(documento.getTipoDocumento().getNombre());
         item.setNrodocumento(documento.getNumeroDocumento());
         item.setNumeroMesaPartes(documento.getNumeroMesaPartes());
         item.setFecharecepcion(documento.getFechaCreacion());

         Expediente expediente = documento.getExpediente();
         if (expediente != null) {
            item.setExpediente(expediente.getNroexpediente());
            item.setAsuntoExpediente(expediente.getAsunto());
            item.setProceso(expediente.getProceso().getNombre());
            Concesionario concesionario = expediente.getConcesionario();
            if (concesionario != null) {
               item.setConcesionario(concesionario.getRazonSocial());
            }
            Cliente cliente = expediente.getCliente();
            if (cliente != null) {
               if (cliente.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
                  item.setCliente(cliente.getRazonSocial());
               } else {
                  item.setCliente(cliente.getNombres() + " " + cliente.getApellidoPaterno() + " " + cliente.getApellidoMaterno());
               }
            }
            char estado = expediente.getEstado();
            if (estado == Constantes.ESTADO_ACTIVO) {
               item.setEstado("Activo");
            } else if (estado == Constantes.ESTADO_INACTIVO) {
               item.setEstado("Archivado");
            } else if (estado == Constantes.ESTADO_ANULADO) {
               item.setEstado("Anulado");
            }
         }
         Usuario propietario = documento.getPropietario();
         if (propietario != null) {
            item.setPropietario(propietario.getNombres() + " " + propietario.getApellidos());
            item.setArea(propietario.getUnidad().getNombre());
         }
         items.add(item);
      }*/
		return items;
	}

	@Override
	public List<Item> getItems_BusquedaAvanzada(String sTipoFiltro, BusquedaAvanzada objFiltro, String arrFecha[], String sqlQueryDinamico[], String tipoConsulta, String unidadUsuario, String cargoUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_BusquedaAvanzada():List<Item> ");

		List<Item> items = new ArrayList<Item>();
		_log.debug("Se buscara segun el filtro [" + sTipoFiltro + "]");
                List<Documento> resultados = documentoService.busquedaDocumentoAvanzada(
				objFiltro.getContenido(), objFiltro.getNumeroDocumento(),
				objFiltro.getNumeroMesaPartes(), objFiltro.getAsuntodocumento(),
				objFiltro.getNumeroExpediente(), objFiltro.getAsuntoexpediente(),
				objFiltro.getEstadoexpediente(), objFiltro.getConcesionario(),
				objFiltro.getCliente(), objFiltro.getProceso(),
				objFiltro.getPropietario(), objFiltro.getAreaDestino(),
				objFiltro.getTipoDocumento(), arrFecha[0], arrFecha[1],
				arrFecha[2], arrFecha[3], objFiltro.getNroSuministro(), objFiltro.getTipoBusqueda(), objFiltro.getAreaOrigen(), objFiltro.getAreaAutor(), sqlQueryDinamico,
                                objFiltro.getNroHT(), objFiltro.getNroRS(), objFiltro.getNumeroLegajo(), objFiltro.getTipoLegajo(), tipoConsulta, unidadUsuario, cargoUsuario, objFiltro.getAutor());
		if (resultados == null || resultados.size() == 0) {
			_log.info("No se encontro ningun documento con el filtro de busqueda avanzada");
			return null;
		}
		_log.debug("Se encontraron [" + resultados.size() + "] documentos para el filtro de busqueda avanzada");

		for (Documento documento : resultados) {
			Item item = new Item();
			item.setId(documento.getIdDocumento());
			//item.setIdproceso(documento.getExpediente().getProceso().getIdproceso());
			item.setAsuntodocumento(documento.getAsunto());
			item.setTipodocumento(documento.getTipoDocumento().getNombre());
			item.setNrodocumento(documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento());
			item.setNumeroMesaPartes(documento.getNumeroMesaPartes());
			item.setFecharecepcion(documento.getFechaCreacion());
                        item.setNroTramite(documento.getID_CODIGO());
                        item.setPropietario(documento.getPropietario().getNombres() + " " + documento.getPropietario().getApellidos());
                        item.setAutor(documento.getAutor().getNombres() + " " + documento.getAutor().getApellidos());

			Expediente expediente = documento.getExpediente();
			if (expediente != null) {
				item.setExpediente(expediente.getNroexpediente());
				item.setAsuntoExpediente(expediente.getAsunto());
				//item.setProceso(expediente.getProceso().getNombre());
				Concesionario concesionario = expediente.getConcesionario();
				if (concesionario != null) {
					item.setConcesionario(concesionario.getRazonSocial());
				}
				item.setCliente(expediente.getCliente().getNombreRazon());
				char estado = documento.getEstado();
				if (estado == Constantes.ESTADO_ACTIVO) {
					item.setEstado("Registrado");
				} else if (estado == Constantes.ESTADO_CERRADO) {
					item.setEstado("Archivado");
				} else if (estado == Constantes.ESTADO_ANULADO) {
					item.setEstado("Anulado");
				}else if (estado == Constantes.ESTADO_ATENDER) {
					item.setEstado("Atendido");
				}else if (estado == Constantes.ESTADO_PENDIENTE) {
					item.setEstado("Pendiente");
				}
			}
			Usuario propietario = documento.getPropietario();
			if (propietario != null) {
				item.setPropietario(propietario.getNombres() + " " + propietario.getApellidos());
				item.setArea(propietario.getUnidad().getNombre());
			}

            if(documento.getPrioridad() != null){
            	item.setPrioridad("images/Prioridad_"+documento.getPrioridad()+".png");
            }else{
               item.setPrioridad("images/ed_blank.gif");
            }
            
            if (documento.getCliente()!=null){
            	item.setCliente(documento.getCliente().getNombreRazon());
            }else
            	item.setCliente("");

            item.setFechalimite(documento.getFechaLimiteAtencion());
			items.add(item);
		}
		return items;
	}

	public List<Item> getCamposPorPlantilla(Integer iTipoPlantilla) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getCamposPorPlantilla():List<Item> ");

		List<Item> lstItem = new ArrayList<Item>();

		List<Campo> lstCampo = srvPlantilla.listCamposByTipoPlantilla(iTipoPlantilla);

		for (Campo objCampo : lstCampo) {
			Item objItem = new Item();

			objItem.setId(objCampo.getIdCampo());
			objItem.setCodigo(objCampo.getCodigo());
			objItem.setDescripcion(objCampo.getDescripcion());
			objItem.setTipo(objCampo.getTipo().equals("TX") ? "Caja de Texto" : "Texto Ampliado");
			objItem.setValor(objCampo.getValorDefecto());

			lstItem.add(objItem);
		}

		return lstItem;
	}

	public List<Item> getItems_Lista(Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getItems_Lista():List<Item> ");

		List<Item> lstItem = new ArrayList<Item>();
		List<Lista> lstLista = null;

		lstLista = srvLista.findLstBy(objUsuario, Constantes.ESTADO_ACTIVO);

		if (lstLista == null) {
			_log.debug("El usuario [" + objUsuario.getUsuario() + "] no administra ninguna lista");

			return null;
		}

		_log.debug("El usuario [" + objUsuario.getUsuario() + "] administra [" + lstLista.size() + "] lista(s)");

		for (Lista objLista : lstLista) {
			Item objItem = new Item();

			objItem.setId(objLista.getIdlista());
			objItem.setNombre(objLista.getNombre());
			objItem.setFechacreacion(objLista.getFechacreacion());

			lstItem.add(objItem);
		}

		return lstItem;
	}

	public List<Estructura> getEstructura(String sTipoGrid, Usuario objUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getEstructura():List<Estructura> ");
                Integer iIdGrid = null;
		List<Estructura> estructura = null;
		List<Gridcolumnaxusuario> lstGridColumnaXUsuario = null;
		List<GridXGridColumna> columnas = null;
                iIdGrid = gridDao.findIDByCodigo(sTipoGrid);
                lstGridColumnaXUsuario = dao.findByIdUsuario(objUsuario.getIdusuario());
                columnas = gridXGridColumnaDao.encontrarPorGrid(iIdGrid);
                estructura = buildEstructura(lstGridColumnaXUsuario, columnas, objUsuario.getIdusuario());
                return estructura;
	}

	// ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public ArchivoService getSrvArchivo() {
		return srvArchivo;
	}

	public void setSrvArchivo(ArchivoService srvArchivo) {
		this.srvArchivo = srvArchivo;
	}

	public GridcolumnaxusuarioDAO getDao() {
		return dao;
	}

	public void setDao(GridcolumnaxusuarioDAO dao) {
		this.dao = dao;
	}

	public TrazabilidaddocumentoService getSrvTrazabilidadDocumento() {
		return srvTrazabilidadDocumento;
	}

	public void setSrvTrazabilidadDocumento(TrazabilidaddocumentoService srvTrazabilidadDocumento) {
		this.srvTrazabilidadDocumento = srvTrazabilidadDocumento;
	}

	/**
	 * Almacena el estado actual del grid en la bandeja para un usuario
	 * determinado
	 *
	 * @param columnas
	 *            las columnas que han sido modificadas por el usuario
	 * @param idUsuario
	 *            el identificador del usuario en sesion
	 * @author German Enriquez
	 */
	@Override
	@Transactional
	public void guardarGridUsuario(GridUsuario[] columnas, int idUsuario) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - guardarGridUsuario():void ");

		for (GridUsuario columna : columnas) {
			String field = columna.getField();
			int id = columna.getIdGridXGridColumna();
                        if (id != -1) {
				Gridcolumnaxusuario gridColumnaPorUsuario = dao.find(idUsuario, id);
				if (gridColumnaPorUsuario == null) {
					gridColumnaPorUsuario = new Gridcolumnaxusuario(idUsuario, id);
				}
				int hidden = columna.getHidden();
				String width = columna.getWidth();
				int position = columna.getPosition();
				char ordenado = columna.getOrdenado();
				gridColumnaPorUsuario.setHidden(hidden);
				gridColumnaPorUsuario.setWidth(width);
				gridColumnaPorUsuario.setPosition(position);
				gridColumnaPorUsuario.setOrdenado(ordenado);
				dao.guardar(gridColumnaPorUsuario);
				_log.debug("Se guardo la columna \"" + field + "\" con atributos [Width:" + width + ",Hidden:" + hidden + ",Position:" + position + ",Ordenado:" + ordenado + "] para el usuario con id=" + idUsuario);
			} else {
				_log.warn("No existe la columna " + field + " en la tabla GridColumna");
			}
		}
	}

	/*private int getIdFromField(String field, List<GridColumna> columnas) {
   for (GridColumna columna : columnas) {
   if (columna.getField().equals(field)) {
   return columna.getIdgridcolumna();
   }
   }
   return -1;
   }*/
	public ArchivopendienteService getSrvArchivoPendiente() {
		return srvArchivoPendiente;
	}

	public void setSrvArchivoPendiente(ArchivopendienteService srvArchivoPendiente) {
		this.srvArchivoPendiente = srvArchivoPendiente;
	}

	public CarpetabusquedaService getSrvCarpetaBusqueda() {
		return srvCarpetaBusqueda;
	}

	public void setSrvCarpetaBusqueda(CarpetabusquedaService srvCarpetaBusqueda) {
		this.srvCarpetaBusqueda = srvCarpetaBusqueda;
	}

	public DocumentoEnviadoService getSrvDocumentoEnviado() {
		return srvDocumentoEnviado;
	}

	public void setSrvDocumentoEnviado(DocumentoEnviadoService srvDocumentoEnviado) {
		this.srvDocumentoEnviado = srvDocumentoEnviado;
	}

	public ExpedienteService getSrvExpediente() {
		return srvExpediente;
	}

	public void setSrvExpediente(ExpedienteService srvExpediente) {
		this.srvExpediente = srvExpediente;
	}

	public MensajeriaService getSrvMensajeria() {
		return srvMensajeria;
	}

	public void setSrvMensajeria(MensajeriaService srvMensajeria) {
		this.srvMensajeria = srvMensajeria;
	}

	public NotificacionService getSrvNotificacion() {
		return srvNotificacion;
	}

	public void setSrvNotificacion(NotificacionService srvNotificacion) {
		this.srvNotificacion = srvNotificacion;
	}

	public ProcesoService getSrvProceso() {
		return srvProceso;
	}

	public void setSrvProceso(ProcesoService srvProceso) {
		this.srvProceso = srvProceso;
	}

	public ReemplazoService getSrvReemplazo() {
		return srvReemplazo;
	}

	public void setSrvReemplazo(ReemplazoService srvReemplazo) {
		this.srvReemplazo = srvReemplazo;
	}

	public ParametroDAO getParametroDAO() {
		return parametroDAO;
	}

	public void setParametroDAO(ParametroDAO parametroDAO) {
		this.parametroDAO = parametroDAO;
	}

	public PlantillaService getSrvPlantilla() {
		return srvPlantilla;
	}

	public void setSrvPlantilla(PlantillaService srvPlantilla) {
		this.srvPlantilla = srvPlantilla;
	}

	public ListaService getSrvLista() {
		return srvLista;
	}

	public void setSrvLista(ListaService srvLista) {
		this.srvLista = srvLista;
	}

	public void setGridXGridColumnaDao(GridXGridColumnaDAO gridXGridColumnaDao) {
		this.gridXGridColumnaDao = gridXGridColumnaDao;
	}

	public void setDocumentoService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	public ItemService getSrvItem() {
		return srvItem;
	}

	public void setSrvItem(ItemService srvItem) {
		this.srvItem = srvItem;
	}

	public CargoService getCargoService() {
		return cargoService;
	}

	public void setCargoService(CargoService cargoService) {
		this.cargoService = cargoService;
	}

	public DatosDAO getDatosDAO() {
		return datosDAO;
	}

	public void setDatosDAO(DatosDAO datosDAO) {
		this.datosDAO = datosDAO;
	}

	public SeguimientoXUsuarioService getSeguimientoXUsuarioService() {
		return seguimientoXUsuarioService;
	}

	public void setSeguimientoXUsuarioService(
			SeguimientoXUsuarioService seguimientoXUsuarioService) {
		this.seguimientoXUsuarioService = seguimientoXUsuarioService;
	}

	public TrazabilidadapoyoService getTrazabilidadApoyoService() {
		return trazabilidadApoyoService;
	}

	public void setTrazabilidadApoyoService(
			TrazabilidadapoyoService trazabilidadApoyoService) {
		this.trazabilidadApoyoService = trazabilidadApoyoService;
	}

	public ParametroService getSrvParametro() {
		return srvParametro;
	}

	public void setSrvParametro(ParametroService srvParametro) {
		this.srvParametro = srvParametro;
	}

	@Override //Recibidos
	public List getBandejaDocUsuarioFinalFiltro(Usuario objUsuario,
		 String enviados, BusquedaAvanzada objFiltro) {
		_log.debug("-> [Service] GridcolumnaxusuarioService - getBandejaDocUsuarioFinalFiltro():List<Item> ");
                if(objFiltro == null){
                   return documentoService.findDocumentosUsuarioFinal(objUsuario, enviados);
		}else{
		   return documentoService.findDocumentosUsuarioFinalFiltro(objUsuario, enviados,objFiltro);
		}
	}
        
     public DocumentoPendienteDAO getDocumentoPendienteDAO() {
        return documentoPendienteDAO;
    }

    public void setDocumentoPendienteDAO(DocumentoPendienteDAO documentoPendienteDAO) {
        this.documentoPendienteDAO = documentoPendienteDAO;
    }
    
     public DocumentoAtendidoDAO getDocumentoAtendidoDAO() {
        return documentoAtendidoDAO;
    }

    public void setDocumentoAtendidoDAO(DocumentoAtendidoDAO documentoAtendidoDAO) {
        this.documentoAtendidoDAO = documentoAtendidoDAO;
    }
    
    public DocumentoAnuladoDAO getDocumentoAnuladoDAO() {
        return documentoAnuladoDAO;
    }

    public void setDocumentoAnuladoDAO(DocumentoAnuladoDAO documentoAnuladoDAO) {
        this.documentoAnuladoDAO = documentoAnuladoDAO;
    }

}
