/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.common.service;

import java.util.List;
import java.util.Collection;
import java.util.Date;

/**
 * @author Victor Soria
 *
 */
public interface GenericService {
	
	/**
	 * 
	 * Method to save or update collection object in database,
	 * this method insert in database object if this
	 * object have null id else update from id object 
	 * 
	 * @param object
	 */
    public abstract void save(Collection<?> collection) throws Exception;
	
	/**
	 * 
	 * Method to save or update object in database,
	 * this method insert in database object if this
	 * object have null id else update from id object 
	 * 
	 * @param object
	 */
    public abstract void save(Object object) throws Exception;
    
    /**
     * 
     * Method to delete object from database,
     * this method delete object from id
     * 
     * @param object
     */
    public abstract void delete(Object object) throws Exception;
    
    /**
     * 
     * Method to delete object from database,
     * this method delete object using id
     * 
     * @param object
     */
    public abstract void deleteById(Class<?> clazz, String id) throws Exception;
    
    /**
     * 
     * Method to find object in database from id
     * 
     * @param clazz
     * @param id
     * @return Object
     */
    public abstract Object findById(Class<?> clazz, String id) throws Exception;
    
    /**
     * 
     * Method to find object in database from every attribute
     * filled and return object if uniqueResult is true else
     * return list
     * 
     * @param object
     * @param uniqueResult
     * @return Object
     */
    public abstract Object findByObject(Object object, boolean uniqueResult) throws Exception;
    
    /**
     * 
     * Method to verify if object exist in database from id
     * 
     * @param clazz
     * @param id
     * @return
     */
    public abstract boolean exists(Class<?> clazz, String id) throws Exception;
    
    
    /**
     * 
     * Method to get database time
     * 
     * @return Date
     */
    public abstract Date getDataBaseDate() throws Exception;
    
    public List<?> getFindCriteria(Object object) throws Exception;
    
}