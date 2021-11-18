package org.ositran.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.btg.ositran.siged.domain.FilaSeguimiento;
import com.btg.ositran.siged.domain.SeguimientoHoraDia;
import com.btg.ositran.siged.domain.SeguimientoHoraSemana;
import com.btg.ositran.siged.domain.Alerta;

public interface SeguimientoService {

   public List<SeguimientoHoraDia> getListaDocumentosPendientesByDia(Timestamp fecha, Integer idusuario, String filtro1, Integer iIdProceso);

   public List<FilaSeguimiento> getListaDocumentosPendientesBySemana(Date fechaInicio, Date fechaFin, Integer idusuario, String filtro1, Integer iIdProceso, Boolean propios);

   public Date[] getSemana(Date actual);

   public List<Alerta> getListaAlerta(Integer idUsuario, Integer idUnidad, String orden);
   /**
    * INICIO: JOSE ZENTENO
    */
   public List<SeguimientoHoraSemana> getListaDocumentosPendientesBySemanaFiltros(Timestamp fecha, Integer idusuario, Integer idProceso, boolean todosUsuarios);
   /**
    * FIN: JOSE ZENTENO
    */
}
