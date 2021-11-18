/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.IotdtmDocExterno;
import com.btg.ositran.siged.domain.LogOperacion;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.ActionContext;
import com.ositran.cmis.api.AlfrescoApiWs;
import gob.ositran.siged.config.SigedProperties;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.struts2.ServletActionContext;
import org.ositran.daos.ArchivoDAO;
import org.ositran.daos.DocumentoExternoVirtualDAO;
import org.ositran.services.LogOperacionService;
import org.ositran.utils.Constantes;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 *
 * @author jbengoa
 */
public class VerDocumento extends HttpServlet  {
    private static String USERCONSULTA = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_USUARIOCONSULTA);
    private static String USERCONSULTA_CLAVE=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_USUARIOCONSULTA_CLAVE);
    private static String REPOSITORIO_ID  = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_ROOTID);
    
    public void init() throws ServletException {
        
    }
    public void destroy() {
        
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        
                String accion = request.getParameter("accion");
                Session sesionAlfresco = null;
                OutputStream outStream = null;
                            
                try{
                        if (accion.equals("abrirCargoRecepcionVirtual")){
                            try{
                                WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
                                DocumentoExternoVirtualDAO recepcionDAO =  (DocumentoExternoVirtualDAO)wac.getBean("documentoExternoVirtualDAO");

                                IotdtmDocExterno recepcion = recepcionDAO.buscarDocumentoVirtual(new Integer(request.getParameter("idCodigo")));
                                if (recepcion.getSidrecext().getBcarstd()!=null){
                                    byte[] bytes = recepcion.getSidrecext().getBcarstd();
                                    response.setContentType("application/pdf");
                                    response.setContentLength(bytes.length);
                                    String headerKey = "Content-Disposition";
                                    String headerValue = String.format("inline; filename=ImportLog.pdf");
                                    response.setHeader(headerKey, headerValue);
                                    outStream = response.getOutputStream();
                                    outStream.write(bytes);
                                    outStream.flush();
                                    outStream.close();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                                if (outStream!=null){
                                    try{
                                         outStream.close();
                                    }catch(Exception ex2){
                                         ex2.printStackTrace();
                                      }
                                }   
                            }    
                        }
                        
                        if (accion.equals("abrirCargoDespachoVirtual")){
                            try{
                                WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
                                DocumentoExternoVirtualDAO recepcionDAO =  (DocumentoExternoVirtualDAO)wac.getBean("documentoExternoVirtualDAO");
                                IotdtmDocExterno despacho = recepcionDAO.buscarDocumentoVirtual(new Integer(request.getParameter("idCodigo")));
                                
                                if (despacho.getSidemiext().getBcarstdrec()!=null){
                                    byte[] bytes = despacho.getSidemiext().getBcarstdrec();
                                    response.setContentType("application/pdf");
                                    response.setContentLength(bytes.length);
                                    String headerKey = "Content-Disposition";
                                    String headerValue = String.format("inline; filename=ImportLog.pdf");
                                    response.setHeader(headerKey, headerValue);
                                    outStream = response.getOutputStream();
                                    outStream.write(bytes);
                                    outStream.flush();
                                    outStream.close();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                                if (outStream!=null){
                                    try{
                                         outStream.close();
                                    }catch(Exception ex2){
                                         ex2.printStackTrace();
                                      }
                                }   
                            }    
                        }
                    
                        if (accion.equals("abrirPrincipalVirtual")){
                            try{
                                WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
                                DocumentoExternoVirtualDAO recepcionDAO =  (DocumentoExternoVirtualDAO)wac.getBean("documentoExternoVirtualDAO");

                                IotdtmDocExterno recepcion = recepcionDAO.buscarDocumentoVirtual(new Integer(request.getParameter("idCodigo")));
                                byte[] bytes = recepcion.getIotdtdDocPrincipalList().get(0).getBpdfdoc();
                                response.setContentType("application/pdf");
                                response.setContentLength(bytes.length);
                                String headerKey = "Content-Disposition";
                                String headerValue = String.format("inline; filename=ImportLog.pdf");
                                response.setHeader(headerKey, headerValue);
                                outStream = response.getOutputStream();
                                outStream.write(bytes);
                                outStream.flush();
                                outStream.close();
                            }catch(Exception e){
                                e.printStackTrace();
                                if (outStream!=null){
                                    try{
                                         outStream.close();
                                    }catch(Exception ex2){
                                         ex2.printStackTrace();
                                    }
                                }
                            }    
                        }
                        
                        if (accion.equals("abrirBandeja")){
                            String sURL = request.getParameter("url");
                            String idArchivo = request.getParameter("idArchivo");
                            String objectId = request.getParameter("objectId");
                               
                            if (sURL == null || idArchivo == null || objectId == null)
                               return; 
                            
                            request.setAttribute("url", sURL);
                            request.setAttribute("idArchivo", idArchivo);
                            request.setAttribute("objectId", objectId);
                            
                            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
                            ArchivoDAO archivoDAO =  (ArchivoDAO)wac.getBean("archivoDAO");
                            Archivo archivo = archivoDAO.buscarObjPorId(Integer.valueOf(idArchivo));
                            List<Archivo> lst = archivoDAO.findByIdDocumento(archivo.getDocumento().getIdDocumento());
                            List<Archivo> lstTotal = new ArrayList<Archivo>();
                            
                            if (lst == null || lst.size() == 0)
                               return; 
                          
                            
                            for(int i=0;i<lst.size();i++){
                                if (lst.get(i).getPrincipal()=='S'){
                                    lstTotal.add(lst.get(i));
                                    break;
                                }                               
                            }
                            
                            for(int i=0;i<lst.size();i++){
                                if (lst.get(i).getPrincipal()=='N'){
                                    lstTotal.add(lst.get(i));
                                }                               
                            }
                            
                            for(int i=0;i<lst.size();i++){
                                if (lst.get(i).getPrincipal()!='N' && lst.get(i).getPrincipal()!='S'){
                                    lstTotal.add(lst.get(i));
                                }                               
                            }
                            
                            request.setAttribute("lstBandeja", lstTotal);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/bandeja.jsp");
                            dispatcher.forward(request,response);
                        }
                        
                        //////////////////   NAVEGADOR  ///////////////////////
                        if (accion.equals("abrirDocumentoNavegador")){
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/documentosNavegador.jsp");
                            dispatcher.forward(request,response);
                        }
                        
                        if (accion.equals("abrirNavegador")){
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/navegador.jsp");
                            dispatcher.forward(request,response);
                        }
                        
                        if (accion.equals("cargarBandejaDocumentosNavegador")){
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/bandejaDocumentoNavegador.jsp");
                            dispatcher.forward(request,response);
                        }
                        
                        if (accion.equals("cargarBandejaUnidadNavegador")){
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/bandejaUnidadNavegador.jsp");
                            dispatcher.forward(request,response);
                        }
                        
                        if (accion.equals("cargarBandejaTipoDocumentosNavegador")){
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/bandejaNavegador.jsp");
                            dispatcher.forward(request,response);
                        }
                        
                        ////////////////////////////////////////////////
                        
                        if (accion.equals("abrirDocumentoTemporal")){
                            String sURL = request.getParameter("url");
                            String idArchivo = request.getParameter("idArchivo");
                            String objectId = request.getParameter("objectId");
                            
                            if (sURL == null || idArchivo == null || objectId == null || objectId.equals("null") || sURL.equals("null"))
                               return; 
                            
                            request.setAttribute("url", sURL);
                            request.setAttribute("idArchivo", idArchivo);
                            request.setAttribute("objectId", objectId);
                            
                            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
                            ArchivoDAO archivoDAO =  (ArchivoDAO)wac.getBean("archivoDAO");
                            Archivo archivo = archivoDAO.buscarObjPorId(Integer.valueOf(idArchivo));
                            List<Archivo> lst = archivoDAO.findByIdDocumento(archivo.getDocumento().getIdDocumento());
                            
                            if (lst == null || lst.size() == 0)
                               return; 
                            
                            boolean bandera = false;
                            for(int i=0;i<lst.size();i++){
                                if (lst.get(i).getPrincipal()=='S'){
                                    bandera = true;
                                    break;
                                }                               
                            }
                            
                            if (!bandera)
                              return;  
                          
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/principal.jsp");
                            dispatcher.forward(request,response);
                        }
                        
                        if (accion.equals("abrirDocumento")){
                            try{
                                AlfrescoApiWs alfrescoApiWs;
                                String sURL = request.getParameter("url");
                                String idArchivo = request.getParameter("idArchivo");
                                String objectId = request.getParameter("objectId");
                                
                                if (sURL==null || idArchivo == null || objectId == null || sURL.trim().equals("") || idArchivo.trim().equals("") || objectId.trim().equals("") || objectId.equals("null") || sURL.equals("null"))
                                   return;    
                                
                                WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
                                ArchivoDAO archivoDAO =  (ArchivoDAO)wac.getBean("archivoDAO");
                                Archivo archivo = archivoDAO.buscarObjPorId(Integer.valueOf(idArchivo));
                                
                                if (archivo==null || archivo.getRutaAlfresco()==null)
                                    return;
                                
                                if (archivo.getObjectId()!=null && !archivo.getObjectId().equals(objectId))
                                     return; 
                                
                                String idDocumento="";
                                String alfrescoHostPublico = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_HOST);
                                String alfrescoHostPort = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PORT);
                                String alfrescoProtocolo = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PROTOCOLO);
                                String URL_ALFRESCO = alfrescoProtocolo+"://"+alfrescoHostPublico+":"+alfrescoHostPort+"/alfresco/cmisatom";
                                String user = USERCONSULTA;//usuario.getUsuario();
                                String pass = USERCONSULTA_CLAVE;//usuario.getClave();
                                sURL = archivo.getRutaAlfresco();
                                String sruta[] = sURL.split("/");
                                
                                String rutaCarpeta = "/"+sruta[0]+"/"+sruta[1]+"/"+sruta[2]+"/"+sruta[3]+"/"+sruta[4]+"/"+sruta[5]+"/"+ sruta[6];
                                alfrescoApiWs = new AlfrescoApiWs(URL_ALFRESCO, user, pass, REPOSITORIO_ID);
                                sesionAlfresco = alfrescoApiWs.getSessionAlfresco();
                                Document carpetaTestSistemas = (Document)sesionAlfresco.getObjectByPath(rutaCarpeta);
                                idDocumento = carpetaTestSistemas.getId();
                                String idDocRuta[] = idDocumento.split("//");
                                String idDocRutaFinal[] = idDocRuta[1].split(";");
                                
                                byte[] bytes = toByteArray(carpetaTestSistemas.getContentStream().getStream());
                                response.setContentType("application/" + archivo.getNombre().substring(archivo.getNombre().lastIndexOf('.')+1, archivo.getNombre().length()).toLowerCase());
                                response.setContentLength(bytes.length);
                                String headerKey = "Content-Disposition";
                                String headerValue = String.format("inline; filename=" + archivo.getNombre().substring(archivo.getNombre().indexOf("]") +1, archivo.getNombre().length()).replace(",", "_"));
                                response.setHeader(headerKey, headerValue);
                                outStream = response.getOutputStream();
                                outStream.write(bytes);
                                outStream.flush();
                                outStream.close();
                                sesionAlfresco.clear();
                                sesionAlfresco = null;
                                
                                try{
                                    Map<String, Object> mapSession = null;
                                    mapSession = ActionContext.getContext().getSession();
                        
                                    LogOperacion logOperacion = new LogOperacion();
                                    logOperacion.setIddocumento((Integer)mapSession.get(Constantes.SESSION_IDDOCUMENTO));
                                    logOperacion.setIdusuario(((Usuario) mapSession.get(Constantes.SESSION_USUARIO)).getIdusuario());
                                    logOperacion.setOpcion((String)mapSession.get("sTipoGrid"));
                                    logOperacion.setNombrepc((String)mapSession.get("nombrePC"));
                                    logOperacion.setNombrefile(sURL.substring(sURL.lastIndexOf("/") + 1, sURL.length()));
                                    logOperacion.setFechaoperacion(new Date());  
                                    LogOperacionService logOperacionService =  (LogOperacionService)wac.getBean("logOperacionService");
                                    logOperacionService.saveLogOperacion(logOperacion);
                                }catch(Exception e){
                                   e.printStackTrace();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                                if (sesionAlfresco!=null){
                                    try{
                                         sesionAlfresco.clear();
                                         sesionAlfresco = null;
                                    }catch(Exception ex){
                                         ex.printStackTrace();
                                    }
                                }    
                            }    
                        }
                        
                        if (accion.equals("abrirDocumentoEmail")){
                            try{
                                AlfrescoApiWs alfrescoApiWs;
                                String sURL = request.getParameter("url");
                                String idArchivo = request.getParameter("idArchivo");
                                String objectId = request.getParameter("objectId");
                                
                                if (sURL==null || idArchivo == null || objectId == null || sURL.trim().equals("") || idArchivo.trim().equals("") || objectId.trim().equals(""))
                                   return;    
                                
                                WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
                                ArchivoDAO archivoDAO =  (ArchivoDAO)wac.getBean("archivoDAO");
                                Archivo archivo = archivoDAO.buscarObjPorId(Integer.valueOf(idArchivo));
                                
                                if (archivo==null)
                                    return;
                                
                                if (archivo.getObjectId()!=null && !archivo.getObjectId().equals(objectId))
                                     return; 
                                
                                String idDocumento="";
                                String alfrescoHostPublico = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_HOST);
                                String alfrescoHostPort = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PORT);
                                String alfrescoProtocolo = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PROTOCOLO);
                                String URL_ALFRESCO = alfrescoProtocolo+"://"+alfrescoHostPublico+":"+alfrescoHostPort+"/alfresco/cmisatom";
                                String user = USERCONSULTA;//usuario.getUsuario();
                                String pass = USERCONSULTA_CLAVE;//usuario.getClave();
                                sURL = archivo.getRutaAlfresco();
                                String sruta[] = sURL.split("/");
                                String rutaCarpeta = "/"+sruta[0]+"/"+sruta[1]+"/"+sruta[2]+"/"+sruta[3]+"/"+sruta[4]+"/"+sruta[5]+"/"+sruta[6];

                                alfrescoApiWs = new AlfrescoApiWs(URL_ALFRESCO, user, pass, REPOSITORIO_ID);
                                sesionAlfresco = alfrescoApiWs.getSessionAlfresco();
                                Document carpetaTestSistemas = (Document)sesionAlfresco.getObjectByPath(rutaCarpeta);
                                idDocumento = carpetaTestSistemas.getId();
                                String idDocRuta[] = idDocumento.split("//");
                                String idDocRutaFinal[] = idDocRuta[1].split(";");

                               
                                byte[] bytes = toByteArray(carpetaTestSistemas.getContentStream().getStream());
                                response.setContentType("application/" + archivo.getNombre().substring(archivo.getNombre().lastIndexOf('.')+1, archivo.getNombre().length()).toLowerCase());
                                response.setContentLength(bytes.length);
                                String headerKey = "Content-Disposition";
                                String headerValue = String.format("inline; filename=" + archivo.getNombre().substring(archivo.getNombre().indexOf("]") +1, archivo.getNombre().length()).replace(",", "_"));
                                response.setHeader(headerKey, headerValue);
                                outStream = response.getOutputStream();
                                outStream.write(bytes);
                                outStream.flush();
                                outStream.close();
                                sesionAlfresco.clear();
                                sesionAlfresco = null;
                                
                                try{
                                    Map<String, Object> mapSession = null;
                                    mapSession = ActionContext.getContext().getSession();
                        
                                    LogOperacion logOperacion = new LogOperacion();
                                    logOperacion.setIddocumento((Integer)mapSession.get(Constantes.SESSION_IDDOCUMENTO_EMAIL));
                                    logOperacion.setIdusuario(((Usuario) mapSession.get(Constantes.SESSION_USUARIO_EMAIL)).getIdusuario());
                                    logOperacion.setOpcion((String)mapSession.get("sTipoGridEmail"));
                                    logOperacion.setNombrepc((String)mapSession.get("nombrePCEmail"));
                                    logOperacion.setNombrefile(sURL.substring(sURL.lastIndexOf("/") + 1, sURL.length()));
                                    logOperacion.setFechaoperacion(new Date());  
                                    LogOperacionService logOperacionService =  (LogOperacionService)wac.getBean("logOperacionService");
                                    logOperacionService.saveLogOperacion(logOperacion);
                                }catch(Exception e){
                                   e.printStackTrace();
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                                if (sesionAlfresco!=null){
                                    try{
                                         sesionAlfresco.clear();
                                         sesionAlfresco = null;
                                    }catch(Exception ex){
                                         ex.printStackTrace();
                                    }
                                }    
                            }    
                        }
                    }catch(Exception e){
                           e.printStackTrace();
                    }finally{
                        if (sesionAlfresco!=null){
                            try{
                                 sesionAlfresco.clear();
                                 sesionAlfresco = null;
                            }catch(Exception ex){
                                 ex.printStackTrace();
                            }
                        }
                        
                        if (outStream!=null){
                            try{
                                 outStream.close();
                           }catch(Exception ex2){
                                 ex2.printStackTrace();
                           }
                        }
                    }
                  
    }
    
    private byte[] toByteArray(InputStream intput) throws  IOException{
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len;
        
        while ((len= intput.read(buffer))!=-1){
            os.write(buffer,0,len);
        }
        
        return os.toByteArray();
    }
}
