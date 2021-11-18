/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.common.reportes.birt;

import gob.ositran.siged.config.SigedProperties;
import java.util.Properties;
import java.util.logging.Level;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.IPlatformContext;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.framework.PlatformServletContext;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;

public class BirtEngine {

    private static IReportEngine birtEngine = null;
    private static Properties configProps = new Properties();
    private static Logger log = Logger.getLogger(BirtEngine.class);

    public static synchronized void initBirtConfig() {
    }

    public static synchronized IReportEngine getBirtEngine(ServletContext sc) {
        if (birtEngine == null) {
            EngineConfig config = new EngineConfig();
            if (configProps != null) {
                String logLevel = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.BIRT_LOG_LEVEL);
                Level level = Level.OFF;
                if ("SEVERE".equalsIgnoreCase(logLevel)) {
                    level = Level.SEVERE;
                } else if ("WARNING".equalsIgnoreCase(logLevel)) {
                    level = Level.WARNING;
                } else if ("INFO".equalsIgnoreCase(logLevel)) {
                    level = Level.INFO;
                } else if ("CONFIG".equalsIgnoreCase(logLevel)) {
                    level = Level.CONFIG;
                } else if ("FINE".equalsIgnoreCase(logLevel)) {
                    level = Level.FINE;
                } else if ("FINER".equalsIgnoreCase(logLevel)) {
                    level = Level.FINER;
                } else if ("FINEST".equalsIgnoreCase(logLevel)) {
                    level = Level.FINEST;
                } else if ("OFF".equalsIgnoreCase(logLevel)) {
                    level = Level.OFF;
                }

                config.setLogConfig(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.BIRT_LOG_DIR), level);
            }

            config.setEngineHome("");
            IPlatformContext context = new PlatformServletContext(sc);
            config.setPlatformContext(context);

            try {
                Platform.startup(config);
            } catch (BirtException e) {
                log.error("Error al iniciar plataforma BIRT", e);
            }

            IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
            birtEngine = factory.createReportEngine(config);


        }
        return birtEngine;
    }

    public static synchronized void destroyBirtEngine() {
        if (birtEngine == null) {
            return;
        }
        birtEngine.shutdown();
        Platform.shutdown();
        birtEngine = null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
