/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.sas.action;

import java.util.HashMap;
import javax.servlet.ServletException;
import com.opensymphony.xwork2.Action;
import org.apache.log4j.Logger;

public class ReporteTipo1Action extends ReporteAction {

    private String fechaDesde;
    private String fechaHasta;
    private String mes;
    private String anio;
    private String tipoRecurso;
    private String outputFormat;
    private static Logger log = Logger.getLogger(ReporteTipo1Action.class);

    public void run() throws ServletException, Exception {
        parametros = new HashMap<String, String>();
        parametros.put("fechaDesde", fechaDesde);
        parametros.put("fechaHasta", fechaHasta);
        parametros.put("mes", mes);
        parametros.put("anio", anio);
        parametros.put("tipoRecurso", tipoRecurso);

        lanzarReporte(outputFormat);
    }

    public String ejecutarJSP() throws Exception {
        log.debug("Titulo: " + titulo);

        return Action.SUCCESS;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }
}