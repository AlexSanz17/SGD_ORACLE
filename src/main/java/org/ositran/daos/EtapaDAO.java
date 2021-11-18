/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Etapa;


public interface EtapaDAO {

   public List<Etapa> findAll();
   public Etapa findByIdetapa(int Idetapa);
   public Etapa findByCodigo(String codigo);
   public List<Etapa> getEtapaList(String descripcion, Integer duracion,String estado, String codigo);
   public void saveObject(Etapa etapa);
   public List<Etapa> findEtapabyProceso(Integer idProceso);
   
}
