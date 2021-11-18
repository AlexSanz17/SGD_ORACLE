/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.invokers;

import java.net.*;
import java.io.*;
import org.apache.log4j.Logger;

/**
 * Transports requests over http.
 */
public class Soap
{
    public static final java.lang.String URI_NS_SOAP_ENCODING
        = "http://schemas.xmlsoap.org/soap/encoding/";

    public static final java.lang.String URI_NS_SOAP_ENVELOPE
        = "http://schemas.xmlsoap.org/soap/envelope/";

    public static final java.lang.String URI_SOAP_ACTOR_NEXT
        = "http://schemas.xmlsoap.org/soap/actor/next";

    public static final String SOAP_ACTION_URI = "SOAPAction";

    public static final String NAME_SOAP_ENVELOPE = "Envelope";

    public static final String NAME_SOAP_BODY = "Body";

    public static final String NAME_SOAP_HEADER = "Header";

    public static final String NAME_SOAP_FAULT = "Fault";

    public static final String ATTR_ENCODING_STYLE = "encodingStyle";

    public static final String ENCODING_STYLE_SOAP = URI_NS_SOAP_ENCODING;
    String address="";
    String soapAction="";
    String fileName=""; 
    FileInputStream  objFileInputStream=null;
    DataInputStream  objDataInputStream=null;
    FileInputStream  objFileOutputStream=null;
    String strDemo="";
    String strResponse="";	
    StringBuffer  objStringBuffer= new StringBuffer();

    private static Logger logger=Logger.getLogger(Soap.class);
    
    public Soap() {
    
    }

    public void setValue(String strFileValue)
    {
    try
     {
     
       objStringBuffer.append(strFileValue);  
      
    }catch(Exception e){strDemo=""+e.getMessage();}
    
   } 

    public String getValue()
    {
    
     strDemo=objStringBuffer.toString();
  
    return strDemo;
   }
    public void setRequest(String address1,String soapAction1,String fileName1){

    address=address1;
    soapAction=soapAction1;
    fileName=fileName1;; 

    
    }
    
   
 
    public String getRequest()
    throws MalformedURLException, IOException
    {
        URL url;
        FileInputStream fis;
        String objString=new String();

        url = new URL(address);

       
        HttpURLConnection conn;
        OutputStream os = null;
        // marshal the request.
        try {
            conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
           conn.setRequestProperty("Content-Type", "text/xml; charset=ISO-8859-1");
            // copy the action URI to the request
            if(soapAction != null)
              conn.setRequestProperty(SOAP_ACTION_URI, soapAction);
              conn.setRequestProperty("Accept", "text/xml");
              //conn.setRequestProperty("Accept", "text/xml; charset=UTF-8");

            conn.setDoInput(true);
            conn.setDoOutput(true);

            os = conn.getOutputStream();

            int count=0;
            
            byte[] buf = strDemo.getBytes();
            os.write(buf);
           
        }
        finally {
            if(os != null) {
                try {
                    os.flush();
                    //os.close();
                }
                catch(IOException ex) {
                    objString=""+ex.toString();
                }
            }
        }

        // unmarshal the response.
        InputStream is = null;
        InputStreamReader reader =null;
        try {
            int rCode;

            try {
                is = conn.getInputStream();
                rCode = conn.getResponseCode();
	

            }
            catch(java.io.IOException ex) {
                objString="Error in Soap message"+ex.toString();
                // bug in URL connection, gotta parse to get the reponse code
                String hdr = conn.getHeaderField(0);

                if(hdr == null)
                    throw new IllegalStateException("No header in the recieved reply.");

                int idx1 = hdr.indexOf(' ');
                int idx2 = hdr.indexOf(' ', idx1+1);
                rCode = Integer.parseInt(hdr.substring(idx1+1, idx2));

                is = conn.getErrorStream();
            }

            boolean isFault;
            if(rCode >= HttpURLConnection.HTTP_OK && rCode < (HttpURLConnection.HTTP_OK+100))
                isFault = false;
            else if(is != null && rCode == HttpURLConnection.HTTP_INTERNAL_ERROR)
                isFault = true;
            else
                throw new IllegalStateException("Unexpected reply status code " + rCode);


            reader = new InputStreamReader(is);
            char[] buf;
            CharArrayWriter caw = new CharArrayWriter();
            char [] tbuf = new char[1024];
            int r;
            while((r = reader.read(tbuf)) >= 0)
                caw.write(tbuf, 0, r);
            buf = caw.toCharArray();
            caw.close();
            //logger.debug(buf);
            //logger.debug(buf.toString());
            for(int intBuf=0;intBuf<buf.length;intBuf++){
            	/*if(intBuf==0){
            		objString="";
            	}
            	else{*/
            		if(buf[intBuf]=='>')
                    objString=objString.concat(">"+"\n");
                    else if(buf[intBuf]=='<')
                    objString=objString.concat(/*"\n"+*/"<");
                    else 
                    objString=objString.concat(""+buf[intBuf]);
            	}
            
           /* }*/
	   
        return objString;

        }
        finally {
            if(is != null) {
                try {
                    is.close();
                }
                catch(IOException ex) {
                    objString="Error in Soap "+ex.toString();

                }
            }
            if(os != null) {
                try {
                    os.close();
                }
                catch(IOException ex) {
                    objString="Error in Soap "+ex.toString();
                }
            }
            if (reader != null){
                reader.close();
            }
        return ""+objString;
        }

    }

    /*public static void main(String[] args) {
       Client client = new Client();
        try {
            //address, service name in wsdl, soap message.
            client.setRequest(args[0], args[1], args[2]);
            logger.debug("out"+client.getRequest());
       } catch(Throwable t) {
            t.printStackTrace();
        }
    }*/
}
