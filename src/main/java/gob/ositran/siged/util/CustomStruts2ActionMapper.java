/*LICENCIA DE USO DEL SGD .TXT*/package gob.ositran.siged.util;

import org.apache.struts2.dispatcher.mapper.ActionMapping;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.config.ConfigurationManager;

/**
 * ActionMapper that excludes all /services/* URLs. A shame that this cant be
 * done simpler w/o a custom ActionMapper
 *
 */
public class CustomStruts2ActionMapper extends org.apache.struts2.dispatcher.mapper.PrefixBasedActionMapper {

    @Override
    public ActionMapping getMapping(HttpServletRequest request, ConfigurationManager configManager) {
        String uri = getUri(request);
        if (uri.matches("/remote/.*")) {
            return null;
        }
        return super.getMapping(request, configManager);
    }
}
