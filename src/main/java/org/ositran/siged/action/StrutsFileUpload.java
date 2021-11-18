package org.ositran.siged.action;

/**
 *
 * @author josh
 */
import gob.ositran.siged.config.SigedProperties;
import gob.ositran.siged.util.MessagePropertiesEnum;
import gob.ositran.siged.util.SigedMessageSource;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.ositran.utils.Constantes;

public class StrutsFileUpload {

    private File upload;//The actual file
    private String uploadContentType; //The content type of the file
    private String uploadFileName; //The uploaded file name
    private String contentType;
    private static final String sUsuario = "uploadServlet";
    private static Logger _log = Logger.getLogger(StrutsFileUpload.class);
    private static final String FILE_TOO_LARGE = "-1";
    private static final String ERROR_ON_UPLOAD = "-2";
    private SigedMessageSource messageSource;


    public void uploadFile() throws IOException {
        String rpta = FILE_TOO_LARGE;
        if (upload == null) {
            _log.error(messageSource.getMessage(MessagePropertiesEnum.STRUTS_ERROR_FILE_TOO_LARGE));
        }else{
            try{
                String fileName = uploadFileName;
                _log.debug("Datos del archivo a subir");
                _log.debug("Nombre [" + fileName + "] Size [" + upload.length() + "] Tipo [" + uploadContentType + "]");
                String sAbsolutePath = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.UPLOAD_CARPETA_TEMPO);
                Date datFechaActual = Calendar.getInstance().getTime();
                Random generador = new Random(datFechaActual.getTime());
                String sNewName = Constantes.ARCHIVO_BRACKET_INICIO + sUsuario + '_' + DateFormatUtils.format(datFechaActual, "yyyyMMddHHmmss") +generador.nextLong()+ '_' + Constantes.ARCHIVO_BRACKET_FIN + fileName;
                _log.debug("Destino [" + sAbsolutePath + "] Nombre [" + sNewName + "]");

                File fArchivo = new File(sAbsolutePath, sNewName);
                FileUtils.moveFile(upload, fArchivo);
                _log.debug("Archivo movido satisfactoriamente");
                rpta = fArchivo.getAbsolutePath();
            }catch(Exception ex){
                rpta = ERROR_ON_UPLOAD;
                _log.error("Ocurrio un error al tratar de subir el archivo: " + uploadFileName,ex);
            }
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/plain");
        response.getWriter().write(rpta);
        response.getWriter().flush();
        response.getWriter().close();
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setMessageSource(SigedMessageSource ms) {
        this.messageSource = ms;
    }

}
