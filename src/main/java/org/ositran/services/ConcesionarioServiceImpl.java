/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.ositran.daos.ConcesionarioDAO;

import com.btg.ositran.siged.domain.Concesionario;

public class ConcesionarioServiceImpl implements ConcesionarioService {

    private static Logger log = Logger.getLogger("org.ositran.services.ConcesionarioServiceImpl");
    private ConcesionarioDAO dao;

    public ConcesionarioServiceImpl(ConcesionarioDAO dao) {
        setDao(dao);
    }

    public Concesionario findByIdConcesionario(Integer iId) throws RuntimeException {
        try {
            return getDao().findByIdConcesionario(iId);
        } catch (RuntimeException re) {
            log.error("", re);

            return null;
        }
    }

    public Map<Integer, String> getConcesionarioList() throws RuntimeException {
        try {
            Map<Integer, String> mapAux = new HashMap<Integer, String>();
            List<Concesionario> lstC = getDao().findAll();

            for (Concesionario objC : lstC) {
                mapAux.put(objC.getIdConcesionario(), objC.getRazonSocial());
            }

            return mapAux;
        } catch (RuntimeException re) {
            log.error("", re);

            return null;
        }
    }

    public Concesionario findByRUC(String strRUC) {
        try {
            return getDao().findByRUC(strRUC);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    public List<Concesionario> findAll() {
        try {
            return getDao().findAll();

        } catch (RuntimeException re) {
            log.error("", re);

            return null;
        }
    }

    ////////////////////////
    // Getters and Setters
    ////////////////////////
    public ConcesionarioDAO getDao() {
        return dao;
    }

    public void setDao(ConcesionarioDAO dao) {
        this.dao = dao;
    }
}
