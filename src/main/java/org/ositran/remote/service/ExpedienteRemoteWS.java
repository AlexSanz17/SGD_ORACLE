/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.remote.service;

import java.io.Serializable;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import org.ositran.remote.dto.in.ExpedienteInRO;
import org.ositran.remote.dto.out.ExpedienteOutRO;

/**
 *
 * @author atc_developer
 */
@WebService
@SOAPBinding(parameterStyle = ParameterStyle.WRAPPED)
public interface ExpedienteRemoteWS extends Serializable{

    @WebResult(name="createExpWithOneDocument")
    public ExpedienteOutRO createExpWithOneDocument(@WebParam(name="expedienteInRO")ExpedienteInRO expedienteInRO);

}
