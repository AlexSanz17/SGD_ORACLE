/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.pojos;

import java.io.Serializable;

import org.alfresco.webservice.types.Version;

public class VersionView implements Serializable{

	private static final long serialVersionUID = 1L;
   Version version;
   String enlace;

   public String getEnlace() {
      // version.getCrea
      return enlace;
   }

   public void setEnlace(String enlace) {
      this.enlace = enlace;
   }

   public Version getVersion() {
      return version;
   }

   public void setVersion(Version version) {
      // version.getCreated().
      this.version = version;
   }
}














