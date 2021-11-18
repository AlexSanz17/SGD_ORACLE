/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;


import java.util.Date;
import java.util.List;

import org.ositran.utils.Busdiafestivo;

import com.btg.ositran.siged.domain.DiaFestivo;


public interface DiafestivoDAO {

   public List<DiaFestivo> findAll();

   public DiaFestivo saveDiafestivo(DiaFestivo objDf);

   public List<Busdiafestivo> findlisdiafestivo(String Nomregion);

   public List<Busdiafestivo> findlisdiafxnomregion(String Nomregion,String fecha);

   public List<Busdiafestivo> lisdaysNoLaborable(String Nomregion);

   public void deleteDiafestivo(int iddiafestivo);

   public DiaFestivo findxfecha(String nomreg, Date fecha);

   public List<DiaFestivo> findxnombreregion(String nomreg);

   public List<DiaFestivo> getDiasFestivosPorUsuario();

   public List<Date> findIntervloXRegion(Date desde,Date hasta, String nomreg);

   public List<Date> findFeriadosXRegion(Date desde,Date hasta, String idreg);
}
