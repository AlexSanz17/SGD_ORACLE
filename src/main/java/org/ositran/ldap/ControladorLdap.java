/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ldap;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;

/**
 * Clase que se encarga de almacenar los usuarios de siged dentro del servidor Ldap
 * 
 * @author Germán Enríquez Illescas
 *
 */

public class ControladorLdap {

	private static Hashtable<String,String> conexion=null;
	
	private static DirContext contexto=null;
	
	private static Logger log=Logger.getLogger(ControladorLdap.class);
	
	private static String dn;
	
	private static void iniciar() throws Exception{
		if(contexto==null){
			conexion=new Hashtable<String,String>();
			String url=Config.getPropiedad("ldap.url");
			conexion.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
			conexion.put(Context.PROVIDER_URL,url);
			conexion.put(Context.SECURITY_AUTHENTICATION,"simple");
			conexion.put(Context.SECURITY_PRINCIPAL,Config.getPropiedad("ldap.usuario"));
			conexion.put(Context.SECURITY_CREDENTIALS,Config.getPropiedad("ldap.password"));
			try{
				contexto=new InitialDirContext(conexion);
				if(log.isDebugEnabled())
					log.debug("Conectado al servidor Ldap: "+url);
				dn=Config.getPropiedad("ldap.baseDN");
			}catch(NamingException e){
				log.error("No se pudo conectar con el servidor",e);
				throw new Exception("No se pudo conectar con el servidor de Ldap.");
			}
		}
	}
	
	public static boolean guardarUsuario(String usuario,String password,String nombre,String apellido,String mail,String[] roles,String usuarioAnterior){
		String usuDN="";
		if(mail==null || mail.equals(""))
			mail=" ";
		try {
			iniciar();			
			List<String[]> campos=cargarCampos(usuario,password,nombre,apellido,mail);
			guardarRoles(roles);			
			//buscamos al usuario en el directorio
			if(usuarioAnterior==null || usuarioAnterior.equals(""))
				usuarioAnterior=usuario;
			Attributes campo=new BasicAttributes(true);
			campo.put(new BasicAttribute("uid",usuarioAnterior));
			NamingEnumeration<SearchResult> resultado=contexto.search("ou="+Config.getPropiedad("ldap.usuario.ou")+","+dn,campo);			
			if(resultado.hasMore()){
				//el usuario ya existe				
				SearchResult item=resultado.next();
				usuDN=item.getNameInNamespace();
				log.debug("Encontrado el usuario: "+usuDN);
				//si el se esta cambiando el nombre de usuario es necesario
				//eliminar el registro anterior y crear uno nuevo
				if(!usuarioAnterior.equals(usuario)){
					log.debug("Se ha cambiado el nombre de usuario.");
					contexto.destroySubcontext(usuDN);
					log.info("Eliminado el usuario "+usuDN+" del directorio. Se creara un nuevo usuario con nombre de usuario: "+usuario);
				}
				else{
					Attribute[] atributos=new BasicAttribute[campos.size()];
					int j=0;
					for(String[] datos:campos){
						if(datos[2].equalsIgnoreCase("s")){
							atributos[j]=new BasicAttribute(datos[0],datos[1]);
							j++;
						}
						else if(datos[2].equalsIgnoreCase("m")){
							//FIXME esto se ve feo
							atributos[j]=new BasicAttribute(datos[0]);
							if(datos[0].equals("ou")){
								for(int k=0;k<roles.length;k++){
									atributos[j].add("cn="+roles[k]+",ou="+Config.getPropiedad("ldap.roles.ou")+","+dn);
								}
								if(usuario.equals(Config.getPropiedad("ldap.usuario.admin"))){
									String ous=Config.getPropiedad("ldap.usuario.admin.roles");								
									StringTokenizer st=new StringTokenizer(ous,";");
									while(st.hasMoreTokens()){
										atributos[j].add(st.nextToken().trim());
									}
								}
								else{
									atributos[j].add(Config.getPropiedad("ldap.usuario.default.rol"));
								}
								log.debug("Añadiendo el rol \""+atributos[j]+"\" para el usuario \""+usuDN+"\"");
							}
							j++;
						}
					}
					ModificationItem[] modificaciones = new ModificationItem[atributos.length];
					for(int i=0;i<atributos.length;i++){
						modificaciones[i]=new ModificationItem(DirContext.REPLACE_ATTRIBUTE,atributos[i]);
					}				
					contexto.modifyAttributes(usuDN,modificaciones);
					log.info("El usuario \""+usuDN+"\" ha sido modificado en el directorio");
					return true;
				}
			}
			UsuarioLdap nuevoUsuario=new UsuarioLdap(dn,campos,roles);
			usuDN=nuevoUsuario.getDN();
			log.debug("Creando el usuario: "+usuDN);				
			contexto.bind(nuevoUsuario.getDN(),nuevoUsuario);
			log.info("El usuario \""+usuDN+"\" ha sido creado en el directorio");
			return true;
		}catch(Exception e){
			log.error("Error guardando el usuario "+usuDN,e);
			return false;
		}
	}
	
	public static void guardarRoles(String[] roles){
		for(int i=0;i<roles.length;i++){
			guardarRol(roles[i]);
		}
	}
	
	public static void guardarRol(String rol){
		try{
			iniciar();
			String ou="ou="+Config.getPropiedad("ldap.roles.ou")+","+dn;
			String rolDN="cn="+rol+","+ou;
			Attributes campo=new BasicAttributes(true);
			campo.put(new BasicAttribute("cn",rol));
			NamingEnumeration<SearchResult> resultado=contexto.search(ou,campo);
			if(!resultado.hasMore()){
				//FIXME facil esto deberia sacarlo del archivo de configuracion
				Attribute objectClass=new BasicAttribute("objectClass");
				objectClass.add("top");
				objectClass.add("organizationalRole");
				campo.put(objectClass);
				log.debug("Guardando el rol "+rolDN);
				contexto.bind(rolDN,null,campo);
				log.info("Se guardo el rol "+rolDN);
			}
			else{
				log.debug("No se guardo el rol \""+rolDN+"\" debido a que ya existe en el directorio");
			}
		}
		catch(Exception e){
			log.error("Error guardando el rol "+rol,e);
		}
	}
	
	private static List<String[]> cargarCampos(String usuario,String password,String nombre,String apellido,String mail){
		List<String[]> campos=new ArrayList<String[]>();
		String atributos=Config.getPropiedad("ldap.usuario.atributos");
		StringTokenizer st=new StringTokenizer(atributos,",");
		while(st.hasMoreTokens()){
			String elemento=st.nextToken().trim();
			String tipo=elemento.substring(elemento.length()-2,elemento.length()-1);
			elemento=elemento.substring(0,elemento.length()-3);
			String[] fila=new String[3];
			fila[0]=elemento;
			if(elemento.equals("uid"))
				fila[1]=usuario;
			else if(elemento.equals("userPassword"))
				fila[1]=password;
			else if(elemento.equals("givenName"))
				fila[1]=nombre;
			else if(elemento.equals("sn"))
				fila[1]=apellido;
			else if(elemento.equals("mail"))
				fila[1]=mail;
			else if(elemento.equals("cn"))
				fila[1]=nombre+" "+apellido;			
			fila[2]=tipo;
			campos.add(fila);			
		}
		return campos;
	}
	
}
