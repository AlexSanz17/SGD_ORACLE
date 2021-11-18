/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.ositran.siged.dto.ProcesoDTO;
import org.ositran.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Usuario;

@Repository
public class ProcesoDAOImpl implements ProcesoDAO {

   private EntityManager em;
   private static Logger log=LoggerFactory.getLogger(ProcesoDAOImpl.class);
   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public List<Proceso> bsucarLstPor(Integer iIdResponsable) {
      return em.createNamedQuery("Proceso.buscarLstPorResponsable")
             .setParameter("idusuario", iIdResponsable)
             .getResultList();
   }

   public List<Proceso> findAll() {
      return getEm().createNamedQuery("Proceso.findAll").getResultList();
   }

   public List<Proceso> findPorcentajesProcesos() {
		String elquery=" SELECT  NEW Proceso( " + "  p.idproceso " + ", p.porcentajealertaA" + ", p.porcentajealertaR) " ;
		Query q;
		q=em.createQuery(elquery+" from Proceso p ");

		List<Proceso> result=q.getResultList();

		return result;
   }

   public List<Proceso> buscarProcesosActivos(){
	   return getEm().createNamedQuery("Proceso.findActivos").setParameter("estado", Constantes.ESTADO_ACTIVO)
       .getResultList();
   }

   public List<Proceso> buscarProcesosSancionadorActivos(){
	   return getEm().createNamedQuery("Proceso.findSancionadorActivos").setParameter("estado", Constantes.ESTADO_ACTIVO)
       .getResultList();
   }

   public List<Proceso> findAllBy(Integer iIdTipoProceso) {
      return em.createNamedQuery("Proceso.findAllByIdTipoProceso")
               .setParameter("idtipoproceso", iIdTipoProceso)
               .getResultList();
   }

   public List<Proceso> findAllByTipoProcesoAndEstado(String sTipoProceso, Character cEstado){
	   /*return em.createNamedQuery("Proceso.findAllByIdTipoProcesoAndEstado").setParameter("idtipoproceso", idTipoProceso)
	   			.setParameter("estado", estado)
	   			.getResultList();*/
      String sQuery = "SELECT NEW Proceso(" +
                      "p.idproceso," +
                      "p.nombre," +
                      "u.nombre," +
                      "r.nombres," +
                      "r.apellidos," +
                      "r.idusuario," +
                      "tp.nombre," +
                      "p.permitesumilla," +
                      "p.codigo," +
                      "p.produccion" +
                      ") ";
      sQuery += "FROM Proceso p ";
      sQuery += "LEFT JOIN p.responsable r ";
      sQuery += "LEFT JOIN r.unidad u ";
      sQuery += "LEFT JOIN p.tipoproceso tp ";
      sQuery += "WHERE tp.nombre = :tipoproceso AND p.estado = :estado ";
      sQuery += "ORDER BY p.nombre ";

      Query qQuery = em.createQuery(sQuery)
                       .setParameter("tipoproceso", sTipoProceso)
                       .setParameter("estado", cEstado);

      return qQuery.getResultList();
   }

   public List<Proceso> findAllByTypes(List <Parametro> Parameters) {
	      List <Integer> idtipos = new ArrayList<Integer>();

	      for(Parametro p : Parameters){
	    	  idtipos.add(new Integer (p.getValor() )) ;
	      }
	      String query = " SELECT p FROM Proceso p " ;

	      if(idtipos.size()>0){
	    	   query+=" WHERE p.tipoproceso.idtipoproceso in ( :idtipos ) ORDER BY nombre " ;
	      }

	      Query q = em.createQuery(query);

	      if(idtipos.size()>0){
	    	 q.setParameter("idtipos" ,idtipos   );
	      }

	      return q.getResultList();
	   }


   public List<Proceso> findByProcesoOrIdResponsable_Asistente(Integer iIdUsuario,List <Parametro> Parameters, char cEstado){
	   List <Integer> idtipos = new ArrayList<Integer>();

	      for(Parametro p : Parameters){
	    	  idtipos.add(new Integer (p.getValor() )) ;
	      }
	      String query = " SELECT p FROM Proceso p WHERE ((p.responsable.idusuario = :idusuario OR p.idasistente.idusuario = :idusuario) OR " ;

	      if(idtipos.size()>0){
	    	   query+="( p.tipoproceso.idtipoproceso in ( :idtipos ))) AND p.estado = :estado  ORDER BY nombre " ;
	      }

	      Query q = em.createQuery(query)
	      .setParameter("idusuario", iIdUsuario)
	      .setParameter("estado", cEstado);

	      if(idtipos.size()>0){
	    	 q.setParameter("idtipos" ,idtipos);
	      }

	      return q.getResultList();
   }

   public List<Proceso> findByIdResponsableOrAsistente(Integer iIdUsuario, char cEstado) {
      return getEm().createNamedQuery("Proceso.findByIdResponsableOrAsistente")
             .setParameter("idusuario", iIdUsuario)
             .setParameter("estado", cEstado)
             .getResultList();
   }

   public Proceso findByIdProceso(Integer iIdProceso) {
      return getEm().find(Proceso.class, iIdProceso);
   }


   public List<Proceso> findByIdProcesoList(Integer idProceso) {
      return getEm().createNamedQuery("Proceso.findByTipoIdentificacion")
             .setParameter("idtipoidentificacion", idProceso)
             .getResultList();
   }

   public List<Proceso> buscarProcesosxUsuarioParticipante(Usuario usuario) { 
      return getEm().createNamedQuery("Proceso.findByUsuarioParticipante")
             .setParameter("usuario", usuario)
             .setParameter("estado", Constantes.ESTADO_INACTIVO)
             .getResultList();
   }

   public List<Proceso> buscarProcesosxUsuarioParticipanteN2(Usuario usuario) {
	      return getEm().createNamedQuery("Proceso.findByUsuarioParticipanteN2")
	             .setParameter("usuario", usuario)
	             .getResultList();
	   }

   public Proceso saveProceso(Proceso objProceso) {
      if (objProceso.getIdproceso() == null) {
         em.persist(objProceso);
         em.flush();
         em.refresh(objProceso);
      } else {
         em.merge(objProceso);
         em.flush();
      }

      return objProceso;
   }

   private static String moreIn(Integer[] var) {
      String and = " AND ";
      String in = " IN ";
      String next = " pp.idusuario ";
      StringBuilder q = new StringBuilder("");
      for (int i = 0; i < var.length; i++) {
         if (i + 1 == var.length) {
            and = "";
            next = "";
         }
         q.append(in);
         q.append(" (select idusuario from participantexproceso where idproceso= (select p.idproceso from proceso p, documento d, expediente e  where d.iddocumento=");
         q.append(var[i]);
         q.append("  and d.expediente= e.id and e.proceso=p.idproceso)) ");
         q.append(and);
         q.append(" \n ");
         q.append(next);
      }
      return q.toString();
   }

   public ArrayList<Object> cargaUsuarioMasivo(Integer[] var) {
      String sql = "select distinct pp.idusuario, (select u.nombres from usuario u where u.idusuario= pp.idusuario), (select u.apellidos from usuario u where u.idusuario= pp.idusuario)" +
            " from participantexproceso pp where pp.idusuario " +
            moreIn(var);
      //log.debug("[ " + sql + " ]");
      return (ArrayList<Object>) em.createNativeQuery(sql).getResultList();
   }

   public Proceso findName(String nombreProceso) {

	      String query = " SELECT p FROM Proceso p where nombre='"+nombreProceso+"'" ;
	      Query q = em.createQuery(query);
	      Proceso p=null;
	      try{p=(Proceso)q.getSingleResult();}
	      catch(Exception e){}
	      return p;
	   }

   /*
   private static String moreIn(Integer[] var){
   String and=" AND ";
   String in=" IN ";
   String next=" p.idusuario ";
   String q="";
   for (int i = 0; i < var.length; i++) {
   if(i+1==var.length){and ="";next="";}
   q=q+ in+" (select p1.idusuario from participantexproceso p1 where p1.idproceso="+var[i]+" )"+ and+" \n "+ next;
   }
   return q;
   }

   public ArrayList<Object> cargaUsuarioMasivo(Integer[] var){
   String sql="select distinct pp.idusuario, (select u.nombres from usuario u where u.idusuario= pp.idusuario), (select u.apellidos from usuario u where u.idusuario= pp.idusuario)" +
   " from participantexproceso pp where pp.idusuario "+
   moreIn(var);
   //log.debug("[ "+sql+" ]");
   return  (ArrayList<Object>) em.createNativeQuery(sql).getResultList();
   }
    */
   /* (non-Javadoc)
    * @see org.ositran.daos.ProcesoDAO#deleteProceso(org.ositran.pojos.Proceso)
    */
   public void deleteProceso(Proceso objProceso) {
      objProceso = getEm().getReference(Proceso.class, objProceso.getIdproceso());
      getEm().remove(objProceso);
   }

   public List<Proceso> findLstBy(Usuario objParticipante) {
      return em.createNamedQuery("Proceso.findByParticipante")
               .setParameter("usuario", objParticipante)
               .getResultList();
   }

    @Override
    public List<ProcesoDTO> buscarProcesosActivosResumen() {
        List<ProcesoDTO> procesos = new ArrayList<ProcesoDTO>();

        StringBuilder query = new StringBuilder();

        query.append("SELECT ")
        .append("p.idproceso, p.nombre, u.idunidad, u.nombre, r.nombres, r.apellidos, r.idusuario, tp.nombre, p.permitesumilla, p.codigo, p.produccion ")
        .append("FROM Proceso p ")
        .append("LEFT JOIN p.responsable r ")
        .append("LEFT JOIN r.unidad u ")
        .append("LEFT JOIN p.tipoproceso tp ")
        .append("WHERE p.estado <> 'I' ")
        .append("ORDER BY p.nombre ");

        Query qQuery = em.createQuery(query.toString());
        List<Object[]> list = qQuery.getResultList();

        if (list != null) {
            for (Object[] resultado : list) {
                ProcesoDTO proceso = new ProcesoDTO();

                proceso.setIdProceso( ((Number) resultado[0]).intValue() );
                proceso.setNombre( (String) resultado[1] );
                proceso.setUnidadId( ((Number) resultado[2]).intValue() );
                proceso.setUnidadNombre( (String) resultado[3] );
                proceso.setResponsableNombres( (String) resultado[4] );
                proceso.setResponsableApellidos( (String) resultado[5] );
                proceso.setResponsableIdUsuario( ((Number) resultado[6]).intValue() );
                proceso.setTipoNombre( (String) resultado[7] );
                proceso.setPermiteSumilla( (Character) resultado[8] );
                proceso.setCodigo( (String) resultado[9] );
                proceso.setProduccion( (Character) resultado[10] );

                procesos.add(proceso);
            }
        }

      return procesos;
   }


    public List<Proceso> getLstAutoCompleteVigente() {
        String sQuery = "SELECT NEW Proceso(" +
                        "p.idproceso," +
                        "p.nombre," +
                        "u.nombre," +
                        "r.nombres," +
                        "r.apellidos," +
                        "r.idusuario," +
                        "tp.nombre," +
                        "p.permitesumilla," +
                        "p.codigo," +
                        "p.produccion" +
                        ") ";
        sQuery += "FROM Proceso p ";
        sQuery += "LEFT JOIN p.responsable r ";
        sQuery += "LEFT JOIN r.unidad u ";
        sQuery += "LEFT JOIN p.tipoproceso tp ";
        sQuery += "WHERE p.vigencia = 'V' ";
        sQuery += "ORDER BY p.nombre ";

        Query qQuery = em.createQuery(sQuery);

        return qQuery.getResultList();
     }

   public List<Proceso> getLstAutoComplete() {
      String sQuery = "SELECT NEW Proceso(" +
                      "p.idproceso," +
                      "p.nombre," +
                      "u.nombre," +
                      "r.nombres," +
                      "r.apellidos," +
                      "r.idusuario," +
                      "tp.nombre," +
                      "p.permitesumilla," +
                      "p.codigo," +
                      "p.produccion" +
                      ") ";
      sQuery += "FROM Proceso p ";
      sQuery += "LEFT JOIN p.responsable r ";
      sQuery += "LEFT JOIN r.unidad u ";
      sQuery += "LEFT JOIN p.tipoproceso tp ";
      sQuery += "WHERE p.estado <> 'I' ";
      sQuery += "ORDER BY p.nombre ";

      Query qQuery = em.createQuery(sQuery);

      return qQuery.getResultList();
   }

   public Proceso getProcesoxId(Integer idProceso) {
	      String sQuery = "SELECT NEW Proceso(" +
	                      "p.idproceso," +
	                      "p.nombre," +
	                      "u.nombre," +
	                      "r.nombres," +
	                      "r.apellidos," +
	                      "r.idusuario," +
	                      "tp.nombre," +
	                      "p.permitesumilla," +
	                      "p.codigo," +
	                      "p.produccion" +
	                      ") ";
	      sQuery += "FROM Proceso p ";
	      sQuery += "LEFT JOIN p.responsable r ";
	      sQuery += "LEFT JOIN r.unidad u ";
	      sQuery += "LEFT JOIN p.tipoproceso tp ";
	      sQuery += "WHERE p.estado <> 'I' ";
	      sQuery += "and p.idproceso = "+idProceso;
	      sQuery += "ORDER BY p.nombre ";

	      Query qQuery = em.createQuery(sQuery);

	      return (Proceso)qQuery.getSingleResult();
	   }

   @Override
   public Proceso findObjectBy(String nombre, Character estado) {
      try {
         return (Proceso) em.createNamedQuery("Proceso.findByNombreEstado")
                            .setParameter("nombre", nombre.toLowerCase())
                            .setParameter("estado", estado)
                            .getSingleResult();
      } catch (NoResultException nre) {
         log.warn("No se encontro proceso con nombre [" + nombre + "] estado [" + estado + "]");

         return null;
      } catch (NonUniqueResultException nure) {
         log.warn("Se encontro mas de un registro con nombre [" + nombre + "] estado [" + estado + "]");

         return null;
      }
   }

   @SuppressWarnings("unchecked")
  public List<Proceso> findbyProcesosStor(){
	      String query = " SELECT p FROM Proceso p where upper(p.nombre) like upper('STOR%')";
	      Query q = em.createQuery(query);
	      List<Proceso> data=new ArrayList<Proceso>();
	      try{
	    	  data=(List<Proceso>)q.getResultList();
	     }catch(Exception e){
	    	 log.error("Error al traer lista de procesos de STOR:{}",e.getMessage());
	     }
	     return data;
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public EntityManager getEm() {
      return em;
   }

   @PersistenceContext(unitName="sigedPU")
   public void setEm(EntityManager em) {
      this.em = em;
   }
}
