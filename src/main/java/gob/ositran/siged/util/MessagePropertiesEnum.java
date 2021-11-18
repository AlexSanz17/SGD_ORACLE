/*LICENCIA DE USO DEL SGD .TXT*/package gob.ositran.siged.util;

/**
 *
 * @author javier castillo
 */
public enum MessagePropertiesEnum {

    STRUTS_ERROR_FILE_TOO_LARGE("struts.messages.error.file.too.large"),
    NRODOC_GENERATED("nrodocumento.generated"),
    FIRMA_ENUMERADO("documento.firmadoenumerado"),
    DOC_ADDED("documento.added"),
    VERSIONED("nrodocumento.versionado"),
    NEW_DOCUMENTO_SAVED("mensaje.nuevoDocumento.save");
    private String key;

    MessagePropertiesEnum(String key) {
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
