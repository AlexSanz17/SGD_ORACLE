/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.Date;

import org.ositran.daos.AmbitoenvioDAO;
import org.ositran.daos.AuditoriaDAO;
import org.ositran.daos.DatosDAO;
import org.ositran.daos.EmpresadestinoDAO;
import org.ositran.daos.MensajeriaDAO;
import org.ositran.daos.TipoenvioDAO;
import org.ositran.utils.Constantes;
import org.ositran.utils.MensajeriaDatos;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.AmbitoEnvio;
import com.btg.ositran.siged.domain.Auditoria;
import com.btg.ositran.siged.domain.Datos;
import com.btg.ositran.siged.domain.Empresadestino;
import com.btg.ositran.siged.domain.Mensajeria;
import com.btg.ositran.siged.domain.Tipoenvio;
import com.btg.ositran.siged.domain.Usuario;

public class DatosServiceImpl implements DatosService{
	
	//private static Logger log=Logger.getLogger("org.ositran.services.CarpetabusquedaServiceImpl");
	private DatosDAO dao;
	private TipoenvioDAO daote;
	private EmpresadestinoDAO daoed;
	private MensajeriaDAO daomj;
	private AmbitoenvioDAO daoae;
	//private AuditoriaDAO auditoriaDao;

	@Transactional
	public MensajeriaDatos saveDatos(Usuario usuario,String Strfecha,String Strnumdoc,String Strtipdoc, String Strmensa,String Strnumin,String Strambitoenvio,String Stremdes,String Strusudes,String Strdirdes,String Strdepart,String Strprovin,String Strdistri,String Strrefere){
		Datos Obdt=new Datos();
		Mensajeria Obmj=new Mensajeria();
		MensajeriaDatos Msjdatos=new MensajeriaDatos();
		int id=Integer.parseInt(Strmensa);
		//Tipoenvio Obte=getDaote().findbyNombre(Strtipoenvio);
		Empresadestino Obede=getDaoed().findCod(Stremdes);
		Mensajeria objMensajeria=getDaomj().findId(id);
		AmbitoEnvio ObjAmbito = getDaoae().findId(Integer.parseInt(Strambitoenvio));
		// lleno los datos del Objeto Datos
		if(objMensajeria.getAviso()=='0'){
			Obdt.setNumerointerno(Strnumin);
			Obdt.setUsuariodestino(Strusudes);
			Obdt.setDireccion(Strdirdes);
			Obdt.setDepartamento(Strdepart);
			Obdt.setProvincia(Strprovin);
			Obdt.setDistrito(Strdistri);
			Obdt.setReferencia(Strrefere);
			Obdt.setIdambitoenvio(ObjAmbito);
			Obdt.setIdempresadestino(Obede);
			Obdt.setIdmensajeria(objMensajeria.getIdmensajeria());
			Obdt.setMensajeria(objMensajeria);
            Obmj.setReferenciaDireccionCliente(objMensajeria.getReferenciaDireccionCliente());
            Obmj.setIdcliente(objMensajeria.getIdcliente());
           Obmj.setIdambitoenvio(ObjAmbito.getIdambitoenvio());
                 
		}else{
			Obdt.setNumerointerno(Strnumin);
			Obdt.setUsuariodestino(Strusudes);
			Obdt.setDireccion(Strdirdes);
			Obdt.setReferencia(Strrefere);
			Obdt.setIdambitoenvio(ObjAmbito);
			Obdt.setIdempresadestino(Obede);
			Obdt.setIdmensajeria(objMensajeria.getIdmensajeria());
			Obdt.setMensajeria(objMensajeria);

            Obmj.setReferenciaDireccionCliente(objMensajeria.getReferenciaDireccionCliente());
            Obmj.setIdcliente(objMensajeria.getIdcliente());
            Obmj.setIdambitoenvio(ObjAmbito.getIdambitoenvio());
		
		}
		Obdt=getDao().saveDatos(Obdt);
		// rellenar el objeto mensajeria
		Obmj.setIdmensajeria(objMensajeria.getIdmensajeria());
		Obmj.setEstado(Constantes.ESTADO_ACTIVO);
		Obmj.setEstadoingreso('1');
		Obmj.setNumerointerno(objMensajeria.getNumerointerno());
		Obmj.setTipodocumento(objMensajeria.getTipodocumento());
		Obmj.setNumerodocumento(objMensajeria.getNumerodocumento());
		Obmj.setDestinatario(objMensajeria.getDestinatario());
		Obmj.setDirecciondestino(objMensajeria.getDirecciondestino());
		Obmj.setAsunto(objMensajeria.getAsunto());
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
                Obmj.setIdambitoenvio(ObjAmbito.getIdambitoenvio());
		getDaomj().guardarObj(Obmj);
		// lleno los datos del Objeto mensajeria datos
		Msjdatos.setIdmen(id);
		Msjdatos.setTipocumento(Strtipdoc);
		Msjdatos.setNumerodoc(Strnumdoc);
		Msjdatos.setDestinatario(Strdirdes);
		Msjdatos.setNuminterno(Strnumin);
		Msjdatos.setUsudestino(Strusudes);
		Msjdatos.setTievcodigo(objMensajeria.getIdtipoenvio().getIdtipoenvio().toString()); 
		Msjdatos.setTiev2(objMensajeria.getIdtipoenvio().getTipoenvio2());
		Msjdatos.setCodtipoambito(ObjAmbito.getIdambitoenvio().toString());
		Msjdatos.setNomtipoambito(ObjAmbito.getNomambitoenvio());
		Msjdatos.setDepartamento(Strdepart);
		Msjdatos.setDistrito(Strdistri);
		Msjdatos.setProvincia(Strprovin);
		Msjdatos.setDirec(Strdirdes);
		Msjdatos.setReferencia(Strrefere);
		Msjdatos.setEmpdescod(Stremdes);
		Msjdatos.setEmpdesnom(Obede.getNombre());
		//registrarAuditoria(usuario,Strenvio,Stremdes,Strrefere,Constantes.TA_RegistrarDatosDoc,Constantes.TM_Mensajeria,Constantes.TO_Registrar);
		return Msjdatos;
	}

	public Datos findId(int Id){
		return getDao().findId(Id);
	}

	// ////////////////////
	// getter and setters//
	// ////////////////////
	public void setDao(DatosDAO dao){
		this.dao=dao;
	}

	public DatosDAO getDao(){
		return dao;
	}

	public void setDaote(TipoenvioDAO daote){
		this.daote=daote;
	}

	public TipoenvioDAO getDaote(){
		return daote;
	}

	public void setDaoed(EmpresadestinoDAO daoed){
		this.daoed=daoed;
	}

	public EmpresadestinoDAO getDaoed(){
		return daoed;
	}

	public void setDaomj(MensajeriaDAO daomj){
		this.daomj=daomj;
	}

	public MensajeriaDAO getDaomj(){
		return daomj;
	}

	public AmbitoenvioDAO getDaoae() {
		return daoae;
	}

	public void setDaoae(AmbitoenvioDAO daoae) {
		this.daoae = daoae;
	}

	
	/*public void setAuditoriaDao(AuditoriaDAO auditoriaDao){
		this.auditoriaDao=auditoriaDao;
	}*/

	/*@Transactional
	@Override
	public void registrarAuditoria(Usuario usuario,String tipoEnvio,String empresaDestino,String referencia,String tipoAuditoria,String modulo,String opcion){

        Auditoria ObjAuditoriaTEnvio = new Auditoria();
        ObjAuditoriaTEnvio.setTipoAuditoria(tipoAuditoria);
        ObjAuditoriaTEnvio.setModulo(modulo);
        ObjAuditoriaTEnvio.setOpcion(opcion);
        ObjAuditoriaTEnvio.setFechaAudioria(new Date());
        ObjAuditoriaTEnvio.setUsuario(usuario.getUsuario());        
        ObjAuditoriaTEnvio.setCampo("TipoEnvio");
        ObjAuditoriaTEnvio.setNuevoValor(tipoEnvio);
        auditoriaDao.SaveAuditoria(ObjAuditoriaTEnvio);

        Auditoria ObjAuditoriaEmpDest = new Auditoria();
        ObjAuditoriaEmpDest.setTipoAuditoria(tipoAuditoria);
        ObjAuditoriaEmpDest.setModulo(modulo);
        ObjAuditoriaEmpDest.setOpcion(opcion);
        ObjAuditoriaEmpDest.setFechaAudioria(new Date());
        ObjAuditoriaEmpDest.setUsuario(usuario.getUsuario());        
        ObjAuditoriaEmpDest.setCampo("Emp.Destino");
        ObjAuditoriaEmpDest.setNuevoValor(empresaDestino);
        auditoriaDao.SaveAuditoria(ObjAuditoriaEmpDest);

        Auditoria ObjAuditoriaReferencia = new Auditoria();
        ObjAuditoriaReferencia.setTipoAuditoria(tipoAuditoria);
        ObjAuditoriaReferencia.setModulo(modulo);
        ObjAuditoriaReferencia.setOpcion(opcion);
        ObjAuditoriaReferencia.setFechaAudioria(new Date());
        ObjAuditoriaReferencia.setUsuario(usuario.getUsuario());        
        ObjAuditoriaReferencia.setCampo("Referencia");
        ObjAuditoriaReferencia.setNuevoValor(referencia);
        if(referencia.equals("")!=true){
        	auditoriaDao.SaveAuditoria(ObjAuditoriaReferencia);
        }
	}*/
}
