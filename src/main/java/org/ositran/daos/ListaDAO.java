/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Lista;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Usuario;

public interface ListaDAO {

   public Lista findObjById(Integer iIdLista);

   public Lista guardarObj(Lista objLista);

   public Lista saveLista(Lista objLista);

   public Lista updateLista(Lista objLista);

   public List<Lista> findLstBy(Usuario objAdministrador, Character cEstado);

   public List<Lista> findLstBy(Character cEstado);

   public List<Lista> findLstBy(Integer iIdPropietario);

   public List<Lista> findLstByEstadoExcludedByProceso(Character cEstado, Proceso objProceso);

   public void deleteLista(Lista objLista);
}
