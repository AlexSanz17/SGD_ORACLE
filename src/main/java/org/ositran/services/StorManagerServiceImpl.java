package org.ositran.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.ositran.daos.AccionDAO;
import org.ositran.daos.DocumentoDAO;
import org.ositran.daos.DocumentostorDAO;
import org.ositran.daos.ExpedienteDAO;
import org.ositran.daos.ExpedientestorDAO;
import org.ositran.daos.ResolucionjaruDAO;
import org.ositran.daos.RolDAO;
import org.ositran.daos.SubmotivoDAO;
import org.ositran.daos.SuministroDAO;
import org.ositran.daos.TipodocumentoDAO;
import org.ositran.daos.TrazabilidaddocumentoDAO;
import org.ositran.daos.TrazabilidadexpedienteDAO;
import org.ositran.daos.UsuarioDAO;
import org.ositran.utils.ArchivoTemporal;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoStor;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Expedientestor;
import com.btg.ositran.siged.domain.Motivo;
import com.btg.ositran.siged.domain.Resolucionjaru;
import com.btg.ositran.siged.domain.Submotivo;
import com.btg.ositran.siged.domain.Suministro;
import com.btg.ositran.siged.domain.Usuario;

@Transactional
public class StorManagerServiceImpl implements StorManagerService {

   private static Logger log = Logger.getLogger(StorManagerServiceImpl.class);
   private SuministroDAO suministroDAO;
   private ExpedienteDAO expedienteDAO;
   private DocumentostorDAO documentostorDAO;
   private SubmotivoDAO submotivoDAO;
   private ArchivoService srvAchivo;
   private AccionDAO accionDAO;
   private DocumentoDAO documentoDAO;
   private TipodocumentoDAO tipodocumentoDAO;
   private TrazabilidaddocumentoDAO trazabilidaddocumentoDAO;
   private TrazabilidadexpedienteDAO trazabilidadexpedienteDAO;
   private UsuarioDAO usuarioDAO;
   private RolDAO rolDAO;
   private ResolucionjaruDAO resolucionjaruDAO;
   private ExpedientestorDAO expedientestorDAO;

   public Usuario findUserSCalificador(){      
      Usuario sCalificador = usuarioDAO.findByRolUnico(Constantes.ROL_USUARIO_SCALIFICADOR_STOR);
      return sCalificador;
   }

   public List<Suministro> getListSuministroXExpediente(Integer idExpediente) {
      return suministroDAO.findByExpediente(idExpediente);
   }

   public List<Submotivo> getListSubmotivosXExpediente(Integer idExpediente) {
      return documentostorDAO.findByIdExpediente(idExpediente).getSubmotivos();
   }

	@Transactional
	public void updateDatosExpediente(Integer idExpediente, Integer idSubMotivo, String monto) {
      //Expediente expediente = expedienteDAO.findByIdExpediente(idExpediente);
      DocumentoStor documentostor = documentostorDAO.findByIdExpediente(idExpediente);
      List<Submotivo> listSubMotivos = documentostor.getSubmotivos();
      //Suministro suministro
      Submotivo submotivo = submotivoDAO.findByIdSubmotivo(idSubMotivo);
      listSubMotivos.set(0, submotivo);
      documentostor.setSubmotivos(listSubMotivos);
      documentostor.setMonto(Double.valueOf(monto));

      documentostorDAO.updateDocumentoStor(documentostor);
   }

	@Transactional
	public void updateDatosExpediente(Integer idExpediente, Integer[] listIdSubMotivos, Integer[] listNewIdSubMotivos, String monto, String observacion){
      //log.info("CANTIDAD DE SUBMOTIVOS ACTUALES: "+listIdSubMotivos  != null ? listIdSubMotivos.length:0);
      log.info("CANTIDAD DE SUBMOTIVOS NUEVOS: "+listNewIdSubMotivos.length);
      DocumentoStor documentostor = documentostorDAO.findByIdExpediente(idExpediente);
      Expediente expediente = expedienteDAO.findByIdExpediente(idExpediente);
      List<Submotivo> listSubMotivos = documentostor.getSubmotivos();
      if(listIdSubMotivos != null){
         for(int i=0; i<listIdSubMotivos.length; i++){
            Submotivo submotivo = submotivoDAO.findByIdSubmotivo(listIdSubMotivos[i]);
            listSubMotivos.set(i, submotivo);
         }
      }

      if(listNewIdSubMotivos.length>1){
         log.info("ACTUALIZAR DATOS - Agregado Nuevos Submotivos");
         for(int j=0; j<(listNewIdSubMotivos.length-1); j++){
            Submotivo submotivo = submotivoDAO.findByIdSubmotivo(listNewIdSubMotivos[j]);
            //listSubMotivos.add(submotivo);
            if(!listSubMotivos.contains(submotivo)){
                listSubMotivos.add(submotivo);
            }else{
                log.info("Se Han Ingresado Motivo y SubMotivo Duplicados");
            }

         }
      }
      documentostor.setSubmotivos(listSubMotivos);
      if(monto.length()==0){
         monto="0";
         documentostor.setMonto(Double.valueOf(monto));
      }else{
         documentostor.setMonto(Double.valueOf(monto));
      }

      Expedientestor expedientestor = expediente.getExpedientestor();
      //expedientestor.setObservacion(observacion);
      expediente.setObservacion(observacion);
      expedientestorDAO.updateExpedienteStor(expedientestor);
      //expediente.setObservacion(observacion);


      //Guardar Trazabilidad Documento
      /*Trazabilidaddocumento trazabilidaddocumento = new Trazabilidaddocumento();
      trazabilidaddocumento.setDocumento(documentostor.getDocumento());
      trazabilidaddocumento.setRemitente(documentostor.getDocumento().getPropietario());
      trazabilidaddocumento.setDestinatario(documentostor.getDocumento().getPropietario());
      trazabilidaddocumento.setAsunto(documentostor.getDocumento().getAsunto());
      trazabilidaddocumento.setAccion(accionDAO.buscarObjPor("registrar"));
      trazabilidaddocumento.setIdproceso(expediente.getProceso());
      trazabilidaddocumento.setNroregistro(trazabilidaddocumentoDAO.findByMaxNroRegistro(documentostor.getDocumento().getIddocumento(), null, null).getNroregistro()+1);
      trazabilidaddocumento.setFechacreacion(new Date());
      trazabilidaddocumento.setObservacion(observacion);
      trazabilidaddocumentoDAO.saveTrazabilidadDocumento(trazabilidaddocumento);*/
      documentostorDAO.updateDocumentoStor(documentostor);

      //Guardar Trazabilidad Expediente
      /*Trazabilidadexpediente objMaxTraza = trazabilidadexpedienteDAO.findByMaxNroRegistro(expediente.getIdexpediente());

      Trazabilidadexpediente trazabilidadexpediente = new Trazabilidadexpediente();
      trazabilidadexpediente.setRemitente(documentostor.getDocumento().getPropietario());
      trazabilidadexpediente.setDestinatario(documentostor.getDocumento().getPropietario());
      trazabilidadexpediente.setAccion(accionDAO.buscarObjPor("registrar"));
      trazabilidadexpediente.setNroregistro((objMaxTraza == null) ? 1 : objMaxTraza.getNroregistro() + 1);
      trazabilidadexpediente.setExpediente(expediente);
      trazabilidadexpediente.setEtapa(expediente.getExpedientestor().getEtapa());
      trazabilidadexpediente.setIdproceso(expediente.getProceso());
      trazabilidadexpediente.setObservacion(observacion);
      trazabilidadexpediente.setFechacreacion(new Date());

      trazabilidadexpedienteDAO.saveTrazabilidadExpediente(trazabilidadexpediente);*/
      
   }

   public List<Motivo> getListMotivosxExpediente(Integer idExpediente) {
      List<Motivo> listaMotivos = new ArrayList<Motivo>();
      List<Submotivo> listSubMotivos = getListSubmotivosXExpediente(idExpediente);
      log.debug("NUMERO DE SUBMOTIVOS" + listSubMotivos.size());
      for (Iterator itsubm = listSubMotivos.iterator(); itsubm.hasNext();) {
         Submotivo submotivo = (Submotivo) itsubm.next();
         log.debug("DENTRO DE FOR - SUBMOTIVO: " + submotivo.getDescripcion());
         Motivo motivo = submotivo.getMotivo();
         if(!listaMotivos.contains(motivo))
            listaMotivos.add(motivo);
      }

      return listaMotivos;
   }

	@Transactional
	public Documento changeDocumentoPrincipal(Expediente expediente, Usuario destinatario, String asunto, String accion){
      //Crear Nuevo Documento Principal
      Documento documento = new Documento();
      documento.setAsunto(asunto);
      documento.setPropietario(destinatario);
      documento.setExpediente(expediente);
      documento.setTipoDocumento(tipodocumentoDAO.findByNombre("Otros"));
      documento.setPrincipal('S');
      documento.setAccion(accionDAO.buscarObjPor("aprobar"));
      documento.setFechaAccion(new Date());
      documento.setFechaCreacion(new Date());
      documento.setEstado('A');

      //Actualizar Documento Principal
      Documento docPrincipalActual = expedienteDAO.getDocumentoPrincipal(expediente.getIdexpediente());
      docPrincipalActual.setPrincipal('N');
      docPrincipalActual.setPropietario(destinatario);
      getDocumentoDAO().saveDocumento(docPrincipalActual);
      getDocumentoDAO().saveDocumento(documento);

      return documento;
   }

   public Documento createDocumento(Expediente expediente, Usuario destinatario, String asunto, String accion, char principal){
      Documento docPrincipal = new Documento();
      docPrincipal.setAsunto(asunto+" - "+expediente.getNroexpediente());
      docPrincipal.setPropietario(destinatario);
      docPrincipal.setExpediente(expediente);
      docPrincipal.setTipoDocumento(tipodocumentoDAO.findByNombre("Otros"));
      docPrincipal.setPrincipal(principal);
      docPrincipal.setAccion(accionDAO.buscarObjPor(accion));
      docPrincipal.setFechaAccion(new Date());
      docPrincipal.setFechaCreacion(new Date());
      docPrincipal.setEstado('A');

      return docPrincipal;
   }

    @Transactional
    public void updateEstadoDocumentoPrincipal(Documento documento, char estado){
      /*Documento docPrincipalActual = expedienteDAO.getDocumentoPrincipal(idExpediente);
      docPrincipalActual.setPrincipal('N');
      docPrincipalActual.setPropietario(nuevoDocumentoPrincipal.getPropietario());
      getDocumentoDAO().saveDocumento(docPrincipalActual);*/
      documento.setEstado(estado);
      getDocumentoDAO().updateDocumento(documento);
      log.info("ACTUALIZADO ESATDO DE DOCUMENTO PRINCIPAL");
   }

   public void uploadArchivoToAlfresco(Documento objDocumento, Map mapUpload) {
      Integer iContador = 1;
      List<ArchivoTemporal> lstArchivoTemporal = (List<ArchivoTemporal>) mapUpload.get("upload1");

      log.debug("Numero de documentos a crear [" + lstArchivoTemporal.size() + "]");

      for (ArchivoTemporal objArchivoTemporal : lstArchivoTemporal) {
         getSrvAchivo().uploadToAlfresco(objArchivoTemporal, objDocumento, iContador++);
      }
   }

   public DocumentoStor getDocumentoStorByExpediente(Integer idExpediente) {
      return documentostorDAO.findByIdExpediente(idExpediente);
   }

	@Transactional
	public void updateResolucionJaru(Resolucionjaru resolucionjaru){
		resolucionjaruDAO.saveOrUpdateResolucionJaru(resolucionjaru);
	}
   //Getters and Setters
   public SuministroDAO getSuministroDAO() {
      return suministroDAO;
   }

   public void setSuministroDAO(SuministroDAO suministroDAO) {
      this.suministroDAO = suministroDAO;
   }

   public ExpedienteDAO getExpedienteDAO() {
      return expedienteDAO;
   }

   public void setExpedienteDAO(ExpedienteDAO expedienteDAO) {
      this.expedienteDAO = expedienteDAO;
   }

   public DocumentostorDAO getDocumentostorDAO() {
      return documentostorDAO;
   }

   public void setDocumentostorDAO(DocumentostorDAO documentostorDAO) {
      this.documentostorDAO = documentostorDAO;
   }

   public SubmotivoDAO getSubmotivoDAO() {
      return submotivoDAO;
   }

   public void setSubmotivoDAO(SubmotivoDAO submotivoDAO) {
      this.submotivoDAO = submotivoDAO;
   }

   public AccionDAO getAccionDAO() {
      return accionDAO;
   }

   public void setAccionDAO(AccionDAO accionDAO) {
      this.accionDAO = accionDAO;
   }

   public DocumentoDAO getDocumentoDAO() {
      return documentoDAO;
   }

   public void setDocumentoDAO(DocumentoDAO documentoDAO) {
      this.documentoDAO = documentoDAO;
   }

   public TrazabilidadexpedienteDAO getTrazabilidadexpedienteDAO() {
      return trazabilidadexpedienteDAO;
   }

   public void setTrazabilidadexpedienteDAO(TrazabilidadexpedienteDAO trazabilidadexpedienteDAO) {
      this.trazabilidadexpedienteDAO = trazabilidadexpedienteDAO;
   }

   public ArchivoService getSrvAchivo() {
      return srvAchivo;
   }

   public void setSrvAchivo(ArchivoService srvAchivo) {
      this.srvAchivo = srvAchivo;
   }

   public TipodocumentoDAO getTipodocumentoDAO() {
      return tipodocumentoDAO;
   }

   public void setTipodocumentoDAO(TipodocumentoDAO tipodocumentoDAO) {
      this.tipodocumentoDAO = tipodocumentoDAO;
   }

   public TrazabilidaddocumentoDAO getTrazabilidaddocumentoDAO() {
      return trazabilidaddocumentoDAO;
   }

   public void setTrazabilidaddocumentoDAO(TrazabilidaddocumentoDAO trazabilidaddocumentoDAO) {
      this.trazabilidaddocumentoDAO = trazabilidaddocumentoDAO;
   }

   public UsuarioDAO getUsuarioDAO() {
      return usuarioDAO;
   }

   public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
      this.usuarioDAO = usuarioDAO;
   }

   public RolDAO getRolDAO() {
      return rolDAO;
   }

   public void setRolDAO(RolDAO rolDAO) {
      this.rolDAO = rolDAO;
   }

   public ResolucionjaruDAO getResolucionjaruDAO() {
      return resolucionjaruDAO;
   }

   public void setResolucionjaruDAO(ResolucionjaruDAO resolucionjaruDAO) {
      this.resolucionjaruDAO = resolucionjaruDAO;
   }

    public ExpedientestorDAO getExpedientestorDAO() {
        return expedientestorDAO;
    }

    public void setExpedientestorDAO(ExpedientestorDAO expedientestorDAO) {
        this.expedientestorDAO = expedientestorDAO;
    }





}
