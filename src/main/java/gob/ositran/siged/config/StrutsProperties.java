/*LICENCIA DE USO DEL SGD .TXT*/package gob.ositran.siged.config;

import java.util.Properties;
import org.apache.log4j.Logger;
import org.ositran.utils.PropertyFileUtils;

/**
 *
 * @author javier castillo
 */
public class StrutsProperties {

    private static final Logger log = Logger.getLogger(StrutsProperties.class);
    private static final Properties properties;
    private static final String STRUTS_PROPERTIES = "struts.properties";

    public enum SigedPropertyEnum {

        MULTIPART_MAX_SIZE("struts.multipart.maxSize");
        private String key;

        SigedPropertyEnum(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        @Override
        public String toString() {
            return key;
        }
    }

    static {
        properties = new Properties();
        try {
            properties.putAll(PropertyFileUtils.load(STRUTS_PROPERTIES));
        } catch (Exception e) {
            log.error("No se encuentra el archivo '" + STRUTS_PROPERTIES + "' en el classpath.", e);
        }
    }

    private StrutsProperties() {
    }

    public static String getProperty(SigedPropertyEnum sigedProperty) {
        return properties.getProperty(sigedProperty.getKey());
    }

    public static int getIntProperty(SigedPropertyEnum sigedProperty) {
        return Integer.valueOf(properties.getProperty(sigedProperty.getKey())).intValue();
    }

    public static long getLongProperty(SigedPropertyEnum sigedProperty) {
        return Long.valueOf(properties.getProperty(sigedProperty.getKey())).longValue();
    }
}
