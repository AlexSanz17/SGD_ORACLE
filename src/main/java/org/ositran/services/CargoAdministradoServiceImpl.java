/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import org.ositran.daos.CargoAdministradoDAO;
import com.btg.ositran.siged.domain.CargoAdministrado;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author consultor_jti15
 */

@Repository
public class CargoAdministradoServiceImpl implements CargoAdministradoService{
    private CargoAdministradoDAO cargoAdministradoDAO;
    
    public List<CargoAdministrado> findAllCargoAdministrado(){
    	return cargoAdministradoDAO.findAllCargoAdministrado();
    }
    
     public CargoAdministrado findCargoAdministrado(Integer idCargoAdministrado){
    	return cargoAdministradoDAO.findCargoAdministrado(idCargoAdministrado);
    }
     
     public List<CargoAdministrado> findCargoFiltroAdministrado(String desCargo){
         return cargoAdministradoDAO.findCargoFiltroAdministrado(desCargo);   
     }
    
    public CargoAdministradoDAO getCargoAdministradoDAO() {
        return cargoAdministradoDAO;
    }

    public void setCargoAdministradoDAO(CargoAdministradoDAO cargoAdministradoDAO) {
        this.cargoAdministradoDAO = cargoAdministradoDAO;
    }
}
