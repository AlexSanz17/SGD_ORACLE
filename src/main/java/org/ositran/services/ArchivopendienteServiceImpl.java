/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.daos.ArchivopendienteDAO;
import org.ositran.daos.AuditoriaDAO;
import org.ositran.daos.CampoDAO;
import org.ositran.daos.PlantillaDAO;
import org.ositran.daos.ValorcampoDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.ArchivoPendiente;
import com.btg.ositran.siged.domain.ArchivoTemporal;
import com.btg.ositran.siged.domain.Valorcampo;

public class ArchivopendienteServiceImpl implements ArchivopendienteService{

	ArchivopendienteDAO archivopendienteDAO;
	CampoDAO campoDAO;
	PlantillaDAO plantillaDAO;
	ValorcampoDAO valorcampoDAO;
	private AuditoriaDAO daoauditoria;
	private static Logger log=Logger.getLogger(ArchivopendienteServiceImpl.class);

	public ArchivopendienteDAO getArchivopendienteDAO(){
		return archivopendienteDAO;
	}

	public void setArchivopendienteDAO(ArchivopendienteDAO archivopendienteDAO){
		this.archivopendienteDAO=archivopendienteDAO;
	}

	@Transactional
	public void deleterchildren(Integer idarchivopendiente){

		/*
		 * Archivopendiente guardar =
		 * this.archivopendienteDAO.findByIdarchivopendiente
		 * (idarchivopendiente);
		 * 
		 * log.debug(" valor campo list .size : "+
		 * guardar.getValorcampoList().size() ) ;
		 * 
		 * if( guardar.getValorcampoList()!=null){
		 * guardar.getValorcampoList().clear(); }
		 * 
		 * if( guardar.getArchivotemporalList()!=null){
		 * guardar.getArchivotemporalList().clear(); }
		 * 
		 * this.archivopendienteDAO.saveArchivopendiente(guardar) ;
		 */
		this.archivopendienteDAO.deleterchildren(idarchivopendiente);

	}

	@Transactional
	@Override
	public void saveArchivopendiente(ArchivoPendiente archivopendiente,List<ArchivoTemporal> archivosTemporales,List<Valorcampo> valoresCampo){
		ArchivoPendiente guardar;

		if(archivopendiente.getIdArchivoPendiente() != null){
			guardar=archivopendiente;

			/*
			 * guardar =
			 * this.archivopendienteDAO.findByIdarchivopendiente(archivopendiente
			 * .getIdarchivopendiente());
			 * //this.archivopendienteDAO.refresh(guardar);
			 * 
			 * log.debug(" valor campo list .size : "+
			 * guardar.getValorcampoList().size() ) ;
			 * 
			 * if( guardar.getValorcampoList()!=null){
			 * guardar.getValorcampoList().clear(); }
			 * 
			 * if( guardar.getArchivotemporalList()!=null){
			 * guardar.getArchivotemporalList().clear(); }
			 * 
			 * // this.archivopendienteDAO.saveArchivopendiente(guardar) ;
			 * 
			 * log.debug(" valor campo list .size : "+
			 * guardar.getValorcampoList().size() ) ;
			 * 
			 * /* guardar.copy(archivopendiente);
			 */
			guardar.setArchivosTemporales(archivosTemporales);
			guardar.setValoresCampo(valoresCampo);
			/* */

		}
		else{
			guardar=archivopendiente;
		}
		this.archivopendienteDAO.saveArchivopendiente(guardar);

		log.debug(" archivopendienteDAO guardado ok");
	}

	public List<ArchivoPendiente> findByIdusuario(Integer idusuario){

		return this.archivopendienteDAO.findByIdusuario(idusuario);

	}

	public ArchivoPendiente findByIdarchivopendiente(Integer idArchivoPendiente){

		return this.archivopendienteDAO.findByIdarchivopendiente(idArchivoPendiente);

	}

	@Transactional
	public void deleteArchivopendiente(Integer idArchivoPendiente){

		this.archivopendienteDAO.deleteArchivopendiente(idArchivoPendiente);

	}

	public CampoDAO getCampoDAO(){
		return campoDAO;
	}

	public void setCampoDAO(CampoDAO campoDAO){
		this.campoDAO=campoDAO;
	}

	public PlantillaDAO getPlantillaDAO(){
		return plantillaDAO;
	}

	public void setPlantillaDAO(PlantillaDAO plantillaDAO){
		this.plantillaDAO=plantillaDAO;
	}

	public ValorcampoDAO getValorcampoDAO(){
		return valorcampoDAO;
	}

	public void setValorcampoDAO(ValorcampoDAO valorcampoDAO){
		this.valorcampoDAO=valorcampoDAO;
	}

	public void setDaoauditoria(AuditoriaDAO daoauditoria){
		this.daoauditoria=daoauditoria;
	}

	public AuditoriaDAO getDaoauditoria(){
		return daoauditoria;
	}

}
