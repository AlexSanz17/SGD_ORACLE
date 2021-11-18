/*LICENCIA DE USO DEL SGD .TXT*/package gob.ositran.siged.service;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface CrudService {

    <T> T create(T t);

    <T> T refresh(T t);

    <T> T find(Object id, Class<T> type);

    <T> T update(T t);

    void delete(Object t);

    int executeNamedQuery(String queryName);

    int executeNamedQuery(String queryName, Map<String, Object> parameters);
    
	List findByNamedQuery(String queryName);

    List findByNamedQuery(String queryName, int resultLimit);

    List findByNamedQuery(String namedQueryName, Map<String, Object> parameters);

    List findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

    List findByQuery(String sqlQuery);

    List findByQuery(String sqlQuery, int resultLimit);

    List findByQuery(String sqlQuery, Map<String, Object> parameters);

    List findByQuery(String sqlQuery, Map<String, Object> parameters, int resultLimit);

    List findByNativeQuery(String nativeQuery, Map<String, Object> parameters);

    List<Object> findByNativeQuery(String nativeQuery, Map<String, Object> parameters, int resultLimit);
}
