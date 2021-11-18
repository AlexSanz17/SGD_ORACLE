package org.ositran.services;

import java.util.List;
import java.util.Map;

import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.grid.Estructura;
import org.ositran.dojo.grid.GridUsuario;
import org.ositran.dojo.grid.Item;

import com.btg.ositran.siged.domain.GridXGridColumna;
import com.btg.ositran.siged.domain.Gridcolumnaxusuario;
import com.btg.ositran.siged.domain.Usuario;

public interface GridcolumnaxusuarioService {

   public List<Estructura> buildEstructura(List<Gridcolumnaxusuario> lstGridColumnaXUsuario, List<GridXGridColumna> lstGridColumna, Integer iIdUsuario);

   public List<Item> buildItems(List<Estructura> lstEstructura, Usuario objUsuario, Integer iTipoGrid);

   public List<Item> convertFromDocumentoToItem(List<Estructura> lstEstructura, Usuario objUsuario);

   public List<Gridcolumnaxusuario> findByIdUsuario(Integer iIdUsuario);

   public void guardarGridUsuario(GridUsuario[] columnas, int idUsuario);

   // ////nuevo!
   public List<Item> convertFromCarpetaBusquedaToItem(Integer iIdCarpetaBusqueda);

   public Map<String, List<Estructura>> getStructuresByRol(Usuario objUsuario);

   public List<Estructura> getEstructura(String sTipoGrid, Usuario objUsuario);

   public List<Item> getItems(String sTipoGrid, Usuario objUsuario, BusquedaAvanzada objFiltro);

   public List getItems_Documento(Usuario objUsuario);

   public List<Item> getItems_ArchivoPendiente(Usuario objUsuario);

   public List<Item> getItems_DocumentoEnviado(Usuario objUsuario, BusquedaAvanzada objFiltro);

   public List<Item> getItems_Mensajeria(Usuario objUsuario);

   public List<Item> getItems_Notificacion(Usuario objUsuario);

   public List<Item> getItems_Proceso(Usuario objUsuario);

   public List<Item> getItems_Expediente(Usuario objUsuario);

   public List<Item> getItems_Informativo(Usuario objUsuario, BusquedaAvanzada objFiltro);

   public List<Item> getItems_Reemplazo(Usuario objUsuario);

   public List<Item> getItems_MantReemplazo();

   public List<Item> getItems_BusquedaSimple(String sParametroBusqueda, Usuario usuario);

   public List<Item> getItems_BusquedaAvanzada(String sTipoFiltro, BusquedaAvanzada objFiltro, String arrFecha[], String sqlQueryDinamico[], String tipoConsulta, String unidadUsuario, String cargoUsuario);

   public List<Item> getCamposPorPlantilla(Integer iTipoPlantilla);

   public List<Item> getItems_Lista(Usuario objUsuario);

   public List getBandejaDocUsuarioFinal(Usuario objUsuario, String enviados, BusquedaAvanzada objFiltro);

   public List getItemsSinBandejaCompartida(Usuario objUsuario);

   List getItemsDocumentosXExpediente(String sTipoGrid, Integer idDocumento, Usuario objUsuario);

   public List getBandejaDocUsuarioFinalFiltro(Usuario objUsuario, String enviados, BusquedaAvanzada objFiltro);
   
   public List getItems_Firmar(Usuario objUsuario, String bandeja, BusquedaAvanzada objFiltro);
    
   public List getItems_Recepcion_Virtual(Usuario objUsuario, BusquedaAvanzada objFiltro);
   
   public List getItems_Despacho_Virtual(Usuario objUsuario, BusquedaAvanzada objFiltro);
   
   public List getBandejaLegajo(Usuario objUsuario, BusquedaAvanzada objFiltro);
   
   public List getBandejaLegajoCompartido(Usuario objUsuario, BusquedaAvanzada objFiltro);

}