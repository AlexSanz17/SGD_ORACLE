/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Mensajeria;
import com.btg.ositran.siged.domain.Usuario;

public interface MensajeriaDAO {

   public List<Mensajeria> findEstado(String Est);

   public Mensajeria findId(int Id);

   public Mensajeria guardarObj(Mensajeria objMensajeria);

   public List<Mensajeria> FindusuarioMensajeria(int idusuario);

   public List<Mensajeria> filtrarCriterios(Usuario objUsuario, String fechaDesde, String fechaHasta, String horaDesde, String horaHasta);
}
