/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.Correo;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;


/**
 *
 * @author consultor_jti15
 */
public class ManejoDeEmailDAOImpl implements  ManejoDeEmailDAO{
    private static Logger log=Logger.getLogger(ManejoDeEmailDAOImpl.class);
    private EntityManager em;
    
   @PersistenceContext(unitName = "sigedPU")
   public void setEm(EntityManager em) {
      this.em = em;
   }
   
    public Correo guardarCorreo(Correo correo) {
		log.debug("-> [DAO] CorreoDAO - guardarCorreo():Documento ");

		if (correo.getIdCorreo() == null) {
			em.persist(correo);
			em.flush();
			em.refresh(correo);
		} 
                
		return correo;
    }
        
    public void enviarEmail(){
        try{
              Query query = em.createNativeQuery("{call Alertas.pk_alertas.DBCYDOC_alert_derivacion()}");
	      query.executeUpdate();
		
        }catch(Exception e){
            e.printStackTrace();
        }
        /*
        JDBCCallableStatement jdbc = new JDBCCallableStatement();
	   String getDBUSERByUserIdSql = "{call Alertas.pk_alertas.DBCYDOC_alert_derivacion(?)}";
	   Connection dbConnection = null;
	   CallableStatement callableStatement = null;

       try{

    	   dbConnection = jdbc.getDBConnection();
    	   callableStatement = dbConnection.prepareCall(getDBUSERByUserIdSql);
           
           callableStatement.setString(1, "consultor_jti15@ositran.gob.pe");
        
           callableStatement.executeQuery();

           
       } catch (SQLException e) {
           e.printStackTrace();
    	  
       } catch (Exception e) {
 	   e.printStackTrace();
          
       }finally {
		   try{
			if (callableStatement != null)
			   callableStatement.close();
			}catch(Exception e){
			   e.printStackTrace();
			}

			try{
			   if (dbConnection != null)
			    dbConnection.close();
			}catch(Exception e){
			   e.printStackTrace();
			}
		}*/
    }
}
