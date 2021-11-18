/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ldap;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class UsuarioLdap implements DirContext {

	/* private String idUsuario;
	
	private String nombre;
	
	private String apellidos;
	
	private String password;
	
	private String correo;  */
	
	private String baseDN;
	
	private List<String[]> campos;
	
	private String ou;
	
	private String[] roles;
	
	Attributes atributos;
	
	Attribute oc;

	public UsuarioLdap(String baseDN,String idUsuario, String password) {
		this(baseDN,idUsuario,password,null,null,null);
	}
	
	public UsuarioLdap(String baseDN,String idUsuario, String password,String nombre){
		this(baseDN,idUsuario,password,nombre,null,null);
	}
	
	public UsuarioLdap(String baseDN,String idUsuario, String password,String nombre,String apellidos){
		this(baseDN,idUsuario,password,nombre,apellidos,null);
	}
	
	public UsuarioLdap(String baseDN,String idUsuario, String password,String nombre,String apellidos,String correo){
		this.baseDN=baseDN;
		/*this.idUsuario=idUsuario;
		this.password=password;
		this.nombre=nombre;
		this.apellidos=apellidos;
		this.correo=correo;*/
		oc=new BasicAttribute("objectclass");
		atributos=new BasicAttributes(true);
		ou=Config.getPropiedad("ldap.usuario.ou");
		crearAtributos(idUsuario,password,nombre,apellidos,correo);
		llenarAtributos();
	}
	
	public UsuarioLdap(String baseDN,List<String[]> campos,String[] roles){
		this.baseDN=baseDN;
		this.campos=campos;
		this.roles=roles;
		oc=new BasicAttribute("objectclass");
		atributos=new BasicAttributes(true);
		ou=Config.getPropiedad("ldap.usuario.ou");		
		llenarAtributos();
	}
	
	private void crearAtributos(String idUsuario, String password,String nombre,String apellidos,String correo){
		campos=new ArrayList<String[]>();
		String atributos=Config.getPropiedad("ldap.usuario.atributos");
		StringTokenizer st=new StringTokenizer(atributos,",");
		while(st.hasMoreTokens()){
			String elemento=st.nextToken().trim();
			String tipo=elemento.substring(elemento.length()-2,elemento.length()-1);
			elemento=elemento.substring(0,elemento.length()-3);
			String[] fila=new String[3];
			fila[0]=elemento;
			if(elemento.equals("uid"))
				fila[1]=idUsuario;
			else if(elemento.equals("userPassword"))
				fila[1]=password;
			else if(elemento.equals("givenName"))
				fila[1]=nombre;
			else if(elemento.equals("sn"))
				fila[1]=apellidos;
			else if(elemento.equals("mail"))
				fila[1]=correo;
			else if(elemento.equals("cn"))
				fila[1]=nombre+" "+apellidos;			
			fila[2]=tipo;
			campos.add(fila);
		}
	}
	
	private void llenarAtributos(){
		String objectClass=Config.getPropiedad("ldap.usuario.objectclass");
		StringTokenizer st=new StringTokenizer(objectClass,",");
		while(st.hasMoreTokens()){
			oc.add(st.nextToken().trim());
		}		
		atributos.put(oc);
		for(int i=0;i<campos.size();i++){
			String[] datos=campos.get(i);
			if(datos[2].equalsIgnoreCase("s")){
				atributos.put(datos[0],datos[1]);
			}
			else if(datos[2].equalsIgnoreCase("m")){
				//FIXME esto se ve feo
				Attribute attr=new BasicAttribute(datos[0]);
				if(datos[0].equals("ou")){
					for(int k=0;k<roles.length;k++){
						attr.add("cn="+roles[k]+",ou="+Config.getPropiedad("ldap.roles.ou")+","+baseDN);
					}
					if(campos.get(0)[1].equals(Config.getPropiedad("ldap.usuario.admin"))){
						String ous=Config.getPropiedad("ldap.usuario.admin.roles");								
						StringTokenizer tok=new StringTokenizer(ous,";");
						while(tok.hasMoreTokens()){
							attr.add(tok.nextToken().trim());
						}
					}
					else{
						attr.add(Config.getPropiedad("ldap.usuario.default.rol"));
					}
				}
				atributos.put(attr);
			}
		}
	}
	
	public String getDN(){
		return campos.get(0)[0]+"="+campos.get(0)[1]+",ou="+ou+","+baseDN;
	}
	
	public String getBaseDN(){
		return baseDN;
	}
	
	public void setBaseDN(String baseDN){
		this.baseDN=baseDN;
	}

	/*public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}*/
	
	

	@Override
	public void bind(Name arg0, Object arg1, Attributes arg2)
			throws NamingException {}

	@Override
	public void bind(String arg0, Object arg1, Attributes arg2)
			throws NamingException {}

	@Override
	public DirContext createSubcontext(Name arg0, Attributes arg1)
			throws NamingException {
		return null;
	}

	@Override
	public DirContext createSubcontext(String arg0, Attributes arg1)
			throws NamingException {
		return null;
	}

	@Override
	public Attributes getAttributes(Name name) throws NamingException {
		return getAttributes(name.toString());
	}

	@Override
	public Attributes getAttributes(String name) throws NamingException {
		if (! name.equals("")){
			throw new NameNotFoundException();
		}
		return atributos;
	}

	@Override
	public Attributes getAttributes(Name name, String[] ids)
			throws NamingException {
		return getAttributes(name.toString(),ids);
	}

	@Override
	public Attributes getAttributes(String name, String[] ids)
			throws NamingException {
		if(! name.equals(""))
			throw new NameNotFoundException();
		Attributes answer = new BasicAttributes(true);
		Attribute target;
		for (int i = 0; i < ids.length; i++){
			target = atributos.get(ids[i]);
			if (target != null){
				answer.put(target);
			}
		}
		return answer;
	}

	@Override
	public DirContext getSchema(Name arg0) throws NamingException {
		return null;
	}

	@Override
	public DirContext getSchema(String arg0) throws NamingException {
		return null;
	}

	@Override
	public DirContext getSchemaClassDefinition(Name arg0)
			throws NamingException {
		return null;
	}

	@Override
	public DirContext getSchemaClassDefinition(String arg0)
			throws NamingException {
		return null;
	}

	@Override
	public void modifyAttributes(Name arg0, ModificationItem[] arg1)
			throws NamingException {}

	@Override
	public void modifyAttributes(String arg0, ModificationItem[] arg1)
			throws NamingException {}

	@Override
	public void modifyAttributes(Name arg0, int arg1, Attributes arg2)
			throws NamingException {}

	@Override
	public void modifyAttributes(String arg0, int arg1, Attributes arg2)
			throws NamingException {}

	@Override
	public void rebind(Name arg0, Object arg1, Attributes arg2)
			throws NamingException {}

	@Override
	public void rebind(String arg0, Object arg1, Attributes arg2)
			throws NamingException {}

	@Override
	public NamingEnumeration<SearchResult> search(Name arg0, Attributes arg1)
			throws NamingException {
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(String arg0, Attributes arg1)
			throws NamingException {
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(Name arg0, Attributes arg1,
			String[] arg2) throws NamingException {
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(String arg0, Attributes arg1,
			String[] arg2) throws NamingException {
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(Name arg0, String arg1,
			SearchControls arg2) throws NamingException {
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(String arg0, String arg1,
			SearchControls arg2) throws NamingException {
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(Name arg0, String arg1,
			Object[] arg2, SearchControls arg3) throws NamingException {
		return null;
	}

	@Override
	public NamingEnumeration<SearchResult> search(String arg0, String arg1,
			Object[] arg2, SearchControls arg3) throws NamingException {
		return null;
	}

	@Override
	public Object addToEnvironment(String arg0, Object arg1)
			throws NamingException {
		return null;
	}

	@Override
	public void bind(Name arg0, Object arg1) throws NamingException {}

	@Override
	public void bind(String arg0, Object arg1) throws NamingException {}

	@Override
	public void close() throws NamingException {}

	@Override
	public Name composeName(Name arg0, Name arg1) throws NamingException {
		return null;
	}

	@Override
	public String composeName(String arg0, String arg1) throws NamingException {
		return null;
	}

	@Override
	public Context createSubcontext(Name arg0) throws NamingException {
		return null;
	}

	@Override
	public Context createSubcontext(String arg0) throws NamingException {
		return null;
	}

	@Override
	public void destroySubcontext(Name arg0) throws NamingException {}

	@Override
	public void destroySubcontext(String arg0) throws NamingException {}

	@Override
	public Hashtable<?, ?> getEnvironment() throws NamingException {
		return null;
	}

	@Override
	public String getNameInNamespace() throws NamingException {
		return null;
	}

	@Override
	public NameParser getNameParser(Name arg0) throws NamingException {
		return null;
	}

	@Override
	public NameParser getNameParser(String arg0) throws NamingException {
		return null;
	}

	@Override
	public NamingEnumeration<NameClassPair> list(Name arg0)
			throws NamingException {
		return null;
	}

	@Override
	public NamingEnumeration<NameClassPair> list(String arg0)
			throws NamingException {
		return null;
	}

	@Override
	public NamingEnumeration<Binding> listBindings(Name arg0)
			throws NamingException {
		return null;
	}

	@Override
	public NamingEnumeration<Binding> listBindings(String arg0)
			throws NamingException {
		return null;
	}

	@Override
	public Object lookup(Name arg0) throws NamingException {
		return null;
	}

	@Override
	public Object lookup(String arg0) throws NamingException {
		return null;
	}

	@Override
	public Object lookupLink(Name arg0) throws NamingException {
		return null;
	}

	@Override
	public Object lookupLink(String arg0) throws NamingException {
		return null;
	}

	@Override
	public void rebind(Name arg0, Object arg1) throws NamingException {}

	@Override
	public void rebind(String arg0, Object arg1) throws NamingException {}

	@Override
	public Object removeFromEnvironment(String arg0) throws NamingException {
		return null;
	}

	@Override
	public void rename(Name arg0, Name arg1) throws NamingException {}

	@Override
	public void rename(String arg0, String arg1) throws NamingException {}

	@Override
	public void unbind(Name arg0) throws NamingException {}

	@Override
	public void unbind(String arg0) throws NamingException {}

}
