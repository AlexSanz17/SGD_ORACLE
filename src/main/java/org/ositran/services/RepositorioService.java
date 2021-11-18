package org.ositran.services;

import java.util.List;
import java.util.Map;
import org.ositran.repositorio.EnlaceDocumento;

import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Archivo;

/**
 * 
 * @author armando
 */
public interface RepositorioService{

        public String obtenerRutaDocumento(Documento objDocumento, String siglaSite, String tipoDocumento);
	public List<EnlaceDocumento> listarDocumentos(Integer idexpediente);

	public List<EnlaceDocumento> busquedaArchivos(String textoABuscar,Map<String,String> propiedades);

	public List<Integer> busquedaIdDocumentos(String textoABuscar,Map<String,String> propiedades);
        
	public boolean eliminarEstructuraExpediente(Expediente objExpediente);

	public Map<String,String> obtenerMetadata(Documento doc, Usuario usuario);

        public boolean existeNodo(String sUsuario, String sPassword, String sRutaNodo) throws Exception;

        public boolean crearFolder(String sUsuario, String sPassword, String sRuta, String sFolder);

        public boolean moverNodo(String sUsuario, String sPassword, String sNodo, String sRutaOrigen, String sRutaDestino);

        public String obtenerRutaExpediente(Expediente objExpediente);

        public String obtenerRutaCompletaExpediente(Expediente objExpediente);

        public boolean eliminarNodo(String usuario, String password, String rutaNodo);

        void subirArchivosTransformadosARepositorio(Documento doc, List<Archivo> listaArchivos, boolean versionar, Usuario usuario, String rutaSite, String tipoDocumento) throws RuntimeException;
}
