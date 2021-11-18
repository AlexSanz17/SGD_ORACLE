/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.ositran.services.AmbitoenvioService;
import org.ositran.services.ArchivoService;
import org.ositran.services.DatosService;
import org.ositran.services.DocumentoService;
import org.ositran.services.EmpresadestinoService;
import org.ositran.services.MensajeriaService;
import org.ositran.services.TipoenvioService;
import org.ositran.utils.Constantes;
import org.ositran.utils.MensajeriaDatos;

import com.btg.ositran.siged.domain.AmbitoEnvio;
import com.btg.ositran.siged.domain.Datos;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Empresadestino;
import com.btg.ositran.siged.domain.Mensajeria;
import com.btg.ositran.siged.domain.Tipoenvio;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class DatosAction{

	private static Logger log=Logger.getLogger("org.ositran.actions.DatosAction");
	private DatosService SvrDt;
	private Mensajeria obMsj;
	private MensajeriaService svrMj;
	private MensajeriaDatos Objmjda;
	private TipoenvioService SvrTien;
	private EmpresadestinoService SvrEmdes;
	private DocumentoService svrdocserv;
	private AmbitoenvioService ambitoService;
	private ArchivoService archivoService;
	private Datos obDatos;

	// ////////////////////////////////
	// Constructors //
	// ////////////////////////////////
	public DatosAction(DatosService SvrDt,MensajeriaService svrMj,TipoenvioService SvrTien,EmpresadestinoService SvrEmdes,DocumentoService svrdocserv, AmbitoenvioService ambitoService){
		setSvrDt(SvrDt);
		setSvrMj(svrMj);
		setSvrTien(SvrTien);
		setSvrEmdes(SvrEmdes);
		setSvrdocserv(svrdocserv);
		setAmbitoService(ambitoService);
	}

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	public String savedatos() throws Exception{
		log.debug("-> [Action] DatosAction - savedatos():String ");

		Usuario usuario=(Usuario) ActionContext.getContext().getSession().get(Constantes.SESSION_USUARIO);
		String Strfecha=ServletActionContext.getRequest().getParameter("fecha");
		String Strnumdoc=ServletActionContext.getRequest().getParameter("nmdoc");
		String Strtipdoc=ServletActionContext.getRequest().getParameter("tpdoc");
		String Strmensa=ServletActionContext.getRequest().getParameter("idmensajeria");
		String Strnumin=ServletActionContext.getRequest().getParameter("numinterno");
		String Strambitoenvio=ServletActionContext.getRequest().getParameter("codigo");
		String Stremdes=ServletActionContext.getRequest().getParameter("codigodes");
		String Strusudes=ServletActionContext.getRequest().getParameter("usudes");
		String Strdirdes=ServletActionContext.getRequest().getParameter("dirdes");
		String Strdepart=ServletActionContext.getRequest().getParameter("departamento");
		String Strprovin=ServletActionContext.getRequest().getParameter("provincia");
		String Strdistri=ServletActionContext.getRequest().getParameter("distrito");
		String Strrefere=ServletActionContext.getRequest().getParameter("referencia");
		int id=Integer.parseInt(Strmensa);
		/* MensajeriaDatos obMsjdt= */
		SvrDt.saveDatos(usuario,Strfecha,Strnumdoc,Strtipdoc,Strmensa,Strnumin,Strambitoenvio,Stremdes,Strusudes,Strdirdes,Strdepart,Strprovin,Strdistri,Strrefere);
		Mensajeria obMsj=svrMj.ViewMensaje(id);
		Datos objdt=getSvrDt().findId(obMsj.getIdmensajeria());
		setObDatos(objdt);
		setObMsj(obMsj);
		return "Obmejda";
	}

	@SuppressWarnings("unused")
	public String findkey() throws Exception{
		log.debug("-> [Action] DatosAction - findkey():String ");

		try{
			String Strmensa=ServletActionContext.getRequest().getParameter("Idmen");
			int Id=Integer.parseInt(Strmensa);
			Datos Objdatos=new Datos();
			MensajeriaDatos Objetomjn=new MensajeriaDatos();
			Mensajeria Objmens=new Mensajeria();
			Documento Objdoc=new Documento();
			Tipoenvio Objtien=new Tipoenvio();
			AmbitoEnvio Objamen=new AmbitoEnvio();
			Empresadestino Objemdes=new Empresadestino();
			// private TipoenvioService SvrTien;
			// private EmpresadestinoService SvrEmdes;
			Objdatos=SvrDt.findId(Id);

			Objmens=svrMj.ViewMensaje(Id);
			Objtien=SvrTien.findbyId(Objmens.getIdtipoenvio().getIdtipoenvio());

			Map<String, Object> mapSession = ActionContext.getContext().getSession();
			mapSession.put(Constantes.SESSION_UPLOAD_LIST, archivoService.getArchivoListPorDocumento(Objmens.getIddocumento()));

			if(Objdatos!=null){
				Objemdes=SvrEmdes.findkey(Objdatos.getIdempresadestino().getIdempresadestino());
				Objdoc=svrdocserv.findByIdDocumento(Objmens.getIddocumento());
				Objamen= ambitoService.findId(Objdatos.getIdambitoenvio().getIdambitoenvio());

				if(Objmens.getAviso().charValue()=='0'){
					Objetomjn.setIdmen(Objmens.getIdmensajeria());
					Objetomjn.setTipocumento(Objmens.getTipodocumento());
					Objetomjn.setNumerodoc(Objmens.getNumerodocumento());
					Objetomjn.setDestinatario(Objmens.getDestinatario());
					Objetomjn.setAsunto(Objmens.getAsunto());
					Objetomjn.setFechaderiva(Objmens.getFechaderivacion());
					Objetomjn.setNuminterno(Objdatos.getNumerointerno());
					Objetomjn.setUsudestino(Objdatos.getUsuariodestino());
					Objetomjn.setDirec(Objdatos.getDireccion());
					Objetomjn.setTiev2(Objtien.getTipoenvio2());
					Objetomjn.setDepartamento(Objdatos.getDepartamento());
					Objetomjn.setProvincia(Objdatos.getProvincia());
					Objetomjn.setDistrito(Objdatos.getDistrito());
					Objetomjn.setReferencia(Objdatos.getReferencia());
					Objetomjn.setEmpdescod(Objemdes.getCodigo());
					Objetomjn.setEmpdesnom(Objemdes.getNombre());
					Objetomjn.setCodtipoambito(Objamen.getIdambitoenvio().toString());
					Objetomjn.setNomtipoambito(Objamen.getNomambitoenvio());
					Objetomjn.setReferenciaDireccionCliente(Objmens.getReferenciaDireccionCliente());
					// Objetomjn.setDirec(Objdoc.getExpediente().getCliente().getUbigeoprincipal().getNombre());
					// Objetomjn.setProvincia(Objdoc.getExpediente().getCliente().getUbigeoprincipal().getProvincia().getNombre());
					// Objetomjn.setDepartamento(Objdoc.getExpediente().getCliente().getUbigeoprincipal().getProvincia().getDepartamento().getNombre());
					setObjmjda(Objetomjn);
					return "Objendatox";
				}
				Objetomjn.setIdmen(Objmens.getIdmensajeria());
				Objetomjn.setTipocumento(Objmens.getTipodocumento());
				Objetomjn.setNumerodoc(Objmens.getNumerodocumento());
				Objetomjn.setDestinatario(Objmens.getDestinatario());
				Objetomjn.setAsunto(Objmens.getAsunto());
				Objetomjn.setFechaderiva(Objmens.getFechaderivacion());
				Objetomjn.setNuminterno(Objdatos.getNumerointerno());
				Objetomjn.setUsudestino(Objdatos.getUsuariodestino());
				Objetomjn.setDirec(Objdatos.getDireccion());
				Objetomjn.setTiev2(Objtien.getTipoenvio2());
				Objetomjn.setReferencia(Objdatos.getReferencia());
				Objetomjn.setEmpdescod(Objemdes.getCodigo());
				Objetomjn.setEmpdesnom(Objemdes.getNombre());
				Objetomjn.setDistrito(Objdatos.getDistrito());
				Objetomjn.setProvincia(Objdatos.getProvincia());
				Objetomjn.setDepartamento(Objdatos.getDepartamento());
				Objetomjn.setCodtipoambito(Objamen.getIdambitoenvio().toString());
				Objetomjn.setNomtipoambito(Objamen.getNomambitoenvio());
				setObjmjda(Objetomjn);
				return "Objendatox2";
			}
			Objmens=svrMj.ViewMensaje(Id);
			Objdoc=svrdocserv.findByIdDocumento(Objmens.getIddocumento());
			Objetomjn.setIdmen(Objmens.getIdmensajeria());
			Objetomjn.setTipocumento(Objmens.getTipodocumento());
			Objetomjn.setNumerodoc(Objmens.getNumerodocumento());
			Objetomjn.setDestinatario(Objmens.getDestinatario());
			Objetomjn.setAsunto(Objmens.getAsunto());
			Objetomjn.setFechaderiva(Objmens.getFechaderivacion());
			Objetomjn.setNuminterno(Objmens.getNumerointerno());
			Objetomjn.setDirec(Objmens.getDirecciondestino());
			Objetomjn.setTiev2(Objtien.getTipoenvio2());
			/*Objetomjn.setDistrito(Objdoc.getExpediente().getCliente().getUbigeoPrincipal().getNombre());
			Objetomjn.setProvincia(Objdoc.getExpediente().getCliente().getUbigeoPrincipal().getProvincia().getNombre());
			Objetomjn.setDepartamento(Objdoc.getExpediente().getCliente().getUbigeoPrincipal().getProvincia().getDepartamento().getNombre());*/
         Objetomjn.setDepartamento(Objmens.getDepartamento());
         Objetomjn.setProvincia(Objmens.getProvincia());

         Objetomjn.setReferenciaDireccionCliente(Objmens.getReferenciaDireccionCliente());

         Objetomjn.setDistrito(Objmens.getDistrito());
			setObjmjda(Objetomjn);
			// setObMsj(Objmens);
			if(Objmens.getAviso()=='0'){
				return "Objemsj";
			}
			return "Objemsj2";
			// return "Objendatox";
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	@SuppressWarnings("unused")
	public String findkeyUser() throws Exception{
		log.debug("-> [Action] DatosAction - findkeyUser():String ");
			String Strmensa=ServletActionContext.getRequest().getParameter("Idmen");
			int Id=Integer.parseInt(Strmensa);
			Datos Objdatos=new Datos();
			MensajeriaDatos Objetomjn=new MensajeriaDatos();
			Mensajeria Objmens=new Mensajeria();
			Documento Objdoc=new Documento();
			Tipoenvio Objtien=new Tipoenvio();
			Empresadestino Objemdes=new Empresadestino();
			AmbitoEnvio Objamen=new AmbitoEnvio();
			Objdatos=SvrDt.findId(Id);
			Objmens=svrMj.ViewMensaje(Id);
			Objtien=SvrTien.findbyId(Objmens.getIdtipoenvio().getIdtipoenvio());

			if(Objdatos!=null){
				log.debug("primer if");
				Objemdes=SvrEmdes.findkey(Objdatos.getIdempresadestino().getIdempresadestino());
				Objamen= ambitoService.findId(Objdatos.getIdambitoenvio().getIdambitoenvio());
				if(Objmens.getAviso()=='0'){
					log.debug("segundo if");
					Objetomjn.setIdmen(Objmens.getIdmensajeria());
					Objetomjn.setTipocumento(Objmens.getTipodocumento());
					Objetomjn.setNumerodoc(Objmens.getNumerodocumento());
					Objetomjn.setDestinatario(Objmens.getDestinatario());
					Objetomjn.setAsunto(Objmens.getAsunto());
					Objetomjn.setFechaderiva(Objmens.getFechaderivacion());
					Objetomjn.setNuminterno(Objdatos.getNumerointerno());
					Objetomjn.setUsudestino(Objdatos.getUsuariodestino());
					Objetomjn.setDirec(Objdatos.getDireccion());
					Objetomjn.setTiev2(Objtien.getTipoenvio2());
					Objetomjn.setDepartamento(Objdatos.getDepartamento());
					Objetomjn.setProvincia(Objdatos.getProvincia());
					Objetomjn.setDistrito(Objdatos.getDistrito());
					Objetomjn.setReferencia(Objdatos.getReferencia());
					Objetomjn.setEmpdescod(Objemdes.getCodigo());
					Objetomjn.setEmpdesnom(Objemdes.getNombre());
					Objetomjn.setCodtipoambito(Objamen.getIdambitoenvio().toString());
					Objetomjn.setNomtipoambito(Objamen.getNomambitoenvio());
                                        //punto conflicto heberto
                                        Objetomjn.setReferenciaDireccionCliente(Objmens.getReferenciaDireccionCliente());
					setObjmjda(Objetomjn);
					return "Objendatox";
				}
				Objetomjn.setIdmen(Objmens.getIdmensajeria());
				Objetomjn.setTipocumento(Objmens.getTipodocumento());
				Objetomjn.setNumerodoc(Objmens.getNumerodocumento());
				Objetomjn.setDestinatario(Objmens.getDestinatario());
				Objetomjn.setAsunto(Objmens.getAsunto());
				Objetomjn.setFechaderiva(Objmens.getFechaderivacion());
				Objetomjn.setNuminterno(Objdatos.getNumerointerno());
				Objetomjn.setUsudestino(Objdatos.getUsuariodestino());
				Objetomjn.setDirec(Objdatos.getDireccion());
				Objetomjn.setDepartamento(Objdatos.getDepartamento());
				Objetomjn.setProvincia(Objdatos.getProvincia());
				Objetomjn.setDistrito(Objdatos.getDistrito());
				Objetomjn.setTiev2(Objtien.getTipoenvio2());
				Objetomjn.setReferencia(Objdatos.getReferencia());
				Objetomjn.setEmpdescod(Objemdes.getCodigo());
				Objetomjn.setEmpdesnom(Objemdes.getNombre());

                                   Objetomjn.setReferenciaDireccionCliente(Objmens.getReferenciaDireccionCliente());

				setObjmjda(Objetomjn);
				return "Objendatox2";
			}
			log.debug("afuera");
			Objmens=svrMj.ViewMensaje(Id);
            log.debug("Documento con ID [" + Objmens.getIddocumento() + "]");
			Objdoc=svrdocserv.findByIdDocumento(Objmens.getIddocumento());
			Objetomjn.setIdmen(Objmens.getIdmensajeria());
			Objetomjn.setTipocumento(Objmens.getTipodocumento());
			Objetomjn.setNumerodoc(Objmens.getNumerodocumento());
			Objetomjn.setDestinatario(Objmens.getDestinatario());
			Objetomjn.setAsunto(Objmens.getAsunto());
			Objetomjn.setFechaderiva(Objmens.getFechaderivacion());
			Objetomjn.setNuminterno(Objmens.getNumerointerno());
			Objetomjn.setDirec(Objmens.getDirecciondestino());
            Objetomjn.setDepartamento(Objmens.getDepartamento());
            Objetomjn.setProvincia(Objmens.getProvincia());
            Objetomjn.setDistrito(Objmens.getDistrito());
            Objetomjn.setTiev2(Objtien.getTipoenvio2());

               Objetomjn.setReferenciaDireccionCliente(Objmens.getReferenciaDireccionCliente());

			setObjmjda(Objetomjn);
			return "Objemsj";
	}

	// ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public void setSvrDt(DatosService svrDt){
		SvrDt=svrDt;
	}

	public DatosService getSvrDt(){
		return SvrDt;
	}

	public void setObMsj(Mensajeria obMsj){
		this.obMsj=obMsj;
	}

	public Mensajeria getObMsj(){
		return obMsj;
	}

	public void setSvrMj(MensajeriaService svrMj){
		this.svrMj=svrMj;
	}

	public MensajeriaService getSvrMj(){
		return svrMj;
	}

	public void setSvrTien(TipoenvioService svrTien){
		SvrTien=svrTien;
	}

	public TipoenvioService getSvrTien(){
		return SvrTien;
	}

	public void setSvrEmdes(EmpresadestinoService svrEmdes){
		SvrEmdes=svrEmdes;
	}

	public EmpresadestinoService getSvrEmdes(){
		return SvrEmdes;
	}

	public void setObjmjda(MensajeriaDatos objmjda){
		Objmjda=objmjda;
	}

	public MensajeriaDatos getObjmjda(){
		return Objmjda;
	}

	public void setSvrdocserv(DocumentoService svrdocserv){
		this.svrdocserv=svrdocserv;
	}

	public DocumentoService getSvrdocserv(){
		return svrdocserv;
	}

	public void setObDatos(Datos obDatos){
		this.obDatos=obDatos;
	}

	public Datos getObDatos(){
		return obDatos;
	}

	public AmbitoenvioService getAmbitoService() {
		return ambitoService;
	}

	public void setAmbitoService(AmbitoenvioService ambitoService) {
		this.ambitoService = ambitoService;
	}

	public ArchivoService getArchivoService() {
		return archivoService;
	}

	public void setArchivoService(ArchivoService archivoService) {
		this.archivoService = archivoService;
	}


}
