package org.ositran.services;

import java.util.List;
import org.ositran.daos.ParametroDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Parametro;

public class ParametroServiceImpl implements ParametroService{
	
	private ParametroDAO dao;

	public ParametroServiceImpl(){
		setDao(dao);
	}

	public ParametroServiceImpl(ParametroDAO dao){
		setDao(dao);
	}

	public List<Parametro> findAll(){
		return this.dao.findAll();
	}

   public Parametro findObjById(Integer iIdParametro) {
      return dao.findObjById(iIdParametro);
   }

   @Transactional
   public Parametro guardarObj(Parametro objParametro) {
      return dao.guardarObj(objParametro);
   }

       public Parametro findByTipoAndValue(String tipo, String value) {
           return this.dao.findByTipoAndValue(tipo, value);
       }
   
	public List<Parametro> findByTipo(String tipo){
		return this.dao.findByTipo(tipo);
	}
        
        public List<Parametro> findByTipoActivo(String tipo){
		return this.dao.findByTipoActivo(tipo);
	}

	public ParametroDAO getDao(){
		return dao;
	}

	public void setDao(ParametroDAO dao){
		this.dao=dao;
	}

	@Override
	public Parametro findByTipoUnico(String tipo){
		List<Parametro> todos=findByTipo(tipo);
		if(todos!=null && todos.size()==1){
			return todos.get(0);
		}
		return null;
	}
	
	public List<Parametro> getEstados()  {
		
		return  dao.getEstados();
	}
	public List<Parametro> getProceso() {
		
		return dao.getProceso(); 
	}
	public List<Parametro> getGrupoProceso() {
		
		return dao.getGrupoProceso();
	} 
	
	public List<Parametro> getTipoExpediente(){
		return dao.getTipoExpediente();
	}
	
	public List<Parametro> getSalaAYQ(){
		return dao.getSalaAYQ();
	}
	public List<Parametro> getResponsableAYQ(){
		return dao.getResponsableAYQ();
	}
	public List<Parametro> getAnalistaAYQ(){
		return dao.getAnalistaAYQ();
	}

	@Override
	public List<Parametro> getPrioridades() {
		return dao.getPrioridades();
	}

	@Override
	public List<Parametro> findAll(Integer iWithoutStor) {
		if (iWithoutStor == null) {
	         return dao.findAll();
	      } else {
	         return dao.findAllWithoutStor();
	      }
	}
}
