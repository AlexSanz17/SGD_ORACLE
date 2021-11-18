package org.ositran.services;

import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.daos.ExpedientesPendientesAYQDAO;
import org.ositran.daos.SuministroDAO;

import com.btg.ositran.siged.domain.ExpedientesPendientesAYQ;

public class ReporteSigedUsuariosServiceImpl implements ReporteSigedUsuariosService {

    private ExpedientesPendientesAYQDAO daoAYQ;
    private SuministroDAO suministroDAO;
    private static Logger log = Logger.getLogger(ReporteSigedUsuariosServiceImpl.class);

    public ReporteSigedUsuariosServiceImpl(ExpedientesPendientesAYQDAO daoAYQ) {
        this.daoAYQ = daoAYQ;
    }

    @Override
    public List<ExpedientesPendientesAYQ> listarTodoAYQ() {
        return daoAYQ.listarTodo();
    }

    @Override
    public List<ExpedientesPendientesAYQ> generarListaAYQ(String tipoExpediente, String fechaDesde, String fechaHasta, String sala, String responsable, String analista, String vencimientoDesde, String vencimientoHasta) {
        log.debug("SALA: " + sala);
        if (tipoExpediente == null || tipoExpediente.equals("")) {
            tipoExpediente = "Todos";
        }
        if (fechaDesde == null) {
            fechaDesde = "";
            fechaHasta = "";
        }
        if (fechaHasta == null) {
            fechaDesde = "";
            fechaHasta = "";
        }
        if (vencimientoDesde == null) {
            vencimientoDesde = "";
            vencimientoHasta = "";
        }
        if (vencimientoHasta == null) {
            vencimientoDesde = "";
            vencimientoHasta = "";
        }
        if (sala == null || sala.equals("")) {
            sala = "Todas";
        }
        if (responsable == null || responsable.equals("")) {
            responsable = "Todos";
        }
        if (analista == null || analista.equals("")) {
            analista = "Todos";
        }
        List<ExpedientesPendientesAYQ> lista = daoAYQ.generarListaAYQ(tipoExpediente, fechaDesde, fechaHasta, sala, responsable, analista, vencimientoDesde, vencimientoHasta);
        if (lista != null && !lista.isEmpty()) {
            for (ExpedientesPendientesAYQ expediente : lista) {
                List<String> listaSum = suministroDAO.findByIdDocumento(expediente.getIdDocumento());
                if (listaSum != null && !listaSum.isEmpty()) {
                    for (int i = 0; i < listaSum.size(); i++) {
                        expediente.addSuministros(listaSum.get(i));
                    }
                }
            }
        }
        return lista;
    }

    public ExpedientesPendientesAYQDAO getDaoAYQ() {
        return daoAYQ;
    }

    public void setDaoAYQ(ExpedientesPendientesAYQDAO daoAYQ) {
        this.daoAYQ = daoAYQ;
    }

    public SuministroDAO getSuministroDAO() {
        return suministroDAO;
    }

    public void setSuministroDAO(SuministroDAO suministroDAO) {
        this.suministroDAO = suministroDAO;
    }
}
