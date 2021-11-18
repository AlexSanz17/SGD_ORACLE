/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.Date;
import java.util.List;
import org.ositran.utils.Expedienfindadvance;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Usuario;
import java.util.Map;

public interface ExpedienteDAO {

    public Expediente findByIdExpediente(Integer idExpediente);

    public Expediente findPropietarioByIdExpediente(Integer idExpediente);

    public Expediente findByNroExpediente(String sNroExpediente);

    public Expediente saveExpediente(Expediente objE);

    public List<Expediente> buscarLstPor(Integer iIdCliente, Character cEstado);
    
    public List<Map<String, String>> buscarLstPorCliente(Integer iIdCliente, Character cEstado);

    public List<Expediente> findAll();

    public List<Expediente> filtrarExpediente(String sNroExpediente, String sProceso, String sNroIdentificacion, String sRazonSocial1, String sRuc, String sRazonSocial2);

    public List<Expediente> findByCriteria(String numeroIdentificacion, String razonSocial, String numeroExpediente, String asunto, int proceso, Date fecha, String tipoBusqueda, Integer idusuario, String nt);

    public Integer getMaxIdExpediente();

    public Integer generateNroExpedienteProduccion();

    public List<Expediente> findByNroDocument(String StrNDo);

    public List<Expedienfindadvance> findbyadvanced(String Strcampo);

    public List<Expedienfindadvance> findbyadvancedId(String condicion);

    public List<Expedienfindadvance> findbySuperadvanced(String numeroExpediente, String numeroDocumento, String numeroMesaPartes, String tipoDocumento, String concesionario, String cliente, String areaDestino, String propietario, String proceso, String tipoBusqueda, String asuntoexpediente, String asuntodocumento, String estadoexpediente);

    public List<Expedienfindadvance> busquedaAvanzadaAdicional(String numeroExpediente, String tipoDocumento, String numeroDocumento, String identidadConcesionario, String numeroMesaPartes, String fechaInicioDocumento, String fechaFinDocumento, String concesionario, String fechaInicioExpediente, String fechaFinExpediente, String cliente, String direccionCliente, String areaDestino, String propietario, String proceso, String tipoBusqueda, String asuntoexpediente, String asuntodocumento, String estadoexpediente);

    public Documento getDocumentoPrincipal(Integer idExpediente);

    public List<Expediente> findByNroExpedientesTerminadosGFEyGG_SAS(String sNroExpediente);

    public List<Expediente> findByNroExpedientesTerminadosSAS(String sNroExpediente, String Proceso);

    public Expediente findByNroExpedienteTerminadoSAS(String sNroExpediente);

    public void aplicarNumeracionInternaExpediente(Integer iIdExp);

    public void actualizarResponsableExpediente(Integer idExpediente, Usuario idpropietario);

    public Integer getCantidadTrazabilidad(Integer idExpediente);

    public List<Expediente> findLstBy(Integer iIdResponsable);

    public Expediente findObjectBy(String numeroExpediente, Character estado);

    public Integer generateNroExpedienteDesarrollo();
}
