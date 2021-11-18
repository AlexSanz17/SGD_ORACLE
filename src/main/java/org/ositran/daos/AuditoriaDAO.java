/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Auditoria;

public interface AuditoriaDAO {

   public Auditoria SaveAuditoria(Auditoria objAuditoria);

   public List<Auditoria> findAvanced();

   public List<Auditoria> filtrarAuditoria(String sNroExpediente, String sUsuario, String sModulo, String sNroDocumento, String sFechaDesde, String sFechaHasta);
}
