/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Cliente;
import java.util.Map;

public interface ClienteDAO {

   public List<Cliente> findClientePersonas();
   public List<Cliente> findClienteFiltroPersonas(String desPersona); 
   
   public List<Cliente> findAllConcesionariaActive();
   public List<Cliente> findAllConcesionaria();
   
   public List<Cliente> findClienteInstituciones(Integer tipoInstitucion);
    
   public List<Cliente> findNombreCliente(Cliente cliente);
   
   public List<Cliente> findLstByCriteria(String[] filtroBusqueda);

   public Cliente findByCriteria(String strNroIdentificacion, Integer iIdTipoIdentificacion);

   public Cliente findByIdCliente(Integer iId);

   public Cliente findByNroIdentificacion(String strNI);

   public Cliente guardarObj(Cliente objCliente);

   public List<Cliente> findAll();

   public List<Cliente> findByTipoIdentificacionList(Integer iIdTipoIdentificacion, String sTipoIdentificacion);

   public String generateNroIdentificacionOtro();

   public Cliente findObjectBy(String numeroIdentificacion, Character estado);

   List<Cliente> findLikeNroIdentificacion(String identificacion);
   
   public Cliente findByTipoIdentificacionList2(Integer iIdTipoIdentificacion, String sTipoIdentificacion,String nroIdentificacion);

   public Cliente findByExpediente(Integer idExpediente);

   public List<Cliente> findLikeNroIdentificacionOrNombre(String prm);
   
   List<Cliente> findByNombreRazonLike(String like);
   
   public List<Cliente> findClienteFiltroInstituciones(String desInstituciones);
   
   public List<Cliente> findAllRUCPIDE();
   
   public List<Cliente> findClienteFiltroInstitucionesxTipo(String desInstituciones, Integer tipo);
   
}
