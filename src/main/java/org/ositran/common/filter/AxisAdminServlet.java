package org.ositran.common.filter;

import org.apache.axis2.Constants;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.transport.http.AdminAgent;
import org.apache.axis2.transport.http.AxisServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class AxisAdminServlet extends AxisServlet {
    private static final long serialVersionUID = -6740625806509755370L;
    
    protected transient AdminAgent agent;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doGet(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getSession().setAttribute(Constants.SERVICE_PATH, configContext.getServicePath());
            agent.handle(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext servletContext = config.getServletContext();
        this.configContext =
                (ConfigurationContext) servletContext.getAttribute(CONFIGURATION_CONTEXT);
        servletContext.setAttribute(this.getClass().getName(), this);
        agent = new AdminAgent(configContext);
        this.servletConfig = config;
    }

    @Override
    public void init() throws ServletException {
        if (this.servletConfig != null) {
            init(this.servletConfig);
        }
    }
}
