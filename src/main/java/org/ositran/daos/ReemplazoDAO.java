/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Reemplazo;

public interface ReemplazoDAO{

	public List<Reemplazo> findAll();

	public Reemplazo findByIdReemplazo(Integer idreemplazo);

	public Reemplazo saveReemplazo(Reemplazo objReemplazo);

	public Reemplazo findByUsuario(Integer idusuario);

	public List<Reemplazo> findByIdreemplazadoIdproceso(Integer idproceso,Integer idusuario);

	public int deleteByIdreemplazado(Integer idusuario);
	
	public Reemplazo encontrarReemplazo(int proceso,int reemplazante,int reemplazado);

   public List<Reemplazo> findLstBy(Character cEstado);
}
