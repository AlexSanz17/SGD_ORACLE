/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Auditable;
import com.btg.ositran.siged.domain.Auditoria;

public interface AuditoriaService {

   public void aplicarAuditoria(Auditable objOld, Auditable objNew, String sResponsable, String sOpcion, String sTipoAuditoria) throws ClassNotFoundException;

   public void registrarAuditoria(String sModulo, String sOpcion, String sCampo, String sOld, String sNew, String sUsuario, String sTipoAuditoria);

   public Auditoria SaveAuditoria(Auditoria ObjAuditoria);

   public List<Auditoria> filtrarAuditoria(String sNroExpediente, String sUsuario, String sModulo, String sNroDocumento, String sFechaDesde, String sFechaHasta);
}
