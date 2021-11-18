/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ositran.utils.ExpedientebusStor;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Expedientestor;
import com.btg.ositran.siged.domain.SubmotivoXExpedienteStor;
import com.btg.ositran.siged.domain.Suministro;

@Repository
public class ExpedientestorDAOImpl implements ExpedientestorDAO {

    private static Log log = LogFactory.getLog(ExpedientestorDAOImpl.class);
    private EntityManager em;

    public Expedientestor findByIdExpediente(Integer iIdExp) {
        Expedientestor expediente = null;
        try {
            expediente = em.find(Expedientestor.class, iIdExp);
        } catch (Exception e) {
            log.warn("No se encontro el ExpedienteStor con id: " + iIdExp);
        }
        return expediente;
    }

    public Expedientestor saveExpedienteStor(Expedientestor objES) {
        getEm().persist(objES);
        getEm().flush();
        getEm().refresh(objES);
        return objES;
    }

    public void updateExpedienteStor(Expedientestor objES) {
        getEm().merge(objES);
        getEm().flush();
    }

    public List<ExpedientebusStor> findbycondicion(String condicion) {
        List<ExpedientebusStor> data = new ArrayList<ExpedientebusStor>();
        String sql = "SELECT NEW org.ositran.utils.ExpedientebusStor(" + "p.idproceso," + "p.nombre," + "p.descripcion," + "ex.id," + "ex.nroexpediente," + "ex.fechacreacion," + "exs.tipoanalisis," + "exs.tipoexpediente," + "su.nrosuministro," + "ex.concesionario.idconcesionario," + "ex.concesionario.razonsocial," + "ex.concesionario.direccion," + "d.iddocumento," + "d.nrodocumento," + "d.asunto," + "d.nrofolios," + "d.fechacreacion)" + " FROM Unidad un" + " JOIN un.usuarioList u  " + " JOIN u.procesoList p" + " JOIN p.expedienteList ex " + " JOIN ex.expedientestor exs " + " JOIN ex.documentoList d  " + " JOIN d.documentostor ds  " + " JOIN ds.suministroList su  " + " WHERE " + condicion;
        data = (List<ExpedientebusStor>) em.createQuery(sql).getResultList();
        return data;
    }

//    @SuppressWarnings("unchecked")
    @Override
    public SubmotivoXExpedienteStor findSubmotivo(Integer idExpediente, Integer idSubmotivo) {
        SubmotivoXExpedienteStor ses = null;
        try {
            Query q = em.createQuery("SELECT s from SubmotivoXExpedienteStor s ".concat(
                    "WHERE s.submotivoxexpedientePK.idexpediente = :idexpediente ").concat(
                    "and s.submotivoxexpedientePK.idsubmotivo = :idsubmotivo"));
            q.setParameter("idexpediente", idExpediente);
            q.setParameter("idsubmotivo", idSubmotivo);
            ses = (SubmotivoXExpedienteStor) q.getSingleResult();
        } catch(NoResultException nre ) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ses;
    }

    @Override
    public Suministro findSuministro(Integer idExpediente, String nroSuministro) {
        Suministro s = null;
        try {
            Query q = em.createQuery("SELECT s from Suministro s ".concat(
                    "WHERE s.expediente.id = :idexpediente ").concat(
                    "and s.nrosuministro = :nrosuministro"));
            q.setParameter("idexpediente", idExpediente);
            q.setParameter("nrosuministro", nroSuministro);
            s = (Suministro) q.getSingleResult();
        } catch(NoResultException nre ) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public List<SubmotivoXExpedienteStor> findSubmotivo(Integer idExpediente) {
        List<SubmotivoXExpedienteStor> lista = null;
        try {
            Query q = em.createQuery("SELECT s from SubmotivoXExpedienteStor s WHERE s.submotivoxexpedientePK.idexpediente = :idexpediente");
            q.setParameter("idexpediente", idExpediente);
            lista = (List<SubmotivoXExpedienteStor>) q.getResultList();
        } catch (Exception e) {
            lista = new ArrayList<SubmotivoXExpedienteStor>();
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Suministro> findSuministros(Integer idExpediente) {
        List<Suministro> lista = null;
        try {
            Query q = em.createQuery("SELECT s from Suministro s WHERE s.expediente.id = :idexpediente");
            q.setParameter("idexpediente", idExpediente);
            lista = (List<Suministro>) q.getResultList();
        } catch (Exception e) {
            lista = new ArrayList<Suministro>();
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void retirarSubmotivo(Integer idSubmotivo, Integer idExpediente) {
        SubmotivoXExpedienteStor s = (SubmotivoXExpedienteStor) em.createQuery("SELECT s from SubmotivoXExpedienteStor s WHERE s.submotivoxexpedientePK.idexpediente = :idexpediente AND s.submotivoxexpedientePK.idsubmotivo = :idsubmotivo").setParameter("idexpediente", idExpediente).setParameter("idsubmotivo", idSubmotivo).getSingleResult();
        em.remove(s);
        em.flush();
    }

    @Override
    public void agregarSubmotivo(SubmotivoXExpedienteStor submotivoExpediente) throws Exception {
        if (this.findSubmotivo(submotivoExpediente.getSubmotivoxexpedientePK().getIdexpediente(),
                submotivoExpediente.getSubmotivoxexpedientePK().getIdsubmotivo()) != null) {
            throw new Exception("Ya se ha agregado el submotivo a la lista");
        }
        em.persist(submotivoExpediente);
        em.flush();
    }

    @Override
    public void retirarSuministro(Integer idSuministro) {
        Suministro s = (Suministro) em.createQuery("SELECT s from Suministro s "
                + "WHERE s.idsuministro = :idsuministro").setParameter("idsuministro", idSuministro).getSingleResult();
        em.remove(s);
        em.flush();
    }

    @Override
    public void agregarSuministro(Suministro suministro) throws Exception {
        if (this.findSuministro(suministro.getExpediente().getIdexpediente(),
                suministro.getNrosuministro()) != null) {
            throw new Exception("Ya se ha agregado el suministro a la lista");
        }
        em.persist(suministro);
        em.flush();
    }

    @Override
    public Expedientestor findByExpediente(Integer idExpediente) {
        try {
            String sql = "select o from Expedientestor o where o.idexpediente=?1";
            Query q = em.createQuery(sql);
            return (Expedientestor) q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    // //////////////////////
    // Getters and Setters
    // //////////////////////
    public EntityManager getEm() {
        return em;
    }

    @PersistenceContext(unitName = "sigedPU")
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
