package org.ositran.services;

import java.util.List;
import org.ositran.daos.ProcedimientoIncumplidosDAO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Procedimientoincumplido;

public class ProcedimientoIncumplidosServiceImpl implements ProcedimientoIncumplidosService{
	
	private ProcedimientoIncumplidosDAO procedimientoIncumplidosDAO;
	
	public ProcedimientoIncumplidosDAO getProcedimientoIncumplidosDAO() {
		return procedimientoIncumplidosDAO;
	}

	public void setProcedimientoIncumplidosDAO(
			ProcedimientoIncumplidosDAO procedimientoIncumplidosDAO) {
		this.procedimientoIncumplidosDAO = procedimientoIncumplidosDAO;
	}

	public List<Procedimientoincumplido> findAll() {
		return procedimientoIncumplidosDAO.findAll();
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void saveObject(Procedimientoincumplido procedimientoincumplido) {
		procedimientoIncumplidosDAO.saveObject(procedimientoincumplido);
	}

	public List<Procedimientoincumplido> getProcedimientoIncByFiltro(String nombre, String descripcion, Character estado) {
		return procedimientoIncumplidosDAO.getProcedimientoIncByFiltro(nombre,descripcion,estado);
	}

	
	public Procedimientoincumplido getProcedimientoById(String idP) {
		return procedimientoIncumplidosDAO.getProcedimientoById(idP);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void updateObject(Procedimientoincumplido procedimientoincumplido) {
		procedimientoIncumplidosDAO.updateObject(procedimientoincumplido);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteObject(Procedimientoincumplido procedimientoincumplido) {
		procedimientoIncumplidosDAO.deleteObject(procedimientoincumplido);
	}
}