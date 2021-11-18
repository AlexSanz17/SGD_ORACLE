package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Reemplazo;
import com.btg.ositran.siged.domain.Usuario;

public interface ReemplazoService{

	public List<Reemplazo> buscarPorUsuario(Usuario usuario);

	public List<Reemplazo> findAll();

	public Reemplazo findByIdReemplazo(Integer idreemplazo);

	public Reemplazo saveReemplazo(Reemplazo objReemplazo);

	public int deleteByIdreemplazado(Integer idusuario);

	public Reemplazo findByUsuario(Integer idusuario);

	public List<Reemplazo> findByIdreemplazadoIdproceso(Integer idproceso,Integer idusuario);
	
	public Reemplazo encontrarReemplazo(int proceso,int reemplazante,int reemplazado);

   public List<Reemplazo> findLstBy(Character cEstado);
}
