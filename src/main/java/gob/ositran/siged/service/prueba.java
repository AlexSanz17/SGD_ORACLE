/*LICENCIA DE USO DEL SGD .TXT*/package gob.ositran.siged.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 JSONParser parser = new JSONParser();

	        try {
	            URL oracle = new URL("http://23.254.129.225/gpstracking/api/position"); // URL to Parse
	            URLConnection yc = oracle.openConnection();
	            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));


	            /*NUM_package    NUMBER(10),
	            NUM_SEQ        NUMBER(5),
	            COD_PLATE      VARCHAR2(30 BYTE),
	            DEV_ID         VARCHAR2(30 BYTE),
	            FEC_ON_DATE    VARCHAR2(30 BYTE),
	            FEC_DATE_TIME  DATE,
	            VAL_SPEED      NUMBER,
	            VAL_DIRECTION  NUMBER,
	            VAL_LATITUDE   NUMBER,
	            VAL_LONGITUDE  NUMBER,
	            DES_COMPANY    VARCHAR2(100 BYTE)

*/

	            //select * from /*LICENCIA DE USO DEL SGD .TXT*/package_det_vehicle_position where des_company like '%xyz%'
	            //update /*LICENCIA DE USO DEL SGD .TXT*/package_det_vehicle_position set des_company = replace(des_company,'xyz','&')

	            String inputLine;
	            int j=0;


	            while ((inputLine = in.readLine()) != null) {
	                JSONArray a = (JSONArray) parser.parse(inputLine);
	              //  System.out.println("valor=" + inputLine + "\n");
	                // Loop through each item
	                int i=0;
	                for (Object o : a) {
                        i = i + 1;

	                    JSONObject tutorials = (JSONObject) o;
	                    String plate =  (String) tutorials.get("plate");
	                    String imei =   (String) tutorials.get("imei");
	                    String ondate =   (String) tutorials.get("ondate");
	                    String datetime =   (String) tutorials.get("datetime");
	                    String speed =   (String) tutorials.get("speed");
	                    String direction =   (String) tutorials.get("direction");
	                    String latitude =   (String) tutorials.get("latitude");
	                    String longitude =   (String) tutorials.get("longitude");
	                    String company =   (String) tutorials.get("company");
                        String cadenita = "insert into /*LICENCIA DE USO DEL SGD .TXT*/package_det_vehicle_position (num_/*LICENCIA DE USO DEL SGD .TXT*/package, num_seq,cod_plate, dev_id,fec_on_date, fec_date_time, val_speed, val_direction, val_latitude, val_longitude, des_company) values(";

                        if (plate==null)
                        	plate =  null;
                        else
                        	plate = "'" + plate + "'";

                        if (imei ==null)
                        	imei =  null;
                        else
                        	imei = "'" + imei + "'";

                        if (ondate==null)
                        	ondate =  null;
                        else
                        	ondate = "'" + ondate + "'";

                        if (datetime==null)
                        	datetime =  null;
                        else
                        	datetime = "'" + datetime + "'";

                        if (company==null)
                        	company =  null;
                        else
                        	company = "'" + company + "'";


                        cadenita = cadenita + "1," + i +  "," + plate + ","   +   imei +  "," + ondate + ",to_date(" + datetime + ",'dd/MM/yyyy HH:Mi:SS am')," ;
                        cadenita = cadenita + speed +"," +  direction + "," + latitude + "," + longitude + "," + company.replaceAll("&", "xyz") +");";

	                    //System.out.println("i=" + i + "|Plate=" + plate + "|imei=" +imei + "|ondate=" + ondate + "|datetime=" +datetime + "|speed=" + speed + "|direction=" + direction + "|latitude="+latitude + "|longitude=" +longitude + "|company=" + company );
                        System.out.println(cadenita);

	                  //  String title = (String) tutorials.get("post_title");
	                  //  System.out.println("Post Title : " + title);


	                }
	            }
	            in.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	}


}
