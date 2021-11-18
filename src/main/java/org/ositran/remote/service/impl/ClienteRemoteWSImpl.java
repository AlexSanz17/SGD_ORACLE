/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.remote.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.ositran.dojo.ClienteJSon;
import org.ositran.services.ClienteService;
import org.ositran.services.DistritoService;
import org.ositran.services.TipoidentificacionService;
import org.ositran.remote.dto.in.ClienteInRO;
import org.ositran.remote.dto.out.ClienteOutRO;
import org.ositran.utils.Constantes;
import org.ositran.remote.dto.enums.InvocationResult;
import org.ositran.remote.service.ClienteRemoteWS;
import org.ositran.remote.util.BeanConverterUtil;

import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Tipoidentificacion;
/**
 *
 * @author atc_developer
 */
@WebService(endpointInterface = "org.ositran.remote.service.ClienteRemoteWS", serviceName = "ClienteRemoteWS")
public class ClienteRemoteWSImpl implements ClienteRemoteWS {

    private static Logger log = Logger.getLogger(ClienteRemoteWSImpl.class);
    private ClienteService clienteService;
    private TipoidentificacionService tipoIdentificacionService;
    private DistritoService distritoService;

    @Override
    public List<ClienteOutRO> listClientsByName(String clientName) {
        List<ClienteOutRO> listaClientes = null;
        try {
            List<ClienteJSon> salida = new ArrayList<ClienteJSon>();
            List<ClienteJSon> encontrados = null;//clienteService.getClientesPorRazonSocialSunat(clientName);
            if (encontrados != null) {
                for (ClienteJSon cliente : encontrados) {
                    Cliente existente = clienteService.findByNroIdentificacion(cliente.getIdentificacion());
                    if (existente == null) {
                        salida.add(cliente);
                    }
                }
            }
            ClienteOutRO clienteOutRO = null;
            if (!salida.isEmpty()) {
                listaClientes = new ArrayList<ClienteOutRO>();
                for (ClienteJSon clienteItem : salida) {
                    clienteOutRO = BeanConverterUtil.createSimpleClienteOutROFromClienteJSON(clienteItem);
                    listaClientes.add(clienteOutRO);
                }
                return listaClientes;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return listaClientes;
    }



    @Override
    public ClienteOutRO getClientByCredential(String credential) {
        ClienteOutRO clienteOutRO = null;
        try {
            ClienteJSon cliente = null;
            Cliente existente = clienteService.findByNroIdentificacion(credential);
            if (existente != null) {
                cliente = new ClienteJSon();
                cliente.setIdentificacion("El cliente ya existe.");
            } else {
                cliente = null; //clienteService.getClientePorRUCSunat(credential);
            }
            if (cliente != null) {
                clienteOutRO = BeanConverterUtil.createClienteOutROFromClienteJSON(cliente);
                clienteOutRO.setInvocationResult(InvocationResult.SUCCESS);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            clienteOutRO = new ClienteOutRO();
            clienteOutRO.setInvocationResult(InvocationResult.SUCCESS);
            clienteOutRO.setMessage("Servicio de consulta no disponible");
        }
        return clienteOutRO;
    }

    @Override
    public ClienteOutRO saveClient(ClienteInRO clienteInRO) {
        ClienteOutRO clienteOutRO = new ClienteOutRO();
        ;
        try {
            Cliente objCliente = BeanConverterUtil.createClienteFromClienteInRO(clienteInRO);
            String sTipoIdentificacion = objCliente.getsTipoIdentificacion();
            if (objCliente == null) {
                log.error("No se recibio ningun cliente");
                clienteOutRO.setMessage("No se recibio ningun cliente");
                clienteOutRO.setInvocationResult(InvocationResult.FAILED);
                return clienteOutRO;
            }
            if (sTipoIdentificacion == null && sTipoIdentificacion.equals("")) {
                log.error("No se recibio el tipo de identificacion");
                clienteOutRO.setMessage("El cliente con Nro de Identificacion " + objCliente.getNumeroIdentificacion() + " ya existe.");
                clienteOutRO.setInvocationResult(InvocationResult.FAILED);
                return clienteOutRO;
            }
            log.debug("sTipoIdentificacion [" + sTipoIdentificacion + "]");
            // try{
            log.debug("objCliente [" + objCliente + "]");
            log.debug("Cliente a actualizar con ID [" + objCliente.getIdCliente() + "]");
            log.debug("Nro Identificacion [" + objCliente.getNumeroIdentificacion() + "]");

            if (Constantes.TIPO_IDENTIFICACION_OTRO.equalsIgnoreCase(sTipoIdentificacion) && objCliente.getNumeroIdentificacion() != null && objCliente.getNumeroIdentificacion().equals("")) {
                objCliente.setNumeroIdentificacion(clienteService.generateNroIdentificacionOtro());

                log.info("Numero de identificacion generado [" + objCliente.getNumeroIdentificacion() + "]");
            }

            if ((objCliente.getIdCliente() == null || objCliente.getIdCliente() == 0) && clienteService.findByNroIdentificacion(objCliente.getNumeroIdentificacion()) != null) {
                log.warn("El cliente con Nro de Identificacion [" + objCliente.getNumeroIdentificacion() + "] ya existe en la Base de Datos");
                clienteOutRO.setMessage("El cliente con Nro de Identificacion " + objCliente.getNumeroIdentificacion() + " ya existe.");
                clienteOutRO.setInvocationResult(InvocationResult.FAILED);
                return clienteOutRO;
            }
            log.info("Creando cliente...");
            Cliente objClienteNew = null;
            Cliente objClienteOld = null;
            String sTipoAuditoria = null;
            Tipoidentificacion objTipoidentificacion = null;
            String usuarioCreador = clienteInRO.getCreatedUser();
            objClienteOld = new Cliente();
            objTipoidentificacion = getTipoIdentificacionService().buscarObjPor(sTipoIdentificacion);
            log.debug("** Creacion de Cliente **");
            objCliente.setTipoIdentificacion(objTipoidentificacion);
            objClienteNew = objCliente;
            objClienteNew.setEstado(Constantes.ESTADO_ACTIVO);
            objClienteNew.setFechaCreacion(new Date());
            if (objCliente.getIdDistritoUP() != null) {
           //      objClienteNew.setUbigeoPrincipal(getDistritoService().findById(objCliente.getIdDistritoUP()));
            }
            if (objCliente.getIdDistritoUA() != null) {
                objClienteNew.setUbigeoAlternativo(getDistritoService().findById(objCliente.getIdDistritoUA()));
            }
            sTipoAuditoria = Constantes.AUDITORIA_TIPO_REGISTRO;
            objClienteNew = clienteService.guardarObj(objClienteOld, objClienteNew, usuarioCreador, sTipoAuditoria);
            clienteOutRO.setIdCliente(objClienteNew.getIdCliente());
            clienteOutRO.setInvocationResult(InvocationResult.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            clienteOutRO.setMessage("Ocurrio un error.");
            clienteOutRO.setInvocationResult(InvocationResult.FAILED);
        }
        return clienteOutRO;
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public TipoidentificacionService getTipoIdentificacionService() {
        return tipoIdentificacionService;
    }

    public void setTipoIdentificacionService(TipoidentificacionService tipoIdentificacionService) {
        this.tipoIdentificacionService = tipoIdentificacionService;
    }

    public DistritoService getDistritoService() {
        return distritoService;
    }

    public void setDistritoService(DistritoService distritoService) {
        this.distritoService = distritoService;
    }
}
