/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Parametro;

/**
 *
 * @author Himizu
 */
public interface ParametroService {
	public List<Parametro> findAll() ;
        public Parametro findObjById(Integer iIdParametro);
	public Parametro guardarObj(Parametro objParametro);
	public List<Parametro> findByTipo(String tipo );
        public List<Parametro> findByTipoActivo(String tipo);
	public Parametro findByTipoUnico(String tipo);
	public List<Parametro> getEstados() ;
	public List<Parametro> getProceso();
	public List<Parametro> getGrupoProceso(); 
	public List<Parametro> getTipoExpediente();
	public List<Parametro> getSalaAYQ();
	public List<Parametro> getResponsableAYQ();
	public List<Parametro> getAnalistaAYQ();
	public List<Parametro> getPrioridades();
	public List<Parametro> findAll(Integer iWithoutStor);
        public Parametro findByTipoAndValue(String tipo, String value) ;
}
