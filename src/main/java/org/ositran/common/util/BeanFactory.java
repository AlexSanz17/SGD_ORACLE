/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Victor Soria
 *
 */
public class BeanFactory implements ApplicationContextAware {
	
	/** The Spring application context. */
    private ApplicationContext applicationContext;
    
	public BeanFactory(ApplicationContext context) {
		setApplicationContext(context);
	}

	public void setApplicationContext(ApplicationContext context)
		throws BeansException {
			this.applicationContext = context;
	}
	
    /**
     * Return the configured Spring Bean for the given name.
     *
     * @param beanName the configured name of the Java Bean
     * @return the configured Spring Bean for the given name
     */
    public Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
    
    
}