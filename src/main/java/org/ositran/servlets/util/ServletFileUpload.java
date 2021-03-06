package org.ositran.servlets.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.RequestContext;
import org.apache.log4j.Logger;
 
/**
 * come from commons-fileupload
 * @author alfred
 */
public class ServletFileUpload extends FileUpload {
 
    // ---------------------------------------------------------- Class methods
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ServletFileUpload.class);
    private List errors = new ArrayList();
 
    /**
     * Constructs an uninitialised instance of this class. A factory must be
     * configured, using <code>setFileItemFactory()</code>, before attempting
     * to parse requests.
     *
     * @see FileUpload#FileUpload(FileItemFactory)
     */
    public ServletFileUpload() {
        super();
    }
 
    /**
     * Constructs an instance of this class which uses the supplied factory to
     * create <code>FileItem</code> instances.
     *
     * @see FileUpload#FileUpload()
     */
    public ServletFileUpload(FileItemFactory fileItemFactory) {
        super(fileItemFactory);
    }
 
    /**
     * overide parseRequest
     */
    @SuppressWarnings("deprecation")
	public List /* FileItem */ parseRequest(RequestContext ctx) throws FileUploadException {
        if (ctx == null) {
            throw new NullPointerException("ctx parameter");
        }
 
        ArrayList items = new ArrayList();
        String contentType = ctx.getContentType();
 
        if ((null == contentType) || (!contentType.toLowerCase().startsWith(MULTIPART))) {
            // throw new InvalidContentTypeException("the request doesn't contain a " + MULTIPART_FORM_DATA + " or " + MULTIPART_MIXED + " stream, content type header is " + contentType);
            logger.error("the request doesn't contain a " + MULTIPART_FORM_DATA + " or " + MULTIPART_MIXED + " stream, content type header is " + contentType);
            errors.add("the request doesn't contain a " + MULTIPART_FORM_DATA + " or " + MULTIPART_MIXED + " stream, content type header is " + contentType);
        }
        int requestSize = ctx.getContentLength();
 
        if (requestSize == -1) {
            // throw new UnknownSizeException(
            // "the request was rejected because its size is unknown");
            logger.error("the request was rejected because its size is unknown");
            errors.add("the request was rejected because its size is unknown");
        }
 
        String charEncoding = getHeaderEncoding();
        if (charEncoding == null) {
            charEncoding = ctx.getCharacterEncoding();
        }
 
        try {
            byte[] boundary = getBoundary(contentType);
            if (boundary == null) {
                // throw new FileUploadException(
                // "the request was rejected because "
                // + " no multipart boundary was found  ");
                logger.error("the request was rejected because no multipart boundary was found");
                errors.add("the request was rejected because no multipart boundary was found");
            }
 
            InputStream input = ctx.getInputStream();
 
            MultipartStream multi = new MultipartStream( input, boundary );
          
            multi.setHeaderEncoding(charEncoding);
 
            boolean nextPart = multi.skipPreamble();
            while (nextPart) {
                Map headers = parseHeaders(multi.readHeaders());
                String fieldName = getFieldName(headers);
                if (fieldName != null) {
                    String subContentType = getHeader(headers, CONTENT_TYPE);
                    if (subContentType != null && subContentType.toLowerCase().startsWith(MULTIPART_MIXED)) {
                        // Multiple files.
                        byte[] subBoundary = getBoundary(subContentType);
                        multi.setBoundary(subBoundary);
                        boolean nextSubPart = multi.skipPreamble();
                        while (nextSubPart) {
                            headers = parseHeaders(multi.readHeaders());
                            if (getFileName(headers) != null) {
                                FileItem item = createItem(headers, false);
                                OutputStream os = item.getOutputStream();
                                try {
                                    multi.readBodyData(os);
                                } finally {
                                    os.close();
                                }
                                items.add(item);
                            } else {
                                // Ignore anything but files inside
                                // multipart/mixed.
                                multi.discardBodyData();
                            }
                            nextSubPart = multi.readBoundary();
                        }
                        multi.setBoundary(boundary);
                    } else {
                        FileItem item = createItem(headers, getFileName(headers) == null);
                        OutputStream os = item.getOutputStream();
                        try {
                            multi.readBodyData(os);
                        } finally {
                            os.close();
                        }
                        items.add(item);
                    }
                } else {
                    // Skip this part.
                    multi.discardBodyData();
                }
                nextPart = multi.readBoundary();
            }
            // remove SizeLimitExceededException
            if (getSizeMax() >= 0 && requestSize > getSizeMax()) {
                // throw new SizeLimitExceededException(
                // "the request was rejected because its size (" + requestSize
                // + ") exceeds the configured maximum (" + getSizeMax() + ")",
                // requestSize, getSizeMax());
                logger.error("SE PASO DEL TAMA:O the request was rejected because its size (" + requestSize + ") exceeds the configured maximum (" + getSizeMax() + ")");
            }
        } catch (IOException e) {
            logger.error("Processing of " + MULTIPART_FORM_DATA + " request failed. " + e.getMessage());
            errors.add("Processing of " + MULTIPART_FORM_DATA + " request failed. " + e.getMessage());
        // throw new FileUploadException(
        // "Processing of " + MULTIPART_FORM_DATA
        // + " request failed. " + e.getMessage());
        }
 
        return items;
    }
 
    /**
     * @return the errors
     */
    public List getErrors() {
        return errors;
    }
 
    /**
     * @param errors the errors to set
     */
    public void setErrors(List errors) {
        this.errors = errors;
    }
}