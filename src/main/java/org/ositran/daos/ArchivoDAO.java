/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Archivo;

public interface ArchivoDAO {
   public List<Archivo> findArchivoTipoFirmardo(Integer idDocumento , char tipoArchivo, String tipoFirma);
   public List<Archivo> findArchivoPrincipalFirmardo(Integer idDocumento , Usuario usuario);
   public List<String> contarArchivosxFirmar(Integer idDocumento , Usuario usuario); 
   public List<Archivo> findArchivosxFirmar(Integer idDocumento , Usuario usuario);
   public List<Archivo> buscarDocumentosPublicar(String nroTramite);
   public Archivo buscarObjPor(Integer iIdDocumento, String sNombre);
   public Archivo buscarObjPorId(Integer iIdArchivo);
   List<Archivo> buscarPorAutor(Integer idAutor, Integer idDocumento);
   List<Archivo> buscarPorAutor(Usuario usuario, Integer idDocumento);
   public Archivo guardarObj(Archivo objArchivo);
   public  Long buscarArchivosPorRuta(String ruta, Usuario usuario);
   public List<Archivo> buscarArchivosPorRutaDocumento(Integer idDocumento, String nombre);    
   public List<Archivo> checkEstadoDigitalizacion(Integer iIdDoc);
   public List<Archivo> findByCriteria(Integer iIdDoc, String strNombre, SearchMode sm);
   public List<Archivo> findByIdDocumentoOrderDesc(Integer iIdExpediente, Integer iIdDocumento, String sRol);
   public List<Archivo> findByEstado(char estado);
   public List<Archivo> findByIdDocumento(Integer iIdDoc);
   public int updateEstado(Integer iIdArchivo, Character cEstado);
   List<Archivo> findlstByExpediente(Integer idExpediente);
   public Archivo findByArchivoPrincipalIdDocumento(Integer iIdDoc);
   public int updatePrincipal(Integer iIdArchivo , Character principal);
   public Archivo updateArchivo( Archivo objT);
   public List<Archivo> findByIdNombreEstado(Integer idDocumento, String nombre);
   public List<Archivo> buscarArchivosObjectId(String objectId, Integer nroTramite);
   public List<Archivo> buscarArchivoExterno(String objectId, Integer nroTramite, String clave);
}
