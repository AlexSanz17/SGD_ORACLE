package org.ositran.services;

import java.util.List;
import org.ositran.daos.ReemplazoDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Reemplazo;
import com.btg.ositran.siged.domain.Usuario;

public class ReemplazoServiceImpl implements ReemplazoService{

	private ReemplazoDAO reemplazoDao;
   private ProcesoService srvProceso;
   private UsuarioService srvUsuario;

	public List<Reemplazo> buscarPorUsuario(Usuario usuario){
		return null;
	}

	public List<Reemplazo> findAll(){
		return reemplazoDao.findAll();
	}

	public Reemplazo findByIdReemplazo(Integer idreemplazo){
		return reemplazoDao.findByIdReemplazo(idreemplazo);
	}

	@Transactional
	public Reemplazo saveReemplazo(Reemplazo objReemplazo){
		return reemplazoDao.saveReemplazo(objReemplazo);
	}

	public void setReemplazoDao(ReemplazoDAO reemplazoDao){
		this.reemplazoDao=reemplazoDao;
	}

	@Transactional
	public int deleteByIdreemplazado(Integer idusuario){
		return this.reemplazoDao.deleteByIdreemplazado(idusuario);
	}

	public Reemplazo findByUsuario(Integer idusuario){
		return this.reemplazoDao.findByUsuario(idusuario);
	}

	public List<Reemplazo> findByIdreemplazadoIdproceso(Integer idproceso,Integer idusuario){
		return reemplazoDao.findByIdreemplazadoIdproceso(idproceso,idusuario);
	}

	@Override
	public Reemplazo encontrarReemplazo(int proceso,int reemplazante,int reemplazado){
		return reemplazoDao.encontrarReemplazo(proceso,reemplazante,reemplazado);
	}

   public List<Reemplazo> findLstBy(Character cEstado) {
      return reemplazoDao.findLstBy(cEstado);
   }

   public ProcesoService getSrvProceso() {
      return srvProceso;
   }

   public void setSrvProceso(ProcesoService srvProceso) {
      this.srvProceso = srvProceso;
   }

   public UsuarioService getSrvUsuario() {
      return srvUsuario;
   }

   public void setSrvUsuario(UsuarioService srvUsuario) {
      this.srvUsuario = srvUsuario;
   }
}
