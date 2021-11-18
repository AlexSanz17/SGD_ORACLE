/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Trazabilidadexpediente;

public interface TrazabilidadexpedienteDAO {

   public Trazabilidadexpediente findByMaxNroRegistro(Integer iIdExpediente);

   public Trazabilidadexpediente findByNroRegistroByExpediente(Integer idExpediente, Integer nroRegistro);

   public Trazabilidadexpediente saveTrazabilidadExpediente(Trazabilidadexpediente objTrazaExpediente);

   public Trazabilidadexpediente save(Trazabilidadexpediente trazabilidadexpediente);

   public List findTrazabilidadXUsuarioxExp(int remitente, int destinatario, int idexpediente, int etapa);

   public Trazabilidadexpediente findByIdtrazabilidadexpediente(int idtrazabilidadexpediente);
   
   public List<Trazabilidadexpediente>  findByIdexpediente(Integer iIdExpediente);
      
}
