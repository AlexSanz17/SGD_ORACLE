package org.ositran.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.btg.ositran.siged.domain.Rol;
import com.btg.ositran.siged.domain.Usuario;

public class UtilOsinerg {
	
	private static Logger log = Logger.getLogger(UtilOsinerg.class);
	 
    public static Usuario propietarioActual(Usuario usrLogeado,Usuario propietario){
    	Usuario forward = usrLogeado;
        List<Usuario> compartidos=null;
        if (propietario!=null){
            compartidos=propietario.getCompartidoxusuario();
        }
    	if(compartidos!=null){
    		if(compartidos.size()>0){
    			for(Usuario u: compartidos){
    				if(u.getIdusuario().equals(usrLogeado.getIdusuario())){
    					forward = propietario;
    				}
    			}
    		}
    	}
    	return forward;
    }	
    
    public static Usuario verificarUsuariosCompartidos(Usuario usrLogeado,Usuario propietarioDoc){
    	Usuario forward = usrLogeado;
        List<Usuario> compartidos=null;
        if (usrLogeado!=null){
            compartidos=usrLogeado.getCompartidoxusuario();
        }
    	if(compartidos!=null){
    		if(compartidos.size()>0){
    			for(Usuario u: compartidos){
    				if(u.getIdusuario().equals(propietarioDoc.getIdusuario())){
    					forward = propietarioDoc;
    				}
    			}
    		}else{
    			forward=usrLogeado;
    		}
    	}
    	return forward;
    }
    
    public static Integer totalCompartidos(Usuario propietario){
        int total=0;
        List<Usuario> compartidos=propietario.getCompartidoxusuario();
        if (propietario!=null){
        	if(compartidos!=null){
        		total=compartidos.size();
        	}
        }
    	return total;
    }
        
    
    public static boolean noEsPropietario(Usuario usrLogeado,Usuario propietario){
    	boolean forward=true;
        List<Usuario> compartidos=null;
        if (propietario!=null){
            compartidos=propietario.getCompartidoxusuario();
        }
    	if(compartidos!=null){
    		if(compartidos.size()>0){
    			for(Usuario u: compartidos){
    				log.info("CASO OBTENER BANDEJA Y RECURSOS: Usuario Compartido:"+u.getIdusuario() +" - Usuario Logeado:"+usrLogeado.getIdusuario());
    				if(u.getIdusuario().equals(usrLogeado.getIdusuario())){
    					forward=false;
    				}
    			}
    		}
    	}
    	if(propietario!=null && usrLogeado.getIdusuario().equals(propietario.getIdusuario())){
    		forward=false;
    	}
    	
    	return forward;
    }    
    
    public static boolean tieneRolUsuarioFinal(List<Rol> roles,Map<String,Integer> recursos){
    	boolean forward=false;
    	
    	for(Rol r: roles){
    		if(r.getNombre().equals(Constantes.ROL_USUARIO_FINAL)){
    	    	int MPMnuDocReg=recursos.get("MPMnuDocReg").intValue();
    	    	int DigMnuDocIng=recursos.get("DigMnuDocIng").intValue();
    	    	int QASMnuDigitalizados=recursos.get("QASMnuDigitalizados").intValue();
    	    	if(MPMnuDocReg==0 ||DigMnuDocIng==0 || QASMnuDigitalizados==0){
    	    		forward=true;
    	    		log.info("tiene el rol usuario final");
    	    	}
    		}
    	}
    	return forward;
    }

    public static Integer calcularDiasFaltantesEnDoc(Date fechacreacionExp,Integer tiempoProceso){
        Calendar fechaExpediente = Calendar.getInstance();
        fechaExpediente.setTime(fechacreacionExp);
        Calendar hoy = Calendar.getInstance();
        int diasPasados = hoy.get(Calendar.DAY_OF_YEAR) - fechaExpediente.get(Calendar.DAY_OF_YEAR);
        int diasFaltantes = tiempoProceso - diasPasados;
        return diasFaltantes;
    }

}
