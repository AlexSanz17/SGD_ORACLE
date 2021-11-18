/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Campo;
import com.btg.ositran.siged.domain.Plantilla;

/**
 *
 * @author Himizu 
 */
public interface PlantillaDAO {

   public List<Plantilla> findAll();

   public List<Campo> listCamposByTipoPlantilla(Integer tipoPlantilla);

   public List listSeccionesByTipoPlantilla(String tipoPlantilla);

   public Plantilla findByIdplantilla(Integer idplantilla);

   public Plantilla guardarObj(Plantilla objPlantilla);
}
