/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package com.ositran.pide;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.xml.ws.BindingProvider;
import com.ositran.ws.RespuestaTramite;
import com.ositran.ws.RecepcionTramite;
import com.ositran.ws.RespuestaCargoTramite;
import com.ositran.ws.CargoTramite;
import com.ositran.ws.ConsultaTramite;
import com.ositran.ws.IOTramite;
import com.ositran.ws.IOTramiteService;
import com.ositran.ws.IoTipoDocumentoTramite;
import com.ositran.ws.PcmIMgdTramite;
import com.ositran.ws.PcmIMgdTramitePortType;
import com.ositran.ws.RespuestaConsultaTramite;
import com.ositran.ws.Tramite;
import com.ositran.ws.Tramite_Service;
import org.ositran.utils.Constantes;

public class WSPideTramite{
 
  public List<IoTipoDocumentoTramite> getTipoDocumento(String co_par) throws  Exception{
    List<IoTipoDocumentoTramite> lista = null;
    try
    {
      if (co_par.equals("D")) {
        lista = getIOTramiteDesarrollo().getTipoDocumento();
      } else if (co_par.equals("P")) {
        lista = getIOTramiteProduccion().getTipoDocumento();
      }
    }
    catch (Exception e){
       throw e;
    }
    return lista;
  }
  
  public RespuestaTramite recepcionarTramite(RecepcionTramite request, String co_par) throws  Exception{
    RespuestaTramite respuestaTramite = null;
    
    try{
      if (co_par.equals("D")) {
        respuestaTramite = getIOTramiteDesarrollo().recepcionarTramiteResponse(request);
      } else if (co_par.equals("P")) {
        respuestaTramite = getIOTramiteProduccion().recepcionarTramiteResponse(request);
      }else if (co_par.equals("O")) {
        respuestaTramite = getIOTramiteOsitran().recepcionarTramiteResponse(request);
      }
    }
    catch (Exception e){
        throw e; 
    }
    return respuestaTramite;
  }
  
  public RespuestaCargoTramite cargoTramite(CargoTramite request, String co_par) throws  Exception{
    RespuestaCargoTramite respuestaCargoTramite = null;
    try
    {
      if (co_par.equals("D")) {
        respuestaCargoTramite = getIOTramiteDesarrollo().cargoResponse(request);
      }else if (co_par.equals("P")) {
        respuestaCargoTramite = getIOTramiteProduccion().cargoResponse(request);
      }else if (co_par.equals("O")) {
        respuestaCargoTramite = getIOTramiteOsitran().cargoResponse(request);
      }
    }
    catch (Exception e){
       throw e;
    }
    return respuestaCargoTramite;
  }
  

  
  public RespuestaConsultaTramite consultarTramite(ConsultaTramite request, String co_par) throws  Exception
  {
    RespuestaConsultaTramite respuestaConsultaTramite = null;
    try{
     if (co_par.equals("D")) {
        respuestaConsultaTramite = getIOTramiteDesarrollo().consultarTramiteResponse(request);
     }else if (co_par.equals("P")) {
        respuestaConsultaTramite = getIOTramiteProduccion().consultarTramiteResponse(request);
     }else if (co_par.equals("O")) {
        respuestaConsultaTramite = getIOTramiteOsitran().consultarTramiteResponse(request.getVcuo());
     }
    }
    catch (Exception e){
        throw e;
    }
    return respuestaConsultaTramite;
  }
  
  
  private PcmIMgdTramitePortType getIOTramiteProduccion()
    throws MalformedURLException
  {
      PcmIMgdTramite service = new PcmIMgdTramite(new URL(Constantes.URL_PIDE_TRAMITE_PRODUCCION));
      PcmIMgdTramitePortType entidad = service.getPcmIMgdTramiteHttpsSoap11Endpoint();
    
     BindingProvider bindingProvider = (BindingProvider)entidad;
     bindingProvider.getRequestContext().put("javax.xml.ws.service.endpoint.address", Constantes.URL_PIDE_TRAMITE_PRODUCCION);
    
    return entidad;
  }
  
  private IOTramite getIOTramiteDesarrollo() throws MalformedURLException{
    IOTramiteService service = new IOTramiteService(new URL(Constantes.URL_PIDE_TRAMITE_DESARROLLO));
    IOTramite entidad = service.getIOTramitePort();
    
    BindingProvider bindingProvider = (BindingProvider)entidad;
    bindingProvider.getRequestContext().put("javax.xml.ws.service.endpoint.address", Constantes.URL_PIDE_TRAMITE_DESARROLLO);
    
    return entidad;
  }
  
  private Tramite getIOTramiteOsitran() throws MalformedURLException{
    Tramite_Service service = new Tramite_Service(new URL(Constantes.URL_PIDE_TRAMITE_OSITRAN));
    Tramite entidad = service.getTramitePort();
    BindingProvider bindingProvider = (BindingProvider)entidad;
    bindingProvider.getRequestContext().put("javax.xml.ws.service.endpoint.address", Constantes.URL_PIDE_TRAMITE_OSITRAN);
    return entidad;
  }
  
  
}
