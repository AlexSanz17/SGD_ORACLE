/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.Serie;
import java.util.List;

/**
 *
 * @author consultor_jti15
 */
public interface SerieDAO {
    public List<Serie> getListSerie(String estado);
}
