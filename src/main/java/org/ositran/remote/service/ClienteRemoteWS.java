/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.remote.service;

import java.io.Serializable;
import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import org.ositran.remote.dto.in.ClienteInRO;
import org.ositran.remote.dto.out.ClienteOutRO;

/**
 *
 * @author atc_developer
 */
@WebService
@SOAPBinding(parameterStyle = ParameterStyle.WRAPPED)
public interface ClienteRemoteWS extends Serializable{

    @WebResult(name="listClientsByName")
    public List<ClienteOutRO> listClientsByName(@WebParam(name="clientNameIn")String clientName);

    @WebResult(name="getClientByCredential")
    public ClienteOutRO getClientByCredential(@WebParam(name="credential")String credential);

    @WebResult(name="saveClient")
    public ClienteOutRO saveClient(@WebParam(name="clientInRO")ClienteInRO clienteInRO);


}
