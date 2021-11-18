/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.remote.util;

import org.ositran.dojo.ClienteJSon;
import org.ositran.remote.dto.in.ClienteInRO;
import org.ositran.remote.dto.out.ClienteOutRO;
import org.ositran.remote.dto.out.ExpedienteOutRO;

import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Documento;

/**
 *
 * @author atc_developer
 */
public class BeanConverterUtil {
    
    public static ExpedienteOutRO convertDocumentoToExpedienteOutRO(Documento documento){
        ExpedienteOutRO expedienteOutRO = new ExpedienteOutRO();
        expedienteOutRO.setIdDocumento(documento.getIdDocumento());
        expedienteOutRO.setNumeroExpediente(documento.getNroexpediente());
        expedienteOutRO.setPrincipal(documento.getPrincipal());
        expedienteOutRO.setNumeroFolios(documento.getNumeroFolios());
        expedienteOutRO.setAsunto(documento.getAsunto());
        expedienteOutRO.setEstado(documento.getEstado());
        return expedienteOutRO;
    }

    public static ClienteOutRO createClienteOutROFromClienteJSON(ClienteJSon clienteJson) {
        if (clienteJson != null) {
            ClienteOutRO clienteOutRO = new ClienteOutRO();
            clienteOutRO.setNumeroIdentificacion(clienteJson.getIdentificacion());
            clienteOutRO.setRazonSocial(clienteJson.getRazonSocial());
            clienteOutRO.setNombres(clienteJson.getNombre());
           // clienteOutRO.setApellidoPaterno(clienteJson.getApellido());
            clienteOutRO.setTelefono(clienteJson.getTelefono());
            clienteOutRO.setDireccionPrincipal(clienteJson.getDireccion());
            clienteOutRO.setCorreo(clienteJson.getCorreo());
            clienteOutRO.setDepartamento(clienteJson.getDepartamento() + "");
            clienteOutRO.setProvincia(clienteJson.getProvincia() + "");
            clienteOutRO.setDistrito(clienteJson.getDistrito() + "");
            return clienteOutRO;
        }
        return null;
    }

    public static ClienteOutRO createSimpleClienteOutROFromClienteJSON(ClienteJSon clienteJson) {
        if (clienteJson != null) {
            ClienteOutRO clienteOutRO = new ClienteOutRO();
            clienteOutRO.setTipoIdentificacion("RUC");
            clienteOutRO.setNumeroIdentificacion(clienteJson.getIdentificacion());
            clienteOutRO.setRazonSocial(clienteJson.getRazonSocial());
            clienteOutRO.setNombres(clienteJson.getNombre());
           // clienteOutRO.setApellidoPaterno(clienteJson.getApellido());
            return clienteOutRO;
        }
        return null;
    }

    public static Cliente createClienteFromClienteInRO(ClienteInRO clienteInRO) {
        if (clienteInRO != null) {
            Cliente cliente = new Cliente();
            cliente.setNumeroIdentificacion(clienteInRO.getNumeroIdentificacion());
            cliente.setsTipoIdentificacion(clienteInRO.getTipoIdentificacion());
            cliente.setRazonSocial(clienteInRO.getRazonSocial());
            cliente.setNombres(clienteInRO.getNombres());
            cliente.setApellidoPaterno(clienteInRO.getApellidoPaterno());
            cliente.setApellidoMaterno(clienteInRO.getApellidoMaterno());
            cliente.setTelefono(clienteInRO.getTelefono());
            cliente.setDireccionPrincipal(clienteInRO.getDireccionPrincipal());
            cliente.setDireccionAlternativa(clienteInRO.getDireccionAlternativa());
            cliente.setCorreo(clienteInRO.getCorreo());
            cliente.setIdDepartamentoUP(clienteInRO.getIdDepartamento());
            cliente.setIdProvinciaUP(clienteInRO.getIdProvincia());
            cliente.setIdDistritoUP(clienteInRO.getIdDistrito());
            cliente.setIdDepartamentoUA(clienteInRO.getIdDepartamentoAlternativo());
            cliente.setIdProvinciaUA(clienteInRO.getIdProvinciaAlternativo());
            cliente.setIdDistritoUA(clienteInRO.getIdDistritoAlternativo());
            cliente.setRepresentanteLegal(clienteInRO.getRepresentanteLegal());
            return cliente;
        }
        return null;
    }

    public static Cliente createClienteFromClienteJSON(ClienteJSon clienteJSon) {
        Cliente cliente = new Cliente();
        cliente.setRazonSocial(clienteJSon.getRazonSocial());
        cliente.setNumeroIdentificacion(clienteJSon.getIdentificacion());
        //cliente.setIdDepartamentoUP(clienteJSon.getDepartamento());
        //cliente.setIdProvinciaUP(clienteJSon.getProvincia());
        //cliente.setIdDistritoUP(clienteJSon.getDistrito());
        cliente.setDireccionPrincipal(clienteJSon.getDireccion());
        cliente.setCorreo(clienteJSon.getCorreo());
        cliente.setTelefono(clienteJSon.getTelefono());
        return cliente;
    }

}
