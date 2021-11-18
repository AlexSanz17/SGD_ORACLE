/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.common.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.ositran.common.dao.GenericDao;
import org.ositran.common.service.GenericService;

public class GenericServiceImpl implements GenericService {
	
	private GenericDao dao;
	
	public void delete(Object object) throws Exception {
		getDao().delete(object);
	}
	
	public void deleteById(Class<?> clazz, String id) throws Exception {
		getDao().deleteById(clazz, id);
	}

	public boolean exists(Class<?> clazz, String id) throws Exception {
		return getDao().exists(clazz, id);
	}

	public Object findById(Class<?> clazz, String id) throws Exception {
		return getDao().findById(clazz, id);
	}

	public Object findByObject(Object object, boolean uniqueResult)
			throws Exception {
		return getDao().findByObject(object, uniqueResult);
	}

	public Date getDataBaseDate() throws Exception {
		return getDao().getDataBaseDate();
	}

	public void save(Object object) throws Exception {
		getDao().save(object);
	}
	
	public void save(Collection<?> collection) throws Exception {
		getDao().save(collection);
	}

	public GenericDao getDao() {
		return dao;
	}

	public void setDao(GenericDao dao) {
		this.dao = dao;
	}
	
	 public List<?> getFindCriteria(Object object) throws Exception{
	        List<?> lista = getDao().getFindCriteria(object);
	        return lista;
	    }
	
}