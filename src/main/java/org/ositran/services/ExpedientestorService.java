package org.ositran.services;

import java.util.List;

import org.ositran.dojo.grid.Item;
import org.ositran.utils.ExpedientebusStor;

import com.btg.ositran.siged.domain.Expedientestor;
import com.btg.ositran.siged.domain.SubmotivoXExpedienteStor;
import com.btg.ositran.siged.domain.Suministro;

public interface ExpedientestorService {

    public List<ExpedientebusStor> findSimple(String Condicion);

    public List<ExpedientebusStor> finavanzada(String experiente, String sel1, String sumnistro, String sel2, String proce, String sel3, String concesio);

    public Expedientestor findByIdExpediente(Integer iIdExp);

    public Expedientestor saveExpedientestor(Expedientestor expedientestor);

    List<Item> getItemSubmotivos(Integer idExpediente);

    void retirarSubmotivo(Integer idSubmotivo, Integer idExpediente);

    void agregarSubmotivo(SubmotivoXExpedienteStor submotivoExpediente) throws Exception;

    public void saveExpedienteStor(Expedientestor objES);

    public void updateExpedienteStor(Expedientestor objES);

    public List<Item> getItemSuministros(Integer idExpediente);

    public void retirarSuministros(Integer idSuministro);

    public void agregarSuministro(Suministro suministro) throws Exception;
}
