package org.ositran.services;

import java.util.List;
import java.util.Map;

import com.btg.ositran.siged.domain.Lista;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Usuario;

public interface ListaService {

   public Lista findByIdLista(Integer iIdLista);

   public Lista guardarObj(Lista objLista, List<Integer> lstIdAdministrador, List<Integer> lstIdParticipante);

   public List<Lista> findLstBy(Usuario objAdministrador, Character cEstado);

   public List<Lista> findLstBy(Character cEstado);

   public List<Lista> findLstBy(Integer iIdPropietario);

   public List<Lista> findLstByEstadoExcludedByProceso(Character cEstado, Proceso objProceso);

   public Map<Integer, String> convertFromLstToMap(List<Lista> lstLista);

   public void deleteLista(Integer iIdLista);

   public void saveLista(Lista objListaOld, Lista objListaNew, List<Integer> lstIdContacto, String sUsuarioSesion, String sTipoAuditoria);
}
