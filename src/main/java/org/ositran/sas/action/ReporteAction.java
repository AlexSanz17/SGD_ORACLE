/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.sas.action;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.ositran.common.reportes.birt.BirtEngine;


public class ReporteAction  {
	private static Logger log = Logger.getLogger("org.ositran.sas.action.ReporteAction");

	protected String titulo;
	protected String reportName;
	protected HashMap<String,String> parametros;

	   public void lanzarReporte (String tipoReporte)throws ServletException, Exception {

		   if(tipoReporte.equalsIgnoreCase("html")){
			   imprimirHTMLBIRT();
		   } else if (tipoReporte.equalsIgnoreCase("xls")){
			   imprimirXLSBIRT();
		   } else if(tipoReporte.equalsIgnoreCase("pdf")){
			   imprimirPdfBIRT();
		   }
	   }

	   public void imprimirPdfBIRT() throws ServletException, Exception {

		   HttpServletResponse response;
		   HttpServletRequest request;
		   IReportEngine birtReportEngine = null;

		   response=ServletActionContext.getResponse();
		   request=ServletActionContext.getRequest();
		   response.setContentType("application/pdf");
	       response.setHeader("Content-Disposition", "inline; filename=WebReport.pdf");
	    //   String reportName = "cargoDocumento.rptdesign";
	       ServletContext sc=ServletActionContext.getServletContext();
	       birtReportEngine= BirtEngine.getBirtEngine(sc);

	    // setup image directory
	       HTMLRenderContext renderContext = new HTMLRenderContext();
	       renderContext.setBaseImageURL(request.getContextPath() + "/imagesBirt");
	       renderContext.setImageDirectory(sc.getRealPath("/imagesBirt"));

	       HashMap<String, HTMLRenderContext> contextMap = new HashMap<String, HTMLRenderContext>();
	       contextMap.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext);

	       IReportRunnable design;
	       try {
	           // Open report design
	           design = null;// birtReportEngine.openReportDesign(sc.getRealPath("/reports") + "/" + reportName +".rptdesign");

	           // create task to run and render report
	           IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(design);
	         //  task.setParameterValue("cadena", cadena);
	           Iterator it = parametros.entrySet().iterator();
	           log.debug("Nombre de Reporte: "+reportName);
	           while (it.hasNext()) {
	        	   Map.Entry e = (Map.Entry)it.next();

	        	   log.debug("Nombre de Parametro: "+e.getKey() + " valor: " + e.getValue());
	        	   task.setParameterValue((String)e.getKey(), e.getValue());
	        	   }
	           task.setAppContext(contextMap);

	           // set output options
	           HTMLRenderOption options = new HTMLRenderOption();
	           options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_PDF);
	           options.setOutputStream(response.getOutputStream());
	           task.setRenderOption(options);

	           // run report
	           task.run();
	           task.close();
	       } catch (Exception e) {
	           log.debug(Level.SEVERE+ e.getMessage(), e);
	           throw new ServletException(e);
	       }



	   }


	   public void imprimirHTMLBIRT() throws ServletException, Exception {

		   ByteArrayOutputStream out = new ByteArrayOutputStream();

		   HttpServletResponse response;
		   HttpServletRequest request;
		   IReportEngine birtReportEngine = null;

		   response=ServletActionContext.getResponse();
		   request=ServletActionContext.getRequest();
		   response.setContentType("text/html");
	       response.setHeader("Content-Disposition", "inline; filename=WebReport.html");
	  //     String reportName = "cargoDocumento.rptdesign";
	       ServletContext sc=ServletActionContext.getServletContext();
	       birtReportEngine= BirtEngine.getBirtEngine(sc);

	    // setup image directory
	       HTMLRenderContext renderContext = new HTMLRenderContext();
	       renderContext.setBaseImageURL(request.getContextPath() + "/imagesBirt");
	       renderContext.setImageDirectory(sc.getRealPath("/imagesBirt"));
	       log.debug("setBaseImageURL: "+request.getContextPath() + "/imagesBirt");
	       log.debug("setImageDirectory: "+sc.getRealPath("/imagesBirt"));

	       HashMap<String, HTMLRenderContext> contextMap = new HashMap<String, HTMLRenderContext>();
	       contextMap.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext);

	       IReportRunnable design;
	       try {
	           // Open report design
	    	   design = null;//birtReportEngine.openReportDesign(sc.getRealPath("/reports") + "/" + reportName +".rptdesign");

	           // create task to run and render report
	           IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(design);
	       //    task.setParameterValue("cadena", cadena);
	           log.debug("Nombre de Reporte: "+reportName);
	           Iterator it = parametros.entrySet().iterator();
	           while (it.hasNext()) {
	        	   Map.Entry e = (Map.Entry)it.next();
	        	   log.debug("Nombre de Parametro: "+e.getKey() + " valor: " + e.getValue());
	        	   task.setParameterValue((String)e.getKey(), e.getValue());
	        	   }

	           task.setAppContext(contextMap);

	           // set output options
	           HTMLRenderOption options = new HTMLRenderOption();
	           options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_HTML);
	           options.setOutputStream(out);
	           options.setEmbeddable(true);
	           //options.setOutputStream(response.getOutputStream());
	           task.setRenderOption(options);

	           // run report
	           task.run();
	           task.close();
	           out.writeTo(response.getOutputStream());

	       } catch (Exception e) {
	           log.debug(Level.SEVERE+ e.getMessage(), e);
	           throw new ServletException(e);
	       }



	   }


public void imprimirXLSBIRT() throws ServletException, Exception {

		   HttpServletResponse response;
		   HttpServletRequest request;
		   IReportEngine birtReportEngine = null;

		   response=ServletActionContext.getResponse();
		   request=ServletActionContext.getRequest();
		   response.setContentType("application/vnd.ms-excel");
	       response.setHeader("Content-Disposition", "inline; filename=WebReport.xls");
	  //     String reportName = "cargoDocumento.rptdesign";
	       ServletContext sc=ServletActionContext.getServletContext();
	       birtReportEngine= BirtEngine.getBirtEngine(sc);

	    // setup image directory
	       HTMLRenderContext renderContext = new HTMLRenderContext();
	       renderContext.setBaseImageURL(request.getContextPath() + "/imagesBirt");
	       renderContext.setImageDirectory(sc.getRealPath("/imagesBirt"));

	       HashMap<String, HTMLRenderContext> contextMap = new HashMap<String, HTMLRenderContext>();
	       contextMap.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContext);

	       IReportRunnable design;
	       try {
	           // Open report design
	    	   design = null;// birtReportEngine.openReportDesign(sc.getRealPath("/reports") + "/" + reportName +".rptdesign");

	           // create task to run and render report
	           IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(design);
	     //      task.setParameterValue("cadena", cadena);
	           log.debug("Nombre de Reporte: "+reportName);
	           Iterator it = parametros.entrySet().iterator();
	           while (it.hasNext()) {
	        	   Map.Entry e = (Map.Entry)it.next();
	        	   log.debug("Nombre de Parametro: "+e.getKey() + " valor: " + e.getValue());
	        	   task.setParameterValue((String)e.getKey(), e.getValue());
	        	   }

	           task.setAppContext(contextMap);

	           // set output options
	           HTMLRenderOption options = new HTMLRenderOption();
	           options.setOutputFormat("xls");
	           options.setOutputStream(response.getOutputStream());
	           task.setRenderOption(options);

	           // run report
	           task.run();
	           task.close();
	       } catch (Exception e) {
	           log.debug(Level.SEVERE+ e.getMessage(), e);
	           throw new ServletException(e);
	       }



	   }


public String getReportName() {
	return reportName;
}


public void setReportName(String reportName) {
	this.reportName = reportName;
}


public HashMap<String, String> getParametros() {
	return parametros;
}


public void setParametros(HashMap<String, String> parametros) {
	this.parametros = parametros;
}

public String getTitulo() {
	return titulo;
}

public void setTitulo(String titulo) {
	this.titulo = titulo;
}





}
