/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services; 

import gob.ositran.siged.config.SigedProperties;
import gob.ositran.siged.service.AlfrescoWebscriptService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.ositran.pojos.ArchivoVersionado;
import org.ositran.daos.ArchivoDAO;
import org.ositran.daos.SearchMode;
import org.ositran.dojo.grid.Item;
import org.ositran.utils.ArchivoTemporal;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Tipodocumento;
import java.net.URLEncoder;
import org.ositran.daos.DocumentoDAO;
import org.ositran.daos.TipodocumentoDAO;
import org.ositran.utils.PasswordGenerator;

public class ArchivoServiceImpl implements ArchivoService{
        private static Logger log=Logger.getLogger(ArchivoServiceImpl.class);
        private ArchivoDAO dao;
        private RepositorioService srvRepositorio;
        private AlfrescoWebscriptService alfrescoWebscriptClient;
        private TipodocumentoDAO tipoDocumentoDao;
        private List<Archivo> listArchivo;
        private DocumentoDAO documentoDAO;

        public DocumentoDAO getDocumentoDAO() {
            return documentoDAO;
        }

        public void setDocumentoDAO(DocumentoDAO documentoDAO) {
            this.documentoDAO = documentoDAO;
        }
    
        public TipodocumentoDAO getTipoDocumentoDao() {
            return tipoDocumentoDao;
        }

        public void setTipoDocumentoDao(TipodocumentoDAO tipoDocumentoDao) {
            this.tipoDocumentoDao = tipoDocumentoDao;
        }
        
	public ArchivoServiceImpl(ArchivoDAO dao){
		setDao(dao);
	}
        
        public List<Archivo> findArchivoTipoFirmardo(Integer idDocumento , char tipoArchivo, String tipoFirma){
            return getDao().findArchivoTipoFirmardo(idDocumento, tipoArchivo, tipoFirma);
        }
        
        public List<Archivo> findArchivoPrincipalFirmardo(Integer idDocumento , Usuario usuario){
            return getDao().findArchivoPrincipalFirmardo(idDocumento, usuario);
        }
        
         public List<Archivo> buscarDocumentosPublicar(String nroTramite){
             return getDao().buscarDocumentosPublicar(nroTramite);
         }
        
        public List<Archivo> findArchivosxFirmar(Integer idDocumento , Usuario usuario){
            return getDao().findArchivosxFirmar(idDocumento, usuario);
        }
        
        public List<Archivo> buscarArchivoExterno(String objectId, Integer nroTramite, String clave){
            return getDao().buscarArchivoExterno(objectId, nroTramite, clave);
        }
        
        public List<Archivo> buscarArchivosObjectId(String objectId, Integer nroTramite){
            return getDao().buscarArchivosObjectId(objectId, nroTramite);
        }

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
        
        public List<String> contarArchivosxFirmar(Integer idDocumento , Usuario usuario){
           return  getDao().contarArchivosxFirmar(idDocumento, usuario);
        }
        
	public Archivo findByCriteria(Integer iIdDoc,String strNombre){
		try{
			return getDao().buscarObjPor(iIdDoc,strNombre);
		}catch(RuntimeException re){
			log.error("",re);
			return null;
		}
	}

	public List<Archivo> findByIdNombreEstado(Integer idDocumento, String nombre){
		return getDao().findByIdNombreEstado(idDocumento, nombre);
	}

	public Archivo findById(Integer idArchivo){
		return getDao().buscarObjPorId(idArchivo);
	}
        
  
        
         public List<Archivo> buscarArchivosPorRutaDocumento(Integer idDocumento, String nombre){
              return getDao().buscarArchivosPorRutaDocumento(idDocumento , nombre);
         }
        
        public Long buscarArchivosPorRuta(String ruta, Usuario usuario){
              return getDao().buscarArchivosPorRuta(ruta, usuario);
         }

	public List<Archivo> buscarPorAutor(Integer idAutor, Integer idDocumento){
		return dao.buscarPorAutor(idAutor, idDocumento);
	}
        
        public List<Archivo> buscarPorAutor(Usuario usuario, Integer idDocumento){
		return dao.buscarPorAutor(usuario, idDocumento);
	}
        
        public List<Item> buscarItemArchivoXAutor(Usuario usuario, Integer idDocumento){
                List<Archivo> archivos = buscarPorAutor(usuario, idDocumento);
		List<Item> items = new ArrayList<Item>();
                Documento d = documentoDAO.findByIdDocumento(idDocumento);
		if(archivos != null && !archivos.isEmpty()){
			for(Archivo archivo : archivos){
                            if (d.getNroVirtual()==null){
				Item item = new Item();
				item.setId(archivo.getIdArchivo());
				item.setNombre(archivo.getNombreArchivo());
				items.add(item);
                            }else{
                                if (archivo.getPrincipal()!= 'S' && archivo.getPrincipal()!= 'M'){
                                    Item item = new Item();
                                    item.setId(archivo.getIdArchivo());
                                    item.setNombre(archivo.getNombreArchivo());
                                    items.add(item);
                                }    
                            }    
			}
		}

		return items;
	}

	public List<Item> buscarItemArchivoXAutor(Integer idAutor, Integer idDocumento){
		List<Archivo> archivos = buscarPorAutor(idAutor, idDocumento);
		List<Item> items = new ArrayList<Item>();

		if(archivos != null && !archivos.isEmpty()){
			for(Archivo archivo : archivos){
				Item item = new Item();
				item.setId(archivo.getIdArchivo());
				item.setNombre(archivo.getNombreArchivo());
				items.add(item);
			}
		}

		return items;
	}

	@Transactional
	public void eliminarArchivo(Integer idArchivo, Usuario usuario){
		try{
			Archivo archivo = dao.buscarObjPorId(idArchivo);
			archivo.setEstado(Constantes.ESTADO_INACTIVO);
                        archivo.setFechaModificacion(new Date());
                        archivo.setUsuariomodificacion(usuario.getIdusuario());
			dao.guardarObj(archivo);
		}catch(NoResultException e){
			e.printStackTrace();
		}
	}

	@Transactional
	public Archivo saveArchivo(Archivo objA){
                return getDao().guardarObj(objA);
	}

	public File getFile(Integer idArchivo,char tipo) throws FileNotFoundException{
		Archivo archivo=getDao().buscarObjPorId(idArchivo);
		if(archivo!=null){
			File f=null;
			if(tipo==Archivo.ESTADO_REGISTRADO){
				f=new File(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_ENTRANTE),archivo.getNombre());
			}else{
				if(tipo==Archivo.ESTADO_DISPONIBLE){
					f=new File(archivo.getRutaArchivoPdf());
				}
			}
			if(f==null||!f.exists()){
				throw new RuntimeException("Tipo incorrecto o archivo inexistente :"+f);
			}
			return f;
		}
		log.debug("Archivo con id:"+idArchivo+" no encontrado.");
		return null;
	}

	public File renombrarArchivoDigitalizacion(Documento objD,ArchivoTemporal archivo,int contador){
		// Para el nombre tenemos pensado la estrutura de
		// [id_fechahora_contador]nombreArchivo
		Date d=new Date();
		// String nuevoNombre = '[' + objD.getIdDocumento().toString() + '_' +
		// DateFormatUtils.format(d, "yyyyMMddHHmm") + '_' + contador + ']' +
		// archivo.getSNombre();
		String tmpExtension=archivo.getSNombre().substring(archivo.getSNombre().lastIndexOf('.')+1);
		log.debug("EXTENSION FILE_:"+tmpExtension);
		String nuevoNombre;
		String rutaDig;
		/*if(tmpExtension.equalsIgnoreCase("tif")||tmpExtension.equalsIgnoreCase("tiff")){
			nuevoNombre='['+objD.getIdDocumento().toString()+'_'+DateFormatUtils.format(d,"yyyyMMddHHmm")+'_'+contador+']'+archivo.getSNombre().substring(0,archivo.getSNombre().lastIndexOf('.')+1)+"pdf";
			rutaDig=ValoresProperties.getProperty(ValoresProperties.DIRECTORIO_ENTRANTE);
		}else{*/
			nuevoNombre='['+objD.getIdDocumento().toString()+'_'+DateFormatUtils.format(d,"yyyyMMddHHmm")+'_'+contador+']'+archivo.getSNombre().substring(0,archivo.getSNombre().lastIndexOf('.')+1)+tmpExtension;
			rutaDig=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_SALIENTE);
		//}
		log.debug("Nuevo nombre Archivo:"+nuevoNombre);
		File f=new File(rutaDig,nuevoNombre);
		log.debug("Ruta Origen"+archivo.getFArchivo().getAbsolutePath());
		log.debug("Ruta Destino:"+f.getAbsolutePath());
		try{
			FileUtils.moveFile(archivo.getFArchivo(),f);
		}catch(IOException ex){
			log.error(ex.getMessage(),ex);
			return null;
		}
		return f;
	}

	public Integer checkEstadoDigitalizacion(Integer iIdDoc){
		try{
			List<Archivo> lstArch1=getDao().findByIdDocumento(iIdDoc);
			List<Archivo> lstArch2=getDao().checkEstadoDigitalizacion(iIdDoc);
			if(lstArch1==null){
				return 1;
			}
			if(lstArch2==null){
				return 0;
			}
			if(lstArch1.size()==lstArch2.size()){
				return 1;
			}
			return 0;
		}catch(RuntimeException re){
			log.error("",re);
			return null;
		}
	}

	@Override
	public Map<String,List<Archivo>> findByIdDocumento(Integer idDocumento){
		List<Archivo> archivos=dao.findByIdDocumento(idDocumento);
		List<Archivo> alfrescados;
		if(archivos!=null){
			alfrescados=new ArrayList<Archivo>();
			log.debug("Se encontraron "+archivos.size()+" archivos para el documento con id: "+idDocumento);
			for(Archivo archivo : archivos){
            //log.debug("Archivo [" + archivo.getNombre() + "] ruta alfresco [" + archivo.getRutaAlfresco() + "]");

            /*if (!StringUtil.isEmpty(archivo.getRutaAlfresco()) /*&& archivo.getEstadoDigitalizacion() == Archivo.ESTADO_IMPORTADO) {
               archivo.setSURL(RepositorioUtils.obtenerLinkContenido(archivo.getRutaAlfresco()));
            }*/

				alfrescados.add(archivo);
			}
			Map<String,List<Archivo>> salida=new HashMap<String,List<Archivo>>();
			salida.put("upload1",alfrescados);
			return salida;
		}
		return null;
	}


	public Map<String,List<Archivo>> getArchivoList(Integer iIdExpediente,Integer iIdDocumento,String strRol){
		//int iBracket;
		List<Archivo> lstArch=getDao().findByIdDocumentoOrderDesc(iIdExpediente,iIdDocumento,strRol);
		for(Archivo objA : lstArch){
			/*iBracket=objA.getNombre().indexOf("]");
			if(iBracket!=-1){
				objA.setSNombreReal(objA.getNombre().substring(iBracket+1));
			}else{
				objA.setSNombreReal(objA.getNombre());
			}*/
			if(strRol!=null && !strRol.equals(Constantes.ROL_DIGITALIZADOR) && !strRol.equals(Constantes.ROL_QAS) && objA.getRutaAlfresco()!=null){
				//objA.setSURL(RepositorioUtils.obtenerLinkContenido(objA.getRutaAlfresco()));
				log.debug("Ruta en Alfresco ["+objA.getRutaAlfresco()+"]");
				// objA.setRutaalfresco(RepositorioUtils.obtenerLinkContenido(objA.getRutaAlfresco()));
			}
			/*
			 * else{objA.setSURL(RepositorioUtils.obtenerLinkContenido(objA.
			 * getRutaAlfresco())); }
			 */
		}
		Map<String,List<Archivo>> salida=new HashMap<String,List<Archivo>>();
		salida.put("upload1",lstArch);
		return salida;
	}

	public Map<String,List<Archivo>> getArchivoListPorDocumento(Integer iIdDocumento){
		List<Archivo> lstArch=getDao().findByIdDocumento(iIdDocumento);
                Map<String,List<Archivo>> salida=new HashMap<String,List<Archivo>>();
		salida.put("upload1",lstArch);
		return salida;
	}

	public void checkInToAlfresco(Usuario usuario,ArchivoTemporal objArchivoTemporal,Documento objDocumento,Integer iContador,boolean quitarCorchete) throws RemoteException,IOException{
		String nombreArchivo=objArchivoTemporal.getFArchivo().getName();
		if(quitarCorchete){
			nombreArchivo=nombreArchivo.substring(nombreArchivo.indexOf(']')+1,nombreArchivo.length());
		}
		String ruta = srvRepositorio.obtenerRutaCompletaExpediente(objDocumento.getExpediente());
		alfrescoWebscriptClient.modificarContenidoArchivo(usuario, objArchivoTemporal.getFArchivo(), ruta, nombreArchivo);
	}

	public List<ArchivoVersionado> getVersions(Documento documento){
                List<ArchivoVersionado> lista=new ArrayList<ArchivoVersionado>();
		List<Archivo> archivos;
                
                if (documento.getDocumentoreferencia()==null){
                    archivos=dao.findByIdDocumento(documento.getIdDocumento());
                }else{
                    archivos=dao.findByIdDocumento(documento.getDocumentoreferencia());
                }
               
                for(Archivo archivo : archivos){
                        if(archivo.getRutaAlfresco()!=null && archivo.getEstadoDigitalizacion() != Constantes.ESTADO_INACTIVO && archivo.getEstado().equals(Constantes.ESTADO_ACTIVO)){
			        ArchivoVersionado archivoVersionado=new ArchivoVersionado();
			   	archivoVersionado.setArchivo(archivo);
                                lista.add(archivoVersionado);
			}
		}
               
                return lista;
	}

	@Transactional
	public void updateEstadoByArchivo(Integer iIdArchivo,Character cEstado){
		int iUpdates=0;
		iUpdates=dao.updateEstado(iIdArchivo,cEstado);
		log.debug("Numero de archivos actualizados ["+iUpdates+"]");
	}

	@Transactional
	public void updateEstadoByDocumento(Integer iIdDocumento,Character cEstado){
		List<Archivo> lstArchivo=null;
		int iUpdates=0;
		log.debug("Documento con ID ["+iIdDocumento+"]");
		log.debug("Estado a setear ["+cEstado+"]");
		Map<String,List<Archivo>> archivos=findByIdDocumento(iIdDocumento);
		if(archivos!=null){
			lstArchivo=archivos.get("upload1");
			if(log.isDebugEnabled()){
				if(lstArchivo!=null){
					log.debug("Numero de archivos a actualizar el estado ["+lstArchivo.size()+"]");
				}
			}
			for(Archivo objArchivo : lstArchivo){
				if(dao.updateEstado(objArchivo.getIdArchivo(),cEstado)>0){
					iUpdates++;
				}
			}
		}
		log.debug("Numero de archivos actualizados ["+iUpdates+"]");

	}

   @Override
	@Transactional
	public void saveArhivoFromSessionToDB(List<ArchivoTemporal> lstArchivoTemporal,Documento objDocumento,Usuario usuario){
		int iContador=0;
		for(ArchivoTemporal objArchivoTemporal : lstArchivoTemporal){
			File fArchivo=this.renombrarArchivoDigitalizacion(objDocumento,objArchivoTemporal,iContador);
			if(fArchivo!=null&&fArchivo.exists()){
				Archivo objArchivo=new Archivo();
				objArchivo.setDocumento(objDocumento);
				objArchivo.setNombre(fArchivo.getName());
				// /
				// objArchivo.setRutaarchivopdf(objArchivoTemporal.getFArchivo().)
				// String
				// tmpExtension=fArchivo.getName().substring(fArchivo.getName().lastIndexOf('.')
				// + 1);
				// if(tmpExtension.equalsIgnoreCase("tif") ||
				// tmpExtension.equalsIgnoreCase("tiff")){
				//objArchivo.setEstadoDigitalizacion(Constantes.ARCHIVO_ESTADO_DIGITALIZACION_NO);
            objArchivo.setEstadoDigitalizacion(Constantes.ARCHIVO_ESTADO_DIGITALIZACION_YES);
            objArchivo.setRutaArchivoPdf(fArchivo.getAbsolutePath());
				// }else{
				// objArchivo.setEstadodigitalizacion(Constantes.ARCHIVO_ESTADO_DIGITALIZACION_YES);
				// }
				objArchivo.setFechaCreacion(new Date());
				this.saveArchivo(objArchivo);
				// tmpExtension=null;
				/*Auditoria ObjAuditoria=new Auditoria();
				ObjAuditoria.setTipoAuditoria(Constantes.TA_Adjuntar);
				ObjAuditoria.setModulo(Constantes.TM_Digitalizador);
				ObjAuditoria.setOpcion(Constantes.TC_Archivo);
				ObjAuditoria.setNuevoValor(fArchivo.getName());
				ObjAuditoria.setFechaAudioria(new Date());
				ObjAuditoria.setUsuario(usuario.getUsuario());
				ObjAuditoria.setExpediente(objDocumento.getExpediente());
				ObjAuditoria.setDocumento(objDocumento);
				daoauditoria.SaveAuditoria(ObjAuditoria);*/
			}else{
				throw new RuntimeException("Archivo no ha sido movido correctamente");
			}
			iContador++;
		}
	}

	public void uploadFile(Usuario usuario,ArchivoTemporal objArchivoTemporal,Documento objDocumento,Integer iContador){
		try{
			this.checkInToAlfresco(usuario,objArchivoTemporal,objDocumento,iContador,false);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			this.uploadToAlfresco(objArchivoTemporal,objDocumento,iContador);
		}
	}

	@Transactional
	public void uploadToAlfresco(ArchivoTemporal objArchivoTemporal,Documento objDocumento,Integer iContador){
		if(objDocumento==null){
			log.error("El Documento es ["+objDocumento+"]");
			throw new RuntimeException();
		}
		// Renombrando el nombre del archivo al formato
		// [id_fechahora_contador]nombreArchivo
		Date d=new Date();
		String sNuevoNombre="["+(objDocumento.getIdDocumento()!=null ? objDocumento.getIdDocumento() : new SimpleDateFormat("SSS").format(new Date()))+"_"+DateFormatUtils.format(d,"yyyyMMddHHmm")+"_"+iContador+"]"+objArchivoTemporal.getSNombre();
		log.debug("Archivo: Nombre ["+objArchivoTemporal.getSNombre()+"] nuevo Nombre ["+sNuevoNombre+"]");
		String rutaDig=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_TEMPORAL_ALFRESCO);
		File f=new File(rutaDig,sNuevoNombre);
		log.debug("Ruta completa a mover ["+f.getAbsolutePath()+"]");
		try{
			FileUtils.moveFile(objArchivoTemporal.getFArchivo(),f);
		}catch(IOException ex){
			log.error(ex.getMessage(),ex);
		}
		// Registrando el archivo en SIGED y subiendo al Alfresco
		Archivo objArchivo=new Archivo();
		String ruta=f.getAbsolutePath();
		log.debug("Ruta absoluta ["+f.getAbsolutePath()+"]");
		objArchivo.setDocumento(objDocumento);
		objArchivo.setNombre(f.getName());
		objArchivo.setEstadoDigitalizacion(Constantes.ARCHIVO_ESTADO_DIGITALIZACION_YES);
		objArchivo.setFechaCreacion(new Date());
		objArchivo.setRutaArchivoPdf(ruta);
		objArchivo.setEstado(Constantes.ESTADO_ACTIVO);
		this.saveArchivo(objArchivo);
	}

	@Transactional
	public Archivo guardarArchivoTemporal(ArchivoTemporal objArchivoTemporal,Documento objDocumento,Integer iContador, Usuario usuarioSesion, String nombrePDFprincipal, String siglaSite){
		String nombre_original = nombrePDFprincipal;
                if(objDocumento==null){
			throw new RuntimeException();
		}
                Date d=new Date();
                
                if (nombrePDFprincipal!=null && !nombrePDFprincipal.trim().equals("") && objArchivoTemporal.getSNombre().toLowerCase().equals(nombrePDFprincipal.toLowerCase())){
                    int contcadena = objArchivoTemporal.getSNombre().lastIndexOf(".");
                    String extension = "";
                    if (contcadena>0)
                      extension = objArchivoTemporal.getSNombre().substring(contcadena, objArchivoTemporal.getSNombre().length());
                   
                    if (objDocumento.getID_EXTERNO().toString().equals("1")){
                       objArchivoTemporal.setSNombre(objDocumento.getID_CODIGO().toString() + "_" + objDocumento.getTipoDocumento().getNombre().toUpperCase() + extension.toLowerCase());
                       nombrePDFprincipal = objDocumento.getID_CODIGO().toString() + "_" + objDocumento.getTipoDocumento().getNombre() + extension.toLowerCase();
                    }else{  
                       if(String.valueOf(objDocumento.getTipoDocumento().getIdtipodocumento()).equals(Constantes.COD_TIPODOCUMENTO_OFICIO_CIRCULAR))
                       {
                            objArchivoTemporal.setSNombre(objDocumento.getID_CODIGO().toString() + "_" + objDocumento.getTipoDocumento().getNombre().toUpperCase() + "_" + objDocumento.getNumeroDocumento().replace("N°", "").trim() +"_"+nombre_original.substring(0, 15)+ extension.toLowerCase());
                            nombrePDFprincipal = objDocumento.getID_CODIGO().toString() + "_" + objDocumento.getTipoDocumento().getNombre() + "_" +  objDocumento.getNumeroDocumento().replace("N°", "").trim() +"_"+nombre_original.substring(0, 15)+ extension.toLowerCase();  
                       }else{
                            objArchivoTemporal.setSNombre(objDocumento.getID_CODIGO().toString() + "_" + objDocumento.getTipoDocumento().getNombre().toUpperCase() + "_" + objDocumento.getNumeroDocumento().replace("N°", "").trim() + extension.toLowerCase());
                            nombrePDFprincipal = objDocumento.getID_CODIGO().toString() + "_" + objDocumento.getTipoDocumento().getNombre() + "_" +  objDocumento.getNumeroDocumento().replace("N°", "").trim() + extension.toLowerCase();  
                       }
                    }
                }else{
                    int contcadena = objArchivoTemporal.getSNombre().lastIndexOf(".");
                    String extension = "";
                    String nombre = objArchivoTemporal.getSNombre().toUpperCase();
                    
                    if (contcadena>0){
                      nombre =  objArchivoTemporal.getSNombre().toUpperCase().substring(0, contcadena).replace(" ", "_").replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace("N°", "N").replace("°", "");
                      extension = objArchivoTemporal.getSNombre().substring(contcadena, objArchivoTemporal.getSNombre().length()).toLowerCase();
                    }
                    
                    if (objArchivoTemporal.getPrincipal().equals("C"))
                       objArchivoTemporal.setSNombre(objDocumento.getID_CODIGO().toString() + "_CARGO_" + nombre + extension);
                    if (objArchivoTemporal.getPrincipal().equals("A"))
                       objArchivoTemporal.setSNombre(objDocumento.getID_CODIGO().toString() + "_ANX_" + nombre + extension);
                    if (objArchivoTemporal.getPrincipal().equals("Y"))
                       objArchivoTemporal.setSNombre(objDocumento.getID_CODIGO().toString() + "_PROYECTO_" + nombre + extension);
                }
                
                ///////////////////////jc fin ///////
                String sNuevoNombre="["+objDocumento.getIdDocumento()+"_"+DateFormatUtils.format(d,"yyyyMMddHHmmss")+"_"+iContador+"]"+objArchivoTemporal.getSNombre();
		String rutaDig=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_TEMPORAL_ALFRESCO);
		
                File f=new File(rutaDig,sNuevoNombre);
		
                try{
		    FileUtils.moveFile(objArchivoTemporal.getFArchivo(),f);
		}catch(Exception ex){
		    log.error(ex.getMessage(),ex);
		}
		// Registrando el archivo en SIGED y subiendo al Alfresco
		Archivo objArchivo=new Archivo();
		String ruta=f.getAbsolutePath();
		objArchivo.setDocumento(objDocumento);
		objArchivo.setNombre(f.getName());
		String sNombreReal = f.getName();
		Integer i = sNombreReal.indexOf("]");
		sNombreReal= sNombreReal.substring(i+1);
                
               if((sNombreReal.toString().trim().toLowerCase()).equals(nombrePDFprincipal.toString().trim().toLowerCase())){
                   objArchivo.setPrincipal('S');
	       }else{
                   if (objArchivoTemporal.getPrincipal().equals("C"))
                      objArchivo.setPrincipal('C');
                   if (objArchivoTemporal.getPrincipal().equals("A"))
                      objArchivo.setPrincipal('N');
                   if (objArchivoTemporal.getPrincipal().equals("Y"))
                      objArchivo.setPrincipal('Y');
	       }
                
                objArchivo.setEstadoDigitalizacion(Constantes.ARCHIVO_ESTADO_DIGITALIZACION_YES);
		objArchivo.setFechaCreacion(new Date());
                objArchivo.setRutaArchivoPdf(ruta);
		objArchivo.setRutaAlfresco(srvRepositorio.obtenerRutaDocumento(objDocumento, siglaSite, objDocumento.getTipoDocumento().getCodigo())+objArchivoTemporal.getSNombre());
		objArchivo.setAutor(new Usuario(usuarioSesion.getIdUsuarioPerfil()));
                objArchivo.setUnidadAutor(usuarioSesion.getIdUnidadPerfil());
                objArchivo.setUsuariocreacion(usuarioSesion.getIdusuario());
                objArchivo.setUsuariomodificacion(usuarioSesion.getIdusuario());
                objArchivo.setClave(null);
                
                try{
                    objArchivo.setTamano(Integer.valueOf((int)f.length()));
                }catch(Exception e){
                    objArchivo.setTamano(null);
                }
                
		objArchivo.setEstado(Constantes.ESTADO_ACTIVO);
                Tipodocumento t = tipoDocumentoDao.findByIdTipoDocumento(objDocumento.getTipoDocumento().getIdtipodocumento());
              
                if (objDocumento.getID_EXTERNO().toString().equals("0") && t.getExternoQR() !=null && t.getExternoQR().toString().equals("1") && objArchivo.getPrincipal()=='S'){
                    String clave = PasswordGenerator.getPassword(
		    PasswordGenerator.MINUSCULAS+
		    PasswordGenerator.MAYUSCULAS+
		    PasswordGenerator.NUMEROS, 7);   
                    clave = clave.replaceAll("i", "U").replaceAll("I", "2").replaceAll("l", "U").replaceAll("L", "2");
                    objArchivo.setClave(clave);
                } 
                
                return this.saveArchivo(objArchivo);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Archivo> buscarLstPor(Integer iIdDocumento,String sNombre,SearchMode sm){
		return dao.findByCriteria(iIdDocumento,sNombre,sm);
	}

	public Map<String,List<ArchivoTemporal>> obtenerArchivosRechazados(Integer iIdDocumento){
		// Integer iContador = null;
		List<Archivo> lstArchivo=null;
		List<ArchivoTemporal> lstArchivoTemporal=null;
		Map<String,List<ArchivoTemporal>> mapUpload=null;
		Map<String,List<Archivo>> archivos=findByIdDocumento(iIdDocumento);
		if(archivos!=null){
			if((lstArchivo=archivos.get("upload1"))!=null){
				log.debug("Archivos encontrados ["+lstArchivo.size()+"]");
				mapUpload=new HashMap<String,List<ArchivoTemporal>>();
				lstArchivoTemporal=new ArrayList<ArchivoTemporal>();
				// iContador = 1;
				for(Archivo objArchivo : lstArchivo){
					int iBracket=-1;
					String sRealName=objArchivo.getNombre();
					iBracket=sRealName.indexOf(Constantes.ARCHIVO_BRACKET_FIN);
					if(iBracket!=-1){
						sRealName=sRealName.substring(iBracket+1);
					}
					log.debug("Archivo con nombre ["+objArchivo.getNombre()+"]");
					log.debug("Nombre Real ["+sRealName+"]");
					log.debug("Ruta ["+objArchivo.getRutaArchivoPdf()+"]");
               log.debug("Ruta Alfresco ["+objArchivo.getRutaAlfresco()+"]");
					ArchivoTemporal objArchivoTemporal=new ArchivoTemporal(sRealName,new File(objArchivo.getRutaArchivoPdf()));
					lstArchivoTemporal.add(objArchivoTemporal);
				}
				mapUpload.put("upload2",lstArchivoTemporal);
			}else{
				log.debug("No se encontraron archivos");
			}
		}
		return mapUpload;
	}

    
   @Override
   public List<Archivo> findLstByExpediente(Integer idExpediente) {
      try {
         //TODO Mejorar los parametros de busqueda, deberia ser un Map
         return dao.findlstByExpediente(idExpediente);
      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return null;
      }
   }

   @Override
   public List<Archivo> findLstByIdDocumento(Integer idDocumento) {
      return dao.findByIdDocumento(idDocumento);
   }

	// ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public ArchivoDAO getDao(){
		return dao;
	}

	public void setDao(ArchivoDAO dao){
		this.dao=dao;
	}

	public RepositorioService getSrvRepositorio(){
		return srvRepositorio;
	}

	public void setSrvRepositorio(RepositorioService srvRepositorio){
		this.srvRepositorio=srvRepositorio;
	}

	

        /*
    public void setAlfrescoWebServiceClient(AlfrescoWSService alfrescoWebServiceClient) {
        this.alfrescoWebServiceClient = alfrescoWebServiceClient;
    }*/

    public void setAlfrescoWebscriptClient(AlfrescoWebscriptService alfrescoWebscriptClient) {
        this.alfrescoWebscriptClient = alfrescoWebscriptClient;
    }

	@Override
	public Archivo getArchivoPrincipalPorDocumento(
			Integer iIdDocumento) {
		try{
			return getDao().findByArchivoPrincipalIdDocumento(iIdDocumento);
		}catch(RuntimeException re){
			log.error("",re);
			return null;
		}
	}

	@Override
	public List<Archivo> getArchivoListPorDocumentoIG(Integer iIdDocumento) {
		try{
			return getDao().findByIdDocumento(iIdDocumento);
		}catch(RuntimeException re){
			log.error("",re);
			return null;
		}

	}

	@Override
	public List<Item> buscarItemArchivoXAutorPDF(Integer idAutor,
			Integer idDocumento) {
		List<Archivo> archivos = buscarPorAutor(idAutor, idDocumento);
		List<Item> items = new ArrayList<Item>();

		if(archivos != null && !archivos.isEmpty()){
			for(Archivo archivo : archivos){
				if(funcExtension(archivo.getNombreReal().toString()).equals("pdf")){
					Item item = new Item();
					item.setId(archivo.getIdArchivo());
					item.setNombre(archivo.getNombreArchivo());
					items.add(item);
				}

			}
		}

		return items;
	}

    private String funcExtension(String miString){
  	   String  extensionTemp = "";
 	       String  extension= "";
 	        for (int i=(miString.length()-1);i+1>0;i--) {
 	            if(miString.charAt(i)=='.'){
 	            	break;
 	    	    }
 	            extensionTemp +=miString.charAt(i);
 	        }
 			for (int i=(extensionTemp.length()-1);i+1>0;i--) {
 	            extension +=extensionTemp.charAt(i);
 	        }
 				return extension;

 	}


	@Transactional
	public void updatePrincipalByArchivo(Integer iIdArchivo , Character principal) {
		log.debug("updatePrincipalByArchivo");
		int iUpdates=0;
		Archivo objArchivo=null;
		try{
			objArchivo=dao.buscarObjPorId(iIdArchivo);
			if(objArchivo !=null ){
				objArchivo.setPrincipal(principal);
				log.debug("objArchivo.getIdArchivo()"+objArchivo.getIdArchivo()+ "objArchivo.getNombreReal()"+objArchivo.getNombreReal()+ "objArchivo.getPrincipal()"+ objArchivo.getPrincipal() );
				dao.updateArchivo(objArchivo);
			}
			iUpdates = 1;
		}catch(NoResultException e){
			log.error(e.getMessage());
			iUpdates = 0;
		}

		log.debug("Numero de archivos actualizados ["+iUpdates+"]");
	}

	@Override
	public List<Archivo> buscarArchivoXAutorPDF(Integer idAutor,
			Integer idDocumento) {

		listArchivo = findLstByIdDocumento(idDocumento);
		List<Archivo> items = new ArrayList<Archivo>();
		if(listArchivo!=null){
		    for(int k=0; k<listArchivo.size(); k++){
			    Archivo archivo = listArchivo.get(k);
				String extension =  funcExtension(archivo.getNombreReal());
				if(extension.equals("PDF") ||  extension.equals("pdf")){
					Archivo item = new Archivo();
					log.debug("archivo.getIdArchivo() ["+archivo.getIdArchivo()+"]"+"archivo.getNombreArchivo() ["+archivo.getNombreArchivo()+"]"+"archivo.getPrincipal()["+archivo.getPrincipal()+"]" );
					item.setIdArchivo(archivo.getIdArchivo());
					item.setNombre(archivo.getNombreArchivo());
					item.setPrincipal(archivo.getPrincipal());
					items.add(item);
				}
		    }
		 }
		 
		return items;
	}

	public List<Archivo> getListArchivo() {
		return listArchivo;
	}

	public void setListArchivo(List<Archivo> listArchivo) {
		this.listArchivo = listArchivo;
	}

}
