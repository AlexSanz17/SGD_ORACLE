/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import org.ositran.daos.SearchMode;
import org.ositran.dojo.grid.Item;
import org.ositran.pojos.ArchivoVersionado;
import org.ositran.utils.ArchivoTemporal;

import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Usuario;

public interface ArchivoService {
    
   public List<String> contarArchivosxFirmar(Integer idDocumento , Usuario usuario); 
   public List<Archivo> findArchivosxFirmar(Integer idDocumento , Usuario usuario);
   public Archivo findByCriteria(Integer iIdDoc, String strNombre) throws RuntimeException;
   public List<Archivo> buscarArchivosObjectId(String objectId, Integer nroTramite);
   public List<Archivo> buscarDocumentosPublicar(String nroTramite);
   public List<Archivo> buscarArchivoExterno(String objectId, Integer nroTramite, String clave);
   public List<Archivo> findArchivoPrincipalFirmardo(Integer idDocumento , Usuario usuario);
   public Archivo findById(Integer idArchivo);
   List<Archivo> buscarPorAutor(Integer idAutor, Integer idDocumento);
   List<Item> buscarItemArchivoXAutor(Integer idAutor, Integer idDocumento);
   List<Item> buscarItemArchivoXAutor(Usuario usuario, Integer idDocumento);
   Long buscarArchivosPorRuta(String ruta, Usuario usuario);
   public List<Archivo> buscarArchivosPorRutaDocumento(Integer idDocumento, String nombre);
   void eliminarArchivo(Integer idArchivo, Usuario usuario);
   public Archivo saveArchivo(Archivo objA) throws RuntimeException;
   public List<Archivo> findArchivoTipoFirmardo(Integer idDocumento , char tipoArchivo, String tipoFirma);
   public File getFile(Integer idArchivo, char tipo) throws FileNotFoundException;
   public File renombrarArchivoDigitalizacion(Documento objD, ArchivoTemporal archivo, int contador);
   public Integer checkEstadoDigitalizacion(Integer iIdDoc) throws RuntimeException;
   public Map<String,List<Archivo>> findByIdDocumento(Integer iIdDocumento);
   public Map<String,List<Archivo>> getArchivoList(Integer iIdExpediente, Integer iIdDocumento, String strRol);
   public void saveArhivoFromSessionToDB(List<ArchivoTemporal> lstArchivoTemporal, Documento objDocumento, Usuario usuario);
   public void updateEstadoByArchivo(Integer iIdArchivo, Character cEstado);
   public void updateEstadoByDocumento(Integer iIdDocumento, Character cEstado);
   public void uploadToAlfresco(ArchivoTemporal objArchivoTemporal, Documento objDocumento, Integer iContador);
   public void checkInToAlfresco(Usuario usuario,ArchivoTemporal objArchivoTemporal, Documento objDocumento, Integer iContador, boolean quitarCorchete) throws RemoteException, IOException;
   public void uploadFile(Usuario usuario,ArchivoTemporal objArchivoTemporal, Documento objDocumento, Integer iContador) throws RemoteException, IOException;
   public List<ArchivoVersionado> getVersions(Documento documento);
   public Archivo guardarArchivoTemporal(ArchivoTemporal objArchivoTemporal, Documento objDocumento, Integer iContador, Usuario usuarioSesion, String nombrePDFprincipal, String siglaUnidad);
   public List<Archivo> buscarLstPor(Integer iIdDocumento, String sNombre, SearchMode sm);
   public Map<String,List<ArchivoTemporal>> obtenerArchivosRechazados(Integer iIdDocumento);
   List<Archivo> findLstByExpediente(Integer idExpediente);
   List<Archivo> findLstByIdDocumento(Integer idDocumento);
   public Map<String,List<Archivo>> getArchivoListPorDocumento(Integer iIdDocumento);
   public Archivo getArchivoPrincipalPorDocumento(Integer iIdDocumento);
   public List<Archivo> getArchivoListPorDocumentoIG(Integer iIdDocumento);
   public List<Item> buscarItemArchivoXAutorPDF(Integer idAutor, Integer idDocumento);
   public List<Archivo> buscarArchivoXAutorPDF(Integer idAutor, Integer idDocumento);
   public void updatePrincipalByArchivo(Integer iIdArchivo, Character principal);
   public List<Archivo> findByIdNombreEstado(Integer idDocumento, String nombre);
}
