/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.FilaHojaRuta;
import com.btg.ositran.siged.domain.Trazabilidadapoyo;
import com.btg.ositran.siged.domain.Trazabilidadcopia;
import com.btg.ositran.siged.domain.DocumentoReferencia;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Trazabilidadexpediente;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import org.ositran.utils.DateUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.ositran.daos.UnidadDAO;
import org.ositran.pojos.ArchivoVersionado;
import org.ositran.services.ArchivoService;
import org.ositran.services.DocumentoService;
import org.ositran.services.EtapaService;
import org.ositran.services.ProcesoService;
import org.ositran.services.ReporteAPNService;
import org.ositran.services.RepositorioService;
import org.ositran.services.TrazabilidadapoyoService;
import org.ositran.services.DocumentoReferenciaService;
import org.ositran.services.ParametroService;
import org.ositran.services.TrazabilidadcopiaService;
import org.ositran.services.TrazabilidaddocumentoService;
import org.ositran.services.TrazabilidadexpedienteService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;
import org.ositran.utils.SigedUtils;
import org.ositran.utils.StringUtil;

public class TrazabilidadAction {

    private static Logger log = Logger.getLogger(TrazabilidadAction.class);

    private Date fechaLimite;

    private Character tipoOrigen;
    private UnidadDAO unidadDAO;
    private ParametroService parametroService;
    private Long apoyosPendientes;

    @SuppressWarnings("unused")
    private boolean noHayAlfresco;
    private boolean mostrarDetalle;
    private String sinDetalle;
    private String opcionMenu;

    public String getSinDetalle() {
        return sinDetalle;
    }

    public void setSinDetalle(String sinDetalle) {
        this.sinDetalle = sinDetalle;
    }
    private boolean mostrarEtapa;
    private boolean mostrarActividad;
    private boolean mostrarAdjuntos;
    private boolean mostrarToolbar;
    private boolean puedeReferenciar;
    
    private Integer iIdDocumento;
    private Integer iIdDocumentoReferencia;

    private Integer iIdExp;
    private Integer idTrazabilidad;

    private String fechaEnTexto;
    private String creador;
    private String idOrigen;
    private String mode;
    private String origenDetalleDoc = null;
    private String indexTreeExp;
    private Integer father;

   // @SuppressWarnings("unused")
    //private Trazabilidaddocumento objTrazabilidadDocumento;
    private Usuario usuarioLogeado;
    private Documento objDocumento;
    private Documento objDocumentoReferencia;
    private DocumentoReferenciaService documentoReferenciaService;
    private List<Trazabilidaddocumento> lstTrazabilidadDocumento;
    private List<Trazabilidadexpediente> lstTrazabilidadExpediente;
    private List<Trazabilidadcopia> lstTrazabilidadCopia;
    private List<Trazabilidadapoyo> lstTrazabilidadapoyo;
    private List<ArchivoVersionado> versionamiento;
    private List<FilaHojaRuta> trazabilidadUnificada;


    private DocumentoService documentoService;
    private TrazabilidaddocumentoService trazabilidadDocumentoService;
    private TrazabilidadexpedienteService srvTrazabilidadEx;
    private ArchivoService archivoService;
    private UsuarioService usuarioService;
    private RepositorioService repositorioService;
    private ProcesoService procesoService;
    private EtapaService etapaService;
    private TrazabilidadcopiaService trazabilidadcopiaService;
    private TrazabilidadapoyoService trazabilidadapoyoService;
    private ReporteAPNService reporteAPNService;
    private String tab;

    public TrazabilidadAction(TrazabilidaddocumentoService srvTrazabilidad, ArchivoService archivoService, UsuarioService usuarioService, RepositorioService repositorioService, TrazabilidadexpedienteService srvTrazabilidadEx) {
        setTrazabilidadDocumentoService(srvTrazabilidad);
        this.setArchivoService(archivoService);
        this.setUsuarioService(usuarioService);
        this.setRepositorioService(repositorioService);
        this.setSrvTrazabilidadEx(srvTrazabilidadEx);
    }




    /**Llamado cuando se abre el popup de "Ver Trazabilidad"
     * Carga los datos a ser mostrados en la ventana "Trazabilidad Documento"
     */
    
	public String viewSeguimiento() throws Exception {
    	log.debug("-> [Action] TrazabilidadAction - viewSeguimiento():String ");
    
        if (getIIdDocumento() == null) {
            log.error("No se recibio ningun ID Documento");
            return Action.ERROR;
        }

        try{
             Map<String, Object> mapSession = ActionContext.getContext().getSession();
             Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
             if (usuario==null)
        	 return Action.ERROR;

        }catch (Exception e){
            e.printStackTrace();
        }

        if ("documento".equalsIgnoreCase(mode)) {
            objDocumento = documentoService.findByIdDocumento(iIdDocumento);
            lstTrazabilidadDocumento = trazabilidadDocumentoService.findByIdDocumento(iIdDocumento);

            return Action.SUCCESS;
        }
        String action = "";
        if("imprimir".equalsIgnoreCase(mode)){
        	action = "print";
        }else{
        	action = Action.SUCCESS;
        }
        
        apoyosPendientes = 0l;
        objDocumento = documentoService.findByIdDocumento(getIIdDocumento());

        Integer idDocumento = objDocumento.getDocumentoreferencia() != null ? objDocumento.getDocumentoreferencia() : objDocumento.getIdDocumento();
        trazabilidadUnificada = reporteAPNService.generarHojaRuta(idDocumento);
        Trazabilidaddocumento ultimo = null;
        if(trazabilidadUnificada != null && !trazabilidadUnificada.isEmpty()){
        	trazabilidadUnificada.get(0).setDestinatario("-");
        	for(int i = trazabilidadUnificada.size()-1; i>=0; i--){
            	if(trazabilidadUnificada.get(i).getPk().getTipo().equalsIgnoreCase("transferencia")){
            		ultimo = trazabilidadDocumentoService.findTrabilidadbyId(trazabilidadUnificada.get(i).getPk().getId());
            		break;
            	}
            }
        }

        if(objDocumento.getDocumentoreferencia() == null){
        	if (ultimo!=null)
        	   fechaLimite = ultimo.getFechalimiteatencion();
        }else{
        	Trazabilidadapoyo ta = trazabilidadapoyoService.buscarUltimaDelegacionUsuario(objDocumento);
        	fechaLimite = ta.getFechalimiteatencion();
        }
        
        Date f = fechaLimite;

        if (f != null) {
            fechaEnTexto = DateUtil.getDia(f) + " " + DateUtil.getDiadeMes(f) + " de " + DateUtil.getMes(f) + " del " + DateUtil.getAnio(f) + " a las " + DateUtil.getHora(f) + " horas.";
        }

        return action;
      
    } 
        
    private boolean verificarPermiso(Trazabilidaddocumento t, Usuario objUsuario){
            try{
                  Usuario usuarioFinal = usuarioService.findByIdUsuario(objUsuario.getIdUsuarioPerfil());
                  if (trazabilidadDocumentoService.contarListTotalTrazabilidadesDocumento(t)==0){
                        boolean encontrado = false;
                        List<Unidad> lstAreasAcceso = new ArrayList();
                        List<Parametro> lstUnidad = parametroService.findByTipo(Constantes.AREAS_ACCESO_GENERAL);
                 
                        for(int i=0;i<lstUnidad.size();i++){
                            lstAreasAcceso.add(new Unidad(Integer.valueOf(lstUnidad.get(i).getValor())));
                            if (lstUnidad.get(i).getValor().toString().equals(objUsuario.getIdUnidadPerfil().toString()))
                               encontrado = true;
                        }

                        if (encontrado){
                            return true;
                        }else{
                            Unidad uf = unidadDAO.findByIdunidad(objUsuario.getIdUnidadPerfil().intValue());
                            List<Unidad> lstJefatura  = new ArrayList();
                            List<Unidad> lstJefaturas = new ArrayList();
                            List<Unidad> lstTodos = new ArrayList();

                            if (objUsuario.getIdFuncionPerfil().toString().equals("3")){
                                 lstTodos = unidadDAO.findByGrupoUnidad(uf.getIdunidad());
                                 if (lstTodos == null) lstTodos = new ArrayList();
                                 lstTodos.add(new Unidad(uf.getIdunidad()));

                                 if (trazabilidadDocumentoService.contarListTotalTrazabilidadesUnidad(t, lstTodos)==0 && trazabilidadDocumentoService.contarListTotalTrazabilidadesUnidad(t, lstAreasAcceso)>0){
                                     return false;
                                 }else{
                                     return true;
                                 }
                            }else{
                                   if (uf.getUnidadgrupo()!=null){
                                        if (uf.getNiveles()!=null && uf.getNiveles().equals("2")){
                                            lstJefatura.add(new Unidad(objUsuario.getIdUnidadPerfil()));
                                            lstJefaturas = unidadDAO.findByGrupoUnidad(objUsuario.getIdUnidadPerfil());
                                            lstTodos =     unidadDAO.findByGrupoUnidad(objUsuario.getIdUnidadPerfil());
                                            lstTodos.add(new Unidad(objUsuario.getIdUnidadPerfil()));  
                                        }else{
                                            lstJefatura.add(new Unidad(objUsuario.getIdUnidadPerfil()));
                                            lstJefaturas = unidadDAO.findByGrupoUnidad(uf.getUnidadgrupo());
                                            lstTodos =     unidadDAO.findByGrupoUnidad(uf.getUnidadgrupo());
                                            lstTodos.add(new Unidad(uf.getUnidadgrupo()));
                                        }    
                                   }else{
                                        lstJefatura.add(new Unidad(objUsuario.getIdUnidadPerfil())); 
                                        lstJefaturas.add(new Unidad(objUsuario.getIdUnidadPerfil()));
                                        lstTodos.add(new Unidad(objUsuario.getIdUnidadPerfil()));
                                    }

                                    if (usuarioFinal.getFlagviewtrazabilidad()!=null){
                                        if (usuarioFinal.getFlagviewtrazabilidad().equals("0")){
                                            if (objUsuario.getIdFuncionPerfil().toString().equals("2") || objUsuario.getIdFuncionPerfil().toString().equals("4")){
                                                if (trazabilidadDocumentoService.contarListTotalTrazabilidadesUnidad(t, lstJefatura)==0){
                                                    return false;
                                                }else{
                                                    return true;
                                                }
                                            }else{
                                                 return false;
                                            }
                                        }

                                        if (usuarioFinal.getFlagviewtrazabilidad().equals("2")){
                                            if (trazabilidadDocumentoService.contarListTotalTrazabilidadesUnidad(t, lstJefatura)==0){
                                                return false;
                                            }else{
                                                return true;
                                            }
                                        }

                                        if (usuarioFinal.getFlagviewtrazabilidad().equals("3")){
                                            if (trazabilidadDocumentoService.contarListTotalTrazabilidadesUnidad(t, lstJefaturas)==0){
                                                 return false;
                                            }else{
                                                 return true;
                                            }
                                        }

                                        if (usuarioFinal.getFlagviewtrazabilidad().equals("4")){
                                            if (trazabilidadDocumentoService.contarListTotalTrazabilidadesUnidad(t, lstTodos)==0){
                                                return false;
                                            }else{
                                                return true;
                                            }       
                                        }
                                    }else{
                                        return false;
                                    }
                            } 
                        }
                  }else{
                     return true; 
                  }
            }catch(Exception e){
                e.printStackTrace();
            }
            
            return false;
        }    

    @SuppressWarnings("unused")
    public String viewTrazabilidadReferencia() {
        log.debug("-> [Action] TrazabilidadAction - viewTrazabilidadReferencia():String ");
        Documento doc = documentoService.findByIdDocumento(getiIdDocumentoReferencia());
       
        //Integer idExpediente = getIIdExp();
        Map<String, Object> session = ActionContext.getContext().getSession();
        Usuario usuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
        //String tabBusqueda = (String) session.get(Constantes.TAB_BUSQUEDA);
        usuarioLogeado = usuario;
        Integer counttraza = 0;
        setObjDocumentoReferencia(doc);
        //TODO
        mostrarAdjuntos = true;
        //mostrarToolbar = true;

        try{
        
            Trazabilidaddocumento t = new Trazabilidaddocumento();
            Documento d = new Documento();
            Usuario u = new Usuario();
            Usuario propietario = new Usuario();
            Expediente exp = new Expediente();
            u.setIdusuario(usuario.getIdUsuarioPerfil());
            //u.setIdusuario(usuario.getIdusuario());
            propietario.setIdusuario(doc.getPropietario().getIdusuario());
            d.setIdDocumento(getiIdDocumentoReferencia());
            d.setDocumentoreferencia(doc.getDocumentoreferencia());
            d.setPropietario(propietario);
            exp.setId(doc.getExpediente().getId());
            d.setExpediente(exp);
            t.setDocumento(d);
            t.setRemitente(u);
            t.setDestinatario(u);
            
            Usuario usuarioFinal = usuarioService.findByIdUsuario(usuario.getIdUsuarioPerfil());
            if (opcionMenu!=null && opcionMenu.equals("B")){
               boolean conpermiso = false;
               if (usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("1")){
                    conpermiso = true;   
               }else{
                    conpermiso = verificarPermiso(t, usuario);
                    if (!conpermiso)
                        return "sinpermiso"; 
               }
             }else{
                   if (getFather()!=null && getFather()!=0){
                        DocumentoReferencia r = documentoReferenciaService.getReferenciaDocumento(getFather(), getiIdDocumentoReferencia());
                        if (r==null || r.getVerDocumento()==null || r.getVerDocumento().equals("I")){ 
                           return "sinpermiso";
                        }  
                   } 
            }
            
           /* Usuario usuarioFinal = usuarioService.findByIdUsuario(usuario.getIdUsuarioPerfil()); 

            boolean conpermiso = true;
            if (usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("1")){
                  conpermiso = true;   
            }else{
                  if (tabBusqueda!=null && tabBusqueda.equals("B")){
                      conpermiso = verificarPermiso(t, usuario);
                      if (!conpermiso)
                         return "sinpermiso"; 
                  }else{
                      if (getFather()!=null && getFather()!=0){
                           DocumentoReferencia r = documentoReferenciaService.getReferenciaDocumento(getFather(), getiIdDocumentoReferencia());
                           if (r==null || r.getVerDocumento()==null || r.getVerDocumento().equals("I")){ 
                              return "sinpermiso";
                           }  
                       } 
                  }      
            }*/
            
            
            Documento documento = documentoService.findByIdDocumento(getiIdDocumentoReferencia());
            if(documento.getConfidencial().equals(Constantes.Si)){
                List<Integer> permitidos = documentoService.getUsuariosPermitidos(documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : documento.getIdDocumento());
                if(!permitidos.contains(new BigDecimal(usuario.getIdUsuarioPerfil()))){
                    return "sinpermiso";
                }
            }

            setLstTrazabilidadDocumento(trazabilidadDocumentoService.findByIdDocumento(getiIdDocumentoReferencia()));
            List<Trazabilidaddocumento> lstTraza = new ArrayList<Trazabilidaddocumento>();

            for (Trazabilidaddocumento actual : lstTrazabilidadDocumento) {

                actual.setEstado(SigedUtils.getDescripcionEstado(actual.getDocumento().getEstado()));
                lstTraza.add(actual);

                /**Adicionamos las trazabilidades de las copias -------------------------------------------------------------------*/
                List<Trazabilidadcopia> copias = trazabilidadcopiaService.buscarPorOrigen(actual.getIdtrazabilidaddocumento(), Constantes.TIPO_ORIGEN_TRAZADOCUMENTO);

                if(copias != null && !copias.isEmpty()){
                    for(Trazabilidadcopia copia : copias){
                            Trazabilidaddocumento temp = new Trazabilidaddocumento();
                            temp.setFechacreacion(copia.getFechacreacion());
                            temp.setRemitente(copia.getRemitente());
                            temp.setDestinatario(copia.getDestinatario());
                            temp.setAccion(copia.getAccion());
                            temp.setConCopias(true);
                            temp.setEstado(actual.getEstado());
                            lstTraza.add(temp);
                    }
                }


                List<Trazabilidadapoyo> apoyos = trazabilidadapoyoService.buscarPorOrigen(actual.getIdtrazabilidaddocumento());

                if(apoyos != null && !apoyos.isEmpty()){
                    for(Trazabilidadapoyo apoyo : apoyos){
                            Trazabilidaddocumento temp = new Trazabilidaddocumento();
                            temp.setFechacreacion(apoyo.getFechacreacion());
                            temp.setRemitente(apoyo.getRemitente());
                            temp.setDestinatario(apoyo.getDestinatario());
                            temp.setAccion(apoyo.getAccion());
                            temp.setConApoyo(true);
                            temp.setEstado(apoyo.getEstado().getDescripcion());
                            lstTraza.add(temp);
                    }
                }
            }
            setLstTrazabilidadDocumento(lstTraza);
            int tam = lstTrazabilidadDocumento.size();
            if (tam > 0) {
                creador = (doc.getAutor() != null ? doc.getAutor().getNombreCompleto() : null);
                counttraza =0 ;
                List<Trazabilidaddocumento> traz = trazabilidadDocumentoService.findByMaxtrazabyIddocumento(getiIdDocumentoReferencia());
                if (traz != null) {
                    counttraza = traz.size();
                }

                if (creador == null && counttraza <= 1) {
                    creador = usuarioLogeado.getNombres() + (StringUtil.isEmpty(usuarioLogeado.getApellidos()) ? "" : " " + usuarioLogeado.getApellidos());
                } else if (creador == null || creador.trim().equals("")) {
                    creador = Constantes.MESA_PARTES;
                }
            }

            setVersionamiento(archivoService.getVersions(doc));
            Integer idDocumentoBandeja = (Integer) session.get(Constantes.SESSION_IDDOCUMENTO);

            if (log.isDebugEnabled()) {
                log.debug("Obteniendo de sesion idDocumento [" + idDocumentoBandeja + "]");
            }

            if (tam > 0) {
                    lstTrazabilidadDocumento.get(0).setDestinatario(null);
            }

            if(doc.getConfidencial().equals(Constantes.Si)){
                List<Integer> permitidos = documentoService.getUsuariosPermitidos(doc.getIdDocumento());
                if(!permitidos.contains(new BigDecimal(usuario.getIdusuario()))){
                  mostrarAdjuntos = false;
                  //mostrarToolbar = false;
                }
            }

        }catch(Exception e){
             e.printStackTrace();
        }
        
        return Action.SUCCESS;
    }

    /**
     * Obtiene los detalles de la trazabilidad para un documento en Documentos
     * Adicionales. Optimizado por German...
     *
     * @author German Enriquez
     */
    /**REN: Carga toda la pagina adicional del detalle de documentos ----------------------------------------------------------*/
    @SuppressWarnings("unused")
    public String viewTrazabilidad() {
        log.debug("-> [Action] TrazabilidadAction - viewTrazabilidad():String ");
        Documento doc = documentoService.findByIdDocumento(getIIdDocumento());
        
        Map<String, Object> session = ActionContext.getContext().getSession();
        Usuario usuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
        usuarioLogeado = usuario;
        Integer counttraza = 0;
        setObjDocumento(doc);
        //TODO
        mostrarAdjuntos = true;
        mostrarToolbar = true;

        try{
        
                Trazabilidaddocumento t = new Trazabilidaddocumento();
                Documento d = new Documento();
                Usuario u = new Usuario();
                Usuario propietario = new Usuario();
                Expediente exp = new Expediente();
                u.setIdusuario(usuario.getIdUsuarioPerfil());
                propietario.setIdusuario(doc.getPropietario().getIdusuario());
                d.setIdDocumento(getIIdDocumento());
                d.setDocumentoreferencia(doc.getDocumentoreferencia());
                d.setPropietario(propietario);
                exp.setId(doc.getExpediente().getId());
                d.setExpediente(exp);
                t.setDocumento(d);
                t.setRemitente(u);
                t.setDestinatario(u);
               
                Documento documento = documentoService.findByIdDocumento(getIIdDocumento());
                if(documento.getConfidencial().equals(Constantes.Si)){
                    List<Integer> permitidos = documentoService.getUsuariosPermitidos(documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : documento.getIdDocumento());
                    if(!permitidos.contains(new BigDecimal(usuario.getIdUsuarioPerfil()))){
                        return "sinpermiso";
                    }
                }
                
                Usuario usuarioFinal = usuarioService.findByIdUsuario(usuario.getIdUsuarioPerfil());
                if (opcionMenu!=null && opcionMenu.equals("B")){
                    boolean conpermiso = false;
                    if (usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("1")){
                          conpermiso = true;   
                    }else{
                          conpermiso = verificarPermiso(t, usuario);
                          if (!conpermiso)
                             return "sinpermiso"; 
                    }
                }

                setLstTrazabilidadDocumento(trazabilidadDocumentoService.findByIdDocumento(getIIdDocumento()));
                List<Trazabilidaddocumento> lstTraza = new ArrayList<Trazabilidaddocumento>();

                for (Trazabilidaddocumento actual : lstTrazabilidadDocumento) {
                    actual.setEstado(SigedUtils.getDescripcionEstado(actual.getDocumento().getEstado()));
                    lstTraza.add(actual);

                    /**Adicionamos las trazabilidades de las copias -------------------------------------------------------------------*/
                    List<Trazabilidadcopia> copias = trazabilidadcopiaService.buscarPorOrigen(actual.getIdtrazabilidaddocumento(), Constantes.TIPO_ORIGEN_TRAZADOCUMENTO);

                    if(copias != null && !copias.isEmpty()){
                        for(Trazabilidadcopia copia : copias){
                                Trazabilidaddocumento temp = new Trazabilidaddocumento();
                                temp.setFechacreacion(copia.getFechacreacion());
                                temp.setRemitente(copia.getRemitente());
                                temp.setDestinatario(copia.getDestinatario());
                                temp.setAccion(copia.getAccion());
                                temp.setConCopias(true);
                                temp.setEstado(actual.getEstado());
                                lstTraza.add(temp);
                        }
                    }

                    List<Trazabilidadapoyo> apoyos = trazabilidadapoyoService.buscarPorOrigen(actual.getIdtrazabilidaddocumento());

                    if(apoyos != null && !apoyos.isEmpty()){
                        for(Trazabilidadapoyo apoyo : apoyos){
                                Trazabilidaddocumento temp = new Trazabilidaddocumento();
                                temp.setFechacreacion(apoyo.getFechacreacion());
                                temp.setRemitente(apoyo.getRemitente());
                                temp.setDestinatario(apoyo.getDestinatario());
                                temp.setAccion(apoyo.getAccion());
                                temp.setConApoyo(true);
                                temp.setEstado(apoyo.getEstado().getDescripcion());
                                lstTraza.add(temp);
                        }
                    }
                }
                setLstTrazabilidadDocumento(lstTraza);
                int tam = lstTrazabilidadDocumento.size();
                if (tam > 0) {
                    creador = (doc.getAutor() != null ? doc.getAutor().getNombreCompleto() : null);
                    counttraza =0 ;
                    List<Trazabilidaddocumento> traz = trazabilidadDocumentoService.findByMaxtrazabyIddocumento(getIIdDocumento());
                    if (traz != null) {
                        counttraza = traz.size();
                    }

                    if (creador == null && counttraza <= 1) {
                        creador = usuarioLogeado.getNombres() + (StringUtil.isEmpty(usuarioLogeado.getApellidos()) ? "" : " " + usuarioLogeado.getApellidos());
                    } else if (creador == null || creador.trim().equals("")) {
                        creador = Constantes.MESA_PARTES;
                    }
                }

                setVersionamiento(archivoService.getVersions(doc));
                puedeReferenciar = (true && (this.origenDetalleDoc == null || !this.origenDetalleDoc.equals("busquedaAvanzada")));

                Integer idDocumentoBandeja = (Integer) session.get(Constantes.SESSION_IDDOCUMENTO);

                if (log.isDebugEnabled()) {
                    log.debug("Obteniendo de sesion idDocumento [" + idDocumentoBandeja + "]");
                }

                mostrarDetalle = false;
               
                if (idDocumentoBandeja != null) {
                    Documento documentoBandeja = documentoService.findByIdDocumento(idDocumentoBandeja);
                    mostrarDetalle = doc.getExpediente().equals(documentoBandeja.getExpediente());
                }

                if (log.isDebugEnabled()) {
                    log.debug("Mostrar Detalle [" + mostrarDetalle + "]");
                }

                if (tam > 0) {
                        lstTrazabilidadDocumento.get(0).setDestinatario(null);
                }

                if(doc.getConfidencial().equals(Constantes.Si)){
                        List<Integer> permitidos = documentoService.getUsuariosPermitidos(doc.getIdDocumento());
                        if(!permitidos.contains(new BigDecimal(usuario.getIdusuario()))){
                                mostrarDetalle = false;
                                mostrarAdjuntos = false;
                                mostrarToolbar = false;
                    }
                }


                if (sinDetalle!=null && sinDetalle.equals("S")){      
                   mostrarDetalle = false; 
                } 
                
                if (tab!=null && tab.equals("LEGAJO"))
                    mostrarDetalle = false;  
                
                
          }catch(Exception e){
             e.printStackTrace();
          }
      
         return Action.SUCCESS;
    }

    /**REN Cuando se hace click en el boton "Copias" en la ventana de trazabilidad --------------------------------------------*/
    public String detalleCopias() {
    	log.debug("-> [Action] TrazabilidadAction - detalleCopias():String ");
        //log.debug("DOCUMENTO [" + objDocumento.getIdDocumento() + "]");

        lstTrazabilidadCopia = trazabilidadcopiaService.buscarPorOrigen(idTrazabilidad, tipoOrigen);
        if (tipoOrigen.equals(Constantes.TIPO_ORIGEN_TRAZADOCUMENTO)) {
            idOrigen = trazabilidadDocumentoService.findTrabilidadbyId(idTrazabilidad).getDocumento().getExpediente().getNroexpediente();
        } else {
            idOrigen = Integer.toString(trazabilidadcopiaService.buscarPorId(idTrazabilidad).getIdorigen().getIdtrazabilidaddocumento());
        }
        for (Trazabilidadcopia actual : lstTrazabilidadCopia) {
            if (trazabilidadcopiaService.numeroCopias(actual.getIdtrazabilidadcopia(), Constantes.TIPO_ORIGEN_TRAZACOPIA) > 0) {
                actual.setConCopias(true);
            } else {
                actual.setConCopias(false);
            }
        }
        return Action.SUCCESS;
    }

    /**REN Cuando se hace click en el boton "Copias de Apoyo" en la ventana de trazabilidad -----------------------------------*/
    public String detalleApoyos(){
    	log.debug("-> [Action] TrazabilidadAction - detalleApoyos():String ");
    	lstTrazabilidadapoyo = trazabilidadapoyoService.buscarPorOrigen(idTrazabilidad);
    	idOrigen = trazabilidadDocumentoService.findTrabilidadbyId(idTrazabilidad).getDocumento().getExpediente().getNroexpediente();
    	return Action.SUCCESS;
    }

    public String mostrarTrazabilidadExpediente(){
    	log.debug("-> [Action] TrazabilidadAction - mostrarTrazabilidadExpediente():String ");
    	String action = "trazabilidadExpediente";

    	if(mode != null && "imprimir".equalsIgnoreCase(mode)){
           	action = "print";
        }

    	objDocumento = documentoService.findByIdDocumento(iIdDocumento);

    	List<Trazabilidaddocumento> traza = new ArrayList<Trazabilidaddocumento>();
    	lstTrazabilidadDocumento = trazabilidadDocumentoService.buscarTrazaCompleta(objDocumento.getExpediente().getId());
    	//boolean primero = true;

    	for (Trazabilidaddocumento actual : lstTrazabilidadDocumento) {

            actual.setEstado(SigedUtils.getDescripcionEstado(actual.getDocumento().getEstado()));
            traza.add(actual);
            /**Adicionamos las trazabilidades de las copias -------------------------------------------------------------------*/
            List<Trazabilidadcopia> copias = trazabilidadcopiaService.buscarPorOrigen(actual.getIdtrazabilidaddocumento(), Constantes.TIPO_ORIGEN_TRAZADOCUMENTO);
            copias.addAll(trazabilidadcopiaService.buscarPorOrigen(actual.getIdtrazabilidaddocumento(), Constantes.TIPO_ORIGEN_TRAZACOPIA));
            if(copias != null && !copias.isEmpty()){
            	for(Trazabilidadcopia copia : copias){
            		Trazabilidaddocumento temp = new Trazabilidaddocumento();
            		Documento doc = new Documento();
            		doc.setNumeroDocumento(actual.getDocumento().getNumeroDocumento());
                	temp.setFechacreacion(copia.getFechacreacion());
            		temp.setRemitente(copia.getRemitente());
            		temp.setDestinatario(copia.getDestinatario());
            		temp.setAccion(copia.getAccion());
            		temp.setConCopias(true);
            		temp.setDocumento(doc);
            		temp.setEstado(actual.getEstado());
            		traza.add(temp);
            	}
            }

            List<Trazabilidadapoyo> apoyos = trazabilidadapoyoService.buscarPorOrigen(actual.getIdtrazabilidaddocumento());

            if(apoyos != null && !apoyos.isEmpty()){
            	for(Trazabilidadapoyo apoyo : apoyos){
            		Trazabilidaddocumento temp = new Trazabilidaddocumento();
            		Documento doc = new Documento();
            		doc.setNumeroDocumento(actual.getDocumento().getNumeroDocumento());
                	temp.setFechacreacion(apoyo.getFechacreacion());
            		temp.setRemitente(apoyo.getRemitente());
            		temp.setDestinatario(apoyo.getDestinatario());
            		temp.setAccion(apoyo.getAccion());
            		temp.setConApoyo(true);
            		temp.setEstado(apoyo.getEstado().getDescripcion());
            		temp.setDocumento(doc);
            		traza.add(temp);
            	}
            }

            if (!StringUtil.isEmpty(actual.getActividad())) {
                mostrarActividad = true;
            }
        }

    	lstTrazabilidadDocumento = traza;

    	return action;
	}

    // ////////////////////////////////
    // Getters and Setters //
    // ////////////////////////////////
    public Documento getObjDocumento() {
        return objDocumento;
    }

    public void setObjDocumento(Documento objDocumento) {
        this.objDocumento = objDocumento;
    }

    public void setDocumentoService(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    public Integer getIIdDocumento() {
        return iIdDocumento;
    }

    public void setIIdDocumento(Integer iIdDocumento) {
        this.iIdDocumento = iIdDocumento;
    }

    public List<Trazabilidaddocumento> getLstTrazabilidadDocumento() {
        return lstTrazabilidadDocumento;
    }

    public void setLstTrazabilidadDocumento(List<Trazabilidaddocumento> lstTrazabilidadDocumento) {
        this.lstTrazabilidadDocumento = lstTrazabilidadDocumento;
    }

    public void setTrazabilidadDocumentoService(TrazabilidaddocumentoService trazabilidadDocumentoService) {
        this.trazabilidadDocumentoService = trazabilidadDocumentoService;
    }

    public RepositorioService getRepositorioService() {
        return repositorioService;
    }

    public void setRepositorioService(RepositorioService repositorioService) {
        this.repositorioService = repositorioService;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public ArchivoService getArchivoService() {
        return archivoService;
    }

    public void setArchivoService(ArchivoService archivoService) {
        this.archivoService = archivoService;
    }

    public List<ArchivoVersionado> getVersionamiento() {
        return versionamiento;
    }

    public void setVersionamiento(List<ArchivoVersionado> versionamiento) {
        this.versionamiento = versionamiento;
    }

    public void setLstTrazabilidadExpediente(List<Trazabilidadexpediente> lstTrazabilidadExpediente) {
        this.lstTrazabilidadExpediente = lstTrazabilidadExpediente;
    }

    public List<Trazabilidadexpediente> getLstTrazabilidadExpediente() {
        return lstTrazabilidadExpediente;
    }

    public void setSrvTrazabilidadEx(TrazabilidadexpedienteService srvTrazabilidadEx) {
        this.srvTrazabilidadEx = srvTrazabilidadEx;
    }

    public TrazabilidadexpedienteService getSrvTrazabilidadEx() {
        return srvTrazabilidadEx;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getFechaEnTexto() {
        return fechaEnTexto;
    }

    public void setFechaEnTexto(String fechaEnTexto) {
        this.fechaEnTexto = fechaEnTexto;
    }

    public boolean isPuedeReferenciar() {
        return puedeReferenciar;
    }

    public void setPuedeReferenciar(boolean puedeReferenciar) {
        this.puedeReferenciar = puedeReferenciar;
    }

    public boolean isMostrarDetalle() {
        return mostrarDetalle;
    }

    public void setMostrarDetalle(boolean mostrarDetalle) {
        this.mostrarDetalle = mostrarDetalle;
    }

    public Integer getIIdExp() {
        return iIdExp;
    }

    public void setIIdExp(Integer idExp) {
        iIdExp = idExp;
    }

    public boolean isMostrarEtapa() {
        return mostrarEtapa;
    }

    public void setMostrarEtapa(boolean mostrarEtapa) {
        this.mostrarEtapa = mostrarEtapa;
    }

    public ProcesoService getProcesoService() {
        return procesoService;
    }

    public void setProcesoService(ProcesoService procesoService) {
        this.procesoService = procesoService;
    }

    public EtapaService getEtapaService() {
        return etapaService;
    }

    public void setEtapaService(EtapaService etapaService) {
        this.etapaService = etapaService;
    }

    public TrazabilidadcopiaService getTrazabilidadcopiaService() {
        return trazabilidadcopiaService;
    }

    public void setTrazabilidadcopiaService(
          TrazabilidadcopiaService trazabilidadcopiaService) {
        this.trazabilidadcopiaService = trazabilidadcopiaService;
    }

    public Integer getIdTrazabilidad() {
        return idTrazabilidad;
    }

    public void setIdTrazabilidad(Integer idTrazabilidad) {
        this.idTrazabilidad = idTrazabilidad;
    }

    public Character getTipoOrigen() {
        return tipoOrigen;
    }

    public void setTipoOrigen(Character tipoOrigen) {
        this.tipoOrigen = tipoOrigen;
    }

    public List<Trazabilidadcopia> getLstTrazabilidadCopia() {
        return lstTrazabilidadCopia;
    }

    public void setLstTrazabilidadCopia(List<Trazabilidadcopia> lstTrazabilidadCopia) {
        this.lstTrazabilidadCopia = lstTrazabilidadCopia;
    }

    public String getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(String idOrigen) {
        this.idOrigen = idOrigen;
    }

    public boolean isMostrarActividad() {
        return mostrarActividad;
    }

    public void setMostrarActividad(boolean mostrarActividad) {
        this.mostrarActividad = mostrarActividad;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getOrigenDetalleDoc() {
        return origenDetalleDoc;
    }

    public void setOrigenDetalleDoc(String origenDetalleDoc) {
        this.origenDetalleDoc = origenDetalleDoc;
    }

    public Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(Usuario usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }

	public TrazabilidadapoyoService getTrazabilidadapoyoService() {
		return trazabilidadapoyoService;
	}

	public void setTrazabilidadapoyoService(
			TrazabilidadapoyoService trazabilidadapoyoService) {
		this.trazabilidadapoyoService = trazabilidadapoyoService;
	}

	public List<Trazabilidadapoyo> getLstTrazabilidadapoyo() {
		return lstTrazabilidadapoyo;
	}

	public void setLstTrazabilidadapoyo(List<Trazabilidadapoyo> lstTrazabilidadapoyo) {
		this.lstTrazabilidadapoyo = lstTrazabilidadapoyo;
	}

	public Long getApoyosPendientes() {
		return apoyosPendientes;
	}

	public void setApoyosPendientes(Long apoyosPendientes) {
		this.apoyosPendientes = apoyosPendientes;
	}

	public String getIndexTreeExp() {
		return indexTreeExp;
	}

	public void setIndexTreeExp(String indexTreeExp) {
		this.indexTreeExp = indexTreeExp;
	}

	public List<FilaHojaRuta> getTrazabilidadUnificada() {
		return trazabilidadUnificada;
	}

	public void setTrazabilidadUnificada(List<FilaHojaRuta> trazabilidadUnificada) {
		this.trazabilidadUnificada = trazabilidadUnificada;
	}

	public ReporteAPNService getReporteAPNService() {
		return reporteAPNService;
	}

	public void setReporteAPNService(ReporteAPNService reporteAPNService) {
		this.reporteAPNService = reporteAPNService;
	}

	public boolean isMostrarAdjuntos() {
		return mostrarAdjuntos;
	}

	public void setMostrarAdjuntos(boolean mostrarAdjuntos) {
		this.mostrarAdjuntos = mostrarAdjuntos;
	}

	public boolean isMostrarToolbar() {
		return mostrarToolbar;
	}

	public void setMostrarToolbar(boolean mostrarToolbar) {
		this.mostrarToolbar = mostrarToolbar;
	}
        
        public Integer getFather() {
            return father;
        }

        public void setFather(Integer father) {
            this.father = father;
        }
        
        public DocumentoReferenciaService getDocumentoReferenciaService() {
            return documentoReferenciaService;
        }

        public void setDocumentoReferenciaService(DocumentoReferenciaService documentoReferenciaService) {
            this.documentoReferenciaService = documentoReferenciaService;
        }
        
        public Integer getiIdDocumentoReferencia() {
            return iIdDocumentoReferencia;
        }

        public void setiIdDocumentoReferencia(Integer iIdDocumentoReferencia) {
            this.iIdDocumentoReferencia = iIdDocumentoReferencia;
        }
        
         public Documento getObjDocumentoReferencia() {
            return objDocumentoReferencia;
        }

        public void setObjDocumentoReferencia(Documento objDocumentoReferencia) {
            this.objDocumentoReferencia = objDocumentoReferencia;
        }
        
        public UnidadDAO getUnidadDAO() {
            return unidadDAO;
        }

        public void setUnidadDAO(UnidadDAO unidadDAO) {
            this.unidadDAO = unidadDAO;
        }
        
        public ParametroService getParametroService() {
            return parametroService;
        }

        public void setParametroService(ParametroService parametroService) {
            this.parametroService = parametroService;
        }
        
        public String getOpcionMenu() {
            return opcionMenu;
        }

        public void setOpcionMenu(String opcionMenu) {
            this.opcionMenu = opcionMenu;
        }
        
        public String getTab() {
            return tab;
        }

        public void setTab(String tab) {
            this.tab = tab;
        }
}
