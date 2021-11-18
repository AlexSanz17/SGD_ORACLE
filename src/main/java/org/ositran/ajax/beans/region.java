/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.beans;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class region implements Serializable {

	private String idregion;
	private String region;
	
    public String getIdregion() {
        return idregion;
    }

    public void setIdregion(String idregion) {
        this.idregion = idregion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    
    public String toString()
	{
	  return new ToStringBuilder(this).append("idregion",getIdregion()).append("region",getRegion()).toString();
	}
	
}
