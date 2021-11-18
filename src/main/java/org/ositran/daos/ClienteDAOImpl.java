/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import com.btg.ositran.siged.domain.Cliente;
import org.ositran.utils.Constantes;

@Repository
public class ClienteDAOImpl implements ClienteDAO {

    private EntityManager em;
    // ////////////////////////////////
    // Methods //
    // ////////////////////////////////
    private static Logger log = Logger.getLogger(ClienteDAOImpl.class);

    @Override
    public List<Cliente> findLstByCriteria(String[] filtroBusqueda) {
        boolean bMoreThanOne = false;
        String sMainOperator = " AND ";
        String sOperator = " OR ";
        StringBuilder sbQuery = new StringBuilder("SELECT NEW Cliente(c.idCliente, ti.nombre, c.numeroIdentificacion, c.razonSocial, c.nombres, c.apellidoPaterno, c.apellidoMaterno) ");

        sbQuery.append("FROM Cliente c LEFT JOIN c.tipoIdentificacion ti ");
        sbQuery.append("WHERE ");

        for (int i = 0; i < filtroBusqueda.length; i++) {
            if (bMoreThanOne) {
                sbQuery.append(sMainOperator);
            }

            sbQuery.append("(LOWER(c.razonSocial) LIKE ?").append(i + 1).append(sOperator);
            sbQuery.append("LOWER(c.nombres) LIKE ?").append(i + 1).append(sOperator);
            sbQuery.append("LOWER(c.apellidoPaterno) LIKE ?").append(i + 1).append(sOperator);
            sbQuery.append("LOWER(c.apellidoMaterno) LIKE ?").append(i + 1).append(")");
            bMoreThanOne = true;
        }

        sbQuery.append(" AND c.estado = 'A' ORDER BY c.idCliente");

        if (log.isDebugEnabled()) {
            log.debug("QUERY [" + sbQuery.toString() + "]");
        }

        Query qQuery = em.createQuery(sbQuery.toString());

        for (int i = 0; i < filtroBusqueda.length; i++) {
            StringBuilder sbParameter = new StringBuilder("%").append(filtroBusqueda[i].toLowerCase()).append("%");

            qQuery.setParameter(i + 1, sbParameter.toString());
        }

        return qQuery.getResultList();
    }
    
     public List<Cliente> findClienteInstituciones(Integer tipoInstitucion){
         return em.createNamedQuery("Cliente.findClienteInstituciones").setParameter("codTipoInstitucion", tipoInstitucion).getResultList();
     }
     
     public List<Cliente> findClientePersonas(){
         return em.createNamedQuery("Cliente.findClientePersonas").getResultList();
     }
     
     public List<Cliente> findAllConcesionariaActive(){
         return em.createNamedQuery("Cliente.findAllConcesionariaActive").setParameter("concesion", "S").getResultList();
     }
     
      public List<Cliente> findAllRUCPIDE(){
           return em.createNamedQuery("Cliente.findAllRUCPIDE").getResultList();
      }
     
     public List<Cliente> findAllConcesionaria(){
         return em.createNamedQuery("Cliente.findAllConcesionaria").setParameter("concesion", "S").getResultList();
     }
     
     public List<Cliente> findClienteFiltroPersonas(String desPersona){
         
            String sql = "SELECT c FROM Cliente c WHERE c.estado = 'A' and  c.codtipoinstitucion= 3  and upper(trim(c.nombres) || ' '  || trim(c.apellidoPaterno) || ' ' || trim(c.apellidoMaterno)) like :desPersona";
            Query q = em.createQuery(sql);
            q.setParameter("desPersona", '%' + desPersona.toUpperCase() + '%');
            return q.getResultList(); 
     }
     
      public List<Cliente> findClienteFiltroInstituciones(String desInstitucion){
            String sql = "SELECT c FROM Cliente c WHERE c.estado = 'A' and  c.tipoinstitucion.cod_tipoinstitucion is not null  and upper(c.razonSocial) like :desInstitucion";
            Query q = em.createQuery(sql);
            q.setParameter("desInstitucion", '%' + desInstitucion.toUpperCase() + '%');
            return q.getResultList(); 
     }
      
    public List<Cliente> findClienteFiltroInstitucionesxTipo(String desInstitucion, Integer tipo){
          String sql = "SELECT c FROM Cliente c WHERE c.estado = 'A' and  c.codtipoinstitucion = :tipo  and upper(c.razonSocial) like :desInstitucion";
          Query q = em.createQuery(sql);
          q.setParameter("desInstitucion", '%' + desInstitucion.toUpperCase() + '%');
          q.setParameter("tipo", tipo);
          return q.getResultList(); 
    }  
     

    public Cliente findByCriteria(String strNroIdentificacion, Integer iIdTipoIdentificacion) {
        return (Cliente) getEm().createNamedQuery("Cliente.findByCriteria").setParameter("nroidentificacion", strNroIdentificacion).setParameter("idtipoidentificacion", iIdTipoIdentificacion).getSingleResult();
    }

    public Cliente findByIdCliente(Integer iId) {  
         return (Cliente) getEm().createNamedQuery("Cliente.findById").setParameter("idcliente", iId).getSingleResult();
      }

    @Override
    public Cliente findByNroIdentificacion(String identificacion) {
        Cliente cliente = null;

        try {
            cliente = (Cliente) em.createNamedQuery("Cliente.findByNroidentificacion").setParameter("nroidentificacion", identificacion.toUpperCase()).getSingleResult();
        } catch (NoResultException nre) {
            log.warn("No se encontro Cliente con Nro Identificacion [" + identificacion + "]");
        }

        return cliente;
    }

    @Override
    public Cliente findByExpediente(Integer idExpediente) {
        try {
            String sql = "select o.cliente from Expediente o where o.id=?1";
            Query q = em.createQuery(sql);
            return (Cliente) q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<Cliente> findLikeNroIdentificacion(String identificacion) {

        try {
            return em.createNamedQuery("Cliente.findLikeNroidentificacion").setParameter("nroidentificacion", '%' + identificacion + '%').getResultList();
        } catch (NoResultException nre) {
            return new ArrayList<Cliente>();
        }

    }
    
    public List<Cliente> findNombreCliente(Cliente cliente){
        String sql = "";
        Query q = null; 
        
        if (cliente.getCodtipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
            sql = "select c from Cliente c where c.estado = 'A' and upper(c.nombres)=:nombres and upper(c.apellidoPaterno)=:paterno and DECODE(trim(upper(c.apellidoMaterno)), null, '_', trim(upper(c.apellidoMaterno))) =:materno";
            q = em.createQuery(sql);
            q.setParameter("nombres", cliente.getNombres()).setParameter("paterno", cliente.getApellidoPaterno()).setParameter("materno", cliente.getApellidoMaterno().equals("")?"_":cliente.getApellidoMaterno());
        }else{
            sql = "select c from Cliente c where c.estado = 'A' and upper(c.razonSocial)= :razon";
            q = em.createQuery(sql);
            q.setParameter("razon", cliente.getRazonSocial());
        }
         
         return q.getResultList();
    }

    @Override
    public List<Cliente> findLikeNroIdentificacionOrNombre(String prm) {

        try {
            String sql = "SELECT c FROM Cliente c WHERE (c.numeroIdentificacion ".concat(
                    "LIKE :nroidentificacion OR c.razonSocial LIKE :rs OR c.apellidoPaterno LIKE :ap) AND c.estado = 'A'");
            Query q = em.createQuery(sql);
            q.setParameter("nroidentificacion", '%' + prm + '%');
            q.setParameter("rs", '%' + prm.toUpperCase() + '%');
            q.setParameter("ap", '%' + prm.toUpperCase() + '%');
            return q.getResultList();
        } catch (NoResultException nre) {
            return new ArrayList<Cliente>();
        }

    }

    public Cliente guardarObj(Cliente objCliente) {
        if (objCliente.getIdCliente() == null) {
            getEm().persist(objCliente);
            getEm().flush();
            getEm().refresh(objCliente);
        } else {
            getEm().merge(objCliente);
            getEm().flush();
        }
        return objCliente;
    }

    public List<Cliente> findAll() {
        return getEm().createNamedQuery("Cliente.findAll").getResultList();
    }

    public List<Cliente> findByTipoIdentificacionList(Integer iIdTipoIdentificacion, String sTipoIdentificacion) {
        log.info("Buscando clientes con tipo identificacion " + sTipoIdentificacion + ", ididentificacion..." + iIdTipoIdentificacion);
        Query qQuery = null;
        String sQuery = "SELECT NEW Cliente("
                + "c.idCliente,"
                + "c.tipoIdentificacion,"
                + "c.numeroIdentificacion,"
                + "c.razonSocial,"
                + "c.nombres,"
                + "c.apellidoPaterno,"
                + "c.apellidoMaterno,"
                + "c.representanteLegal,"
                + "c.direccionPrincipal,"
                + //                      "c.ubigeoPrincipal," +
                "c.direccionAlternativa,"
                + //                      "c.ubigeoAlternativo," +
                "c.telefono,"
                + "c.correo,"
                + "depup.iddepartamento,"
                + "depup.nombre,"
                + "provup.idprovincia,"
                + "provup.nombre,"
                + "up.iddistrito,"
                + "up.nombre,"
                + "depua.iddepartamento,"
                + "depua.nombre,"
                + "provua.idprovincia,"
                + "provua.nombre,"
                + "ua.iddistrito,"
                + "ua.nombre"
                + ") ";
        sQuery += "FROM Cliente c ";
        sQuery += "LEFT JOIN c.tipoIdentificacion ti ";
        sQuery += "LEFT JOIN c.ubigeoPrincipal up ";
        sQuery += "LEFT JOIN up.provincia provup ";
        sQuery += "LEFT JOIN provup.departamento depup ";
        sQuery += "LEFT JOIN c.ubigeoAlternativo ua ";
        sQuery += "LEFT JOIN ua.provincia provua ";
        sQuery += "LEFT JOIN provua.departamento depua ";
        sQuery += "WHERE c.estado = 'A' ";
        if (iIdTipoIdentificacion != null) {
            sQuery += "AND ti.idtipoidentificacion = :idtipoidentificacion ";
        } else if (sTipoIdentificacion != null) {
            sQuery += "AND ti.nombre = :nombre ";
        }
        sQuery += "AND c.estado <> 'I' ORDER BY c.numeroIdentificacion DESC";
        qQuery = em.createQuery(sQuery);
        if (iIdTipoIdentificacion != null) {
            qQuery.setParameter("idtipoidentificacion", iIdTipoIdentificacion);
        } else if (sTipoIdentificacion != null) {
            qQuery.setParameter("nombre", sTipoIdentificacion);
        }
        return qQuery.getResultList();
    }

    public Cliente findByTipoIdentificacionList2(Integer iIdTipoIdentificacion, String sTipoIdentificacion, String nroIdentificacion) {
        log.info("Buscando clientes con tipo identificacion " + sTipoIdentificacion + ", ididentificacion..." + iIdTipoIdentificacion);
        Query qQuery = null;
        String sQuery = "SELECT NEW Cliente("
                + "c.idCliente,"
                + "c.tipoIdentificacion,"
                + "c.numeroIdentificacion,"
                + "c.razonSocial,"
                + "c.nombres,"
                + "c.apellidoPaterno,"
                + "c.apellidoMaterno,"
                + "c.representanteLegal,"
                + "c.direccionPrincipal,"
                + //	                      "c.ubigeoPrincipal," +
                "c.direccionAlternativa,"
                + //	                      "c.ubigeoAlternativo," +
                "c.telefono,"
                + "c.correo,"
                + "depup.iddepartamento,"
                + "depup.nombre,"
                + "provup.idprovincia,"
                + "provup.nombre,"
                + "up.iddistrito,"
                + "up.nombre,"
                + "depua.iddepartamento,"
                + "depua.nombre,"
                + "provua.idprovincia,"
                + "provua.nombre,"
                + "ua.iddistrito,"
                + "ua.nombre"
                + ") ";
        sQuery += "FROM Cliente c ";
        sQuery += "LEFT JOIN c.tipoIdentificacion ti ";
        sQuery += "LEFT JOIN c.ubigeoPrincipal up ";
        sQuery += "LEFT JOIN up.provincia provup ";
        sQuery += "LEFT JOIN provup.departamento depup ";
        sQuery += "LEFT JOIN c.ubigeoAlternativo ua ";
        sQuery += "LEFT JOIN ua.provincia provua ";
        sQuery += "LEFT JOIN provua.departamento depua ";
        sQuery += "WHERE c.estado = 'A' and c.numeroIdentificacion='" + nroIdentificacion + "' ";
        if (iIdTipoIdentificacion != null) {
            sQuery += "AND ti.idtipoidentificacion = :idtipoidentificacion ";
        } else if (sTipoIdentificacion != null) {
            sQuery += "AND ti.nombre = :nombre ";
        }
        sQuery += "AND c.estado <> 'I' ORDER BY c.numeroIdentificacion DESC";
        qQuery = em.createQuery(sQuery);
        if (iIdTipoIdentificacion != null) {
            qQuery.setParameter("idtipoidentificacion", iIdTipoIdentificacion);
        } else if (sTipoIdentificacion != null) {
            qQuery.setParameter("nombre", sTipoIdentificacion);
        }
        return (Cliente) qQuery.getSingleResult();
    }

    @Override
    public String generateNroIdentificacionOtro() {
        String sQuery = "SELECT tipoidentificacionotro_seq.nextval FROM DUAL";
        Query qQuery = em.createNativeQuery(sQuery);

        return qQuery.getResultList().get(0).toString();
    }

    @Override
    public Cliente findObjectBy(String numeroIdentificacion, Character estado) {
        try {
            return (Cliente) em.createNamedQuery("Cliente.findByNumeroidentificacionEstado").setParameter("numeroidentificacion", numeroIdentificacion.toLowerCase()).setParameter("estado", estado).getSingleResult();
        } catch (NoResultException nre) {
            log.warn("No se encontro registro con numeroIdentificacion [" + numeroIdentificacion + "] estado [" + estado + "]");

            return null;
        } catch (NonUniqueResultException nure) {
            log.warn("Se encontro mas de un registro con numeroIdentificacion [" + numeroIdentificacion + "] estado [" + estado + "]");

            return null;
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Cliente> findByNombreRazonLike(String like){
    	if(like != null){
    		like = "%"+like.toLowerCase()+"%";
    	}
    	String sql = "SELECT c FROM Cliente c WHERE LOWER(c.razonSocial) LIKE :like OR LOWER(c.nombres) LIKE :like OR LOWER(c.apellidoPaterno) LIKE :like OR LOWER(c.apellidoMaterno) LIKE :like ";
    	return em.createQuery(sql).setParameter("like", like).getResultList();
    }
    
    // ////////////////////////////////
    // Getters and Setters //
    // ////////////////////////////////
    public EntityManager getEm() {
        return em;
    }

    @PersistenceContext(unitName = "sigedPU")
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
