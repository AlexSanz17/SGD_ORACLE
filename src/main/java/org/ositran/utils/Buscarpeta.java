/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.utils;

import java.util.Date;


/**
 *
 * @author WMH
 */

public class Buscarpeta {

    private int iddocumento;
    private String Param1;
    private String Param2;
    private String Param3;
    private String Param4;
    private Date fecha;

    public Buscarpeta(int iddocumento, String Param1, String Param2, String Param3, String Param4, Date fecha) {
        this.iddocumento = iddocumento;
        this.Param1 = Param1;
        this.Param2 = Param2;
        this.Param3 = Param3;
        this.Param4 = Param4;
        this.fecha = fecha;
    }

    public Buscarpeta() {
    }

    
   
    public int getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(int iddocumento) {
        this.iddocumento = iddocumento;
    }

    public String getParam1() {
        return Param1;
    }

    public void setParam1(String Param1) {
        this.Param1 = Param1;
    }

    public String getParam2() {
        return Param2;
    }

    public void setParam2(String Param2) {
        this.Param2 = Param2;
    }

    public String getParam3() {
        return Param3;
    }

    public void setParam3(String Param3) {
        this.Param3 = Param3;
    }

    public String getParam4() {
        return Param4;
    }

    public void setParam4(String Param4) {
        this.Param4 = Param4;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

   
}
