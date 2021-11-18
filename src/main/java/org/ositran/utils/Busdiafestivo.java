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

public class Busdiafestivo {

   private int id_festivo;
   private String nom_region;
   private int iddiafestivo;
   private String dia_festiva;
   private String mes_festiva;
   private String anio_festiva;
   private String motivo_festivo;
   private Date fechanolaborable;

	public Busdiafestivo(int id_festivo, String nom_region, int iddiafestivo, String dia_festiva, String mes_festiva, String anio_festiva, String motivo_festivo) {
        this.id_festivo = id_festivo;
        this.nom_region = nom_region;
        this.iddiafestivo = iddiafestivo;
        this.dia_festiva = dia_festiva;
        this.mes_festiva = mes_festiva;
        this.anio_festiva = anio_festiva;
        this.motivo_festivo = motivo_festivo;
    }

    public Busdiafestivo() {
    }


    public int getId_festivo() {
        return id_festivo;
    }

    public void setId_festivo(int id_festivo) {
        this.id_festivo = id_festivo;
    }

    public String getNom_region() {
        return nom_region;
    }

    public void setNom_region(String nom_region) {
        this.nom_region = nom_region;
    }

    public int getIddiafestivo() {
        return iddiafestivo;
    }

    public void setIddiafestivo(int iddiafestivo) {
        this.iddiafestivo = iddiafestivo;
    }

    public String getDia_festiva() {
        return dia_festiva;
    }

    public void setDia_festiva(String dia_festiva) {
        this.dia_festiva = dia_festiva;
    }

    public String getMes_festiva() {
        return mes_festiva;
    }

    public void setMes_festiva(String mes_festiva) {
        this.mes_festiva = mes_festiva;
    }

    public String getAnio_festiva() {
        return anio_festiva;
    }

    public void setAnio_festiva(String anio_festiva) {
        this.anio_festiva = anio_festiva;
    }

    public String getMotivo_festivo() {
        return motivo_festivo;
    }

    public void setMotivo_festivo(String motivo_festivo) {
        this.motivo_festivo = motivo_festivo;
    }
    public Date getFechanolaborable() {
    	return fechanolaborable;
    }

    public void setFechanolaborable(Date fechanolaborable) {
    	this.fechanolaborable = fechanolaborable;
    }
}
