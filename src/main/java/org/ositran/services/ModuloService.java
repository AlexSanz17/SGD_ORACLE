package org.ositran.services;

import java.util.List;
import java.util.Map;

import com.btg.ositran.siged.domain.Modulo;

public interface ModuloService {

   public List<Modulo> findAll();
   
   public List<Modulo> findModulosActivos();

   public Map<Integer,String> findMapAll();

   public Modulo findByIdModulo(Integer iIdModulo);

   public void saveModulo(Modulo objModuloOld, Modulo objModuloNew, String sUsuarioSesion, String sTipoAuditoria);
   
   public void updateEstadoModulo(Integer IdModulo, Character estado);
   
}
