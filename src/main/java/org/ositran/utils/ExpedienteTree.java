package org.ositran.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpedienteTree {

   private String iIdExpediente;
   private List<ExpedienteTree> lstExpedienteChildren;
   private Map<String, ExpedienteTree> mapExpedienteTree = new HashMap<String, ExpedienteTree>();
   private String sDescripcion;
   private String delexpediente ;
   
   private String sObservacion;


   public ExpedienteTree(String iIdExp, String sDesc, ExpedienteTree... lstExpCh) {
      setIIdExpediente(iIdExp);
      setSDescripcion(sDesc);
      this.lstExpedienteChildren = new ArrayList<ExpedienteTree>();

      for (ExpedienteTree objExpediente : lstExpCh) {
         getLstExpedienteChildren().add(objExpediente);
      } 

      getMapExpedienteTree().put(iIdExp, this);
   }

   ////////////////////////
   // Getters and Setters
   ////////////////////////
   public String getIIdExpediente() {
      return iIdExpediente;
   }

   public void setIIdExpediente(String iIdExpediente) {
      this.iIdExpediente = iIdExpediente;
   }

   public List<ExpedienteTree> getLstExpedienteChildren() {
      return lstExpedienteChildren;
   }

   public void setLstExpedienteChildren(List<ExpedienteTree> lstExpedienteChildren) {
      this.lstExpedienteChildren = lstExpedienteChildren;
   }

   public Map<String, ExpedienteTree> getMapExpedienteTree() {
      return mapExpedienteTree;
   }

   public void setMapExpedienteTree(Map<String, ExpedienteTree> mapExpedienteTree) {
      this.mapExpedienteTree = mapExpedienteTree;
   }

   public String getSDescripcion() {
      return sDescripcion;
   }

   public void setSDescripcion(String sDescripcion) {
      this.sDescripcion = sDescripcion;
   }

   public String getDelexpediente() {
      return delexpediente;
   }

   public void setDelexpediente(String delexpediente) {
      this.delexpediente = delexpediente;
   }

	public String getsObservacion() {
		return sObservacion;
	}
	
	public void setsObservacion(String sObservacion) {
		this.sObservacion = sObservacion;
	}
   
             
}
