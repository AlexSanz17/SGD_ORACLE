/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.grid.Item;
import com.btg.ositran.siged.domain.Usuario;

public interface ItemDAO {

	public List<Item> findLstExpedienteBy(Integer iIdUsuario);

	public List<Item> findLstInformativoBy(Usuario objUsuario,
			Character cEstado);

	public List<Item> findLstInformativoBy(Usuario objUsuario, BusquedaAvanzada objFiltro);

	public List<Item> findLstNotificacionBy(Integer iIdPropietario,
			Character cEstado);

	public List<Item> findLstDocumentoBy(Integer iIdExpediente);
}
