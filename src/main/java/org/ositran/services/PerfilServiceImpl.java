package org.ositran.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ositran.daos.PerfilDAO;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Perfil;
import com.btg.ositran.siged.domain.Recurso;
import com.btg.ositran.siged.domain.Rol;

public class PerfilServiceImpl implements PerfilService{

	private static Logger log=Logger.getLogger(PerfilServiceImpl.class);
	private PerfilDAO dao;
	private RecursoService srvRecurso;
	private AuditoriaService srvAuditoria;

	// ////////////////////////////////
	// Constructors //
	// ////////////////////////////////
	public PerfilServiceImpl(PerfilDAO dao){
		this.dao=dao;
	}

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	public List<Perfil> findAll(){
		return getDao().findAll();
	}

	public Map<String,Integer> getMapRecursoXPerfil(Integer iIdPerfil){
		List<Recurso> lstRecurso=null;
		Map<String,Integer> mapRecurso=new LinkedHashMap<String,Integer>();
		Perfil objPerfil=null;
		if((objPerfil=this.findByIdPerfil(iIdPerfil))==null){
			log.debug("No se encontro Perfil con ID ["+iIdPerfil+"]");
			return null;
		}
		log.debug("Perfil con ID ["+objPerfil.getIdperfil()+"] Nombre ["+objPerfil.getNombre()+"]");
		if((lstRecurso=objPerfil.getRecursoList())==null){
			log.debug("No se encontro recursos para el perfil con Nombre ["+objPerfil.getNombre()+"]");
			return null;
		}
		log.debug("Numero de recursos asignados al perfil ["+lstRecurso.size()+"]");
		//log.debug("Numero de columnas en el grid ["+objPerfil.getGridcolumnaList().size()+"]");
		if((mapRecurso=getSrvRecurso().findMapAllByCodigo())==null){
			return null;
		}
		for(Recurso objRecurso : lstRecurso){
			log.debug("ID ["+objRecurso.getIdrecurso()+"] Codigo ["+objRecurso.getCodigo()+"]");
			mapRecurso.put(objRecurso.getCodigo(),1);
		}
		return mapRecurso;
	}

	public Map<Integer,String> obtenerMapTodo(){
		Map<Integer,String> mapPerfil=new LinkedHashMap<Integer,String>();
		List<Perfil> lstPerfil=dao.findAll();
		for(Perfil objPerfil : lstPerfil){
			mapPerfil.put(objPerfil.getIdperfil(),objPerfil.getNombre());
		}
		return mapPerfil;
	}

	public Perfil findByIdPerfil(Integer iIdPerfil){
		return getDao().findByIdPerfil(iIdPerfil);
	}

	@Transactional
	public void savePerfil(Perfil objPerfilOld,Perfil objPerfilNew,List<Integer> lstIdRecurso,String sUsuarioSesion,String sTipoAuditoria){
		List<Recurso> lstRecurso=new ArrayList<Recurso>();
		for(Integer iIdRecurso : lstIdRecurso){
			Recurso objRecurso=srvRecurso.findByIdRecurso(iIdRecurso);
			lstRecurso.add(objRecurso);
		}
		// Solo se hace clear cuando haya una lista existente
		if(objPerfilNew.getIdperfil()!=null){
			objPerfilNew.getRecursoList().clear();
		}
		objPerfilNew.setRecursoList(lstRecurso);
		try{
			srvAuditoria.aplicarAuditoria(objPerfilOld,objPerfilNew,sUsuarioSesion,Constantes.AUDITORIA_OPCION_GUARDAR,sTipoAuditoria);
		}catch(ClassNotFoundException e){
			log.error(e.getMessage(),e);
		}
		objPerfilNew=dao.savePerfil(objPerfilNew);
		log.debug("Perfil guardado con ID ["+objPerfilNew.getIdperfil()+"] Nombre ["+objPerfilNew.getNombre()+"] Nro Recursos ["+objPerfilNew.getRecursoList().size()+"]");
	}

	// ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public PerfilDAO getDao(){
		return dao;
	}

	public void setDao(PerfilDAO dao){
		this.dao=dao;
	}

	public RecursoService getSrvRecurso(){
		return srvRecurso;
	}

	public void setSrvRecurso(RecursoService srvRecurso){
		this.srvRecurso=srvRecurso;
	}

	public AuditoriaService getSrvAuditoria(){
		return srvAuditoria;
	}

	public void setSrvAuditoria(AuditoriaService srvAuditoria){
		this.srvAuditoria=srvAuditoria;
	}

	@Override
	public Map<String,Integer> getRecursosPorPerfiles(List<Perfil> perfiles){
		Map<String,Integer> recursos=getSrvRecurso().findMapAllByCodigo();

		LoginServiceImpl.recursosSession=new ArrayList<Recurso>();
        if(recursos!=null){
	   	   for(Perfil perfil : perfiles){
             if(perfil!=null&&!esPerfilEspecial(perfil.getNombre())){
            	  List<Recurso> recs=perfil.getRecursoList();
           	    for(Recurso rec : recs){
	        	  recursos.put(rec.getCodigo(),1);
	        	  LoginServiceImpl.recursosSession.add(rec);
		        }
		     }
		   }
		}
                
		return recursos;
	}

	private boolean esPerfilEspecial(String perfil){
		return perfil.equals(Constantes.PERFIL_MP)||perfil.equals(Constantes.PERFIL_DIG)||perfil.equals(Constantes.PERFIL_QAS)||perfil.equals(Constantes.ROL_ADMINISTRADOR);
	}

	/**
	 * Obtiene un <code>Map</code> con el codigo de recurso y su respectiva url para los
	 * roles que posea un usuario, siempre y cuando estos recursos esten disponibles.
	 * Principalmente usado para SAS
	 *
	 * @param roles la lista de roles del usuario
	 * @param recursosSesion la lista de recursos en sesion
	 * @return un <code>Map(String,String)</code> de la forma <code>codigo,actionUrl</code>
	 * @author German Enriquez
	 */
	@Override
	public Map<String,String> getMapRecursosPorRoles(List<Rol> roles,Map<String,Integer> recursosSesion){
		Map<String,String> recursos=new HashMap<String,String>();
		for(Rol rol : roles){
			List<Recurso> rec=dao.findByIdPerfil(rol.getIdperfil().getIdperfil()).getRecursoList();
			for(Recurso r : rec){
				if(recursosSesion.get(r.getCodigo())==1){
					recursos.put(r.getCodigo(),r.getActionurl());
				}
			}
		}
		return recursos;
	}
}
