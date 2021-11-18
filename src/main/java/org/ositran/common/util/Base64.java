/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.common.util;

import java.io.UnsupportedEncodingException;
import org.apache.log4j.Logger;

  

public class Base64 {

        private static Logger log =Logger.getLogger(Base64.class);
	public static String _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

	
	public static boolean  esNumero (String cadena) {
		
		boolean esnumero = false ; 
		try {
			
			Double num = new Double(cadena);
			esnumero =true;
		}catch(Exception e ){
			esnumero =false ;
		}
		
		return esnumero ;
	}	

public static String encode (String input) {
	StringBuilder output = new StringBuilder("");
	char chr1, chr2, chr3, enc1, enc2, enc3, enc4;
	int  i = 0;

	// input = Base64._utf8_encode(input);

	while (i < input.length()) {

		chr1 = input.charAt(i++);
		chr2 = input.charAt(i++);
		chr3 = input.charAt(i++);

		enc1 = (char) (chr1 >> 2);
		enc2 = (char)(  ( (chr1 & 3) << 4) | (chr2 >> 4));
		enc3 = (char)(((chr2 & 15) << 2) | (chr3 >> 6));
		enc4 = (char)( chr3 & 63);
		

		if (esNumero(""+chr2)) {
			enc3 = enc4 = 64;
		} else if (esNumero(""+chr3)) {
			enc4 = 64;
		}

		output.append(_keyStr.charAt(enc1));
		output.append(_keyStr.charAt(enc2));
		output.append(_keyStr.charAt(enc3));
		output.append(_keyStr.charAt(enc4));

	}

	return output.toString();
}

public static String decode (String input) throws UnsupportedEncodingException { 
	StringBuilder output = new StringBuilder("");
	char chr1, chr2, chr3;
	char enc1, enc2, enc3, enc4;
	int i = 0;
	log.debug("decode ->"+input);
	//input = input.replaceAll("[^A-Za-z0-9\\p{/}\\p{+}\\p{=}]", "");

	 
	while (input!=null&&i < input.length()) { 
            
		enc1 = (char)_keyStr.indexOf(input.charAt(i++)); 
		enc2 = (char) _keyStr.indexOf(input.charAt(i++));
		enc3 = (char)_keyStr.indexOf(input.charAt(i++));
		enc4 = (char)_keyStr.indexOf(input.charAt(i++));

		chr1 = (char)((enc1 << 2) | (enc2 >> 4));
		chr2 = (char)(((enc2 & 15) << 4) | (enc3 >> 2));
		chr3 = (char)(((enc3 & 3) << 6) | enc4);

		output.append(chr1);

		if (enc3 != 64) {
			output.append(chr2);
		}
		if (enc4 != 64) {
			output.append(chr3);
		}

	}
	return new String(output.toString().getBytes("ISO8859-1"),"UTF-8");

}
	


}


			
	 
