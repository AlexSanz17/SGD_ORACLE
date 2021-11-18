/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.remote.dto.in;

import java.io.Serializable;

/**
 *
 * @author josh
 */
public class ArchivoInRO implements Serializable{

    private Integer id;
    private String fileName;
    private String temporalPath;

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the temporalPath
     */
    public String getTemporalPath() {
        return temporalPath;
    }

    /**
     * @param temporalPath the temporalPath to set
     */
    public void setTemporalPath(String temporalPath) {
        this.temporalPath = temporalPath;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
}
