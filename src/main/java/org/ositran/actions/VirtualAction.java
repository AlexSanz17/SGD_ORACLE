/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.IotdtmDocExterno;
import com.btg.ositran.siged.domain.Tipodocumento;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Archivo;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.ositran.pide.WSPideTramite;
import com.ositran.ws.ConsultaTramite;
import com.ositran.ws.RespuestaConsultaTramite;
import java.util.ArrayList;
import org.ositran.daos.DocumentoExternoVirtualDAO;
import org.ositran.daos.TipodocumentoDAO;
import org.ositran.services.ClienteService;
import org.ositran.utils.DocumentoDetail;
import java.util.List;
import java.util.Map;
import org.ositran.daos.DespachoVirtualDAO;
import org.ositran.daos.DocPrincipalVirtualDAO;
import org.ositran.daos.DocumentoDAO;
import org.ositran.daos.ParametroDAO;
import org.ositran.daos.RecepcionVirtualDAO;
import org.ositran.services.ArchivoService;
import org.ositran.services.DocumentoService;
import org.ositran.utils.Constantes;
/**
 *
 * @author Juan Carlos Bengoa
 */
public class VirtualAction {
    private DocumentoExternoVirtualDAO documentoExternoVirtualDAO;
    private RecepcionVirtualDAO recepcionVirtualDAO;
    private DespachoVirtualDAO despachoVirtualDAO;
    private DocumentoDetail objDD;
    private ParametroDAO parametroDao;
    private Integer idRecepcion;
    private Integer idDespacho;
    private ClienteService clienteService;
    private TipodocumentoDAO tipoDocumentoDAO;
    private Documento documento;
    private Integer iIdDoc;
    private DocumentoService documentoService;
    private Map<String, Object> mapSession;
    private ArchivoService archivoService;
    private DocumentoDAO documentoDAO;
    private DocPrincipalVirtualDAO docPrincipalVirtualDAO;
    
    public ParametroDAO getParametroDao() {
        return parametroDao;
    }

    public void setParametroDao(ParametroDAO parametroDao) {
        this.parametroDao = parametroDao;
    }

    public DocPrincipalVirtualDAO getDocPrincipalVirtualDAO() {
        return docPrincipalVirtualDAO;
    }

    public void setDocPrincipalVirtualDAO(DocPrincipalVirtualDAO docPrincipalVirtualDAO) {
        this.docPrincipalVirtualDAO = docPrincipalVirtualDAO;
    }
    
    public Integer getIdDespacho() {
        return idDespacho;
    }

    public void setIdDespacho(Integer idDespacho) {
        this.idDespacho = idDespacho;
    }
    
    public RecepcionVirtualDAO getRecepcionVirtualDAO() {
        return recepcionVirtualDAO;
    }

    public void setRecepcionVirtualDAO(RecepcionVirtualDAO recepcionVirtualDAO) {
        this.recepcionVirtualDAO = recepcionVirtualDAO;
    }

    public DocumentoDAO getDocumentoDAO() {
        return documentoDAO;
    }

    public void setDocumentoDAO(DocumentoDAO documentoDAO) {
        this.documentoDAO = documentoDAO;
    }

    public ArchivoService getArchivoService() {
        return archivoService;
    }

    public void setArchivoService(ArchivoService archivoService) {
        this.archivoService = archivoService;
    }

    public Map<String, Object> getMapSession() {
        return mapSession;
    }

    public void setMapSession(Map<String, Object> mapSession) {
        this.mapSession = mapSession;
    }

    public DocumentoService getDocumentoService() {
        return documentoService;
    }

    public void setDocumentoService(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    public Integer getiIdDoc() {
        return iIdDoc;
    }

    public void setiIdDoc(Integer iIdDoc) {
        this.iIdDoc = iIdDoc;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public VirtualAction() {
	super();
    }
    
    public DocumentoExternoVirtualDAO getDocumentoExternoVirtualDAO() {
        return documentoExternoVirtualDAO;
    }

    public void setDocumentoExternoVirtualDAO(DocumentoExternoVirtualDAO documentoExternoVirtualDAO) {
        this.documentoExternoVirtualDAO = documentoExternoVirtualDAO;
    }
     
    public TipodocumentoDAO getTipoDocumentoDAO() {
        return tipoDocumentoDAO;
    }

    public void setTipoDocumentoDAO(TipodocumentoDAO tipoDocumentoDAO) {
        this.tipoDocumentoDAO = tipoDocumentoDAO;
    }
   
    public ClienteService getClienteService() {
        return clienteService;
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public Integer getIdRecepcion() {
        return idRecepcion;
    }

    public void setIdRecepcion(Integer idRecepcion) {
        this.idRecepcion = idRecepcion;
    }
    
    public DespachoVirtualDAO getDespachoVirtualDAO() {
        return despachoVirtualDAO;
    }

    public void setDespachoVirtualDAO(DespachoVirtualDAO despachoVirtualDAO) {
        this.despachoVirtualDAO = despachoVirtualDAO;
    }
    
    @SuppressWarnings("unused")
    public String viewDocDespachoVirtual() {
        IotdtmDocExterno despacho = documentoExternoVirtualDAO.buscarDocumentoVirtual(idDespacho);
        Documento d = documentoDAO.findByIdDocumento(despacho.getSidemiext().getIddocumento());
      
        Cliente cliente =clienteService.findObjectBy(despacho.getSidemiext().getVrucentrec(), 'A');
        Tipodocumento tipoDocumento = tipoDocumentoDAO.findByIdTipoDocumentoPIDE(despacho.getCcodtipdoc());
        mapSession = ActionContext.getContext().getSession();
        Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
        objDD = new DocumentoDetail();
        objDD.setStrAsunto(despacho.getVasu());
        objDD.setArchivoCargo("");
        
        if (despacho.getSidemiext().getBcarstdrec()!=null){
            objDD.setArchivoCargo(d.getID_CODIGO() + "_CARGO_VIRTUAL_" + d.getTipoDocumento().getNombre() + ".pdf");  
        }
        
        objDD.setStrRazonSocial(cliente==null?"":cliente.getRazonSocial());
        objDD.setStrNroDocumento(tipoDocumento.getNombre() + " - " + despacho.getVnumdoc());
        objDD.setStrFecha(despacho.getSidemiext().getDfecreg());
        objDD.setIdCodigo(Integer.valueOf(despacho.getSiddocext().intValue()));
        objDD.setArchivoPrincipal(docPrincipalVirtualDAO.buscarPrincipaByDocExterno(idDespacho).getVnomdoc());
        objDD.setArchivoAnexo(despacho.getVurldocanx()==null?"":despacho.getVurldocanx().trim());
        objDD.setCantAnexos(despacho.getSnumanx()==null? "0" : despacho.getSnumanx().toString());
        objDD.setNroTramite(despacho.getSidemiext().getVnumregstd());
        objDD.setCEstado(despacho.getSidemiext().getCflgest());
        objDD.setCuo(despacho.getSidemiext().getVcuo());
        objDD.setIIdDocumento(despacho.getSidemiext().getIddocumento());
        objDD.setiIdDocumentoReferencia(d.getDocumentoreferencia());
        
        if (despacho.getIotdtdAnexoList()!=null && despacho.getIotdtdAnexoList().size()>0){
            List<String> list = new ArrayList<String>();
            for (int i=0;i<despacho.getIotdtdAnexoList().size();i++){
                list.add(despacho.getIotdtdAnexoList().get(i).getVnomdoc());
            }
            
            objDD.setListAnexos(list);
        }
        
        if (despacho.getSidemiext().getCflgest() == 'P'){
           if (despacho.getSidemiext().getCflgenv() == 'E'){
              try{ 
                    WSPideTramite wsPideTramite = new WSPideTramite();
                    ConsultaTramite consultaTramite = new ConsultaTramite();
                    consultaTramite.setVcuo(despacho.getSidemiext().getVcuo());
                    consultaTramite.setVrucentrec(despacho.getSidemiext().getVrucentrec());
                    consultaTramite.setVrucentrem(parametroDao.findByTipoUnico("RUC_OSITRAN").getValor());
                    RespuestaConsultaTramite respuestaConsultaTramite =  wsPideTramite.consultarTramite(consultaTramite, Constantes.AMBIENTE_WS_PIDE_TRAMIE);
                    
                    if(respuestaConsultaTramite.getVcodres().equals("0000")){
                       objDD.setFlagCodigoVirtual('3');
                    }else{
                          if(respuestaConsultaTramite.getVcodres().equals("0001")){
                               objDD.setFlagCodigoVirtual('1'); 
                          }else{
                               objDD.setFlagCodigoVirtual('4');
                          }
                    }
                    
              }catch(Exception e){
                 objDD.setFlagCodigoVirtual('5'); 
              }      
           }else{  
              objDD.setFlagCodigoVirtual('1');
           }  
        }  
        
        if (despacho.getSidemiext().getCflgest() == 'E')
          objDD.setFlagCodigoVirtual('0');
        if (despacho.getSidemiext().getCflgest() == 'R')
          objDD.setFlagCodigoVirtual('0');
        if (despacho.getSidemiext().getCflgest() == 'S')
          objDD.setFlagCodigoVirtual('0');
        if (despacho.getSidemiext().getCflgest() == 'O'){
            //JC-RUC ANALIZAR ES IMPORTANTE  
            List<String> lst = documentoExternoVirtualDAO.buscarTramiteVirtual(d.getID_CODIGO().toString());
            /////
            if (lst!=null && lst.size()>0 && lst.get(0)!=null && !lst.get(0).equals("") && lst.get(0).equals(idDespacho.toString())){
               despacho = documentoExternoVirtualDAO.buscarDocumentoVirtual(new Integer(lst.get(0))); 
               d = documentoDAO.findByIdDocumento(despacho.getSidemiext().getIddocumento());

               if (d.getPropietario().getIdusuario().toString().equals(usuario.getIdUsuarioPerfil().toString()) && d.getUnidadpropietario().toString().equals(usuario.getIdUnidadPerfil().toString()) && d.getFlagMultiple()==null)
                 objDD.setFlagCodigoVirtual('2');
               else
                 objDD.setFlagCodigoVirtual('0');
            }else{
               objDD.setFlagCodigoVirtual('0');
            }   
        }
        
        return Action.SUCCESS;
    }
    
     
    @SuppressWarnings("unused")
    public String viewDocRecepcionVirtual() {
        List<Archivo> lst = null;
        Documento d = null;
        IotdtmDocExterno recepcion = documentoExternoVirtualDAO.buscarDocumentoVirtual(idRecepcion);
        
        if (recepcion.getSidrecext().getIddocumento()!=null){
            d = documentoDAO.findByIdDocumento(recepcion.getSidrecext().getIddocumento());
            lst = archivoService.findLstByIdDocumento(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia());
        }
        
        objDD = new DocumentoDetail();
        objDD.setStrAsunto(recepcion.getVasu());
        
        try{
            objDD.setStrRazonSocial(recepcion.getVnomentemi());
        }catch(Exception e){
            e.printStackTrace();
            objDD.setStrRazonSocial("");
        }
        
        try{
            Tipodocumento tipoDocumento = tipoDocumentoDAO.findByIdTipoDocumentoPIDE(recepcion.getCcodtipdoc());
            objDD.setStrNroDocumento(tipoDocumento.getNombre() + " - " + recepcion.getVnumdoc());
        }catch(Exception e){
            e.printStackTrace();
            objDD.setStrNroDocumento("");
        }
        
        objDD.setStrFecha(recepcion.getSidrecext().getDfecreg());
        objDD.setIdCodigo(Integer.valueOf(recepcion.getSiddocext().intValue()));
        objDD.setArchivoPrincipal(docPrincipalVirtualDAO.buscarPrincipaByDocExterno(idRecepcion).getVnomdoc());
        objDD.setArchivoAnexo(recepcion.getVurldocanx()==null?"":recepcion.getVurldocanx().trim());
        objDD.setCantAnexos(recepcion.getSnumanx()==null? "0" :recepcion.getSnumanx().toString());
        objDD.setNroTramite(recepcion.getSidrecext().getVnumregstd());
        objDD.setCEstado(recepcion.getSidrecext().getCflgest());
        objDD.setCuo(recepcion.getSidrecext().getVcuo());
        objDD.setRuc(recepcion.getSidrecext().getVrucentrem());
        objDD.setFlagCodigoVirtual('1');
       
        try{
             objDD.setTamanoPrincipal(objDD.getTamanoFormateado(String.valueOf(recepcion.getIotdtdDocPrincipalList().get(0).getBpdfdoc().length)));
        }catch(Exception e){
             objDD.setTamanoPrincipal("");
        }
        
        try{
             objDD.setTamanoCargo(objDD.getTamanoFormateado(String.valueOf(recepcion.getSidrecext().getBcarstd().length)));
        }catch(Exception e){
             objDD.setTamanoCargo("");
        }
        
        if (lst!=null && lst.size()>0){
            for(int i=0;i<lst.size();i++){
                if (lst.get(i).getPrincipal() == 'M'){
                    if (recepcion.getSidrecext().getBcarstd()!=null){
                        objDD.setArchivoCargo(lst.get(i).getNombreReal());
                        break;
                    }
                }
            }
        }
        
        if (recepcion.getSidrecext().getIddocumento()==null){
            objDD.setFlagCodigoVirtual('0');
        }else{
            if (objDD.getNroTramite()==null || objDD.getNroTramite().trim().equals("")){
                objDD.setFlagCodigoVirtual('1');
            }else{
                if (recepcion.getSidrecext().getCflgenvcar()=='S'){
                  objDD.setFlagCodigoVirtual('2');
                }else{
                  objDD.setFlagCodigoVirtual('3');
                }  
            }
        }
        
        if (recepcion.getIotdtdAnexoList()!=null && recepcion.getIotdtdAnexoList().size()>0){
            List<String> list = new ArrayList<String>();
            for (int i=0;i<recepcion.getIotdtdAnexoList().size();i++){
                list.add(recepcion.getIotdtdAnexoList().get(i).getVnomdoc());
            }
            
            objDD.setListAnexos(list);
        }
        
        return Action.SUCCESS;
    }
    
    
    
    public DocumentoDetail getObjDD() {
        return objDD;
    }

    public void setObjDD(DocumentoDetail objDD) {
        this.objDD = objDD;
    }
}
