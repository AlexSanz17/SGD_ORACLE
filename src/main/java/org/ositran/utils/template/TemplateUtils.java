package org.ositran.utils.template;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.container.NoSuchElementException;
import com.sun.star.container.XNameAccess;
import com.sun.star.container.XNameContainer;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XModel;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.text.XBookmarksSupplier;
import com.sun.star.text.XText;
import com.sun.star.text.XTextContent;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextRange;
import com.sun.star.ucb.XFileIdentifierConverter;
import com.sun.star.uno.AnyConverter;
import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.CloseVetoException;
import com.sun.star.util.XCloseable;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.ositran.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateUtils {

   private static Logger log = LoggerFactory.getLogger(TemplateUtils.class);

   private static boolean insertImageAtBookmark(String bookmark, String pathImage, XComponent xDocumento) {
      pathImage = filePathToURL(pathImage);
      log.debug("Bookmark {} pathImage {}", bookmark, pathImage);

      try {
         if (xDocumento != null) {
            XBookmarksSupplier bookmarksSupplier = (XBookmarksSupplier) UnoRuntime.queryInterface(XBookmarksSupplier.class, xDocumento);
            XTextRange xTextRange = getBookmarkTextRange(bookmarksSupplier, bookmark);

            if (xTextRange == null) {
               return false;
            }

            XText xText = xTextRange.getText();
            XTextCursor xCursor = xText.createTextCursor();
            XModel xModel = (XModel) UnoRuntime.queryInterface(XModel.class, xDocumento);
            XMultiServiceFactory xMSF = (XMultiServiceFactory) UnoRuntime.queryInterface(XMultiServiceFactory.class, xModel);
            XNameContainer xBitmapContainer = (XNameContainer) UnoRuntime.queryInterface(XNameContainer.class, xMSF.createInstance("com.sun.star.drawing.BitmapTable"));
            XTextContent xImage = (XTextContent) UnoRuntime.queryInterface(XTextContent.class, xMSF.createInstance("com.sun.star.text.TextGraphicObject"));
            XPropertySet xProps = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xImage);

            xCursor.gotoRange(xTextRange, false);
            xBitmapContainer.insertByName("MyTempID", pathImage);

            String internalURL = AnyConverter.toString(xBitmapContainer.getByName("MyTempID"));

            xProps.setPropertyValue("AnchorType", com.sun.star.text.TextContentAnchorType.AS_CHARACTER);
            xProps.setPropertyValue("GraphicURL", internalURL);
            xProps.setPropertyValue("Width", new Integer(3000));
            xProps.setPropertyValue("Height", new Integer(3000));

            xText.insertTextContent(xCursor, xImage, true);
            xBitmapContainer.removeByName("MyTempID");
            return true;
         }

         log.error("No se pudo insertar imagen en el bookmark {} ", bookmark);
         return false;
      } catch (MalformedURLException e) {
         log.error("El nombre de archivo es incorrecto", e);
         return false;
      } catch (BootstrapException e) {
         log.error("No se pudo inicializar Openoffice", e);
         return false;
      } catch (com.sun.star.io.IOException e) {
         log.error(e.getMessage(), e);
         return false;
      } catch (java.lang.Exception e) {
         log.error(e.getMessage(), e);
         return false;
      }
   }
   /**REN Inserta texto en la ubicaci�n del Bookmark ----------------------------------------------------*/
   private static boolean insertTextAtBookmark(String bookmark, String text, XComponent xDocumento) {
      log.debug("Bookmark {} texto a insertar {}", bookmark, text);

      try {
         if (xDocumento != null) {
            XBookmarksSupplier bookmarksSupplier = (XBookmarksSupplier) UnoRuntime.queryInterface(XBookmarksSupplier.class, xDocumento);
            XTextRange xTextRange = getBookmarkTextRange(bookmarksSupplier, bookmark);

            if (xTextRange == null) {
               return false;
            }

            XText xText = xTextRange.getText();
            XTextCursor xCursor = xText.createTextCursor();
            xCursor.gotoRange(xTextRange, false);
            xText.insertString(xCursor, text, true);
            return true;
         }

         log.error("No se pudo insertar texto en el bookmark {} ", bookmark);
         return false;
      } catch (MalformedURLException e) {
         log.error("El nombre de archivo es incorrecto", e);
         return false;
      } catch (BootstrapException e) {
         log.error("No se pudo inicializar Openoffice", e);
         return false;
      } catch (com.sun.star.io.IOException e) {
         log.error(e.getMessage(), e);
         return false;
      } catch (java.lang.Exception e) {
         log.error(e.getMessage(), e);
         return false;
      }
   }

   /**REN Se conecta a Open Office y enumera o firma el documento ---------------------------------------*/
   public synchronized static boolean modifyDocument(String archivo, Map<String, Object> metadata, int whatToDo) {
      boolean modificado = false;
      boolean resultado = false;
      int port = SocketOpenOfficeConnection.DEFAULT_PORT;

      OpenOfficeConnection connection = new SocketOpenOfficeConnection(port);

      try {
         log.debug("Conectando a OpenOffice en puerto {}", port);
         connection.connect();
      } catch (ConnectException officeNotRunning) {
         log.error("Fallo la conexion. Verifique que existe un OpenOffice corriendo y escuchando en el puerto {}", port);
         return false;
      }

      try {
         XFileIdentifierConverter fileContentProvider = connection.getFileContentProvider();
         archivo = fileContentProvider.getFileURLFromSystemPath("", archivo);
         log.debug("url del archivo a enumerar {}", archivo);
         XComponent documento = getDocumento(connection, archivo);

         if (documento != null) {
            switch (whatToDo) {
               case Constantes.DO_ENUMERAR:
                  if (metadata != null) {
                     String bookmark = (String) metadata.get("BOOKMARK");
                     String text = (String) metadata.get("TEXTO");
                     modificado = insertTextAtBookmark(bookmark, text, documento);
                  }

                  if (modificado) {
                     guardarDocumento(documento, archivo, "MS Word 97");
                     resultado = true;
                  } else {
                     log.info("No se ha enumerado el documento");
                     resultado = false;
                  }

                  break;
               case Constantes.DO_FIRMAR:
                  if (metadata != null) {
                     String bookmarkFirma = (String) metadata.get("bookmarkFirma");
                     String rutaArchivo = (String) metadata.get("rutaArchivo");
                     log.debug("rutaArchivo: {}", rutaArchivo);
                     String rutaFirmaDigital = (String) metadata.get("rutaFirmaDigital");
                     log.debug("rutaFirmaDigital: {}", rutaFirmaDigital);
                     modificado = insertImageAtBookmark(bookmarkFirma, rutaFirmaDigital, documento);
                  }

                  if (modificado) {
                     guardarDocumento(documento, archivo, "MS Word 97");
                     resultado = true;
                  } else {
                     log.info("No se ha firmado el documento");
                     resultado = false;
                  }

                  break;
            }

            return resultado;
         }

         log.error("No se pudo abrir el archivo " + archivo);
         return false;
      } catch (MalformedURLException e) {
         log.error("El nombre de archivo es incorrecto", e);
         return false;
      } catch (BootstrapException e) {
         log.error("No se pudo inicializar Openoffice", e);
         return false;
      } catch (com.sun.star.io.IOException e) {
         log.error("Ocurrio un error guardando el archivo " + archivo, e);
         return false;
      } finally {
         log.info("-- desconectando de OpenOffice");
         connection.disconnect();
      }
   }

    private static XComponent getDocumento(OpenOfficeConnection connection, String url) throws MalformedURLException, BootstrapException {
        try {
            Map<String, Object> loadProperties = new HashMap<String, Object>();
            loadProperties.put("AsTemplate", Boolean.TRUE);
            loadProperties.put("Hidden", Boolean.TRUE);

            return loadDocument(connection, url, loadProperties);
        } catch (Exception e) {
            log.error("Ocurrio un error al crear la instancia del desktop", e);
            return null;
        }
    }

    private static String filePathToURL(String archivo) {
        File f = new File(archivo);
        StringBuilder sb = new StringBuilder("file:///");
        try {
            sb.append(f.getCanonicalPath().replace('\\', '/'));
        } catch (IOException e) {
        }
        return sb.toString();
    }

    private static void guardarDocumento(XComponent documento, String url, String tipo) throws com.sun.star.io.IOException {
        Map<String, Object> storeProperties = new HashMap<String, Object>();
        if (tipo != null && !tipo.equals("")) {
            storeProperties.put("FilterName", tipo);
        }
        storeDocument(documento, url, storeProperties);
    }

    /**REN Se encarga de buscar el inicio del Bookmark --------------------------------------------------*/
    private static XTextRange getBookmarkTextRange(XBookmarksSupplier bookmarksSupplier, String bookmarkName) throws java.lang.Exception {
      XTextRange textRange = null;
      XNameAccess bookmarkNames = bookmarksSupplier.getBookmarks();
      Object bmk = null;

      try {
         bmk = bookmarkNames.getByName(bookmarkName);
      } catch (NoSuchElementException e) {
         //log.error(e.getMessage(), e);
      }

      if (bmk == null) {
         //throw new java.lang.Exception("No se encontro el bookmark [" + bookmarkName + "]");
         log.info("No se encontro el bookmark [" + bookmarkName + "]");

         return null;
      }

      XTextContent bookmarkContent = (XTextContent) UnoRuntime.queryInterface(XTextContent.class, bmk);
      textRange = bookmarkContent.getAnchor();

      return textRange;
   }

    @SuppressWarnings("rawtypes")
	private static XComponent loadDocument(OpenOfficeConnection connection, String inputUrl, Map loadProperties) throws com.sun.star.io.IOException, com.sun.star.lang.IllegalArgumentException {
        XComponentLoader desktop = connection.getDesktop();
        return desktop.loadComponentFromURL(inputUrl, "_blank", 0, toPropertyValues(loadProperties));
    }

    @SuppressWarnings("rawtypes")
	private static void storeDocument(XComponent document, String outputUrl, Map storeProperties) throws com.sun.star.io.IOException {
        try {
            XStorable storable = (XStorable) UnoRuntime.queryInterface(XStorable.class, document);
            storable.storeToURL(outputUrl, toPropertyValues(storeProperties));
        } finally {
            XCloseable closeable = (XCloseable) UnoRuntime.queryInterface(XCloseable.class, document);
            if (closeable != null) {
                try {
                    closeable.close(true);
                } catch (CloseVetoException closeVetoException) {
                    log.warn("document.close() vetoed");
                }
            } else {
                document.dispose();
            }
        }
    }

    @SuppressWarnings("rawtypes")
	protected static PropertyValue[] toPropertyValues(Map/*<String,Object>*/ properties) {
        PropertyValue[] propertyValues = new PropertyValue[properties.size()];
        int i = 0;
        for (Iterator iter = properties.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object value = entry.getValue();
            if (value instanceof Map) {
                // recursively convert nested Map to PropertyValue[]
                Map subProperties = (Map) value;
                value = toPropertyValues(subProperties);
            }
            propertyValues[i++] = property((String) entry.getKey(), value);
        }
        return propertyValues;
    }

    protected static PropertyValue property(String name, Object value) {
        PropertyValue property = new PropertyValue();
        property.Name = name;
        property.Value = value;
        return property;
    }
}
