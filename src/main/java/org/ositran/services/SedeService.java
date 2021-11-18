package org.ositran.services;

import java.util.List;
import java.util.Map;

import com.btg.ositran.siged.domain.Sede;

public interface SedeService {

   public List<Sede> obtenerTodo();

   public Map<Integer,String> obtenerMapTodo();

   public Sede buscarPorId(Integer iIdSede);

   public void guardarObj(Sede objSedeOld, Sede objSedeNew, String sUsuarioSesion, String sTipoAuditoria);
}
