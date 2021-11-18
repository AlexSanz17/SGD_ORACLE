package org.ositran.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import org.apache.log4j.Logger;
import org.ositran.daos.EnvioDAO;
import org.ositran.daos.MensajeriaDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Envio;
import com.btg.ositran.siged.domain.Mensajeria;

public class EnvioServiceImpl implements EnvioService {

   private static Logger log = Logger.getLogger(EnvioServiceImpl.class);
   private EnvioDAO dao;
   private MensajeriaDAO daomj;
   private Envio Odjenvio;
   
   public Date convertir(String cadefecha)throws ParseException{
	   DateFormat df=null;
	   Date fecha=new Date();
	   
	   
	   
	   return fecha;
   }
   
   
   @Transactional
   public Envio saveDatos(String Strmensa,Envio Objenvio) {
	   
	   Mensajeria Obmj=new Mensajeria();
	   int id=Integer.parseInt(Strmensa); 
	   Envio objen=new Envio();   
		   Mensajeria objMensajeria = getDaomj().findId(id);
		   
		   //actualiza mensajeria
		   
		   Obmj.setIdmensajeria(objMensajeria.getIdmensajeria());
		   Obmj.setEstado('A');
		   Obmj.setEstadoingreso('2');
		   Obmj.setNumerointerno(objMensajeria.getNumerointerno());
		   Obmj.setTipodocumento(objMensajeria.getTipodocumento() );
		   Obmj.setNumerodocumento(objMensajeria.getNumerodocumento() );
		   Obmj.setDestinatario(objMensajeria.getDestinatario() );
		   Obmj.setDirecciondestino(objMensajeria.getDirecciondestino());
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
	   
		   setOdjenvio(Objenvio);		   
		   getOdjenvio().setIdmensajeria(objMensajeria.getIdmensajeria());
		   getOdjenvio().setMensajeria(objMensajeria);		   
		   objen=getDao().saveDatos(Odjenvio);
		   
	      return objen;
	   
   }
   public Envio findkey(int Id){
	   return getDao().findkey(Id);
   }
   
   //////////////////////
   //getter and setters//
  //////////////////////

   public void setDao(EnvioDAO dao) {
	   this.dao = dao;
   }
   public EnvioDAO getDao() {
	   return dao;
   }

	public void setDaomj(MensajeriaDAO daomj) {
		this.daomj = daomj;
	}
	
	public MensajeriaDAO getDaomj() {
		return daomj;
	}



	public void setOdjenvio(Envio odjenvio) {
		Odjenvio = odjenvio;
	}



	public Envio getOdjenvio() {
		return Odjenvio;
	}
  
}
