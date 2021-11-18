/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.ositran.utils.Busdiafestivo;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.DiaFestivo;

@Repository
public class DiafestivoDAOImpl implements DiafestivoDAO{

	private EntityManager em;
	private static Logger log=Logger.getLogger(DiafestivoDAOImpl.class);

	@SuppressWarnings("unchecked")
	public List<DiaFestivo> findAll(){
		return getEm().createNamedQuery("Diafestivo.findAll").getResultList();
	}

	public DiaFestivo findxfecha(String nomreg,Date fecha){
		return (DiaFestivo) getEm().createNamedQuery("Diafestivo.findBydianolaborable").setParameter("nombreregion",nomreg).setParameter("fechanolaborable",fecha).getSingleResult();
	}

	//Devuelve todos los feriados desde la fecha indicada para la region mandada
	//Incluye sabados y domingos
	@SuppressWarnings("unchecked")
	public List<Date> findIntervloXRegion(Date desde,Date hasta, String nomreg){
		String sql="SELECT f.fechanolaborable FROM DiaFestivo f  WHERE LOWER(f.idregion.nombre) = :camporegion "+
				   "AND f.fechanolaborable >= :campodesde AND f.fechanolaborable <= :campohasta " +
				   "GROUP BY f.fechanolaborable ORDER BY f.fechanolaborable";
		Query q=em.createQuery(sql);
		q.setParameter("camporegion", nomreg.toLowerCase());
		q.setParameter("campodesde", desde);
		q.setParameter("campohasta", hasta);
		return q.getResultList();
	}



	@SuppressWarnings("unchecked")
	public List<Date> findFeriadosXRegion(Date desde,Date hasta, String idreg){

		String sql="SELECT f.fechanolaborable FROM DiaFestivo  f WHERE  f.idregion.iddepartamento = :camporegion AND "+
				   " TO_CHAR(f.fechanolaborable,'YYYYMMDD') >= :campodesde AND TO_CHAR(f.fechanolaborable,'YYYYMMDD') <= :campohasta " +
				   "GROUP BY f.fechanolaborable ORDER BY f.fechanolaborable";
		Query q=em.createQuery(sql);
		q.setParameter("camporegion", new Integer(idreg));
		q.setParameter("campodesde", (new SimpleDateFormat("yyyyMMdd")).format(desde));
		q.setParameter("campohasta", (new SimpleDateFormat("yyyyMMdd")).format(hasta));
		return q.getResultList();

	}

	public DiaFestivo saveDiafestivo(DiaFestivo objDf){
		if(objDf.getIddiafestivo()==null){
			getEm().persist(objDf);// Nuevo
			getEm().flush();
			getEm().refresh(objDf);
		}else{
			getEm().merge(objDf); // Actualizacion
			getEm().flush();
		}
		return objDf;
	}

	public List<DiaFestivo> findxnombreregion(String nomreg){
		return (List<DiaFestivo>) getEm().createNamedQuery("Diafestivo.findBydianolaborable").setParameter("nombreregion",nomreg).getResultList();
	}

	public List<Busdiafestivo> findlisdiafxnomregion(String Nomregion,String fecha){
		List<Busdiafestivo> data=new ArrayList<Busdiafestivo>();
		data=null;
		//_log.debug("diafestivo fecha : "+fecha);
		Date fechadate=null;
		try{
			fechadate=new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
		}catch(Exception ex){
			log.error(ex.getMessage(), ex);
		}
		if(Nomregion.equals("")&&fechadate!=null){
		}else{
			try{
				String sql="SELECT NEW org.ositran.utils.Busdiafestivo("+"rg.iddepartamento,"+"rg.nombre,"+"df.iddiafestivo,"+"df.dia,"+"df.mes,"+"df.anio,"+"df.motivo)"+" FROM Departamento rg  "+" JOIN rg.diafestivoList df "+" WHERE rg.nombre='"+Nomregion+"' AND year(df.fechanolaborable)=year(:fecha) "+" AND month(df.fechanolaborable)=month(:fecha) "+" AND day(df.fechanolaborable)=day(:fecha) Order By df.fechanolaborable";
				Query q=getEm().createQuery(sql).setParameter("fecha",fechadate);
				data=(List<Busdiafestivo>) q.getResultList();
			}catch(javax.persistence.EntityExistsException e1){
				log.error(e1.getMessage(), e1);
			}
		}
		return data;
	}

	public List<Busdiafestivo> findlisdiafestivo(String Nomregion){
		List<Busdiafestivo> data=new ArrayList<Busdiafestivo>();
		data=null;
		String sql="SELECT NEW org.ositran.utils.Busdiafestivo("+"rg.iddepartamento,"+"rg.nombre,"+"df.iddiafestivo,"+"df.dia,"+"df.mes,"+"df.anio,"+"df.motivo)"+" FROM Departamento rg  "+" JOIN rg.diafestivoList df ";
		if(Nomregion.equals("")){
			sql+=" Order By df.fechanolaborable";
		}else{
			sql+=" WHERE rg.nombre='"+Nomregion+"' Order By df.fechanolaborable";
		}
		try{
			data=(List<Busdiafestivo>) getEm().createQuery(sql).getResultList();
		}catch(javax.persistence.EntityExistsException ex){
			log.error(ex.getMessage(), ex);
		}
		return data;
	}

	public List<Busdiafestivo> lisdaysNoLaborable(String Nomregion){
		List<Busdiafestivo> data=new ArrayList<Busdiafestivo>();
		data=null;
		if(Nomregion.equals("")){
		}else{
			try{
				String sql="SELECT NEW org.ositran.utils.Busdiafestivo("+"df.fechanolaborable)"+" FROM Departamento rg  "+" JOIN rg.diafestivoList df "+" WHERE rg.nombre='"+Nomregion+"' Order By df.fechanolaborable";
				data=(List<Busdiafestivo>) getEm().createQuery(sql).getResultList();
			}catch(javax.persistence.EntityExistsException e1){
				log.error(e1.getMessage(), e1);
			}
		}
		return data;
	}

	public void deleteDiafestivo(int iddiafestivo){
		DiaFestivo objdiafestivo=new DiaFestivo();
		objdiafestivo=getEm().getReference(DiaFestivo.class,iddiafestivo);
		getEm().remove(objdiafestivo);
	}

	/**
	 * Retorna los dias festivos para un usuario segun la sede a la que
	 * pertenezca
	 *
	 * @param idUsuario
	 * @return los dias festivos para este usuario
	 * @author Germán Enríquez
	 */
	@Override
   @SuppressWarnings("unchecked")
   public List<DiaFestivo> getDiasFestivosPorUsuario(){
           String sql="SELECT df FROM DiaFestivo df WHERE df.idregion = '150000'";
	   return getEm().createQuery(sql).getResultList();
   }

	// //////////////////////
	// Getters and Setters
	// //////////////////////
	public EntityManager getEm(){
		return em;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}
}
