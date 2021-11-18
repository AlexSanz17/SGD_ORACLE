package org.ositran.services;

import java.util.List;

import org.ositran.dojo.tree.NodoArbol;
import org.ositran.utils.DocumentoDetail;
import org.ositran.utils.Expedienfindadvance;
import org.ositran.utils.ExpedienteList;
import org.ositran.utils.ExpedienteTree;

import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Expedientestor;
import com.btg.ositran.siged.domain.Usuario;

public interface ExpedienteService{
	
	public Expediente findByIdExpediente(Integer iIdExp);
	
	public Expediente findPropietarioByIdExpediente(Integer iIdExp);

	public Expediente prepareExpediente(DocumentoDetail objDD,Usuario objUsuario);


	public ExpedienteTree getExpedienteTree(Integer iIdExpediente);

	/*public Expediente findByExpedienteSAS(Integer iId);*/
	
	public List<NodoArbol> getDojoExpedienteTree(Integer iIdExpediente, Integer idDocumento, String valor);
        
        public List<NodoArbol> getDojoDocumentoPublicarTree(Integer idExpediente, Integer idDocumento);

	public ExpedienteTree getExpedienteTreeArchivar(Integer iIdExpediente);

	public List<Expediente> findAll();

	public List<Expediente> filtrarExpediente(Expediente objExpediente);

	public List<ExpedienteList> fillExpedienteList(List<Expediente> lstE) throws RuntimeException;

	public List<ExpedienteList> findByCriteria(String sNroIdentificacion,String sRazonSocial,String sNroExpediente,String sAsunto, Integer idusuario);

	public String getMaxReferencia();

   public String generateNroExpediente(Integer iIdExpediente);

	public List<Expediente> findbyNroExpediente(String NrDoc);

	public List<Expedienfindadvance> findbyadvancedinn(String Strcampo);

	public List<Expedienfindadvance> findbysuperadvanced(boolean alfresco,String numeroExpediente,String numeroDocumento,String numeroMesaPartes,String tipoDocumento,String concesionario,String cliente,String areaDestino,String propietario,String proceso,String contenido,String tipoBusqueda,String asuntoexpediente,String asuntodocumento,String estadoexpediente);

	public List<Expedienfindadvance> busquedaAvanzadaAdicional(boolean alfresco,String numeroExpediente,String tipoDocumento,String numeroDocumento,String identidadConcesionario,String numeroMesaPartes,String fechaInicioDocumento,String fechaFinDocumento,String concesionario,String fechaInicioExpediente,String fechaFinExpediente,String cliente,String direccionCliente,String areaDestino,String propietario,String proceso,String contenido,String tipoBusqueda,String asuntoexpediente,String asuntodocumento,String estadoexpediente);

	public void anularExpediente(Expediente objExpediente);

	public Expediente saveExpediente(Expediente objExpediente);

	public void registrarExpediente(Expediente objExpedienteOld,Expediente objExpedienteNew,String sUsuarioSesion,String sTipoAuditoria);

	public Expedientestor saveExpedienteStor(Expediente objE,DocumentoDetail objDD);

	public void aplicarNumeracionInternaExpediente(Integer iIdExp);

	//public void archivarExpediente(int idDocumento,int[] documentosDelExpediente,Usuario remitente, Integer idexpediente, String observacion, String tipoArchivar, String nombrePC);
	
	//public void archivarExpediente(Expediente expediente,Usuario remitente, String nombrePC);
	
	//public void archivarExpediente(int idExpediente,Usuario remitente, String nombrePC);

	// public void archivarExpediente(int idDocumento,int[]
	// documentosDelExpediente,Usuario remitente);
	public void actualizarResponsableExpediente(Integer idExpediente,Usuario idpropietario);
	
	public String puedeRechazar (Integer idExpediente) ;

   public Expediente buscarObjPor(String sNroExpediente);

   public List<Expediente> findLstBy(Integer iIdResponsable);

   public Expediente findObjectBy(String numeroExpediente, Character estado);

   //public void reabrirExpediente(Expediente expediente,Usuario remitente, String nombrePC);
}