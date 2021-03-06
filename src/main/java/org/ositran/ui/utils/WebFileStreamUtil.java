package org.ositran.ui.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Jose Corcuera <jose.corcuera@antartec.com>
 */
public class WebFileStreamUtil {

    private static Log log=LogFactory.getLog(WebFileStreamUtil.class);
        
    public enum AplicationType {

        RTF("rtf");
        private String applicationType;

        AplicationType(String applicationType) {
            this.applicationType = applicationType;
        }

        public String label() {
            return applicationType;
        }
    };

    public static void sendDocumentToResponse(HttpServletResponse response, AplicationType aplicationType, String filePath, String downloadName) {
        FileInputStream fileToDownload = null;
        ServletOutputStream out = null;
        try {
            fileToDownload = new FileInputStream(filePath);
            out = response.getOutputStream();
            response.setContentType("application/" + aplicationType.label());
            response.setHeader("Content-disposition", "inline; filename=" + downloadName + ";");
            response.setContentLength(fileToDownload.available());
            try {
                int c;
                while ((c = fileToDownload.read()) != -1) {
                    out.write(c);
                }
                out.flush();
            } finally {
                out.close();
                fileToDownload.close();
            }
        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage(),ex);
        } catch (IOException ex) {
            log.error(ex.getMessage(),ex);
        }
    }
}
