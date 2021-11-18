package org.ositran.services;

import java.util.ArrayList;
import java.util.List;
import org.ositran.siged.dto.ProcesoDTO;

import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Usuario;

public interface ProcesoService {

   public List<Proceso> buscarLstPor(Integer iIdResponsable);

   public List<Proceso> findByIdResponsableOrAsistente(Integer iIdUsuario, char cEstado);

   public List<Proceso> getProcesoList(String sTipoProceso);

   public List<Proceso> getProcesosActivos()throws RuntimeException;

   public Proceso findByIdProceso(Integer iId) throws RuntimeException;

   public void saveProceso(Proceso objProcesoOld, Proceso objProcesoNew, List<Integer> lstIdParticipante, List<Integer> lstIdLista, List<Integer> lstIdParticipanteConfidencial, List<Integer> lstIdNotificado, String sUsuarioSesion, String sTipoAuditoria);

   public ArrayList<Object> cargaUsuarioMasivo(Integer[] var) throws RuntimeException;

   public List<Proceso> buscarProcesosxUsuarioParticipante(Usuario usuario);

   public List<ProcesoDTO> buscarProcesosDTOxUsuarioParticipante(Usuario usuario);

   public void updateEstadoProceso(Integer idProceso,Character estado);

   public List<Proceso> getProcesoListxOrigen(String origen) ;

   public List<Proceso> getProcesoListxOrigen(Integer iIdUsuario,String origen);

   public List<Proceso> getProcesosPorTipoProcesoYestado(String sTipoProceso, Character cEstado);

   public List<Proceso> findLstBy(Usuario objParticipante);

   public List<Proceso> getLstAutoComplete();

   public Proceso getProcesoxId(Integer idProceso);

   public List<Proceso> getProcesosActivosSAS() throws RuntimeException ;

   public List<Proceso> findPorcentajesProcesos();

   public Proceso findObjectBy(String nombre, Character estado);

   List<ProcesoDTO> buscarProcesosActivosResumen();

   public List<Proceso> findbyProcesosStor();

   public List<ProcesoDTO> buscarProcesosDTOxUsuarioParticipanteN2(Usuario usuario) ;

   public List<Proceso> getLstAutoCompleteVigente();
}
