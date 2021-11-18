/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package com.ositran.pide;

import com.ositran.ws.PcmCuo;
import com.ositran.ws.PcmCuoPortType;
import javax.xml.ws.BindingProvider;
import org.ositran.utils.Constantes;
import java.net.URL;
/**
 *
 * @author jbengoa
 */
public class EndPoint {
  public String getCuo(String ip){
    String cuo = "";
    try{
        PcmCuo pcmCuo = new PcmCuo(new URL(Constantes.URL_PIDE_CUO_PRODUCCION));
        PcmCuoPortType pcmCuoPortType = pcmCuo.getPcmCuoHttpsSoap11Endpoint();
      
        BindingProvider bindingProvider = (BindingProvider)pcmCuoPortType;
        bindingProvider.getRequestContext().put("javax.xml.ws.service.endpoint.address", Constantes.URL_PIDE_CUO_PRODUCCION);
      
         cuo = pcmCuoPortType.getCUO(ip);
    }
    catch (Exception e){
      cuo = "-1";
    }
    return cuo;
  }
  
  public String getCuo(String ruc, String servicio){
    String cuo = "";
    try{
      PcmCuo pcmCuo = new PcmCuo(new URL(Constantes.URL_PIDE_CUO_PRODUCCION));
      PcmCuoPortType pcmCuoPortType = pcmCuo.getPcmCuoHttpsSoap11Endpoint();
      
      BindingProvider bindingProvider = (BindingProvider)pcmCuoPortType;
      bindingProvider.getRequestContext().put("javax.xml.ws.service.endpoint.address", Constantes.URL_PIDE_CUO_PRODUCCION);
      
      cuo = pcmCuoPortType.getCUOEntidad(ruc, servicio);
    }
    catch (Exception e){
      cuo = "-1";
    }
    return cuo;
  }
}
