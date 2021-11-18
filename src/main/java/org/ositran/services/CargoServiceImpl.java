/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import org.apache.log4j.Logger;
import org.ositran.daos.AuditoriaDAO;
import org.ositran.daos.CargoDAO;
import org.ositran.daos.EmpresadestinoDAO;
import org.ositran.daos.MensajeriaDAO;
import org.ositran.daos.TipoenvioDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Cargo;
import com.btg.ositran.siged.domain.Mensajeria;

public class CargoServiceImpl implements CargoService {

   private static Logger log = Logger.getLogger(CargoServiceImpl.class);
   private CargoDAO dao;
   private TipoenvioDAO daote;
   private EmpresadestinoDAO daoed;
   private MensajeriaDAO daomj;
   private AuditoriaDAO daoauditoria;
   private Cargo Objcargo;
   
   
  
	@Transactional
	public Cargo saveDatos(String Strmensa,String Strgrupo,String Strestado, Cargo objcargox){
	   
	   Cargo objxcargo=new Cargo();
	   Mensajeria Obmj=new Mensajeria();
	   int id=Integer.parseInt(Strmensa);
	   
	    Mensajeria objMensajeria = getDaomj().findId(id);
	    
	    //Actualizacion de mensajeria
		Obmj.setIdmensajeria(objMensajeria.getIdmensajeria());
		Obmj.setEstado('C');
		Obmj.setEstadoingreso('3');
		Obmj.setNumerointerno(objMensajeria.getNumerointerno() );
		Obmj.setTipodocumento(objMensajeria.getTipodocumento() );
		Obmj.setNumerodocumento(objMensajeria.getNumerodocumento() );
		Obmj.setDirecciondestino(objMensajeria.getDirecciondestino() );
		Obmj.setDestinatario(objMensajeria.getDestinatario());
		Obmj.setAsunto(objMensajeria.getAsunto() );
		Obmj.setFechaderivacion(objMensajeria.getFechaderivacion());
		Obmj.setIdusuario(objMensajeria.getIdusuario());
		Obmj.setIddocumento(objMensajeria.getIddocumento());
		Obmj.setAviso(objMensajeria.getAviso());
		Obmj.setDepartamento(objMensajeria.getDepartamento());
		Obmj.setProvincia(objMensajeria.getProvincia());
		Obmj.setDistrito(objMensajeria.getDistrito());
		Obmj.setDocprincipal(objMensajeria.getDocprincipal());
		Obmj.setIdtipoenvio(objMensajeria.getIdtipoenvio());
		Obmj.setReferenciaDireccionCliente(objMensajeria.getReferenciaDireccionCliente());
		 Obmj.setIdcliente(objMensajeria.getIdcliente());
         Obmj.setIdambitoenvio(objMensajeria.getIdambitoenvio());
		getDaomj().guardarObj(Obmj);
		
	   log.debug(objcargox.getFechadevcargo());
		setObjcargo(objcargox);
		getObjcargo().setGrupo(Strgrupo);
		getObjcargo().setEstado(Strestado);
		getObjcargo().setIdmensajeria(objMensajeria.getIdmensajeria());
		getObjcargo().setMensajeria(objMensajeria);
		
		objxcargo=getDao().saveDatos(Objcargo);
		   	
	   return objxcargo;	   
   }
   
   public Cargo findkey(int Id){
	   
	   return getDao().findkey(Id);
   }
   
   
     //////////////////////
    //getter and setters//
   //////////////////////


   

   public void setDaote(TipoenvioDAO daote) {
	   this.daote = daote;
   }

   public TipoenvioDAO getDaote() {
	   return daote;
   }

   public void setDaoed(EmpresadestinoDAO daoed) {
	   this.daoed = daoed;
   }

   public EmpresadestinoDAO getDaoed() {
	   return daoed;
   }

   public void setDaomj(MensajeriaDAO daomj) {
	   this.daomj = daomj;
   }

   public MensajeriaDAO getDaomj() {
	   return daomj;
   }

	public void setDao(CargoDAO dao) {
		this.dao = dao;
	}
	
	public CargoDAO getDao() {
		return dao;
	}

	public void setDaoauditoria(AuditoriaDAO daoauditoria) {
		this.daoauditoria = daoauditoria;
	}

	public AuditoriaDAO getDaoauditoria() {
		return daoauditoria;
	}

	public void setObjcargo(Cargo objcargo) {
		Objcargo = objcargo;
	}

	public Cargo getObjcargo() {
		return Objcargo;
	}
   
}
