/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.Date;
import java.util.List;
import org.ositran.utils.Busdiafestivo;

import com.btg.ositran.siged.domain.DiaFestivo;


public interface DiafestivoService {

   public List<DiaFestivo> ViewAll();

   public String saveDiafestivo(String strregion,String stranio,String strmes,String fech,String Strmotivo, Integer iIdMes,String sUsuarioSesion);

   public List<Busdiafestivo> findlisdiafestivo(String Nomregion);

   public void deleteDiafestivo(int iddiafestivo);

   public List<DiaFestivo> getDiasFestivosPorUsuario();

   public List<Date> getDiasFeriados(Date fechaInicio, Date fechaFin, String idRegion);

   public long tiempoTranscurrido(Date fechaDesde, Date fechaHasta, String nomreg);

   public void setIntervaloFeriados(Date fechaDesde, Date fechaHasta, String nomreg);
}
