/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.remote.dto.base;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author atc_developer
 */
public class BaseRemoteBean implements Serializable{

    private Date createdDate = new Date();

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

   
}
