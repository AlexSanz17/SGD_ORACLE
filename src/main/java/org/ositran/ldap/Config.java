/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.ldap;

import gob.ositran.siged.util.ArchivoPropertiesEnum;
import java.io.File;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.ositran.utils.PropertyFileUtils;

/**
 * 
 * @author Germán Enríquez Illescas
 *
 */
public final class Config {

    private static final Logger log = Logger.getLogger(Config.class);
    private static final int NIVEL = 10;
    private static Properties properties;

    static {
        try {
            String rutaArchivo = System.getProperty("chimera.configuracion");
            if (rutaArchivo != null) {
                properties = PropertyFileUtils.load(new File(rutaArchivo));
            } else {
                properties = PropertyFileUtils.load(ArchivoPropertiesEnum.LDAP.getUbicacion());
            }
            cargarConfiguracion();
        } catch (Exception e) {
            log.error("No se encuentra el archivo '" + ArchivoPropertiesEnum.LDAP.getUbicacion() + "' en el classpath.", e);
        }
    }

    public static String getPropiedad(String name) {
        return properties.getProperty(name);
    }

    public static int getPropiedadInt(String propiedad) {
        String prop = getPropiedad(propiedad);
        int valor = 0;
        if (prop != null) {
            try {
                valor = Integer.parseInt(prop);
            } catch (NumberFormatException e) {
                log.warn("La propiedad " + propiedad + " no tiene un numero valido. Se utilizara 0.");
            }
        }
        return valor;
    }

    public static boolean getPropiedadBoolean(String propiedad) {
        return getPropiedadBoolean(propiedad, false);
    }

    public static boolean getPropiedadBoolean(String propiedad, boolean porDefecto) {
        String prop = getPropiedad(propiedad);
        if (prop != null) {
            prop = prop.trim();
            if (prop.equalsIgnoreCase("true") || prop.equalsIgnoreCase("yes") || prop.equalsIgnoreCase("verdad") || prop.equalsIgnoreCase("si")) {
                return true;
            }
        }
        return porDefecto;
    }

    private static void cargarConfiguracion() {
        for (Enumeration<?> e = properties.propertyNames(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            String valor = interpolar(key, 1);
            if (valor != null) {
                properties.setProperty(key, valor);
            }
        }
    }

    private static String interpolar(String key, int nivel) {
        if (nivel > NIVEL) {
            throw new IllegalArgumentException((new StringBuilder("Demasiados niveles de recursi\363n para la propiedad ")).append(key).toString());
        }
        String valor = properties.getProperty(key);
        int desde = 0;
        StringBuffer resultado = null;
        int fin;
        for (; desde < valor.length(); desde = fin + 1) {
            int inicio = valor.indexOf("${", desde);
            if (inicio < 0) {
                break;
            }
            fin = valor.indexOf('}', inicio);
            if (fin < 0) {
                break;
            }
            String prop = valor.substring(inicio + 2, fin);
            if (resultado == null) {
                resultado = new StringBuffer(valor.substring(desde, inicio));
            } else {
                resultado.append(valor.substring(desde, inicio));
            }
            if (properties.containsKey(prop)) {
                String nValor = interpolar(prop, nivel + 1);
                if (nValor != null) {
                    resultado.append(nValor);
                    properties.setProperty(prop, nValor);
                } else {
                    resultado.append(properties.getProperty(prop));
                }
            }
        }
        if (resultado != null && desde < valor.length()) {
            resultado.append(valor.substring(desde));
        }
        return resultado != null ? resultado.toString() : null;
    }
}
