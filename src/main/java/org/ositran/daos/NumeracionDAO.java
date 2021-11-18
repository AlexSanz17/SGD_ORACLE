/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.sql.SQLException;
import java.util.List;

import com.btg.ositran.siged.domain.Numeracion;
import com.btg.ositran.siged.domain.Unidad;

public interface NumeracionDAO {

   public Numeracion findByIdNumeracion(Integer idUnidad, Integer IdTipoDocumento);

   public Numeracion actualizarObj(Numeracion objNumeracion);

   public Numeracion guardarObj(Numeracion objNumeracion);

   public String guardarObjProcedure(Numeracion objNumeracion, int area_remitente, int usuario) throws Exception;

   public List<Numeracion> findAll();

   public List<Numeracion> findAllUnidadAndTipoDoc(Integer idunidad,Integer idtipodoc);

   public Numeracion findByIdNumeracionbyUnidad(Unidad iIdUnidad, Integer iIdTipoDocumento) ;
   
   public List<Numeracion> findAllUnidadAndTipoDocAndAnioFiscal(Integer idunidad, Integer idtipodoc, Integer aniofiscal);
   
   public Numeracion findByIdNumeracionbyUnidadbyAnioFiscal(Unidad idUnidad, Integer IdTipoDocumento, Integer AnioFiscal);

}
