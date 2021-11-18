package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Numeracion;
import com.btg.ositran.siged.domain.Unidad;

public interface NumeracionService {

   public Numeracion findById(Integer iIdUnidad, Integer iIdTipoDocumento);

   public Numeracion actualizarObj(Numeracion objNumeracion);

   public Numeracion guardarObj(Numeracion objNumeracion);

   public String guardarObjProcedure(Numeracion objNumeracion, int area_remitente, int idUsuario) throws Exception;

   public List<Numeracion> findAll();

   public List<Numeracion> findAllUnidadAndTipoDoc(Integer idunidad,Integer idtipodoc);

   public Numeracion findByIdbyUnidad(Unidad iIdUnidad, Integer iIdTipoDocumento);

   public Numeracion findByIdNumeracion(Integer idUnidad, Integer idTipoDocumento);
   
   public Numeracion findByIdNumeracionbyUnidadbyAnioFiscal(Unidad idUnidad, Integer IdTipoDocumento, Integer AnioFiscal);
}
