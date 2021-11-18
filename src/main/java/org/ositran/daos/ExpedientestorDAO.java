/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import org.ositran.utils.ExpedientebusStor;

import com.btg.ositran.siged.domain.Expedientestor;
import com.btg.ositran.siged.domain.SubmotivoXExpedienteStor;
import com.btg.ositran.siged.domain.Suministro;

public interface ExpedientestorDAO {

    public Expedientestor findByIdExpediente(Integer iIdExp);

    public Expedientestor saveExpedienteStor(Expedientestor objES);

    public void updateExpedienteStor(Expedientestor objES);

    public List<ExpedientebusStor> findbycondicion(String condicion);

    public List<SubmotivoXExpedienteStor> findSubmotivo(Integer idExpediente);

    public void retirarSubmotivo(Integer idSubmotivo, Integer idExpediente);

    public void agregarSubmotivo(SubmotivoXExpedienteStor submotivoExpediente) throws Exception;

    public SubmotivoXExpedienteStor findSubmotivo(Integer idExpediente, Integer idSubmotivo);

    public Expedientestor findByExpediente(Integer idExpediente);

    public List<Suministro> findSuministros(Integer idExpediente);

    public Suministro findSuministro(Integer idExpediente, String nroSuministro);

    public void retirarSuministro(Integer idSuministro);

    public void agregarSuministro(Suministro suministro) throws Exception;
}
