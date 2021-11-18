/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;
import org.ositran.dojo.tree.Nodo;
import org.ositran.utils.Expedienfindadvance;

import com.btg.ositran.siged.domain.CarpetaBusqueda;
import com.btg.ositran.siged.domain.Documento;

public interface CarpetabusquedaService {

   public List<CarpetaBusqueda> ViewAll();

   public CarpetaBusqueda ViewbyId(int IdCarpetabusqueda);

   public List<Expedienfindadvance> findbyAllid(int Strid);

   public void saveCarpetaBusqueda(CarpetaBusqueda objCB);

   public void eliminarCarpetabusqueda(CarpetaBusqueda objCB);

   public List<CarpetaBusqueda> buscarLstPor(Integer iIdUsuario);

   public List<Nodo> getTreeCarpetaBusqueda(Integer iIdUsuario);

   public List<Documento> findLstByFiltro(Integer iIdCarpetaBusqueda);
}
