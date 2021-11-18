/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.dojo.tree;

import java.util.ArrayList;
import java.util.List;

public class NodoArbol {

   private Boolean top;
   private String id;
   private String name;
   private String estado;
   private String principal;
   private String ver;
   private String nroReferencia;
   
   /**
     * @return the ver
     */
    public String getVer() {
        return ver;
    }

    /**
     * @param ver the ver to set
     */
    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

   public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
   private List<ReferenciaArbol> children;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public NodoArbol() {
   }
   
   public NodoArbol(Boolean bTop, String iId, String sName, List  lstChildren, String estado, String ver,String nroReferencia) {
      this.top = bTop;
      this.id = iId;
      this.name = sName;
      this.estado = estado;
      this.ver = ver;
      this.nroReferencia=nroReferencia;
      children =  new ArrayList<ReferenciaArbol>() ;
      
      if(lstChildren!=null) {    
                   for(Object hijolist : lstChildren){
                                  if(hijolist.getClass().equals(NodoArbol.class) ){
                                                 ReferenciaArbol hijo =  new ReferenciaArbol( ((NodoArbol)hijolist).getId());
                                                 children.add(hijo);
                                  }else {
                                                 children  =lstChildren;
                                                 break ;
                                  }   
                      }   
       }              
     

   }


   public NodoArbol(Boolean bTop, String iId, String sName, List  lstChildren) {
      this.top = bTop;
      this.id = iId;
      this.name = sName;
      children =  new ArrayList<ReferenciaArbol>() ;
       if(lstChildren!=null) {    
    	   for(Object hijolist : lstChildren){
    	    	  if(hijolist.getClass().equals(NodoArbol.class) ){
    	    		  ReferenciaArbol hijo =  new ReferenciaArbol( ((NodoArbol)hijolist).getId());
    	    		  children.add(hijo);
    	    	  }else {
    	    		  children  =lstChildren;
    	    		  break ;
    	    	  }   
    	      }   
       }              
     

   }
   
   public NodoArbol(Boolean bTop, String iId, String sName, String estado, String principal, List  lstChildren) {
      this.top = bTop;
      this.id = iId;
      this.name = sName;
      this.estado = estado;
      this.principal = principal;
      children =  new ArrayList<ReferenciaArbol>() ;
       if(lstChildren!=null) {    
    	   for(Object hijolist : lstChildren){
    	    	  if(hijolist.getClass().equals(NodoArbol.class) ){
    	    		  ReferenciaArbol hijo =  new ReferenciaArbol( ((NodoArbol)hijolist).getId());
    	    		  children.add(hijo);
    	    	  }else {
    	    		  children  =lstChildren;
    	    		  break ;
    	    	  }   
    	      }   
       }              
     

   }


   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public Boolean getTop() {
      return top;
   }

   public void setTop(Boolean top) {
      this.top = top;
   }



   public String getId() {
	return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<ReferenciaArbol> getChildren() {
      return children;
   }

   public void setChildren(List<ReferenciaArbol> children) {
      this.children = children;
   }
   
   /*
   public void setNodeChildren(List<NodoArbol> children) {
	      for(NodoArbol hijo : children){
	    	  hijo
	    	  Ref
	      }
	      
	      this.children = children;
	   }
	   */

    /**
     * @return the nroReferencia
     */
    public String getNroReferencia() {
        return nroReferencia;
    }

    /**
     * @param nroReferencia the nroReferencia to set
     */
    public void setNroReferencia(String nroReferencia) {
        this.nroReferencia = nroReferencia;
    }
   
   
}
