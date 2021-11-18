/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.ositran.pide.WSPideTramite;
import com.ositran.ws.ConsultaTramite;
import com.ositran.ws.IOTramiteService;
import com.ositran.ws.IOTramite;
import com.ositran.ws.IoTipoDocumentoTramite;
import com.ositran.ws.RespuestaConsultaTramite;
import java.net.URL;
import org.ositran.utils.StringUtil;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.JSONObject;
import org.ositran.utils.Constantes;
import org.ositran.utils.SigedUtils;

/**
 *
 * @author consultor_jti15
 */
public class x {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         try {
                double c = (double)11/3;
                System.out.println("x=" +c);
                DecimalFormat df = new DecimalFormat("#.00");
                System.out.println(df.format(c));
                
                String m = "10-20-30|";
                m = m.substring(0, m.length()-1);
                
                System.out.println("m =" + m);
                
                String[] lstUsuarios = StringUtil.stringToArrayPersonalizado(m,'|');
                     System.out.println("tamano=" + lstUsuarios.length);
                    for(int i=0;i<lstUsuarios.length;i++){
                        System.out.println("datos=" + lstUsuarios[i]);
                        String[] lstDatos = lstUsuarios[i].split("-"); 
                        for(int j =0;j<lstDatos.length;j++){
                           System.out.println("valores===" + lstDatos[j]);   
                        }
                    }
                      /*JSONObject j = new JSONObject(SigedUtils.readUrlHttps("https://servicios.ositran.gob.pe:8443/WSDNI/consulta?dni=" + "10025699"));
                      if (j.getString("coResultado").equals("0000")) {
                          JSONObject d = new JSONObject(j.get("datosPersona").toString());
                         
                         System.out.println("d====" + d.getString("apPrimer") + " " );
                                             
                       
                           
                          // System.out.println("Valorsito=====" + d.get("apPrimer"));
                        
                    }
*/
                    
                } catch (Exception ep) {
                   
                    ep.printStackTrace();
                }
    }
    
}
