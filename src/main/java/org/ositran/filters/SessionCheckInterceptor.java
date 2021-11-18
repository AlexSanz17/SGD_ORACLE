/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.filters;

import com.btg.ositran.siged.domain.Usuario;
import java.util.Map;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.ositran.utils.Constantes;
import java.net.URL;
import javax.servlet.ServletContext;
import org.apache.struts2.RequestUtils;
import org.apache.struts2.StrutsStatics;
import org.ositran.actions.UsuarioAction;
public class SessionCheckInterceptor extends AbstractInterceptor implements
		StrutsStatics{

 @Override
 public void destroy() {
  // TODO Auto-generated method stub

 }

 @Override
 public void init() {
   System.out.println("init.................................");
  // TODO Auto-generated method stub

 }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
       /* Map<String, Object> mapSession = invocation.getInvocationContext().getSession();
        System.out.println("..............Entro1.....................");
        Usuario user = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
      */ try{
        /*if(user == null){
             try{
               System.out.println("....eeeeee....ssss.Entro2xxxx.............................");
                 HttpServletRequest request = ServletActionContext.getRequest();
                 HttpServletResponse response = ServletActionContext.getResponse();
                 StringBuffer url = request.getRequestURL();
                 System.out.println(url.toString());
                 
         
             }catch(Exception e){
                 System.out.println("x....................................");
             }   
         }
               UsuarioAction action = (UsuarioAction)invocation.getAction();
               action.prueba();
              return invocation.invoke();*/
          // HttpServletRequest request = (HttpServletRequest)invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
        //HttpServletResponse response = (HttpServletResponse)invocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);
          // ServletContext sc = (ServletContext)ServletActionContext.getServletContext();
             //   sc.getRequestDispatcher("/error.html").forward(request, response);
        //   return "login";
          
       }catch(Exception e){
           e.printStackTrace();
           System.out.println("cccccc........................");
           
           
       }
      
         return invocation.invoke();
     }
    }

