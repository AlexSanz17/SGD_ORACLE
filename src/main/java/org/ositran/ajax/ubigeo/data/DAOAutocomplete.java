/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.ubigeo.data;

import java.util.ArrayList;
import java.util.List;
import org.ositran.ajax.beans.AnalistaStor;
import org.ositran.ajax.beans.RevisorLegalStor;
import org.ositran.ajax.beans.RevisorTecnicoStor;
import org.ositran.ajax.beans.UsuarioStorBeanAjax;
import org.ositran.ajax.beans.analista;
import org.ositran.ajax.beans.carpeta;
import org.ositran.ajax.beans.concesionario;
import org.ositran.ajax.beans.departamento;
import org.ositran.ajax.beans.destinatario;
import org.ositran.ajax.beans.distrito;
import org.ositran.ajax.beans.estadocargo;
import org.ositran.ajax.beans.motivo;
import org.ositran.ajax.beans.proceso;
import org.ositran.ajax.beans.provincia;
import org.ositran.ajax.beans.sala;
import org.ositran.ajax.beans.solicitante;
import org.ositran.ajax.beans.submotivo;
import org.ositran.ajax.beans.tipodocumento;
import org.ositran.ajax.beans.tipoidentificacion;
import org.ositran.ajax.beans.unidad;
import org.ositran.ajax.beans.region;
import org.ositran.ajax.beans.anio;
import org.ositran.ajax.beans.mes;
import org.ositran.ajax.beans.unidadpeso;

public class DAOAutocomplete{
	
	public static List<sala> AutocompletarSala(String name,List<sala> data) throws Exception{
		List<sala> l=new ArrayList<sala>();
		for(sala sal : data){
			// sala sal = (sala) iter.next();
			if(sal.getSala().toLowerCase().startsWith(name.toLowerCase())){
				l.add(sal);
			}
		}
		return l;
	}

	public static List<motivo> AutocompletarMotivo(String name,List<motivo> data) throws Exception{
		List<motivo> l=new ArrayList<motivo>();
		for(motivo mot : data){
			if(mot.getNmotivo().toLowerCase().startsWith(name.toLowerCase())){
				l.add(mot);
			}
		}
		return l;
	}

	public static List<submotivo> AutocompletarSubmotivo(String name,List<submotivo> data) throws Exception{
		List<submotivo> l=new ArrayList<submotivo>();
		for(submotivo submot : data){
			if(submot.getNsubmotivo().toLowerCase().startsWith(name.toLowerCase())){
				l.add(submot);
			}
		}
		return l;
	}

	public static List<departamento> AutocompletarDepartamento(String name,List<departamento> data) throws Exception{
		List<departamento> l=new ArrayList<departamento>();
		for(departamento dep : data){
			if(dep.getDepartamento().toLowerCase().startsWith(name.toLowerCase())){
				l.add(dep);
			}
		}
		return l;
	}

	public static List<provincia> AutocompletarProvincia(String name,List<provincia> data) throws Exception{
		List<provincia> l=new ArrayList<provincia>();
		for(provincia prov : data){
			if(prov.getProvincia().toLowerCase().startsWith(name.toLowerCase())){
				l.add(prov);
			}
		}
		return l;
	}

	public static List<distrito> AutocompletarDistrito(String name,List<distrito> data) throws Exception{
		List<distrito> l=new ArrayList<distrito>();
		for(distrito dis : data){
			if(dis.getDistrito().toLowerCase().startsWith(name.toLowerCase())){
				l.add(dis);
			}
		}
		return l;
	}

	public static List<proceso> AutocompletarProceso(String name,List<proceso> data) throws Exception{
		List<proceso> l=new ArrayList<proceso>();
		for(proceso p : data){
			if(p.getproceso().toLowerCase().startsWith(name.toLowerCase())){
				l.add(p);
			}
		}
		return l;
	}

	public static List<concesionario> AutocompletarConcesionario(String name,List<concesionario> data) throws Exception{
		List<concesionario> l=new ArrayList<concesionario>();
		for(concesionario c : data){
			if(c.getConcesionario().toLowerCase().startsWith(name.toLowerCase())){
				l.add(c);
			}
		}
		return l;
	}

	public static List<tipodocumento> AutocompletarTipoDocumento(String name,List<tipodocumento> data) throws Exception{
		List<tipodocumento> l=new ArrayList<tipodocumento>();
		for(tipodocumento td : data){
			if(td.gettipodocumento().toLowerCase().startsWith(name.toLowerCase())){
				l.add(td);
			}
		}
		return l;
	}

	public static List<tipoidentificacion> AutocompletarTipoIdentificacion(String name,List<tipoidentificacion> data) throws Exception{
		List<tipoidentificacion> l=new ArrayList<tipoidentificacion>();
		for(tipoidentificacion ti : data){
			if(ti.gettipoidentificacion().toLowerCase().startsWith(name.toLowerCase())){
				l.add(ti);
			}
		}
		return l;
	}

	public static List<solicitante> AutocompletarNroIdentificacion(String name,List<solicitante> data) throws Exception{
		List<solicitante> l=new ArrayList<solicitante>();
		for(solicitante s : data){
			if(s.getNroidentificacion().toLowerCase().startsWith(name.toLowerCase())){
				l.add(s);
			}
		}
		return l;
	}

	public static List<destinatario> AutocompletarDestinatario(String name,List<destinatario> data) throws Exception{
		List<destinatario> l=new ArrayList<destinatario>();
		for(destinatario d : data){
			if(d.getdestinatario().toLowerCase().startsWith(name.toLowerCase())){
				l.add(d);
			}
		}
		return l;
	}

	public static List<destinatario> AutocompletarCCDestinatario(String name,List<destinatario> data) throws Exception{
		List<destinatario> l=new ArrayList<destinatario>();
		for(destinatario d : data){
			if(d.getCcdestinatario().toLowerCase().startsWith(name.toLowerCase())){
				l.add(d);
			}
		}
		return l;
	}

	public static List<analista> AutocompletarAnalista(String name,List<analista> list){
		List<analista> l=new ArrayList<analista>();
		for(analista u : list){
			if(u.getAnalista().toLowerCase().startsWith(name.toLowerCase())){
				l.add(u);
			}
		}
		return l;
	}

	public static List<unidad> AutocompletarUnidad(String name,List<unidad> list){
		List<unidad> l=new ArrayList<unidad>();
		for(unidad u : list){
			if(u.getUnidad().toLowerCase().startsWith(name.toLowerCase())){
				l.add(u);
			}
		}
		return l;
	}

	public static List<region> AutocompletarRegion(String name,List<region> data) throws Exception{
		List<region> l=new ArrayList<region>();
		for(region tr : data){
			if(tr.getRegion().toLowerCase().startsWith(name.toLowerCase())){
				l.add(tr);
			}
		}
		return l;
	}

	public static List<anio> AutocompletarAnio(String name,List<anio> data) throws Exception{
		List<anio> l=new ArrayList<anio>();
		for(anio tr : data){
			if(tr.getAnio().toLowerCase().startsWith(name.toLowerCase())){
				l.add(tr);
			}
		}
		return l;
	}

	public static List<unidadpeso> AutocompletarUnidadpeso(String name,List<unidadpeso> data) throws Exception{
		List<unidadpeso> l=new ArrayList<unidadpeso>();
		for(unidadpeso tr : data){
			if(tr.getNombreunidad().toLowerCase().startsWith(name.toLowerCase())){
				l.add(tr);
			}
		}
		return l;
	}

	public static List<mes> AutocompletarMes(String name,List<mes> data) throws Exception{
		List<mes> l=new ArrayList<mes>();
		for(mes tr : data){
			if(tr.getMes().toLowerCase().startsWith(name.toLowerCase())){
				l.add(tr);
			}
		}
		return l;
	}

	public static List<UsuarioStorBeanAjax> autocompletarUsuarioStor(String name,List<UsuarioStorBeanAjax> data) throws Exception{
		List<UsuarioStorBeanAjax> l=new ArrayList<UsuarioStorBeanAjax>();
		for(UsuarioStorBeanAjax tr : data){
			if(tr.getDescripcionUsuarioStor().toLowerCase().startsWith(name.toLowerCase())){
				l.add(tr);
			}
		}
		return l;
	}

	public static List<AnalistaStor> autocompletarAnalistaStor(String name,List<AnalistaStor> data) throws Exception{
		List<AnalistaStor> l=new ArrayList<AnalistaStor>();
		for(AnalistaStor tr : data){
			if(tr.getAnalista().toLowerCase().startsWith(name.toLowerCase())){
				l.add(tr);
			}
		}
		return l;
	}

	public static List<RevisorTecnicoStor> autocompletarRevisorTecnicoStor(String name,List<RevisorTecnicoStor> data) throws Exception{
		List<RevisorTecnicoStor> l=new ArrayList<RevisorTecnicoStor>();
		for(RevisorTecnicoStor tr : data){
			if(tr.getRevisortecnico().toLowerCase().startsWith(name.toLowerCase())){
				l.add(tr);
			}
		}
		return l;
	}

	public static List<RevisorLegalStor> autocompletarRevisorLegalStor(String name,List<RevisorLegalStor> data) throws Exception{
		List<RevisorLegalStor> l=new ArrayList<RevisorLegalStor>();
		for(RevisorLegalStor tr : data){
			if(tr.getRevisorlegal().toLowerCase().startsWith(name.toLowerCase())){
				l.add(tr);
			}
		}
		return l;
	}

	public static List<estadocargo> AutocompletarEstadocargo(String name,List<estadocargo> data) throws Exception{
		List<estadocargo> l=new ArrayList<estadocargo>();
		for(estadocargo tr : data){
			if(tr.getEstadocargo().toLowerCase().startsWith(name.toLowerCase())){
				l.add(tr);
			}
		}
		return l;
	}

	public static List<carpeta> AutocompletarCarpeta(String name,List<carpeta> data) throws Exception{
		List<carpeta> l=new ArrayList<carpeta>();
		for(carpeta tr : data){
			if(tr.getNombre().toLowerCase().startsWith(name.toLowerCase())){
				l.add(tr);
			}
		}
		return l;
	}
}
