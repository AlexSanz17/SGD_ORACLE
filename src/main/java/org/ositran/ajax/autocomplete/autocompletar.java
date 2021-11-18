/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ajax.autocomplete;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ajaxtags.helpers.AjaxXmlBuilder;
import org.ajaxtags.servlets.BaseAjaxServlet;
import org.apache.log4j.Logger;
import org.ositran.ajax.beans.carpeta;
import org.ositran.ajax.ubigeo.data.DAOAutocomplete;
import org.ositran.ajax.ubigeo.data.DAOMapeo;
import org.ositran.daos.CarpetaDAO;
import org.ositran.daos.ConcesionarioDAO;
import org.ositran.daos.EstadocargoDAO;
import org.ositran.daos.ProcesoDAO;
import org.ositran.daos.ClienteDAO;
import org.ositran.daos.DepartamentoDAO;
import org.ositran.daos.DistritoDAO;
import org.ositran.daos.MotivoDAO;
import org.ositran.daos.ProvinciaDAO;
import org.ositran.daos.SalaDAO;
import org.ositran.daos.SubmotivoDAO;
import org.ositran.daos.TipodocumentoDAO;
import org.ositran.daos.TipoidentificacionDAO;
import org.ositran.daos.UnidadDAO;
import org.ositran.daos.UnidadpesoDAO;
import org.ositran.daos.UsuarioDAO;
import org.ositran.daos.AnioDAO;
import org.ositran.daos.MesDAO;
import org.ositran.daos.UsuariostorDAO;
import org.ositran.utils.Constantes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.btg.ositran.siged.domain.Carpeta;
import com.btg.ositran.siged.domain.Sala;
import com.btg.ositran.siged.domain.Usuario;

public class autocompletar extends BaseAjaxServlet {

   private static final long serialVersionUID = 1L;
   private static Logger log = Logger.getLogger(autocompletar.class);

   public String getXmlContent(HttpServletRequest request, HttpServletResponse response) throws Exception {
      String metodo = request.getParameter("metodo");

      List list = new ArrayList();
      String param1 = null;
      String param2 = null;

      if (metodo.equals("ObtenerSala")) {
         String sala = request.getParameter("sala");
         SalaDAO daoSal = (SalaDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("salaDAO");
         List lstS = daoSal.findAll();
         list = DAOMapeo.ObtenerSalas(lstS);
         list = DAOAutocomplete.AutocompletarSala(sala, list);

         param1 = "sala";
         param2 = "idsala";
      } else if (metodo.equals("ObtenerMotivo")) {
         String motivo = request.getParameter("nmotivo");
         MotivoDAO daoMot = (MotivoDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("motivoDAO");
         List lstM = daoMot.findAll();
         list = DAOMapeo.ObtenerMotivos(lstM);
         list = DAOAutocomplete.AutocompletarMotivo(motivo, list);

         param1 = "nmotivo";
         param2 = "idmotivo";
      } else if (metodo.equals("ObtenerSubmotivo")) {
         String submotivo = request.getParameter("nsubmotivo");
         String idmotivo = request.getParameter("idmotivo");
         SubmotivoDAO daoSubmot = (SubmotivoDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("submotivoDAO");
         List lstSM = daoSubmot.findByMotivo(Integer.valueOf(idmotivo));
         list = DAOMapeo.ObtenerSubmotivos(lstSM);
         list = DAOAutocomplete.AutocompletarSubmotivo(submotivo, list);

         param1 = "nsubmotivo";
         param2 = "idsubmotivo";
      } else if (metodo.equals("ObtenerDepartamento")) {
         String departamento = request.getParameter("departamento");
         DepartamentoDAO daoDpto = (DepartamentoDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("departamentoDAO");
         List lstD = daoDpto.findAll();
         list = DAOMapeo.ObtenerDepartamentos(lstD);
         list = DAOAutocomplete.AutocompletarDepartamento(departamento, list);

         param1 = "departamento";
         param2 = "iddepartamento";
      } else if (metodo.equals("ObtenerProvincia")) {
         String provincia = request.getParameter("provincia");
         String iddepartamento = request.getParameter("iddepartamento");
         ProvinciaDAO daoProv = (ProvinciaDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("provinciaDAO");
         List lstP = daoProv.findByDepartamento(Integer.valueOf(iddepartamento));
         list = DAOMapeo.ObtenerProvincias(lstP);
         list = DAOAutocomplete.AutocompletarProvincia(provincia, list);

         param1 = "provincia";
         param2 = "idprovincia";
      } else if (metodo.equals("ObtenerDistrito")) {
         String distrito = request.getParameter("distrito");
         String idprovincia = request.getParameter("idprovincia");
         DistritoDAO daoDist = (DistritoDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("distritoDAO");
         List lstD = daoDist.findByProvincia(Integer.valueOf(idprovincia));
         list = DAOMapeo.ObtenerDistritos(lstD);
         list = DAOAutocomplete.AutocompletarDistrito(distrito, list);

         param1 = "distrito";
         param2 = "iddistrito";
      } else if (metodo.equals("ObtenerProceso")) {
         String proceso = request.getParameter("proceso");
         ProcesoDAO pdao = (ProcesoDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("procesoDAO");
         List lstP = pdao.findAll();
         list = DAOMapeo.ObtenerProcesos(lstP);
         list = DAOAutocomplete.AutocompletarProceso(proceso, list);

         param1 = "proceso";
         param2 = "idproceso";
      } else if (metodo.equals("ObtenerProcesoResposable")) {
         String proceso = request.getParameter("proceso");
         Integer idusuario = (Integer)request.getSession().getAttribute("idusuario");

         log.debug("ObtenerProcesoResposable idusuario:"+idusuario) ;

         ProcesoDAO pdao = (ProcesoDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("procesoDAO");
         List lstP = pdao.findByIdResponsableOrAsistente(idusuario, Constantes.ESTADO_ACTIVO );
                                    
         list = DAOMapeo.ObtenerProcesos(lstP);
         list = DAOAutocomplete.AutocompletarProceso(proceso, list);

         log.debug("ObtenerProcesoResposable list.size :"+(list!=null?list.size():0)) ;
              
         param1 = "proceso";
         param2 = "idproceso";
      } else if (metodo.equals("ObtenerConcesionario")) {
         String concesionario = request.getParameter("concesionario");
         ConcesionarioDAO cdao = (ConcesionarioDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("concesionarioDAO");
         List lstC = cdao.findAll();
         list = DAOMapeo.ObtenerConcesionarios(lstC);
         list = DAOAutocomplete.AutocompletarConcesionario(concesionario, list);
         param1 = "concesionario";
         param2 = "idconcesionario";
      } else if (metodo.equals("ObtenerTipoDoc")) {
         String tipodocumento = request.getParameter("tipodocumento");
         TipodocumentoDAO tddao = (TipodocumentoDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("tipodocumentoDAO");
         List lstTD = tddao.findAllwithPlantilla();  
         list = DAOMapeo.ObtenerTipoDocumentos(lstTD);
         list = DAOAutocomplete.AutocompletarTipoDocumento(tipodocumento, list);

         param1 = "tipodocumento";
         param2 = "idtipodocumento";

      } else if (metodo.equals("ObtenerAllTipoDoc")) {
          String tipodocumento = request.getParameter("tipodocumento");
          TipodocumentoDAO tddao = (TipodocumentoDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("tipodocumentoDAO");
          List lstTD = tddao.findAll();  
          list = DAOMapeo.ObtenerTipoDocumentos(lstTD);
          list = DAOAutocomplete.AutocompletarTipoDocumento(tipodocumento, list);

          param1 = "tipodocumento";
          param2 = "idtipodocumento";

       }else if (metodo.equals("ObtenerTipoDocIdent")) {
         String tipoidentificacion = request.getParameter("tipoidentificacion");
         TipoidentificacionDAO tidao = (TipoidentificacionDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("tipoidentificacionDAO");
         List lstTI = tidao.findAll();
         list = DAOMapeo.ObtenerTipoIdentificacion(lstTI);
         list = DAOAutocomplete.AutocompletarTipoIdentificacion(tipoidentificacion, list);

         param1 = "tipoidentificacion";
         param2 = "idtipoidentificacion";

      } else if (metodo.equals("ObtenerNroIdent")) {
         String nroidentificacion = request.getParameter("nroidentificacion");
         Integer idtipoidentificacion = Integer.valueOf(request.getParameter("idtipoidentificacion"));
         ClienteDAO sdao = (ClienteDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("clienteDAO");
         List lstS = sdao.findByTipoIdentificacionList(idtipoidentificacion, null);
         list = DAOMapeo.ObtenerNroIdentificacion(lstS);
         list = DAOAutocomplete.AutocompletarNroIdentificacion(nroidentificacion, list);

         param1 = "nroidentificacion";
         param2 = "nroidentificacion";
      } else if (metodo.equals("ObtenerDestinatario")) {
    	 log.debug("methodo Obtener Destinatario ");
         String destinatario = request.getParameter("destinatario");
         String strProceso = request.getParameter("proceso");
         log.debug("param=["+destinatario+" , "+strProceso+"]");

         ProcesoDAO pdao = (ProcesoDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("procesoDAO");
         //List lstD = udao.findByRolList("user");
         List lstD = pdao.findByIdProceso(Integer.valueOf(strProceso)).getUsuarioList();
         //List lstD = udao.findParticipanteXProceso(Integer.valueOf(strProceso));
         list = DAOMapeo.ObtenerDestinatario(lstD);
         list = DAOAutocomplete.AutocompletarDestinatario(destinatario, list);

         param1 = "destinatario";
         param2 = "iddestinatario";
      } else if (metodo.equals("ObtenerCCDestinatario")) {
         String ccdestinatario = request.getParameter("ccdestinatario");
         UsuarioDAO udao = (UsuarioDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("usuarioDAO");
         List<Usuario> lstCCD = udao.findByRol(Constantes.ROL_USUARIO_FINAL);
         list = DAOMapeo.ObtenerCCDestinatario(lstCCD);
         list = DAOAutocomplete.AutocompletarCCDestinatario(ccdestinatario, list);

         param1 = "ccdestinatario";
         param2 = "idccdestinatario";
      } else if (metodo.equals("obtenerEspecialidad")) {
         String especialidad = request.getParameter("unidad");
         UnidadDAO unidadDAO = (UnidadDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("unidadDAO");
         List unidadesList = unidadDAO.findAll();
         list = DAOMapeo.obtenerUnidad(unidadesList);
         list = DAOAutocomplete.AutocompletarUnidad(especialidad, list);

         param1 = "unidad";
         param2 = "idUnidad";
      } else if (metodo.equals("obtenerAnalista")) {
         String unidad = request.getParameter("idunidad");
         String analista = request.getParameter("analista");

         UsuarioDAO udao = (UsuarioDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("usuarioDAO");
         List analistaList = udao.getUsuariosByUnidad(unidad);

         list = DAOMapeo.ObtenerAnalista(analistaList);
         list = DAOAutocomplete.AutocompletarAnalista(analista, list);

         param1 = "analista";
         param2 = "idanalista";
      } else if (metodo.equals("ObtenerRegion")) {
         String region = request.getParameter("region");
         DepartamentoDAO rdao = (DepartamentoDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("departamentoDAO");
         List lstR = rdao.findAll();  
         list = DAOMapeo.ObtenerRegion(lstR);
         list = DAOAutocomplete.AutocompletarRegion(region, list);

         param1 = "region";
         param2 = "idregion";
      } else if (metodo.equals("ObtenerAnio")) {
         String anio = request.getParameter("anio");
         AnioDAO andao = (AnioDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("anioDAO");
         List lstA = andao.buscarLst();
         list = DAOMapeo.ObtenerAnio(lstA);
         list = DAOAutocomplete.AutocompletarAnio(anio, list);

         param1 = "anio";
         param2 = "idanio";

      } else if (metodo.equals("ObtenerUnidadpeso")) {
         String unipeso = request.getParameter("unidadpeso");
         UnidadpesoDAO unipdao = (UnidadpesoDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("unidadpesoDAO");
         List lstA = unipdao.findAll();
         list = DAOMapeo.ObtenerUnidadpeso(lstA);
         list = DAOAutocomplete.AutocompletarUnidadpeso(unipeso, list);

         param1 = "nombreunidad";
         param2 = "idunidadpeso";
      } else if (metodo.equals("ObtenerMes")) {
         String mes = request.getParameter("mes");
         MesDAO msdao = (MesDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("mesDAO");
         List lstMs = msdao.findAll();
         list = DAOMapeo.ObtenerMes(lstMs);
         list = DAOAutocomplete.AutocompletarMes(mes, list);

         param1 = "mes";
         param2 = "idmes";
      } else if (metodo.equals("obtenerAnalistasStor")) {
         String analista = request.getParameter("analista");
         String sala = request.getParameter("idsala");
         String otrasala = request.getParameter("otrasala");
         log.info("PARAMETROS ==> analista:" + analista + " idsala:" + sala + " otrasala: " + otrasala);
         SalaDAO salaDAO = (SalaDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("salaDAO");
         UsuariostorDAO usDao = (UsuariostorDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("usuariostorDAO");
         List<Sala> lstSalas = salaDAO.findAll();
         log.info("CANTIDAD DE SALAS: " + lstSalas.size());

         List lstAnalistas = null;
         if (otrasala.equalsIgnoreCase("si")) {
            log.info("SELECCIONAR ANALISTAS DE OTRA SALA");
            for (Sala objSala : lstSalas) {
               if (!objSala.getIdsala().toString().equalsIgnoreCase(sala)) {
                  log.info("LISTANDO ANALISTAS DE LA SALA: " + objSala.getIdsala().toString());
                  if (lstAnalistas == null) {
                     lstAnalistas = usDao.getAnalistasBySala(objSala.getIdsala().toString());
                  } else {
                     List lstAnalistasAux = usDao.getAnalistasBySala(objSala.getIdsala().toString());
                     log.info("ANALISTA ANTES DE FOR: " + lstAnalistas.size());
                     for (int i = 0; i < lstAnalistasAux.size(); i++) {
                        lstAnalistas.add(lstAnalistasAux.get(i));
                     }
                     log.info("ANALISTA ANTES DE FOR: " + lstAnalistas.size());
                  }
               }
            }
         } else {
            log.info("SELECCIONAR ANALISTAS DE LA SALA SELECCIONADA");
            lstAnalistas = usDao.getAnalistasBySala(sala);
         }
         list = DAOMapeo.obtenerAnalistaStor(lstAnalistas);
         list = DAOAutocomplete.autocompletarAnalistaStor(analista, list);

         param1 = "analista";
         param2 = "idanalista";
      } else if (metodo.equals("obtenerRevisorTecnicoStor")) {
         String revisortecnico = request.getParameter("revisortecnico");
         String sala = request.getParameter("idsala");         

         UsuariostorDAO usDao = (UsuariostorDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("usuariostorDAO");
         List lstRevTecs = usDao.getRevisoresTecnicosBySala(sala);
         list = DAOMapeo.obtenerRevisorTecnicoStor(lstRevTecs);
         list = DAOAutocomplete.autocompletarRevisorTecnicoStor(revisortecnico, list);

         param1 = "revisortecnico";
         param2 = "idrevisortecnico";
      } else if (metodo.equals("obtenerRevisorLegalStor")) {
         String revisorlegal = request.getParameter("revisorlegal");
         String sala = request.getParameter("idsala");

         UsuariostorDAO usDao = (UsuariostorDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("usuariostorDAO");
         List lstRevLegs = usDao.getRevisoresLegalesBySala(sala);
         list = DAOMapeo.obtenerRevisorLegalStor(lstRevLegs);
         list = DAOAutocomplete.autocompletarRevisorLegalStor(revisorlegal, list);

         param1 = "revisorlegal";
         param2 = "idrevisorlegal";
      } else if (metodo.equals("ObtenerEstadocargo")) {
         String escarg = request.getParameter("estado");
         EstadocargoDAO ecdao = (EstadocargoDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("estadocargoDAO");
         List lstEc = ecdao.findAll();
         list = DAOMapeo.ObtenerEstadocargo(lstEc);
         list = DAOAutocomplete.AutocompletarEstadocargo(escarg, list);

         param1 = "estadocargo";
         param2 = "idestadocargo";
      }else if (metodo.equals("ObtenerCarpetasPadre")) {
    	  
    	  if (log.isDebugEnabled()) {
              StringBuilder parameters = new StringBuilder("?");
              Enumeration names = request.getParameterNames();
               while (names.hasMoreElements()){
                       String name = (String)names.nextElement();
                       parameters.append(name);
                       parameters.append("=");
                       parameters.append(request.getParameter(name));
                       if(names.hasMoreElements()){
                               parameters.append("&");
                       }
               }
               log.debug("parameters : "+parameters);
          }
    	   
          String idDocumento = request.getParameter("idDocumento");
          String nombre = request.getParameter("nombre");
          if(nombre==null) nombre  = request.getParameter("objDocumento.idcarpeta.nombre");   
          log.debug(" autocomplete nombre: "+nombre) ;   
          CarpetaDAO ecdao = (CarpetaDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("carpetaDAO");
          
          List lstEc = ecdao.findbyDocumento(new Integer (idDocumento));
          log.debug("obec0:"+lstEc.size()) ; 
          carpeta  obec  = null ;
          if(lstEc!=null&&lstEc.size()>0){
        	  Carpeta c =  (Carpeta )lstEc.get(0);    
        	    
               obec = new carpeta(); 
    	      obec.setIdcarpeta(Constantes.ID_CARPETA_EXPEDIENTE); 
    	      obec.setNombre(c.getIdexpediente().getNroexpediente()); 
    	      
    	      log.debug("obec:"+c.getIdexpediente().getNroexpediente()); 
        	  
          } else{
        	  log.debug("lstEc vacio");
          }
          list = DAOMapeo.ObtenerCarpetas(lstEc);
          if(obec!=null){
        	  list.add(obec);   
          }
         
          
          list = DAOAutocomplete.AutocompletarCarpeta( nombre, list); 
          log.debug("ObtenerCarpetasPadre.list.size : "+list.size()) ;  
          param1 = "nombre";      
          param2 = "idcarpeta";
       }

      AjaxXmlBuilder retorno = new AjaxXmlBuilder();
      String text = retorno.addItems(list, param1, param2).toString();

      return text;
   }
}
