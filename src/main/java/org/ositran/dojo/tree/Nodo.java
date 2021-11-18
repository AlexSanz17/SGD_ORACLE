/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.dojo.tree;

import java.util.List;

public class Nodo {

   private Boolean top;
   private Integer id;
   private String name;
   private List<Referencia> children;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public Nodo() {
   }

   public Nodo(Boolean bTop, Integer iId, String sName, List<Referencia> lstChildren) {
      this.top = bTop;
      this.id = iId;
      this.name = sName;
      this.children = lstChildren;
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

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<Referencia> getChildren() {
      return children;
   }

   public void setChildren(List<Referencia> children) {
      this.children = children;
   }
}
