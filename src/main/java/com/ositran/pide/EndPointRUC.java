/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package com.ositran.pide;

import com.ositran.ws.EntidadService;
import com.ositran.ws.Entidad;
import com.ositran.ws.EntidadBean;
import com.ositran.ws.PcmIMgdEntidad;
import com.ositran.ws.PcmIMgdEntidadPortType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.xml.ws.BindingProvider;
import org.ositran.utils.Constantes;
/**
 *
 * @author consultor_jti15
 */
public class EndPointRUC {
   
  public List<EntidadBean> getListaEntidad(int sidcatent, String co_par)throws  Exception{
      List<EntidadBean> lista = null;
      
      try{
          if (co_par.equals("D")) {
             lista = getEntidadRUCDesarrollo().getListaEntidad(sidcatent);
          } else if (co_par.equals("P")) {
            lista = getEntidadRUCProduccion().getListaEntidad(sidcatent);
          }
      }catch(Exception e){
          throw e;
      }
      
      return lista;
  }
  
  public String validarEntidad(String vrucent, String co_par)throws  Exception{
      String respuesta = null;
      
      try{
          if (co_par.equals("D")) {
             respuesta = getEntidadRUCDesarrollo().validarEntidad(vrucent);
          } else if (co_par.equals("P")) {
            respuesta = getEntidadRUCProduccion().validarEntidad(vrucent);
          }
      }catch(Exception e){
          throw e;
      }
      
      return respuesta;
  }
    
    
  private PcmIMgdEntidadPortType getEntidadRUCProduccion()
    throws MalformedURLException
  {
    PcmIMgdEntidad service = new PcmIMgdEntidad(new URL(Constantes.URL_PIDE_RUC_PRODUCCION));
    PcmIMgdEntidadPortType entidad = service.getPcmIMgdEntidadHttpsSoap11Endpoint();
    
    BindingProvider bindingProvider = (BindingProvider)entidad;
    bindingProvider.getRequestContext().put("javax.xml.ws.service.endpoint.address", Constantes.URL_PIDE_RUC_PRODUCCION);
    
    return entidad;
  }
  
  private Entidad getEntidadRUCDesarrollo() throws MalformedURLException{
    EntidadService service = new EntidadService(new URL(Constantes.URL_PIDE_RUC_DESARROLLO));
    Entidad entidad = service.getEntidadPort();
    
    BindingProvider bindingProvider = (BindingProvider)entidad;
    bindingProvider.getRequestContext().put("javax.xml.ws.service.endpoint.address", Constantes.URL_PIDE_RUC_DESARROLLO);
    
    return entidad;
  }

}
