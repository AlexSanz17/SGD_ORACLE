/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.btg.ositran.siged.domain.LogOperacion;


@Repository
public class LogOperacionDAOImpl implements LogOperacionDAO{
	private EntityManager em;
	private static Logger log = LoggerFactory.getLogger(LogOperacionDAOImpl.class);

	public List<LogOperacion> findLogOperacion(String usuario, String tipoDocumento, String nroDocumento){
		log.debug("-> [DAO] LogOperacionDAO - findLogOperacion():LogOperacion ");

		String sql="SELECT td.nombre || ' - ' || d.nrodocumento, u.nombres || ' ' || u.apellidos, t.fechaoperacion, t.nombrefile, p.descripcion  FROM LogOperacion t, tipoDocumento td, documento d, usuario u, parametro p WHERE d.iddocumento = t.iddocumento and d.tipodocumento=td.idtipodocumento ";

		if(usuario!=null && !usuario.trim().equals("")){
			sql+=" and  t.idusuario = " + Integer.valueOf(usuario);
		}

		if(nroDocumento!=null && !nroDocumento.trim().equals("")){
			sql+=" and d.nrodocumento like  '%" + nroDocumento + "%'";
		}

		if(tipoDocumento!=null && !tipoDocumento.trim().equals("")){
			sql+=" and  d.tipodocumento = " + Integer.valueOf(tipoDocumento);
		}

		sql += " and t.idusuario = u.idusuario ";
		sql += " and p.idparametro>85 ";
		sql += " and p.tipo='MENU' ";
		sql += " and p.valor=t.opcion ";
		sql+=" ORDER by t.fechaoperacion desc ";

		Query q=em.createNativeQuery(sql);

		List data=q.getResultList();
		List<LogOperacion> dataforward=new ArrayList<LogOperacion>();
		LogOperacion logOperacion = null;
		for(int i=0;i<data.size();i++){
			logOperacion = new LogOperacion();
            try{
				Object obj[]=(Object[])data.get(i);
				String documento=String.valueOf(obj[0]);
				String nombreUsuario =String.valueOf(obj[1]);
				SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date fecha = formato.parse(obj[2].toString());
				String nombreFile = String.valueOf(obj[3]);
				String opcion = String.valueOf(obj[4]);

				logOperacion.setDocumento(documento);
				logOperacion.setUsuario(nombreUsuario);
				logOperacion.setFechaoperacion(fecha);
				logOperacion.setNombrefile(nombreFile);
                logOperacion.setDesOpcion(opcion);
				dataforward.add(logOperacion);
            }catch(Exception ex){
            	ex.printStackTrace();
            }
		}
		return dataforward;


	}

	@Override
	public LogOperacion saveLogOperacion(LogOperacion logOperacion){
		log.debug("-> [DAO] LogOperacionDAO - saveLogOperacion():LogOperacionDAOImpl");
		if (logOperacion.getIdlogoperacion() == null) {
		  em.persist(logOperacion); //
		  em.flush();
		}else{
		   em.merge(logOperacion);
	       em.flush();
		}
       return logOperacion;
	}

	 // ////////////////////////////////
    // Getters and Setters //
    // ////////////////////////////////
    public EntityManager getEm() {
        return em;
    }

    @PersistenceContext(unitName = "sigedPU")
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
