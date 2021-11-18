<%@page contentType="text/html" pageEncoding="UTF-8"  import="java.util.Enumeration"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <%  
      String parameters ="?";
      Enumeration names = request.getParameterNames();
       while (names.hasMoreElements()){
    	   String name = (String)names.nextElement();
    	   parameters+= name+"="+request.getParameter(name); 
    	   if(names.hasMoreElements()){
    		   parameters+="&";
    	   }
       }
      %>               

<frameset cols="190,*" id="centro">
   <frame src="<s:url action="doMenu" />" name="leftFrame" scrolling="no" frameborder="no" noresize />
 
      <frameset rows="300,*" cols="*" > 
         <frame src="<s:url action="doBuscarpeta"/><%=parameters%>" name="mainFrame" />
         <frame src="secondFrame.jsp" name="secondFrame"/>
      </frameset>
                    
</frameset>


