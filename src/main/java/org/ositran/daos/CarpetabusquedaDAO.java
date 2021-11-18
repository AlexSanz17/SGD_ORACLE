/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import org.ositran.utils.Buscarpeta;
import org.ositran.utils.Expedienfindadvance;

import com.btg.ositran.siged.domain.CarpetaBusqueda;

public interface CarpetabusquedaDAO {

   public List<CarpetaBusqueda> findAll();

   public CarpetaBusqueda saveCarpetaBuqueda(CarpetaBusqueda objCB);

   public CarpetaBusqueda findByIdcarpetabusqueda(int IdCarpetabusqueda);

   public List<Expedienfindadvance> findbycondicion(String condicion, java.util.Date fechainidoc, java.util.Date fechafindoc, java.util.Date fechainiexp, java.util.Date fechafinexp);

   public List<Buscarpeta> findbycondicion2(String condicion);

   public void deleteCarpetabusqueda(CarpetaBusqueda objcarbusq);

   public List<CarpetaBusqueda> buscarLstPor(Integer iIdUsuario);
}
