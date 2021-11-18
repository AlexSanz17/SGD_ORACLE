/*LICENCIA DE USO DEL SGD .TXT*/package gob.ositran.siged.config;

import java.net.InetAddress;

public class prueba45 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{

	         System.out.println("Host Name e IP=" + java.net.InetAddress.getLocalHost().getCanonicalHostName() + "-" + java.net.InetAddress.getLocalHost().getHostAddress());

		}catch(Exception e) {
             e.printStackTrace();
		}
	}

}
