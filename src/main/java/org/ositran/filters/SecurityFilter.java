/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.filters;


import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import java.io.IOException;




/**
 * Clase que actua como cortafuegos para todos los accesos
 * no autorizados al sistema.
 * Garantiza que para ejecutar las operaciones del sistema el usuario
 * haya pasado por la ventana de login.
 * @author ACOTRINA
 * @modificado Sergio Buchelli
 */
public class SecurityFilter implements Filter
{
  /** Objeto con los datos de configuracion del filtro. */

	private static Logger logger=Logger.getLogger(SecurityFilter.class);

	private FilterConfig filterConfig=null;



	/*public SecurityFilter(LoginService loginService){
		this.loginService=loginService;
	}*/

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
		HttpServletRequest hReq = (HttpServletRequest) request;
		System.out.println("xxxxxxxxxxxx................................................");
                        HttpSession session = ((HttpServletRequest) hReq).getSession();
                        if (session != null){
                              chain.doFilter(request, response);
                        }else{ 
                             ((HttpServletResponse)response).sendRedirect("/sigedR/login.jsp");
                        }
                       
                   
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
	}

	public void destroy(){

       
        }
	
}