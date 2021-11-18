package org.ositran.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.ositran.daos.DistritoDAO;
import org.ositran.daos.EstadoDAO;
import org.ositran.daos.EtapaDAO;
import org.ositran.daos.ExpedientestorDAO;
import org.ositran.dojo.grid.Item;
import org.ositran.utils.ExpedientebusStor;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Expedientestor;
import com.btg.ositran.siged.domain.SubmotivoXExpedienteStor;
import com.btg.ositran.siged.domain.Suministro;

public class ExpedientestorServiceImpl implements ExpedientestorService {

    private static Logger log = Logger.getLogger(ExpedientestorServiceImpl.class);
    private ExpedientestorDAO dao;
    private EstadoDAO estadoDAO;
    private EtapaDAO etapaDAO;
    private DistritoDAO distritoDAO;

//   Constructores
    public ExpedientestorServiceImpl(ExpedientestorDAO dao) {
        setDao(dao);
    }

    public ExpedientestorServiceImpl() {
    }

//    Metodos
    public List<ExpedientebusStor> findSimple(String Condicion) {

        List<ExpedientebusStor> data = new ArrayList<ExpedientebusStor>();

        String Cond = "ex.nroexpediente Like '%" + Condicion + "%'";

        data = dao.findbycondicion(Cond);

        return data;

    }

    public List<ExpedientebusStor> finavanzada(String experiente, String sel1, String sumnistro, String sel2, String proce, String sel3, String concesio) {
        List<ExpedientebusStor> data = new ArrayList<ExpedientebusStor>();
        StringBuilder condic = new StringBuilder("");

        if (experiente != null && !experiente.equals("")) {
            String[] numExps = experiente.split(",");
            for (int k = 0; k < numExps.length; k++) {
                condic.append(' ');
                condic.append("ex.nroexpediente Like'%");
                condic.append(numExps[k]);
                condic.append("%' OR ");
            }

            int lngcondic;
            lngcondic = condic.length();
            condic.delete(lngcondic - 3, lngcondic);

            condic.append(' ');
            condic.append(sel1);
            condic.append(' ');

        }
        if (sumnistro != null && !sumnistro.equals("")) {
            condic.append(' ');
            condic.append("su.nrosuministro Like'%");
            condic.append(sumnistro);
            condic.append("%' ");
            condic.append(' ');
            condic.append(sel2);
            condic.append(' ');
        }
        if (proce != null && !proce.equals("")) {
            condic.append(' ');
            condic.append("p.nombre Like'%");
            condic.append(proce);
            condic.append("%' ");
            condic.append(' ');
            condic.append(sel3);
            condic.append(' ');
        }
        if (concesio != null && !concesio.equals("")) {
            condic.append(' ');
            condic.append("ex.concesionario.idconcesionario Like'%");
            condic.append(concesio);
            condic.append("%' ");
        }
        int logcad = condic.length();
        condic.delete(logcad - 4, logcad);

        data = dao.findbycondicion(condic.toString());

        return data;
    }

    public Expedientestor findByIdExpediente(Integer iIdExp) {
        return dao.findByIdExpediente(iIdExp);
    }

    @Transactional
    public void saveExpedienteStor(Expedientestor objES) {
        dao.saveExpedienteStor(objES);
    }

    @Transactional
    public void updateExpedienteStor(Expedientestor objES) {
        dao.updateExpedienteStor(objES);
    }

    @Transactional
    public Expedientestor saveExpedientestor(Expedientestor expedientestor) {
        if (expedientestor.getEstado() == null) {
            expedientestor.setEstado(estadoDAO.find("anls", "stor"));
        }
        if (expedientestor.getIdestadosiged() == null) {
            expedientestor.setIdestadosiged(estadoDAO.find("anls", "stor"));
        }
        if (expedientestor.getEtapa() == null) {
            expedientestor.setEtapa(etapaDAO.findByCodigo("traming"));
        }
        if (expedientestor.getReclamanteUbigeoReal() != null && expedientestor.getReclamanteUbigeoReal().getIddistrito() != null) {
            expedientestor.setReclamanteUbigeoReal(distritoDAO.findById(expedientestor.getReclamanteUbigeoReal().getIddistrito()));
        } else {
            expedientestor.setReclamanteUbigeoReal(null);
        }
        if (expedientestor.getReclamanteUbigeoProcesal() != null && expedientestor.getReclamanteUbigeoProcesal().getIddistrito() != null) {
            expedientestor.setReclamanteUbigeoProcesal(distritoDAO.findById(expedientestor.getReclamanteUbigeoProcesal().getIddistrito()));
        } else {
            expedientestor.setReclamanteUbigeoProcesal(null);
        }
        return dao.saveExpedienteStor(expedientestor);
    }

    public Expedientestor findByExpediente(Integer idExpediente) {
        return dao.findByExpediente(idExpediente);
    }

    public List<Item> getItemSubmotivos(Integer idExpediente) {
        List<Item> items = new ArrayList<Item>();
        try {
            List<SubmotivoXExpedienteStor> submotivos = dao.findSubmotivo(idExpediente);
            for (SubmotivoXExpedienteStor submotivo : submotivos) {
//                System.out.println("-------------monto-Double->" + new BigDecimal(submotivo.getMonto().doubleValue()).setScale(2, RoundingMode.HALF_UP));
                if (submotivo.getSubmotivo() != null) {
                    Item item = new Item();
                    item.setCodigo(String.valueOf(submotivo.getSubmotivo().getIdsubmotivo()));
                    item.setMotivo(submotivo.getSubmotivo().getMotivo().getDescripcion());
                    item.setSubmotivo(submotivo.getSubmotivo().getDescripcion());
                    if (submotivo.getMonto() != null) {
                        //String monto = String.valueOf(new BigDecimal(submotivo.getMonto().doubleValue()).setScale(2, RoundingMode.HALF_UP));
                        NumberFormat nf = NumberFormat.getInstance();
                        String monto = nf.format(submotivo.getMonto().doubleValue());
                        if (submotivo.getMoneda() != null) {
                            monto += " " + submotivo.getMoneda().getDescripcion();
                        }
                        item.setMonto(monto);
                    } else {
                        item.setMonto("");
                    }
                    items.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(e.getMessage());
        }
        return items;
    }

    public List<Item> getItemSuministros(Integer idExpediente) {
        List<Item> items = new ArrayList<Item>();
        try {
            List<Suministro> suministros = dao.findSuministros(idExpediente);
            for (Suministro suministro : suministros) {
                Item item = new Item();
                item.setCodigo(suministro.getIdsuministro().toString());
                if (suministro.getNrosuministro() != null) {
                    item.setNroSuministro(suministro.getNrosuministro());
                } else {
                    item.setNroSuministro("");
                }
                items.add(item);
            }
        } catch (NoResultException e) {
            log.debug("No hay submotivos asignados expediente");
        }
        return items;
    }

    @Transactional
    public void retirarSubmotivo(Integer idSubmotivo, Integer idExpediente) {
        dao.retirarSubmotivo(idSubmotivo, idExpediente);
    }

    @Transactional
    public void agregarSubmotivo(SubmotivoXExpedienteStor submotivoExpediente) throws Exception {
        try {
            submotivoExpediente.setMonto(Double.valueOf(submotivoExpediente.getStrMonto()));
        } catch (NumberFormatException nfe) {
            submotivoExpediente.setMonto(null);
        }
        dao.agregarSubmotivo(submotivoExpediente);
    }

    @Transactional
    public void retirarSuministros(Integer idSuministro) {
        dao.retirarSuministro(idSuministro);
    }

    @Transactional
    public void agregarSuministro(Suministro suministro) throws Exception {
        dao.agregarSuministro(suministro);
    }

    ////////////////////////
    // Getters and Setters
    ////////////////////////
    public ExpedientestorDAO getDao() {
        return dao;
    }

    public void setDao(ExpedientestorDAO dao) {
        this.dao = dao;
    }

    public EstadoDAO getEstadoDAO() {
        return estadoDAO;
    }

    public void setEstadoDAO(EstadoDAO estadoDAO) {
        this.estadoDAO = estadoDAO;
    }

    public EtapaDAO getEtapaDAO() {
        return etapaDAO;
    }

    public void setEtapaDAO(EtapaDAO etapaDAO) {
        this.etapaDAO = etapaDAO;
    }

    public DistritoDAO getDistritoDAO() {
        return distritoDAO;
    }

    public void setDistritoDAO(DistritoDAO distritoDAO) {
        this.distritoDAO = distritoDAO;
    }
}
