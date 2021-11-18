package org.ositran.common.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/** A servlet filter that simply adds all header specified in its config
to replies the filter is mapped to. An example would be to set the cache
control max age:

<filter>
<filter-name>CacheControlFilter</filter-name>
<filter-class>filter.ReplyHeaderFilter</filter-class>
<init-param>
<param-name>Cache-Control</param-name>
<param-value>max-age=3600</param-value>
</init-param>
</filter>

<filter-mapping>
<filter-name>CacheControlFilter</filter-name>
<url-pattern>/images/*</url-pattern>
</filter-mapping>
<filter-mapping>
<filter-name>CacheControlFilter</filter-name>
<url-pattern>*.js</url-pattern>
</filter-mapping>


@author Scott.Stark@jboss.org
@version $Revison:$
 */
public class ReplyHeaderFilter implements Filter {

    private String[][] replyHeaders = {{}};

    @Override
    public void init(FilterConfig config) {
        Enumeration names = config.getInitParameterNames();
        ArrayList tmp = new ArrayList();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = config.getInitParameter(name);
            String[] pair = {name, value};
            tmp.add(pair);
        }
        replyHeaders = new String[tmp.size()][2];
        tmp.toArray(replyHeaders);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        // Apply the headers
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        for (int n = 0; n < replyHeaders.length; n++) {
            String name = replyHeaders[n][0];
            String value = replyHeaders[n][1];
            // httpResponse.addHeader(name, value);
            httpResponse.setHeader(name, value);
        }
        chain.doFilter(request, httpResponse);
    }

    @Override
    public void destroy() {
    }
}
