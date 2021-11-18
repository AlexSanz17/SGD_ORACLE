/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.ositran.daos.ClienteDAO;
import org.ositran.utils.Constantes;
import org.ositran.utils.DocumentoDetail;
import org.springframework.transaction.annotation.Transactional;
import com.btg.ositran.siged.domain.Cliente;
import org.ositran.utils.StringUtil;

public class ClienteServiceImpl implements ClienteService{
	
	private static Logger log=Logger.getLogger(ClienteServiceImpl.class);
	private ClienteDAO dao;
	private AuditoriaService srvAuditoria;
	private DistritoService distritoService;

	// ////////////////////////////////
	// Constructors //
	// ////////////////////////////////
	public ClienteServiceImpl(ClienteDAO dao){
		this.dao=dao;
	}

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	@Transactional
	public void deleteCliete(Cliente objCliente){
		objCliente.setEstado(Constantes.ESTADO_INACTIVO);
		dao.guardarObj(objCliente);
	}
        
        public List<Cliente> findNombreCliente(Cliente cliente){
            return dao.findNombreCliente(cliente);
        }
        
         public List<Cliente> findAllRUCPIDE(){
            return dao.findAllRUCPIDE();
         }

	public Map<Integer,String> getClienteList() throws RuntimeException{
		try{
			Map<Integer,String> mapAux=new HashMap<Integer,String>();
			List<Cliente> lstC=getDao().findAll();
			for(Cliente objC : lstC){
				mapAux.put(objC.getIdCliente(),objC.getNumeroIdentificacion());
			}
			return mapAux;
		}catch(RuntimeException re){
			log.error(re.getMessage(),re);
			return null;
		}
	}

	public Cliente findByIdCliente(Integer iIdCliente){
		return dao.findByIdCliente(iIdCliente);
	}
        
        public List<Cliente> findAllConcesionaria(Integer iWithoutStor){
             if (iWithoutStor == 0) {
                return dao.findAllConcesionariaActive();
             } else {
                return dao.findAllConcesionaria();
             }
         }

	public Cliente findByNroIdentificacion(String sNroIdentificacion){
		return dao.findByNroIdentificacion(sNroIdentificacion);
	}

        public Cliente findByExpediente(Integer idExpediente) {
		return dao.findByExpediente(idExpediente);
	}

	@Override
	public List<Cliente> findLikeNroIdentificacion(String sNroIdentificacion){
		return dao.findLikeNroIdentificacion(sNroIdentificacion);
	}

        public List<Cliente> findLikeNroIdentificacionOrNombre(String prm) {
            return dao.findLikeNroIdentificacionOrNombre(prm);
        }

	@Transactional
	public Cliente guardarObj(Cliente objClienteOld,Cliente objClienteNew,String sUsuarioSesion,String sTipoAuditoria){
		try{
			srvAuditoria.aplicarAuditoria(objClienteOld,objClienteNew,sUsuarioSesion,Constantes.AUDITORIA_OPCION_GUARDAR,sTipoAuditoria);
		}catch(ClassNotFoundException e){
			log.error(e.getMessage(),e);
		}
		objClienteNew=dao.guardarObj(objClienteNew);
		log.debug("Cliente guardado con ID ["+objClienteNew.getIdCliente()+"] Razon Social ["+objClienteNew.getRazonSocial()+"]");
		return objClienteNew;
	}

   @Transactional
   public Cliente updateInfoCliente(DocumentoDetail objDD) {
      Cliente objCliente = null;
      objCliente = this.findByIdCliente(objDD.getIIdCliente());
      return objCliente;
   }

	public List<Cliente> findAll(){
		List<Cliente> lstCliente=getDao().findAll();
		for(Cliente objCliente : lstCliente){
			if(objCliente.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_RUC)){
				objCliente.setsNombre(objCliente.getRazonSocial());
			}else{
				String sNombre=objCliente.getNombres()+" "+objCliente.getApellidoPaterno();
				sNombre+=(objCliente.getApellidoMaterno()!=null) ? " "+objCliente.getApellidoMaterno() : "";
				objCliente.setsNombre(sNombre);
			}
		}
		return lstCliente;
	}

	public List<Cliente> findByTipoIdentificacionList(Integer iIdTipoIdentificacion, String sTipoIdentificacion){
		try{
			return getDao().findByTipoIdentificacionList(iIdTipoIdentificacion, sTipoIdentificacion);
		}catch(NoResultException nre){
			log.warn(nre.getMessage());
			return null;
		}
	}

	public Cliente findByTipoIdentificacionList2(Integer iIdTipoIdentificacion, String sTipoIdentificacion,String nroIdentificacion){
		try{
			return getDao().findByTipoIdentificacionList2(iIdTipoIdentificacion, sTipoIdentificacion,nroIdentificacion);
		}catch(NoResultException nre){
			log.warn(nre.getMessage());
			return null;
		}
	}
	
	// ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public ClienteDAO getDao(){
		return dao;
	}

	public void setDao(ClienteDAO dao){
		this.dao=dao;
	}

	public AuditoriaService getSrvAuditoria(){
		return srvAuditoria;
	}

	public void setSrvAuditoria(AuditoriaService srvAuditoria){
		this.srvAuditoria=srvAuditoria;
	}

	/**
	 * Retorna un objeto Cliente a partir del ruc ingresado. Para esto conulta
	 * el webserice de la SUNAT.
	 * 
	 * @param ruc
	 * 			el numero de RUC del cliente
	 * @author German Enriquez
	 */
	//@Override
	//public ClienteJSon getClientePorRUCSunat(String ruc){
	//	ClienteJSon cliente=null;
                /*
		if(ruc!=null && !ruc.equals("")){
			DatosRucWSFacadeRemoteProxy proxy=new DatosRucWSFacadeRemoteProxy();
			try{
				BeanDdp principales=proxy.getDatosPrincipales(ruc);
				if(principales!=null){
					cliente=new ClienteJSon();
					if(principales.getDdp_numruc()!=null){
						log.debug("Cliente encontrado con razon social "+principales.getDdp_nombre());
						
						cliente.setIdentificacion(ruc);
						cliente.setRazonSocial(principales.getDdp_nombre().trim());
						String direccion=principales.getDdp_nomvia().trim()+" ";
						direccion+=principales.getDdp_numer1().trim()+" ";
						String interior=principales.getDdp_inter1().trim();
						if(interior!=null && !interior.equals("")){
							direccion+=interior+" ";
						}
						direccion+=principales.getDdp_nomzon().trim();
						cliente.setDireccion(direccion);
						int distrito=0;
						int provincia=0;
						int departamento=0;						
						try{
							distrito=Integer.parseInt(principales.getDdp_ubigeo());
							Distrito dist=distritoService.findById(distrito);							
							if(dist!=null){
								Provincia prov=dist.getProvincia();
								if(prov!=null){
									provincia=prov.getIdprovincia();
									departamento=prov.getDepartamento().getIddepartamento();
								}
							}
						}
						catch(NumberFormatException e){
							log.warn("No se pudo encontrar el distrito con ubigeo: "+principales.getDdp_ubigeo());
						}
						cliente.setDistrito(distrito);
						cliente.setProvincia(provincia);
						cliente.setDepartamento(departamento);
						//cliente.setRepresentantelegal(resultado.get)
						BeanDds secundarios=proxy.getDatosSecundarios(ruc);
						if(secundarios!=null){
							String telefono=secundarios.getDds_telef1().trim();
							if(!telefono.equals("-"))
								cliente.setTelefono(telefono);
						}
						BeanT1144 otros=proxy.getDatosT1144(ruc);
						if(otros!=null){
							String correo=otros.getCod_correo1().trim();
							if(!correo.equals("-"))
								cliente.setCorreo(correo);
						}
					}else{
						cliente.setIdentificacion("No se ha podido encontrar el RUC en los registros de la SUNAT") ;
					}
				}
			}catch(RemoteException e){
				log.error("Ocurrio un error al intentar conectarse con el webservice.",e);
				cliente=new ClienteJSon();
				cliente.setIdentificacion("Ocurri&oacute; un error al tratar de acceder a la SUNAT.");
			}
		}*/
		//return cliente;
	//}
        
         public List<Cliente> findClienteInstituciones(Integer tipoInstitucion){
            return dao.findClienteInstituciones(tipoInstitucion);
         }
         
          public List<Cliente> findClientePersonas(){
            return dao.findClientePersonas();
         }
          
           public List<Cliente> findClienteFiltroPersonas(String desPersona){
            return dao.findClienteFiltroPersonas(desPersona);
         }
           
          public List<Cliente> findClienteFiltroInstituciones(String desInstituciones){
            return dao.findClienteFiltroInstituciones(desInstituciones);
         }
          
         public List<Cliente> findClienteFiltroInstitucionesxTipo(String desInstituciones, Integer tipo){
            return dao.findClienteFiltroInstitucionesxTipo(desInstituciones, tipo);
         }  

           /*
	@Override
	public List<ClienteJSon> getClientesPorRazonSocialSunat(String razonSocial){
		List<ClienteJSon> clientes=null;
		if(razonSocial!=null && !razonSocial.equals("")){
			DatosRucWSFacadeRemoteProxy proxy=new DatosRucWSFacadeRemoteProxy();
			try{
				BeanDdp[] principales=proxy.buscaRazonSocial(razonSocial);
				if(principales!=null && principales.length>0){
					clientes=new ArrayList<ClienteJSon>();
					for(BeanDdp principal : principales){						
						if(principal!=null){
							String ruc=principal.getDdp_numruc();
							if(ruc!=null){
								ruc=ruc.trim();
								log.debug("Cliente encontrado con razon social "+principal.getDdp_nombre());
								ClienteJSon cliente=new ClienteJSon();
								cliente.setIdentificacion(ruc);
								cliente.setRazonSocial(principal.getDdp_nombre().trim());								
								clientes.add(cliente);
							}
						}						
					}
				}
			}
			catch(RemoteException e){
				log.error("Ocurrio un error al intentar conectarse con el webservice.",e);
			}
		}
		return clientes;
	}*/

	public void setDistritoService(DistritoService distritoService){
		this.distritoService=distritoService;
	}

   @Override
   public List<Cliente> findLstByCriteria(String sFiltroBusqueda) {
      StringTokenizer stFiltroBusqueda = null;
      String[] filtroBusqueda = null;

      try {
         stFiltroBusqueda = new StringTokenizer(sFiltroBusqueda);

         if (stFiltroBusqueda.countTokens() <= 0) {
            log.info("No se recibio filtro de busqueda");

            return null;
         }

         filtroBusqueda = new String[stFiltroBusqueda.countTokens()];
         int i = 0;

         while (stFiltroBusqueda.hasMoreTokens()) {
            filtroBusqueda[i++] = stFiltroBusqueda.nextToken();
         }

         if (log.isDebugEnabled()) {
            for (i = 0; i < filtroBusqueda.length; i++) {
               log.debug("token [" + filtroBusqueda[i] + "]");
            }
         }

         return dao.findLstByCriteria(filtroBusqueda);
      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return null;
      }
   }

   @Override
   public String generateNroIdentificacionOtro() {
      return dao.generateNroIdentificacionOtro();
   }

   @Override
   public Cliente findObjectBy(String numeroIdentificacion, Character estado) {
      if (log.isDebugEnabled()) {
         log.debug("Buscando cliente con numeroIdentificacion [" + numeroIdentificacion + "] estado [" + estado + "]");
      }

      if (StringUtil.isEmpty(numeroIdentificacion) || estado == null) {
         return null;
      }

      return dao.findObjectBy(numeroIdentificacion, estado);
   }
   
   public List<Cliente> findByNombreRazonLike(String like){
	   return dao.findByNombreRazonLike(like);
   }
}
