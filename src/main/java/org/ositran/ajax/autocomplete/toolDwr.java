/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.autocomplete;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.ositran.daos.DiafestivoDAO;
import org.ositran.daos.DocumentoDAO;
import org.ositran.daos.NumeracionDAO;
import org.ositran.daos.ParametroDAO;
import org.ositran.daos.PlantillaDAO;
import org.ositran.daos.ProcesoDAO;
import org.ositran.daos.ClienteDAO;
import org.ositran.daos.ConcesionarioDAO;
import org.ositran.services.DocumentoService;
import org.ositran.services.ExpedienteService;
import org.ositran.utils.Constantes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.btg.ositran.siged.domain.Campo;
import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Concesionario;
import com.btg.ositran.siged.domain.DiaFestivo;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Numeracion;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Proceso;

@Transactional
public class toolDwr {

        private static Logger log =  Logger.getLogger(toolDwr.class);
	private DocumentoService srvD;
	private ParametroDAO  parametroDAO  ;
	
	public ParametroDAO getParametroDAO() {
		return parametroDAO;
	}

	public void setParametroDAO(ParametroDAO parametroDAO) {
		this.parametroDAO = parametroDAO;
	}

	public DocumentoService getSrvD() {
		return srvD;
	}

	public void setSrvD(DocumentoService srvD) {
		this.srvD = srvD;
	}

	
   public Map ObtenerInfoProcess(String idProceso) {

      Map reply = new HashMap();

      try {
         WebContext ctx = WebContextFactory.get();
         ProcesoDAO pdao = (ProcesoDAO) WebApplicationContextUtils.getWebApplicationContext(ctx.getSession().getServletContext()).getBean("procesoDAO");
         Proceso objP = pdao.findByIdProceso(Integer.valueOf(idProceso));

         reply.put("resposable", objP.getResponsable().getNombres() + " " + objP.getResponsable().getApellidos());
         reply.put("unidad", objP.getResponsable().getUnidad().getNombre());
         
      } catch (Exception e) {
         reply.put("resposable", "");
         reply.put("unidad", "");
      }   

      return reply;
   }
   
   public Map ObtenerInfoNumeracion(String idProceso , String idtipodoc) {
 
	      Map reply = new HashMap();

	      try {
	         WebContext ctx = WebContextFactory.get();
	         ProcesoDAO pdao = (ProcesoDAO) WebApplicationContextUtils.getWebApplicationContext(ctx.getSession().getServletContext()).getBean("procesoDAO");
	         NumeracionDAO ndao = (NumeracionDAO) WebApplicationContextUtils.getWebApplicationContext(ctx.getSession().getServletContext()).getBean("numeracionDAO");
	         Proceso objP = pdao.findByIdProceso(Integer.valueOf(idProceso));
	      
	         Numeracion num = ndao.findByIdNumeracion(objP.getResponsable().getUnidad().getIdunidad(), new Integer(idtipodoc)) ; 
	         reply.put("numero",this.replaceFormat(num.getFormato(), num.getNumeroactual())) ;
	             
	      } catch (Exception e) {
	         reply.put("numero", "");

	      }

	      return reply;
	   }
    
   public static String replaceFormat (String formato ,  Integer  numero) {
	   
	    
	   return formato.replace("$NUMERO", ""+numero);  
	   
   } 
   

   public Map ObtenerInfoCliente(String dni, Integer idTipoIdentif) {
      Map reply = new HashMap();
      try {
         WebContext ctx = WebContextFactory.get();
         ClienteDAO sdao = (ClienteDAO) WebApplicationContextUtils.getWebApplicationContext(ctx.getSession().getServletContext()).getBean("clienteDAO");

         log.debug("parametros dni:"+dni+" idTipoIdentif:"+idTipoIdentif);
         Cliente objC = sdao.findByCriteria(dni, idTipoIdentif);

         if (objC != null) {

            reply.put("idcliente", objC.getIdCliente());
/*
            reply.put("razonsocial", objC.getRazonSocial());
            reply.put("representantelegal", objC.getRepresentanteLegal());
            reply.put("direccionp", objC.getDireccionPrincipal());
            reply.put("iddepartmento", objC.getUbigeoPrincipal().getProvincia().getDepartamento().getIddepartamento().toString());
            reply.put("departmento", objC.getUbigeoPrincipal().getProvincia().getDepartamento().getNombre());
            reply.put("idprovincia", objC.getUbigeoPrincipal().getProvincia().getIdprovincia().toString());
            reply.put("provincia", objC.getUbigeoPrincipal().getProvincia().getNombre());
            reply.put("iddistrito", objC.getUbigeoPrincipal().getIddistrito().toString());
            reply.put("distrito", objC.getUbigeoPrincipal().getNombre());
            reply.put("telefono", objC.getTelefono());
            reply.put("correo", objC.getCorreo());
 */
         }
      } catch (Exception e) {

    	  log.error(e.getMessage(), e);
    	  reply.put("idcliente", "");

         reply.put("razonsocial", "");
         reply.put("direccionp", "");
         reply.put("iddepartmento", "");
         reply.put("departmento", "");
         reply.put("idprovincia", "");
         reply.put("provincia", "");
         reply.put("iddistrito", "");
         reply.put("distrito", "");
      }

      return reply;
   }

   public Map ObtenerInfoConcesionario(String ruc) {
      Map reply = new HashMap();

      try {
         WebContext ctx = WebContextFactory.get();
         ConcesionarioDAO daoC = (ConcesionarioDAO) WebApplicationContextUtils.getWebApplicationContext(ctx.getSession().getServletContext()).getBean("concesionarioDAO");
         Concesionario objC = daoC.findByRUC(ruc);

         if (objC != null) {
            reply.put("idconcesionario", objC.getIdConcesionario());
            reply.put("razonsocial", objC.getRazonSocial());
            reply.put("direccion", objC.getDireccion());
            reply.put("correo", objC.getCorreo());
         }
      } catch (Exception e) {
         reply.put("idconcesionario", "");
         reply.put("razonsocial", "");
         reply.put("direccion", "");
         reply.put("correo", "");
      }

      return reply;
   }

   public List ObtenerlistaCampos(String tipoPlantilla) {

      List reply = new ArrayList();

      WebContext ctx = WebContextFactory.get();
      PlantillaDAO sdao = (PlantillaDAO) WebApplicationContextUtils.getWebApplicationContext(ctx.getSession().getServletContext()).getBean("plantillaDAO");
        
      reply = sdao.listCamposByTipoPlantilla(new Integer(tipoPlantilla));
 
      if (reply == null) {
         reply = new ArrayList();
         log.debug("$$$$$$$ vacio : ");
      } else {
         log.debug("$$$$$$$ tamaño : " + reply.size());
      }

      return reply;
   }

   public List ObtenerlistaCampos2(String tipoPlantilla, String idexpediente) {
      log.debug("Tipo de Plantilla con ID [" + tipoPlantilla + "]");
      log.debug("Expediente asociado con ID [" + idexpediente + "]");

      List<Campo> lstCampo = null;
      Expediente objE = null;

      WebContext ctx = WebContextFactory.get();
      PlantillaDAO sdao = (PlantillaDAO) WebApplicationContextUtils.getWebApplicationContext(ctx.getSession().getServletContext()).getBean("plantillaDAO");
      ExpedienteService srvE = (ExpedienteService) WebApplicationContextUtils.getWebApplicationContext(ctx.getSession().getServletContext()).getBean("expedienteService");

      lstCampo = sdao.listCamposByTipoPlantilla(new Integer(tipoPlantilla));

      if (!idexpediente.isEmpty()) {
         objE = srvE.findByIdExpediente(Integer.valueOf(idexpediente));
      }

      if (lstCampo == null) {
         lstCampo = new ArrayList();
         log.debug("$$$$$$$ vacio : ");
      } else {
         log.debug("$$$$$$$ tamaño : " + lstCampo.size());

         for (int i = 0; i < lstCampo.size(); i++) {
            String sDatoValor = "";

            if (objE != null) {
               if (lstCampo.get(i).getValorDefecto().equals("Propietario del Documento")) {
                  sDatoValor = objE.getDocumentoPrincipal().getPropietario().getNombres() + " " + objE.getDocumentoPrincipal().getPropietario().getApellidos();
               } else if (lstCampo.get(i).getValorDefecto().equals("Area del Propietario del Documento")) {
                  sDatoValor = objE.getDocumentoPrincipal().getPropietario().getUnidad().getNombre();
               }
               else if (lstCampo.get(i).getValorDefecto().equals("Cliente")) {
                  if (objE.getCliente().getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
                     sDatoValor = objE.getClienterazonsocial();
                  } else {
                     sDatoValor = objE.getClientenombres() + " " + objE.getClienteapellidopaterno();
                  }
               } else if (lstCampo.get(i).getValorDefecto().equals("Concesionario")) {
                  if (objE.getConcesionario() != null) {
                     sDatoValor = objE.getConcesionario().getRazonSocial();
                  }
               } else if (lstCampo.get(i).getValorDefecto().equals("Proceso")) {
                  sDatoValor = null; //objE.getProceso().getNombre();
               } else if (lstCampo.get(i).getValorDefecto().equals("Etapa")) {
                  if (objE.getIdetapa() != null) {
                     sDatoValor = objE.getIdetapa().getDescripcion();
                  }
               } else if (lstCampo.get(i).getValorDefecto().equals("Actividad")) {
                  if (objE.getIdactividad() != null) {
                     sDatoValor = objE.getIdactividad().getNombre();
                  }
               } else if (lstCampo.get(i).getValorDefecto().equals("Nro Expediente")) {
                  sDatoValor = objE.getNroexpediente();
               } else if (lstCampo.get(i).getValorDefecto().equals("Descripcion")) {
                  sDatoValor = objE.getDescripcion();
               } else if (lstCampo.get(i).getValorDefecto().equals("Asunto del Expediente")) {
                  sDatoValor = objE.getAsunto();
               } else if (lstCampo.get(i).getValorDefecto().equals("Contenido")) {
                  sDatoValor = objE.getContenido();
               } else if (lstCampo.get(i).getValorDefecto().equals("Observacion")) {
                  sDatoValor = objE.getObservacion();
               } else if (lstCampo.get(i).getValorDefecto().equals("Fecha Creacion")) {
                  sDatoValor = objE.getFechacreacion().toString();
               } else if (lstCampo.get(i).getValorDefecto().equals("Estado")) {
                  sDatoValor = String.valueOf(objE.getEstado());
               } else if (lstCampo.get(i).getValorDefecto().equals("Observacion de Rechazo")) {
                  sDatoValor = objE.getObservacionrechazo();
               } else if (lstCampo.get(i).getValorDefecto().equals("Nro Interno")) {
                  sDatoValor = objE.getNrointerno();
               } else if (lstCampo.get(i).getValorDefecto().equals("Responsable del Expediente")) {
                  sDatoValor = objE.getIdpropietario().getNombres() + " " + objE.getIdpropietario().getApellidos();
               } else if (lstCampo.get(i).getValorDefecto().equals("Area del Responsable del Expediente")) {
                  sDatoValor = objE.getIdpropietario().getUnidad().getNombre();
               } else if (lstCampo.get(i).getValorDefecto().equals("Responsable del Proceso")) {
                  sDatoValor = null;//objE.getProceso().getResponsable().getNombres() + " " + objE.getProceso().getResponsable().getApellidos();
               } else if (lstCampo.get(i).getValorDefecto().equals("Area del Responsable del Proceso")) {
                  sDatoValor = null;//objE.getProceso().getResponsable().getUnidad().getNombre();
               }
            }

            lstCampo.get(i).setValor(sDatoValor == null ? "" : sDatoValor);
         }
      }

      return lstCampo;
   }

   public String calculaFechaLimite(String diasLimiteAtencion,String horasLimiteAtencion,String destinatario){
	   log.debug("dia: "+diasLimiteAtencion);
	   log.debug("hora: "+horasLimiteAtencion);
	   try{
		   int dias=0;
		   int horas=0;
		   int idDestinatario=0;
		   if(destinatario!=null && !destinatario.equals("")){
			   if(!diasLimiteAtencion.equals("")){
				   dias=Integer.parseInt(diasLimiteAtencion);
			   }
			   if(!horasLimiteAtencion.equals("")){
				   horas=Integer.parseInt(horasLimiteAtencion);
			   }
			   idDestinatario=Integer.parseInt(destinatario);
			   SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
			   return sdf.format(calcularFechaLimite(dias,horas,idDestinatario));
		   }
		   return "";
	   }catch(NumberFormatException e){
		   log.error("La cantidad de dias y/o horas deben ser numeros.",e);
		   return null;
	   }
   }
   
   public String calculaFechaLimite2(String diasLimiteAtencion,String horasLimiteAtencion,String destinatario,Integer idDocumento){
	   log.debug("diaaa: "+diasLimiteAtencion);
	   log.debug("horaaa: "+horasLimiteAtencion);
	   try{
		   WebContext ctx = WebContextFactory.get();
	       DocumentoService srvDoc = (DocumentoService) WebApplicationContextUtils.getWebApplicationContext(ctx.getSession().getServletContext()).getBean("documentoService");
		   int dias=0;
		   int horas=0;
		   int idDestinatario=0;
		   if(destinatario!=null && !destinatario.equals("")){
			   if(!diasLimiteAtencion.equals("")){
				   dias=Integer.parseInt(diasLimiteAtencion);
			   }
			   if(!horasLimiteAtencion.equals("")){
				   horas=Integer.parseInt(horasLimiteAtencion);
			   }
			   idDestinatario=Integer.parseInt(destinatario);
			   SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm");
			   Date fechaLimite=srvDoc.getFechaLimiteAtencion(idDocumento);
			   Date fechaCalculada=calcularFechaLimite(dias,horas,idDestinatario);
			   return sdf.format(fechaCalculada)+"|"+(fechaCalculada.getTime()-fechaLimite.getTime());
		   }
		   return "";
	   }catch(NumberFormatException e){
		   log.error("La cantidad de dias y/o horas deben ser numeros.",e);
		   return null;
	   }
   }
   
   public int calcularEnFechaTexto(Integer idDoc,String fecha){
	   log.debug("en 2da opcion");
	   if(idDoc==null){
		   return 0;
	   }
	   try {
			   SimpleDateFormat fmt=new SimpleDateFormat("dd/MM/yyyy");
			   Date fechaTest=fmt.parse(fecha);
			   WebContext ctx = WebContextFactory.get();
			   DocumentoService srvDoc = (DocumentoService) WebApplicationContextUtils.getWebApplicationContext(ctx.getSession().getServletContext()).getBean("documentoService");
			   Date fechaLimite=srvDoc.getFechaLimiteAtencion(idDoc);
			   log.debug("Fecha T:"+fechaTest+" - Fecha L:"+fechaLimite);
			   return (fechaTest.getTime()-fechaLimite.getTime())>0?1:-1;
		} catch (ParseException e) {
			return 0;
		}
   }
   
   /**
    * Calcula la nueva fecha a partir de la fecha actual 
    * y el offset de dias y horas ingresado.
    * 
    * @author Germán Enríquez
    * @param dias
    * @param horas
    * @return la nueva fecha
    */
   public static Date calcularFechaLimite(int dias,int horas,int idDestinatario){
	   WebContext ctx = WebContextFactory.get();
	   ParametroDAO parametroDAO;
	   ApplicationContext context=null;
	   ////aca cuando hacen null y cargan de nuevo el contexto se demora un eggg x q  michigan hacen esto ...esto hace q se demore un monton cuando se lo llama desde derivar
	   // y por esto ya no regresa al jsp y no cier
	   if(ctx==null){
		   context=new ClassPathXmlApplicationContext("../applicationContext.xml");
		   parametroDAO=(ParametroDAO) context.getBean("parametroDAO");
	   }
	   else
		   parametroDAO=(ParametroDAO) WebApplicationContextUtils.getWebApplicationContext(ctx.getSession().getServletContext()).getBean("parametroDAO");
	   String horaInicio=parametroDAO.findByTipo(Constantes.INICIO_ENVIO).get(0).getValor();
	   String horaFin=parametroDAO.findByTipo(Constantes.FIN_ENVIO).get(0).getValor();
	   // hallamos las horas y minutos que delimitan el horario de envio
	   int horaInicioHoras=Integer.parseInt(horaInicio.substring(0,horaInicio.indexOf(':')));
	   int horaInicioMinutos=Integer.parseInt(horaInicio.substring(horaInicio.indexOf(':')+1));
	   int horaFinHoras=Integer.parseInt(horaFin.substring(0,horaFin.indexOf(':')));
	   int horaFinMinutos=Integer.parseInt(horaFin.substring(horaFin.indexOf(':')+1));
	   Calendar hoy=Calendar.getInstance();
	   Calendar nuevo=Calendar.getInstance();
	   int horaInicial=hoy.get(Calendar.HOUR_OF_DAY);
	   int nuevaHora=horaInicial+horas;
	   int minutos=0;
	   // calculamos los dias que agregaremos si la cantidad de horas supera la hora limite
	   while(nuevaHora>horaFinHoras){
		   int difHoras=horaFinHoras-horaInicial;
		   horas-=difHoras;
		   dias++;
		   horaInicial=horaInicioHoras;
		   nuevaHora=horaInicial+horas;			   
		   minutos=horaInicioMinutos;
	   }
	   // si la hora limite es igual a la nueva hora calculada, nos fijamos en los minutos
	   if(nuevaHora==horaFinHoras && hoy.get(Calendar.MINUTE)+minutos>horaFinMinutos){
		   dias++;
		   nuevaHora=horaInicioHoras;
		   minutos=horaInicioMinutos;
	   }
	   // para los dias feriados	   
	   DiafestivoDAO diaFestivoDAO;
	   if(ctx==null)
		   diaFestivoDAO=(DiafestivoDAO) context.getBean("diafestivoDAO");
	   else
		   diaFestivoDAO=(DiafestivoDAO) WebApplicationContextUtils.getWebApplicationContext(ctx.getSession().getServletContext()).getBean("diafestivoDAO");
	   List<DiaFestivo> festivos=diaFestivoDAO.getDiasFestivosPorUsuario();
	   // para los fines de semana
	   Calendar temp=Calendar.getInstance();		   
	   for(int i=0;i<=dias;i++){		   
		   // si es sabado o domingo
		   /*if(temp.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || temp.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
			   dias++;
		   // si es dia festivo
		   else */if(esDiaFestivo(temp,festivos))
			   dias++;
		   temp.add(Calendar.DAY_OF_MONTH,1);
	   }
	   nuevo.add(Calendar.DAY_OF_MONTH,dias);
	   nuevo.set(Calendar.HOUR_OF_DAY,nuevaHora);
	   nuevo.add(Calendar.MINUTE,minutos);
	   return nuevo.getTime();
   }
   
   private static boolean esDiaFestivo(Calendar dia,List<DiaFestivo> festivos){
	   for(DiaFestivo actual:festivos){
		   Calendar festivo=Calendar.getInstance();
		   festivo.setTime(actual.getFechanolaborable());
		   if(dia.get(Calendar.YEAR)==festivo.get(Calendar.YEAR) 
				   && dia.get(Calendar.MONTH)==festivo.get(Calendar.MONTH)
				   && dia.get(Calendar.DAY_OF_MONTH)==festivo.get(Calendar.DAY_OF_MONTH))
			   return true;
	   }
	   return false;
   }

   
   public  String calculaFechaLimiteOld(String sDiaLimiteAtencion, String  shorasLimiteAtencion) {
	   
	   log.debug("dia: "+sDiaLimiteAtencion);
	   log.debug("hora: "+shorasLimiteAtencion.toString());
	   
	   SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
	   SimpleDateFormat formatoString= new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
	   Date trialTime = new Date();
	   Calendar calendar = new GregorianCalendar();
	   calendar.setTime(trialTime);
	   String fechaLimete="";
	   WebContext ctx = WebContextFactory.get();
	   DocumentoDAO documentoDAO = (DocumentoDAO) WebApplicationContextUtils.getWebApplicationContext(ctx.getSession().getServletContext()).getBean("documentoDAO");
	   int hora;
	   ParametroDAO parametroDAO = (ParametroDAO) WebApplicationContextUtils.getWebApplicationContext(ctx.getSession().getServletContext()).getBean("parametroDAO");
	   Parametro p = parametroDAO.findByTipo(Constantes.INICIOHORARIO ).get(0);
	   
        int horainicio = new Integer(p.getValor()) ;
        
        log.debug("hora de inicio= "+horainicio);
        
        p =  parametroDAO.findByTipo(Constantes.FINHORARIO).get(0)  ;
        int horafin = new Integer(p.getValor()) ;
        
        log.debug("hora de fin= "+horafin);
        int horaTotalTrabajo = horafin -horainicio;
        log.debug("hora total de Trabajo= "+horaTotalTrabajo);
        
        
	   if(!sDiaLimiteAtencion.equals("")){
		   int dia = new Integer(sDiaLimiteAtencion);
		   
		   hora =0;
		   if(shorasLimiteAtencion.equals("")){
			   hora =0;
		   }else{
			   hora =new Integer(shorasLimiteAtencion);
		   }
		   int horasxdia = (dia * 24) + hora;
	       String fechaInicio = formatoFecha.format(calendar.getTime());
	       	calendar.add(Calendar.HOUR_OF_DAY, horasxdia);
	       String fechaFinal = formatoFecha.format(calendar.getTime());
	       String diasNoAbiles = documentoDAO.diasNoLaborables(fechaInicio, fechaFinal);
	       if(!diasNoAbiles.equals("0")){
	    	   return fechaLimite(diasNoAbiles, horasxdia, horaTotalTrabajo);
	       }
	       log.debug("fecha Limete="+fechaFinal);
	      return formatoString.format(calendar.getTime());	       
	   }return fechaLimete;
	  }

   
	 private String fechaLimite(String diasNoAbiles, int horasxdia, int horaTotal){
		 Calendar fechaLinAten = new GregorianCalendar();
		 //SimpleDateFormat formatoString= new SimpleDateFormat("dd/MM/yyyy");
		 SimpleDateFormat formatoString= new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
		 Date trialTime = new Date();
	         int horasLaborables = horasxdia + (Integer.parseInt(diasNoAbiles) * 24);
	         fechaLinAten.setTime(trialTime);
	         fechaLinAten.add(Calendar.HOUR_OF_DAY, horasLaborables);
	         log.debug("fecha Limete=="+fechaLinAten.getTime().toString());
	         return formatoString.format(fechaLinAten.getTime());
	
	 }

}
