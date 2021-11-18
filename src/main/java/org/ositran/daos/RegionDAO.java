/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Region;

/**
 *
 * @author Himizu
 */
public interface RegionDAO {

    public List<Region> findAll() ;

    public Region lisNomdiafestivo(String Nombreregion);

    public Region saveRegion(Region objRegion);

}
