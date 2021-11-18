/*LICENCIA DE USO DEL SGD .TXT*/package gob.ositran.siged.service.impl;

import gob.ositran.siged.service.CrudService;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrudServiceImpl implements CrudService {

    private static Logger log = LoggerFactory.getLogger(CrudServiceImpl.class);
    private EntityManager em;

    //////////////////////////////////
    // Getters and Setters          //
    //////////////////////////////////
    public EntityManager getEm() {
        return em;
    }

    @PersistenceContext(unitName = "sigedPU")
    public void setEm(EntityManager em) {
        this.em = em;
    }

    private Query createNamedQuery(String namedQueryName) {
        return this.em.createNamedQuery(namedQueryName);
    }

    private Query createQuery(String query) {
        return this.em.createQuery(query);
    }

    @Override
    public <T> T create(T t) {
        log.info("inserting " + t.getClass().getName());
        this.em.persist(t);
        this.em.flush();
        this.em.refresh(t);
        return t;
    }

    @Override
    public <T> T refresh(T t) {
        this.em.refresh(t);
        return t;
    }

    @Override
    public <T> T find(Object id, Class<T> type) {
        log.info("finding " + type.getName() + " by id=" + id.toString());
        return (T) this.em.find(type, id);
    }

    @Override
    public void delete(Object t) {
        log.info("deleting " + t.getClass().getName());
        Object ref = this.em.getReference(t.getClass(), t);
        this.em.remove(ref);
    }

    @Override
    public <T> T update(T t) {
        log.info("updating " + t.getClass().getName());
        return (T) this.em.merge(t);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Object> findByNamedQuery(String namedQueryName) {
        log.info("finding by named query :" + namedQueryName);
        return createNamedQuery(namedQueryName).getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Object> findByQuery(String sqlQuery) {
        log.info("finding by query :" + sqlQuery);
        return createQuery(sqlQuery).getResultList();
    }

    @Override
    public List<Object> findByNamedQuery(String namedQueryName,
            Map<String, Object> parameters) {

        return findByNamedQuery(namedQueryName, parameters, 0);
    }

    @Override
    public List<Object> findByQuery(String sqlQuery,
            Map<String, Object> parameters) {

        return findByQuery(sqlQuery, parameters, 0);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Object> findByNamedQuery(String namedQueryName, int resultLimit) {
        log.info("finding by named query :" + namedQueryName);
        return createNamedQuery(namedQueryName).
                setMaxResults(resultLimit).
                getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Object> findByQuery(String sqlQuery, int resultLimit) {
        log.info("finding by query :" + sqlQuery);
        return createNamedQuery(sqlQuery).
                setMaxResults(resultLimit).
                getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Object> findByNamedQuery(String namedQueryName,
            Map<String, Object> parameters, int resultLimit) {
        Set<Entry<String, Object>> rawParameters = parameters.entrySet();
        log.info("finding by named query :" + namedQueryName);
        Query query = createNamedQuery(namedQueryName);
        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }
        for (Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Object> findByQuery(String sqlQuery,
            Map<String, Object> parameters, int resultLimit) {
        Set<Entry<String, Object>> rawParameters = parameters.entrySet();
        log.info("finding by query :" + sqlQuery);
        Query query = createNamedQuery(sqlQuery);
        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }
        for (Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    @Override
    public int executeNamedQuery(String namedQueryName) {
        log.info("executing named query :" + namedQueryName);
        Query query = createNamedQuery(namedQueryName);
        return query.executeUpdate();
    }

    @Override
    public int executeNamedQuery(String namedQueryName, Map<String, Object> parameters) {
        Set<Entry<String, Object>> rawParameters = parameters.entrySet();
        log.info("executing named query :" + namedQueryName);
        Query query = createNamedQuery(namedQueryName);
        for (Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.executeUpdate();
    }

    @Override
    public List<Object> findByNativeQuery(String nativeQuery, Map<String, Object> parameters) {
        return findByNativeQuery(nativeQuery, parameters, 0);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Object> findByNativeQuery(String nativeQuery, Map<String, Object> parameters, int resultLimit) {
        Set<Entry<String, Object>> rawParameters = parameters.entrySet();
        log.info("finding by natived query :" + nativeQuery);
        Query query = this.em.createNativeQuery(nativeQuery);
        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }
        for (Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }
}
