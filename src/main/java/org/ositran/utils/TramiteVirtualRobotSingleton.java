/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.utils;
import java.io.File;
import org.apache.commons.codec.binary.Base64;
import com.btg.ositran.siged.domain.IotdtcDespacho;
import com.btg.ositran.siged.domain.IotdtmDocExterno;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Trazabilidadapoyo;
import com.ositran.pide.EndPointRUC;
import com.ositran.pide.WSPideTramite;
import com.ositran.ws.ConsultaTramite;
import com.ositran.ws.EntidadBean;
import com.ositran.ws.RespuestaConsultaTramite;
import gob.ositran.siged.config.SigedProperties;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.lang.xwork.time.DateFormatUtils;
import org.ositran.daos.ClienteDAO;
import org.ositran.daos.DocumentoExternoVirtualDAO;
import org.ositran.daos.ParametroDAO;
import org.ositran.daos.TrazabilidadapoyoDAO;
import org.ositran.daos.DespachoVirtualDAO;
import org.ositran.daos.RecepcionVirtualDAO;
import org.ositran.daos.TrazabilidaddocumentoDAO;
import org.ositran.daos.UnidadDAO;
import org.ositran.daos.UsuarioDAO;
import org.ositran.services.ArchivoService;
import org.ositran.services.DocumentoService;
import org.ositran.services.RepositorioService;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jbengoa (JUAN CARLOS BENGOA)
 */
public class TramiteVirtualRobotSingleton {
    private DocumentoExternoVirtualDAO documentoExternoVirtualDAO;
    private ParametroDAO parametroDAO;
    private DespachoVirtualDAO despachoVirtualDAO;
    private RecepcionVirtualDAO recepcionVirtualDAO;
    private ClienteDAO clienteDAO;
    private ArchivoService archivoService;
    private DocumentoService documentoService; 
    private RepositorioService repositorioService;
    private UsuarioDAO usuarioDAO; 
    private UnidadDAO unidadDAO;
    private TrazabilidaddocumentoDAO trazabilidaddocumentoDAO;
    private TrazabilidadapoyoDAO trazabilidadapoyoDAO;
    
    public RecepcionVirtualDAO getRecepcionVirtualDAO() {
        return recepcionVirtualDAO;
    }

    public void setRecepcionVirtualDAO(RecepcionVirtualDAO recepcionVirtualDAO) {
        this.recepcionVirtualDAO = recepcionVirtualDAO;
    }

    public TrazabilidadapoyoDAO getTrazabilidadapoyoDAO() {
        return trazabilidadapoyoDAO;
    }

    public void setTrazabilidadapoyoDAO(TrazabilidadapoyoDAO trazabilidadapoyoDAO) {
        this.trazabilidadapoyoDAO = trazabilidadapoyoDAO;
    }

    public TrazabilidaddocumentoDAO getTrazabilidaddocumentoDAO() {
        return trazabilidaddocumentoDAO;
    }

    public void setTrazabilidaddocumentoDAO(TrazabilidaddocumentoDAO trazabilidaddocumentoDAO) {
        this.trazabilidaddocumentoDAO = trazabilidaddocumentoDAO;
    }
    
    public DocumentoService getDocumentoService() {
        return documentoService;
    }

    public void setDocumentoService(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }
    
    public RepositorioService getRepositorioService() {
        return repositorioService;
    }

    public void setRepositorioService(RepositorioService repositorioService) {
        this.repositorioService = repositorioService;
    }

    public UnidadDAO getUnidadDAO() {
        return unidadDAO;
    }

    public void setUnidadDAO(UnidadDAO unidadDAO) {
        this.unidadDAO = unidadDAO;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    
    public ArchivoService getArchivoService() {
        return archivoService;
    }

    public void setArchivoService(ArchivoService archivoService) {
        this.archivoService = archivoService;
    }

    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public DespachoVirtualDAO getDespachoVirtualDAO() {
        return despachoVirtualDAO;
    }

    public void setDespachoVirtualDAO(DespachoVirtualDAO despachoVirtualDAO) {
        this.despachoVirtualDAO = despachoVirtualDAO;
    }

    public ParametroDAO getParametroDAO() {
        return parametroDAO;
    }

    public void setParametroDAO(ParametroDAO parametroDAO) {
        this.parametroDAO = parametroDAO;
    }

    public DocumentoExternoVirtualDAO getDocumentoExternoVirtualDAO() {
        return documentoExternoVirtualDAO;
    }

    public void setDocumentoExternoVirtualDAO(DocumentoExternoVirtualDAO documentoExternoVirtualDAO) {
        this.documentoExternoVirtualDAO = documentoExternoVirtualDAO;
    }
    
    @Transactional
    public  void procesarLecturaCargo() {
        
        try{
            Date fecha = new Date();
            WSPideTramite wsPideTramite = null;
            EndPointRUC endPointRUC = null;
            List<IotdtmDocExterno> list = documentoExternoVirtualDAO.buscarDocumentosEnviadosPendientesCargo();
            List<IotdtmDocExterno> listMigrar = documentoExternoVirtualDAO.buscarDocumentosEnviadosPendientesMigrarCargo();
          //  List<IotdtmDocExterno> listRecepcion = documentoExternoVirtualDAO.buscarDocumentosRecepcionPendientesSubsanacion();
            List<IotdtmDocExterno> listDespacho = documentoExternoVirtualDAO.buscarDocumentosDespachoPendientesSubsanacion();
            List<IotdtmDocExterno> listAtender = documentoExternoVirtualDAO.buscarDocumentosEnviadosPendientesAtencion();
            System.out.println("..............INICIANDO PROCESO ROBOT VIRTUAL.................=" + new Date());        
            System.out.println("....Pendientes Tamano.....................=" + list.size());
            System.out.println("....Migrar Tamano.........................=" + listMigrar.size());
            System.out.println("....Atender Tamano........................=" + listAtender.size());
            //System.out.println("....Recepcion Tamano.....................=" + listRecepcion.size());
            System.out.println("....Despacho Tamano.......................=" + listDespacho.size());
            
            if (list!=null && list.size()>0){
                 for(int i=0;i<list.size();i++){
                    try{ 
                        wsPideTramite = new WSPideTramite();
                        ConsultaTramite consultaTramite = new ConsultaTramite();
                        IotdtcDespacho iotdtcDespacho = null;
                        
                        consultaTramite.setVcuo(list.get(i).getSidemiext().getVcuo());
                        consultaTramite.setVrucentrec(list.get(i).getSidemiext().getVrucentrec());
                        consultaTramite.setVrucentrem(parametroDAO.findByTipoUnico("RUC_OSITRAN").getValor());
                        RespuestaConsultaTramite respuestaConsultaTramite =  wsPideTramite.consultarTramite(consultaTramite, Constantes.AMBIENTE_WS_PIDE_TRAMIE);
                       
                        if(respuestaConsultaTramite.getVcodres().equals("0000")) {
                            try{
                                if (list.get(i).getSidemiext().getCflgest()=='P'){
                                    if (!respuestaConsultaTramite.getCflgest().equals("R") && !respuestaConsultaTramite.getCflgest().equals("O")){
                                        iotdtcDespacho = list.get(i).getSidemiext();
                                        iotdtcDespacho.setDfecmod(new Date());
                                        iotdtcDespacho.setCflgest('E');
                                        despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                                    }
                                }
                                
                                if ((respuestaConsultaTramite.getCflgest().equals("R") || respuestaConsultaTramite.getCflgest().equals("O")) && respuestaConsultaTramite.getBcarstd()!=null){
                                    iotdtcDespacho = list.get(i).getSidemiext();
                                    iotdtcDespacho.setVnumregstdrec(respuestaConsultaTramite.getVnumregstd());
                                    iotdtcDespacho.setVanioregstdrec(respuestaConsultaTramite.getVanioregstd());
                                    iotdtcDespacho.setDfecregstdrec(respuestaConsultaTramite.getDfecregstd().toGregorianCalendar().getTime());
                                    iotdtcDespacho.setVuniorgstdrec(respuestaConsultaTramite.getVuniorgstd());
                                    iotdtcDespacho.setVusuregstdrec(respuestaConsultaTramite.getVusuregstd());
                                    byte[] decodedBytes = Base64.decodeBase64(respuestaConsultaTramite.getBcarstd());
                                    iotdtcDespacho.setBcarstdrec(decodedBytes);
                                    iotdtcDespacho.setCflgest(respuestaConsultaTramite.getCflgest().charAt(0));
                                    iotdtcDespacho.setVobs(respuestaConsultaTramite.getVobs());
                                    iotdtcDespacho.setDfecmod(new Date());
                                    despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                                }
                            }catch(Exception e){
                                System.out.println("Error al Procesar Robot CUO::" + list.get(i).getSidemiext().getVcuo());
                                e.printStackTrace();
                            }    
                        }
                    }catch(Exception e){
                        System.out.println("Error al Procesar Robot CUO::" + list.get(i).getSidemiext().getVcuo());
                        e.printStackTrace();
                    }           
                 }
            }
            
            /*if (listRecepcion!=null && listRecepcion.size()>0){
                IotdtcRecepcion iotdtcRecepcion = null;
                for(int i=0;i<listRecepcion.size();i++){
                    try{
                        iotdtcRecepcion = listRecepcion.get(i).getSidrecext();
                        iotdtcRecepcion.setCflgest('S');
                        iotdtcRecepcion.setDfecmod(new Date());
                        recepcionVirtualDAO.registrarDocumento(iotdtcRecepcion);
                    }catch(Exception e){
                        e.printStackTrace();
                    }    
                }
            }*/
            
            
            if (listDespacho!=null && listDespacho.size()>0){
                IotdtcDespacho iotdtcDespacho = null;
                for(int i=0;i<listDespacho.size();i++){
                    try{
                        iotdtcDespacho = despachoVirtualDAO.findByVcuo(listDespacho.get(i).getSidemiext().getVcuoref());
                        iotdtcDespacho.setCflgest('S');
                        iotdtcDespacho.setDfecmod(new Date());
                        despachoVirtualDAO.registrarDocumento(iotdtcDespacho);
                    }catch(Exception e){
                        System.out.println("Error al Procesar Robot de Subsanacion");
                        e.printStackTrace();
                    }
                }
            }
            
            if (listMigrar!=null && listMigrar.size()>0){
                Usuario usuario = usuarioDAO.findByUsuario(Constantes.USUARIO_MENSAJERIA_VIRTUAL);
                Unidad unidad = unidadDAO.findByIdunidad(usuario.getUnidad().getIdunidad());
                List<Archivo> archivosSubidos = new ArrayList<Archivo>();
                for(int i=0;i<listMigrar.size();i++){
                    try{
                         Documento d = documentoService.findByIdDocumento(listMigrar.get(i).getSidemiext().getIddocumento());
                         d = documentoService.findByIdDocumento(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia());
                         String sNuevoNombreCargo="["+d.getIdDocumento()+"_"+ DateFormatUtils.format(fecha,"yyyyMMddHHmmss")+"_"+"1"+"]"+d.getID_CODIGO() + "_CARGO_VIRTUAL_" + d.getTipoDocumento().getNombre() + "." + "pdf";
                         String rutaDig=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_TEMPORAL_ALFRESCO);

                         OutputStream out = new FileOutputStream(rutaDig + sNuevoNombreCargo); 
                         out.write(listMigrar.get(i).getSidemiext().getBcarstdrec()); 
                         out.close();

                         File f=new File(rutaDig,sNuevoNombreCargo);
                         
                         Archivo objArchivo = new Archivo();
                         objArchivo.setDocumento(d);
                         objArchivo.setNombre(sNuevoNombreCargo);
                         objArchivo.setPrincipal('M');
                         objArchivo.setEstadoDigitalizacion(Constantes.ARCHIVO_ESTADO_DIGITALIZACION_YES);
                         objArchivo.setFechaCreacion(new Date());
                         objArchivo.setRutaArchivoPdf(rutaDig + sNuevoNombreCargo);
                         objArchivo.setRutaAlfresco(repositorioService.obtenerRutaDocumento(d, unidad.getRutaSite(), d.getTipoDocumento().getCodigo())+d.getID_CODIGO() + "_CARGO_VIRTUAL_" + d.getTipoDocumento().getNombre() + "." + "pdf");
                         objArchivo.setAutor(usuario);
                         objArchivo.setUnidadAutor(usuario.getUnidad().getIdunidad());
                         objArchivo.setUsuariocreacion(usuario.getIdusuario());
                         objArchivo.setUsuariomodificacion(usuario.getIdusuario());
                         objArchivo.setClave(null);
                         objArchivo.setEstado(Constantes.ESTADO_ACTIVO);
                         
                         try{
                            objArchivo.setTamano(Integer.valueOf((int)f.length()));
                         }catch(Exception e){
                            objArchivo.setTamano(null);
                         }
                         
                         objArchivo = archivoService.saveArchivo(objArchivo);
                         archivosSubidos.add(objArchivo);   
                         repositorioService.subirArchivosTransformadosARepositorio(d, archivosSubidos, false, usuario, unidad.getRutaSite(), d.getTipoDocumento().getCodigo());
                    }catch(Exception e){
                        System.out.println("Error al Procesar Robot de Migracion de Cargo");
                        e.printStackTrace();
                    }
                }
            }
            
            if (listAtender!=null && listAtender.size()>0){
                Usuario usuario = usuarioDAO.findByUsuario(Constantes.USUARIO_MENSAJERIA_VIRTUAL);
                usuario.setIdUsuarioPerfil(usuario.getIdusuario());
                usuario.setIdUnidadPerfil(usuario.getUnidad().getIdunidad());
                usuario.setIdFuncionPerfil(usuario.getIdfuncion());
                
                for(int i=0;i<listAtender.size();i++){
                    try{
                        Documento d = documentoService.findByIdDocumento(listAtender.get(i).getSidemiext().getIddocumento());
                        if (d.getDocumentoreferencia()==null){
                           Trazabilidaddocumento t =trazabilidaddocumentoDAO.findByMaxNroRegistro(d.getIdDocumento(), null, null);
                           if (!t.getAccion().getIdAccion().toString().trim().equals("31")){
                             documentoService.atenderDocumento(usuario, d, null, true, null, null, null, null, null);
                           }
                        }else{
                           Trazabilidadapoyo t =trazabilidadapoyoDAO.buscarUltimaDelegacion(d.getIdDocumento());
                           if (!t.getAccion().getIdAccion().toString().trim().equals("31")){
                             documentoService.atenderDocumento(usuario, d, null, true, null, null, null, null, null);
                           }
                        }
                    }catch(Exception e){
                        System.out.println("Error al Procesar Robot de Atender");
                        e.printStackTrace();
                    }    
                }
            }
            
            Calendar calendario = new GregorianCalendar();
            int hora =calendario.get(Calendar.HOUR_OF_DAY);
            
            if (hora == 8){
             endPointRUC = new EndPointRUC();
              try{
                    //JC-RUC
                    List<EntidadBean> lst = endPointRUC.getListaEntidad(1, Constantes.AMBIENTE_WS_PIDE_RUC);
                    //JC-RUC
                    for(int i=0;i<lst.size();i++){
                        try{
                              Cliente cliente = clienteDAO.findByNroIdentificacion(lst.get(i).getVrucent());
                              if (cliente!=null){
                                cliente.setFlagPide("1");
                                clienteDAO.guardarObj(cliente);
                              }  
                        }catch(Exception e){
                            System.out.println("Error al Procesar Robot Calendario RUC::" + lst.get(i).getVrucent());
                            e.printStackTrace();
                        }      
                    }
               }catch(Exception e){
                   e.printStackTrace();
               }      
            }
            
        }catch(Exception e){
           e.printStackTrace();
        }    
    }
    
}
