/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;
import java.util.Map;

import org.ositran.utils.DocumentoDetail;

import com.btg.ositran.siged.domain.Cliente;

public interface ClienteService{
	
        public List<Cliente> findClientePersonas();
         public List<Cliente> findClienteFiltroPersonas(String desPersona);
        public List<Cliente> findClienteInstituciones(Integer tipoInstitucion);
	public void deleteCliete(Cliente objCliente);
        public List<Cliente> findAllRUCPIDE();
	public Map<Integer,String> getClienteList() throws RuntimeException;

	public Cliente findByIdCliente(Integer iIdCliente);
        
        public List<Cliente> findNombreCliente(Cliente cliente);
        
        public List<Cliente> findAllConcesionaria(Integer iWithoutStor);

	public Cliente findByNroIdentificacion(String sNroIdentificacion);

	public Cliente guardarObj(Cliente objClienteOld,Cliente objClienteNew,String sUsuarioSesion,String sTipoAuditoria);

	public Cliente updateInfoCliente(DocumentoDetail objDD);

	public List<Cliente> findByTipoIdentificacionList(Integer iIdTipoIdentificacion, String sTipoIdentificacion);

	public List<Cliente> findAll();
	
	//public ClienteJSon getClientePorRUCSunat(String ruc);
	
	//public List<ClienteJSon> getClientesPorRazonSocialSunat(String razonSocial);
        
        public List<Cliente> findClienteFiltroInstituciones(String desInstituciones);
        
         public List<Cliente> findClienteFiltroInstitucionesxTipo(String desInstituciones, Integer tipo);

        public List<Cliente> findLstByCriteria(String sFiltroBusqueda);

        public String generateNroIdentificacionOtro();

        public Cliente findObjectBy(String numeroIdentificacion, Character estado);

        List<Cliente> findLikeNroIdentificacion(String sNroIdentificacion);

        public List<Cliente> findLikeNroIdentificacionOrNombre(String prm);

        public Cliente findByTipoIdentificacionList2(Integer iIdTipoIdentificacion, String sTipoIdentificacion,String nroIdentificacion);

        public Cliente findByExpediente(Integer idExpediente);

        List<Cliente> findByNombreRazonLike(String like);
}
