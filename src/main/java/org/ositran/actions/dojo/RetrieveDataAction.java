/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions.dojo;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONValue;
import org.ositran.common.action.GenericAction;
import org.ositran.services.ClienteService;
import org.ositran.services.ConcesionarioService;
import org.apache.log4j.Logger;
import org.ositran.utils.Constantes;

import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Concesionario;

public class RetrieveDataAction extends GenericAction {

    private String tipoId;

   private static Logger log=Logger.getLogger(RetrieveDataAction.class);

   private ConcesionarioService concesionarioService;

   public void dataConcesionario() throws Exception {
      List<Concesionario> listaConcesionarios = concesionarioService.findAll();
      List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

      for (Concesionario objC : listaConcesionarios) {
         Map<String, String> reply = new HashMap<String, String>();
         reply.put("id", objC.getRuc());
         reply.put("idcorrentista", objC.getIdConcesionario().toString());
         reply.put("label", objC.getRuc() + " - " + objC.getRazonSocial());
         reply.put("correntista", objC.getRazonSocial());
         reply.put("strDireccion", objC.getDireccion());
         reply.put("strCorreoConcesionario", objC.getCorreo());
         l1.add(reply);
      }
      enviaDatos(l1);
   }
	 
   public void dataCliente() throws Exception {
      if (tipoId == null) {
         log.error("No se recibio tipo de identificacion");

         return;
      }

      log.debug("Tipo de Identificacion con ID [" + tipoId + "]");

      List<Map<String,String>> l1 = new LinkedList<Map<String,String>>();
      iniciarFactorias();
      ClienteService clienteService = serviceFactory.getClienteService();
      List<Cliente> lstCliente = clienteService.findByTipoIdentificacionList(Integer.valueOf(tipoId), null);
 
      if (lstCliente != null) {
         log.debug("Nro de Clientes encontrados [" + lstCliente.size() + "]");

         for (Cliente objCliente : lstCliente) {
            Map<String, String> reply = new HashMap<String, String>();
            reply.put("tipoidentificacion", objCliente.getTipoIdentificacion().getNombre());
            reply.put("id", objCliente.getNumeroIdentificacion());

            if (objCliente.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
               reply.put("label", objCliente.getNumeroIdentificacion() + " - " + objCliente.getRazonSocial());
            } else if (objCliente.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_DNI) || objCliente.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_OTRO)) {
               String sNombreCompleto = objCliente.getNombres() + " " + objCliente.getApellidoPaterno();
               sNombreCompleto += objCliente.getApellidoMaterno() == null ? "" : " " + objCliente.getApellidoMaterno();

               reply.put("label", objCliente.getNumeroIdentificacion() + " - " + sNombreCompleto);
            }

            log.debug("Nombre del Cliente [" + reply.get("label").toString() + "]");

            reply.put("idcliente", objCliente.getIdCliente().toString());
            String razonSocial = objCliente.getRazonSocial();
            reply.put("razonsocial", razonSocial == null ? "" : razonSocial);
            reply.put("representantelegal", objCliente.getRepresentanteLegal() == null ? "" : objCliente.getRepresentanteLegal());
            String nombres = objCliente.getNombres();
            reply.put("nombres", nombres == null ? "" : nombres);
            String paterno = objCliente.getApellidoPaterno();
            reply.put("apellidopaterno", paterno == null ? "" : paterno);
            String materno = objCliente.getApellidoMaterno();
            reply.put("apellidomaterno", materno == null ? "" : materno);
            reply.put("direccionp", objCliente.getDireccionPrincipal() == null ? "" : objCliente.getDireccionPrincipal());

            if (objCliente.getIdDistritoUP() != null) {              
               reply.put("iddistrito", objCliente.getIdDistritoUP().toString());
               reply.put("distrito", objCliente.getSDistritoUP());
               if(objCliente.getIdProvinciaUP()!= null){
            	   reply.put("idprovincia", objCliente.getIdProvinciaUP().toString());
                   reply.put("provincia", objCliente.getSProvinciaUP());
            	   if(objCliente.getIdDepartamentoUP()!= null){
            		   reply.put("iddepartamento", objCliente.getIdDepartamentoUP().toString());
                       reply.put("departamento", objCliente.getSDepartamentoUP()); 
                   }
                   else{
                	   reply.put("departamento", "");
                   }  
               }
               else{
            	   reply.put("provincia", "");  
               }
            } else {              
               reply.put("distrito", "");
            }

            reply.put("direcciona", objCliente.getDireccionAlternativa() == null ? "" : objCliente.getDireccionAlternativa());
                  
            if (objCliente.getIdDistritoUA() != null) {               
               reply.put("distritoa", objCliente.getSDistritoUA());
               if(objCliente.getIdProvinciaUA()!=null){            	   
                   reply.put("provinciaa", objCliente.getSProvinciaUA());
            	   if(objCliente.getIdDepartamentoUA()!=null){
            		   reply.put("departamentoa", objCliente.getSDepartamentoUA());
            	   }
            	   else{
            		   reply.put("departamentoa", ""); 
            	   }
               }
               else{
            	   reply.put("provinciaa", "");
               }
            } else {  
               reply.put("distritoa", "");
            }
            reply.put("telefono", objCliente.getTelefono() == null ? "" : objCliente.getTelefono());
            reply.put("correo", objCliente.getCorreo() == null ? "" : objCliente.getCorreo());

            l1.add(reply);
         }
      }

      enviaDatos(l1);
   } 

	 private String completaDataJSON(String jsonString){
		 String resultado = " { 'identifier': 'id', " +
		 					" 	 'items': " +	jsonString + "}";		
		 
		 return resultado;
	 }
	 
	 private void enviaDatos(List<Map<String,String>> l1){
		 try{
		 String jsonString = JSONValue.toJSONString(l1);
		 jsonString= completaDataJSON(jsonString);
		 HttpServletResponse response=ServletActionContext.getResponse();
		 response.setContentType("text/x-json;charset=UTF-8"); 
		 PrintWriter out = response.getWriter();
		 out.print(jsonString);
      } catch (Exception e) {
			log.error(e.getMessage(), e);
		 }
	 }

	public String getTipoId() {
		return tipoId;
	}

	public void setTipoId(String tipoId) {
		this.tipoId = tipoId;
	}

	public void setConcesionarioService(ConcesionarioService concesionarioService) {
		this.concesionarioService = concesionarioService;
	}
	 
	 
}
