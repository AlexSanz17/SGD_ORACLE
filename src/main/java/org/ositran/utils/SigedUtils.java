package org.ositran.utils;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class SigedUtils {
        public static String readUrlHttps(String urlString)  {
            StringBuffer buffer=null;
            Reader reader=null;
            try{
             // Create a trust manager that does not validate certificate chains
                TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            URL url = new URL(urlString);
            URLConnection con = url.openConnection();
            reader = new InputStreamReader(con.getInputStream());
            buffer = new StringBuffer();
                int read;
                char[] chars = new char[1024];
                while ((read = reader.read(chars)) != -1)
                    buffer.append(chars, 0, read); 

                return buffer.toString();
            }catch(Exception ex){
                ex.printStackTrace();

            }
            return null;
        }

    
	public static String getDescripcionEstado(Character estado){
		if(estado != null){
			switch (estado){
				case Constantes.ESTADO_ACTIVO:
					return "Activo";
				case Constantes.ESTADO_ANULADO:
					return "Anulado";
				case Constantes.ESTADO_CERRADO:
					return "Archivado";
				case Constantes.ESTADO_INACTIVO:
					return "Inactivo";
				default:
					return "-";
			}
		}
		return "-";
	}
}
