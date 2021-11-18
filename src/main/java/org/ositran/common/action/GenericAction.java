/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.common.action;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.ositran.common.util.BeanFactory;
import org.ositran.common.util.ServiceFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.util.PropertiesReader;

public class GenericAction  {

	private ApplicationContext applicationContext;

	/**
	 * Fabrica de Objetos tipo Bean
	 */
	protected static BeanFactory beanFactory;

	/**
	 * Fabrica de Objetos tipo Service
	 */
	protected static ServiceFactory serviceFactory;

	/**
	 * Manejador de mensajes
	 */
	protected static PropertiesReader messages;
	
	private static Logger log = Logger.getLogger(GenericAction.class);

	public GenericAction() {

	}
	
	public void iniciarFactorias()
	{
		try {
			if (applicationContext == null) {
				applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
			}
			if (beanFactory == null) {
				beanFactory = new BeanFactory(applicationContext);
			}
			if (serviceFactory == null) {
				serviceFactory = new ServiceFactory(applicationContext);
			}
	//		if (messages == null) {
	//			messages = (PropertiesReader) applicationContext.getBean("PropertiesReader");
	//		}
		} catch (Exception e) {
			log.error(e);
		}

	}
	
}
