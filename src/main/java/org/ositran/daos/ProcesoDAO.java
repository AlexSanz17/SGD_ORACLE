/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.ArrayList;
import java.util.List;

import org.ositran.siged.dto.ProcesoDTO;

import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Usuario;

public interface ProcesoDAO {

   public List<Proceso> bsucarLstPor(Integer iIdResponsable);

   public List<Proceso> findAll();

   public List<Proceso> findAllBy(Integer iIdTipoProceso);

   public List<Proceso> findByIdResponsableOrAsistente(Integer iIdUsuario, char cEstado);

   public Proceso findByIdProceso(Integer iIdProceso);

   public List<Proceso> findByIdProcesoList(Integer iIdProceso);

   public Proceso saveProceso(Proceso objProceso);

   public void deleteProceso(Proceso objProceso);

   public ArrayList<Object> cargaUsuarioMasivo(Integer[] var);

   public List<Proceso> buscarProcesosxUsuarioParticipanteN2(Usuario usuario);

   public List<Proceso> buscarProcesosxUsuarioParticipante(Usuario usuario);

   public List<Proceso> buscarProcesosActivos();

   public List<Proceso> buscarProcesosSancionadorActivos();

   public List<Proceso> findAllByTypes(List <Parametro> Parameters) ;

   public List<Proceso> findByProcesoOrIdResponsable_Asistente(Integer iIdUsuario,List <Parametro> Parameters, char cEstado);

   public List<Proceso> findAllByTipoProcesoAndEstado(String sTipoProceso, Character cEstado);

   public List<Proceso> findLstBy(Usuario objParticipante);

   public List<Proceso> getLstAutoComplete();

   public List<Proceso> findPorcentajesProcesos();

   public Proceso findName(String nombreProceso);

   public Proceso findObjectBy(String nombre, Character estado);

   public Proceso getProcesoxId(Integer idProceso);

   List<ProcesoDTO> buscarProcesosActivosResumen();

   public List<Proceso> findbyProcesosStor();

   public List<Proceso> getLstAutoCompleteVigente();
}
