/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import com.btg.ositran.siged.domain.CargoAdministrado;
import java.util.List;

/**
 *
 * @author consultor_jti15
 */
public interface CargoAdministradoService {
     public List<CargoAdministrado> findAllCargoAdministrado();
     public List<CargoAdministrado> findCargoFiltroAdministrado(String desCargo);
     public CargoAdministrado findCargoAdministrado(Integer idCargoAdministrado);
}
